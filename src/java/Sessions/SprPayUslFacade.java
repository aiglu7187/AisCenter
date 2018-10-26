/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprPayUsl;
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
public class SprPayUslFacade extends AbstractFacade<SprPayUsl> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprPayUslFacade() {
        super(SprPayUsl.class);
    }
    
    public List<SprPayUsl> findAllLessonUsl(){
        TypedQuery<SprPayUsl> query = em.createNamedQuery("SprPayUsl.findBySprpayuslLesson", SprPayUsl.class)
                .setParameter("sprpayuslLesson", 1);
        List<SprPayUsl> result = query.getResultList();
        return result;
    }
    
    public List<SprPayUsl> findUslByGroup(Integer group){
        TypedQuery<SprPayUsl> query = em.createNamedQuery("SprPayUsl.findBySprpayuslGroup", SprPayUsl.class)
                .setParameter("sprpayuslGroup", group);
        List<SprPayUsl> result = query.getResultList();
        return result;
    }
    
    public SprPayUsl findById(Integer id){
        TypedQuery<SprPayUsl> query = em.createNamedQuery("SprPayUsl.findBySprpayuslId", SprPayUsl.class)
                .setParameter("sprpayuslId", id);
        SprPayUsl result = query.getSingleResult();
        return result;
    }
}
