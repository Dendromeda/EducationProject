package educationproject.dao;

import javax.persistence.EntityManagerFactory;
import educationproject.entities.Teacher;
import java.util.List;
import javax.persistence.EntityManager;

public class TeacherDAO {
    
    private EntityManagerFactory emf;
    
    public TeacherDAO(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    public Teacher findById(int id){
        EntityManager em = emf.createEntityManager();
        Teacher c = em.find(Teacher.class, id);
        em.close();
        return c;
    }
    
    public List<Teacher> findByName(String string){
        EntityManager em = emf.createEntityManager();
        List<Teacher> list = em.createQuery("Select c from Teacher c WHERE c.name LIKE :name")
                //.setParameter("name", String.format("%%s%", string))
                .setParameter("name", "%"+string+"%")
                .getResultList();
        em.close();
        return list;
    }
    
    public void delete(Teacher teacher){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            if (em.contains(teacher))
                em.merge(teacher);
            else
                em.persist(teacher);
        em.getTransaction().commit();
        em.close();
    }
    
        public void merge(Teacher teacher){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            em.merge(teacher);
        em.getTransaction().commit();
        em.close();
    }
    
    public void persist(Teacher teacher){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            em.persist(teacher);
        em.getTransaction().commit();
        em.close();
    }
    
}
