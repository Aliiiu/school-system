package org.encentral.service;

import org.encentral.dto.StudentDTO;
import org.encentral.entity.Course;
import org.encentral.entity.Student;
import org.encentral.entity.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class StudentServiceTest {
    @Container
    private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:alpine");;
    private static StudentService studentService;
    private static CourseService courseService;
    private static TeacherService teacherService;

    @BeforeEach
    public void startContainer() {
        // Create a PostgreSQL container
        container.start();

        // Set the database URL, username, and password
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());

        studentService = new StudentService("school-test-pu");
        teacherService = new TeacherService("school-test-pu");
        courseService = new CourseService("school-test-pu");

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

        Teacher teacher1 = new Teacher("John");
        Teacher teacher2 = new Teacher("Emma");
        Teacher teacher3 = new Teacher("David");

        ArrayList<Teacher> teacherList = new ArrayList<>(List.of(teacher1, teacher2, teacher3));
        teacherService.addAllTeachers(teacherList);
    }

    @AfterEach
    public void tearDown() {
        // Stop the PostgreSQL container
        container.stop();
        studentService.closeStudentService();
    }

    @Test
    void testRegisterStudent() {
        Student student = studentService.registerStudent("aliu", 1);
        assertNotNull(student);
    }

    @Test
    void testAssignedTeacher(){
        Student student = studentService.registerStudent("aliu", 1);
        assertNotNull(student.getPersonalGuide());
    }

    @Test
    void testGetAllStudents() {
        studentService.registerStudent("aliu", 1);
        List<StudentDTO> studentList = studentService.getAllStudents();
        assertEquals(1, studentList.size());
    }

    @Test
    void testFindStudentByName() {
        studentService.registerStudent("aliu", 1);
        Student student = studentService.findStudentByName("aliu");
        assertEquals("aliu", student.getName());
    }
    @Test
    void testRegisteredCourses(){
        Student student = studentService.registerStudent("aliu", 1);
        Set<Course> courses = new HashSet<>();
        courses.add(courseService.findByCourseName("MATHS"));
        courses.add(courseService.findByCourseName("ENGLISH"));
        courses.add(courseService.findByCourseName("AGRIC"));
        courses.add(courseService.findByCourseName("BIOLOGY"));
        courses.add(courseService.findByCourseName("CHEMISTRY"));

        student = studentService.registerStudentCourse(student, courses);
        assertEquals(5, student.getCourses().size());
    }
}