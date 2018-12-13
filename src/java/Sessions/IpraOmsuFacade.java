/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Ipra18N;
import Entities.IpraOmsu;
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
public class IpraOmsuFacade extends AbstractFacade<IpraOmsu> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IpraOmsuFacade() {
        super(IpraOmsu.class);
    }
    
    public List<IpraOmsu> findDisByIpra(Ipra18N ipra18Id){
        String qlString = "SELECT i FROM IpraOmsu i WHERE i.ipra18Id = :ipra18Id "
                + "AND i.ipraomsuReq = :dis ";
        TypedQuery<IpraOmsu> query = em.createQuery(qlString, IpraOmsu.class)
                .setParameter("ipra18Id", ipra18Id)
                .setParameter("dis", 0);
        List<IpraOmsu> result = query.getResultList();
        return result;
    }
    
}
