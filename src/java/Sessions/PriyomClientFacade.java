/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Count.CountDate;
import Count.CountKatBig;
import Count.CountProblemKod;
import Count.CountStatus;
import Entities.Priyom;
import Entities.PriyomClient;
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
 * @author admin_ai
 */
@Stateless
public class PriyomClientFacade extends AbstractFacade<PriyomClient> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PriyomClientFacade() {
        super(PriyomClient.class);
    }

    public List<PriyomClient> findByPriyom(Priyom priyom) {
        TypedQuery<PriyomClient> query = em.createNamedQuery("PriyomClient.findByPriyomId", PriyomClient.class)
                .setParameter("priyomId", priyom);
        List<PriyomClient> result = query.getResultList();
        return result;
    }

    public List<PriyomClient> findByClient(Integer clientId, String kat) {
        String ql = "SELECT p FROM PriyomClient p, Priyom pr WHERE p.clientId = :clientId AND p.prclKatcl = :prclKatcl "
                + "AND pr = p.priyomId ORDER BY pr.priyomDate";
        TypedQuery<PriyomClient> query = em.createQuery(ql, PriyomClient.class)
                .setParameter("clientId", clientId)
                .setParameter("prclKatcl", kat);
        List<PriyomClient> result = query.getResultList();
        return result;
    }

    public List<CountKatBig> countKatDist(String kat, Date dateN, Date dateK, SprUsl usl, SotrudDolgn sotrudDolgn, Integer age, Integer cent) {
        String client = "";
        String clientId = "";
        if (kat.equals("children")) {
            client = "Children";
            clientId = "childId";
        } else if (kat.equals("parents")) {
            client = "Parents";
            clientId = "parentId";
        } else if (kat.equals("ped")) {
            client = "Ped";
            clientId = "pedId";
        }
        String ql = "";
        if (cent == 1) {
            ql = "SELECT COUNT(DISTINCT pc.clientId), r2.sprregName "
                    + "FROM Priyom p, PriyomClient pc, PriyomSotrud ps, SprRegion r1, SprRegion r2, "
                    + client + " c "
                    + "WHERE p.spruslId = :usl AND p = pc.priyomId "
                    + "AND pc.prclKatcl = :kat AND pc.clientId = c." + clientId + " "
                    + "AND c.sprregId = r2 AND ps.sotruddolgnId = :sotruddolgnId "
                    + "AND p = ps.priyomId AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                    + "AND p.sprregId = r1 AND r1.sprregCenter = :cent ";

            if (age > 0) {
                ql += "AND p.priyomDate - c.childDr >= " + age.toString() + " * 365 ";
            }

            ql += "GROUP BY r2.sprregName";
        } else if (cent == 0) {
            ql = "SELECT COUNT(DISTINCT pc.clientId), r.sprregName "
                    + "FROM Priyom p, PriyomClient pc, PriyomSotrud ps, SprRegion r, "
                    + client + " c "
                    + "WHERE p.spruslId = :usl AND p = pc.priyomId "
                    + "AND pc.prclKatcl = :kat AND pc.clientId = c." + clientId + " "
                    + "AND ps.sotruddolgnId = :sotruddolgnId "
                    + "AND p = ps.priyomId AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                    + "AND p.sprregId = r AND r.sprregCenter = :cent ";

            if (age > 0) {
                ql += "AND p.priyomDate - c.childDr >= " + age.toString() + " * 365 ";
            }

            ql += "GROUP BY r.sprregName";
        }
        Query query = em.createQuery(ql)
                .setParameter("usl", usl)
                .setParameter("kat", kat)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK)
                .setParameter("cent", cent);
        List resultList = query.getResultList();
        List<CountKatBig> result = new ArrayList<>();
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
        return result;
    }

    public List<CountKatBig> countKat(String kat, Date dateN, Date dateK, SprUsl usl, SotrudDolgn sotrudDolgn, Integer age, Integer cent) {
        String client = "";
        String clientId = "";
        if (kat.equals("children")) {
            client = "Children";
            clientId = "childId";
        } else if (kat.equals("parents")) {
            client = "Parents";
            clientId = "parentId";
        } else if (kat.equals("ped")) {
            client = "Ped";
            clientId = "pedId";
        }
        String ql = "";
        if (cent == 1) {
            ql = "SELECT COUNT(pc), r2.sprregName "
                    + "FROM Priyom p, PriyomClient pc, PriyomSotrud ps, SprRegion r1, SprRegion r2, "
                    + client + " c "
                    + "WHERE p.spruslId = :usl AND p = pc.priyomId "
                    + "AND pc.prclKatcl = :kat AND pc.clientId = c." + clientId + " "
                    + "AND c.sprregId = r2 AND ps.sotruddolgnId = :sotruddolgnId "
                    + "AND p = ps.priyomId AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                    + "AND p.sprregId = r1 AND r1.sprregCenter = :cent ";

            if (age > 0) {
                ql += "AND p.priyomDate - c.childDr >= " + age.toString() + " * 365 ";
            }

            ql += "GROUP BY r2.sprregName";
        } else if (cent == 0) {
            ql = "SELECT COUNT(pc), r.sprregName "
                    + "FROM Priyom p, PriyomClient pc, PriyomSotrud ps, SprRegion r, "
                    + client + " c "
                    + "WHERE p.spruslId = :usl AND p = pc.priyomId "
                    + "AND pc.prclKatcl = :kat AND pc.clientId = c." + clientId + " "
                    + "AND ps.sotruddolgnId = :sotruddolgnId "
                    + "AND p = ps.priyomId AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                    + "AND p.sprregId = r AND r.sprregCenter = :cent ";

            if (age > 0) {
                ql += "AND p.priyomDate - c.childDr >= " + age.toString() + " * 365 ";
            }

            ql += "GROUP BY r.sprregName";
        }
        Query query = em.createQuery(ql)
                .setParameter("usl", usl)
                .setParameter("kat", kat)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK)
                .setParameter("cent", cent);
        List resultList = query.getResultList();
        List<CountKatBig> result = new ArrayList<>();
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
        return result;
    }

    public List<CountStatus> countStatVSotrud(Integer statV, Date dateN, Date dateK, SotrudDolgn sotrudDolgn, SprUsl usl, Integer cent) {
        String qlString = "SELECT COUNT(DISTINCT c.childId), s.sprstatName "
                + "FROM ChildStatus cs, SprStat s, Priyom p, PriyomSotrud ps, SprRegion r, PriyomClient pc, Children c "
                + "WHERE cs.sprstatId = s AND cs.childId = c AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND s.sprstatV = :statV "
                + "AND p.spruslId = :usl AND p = ps.priyomId AND ps.sotruddolgnId = :sotruddolgnId "
                + "AND p.sprregId = r AND r.sprregCenter = :cent "
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
                .setParameter("usl", usl)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("cent", cent);
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

    public Integer countAgesSotrud(Date dateN, Date dateK, SotrudDolgn sotrudDolgn, SprUsl usl, Integer ageN, Integer ageK, Integer cent) {
        String ql = "SELECT COUNT(DISTINCT c.childId) "
                + "FROM Children c, Priyom p, PriyomClient pc, PriyomSotrud ps, SprRegion r "
                + "WHERE p = pc.priyomId AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND p = ps.priyomId AND ps.sotruddolgnId = :sotruddolgnId AND p.spruslId = :usl "
                + "AND pc.prclKatcl = :kat AND pc.clientId = c.childId "
                + "AND p.priyomDate - c.childDr >= :ageN * 365 AND p.priyomDate - c.childDr <= :ageK * 365 "
                + "AND p.sprregId = r AND r.sprregCenter = :cent ";
        Query query = em.createQuery(ql)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("usl", usl)
                .setParameter("ageN", ageN)
                .setParameter("ageK", ageK)
                .setParameter("cent", cent)
                .setParameter("kat", "children");
        List resultList = query.getResultList();
        Integer count = 0;
        for (int i = 0; i < resultList.size(); i++) {
            Long l = (Long) resultList.get(i);
            count = l.intValue();
        }
        return count;
    }

    public Integer countUslSotrud(Date dateN, Date dateK, SotrudDolgn sotrudDolgn, SprUsl usl, Integer cent) {
        String ql = "SELECT COUNT(pc) "
                + "FROM Priyom p, PriyomClient pc, PriyomSotrud ps, SprRegion r "
                + "WHERE p = pc.priyomId AND p = ps.priyomId AND ps.sotruddolgnId = :sotruddolgnId "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK AND p.spruslId = :usl "
                + "AND p.sprregId = r AND r.sprregCenter = :cent ";
        Query query = em.createQuery(ql)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("usl", usl)
                .setParameter("cent", cent);
        List resultList = query.getResultList();
        Integer count = 0;
        for (int i = 0; i < resultList.size(); i++) {
            Long l = (Long) resultList.get(i);
            count = l.intValue();
        }
        return count;
    }

    public Integer countUdovlSotrud(Date dateN, Date dateK, SotrudDolgn sotrudDolgn, SprUsl usl, Integer cent) {
        String ql = "SELECT COUNT(pc) "
                + "FROM Priyom p, PriyomClient pc, PriyomSotrud ps, SprRegion r "
                + "WHERE p = pc.priyomId AND p = ps.priyomId AND ps.sotruddolgnId = :sotruddolgnId "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK AND p.spruslId = :usl "
                + "AND (pc.prclUdovl = :one OR pc.prclUdovl is null) "
                + "AND p.sprregId = r AND r.sprregCenter = :cent ";
        Query query = em.createQuery(ql)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("usl", usl)
                .setParameter("cent", cent)
                .setParameter("one", 1);
        List resultList = query.getResultList();
        Integer count = 0;
        for (int i = 0; i < resultList.size(); i++) {
            Long l = (Long) resultList.get(i);
            count = l.intValue();
        }
        return count;
    }

    public Integer countNeudovlSotrud(Date dateN, Date dateK, SotrudDolgn sotrudDolgn, SprUsl usl, Integer cent) {
        String ql = "SELECT COUNT(pc) "
                + "FROM Priyom p, PriyomClient pc, PriyomSotrud ps, SprRegion r "
                + "WHERE p = pc.priyomId AND p = ps.priyomId AND ps.sotruddolgnId = :sotruddolgnId "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK AND p.spruslId = :usl "
                + "AND pc.prclUdovl = :one "
                + "AND p.sprregId = r AND r.sprregCenter = :cent ";
        Query query = em.createQuery(ql)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("usl", usl)
                .setParameter("cent", cent)
                .setParameter("one", 0);
        List resultList = query.getResultList();
        Integer count = 0;
        for (int i = 0; i < resultList.size(); i++) {
            Long l = (Long) resultList.get(i);
            count = l.intValue();
        }
        return count;
    }

    public List<CountProblemKod> countProblemType(Date dateN, Date dateK, SotrudDolgn sotrudDolgn, SprUsl usl, Integer cent) {
        String ql = "SELECT COUNT(pc), pt.sprproblemtypeKod, pt.sprproblemtypeName "
                + "FROM SprProblemType pt, Priyom p, PriyomClient pc, PriyomSotrud ps, "
                + "PriyomProblem pp, SprProblem pr, SprRegion r "
                + "WHERE pc.priyomId = p AND ps.priyomId = p AND pp.priyomId = p AND p.sprregId = r "
                + "AND pp.sprproblemId = pr AND pr.sprproblemtypeId = pt AND ps.sotruddolgnId = :sotruddolgnId "
                + "AND p.spruslId = :usl AND r.sprregCenter = :cent "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "GROUP BY pt.sprproblemtypeKod, pt.sprproblemtypeName ";
        Query query = em.createQuery(ql)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("usl", usl)
                .setParameter("cent", cent);
        List resultList = query.getResultList();
        List<CountProblemKod> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountProblemKod count = new CountProblemKod();
            Long l = (Long) o[0];
            count.setCount(l.intValue());
            count.setType(Boolean.TRUE);
            count.setKod((Integer) o[1]);
            count.setProblem((String) o[2]);
            result.add(count);
        }
        return result;
    }

    public List<CountProblemKod> countProblem(Date dateN, Date dateK, SotrudDolgn sotrudDolgn, SprUsl usl, Integer cent) {
        String ql = "SELECT COUNT(pc), pr.sprproblemKod, pr.sprproblemName "
                + "FROM Priyom p, PriyomClient pc, PriyomSotrud ps, "
                + "PriyomProblem pp, SprProblem pr, SprRegion r "
                + "WHERE pc.priyomId = p AND ps.priyomId = p AND pp.priyomId = p AND p.sprregId = r "
                + "AND pp.sprproblemId = pr AND ps.sotruddolgnId = :sotruddolgnId "
                + "AND p.spruslId = :usl AND r.sprregCenter = :cent "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "GROUP BY  pr.sprproblemKod, pr.sprproblemName "
                + "ORDER BY  pr.sprproblemKod ";
        Query query = em.createQuery(ql)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("usl", usl)
                .setParameter("cent", cent);
        List resultList = query.getResultList();
        List<CountProblemKod> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountProblemKod count = new CountProblemKod();
            Long l = (Long) o[0];
            count.setCount(l.intValue());
            count.setType(Boolean.FALSE);
            count.setKod((Integer) o[1]);
            count.setProblem((String) o[2]);
            result.add(count);
        }
        return result;
    }

    public Integer manyStatusC(Date dateN, Date dateK, SotrudDolgn sotrudDolgn, SprUsl usl, Integer cent) {
        String ql = "SELECT COUNT(cl) "
                + "FROM (SELECT pc.client_id cl, count(DISTINCT s.sprstat_name) n "
                + "FROM priyom_client pc, priyom pr, spr_stat s, child_status cs, priyom_sotrud ps, spr_region r "
                + "WHERE pr.priyom_id = pc.priyom_id AND pc.prcl_katcl = ? "
                + "AND cs.child_id = pc.client_id "
                + "AND cs.sprstat_id = s.sprstat_id AND ps.sotruddolgn_id = ? AND ps.priyom_id = pr.priyom_id "
                + "AND pr.sprusl_id = ? AND r.sprreg_id = pr.sprreg_id AND r.sprreg_center = ? "
                + "AND pr.priyom_date >= ? AND pr.priyom_date <= ? "
                + "AND ((cs.childstatus_date_n >= ? AND cs.childstatus_Date_N <= ?) "
                + "OR (cs.childstatus_Date_N < ? AND cs.childstatus_Date_K >= ?)) "
                + "GROUP BY pc.client_id) "
                + "WHERE n > 1 ";
        Query query = em.createNativeQuery(ql)
                .setParameter(1, "children")
                .setParameter(2, sotrudDolgn.getSotruddolgnId())
                .setParameter(3, usl.getSpruslId())
                .setParameter(4, cent)
                .setParameter(5, dateN)
                .setParameter(6, dateK)
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

    public List<CountDate> countPriyomDate(Date d1, Date d2, SprUsl usl, SotrudDolgn sotrudDolgn, String kat) {
        String ql = "SELECT r.sprregName, p.priyomDate, COUNT(pc.prclId), r.sprregId, p.priyomId "
                + "FROM SprRegion r, Priyom p, PriyomClient pc, PriyomSotrud ps "
                + "WHERE p.sprregId = r AND pc.priyomId = p AND ps.priyomId = p "
                + "AND pc.prclKatcl = :kat AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND ps.sotruddolgnId = :sotruddolgnId AND p.spruslId = :usl "
                + "GROUP BY p.priyomId, r.sprregName, p.priyomDate, r.sprregId "
                + "ORDER BY r.sprregId, p.priyomDate ";
        Query query = em.createQuery(ql)
                .setParameter("kat", kat)
                .setParameter("dateN", d1)
                .setParameter("dateK", d2)
                .setParameter("usl", usl)
                .setParameter("sotruddolgnId", sotrudDolgn);
        List resultList = query.getResultList();
        List<CountDate> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountDate count = new CountDate();
            count.setReg((String) o[0]);
            count.setDate((Date) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountDate> countPriyomDateReg(Date d1, Date d2, SprUsl usl, SotrudDolgn sotrudDolgn, String kat, Integer cent) {
        String ql = "SELECT r.sprregName, p.priyomDate, COUNT(pc.prclId), r.sprregId, p.priyomId "
                + "FROM SprRegion r, Priyom p, PriyomClient pc, PriyomSotrud ps "
                + "WHERE p.sprregId = r AND pc.priyomId = p AND ps.priyomId = p "
                + "AND pc.prclKatcl = :kat AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND ps.sotruddolgnId = :sotruddolgnId AND p.spruslId = :usl AND r.sprregCenter = :cent "
                + "GROUP BY p.priyomId, r.sprregName, p.priyomDate, r.sprregId "
                + "ORDER BY r.sprregId, p.priyomDate ";
        Query query = em.createQuery(ql)
                .setParameter("kat", kat)
                .setParameter("dateN", d1)
                .setParameter("dateK", d2)
                .setParameter("usl", usl)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("cent", cent);
        List resultList = query.getResultList();
        List<CountDate> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountDate count = new CountDate();
            count.setReg((String) o[0]);
            count.setDate((Date) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }
    
    public List<PriyomClient> findByClientAndDate(Integer clientId, String kat, Date date1, Date date2) {
        String ql = "SELECT p FROM PriyomClient p, Priyom pr WHERE p.clientId = :clientId AND p.prclKatcl = :prclKatcl "
                + "AND pr = p.priyomId AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 "
                + "ORDER BY pr.priyomDate";
        TypedQuery<PriyomClient> query = em.createQuery(ql, PriyomClient.class)
                .setParameter("clientId", clientId)
                .setParameter("prclKatcl", kat)
                .setParameter("date1", date1)
                .setParameter("date2", date2);
        List<PriyomClient> result = query.getResultList();
        return result;
    }
}
