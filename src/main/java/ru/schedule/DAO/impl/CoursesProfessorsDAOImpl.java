package ru.schedule.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.schedule.DAO.CoursesProfessorsDAO;
import ru.schedule.models.CoursesProfessors;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class CoursesProfessorsDAOImpl extends CommonDAOImpl<CoursesProfessors, Long> implements CoursesProfessorsDAO {

    public CoursesProfessorsDAOImpl() {
        super(CoursesProfessors.class);
    }
    @Override
    public List<CoursesProfessors> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<CoursesProfessors> criteriaQuery = session.getCriteriaBuilder().createQuery(persistentClass);
            criteriaQuery.from(persistentClass);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
