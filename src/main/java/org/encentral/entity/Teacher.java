package org.encentral.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long teacherId;

    @Column(name = "teacherName")
    private String teacherName;

    @OneToMany(mappedBy = "personalGuide")
    private Set<Student> students;

    private Date createdAt;

    public Teacher (){}

    public Teacher(Long teacherId, String teacherName, Set<Student> students) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.students = students;
        this.createdAt = new Date();
    }

    public Teacher(String teacherName){
        this.teacherName = teacherName;
        this.students = new HashSet<>();
        this.createdAt = new Date();
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(teacherId, teacher.teacherId) && Objects.equals(createdAt, teacher.createdAt);
    }

    @Override
    public int hashCode() {
        int result = teacherId != null ? teacherId.hashCode() : 0;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", students=" + students +
                ", createdAt=" + createdAt +
                '}';
    }
}
