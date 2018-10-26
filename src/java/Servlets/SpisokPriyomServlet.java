/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.SotrudDolgn;
import Entities.SprRegion;
import Entities.SprUsl;
import Entities.Users;
import Other.RegionComparator;
import Other.SotrudDolgnComparator;
import Sessions.LoginLogFacade;
import Sessions.SotrudDolgnFacade;
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
// сервлет для перехода на страницы списка приёма
@WebServlet(name = "SpisokPriyomServlet",
        loadOnStartup = 1,
        urlPatterns = "/spisokpriyom")

public class SpisokPriyomServlet extends HttpServlet {

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
    SotrudDolgnFacade sotrudDolgnFacade = new SotrudDolgnFacade();
    @EJB
    SprUslFacade sprUslFacade = new SprUslFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();

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

        try {
            session.removeAttribute("regListPriyom");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("uslListPriyom");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("sotr");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("sotrListPriyom");
        } catch (Exception ex) {
        }

        List<SprRegion> regList = sprRegionFacade.findAll();
        Collections.sort(regList, new RegionComparator());

        List<SprUsl> uslList = sprUslFacade.findAll();

        session.setAttribute("regListPriyom", regList);
        session.setAttribute("uslListPriyom", uslList);

        String sotr = request.getParameter("id");
        session.setAttribute("sotr", sotr);
        if (sotr.equals("sotr")) {
            List<SotrudDolgn> sotrList = sotrudDolgnFacade.findAll();
            Collections.sort(sotrList, new SotrudDolgnComparator());
            session.setAttribute("sotrListPriyom", sotrList);
        }

        String userPath = "/pup/priyom";
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
