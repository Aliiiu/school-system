package org.encentral.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long adminId;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "createdAt")
    private Date createdAt;

    public Admin(){}

    public Admin(Long adminId, String name, String password, Date createdAt) {
        this.adminId = adminId;
        this.name = name;
        this.password = password;
        this.createdAt = createdAt;
    }

    public Admin(String name, String password) {
        this.name = name;
        this.password = password;
        this.createdAt = new Date();
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(adminId, admin.adminId) && Objects.equals(createdAt, admin.createdAt);
    }

    @Override
    public int hashCode() {
        int result = adminId != null ? adminId.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
