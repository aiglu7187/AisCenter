/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprMseExp;
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
public class SprMseExpFacade extends AbstractFacade<SprMseExp> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprMseExpFacade() {
        super(SprMseExp.class);
    }
    
    public List<SprMseExp> findAllSprMseExp(){
        TypedQuery<SprMseExp> query = em.createNamedQuery("SprMseExp.findAll", SprMseExp.class);
        List<SprMseExp> result = query.getResultList();
        return result;
    }
    
    public SprMseExp findById(Integer id){
        TypedQuery<SprMseExp> query = em.createNamedQuery("SprMseExp.findBySprmseexpId", SprMseExp.class)
                .setParameter("sprmseexpId", id);
        SprMseExp result = query.getSingleResult();
        return result;
    }
}
