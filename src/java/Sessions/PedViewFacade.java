/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.PedView;
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
public class PedViewFacade extends AbstractFacade<PedView> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedViewFacade() {
        super(PedView.class);
    }
    
    public List <PedView> searchPed(String fam, String name, String patr){
        TypedQuery query = em.createNamedQuery("PedView.findByPedFIO", PedView.class)
                .setParameter("fam", fam + "%")
                .setParameter("name", name + "%")
                .setParameter("patr", patr + "%");
        List <PedView> result;
        result = query.getResultList();
        return result;
    }
 
    public List <PedView> searchPedS(String fam, String name, String patr){
        TypedQuery query = em.createNamedQuery("PedView.findByPedFIO", PedView.class)
                .setParameter("fam", fam + "%")
                .setParameter("name", name + "%")
                .setParameter("patr", patr + "%");
        query.setFirstResult(0);
        query.setMaxResults(30);
        List <PedView> result;
        result = query.getResultList();
        return result;
    }
    
}
