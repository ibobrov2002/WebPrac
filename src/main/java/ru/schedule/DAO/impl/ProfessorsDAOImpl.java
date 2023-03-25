package ru.schedule.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.schedule.DAO.CoursesProfessorsDAO;
import ru.schedule.DAO.ProfessorsDAO;
import ru.schedule.models.Classes;
import ru.schedule.models.CoursesProfessors;
import ru.schedule.models.Professors;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Repository
public class ProfessorsDAOImpl extends CommonDAOImpl<Professors, Long> implements ProfessorsDAO {

    public ProfessorsDAOImpl() {
        super(Professors.class);
    }

    @Autowired
    private CoursesProfessorsDAO coursesProfessorsDAO = new CoursesProfessorsDAOImpl();
    @Override
    public Professors getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(persistentClass, id);
        }
    }
    @Override
    public List<Professors> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Professors> criteriaQuery = session.getCriteriaBuilder().createQuery(persistentClass);
            criteriaQuery.from(persistentClass);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public void update(Professors professor) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(professor);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Professors professor) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(professor);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Classes> get_schedule(Professors professor, Short start, Short end) {
        try (Session session = sessionFactory.openSession()) {
            List<CoursesProfessors> course = new ArrayList<>();
            for(CoursesProfessors coursesProfessor : coursesProfessorsDAO.getAll()) {
                if (Objects.equals(coursesProfessor.getProfessor_id(), professor.getId())) {
                    course.add(coursesProfessor);
                }
            }
            List<Classes> classes = new ArrayList<>();
            for( CoursesProfessors i : course) {
                Query<Classes> query_new = session.createQuery("FROM Classes WHERE course_id = :gotCourse and day_of_week >= :gotStart and day_of_week <= :gotEnd", Classes.class)
                        .setParameter("gotCourse", i.getCourse_id()).setParameter("gotStart", start).setParameter("gotEnd", end);
                List<Classes> clas = query_new.getResultList();
                classes.addAll(clas);
            }
            return classes.size() == 0 ? null : classes;
        }
    }

    @Override
    public void saveCollection(Collection<Professors> entities) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Professors entity : entities) {
                this.add(entity);
            }
            session.getTransaction().commit();
        }
    }
}
