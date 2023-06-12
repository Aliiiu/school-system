package org.encentral.dto;

import org.encentral.entity.Student;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TeacherDTO {
    private String teacherName;

    private Date createdAt;

    public TeacherDTO(String teacherName) {
        this.teacherName = teacherName;
        this.createdAt = new Date();
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "TeacherDTO{" +
                "teacherName='" + teacherName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
