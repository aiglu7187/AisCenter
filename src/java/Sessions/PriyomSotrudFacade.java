/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Count.CountStatPmpk;
import Count.StandartCount;
import Entities.Priyom;
import Entities.PriyomSotrud;
import Entities.Sotrud;
import Entities.SotrudDolgn;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author admin_ai
 */
@Stateless
public class PriyomSotrudFacade extends AbstractFacade<PriyomSotrud> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PriyomSotrudFacade() {
        super(PriyomSotrud.class);
    }
    
    public List<PriyomSotrud> findByPriyom(Priyom priyom){
        TypedQuery <PriyomSotrud> query = em.createNamedQuery("PriyomSotrud.findByPriyomId", PriyomSotrud.class)
                .setParameter("priyomId", priyom);
        List<PriyomSotrud> result = query.getResultList();
        return result;
    }
    
    public List <StandartCount> countPmpk(Date dateN, Date dateK, SotrudDolgn sotrudDolgn){
        String qlString = "SELECT r.sprregName, COUNT(p.priyomId) " +
                "FROM SprRegion r, Priyom p, SprUsl u, PriyomSotrud ps " +
                "WHERE p.spruslId = u AND u.spruslId = 1 " +
                "AND p.priyomDate >= :dateN AND p.priyomDate <= :dateK " +
                "AND r = p.sprregId AND ps.priyomId = p AND ps.sotruddolgnId = :sotruddolgnId " +
                "GROUP BY r.sprregName";
        Query query = em.createQuery(qlString)                
                .setParameter("dateN", dateN)
                .setParameter("dateK", dateK)
                .setParameter("sotruddolgnId", sotrudDolgn);
        List resultList = query.getResultList();
        List<StandartCount> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++){
            Object[] o = (Object[])resultList.get(i);
            StandartCount count = new StandartCount();
            count.setTitle((String)o[0]);
            Long l = (Long)o[1];
            count.setCount(l.intValue());
            result.add(count);
        }
        return result;
    }
}
