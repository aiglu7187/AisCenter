/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Children;
import Entities.Pmpk;
import Entities.Priyom;
import Entities.PriyomClient;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.websocket.CloseReason;

/**
 *
 * @author Aiglu
 */
@Stateless
public class PmpkFacade extends AbstractFacade<Pmpk> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmpkFacade() {
        super(Pmpk.class);
    }   
    
    public List<Pmpk> findByPrcl(PriyomClient prcl){
        TypedQuery <Pmpk> query = em.createNamedQuery("Pmpk.findByPrclId", Pmpk.class)
                .setParameter("prclId", prcl);
        List<Pmpk> result = query.getResultList();
        return result;
    }
    
    public List<Pmpk> findByPmpkNp(String np){
        String qlString = "SELECT p FROM Pmpk p WHERE p.pmpkNp LIKE :pmpkNp";
        TypedQuery<Pmpk> query = em.createQuery(qlString, Pmpk.class)
                .setParameter("pmpkNp", np);
        List<Pmpk> result = query.getResultList();
        return result;
    }
    
    public Pmpk findById(Integer id){
        TypedQuery<Pmpk> query = em.createNamedQuery("Pmpk.findByPmpkId", Pmpk.class)
                .setParameter("pmpkId", id);
        Pmpk result = query.getSingleResult();
        return result;
    }
    
    public List<Pmpk> findPmpkWithVar(){
        String qlString = "SELECT pk FROM Pmpk pk WHERE (pk.sprobrvarId IS NOT NULL)";
        TypedQuery<Pmpk> query = em.createQuery(qlString, Pmpk.class);
        List<Pmpk> result = query.getResultList();
        return result;
    }
    
    public List<Pmpk> findPmpkByChild(Children child){
        String qlString = "SELECT pk "
                + "FROM Pmpk pk, PriyomClient pc, Priyom p, SprUsl u "
                + "WHERE pk.prclId = pc AND pc.priyomId = p AND p.spruslId = u "
                + "AND u.spruslPmpk = 1 AND pc.clientId = :clientId AND pc.prclKatcl = :kat ";
        TypedQuery<Pmpk> query = em.createQuery(qlString, Pmpk.class)
                .setParameter("clientId", child.getChildId())
                .setParameter("kat", "children");
        List<Pmpk> result = query.getResultList();
        return result;
    }
    
    public List<Pmpk> findAllPmpk(){
        String qlString = "SELECT pk FROM Pmpk pk ";
        TypedQuery<Pmpk> query = em.createQuery(qlString, Pmpk.class);
        query.setMaxResults(50);
        List<Pmpk> result = query.getResultList();
        return result;
    }
    
    public List<Pmpk> findPmpkByChildAndDate(Children child, Date date1, Date date2){
        String qlString = "SELECT pk "
                + "FROM Pmpk pk, PriyomClient pc, Priyom p, SprUsl u "
                + "WHERE pk.prclId = pc AND pc.priyomId = p AND p.spruslId = u "
                + "AND u.spruslPmpk = 1 AND pc.clientId = :clientId AND pc.prclKatcl = :kat "
                + "AND p.priyomDate >= :date1 AND p.priyomDate <= :date2 ";
        TypedQuery<Pmpk> query = em.createQuery(qlString, Pmpk.class)
                .setParameter("clientId", child.getChildId())
                .setParameter("kat", "children")
                .setParameter("date1", date1)
                .setParameter("date2", date2);
        List<Pmpk> result = query.getResultList();
        return result;
    }
}
