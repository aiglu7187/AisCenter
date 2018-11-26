/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Ipra18N;
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
public class IpraPerechenNFacade extends AbstractFacade<IpraPerechenN> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IpraPerechenNFacade() {
        super(IpraPerechenN.class);
    }
    
    public List<IpraPerechenN> findNoPerechen(){
        String qlString = "SELECT i FROM IpraPerechenN i, IshCorr ic "
                + "WHERE i.ishcorrId = ic AND ic.ishcorrN IS NULL ";
        TypedQuery<IpraPerechenN> query = em.createQuery(qlString, IpraPerechenN.class);
        List<IpraPerechenN> result = query.getResultList();
        return result;
    }
    
    public List<IpraPerechenN> findByIpra18(Ipra18N ipra){        
        TypedQuery<IpraPerechenN> query = em.createNamedQuery("IpraPerechenN.findByIpra18Id", IpraPerechenN.class)
                .setParameter("ipra18Id", ipra);
        List<IpraPerechenN> result = query.getResultList();
        return result;
    }
    
    public IpraPerechenN findById(Integer id){        
        TypedQuery<IpraPerechenN> query = em.createNamedQuery("IpraPerechenN.findByIpraperechennId", IpraPerechenN.class)
                .setParameter("ipraperechennId", id);
        IpraPerechenN result = query.getSingleResult();
        return result;
    }
    
}
