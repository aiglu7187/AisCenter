/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Count.CountStatPmpk;
import Count.StandartCount;
import Entities.Children;
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
public class ChildrenFacade extends AbstractFacade<Children> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChildrenFacade() {
        super(Children.class);
    }
    
    public List<Children> search(String fam, String name, String patr){
        String qlString = "SELECT distinct child_id, child_fam, child_name, child_patr FROM "
                + "((SELECT c.child_id, c.child_fam, c.child_name, c.child_patr FROM children c "
                + "WHERE REPLACE(UPPER(c.child_fam),?,?) LIKE REPLACE(UPPER(?),?,?) "                
                + "AND REPLACE(UPPER(c.child_name),?,?) LIKE REPLACE(UPPER(?),?,?) "                
                + "AND (REPLACE(UPPER(c.child_patr),?,?) LIKE REPLACE(UPPER(?),?,?) "               
                + "OR c.child_patr IS NULL))"
                + "UNION ALL "
                + "(SELECT c.child_id, c.child_fam, c.child_name, c.child_patr FROM children c, children_oldfio co "
                + "WHERE c.child_id = co.child_id AND REPLACE(UPPER(co.childoldfio_fam),?,?) LIKE REPLACE(UPPER(?),?,?) "
                + "AND REPLACE(UPPER(co.childoldfio_name),?,?) LIKE REPLACE(UPPER(?),?,?) "
                + "AND (REPLACE(UPPER(co.childoldfio_patr),?,?) LIKE REPLACE(UPPER(?),?,?) OR co.childoldfio_patr IS NULL))) "                         
                + "ORDER BY child_fam, child_name, child_patr";
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
                .setParameter(15, "Е")
                .setParameter(16, "Ё")
                .setParameter(17, "Е")
                .setParameter(18, fam + "%")
                .setParameter(19, "Ё")
                .setParameter(20, "Е")
                .setParameter(21, "Ё")
                .setParameter(22, "Е")
                .setParameter(23, name + "%")
                .setParameter(24, "Ё")
                .setParameter(25, "Е")
                .setParameter(26, "Ё")
                .setParameter(27, "Е")
                .setParameter(28, patr + "%")
                .setParameter(29, "Ё")
                .setParameter(30, "Е");
        query.setMaxResults(50);
        List resultList = query.getResultList();
        List<Children> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            BigDecimal d = (BigDecimal) o[0];
            Long l = d.longValue();
            Integer id = l.intValue();
            Children child = findById(id);
            result.add(child);
        }                
        return result;
    }

    public List<Children> findByFioDr(String fam, String name, String patr, Date dr) {
        String qlString = "SELECT c.child_id FROM children c WHERE "
                + "REPLACE(UPPER(c.child_fam),?,?) = REPLACE(UPPER(?),?,?) "
                + "AND REPLACE(UPPER(c.child_name),?,?) = REPLACE(UPPER(?),?,?) "
                + "AND REPLACE(REPLACE(UPPER(c.child_patr),?,?),?,?) = REPLACE(REPLACE(UPPER(?),?,?),?,?) "
                + "AND c.child_dr = ? ";
        Query query = em.createNativeQuery(qlString)
                .setParameter(1, "Ё")
                .setParameter(2, "Е")
                .setParameter(3, fam)
                .setParameter(4, "Ё")
                .setParameter(5, "Е")
                .setParameter(6, "Ё")
                .setParameter(7, "Е")
                .setParameter(8, name)
                .setParameter(9, "Ё")
                .setParameter(10, "Е")
                .setParameter(11, "Ё")
                .setParameter(12, "Е")
                .setParameter(13, " ")
                .setParameter(14, "")
                .setParameter(15, patr)                
                .setParameter(16, "Ё")
                .setParameter(17, "Е")
                .setParameter(18, " ")
                .setParameter(19, "")
                .setParameter(20, dr);
        List resultList = query.getResultList();
        List<Children> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object o = (Object) resultList.get(i);
            BigDecimal d = (BigDecimal) o;
            Long l = d.longValue();
            Integer id = l.intValue();
            Children child = findById(id);
            result.add(child);
        }
        return result;
    }

    public Children findById(Integer id) {
        TypedQuery<Children> query
                = em.createNamedQuery("Children.findByChildId", Children.class).setParameter("childId", id);
        Children result;
        result = query.getSingleResult();
        return result;
    }

    public Children findByNom(Integer nom) {
        TypedQuery<Children> query
                = em.createNamedQuery("Children.findByChildNom", Children.class).setParameter("childNom", nom);
        Children result;
        result = query.getSingleResult();
        return result;
    }

    public List<Children> findAllChildren() {
        TypedQuery query = em.createNamedQuery("Children.findAll", Children.class);
        query.setMaxResults(50);
        List<Children> result;
        result = query.getResultList();
        return result;
    }

    public List<CountStatPmpk> childrenAge3(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 AND p.priyomDate - c.childDr > 0 AND p.priyomDate - c.childDr <= 3*365 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenAge37(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 AND p.priyomDate - c.childDr > 3*365 AND p.priyomDate - c.childDr < 7*365 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenAge7(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 AND p.priyomDate - c.childDr >= 7*365 AND p.priyomDate - c.childDr <= 18*365 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenAge18(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 AND p.priyomDate - c.childDr > 18*365 AND p.priyomDate - c.childDr < 100*365 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPMPK(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = :usl "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenNoOu(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkOu = 0 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenAge3NoOu(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 AND p.priyomDate - c.childDr > 0 AND p.priyomDate - c.childDr <= 3*365 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkOu = 0 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenAge37NoOu(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 AND p.priyomDate - c.childDr > 3*365 AND p.priyomDate - c.childDr < 7*365 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkOu = 0 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenAge7NoOu(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 AND p.priyomDate - c.childDr >= 7*365 AND p.priyomDate - c.childDr <= 18*365 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkOu = 0 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenNoOuOVZ(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, ChildStatus cs, SprStat s, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "                
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkOu = 0 "
                + "AND cs.childId = c AND cs.sprstatId = s AND s.sprstatName = :sprstatName "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "               
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("sprstatName", "ОВЗ");
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenNoOuInv(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, ChildStatus cs, SprStat s, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "                
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkOu = 0 "
                + "AND cs.childId = c AND cs.sprstatId = s AND s.sprstatName = :sprstatName "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "               
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("sprstatName", "ДИ");
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkNorm(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, ChildStatus cs, SprStat s, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "                
                + "AND r = c.sprregId "
                + "AND cs.childId = c AND cs.sprstatId = s AND s.sprstatName = :sprstatName "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "               
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK)
                .setParameter("sprstatName", "N");
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkStat(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId), s.sprstatName "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, ChildStatus cs, SprStat s, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = :usl "                
                + "AND r = c.sprregId "
                + "AND cs.childId = c AND cs.sprstatId = s AND s.sprstatId > 5 AND s.sprstatId < 14 "
                + "AND ((cs.childstatusDateN >= :dateN1 AND cs.childstatusDateN <= :dateK1) "
                + "OR (cs.childstatusDateN <= :dateN2 AND (cs.childstatusDateK >= :dateN3 OR cs.childstatusDateK IS NULL))) "                
                + "AND p.priyomDate >= :dateN4 AND p.priyomDate <= :dateK2 "
                + "GROUP BY r.sprregName, s.sprstatName "
                + "ORDER BY r.sprregName, s.sprstatName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("dateN1", dateN)
                .setParameter("dateN2", dateN)
                .setParameter("dateN3", dateN)
                .setParameter("dateN4", dateN)
                .setParameter("dateK1", dateK)
                .setParameter("dateK2", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setParS((String) o[2]);
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkIpr(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkIpr = 1 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkSogl(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkSogl = 1 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkNoSogl(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkSogl = 0 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkGia9(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkGia = 1 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkGia11(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkGia = 2 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkTpmpkNapr(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkTpmpk = 1 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkTpmpkOb(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkTpmpk = 2 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkDop(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, Pmpk k, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND k.prclId = pc AND k.pmpkDop = 1 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkUdovl(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND pc.prclUdovl = 1 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkNoUdovl(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId) "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, SprUsl u "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId AND pc.prclUdovl = 0 "
                + "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkOp(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId), o2.sprobrShname, o2.sprobrId "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, SprUsl u, SprObr o, Pmpk k "
                + ", ObrObr oo, SprObr o2 "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND oo.sprobrIdMain = o2 AND oo.sprobrIdSoo = o "
                + "AND p.spruslId = u AND u.spruslId = :usl "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId "
                + "AND k.prclId = pc AND k.sprobrId = o AND o.sprobrActive = 0 "
                + "AND k.sprobrvarId is null "
                + "GROUP BY r.sprregName, o2.sprobrShname, o2.sprobrId "
                + "ORDER BY o2.sprobrId ";        
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        String qlString2 = "SELECT r.sprregName, COUNT(c.childId), o.sprobrShname, o.sprobrId "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, SprUsl u, SprObr o, Pmpk k "               
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = :usl "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId "
                + "AND k.prclId = pc AND k.sprobrId = o AND o.sprobrActive = 1 "
                + "AND k.sprobrvarId is null "
                + "GROUP BY r.sprregName, o.sprobrShname, o.sprobrId "
                + "ORDER BY o.sprobrId ";
        Query query2 = em.createQuery(qlString2)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        String qlString3 = "SELECT r.sprregName, COUNT(c.childId), o.sprobrShname, o.sprobrId "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, SprUsl u, SprObr o, Pmpk k "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "                
                + "AND p.spruslId = u AND u.spruslId = :usl "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId "
                + "AND k.prclId = pc AND k.sprobrId = o AND o.sprobrActive = 0 "
                + "AND NOT EXISTS (SELECT oo FROM ObrObr oo WHERE oo.sprobrIdSoo = o) "
                + "AND k.sprobrvarId is null "
                + "GROUP BY r.sprregName, o.sprobrShname, o.sprobrId "
                + "ORDER BY o.sprobrId ";        
        Query query3 = em.createQuery(qlString3)
                .setParameter("kat", "children")
                .setParameter("usl", 1)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List resultList2 = query2.getResultList();
        List resultList3 = query3.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setParS((String) o[2]);
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        for (int i = 0; i < resultList2.size(); i++) {
            Object[] o = (Object[]) resultList2.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setParS((String) o[2]);
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        for (int i = 0; i < resultList3.size(); i++) {
            Object[] o = (Object[]) resultList3.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setParS((String) o[2]);
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenPmpkVar(Date dateN, Date dateK) {
        String qlString = "SELECT r.sprregName, COUNT(c.childId), v.sprobrvarName, v.sprobrvarId "
                + "FROM SprRegion r, Children c, Priyom p, PriyomClient pc, SprUsl u, SprObrVar v, Pmpk k "
                + "WHERE p = pc.priyomId AND c.childId = pc.clientId AND pc.prclKatcl = :kat "
                + "AND p.spruslId = u AND u.spruslId = 1 "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND r = c.sprregId "
                + "AND k.prclId = pc AND k.sprobrvarId = v "
                + "GROUP BY r.sprregName, v.sprobrvarName, v.sprobrvarId "
                + "ORDER BY r.sprregName, v.sprobrvarName, v.sprobrvarId ";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setParS((String) o[2]);
            count.setReg((String) o[0]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<StandartCount> uslAges(Date dateN, Date dateK, Integer ageN, Integer ageK) {
        String qlString = "SELECT u.spruslId, u.spruslName, reg.sprregName, COUNT(DISTINCT pc.clientId) "
                + "FROM SprUsl u, PriyomClient pc, Priyom p, Children c, SprRegion reg "
                + "WHERE pc.priyomId = p AND p.spruslId = u AND pc.prclKatcl = :kat "
                + "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK "
                + "AND pc.clientId = c.childId AND p.priyomDate - c.childDr > :ageN * 365 "
                + "AND p.priyomDate - c.childDr <= :ageK * 365 "
                + "AND reg = c.sprregId "
                + "GROUP BY u.spruslId, u.spruslName, reg.sprregName "
                + "ORDER BY u.spruslId, reg.sprregName ";
        Query query = em.createQuery(qlString)
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK)
                .setParameter("ageN", ageN)
                .setParameter("ageK", ageK)
                .setParameter("kat", "children");
        List resultList = query.getResultList();
        List<StandartCount> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            StandartCount count = new StandartCount();
            count.setTitle((String) o[1]);
            count.setPar((String) o[2]);
            Long l = (Long) o[3];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }

    public List<CountStatPmpk> childrenRekomend(Date d1, Date d2) {
        String qlString = "SELECT r.sprregName, count(c.childId), rek.sprrekName "
                + "FROM SprRegion r, Priyom pr, PriyomClient pc, Children c, Pmpk pk, "
                + "PmpkRek prek, SprRekomend rek "
                + "WHERE pr = pc.priyomId AND pc.prclKatcl = :kat AND c.childId = pc.clientId "
                + "AND c.sprregId = r AND pr.priyomDate >= :date1 AND pr.priyomDate <= :date2 "
                + "AND pk.prclId = pc AND prek.pmpkId = pk AND prek.sprrekId = rek "
                + "GROUP BY r.sprregName, rek.sprrekName "
                + "ORDER BY r.sprregName, rek.sprrekName ";
        Query query = em.createQuery(qlString)
                .setParameter("kat", "children")
                .setParameter("date1", d1)
                .setParameter("date2", d2);
        List resultList = query.getResultList();
        List<CountStatPmpk> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            CountStatPmpk count = new CountStatPmpk();
            count.setReg((String) o[0]);
            count.setParS((String) o[2]);
            Long l = (Long) o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }
}
