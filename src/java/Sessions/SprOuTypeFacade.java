/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprOuType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprOuTypeFacade extends AbstractFacade<SprOuType> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprOuTypeFacade() {
        super(SprOuType.class);
    }
    
    public SprOuType findById(Integer id){
        TypedQuery<SprOuType> query = em.createNamedQuery("SprOuType.findBySproutypeId", SprOuType.class)
                .setParameter("sproutypeId", id);
        SprOuType result = query.getSingleResult();
        return result;
    }
    
}
