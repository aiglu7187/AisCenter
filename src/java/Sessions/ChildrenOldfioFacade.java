/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Children;
import Entities.ChildrenOldfio;
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
public class ChildrenOldfioFacade extends AbstractFacade<ChildrenOldfio> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChildrenOldfioFacade() {
        super(ChildrenOldfio.class);
    }
    
    public List<ChildrenOldfio> findByChildId (Children childId){
        TypedQuery<ChildrenOldfio> query = em.createNamedQuery("ChildrenOldfio.findByChildId", ChildrenOldfio.class)
                .setParameter("childId", childId);
        List<ChildrenOldfio> result = query.getResultList();
        return result;
    }
}
