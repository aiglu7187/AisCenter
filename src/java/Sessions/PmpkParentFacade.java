/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Parents;
import Entities.Pmpk;
import Entities.PmpkParent;
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
public class PmpkParentFacade extends AbstractFacade<PmpkParent> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmpkParentFacade() {
        super(PmpkParent.class);
    }
    
    public List<PmpkParent> findByParent (Parents parent){
        TypedQuery<PmpkParent> query = em.createNamedQuery("PmpkParent.findByParentId", PmpkParent.class)
                .setParameter("parentId", parent);
        List<PmpkParent> result = query.getResultList();
        return result;
    }
    
    public List<PmpkParent> findByPmpk (Pmpk pmpk){
        TypedQuery<PmpkParent> query = em.createNamedQuery("PmpkParent.findByPmpkId", PmpkParent.class)
                .setParameter("pmpkId", pmpk);
        List<PmpkParent> result = query.getResultList();
        return result;
    }
}
