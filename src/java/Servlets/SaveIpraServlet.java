/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.ChildStatus;
import Entities.Children;
import Entities.Ipra;
import Entities.LoginLog;
import Entities.Nom;
import Entities.SprRegion;
import Entities.Users;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenFacade;
import Sessions.IpraFacade;
import Sessions.LoginLogFacade;
import Sessions.NomFacade;
import Sessions.SprRegionFacade;
import Sessions.SprStatFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "SaveIpraServlet",
        loadOnStartup = 1,
        urlPatterns = "/saveipra")

public class SaveIpraServlet extends HttpServlet {

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
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    IpraFacade ipraFacade = new IpraFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    NomFacade nomFacade = new NomFacade();
    @EJB
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    SprStatFacade sprStatFacade = new SprStatFacade();

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

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");

        String ipraId = request.getParameter("ipraId");
        Ipra ipra = null;
        if (ipraId == null) {
            ipra = new Ipra();
        } else {
            ipra = ipraFacade.findById(Integer.parseInt(ipraId));
        }
        String childIdS = request.getParameter("childId");
        Children child = null;
        if (childIdS != null) {
            Integer childId = 0;
            try {
                childId = Integer.parseInt(childIdS);
            } catch (Exception ex) {

            }
            if (childId != 0) {
                child = childrenFacade.findById(childId);
            }
        } else {
            String childFam = request.getParameter("childFam");
            String childName = request.getParameter("childName");
            String childPatr = request.getParameter("childPatr");
            String childDrS = request.getParameter("childDr");
            Date childDr = null;
            try {
                childDr = format.parse(childDrS);
            } catch (ParseException ex) {
                Logger.getLogger(SaveIpraServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String regIdS = request.getParameter("regId");
            Integer regId = 0;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }
            SprRegion reg = null;
            if (regId != 0) {
                reg = sprRegionFacade.findById(regId);
            }
            Nom nom = new Nom();
            nomFacade.create(nom);

            child = new Children();
            child.setChildNom(nom.getNom());
            child.setChildFam(childFam);
            child.setChildName(childName);
            child.setChildPatr(childPatr);
            if (childDr != null) {
                child.setChildDr(childDr);
            }
            if (reg != null) {
                child.setSprregId(reg);
            }
            child.setUserId(user);
            child.setDateUpd(curDate);
            childrenFacade.create(child);
        }
        ipra.setChildId(child);
        String ipraDateexpS = request.getParameter("expDate");
        Date ipraDateexp = null;
        try {
            ipraDateexp = format.parse(ipraDateexpS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (ipraDateexp != null) {
            ipra.setIpraDateexp(ipraDateexp);
        }

        String ipraDateokS = request.getParameter("ipraDateOk");
        Date ipraDateok = null;
        try {
            ipraDateok = format.parse(ipraDateokS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (ipraDateok != null) {
            ipra.setIpraDateok(ipraDateok);
        }

        String ipraIshmseDS = request.getParameter("ishMseD");
        Date ipraIshmseD = null;
        try {
            ipraIshmseD = format.parse(ipraIshmseDS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (ipraIshmseD != null) {
            ipra.setIpraIshmseD(ipraIshmseD);
        }

        String ipraIshmseN = request.getParameter("ishMseN");
        if (ipraIshmseN != null) {
            ipra.setIpraIshmseN(ipraIshmseN);
        }

        String ipraN = request.getParameter("ipraN");
        if (ipraN != null) {
            ipra.setIpraN(ipraN);
        }

        String ipraOtchcenterS = request.getParameter("otchCenter");
        Date ipraOtchcenter = null;
        try {
            ipraOtchcenter = format.parse(ipraOtchcenterS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (ipraOtchcenter != null) {
            ipra.setIpraOtchcenter(ipraOtchcenter);
        }
        
        String ipraOtchcenterN = request.getParameter("otchCenterN");
        if (ipraOtchcenterN != null) {
            ipra.setIpraOtchcenterN(ipraOtchcenterN);
        }

        String ipraOtchdoS = request.getParameter("otchDo");
        Date ipraOtchdo = null;
        try {
            ipraOtchdo = format.parse(ipraOtchdoS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (ipraOtchdo != null) {
            ipra.setIpraOtchdo(ipraOtchdo);
        }

        String ipraOtchomsuS = request.getParameter("otchOmsu");
        Date ipraOtchomsu = null;
        try {
            ipraOtchomsu = format.parse(ipraOtchomsuS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (ipraOtchomsu != null) {
            ipra.setIpraOtchomsu(ipraOtchomsu);
        }

        String ipraPerechDS = request.getParameter("omsuD");
        Date ipraPerechD = null;
        try {
            ipraPerechD = format.parse(ipraPerechDS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (ipraPerechD != null) {
            ipra.setIpraPerechD(ipraPerechD);
        }

        String ipraOmsuN = request.getParameter("omsuN");
        if (ipraOmsuN != null) {
            ipra.setIpraOmsuN(ipraOmsuN);
        }

        String prOmsuDS = request.getParameter("prOmsuD");
        Date prOmsuD = null;
        try {
            prOmsuD = format.parse(prOmsuDS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (prOmsuD != null) {
            ipra.setIpraPrikazOmsuD(prOmsuD);
        }

        String oznakDS = request.getParameter("oznakD");
        Date oznakD = null;
        try {
            oznakD = format.parse(oznakDS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (oznakD != null) {
            ipra.setIpraOznakom(oznakD);
        }

        String ipraPrikazDS = request.getParameter("prikazDoD");
        Date ipraPrikazD = null;
        try {
            ipraPrikazD = format.parse(ipraPrikazDS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (ipraPrikazD != null) {
            ipra.setIpraPrikazD(ipraPrikazD);
        }

        String ipraPrikazN = request.getParameter("prikazDoN");
        if (ipraPrikazN != null) {
            ipra.setIpraPrikazN(ipraPrikazN);
        }

        String ipraTpmpkDS = request.getParameter("tpmpkD");
        Date ipraTpmpkD = null;
        try {
            ipraTpmpkD = format.parse(ipraTpmpkDS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (ipraTpmpkDS != null) {
            ipra.setIpraTpmpkD(ipraTpmpkD);
        }

        String ipraVhdoDS = request.getParameter("vhDoD");
        Date ipraVhdoD = null;
        try {
            ipraVhdoD = format.parse(ipraVhdoDS);

        } catch (ParseException ex) {
            Logger.getLogger(SaveIpraServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        if (ipraVhdoD != null) {
            ipra.setIpraVhdoD(ipraVhdoD);
        }

        String ipraVhdoN = request.getParameter("vhDoN");
        if (ipraVhdoN != null) {
            ipra.setIpraVhdoN(ipraVhdoN);
        }

        String status = request.getParameter("status");
        if (status != null) {
            ipra.setIpraStatus(Integer.parseInt(status));
        } else {
            ipra.setIpraStatus(1);
        }
        ipra.setUserId(user);
        ipra.setDateUpd(curDate);

        if (ipraId == null) {
            ipraFacade.create(ipra);
        } else {
            ipraFacade.edit(ipra);
        }

        List<ChildStatus> chstList = childStatusFacade.findByChildAct(child);
        Boolean isDI = false;
        Boolean isTR = false;
        for (ChildStatus chst : chstList) {
            if (chst.getSprstatId().getSprstatInv() == 1) {
                isDI = true;
                chst.setChildstatusDateK(ipra.getIpraDateok());
                childStatusFacade.edit(chst);
            } else if (chst.getSprstatId().getSprstatName().equals("ТР")) {
                isTR = true;
            }
        }
        if (!isDI) {
            ChildStatus childStatus = new ChildStatus();
            childStatus.setChildId(child);
            childStatus.setSprstatId(sprStatFacade.findById(6));
            childStatus.setChildstatusDateN(ipra.getIpraDateexp());
            childStatus.setChildstatusDateK(ipra.getIpraDateok());
            childStatusFacade.create(childStatus);
        }
        if (!isTR) {
            ChildStatus childStatus = new ChildStatus();
            childStatus.setChildId(child);
            childStatus.setSprstatId(sprStatFacade.findById(2));
            childStatus.setChildstatusDateN(ipra.getIpraDateexp());
            childStatusFacade.create(childStatus);
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
