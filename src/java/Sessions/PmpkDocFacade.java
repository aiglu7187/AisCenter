/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Pmpk;
import Entities.PmpkDoc;
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
public class PmpkDocFacade extends AbstractFacade<PmpkDoc> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmpkDocFacade() {
        super(PmpkDoc.class);
    }
    
    public List<PmpkDoc> findByPmpk(Pmpk pmpk){
        TypedQuery<PmpkDoc> query = em.createNamedQuery("PmpkDoc.findByPmpkId", PmpkDoc.class)
                .setParameter("pmpkId", pmpk);
        List<PmpkDoc> result = query.getResultList();
        return result;
    }
    
}
