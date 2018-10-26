/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprRekomend;
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
public class SprRekomendFacade extends AbstractFacade<SprRekomend> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprRekomendFacade() {
        super(SprRekomend.class);
    }
    
    public List<SprRekomend> findAllRekomend(){
        TypedQuery <SprRekomend> query = em.createNamedQuery("SprRekomend.findAll", SprRekomend.class);
        List <SprRekomend> result = query.getResultList();
        return result;
    }
    
    public SprRekomend findById(Integer id){
        TypedQuery <SprRekomend> query = em.createNamedQuery("SprRekomend.findBySprrekId", SprRekomend.class)
                .setParameter("sprrekId", id);
        SprRekomend result = query.getSingleResult();
        return result;
    }
}
