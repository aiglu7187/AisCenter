/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprObr;
import Entities.SprObrSpec;
import Entities.SprObrType;
import Entities.SprObrVid;
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
public class SprObrFacade extends AbstractFacade<SprObr> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprObrFacade() {
        super(SprObr.class);
    }

    public List<SprObr> findObrByType(SprObrType type) {
        String qlString = "SELECT s FROM SprObr s "
                + "WHERE s.sprobrtypeId = :sprobrtypeId AND s.sprobrActive = :sprobrActive "
                + "ORDER BY s.sprobrShname";
        TypedQuery<SprObr> query = em.createQuery(qlString, SprObr.class)
                .setParameter("sprobrtypeId", type)
                .setParameter("sprobrActive", 1);
        List<SprObr> result = query.getResultList();
        return result;
    }

    public SprObr findObrById(Integer id) {
        TypedQuery<SprObr> query = em.createNamedQuery("SprObr.findBySprobrId", SprObr.class)
                .setParameter("sprobrId", id);
        SprObr result = query.getSingleResult();
        return result;
    }

    public List<SprObr> findAllObr() {
        TypedQuery<SprObr> query = em.createNamedQuery("SprObr.findAll", SprObr.class);
        List<SprObr> result = query.getResultList();
        return result;
    }

    public List<SprObr> findAllActObr(Integer act) {
        TypedQuery<SprObr> query = em.createNamedQuery("SprObr.findAllAct", SprObr.class)
                .setParameter("sprobrActive", act);
        List<SprObr> result = query.getResultList();
        return result;
    }

    public List<SprObr> findByVid(SprObrVid vid) {
        TypedQuery<SprObr> query = em.createNamedQuery("SprObr.findBySprobrvidId", SprObr.class)
                .setParameter("sprobrvidId", vid);
        List<SprObr> result = query.getResultList();
        return result;
    }

    public List<SprObr> findByTypeVidSpec(SprObrType type, SprObrVid vid, SprObrSpec spec) {
        String qlString = "SELECT o "
                + "FROM SprObr o "
                + "WHERE o.sprobrtypeId = :type AND o.sprobrvidId = :vid ";
        if (spec == null) {
            qlString += "AND o.sprobrspecId IS NULL ";
        } else {
            qlString += "AND o.sprobrspecId = :spec ";
        }
        TypedQuery<SprObr> query = em.createQuery(qlString, SprObr.class)
                .setParameter("type", type)
                .setParameter("vid", vid);
        if (spec != null) {
            query.setParameter("spec", spec);
        }
        List<SprObr> result = query.getResultList();
        return result;
    }
}
