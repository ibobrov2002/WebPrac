package ru.schedule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.schedule.DAO.ClassroomsDAO;
import ru.schedule.DAO.ProfessorsDAO;
import ru.schedule.DAO.impl.ClassroomsDAOImpl;
import ru.schedule.DAO.impl.ProfessorsDAOImpl;
import ru.schedule.models.Classes;
import ru.schedule.models.Professors;
import ru.schedule.models.Students;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ProfessorsController {

    @Autowired
    private final ProfessorsDAO professorsDAO = new ProfessorsDAOImpl();

    @GetMapping("/professors/add")
    public String professorsAdd(Model model) {
        return "professorsAdd";
    }

    @GetMapping("/professors/find")
    public String studentsFind(Model model) {
        return "professorsFind";
    }

    @PostMapping("/professors/add")
    public String professorsPostAdd(@RequestParam String first_name, @RequestParam String lastName, @RequestParam String patronymic, Model model) {
        Professors professor = new Professors(null, first_name, lastName, patronymic);
        professorsDAO.add(professor);
        return "redirect:/professors";
    }

    @PostMapping("/professors/find")
    public String professorsPostFind(@RequestParam String first_name, @RequestParam String lastName, @RequestParam String patronymic, Model model) {
        List<Professors> professors = professorsDAO.getAll();
        Professors professorFind = null;
        for (Professors professor : professors) {
            if (first_name.equals(professor.getFirst_name()) && lastName.equals(professor.getLast_name()) && patronymic.equals(professor.getPatronymic())) {
                professorFind = professor;
            }
        }
        if (Objects.isNull(professorFind)) {
            return "error3";
        }
        Optional<Professors> professor = Optional.ofNullable(professorFind);
        ArrayList<Professors> res = new ArrayList<>();
        professor.ifPresent(res::add);
        model.addAttribute("prof", res);
        return "professorsId";
    }

    @GetMapping("/professors/{id}")
    public String professorsId(@PathVariable(value = "id") long id, Model model) {
        Optional<Professors> professor = Optional.ofNullable(professorsDAO.getById(id));
        if (professor.isEmpty()) {
            return "redirect:/professors";
        }
        ArrayList<Professors> res = new ArrayList<>();
        professor.ifPresent(res::add);
        model.addAttribute("prof", res);
        return "professorsId";
    }

    @GetMapping("/professors/{id}/update")
    public String professorsUpdate(@PathVariable(value = "id") long id, Model model) {
        Optional<Professors> professor = Optional.ofNullable(professorsDAO.getById(id));
        if (professor.isEmpty()) {
            return "redirect:/professors";
        }
        ArrayList<Professors> res = new ArrayList<>();
        professor.ifPresent(res::add);
        model.addAttribute("prof", res);
        return "professorsUpdate";
    }

    @PostMapping("/professors/{id}/update")
    public String professorsPostUpdate(@PathVariable(value = "id") long id, @RequestParam String first_name, @RequestParam String lastName, @RequestParam String patronymic, Model model) {
        Professors professor = professorsDAO.getById(id);
        professor.setFirst_name(first_name);
        professor.setLast_name(lastName);
        professor.setPatronymic(patronymic);
        professorsDAO.update(professor);
        return "redirect:/professors/{id}";
    }

    @PostMapping("/professors/{id}/remove")
    public String professorsPostRemove(@PathVariable(value = "id") long id, Model model) {
        Professors professor = professorsDAO.getById(id);
        professorsDAO.delete(professor);
        return "redirect:/professors";
    }

    @PostMapping("/professors/{id}/schedule")
    public String professorsSchedule(@PathVariable(value = "id") long id, @RequestParam String start, @RequestParam String end, Model model) {
        Iterable<Classes> classes = professorsDAO.get_schedule(professorsDAO.getById(id), Short.parseShort(start), Short.parseShort(end));
        model.addAttribute("classes", classes);
        return "schedule";
    }
}
