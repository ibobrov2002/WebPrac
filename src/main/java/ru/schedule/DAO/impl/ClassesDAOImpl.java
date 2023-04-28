package ru.schedule.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.schedule.DAO.ClassesDAO;
import ru.schedule.models.Classes;
import ru.schedule.models.Courses;

import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;
import java.util.List;

@Repository
public class ClassesDAOImpl extends CommonDAOImpl<Classes, Long> implements ClassesDAO {

    public ClassesDAOImpl() {
        super(Classes.class);
    }

    @Override
    public Classes getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(persistentClass, id);
        }
    }

    @Override
    public List<Classes> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Classes> criteriaQuery = session.getCriteriaBuilder().createQuery(persistentClass);
            criteriaQuery.from(persistentClass);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public void delete(Classes clas) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(clas);
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveCollection( Collection<Classes> entities) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Classes entity : entities) {
                this.add(entity);
            }
            session.getTransaction().commit();
        }
    }
}
