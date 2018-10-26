/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.ParentsView;
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
public class ParentsViewFacade extends AbstractFacade<ParentsView> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParentsViewFacade() {
        super(ParentsView.class);
    }
    
    public List <ParentsView> searchParent(String fam, String name, String patr){
        TypedQuery query = em.createNamedQuery("ParentsView.findByParentFIO", ParentsView.class)
                .setParameter("fam", fam + "%")
                .setParameter("name", name + "%")
                .setParameter("patr", patr + "%");
        List <ParentsView> result;
        result = query.getResultList();
        return result;
    }
    
    public List <ParentsView> searchParentS(String fam, String name, String patr){
        TypedQuery query = em.createNamedQuery("ParentsView.findByParentFIO", ParentsView.class)
                .setParameter("fam", fam + "%")
                .setParameter("name", name + "%")
                .setParameter("patr", patr + "%");
        query.setFirstResult(0);
        query.setMaxResults(30);
        List <ParentsView> result;
        result = query.getResultList();
        return result;
    }
}
