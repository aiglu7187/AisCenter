/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.Ipra18N;
import Entities.SprVhType;
import Entities.VhCorr;
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
public class VhCorrFacade extends AbstractFacade<VhCorr> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VhCorrFacade() {
        super(VhCorr.class);
    }
    
    public List<VhCorr> findByIpra(Ipra18N ipra){
        String qlString = "SELECT vc FROM VhCorr vc, IpraVhcorr ivc "
                + "WHERE ivc.vhcorrId = vc AND ivc.ipra18Id = :ipra ";
        TypedQuery<VhCorr> query = em.createQuery(qlString, VhCorr.class)
                .setParameter("ipra", ipra);
        List<VhCorr> result = query.getResultList();
        return result;
    }
    
    public List<VhCorr> findByIpraAndType(Ipra18N ipra, SprVhType type){
        String qlString = "SELECT vc FROM VhCorr vc, IpraVhcorr ivc "
                + "WHERE ivc.vhcorrId = vc AND ivc.ipra18Id = :ipra AND vc.sprvhtypeId = :type ";
        TypedQuery<VhCorr> query = em.createQuery(qlString, VhCorr.class)
                .setParameter("ipra", ipra)
                .setParameter("type", type);
        List<VhCorr> result = query.getResultList();
        return result;
    }
}
