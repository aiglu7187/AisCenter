/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.ChildrenView;
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
public class ChildrenViewFacade extends AbstractFacade<ChildrenView> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChildrenViewFacade() {
        super(ChildrenView.class);
    }
    
    public List <ChildrenView> searchChild(String fam, String name, String patr){
        TypedQuery query = em.createNamedQuery("ChildrenView.findByChildFIO", ChildrenView.class)
                .setParameter("fam", fam + "%")
                .setParameter("name", name + "%")
                .setParameter("patr", patr + "%");
        List <ChildrenView> result;
        result = query.getResultList();
        return result;
    }
    
    public List <ChildrenView> searchChildS(String fam, String name, String patr){
        TypedQuery query = em.createNamedQuery("ChildrenView.findByChildFIO", ChildrenView.class)
                .setParameter("fam", fam + "%")
                .setParameter("name", name + "%")
                .setParameter("patr", patr + "%");
        query.setFirstResult(0);
        query.setMaxResults(30);
        List <ChildrenView> result;
        result = query.getResultList();
        return result;
    }
    
}
