/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Parents;
import Entities.Telephon;
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
public class TelephonFacade extends AbstractFacade<Telephon> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TelephonFacade() {
        super(Telephon.class);
    }

    public List<Telephon> findByParent(Parents parent) {
        TypedQuery<Telephon> query = em.createNamedQuery("Telephon.findByParentId", Telephon.class)
                .setParameter("parentId", parent);
        List<Telephon> result = query.getResultList();
        return result;
    }
}
