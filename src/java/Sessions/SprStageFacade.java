/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprStage;
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
public class SprStageFacade extends AbstractFacade<SprStage> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprStageFacade() {
        super(SprStage.class);
    }
    
    public List<SprStage> findAllStages(){
        TypedQuery<SprStage> query = em.createNamedQuery("SprStage.findAll", SprStage.class);
        List<SprStage> result = query.getResultList();
        return result;              
    }
    
    public SprStage findById(Integer id){
        TypedQuery<SprStage> query = em.createNamedQuery("SprStage.findBySprstageId", SprStage.class)
                .setParameter("sprstageId", id);
        SprStage result = query.getSingleResult();
        return result;
    }
    
    public SprStage findByName(String name){
        TypedQuery<SprStage> query = em.createNamedQuery("SprStage.findBySprstageName", SprStage.class)
                .setParameter("sprstageName", name);
        SprStage result = query.getSingleResult();
        return result;
    }
}
