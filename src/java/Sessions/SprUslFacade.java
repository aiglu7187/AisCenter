/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprOsnusl;
import Entities.SprUsl;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author admin_ai
 */
@Stateless
public class SprUslFacade extends AbstractFacade<SprUsl> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprUslFacade() {
        super(SprUsl.class);
    }
    
    public List<SprUsl> findByOsnusl(SprOsnusl osnUsl) {
        TypedQuery <SprUsl> query = 
                em.createNamedQuery("SprUsl.findBySprosnuslId", SprUsl.class).setParameter("sprosnuslId", osnUsl);
        List<SprUsl> result;
        result = query.getResultList();
        return result;
    }
    
    public List<SprUsl> findByOsnuslNotLesson(SprOsnusl osnUsl) {
        String qlString = "SELECT s FROM SprUsl s WHERE s.sprosnuslId = :sprosnuslId "
                + "AND s.spruslLesson = :spruslLesson ";
        TypedQuery <SprUsl> query = 
                em.createQuery(qlString, SprUsl.class)
                        .setParameter("sprosnuslId", osnUsl)
                        .setParameter("spruslLesson", 0);
        List<SprUsl> result;
        result = query.getResultList();
        return result;
    }
    
    public SprUsl findById(Integer uslId) {
        TypedQuery <SprUsl> query = 
                em.createNamedQuery("SprUsl.findBySpruslId", SprUsl.class).setParameter("spruslId", uslId);
        SprUsl result;
        result = query.getSingleResult();
        return result;
    }
    
    public List<SprUsl> findAllUsl(){
        TypedQuery<SprUsl> query = 
                em.createNamedQuery("SprUsl.findAll", SprUsl.class);
        List<SprUsl> result;
        result = query.getResultList();
        return result;
    }
    
    public SprUsl findByName(String uslName) {
        TypedQuery <SprUsl> query = 
                em.createNamedQuery("SprUsl.findBySpruslName", SprUsl.class).setParameter("spruslName", uslName);
        SprUsl result;
        result = query.getSingleResult();
        return result;
    }
    
    public List<SprUsl> findPmpk() {
        String qlString = "SELECT u FROM SprUsl u WHERE u.spruslPmpk = 1";
        TypedQuery <SprUsl> query = em.createQuery(qlString, SprUsl.class);
        List<SprUsl> result;
        result = query.getResultList();
        return result;
    }

}
