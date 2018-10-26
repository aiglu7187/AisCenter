/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.CenterName;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class CenterNameFacade extends AbstractFacade<CenterName> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CenterNameFacade() {
        super(CenterName.class);
    }
    
    public CenterName findById (Integer id){
        TypedQuery<CenterName> query = em.createNamedQuery("CenterName.findById", CenterName.class)
                .setParameter("id", id);
        CenterName result = query.getSingleResult();
        return result;
    }
    
}
