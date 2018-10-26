/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprIshType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprIshTypeFacade extends AbstractFacade<SprIshType> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprIshTypeFacade() {
        super(SprIshType.class);
    }
    
    public SprIshType findByName(String name){
        String qlString = "SELECT s FROM SprIshType s "
                + "WHERE s.sprishtypeName = :sprishtypeName ";
        TypedQuery<SprIshType> query = em.createQuery(qlString, SprIshType.class)
                .setParameter("sprishtypeName", name);
        SprIshType result = query.getSingleResult();
        return result;
    }
}
