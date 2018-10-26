/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Children;
import Entities.Parents;
import Entities.PayDogovor;
import Entities.PayUsl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class PayDogovorFacade extends AbstractFacade<PayDogovor> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PayDogovorFacade() {
        super(PayDogovor.class);
    }

    public List<PayDogovor> findByChild(Children child) {
        String qlString = "SELECT p FROM PayDogovor p "
                + "WHERE p.childId = :childId ";
        TypedQuery<PayDogovor> query = em.createQuery(qlString, PayDogovor.class)
                .setParameter("childId", child);
        List<PayDogovor> result = query.getResultList();
        return result;
    }

    public List<PayDogovor> findByParent(Parents parent) {
        String qlString = "SELECT p FROM PayDogovor p "
                + "WHERE p.parentId = :parentId ";
        TypedQuery<PayDogovor> query = em.createQuery(qlString, PayDogovor.class)
                .setParameter("parentId", parent);
        List<PayDogovor> result = query.getResultList();
        return result;
    }

    public List<PayDogovor> findByPayUsl(PayUsl payuslId) {
        String qlString = "SELECT p FROM PayDogovor p "
                + "WHERE p.payuslId = :payuslId ";
        TypedQuery<PayDogovor> query = em.createQuery(qlString, PayDogovor.class)
                .setParameter("payuslId", payuslId);
        List<PayDogovor> result = query.getResultList();
        return result;
    }

    public List<PayDogovor> findByPayUslAndChild(PayUsl payuslId, Children childId) {
        String qlString = "SELECT p FROM PayDogovor p "
                + "WHERE p.payuslId = :payuslId AND p.childId = :childId ";
        TypedQuery<PayDogovor> query = em.createQuery(qlString, PayDogovor.class)
                .setParameter("payuslId", payuslId)
                .setParameter("childId", childId);
        List<PayDogovor> result = query.getResultList();
        return result;
    }

    public List<PayDogovor> findBySearch(String nom, Date date, String fam, String name, String patr) {
        String qlString = "SELECT d.PAYDOG_ID FROM Pay_Dogovor d, Parents p "
                + "WHERE d.parent_id = p.parent_id "
                + "AND REPLACE(UPPER(p.parent_fam),?,?) like REPLACE(UPPER(?),?,?) "
                + "AND REPLACE(UPPER(p.parent_name),?,?) like REPLACE(UPPER(?),?,?) "
                + "AND (REPLACE(UPPER(p.parent_patr),?,?) like REPLACE(UPPER(?),?,?) "
                + "OR p.parent_patr IS NULL ) ";
        if (!nom.equals("")) {
            qlString += "AND d.paydog_N = ? ";
        }
        if (date != null) {
            qlString += "AND d.paydog_D = ? ";
        }
        qlString += "ORDER BY p.parent_fam, p.parent_name, p.parent_patr ";

        Query query = em.createNativeQuery(qlString)
                .setParameter(1, "Ё")
                .setParameter(2, "Е")
                .setParameter(3, fam + "%")
                .setParameter(4, "Ё")
                .setParameter(5, "Е")
                .setParameter(6, "Ё")
                .setParameter(7, "Е")
                .setParameter(8, name + "%")
                .setParameter(9, "Ё")
                .setParameter(10, "Е")
                .setParameter(11, "Ё")
                .setParameter(12, "Е")
                .setParameter(13, patr + "%")
                .setParameter(14, "Ё")
                .setParameter(15, "Е");
        if ((!nom.equals(""))&&(date != null)) {
            query.setParameter(16, nom).setParameter(17, date);           
        }
        else if ((!nom.equals(""))&&(date == null)){
            query.setParameter(16, nom);
        }
        else if ((nom.equals(""))&&(date != null)){
            query.setParameter(16, date);
        }
        List resultList = query.getResultList();
        List<PayDogovor> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object o = resultList.get(i);
            BigDecimal d = (BigDecimal) o;
            Long l = d.longValue();
            Integer id = l.intValue();
            PayDogovor payDog = findById(id);
            result.add(payDog);
        }
        return result;
    }
    
    public PayDogovor findById(Integer id){
        TypedQuery<PayDogovor> query = em.createNamedQuery("PayDogovor.findByPaydogId", PayDogovor.class)
                .setParameter("paydogId", id);
        PayDogovor result = query.getSingleResult();
        return result;
    }
}
