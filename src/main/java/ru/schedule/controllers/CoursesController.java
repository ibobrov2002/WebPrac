package ru.schedule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.schedule.DAO.ClassesDAO;
import ru.schedule.DAO.ClassroomsDAO;
import ru.schedule.DAO.CoursesDAO;
import ru.schedule.DAO.impl.ClassesDAOImpl;
import ru.schedule.DAO.impl.ClassroomsDAOImpl;
import ru.schedule.DAO.impl.CoursesDAOImpl;
import ru.schedule.models.Classes;
import ru.schedule.models.Classrooms;
import ru.schedule.models.Courses;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CoursesController {

    @Autowired
    private final CoursesDAO coursesDAO = new CoursesDAOImpl();

    @Autowired
    private final ClassesDAO classesDAO = new ClassesDAOImpl();

    @Autowired
    private final ClassroomsDAO classroomsDAO = new ClassroomsDAOImpl();

    @GetMapping("/courses/add")
    public String coursesAdd(Model model) {
        return "coursesAdd";
    }

    @GetMapping("/courses/find")
    public String coursesFind(Model model) {
        return "coursesFind";
    }

    @PostMapping("/courses/add")
    public String coursesPostAdd(@RequestParam String name, @RequestParam String coverage, @RequestParam String intensity, @RequestParam String year, Model model) {
        Courses courses = new Courses(null, name, coverage, Short.parseShort(intensity), Short.parseShort(year));
        coursesDAO.add(courses);
        return "redirect:/courses";
    }

    @PostMapping("/courses/find")
    public String coursesPostFind(@RequestParam String name, @RequestParam String coverage, @RequestParam String intensity, @RequestParam String year, Model model) {
        List<Courses> courses = coursesDAO.getAll();
        Courses courseFind = null;
        for (Courses course : courses) {
            if (name.equals(course.getName()) && coverage.equals(course.getCoverage()) && Short.parseShort(intensity) == course.getIntensity() && Short.parseShort(year) == course.getYear()) {
                courseFind = course;
            }
        }
        if (Objects.isNull(courseFind)) {
            return "error4";
        }
        Optional<Courses> course = Optional.ofNullable(courseFind);
        ArrayList<Courses> res = new ArrayList<>();
        course.ifPresent(res::add);
        model.addAttribute("cour", res);
        return "coursesId";
    }

    @GetMapping("/courses/{id}")
    public String coursesId(@PathVariable(value = "id") long id, Model model) {
        Optional<Courses> courses = Optional.ofNullable(coursesDAO.getById(id));
        if (courses.isEmpty()) {
            return "redirect:/courses";
        }
        ArrayList<Courses> res = new ArrayList<>();
        courses.ifPresent(res::add);
        model.addAttribute("cour", res);
        return "coursesId";
    }

    @GetMapping("/courses/{id}/update")
    public String coursesUpdate(@PathVariable(value = "id") long id, Model model) {
        Optional<Courses> courses = Optional.ofNullable(coursesDAO.getById(id));
        if (courses.isEmpty()) {
            return "redirect:/courses";
        }
        ArrayList<Courses> res = new ArrayList<>();
        courses.ifPresent(res::add);
        model.addAttribute("cour", res);
        return "coursesUpdate";
    }

    @PostMapping("/courses/{id}/update")
    public String coursesPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String coverage, @RequestParam String intensity, @RequestParam String year, Model model) {
        Courses courses = coursesDAO.getById(id);
        courses.setName(name);
        courses.setCoverage(coverage);
        courses.setIntensity(Short.parseShort(intensity));
        courses.setYear(Short.parseShort(year));
        coursesDAO.update(courses);
        return "redirect:/courses/{id}";
    }

    @PostMapping("/courses/{id}/remove")
    public String coursesPostRemove(@PathVariable(value = "id") long id, Model model) {
        Courses courses = coursesDAO.getById(id);
        coursesDAO.delete(courses);
        return "redirect:/courses";
    }

    @GetMapping("/courses/{id}/schedule_add")
    public String coursesScheduleAdd(@PathVariable(value = "id") long id, Model model) {
        Optional<Courses> courses = Optional.ofNullable(coursesDAO.getById(id));
        ArrayList<Courses> res = new ArrayList<>();
        courses.ifPresent(res::add);
        model.addAttribute("cour", res);
        return "coursesScheduleAdd";
    }

    @GetMapping("/courses/{id}/schedule_del")
    public String coursesScheduleDel(@PathVariable(value = "id") long id, Model model) {
        Optional<Courses> courses = Optional.ofNullable(coursesDAO.getById(id));
        ArrayList<Courses> res = new ArrayList<>();
        courses.ifPresent(res::add);
        model.addAttribute("cour", res);
        return "coursesScheduleDel";
    }

    @PostMapping("/courses/{id}/schedule_add")
    public String coursesPostScheduleAdd(@PathVariable(value = "id") long id, @RequestParam String classroom, @RequestParam String time, @RequestParam String day_of_week, Model model) {
        List<Classrooms> classrooms = classroomsDAO.get_free(Short.parseShort(day_of_week), Short.parseShort(day_of_week));
        for (Classrooms classroom1 : classrooms) {
            if (classroom1.getNumber() == Long.parseLong(classroom)) {
                Classes classes = new Classes(null, id, classroom1.getId(), time, Short.parseShort(day_of_week));
                try {
                    List<Courses> courses = coursesDAO.getAll();
                    for (Courses courses1 : courses) {
                        if (courses1.getId() == id && (courses1.getCoverage().equals("stream") && classroom1.getRoominess() >= 100 || courses1.getCoverage().equals("group") && classroom1.getRoominess() >= 25 || courses1.getCoverage().equals("special"))) {
                            for (Courses courses2 : courses) {
                                if (courses1.getYear() == courses2.getYear() && !courses1.equals(courses2)) {
                                    for (Classes classes1 : classesDAO.getAll()) {
                                        if (classes1.getCourse_id() == courses2.getId() && classes1.getTime().equals(time) && classes1.getDay_of_week() == Short.parseShort(day_of_week)) {
                                            return "error9";
                                        }
                                    }
                                }
                            }
                            classesDAO.add(classes);
                            return "redirect:/courses/{id}";
                        }
                    }
                    return "error8";
                } catch (Exception e) {
                    return "error6";
                }
            }
        }
        return "error5";
    }

    @PostMapping("/courses/{id}/schedule_del")
    public String coursesPostScheduleDel(@PathVariable(value = "id") long id, @RequestParam String classroom, @RequestParam String time, @RequestParam String day_of_week, Model model) {
        List<Classrooms> classrooms = classroomsDAO.get_free(Short.parseShort(day_of_week), Short.parseShort(day_of_week));
        for (Classrooms classroom1 : classrooms) {
            if (classroom1.getNumber() == Long.parseLong(classroom)) {
                Classes classes = new Classes(null, id, classroom1.getId(), time, Short.parseShort(day_of_week));
                List<Classes> classesList = classesDAO.getAll();
                for (Classes class1 : classesList) {
                    if (class1.getCourse_id() == id && class1.getClassroom_num() == classroom1.getId() && class1.getTime().equals(time) && class1.getDay_of_week() == classes.getDay_of_week()) {
                        classes.setId(class1.getId());
                        classesDAO.delete(classes);
                        return "redirect:/courses/{id}";
                    }
                }
                return "error7";
            }
        }
        return "error5";
    }
}
