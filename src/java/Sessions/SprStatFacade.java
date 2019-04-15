/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprStat;
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
public class SprStatFacade extends AbstractFacade<SprStat> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprStatFacade() {
        super(SprStat.class);
    }
    
    public List<SprStat> findByStatV(Integer v){
        TypedQuery <SprStat> query = 
                em.createNamedQuery("SprStat.findBySprstatV", SprStat.class)
                .setParameter("sprstatV", v);
        List <SprStat> result;
        result = query.getResultList();
        return result;
    }
    
    public SprStat findById(Integer id){
        TypedQuery<SprStat> query = 
            em.createNamedQuery("SprStat.findBySprstatId", SprStat.class).setParameter("sprstatId", id);
        SprStat result;
        result = query.getSingleResult();
        return result;
    }   
    
    public SprStat findByName(String name){
        TypedQuery<SprStat> query = 
            em.createNamedQuery("SprStat.findBySprstatName", SprStat.class).setParameter("sprstatName", name);
        SprStat result;
        result = query.getSingleResult();
        return result;
    }  
}
