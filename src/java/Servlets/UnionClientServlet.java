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
import Entities.ParentsOldfio;
import Entities.ParentsReg;
import Entities.PayDogovor;
import Entities.PayuslClient;
import Entities.Ped;
import Entities.PedOldfio;
import Entities.PedReg;
import Entities.PmpkParent;
import Entities.PriyomClient;
import Entities.PriyomSubject;
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
import Sessions.ParentsOldfioFacade;
import Sessions.ParentsRegFacade;
import Sessions.PayDogovorFacade;
import Sessions.PayuslClientFacade;
import Sessions.PayusllesposFacade;
import Sessions.PedFacade;
import Sessions.PedOldfioFacade;
import Sessions.PedRegFacade;
import Sessions.PmpkParentFacade;
import Sessions.PriyomClientFacade;
import Sessions.PriyomSubjectFacade;
import java.io.IOException;
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
// сервлет для слияния дублей
@WebServlet(name = "UnionClientServlet",
        loadOnStartup = 1,
        urlPatterns = "/unionclient")

public class UnionClientServlet extends HttpServlet {

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
    PriyomClientFacade priyomClientFacade = new PriyomClientFacade();
    @EJB
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    PriyomSubjectFacade priyomSubjectFacade = new PriyomSubjectFacade();
    @EJB
    ChildEduplaceFacade childEduplaceFacade = new ChildEduplaceFacade();
    @EJB
    ChildrenRegFacade childrenRegFacade = new ChildrenRegFacade();
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
    ParentsRegFacade parentsRegFacade = new ParentsRegFacade();
    @EJB
    PedRegFacade pedRegFacade = new PedRegFacade();
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
    @EJB
    ParentsOldfioFacade parentsOldfioFacade = new ParentsOldfioFacade();
    @EJB
    PedOldfioFacade pedOldfioFacade = new PedOldfioFacade();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
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

        String osnIdS = request.getParameter("osnid");
        String doubIdS = request.getParameter("doubid");
        String kat = request.getParameter("kat");
        Integer osnId = 0;
        try {
            osnId = Integer.parseInt(osnIdS);
        } catch (Exception ex) {
        }
        Integer doubId = 0;
        try {
            doubId = Integer.parseInt(doubIdS);
        } catch (Exception ex) {
        }

        // семья
        List<Family> familiesDoub = familyFacade.findByClient(kat, doubId);
        for (Family familyDoub : familiesDoub) {
            familyDoub.setClientId(osnId);
            familyFacade.edit(familyDoub);
        }
        List<Family> families = familyFacade.findByClient(kat, osnId);
        if (!families.isEmpty()) {
            Integer famNom = families.get(0).getFamNom();
            for (int i = 1; i < families.size(); i++) {
                if (families.get(i).getFamNom().equals(famNom)) {
                    familyFacade.remove(families.get(i));
                }
            }
        }

        if (kat.equals("children")) {
            Children osnChild = null;
            Children doubChild = null;
            if (osnId != 0) {
                osnChild = childrenFacade.findById(osnId);
            }
            if (doubId != 0) {
                doubChild = childrenFacade.findById(doubId);
            }

            if ((doubChild != null) && (osnChild != null)) {
                List<PriyomClient> prClList = priyomClientFacade.findByClient(doubId, kat);
                List<PriyomSubject> prSubList = priyomSubjectFacade.findBySubject(doubChild);
                for (PriyomClient prcl : prClList) {
                    prcl.setClientId(osnId);
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
                List<PayuslClient> puc = payuslClientFacade.findByClient(doubId, kat, status);
                for (PayuslClient pu : puc) {
                    pu.setClientId(osnId);
                    payuslClientFacade.edit(pu);
                }
                status = "подготовлено";
                List<PayuslClient> puc2 = payuslClientFacade.findByClient(doubId, kat, status);
                for (PayuslClient pu : puc2) {
                    pu.setClientId(osnId);
                    payuslClientFacade.edit(pu);
                }
                status = "окончено";
                List<PayuslClient> puc3 = payuslClientFacade.findByClient(doubId, kat, status);
                for (PayuslClient pu : puc3) {
                    pu.setClientId(osnId);
                    payuslClientFacade.edit(pu);
                }
                // старые ФИО
                List<ChildrenOldfio> oldfio = childrenOldfioFacade.findByChildId(doubChild);
                for (ChildrenOldfio co : oldfio) {
                    co.setChildId(osnChild);
                    childrenOldfioFacade.edit(co);
                }

                childrenFacade.remove(doubChild);
            }
        } else if (kat.equals("parents")) {
            Parents osnParent = null;
            Parents doubParent = null;
            if (osnId != 0) {
                osnParent = parentsFacade.findById(osnId);
            }
            if (doubId != 0) {
                doubParent = parentsFacade.findById(doubId);
            }

            if ((doubParent != null) && (osnParent != null)) {
                List<PriyomClient> prClList = priyomClientFacade.findByClient(doubId, kat);
                for (PriyomClient prcl : prClList) {
                    prcl.setClientId(osnId);
                    priyomClientFacade.edit(prcl);
                }
                List<ParentsReg> parentsReg = parentsRegFacade.findByParent(doubParent);
                for (ParentsReg pr : parentsReg) {
                    pr.setParentId(osnParent);
                    parentsRegFacade.edit(pr);
                }
                List<PayDogovor> parDog = payDogovorFacade.findByParent(doubParent);
                for (PayDogovor pd : parDog) {
                    pd.setParentId(osnParent);
                    payDogovorFacade.edit(pd);
                }
                String status = "готово";
                List<PayuslClient> puc = payuslClientFacade.findByClient(doubId, kat, status);
                for (PayuslClient pu : puc) {
                    pu.setClientId(osnId);
                    payuslClientFacade.edit(pu);
                }
                status = "подготовлено";
                List<PayuslClient> puc2 = payuslClientFacade.findByClient(doubId, kat, status);
                for (PayuslClient pu : puc2) {
                    pu.setClientId(osnId);
                    payuslClientFacade.edit(pu);
                }
                status = "окончено";
                List<PayuslClient> puc3 = payuslClientFacade.findByClient(doubId, kat, status);
                for (PayuslClient pu : puc3) {
                    pu.setClientId(osnId);
                    payuslClientFacade.edit(pu);
                }
                List<PmpkParent> pmpkParents = pmpkParentFacade.findByParent(doubParent);
                for (PmpkParent pp : pmpkParents) {
                    pp.setParentId(osnParent);
                    pmpkParentFacade.edit(pp);
                }
                // старые ФИО
                List<ParentsOldfio> oldfio = parentsOldfioFacade.findByParentId(doubParent);
                for (ParentsOldfio o : oldfio) {
                    o.setParentId(osnParent);
                    parentsOldfioFacade.edit(o);
                }

                parentsFacade.remove(doubParent);
            }
        } else if (kat.equals("ped")) {
            Ped osnPed = null;
            Ped doubPed = null;
            if (osnId != 0) {
                osnPed = pedFacade.findById(osnId);
            }
            if (doubId != 0) {
                doubPed = pedFacade.findById(doubId);
            }

            if ((doubPed != null) && (osnPed != null)) {
                List<PriyomClient> prClList = priyomClientFacade.findByClient(doubId, kat);
                for (PriyomClient prcl : prClList) {
                    prcl.setClientId(osnId);
                    priyomClientFacade.edit(prcl);
                }
                List<PedReg> pr = pedRegFacade.findByPed(doubPed);
                for (PedReg pedReg : pr) {
                    pedReg.setPedId(osnPed);
                    pedRegFacade.edit(pedReg);
                }

                String status = "готово";
                List<PayuslClient> puc = payuslClientFacade.findByClient(doubId, kat, status);
                for (PayuslClient pu : puc) {
                    pu.setClientId(osnId);
                    payuslClientFacade.edit(pu);
                }
                status = "подготовлено";
                List<PayuslClient> puc2 = payuslClientFacade.findByClient(doubId, kat, status);
                for (PayuslClient pu : puc2) {
                    pu.setClientId(osnId);
                    payuslClientFacade.edit(pu);
                }
                status = "окончено";
                List<PayuslClient> puc3 = payuslClientFacade.findByClient(doubId, kat, status);
                for (PayuslClient pu : puc3) {
                    pu.setClientId(osnId);
                    payuslClientFacade.edit(pu);
                }
                // старые ФИО
                List<PedOldfio> oldfio = pedOldfioFacade.findByPedId(doubPed);
                for (PedOldfio o : oldfio) {
                    o.setPedId(osnPed);
                    pedOldfioFacade.edit(o);
                }
                pedFacade.remove(doubPed);
            }
        }
        String userPath = "/pup/goodsave";
        String url = "/WEB-INF/pages" + userPath + ".jsp";
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        processRequest(request, response);
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
