/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprObr;
import Entities.SprObrVar;
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
public class SprObrVarFacade extends AbstractFacade<SprObrVar> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprObrVarFacade() {
        super(SprObrVar.class);
    }
    
    public List<SprObrVar> findVarByObr(SprObr obr){
        TypedQuery <SprObrVar> query = em.createNamedQuery("SprObrVar.findBySprobrId", SprObrVar.class)
                .setParameter("sprobrId", obr);
        List<SprObrVar> result = query.getResultList();
        return result;
    }
    
    public SprObrVar findById(Integer id){
        TypedQuery <SprObrVar> query = em.createNamedQuery("SprObrVar.findBySprobrvarId", SprObrVar.class)
                .setParameter("sprobrvarId", id);
        SprObrVar result = query.getSingleResult();
        return result;
    }
    
    public List<SprObrVar> findByName(String name){
        TypedQuery <SprObrVar> query = em.createNamedQuery("SprObrVar.findBySprobrvarName", SprObrVar.class)
                .setParameter("sprobrvarName", name);
        List<SprObrVar> result = query.getResultList();
        return result;
    }
   
}
