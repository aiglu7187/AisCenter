/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Pmpk;
import Entities.PmpkOu;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class PmpkOuFacade extends AbstractFacade<PmpkOu> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmpkOuFacade() {
        super(PmpkOu.class);
    }
    
    public PmpkOu findByPmpk (Pmpk pmpk){
        TypedQuery<PmpkOu> query = em.createNamedQuery("PmpkOu.findByPmpkId", PmpkOu.class)
                .setParameter("pmpkId", pmpk);
        PmpkOu result = query.getSingleResult();
        return result;
    }
}
