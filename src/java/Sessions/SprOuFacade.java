/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprOu;
import Entities.SprOuType;
import Entities.SprRegion;
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
public class SprOuFacade extends AbstractFacade<SprOu> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprOuFacade() {
        super(SprOu.class);
    }
    
    public List<SprOu> findBySprOuType(SprOuType type){
        TypedQuery<SprOu> query = em.createNamedQuery("SprOu.findBySproutypeId", SprOu.class)
                .setParameter("sproutypeId", type);
        List<SprOu> result = query.getResultList();
        return result;
    }
    
    public List<SprOu> findBySprRegion(SprRegion reg){
        TypedQuery<SprOu> query = em.createNamedQuery("SprOu.findBySprregId", SprOu.class)
                .setParameter("sprregId", reg);
        List<SprOu> result = query.getResultList();
        return result;
    }
    
    public List<SprOu> findBySprOuTypeAndRegion(SprOuType type, SprRegion reg){
        String qlString = "SELECT s FROM SprOu s "
                + "WHERE s.sproutypeId = :sproutypeId AND s.sprregId = :sprregId ";
        TypedQuery<SprOu> query = em.createQuery(qlString, SprOu.class)
                .setParameter("sproutypeId", type)
                .setParameter("sprregId", reg);
        List<SprOu> result = query.getResultList();
        return result;
    }
    
}
