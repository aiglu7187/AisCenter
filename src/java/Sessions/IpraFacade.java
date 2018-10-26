/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Children;
import Entities.Ipra;
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
public class IpraFacade extends AbstractFacade<Ipra> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IpraFacade() {
        super(Ipra.class);
    }

    public Ipra findById(Integer id) {
        TypedQuery<Ipra> query = em.createNamedQuery("Ipra.findByIpraId", Ipra.class)
                .setParameter("ipraId", id);
        Ipra result = query.getSingleResult();
        return result;
    }

    public List<Ipra> findByChild(Children child) {
        String qlString = "SELECT i FROM Ipra i "
                + "WHERE i.childId = :childId ";
        TypedQuery<Ipra> query = em.createQuery(qlString, Ipra.class)
                .setParameter("childId", child);
        List<Ipra> result = query.getResultList();
        return result;
    }

    public List<Ipra> findBySearch(String fam, String name, String patr, SprRegion reg,
            Integer status, Date dpr, String npr, String sort) {
        String qlString = "SELECT i.ipra_id FROM ipra i, children c "
                + "WHERE i.child_id = c.child_id "
                + "AND REPLACE(UPPER(c.child_fam),?,?) like REPLACE(UPPER(?),?,?) "
                + "AND REPLACE(UPPER(c.child_name),?,?) like REPLACE(UPPER(?),?,?) "
                + "AND (REPLACE(UPPER(c.child_patr),?,?) like REPLACE(UPPER(?),?,?) "
                + "OR c.child_patr IS NULL ) "
                + "AND i.ipra_status = ? ";
        if (reg != null) {
            qlString += "AND c.sprreg_id = ? ";
        }
        if (!npr.equals("")) {
            qlString += "AND i.ipra_prikaz_n = ? ";
        }
        if (dpr != null) {
            qlString += "AND i.ipra_prikaz_d = ? ";
        }
        if (sort.equals("do2")){
            qlString += "ORDER BY i.ipra_otchdo desc ";
        }
        else if (sort.equals("do1")){
            qlString += "ORDER BY i.ipra_otchdo ";
        }
        else if (sort.equals("omsu1")){
            qlString += "ORDER BY i.ipra_perech_d ";
        }
        else if (sort.equals("omsu2")){
            qlString += "ORDER BY i.ipra_perech_d desc ";
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
        List<Ipra> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object o = (Object) resultList.get(i);
            BigDecimal d = (BigDecimal) o;
            Long l = d.longValue();
            Integer id = l.intValue();
            Ipra ipra = findById(id);
            result.add(ipra);
        }
        return result;
    }

    public List<Ipra> findRed() {
        String qlString = "SELECT i.ipra_id FROM ipra i "
                + "WHERE (i.ipra_otchomsu <= ?) OR (i.ipra_otchcenter <= ?) "
                + "OR (i.ipra_otchdo <= ?) ";
        Long period = 1000 * 60 * 60 * 24 * 30l;
        Date now = new Date();
        Date periodD = new Date(now.getTime() + period);
        Query query = em.createNativeQuery(qlString)
                .setParameter(1, periodD)
                .setParameter(2, periodD)
                .setParameter(3, periodD);
        List resultList = query.getResultList();
        List<Ipra> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object o = (Object) resultList.get(i);
            BigDecimal d = (BigDecimal) o;
            Long l = d.longValue();
            Integer id = l.intValue();
            Ipra ipra = findById(id);
            result.add(ipra);
        }
        return result;
    }

    public List<Ipra> findAllCur() {
        TypedQuery<Ipra> query = em.createNamedQuery("Ipra.findByIpraStatus", Ipra.class)
                .setParameter("ipraStatus", 1);
        List<Ipra> result = query.getResultList();
        return result;
    }

    public List<Ipra> findAllArchive() {
        TypedQuery<Ipra> query = em.createNamedQuery("Ipra.findByIpraStatus", Ipra.class)
                .setParameter("ipraStatus", 0);
        List<Ipra> result = query.getResultList();
        return result;
    }
    
    public List<Ipra> findAllCorresp(){
        String qlString = "SELECT i FROM Ipra i "
                + "WHERE i.ipraOtchcenterN is not null ";
        TypedQuery<Ipra> query = em.createQuery(qlString, Ipra.class);
        List<Ipra> result = query.getResultList();
        return result;
    }
}
