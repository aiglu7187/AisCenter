/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Parents;
import java.math.BigDecimal;
import java.util.ArrayList;
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
public class ParentsFacade extends AbstractFacade<Parents> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParentsFacade() {
        super(Parents.class);
    }

    public List<Parents> search(String fam, String name, String patr) {
        String qlString = "SELECT distinct parent_id, parent_fam, parent_name, parent_patr FROM "
                + "((SELECT c.parent_id, c.parent_fam, c.parent_name, c.parent_patr FROM parents c "
                + "WHERE REPLACE(UPPER(c.parent_fam),?,?) LIKE REPLACE(UPPER(?),?,?) "                
                + "AND REPLACE(UPPER(c.parent_name),?,?) LIKE REPLACE(UPPER(?),?,?) "                
                + "AND (REPLACE(UPPER(c.parent_patr),?,?) LIKE REPLACE(UPPER(?),?,?) "               
                + "OR c.parent_patr IS NULL))"
                + "UNION ALL "
                + "(SELECT c.parent_id, c.parent_fam, c.parent_name, c.parent_patr FROM parents c, parents_oldfio co "
                + "WHERE c.parent_id = co.parent_id AND REPLACE(UPPER(co.parentoldfio_fam),?,?) LIKE REPLACE(UPPER(?),?,?) "
                + "AND REPLACE(UPPER(co.parentoldfio_name),?,?) LIKE REPLACE(UPPER(?),?,?) "
                + "AND (REPLACE(UPPER(co.parentoldfio_patr),?,?) LIKE REPLACE(UPPER(?),?,?) OR co.parentoldfio_patr IS NULL))) "                         
                + "ORDER BY parent_fam, parent_name, parent_patr";
        Query query = em.createNativeQuery(qlString)
                .setParameter(1, "Ё")
                .setParameter(2, "Е")
                .setParameter(3, fam + "%")
                .setParameter(4, "Ё")
                .setParameter(5, "Е")
                .setParameter(6, "Ё")
                .setParameter(7, "Е")
                .setParameter(8, name + "%")
                .setParameter(9, "Ё")
                .setParameter(10, "Е")
                .setParameter(11, "Ё")
                .setParameter(12, "Е")
                .setParameter(13, patr + "%")
                .setParameter(14, "Ё")
                .setParameter(15, "Е")
                .setParameter(16, "Ё")
                .setParameter(17, "Е")
                .setParameter(18, fam + "%")
                .setParameter(19, "Ё")
                .setParameter(20, "Е")
                .setParameter(21, "Ё")
                .setParameter(22, "Е")
                .setParameter(23, name + "%")
                .setParameter(24, "Ё")
                .setParameter(25, "Е")
                .setParameter(26, "Ё")
                .setParameter(27, "Е")
                .setParameter(28, patr + "%")
                .setParameter(29, "Ё")
                .setParameter(30, "Е");
        query.setMaxResults(50);
        List resultList = query.getResultList();
        List<Parents> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            BigDecimal d = (BigDecimal) o[0];
            Long l = d.longValue();
            Integer id = l.intValue();
            Parents parent = findById(id);
            result.add(parent);
        }
        return result;
    }

    public List<Parents> searchParent(String fam, String name, String patr) {
        TypedQuery<Parents> query = em.createNamedQuery("Parents.findByFIO", Parents.class)
                .setParameter("fam", fam + "%")
                .setParameter("name", name + "%")
                .setParameter("patr", patr + "%");
        List<Parents> result;
        result = query.getResultList();
        return result;
    }

    public Parents findByNom(Integer nom) {
        TypedQuery<Parents> query
                = em.createNamedQuery("Parents.findByParentNom", Parents.class).setParameter("parentNom", nom);
        Parents result;
        result = query.getSingleResult();
        return result;
    }

    public Parents findById(Integer id) {
        TypedQuery<Parents> query
                = em.createNamedQuery("Parents.findByParentId", Parents.class).setParameter("parentId", id);
        Parents result;
        result = query.getSingleResult();
        return result;
    }

    public List<Parents> findAllParents() {
        TypedQuery query = em.createNamedQuery("Parents.findAll", Parents.class);
        query.setMaxResults(50);
        List<Parents> result;
        result = query.getResultList();
        return result;
    }
}
