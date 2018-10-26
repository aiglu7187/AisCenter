/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprEduform;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprEduformFacade extends AbstractFacade<SprEduform> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprEduformFacade() {
        super(SprEduform.class);
    }
    
    public SprEduform findByName(String name){
        TypedQuery<SprEduform> query = em.createNamedQuery("SprEduform.findBySpreduformName", SprEduform.class)
                .setParameter("spreduformName", name);
        SprEduform result = query.getSingleResult();
        return result;        
    }
}
