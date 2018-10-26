/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprMse;
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
public class SprMseFacade extends AbstractFacade<SprMse> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprMseFacade() {
        super(SprMse.class);
    }
    
    public List<SprMse> findAllSprMse(){
        TypedQuery<SprMse> query = em.createNamedQuery("SprMse.findAll", SprMse.class);
        List<SprMse> result = query.getResultList();
        return result;
    }
    
    public SprMse findById(Integer id){
        TypedQuery<SprMse> query = em.createNamedQuery("SprMse.findBySprmseId", SprMse.class)
                .setParameter("sprmseId", id);
        SprMse result = query.getSingleResult();
        return result;
    }
}
