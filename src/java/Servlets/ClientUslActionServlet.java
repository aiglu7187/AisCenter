/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.LoginLog;
import Entities.Parents;
import Entities.Ped;
import Entities.SprOsnusl;
import Entities.SprRegion;
import Entities.SprUsl;
import Entities.Users;
import Sessions.LoginLogFacade;
import Sessions.PriyomFacade;
import Sessions.SprOsnuslFacade;
import Sessions.SprRegionFacade;
import Sessions.SprUslFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
// сервлет для обработки страницы списка клиентов по услугам
@WebServlet(name = "ClientUslActionServlet",
        loadOnStartup = 1,
        urlPatterns = "/clientuslaction")

public class ClientUslActionServlet extends HttpServlet {

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
    SprUslFacade sprUslFacade = new SprUslFacade();
    @EJB
    SprOsnuslFacade sprOsnuslFacade = new SprOsnuslFacade();
    @EJB
    PriyomFacade priyomFacade = new PriyomFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();

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

        String action = request.getParameter("action");
        StringBuffer sb = new StringBuffer();
        Boolean itsOk = false;

        if (action.equals("osnusl")) {
            String osnuslIdS = request.getParameter("id");
            Integer osnuslId = 0;
            try {
                osnuslId = Integer.parseInt(osnuslIdS);
            } catch (Exception ex) {
            }
            List<SprUsl> uslList = new ArrayList<>();
            if (osnuslId != 0) {
                SprOsnusl osnUsl = sprOsnuslFacade.findById(osnuslId);
                uslList = sprUslFacade.findByOsnusl(osnUsl);
            }

            sb.append("<usls>");
            for (SprUsl usl : uslList) {
                sb.append("<usl>");
                sb.append("<id>").append(usl.getSpruslId()).append("</id>");
                sb.append("<name>").append(usl.getSpruslName()).append("</name>");
                sb.append("</usl>");
            }
            sb.append("</usls>");
            itsOk = true;
        }
        if (action.equals("search")) {
            String osnUslIdS = request.getParameter("osnusl");
            String uslIdS = request.getParameter("usl");
            String regIdS = request.getParameter("reg");
            String regClIdS = request.getParameter("regcl");
            String date1 = request.getParameter("date1");
            String date2 = request.getParameter("date2");
            String kat = request.getParameter("kat");
            Integer osnUslId = 0;
            Integer uslId = 0;
            Integer regId = 0;
            Integer regClId = 0;

            try {
                osnUslId = Integer.parseInt(osnUslIdS);
            } catch (Exception ex) {
            }
            try {
                uslId = Integer.parseInt(uslIdS);
            } catch (Exception ex) {
            }
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }
            try {
                regClId = Integer.parseInt(regClIdS);
            } catch (Exception ex) {
            }

            SprOsnusl osnusl = null;
            SprUsl usl = null;
            SprRegion reg = null;
            SprRegion regcl = null;

            if (osnUslId != 0) {
                osnusl = sprOsnuslFacade.findById(osnUslId);
            }
            if (uslId != 0) {
                usl = sprUslFacade.findById(uslId);
            }
            if (regId != 0) {
                reg = sprRegionFacade.findById(regId);
            }
            if (regClId != 0) {
                regcl = sprRegionFacade.findById(regClId);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date datePr1 = null;
            try {
                datePr1 = dateFormat.parse(date1);
            } catch (ParseException ex) {
                Logger.getLogger(ClientUslActionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            Date datePr2 = null;
            try {
                datePr2 = dateFormat.parse(date2);
            } catch (ParseException ex) {
                Logger.getLogger(ClientUslActionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            List<Children> children = null;
            List<Parents> parents = null;
            List<Ped> peds = null;

            switch (kat) {
                case "all":
                    children = priyomFacade.findChildrenPriyom(osnusl, usl, reg, regcl, datePr1, datePr2);
                    parents = priyomFacade.findParentsPriyom(osnusl, usl, reg, regcl, datePr1, datePr2);
                    peds = priyomFacade.findPedPriyom(osnusl, usl, reg, regcl, datePr1, datePr2);
                    break;
                case "children":
                    children = priyomFacade.findChildrenPriyom(osnusl, usl, reg, regcl, datePr1, datePr2);
                    break;
                case "parents":
                    parents = priyomFacade.findParentsPriyom(osnusl, usl, reg, regcl, datePr1, datePr2);
                    break;
                case "ped":
                    peds = priyomFacade.findPedPriyom(osnusl, usl, reg, regcl, datePr1, datePr2);
                    break;
                default:
                    break;
            }

            sb.append("<clients>");
            if (children != null) {
                for (Children child : children) {
                    sb.append("<client>");
                    sb.append("<kat>children</kat>");
                    sb.append("<id>").append(child.getChildId()).append("</id>");
                    sb.append("<nom>").append(child.getChildNom()).append("</nom>");
                    sb.append("<fam>").append(child.getChildFam()).append("</fam>");
                    sb.append("<name>").append(child.getChildName()).append("</name>");
                    String patr = child.getChildPatr();
                    if ((patr == null) || (patr.equals(""))) {
                        patr = " ";
                    }
                    sb.append("<patr>").append(patr).append("</patr>");
                    sb.append("</client>");
                }
            }
            if (parents != null) {
                for (Parents parent : parents) {
                    sb.append("<client>");
                    sb.append("<kat>parents</kat>");
                    sb.append("<id>").append(parent.getParentId()).append("</id>");
                    sb.append("<nom>").append(parent.getParentNom()).append("</nom>");
                    sb.append("<fam>").append(parent.getParentFam()).append("</fam>");
                    sb.append("<name>").append(parent.getParentName()).append("</name>");
                    String patr = parent.getParentPatr();
                    if ((patr == null) || (patr.equals(""))) {
                        patr = " ";
                    }
                    sb.append("<patr>").append(patr).append("</patr>");
                    sb.append("</client>");
                }
            }
            if (peds != null) {
                for (Ped ped : peds) {
                    sb.append("<client>");
                    sb.append("<kat>ped</kat>");
                    sb.append("<id>").append(ped.getPedId()).append("</id>");
                    sb.append("<nom>").append(ped.getPedNom()).append("</nom>");
                    sb.append("<fam>").append(ped.getPedFam()).append("</fam>");
                    sb.append("<name>").append(ped.getPedName()).append("</name>");
                    String patr = ped.getPedPatr();
                    if ((patr == null) || (patr.equals(""))) {
                        patr = " ";
                    }
                    sb.append("<patr>").append(patr).append("</patr>");
                    sb.append("</client>");
                }
            }
            sb.append("</clients>");

            itsOk = true;
        }

        if (itsOk) {
            response.setContentType("text/xml; charset=windows-1251");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(sb.toString());
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
