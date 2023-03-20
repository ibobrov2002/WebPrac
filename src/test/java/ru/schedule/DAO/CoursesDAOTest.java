package ru.schedule.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.schedule.models.Courses;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class CoursesDAOTest {

    @Autowired
    private CoursesDAO coursesDAO;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void testSimpleManipulations() {
        List<Courses> coursesListAll = coursesDAO.getAll();
        assertEquals(4, coursesListAll.size());

        Courses courseId3 = coursesDAO.getById(3L);
        assertEquals(3, courseId3.getId());
        assertEquals("linal", courseId3.getName());

        Courses courseNotExist = coursesDAO.getById(100L);
        assertNull(courseNotExist);
    }
    @Test
    void testUpdate() {
        Short intensity = 2, year = 2;

        Courses updateCourses = coursesDAO.getById(2L);
        updateCourses.setIntensity(intensity);
        updateCourses.setYear(year);
        coursesDAO.update(updateCourses);

        Courses prak = coursesDAO.getById(2L);
        assertEquals(intensity, prak.getIntensity());
        assertEquals(year, prak.getYear());
    }
    @Test
    void testDelete() {
        Courses deleteCourse = coursesDAO.getById(1L);
        coursesDAO.delete(deleteCourse);

        Courses matan = coursesDAO.getById(1L);
        assertNull(matan);
    }
    @BeforeEach
    void beforeEach() {
        List<Courses> courseList = new ArrayList<>();
        courseList.add(new Courses(123L, "matan", "stream", (short) 3, (short) 1));
        courseList.add(new Courses(null, "prak", "group", (short) 1, (short) 3));
        courseList.add(new Courses(1L, "linal", "special", (short) 2, (short) 2));
        courseList.add(new Courses(123L, "oki", "stream", (short) 3, null));
        coursesDAO.saveCollection(courseList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE courses RESTART IDENTITY CASCADE;").executeUpdate();
            session.createSQLQuery("ALTER SEQUENCE courses_id_seq RESTART WITH 1;").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
