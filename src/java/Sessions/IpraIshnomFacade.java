/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.IpraIshnom;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class IpraIshnomFacade extends AbstractFacade<IpraIshnom> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IpraIshnomFacade() {
        super(IpraIshnom.class);
    }

    public String getNextNomer() {
        String qlString = "SELECT i.ipraishnomNom, i.ipraishnomSuffix "
                + "FROM IpraIshnom i ";
        Query query = em.createQuery(qlString);
        Object[] sqlResult = (Object[]) query.getSingleResult();
        Integer nom = (Integer) sqlResult[0] + 1;
        String result = nom + (String) sqlResult[1];
        return result;
    }
    
    public IpraIshnom getIpraIshnom(){
        String qlString = "SELECT i FROM IpraIshnom i ";
        TypedQuery<IpraIshnom> query = em.createQuery(qlString, IpraIshnom.class);
        IpraIshnom result = query.getSingleResult();
        return result;
    }

}
