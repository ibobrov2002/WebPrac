package ru.schedule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.schedule.DAO.ClassroomsDAO;
import ru.schedule.DAO.impl.ClassroomsDAOImpl;
import ru.schedule.models.Classes;
import ru.schedule.models.Classrooms;

@Controller
public class ClassroomsController {

    @Autowired
    private final ClassroomsDAO classroomsDAO = new ClassroomsDAOImpl();

    @PostMapping("/classrooms/free")
    public String classroomsPostFree(@RequestParam String startF, @RequestParam String endF, Model model) {
        Iterable<Classrooms> classrooms = classroomsDAO.get_free(Short.parseShort(startF), Short.parseShort(endF));
        model.addAttribute("classrooms", classrooms);
        return "classroomsFree";
    }

    @PostMapping("/classrooms/schedule")
    public String classroomsPostSchedule(@RequestParam String id, @RequestParam String start, @RequestParam String end, Model model) {
        Iterable<Classes> classes = classroomsDAO.get_schedule(Long.parseLong(id), Short.parseShort(start), Short.parseShort(end));
        model.addAttribute("classes", classes);
        return "schedule";
    }
}
