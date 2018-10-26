/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprCorr;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class SprCorrFacade extends AbstractFacade<SprCorr> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprCorrFacade() {
        super(SprCorr.class);
    }
    
    public SprCorr findByName(String name){
        String qlString = "SELECT s FROM SprCorr s "
                + "WHERE s.sprcorrName = :sprcorrName ";
        TypedQuery<SprCorr> query = em.createQuery(qlString, SprCorr.class)
                .setParameter("sprcorrName", name);
        SprCorr result = query.getSingleResult();
        return result;
    }
    
    public SprCorr findByTableAndId(String tableName, Integer id){
        String qlString = "SELECT s FROM SprCorr s "
                + "WHERE s.sprcorrTable = :sprcorrTable AND s.sprcorrCorrId = :sprcorrCorrId ";
        TypedQuery<SprCorr> query = em.createQuery(qlString, SprCorr.class)
                .setParameter("sprcorrTable", tableName)
                .setParameter("sprcorrCorrId", id);
        SprCorr result = query.getSingleResult();
        return result;
    }
}
