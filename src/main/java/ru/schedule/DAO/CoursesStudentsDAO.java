package ru.schedule.DAO;

import ru.schedule.models.CoursesStudents;

import java.util.List;

public interface CoursesStudentsDAO extends CommonDAO<CoursesStudents, Long> {
    List<CoursesStudents> getAll();
}
