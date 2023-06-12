package org.encentral;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.encentral.application.AdminMode;
import org.encentral.application.StudentMode;
import org.encentral.entity.Course;
import org.encentral.entity.Teacher;

import java.util.*;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static Scanner sc;

    private static BackendController backend;

    public static void setup(){
        backend.createAdmin("admin", "admin");

        List<Course> courses = new ArrayList<>(List.of(
                new Course("MATHS"),
                new Course("ENGLISH"),
                new Course("FINE ART"),
                new Course("FURTHER MATHS"),
                new Course("AGRIC"),
                new Course("BIOLOGY"),
                new Course("CHEMISTRY"),
                new Course("PHYSICS")
        ));

        backend.addAllCourses(courses);

        Teacher teacher1 = new Teacher("John");
        Teacher teacher2 = new Teacher("Emma");
        Teacher teacher3 = new Teacher("David");

        ArrayList<Teacher> teacherList = new ArrayList<>(List.of(teacher1, teacher2, teacher3));
        backend.addAllTeachers(teacherList);
    }

    public static int chooseMode() {

        System.out.println("Please Enter 1 to enter student mode");
        System.out.println("or 2 to sign in as an admin");
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
                        backend.shutDown();
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

    public static void main(String[] args) {
        backend = BackendController.getInstance();
        setup();
        System.out.println("Welcome to Encentral school");
        int mode = chooseMode();
        switch (mode) {
            case 1:
                StudentMode.run();
                break;
            case 2:
                AdminMode.signIn();
                break;
            default:
                break;
        }
    }
}