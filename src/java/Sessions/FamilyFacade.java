/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Family;
import Entities.Parents;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author admin_ai
 */
@Stateless
public class FamilyFacade extends AbstractFacade<Family> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FamilyFacade() {
        super(Family.class);
    }
   
    public List<Family> findByClient (String kat, Integer clientId){
        TypedQuery<Family> query = em.createNamedQuery("Family.findByClientAndKatcl", Family.class)
                .setParameter("clientId", clientId)
                .setParameter("famKatcl", kat);
        List<Family> result = query.getResultList();
        return result;
    }
        
    public List<Family> findByFamNom (Integer nom){
        TypedQuery<Family> query = em.createNamedQuery("Family.findByFamNom", Family.class)
                .setParameter("famNom", nom);
        List<Family> result = query.getResultList();
        return result;
    }
}
