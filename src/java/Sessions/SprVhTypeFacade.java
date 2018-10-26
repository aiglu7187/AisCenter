/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprVhType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprVhTypeFacade extends AbstractFacade<SprVhType> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprVhTypeFacade() {
        super(SprVhType.class);
    }
    
    public SprVhType findByName(String name){
        String qlString = "SELECT s FROM SprVhType s "
                + "WHERE s.sprvhtypeName = :sprvhtypeName ";
        TypedQuery<SprVhType> query = em.createQuery(qlString, SprVhType.class)
                .setParameter("sprvhtypeName", name);
        SprVhType result = query.getSingleResult();
        return result;
    }
}
