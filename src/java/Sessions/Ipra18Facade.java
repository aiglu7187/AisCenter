/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Children;
import Entities.Ipra18;
import Entities.SprRegion;
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
public class Ipra18Facade extends AbstractFacade<Ipra18> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Ipra18Facade() {
        super(Ipra18.class);
    }
    
    public Ipra18 findById(Integer id) {
        TypedQuery<Ipra18> query = em.createNamedQuery("Ipra18.findByIpra18Id", Ipra18.class)
                .setParameter("ipra18Id", id);
        Ipra18 result = query.getSingleResult();
        return result;
    }

    public List<Ipra18> findByChild(Children child) {
        String qlString = "SELECT i FROM Ipra18 i "
                + "WHERE i.childId = :childId ";
        TypedQuery<Ipra18> query = em.createQuery(qlString, Ipra18.class)
                .setParameter("childId", child);
        List<Ipra18> result = query.getResultList();
        return result;
    }

    
    public List<Ipra18> findBySearch(String fam, String name, String patr, SprRegion reg, String sort, Date dpr, String npr, Integer status) {
        String qlString = "SELECT i.ipra18_id FROM ipra18 i, children c, ipra18_prikaz p "
                + "WHERE i.child_id = c.child_id AND p.ipra18_id = i.ipra18_id "
                + "AND REPLACE(UPPER(c.child_fam),?,?) like REPLACE(UPPER(?),?,?) "
                + "AND REPLACE(UPPER(c.child_name),?,?) like REPLACE(UPPER(?),?,?) "
                + "AND (REPLACE(UPPER(c.child_patr),?,?) like REPLACE(UPPER(?),?,?) "
                + "OR c.child_patr IS NULL ) "
                + "AND i.ipra18_status = ? ";
        if (reg != null) {
            qlString += "AND c.sprreg_id = ? ";
        }
        if (!npr.equals("")) {
            qlString += "AND p.ipra18prikaz_do_n = ? ";
        }
        if (dpr != null) {
            qlString += "AND p.ipra18prikaz_do_d = ? ";
        }
        if (sort.equals("do2")){
            qlString += "ORDER BY i.ipra18_otchdo desc ";
        }
        else if (sort.equals("do1")){
            qlString += "ORDER BY i.ipra18_otchdo ";
        }
        else if (sort.equals("omsu1")){
            qlString += "ORDER BY p.ipra18prikaz_perech_d ";
        }
        else if (sort.equals("omsu2")){
            qlString += "ORDER BY p.ipra18prikaz_perech_d desc ";
        }
        else if (sort.equals("pr1")){
            qlString += "ORDER BY p.ipra18prikaz_do_d ";
        }
        else if (sort.equals("pr2")){
            qlString += "ORDER BY p.ipra18prikaz_do_d desc ";
        }
        
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
                .setParameter(16, status);
        int j = 17;
        if (reg != null) {
            query.setParameter(j, reg.getSprregId());
            j++;
        }
       if (!npr.equals("")) {
            query.setParameter(j, npr);
            j++;
        }
        if (dpr != null) {
            query.setParameter(j, dpr);
        }

        List resultList = query.getResultList();
        List<Ipra18> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object o = (Object) resultList.get(i);
            BigDecimal d = (BigDecimal) o;
            Long l = d.longValue();
            Integer id = l.intValue();
            Ipra18 ipra = findById(id);
            result.add(ipra);
        }
        return result;
    }
    
    public List<Ipra18> findAllCur() {
        TypedQuery<Ipra18> query = em.createNamedQuery("Ipra18.findByIpra18Status", Ipra18.class)
                .setParameter("ipra18Status", 1);
        List<Ipra18> result = query.getResultList();
        return result;
    }

    public List<Ipra18> findAllArchive() {
        TypedQuery<Ipra18> query = em.createNamedQuery("Ipra.findByIpra18Status", Ipra18.class)
                .setParameter("ipra18Status", 0);
        List<Ipra18> result = query.getResultList();
        return result;
    }
    
    public List<Ipra18> findRed() {
        String qlString = "SELECT i.ipra18_id FROM ipra18 i, ipra18_prikaz ip "
                + "WHERE ((ip.ipra18prikaz_otchomsu <= ?) OR (ip.ipra18prikaz_otchcenter <= ?) "
                + "OR (i.ipra18_otchdo <= ?))"
                + "AND i.ipra18_id = ip.ipra18_id ";
        Long period = 1000 * 60 * 60 * 24 * 30l;
        Date now = new Date();
        Date periodD = new Date(now.getTime() + period);
        Query query = em.createNativeQuery(qlString)
                .setParameter(1, periodD)
                .setParameter(2, periodD)
                .setParameter(3, periodD);
        List resultList = query.getResultList();
        List<Ipra18> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object o = (Object) resultList.get(i);
            BigDecimal d = (BigDecimal) o;
            Long l = d.longValue();
            Integer id = l.intValue();
            Ipra18 ipra = findById(id);
            result.add(ipra);
        }
        return result;
    }
    
    
    
}
