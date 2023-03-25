package ru.schedule.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.schedule.DAO.CoursesStudentsDAO;
import ru.schedule.DAO.StudentsDAO;
import ru.schedule.models.*;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Repository
public class StudentsDAOImpl extends CommonDAOImpl<Students, Long> implements StudentsDAO {

    public StudentsDAOImpl() {
        super(Students.class);
    }
    @Autowired
    private CoursesStudentsDAO coursesStudentsDAO = new CoursesStudentsDAOImpl();

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
            if (course.get(0).getCoverage().equals("special")) {
                coursesStudentsDAO.add(new CoursesStudents(1L,  course.get(0).getId(), student.getId()));
            }
        }
    }

    private String likeExpr(String param) {
        return "%" + param + "%";
    }

    @Override
    public List<Classes> get_schedule(Students student, Short start, Short end) {
        try (Session session = sessionFactory.openSession()) {
            List<CoursesStudents> course = new ArrayList<>();
            for(CoursesStudents coursesStudents : coursesStudentsDAO.getAll()) {
                if (Objects.equals(coursesStudents.getStudent_id(), student.getId())) {
                    course.add(coursesStudents);
                }
            }
            List<Classes> classes = new ArrayList<>();
            for( CoursesStudents i : course) {
                Query<Classes> query_new = session.createQuery("FROM Classes WHERE course_id = :gotCourse and day_of_week >= :gotStart and day_of_week <= :gotEnd", Classes.class)
                        .setParameter("gotCourse", i.getCourse_id()).setParameter("gotStart", start).setParameter("gotEnd", end);
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
