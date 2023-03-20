package ru.schedule.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.schedule.DAO.CoursesDAO;
import ru.schedule.models.Courses;

import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;
import java.util.List;

@Repository
public class CoursesDAOImpl extends CommonDAOImpl<Courses, Long> implements CoursesDAO {

    public CoursesDAOImpl() {
        super(Courses.class);
    }

    @Override
    public Courses getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(persistentClass, id);
        }
    }
    @Override
    public List<Courses> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Courses> criteriaQuery = session.getCriteriaBuilder().createQuery(persistentClass);
            criteriaQuery.from(persistentClass);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public void update(Courses course) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(course);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Courses course) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(course);
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveCollection(Collection<Courses> entities) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Courses entity : entities) {
                this.add(entity);
            }
            session.getTransaction().commit();
        }
    }
}
