package ru.schedule.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.schedule.DAO.ClassroomsDAO;
import ru.schedule.models.Classes;
import ru.schedule.models.Classrooms;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class ClassroomsDAOImpl extends CommonDAOImpl<Classrooms, Long> implements ClassroomsDAO {

    public ClassroomsDAOImpl() {
        super(Classrooms.class);
    }

    @Override
    public List<Classrooms> get_free(Short start, Short end) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Classrooms> criteriaQuery = session.getCriteriaBuilder().createQuery(persistentClass);
            criteriaQuery.from(persistentClass);
            List<Classrooms> all_classrooms = session.createQuery(criteriaQuery).getResultList();
            List<Classrooms> free = new ArrayList<>();
            for( Classrooms i : all_classrooms) {

                Query<Classes> query = session.createQuery("FROM Classes WHERE classroom_num = :gotClassroom and day_of_week >= :gotStart and day_of_week <= :gotEnd", Classes.class)
                        .setParameter("gotClassroom", i.getId()).setParameter("gotStart", start).setParameter("gotEnd", end);
                List<Classes> clas = query.getResultList();
                if (clas.size() != (start - end + 1) * 6) {
                    free.add(i);
                }
            }
            return free.size() == 0 ? null : free;
        }
    }

    @Override
    public List<Classes> get_schedule(Long number, Short start, Short end) {
        try (Session session = sessionFactory.openSession()) {
            Query<Classes> query = session.createQuery("FROM Classes WHERE classroom_num = (SELECT id FROM Classrooms WHERE number = :gotClas ) and day_of_week >= :gotStart and day_of_week <= :gotEnd", Classes.class)
                    .setParameter("gotClas", number).setParameter("gotStart", start).setParameter("gotEnd", end);
            List<Classes> clas = query.getResultList();
            return clas.size() == 0 ? null : clas;
        }
    }

    @Override
    public void saveCollection(Collection<Classrooms> entities) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Classrooms entity : entities) {
                this.add(entity);
            }
            session.getTransaction().commit();
        }
    }
}
