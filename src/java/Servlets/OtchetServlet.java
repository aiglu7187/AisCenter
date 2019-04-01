/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.SprObrType;
import Entities.SprRegion;
import Entities.SprUsl;
import Entities.Users;
import Other.RegionComparator;
import Sessions.LoginLogFacade;
import Sessions.SprObrTypeFacade;
import Sessions.SprRegionFacade;
import Sessions.SprUslFacade;
import java.io.IOException;
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
// сервлет для открытия страницы отчета
@WebServlet(name = "OtchetServlet",
        loadOnStartup = 1,
        urlPatterns = "/otchet")

public class OtchetServlet extends HttpServlet {

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
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    SprObrTypeFacade sprObrTypeFacade = new SprObrTypeFacade();
    @EJB
    SprUslFacade sprUslFacade = new SprUslFacade();

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

        String id = request.getParameter("id");
        String url = "";
        String userPath = "";

        if (id.equals("goszad")) {
            userPath = "/otchet/otchetgz";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("rpmpk")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);
            userPath = "/pmpk/reestrpmpk";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("statpmpk")) {
            userPath = "/pmpk/statpmpk";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("status")) {
            userPath = "/otchet/otchetstat";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("problem")) {
            userPath = "/otchet/otchetproblem";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("age")) {
            userPath = "/otchet/otchetage";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("pmpkstatus")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);
            userPath = "/pmpk/pmpkstatus";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("pmpkipr")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);
            userPath = "/pmpk/pmpkipr";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("pmpkgia")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);
            userPath = "/pmpk/pmpkgia";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("pmpkter")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);
            userPath = "/pmpk/pmpkter";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("pmpkrek")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);
            userPath = "/pmpk/pmpkrek";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("statpmpkstatus")) {
            userPath = "/pmpk/statpmpkstatus";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("statpmpkrek")) {
            userPath = "/pmpk/statpmpkrek";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("statpmpkpar")) {
            userPath = "/pmpk/statpmpkpar";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("rpmpkmonit")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);
            userPath = "/pmpk/monitpmpk";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("toovzfgos")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);
            userPath = "/pmpk/ovzfgos";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("toovzarch")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);

            List<SprObrType> sprObrTypes = sprObrTypeFacade.findAll();
            try {
                session.removeAttribute("sprObrTypes");
            } catch (Exception ex) {
            }
            session.setAttribute("sprObrTypes", sprObrTypes);

            userPath = "/pmpk/ovzarch";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("reestrusl")) {
            userPath = "/otchet/reestrusl";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            List<SprUsl> sprUslList = sprUslFacade.findAllUsl();
            try {
                session.removeAttribute("sprUslList");
            } catch (Exception ex) {
            }
            session.setAttribute("sprUslList", sprUslList);
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("rpmpkfull")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);
            userPath = "/pmpk/reestrpmpkfull";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("pmpkfirstovz")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);
            userPath = "/pmpk/pmpkfirstovz";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("statuslkat")) {
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            session.setAttribute("regions", regions);
            userPath = "/otchet/otchetstatuslkat";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("ranniy")){
            userPath = "/otchet/otchetranniy";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("consultpar")){
            userPath = "/otchet/otchetconsultpar";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
