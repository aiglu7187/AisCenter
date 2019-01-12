/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Sotrud;
import Entities.SotrudDolgn;
import Entities.SprDolgn;
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
public class SotrudDolgnFacade extends AbstractFacade<SotrudDolgn> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SotrudDolgnFacade() {
        super(SotrudDolgn.class);
    }

    public List<SotrudDolgn> findBySotrud(Sotrud sotrud) {
        TypedQuery<SotrudDolgn> query = em.createNamedQuery("SotrudDolgn.findBySotrudId", SotrudDolgn.class)
                .setParameter("sotrudId", sotrud);
        List<SotrudDolgn> result = query.getResultList();
        return result;
    }

    public List<SotrudDolgn> findByDolgnAct(SprDolgn dolgn) {
        String qlString = "SELECT s FROM SotrudDolgn s "
                + "WHERE s.sprdolgnId = :sprdolgnId "
                + "AND s.sotruddolgnActive = 1 ";
        TypedQuery<SotrudDolgn> query = em.createQuery(qlString, SotrudDolgn.class)
                .setParameter("sprdolgnId", dolgn);
        List<SotrudDolgn> result = query.getResultList();
        return result;
    }
    
    public List<SotrudDolgn> findByDolgnSotrAct(SprDolgn dolgn) {
        String qlString = "SELECT s FROM SotrudDolgn s, Sotrud sotr "
                + "WHERE s.sprdolgnId = :sprdolgnId AND sotr = s.sotrudId "
                + "AND sotr.sotrudActive = 1 ";
        TypedQuery<SotrudDolgn> query = em.createQuery(qlString, SotrudDolgn.class)
                .setParameter("sprdolgnId", dolgn);
        List<SotrudDolgn> result = query.getResultList();
        return result;
    }

    public List<SotrudDolgn> findActSotrudByDolgnAct(SprDolgn dolgn) {
        String qlString = "SELECT sd FROM SotrudDolgn sd, Sotrud s "
                + "WHERE sd.sprdolgnId = :sprdolgnId "
                + "AND sd.sotruddolgnActive = 1 "
                + "AND sd.sotrudId = s AND s.sotrudActive = 1 ";
        TypedQuery<SotrudDolgn> query = em.createQuery(qlString, SotrudDolgn.class)
                .setParameter("sprdolgnId", dolgn);
        List<SotrudDolgn> result = query.getResultList();
        return result;
    }

    public List<SotrudDolgn> findBySotrudAct(Sotrud sotrud) {
        String qlString = "SELECT s FROM SotrudDolgn s "
                + "WHERE s.sotrudId = :sotrudId "
                + "AND s.sotruddolgnActive = 1 ";
        TypedQuery<SotrudDolgn> query = em.createQuery(qlString, SotrudDolgn.class)
                .setParameter("sotrudId", sotrud);
        List<SotrudDolgn> result = query.getResultList();
        return result;
    }

    public SotrudDolgn findById(Integer id) {
        TypedQuery<SotrudDolgn> query = em.createNamedQuery("SotrudDolgn.findBySotruddolgnId", SotrudDolgn.class)
                .setParameter("sotruddolgnId", id);
        SotrudDolgn result = query.getSingleResult();
        return result;
    }

}
