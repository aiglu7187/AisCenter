/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.IpraVhnom;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class IpraVhnomFacade extends AbstractFacade<IpraVhnom> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IpraVhnomFacade() {
        super(IpraVhnom.class);
    }
    
    public IpraVhnom getIpraVhnom(){
        String qlString = "SELECT i FROM IpraVhnom i ";
        TypedQuery<IpraVhnom> query = em.createQuery(qlString, IpraVhnom.class);
        IpraVhnom result = query.getSingleResult();
        return result;
    }
}
