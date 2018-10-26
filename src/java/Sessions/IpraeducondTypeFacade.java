/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.IpraeducondType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class IpraeducondTypeFacade extends AbstractFacade<IpraeducondType> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IpraeducondTypeFacade() {
        super(IpraeducondType.class);
    }
    
    public IpraeducondType findById (Integer id){
        TypedQuery<IpraeducondType> query = em.createNamedQuery("IpraeducondType.findByIpraeducondtypeId", IpraeducondType.class)
                .setParameter("ipraeducondtypeId", id);
        IpraeducondType result = query.getSingleResult();
        return result;                
    }
}
