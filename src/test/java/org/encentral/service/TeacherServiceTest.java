package org.encentral.service;

import org.encentral.entity.Teacher;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {

    private static PostgreSQLContainer<?> container =  new PostgreSQLContainer<>("postgres:alpine");;
    private static TeacherService teacherService;

    @BeforeEach
    public void startContainer() {
        // Create a PostgreSQL container
        container.start();

        // Set the database URL, username, and password
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());

        teacherService = new TeacherService("school-test-pu");
    }

    @AfterEach
    public void tearDown() {
        // Stop the PostgreSQL container
        container.stop();
    }

    @Test
    void registerTeacher() {
        Teacher teacher = new Teacher("aliu");
        teacher = teacherService.registerTeacher(teacher);

        assertNotNull(teacher.getTeacherId());
    }

    @Test
    void getAllTeachers() {
        Teacher teacher1 = new Teacher("John");
        Teacher teacher2 = new Teacher("Emma");
        Teacher teacher3 = new Teacher("David");

        ArrayList<Teacher> teacherList = new ArrayList<>(List.of(teacher1, teacher2, teacher3));
        teacherService.addAllTeachers(teacherList);
        List<Teacher> res = teacherService.getAllTeachers();

        assertEquals(3, res.size());
    }
}