/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprObrType;
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
public class SprObrTypeFacade extends AbstractFacade<SprObrType> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprObrTypeFacade() {
        super(SprObrType.class);
    }
    
    public SprObrType findBySprObrTypeId(Integer id){
        TypedQuery <SprObrType> query = em.createNamedQuery("SprObrType.findBySprobrtypeId", SprObrType.class)
                .setParameter("sprobrtypeId", id);
        SprObrType result = query.getSingleResult();
        return result;
    }
    
    public List<SprObrType> findAllSprObrType(){
        TypedQuery<SprObrType> query = em.createNamedQuery("SprObrType.findAll", SprObrType.class);
        List<SprObrType> result = query.getResultList();
        return result;
    }
    
}
