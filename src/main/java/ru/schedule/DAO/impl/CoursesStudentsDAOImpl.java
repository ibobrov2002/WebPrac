package ru.schedule.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.schedule.DAO.CoursesStudentsDAO;
import ru.schedule.models.CoursesStudents;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class CoursesStudentsDAOImpl extends CommonDAOImpl<CoursesStudents, Long> implements CoursesStudentsDAO {
    public CoursesStudentsDAOImpl() {
        super(CoursesStudents.class);
    }
    @Override
    public List<CoursesStudents> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<CoursesStudents> criteriaQuery = session.getCriteriaBuilder().createQuery(persistentClass);
            criteriaQuery.from(persistentClass);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
