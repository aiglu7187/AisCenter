/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.RegionPlaces;
import Entities.SprPlaces;
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
public class RegionPlacesFacade extends AbstractFacade<RegionPlaces> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RegionPlacesFacade() {
        super(RegionPlaces.class);
    }
    
    public List<SprPlaces> findByRegion(SprRegion reg){
        String qlString = "SELECT pl FROM RegionPlaces r, SprPlaces pl "
                + "WHERE r.sprregId = :sprregId AND r.sprplaceId = pl ";
        TypedQuery<SprPlaces> query = em.createQuery(qlString, SprPlaces.class)
                .setParameter("sprregId", reg);
        List<SprPlaces> result = query.getResultList();
        return result;
    }
    
}
