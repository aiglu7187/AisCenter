/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprObrVid;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprObrVidFacade extends AbstractFacade<SprObrVid> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprObrVidFacade() {
        super(SprObrVid.class);
    }
    
    public SprObrVid findById(Integer id){        
        TypedQuery<SprObrVid> query = em.createNamedQuery("SprObrVid.findBySprobrvidId", SprObrVid.class)
                .setParameter("sprobrvidId", id);
        SprObrVid result = query.getSingleResult();
        return result;
    }
    
}
