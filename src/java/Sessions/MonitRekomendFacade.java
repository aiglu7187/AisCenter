/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.MonitRekomend;
import Entities.MonitoringData;
import Entities.SprRekomend;
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
public class MonitRekomendFacade extends AbstractFacade<MonitRekomend> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MonitRekomendFacade() {
        super(MonitRekomend.class);
    }
    
    public List<MonitRekomend> findByMonitData(MonitoringData monData){
        String qlString = "SELECT m FROM MonitRekomend m "  +
                "WHERE m.monitdataId = :monitdataId ";
        TypedQuery<MonitRekomend> query = em.createQuery(qlString, MonitRekomend.class)
                .setParameter("monitdataId", monData);
        List<MonitRekomend> result = query.getResultList();
        return result;
    }
}
