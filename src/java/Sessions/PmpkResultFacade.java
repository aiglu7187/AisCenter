/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Pmpk;
import Entities.PmpkResult;
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
public class PmpkResultFacade extends AbstractFacade<PmpkResult> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmpkResultFacade() {
        super(PmpkResult.class);
    }
    
    public List<PmpkResult> findByPmpk(Pmpk pmpk) {
        TypedQuery<PmpkResult> query = em.createNamedQuery("PmpkResult.findByPmpkId", PmpkResult.class)
                .setParameter("pmpkId", pmpk);
        List<PmpkResult> result = query.getResultList();
        return result;
    }
    
}
