package org.encentral.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encentral.dto.AdminDTO;
import org.encentral.dto.StudentDTO;
import org.encentral.entity.Admin;
import org.encentral.entity.QAdmin;
import org.encentral.entity.Student;
import org.encentral.util.JPAWrapper;

import java.util.ArrayList;
import java.util.List;

public class AdminService {
    private final Logger logger = LogManager.getLogger(AdminService.class);
    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;
    private QAdmin qadmin;

    public AdminService(){
        this("school-pu");
    }
    public AdminService (String pu){
        entityManager  = JPAWrapper.getEntityManager("school-pu");
        queryFactory = new JPAQueryFactory(entityManager);
        qadmin = QAdmin.admin;
    }

    public Admin addAdmin(String name, String password){
        Admin admin = new Admin(name, password);
        entityManager.getTransaction().begin();
        entityManager.persist(admin);
        entityManager.getTransaction().commit();
        return admin;
    }

    public Admin findAdminByName(String name){
        Admin admin = queryFactory.selectFrom(qadmin).where(qadmin.name.eq(name)).fetchOne();
        if (admin == null){
            logger.warn(String.format("name '%s' not found", name));
            return null;
        }
        return admin;
    }

    public void deletePost(Admin admin){
        entityManager.getTransaction().begin();
        entityManager.remove(admin);
        entityManager.getTransaction().commit();
    }

    public static AdminDTO toAdminDTO(Admin admin) {
        return new AdminDTO(admin.getName(), admin.getPassword(), admin.getCreatedAt());
    }

    public Admin changePassword(String name, String password){
        Admin adminToUpdate = findAdminByName(name);
        entityManager.getTransaction().begin();
        adminToUpdate.setPassword(password);
        entityManager.getTransaction().commit();
        return adminToUpdate;
    }

    public static List<AdminDTO> toAdminDTO(List<Admin> adminList) {
        List<AdminDTO> adminDTOList = new ArrayList<>();

        for (Admin admin : adminList) {
            adminDTOList.add(toAdminDTO(admin));
        }
        return adminDTOList;
    }

    public void close(){
        entityManager.close();
        JPAWrapper.closeEntityManagerFactory();
    }

}
