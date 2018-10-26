/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.PayUsl;
import Entities.PayuslClient;
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
public class PayuslClientFacade extends AbstractFacade<PayuslClient> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PayuslClientFacade() {
        super(PayuslClient.class);
    }
    
    public List<PayuslClient> findByClient(Integer clientId, String kat, String status){
        String qlString = "SELECT p FROM PayuslClient p, PayUsl pu "
                + "WHERE p.clientId = :clientId AND p.payuslclientKatcl = :payuslclientKatcl "
                + "AND p.payuslId = pu AND pu.payuslStatus = :payuslStatus ORDER BY pu.payuslDate ";
        TypedQuery<PayuslClient> query = em.createQuery(qlString, PayuslClient.class)
                .setParameter("clientId", clientId)
                .setParameter("payuslclientKatcl", kat)
                .setParameter("payuslStatus", status);
        List<PayuslClient> result = query.getResultList();
        return result;
    }
    
    public List<PayuslClient> findByPayusl(PayUsl payuslId){
        TypedQuery<PayuslClient> query = em.createNamedQuery("PayuslClient.findByPayuslId", PayuslClient.class)
                .setParameter("payuslId", payuslId);
        List<PayuslClient> result = query.getResultList();
        return result;
    }
    
}
