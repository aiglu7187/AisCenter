/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Ped;
import Entities.PedOldfio;
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
public class PedOldfioFacade extends AbstractFacade<PedOldfio> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedOldfioFacade() {
        super(PedOldfio.class);
    }
    
    public List<PedOldfio> findByPedId (Ped pedId){
        TypedQuery<PedOldfio> query = em.createNamedQuery("PedOldfio.findByPedId", PedOldfio.class)
                .setParameter("pedId", pedId);
        List<PedOldfio> result = query.getResultList();
        return result;
    }
    
}
