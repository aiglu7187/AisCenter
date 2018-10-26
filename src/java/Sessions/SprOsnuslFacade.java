/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprOsnusl;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author admin_ai
 */
@Stateless
public class SprOsnuslFacade extends AbstractFacade<SprOsnusl> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprOsnuslFacade() {
        super(SprOsnusl.class);
    }
    
    public SprOsnusl findById(Integer uslId) {
        TypedQuery <SprOsnusl> query = 
                em.createNamedQuery("SprOsnusl.findBySprosnuslId", SprOsnusl.class).setParameter("sprosnuslId", uslId);
        SprOsnusl result;
        result = query.getSingleResult();
        return result;
    }
}
