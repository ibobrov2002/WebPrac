package ru.schedule.DAO;

import ru.schedule.models.Classes;
import ru.schedule.models.Courses;

import java.util.Collection;
import java.util.List;

public interface ClassesDAO extends CommonDAO<Classes, Long> {

    Classes getById(Long id);
    List<Classes> getAll();
    void delete(Classes clas);
    void saveCollection(Collection<Classes> entities);
}
