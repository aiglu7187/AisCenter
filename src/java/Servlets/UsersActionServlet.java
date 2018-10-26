/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.Users;
import Other.UsersComparator;
import Sessions.LoginLogFacade;
import Sessions.UsersFacade;
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

@WebServlet(name = "UsersActionServlet", 
        loadOnStartup = 1,
        urlPatterns = {"/usersaction"})

public class UsersActionServlet extends HttpServlet {

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
        }
        catch(Exception ex){
            
        }        
                
        String action = request.getParameter("action");
        StringBuffer sb = new StringBuffer();
        boolean itsOk = false;
        
        if (action.equals("search")){
            List<Users> users = new ArrayList<>();
            users = usersFacade.findAll();
            Collections.sort(users, new UsersComparator());
            if (users.size() > 0){
                sb.append("<users>");
                for (Users u : users){
                    sb.append("<user>");
                    sb.append("<id>").append(u.getUserId().toString()).append("</id>");
                    sb.append("<name>").append(u.getUserName()).append("</name>");
                    if (u.getSotrudId() != null) {
                        sb.append("<sotr>").append(u.getSotrudId().getSotrudFIO()).append("</sotr>");
                    }
                    else {
                        sb.append("<sotr>").append(" ").append("</sotr>");
                    }
                    sb.append("<act>").append(u.getUserActive().toString()).append("</act>");
                    sb.append("</user>");
                }
                sb.append("</users>");
                itsOk = true;
            }
        }
        if (itsOk){
            response.setContentType("text/xml; charset=windows-1251");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(sb.toString()); 
        } 
        else {
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
