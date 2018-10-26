/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprPeddolg;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprPeddolgFacade extends AbstractFacade<SprPeddolg> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprPeddolgFacade() {
        super(SprPeddolg.class);
    }
    
    public SprPeddolg findById(Integer id){
        TypedQuery <SprPeddolg> query =
                em.createNamedQuery("SprPeddolg.findBySprpeddolgId", SprPeddolg.class).setParameter("sprpeddolgId", id);
        SprPeddolg result = query.getSingleResult();
        return result;
    }
}
