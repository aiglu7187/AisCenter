/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.OtherpmpkRegion;
import Entities.SprOtherPmpk;
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
public class OtherpmpkRegionFacade extends AbstractFacade<OtherpmpkRegion> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OtherpmpkRegionFacade() {
        super(OtherpmpkRegion.class);
    }

    public OtherpmpkRegion findByRegion(SprRegion sprregId) {
        TypedQuery<OtherpmpkRegion> query = em.createNamedQuery("OtherpmpkRegion.findBySprregId", OtherpmpkRegion.class)
                .setParameter("sprregId", sprregId);        
        OtherpmpkRegion result = query.getSingleResult();
        return result;
    }
    
    public List<SprRegion> findRegionsByOtherPmpk(SprOtherPmpk sprOtherPmpk) {
        String qlString = "SELECT r FROM SprRegion r, OtherpmpkRegion o "
                + "WHERE o.sprregId = r AND o.sprotherpmpkId = :sprotherpmpkId ";
        TypedQuery<SprRegion> query = em.createQuery(qlString, SprRegion.class)
                .setParameter("sprotherpmpkId", sprOtherPmpk);        
        List<SprRegion> result = query.getResultList();
        return result;
    }
}
