/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprStat;
import Entities.SprStatPod;
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
public class SprStatPodFacade extends AbstractFacade<SprStatPod> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprStatPodFacade() {
        super(SprStatPod.class);
    }
    
    public List<SprStat> findByMainStat(SprStat main){
        TypedQuery<SprStat> query = em.createNamedQuery("SprStatPod.findByStatMain", SprStat.class)
                .setParameter("sprstatIdMain", main);
        List<SprStat> result = query.getResultList();
        return result;
    }
    
    public List<SprStatPod> findAllStatPod(){
        TypedQuery<SprStatPod> query = em.createNamedQuery("SprStatPod.findAll", SprStatPod.class);
        List<SprStatPod> result = query.getResultList();
        return result;
    }
    
    public SprStat findByPodStat(SprStat pod){
        TypedQuery<SprStat> query = em.createNamedQuery("SprStatPod.findByStatPod", SprStat.class)
                .setParameter("sprstatIdPod", pod);
        SprStat result = query.getSingleResult();
        return result;
    }
}
