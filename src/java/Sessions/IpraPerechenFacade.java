/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Ipra18;
import Entities.IpraPerechen;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class IpraPerechenFacade extends AbstractFacade<IpraPerechen> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IpraPerechenFacade() {
        super(IpraPerechen.class);
    }

    public IpraPerechen findByIpra18(Ipra18 ipra) {
        TypedQuery<IpraPerechen> query = em.createNamedQuery("IpraPerechen.findByIpra18Id", IpraPerechen.class)
                .setParameter("ipra18Id", ipra);
        IpraPerechen result = query.getSingleResult();
        return result;
    }

    public IpraPerechen findById(Integer id) {
        TypedQuery<IpraPerechen> query = em.createNamedQuery("IpraPerechen.findByIpraperechenId", IpraPerechen.class)
                .setParameter("ipraperechenId", id);
        IpraPerechen result = query.getSingleResult();
        return result;
    }
}
