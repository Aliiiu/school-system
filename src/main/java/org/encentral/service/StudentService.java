package org.encentral.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encentral.dto.CourseDTO;
import org.encentral.dto.StudentDTO;
import org.encentral.entity.*;
import org.encentral.util.JPAWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Iterables;

public class StudentService {
    private final EntityManager entityManager;
    private JPAQueryFactory queryFactory;
    private QTeacher qteacher;
    private QStudent qstudent;
    private QCourse qcourse;

    private final Logger logger = LogManager.getLogger(StudentService.class);

    public StudentService(){
        this("school-pu");
    }
    public StudentService (String pu){
        entityManager  = JPAWrapper.getEntityManager(pu);
        queryFactory = new JPAQueryFactory(entityManager);
        qteacher = QTeacher.teacher;
        qstudent = QStudent.student;
        qcourse = QCourse.course;
    }

    public Student registerStudent(String name, int year){
        Student student = new Student(name, year);
        Student updatedStudent = assignRandomTeacher(student);
        entityManager.getTransaction().begin();
        entityManager.persist(updatedStudent);
        entityManager.getTransaction().commit();
        return updatedStudent;
    }

    public void addAllStudentCourses(Student student , Set<Course> courses){
        for(Course course : courses){
            student.addCourse(course);
        }
    }

    public Student registerStudentCourse(Student student, Set<Course> courses){
        Student studentToUpdate = findStudentByName(student.getName());
        entityManager.getTransaction().begin();
            studentToUpdate.setCourses(courses);
//        addAllStudentCourses(studentToUpdate, courses);
        entityManager.getTransaction().commit();
        return studentToUpdate;
    }

    public Student assignRandomTeacher(Student student){
        List<Teacher> teacherList = queryFactory.selectFrom(qteacher).fetch();

        if (!teacherList.isEmpty()){
            Teacher randomTeacher = Iterables.get(teacherList, new Random().nextInt(teacherList.size()));
            student.setPersonalGuide(randomTeacher);
            randomTeacher.getStudents().add(student);
        }
        return student;
    }

    public List<StudentDTO> getAllStudents(){
        return StudentService.toStudentDTO(queryFactory.selectFrom(qstudent).fetch());
    }

    public List<CourseDTO> getCourseList(Long id){
        List<Course> courseList = queryFactory.selectFrom(qcourse).join(qcourse.students, qstudent).where(qstudent.studentId.eq(id)).fetch();

        if(courseList.size() > 0){
            return CourseService.toCourseDTO(courseList);
        }
        return null;
    }

    public String getStudentGuide(String name){
        Student student = queryFactory.selectFrom(qstudent).where(qstudent.name.eq(name)).fetchOne();
        if(student != null){
            return student.getPersonalGuide().getTeacherName();
        }
        return null;
    }

    public Student findStudentByName(String name){
        Student student = queryFactory.selectFrom(qstudent).where(qstudent.name.eq(name)).fetchOne();
        if (student == null){
            logger.warn(String.format("name '%s' not found", name));
            return null;
        }
        return student;
    }

    public void deletePost(Student student){
        entityManager.getTransaction().begin();
        entityManager.remove(student);
        entityManager.getTransaction().commit();
    }

    public static StudentDTO toSTudentDTO(Student student) {
        return new StudentDTO(student.getName(), TeacherService.toTeacherDTO(student.getPersonalGuide()), student.getCreatedAt());
    }

    public static List<StudentDTO> toStudentDTO(List<Student> studentList) {
        List<StudentDTO> studentDTOList = new ArrayList<>();

        for (Student student : studentList) {
            studentDTOList.add(toSTudentDTO(student));
        }
        return studentDTOList;
    }

    public void closeStudentService(){
        entityManager.close();
        JPAWrapper.closeEntityManagerFactory();
    }
}
