/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Children;
import Entities.Monitoring;
import Entities.MonitoringEducond;
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
public class MonitoringEducondFacade extends AbstractFacade<MonitoringEducond> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MonitoringEducondFacade() {
        super(MonitoringEducond.class);
    }
    
    public List<MonitoringEducond> findByChild(Children child){
        String qlString = "SELECT m FROM MonitoringEducond m, ChildrenEducond ce "
                + "WHERE m.childreneducondId = ce AND ce.childId = :childId ";
        TypedQuery<MonitoringEducond> query = em.createQuery(qlString, MonitoringEducond.class)
                .setParameter("childId", child);
        List<MonitoringEducond> result = query.getResultList();
        return result;
    }
       
}
