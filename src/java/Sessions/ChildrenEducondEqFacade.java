/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.ChildrenEducond;
import Entities.ChildrenEducondEq;
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
public class ChildrenEducondEqFacade extends AbstractFacade<ChildrenEducondEq> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChildrenEducondEqFacade() {
        super(ChildrenEducondEq.class);
    }
    
    public List<ChildrenEducondEq> findByChildreneducond(ChildrenEducond childreneducondId){
        TypedQuery<ChildrenEducondEq> query = em.createNamedQuery("ChildrenEducondEq.findByChildreneducondId", ChildrenEducondEq.class)
                .setParameter("childreneducondId", childreneducondId);
        List<ChildrenEducondEq> result = query.getResultList();
        return result;
    }
}
