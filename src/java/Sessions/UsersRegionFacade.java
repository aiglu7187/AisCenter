/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.SprRegion;
import Entities.Users;
import Entities.UsersRegion;
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
public class UsersRegionFacade extends AbstractFacade<UsersRegion> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersRegionFacade() {
        super(UsersRegion.class);
    }
    
    public List<Users> getUserByRegion(SprRegion reg){
        TypedQuery <Users> query = em.createNamedQuery("UsersRegion.findBySprregId", Users.class)
                .setParameter("sprregId", reg);
        List<Users> result = query.getResultList();
        return result;
    }
}
