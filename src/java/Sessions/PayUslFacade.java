/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.PayUsl;
import Entities.SprPayUsl;
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
public class PayUslFacade extends AbstractFacade<PayUsl> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PayUslFacade() {
        super(PayUsl.class);
    }
    
    public List<PayUsl> findByStatus(String status){
        TypedQuery<PayUsl> query = em.createNamedQuery("PayUsl.findByPayuslStatus", PayUsl.class)
                .setParameter("payuslStatus", status);
        List<PayUsl> result = query.getResultList();
        return result;
    }
    
    public PayUsl findById(Integer id){
        PayUsl result = new PayUsl();
        TypedQuery<PayUsl> query = em.createNamedQuery("PayUsl.findByPayuslId", PayUsl.class)
                .setParameter("payuslId", id);
        result = query.getSingleResult();
        return result;
    }
    
    public List<PayUsl> findBySprPayUsl(SprPayUsl sprPayUsl){
        TypedQuery<PayUsl> query = em.createNamedQuery("PayUsl.findBySprpayuslId", PayUsl.class)
                .setParameter("sprpayuslId", sprPayUsl);
        List<PayUsl> result = query.getResultList();
        return result;
    }
}
