package ru.schedule.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.schedule.DAO.StudentsDAO;
import ru.schedule.models.Classes;
import ru.schedule.models.Courses;
import ru.schedule.models.CoursesStudents;
import ru.schedule.models.Students;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class StudentsDAOImpl extends CommonDAOImpl<Students, Long> implements StudentsDAO {

    public StudentsDAOImpl() {
        super(Students.class);
    }

    @Override
    public Students getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(persistentClass, id);
        }
    }
    @Override
    public List<Students> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Students> criteriaQuery = session.getCriteriaBuilder().createQuery(persistentClass);
            criteriaQuery.from(persistentClass);
            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    @Override
    public void update(Students student) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(student);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Students student) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(student);
            session.getTransaction().commit();
        }
    }

    @Override
    public void add_special(String title, Students student) {
        try (Session session = sessionFactory.openSession()) {
            Query<Courses> query = session.createQuery("FROM Courses WHERE name LIKE :gotName", Courses.class)
                    .setParameter("gotName", likeExpr(title));
            List<Courses> course = query.getResultList();
            session.beginTransaction();
            session.saveOrUpdate(new CoursesStudents(1L,  course.get(0), student));
            session.getTransaction().commit();
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }

    @Override
    public List<Classes> get_schedule(Students student, Short start, Short end) {
        try (Session session = sessionFactory.openSession()) {
            Query<CoursesStudents> query = session.createQuery("FROM CoursesStudents WHERE student_id = :gotSt", CoursesStudents.class)
                    .setParameter("gotSt", student);
            List<CoursesStudents> course = query.getResultList();
            List<Classes> classes = new ArrayList<>();
            for( CoursesStudents i : course) {
                Query<Classes> query_new = session.createQuery("FROM Classes WHERE course_id = :gotCourse and day_of_week >= :gotStart and day_of_week <= :gotEnd", Classes.class)
                        .setParameter("gotCourse", i.getId()).setParameter("gotStart", start).setParameter("gotEnd", end);
                List<Classes> clas = query_new.getResultList();
                classes.addAll(clas);
            }
            return classes.size() == 0 ? null : classes;
        }
    }

    @Override
    public void saveCollection(Collection<Students> entities) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Students entity : entities) {
                this.add(entity);
            }
            session.getTransaction().commit();
        }
    }
}
