package ru.schedule.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.schedule.models.Classes;
import ru.schedule.models.Classrooms;
import ru.schedule.models.Courses;
import ru.schedule.models.Students;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class StudentDAOTest {

    @Autowired
    private StudentsDAO studentsDAO;
    @Autowired
    private CoursesDAO coursesDAO;
    @Autowired
    private CoursesStudentsDAO coursesStudentsDAO;
    @Autowired
    private ClassesDAO classesDAO;
    @Autowired
    private ClassroomsDAO classroomsDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {
        List<Students> studentsListAll = studentsDAO.getAll();
        assertEquals(4, studentsListAll.size());

        Students studentId3 = studentsDAO.getById(3L);
        assertEquals(3, studentId3.getId());
        assertEquals("Boris", studentId3.getLast_name());

        Courses courseNotExist = coursesDAO.getById(100L);
        assertNull(courseNotExist);
    }
    @Test
    void testUpdate() {
        Short year = 3, stream = 3;

        Students updateStudents = studentsDAO.getById(2L);
        updateStudents.setYear(year);
        updateStudents.setStream(stream);
        studentsDAO.update(updateStudents);

        Students petr = studentsDAO.getById(2L);
        assertEquals(year, petr.getYear());
        assertEquals(stream, petr.getStream());
    }
    @Test
    void testDelete() {
        Students deleteStudent = studentsDAO.getById(1L);
        studentsDAO.delete(deleteStudent);

        Students ivan = studentsDAO.getById(1L);
        assertNull(ivan);
    }
    @Test
    void testSpecial() {
        Integer before = coursesStudentsDAO.getAll().size();
        studentsDAO.add_special("linal", new Students(1L, "Ivanov", "Ivan", "Ivanovich", (short) 1, (short) 1, 114));
        assertEquals(coursesStudentsDAO.getAll().size() - before, 1);
    }
    @Test
    void testSchedule() {
        List<Classes> schedule3 = studentsDAO.get_schedule( new Students(1L, "Ivanov", "Ivan", "Ivanovich", (short) 1, (short) 1, 114), (short) 1, (short) 5);
        assertEquals(2, schedule3.size());
        schedule3 = studentsDAO.get_schedule( new Students(5L, "Ivanov", "Ivan", "Ivanovich", (short) 1, (short) 1, 114), (short) 1, (short) 5);
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

        List<Students> studentsList = new ArrayList<>();
        studentsList.add(new Students(123L, "Ivanov", "Ivan", "Ivanovich", (short) 1, (short) 1, 114));
        studentsList.add(new Students(null, "Petrov", "Petr", "Petrovich", (short) 2, (short) 2, 214));
        studentsList.add(new Students(1L, "Borisov", "Boris", "Borisovich", (short) 3, (short) 3, 314));
        studentsList.add(new Students(123L, "Kirillov", "Kirill", null, (short) 3, (short)3, 328));
        studentsDAO.saveCollection(studentsList);

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
            session.createSQLQuery("TRUNCATE students RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("TRUNCATE coursesstudents RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE courses_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE students_id_seq RESTART WITH 1;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE coursesstudents_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
