package ru.schedule.DAO.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.schedule.DAO.ClassesDAO;
import ru.schedule.models.Classes;

import java.util.Collection;

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
