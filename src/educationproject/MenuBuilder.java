package educationproject;

import educationproject.dao.*;
import java.util.function.Consumer;
import educationproject.entities.*;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;

public class MenuBuilder {

    public static CourseDAO courseDAO;
    public static ProgramDAO programDAO;
    public static StudentDAO studentDAO;
    public static TeacherDAO teacherDAO;

    public static Program activeProgram;

    public static void initDAO(EntityManagerFactory emf) {
        courseDAO = new CourseDAO(emf);
        programDAO = new ProgramDAO(emf);
        studentDAO = new StudentDAO(emf);
        teacherDAO = new TeacherDAO(emf);
    }

    public static Menu createMainMenu() {
        Menu mainMenu = new Menu("Main menu");
        mainMenu.addOption(createCourseMenu());
        mainMenu.addOption(createProgramMenu());
        mainMenu.addOption(createStudentMenu());
        mainMenu.addOption(createTeacherMenu());
        
        return mainMenu;
    }

    private static Menu createCourseMenu() {
        Menu menu = new Menu("Courses");

        menu.addOption("Add new Course", s -> {
            Course course = new Course();
            System.out.print("Name: ");
            course.setName(Menu.readString());
            System.out.print("Teacher ID: ");
            teacherDAO.findById(Menu.readNumber()).addCourse(course);
            courseDAO.persist(course);
        });
        menu.addOption("List by name", s -> {
            System.out.print("Name: ");
            List<Course> list = courseDAO.findByName(Menu.readString());
            for (Course course : list) {
                System.out.println(course);
            }
        });
        menu.addOption("Update by ID", s -> {
            System.out.print("ID: ");
            Course course = courseDAO.findById(Menu.readNumber());
            if (course == null) {
                System.out.println("No such ID");
                return;
            }
            System.out.println(course);
            System.out.print("Name (Leave blank if unchanged): ");
            String name = Menu.readString();
            if (!name.equals("")) {
                course.setName(Menu.readString());
            }
            System.out.print("Teacher ID (Leave blank if unchanged): ");
            int teacherId = Menu.readNumber();
            if (teacherId != -1) {
                teacherDAO.findById(teacherId).addCourse(course);
            }
            courseDAO.merge(course);

        });
        menu.addOption("Delete by ID", s -> {
            System.out.print("ID: ");
            Course course = courseDAO.findById(Menu.readNumber());
            if (course == null) {
                System.out.println("No such ID");
                return;
            }
            System.out.println(course);
            System.out.print("Delete course? (Y/N)");
            if (Menu.readString().equalsIgnoreCase("Y")) {
                courseDAO.delete(course);
            }
        });
        return menu;
    }

    private static Menu createProgramMenu() {
        Menu menu = new Menu("Programs");

        menu.addOption("Add new Program", s -> {
            System.out.print("Name: ");
            Program program = new Program(Menu.readString());
            programDAO.persist(program);
        });
        menu.addOption("List all programs", s -> {
            List<Program> list = programDAO.findByName("");
            for (Program program : list) {
                System.out.println(program);
            }
        });
        menu.addOption("Edit program by ID", s -> {
            System.out.print("ID: ");
            Program program = programDAO.findById(Menu.readNumber());
            if (program == null) {
                System.out.println("No such ID");
                return;
            }
            activeProgram = program;
            createProgramSubMenu().execute();
        });
        menu.addOption("Delete by ID", s -> {
            System.out.print("ID: ");
            Program program = programDAO.findById(Menu.readNumber());
            if (program == null) {
                System.out.println("No such ID");
                return;
            }
            programDAO.delete(program);
        });
        return menu;
    }

    private static Menu createProgramSubMenu() {
        Menu menu = new Menu("Edit Program " + activeProgram.toString());

        menu.addOption("List Courses", s -> {
            for (Course c : activeProgram.getCourses()) {
                System.out.println(c);
            }
        });
        menu.addOption("List Students", s -> {
            for (Student student : activeProgram.getStudents()) {
                System.out.println(student);
            }
        });
        menu.addOption("Add Course to Program", s -> {
            System.out.print("Course ID: ");
            Course course = courseDAO.findById(Menu.readNumber());
            if (course == null){
                System.out.println("No such ID");
                return;
            }
            activeProgram.addCourse(course);
            programDAO.merge(activeProgram);
        });
        menu.addOption("Add Student to Program", s -> {
            System.out.print("Student ID: ");
            Student student = studentDAO.findById(Menu.readNumber());
            if (student == null){
                System.out.println("No such ID");
                return;
            }
            activeProgram.addStudent(student);
            programDAO.merge(activeProgram);
        });
        menu.addOption("Change name", s -> {
            System.out.print("Name: ");
            activeProgram.setName(Menu.readString());
            programDAO.merge(activeProgram);
        });

        return menu;
    }

    private static Menu createStudentMenu() {
        Menu menu = new Menu("Students");

        menu.addOption("Add new Student", s -> {
            Student student = new Student();
            System.out.print("Name: ");
            student.setName(Menu.readString());
            System.out.print("Birthdate (YYYY-MM-DD): ");
            student.setBirthDate(Date.valueOf(Menu.readString()));
            System.out.println("Program (Leave blank if no program):");
            int programId = Menu.readNumber();
            if (programId != -1) {
                programDAO.findById(programId).addStudent(student);
            }
            studentDAO.persist(student);
        });

        menu.addOption("List by name", s -> {
            System.out.print("Name: ");
            List<Student> list = studentDAO.findByName(Menu.readString());
            for (Student student : list) {
                System.out.println(student);
            }
        });

        menu.addOption("Update by ID", s -> {
            System.out.print("ID: ");
            Student student = studentDAO.findById(Menu.readNumber());
            if (student == null) {
                System.out.println("No such ID");
                return;
            }
            System.out.println(student);
            System.out.print("New name (Leave blank if unchanged): ");
            String name = Menu.readString();
            if (!name.equals("")) {
                student.setName(name);
            }
            System.out.print("Birthdate (YYYY-MM-DD) (Leave blank if unchanged): ");
            String date = Menu.readString();
            if (!date.equals("")) {
                student.setBirthDate(Date.valueOf(date));
            }
            System.out.println("Program (Leave blank if unchanged):");
            int programId = Menu.readNumber();
            if (programId != -1) {
                programDAO.findById(programId).addStudent(student);
            }
            studentDAO.merge(student);

        });

        menu.addOption("Delete by ID", s -> {
            System.out.print("ID: ");
            Student student = studentDAO.findById(Menu.readNumber());
            if (student == null) {
                System.out.println("No such ID");
                return;
            }
            System.out.println(student);
            System.out.print("Delete student? (Y/N)");
            if (Menu.readString().equalsIgnoreCase("Y")) {
                studentDAO.delete(student);
            }
        });
        menu.addOption("Add Grade", s -> {
            System.out.print("Student ID: ");
            Student student = studentDAO.findById(Menu.readNumber());
            if (student == null) {
                System.out.println("No such ID");
                return;
            }
            System.out.println(student);
            System.out.print("Course ID: ");
            Course course = courseDAO.findById(Menu.readNumber());
            if (course == null) {
                System.out.println("No such ID");
                return;
            }
            System.out.print("Grade: ");
            student.addGrade(Menu.readString(), course);
            studentDAO.merge(student);
        });

        return menu;
    }
    
    private static Menu createTeacherMenu(){
        Menu menu = new Menu("Teachers");
        menu.addOption("Add new Teacher", s -> {
            System.out.print("Name: ");
            Teacher teacher = new Teacher(Menu.readString());
            teacherDAO.persist(teacher);
        });
        menu.addOption("List by name", s -> {
            System.out.print("Name: ");
            List<Teacher> list = teacherDAO.findByName(Menu.readString());
            for (Teacher teacher : list) {
                System.out.println(teacher);
            }
        });
        menu.addOption("Add Course", s -> {
            System.out.print("Teacher ID: ");
            Teacher teacher = teacherDAO.findById(Menu.readNumber());
            if (teacher == null){
                System.out.println("No such ID");
                return;
            } 
            System.out.print("Course ID: ");
            Course course = courseDAO.findById(Menu.readNumber());
            if (teacher == null){
                System.out.println("No such ID");
                return;
            }
            teacher.addCourse(course);
            teacherDAO.merge(teacher);
        });
        menu.addOption("Update name", s -> {
            System.out.print("Teacher ID: ");
            Teacher teacher = teacherDAO.findById(Menu.readNumber());
            if (teacher == null){
                System.out.println("No such ID");
                return;
            }
            System.out.println(teacher);
            System.out.print("Name: ");
            teacher.setName(Menu.readString());
            teacherDAO.merge(teacher);
            
        });
        return menu;
    }

}
