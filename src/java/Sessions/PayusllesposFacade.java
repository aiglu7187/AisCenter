/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.PayuslClient;
import Entities.PayuslLesson;
import Entities.Payusllespos;
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
public class PayusllesposFacade extends AbstractFacade<Payusllespos> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PayusllesposFacade() {
        super(Payusllespos.class);
    }
    
    public List<Payusllespos> findByPayusllesson(PayuslLesson payusllessonId){
        TypedQuery<Payusllespos> query = em.createNamedQuery("Payusllespos.findByPayusllessonId", Payusllespos.class)
                .setParameter("payusllessonId", payusllessonId);
        List<Payusllespos> result = query.getResultList();
        return result;
    }
    
    public List<Payusllespos> findByPayuslclient(PayuslClient payuslclientId){
        TypedQuery<Payusllespos> query = em.createNamedQuery("Payusllespos.findByPayuslclientId", Payusllespos.class)
                .setParameter("payuslclientId", payuslclientId);
        List<Payusllespos> result = query.getResultList();
        return result;
    }
    
    public Payusllespos findByLessonAndClient(PayuslLesson payusllessonId, PayuslClient payuslclientId){
        String qlString = "SELECT p FROM Payusllespos p WHERE "
                + "p.payusllessonId = :payusllessonId AND "
                + "p.payuslclientId = :payuslclientId ";
        TypedQuery<Payusllespos> query = em.createQuery(qlString, Payusllespos.class)
                .setParameter("payusllessonId", payusllessonId)
                .setParameter("payuslclientId", payuslclientId);
        Payusllespos result = query.getSingleResult();
        return result;        
    }
}
