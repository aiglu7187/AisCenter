/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Parents;
import Entities.ParentsOldfio;
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
public class ParentsOldfioFacade extends AbstractFacade<ParentsOldfio> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParentsOldfioFacade() {
        super(ParentsOldfio.class);
    }

    public List<ParentsOldfio> findByParentId(Parents parentId) {
        TypedQuery<ParentsOldfio> query = em.createNamedQuery("ParentsOldfio.findByParentId", ParentsOldfio.class)
                .setParameter("parentId", parentId);
        List<ParentsOldfio> result = query.getResultList();
        return result;
    }
}
