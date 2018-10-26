/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.PayUsl;
import Entities.PayuslSotrud;
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
public class PayuslSotrudFacade extends AbstractFacade<PayuslSotrud> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PayuslSotrudFacade() {
        super(PayuslSotrud.class);
    }
    
    public List<PayuslSotrud> findByPayUsl(PayUsl payUsl){
        TypedQuery<PayuslSotrud> query = em.createNamedQuery("PayuslSotrud.findByPayuslId",PayuslSotrud.class)
                .setParameter("payuslId", payUsl);
        List<PayuslSotrud> result = query.getResultList();
        return result;
    }
}
