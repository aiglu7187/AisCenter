/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Monitoring;
import Entities.SprRegion;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class MonitoringFacade extends AbstractFacade<Monitoring> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MonitoringFacade() {
        super(Monitoring.class);
    }
    
    public Monitoring findById(Integer id){
        TypedQuery<Monitoring> query = em.createNamedQuery("Monitoring.findByMonitoringId", Monitoring.class)
                .setParameter("monitoringId", id);
        Monitoring result = query.getSingleResult();
        return result;
    }
    
    public Monitoring findByDateReg(Date date, SprRegion reg){
        String qlString = "SELECT m FROM Monitoring m "
                + "WHERE m.monitoringDate = :monitoringDate"
                + "AND m.sprregId = :sprregId ";
        TypedQuery<Monitoring> query = em.createQuery(qlString, Monitoring.class)
                .setParameter("sprregId", reg)
                .setParameter("monitoringDate", date);
        Monitoring result = query.getSingleResult();
        return result;
    }
    
}
