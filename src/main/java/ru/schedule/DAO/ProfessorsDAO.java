package ru.schedule.DAO;

import ru.schedule.models.Classes;
import ru.schedule.models.Professors;

import java.util.Collection;
import java.util.List;

public interface ProfessorsDAO extends CommonDAO<Professors, Long> {

    Professors getById(Long id);
    List<Professors> getAll();
    void delete(Professors professor);
    void update(Professors professor);
    List<Classes> get_schedule(Professors professor, Short start, Short end);
    void saveCollection(Collection<Professors> entities);
}
