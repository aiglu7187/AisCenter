/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprParentType;
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
public class SprParentTypeFacade extends AbstractFacade<SprParentType> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprParentTypeFacade() {
        super(SprParentType.class);
    }
    
    public List<SprParentType> findAllSprParentType(){
        TypedQuery<SprParentType> query = em.createNamedQuery("SprParentType.findAll", SprParentType.class);
        List<SprParentType> result = query.getResultList();
        return result;
    }
    
    public SprParentType findById(Integer id){
        TypedQuery<SprParentType> query = em.createNamedQuery("SprParentType.findBySprparenttypeId", SprParentType.class)
                .setParameter("sprparenttypeId", id);
        SprParentType result = query.getSingleResult();
        return result;
    }
    
}
