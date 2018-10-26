/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Ipra18N;
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
public class Ipra18NFacade extends AbstractFacade<Ipra18N> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Ipra18NFacade() {
        super(Ipra18N.class);
    }

    public Ipra18N findById(Integer id) {
        TypedQuery<Ipra18N> query = em.createNamedQuery("Ipra18N.findByIpra18Id", Ipra18N.class)
                .setParameter("ipra18Id", id);
        Ipra18N result = query.getSingleResult();
        return result;
    }

    public List<Ipra18N> findBySearch(String fam, String name, String patr, SprRegion sprReg, String npr, Date dprDate, Integer status) {
        String qlString = "SELECT ipr.ipra18_id "
                + "FROM children c, "
                + "(SELECT i.ipra18_id, i.ipra18_n, i.ipra18_otchomsu, i.ipra18_otchcenter, "
                + "i.ipra18_otchdo, i.child_id, ip.ipra18prikaz_n, ip.ipra18prikaz_d "
                + "FROM ipra18N i LEFT JOIN ipra18_prikaz_N ip ON i.ipra18_id = ip.ipra18_id "
                + "WHERE i.ipra18_status = ?) ipr "
                + "WHERE c.child_id = ipr.child_id "
                + "AND REPLACE(UPPER(c.child_fam),?,?) like REPLACE(UPPER(?),?,?) "
                + "AND REPLACE(UPPER(c.child_name),?,?) like REPLACE(UPPER(?),?,?) "
                + "AND (REPLACE(UPPER(c.child_patr),?,?) like REPLACE(UPPER(?),?,?) "
                + "OR c.child_patr IS NULL ) ";
        if (sprReg != null) {
            qlString += "AND c.sprreg_id = ? ";
        }
        if (dprDate != null) {
            qlString += "AND ipr.ipra18prikaz_d = ? ";
        }
        if (npr != null) {
            if (!npr.equals("")) {
                qlString += "AND ipr.ipra18prikaz_n = ? ";
            }
        }

        Query query = em.createNativeQuery(qlString)
                .setParameter(1, status)
                .setParameter(2, "Ё")
                .setParameter(3, "Е")
                .setParameter(4, fam + "%")
                .setParameter(5, "Ё")
                .setParameter(6, "Е")
                .setParameter(7, "Ё")
                .setParameter(8, "Е")
                .setParameter(9, name + "%")
                .setParameter(10, "Ё")
                .setParameter(11, "Е")
                .setParameter(12, "Ё")
                .setParameter(13, "Е")
                .setParameter(14, patr + "%")
                .setParameter(15, "Ё")
                .setParameter(16, "Е");
        int j = 17;
        if (sprReg != null) {
            query.setParameter(j, sprReg.getSprregId());
            j++;
        }
        if (npr != null) {
            if (!npr.equals("")) {
                query.setParameter(j, npr);
                j++;
            }
        }
        if (dprDate != null) {
            query.setParameter(j, dprDate);
        }        

        List resultList = query.getResultList();
        List<Ipra18N> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object o = (Object) resultList.get(i);
            BigDecimal d = (BigDecimal) o;
            Long l = d.longValue();
            Integer id = l.intValue();
            Ipra18N ipra = findById(id);
            result.add(ipra);
        }
        return result;
    }
    
    public List<Ipra18N> findUpcomingOthcet(){  // выбираем отчёты с ближайшими сроками (в течение месяца)
        String qlString = "SELECT i FROM Ipra18N i "
                + "WHERE (i.ipra18Otchomsu <= :date1) OR (i.ipra18Otchcenter <= :date2) "
                + "OR (i.ipra18Otchdo <= :date3)"
                + "ORDER BY i.ipra18Otchdo ";
        Long period = 1000 * 60 * 60 * 24 * 30l;
        Date now = new Date();
        Date periodD = new Date(now.getTime() + period);
        TypedQuery<Ipra18N> query = em.createQuery(qlString, Ipra18N.class)
                .setParameter("date1", periodD)
                .setParameter("date2", periodD)
                .setParameter("date3", periodD);        
        List<Ipra18N> result = query.getResultList();
        return result;
    }
}
