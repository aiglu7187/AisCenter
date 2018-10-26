/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprObrSpec;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprObrSpecFacade extends AbstractFacade<SprObrSpec> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprObrSpecFacade() {
        super(SprObrSpec.class);
    }
    
    public SprObrSpec findById(Integer id){        
        TypedQuery<SprObrSpec> query = em.createNamedQuery("SprObrSpec.findBySprobrspecId", SprObrSpec.class)
                .setParameter("sprobrspecId", id);
        SprObrSpec result = query.getSingleResult();
        return result;
    }
    
}
