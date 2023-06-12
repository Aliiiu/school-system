package org.encentral.dto;

import java.util.Date;

public class AdminDTO {
    private String name;

    private String password;

    private Date createdAt;

    public AdminDTO(String name, String password, Date createdAt) {
        this.name = name;
        this.password = password;
        this.createdAt = createdAt;
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
    public String toString() {
        return "AdminDTO{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
