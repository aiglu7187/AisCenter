/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprRegion;
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
public class SprRegionFacade extends AbstractFacade<SprRegion> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprRegionFacade() {
        super(SprRegion.class);
    }
    
    public SprRegion findById(Integer regId) {
        TypedQuery <SprRegion> query = 
                em.createNamedQuery("SprRegion.findBySprregId", SprRegion.class).setParameter("sprregId", regId);
        SprRegion result;
        result = query.getSingleResult();
        return result;
    }
    
    public List<SprRegion> findByName(String regName){
        TypedQuery <SprRegion> query = 
                em.createNamedQuery("SprRegion.findBySprregName", SprRegion.class)
                        .setParameter("sprregName", regName);
        List<SprRegion> result;
        result = query.getResultList();
        return result;
    }
    
    public List<SprRegion> findNoCenter() {
        int cent = 0;
        TypedQuery <SprRegion> query = 
                em.createNamedQuery("SprRegion.findBySprregCenter", SprRegion.class).setParameter("sprregCenter", cent);
        List <SprRegion> result;
        result = query.getResultList();
        return result;
    }
    
    public List<SprRegion> findCenter() {
        int cent = 1;
        TypedQuery <SprRegion> query = 
                em.createNamedQuery("SprRegion.findBySprregCenter", SprRegion.class).setParameter("sprregCenter", cent);
        List <SprRegion> result;
        result = query.getResultList();
        return result;
    }
    
    public List<SprRegion> findNoOther() {
        int oth = 0;
        TypedQuery <SprRegion> query = 
                em.createNamedQuery("SprRegion.findBySprregOth", SprRegion.class).setParameter("sprregOth", oth);
        List <SprRegion> result;
        result = query.getResultList();
        return result;
    }
    
    public List<SprRegion> findOther() {
        int oth = 1;
        TypedQuery <SprRegion> query = 
                em.createNamedQuery("SprRegion.findBySprregOth", SprRegion.class).setParameter("sprregOth", oth);
        List <SprRegion> result;
        result = query.getResultList();
        return result;
    }
    
}
