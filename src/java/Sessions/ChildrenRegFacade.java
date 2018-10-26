/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Children;
import Entities.ChildrenReg;
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
public class ChildrenRegFacade extends AbstractFacade<ChildrenReg> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChildrenRegFacade() {
        super(ChildrenReg.class);
    }
    
    public List<ChildrenReg> findByChild(Children child){
        TypedQuery query = em.createNamedQuery("ChildrenReg.findByChildId", ChildrenReg.class)
                .setParameter("childId", child);
        List<ChildrenReg> result = query.getResultList();
        return result;
    }
}
