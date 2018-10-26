/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.PayDogovor;
import Entities.Payment;
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
public class PaymentFacade extends AbstractFacade<Payment> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PaymentFacade() {
        super(Payment.class);
    }
    
    public List<Payment> findByPayDogovor (PayDogovor paydogId){
        String qlString = "SELECT p FROM Payment p WHERE p.paydogId = :paydogId";
        TypedQuery<Payment> query = em.createQuery(qlString, Payment.class)
                .setParameter("paydogId", paydogId);
        List<Payment> result = query.getResultList();
        return result;
               
    }
    
}
