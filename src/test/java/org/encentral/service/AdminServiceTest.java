package org.encentral.service;
import org.encentral.entity.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {
    private static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:alpine");;
    private static AdminService adminService;

    @BeforeEach
    public void startContainer() {
        // Create a PostgreSQL container
        container.start();

        // Set the database URL, username, and password
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());

        adminService = new AdminService("school-test-pu");
    }

    @AfterEach
    public void tearDown() {
        // Stop the PostgreSQL container
        container.stop();
    }


    @Test
    void addAdmin() {
        Admin admin = adminService.addAdmin("admin", "admin");
        assertNotNull(admin);
    }

    @Test
    void findAdminByName() {
        adminService.addAdmin("admin", "admin");
        Admin admin = adminService.findAdminByName("admin");

    }

    @Test
    void changePassword() {
        adminService.addAdmin("admin", "admin");
        Admin admin = adminService.changePassword("admin", "12345");
        assertEquals("12345", admin.getPassword());
    }
}