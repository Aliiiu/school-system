package org.encentral.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encentral.dto.TeacherDTO;
import org.encentral.entity.QTeacher;
import org.encentral.entity.Teacher;
import org.encentral.util.JPAWrapper;

import java.util.ArrayList;
import java.util.List;

public class TeacherService {
    private static final Logger logger = LogManager.getLogger(TeacherService.class);
    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;
    private final QTeacher qteacher;

    public TeacherService(){
        this("school-pu");
    }

    public TeacherService (String pu){
        entityManager = JPAWrapper.getEntityManager(pu);
        queryFactory = new JPAQueryFactory(entityManager);
        qteacher = QTeacher.teacher;
    }

    public Teacher registerTeacher(Teacher teacher){
        entityManager.getTransaction().begin();
        entityManager.persist(teacher);
        entityManager.getTransaction().commit();
        return teacher;
    }

    public void addAllTeachers(List<Teacher> teacherList){
        for(Teacher teacher : teacherList){
            registerTeacher(teacher);
        }
        String msg = String.format("%d teachers added successfully", teacherList.size());
        logger.info(msg);
    }

    public List<Teacher> getAllTeachers(){
        return queryFactory.selectFrom(qteacher).fetch();
    }

    public void deletePost(Teacher teacher){
        entityManager.getTransaction().begin();
        entityManager.remove(teacher);
        entityManager.getTransaction().commit();
    }

    public static TeacherDTO toTeacherDTO(Teacher teacher) {
        return new TeacherDTO(teacher.getTeacherName());
    }

    public static List<TeacherDTO> toPostDTO(List<Teacher> teacherList) {
        List<TeacherDTO> teacherDTOList = new ArrayList<>();

        for (Teacher teacher : teacherList) {
            teacherDTOList.add(toTeacherDTO(teacher));
        }
        return teacherDTOList;
    }

    public void closeTeacherService(){
        entityManager.close();
        JPAWrapper.closeEntityManagerFactory();
    }
}
