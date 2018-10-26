/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprVar;
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
public class SprVarFacade extends AbstractFacade<SprVar> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprVarFacade() {
        super(SprVar.class);
    }
    
    public List<SprVar> findByName(String name){
        TypedQuery <SprVar> query = em.createNamedQuery("SprVar.findBySprvarName", SprVar.class)
                .setParameter("sprvarName", name);
        List<SprVar> result = query.getResultList();
        return result;
    }
}
