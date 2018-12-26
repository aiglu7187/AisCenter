/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Count.CountKatBig;
import Entities.Children;
import Entities.Priyom;
import Entities.PriyomSubject;
import Entities.Sotrud;
import Entities.SotrudDolgn;
import Entities.SprUsl;
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
public class PriyomSubjectFacade extends AbstractFacade<PriyomSubject> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PriyomSubjectFacade() {
        super(PriyomSubject.class);
    }

    public List<PriyomSubject> findByPriyom(Priyom priyom) {
        TypedQuery<PriyomSubject> query = em.createNamedQuery("PriyomSubject.findByPriyomId", PriyomSubject.class)
                .setParameter("priyomId", priyom);
        List<PriyomSubject> result = query.getResultList();
        return result;
    }

    public List<PriyomSubject> findBySubject(Children childId) {
        String ql = "SELECT p FROM PriyomSubject p, Priyom pr WHERE p.childId = :childId "
                + "AND pr = p.priyomId ORDER BY pr.priyomDate";
        TypedQuery<PriyomSubject> query = em.createQuery(ql, PriyomSubject.class)
                .setParameter("childId", childId);
        List<PriyomSubject> result = query.getResultList();
        return result;
    }

    public List<CountKatBig> countKat(String kat, Date dateN, Date dateK, SprUsl usl, Sotrud sotrud, Integer age, Integer cent) {
        String client = "";
        List<CountKatBig> result = new ArrayList<>();
        if (kat.equals("children")) {
            client = "Children";
            String ql = "";
            if (cent == 1) {
                ql = "SELECT COUNT(ps), r2.sprregName "
                        + "FROM Priyom p, PriyomSubject psub, PriyomSotrud ps, SprRegion r1, SprRegion r2, "
                        + client + " c "
                        + "WHERE p.spruslId = :usl AND p = psub.priyomId "
                        + "AND psub.childId = c "
                        + "AND c.sprregId = r2 AND ps.sotrudId = :sotrud "
                        + "AND p = ps.priyomId AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                        + "AND p.sprregId = r1 AND r1.sprregCenter = :cent ";

                if (age > 0) {
                    ql += "AND p.priyomDate - c.childDr >= " + age.toString() + " * 365 ";
                }

                ql += "GROUP BY r2.sprregName";
            } else if (cent == 0) {
                ql = "SELECT COUNT(ps), r.sprregName "
                        + "FROM Priyom p, PriyomSubject psub, PriyomSotrud ps, SprRegion r, "
                        + client + " c "
                        + "WHERE p.spruslId = :usl AND p = psub.priyomId "
                        + "AND psub.childId = c "
                        + "AND ps.sotrudId = :sotrud "
                        + "AND p = ps.priyomId AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                        + "AND p.sprregId = r AND r.sprregCenter = :cent ";

                if (age > 0) {
                    ql += "AND p.priyomDate - c.childDr >= " + age.toString() + " * 365 ";
                }

                ql += "GROUP BY r.sprregName";
            }
            Query query = em.createQuery(ql)
                    .setParameter("usl", usl)
                    .setParameter("sotrud", sotrud)
                    .setParameter("dateN", dateN)
                    .setParameter("dateK", dateK)
                    .setParameter("cent", cent);
            List resultList = query.getResultList();

            for (int i = 0; i < resultList.size(); i++) {
                Object[] o = (Object[]) resultList.get(i);
                CountKatBig count = new CountKatBig();
                Long l = (Long) o[0];
                count.setCount(l.intValue());
                if (age == 0) {
                    count.setKat(kat);
                } else if (age > 0) {
                    count.setKat(kat + age.toString());
                }
                count.setReg((String) o[1]);
                result.add(count);
            }
        }
        return result;
    }

    public Integer manyStatusS(Date dateN, Date dateK, SotrudDolgn sotrudDolgn, SprUsl usl, Integer cent) {
        String ql = "SELECT COUNT(sub) "
                + "FROM (SELECT psub.child_id sub, count(DISTINCT s.sprstat_name) n "
                + "FROM priyom_subject psub, priyom pr, spr_stat s, child_status cs, priyom_sotrud ps, spr_region r "
                + "WHERE pr.priyom_id = psub.priyom_id "
                + "AND cs.child_id = psub.child_id "
                + "AND cs.sprstat_id = s.sprstat_id AND ps.sotruddolgn_id = ? AND ps.priyom_id = pr.priyom_id "
                + "AND pr.sprusl_id = ? AND r.sprreg_id = pr.sprreg_id AND r.sprreg_center = ? "
                + "AND pr.priyom_date >= ? AND pr.priyom_date <= ? "
                + "AND ((cs.childstatus_date_n >= ? AND cs.childstatus_Date_N <= ?) "
                + "OR (cs.childstatus_Date_N < ? AND cs.childstatus_Date_K >= ?)) "
                + "GROUP BY psub.child_id) "
                + "WHERE n > 1 ";
        Query query = em.createNativeQuery(ql)
                .setParameter(1, sotrudDolgn.getSotruddolgnId())
                .setParameter(2, usl.getSpruslId())
                .setParameter(3, cent)
                .setParameter(4, dateN)
                .setParameter(5, dateK)
                .setParameter(7, dateN)
                .setParameter(8, dateK)
                .setParameter(9, dateN)
                .setParameter(10, dateN);

        List resultList = query.getResultList();
        Integer count = 0;
        for (int i = 0; i < resultList.size(); i++) {
            BigDecimal d = (BigDecimal) resultList.get(i);
            Long l = d.longValue();
            count = l.intValue();
        }
        return count;
    }

    public Integer countAgesSotrud(Date dateN, Date dateK, SotrudDolgn sotrudDolgn, SprUsl usl, Integer ageN, Integer ageK, Integer cent) {
        String ql = "SELECT COUNT(c.childId) "
                + "FROM Children c, Priyom p, PriyomSubject psub, PriyomSotrud ps, SprRegion r "
                + "WHERE p = psub.priyomId AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND p = ps.priyomId AND ps.sotruddolgnId = :sotruddolgnId AND p.spruslId = :usl "
                + "AND psub.childId = c "
                + "AND p.priyomDate - c.childDr >= :ageN * 365 AND p.priyomDate - c.childDr <= :ageK * 365 "
                + "AND p.sprregId = r AND r.sprregCenter = :cent ";
        Query query = em.createQuery(ql)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("usl", usl)
                .setParameter("ageN", ageN)
                .setParameter("ageK", ageK)
                .setParameter("cent", cent);
        List resultList = query.getResultList();
        Integer count = 0;
        for (int i = 0; i < resultList.size(); i++) {
            Long l = (Long) resultList.get(i);
            count = l.intValue();
        }
        return count;
    }
    
    public List<PriyomSubject> findBySubjectAndDate(Children childId, Date date1, Date date2) {
        String ql = "SELECT p FROM PriyomSubject p, Priyom pr WHERE p.childId = :childId "
                + "AND pr = p.priyomId AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 "
                + "ORDER BY pr.priyomDate";
        TypedQuery<PriyomSubject> query = em.createQuery(ql, PriyomSubject.class)
                .setParameter("childId", childId)
                .setParameter("date1", date1)
                .setParameter("date2", date2);
        List<PriyomSubject> result = query.getResultList();
        return result;
    }
}
