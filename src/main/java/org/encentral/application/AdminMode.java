package org.encentral.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encentral.BackendController;
import org.encentral.Main;
import org.encentral.dto.CourseDTO;
import org.encentral.dto.StudentDTO;
import org.encentral.dto.TeacherDTO;
import org.encentral.entity.Admin;
import org.encentral.entity.Student;
import org.encentral.entity.Teacher;
import org.encentral.service.StudentService;
import org.encentral.service.TeacherService;
import org.encentral.util.ResponseMessage;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AdminMode {
    private static final Logger logger = LogManager.getLogger(StudentMode.class);

    private static Scanner sc;

    private static BackendController backend = BackendController.getInstance();

    private static String name;

    public static void signIn(){
        sc = new Scanner(System.in);
        logger.info("\n======= SignIn ========");
        int tries = 5;
        sc = new Scanner(System.in);

        while (tries > 0) {
            System.out.println("Enter you name");
            String name = sc.nextLine();
            System.out.println("Enter you password");
            String password = sc.nextLine();
            ResponseMessage<Boolean> message = backend.signInAdmin(name, password);
            if(message.getResponseData()){
                logger.info(message.getMessage());
                run();
            } else {
                logger.warn(message.getMessage());
            }
            tries--;
        }
        int choice = -1;
    }

    public static void namePrompter (){
        sc = new Scanner(System.in);
        System.out.println("Enter your name");
        name = sc.nextLine();
    }
    public static void run(){

        int choice = -1;

        do{
            System.out.println("welcome to admin mode");
            System.out.println("Press 0 to exit program");
            System.out.println("Press 1 to get list of teachers in school");
            System.out.println("Press 2 to get list of students in school");
            System.out.println("Press 3 to get list of courses registered by a student");
            System.out.println("Press 4 to get the assigned teacher to a student");
            sc = new Scanner(System.in);

            try {
                choice = sc.nextInt();
                switch (choice){
                    case 0:
                        System.out.println("Exiting program");
                        System.exit(0);
                        backend.shutDown();
                        break;
                    case 1:
                        ResponseMessage<List<Teacher>> message = backend.getAllTeachers();
                        List<TeacherDTO> teacherList = TeacherService.toPostDTO(message.getResponseData());
                        System.out.println(message.getMessage());
                        for (TeacherDTO teacher : teacherList){
                            System.out.println(teacher);
                        }
                        break;
                    case 2:
                        ResponseMessage<List<StudentDTO>> message1 = backend.getAllStudent();
                        List<StudentDTO> studentList = message1.getResponseData();
                        System.out.println(studentList);
                        break;
                    case 3:
                        namePrompter();
                        ResponseMessage<List<CourseDTO>> message2 = backend.getListOfRegisteredCoursesByStudent(name);
                        System.out.println(message2.getResponseData());
                        break;
                    case 4:
                        namePrompter();
                        ResponseMessage<String> message3 = backend.getTeacherAssignedToStudent(name);
                        System.out.println(message3.getResponseData());
                        break;
                    default:
                        logger.warn("\nPlease enter either 0 or 4 ");
                        break;
                }
            }catch (final NumberFormatException e) {
                logger.warn("\nPlease enter a valid number: ");
            }

        }while (choice != 0);
    }

}
