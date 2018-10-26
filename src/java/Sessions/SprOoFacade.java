/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprOo;
import Entities.SprRegion;
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
public class SprOoFacade extends AbstractFacade<SprOo> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprOoFacade() {
        super(SprOo.class);
    }
    
    public List<SprOo> findAllOo(){
        String qlString = "SELECT o FROM SprOo o";
        TypedQuery <SprOo> query = em.createQuery(qlString, SprOo.class);
        List<SprOo> result = query.getResultList();
        return result;
    }
    
    public List<SprOo> findByRegion(SprRegion reg){
        String qlString = "SELECT o FROM SprOo o " +
                "WHERE o.sprregId = :reg ORDER BY o.sprooName ";
        TypedQuery<SprOo> query = em.createQuery(qlString, SprOo.class)
                .setParameter("reg", reg);
        List<SprOo> result = query.getResultList();
        return result;
    }
    
    public SprOo findById (Integer id){
        TypedQuery<SprOo> query = em.createNamedQuery("SprOo.findBySprooId", SprOo.class)
                .setParameter("sprooId", id);
        SprOo result = query.getSingleResult();
        return result;        
    }
    
    public SprOo findByName (String name, SprRegion sprregId){
        TypedQuery<SprOo> query = em.createNamedQuery("SprOo.findBySprooNameAndSprregId", SprOo.class)
                .setParameter("sprooName", name)
                .setParameter("sprregId", sprregId);
        SprOo result = query.getSingleResult();
        return result;        
    }
}
