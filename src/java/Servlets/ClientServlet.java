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
import Entities.ChildrenEducondEq;
import Entities.ChildrenEducondRek;
import Entities.ChildrenOldfio;
import Entities.ChildrenReg;
import Entities.Family;
import Entities.Ipra;
import Entities.Ipra18;
import Entities.LoginLog;
import Entities.MonitoringEducond;
import Entities.Parents;
import Entities.ParentsOldfio;
import Entities.ParentsReg;
import Entities.PayuslClient;
import Entities.PayuslSotrud;
import Entities.Ped;
import Entities.PedOldfio;
import Entities.PedReg;
import Entities.PriyomClient;
import Entities.PriyomSotrud;
import Entities.PriyomSubject;
import Entities.SprOrg;
import Entities.SprPeddolg;
import Entities.SprRegion;
import Entities.Telephon;
import Entities.Users;
import Other.ChildStatusComparator;
import Other.FamilyMember;
import Other.RegionComparator;
import Sessions.ChildEduplaceFacade;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenEducondEqFacade;
import Sessions.ChildrenEducondFacade;
import Sessions.ChildrenEducondRekFacade;
import Sessions.ChildrenFacade;
import Sessions.ChildrenOldfioFacade;
import Sessions.ChildrenRegFacade;
import Sessions.FamilyFacade;
import Sessions.Ipra18Facade;
import Sessions.IpraFacade;
import Sessions.LoginLogFacade;
import Sessions.MonitoringEducondFacade;
import Sessions.ParentsFacade;
import Sessions.ParentsOldfioFacade;
import Sessions.ParentsRegFacade;
import Sessions.PayuslClientFacade;
import Sessions.PayuslSotrudFacade;
import Sessions.PedFacade;
import Sessions.PedOldfioFacade;
import Sessions.PedRegFacade;
import Sessions.PriyomClientFacade;
import Sessions.PriyomSotrudFacade;
import Sessions.PriyomSubjectFacade;
import Sessions.SprOrgFacade;
import Sessions.SprPeddolgFacade;
import Sessions.SprRegionFacade;
import Sessions.TelephonFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
// сервлет для страницы с информацией о клиенте
@WebServlet(name = "ClientServlet",
        loadOnStartup = 1,
        urlPatterns = "/client")

public class ClientServlet extends HttpServlet {

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
    PriyomClientFacade priyomClientFacade = new PriyomClientFacade();
    @EJB
    PriyomSotrudFacade priyomSotrudFacade = new PriyomSotrudFacade();
    @EJB
    PriyomSubjectFacade priyomSubjectFacade = new PriyomSubjectFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    ChildEduplaceFacade childEduplaceFacade = new ChildEduplaceFacade();
    @EJB
    IpraFacade ipraFacade = new IpraFacade();
    @EJB
    ChildrenRegFacade childrenRegFacade = new ChildrenRegFacade();
    @EJB
    ParentsRegFacade parentsRegFacade = new ParentsRegFacade();
    @EJB
    PedRegFacade pedRegFacade = new PedRegFacade();
    @EJB
    TelephonFacade telephonFacade = new TelephonFacade();
    @EJB
    PayuslClientFacade payuslClientFacade = new PayuslClientFacade();
    @EJB
    PayuslSotrudFacade payuslSotrudFacade = new PayuslSotrudFacade();
    @EJB
    FamilyFacade familyFacade = new FamilyFacade();
    @EJB
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    ChildrenOldfioFacade childrenOldfioFacade = new ChildrenOldfioFacade();
    @EJB
    ParentsOldfioFacade parentsOldfioFacade = new ParentsOldfioFacade();
    @EJB
    PedOldfioFacade pedOldfioFacade = new PedOldfioFacade();
    @EJB
    Ipra18Facade ipra18Facade = new Ipra18Facade();
    @EJB
    ChildrenEducondFacade childrenEducondFacade = new ChildrenEducondFacade();
    @EJB
    ChildrenEducondRekFacade childrenEducondRekFacade = new ChildrenEducondRekFacade();
    @EJB
    ChildrenEducondEqFacade childrenEducondEqFacade = new ChildrenEducondEqFacade();
    @EJB
    MonitoringEducondFacade monitoringEducondFacade = new MonitoringEducondFacade();

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

        try {
            session.removeAttribute("reg");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("client");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("org");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("dolg");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("prcl");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("prsotr");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("kat");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("prsub");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("eduplace");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("childipra");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("childreg");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("parentreg");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("pedreg");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("parenttel");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("pucl");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("pusotr");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("familyList");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("childstats");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("clientOldFio");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("educondList");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("educondrekList");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("educondeqList");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("monitEducondList");
        } catch (Exception ex) {
        }
        
        String kat = request.getParameter("kat");
        String idS = request.getParameter("id");
        Integer id = Integer.parseInt(idS);

        List<SprRegion> regions = sprRegionFacade.findNoCenter();
        Collections.sort(regions, new RegionComparator());
        session.setAttribute("reg", regions);

        List<SprOrg> org = sprOrgFacade.findAll();
        List<SprPeddolg> dolg = sprPeddolgFacade.findAll();
        Boolean itsOk = true;

        Children child = null;
        switch (kat) {
            case "children":
                try {
                    child = childrenFacade.findById(id);
                    session.setAttribute("client", child);
                    List<ChildrenReg> cr = childrenRegFacade.findByChild(child);
                    session.setAttribute("childreg", cr);
                    List<ChildrenOldfio> childOldfio = childrenOldfioFacade.findByChildId(child);
                    if (!childOldfio.isEmpty()) {
                        session.setAttribute("clientOldFio", childOldfio);
                    }
                } catch (Exception ex) {
                    itsOk = false;
                }
                ;
                break;
            case "parents":
                try {
                    Parents parent = parentsFacade.findById(id);
                    session.setAttribute("client", parent);
                    List<ParentsReg> cr = parentsRegFacade.findByParent(parent);
                    session.setAttribute("parentreg", cr);
                    List<Telephon> tel = telephonFacade.findByParent(parent);
                    session.setAttribute("parenttel", tel);
                    List<ParentsOldfio> parentOldfio = parentsOldfioFacade.findByParentId(parent);
                    if (!parentOldfio.isEmpty()) {
                        session.setAttribute("clientOldFio", parentOldfio);
                    }
                } catch (Exception ex) {
                    itsOk = false;
                }
                ;
                break;
            case "ped":
                try {
                    Ped ped = pedFacade.findById(id);
                    session.setAttribute("client", ped);
                    session.setAttribute("org", org);
                    session.setAttribute("dolg", dolg);
                    List<PedReg> cr = pedRegFacade.findByPed(ped);
                    session.setAttribute("pedreg", cr);
                    List<PedOldfio> pedOldfio = pedOldfioFacade.findByPedId(ped);
                    if (!pedOldfio.isEmpty()) {
                        session.setAttribute("clientOldFio", pedOldfio);
                    }
                } catch (Exception ex) {
                    itsOk = false;
                }
                ;
                break;
            default:
                break;
        }
        if (child != null) {
            List<ChildStatus> childStatusList = new ArrayList<>();
            childStatusList.addAll(childStatusFacade.findByChild(child));
            Collections.sort(childStatusList, new ChildStatusComparator());
            session.setAttribute("childstats", childStatusList);
        }
        List<PriyomClient> prCl = priyomClientFacade.findByClient(id, kat);
        session.setAttribute("prcl", prCl);

        List<PriyomSubject> prSub = null;
        if (child != null) {
            prSub = priyomSubjectFacade.findBySubject(child);
        }
        if (prSub != null) {
            session.setAttribute("prsub", prSub);
        }

        List<ChildEduplace> eduplaces = null;
        if (child != null) {
            eduplaces = childEduplaceFacade.findByChild(child);
        }
        if (eduplaces != null) {
            session.setAttribute("eduplace", eduplaces);
        }

        List<Ipra> ipra = null;
        if (child != null) {
            ipra = ipraFacade.findByChild(child);
        }
        if (ipra != null) {
            session.setAttribute("childipra", ipra);
        }

        List<Ipra18> ipra18 = null;
        if (child != null) {
            ipra18 = ipra18Facade.findByChild(child);
        }
        if (ipra18 != null) {
            session.setAttribute("childipra18", ipra18);
        }

        List<PriyomSotrud> prSotrud = new ArrayList<>();
        for (PriyomClient pr : prCl) {
            List<PriyomSotrud> prSotr = priyomSotrudFacade.findByPriyom(pr.getPriyomId());
            for (PriyomSotrud prS : prSotr) {
                prSotrud.add(prS);
            }
        }
        if (prSub != null) {
            for (PriyomSubject pr : prSub) {
                List<PriyomSotrud> prSotr = priyomSotrudFacade.findByPriyom(pr.getPriyomId());
                for (PriyomSotrud prS : prSotr) {
                    prSotrud.add(prS);
                }
            }
        }
        session.setAttribute("prsotr", prSotrud);

        String status = "готово";
        List<PayuslClient> pucl = payuslClientFacade.findByClient(id, kat, status);
        session.setAttribute("pucl", pucl);

        List<PayuslSotrud> pusotr = new ArrayList<>();
        for (PayuslClient p : pucl) {
            List<PayuslSotrud> payUslSotr = payuslSotrudFacade.findByPayUsl(p.getPayuslId());
            pusotr.addAll(payUslSotr);
        }
        session.setAttribute("pusotr", pusotr);

        List<Family> family = familyFacade.findByClient(kat, id);
        List<FamilyMember> familyList = new ArrayList<>();
        // список семьи
        for (Family fam : family) {
            List<Family> familyByNom = familyFacade.findByFamNom(fam.getFamNom());
            for (Family famByNom : familyByNom) {
                if ((famByNom.getClientId().equals(id)) && (famByNom.getFamKatcl().equals(kat))) {
                } else {
                    FamilyMember f = new FamilyMember();
                    String memberKat = famByNom.getFamKatcl();
                    f.setKat(memberKat);
                    Integer memberId = famByNom.getClientId();
                    f.setId(memberId);
                    if (memberKat.equals("children")) {
                        Children ch = childrenFacade.findById(memberId);
                        f.setFio(ch.getFIO());
                        f.setDateR(ch.getChildDr());
                    } else if (memberKat.equals("parents")) {
                        Parents p = parentsFacade.findById(memberId);
                        f.setFio(p.getFIO());
                    }
                    familyList.add(f);
                }
            }
        }
        session.setAttribute("familyList", familyList);

        String url = "";
        String userPath = "";
        session.setAttribute("kat", kat);

        List<MonitoringEducond> monitEducondList = monitoringEducondFacade.findByChild(child);
        session.setAttribute("monitEducondList", monitEducondList);
        List<ChildrenEducond> educondList = childrenEducondFacade.findByChild(child);        
        session.setAttribute("educondList", educondList);
        List<ChildrenEducondRek> educondrekList = new ArrayList<>();
        for (ChildrenEducond ce : educondList) {
            educondrekList.addAll(childrenEducondRekFacade.findByChildreneducond(ce));
        }
        session.setAttribute("educondrekList", educondrekList);
        List<ChildrenEducondEq> educondeqList = new ArrayList<>();
        for (ChildrenEducond ce : educondList) {
            educondeqList.addAll(childrenEducondEqFacade.findByChildreneducond(ce));
        }
        session.setAttribute("educondeqList", educondeqList);
        
        if (itsOk == true) {
            userPath = "/pup/clientview";
        } else if (itsOk == false) {
            userPath = "/pup/spisokclient";

        }
        url = "/WEB-INF/pages" + userPath + ".jsp";
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
