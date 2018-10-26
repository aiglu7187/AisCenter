/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprPos;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprPosFacade extends AbstractFacade<SprPos> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprPosFacade() {
        super(SprPos.class);
    }
    
    public SprPos findById(Integer id){
        TypedQuery<SprPos> query = em.createNamedQuery("SprPos.findBySprposId", SprPos.class)
                .setParameter("sprposId", id);
        SprPos result = query.getSingleResult();
        return result;
    }
}
