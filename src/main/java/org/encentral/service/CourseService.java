package org.encentral.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encentral.dto.CourseDTO;
import org.encentral.entity.Course;
import org.encentral.entity.QCourse;
import org.encentral.entity.QStudent;
import org.encentral.entity.QTeacher;
import org.encentral.util.JPAWrapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseService {
    private static final Logger logger = LogManager.getLogger(CourseService.class);
    private final EntityManager entityManager;
    private JPAQueryFactory queryFactory;
    private QCourse qcourse;

    public CourseService(){
        this("school-pu");
    }

    public CourseService (String pu){
        entityManager = JPAWrapper.getEntityManager(pu);
        queryFactory = new JPAQueryFactory(entityManager);
        qcourse = QCourse.course;
    }

    public Course registerCourse(Course course){
        entityManager.getTransaction().begin();
        entityManager.persist(course);
        entityManager.getTransaction().commit();
        return course;
    }

    public void addAllCourse(List<Course> courses){
        for(Course course : courses){
            registerCourse(course);
        }
        String msg = String.format("%d courses added successfully", courses.size());
        logger.info(msg);
    }

    public List<CourseDTO> getAllCourses(){
        return toCourseDTO(queryFactory.selectFrom(qcourse).fetch());
    }

    public Course findByCourseName (String coursename){
        QCourse qcourse  = QCourse.course;
        return queryFactory
                .selectFrom(qcourse).where(qcourse.courseName.eq(coursename)).fetchOne();
    }

    public void deletePost(Course course){
        entityManager.getTransaction().begin();
        entityManager.remove(course);
        entityManager.getTransaction().commit();
    }

    public static CourseDTO toCourseDTO(Course course) {
        return new CourseDTO(course.getCourseName(), course.getCreatedAt());
    }

    public static List<CourseDTO> toCourseDTO(List<Course> courseList) {
        List<CourseDTO> courseDTOList = new ArrayList<>();

        for (Course course : courseList) {
            courseDTOList.add(toCourseDTO(course));
        }
        return courseDTOList;
    }

    public static Set<CourseDTO> toCourseDTO(Set<Course> courseList) {
        Set<CourseDTO> courseDTOList = new HashSet<>();

        for (Course course : courseList) {
            courseDTOList.add(toCourseDTO(course));
        }
        return courseDTOList;
    }

    public void closeCourseService(){
        entityManager.close();
        JPAWrapper.closeEntityManagerFactory();
    }
}
