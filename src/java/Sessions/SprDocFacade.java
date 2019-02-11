/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprDoc;
import java.util.ArrayList;
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
public class SprDocFacade extends AbstractFacade<SprDoc> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SprDocFacade() {
        super(SprDoc.class);
    }
    
    public List<SprDoc> findAllDoc(){
        String qlString = "SELECT s FROM SprDoc s "
                + "WHERE s.sprdocMain IS NULL ";
        TypedQuery<SprDoc> query = em.createQuery(qlString, SprDoc.class);
        List<SprDoc> mainDoc = query.getResultList();
        List<SprDoc> result = new ArrayList<>();
        // выстраиваем список в нужном порядке (с учётом документов категорий)
        for (SprDoc doc : mainDoc) {
            result.add(doc);
            List<SprDoc> podDoc = findByMain(doc.getSprdocId());
            result.addAll(podDoc);            
        }
        return result;
    }
    
    public List<SprDoc> findByMain(Integer mainId){
        TypedQuery<SprDoc> query = em.createNamedQuery("SprDoc.findBySprdocMain", SprDoc.class)
                .setParameter ("sprdocMain", mainId);
        List<SprDoc> result = query.getResultList();
        return result;
    }
    
}
