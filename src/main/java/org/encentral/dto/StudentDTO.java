package org.encentral.dto;
import org.encentral.entity.Course;
import org.encentral.entity.Teacher;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class StudentDTO {
    private String name;
    private Set<Course> courses = new HashSet<>();
    private TeacherDTO personalGuide;

    private Date createdAt;

    public StudentDTO(String name, TeacherDTO personalGuide, Date createdAt) {
        this.name = name;
        this.personalGuide = personalGuide;
        this.createdAt = createdAt;
    }

    public StudentDTO(String name, Date createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public TeacherDTO getPersonalGuide() {
        return personalGuide;
    }

    public void setPersonalGuide(TeacherDTO personalGuide) {
        this.personalGuide = personalGuide;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "name='" + name + '\'' +
//                ", courses=" + courses +
                ", personalGuide=" + personalGuide +
                ", createdAt=" + createdAt +
                '}';
    }
}
