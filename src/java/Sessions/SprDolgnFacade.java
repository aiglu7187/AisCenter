/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprDolgn;
import Entities.SprDolgnType;
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
public class SprDolgnFacade extends AbstractFacade<SprDolgn> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprDolgnFacade() {
        super(SprDolgn.class);
    }
    
    public List<SprDolgn> findByType(SprDolgnType t){
        TypedQuery <SprDolgn> query = 
                em.createNamedQuery("SprDolgn.findBySprdolgnType", SprDolgn.class).setParameter("sprdolgnType", t);
        List <SprDolgn> result;
        result = query.getResultList();
        return result;
    }
    
    public SprDolgn findById(Integer dolgnId) {
        TypedQuery <SprDolgn> query = 
                em.createNamedQuery("SprDolgn.findBySprdolgnId", SprDolgn.class).setParameter("sprdolgnId", dolgnId);
        SprDolgn result;
        result = query.getSingleResult();
        return result;
    }
    
    public List<SprDolgn> findAllDolgn(){
        TypedQuery<SprDolgn> query = em.createNamedQuery("SprDolgn.findAll", SprDolgn.class);
        List<SprDolgn> result;
        result = query.getResultList();
        return result;
    }
    
}
