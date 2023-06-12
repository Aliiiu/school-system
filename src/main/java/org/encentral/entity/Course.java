package org.encentral.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long courseId;

    @Column(name = "courseName")
    private String courseName;

    @ManyToMany(mappedBy = "courses")
    private Set<Student> students = new HashSet<>();

    private Date createdAt;

    public Course (){}

    public Course(Long courseId, String courseName, Set<Student> students) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.students = students;
        this.createdAt = new Date();
    }

    public Course(String courseName){
        this.courseName = courseName;
        this.students = new HashSet<>();
        this.createdAt = new Date();
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
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
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId) && Objects.equals(createdAt, course.createdAt);
    }

    @Override
    public int hashCode() {
        int result = courseId != null ? courseId.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Course{ " +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", students=" + students +
                ", createdAt=" + createdAt +
                '}';
    }
}
