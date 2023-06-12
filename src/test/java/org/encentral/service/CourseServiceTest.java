package org.encentral.service;

import org.encentral.dto.CourseDTO;
import org.encentral.entity.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class CourseServiceTest {

    @Container
    private static final PostgreSQLContainer<?> container =  new PostgreSQLContainer<>("postgres:alpine");;
    private static CourseService courseService;

    @BeforeEach
    public void startContainer() {
        // Create a PostgreSQL container
        container.start();

        // Set the database URL, username, and password
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());

        courseService = new CourseService("school-test-pu");
    }

    @AfterEach
    public void tearDown() {
        // Stop the PostgreSQL container
        container.stop();
        courseService.closeCourseService();
    }

    @Test
    void testRegisterCourse() {
        Course course = courseService.registerCourse(new Course("MATHS"));

        assertTrue(course.getCourseId() > 0);
    }

    @Test
    void testGetAllCourses() {
        List<Course> courses = new ArrayList<>(List.of(
                new Course("MATHS"),
                new Course("ENGLISH"),
                new Course("FINE ART"),
                new Course("FURTHER MATHS"),
                new Course("AGRIC"),
                new Course("BIOLOGY"),
                new Course("CHEMISTRY"),
                new Course("PHYSICS")
        ));
        courseService.addAllCourse(courses);
        List<CourseDTO> courseDTOList = courseService.getAllCourses();

        assertEquals(courseDTOList.size(), 8);
    }

    @Test
    void testFindByCourseName() {
        courseService.registerCourse(new Course("MATHS"));
        Course course = courseService.findByCourseName("MATHS");
        assertEquals(course.getCourseName(), "MATHS");
    }
}