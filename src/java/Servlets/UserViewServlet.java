/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.Roles;
import Entities.Sotrud;
import Entities.Users;
import Sessions.LoginLogFacade;
import Sessions.RolesFacade;
import Sessions.SotrudFacade;
import Sessions.UsersFacade;
import java.io.IOException;
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
@WebServlet(name = "UserViewServlet",
        loadOnStartup = 1,
        urlPatterns = {"/usersview"})

public class UserViewServlet extends HttpServlet {

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
    @EJB
    UsersFacade usersFacade = new UsersFacade();
    @EJB
    RolesFacade rolesFacade = new RolesFacade();
    @EJB
    SotrudFacade sotrudFacade = new SotrudFacade();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Users adminUser = (Users) session.getAttribute("user");
        String sessId = session.getId();
        try {
            List<LoginLog> logs = loginLogFacade.findLog(adminUser, sessId);
            LoginLog log = logs.get(0);
            Date lastAct = new Date(session.getLastAccessedTime());
            log.setLoginlogLastact(lastAct);
            loginLogFacade.edit(log);
        } catch (Exception ex) {

        }

        try {
            session.removeAttribute("us");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("roles");
        } catch (Exception ex) {
        }
        try {
            session.removeAttribute("sotrud");
        } catch (Exception ex) {
        }

        String idS = request.getParameter("id");
        Integer id = 0;
        try {
            id = Integer.parseInt(idS);
        } catch (Exception ex) {
        }
        Users user = null;
        if (id != 0) {
            user = usersFacade.findById(id);
        }

        session.setAttribute("us", user);

        List<Roles> roles = rolesFacade.findAll();
        session.setAttribute("roles", roles);

        List<Sotrud> sotrud = sotrudFacade.findAllSotrud();
        session.setAttribute("sotrud", sotrud);

        String url = "";
        String userPath = "";
        userPath = "/admin/userview";

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
