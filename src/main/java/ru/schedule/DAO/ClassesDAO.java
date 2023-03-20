package ru.schedule.DAO;

import ru.schedule.models.Classes;

import java.util.Collection;

public interface ClassesDAO extends CommonDAO<Classes, Long> {

    Classes getById(Long id);
    void delete(Classes clas);
    void saveCollection(Collection<Classes> entities);
}
