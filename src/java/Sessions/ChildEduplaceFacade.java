/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sessions;

import Entities.ChildEduplace;
import Entities.Children;
import Entities.SprObrType;
import Entities.SprRegion;
import Other.OvzFgos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Aiglu
 */
@Stateless
public class ChildEduplaceFacade extends AbstractFacade<ChildEduplace> {

    @PersistenceContext(unitName = "AisCenterPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChildEduplaceFacade() {
        super(ChildEduplace.class);
    }

    public List<ChildEduplace> findByChild(Children child) {
        TypedQuery<ChildEduplace> query = em.createNamedQuery("ChildEduplace.findByChild", ChildEduplace.class)
                .setParameter("childId", child);
        List<ChildEduplace> result = query.getResultList();
        return result;
    }

    public List<OvzFgos> findOvzFgos(Date d1, Date d2, String pmpkShname) {
        String qlString = "SELECT reg2.sprreg_name reg, oo.sproo_name oo_name, "
                + "c.child_fam||' '||c.child_name||' '||c.child_patr fio, "
                + "c.child_dr dr, edu.childeduplace_beginedu beginedu, "
                + "'" + pmpkShname + "' pmpk_name, pr.priyom_date pmpk_date, pk.pmpk_np np, "
                + "obr.sprobr_shname||' ('||var.sprobrvar_name||')' obr, pmpk_id id "
                + "FROM children c, pmpk pk, priyom pr, spr_obr obr, spr_obr_var var, "
                + "priyom_client pc, child_eduplace edu, spr_oo oo, "
                + "spr_region reg, spr_region reg2 "
                + "WHERE pr.priyom_id = pc.priyom_id AND pc.client_id = c.child_id "
                + "AND pc.prcl_katcl = 'children' AND pk.prcl_id = pc.prcl_id and "
                + "pk.sprobr_id = obr.sprobr_id AND pk.sprobrvar_id = var.sprobrvar_id "
                + "AND reg.sprreg_id = c.sprreg_id AND pr.priyom_date >= ? AND pr.priyom_date <= ? "
                + "AND edu.child_id = c.child_id AND oo.sproo_id = edu.sproo_id "
                + "AND reg2.sprreg_id = oo.sprreg_id "
                + "UNION ALL "
                + "SELECT reg.sprreg_name reg, 'не обращался', "
                + "c.child_fam||' '||c.child_name||' '||c.child_patr fio, "
                + "c.child_dr dr, to_date('01.01.2000', 'dd.mm.yyyy') beginedu, '" + pmpkShname + "' pmpk_name, "
                + "pr.priyom_date pmpk_date, pk.pmpk_np np, obr.sprobr_shname||' ('||var.sprobrvar_name||')' obr,"
                + "pmpk_id id "
                + "FROM children c, pmpk pk, priyom pr, spr_obr obr, spr_obr_var var, priyom_client pc, "
                + "spr_region reg "
                + "WHERE pr.priyom_id = pc.priyom_id AND pc.client_id = c.child_id AND pc.prcl_katcl = 'children' "
                + "AND pk.prcl_id = pc.prcl_id AND pk.sprobr_id = obr.sprobr_id "
                + "AND pk.sprobrvar_id = var.sprobrvar_id AND reg.sprreg_id = c.sprreg_id "
                + "AND pr.priyom_date >= ? and PR.PRIYOM_DATE <= ? "
                + "AND NOT EXISTS (SELECT chedu.childeduplace_id FROM child_eduplace chedu "
                + "WHERE chedu.child_id = c.child_id) ";
        Query query = em.createNativeQuery(qlString)
                .setParameter(1, d1)
                .setParameter(2, d2)
                .setParameter(3, d1)
                .setParameter(4, d2);
        List resultList = query.getResultList();
        List<OvzFgos> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            OvzFgos child = new OvzFgos();
            child.setReg((String) o[0]);
            child.setOo((String) o[1]);
            child.setFio((String) o[2]);
            child.setDr((Date) o[3]);
            child.setBeginEdu((Date) o[4]);
            child.setPmpkName((String) o[5]);
            child.setPmpkDate((Date) o[6]);
            child.setNp((String) o[7]);
            child.setObr((String) o[8]);
            BigDecimal d = (BigDecimal) o[9];
            Long l = d.longValue();
            child.setPmpkId(l.intValue());
            result.add(child);
        }
        return result;
    }

    public List<OvzFgos> findOvzByRegion(Date d1, Date d2, SprRegion reg, Date d3, Date d4, 
            List<SprObrType> sprObrTypes, String pmpkShname) {
        String type = "";
        type = "AND (";
        for (SprObrType sprObrType : sprObrTypes) {
            if (!type.equals("AND (")) {
                type += " OR ";
            }
            type += "type.sprobrtype_id = ?";
        }
        type += ") ";
        String qlString = "SELECT reg, fio, dr, pmpk_name, pmpk_date, np, obr, pk_id "
                + "FROM (SELECT reg.sprreg_name reg, "
                + "c.child_fam||' '||c.child_name||' '||c.child_patr fio, "
                + "c.child_dr dr, "
                + "'" + pmpkShname + "' pmpk_name, pr.priyom_date pmpk_date, pk.pmpk_np np, "
                + "obr.sprobr_shname||' ('||var.sprobrvar_name||')' obr, pmpk_id pk_id "
                + "FROM children c, pmpk pk, priyom pr, spr_obr obr, spr_obr_var var, "
                + "priyom_client pc, spr_region reg, spr_obr_type type "
                + "WHERE pr.priyom_id = pc.priyom_id AND pc.client_id = c.child_id "
                + "AND pc.prcl_katcl = 'children' AND pk.prcl_id = pc.prcl_id AND "
                + "pk.sprobr_id = obr.sprobr_id AND pk.sprobrvar_id = var.sprobrvar_id "
                + "AND obr.sprobrtype_id = type.sprobrtype_id " + type
                + "AND reg.sprreg_id = c.sprreg_id AND pr.priyom_date >= ? AND pr.priyom_date <= ? "
                + "AND reg.sprreg_id = ? AND c.child_dr >= ? AND c.child_dr <= ? AND "
                + "pr.priyom_date = (SELECT MAX (pr1.priyom_date) FROM priyom pr1, priyom_client pc1 "
                + "WHERE pr1.priyom_id = pc1.priyom_id AND pc1.client_id = c.child_id "
                + "AND pc1.prcl_katcl = 'children' AND PR1.SPRUSL_ID = ?) "
                + "UNION ALL "
                + "SELECT reg.sprreg_name reg, "
                + "c.child_fam||' '||c.child_name||' '||c.child_patr fio, "
                + "c.child_dr dr, "
                + "'" + pmpkShname + "' pmpk_name, pr.priyom_date pmpk_date, pk.pmpk_np np, "
                + "obr.sprobr_shname obr, pmpk_id pk_id "
                + "FROM children c, pmpk pk, priyom pr, spr_obr obr, priyom_client pc, "
                + "spr_region reg, spr_obr_type type "
                + "WHERE pr.priyom_id = pc.priyom_id AND pc.client_id = c.child_id "
                + "AND pc.prcl_katcl = 'children' AND pk.prcl_id = pc.prcl_id AND "
                + "pk.sprobr_id = obr.sprobr_id AND pk.sprobrvar_id is null "
                + "AND obr.sprobrtype_id = type.sprobrtype_id " + type
                + "AND (obr.sprobr_shname like ? OR obr.sprobr_shname like ?) "
                + "AND reg.sprreg_id = c.sprreg_id AND pr.priyom_date >= ? AND pr.priyom_date <= ? "
                + "AND reg.sprreg_id = ? AND c.child_dr >= ? AND c.child_dr <= ? AND "
                + "pr.priyom_date = (SELECT MAX (pr1.priyom_date) FROM priyom pr1, priyom_client pc1 "
                + "WHERE pr1.priyom_id = pc1.priyom_id AND pc1.client_id = c.child_id "
                + "AND pc1.prcl_katcl = 'children' AND PR1.SPRUSL_ID = ?)) "
                + "ORDER BY fio ";
        Query query = em.createNativeQuery(qlString);
        int j = 0;
        for (SprObrType sprObrType : sprObrTypes) {
            query.setParameter(++j, sprObrType.getSprobrtypeId());
        }
        query
                .setParameter(++j, d1)
                .setParameter(++j, d2)
                .setParameter(++j, reg.getSprregId())
                .setParameter(++j, d3)
                .setParameter(++j, d4)
                .setParameter(++j, 1)
                ;
        for (SprObrType sprObrType : sprObrTypes) {
            query.setParameter(++j, sprObrType.getSprobrtypeId());
        }
        query
                .setParameter(++j, "АООП%")
                .setParameter(++j, "АОП%")
                .setParameter(++j, d1)
                .setParameter(++j, d2)
                .setParameter(++j, reg.getSprregId())
                .setParameter(++j, d3)
                .setParameter(++j, d4)
                .setParameter(++j, 1);
        List resultList = query.getResultList();
        List<OvzFgos> result = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Object[] o = (Object[]) resultList.get(i);
            OvzFgos child = new OvzFgos();
            child.setReg((String) o[0]);
            child.setFio((String) o[1]);
            child.setDr((Date) o[2]);
            child.setPmpkName((String) o[3]);
            child.setPmpkDate((Date) o[4]);
            child.setNp((String) o[5]);
            child.setObr((String) o[6]);
            BigDecimal d = (BigDecimal) o[7];
            Long l = d.longValue();
            child.setPmpkId(l.intValue());
            result.add(child);
        }
        return result;
    }
}
