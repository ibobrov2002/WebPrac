package ru.schedule.DAO;

import ru.schedule.models.Classes;
import ru.schedule.models.Students;

import java.util.Collection;
import java.util.List;

public interface StudentsDAO extends CommonDAO<Students, Long> {

    Students getById(Long id);
    List<Students> getAll();
    void delete(Students student);
    void update(Students student);
    void add_special(String title, Students student);
    List<Classes> get_schedule(Students student, Short start, Short end);
    void saveCollection(Collection<Students> entities);
}

