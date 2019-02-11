/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Pmpk;
import Entities.PmpkReason;
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
public class PmpkReasonFacade extends AbstractFacade<PmpkReason> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmpkReasonFacade() {
        super(PmpkReason.class);
    }
    
    public List<PmpkReason> findByPmpk(Pmpk pmpk){
        TypedQuery<PmpkReason> query = em.createNamedQuery("PmpkReason.findByPmpkId", PmpkReason.class)
                .setParameter("pmpkId", pmpk);
        List<PmpkReason> result = query.getResultList();
        return result;
    }
    
}
