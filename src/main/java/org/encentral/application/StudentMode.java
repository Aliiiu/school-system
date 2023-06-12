package org.encentral.application;

import com.google.common.base.Preconditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encentral.BackendController;
import org.encentral.Main;
import org.encentral.dto.CourseDTO;
import org.encentral.dto.StudentDTO;
import org.encentral.entity.Course;
import org.encentral.entity.Student;
import org.encentral.service.CourseService;
import org.encentral.service.StudentService;
import org.encentral.service.TeacherService;
import org.encentral.util.ResponseMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class StudentMode {
    private static final Logger logger = LogManager.getLogger(StudentMode.class);
    private static Scanner sc;

    private static BackendController backend = BackendController.getInstance();

    public static void displayCourseMenu(){
        System.out.println("Select 0 to end program");
        System.out.println("Select 1 to add MATHS to the list of courses");
        System.out.println("Select 2 to add ENGLISH to the list of courses");
        System.out.println("Select 3 to add FINE ART to the list of courses");
        System.out.println("Select 4 to add FURTHER MATHS to the list of courses");
        System.out.println("Select 5 to add AGRIC to the list of courses");
        System.out.println("Select 6 to add BIOLOGY to the list of courses");
        System.out.println("Select 7 to add CHEMISTRY to the list of courses");
        System.out.println("Select 8 to add PHYSICS to the list of courses");
        System.out.println("Select 9 to register courses");
    }

    public static int chooseMode() {

        System.out.println("Please Enter 1 to register to become a student");
        System.out.println("or 2 to sign in as a student");
        System.out.println("or Enter 0 to end the program");
        sc = new Scanner(System.in);
        int choice = -1;
        do {
            try {
                String input = sc.nextLine();
                choice = Integer.parseInt(input);
                switch (choice) {
                    case 0:
                        logger.info("Exiting Program...");
                        System.exit(0);
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                    default:
                        logger.warn("\nPlease enter either 1 or 2 ");
                        break;
                }

            } catch (final NumberFormatException e) {
                logger.warn("\nPlease enter a valid number: ");
            }
        }
        while (choice != 0);

        return -1;
    }

    public static void registerStudent(){
        sc = new Scanner(System.in);
        System.out.println("Enter the student name");
        String name = sc.nextLine();
        System.out.println("Enter the student year, this must be between 1 and 6");
        int year = sc.nextInt();

        Student student1;
        ResponseMessage<Student> message3 = backend.signUpUser(name, year);
        student1 = message3.getResponseData();
        ResponseMessage<List<CourseDTO>> message = backend.getListOfAllCourses();
        System.out.println(message.getResponseData().size());

        ResponseMessage<Course> message1;
        Set<Course> courses = new HashSet<>();

        int choice = -1;

        do{
            System.out.println(courses.size());
            displayCourseMenu();

            try{

                sc = new Scanner(System.in);
                choice = sc.nextInt();
                switch (choice){
                    case 0:
                        logger.info("Exiting Program...");
                        System.exit(0);
                        break;
                    case 1:
                        message1 = backend.findCourseByTitle("MATHS");
                        System.out.println(message1.getResponseData());
                        courses.add(message1.getResponseData());
                        break;
                    case 2:
                        message1 = backend.findCourseByTitle("ENGLISH");
                        System.out.println(message1.getResponseData());
                        courses.add(message1.getResponseData());
                        break;
                    case 3:
                        message1 = backend.findCourseByTitle("FINE ART");
                        System.out.println(message1.getResponseData());
                        courses.add(message1.getResponseData());
                        break;
                    case 4:
                        message1 = backend.findCourseByTitle("FURTHER MATHS");
                        System.out.println(message1.getResponseData());
                        courses.add(message1.getResponseData());
                        break;
                    case 5:
                        message1 = backend.findCourseByTitle("AGRIC");
                        System.out.println(message1.getResponseData());
                        courses.add(message1.getResponseData());
                        break;
                    case 6:
                        message1 = backend.findCourseByTitle("BIOLOGY");
                        System.out.println(message1.getResponseData());
                        courses.add(message1.getResponseData());
                        break;
                    case 7:
                        message1 = backend.findCourseByTitle("CHEMISTRY");
                        System.out.println(message1.getResponseData());
                        courses.add(message1.getResponseData());
                        break;
                    case 8:
                        message1 = backend.findCourseByTitle("PHYSICS");
                        System.out.println(message1.getResponseData());
                        courses.add(message1.getResponseData());
                        break;
                    case 9:
                        ResponseMessage<Student> message4 = backend.registerStudentCourse(student1, courses);
                        if (message4.getResponseData().getStudentId() != null){
                            logger.info(message4.getMessage());
                            logger.info("Student id is {}", message4.getResponseData().getStudentId());
                            signIn();
                        } else {
                            logger.warn(message4.getMessage());
                        }
                        break;
                    default:
                        logger.warn("\nPlease enter number between 0 and 9 ");
                        break;
                }
            } catch (NumberFormatException e){
                logger.warn("\nPlease enter a valid number: ");
            }
            catch (IllegalArgumentException e){
                logger.warn("\n {}", e.getMessage());
            }
        }
        while (choice != 0);
    }

    public static void signIn(){
        sc = new Scanner(System.in);
        System.out.println("Enter your id");
        String id = sc.nextLine();
        System.out.println("Enter your name");
        String name = sc.nextLine();
        try{
            ResponseMessage<Boolean> message = backend.signInUser(name, Long.parseLong(id));
            if (message.getResponseData()){
                int choice = -1;
                do{
                    System.out.println("Enter 0 to exit program");
                    System.out.println("Enter 1 to get assigned teacher");
                    System.out.println("Enter 2 to get list of courses registered");
                    choice = sc.nextInt();

                    switch (choice){
                        case 0:
                            System.out.println("Exiting program");
                            System.exit(0);
                            backend.shutDown();
                            break;
                        case 1:
                            ResponseMessage<String> message1 = backend.getTeacherAssignedToStudent(name);
                            System.out.println(message1.getResponseData());
                            break;
                        case 2:
                            ResponseMessage<List<CourseDTO>> message2 = backend.getListOfRegisteredCoursesByStudent(name);
                            System.out.println(message2.getMessage());
                            List<CourseDTO> response = message2.getResponseData();
                            for (CourseDTO course : response){
                                System.out.println(course);
                            }
                            break;
                        default:
                            logger.warn("\nPlease enter number between 1 and 2");
                            break;
                    }
                }
                while (choice != 0);
            }
        }catch (Exception e){
            logger.warn(e.getMessage());
        }

    }

    public static void run(){
        int mode = chooseMode();
        switch (mode) {
            case 1:
                StudentMode.registerStudent();
                break;
            case 2:
                StudentMode.signIn();
                break;
            default:
                break;
        }
    }
}
