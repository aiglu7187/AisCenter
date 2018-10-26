/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprUsl;
import Entities.UslDolgntype;
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
public class UslDolgntypeFacade extends AbstractFacade<UslDolgntype> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UslDolgntypeFacade() {
        super(UslDolgntype.class);
    }
    
    public List<UslDolgntype> findByUsl(SprUsl id){
        TypedQuery <UslDolgntype> query = 
                em.createNamedQuery("UslDolgntype.findBySpruslId", UslDolgntype.class)
                .setParameter("spruslId", id);
        List<UslDolgntype> result;
        result = query.getResultList();
        return result;
    }
    
}
