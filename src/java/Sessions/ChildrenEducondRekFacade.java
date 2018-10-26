/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.ChildrenEducond;
import Entities.ChildrenEducondRek;
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
public class ChildrenEducondRekFacade extends AbstractFacade<ChildrenEducondRek> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChildrenEducondRekFacade() {
        super(ChildrenEducondRek.class);
    }
    
    public List<ChildrenEducondRek> findByChildreneducond(ChildrenEducond childreneducondId){
        TypedQuery<ChildrenEducondRek> query = em.createNamedQuery("ChildrenEducondRek.findByChildreneducondId", ChildrenEducondRek.class)
                .setParameter("childreneducondId", childreneducondId);
        List<ChildrenEducondRek> result = query.getResultList();
        return result;
    }
}
