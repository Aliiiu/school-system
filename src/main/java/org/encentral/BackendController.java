package org.encentral;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import org.encentral.dto.*;
import org.encentral.entity.*;
import org.encentral.service.*;
import org.encentral.util.ResponseMessage;

import java.util.*;

public class BackendController {
    private static BackendController instance = null;
    private static TeacherService teacherService;
    private static CourseService courseService;
    private static StudentService studentService;
    private static AdminService adminService;
//    private Student student;

    public BackendController(){
        studentService = new StudentService();
        courseService = new CourseService();
        teacherService = new TeacherService();
        adminService = new AdminService();
//        student = null;
    }

    public ResponseMessage<Student> signUpUser(String name, int year) {
        Student student = studentService.findStudentByName(name);

        if (student != null){
            return  new ResponseMessage<>(400,"User exists already", student);
        }

        student = studentService.registerStudent(name, year);

        if (student != null) {
            return  new ResponseMessage<>(201, "User Created Successfully", student);
        }
        return new ResponseMessage<>(505,"Internal Server Error", student);

    }

    public ResponseMessage<Boolean> createAdmin(String name, String password){
        Admin admin = adminService.findAdminByName(name);

        if(admin != null){
            return  new ResponseMessage<>(400,"Admin exists already", false);
        }

        admin = adminService.addAdmin(name, password);

        if (admin != null) {
            return  new ResponseMessage<>(201, "Admin Created Successfully", true);
        }
        return new ResponseMessage<>(505,"Internal Server Error", false);
    }

    public ResponseMessage<Boolean> signInAdmin(String name, String password) {
        Admin admin = adminService.findAdminByName(name);

        if (Objects.equals(admin.getPassword(), password)) {
            return  new ResponseMessage<>(200, "Sign in successfully", true);
        }
        return new ResponseMessage<>(400, "Incorrect Username or Password", false);
    }

    public ResponseMessage<Boolean> signInUser(String name, Long id) {
        Student student = studentService.findStudentByName(name);

        if (student.getStudentId() == id) {
            return  new ResponseMessage<>(200, "Sign in successfully", true);
        }
        return new ResponseMessage<>(400, "Incorrect Username or Password", false);
    }

    public ResponseMessage<Student> registerStudentCourse(Student student, Set<Course> courses) throws IllegalArgumentException {
        Preconditions.checkArgument(courses.size() >= 5 && courses.size() <= 7, "You can only register between 5 and 7 courses");

        Student studentToUpdate = studentService.findStudentByName(student.getName());
        int length = studentToUpdate.getCourses().size();
        if (length > 0){
            return new ResponseMessage<>(400, "Student already registered for courses", studentToUpdate);
        } else {
            studentToUpdate = studentService.registerStudentCourse(studentToUpdate, courses);
            return new ResponseMessage<>(201, "You have registered for " + courses.size() + " courses", studentToUpdate);
        }
    }

    public ResponseMessage<Boolean> addAllCourses(List<Course> courses){
        courseService.addAllCourse(courses);
        return new ResponseMessage<>(201, courses.size() + " courses added successfully", true);
    }
    public ResponseMessage<Boolean> addAllTeachers(List<Teacher> teacherList){
        teacherService.addAllTeachers(teacherList);
        return new ResponseMessage<>(201, teacherList.size() + " teachers added successfully", true);
    }

    public ResponseMessage<Boolean> findUserByName(String name){
        Student student = studentService.findStudentByName(name);
        if (student != null){
            return new ResponseMessage<>(201, "search successful", true);
        } else {
            return new ResponseMessage<>(400, "student not found", false);
        }
    }

    public ResponseMessage<Course> findCourseByTitle(String title){
        Course course = courseService.findByCourseName(title);
        if (course != null){
            return new ResponseMessage<>(201, "search successful", course);
        } else {
            return new ResponseMessage<>(400, "student not found", course);
        }
    }

    public ResponseMessage<List<CourseDTO>> getListOfRegisteredCoursesByStudent(String name){
        Student student = studentService.findStudentByName(name);
        List<CourseDTO> studentList = studentService.getCourseList(student.getStudentId());
        if(studentList.size() > 0){
            return new ResponseMessage<>(201, "List fetch successfully", studentList);
        } else {
            return new ResponseMessage<>(400, "Student is yet to register for courses", studentList);
        }
    }

    public ResponseMessage<List<CourseDTO>> getListOfAllCourses(){
        List<CourseDTO> courseList = courseService.getAllCourses();
        if(courseList.size() > 0){
            return new ResponseMessage<>(201, "List fetch successfully", courseList);
        } else {
            return new ResponseMessage<>(400, "No course registered yet", courseList);
        }
    }

    public ResponseMessage<List<Teacher>> getAllTeachers(){
        List<Teacher> teacherList = teacherService.getAllTeachers();
        if(teacherList.size() > 0){
            return new ResponseMessage<>(201, "List fetch successfully", teacherList);
        } else {
            return new ResponseMessage<>(400, "No course registered yet", teacherList);
        }
    }

    public ResponseMessage<List<StudentDTO>> getAllStudent(){
        List<StudentDTO> studentList = studentService.getAllStudents();
        if(studentList.size() > 0){
            return new ResponseMessage<>(201, "List fetch successfully", studentList);
        } else {
            return new ResponseMessage<>(400, "No course registered yet", studentList);
        }
    }

    public ResponseMessage<String> getTeacherAssignedToStudent(String name){
//        Student student = studentService.findStudentByName(name);
        String assignedTeacher = studentService.getStudentGuide(name);
        ObjectMapper objectMapper = new ObjectMapper();
        String teacherJson;
        try {
            teacherJson = objectMapper.writeValueAsString(assignedTeacher);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(teacherJson.length() > 0){
            return new ResponseMessage<>(201, "Fetch successful", teacherJson);
        } else {
            return new ResponseMessage<>(400, "Teacher not found", teacherJson);
        }
    }

    public static BackendController getInstance() {
        if (instance == null) {
            instance = new BackendController();
        }
        return  instance;
    }

    public void shutDown() {
        studentService.closeStudentService();
        courseService.closeCourseService();
        teacherService.closeTeacherService();
    }

}
