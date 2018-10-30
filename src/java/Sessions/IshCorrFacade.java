/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Ipra18N;
import Entities.IshCorr;
import Entities.SprIshType;
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
public class IshCorrFacade extends AbstractFacade<IshCorr> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IshCorrFacade() {
        super(IshCorr.class);
    }
    
    public List<IshCorr> findByTypeNoNomer(SprIshType type, Ipra18N ipra){
        String qlString = "SELECT ic FROM IshCorr ic, IpraIshcorr iic "
                + "WHERE ic.sprishtypeId = :type AND ic.ishcorrN IS NULL "
                + "AND iic.ishcorrId = ic AND iic.ipra18Id = :ipra ";
        TypedQuery<IshCorr> query = em.createQuery(qlString, IshCorr.class)
                .setParameter("type", type)
                .setParameter("ipra", ipra);
        List<IshCorr> result = query.getResultList();
        return result;
    }
    
    public List<IshCorr> findByIpra(Ipra18N ipra){
        String qlString = "SELECT ic FROM IshCorr ic, IpraIshcorr iic "
                + "WHERE iic.ishcorrId = ic AND iic.ipra18Id = :ipra ";
        TypedQuery<IshCorr> query = em.createQuery(qlString, IshCorr.class)
                .setParameter("ipra", ipra);
        List<IshCorr> result = query.getResultList();
        return result;
    }
    
}
