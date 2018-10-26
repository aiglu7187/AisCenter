/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Parents;
import Entities.ParentsReg;
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
public class ParentsRegFacade extends AbstractFacade<ParentsReg> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParentsRegFacade() {
        super(ParentsReg.class);
    }
    
    public List<ParentsReg> findByParent(Parents parent){
        TypedQuery query = em.createNamedQuery("ParentsReg.findByParentId", ParentsReg.class)
                .setParameter("parentId", parent);
        List<ParentsReg> result = query.getResultList();
        return result;
    }
    
}
