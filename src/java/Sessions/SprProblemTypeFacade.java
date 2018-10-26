/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprProblemType;
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
public class SprProblemTypeFacade extends AbstractFacade<SprProblemType> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprProblemTypeFacade() {
        super(SprProblemType.class);
    }
    
    public SprProblemType findById(Integer id){
        TypedQuery <SprProblemType> query = 
                em.createNamedQuery("SprProblemType.findBySprproblemtypeId", SprProblemType.class).setParameter("sprproblemtypeId", id);
        SprProblemType result;
        result = query.getSingleResult();
        return result;
    }
    
    public List<SprProblemType> findAllType() {
        TypedQuery <SprProblemType> query = 
                em.createNamedQuery("SprProblemType.findAll", SprProblemType.class);
        List <SprProblemType> result;
        result = query.getResultList();
        return result;
    }
    
}
