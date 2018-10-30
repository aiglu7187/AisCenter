/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Ipra18N;
import Entities.Ipra18PrikazN;
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
public class Ipra18PrikazNFacade extends AbstractFacade<Ipra18PrikazN> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Ipra18PrikazNFacade() {
        super(Ipra18PrikazN.class);
    }
    
    public List<Ipra18PrikazN> findNoPrikaz(){
        String qlString = "SELECT ip FROM Ipra18N i, Ipra18PrikazN ip "
                + "WHERE ip.ipra18Id = i AND ip.ipra18prikazN is null "
                + "ORDER BY ip.ipra18prikazD ";
        TypedQuery<Ipra18PrikazN> query = em.createQuery(qlString, Ipra18PrikazN.class);
        List<Ipra18PrikazN> result = query.getResultList();
        return result;
    }
    
    public Ipra18PrikazN findByIpra(Ipra18N ipra){
        TypedQuery<Ipra18PrikazN> query = em.createNamedQuery("Ipra18PrikazN.findByIpra18Id", Ipra18PrikazN.class)
                .setParameter("ipra18Id", ipra);
        Ipra18PrikazN result = query.getSingleResult();
        return result;
    }
}
