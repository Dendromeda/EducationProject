package educationproject;

import educationproject.dao.ProgramDAO;
import educationproject.dao.StudentDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.EntityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import educationproject.entities.*;
import java.util.List;

public class Main {
    
    public static void main(String[] args){
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducationProjectPU");
        MenuBuilder.initDAO(emf);
        
        Menu mainMenu = MenuBuilder.createMainMenu();
        mainMenu.execute();
    }
        
//        

        
        
 
    
    public static void initDb(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Program p = new Program("Javautveckling");
        Teacher t = new Teacher("Bita");
        em.persist(t);
        Course c = new Course("Java 101", t);
        p.addCourse(c);
        Student s = new Student("Jakob", LocalDate.of(1994,5,12));
        s.addGrade(new Grade(s,c,"MVG"));
        p.addStudent(s);
        p.addStudent(new Student("Gustaf", LocalDate.of(1983,5,28)));
        p.addStudent(new Student("Josef", LocalDate.of(1995,8,8)));
        p.addStudent(new Student("Fredrik", LocalDate.of(1995,5,5)));
        p.addStudent(new Student("Markus", LocalDate.of(1995,5,5)));
        em.persist(p);
        em.getTransaction().commit();
        
        
        
    }
  
    
    
}
