package ru.schedule.DAO;

import ru.schedule.models.Courses;

import java.util.Collection;
import java.util.List;

public interface CoursesDAO extends CommonDAO<Courses, Long> {

    Courses getById(Long id);
    List<Courses> getAll();
    void delete(Courses course);
    void update(Courses course);
    void saveCollection(Collection<Courses> entities);
}
