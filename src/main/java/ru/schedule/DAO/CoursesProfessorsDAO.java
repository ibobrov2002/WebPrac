package ru.schedule.DAO;

import ru.schedule.models.CoursesProfessors;

import java.util.List;

public interface CoursesProfessorsDAO extends CommonDAO<CoursesProfessors, Long> {
    List<CoursesProfessors> getAll();
}
