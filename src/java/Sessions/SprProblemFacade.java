/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprProblem;
import Entities.SprProblemType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprProblemFacade extends AbstractFacade<SprProblem> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprProblemFacade() {
        super(SprProblem.class);
    }
    
    public List<SprProblem> findByProblemType(SprProblemType sprproblemtypeId){
        TypedQuery <SprProblem> query = 
                em.createNamedQuery("SprProblem.findBySprproblemtypeId", SprProblem.class).setParameter("sprproblemtypeId", sprproblemtypeId);
        List<SprProblem> result;
        result = query.getResultList();
        return result;
    }
    
    public SprProblem findById(Integer id) {
        TypedQuery <SprProblem> query = 
                em.createNamedQuery("SprProblem.findBySprproblemId", SprProblem.class).setParameter("sprproblemId", id);
        SprProblem result;
        result = query.getSingleResult();
        return result;
    }
    
    public SprProblem findByKod(Integer kod) {
        TypedQuery <SprProblem> query = 
                em.createNamedQuery("SprProblem.findBySprproblemKod", SprProblem.class)
                        .setParameter("sprproblemKod", kod);
        SprProblem result;
        result = query.getSingleResult();
        return result;
    }
}
