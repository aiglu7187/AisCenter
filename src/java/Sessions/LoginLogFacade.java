/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.LoginLog;
import Entities.Users;
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
public class LoginLogFacade extends AbstractFacade<LoginLog> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LoginLogFacade() {
        super(LoginLog.class);
    }
    
    public List<LoginLog> findLog(Users user, String sessId){
        TypedQuery<LoginLog> query = em.createNamedQuery("LoginLog.findByUserAndSessId", LoginLog.class)
                .setParameter("userId", user)
                .setParameter("sessId", sessId);
        List<LoginLog> result = query.getResultList();
        return result;
    }
    
    public List<LoginLog> findLogByUser(Users user){
        TypedQuery<LoginLog> query = em.createNamedQuery("LoginLog.findByUserId", LoginLog.class)
                .setParameter("userId", user);
        List<LoginLog> result = query.getResultList();
        return result;
    }
}
