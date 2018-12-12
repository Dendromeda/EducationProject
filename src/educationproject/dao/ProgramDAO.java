package educationproject.dao;

import javax.persistence.EntityManagerFactory;
import educationproject.entities.Program;
import java.util.List;
import javax.persistence.EntityManager;

public class ProgramDAO {
    
    private EntityManagerFactory emf;
    
    public ProgramDAO(EntityManagerFactory emf){
        this.emf = emf;
    }
    
    public Program findById(int id){
        EntityManager em = emf.createEntityManager();
        Program c = em.find(Program.class, id);
        em.close();
        return c;
    }
    
    public List<Program> findByName(String string){
        EntityManager em = emf.createEntityManager();
        List<Program> list = em.createQuery("Select p from Program p WHERE p.name LIKE :name")
                //.setParameter("name", String.format("%%s%", string))
                .setParameter("name", "%"+string+"%")
                .getResultList();
        em.close();
        return list;
    }
    
    public void delete(Program program){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            em.remove(program);
        em.getTransaction().commit();
        em.close();
    }
    
        public void merge(Program program){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            em.merge(program);
        em.getTransaction().commit();
        em.close();
    }
    
    public void persist(Program program){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
            em.persist(program);
        em.getTransaction().commit();
        em.close();
    }
    
}
