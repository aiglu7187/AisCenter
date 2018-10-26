/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Count.CountProblem;
import Count.CountProblemUsl;
import Entities.Priyom;
import Entities.PriyomProblem;
import java.math.BigDecimal;
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
 * @author Aiglu
 */
@Stateless
public class PriyomProblemFacade extends AbstractFacade<PriyomProblem> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PriyomProblemFacade() {
        super(PriyomProblem.class);
    }
    
    public List<PriyomProblem> findByPriyom(Priyom priyom){
        TypedQuery<PriyomProblem> query = em.createNamedQuery("PriyomProblem.findByPriyomId", PriyomProblem.class)
                .setParameter("priyomId", priyom);
        List<PriyomProblem> result = query.getResultList();
        return result;
    }
    
    public List<CountProblem> countProblemAll(Date dateN, Date dateK){
        String qlNString = "select count(*), kod, problem, problemtypekod, problemtype " +
            "from ( " +
            "select distinct SPR_PROBLEM.SPRPROBLEM_NAME as problem, PRIYOM_CLIENT.CLIENT_ID, PRIYOM_CLIENT.PRCL_KATCL, SPR_PROBLEM_TYPE.SPRPROBLEMTYPE_KOD as problemtypekod, " +
            "SPR_PROBLEM.SPRPROBLEM_KOD as kod, SPR_PROBLEM_TYPE.SPRPROBLEMTYPE_NAME as problemtype " +
            "from priyom_client, spr_problem, priyom_problem, priyom, spr_problem_type " +
            "where PRIYOM.PRIYOM_ID = PRIYOM_CLIENT.PRIYOM_ID and PRIYOM_PROBLEM.PRIYOM_ID = PRIYOM.PRIYOM_ID and SPR_PROBLEM.SPRPROBLEM_ID = PRIYOM_PROBLEM.SPRPROBLEM_ID " +
            "and SPR_PROBLEM.SPRPROBLEMTYPE_ID = SPR_PROBLEM_TYPE.SPRPROBLEMTYPE_ID " +
            "and PRIYOM.PRIYOM_DATE >= ? and PRIYOM.PRIYOM_DATE <= ? " +
            ")\n" +
            "group by kod, problem, problemtypekod, problemtype " +
            "order by problemtypekod, kod ";
        Query nQuery = em.createNativeQuery(qlNString)
                .setParameter(1, dateN)
                .setParameter(2, dateK);
        List resultList = nQuery.getResultList();
        List<CountProblem> result = new ArrayList<CountProblem>();
        for (int i = 0; i < resultList.size(); i++){
            Object[] o = (Object[])resultList.get(i);
            CountProblem count = new CountProblem();
            BigDecimal d = (BigDecimal)o[0];
            Long l = d.longValue();
            count.setCount(l.intValue());
            d = (BigDecimal)o[1];
            count.setProblem(d.toString() + " " + (String)o[2]);
            d = (BigDecimal)o[3];
            l = d.longValue();
            if (l < 10){
                count.setProblemType("0" + d.toString() + " " +(String)o[4]);
            }
            else {
                count.setProblemType(d.toString() + " " +(String)o[4]);
            }
            l = d.longValue();
            count.setPtKod(l.intValue());
            result.add(count);
        }
        return result;
    }
    
    public List<CountProblemUsl> countProblemUsl(Date dateN, Date dateK){
        String qlNString = "select count(*), kod, problem, problemtypekod, problemtype, usl " +
            "from ( " +
            "select distinct SPR_PROBLEM.SPRPROBLEM_NAME as problem, PRIYOM_CLIENT.CLIENT_ID, PRIYOM_CLIENT.PRCL_KATCL, SPR_PROBLEM_TYPE.SPRPROBLEMTYPE_KOD as problemtypekod, " +
            "SPR_PROBLEM.SPRPROBLEM_KOD as kod, SPR_PROBLEM_TYPE.SPRPROBLEMTYPE_NAME as problemtype, SPR_USL.SPRUSL_NAME as usl " +
            "from priyom_client, spr_problem, priyom_problem, priyom, spr_problem_type, spr_usl " +
            "where PRIYOM.PRIYOM_ID = PRIYOM_CLIENT.PRIYOM_ID and PRIYOM_PROBLEM.PRIYOM_ID = PRIYOM.PRIYOM_ID and SPR_PROBLEM.SPRPROBLEM_ID = PRIYOM_PROBLEM.SPRPROBLEM_ID " +
            "and SPR_PROBLEM.SPRPROBLEMTYPE_ID = SPR_PROBLEM_TYPE.SPRPROBLEMTYPE_ID and SPR_USL.SPRUSL_ID = PRIYOM.SPRUSL_ID " +
            "and PRIYOM.PRIYOM_DATE >= ? and PRIYOM.PRIYOM_DATE <= ? " +
            ") " +
            "group by kod, problem, problemtypekod, problemtype, usl " +
            "order by usl, problemtypekod, kod ";
        Query nQuery = em.createNativeQuery(qlNString)
                .setParameter(1, dateN)
                .setParameter(2, dateK);
        List resultList = nQuery.getResultList();
        List<CountProblemUsl> result = new ArrayList<CountProblemUsl>();
        for (int i = 0; i < resultList.size(); i++){
            Object[] o = (Object[])resultList.get(i);
            CountProblemUsl count = new CountProblemUsl();
            BigDecimal d = (BigDecimal)o[0];
            Long l = d.longValue();
            count.setCount(l.intValue());
            d = (BigDecimal)o[1];
            count.setProblem(d.toString() + " " + (String)o[2]);
            d = (BigDecimal)o[3];
            l = d.longValue();
            if (l < 10){
                count.setProblemType("0" + d.toString() + " " +(String)o[4]);
            }
            else {
                count.setProblemType(d.toString() + " " +(String)o[4]);
            }
            l = d.longValue();
            count.setPtKod(l.intValue());
            count.setUsl((String)o[5]);
            result.add(count);
        }
        return result;
    }
}
