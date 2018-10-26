/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.ObrObr;
import Entities.SprObr;
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
public class ObrObrFacade extends AbstractFacade<ObrObr> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ObrObrFacade() {
        super(ObrObr.class);
    }
    
    public List<ObrObr> findByMain(SprObr obrMain){
        TypedQuery<ObrObr> query = em.createNamedQuery("ObrObr.findBySprobrIdMain", ObrObr.class)
                .setParameter("sprobrIdMain", obrMain);
        List<ObrObr> result = query.getResultList();
        return result;
    }
    
    public List<ObrObr> findBySoo(SprObr obrSoo){
        TypedQuery<ObrObr> query = em.createNamedQuery("ObrObr.findBySprobrIdSoo", ObrObr.class)
                .setParameter("sprobrIdSoo", obrSoo);
        List<ObrObr> result = query.getResultList();
        return result;
    }
}
