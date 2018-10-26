/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Ipra18;
import Entities.Ipra18Prikaz;
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
public class Ipra18PrikazFacade extends AbstractFacade<Ipra18Prikaz> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Ipra18PrikazFacade() {
        super(Ipra18Prikaz.class);
    }

    public Ipra18Prikaz findByIpra(Ipra18 ipra) {
        TypedQuery<Ipra18Prikaz> query = em.createNamedQuery("Ipra18Prikaz.findByIpra18Id", Ipra18Prikaz.class)
                .setParameter("ipra18Id", ipra);
        Ipra18Prikaz result = query.getSingleResult();
        return result;
    }

    public List<Ipra18Prikaz> findNoTpmpkN() {
        String qlString = "SELECT i FROM Ipra18Prikaz i, Children c, Ipra18 ip "
                + "WHERE i.ipra18prikazTpmpkN IS NULL AND i.sprotherpmpkId IS NOT NULL "
                + "AND i.ipra18Id = ip AND ip.childId = c "
                + "ORDER BY i.ipra18prikazTpmpkD, i.sprotherpmpkId, c.childFam, c.childName, c.childPatr ";
        TypedQuery<Ipra18Prikaz> query = em.createQuery(qlString, Ipra18Prikaz.class);
        List<Ipra18Prikaz> result = query.getResultList();
        return result;
    }

    public List<Ipra18Prikaz> findAllCorresp() {
        String qlString = "SELECT i FROM Ipra18Prikaz i "
                + "WHERE i.ipra18prikazPerechN is not null OR "
                + "i.ipra18prikazTpmpkN is not null OR "
                + "i.ipra18prikazOtchcenterN is not null ";
        TypedQuery<Ipra18Prikaz> query = em.createQuery(qlString, Ipra18Prikaz.class);
        List<Ipra18Prikaz> result = query.getResultList();
        return result;
    }
}
