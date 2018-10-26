/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Children;
import Entities.ChildrenEducond;
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
public class ChildrenEducondFacade extends AbstractFacade<ChildrenEducond> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChildrenEducondFacade() {
        super(ChildrenEducond.class);
    }
    
    public List<ChildrenEducond> findByChild (Children child){
        TypedQuery<ChildrenEducond> query = em.createNamedQuery("ChildrenEducond.findByChildId", ChildrenEducond.class)
                .setParameter("childId", child);
        List<ChildrenEducond> result = query.getResultList();
        return result;
    }
    
}
