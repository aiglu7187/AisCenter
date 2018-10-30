/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.IpraPerechenTpmpk;
import javax.ejb.Stateless;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class IpraPerechenTpmpkFacade extends AbstractFacade<IpraPerechenTpmpk> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IpraPerechenTpmpkFacade() {
        super(IpraPerechenTpmpk.class);
    }
    
    public List<IpraPerechenTpmpk> findNoNomTpmpk(){
        String qlString = "SELECT it "
                + "FROM IpraPerechenN i, IpraPerechenTpmpk it, IshCorr ic, IpraIshcorr iic, SprIshType t "
                + "WHERE i = it.ipraperechennId AND iic.ishcorrId = ic AND iic.ipra18Id = i.ipra18Id "
                + "AND ic.sprishtypeId = t AND t.sprishtypeName = :type AND ic.ishcorrN IS NULL "
                + "ORDER BY ic.ishcorrD ";
        TypedQuery<IpraPerechenTpmpk> query = em.createQuery(qlString, IpraPerechenTpmpk.class)
                .setParameter("type", "req_tpmpk");
        List<IpraPerechenTpmpk> result = query.getResultList();
        return result;
    }
}
