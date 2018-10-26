/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Children;
import Entities.Monitoring;
import Entities.MonitoringData;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class MonitoringDataFacade extends AbstractFacade<MonitoringData> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MonitoringDataFacade() {
        super(MonitoringData.class);
    }
    
    public List<MonitoringData> findByMonitoring(Monitoring monitoring){
        String qlString = "SELECT m FROM MonitoringData m " +
                "WHERE m.monitoringId = :monitoringId ";
        TypedQuery<MonitoringData> query = em.createQuery(qlString, MonitoringData.class)
                .setParameter("monitoringId", monitoring);
        List<MonitoringData> result = query.getResultList();
        return result;
    }
    
    public MonitoringData findById(Integer id){
        TypedQuery<MonitoringData> query = em.createNamedQuery("MonitoringData.findByMonitdataId", MonitoringData.class)
                .setParameter("monitdataId", id);
        MonitoringData result = query.getSingleResult();
        return result;       
        
    }
    
    public List<MonitoringData> findByChild(Children child){
        String qlString = "SELECT m FROM MonitoringData m "
                + "WHERE m.childId = :childId ";
        TypedQuery<MonitoringData> query = em.createQuery(qlString, MonitoringData.class)
                .setParameter("childId", child);
        List<MonitoringData> result = query.getResultList();
        return result;
    }
}
