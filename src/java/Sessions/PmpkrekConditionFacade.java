/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.PmpkrekCondition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aiglu
 */
@Stateless
public class PmpkrekConditionFacade extends AbstractFacade<PmpkrekCondition> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmpkrekConditionFacade() {
        super(PmpkrekCondition.class);
    }
    
}
