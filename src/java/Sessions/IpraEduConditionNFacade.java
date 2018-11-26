/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.IpraEduConditionN;
import Entities.IpraPerechenN;
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
public class IpraEduConditionNFacade extends AbstractFacade<IpraEduConditionN> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IpraEduConditionNFacade() {
        super(IpraEduConditionN.class);
    }
    
    public List<IpraEduConditionN> findByIpraPerechen(IpraPerechenN ipraperechennId){
        TypedQuery<IpraEduConditionN> query = em.createNamedQuery("IpraEduConditionN.findByIpraperechennId", IpraEduConditionN.class)
                .setParameter("ipraperechennId", ipraperechennId);
        List<IpraEduConditionN> result = query.getResultList();
        return result;
    }
}
