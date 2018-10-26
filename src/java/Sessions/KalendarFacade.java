/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Kalendar;
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
public class KalendarFacade extends AbstractFacade<Kalendar> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KalendarFacade() {
        super(Kalendar.class);
    }

    public Date getLastDate() {
        String qlString = "SELECT MAX(k.kalendarDate) FROM Kalendar k";
        Query query = em.createQuery(qlString);
        Date result = null;
        try {
            result = (Date) query.getSingleResult();
        } catch (Exception ex) {
        }
        if (result == null) {
            result = new Date();
        }
        return result;
    }
    
    public List<Kalendar> findAllDaysOfYear(Integer year){
        String qlString = "SELECT k.kalendar_id FROM kalendar k WHERE to_char(k.kalendar_date, 'yyyy') = ? ";
        Query query = em.createNativeQuery(qlString)
                .setParameter(1, year);
        List resultList = query.getResultList();
        List<Kalendar> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++){           
            BigDecimal d = (BigDecimal)resultList.get(i);
            Long l = d.longValue();
            Kalendar k;
            k = findById(l.intValue());
            result.add(k);
        }
        return result;
    }
    
    public Kalendar findById(Integer id){
        TypedQuery<Kalendar> query = em.createNamedQuery("Kalendar.findByKalendarId", Kalendar.class)
                .setParameter("kalendarId", id);
        Kalendar result = query.getSingleResult();
        return result;
    }
    
    public List<Kalendar> findAllDays(){
        TypedQuery<Kalendar> query = em.createNamedQuery("Kalendar.findAll", Kalendar.class);
        List<Kalendar> result = query.getResultList();
        return result;
    }
    
    public List<Kalendar> findAllDaysAfter(Date date){
        String qlString = "SELECT k FROM Kalendar k WHERE k.kalendarDate >= :date ";
        TypedQuery<Kalendar> query = em.createQuery(qlString, Kalendar.class)
                .setParameter("date", date);
        List<Kalendar> result = query.getResultList();
        return result;
    }
}
