package ru.schedule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.schedule.DAO.CoursesDAO;
import ru.schedule.DAO.ProfessorsDAO;
import ru.schedule.DAO.StudentsDAO;
import ru.schedule.DAO.impl.CoursesDAOImpl;
import ru.schedule.DAO.impl.ProfessorsDAOImpl;
import ru.schedule.DAO.impl.StudentsDAOImpl;
import ru.schedule.models.Courses;
import ru.schedule.models.Professors;
import ru.schedule.models.Students;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private final StudentsDAO studentsDAO = new StudentsDAOImpl();

    @Autowired
    private final ProfessorsDAO professorsDAO = new ProfessorsDAOImpl();

    @Autowired
    private  final CoursesDAO coursesDAO = new CoursesDAOImpl();

    @RequestMapping(value = "/")
    public String home() {
        return "home";
    }

    @GetMapping("/students")
    public String students(Model model) {
        Iterable<Students> studs = studentsDAO.getAll();
        model.addAttribute("studs", studs);
        return "students";
    }

    @RequestMapping(value = "/professors")
    public String professors(Model model) {
        Iterable<Professors> professors = professorsDAO.getAll();
        model.addAttribute("profes", professors);
        return "professors";
    }

    @RequestMapping(value = "/classrooms")
    public String classrooms() {
        return "classrooms";
    }

    @RequestMapping(value = "/courses")
    public String courses(Model model) {
        Iterable<Courses> courses = coursesDAO.getAll();
        model.addAttribute("courses", courses);
        return "courses";
    }
}
