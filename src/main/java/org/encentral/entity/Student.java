package org.encentral.entity;

import com.google.common.base.Preconditions;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.*;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentId;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    @Min(value = 1, message = "student year should not be lower than 1")
    @Max(value = 7, message = "student year should not be greater than 7")
    private int year;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher personalGuide;

    @Column(name = "createdAt")
    private Date createdAt;

    public Student(){}

    public Student(Long studentId, String name, Set<Course> courses, Teacher personalGuide, int year) {
        Preconditions.checkArgument(year >= 1 && year <= 7, "Year must be between 1 and 7");
        this.studentId = studentId;
        this.name = name;
        this.courses = courses;
        this.personalGuide = personalGuide;
        this.createdAt = new Date();
        this.year = year;
    }

    public Student(String name, int year){
        Preconditions.checkArgument(year >= 1 && year <= 7, "Year must be between 1 and 7");
        this.name = name;
        this.courses = new HashSet<>();
        this.createdAt = new Date();
        this.year = year;
    }
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCourse(Course course) {
        boolean added = courses.add(course);
        if (added){
            course.getStudents().add(this);
        }
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Teacher getPersonalGuide() {
        return personalGuide;
    }

    public void setPersonalGuide(Teacher personalGuide) {
        this.personalGuide = personalGuide;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        Preconditions.checkArgument(year >= 1 && year <= 7, "Year must be between 1 and 7");
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) && Objects.equals(createdAt, student.createdAt);
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", courses=" + courses +
                ", personalGuide=" + personalGuide +
                ", createdAt=" + createdAt +
                '}';
    }
}
