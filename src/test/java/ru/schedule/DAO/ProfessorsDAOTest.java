package ru.schedule.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.schedule.models.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class ProfessorsDAOTest {

    @Autowired
    private ProfessorsDAO professorsDAO;
    @Autowired
    private CoursesDAO coursesDAO;
    @Autowired
    private CoursesProfessorsDAO coursesProfessorsDAO;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private ClassesDAO classesDAO;
    @Autowired
    private ClassroomsDAO classroomsDAO;

    @Test
    void testSimpleManipulations() {
        List<Professors> professorsListAll = professorsDAO.getAll();
        assertEquals(4, professorsListAll.size());

        Professors professorId3 = professorsDAO.getById(3L);
        assertEquals(3, professorId3.getId());
        assertEquals("Boris", professorId3.getLast_name());

        Professors professorNotExist = professorsDAO.getById(100L);
        assertNull(professorNotExist);
    }
    @Test
    void testUpdate() {
        String patronymic = "Dmitrievich";

        Professors updateProfessor = professorsDAO.getById(2L);
        updateProfessor.setPatronymic(patronymic);
        professorsDAO.update(updateProfessor);

        Professors petr = professorsDAO.getById(2L);
        assertEquals(patronymic, petr.getPatronymic());
    }
    @Test
    void testDelete() {
        Professors deleteProfessor = professorsDAO.getById(1L);
        professorsDAO.delete(deleteProfessor);

        Professors ivan = professorsDAO.getById(1L);
        assertNull(ivan);
    }

    @Test
    void testSchedule() {
        List<Classes> schedule3 = professorsDAO.get_schedule( new Professors(1L, "Ivanov", "Ivan", "Ivanovich"), (short) 1, (short) 5);
        assertEquals(2, schedule3.size());
        schedule3 = professorsDAO.get_schedule( new Professors(5L, "Ivanov", "Ivan", "Ivanovich"), (short) 1, (short) 5);
        assertNull(schedule3);
    }
    @BeforeEach
    void beforeEach() {
        List<Courses> courseList = new ArrayList<>();
        courseList.add(new Courses(123L, "matan", "stream", (short) 3, (short) 1));
        courseList.add(new Courses(null, "prak", "group", (short) 1, (short) 3));
        courseList.add(new Courses(1L, "linal", "special", (short) 2, (short) 2));
        courseList.add(new Courses(123L, "oki", "stream", (short) 3, null));
        coursesDAO.saveCollection(courseList);

        List<Professors> professorsList = new ArrayList<>();
        professorsList.add(new Professors(123L, "Ivanov", "Ivan", "Ivanovich"));
        professorsList.add(new Professors(null, "Petrov", "Petr", "Petrovich"));
        professorsList.add(new Professors(1L, "Borisov", "Boris", "Borisovich"));
        professorsList.add(new Professors(123L, "Kirillov", "Kirill", null));
        professorsDAO.saveCollection(professorsList);

        coursesProfessorsDAO.add(new CoursesProfessors(1L, 1L, 1L));
        coursesProfessorsDAO.add(new CoursesProfessors(1L, 4L, 1L));

        List<Classrooms> classroomList = new ArrayList<>();
        classroomList.add(new Classrooms(1L, 106L, 150));
        classroomList.add(new Classrooms(2L, 666L, 30));
        classroomList.add(new Classrooms(3L, 527L, 50));
        classroomsDAO.saveCollection(classroomList);

        List<Classes> classesList = new ArrayList<>();
        classesList.add(new Classes(123L, 1L, 1L, "16:20:00", (short) 1));
        classesList.add(new Classes(null, 2L, 2L, "08:45:00", (short) 2));
        classesList.add(new Classes(1L, 3L, 3L, "14:35:00", (short) 3));
        classesList.add(new Classes(123L, 1L, 3L, "10:30:00", (short) 4));
        classesDAO.saveCollection(classesList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE courses RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE professors RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE courses_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE professors_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
