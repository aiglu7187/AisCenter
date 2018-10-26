/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Ped;
import Entities.PedReg;
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
public class PedRegFacade extends AbstractFacade<PedReg> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedRegFacade() {
        super(PedReg.class);
    }
    
    public List<PedReg> findByPed(Ped ped){
        TypedQuery query = em.createNamedQuery("PedReg.findByPedId", PedReg.class)
                .setParameter("pedId", ped);
        List<PedReg> result = query.getResultList();
        return result;
    }
}
