/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.PayuslDolgntype;
import Entities.SprPayUsl;
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
public class PayuslDolgntypeFacade extends AbstractFacade<PayuslDolgntype> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PayuslDolgntypeFacade() {
        super(PayuslDolgntype.class);
    }
    
    public List<PayuslDolgntype> findByUsl(SprPayUsl usl){
        TypedQuery query = em.createNamedQuery("PayuslDolgntype.findBySprpayuslId", PayuslDolgntype.class)
                .setParameter("sprpayuslId", usl);
        List<PayuslDolgntype> result = query.getResultList();
        return result;
    }
}
