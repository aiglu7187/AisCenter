/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprOrg;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprOrgFacade extends AbstractFacade<SprOrg> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprOrgFacade() {
        super(SprOrg.class);
    }
    
    public SprOrg findById(Integer id){
        TypedQuery <SprOrg> query =
                em.createNamedQuery("SprOrg.findBySprorgId", SprOrg.class).setParameter("sprorgId", id);
        SprOrg result = query.getSingleResult();
        return result;
    }
    
}
