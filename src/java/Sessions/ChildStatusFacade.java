/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Count.CountStatus;
import Count.CountStatusDolgn;
import Count.CountStatusUsl;
import Entities.ChildStatus;
import Entities.Children;
import Entities.SprStat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ChildStatusFacade extends AbstractFacade<ChildStatus> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChildStatusFacade() {
        super(ChildStatus.class);
    }

    public List<ChildStatus> findByChild(Children child) {
        TypedQuery<ChildStatus> query = em.createNamedQuery("ChildStatus.findByChildId", ChildStatus.class)
                .setParameter("childId", child);
        List<ChildStatus> result = query.getResultList();
        return result;
    }

    public List<ChildStatus> findByChildAct(Children child) {
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(curDate);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        curDate = calendar.getTime();
        String qlString = "SELECT c FROM ChildStatus c "
                + "WHERE c.childId = :childId AND "
                + "(c.childstatusDateK >= :curDate OR c.childstatusDateK IS NULL) ";
        TypedQuery<ChildStatus> query = em.createQuery(qlString, ChildStatus.class)
                .setParameter("childId", child)
                .setParameter("curDate", curDate);
        List<ChildStatus> result = query.getResultList();
        return result;
    }

    public List<CountStatus> countStatV(Integer statV, Date dateN, Date dateK) {
        String kat = "children";
        String qlString = "SELECT COUNT(DISTINCT c), s.sprstatName "
                + "FROM ChildStatus cs, Children c, SprStat s, Priyom p, PriyomClient pc "
                + "WHERE cs.sprstatId = s "
                + "AND pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND cs.childId = c "
                + "AND s.sprstatV = :statV "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "GROUP BY s.sprstatName "
                + "ORDER BY s.sprstatName";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("statV", statV)
                .setParameter("kat", kat);
        List resultList = query.getResultList();
        List<CountStatus> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatus count = new CountStatus();
            Long l = (Long) o[0];
            count.setCount(l.intValue());
            count.setStatus((String) o[1]);
            result.add(count);
        }
        return result;
    }

    public List<CountStatusUsl> countStatUsl(Date dateN, Date dateK) {
        String kat = "children";
        String qlString = "SELECT COUNT(DISTINCT c), s.sprstatName, u.spruslName "
                + "FROM ChildStatus cs, SprStat s, SprUsl u, Priyom p, Children c, PriyomClient pc "
                + "WHERE cs.sprstatId = s "
                + "AND pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND cs.childId = c "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND p.spruslId = u "
                + "GROUP BY s.sprstatName, u.spruslName "
                + "ORDER BY u.spruslName, s.sprstatName";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("kat", kat);
        List resultList = query.getResultList();
        List<CountStatusUsl> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatusUsl count = new CountStatusUsl();
            Long l = (Long) o[0];
            count.setCount(l.intValue());
            count.setStatus((String) o[1]);
            count.setUsl((String) o[2]);
            result.add(count);
        }
        return result;
    }

    public List<CountStatusUsl> countStatOsnUsl(Date dateN, Date dateK) {
        String kat = "children";
        String qlString = "SELECT COUNT(DISTINCT c), s.sprstatName, ou.sprosnuslName "
                + "FROM ChildStatus cs, SprStat s, SprUsl u, SprOsnusl ou, Priyom p, Children c, PriyomClient pc "
                + "WHERE cs.sprstatId = s AND p.spruslId = u "
                + "AND pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND cs.childId = c "
                + "AND u.sprosnuslId = ou "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "GROUP BY s.sprstatName, ou.sprosnuslName "
                + "ORDER BY ou.sprosnuslName, s.sprstatName";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("kat", kat);
        List resultList = query.getResultList();
        List<CountStatusUsl> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatusUsl count = new CountStatusUsl();
            Long l = (Long) o[0];
            count.setCount(l.intValue());
            count.setStatus((String) o[1]);
            count.setUsl((String) o[2]);
            result.add(count);
        }
        return result;
    }

    public List<CountStatusDolgn> countStatDolgn(Date dateN, Date dateK) {
        String kat = "children";
        String qlString = "SELECT COUNT(DISTINCT c), s.sprstatName, d.sprdolgnName "
                + "FROM ChildStatus cs, SprDolgn d, SprStat s, Priyom p, PriyomSotrud ps, SotrudDolgn sd, SprUsl u, "
                + "PriyomClient pc, Children c "
                + "WHERE cs.sprstatId = s AND ps.priyomId = p AND ps.sotruddolgnId = sd "
                + "AND pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND cs.childId = c "
                + "AND sd.sprdolgnId = d AND p.spruslId = u AND u.spruslId = :usl "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "GROUP BY s.sprstatName, d.sprdolgnName "
                + "ORDER BY d.sprdolgnName, s.sprstatName";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("kat", kat)
                .setParameter("usl", 2);
        List resultList = query.getResultList();
        List<CountStatusDolgn> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatusDolgn count = new CountStatusDolgn();
            Long l = (Long) o[0];
            count.setCount(l.intValue());
            count.setStatus((String) o[1]);
            count.setDolgn((String) o[2]);
            result.add(count);
        }
        return result;
    }

    public List<ChildStatus> findByChildAndStatusActOnDate(Children child, SprStat sprStat, Date date) {
        if (date == null) {
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(curDate);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            curDate = calendar.getTime();
            date = curDate;
        }
        String qlString = "SELECT c FROM ChildStatus c "
                + "WHERE c.childId = :childId AND "
                + "c.sprstatId = :sprstatId AND "
                + "c.childstatusDateN <= :date1 AND "
                + "(c.childstatusDateK >= :date2 OR c.childstatusDateK IS NULL) ";
        TypedQuery<ChildStatus> query = em.createQuery(qlString, ChildStatus.class)
                .setParameter("childId", child)
                .setParameter("sprstatId", sprStat)
                .setParameter("date1", date)
                .setParameter("date2", date);
        List<ChildStatus> result = query.getResultList();
        return result;
    }
    
    public List<ChildStatus> findByChildActOnDate(Children child, Date date) {
        if (date == null) {
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(curDate);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.HOUR, 0);
            curDate = calendar.getTime();
            date = curDate;
        }
        String qlString = "SELECT c FROM ChildStatus c "
                + "WHERE c.childId = :childId AND "
                + "c.childstatusDateN <= :date1 AND "
                + "(c.childstatusDateK >= :date2 OR c.childstatusDateK IS NULL) ";
        TypedQuery<ChildStatus> query = em.createQuery(qlString, ChildStatus.class)
                .setParameter("childId", child)
                .setParameter("date1", date)
                .setParameter("date2", date);
        List<ChildStatus> result = query.getResultList();
        return result;
    }
}
