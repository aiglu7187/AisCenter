/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprStat;
import Entities.SprUsl;
import Entities.UslStatus;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class UslStatusFacade extends AbstractFacade<UslStatus> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UslStatusFacade() {
        super(UslStatus.class);
    }
    
    public UslStatus findByUslAndStat (SprUsl spruslId, SprStat sprstatId){
        TypedQuery<UslStatus> query = em.createNamedQuery("UslStatus.findBySpruslIdAndSprstatId", UslStatus.class)
                .setParameter("spruslId", spruslId)
                .setParameter("sprstatId", sprstatId);
        UslStatus result = query.getSingleResult();
        return result;
    }
}
