/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Ped;
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
public class PedFacade extends AbstractFacade<Ped> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedFacade() {
        super(Ped.class);
    }
    
    public List<Ped> search(String fam, String name, String patr){
        String qlString = "SELECT distinct ped_id, ped_fam, ped_name, ped_patr FROM "
                + "((SELECT c.ped_id, c.ped_fam, c.ped_name, c.ped_patr FROM ped c "
                + "WHERE REPLACE(UPPER(c.ped_fam),?,?) LIKE REPLACE(UPPER(?),?,?) "                
                + "AND REPLACE(UPPER(c.ped_name),?,?) LIKE REPLACE(UPPER(?),?,?) "                
                + "AND (REPLACE(UPPER(c.ped_patr),?,?) LIKE REPLACE(UPPER(?),?,?) "               
                + "OR c.ped_patr IS NULL))"
                + "UNION ALL "
                + "(SELECT c.ped_id, c.ped_fam, c.ped_name, c.ped_patr FROM ped c, ped_oldfio co "
                + "WHERE c.ped_id = co.ped_id AND REPLACE(UPPER(co.pedoldfio_fam),?,?) LIKE REPLACE(UPPER(?),?,?) "
                + "AND REPLACE(UPPER(co.pedoldfio_name),?,?) LIKE REPLACE(UPPER(?),?,?) "
                + "AND (REPLACE(UPPER(co.pedoldfio_patr),?,?) LIKE REPLACE(UPPER(?),?,?) OR co.pedoldfio_patr IS NULL))) "                         
                + "ORDER BY ped_fam, ped_name, ped_patr";
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
        List<Ped> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            BigDecimal d = (BigDecimal) o[0];
            Long l = d.longValue();
            Integer id = l.intValue();
            Ped ped = findById(id);
            result.add(ped);
        }
        return result;
    }
    
    public List<Ped> findByFIO(String fam,String name,String patr){
        TypedQuery<Ped> query = em.createNamedQuery("Ped.findByFIO", Ped.class)
                .setParameter("fam", fam + "%")
                .setParameter("name", name + "%")
                .setParameter("patr", patr + "%");
        List<Ped> result = query.getResultList();
        return result;
    }
    
    public Ped findByNom(Integer nom){
        TypedQuery<Ped> query = 
                em.createNamedQuery("Ped.findByPedNom", Ped.class).setParameter("pedNom", nom);
        Ped result;
        result = query.getSingleResult();
        return result;
    }
    
    public Ped findById(Integer id){
        TypedQuery<Ped> query = 
                em.createNamedQuery("Ped.findByPedId", Ped.class).setParameter("pedId", id);
        Ped result;
        result = query.getSingleResult();
        return result;
    }
    
    public List <Ped> findAllPed(){
        TypedQuery query = em.createNamedQuery("Ped.findAll", Ped.class);
        query.setMaxResults(50);
        List <Ped> result;
        result = query.getResultList();
        return result;
    }
}
