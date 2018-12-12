package educationproject.dao;

import javax.persistence.EntityManagerFactory;
import educationproject.entities.Course;
import java.util.List;
import javax.persistence.EntityManager;

public class CourseDAO {
    
    private EntityManagerFactory emf;
    
    public CourseDAO(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    public Course findById(int id){
        EntityManager em = emf.createEntityManager();
        Course c = em.find(Course.class, id);
        em.close();
        return c;
    }
    
    public List<Course> findByName(String string){
        EntityManager em = emf.createEntityManager();
        List<Course> list = em.createQuery("Select c from Course c WHERE c.name LIKE :name")
                //.setParameter("name", String.format("%%s%", string))
                .setParameter("name", "%"+string+"%")
                .getResultList();
        em.close();
        return list;
    }
    
    public void delete(Course course){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            em.remove(course);
        em.getTransaction().commit();
        em.close();
    }
    
    public void merge(Course course){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            em.merge(course);
        em.getTransaction().commit();
        em.close();
    }
    
    public void persist(Course course){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            em.persist(course);
        em.getTransaction().commit();
        em.close();
    }
    
}
