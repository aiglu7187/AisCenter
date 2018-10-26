/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Sotrud;
import Entities.Users;
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
public class UsersFacade extends AbstractFacade<Users> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
    
    public List<Users> findByName(String uName) {
        TypedQuery <Users> query = 
                em.createNamedQuery("Users.findByUserName", Users.class).setParameter("userName", uName);
        List<Users> result;
        result = query.getResultList();
        return result;
    }
    
    public List<Users> findBySotrud(Sotrud sotrud){
        TypedQuery <Users> query = em.createNamedQuery("Users.findBySotrudId", Users.class)
                .setParameter("sotrudId", sotrud);
        List<Users> result;
        result = query.getResultList();
        return result;
    }
    
    public Users findById(Integer uId){
        TypedQuery <Users> query = 
                em.createNamedQuery("Users.findByUserId", Users.class).setParameter("userId", uId);
        Users result;
        result = query.getSingleResult();
        return result;
    }
    
}
