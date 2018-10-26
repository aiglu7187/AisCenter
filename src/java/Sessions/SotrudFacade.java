/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Sotrud;
import Entities.SprDolgn;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author admin_ai
 */
@Stateless
public class SotrudFacade extends AbstractFacade<Sotrud> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SotrudFacade() {
        super(Sotrud.class);
    }
    
    public List<Sotrud> findByDolgn(SprDolgn dolgn) {
        String qlString = "SELECT s "
                + "FROM Sotrud s, SotrudDolgn sd "
                + "WHERE s = sd.sotrudId AND sd.sprdolgnId = :sprdolgnId";
        TypedQuery <Sotrud> query = em.createQuery(qlString, Sotrud.class)
                .setParameter("sprdolgnId", dolgn);
        List<Sotrud> result;
        result = query.getResultList();
        return result;
    }
    
    public List<Sotrud> findByDolgnAct(SprDolgn dolgn) {
        String qlString = "SELECT s "
                + "FROM Sotrud s, SotrudDolgn sd "
                + "WHERE s = sd.sotrudId AND sd.sprdolgnId = :dolgn "
                + "AND sd.sotruddolgnActive = 1 ";
        TypedQuery <Sotrud> query = 
                em.createQuery(qlString, Sotrud.class).setParameter("dolgn", dolgn);
        List<Sotrud> result;
        result = query.getResultList();
        return result;
    }
    
    public Sotrud findById(Integer sotrId) {
        TypedQuery <Sotrud> query = 
                em.createNamedQuery("Sotrud.findBySotrudId", Sotrud.class).setParameter("sotrudId", sotrId);
        Sotrud result;
        result = query.getSingleResult();
        return result;
    }
    
    public List<Sotrud> findSpec(){
        Integer sprdolgnType = 2;
        TypedQuery <Sotrud> query = 
                em.createNamedQuery("Sotrud.findSpec", Sotrud.class).setParameter("sprdolgnType", sprdolgnType);
        List<Sotrud> result;
        result = query.getResultList();
        return result;
    }
    
    public List<Sotrud> findByFam(String fam){
        String qlString = "SELECT s FROM Sotrud s "
                + "WHERE UPPER(s.sotrudFam) LIKE UPPER(:sotrudFam) ";
        TypedQuery <Sotrud> query = em.createQuery(qlString, Sotrud.class)
                .setParameter("sotrudFam", fam + "%");
        List<Sotrud> result = query.getResultList();
        return result;
    }
    
    public List<Sotrud> findByDolgnFamAct(SprDolgn dolgn, String fam){
        String qlString = "SELECT s FROM Sotrud s, SotrudDolgn sd "
                + "WHERE UPPER(s.sotrudFam) LIKE UPPER(:sotrudFam) "
                + "AND sd.sotrudId = s AND "
                + "sd.sprdolgnId = :sprdolgnId "
                + "AND sd.sotruddolgnActive = 1 ";
        TypedQuery <Sotrud> query = em.createQuery(qlString, Sotrud.class)
               .setParameter("sprdolgnId", dolgn)
               .setParameter("sotrudFam", fam + "%");
        List<Sotrud> result = query.getResultList();
        return result;
    }
    
    public List<Sotrud> findAllSotrud(){
        TypedQuery<Sotrud> query = em.createNamedQuery("Sotrud.findAll", Sotrud.class);
        List<Sotrud> result = query.getResultList();
        return result;
    }
    
    public List<Sotrud> findAllAct(){
        TypedQuery<Sotrud> query = em.createNamedQuery("Sotrud.findAllAct", Sotrud.class);
        List<Sotrud> result = query.getResultList();
        return result;
    }
}
