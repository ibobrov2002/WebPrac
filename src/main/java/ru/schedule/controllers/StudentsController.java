package ru.schedule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.schedule.DAO.CoursesDAO;
import ru.schedule.DAO.StudentsDAO;
import ru.schedule.DAO.impl.CoursesDAOImpl;
import ru.schedule.DAO.impl.StudentsDAOImpl;
import ru.schedule.models.Classes;
import ru.schedule.models.Courses;
import ru.schedule.models.Students;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class StudentsController {

    @Autowired
    private final StudentsDAO studentsDAO = new StudentsDAOImpl();

    @Autowired
    private final CoursesDAO coursesDAO = new CoursesDAOImpl();

    @GetMapping("/students/add")
    public String studentsAdd(Model model) {
        return "studentsAdd";
    }

    @GetMapping("/students/find")
    public String studentsFind(Model model) {
        return "studentsFind";
    }

    @PostMapping("/students/add")
    public String studentsPostAdd(@RequestParam String first_name, @RequestParam String lastName, @RequestParam String patronymic, @RequestParam String year, @RequestParam String stream, @RequestParam String group, Model model) {
        Students student = new Students(null, first_name, lastName, patronymic, Short.parseShort(year), Short.parseShort(stream), Integer.parseInt(group));
        studentsDAO.add(student);
        return "redirect:/students";
    }

    @PostMapping("/students/find")
    public String studentsPostFind(@RequestParam String first_name, @RequestParam String lastName, @RequestParam String patronymic, @RequestParam String year, @RequestParam String stream, @RequestParam String group, Model model) {
        List<Students> students = studentsDAO.getAll();
        Students studentFind = null;
        for (Students student : students) {
            if (first_name.equals(student.getFirst_name()) && lastName.equals(student.getLast_name()) && patronymic.equals(student.getPatronymic()) && Short.parseShort(year) == student.getYear() && Short.parseShort(stream) == student.getStream() && Integer.parseInt(group) == student.getGroup_st()) {
                studentFind = student;
            }
        }
        if (Objects.isNull(studentFind)) {
            return "error2";
        }
        Optional<Students> student = Optional.ofNullable(studentFind);
        ArrayList<Students> res = new ArrayList<>();
        student.ifPresent(res::add);
        model.addAttribute("stud", res);
        return "studentsId";
    }

    @GetMapping("/students/{id}")
    public String studentsId(@PathVariable(value = "id") long id, Model model) {
        Optional<Students> student = Optional.ofNullable(studentsDAO.getById(id));
        if (student.isEmpty()) {
            return "redirect:/students";
        }
        ArrayList<Students> res = new ArrayList<>();
        student.ifPresent(res::add);
        model.addAttribute("stud", res);
        return "studentsId";
    }

    @GetMapping("/students/{id}/update")
    public String studentsUpdate(@PathVariable(value = "id") long id, Model model) {
        Optional<Students> student = Optional.ofNullable(studentsDAO.getById(id));
        if (student.isEmpty()) {
            return "redirect:/students";
        }
        ArrayList<Students> res = new ArrayList<>();
        student.ifPresent(res::add);
        model.addAttribute("stud", res);
        return "studentsUpdate";
    }

    @PostMapping("/students/{id}/update")
    public String studentsPostUpdate(@PathVariable(value = "id") long id, @RequestParam String first_name, @RequestParam String lastName, @RequestParam String patronymic, @RequestParam String year, @RequestParam String stream, @RequestParam String group, Model model) {
        Students student = studentsDAO.getById(id);
        student.setFirst_name(first_name);
        student.setLast_name(lastName);
        student.setPatronymic(patronymic);
        student.setYear(Short.parseShort(year));
        student.setStream(Short.parseShort(stream));
        student.setGroup_st(Integer.parseInt(group));
        studentsDAO.update(student);
        return "redirect:/students/{id}";
    }

    @PostMapping("/students/{id}/remove")
    public String studentsPostRemove(@PathVariable(value = "id") long id, Model model) {
        Students student = studentsDAO.getById(id);
        studentsDAO.delete(student);
        return "redirect:/students";
    }

    @PostMapping("/students/{id}/schedule")
    public String studentsPostSchedule(@PathVariable(value = "id") long id, @RequestParam String start, @RequestParam String end, Model model) {
        Iterable<Classes> classes = studentsDAO.get_schedule(studentsDAO.getById(id), Short.parseShort(start), Short.parseShort(end));
        model.addAttribute("classes", classes);
        return "schedule";
    }

    @PostMapping("/students/{id}/special")
    public String studentsPostSpecial(@PathVariable(value = "id") long id, @RequestParam String special, Model model) {
        List<Courses> courses = coursesDAO.getAll();
        Boolean mark = Boolean.TRUE;
        for (Courses course : courses) {
            if (course.getName().equals(special)) {
                mark = Boolean.FALSE;
            }
        }
        if (mark) {
            return "error1";
        }
        studentsDAO.add_special(special, studentsDAO.getById(id));
        return "redirect:/students";
    }
}
