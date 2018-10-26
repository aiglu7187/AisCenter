/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprOmsu;
import Entities.SprRegion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprOmsuFacade extends AbstractFacade<SprOmsu> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprOmsuFacade() {
        super(SprOmsu.class);
    }
    
    public List<SprOmsu> findAllSprOmsu(){
        TypedQuery<SprOmsu> query = em.createNamedQuery("SprOmsu.findAll", SprOmsu.class);
        List<SprOmsu> result = query.getResultList();
        return result;
    }
    
    public SprOmsu findById(Integer id){
        TypedQuery<SprOmsu> query = em.createNamedQuery("SprOmsu.findBySpromsuId", SprOmsu.class)
                .setParameter("spromsuId", id);
        SprOmsu result = query.getSingleResult();
        return result;
    }
    
    public List<SprOmsu> findByRegion (SprRegion sprregId){
        TypedQuery<SprOmsu> query = em.createNamedQuery("SprOmsu.findBySprregId", SprOmsu.class)
                .setParameter("sprregId", sprregId);
        List<SprOmsu> result = query.getResultList();
        return result;
    }
    
}
