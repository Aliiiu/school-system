package org.encentral.dto;

import org.encentral.entity.Student;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CourseDTO {

    private String courseName;

    private Date createdAt;

    public CourseDTO(String courseName, Date createdAt) {
        this.courseName = courseName;
        this.createdAt = createdAt;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "courseName='" + courseName + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
