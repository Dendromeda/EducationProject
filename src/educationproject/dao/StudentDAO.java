package educationproject.dao;

import javax.persistence.EntityManagerFactory;
import educationproject.entities.Student;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;

public class StudentDAO {
    
    private EntityManagerFactory emf;
    
    public StudentDAO(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    public Student findById(int id){
        EntityManager em = emf.createEntityManager();
        Student c = em.find(Student.class, id);
        em.close();
        return c;
    }
    
    public List<Student> findByName(String string){
        EntityManager em = emf.createEntityManager();
        List<Student> list = em.createQuery("Select s from Student s WHERE s.name LIKE :name")
                //.setParameter("name", String.format("%%s%", string))
                .setParameter("name", "%"+string+"%")
                .getResultList();
        em.close();
        return list;
    }
    
    public List<Student> findByBirthdate(LocalDate from, LocalDate to){
        EntityManager em = emf.createEntityManager();
        List<Student> list = em.createQuery("Select s from Student s WHERE s.birthDate BETWEEN :from AND :to ")
                .setParameter("from", Date.valueOf(from))
                .setParameter("to", Date.valueOf(to))
                .getResultList();
        em.close();
        return list;
    }
    
    public void delete(Student student){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            em.remove(student);
        em.getTransaction().commit();
        em.close();
    }
    
    public void persist(Student student){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            em.persist(student);
        em.getTransaction().commit();
        em.close();
    }
    
}
