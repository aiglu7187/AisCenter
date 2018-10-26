/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.ChildEduplace;
import Entities.ChildStatus;
import Entities.Children;
import Entities.ChildrenEducond;
import Entities.ChildrenOldfio;
import Entities.ChildrenReg;
import Entities.Family;
import Entities.Ipra;
import Entities.Ipra18;
import Entities.LoginLog;
import Entities.MonitoringData;
import Entities.Parents;
import Entities.ParentsReg;
import Entities.PayDogovor;
import Entities.PayuslClient;
import Entities.Ped;
import Entities.PedReg;
import Entities.PriyomClient;
import Entities.PriyomSubject;
import Entities.SprOo;
import Entities.SprStage;
import Entities.Telephon;
import Entities.Users;
import Sessions.ChildEduplaceFacade;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenEducondFacade;
import Sessions.ChildrenFacade;
import Sessions.ChildrenOldfioFacade;
import Sessions.ChildrenRegFacade;
import Sessions.FamilyFacade;
import Sessions.Ipra18Facade;
import Sessions.IpraFacade;
import Sessions.LoginLogFacade;
import Sessions.MonitoringDataFacade;
import Sessions.ParentsFacade;
import Sessions.ParentsRegFacade;
import Sessions.PayDogovorFacade;
import Sessions.PayuslClientFacade;
import Sessions.PayusllesposFacade;
import Sessions.PedFacade;
import Sessions.PedRegFacade;
import Sessions.PmpkParentFacade;
import Sessions.PriyomClientFacade;
import Sessions.PriyomSubjectFacade;
import Sessions.SprOoFacade;
import Sessions.SprOrgFacade;
import Sessions.SprPeddolgFacade;
import Sessions.SprRegionFacade;
import Sessions.SprStageFacade;
import Sessions.TelephonFacade;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Aiglu
 */
// сервлет для сохранения данных о клиенте
@WebServlet(name = "SaveClientServlet",
        loadOnStartup = 1,
        urlPatterns = "/saveclient")

public class SaveClientServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    ParentsFacade parentsFacade = new ParentsFacade();
    @EJB
    PedFacade pedFacade = new PedFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    SprOrgFacade sprOrgFacade = new SprOrgFacade();
    @EJB
    SprPeddolgFacade sprPeddolgFacade = new SprPeddolgFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    ChildEduplaceFacade childEduplaceFacade = new ChildEduplaceFacade();
    @EJB
    SprOoFacade sprOoFacade = new SprOoFacade();
    @EJB
    SprStageFacade sprStageFacade = new SprStageFacade();
    @EJB
    ChildrenRegFacade childrenRegFacade = new ChildrenRegFacade();
    @EJB
    ParentsRegFacade parentsRegFacade = new ParentsRegFacade();
    @EJB
    PedRegFacade pedRegFacade = new PedRegFacade();
    @EJB
    TelephonFacade telephonFacade = new TelephonFacade();
    @EJB
    PriyomClientFacade priyomClientFacade = new PriyomClientFacade();
    @EJB
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    PriyomSubjectFacade priyomSubjectFacade = new PriyomSubjectFacade();
    @EJB
    IpraFacade ipraFacade = new IpraFacade();
    @EJB
    MonitoringDataFacade monitoringDataFacade = new MonitoringDataFacade();
    @EJB
    PayDogovorFacade payDogovorFacade = new PayDogovorFacade();
    @EJB
    PayuslClientFacade payuslClientFacade = new PayuslClientFacade();
    @EJB
    PayusllesposFacade payusllesposFacade = new PayusllesposFacade();
    @EJB
    PmpkParentFacade pmpkParentFacade = new PmpkParentFacade();
    @EJB
    Ipra18Facade ipra18Facade = new Ipra18Facade();
    @EJB
    FamilyFacade familyFacade = new FamilyFacade();
    @EJB
    ChildrenEducondFacade childrenEducondFacade = new ChildrenEducondFacade();
    @EJB
    ChildrenOldfioFacade childrenOldfioFacade = new ChildrenOldfioFacade();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        String sessId = session.getId();
        try {
            List<LoginLog> logs = loginLogFacade.findLog(user, sessId);
            LoginLog log = logs.get(0);
            Date lastAct = new Date(session.getLastAccessedTime());
            log.setLoginlogLastact(lastAct);
            loginLogFacade.edit(log);
        } catch (Exception ex) {

        }
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);

        String kat = request.getParameter("kat");
        Integer id = Integer.parseInt(request.getParameter("idClient"));
        Integer idReg = null;
        Integer idOrg = null;
        Integer idDolg = null;
        String dr = "";
        switch (kat) {
            case "children":
                Children child = childrenFacade.findById(id);
                child.setChildFam(request.getParameter("fam").trim());
                child.setChildName(request.getParameter("nam").trim());
                child.setChildPatr(request.getParameter("patr").trim());
                child.setChildPol(request.getParameter("pol"));
                dr = request.getParameter("dr");
                SimpleDateFormat format = new SimpleDateFormat();
                format.applyPattern("yyyy-MM-dd");
                Date dRogd = null;
                try {
                    dRogd = format.parse(dr);
                } catch (Exception ex) {
                }
                child.setChildDr(dRogd);
                idReg = Integer.parseInt(request.getParameter("regId"));
                if (!child.getSprregId().equals(sprRegionFacade.findById(idReg))) {
                    ChildrenReg cr = new ChildrenReg();
                    cr.setChildId(child);
                    cr.setSprregId(child.getSprregId());
                    cr.setChildrenregDate(curDate);
                    childrenRegFacade.create(cr);
                    child.setSprregId(sprRegionFacade.findById(idReg));
                }

                ChildEduplace childEduplace = null;
                String ouIdS = request.getParameter("ouId");
                if (ouIdS != null) {
                    List<ChildEduplace> childEdu = childEduplaceFacade.findByChild(child);
                    for (ChildEduplace e : childEdu) {
                        if (e.getChildeduplaceDatek() == null) {
                            e.setChildeduplaceDatek(curDate);
                            childEduplaceFacade.edit(e);
                        }
                    }
                    Integer ouId = 0;
                    try {
                        ouId = Integer.parseInt(ouIdS);
                    } catch (Exception ex) {
                    }
                    if (ouId > 0) {
                        SprOo ou = null;
                        try {
                            ou = sprOoFacade.findById(ouId);
                        } catch (Exception ex) {
                        }
                        if (ou != null) {
                            childEduplace = new ChildEduplace();
                            childEduplace.setChildId(child);
                            childEduplace.setChildeduplaceDaten(curDate);
                            childEduplace.setSprooId(ou);
                        }
                    }

                }
                String stageIdS = request.getParameter("stageId");
                if (stageIdS != null) {
                    Integer stageId = 0;
                    try {
                        stageId = Integer.parseInt(stageIdS);
                    } catch (Exception ex) {
                    }
                    if (stageId > 0) {
                        SprStage stage = null;
                        try {
                            stage = sprStageFacade.findById(stageId);
                        } catch (Exception ex) {
                        }
                        if (stage != null) {
                            if (childEduplace != null) {
                                childEduplace.setSprstageId(stage);
                            }

                        }
                    }
                }
                if (childEduplace != null) {
                    childEduplaceFacade.create(childEduplace);
                }
                child.setUserId(user);
                child.setDateUpd(curDate);
                childrenFacade.edit(child);

                // проверка на дубль
                List<Children> findChild = childrenFacade.findByFioDr(child.getChildFam(), child.getChildName(), child.getChildPatr(), child.getChildDr());
                Children osnChild = null;
                if (findChild.size() > 1) {
                    for (Children fc : findChild) {
                        if (!fc.getChildId().equals(child.getChildId())) {
                            osnChild = fc;
                        }
                    }
                    Children doubChild = child;
                    if (osnChild != null) {
                        List<PriyomClient> prClList = priyomClientFacade.findByClient(doubChild.getChildId(), kat);
                        List<PriyomSubject> prSubList = priyomSubjectFacade.findBySubject(doubChild);
                        for (PriyomClient prcl : prClList) {
                            prcl.setClientId(osnChild.getChildId());
                            priyomClientFacade.edit(prcl);
                        }
                        for (PriyomSubject prsub : prSubList) {
                            prsub.setChildId(osnChild);
                            priyomSubjectFacade.edit(prsub);
                        }
                        List<ChildStatus> chStListDoub = childStatusFacade.findByChild(doubChild);
                        List<ChildStatus> chStListOsn = childStatusFacade.findByChild(osnChild);
                        List<ChildStatus> chStListForRemove = new ArrayList<>();    // список связок ребёнок-статус для удаления (дублирующие)
                        for (ChildStatus chstd : chStListDoub) {
                            Boolean isDouble = false;
                            for (ChildStatus chst : chStListOsn) {
                                if (chst.getSprstatId().equals(chstd.getSprstatId())) {
                                    if (chstd.getChildstatusDateN().before(chst.getChildstatusDateN())) {
                                        chst.setChildstatusDateN(chstd.getChildstatusDateN());
                                    }
                                    if ((chst.getChildstatusDateK() == null) && (chstd.getChildstatusDateK() != null)) {
                                        if (chstd.getChildstatusDateN().before(chst.getChildstatusDateN())) {
                                            chst.setChildstatusDateK(chstd.getChildstatusDateK());
                                        }
                                    } else if ((chstd.getChildstatusDateK() == null) && (chst.getChildstatusDateK() != null)) {
                                        if (chstd.getChildstatusDateN().after(chst.getChildstatusDateN())) {
                                            chst.setChildstatusDateK(chstd.getChildstatusDateK());
                                        }
                                    }
                                    childStatusFacade.edit(chst);
                                    isDouble = true;
                                }
                            }
                            if (!isDouble) {
                                chstd.setChildId(osnChild);
                                childStatusFacade.edit(chstd);
                            } else {
                                chStListForRemove.add(chstd);
                            }
                        }
                        for (ChildStatus cs : chStListForRemove) {
                            childStatusFacade.remove(cs);
                        }

                        List<ChildEduplace> chEdupl = childEduplaceFacade.findByChild(doubChild);
                        for (ChildEduplace ce : chEdupl) {
                            ce.setChildId(osnChild);
                            childEduplaceFacade.edit(ce);
                        }
                        List<ChildrenReg> chReg = childrenRegFacade.findByChild(doubChild);
                        for (ChildrenReg cr : chReg) {
                            cr.setChildId(osnChild);
                            childrenRegFacade.edit(cr);
                        }
                        List<Ipra> ipra = ipraFacade.findByChild(doubChild);
                        for (Ipra ip : ipra) {
                            ip.setChildId(osnChild);
                            ipraFacade.edit(ip);
                        }
                        List<Ipra18> ipra18 = ipra18Facade.findByChild(doubChild);
                        for (Ipra18 ip : ipra18) {
                            ip.setChildId(osnChild);
                            ipra18Facade.edit(ip);
                        }
                        List<MonitoringData> mon = monitoringDataFacade.findByChild(doubChild);
                        for (MonitoringData md : mon) {
                            md.setChildId(osnChild);
                            monitoringDataFacade.edit(md);
                        }
                        List<ChildrenEducond> educond = childrenEducondFacade.findByChild(doubChild);
                        for (ChildrenEducond ce : educond) {
                            ce.setChildId(osnChild);
                            childrenEducondFacade.edit(ce);
                        }
                        List<PayDogovor> paydog = payDogovorFacade.findByChild(doubChild);
                        for (PayDogovor pd : paydog) {
                            pd.setChildId(osnChild);
                            payDogovorFacade.edit(pd);
                        }
                        String status = "готово";
                        List<PayuslClient> puc = payuslClientFacade.findByClient(doubChild.getChildId(), kat, status);
                        for (PayuslClient pu : puc) {
                            pu.setClientId(osnChild.getChildId());
                            payuslClientFacade.edit(pu);
                        }
                        status = "подготовлено";
                        List<PayuslClient> puc2 = payuslClientFacade.findByClient(doubChild.getChildId(), kat, status);
                        for (PayuslClient pu : puc2) {
                            pu.setClientId(osnChild.getChildId());
                            payuslClientFacade.edit(pu);
                        }
                        status = "окончено";
                        List<PayuslClient> puc3 = payuslClientFacade.findByClient(doubChild.getChildId(), kat, status);
                        for (PayuslClient pu : puc3) {
                            pu.setClientId(osnChild.getChildId());
                            payuslClientFacade.edit(pu);
                        }
                        // старые ФИО
                        List<ChildrenOldfio> oldfio = childrenOldfioFacade.findByChildId(doubChild);
                        for (ChildrenOldfio co : oldfio) {
                            co.setChildId(osnChild);
                            childrenOldfioFacade.edit(co);
                        }

                        // семья
                        List<Family> familiesDoub = familyFacade.findByClient(kat, doubChild.getChildId());
                        for (Family familyDoub : familiesDoub) {
                            familyDoub.setClientId(osnChild.getChildId());
                            familyFacade.edit(familyDoub);
                        }
                        List<Family> families = familyFacade.findByClient(kat, osnChild.getChildId());
                        if (!families.isEmpty()) {
                            Integer famNom = families.get(0).getFamNom();
                            for (int i = 1; i < families.size(); i++) {
                                if (families.get(i).getFamNom().equals(famNom)) {
                                    familyFacade.remove(families.get(i));
                                }
                            }
                        }
                        childrenFacade.remove(doubChild);
                    }
                }
                break;
            case "parents":
                Parents parent = parentsFacade.findById(id);
                parent.setParentFam(request.getParameter("fam").trim());
                parent.setParentName(request.getParameter("nam").trim());
                parent.setParentPatr(request.getParameter("patr").trim());
                idReg = Integer.parseInt(request.getParameter("regId"));
                if (!parent.getSprregId().equals(sprRegionFacade.findById(idReg))) {
                    ParentsReg pr = new ParentsReg();
                    pr.setParentId(parent);
                    pr.setSprregId(parent.getSprregId());
                    pr.setParentsregDate(curDate);
                    parentsRegFacade.create(pr);
                    parent.setSprregId(sprRegionFacade.findById(idReg));
                }
                String tel = request.getParameter("tel");
                if (!tel.equals("")) {
                    List<Telephon> oldTel = telephonFacade.findByParent(parent);
                    if (!oldTel.isEmpty()) {
                        for (Telephon t : oldTel) {
                            telephonFacade.remove(t);
                        }
                    }
                    Telephon telephon = new Telephon();
                    telephon.setParentId(parent);
                    telephon.setTel(tel);
                    telephonFacade.create(telephon);
                }
                parent.setUserId(user);
                parent.setDateUpd(curDate);
                parentsFacade.edit(parent);
                break;
            case "ped":
                Ped ped = pedFacade.findById(id);
                ped.setPedId(Integer.parseInt(request.getParameter("idClient")));
                ped.setPedFam(request.getParameter("fam").trim());
                ped.setPedName(request.getParameter("nam").trim());
                ped.setPedPatr(request.getParameter("patr").trim());
                idReg = Integer.parseInt(request.getParameter("regId"));
                if (!ped.getSprregId().equals(sprRegionFacade.findById(idReg))) {
                    PedReg pr = new PedReg();
                    pr.setPedId(ped);
                    pr.setSprregId(ped.getSprregId());
                    pr.setPedregDate(curDate);
                    pedRegFacade.create(pr);
                    ped.setSprregId(sprRegionFacade.findById(idReg));
                }
                idOrg = Integer.parseInt(request.getParameter("orgId"));
                ped.setSprorgId(sprOrgFacade.findById(idOrg));
                idDolg = Integer.parseInt(request.getParameter("dolgId"));
                ped.setSprpeddolgId(sprPeddolgFacade.findById(idDolg));
                ped.setUserId(user.getUserId());
                ped.setDateUpd(curDate);
                pedFacade.edit(ped);
                break;
            default:
                break;
        }
        String userPath = "/pup/goodsave";
        String url = "/WEB-INF/pages" + userPath + ".jsp";
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
