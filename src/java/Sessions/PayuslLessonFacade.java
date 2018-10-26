/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.PayUsl;
import Entities.PayuslLesson;
import javax.ejb.Stateless;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class PayuslLessonFacade extends AbstractFacade<PayuslLesson> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PayuslLessonFacade() {
        super(PayuslLesson.class);
    }
    
    public List<PayuslLesson> findByPayUsl(PayUsl payuslId){
        TypedQuery<PayuslLesson> query = em.createNamedQuery("PayuslLesson.findByPayuslId", PayuslLesson.class)
                .setParameter("payuslId", payuslId);
        List<PayuslLesson> result = query.getResultList();
        return result;
    }
    
    public PayuslLesson findById(Integer id){
        TypedQuery<PayuslLesson> query = em.createNamedQuery("PayuslLesson.findByPayusllessonId", PayuslLesson.class)
                .setParameter("payusllessonId", id);
        PayuslLesson result = query.getSingleResult();
        return result;
    }
    
}
