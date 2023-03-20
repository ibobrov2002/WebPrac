package ru.schedule.DAO;

import ru.schedule.models.Classes;
import ru.schedule.models.Classrooms;

import java.util.Collection;
import java.util.List;

public interface ClassroomsDAO extends CommonDAO<Classrooms, Long> {
    List<Classrooms> get_free(Short start, Short end);
    List<Classes> get_schedule(Long number, Short start, Short end);
    void saveCollection(Collection<Classrooms> entities);
}
