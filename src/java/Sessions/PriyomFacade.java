/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Count.CountClient;
import Count.CountKatPriyom;
import Count.CountPriyom;
import Count.CountStatPmpk;
import Count.CountStatusReg;
import Entities.Children;
import Entities.Parents;
import Entities.Ped;
import Entities.Priyom;
import Entities.Sotrud;
import Entities.SotrudDolgn;
import Entities.SprOsnusl;
import Entities.SprRegion;
import Entities.SprUsl;
import Reestr.PMPKR;
import Reestr.PMPKStatus;
import Reestr.PMPKTer;
import Reestr.ReestrMonitPMPK;
import Reestr.ReestrPMPK;
import Reestr.ReestrPMPKFull;
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
public class PriyomFacade extends AbstractFacade<Priyom> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PriyomFacade() {
        super(Priyom.class);
    }

    public List<Priyom> isPriyom(Date datePr, SprUsl sprUsl) {
        String qlString = "SELECT p FROM Priyom p, SprUsl u WHERE p.priyomDate = :datePr AND "
                + "u = p.spruslId AND u = :sprUsl ";
        TypedQuery<Priyom> query = em.createQuery(qlString, Priyom.class)
                .setParameter("datePr", datePr)
                .setParameter("sprUsl", sprUsl);
        List<Priyom> result = query.getResultList();
        return result;
    }

    public List<Priyom> findPriyom(Date datePr1, Date datePr2, SprUsl sprUsl, SprRegion sprReg) {
        TypedQuery<Priyom> query;
        if ((datePr1 == null) && (datePr2 == null)) {
            if (sprUsl == null) {
                if (sprReg == null) {
                    query = em.createNamedQuery("Priyom.findAll", Priyom.class);
                } else {
                    query = em.createNamedQuery("Priyom.findBySprregId", Priyom.class)
                            .setParameter("sprregId", sprReg);
                }
            } else if (sprReg == null) {
                query = em.createNamedQuery("Priyom.findBySpruslId", Priyom.class)
                        .setParameter("spruslId", sprUsl);
            } else {
                query = em.createNamedQuery("Priyom.findBySprregAndSprUsl", Priyom.class)
                        .setParameter("spruslId", sprUsl)
                        .setParameter("sprregId", sprReg);
            }
        } else if (sprUsl == null) {
            if (sprReg == null) {
                query = em.createNamedQuery("Priyom.findByPriyomDatePeriod", Priyom.class)
                        .setParameter("priyomDate1", datePr1)
                        .setParameter("priyomDate2", datePr2);
            } else {
                query = em.createNamedQuery("Priyom.findBySprregAndDate", Priyom.class)
                        .setParameter("sprregId", sprReg)
                        .setParameter("priyomDate1", datePr1)
                        .setParameter("priyomDate2", datePr2);
            }
        } else if (sprReg == null) {
            query = em.createNamedQuery("Priyom.findBySpruslAndDate", Priyom.class)
                    .setParameter("spruslId", sprUsl)
                    .setParameter("priyomDate1", datePr1)
                    .setParameter("priyomDate2", datePr2);
        } else {
            query = em.createNamedQuery("Priyom.findPriyom", Priyom.class)
                    .setParameter("priyomDate1", datePr1)
                    .setParameter("priyomDate2", datePr2)
                    .setParameter("spruslId", sprUsl)
                    .setParameter("sprregId", sprReg);
        }

        List<Priyom> result = query.getResultList();
        return result;
    }

    public Priyom findById(Integer id) {
        TypedQuery<Priyom> query = em.createNamedQuery("Priyom.findByPriyomId", Priyom.class)
                .setParameter("priyomId", id);
        Priyom result = query.getSingleResult();
        return result;
    }

    public List<CountPriyom> countPriyomSotrudC(SotrudDolgn sotrudDolgn, Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, u.spruslName, COUNT(p.priyomId) "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, PriyomSotrud s, SprRegion r "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u "
                + "AND s.priyomId = p AND s.sotruddolgnId = :sotruddolgnId "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 1 "
                + "GROUP BY o.sprosnuslName, u.spruslName ";
        Query query = em.createQuery(qlString)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountPriyom> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountPriyom count = new CountPriyom();
            count.setOsnUsl((String) o[0]);
            count.setUsl((String) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountPriyom> countPriyomSotrud(SotrudDolgn sotrudDolgn, Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, u.spruslName, COUNT(p.priyomId) "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, PriyomSotrud s, SprRegion r  "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u "
                + "AND s.priyomId = p AND s.sotruddolgnId = :sotruddolgnId "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 0 "
                + "GROUP BY o.sprosnuslName, u.spruslName";
        TypedQuery<CountPriyom> query = em.createQuery(qlString, CountPriyom.class)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountPriyom> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountPriyom count = new CountPriyom();
            count.setOsnUsl((String) o[0]);
            count.setUsl((String) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountClient> countClientSotrudC(SotrudDolgn sotrudDolgn, Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, u.spruslName, COUNT(DISTINCT c.clientId), c.prclKatcl "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, PriyomSotrud s, SprRegion r, PriyomClient c "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u "
                + "AND s.priyomId = p AND s.sotruddolgnId  = :sotruddolgnId "
                + "AND c.priyomId = p "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 1 "
                + "GROUP BY o.sprosnuslName, u.spruslName, c.prclKatcl ";
        TypedQuery<CountClient> query = em.createQuery(qlString, CountClient.class)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountClient> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountClient count = new CountClient();
            count.setOsnUsl((String) o[0]);
            count.setUsl((String) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            count.setKatClient((String) o[3]);
            result.add(count);
        }
        return result;
    }

    public List<CountClient> countClientSotrud(SotrudDolgn sotrudDolgn, Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, u.spruslName, COUNT(DISTINCT c.clientId), c.prclKatcl "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, PriyomSotrud s, SprRegion r, PriyomClient c "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u "
                + "AND s.priyomId = p AND s.sotruddolgnId = :sotruddolgnId "
                + "AND c.priyomId = p "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 0 "
                + "GROUP BY o.sprosnuslName, u.spruslName, c.prclKatcl ";
        TypedQuery<CountClient> query = em.createQuery(qlString, CountClient.class)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountClient> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountClient count = new CountClient();
            count.setOsnUsl((String) o[0]);
            count.setUsl((String) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            count.setKatClient((String) o[3]);
            result.add(count);
        }
        return result;
    }

    public List<CountKatPriyom> countKatPriyom(SotrudDolgn sotrudDolgn, Date dateN, Date dateK) {
        String qlString = "SELECT COUNT(p.priyomId), c.prclKatcl "
                + "FROM Priyom p, PriyomSotrud s, PriyomClient c "
                + "WHERE s.priyomId = p AND s.sotruddolgnId = :sotruddolgnId "
                + "AND c.priyomId = p "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "GROUP BY c.prclKatcl ";
        TypedQuery<CountClient> query = em.createQuery(qlString, CountClient.class)
                .setParameter("sotruddolgnId", sotrudDolgn)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountKatPriyom> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountKatPriyom count = new CountKatPriyom();
            Long l = (Long) o[0];
            count.setCount(l.intValue());
            count.setKatClient((String) o[1]);
            result.add(count);
        }
        return result;
    }

    public List<Children> findChildrenPriyom(SprOsnusl osnusl, SprUsl usl, SprRegion reg, SprRegion regcl, Date datePr1, Date datePr2) {
        String ql = "SELECT DISTINCT c FROM Priyom p, PriyomClient pc, Children c, SprUsl u "
                + "WHERE pc.priyomId = p AND pc.prclKatcl = :prclKatcl AND pc.clientId = c.childId "
                + "AND p.spruslId = u AND u.sprosnuslId = :osnusl ";
        if (usl != null) {
            ql += "AND u = :usl ";
        }
        if (reg != null) {
            ql += "AND p.sprregId = :reg ";
        }
        if (regcl != null) {
            ql += "AND c.sprregId = :regcl ";
        }
        if ((datePr1 != null) && (datePr2 != null)) {
            ql += "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        }

        ql += "ORDER BY c.childFam, c.childName, c.childPatr ";

        TypedQuery<Children> query = em.createQuery(ql, Children.class)
                .setParameter("prclKatcl", "children")
                .setParameter("osnusl", osnusl);
        if (usl != null) {
            query.setParameter("usl", usl);
        }
        if (reg != null) {
            query.setParameter("reg", reg);
        }
        if (regcl != null) {
            query.setParameter("regcl", regcl);
        }
        if ((datePr1 != null) && (datePr2 != null)) {
            query.setParameter("date1", datePr1);
            query.setParameter("date2", datePr2);
        }

        List<Children> result = query.getResultList();
        return result;
    }

    public List<Parents> findParentsPriyom(SprOsnusl osnusl, SprUsl usl, SprRegion reg, SprRegion regcl, Date datePr1, Date datePr2) {
        String ql = "SELECT DISTINCT c FROM Priyom p, PriyomClient pc, Parents c, SprUsl u "
                + "WHERE pc.priyomId = p AND pc.prclKatcl = :prclKatcl AND pc.clientId = c.parentId "
                + "AND p.spruslId = u AND u.sprosnuslId = :osnusl ";
        if (usl != null) {
            ql += "AND u = :usl ";
        }
        if (reg != null) {
            ql += "AND p.sprregId = :reg ";
        }
        if (regcl != null) {
            ql += "AND c.sprregId = :regcl ";
        }
        if ((datePr1 != null) && (datePr2 != null)) {
            ql += "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        }

        ql += "ORDER BY c.parentFam, c.parentName, c.parentPatr ";

        TypedQuery<Parents> query = em.createQuery(ql, Parents.class)
                .setParameter("prclKatcl", "parents")
                .setParameter("osnusl", osnusl);
        if (usl != null) {
            query.setParameter("usl", usl);
        }
        if (reg != null) {
            query.setParameter("reg", reg);
        }
        if (regcl != null) {
            query.setParameter("regcl", regcl);
        }
        if ((datePr1 != null) && (datePr2 != null)) {
            query.setParameter("date1", datePr1);
            query.setParameter("date2", datePr2);
        }

        List<Parents> result = query.getResultList();
        return result;
    }

    public List<Ped> findPedPriyom(SprOsnusl osnusl, SprUsl usl, SprRegion reg, SprRegion regcl, Date datePr1, Date datePr2) {
        String ql = "SELECT DISTINCT c FROM Priyom p, PriyomClient pc, Ped c, SprUsl u "
                + "WHERE pc.priyomId = p AND pc.prclKatcl = :prclKatcl AND pc.clientId = c.pedId "
                + "AND p.spruslId = u AND u.sprosnuslId = :osnusl ";
        if (usl != null) {
            ql += "AND u = :usl ";
        }
        if (reg != null) {
            ql += "AND p.sprregId = :reg ";
        }
        if (regcl != null) {
            ql += "AND c.sprregId = :regcl ";
        }
        if ((datePr1 != null) && (datePr2 != null)) {
            ql += "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        }

        ql += "ORDER BY c.pedFam, c.pedName, c.pedPatr";

        TypedQuery<Ped> query = em.createQuery(ql, Ped.class)
                .setParameter("prclKatcl", "ped")
                .setParameter("osnusl", osnusl);
        if (usl != null) {
            query.setParameter("usl", usl);
        }
        if (reg != null) {
            query.setParameter("reg", reg);
        }
        if (regcl != null) {
            query.setParameter("regcl", regcl);
        }
        if ((datePr1 != null) && (datePr2 != null)) {
            query.setParameter("date1", datePr1);
            query.setParameter("date2", datePr2);
        }

        List<Ped> result = query.getResultList();
        return result;
    }

    public List<CountPriyom> countPriyomC(Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, u.spruslName, COUNT(p.priyomId) "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, SprRegion r "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 1 "
                + "GROUP BY o.sprosnuslName, u.spruslName ";
        Query query = em.createQuery(qlString)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountPriyom> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountPriyom count = new CountPriyom();
            count.setOsnUsl((String) o[0]);
            count.setUsl((String) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountPriyom> countPriyom(Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, u.spruslName, COUNT(p.priyomId) "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, SprRegion r  "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 0 "
                + "GROUP BY o.sprosnuslName, u.spruslName";
        TypedQuery<CountPriyom> query = em.createQuery(qlString, CountPriyom.class)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountPriyom> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountPriyom count = new CountPriyom();
            count.setOsnUsl((String) o[0]);
            count.setUsl((String) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountClient> countClientC(Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, u.spruslName, COUNT(DISTINCT c.clientId), c.prclKatcl "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, SprRegion r, PriyomClient c "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u "
                + "AND c.priyomId = p "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 1 "
                + "GROUP BY o.sprosnuslName, u.spruslName, c.prclKatcl ";
        TypedQuery<CountClient> query = em.createQuery(qlString, CountClient.class)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountClient> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountClient count = new CountClient();
            count.setOsnUsl((String) o[0]);
            count.setUsl((String) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            count.setKatClient((String) o[3]);
            result.add(count);
        }
        return result;
    }

    public List<CountClient> countOsnClientC(Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, COUNT(DISTINCT c.clientId), c.prclKatcl "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, SprRegion r, PriyomClient c "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u "
                + "AND c.priyomId = p "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 1 "
                + "GROUP BY o.sprosnuslName, c.prclKatcl ";
        TypedQuery<CountClient> query = em.createQuery(qlString, CountClient.class)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountClient> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountClient count = new CountClient();
            count.setOsnUsl((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            count.setKatClient((String) o[2]);
            result.add(count);
        }
        return result;
    }

    public List<CountClient> countClient(Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, u.spruslName, COUNT(DISTINCT c.clientId), c.prclKatcl "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, SprRegion r, PriyomClient c "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u "
                + "AND c.priyomId = p "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 0 "
                + "GROUP BY o.sprosnuslName, u.spruslName, c.prclKatcl ";
        TypedQuery<CountClient> query = em.createQuery(qlString, CountClient.class)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountClient> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountClient count = new CountClient();
            count.setOsnUsl((String) o[0]);
            count.setUsl((String) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            count.setKatClient((String) o[3]);
            result.add(count);
        }
        return result;
    }

    public List<CountClient> countOsnClient(Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, COUNT(DISTINCT c.clientId), c.prclKatcl "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, SprRegion r, PriyomClient c "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u "
                + "AND c.priyomId = p "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 0 "
                + "GROUP BY o.sprosnuslName, c.prclKatcl ";
        TypedQuery<CountClient> query = em.createQuery(qlString, CountClient.class)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountClient> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountClient count = new CountClient();
            count.setOsnUsl((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            count.setKatClient((String) o[2]);
            result.add(count);
        }
        return result;
    }

    public List<CountPriyom> countPrCl(Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, u.spruslName, COUNT(c.prclId) "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, SprRegion r, PriyomClient c  "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u AND c.priyomId = p "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 0 "
                + "GROUP BY o.sprosnuslName, u.spruslName";
        TypedQuery<CountPriyom> query = em.createQuery(qlString, CountPriyom.class)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountPriyom> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountPriyom count = new CountPriyom();
            count.setOsnUsl((String) o[0]);
            count.setUsl((String) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountPriyom> countPrClC(Date dateN, Date dateK) {
        String qlString = "SELECT o.sprosnuslName, u.spruslName, COUNT(c.prclId) "
                + "FROM SprOsnusl o, SprUsl u, Priyom p, SprRegion r, PriyomClient c  "
                + "WHERE o = u.sprosnuslId AND p.spruslId = u AND c.priyomId = p "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 "
                + "AND p.sprregId = r and r.sprregCenter = 1 "
                + "GROUP BY o.sprosnuslName, u.spruslName";
        TypedQuery<CountPriyom> query = em.createQuery(qlString, CountPriyom.class)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountPriyom> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountPriyom count = new CountPriyom();
            count.setOsnUsl((String) o[0]);
            count.setUsl((String) o[1]);
            Long l = (Long) o[2];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<ReestrPMPK> reestrChildren(Date dateN, Date dateK) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId, "
                + "p.priyomDate  "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND (s.sprstatName = :stat1) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("stat1", "ОВЗ")
                .setParameter("kat", "children");
        List resultList = query.getResultList();
        List<ReestrPMPK> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            ReestrPMPK child = new ReestrPMPK();
            child.setId((Integer) o[6]);
            child.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            child.setDr((Date) o[3]);
            child.setReg((String) o[4]);
            child.setOvz((String) o[5]);
            child.setOp(Boolean.FALSE);
            child.setDatep((Date) o[7]);
            result.add(child);
        }

        String qlString2 = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId, "
                + "p.priyomDate  "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND (s.sprstatName = :stat1) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query2 = em.createQuery(qlString2)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("stat1", "ОП")
                .setParameter("kat", "children");
        List resultList2 = query2.getResultList();
        List<ReestrPMPK> result2 = new ArrayList<>();
        for (int i = 0; i < resultList2.size(); i++) {
            Object[] o = (Object[]) resultList2.get(i);
            Boolean flag = Boolean.FALSE;
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[6];
                if (ch.getId().equals(id)) {
                    ch.setOp(Boolean.TRUE);
                    flag = Boolean.TRUE;
                }
            }
            if (!flag) {
                ReestrPMPK child = new ReestrPMPK();
                child.setId((Integer) o[6]);
                child.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
                child.setDr((Date) o[3]);
                child.setReg((String) o[4]);
                child.setOp(Boolean.TRUE);
                child.setOvz("");
                child.setDatep((Date) o[7]);
                result2.add(child);
            }
        }
        for (ReestrPMPK ch : result2) {
            result.add(ch);
        }

        String qlString3 = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, "
                + "c.childId, p.priyomDate "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, "
                + "SprStatPod sp, SprStat s2, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND sp.sprstatIdPod = s AND sp.sprstatIdMain = s2 AND s2.sprstatId = 7 "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query3 = em.createQuery(qlString3)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("kat", "children");
        List resultList3 = query3.getResultList();
        for (int i = 0; i < resultList3.size(); i++) {
            Object[] o = (Object[]) resultList3.get(i);
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[6];
                Date priyomDate = (Date) o[7];
                if ((ch.getId().equals(id)) && (ch.getDatep().equals(priyomDate))) {
                    if (ch.getOvz().equals("ОВЗ")) {
                        ch.setOvz("");
                        ch.setOvz((String) o[5]);
                    } else {
                        ch.setOvz(ch.getOvz() + ", " + (String) o[5]);
                    }
                }
            }
        }

        String qlString4 = "SELECT c.childId, o.sprobrShname, p.priyomDate, k.pmpkDatek "
                + "FROM Children c, Priyom p, SprUsl u, SprObr o, Pmpk k, PriyomClient pc "
                + "WHERE pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND k.prclId = pc AND k.sprobrId = o "
                + "AND p.spruslId = u AND u.spruslId = :usl AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        Query query4 = em.createQuery(qlString4)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList4 = query4.getResultList();
        for (int i = 0; i < resultList4.size(); i++) {
            Object[] o = (Object[]) resultList4.get(i);
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[0];
                Date priyomDate = (Date) o[2];
                if ((ch.getId().equals(id)) && (ch.getDatep().equals(priyomDate))) {
                    ch.setObr((String) o[1]);
                    ch.setDatek((Date) o[3]);
                }
            }
        }

        String qlString5 = "SELECT c.childId, v.sprobrvarName, p.priyomDate "
                + "FROM Children c, Priyom p, SprUsl u, SprObrVar v, Pmpk k, PriyomClient pc "
                + "WHERE pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND k.prclId = pc AND k.sprobrvarId = v "
                + "AND p.spruslId = u AND u.spruslId = :usl AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        Query query5 = em.createQuery(qlString5)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList5 = query5.getResultList();
        for (int i = 0; i < resultList5.size(); i++) {
            Object[] o = (Object[]) resultList5.get(i);
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[0];
                Date priyomDate = (Date) o[2];
                if ((ch.getId().equals(id)) && (ch.getDatep().equals(priyomDate))) {
                    ch.setObr(ch.getObr() + " (" + (String) o[1] + ")");
                }
            }
        }

        return result;
    }

    public List<ReestrPMPK> reestrChildrenReg(Date dateN, Date dateK, SprRegion region) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId, "
                + "p.priyomDate  "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND (s.sprstatName = :stat1) AND (c.sprregId = :region) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("region", region)
                .setParameter("stat1", "ОВЗ")
                .setParameter("kat", "children");
        List resultList = query.getResultList();
        List<ReestrPMPK> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            ReestrPMPK child = new ReestrPMPK();
            child.setId((Integer) o[6]);
            child.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            child.setDr((Date) o[3]);
            child.setReg((String) o[4]);
            child.setOvz((String) o[5]);
            child.setOp(Boolean.FALSE);
            child.setDatep((Date) o[7]);
            result.add(child);
        }

        String qlString2 = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId, "
                + "p.priyomDate  "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1  AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND (s.sprstatName = :stat1) AND (c.sprregId = :region) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query2 = em.createQuery(qlString2)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("region", region)
                .setParameter("stat1", "ОП")
                .setParameter("kat", "children");
        List resultList2 = query2.getResultList();
        List<ReestrPMPK> result2 = new ArrayList<>();
        for (int i = 0; i < resultList2.size(); i++) {
            Object[] o = (Object[]) resultList2.get(i);
            Boolean flag = Boolean.FALSE;
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[6];
                if (ch.getId().equals(id)) {
                    ch.setOp(Boolean.TRUE);
                    flag = Boolean.TRUE;
                }
            }
            if (!flag) {
                ReestrPMPK child = new ReestrPMPK();
                child.setId((Integer) o[6]);
                child.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
                child.setDr((Date) o[3]);
                child.setReg((String) o[4]);
                child.setOp(Boolean.TRUE);
                child.setOvz("");
                child.setDatep((Date) o[7]);
                result2.add(child);
            }
        }
        for (ReestrPMPK ch : result2) {
            result.add(ch);
        }

        String qlString3 = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, "
                + "SprStatPod sp, SprStat s2, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND sp.sprstatIdPod = s AND sp.sprstatIdMain = s2 AND s2.sprstatId = 7  AND (c.sprregId = :region) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query3 = em.createQuery(qlString3)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("region", region)
                .setParameter("kat", "children");
        List resultList3 = query3.getResultList();
        for (int i = 0; i < resultList3.size(); i++) {
            Object[] o = (Object[]) resultList3.get(i);
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[6];
                if (ch.getId().equals(id)) {
                    if (ch.getOvz().equals("ОВЗ")) {
                        ch.setOvz("");
                        ch.setOvz((String) o[5]);
                    } else {
                        ch.setOvz(ch.getOvz() + ", " + (String) o[5]);
                    }
                }
            }
        }

        String qlString4 = "SELECT c.childId, o.sprobrShname "
                + "FROM Children c, Priyom p, SprUsl u, SprObr o, Pmpk k, PriyomClient pc "
                + "WHERE pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND k.prclId = pc AND k.sprobrId = o AND (c.sprregId = :region) "
                + "AND p.spruslId = u AND u.spruslId = :usl AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        Query query4 = em.createQuery(qlString4)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK)
                .setParameter("region", region);
        List resultList4 = query4.getResultList();
        for (int i = 0; i < resultList4.size(); i++) {
            Object[] o = (Object[]) resultList4.get(i);
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[0];
                if (ch.getId().equals(id)) {
                    ch.setObr((String) o[1]);
                }
            }
        }

        String qlString5 = "SELECT c.childId, v.sprobrvarName "
                + "FROM Children c, Priyom p, SprUsl u, SprObrVar v, Pmpk k, PriyomClient pc "
                + "WHERE pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND k.prclId = pc AND k.sprobrvarId = v AND (c.sprregId = :region) "
                + "AND p.spruslId = u AND u.spruslId = :usl AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        Query query5 = em.createQuery(qlString5)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK)
                .setParameter("region", region);
        List resultList5 = query5.getResultList();
        for (int i = 0; i < resultList5.size(); i++) {
            Object[] o = (Object[]) resultList5.get(i);
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[0];
                if (ch.getId().equals(id)) {
                    ch.setObr(ch.getObr() + " (" + (String) o[1] + ")");
                }
            }
        }

        return result;
    }

    public List<CountStatPmpk> countPmpk(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(p.priyomId) "
                + "FROM SprRegion r, Priyom p, SprUsl u "
                + "WHERE p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = p.sprregId "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            //    count.setPar("Организовано заседаний ПМПК");
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<PMPKStatus> pmpkStatus(Date dateN, Date dateK) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, "
                + "st.sprstatName, r.sprregName "
                + "FROM Children c, Priyom pr, PriyomClient pc, ChildStatus cs, SprStat st, SprUsl usl, SprRegion r "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND cs.childId = c "
                + "AND cs.sprstatId = st "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND pr.priyomDate >= :dateN4 AND pr.priyomDate <= :dateK2 "
                + "AND pr.spruslId = usl AND usl.spruslPmpk = :pmpk AND (st.sprstatV = :v2 OR st.sprstatV = :v3) "
                + "AND r = c.sprregId "
                + "ORDER BY st.sprstatName, c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("pmpk", 1)
                .setParameter("v2", 2)
                .setParameter("v3", 3);
        List resultList = query.getResultList();
        List<PMPKStatus> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            PMPKStatus r = new PMPKStatus();
            r.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            r.setDr((Date) o[3]);
            r.setStatus((String) o[4]);
            r.setReg((String) o[5]);
            result.add(r);
        }
        return result;
    }

    public List<PMPKStatus> pmpkStatusReg(Date dateN, Date dateK, SprRegion reg) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, "
                + "st.sprstatName, r.sprregName "
                + "FROM Children c, Priyom pr, PriyomClient pc, ChildStatus cs, SprStat st, SprUsl usl, SprRegion r "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND cs.childId = c "
                + "AND cs.sprstatId = st "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND pr.priyomDate >= :dateN4 AND pr.priyomDate <= :dateK2 "
                + "AND pr.spruslId = usl AND usl.spruslPmpk = :pmpk AND (st.sprstatV = :v2 OR st.sprstatV = :v3) "
                + "AND r = c.sprregId AND r = :reg "
                + "ORDER BY st.sprstatName, c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("pmpk", 1)
                .setParameter("v2", 2)
                .setParameter("v3", 3)
                .setParameter("reg", reg);
        List resultList = query.getResultList();
        List<PMPKStatus> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            PMPKStatus r = new PMPKStatus();
            r.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            r.setDr((Date) o[3]);
            r.setStatus((String) o[4]);
            r.setReg((String) o[5]);
            result.add(r);
        }
        return result;
    }

    public List<PMPKR> pmpkIpr(Date d1, Date d2) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 AND p.pmpkIpr = :ipr "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("date1", d1)
                .setParameter("date2", d2)
                .setParameter("pmpk", 1)
                .setParameter("ipr", 1);
        List resultList = query.getResultList();
        List<PMPKR> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            PMPKR r = new PMPKR();
            r.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            r.setDr((Date) o[3]);
            r.setReg((String) o[4]);
            result.add(r);
        }
        return result;
    }

    public List<PMPKR> pmpkIprReg(Date d1, Date d2, SprRegion reg) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk AND p.pmpkIpr = :ipr "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 AND r = :reg "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("date1", d1)
                .setParameter("date2", d2)
                .setParameter("pmpk", 1)
                .setParameter("reg", reg)
                .setParameter("ipr", 1);
        List resultList = query.getResultList();
        List<PMPKR> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            PMPKR r = new PMPKR();
            r.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            r.setDr((Date) o[3]);
            r.setReg((String) o[4]);
            result.add(r);
        }
        return result;
    }

    public List<PMPKR> pmpkGia(Date d1, Date d2) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 "
                + "AND (p.pmpkGia = :gia1 OR p.pmpkGia = :gia2) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("date1", d1)
                .setParameter("date2", d2)
                .setParameter("pmpk", 1)
                .setParameter("gia1", 1)
                .setParameter("gia2", 2);
        List resultList = query.getResultList();
        List<PMPKR> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            PMPKR r = new PMPKR();
            r.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            r.setDr((Date) o[3]);
            r.setReg((String) o[4]);
            result.add(r);
        }
        return result;
    }

    public List<PMPKR> pmpkGiaReg(Date d1, Date d2, SprRegion reg) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 AND r = :reg "
                + "AND (p.pmpkGia = :gia1 OR p.pmpkGia = :gia2) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("date1", d1)
                .setParameter("date2", d2)
                .setParameter("pmpk", 1)
                .setParameter("reg", reg)
                .setParameter("gia1", 1)
                .setParameter("gia2", 2);
        List resultList = query.getResultList();
        List<PMPKR> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            PMPKR r = new PMPKR();
            r.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            r.setDr((Date) o[3]);
            r.setReg((String) o[4]);
            result.add(r);
        }
        return result;
    }

    public List<PMPKTer> pmpkTer(Date d1, Date d2) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, p.pmpkTpmpk "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 "
                + "AND (p.pmpkTpmpk = :ter1 OR p.pmpkTpmpk = :ter2) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("date1", d1)
                .setParameter("date2", d2)
                .setParameter("pmpk", 1)
                .setParameter("ter1", 1)
                .setParameter("ter2", 2);
        List resultList = query.getResultList();
        List<PMPKTer> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            PMPKTer r = new PMPKTer();
            r.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            r.setDr((Date) o[3]);
            r.setReg((String) o[4]);
            if ((Long) o[5] == 1) {
                r.setTer("по направлению");
            } else if ((Long) o[5] == 2) {
                r.setTer("обжалование");
            }
            result.add(r);
        }
        return result;
    }

    public List<PMPKTer> pmpkTerReg(Date d1, Date d2, SprRegion reg) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, p.pmpkTpmpk "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 AND r = :reg "
                + "AND (p.pmpkTpmpk = :ter1 OR p.pmpkTpmpk = :ter2) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("date1", d1)
                .setParameter("date2", d2)
                .setParameter("pmpk", 1)
                .setParameter("reg", reg)
                .setParameter("ter1", 1)
                .setParameter("ter2", 2);
        List resultList = query.getResultList();
        List<PMPKTer> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            PMPKTer r = new PMPKTer();
            r.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            r.setDr((Date) o[3]);
            r.setReg((String) o[4]);
            if ((Long) o[5] == 1) {
                r.setTer("по направлению");
            } else if ((Long) o[5] == 2) {
                r.setTer("обжалование");
            }
            result.add(r);
        }
        return result;
    }

    public List<PMPKTer> pmpkRek(Date d1, Date d2) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, "
                + "r.sprregName, rek.sprrekName "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + ", PmpkRek prek, SprRekomend rek "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 "
                + "AND prek.pmpkId = p AND prek.sprrekId = rek "
                + "ORDER BY rek.sprrekName, c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("date1", d1)
                .setParameter("date2", d2)
                .setParameter("pmpk", 1);
        List resultList = query.getResultList();
        List<PMPKTer> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            PMPKTer r = new PMPKTer();
            r.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            r.setDr((Date) o[3]);
            r.setReg((String) o[4]);
            r.setTer((String) o[5]);
            result.add(r);
        }
        return result;
    }

    public List<PMPKTer> pmpkRekReg(Date d1, Date d2, SprRegion reg) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, "
                + "r.sprregName, rek.sprrekName "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + ", PmpkRek prek, SprRekomend rek "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 AND r = :reg "
                + "AND prek.pmpkId = p AND prek.sprrekId = rek "
                + "ORDER BY rek.sprrekName, c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("date1", d1)
                .setParameter("date2", d2)
                .setParameter("pmpk", 1)
                .setParameter("reg", reg);
        List resultList = query.getResultList();
        List<PMPKTer> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            PMPKTer r = new PMPKTer();
            r.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            r.setDr((Date) o[3]);
            r.setReg((String) o[4]);
            r.setTer((String) o[5]);
            result.add(r);
        }
        return result;
    }

    public List<CountStatusReg> countStatusPmpk(Date dateN, Date dateK) {
        String qlString = "SELECT count(c), st.sprstatName, r.sprregName "
                + "FROM Children c, Priyom pr, PriyomClient pc, ChildStatus cs, SprStat st, SprUsl usl, SprRegion r "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND cs.childId = c "
                + "AND cs.sprstatId = st "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND pr.priyomDate >= :dateN4 AND pr.priyomDate <= :dateK2 "
                + "AND pr.spruslId = usl AND usl.spruslPmpk = :pmpk AND (st.sprstatV = :v2 OR st.sprstatV = :v3) "
                + "AND r = c.sprregId "
                + "GROUP BY st.sprstatName, r.sprregName "
                + "ORDER BY st.sprstatName, r.sprregName ";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("pmpk", 1)
                .setParameter("v2", 2)
                .setParameter("v3", 3);
        List resultList = query.getResultList();
        List<CountStatusReg> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatusReg r = new CountStatusReg();
            Long l = (Long) o[0];
            r.setCount(l.intValue());
            r.setStatus((String) o[1]);
            r.setReg((String) o[2]);
            result.add(r);
        }
        return result;
    }

    public List<CountStatusReg> countRekPmpk(Date date1, Date date2) {
        String qlString = "SELECT count(c), rek.sprrekName, r.sprregName "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + ", PmpkRek prek, SprRekomend rek "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 "
                + "AND prek.pmpkId = p AND prek.sprrekId = rek "
                + "GROUP BY rek.sprrekName, r.sprregName "
                + "ORDER BY rek.sprrekName, r.sprregName ";
        Query query = em.createQuery(qlString)
                .setParameter("date1", date1)
                .setParameter("date2", date2)
                .setParameter("pmpk", 1);
        List resultList = query.getResultList();
        List<CountStatusReg> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatusReg r = new CountStatusReg();
            Long l = (Long) o[0];
            r.setCount(l.intValue());
            r.setStatus((String) o[1]);
            r.setReg((String) o[2]);
            result.add(r);
        }
        return result;
    }

    public List<CountStatusReg> countParPmpk(Date d1, Date d2) {
        String qlString = "SELECT count(c), r.sprregName "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 AND p.pmpkIpr = :ipr "
                + "GROUP BY r.sprregName "
                + "ORDER BY r.sprregName ";
        Query query = em.createQuery(qlString)
                .setParameter("date1", d1)
                .setParameter("date2", d2)
                .setParameter("pmpk", 1)
                .setParameter("ipr", 1);
        List resultList = query.getResultList();
        List<CountStatusReg> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatusReg r = new CountStatusReg();
            Long l = (Long) o[0];
            r.setCount(l.intValue());
            r.setStatus("ИПР");
            r.setReg((String) o[1]);
            result.add(r);
        }

        String qlString2 = "SELECT count(c), r.sprregName "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 "
                + "AND (p.pmpkGia = :gia1 OR p.pmpkGia = :gia2) "
                + "GROUP BY r.sprregName "
                + "ORDER BY r.sprregName ";
        Query query2 = em.createQuery(qlString2)
                .setParameter("date1", d1)
                .setParameter("date2", d2)
                .setParameter("pmpk", 1)
                .setParameter("gia1", 1)
                .setParameter("gia2", 2);
        List resultList2 = query2.getResultList();
        for (int i = 0; i < resultList2.size(); i++) {
            Object[] o = (Object[]) resultList2.get(i);
            CountStatusReg r = new CountStatusReg();
            Long l = (Long) o[0];
            r.setCount(l.intValue());
            r.setStatus("ГИА");
            r.setReg((String) o[1]);
            result.add(r);
        }

        String qlString3 = "SELECT count(c), r.sprregName, p.pmpkTpmpk "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 "
                + "AND (p.pmpkTpmpk = :ter1 OR p.pmpkTpmpk = :ter2) "
                + "GROUP BY r.sprregName, p.pmpkTpmpk "
                + "ORDER BY r.sprregName, p.pmpkTpmpk ";
        Query query3 = em.createQuery(qlString3)
                .setParameter("date1", d1)
                .setParameter("date2", d2)
                .setParameter("pmpk", 1)
                .setParameter("ter1", 1)
                .setParameter("ter2", 2);
        List resultList3 = query3.getResultList();
        for (int i = 0; i < resultList3.size(); i++) {
            Object[] o = (Object[]) resultList3.get(i);
            CountStatusReg r = new CountStatusReg();
            Long l = (Long) o[0];
            r.setCount(l.intValue());
            if ((Long) o[2] == 1) {
                r.setStatus("по направлению");
            } else if ((Long) o[2] == 2) {
                r.setStatus("обжалование");
            }
            r.setReg((String) o[1]);
            result.add(r);
        }
        return result;
    }

    public List<ReestrMonitPMPK> reestrMonitChildrenReg(Date dateN, Date dateK, SprRegion region) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, "
                + "c.childId, max (p.priyomDate), c.childNom "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, "
                + "PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND (s.sprstatName = :stat) AND (c.sprregId = :region) "
                + "GROUP BY c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId, c.childNom "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("region", region)
                .setParameter("stat", "ОВЗ")
                .setParameter("kat", "children");
        List resultList = query.getResultList();
        List<ReestrMonitPMPK> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            ReestrMonitPMPK child = new ReestrMonitPMPK();
            child.setId((Integer) o[6]);
            child.setNom((Integer) o[8]);
            child.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            child.setDr((Date) o[3]);
            child.setReg((String) o[4]);
            child.setDatep((Date) o[7]);
            result.add(child);
        }

        String qlString4 = "SELECT c.childId, k.pmpkNp, p.priyomDate "
                + "FROM Children c, Priyom p, SprUsl u, Pmpk k, PriyomClient pc "
                + "WHERE pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND k.prclId = pc AND (c.sprregId = :region) "
                + "AND p.spruslId = u AND u.spruslId = :usl AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        Query query4 = em.createQuery(qlString4)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK)
                .setParameter("region", region);
        List resultList4 = query4.getResultList();
        for (int i = 0; i < resultList4.size(); i++) {
            Object[] o = (Object[]) resultList4.get(i);
            for (ReestrMonitPMPK ch : result) {
                Integer id = (Integer) o[0];
                if ((ch.getId().equals(id)) && (ch.getDatep().equals((Date) o[2]))) {
                    ch.setNp((String) o[1]);
                }
            }
        }

        return result;
    }

    public List<ReestrPMPKFull> reestrChildrenFull(Date dateN, Date dateK) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId, "
                + "p.priyomDate  "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND (s.sprstatName = :stat1) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("stat1", "ОВЗ")
                .setParameter("kat", "children");
        List resultList = query.getResultList();
        List<ReestrPMPKFull> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            ReestrPMPKFull child = new ReestrPMPKFull();
            child.setId((Integer) o[6]);
            child.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            child.setDr((Date) o[3]);
            child.setReg((String) o[4]);
            child.setOvz((String) o[5]);
            child.setOp(Boolean.FALSE);
            child.setDi(Boolean.FALSE);
            child.setDs(Boolean.FALSE);
            child.setDatep((Date) o[7]);
            result.add(child);
        }

        String qlString2 = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId, "
                + "p.priyomDate  "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND (s.sprstatName = :stat1) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query2 = em.createQuery(qlString2)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("stat1", "ОП")
                .setParameter("kat", "children");
        List resultList2 = query2.getResultList();
        List<ReestrPMPKFull> result2 = new ArrayList<>();
        for (int i = 0; i < resultList2.size(); i++) {
            Object[] o = (Object[]) resultList2.get(i);
            Boolean flag = Boolean.FALSE;
            for (ReestrPMPKFull ch : result) {
                Integer id = (Integer) o[6];
                if (ch.getId().equals(id)) {
                    ch.setOp(Boolean.TRUE);
                    flag = Boolean.TRUE;
                }
            }
            if (!flag) {
                ReestrPMPKFull child = new ReestrPMPKFull();
                child.setId((Integer) o[6]);
                child.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
                child.setDr((Date) o[3]);
                child.setReg((String) o[4]);
                child.setOp(Boolean.TRUE);
                child.setOvz("");
                child.setDi(Boolean.FALSE);
                child.setDs(Boolean.FALSE);
                child.setDatep((Date) o[7]);
                result2.add(child);
            }
        }
        result.addAll(result2);

        String qlString21 = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId, "
                + "p.priyomDate  "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND (s.sprstatName = :stat1) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query21 = em.createQuery(qlString21)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("stat1", "ДИ")
                .setParameter("kat", "children");
        List resultList21 = query21.getResultList();
        List<ReestrPMPKFull> result21 = new ArrayList<>();
        for (int i = 0; i < resultList21.size(); i++) {
            Object[] o = (Object[]) resultList21.get(i);
            Boolean flag = Boolean.FALSE;
            for (ReestrPMPKFull ch : result) {
                Integer id = (Integer) o[6];
                if (ch.getId().equals(id)) {
                    ch.setDi(Boolean.TRUE);
                    flag = Boolean.TRUE;
                }
            }
            if (!flag) {
                ReestrPMPKFull child = new ReestrPMPKFull();
                child.setId((Integer) o[6]);
                child.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
                child.setDr((Date) o[3]);
                child.setReg((String) o[4]);
                child.setDi(Boolean.TRUE);
                child.setOvz("");
                child.setOp(Boolean.FALSE);
                child.setDs(Boolean.FALSE);
                child.setDatep((Date) o[7]);
                result21.add(child);
            }
        }
        result.addAll(result21);

        String qlString22 = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId, "
                + "p.priyomDate  "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND (s.sprstatName = :stat1) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query22 = em.createQuery(qlString22)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("stat1", "ДС")
                .setParameter("kat", "children");
        List resultList22 = query22.getResultList();
        List<ReestrPMPKFull> result22 = new ArrayList<>();
        for (int i = 0; i < resultList22.size(); i++) {
            Object[] o = (Object[]) resultList22.get(i);
            Boolean flag = Boolean.FALSE;
            for (ReestrPMPKFull ch : result) {
                Integer id = (Integer) o[6];
                if (ch.getId().equals(id)) {
                    ch.setDs(Boolean.TRUE);
                    flag = Boolean.TRUE;
                }
            }
            if (!flag) {
                ReestrPMPKFull child = new ReestrPMPKFull();
                child.setId((Integer) o[6]);
                child.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
                child.setDr((Date) o[3]);
                child.setReg((String) o[4]);
                child.setDs(Boolean.TRUE);
                child.setOp(Boolean.FALSE);
                child.setDi(Boolean.FALSE);
                child.setOvz("");
                child.setDatep((Date) o[7]);
                result22.add(child);
            }
        }
        result.addAll(result22);

        String qlString3 = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, "
                + "c.childId, p.priyomDate "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, "
                + "SprStatPod sp, SprStat s2, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND sp.sprstatIdPod = s AND sp.sprstatIdMain = s2 AND s2.sprstatId = 7 "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query3 = em.createQuery(qlString3)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("kat", "children");
        List resultList3 = query3.getResultList();
        for (int i = 0; i < resultList3.size(); i++) {
            Object[] o = (Object[]) resultList3.get(i);
            for (ReestrPMPKFull ch : result) {
                Integer id = (Integer) o[6];
                Date priyomDate = (Date) o[7];
                if ((ch.getId().equals(id)) && (ch.getDatep().equals(priyomDate))) {
                    if (ch.getOvz().equals("ОВЗ")) {
                        ch.setOvz("");
                        ch.setOvz((String) o[5]);
                    } else {
                        ch.setOvz(ch.getOvz() + ", " + (String) o[5]);
                    }
                }
            }
        }

        String qlString4 = "SELECT c.childId, o.sprobrShname, p.priyomDate, k.pmpkDatek "
                + "FROM Children c, Priyom p, SprUsl u, SprObr o, Pmpk k, PriyomClient pc "
                + "WHERE pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND k.prclId = pc AND k.sprobrId = o "
                + "AND p.spruslId = u AND u.spruslId = :usl AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        Query query4 = em.createQuery(qlString4)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList4 = query4.getResultList();
        for (int i = 0; i < resultList4.size(); i++) {
            Object[] o = (Object[]) resultList4.get(i);
            for (ReestrPMPKFull ch : result) {
                Integer id = (Integer) o[0];
                Date priyomDate = (Date) o[2];
                if ((ch.getId().equals(id)) && (ch.getDatep().equals(priyomDate))) {
                    ch.setObr((String) o[1]);
                    ch.setDatek((Date) o[3]);
                }
            }
        }

        String qlString5 = "SELECT c.childId, v.sprobrvarName, p.priyomDate "
                + "FROM Children c, Priyom p, SprUsl u, SprObrVar v, Pmpk k, PriyomClient pc "
                + "WHERE pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND k.prclId = pc AND k.sprobrvarId = v "
                + "AND p.spruslId = u AND u.spruslId = :usl AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        Query query5 = em.createQuery(qlString5)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList5 = query5.getResultList();
        for (int i = 0; i < resultList5.size(); i++) {
            Object[] o = (Object[]) resultList5.get(i);
            for (ReestrPMPKFull ch : result) {
                Integer id = (Integer) o[0];
                Date priyomDate = (Date) o[2];
                if ((ch.getId().equals(id)) && (ch.getDatep().equals(priyomDate))) {
                    ch.setObr(ch.getObr() + " (" + (String) o[1] + ")");
                }
            }
        }
        for (ReestrPMPKFull ch : result) {
            ch.setAssist(Boolean.FALSE);
            ch.setTutor(Boolean.FALSE);
            ch.setPsy(Boolean.FALSE);
            ch.setLogo(Boolean.FALSE);
            ch.setDefolig(Boolean.FALSE);
            ch.setDeftiflo(Boolean.FALSE);
            ch.setDefsurdo(Boolean.FALSE);
            ch.setSoc(Boolean.FALSE);
        }
        String qlString6 = "SELECT c.childId, rek.sprrekName, pr.priyomDate "
                + "FROM Children c, Priyom pr, PriyomClient pc, SprUsl usl, SprRegion r, Pmpk p "
                + ", PmpkRek prek, SprRekomend rek "
                + "WHERE pr = pc.priyomId AND pc.clientId = c.childId AND pr.spruslId = usl "
                + "AND c.sprregId = r AND p.prclId = pc AND usl.spruslPmpk = :pmpk "
                + "AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 "
                + "AND prek.pmpkId = p AND prek.sprrekId = rek "
                + "ORDER BY rek.sprrekName, c.childFam, c.childName, c.childPatr ";
        Query query6 = em.createQuery(qlString6)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK)
                .setParameter("pmpk", 1);
        List resultList6 = query6.getResultList();
        for (int i = 0; i < resultList6.size(); i++) {
            Object[] o = (Object[]) resultList6.get(i);
            for (ReestrPMPKFull ch : result) {
                Integer id = (Integer) o[0];
                Date priyomDate = (Date) o[2];
                String rek = (String) o[1];
                if ((ch.getId().equals(id)) && (ch.getDatep().equals(priyomDate))) {
                    if (rek.equals("ассистент (помощник)")) {
                        ch.setAssist(Boolean.TRUE);
                    } else if (rek.equals("тьютор")) {
                        ch.setTutor(Boolean.TRUE);
                    } else if (rek.equals("занятия педагога-психолога")) {
                        ch.setPsy(Boolean.TRUE);
                    } else if (rek.equals("занятия учителя-логопеда")) {
                        ch.setLogo(Boolean.TRUE);
                    } else if (rek.equals("занятия учителя-дефектолога (олигофренопедагога)")) {
                        ch.setDefolig(Boolean.TRUE);
                    } else if (rek.equals("занятия учителя-дефектолога (тифлопедагога)")) {
                        ch.setDeftiflo(Boolean.TRUE);
                    } else if (rek.equals("занятия учителя-дефектолога (сурдопедагога)")) {
                        ch.setDefsurdo(Boolean.TRUE);
                    } else if (rek.equals("занятия социального педагога")) {
                        ch.setSoc(Boolean.TRUE);
                    }
                }
            }
        }

        return result;
    }

    public List<ReestrPMPK> reestrChildrenRegFull(Date dateN, Date dateK, SprRegion region) {
        String qlString = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId, "
                + "p.priyomDate  "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND (s.sprstatName = :stat1) AND (c.sprregId = :region) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query = em.createQuery(qlString)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("region", region)
                .setParameter("stat1", "ОВЗ")
                .setParameter("kat", "children");
        List resultList = query.getResultList();
        List<ReestrPMPK> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            ReestrPMPK child = new ReestrPMPK();
            child.setId((Integer) o[6]);
            child.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
            child.setDr((Date) o[3]);
            child.setReg((String) o[4]);
            child.setOvz((String) o[5]);
            child.setOp(Boolean.FALSE);
            child.setDatep((Date) o[7]);
            result.add(child);
        }

        String qlString2 = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId, "
                + "p.priyomDate  "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1  AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND (s.sprstatName = :stat1) AND (c.sprregId = :region) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query2 = em.createQuery(qlString2)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("region", region)
                .setParameter("stat1", "ОП")
                .setParameter("kat", "children");
        List resultList2 = query2.getResultList();
        List<ReestrPMPK> result2 = new ArrayList<>();
        for (int i = 0; i < resultList2.size(); i++) {
            Object[] o = (Object[]) resultList2.get(i);
            Boolean flag = Boolean.FALSE;
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[6];
                if (ch.getId().equals(id)) {
                    ch.setOp(Boolean.TRUE);
                    flag = Boolean.TRUE;
                }
            }
            if (!flag) {
                ReestrPMPK child = new ReestrPMPK();
                child.setId((Integer) o[6]);
                child.setFio((String) o[0] + " " + (String) o[1] + " " + (String) o[2]);
                child.setDr((Date) o[3]);
                child.setReg((String) o[4]);
                child.setOp(Boolean.TRUE);
                child.setOvz("");
                child.setDatep((Date) o[7]);
                result2.add(child);
            }
        }
        for (ReestrPMPK ch : result2) {
            result.add(ch);
        }

        String qlString3 = "SELECT c.childFam, c.childName, c.childPatr, c.childDr, r.sprregName, s.sprstatName, c.childId "
                + "FROM Children c, SprRegion r, SprStat s, ChildStatus cs, Priyom p, SprUsl u, "
                + "SprStatPod sp, SprStat s2, PriyomClient pc "
                + "WHERE c.sprregId = r AND cs.childId = c AND cs.sprstatId = s "
                + "AND p.spruslId = u AND u.spruslId = 1 AND pc.clientId = c.childId AND pc.priyomId = p "
                + "AND pc.prclKatcl = :kat "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "AND sp.sprstatIdPod = s AND sp.sprstatIdMain = s2 AND s2.sprstatId = 7  AND (c.sprregId = :region) "
                + "ORDER BY c.childFam, c.childName, c.childPatr ";
        Query query3 = em.createQuery(qlString3)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("region", region)
                .setParameter("kat", "children");
        List resultList3 = query3.getResultList();
        for (int i = 0; i < resultList3.size(); i++) {
            Object[] o = (Object[]) resultList3.get(i);
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[6];
                if (ch.getId().equals(id)) {
                    if (ch.getOvz().equals("ОВЗ")) {
                        ch.setOvz("");
                        ch.setOvz((String) o[5]);
                    } else {
                        ch.setOvz(ch.getOvz() + ", " + (String) o[5]);
                    }
                }
            }
        }

        String qlString4 = "SELECT c.childId, o.sprobrShname "
                + "FROM Children c, Priyom p, SprUsl u, SprObr o, Pmpk k, PriyomClient pc "
                + "WHERE pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND k.prclId = pc AND k.sprobrId = o AND (c.sprregId = :region) "
                + "AND p.spruslId = u AND u.spruslId = :usl AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        Query query4 = em.createQuery(qlString4)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK)
                .setParameter("region", region);
        List resultList4 = query4.getResultList();
        for (int i = 0; i < resultList4.size(); i++) {
            Object[] o = (Object[]) resultList4.get(i);
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[0];
                if (ch.getId().equals(id)) {
                    ch.setObr((String) o[1]);
                }
            }
        }

        String qlString5 = "SELECT c.childId, v.sprobrvarName "
                + "FROM Children c, Priyom p, SprUsl u, SprObrVar v, Pmpk k, PriyomClient pc "
                + "WHERE pc.priyomId = p AND pc.clientId = c.childId AND pc.prclKatcl = :kat "
                + "AND k.prclId = pc AND k.sprobrvarId = v AND (c.sprregId = :region) "
                + "AND p.spruslId = u AND u.spruslId = :usl AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        Query query5 = em.createQuery(qlString5)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK)
                .setParameter("region", region);
        List resultList5 = query5.getResultList();
        for (int i = 0; i < resultList5.size(); i++) {
            Object[] o = (Object[]) resultList5.get(i);
            for (ReestrPMPK ch : result) {
                Integer id = (Integer) o[0];
                if (ch.getId().equals(id)) {
                    ch.setObr(ch.getObr() + " (" + (String) o[1] + ")");
                }
            }
        }

        return result;
    }

}
