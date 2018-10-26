/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprOtherPmpk;
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
public class SprOtherPmpkFacade extends AbstractFacade<SprOtherPmpk> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprOtherPmpkFacade() {
        super(SprOtherPmpk.class);
    }
        
    public List<SprOtherPmpk> findAllSprOtherPmpk(){
        TypedQuery<SprOtherPmpk> query = em.createNamedQuery("SprOtherPmpk.findAll", SprOtherPmpk.class);
        List<SprOtherPmpk> result = query.getResultList();
        return result;
    }
    
    public SprOtherPmpk findById(Integer id){
        TypedQuery<SprOtherPmpk> query = em.createNamedQuery("SprOtherPmpk.findBySprotherpmpkId", SprOtherPmpk.class)
                .setParameter("sprotherpmpkId", id);
        SprOtherPmpk result = query.getSingleResult();
        return result;
    }
}
