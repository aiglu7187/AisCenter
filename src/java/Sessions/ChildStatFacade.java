/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Count.CountStatus;
import Count.CountStatusDolgn;
import Count.CountStatusUsl;
import Entities.ChildStat;
import Entities.Children;
import Entities.Priyom;
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
public class ChildStatFacade extends AbstractFacade<ChildStat> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChildStatFacade() {
        super(ChildStat.class);
    }
        
    public List<ChildStat> findByChild(Children child){
        TypedQuery<ChildStat> query = em.createNamedQuery("ChildStat.findByChildId", ChildStat.class)
                .setParameter("childId", child);
        List<ChildStat> result = query.getResultList();
        return result;
    }
    public List<ChildStat> findByChildActive(Children child, Integer active){
        String qlString = "SELECT cs FROM ChildStat cs "
                + "WHERE cs.childId = :childId AND cs.childstatActive = :childstatActive ";
        TypedQuery<ChildStat> query = em.createQuery(qlString, ChildStat.class)
                .setParameter("childId", child)
                .setParameter("childstatActive", active);
        List<ChildStat> result = query.getResultList();
        return result;
    }
    
    public List<CountStatus> countStatV(Integer statV, Date dateN, Date dateK){
        String qlString = "SELECT COUNT(DISTINCT c.childId), s.sprstatName " +
                "FROM ChildStat c, SprStat s, Priyom p " +
                "WHERE c.sprstatId = s AND c.priyomId = p " +
                "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 " +
                "AND s.sprstatV = :statV " +
                "GROUP BY s.sprstatName " +
                "ORDER BY s.sprstatName";
        Query query = em.createQuery(qlString)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK)
                .setParameter("statV", statV);
        List resultList = query.getResultList();
        List<CountStatus> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[])resultList.get(i);
            CountStatus count = new CountStatus();
            Long l = (Long)o[0];
            count.setCount(l.intValue());
            count.setStatus((String)o[1]);
            result.add(count);
        }        
        return result;
    }
    
    public List<CountStatusUsl> countStatUsl(Date dateN, Date dateK){
        String qlString = "SELECT COUNT(DISTINCT c.childId), s.sprstatName, u.spruslName " +
                "FROM ChildStat c, SprStat s, SprUsl u, Priyom p " + 
                "WHERE c.sprstatId = s AND c.priyomId = p AND p.spruslId = u " +
                "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 " +
                "GROUP BY s.sprstatName, u.spruslName " +
                "ORDER BY u.spruslName, s.sprstatName";
        Query query = em.createQuery(qlString)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountStatusUsl> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++){
            Object[] o = (Object[])resultList.get(i);
            CountStatusUsl count = new CountStatusUsl();
            Long l = (Long)o[0];
            count.setCount(l.intValue());
            count.setStatus((String)o[1]);
            count.setUsl((String)o[2]);
            result.add(count);
        }
        return result;
    }
    
    public List<CountStatusUsl> countStatOsnUsl(Date dateN, Date dateK){
        String qlString = "SELECT COUNT(DISTINCT c.childId), s.sprstatName, ou.sprosnuslName " +
                "FROM ChildStat c, SprStat s, SprUsl u, SprOsnusl ou, Priyom p " + 
                "WHERE c.sprstatId = s AND c.priyomId = p AND p.spruslId = u " +
                "AND u.sprosnuslId = ou " +
                "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 " +
                "GROUP BY s.sprstatName, ou.sprosnuslName " +
                "ORDER BY ou.sprosnuslName, s.sprstatName";
        Query query = em.createQuery(qlString)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK);
        List resultList = query.getResultList();
        List<CountStatusUsl> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++){
            Object[] o = (Object[])resultList.get(i);
            CountStatusUsl count = new CountStatusUsl();
            Long l = (Long)o[0];
            count.setCount(l.intValue());
            count.setStatus((String)o[1]);
            count.setUsl((String)o[2]);
            result.add(count);
        }
        return result;
    }
    
    public List<CountStatusDolgn> countStatDolgn(Date dateN, Date dateK){
        String qlString = "SELECT COUNT(DISTINCT c.childId), s.sprstatName, d.sprdolgnName " +
                "FROM ChildStat c, SprDolgn d, SprStat s, Priyom p, PriyomSotrud ps, Sotrud sotr, SprUsl u " +
                "WHERE c.sprstatId = s AND c.priyomId = p AND ps.priyomId = p AND ps.sotrudId = sotr " +
                "AND sotr.sprdolgnId = d AND p.spruslId = u AND u.spruslId = :usl " +
                "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 " +
                "GROUP BY s.sprstatName, d.sprdolgnName " +
                "ORDER BY d.sprdolgnName, s.sprstatName";
        Query query = em.createQuery(qlString)
                .setParameter("date1", dateN)
                .setParameter("date2", dateK)
                .setParameter("usl", 2);
        List resultList = query.getResultList();
        List<CountStatusDolgn> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++){
            Object[] o = (Object[])resultList.get(i);
            CountStatusDolgn count = new CountStatusDolgn();
            Long l = (Long) o[0];
            count.setCount(l.intValue());
            count.setStatus((String)o[1]);
            count.setDolgn((String)o[2]);
            result.add(count);
        }
        return result;
    }
    
}
