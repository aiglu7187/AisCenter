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

@WebServlet(name = "SaveUserServlet",
            loadOnStartup = 1,
            urlPatterns = "/savesuser")

public class SaveUserServlet extends HttpServlet {

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
    UsersFacade usersFacade = new UsersFacade();
    @EJB
    SotrudFacade sotrudFacade = new SotrudFacade();
    @EJB
    RolesFacade rolesFacade = new RolesFacade();
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
        Users adminUser = (Users) session.getAttribute("user");
        String sessId = session.getId();
        try {
            List<LoginLog> logs = loginLogFacade.findLog(adminUser, sessId);
            LoginLog log = logs.get(0);
            Date lastAct = new Date(session.getLastAccessedTime());
            log.setLoginlogLastact(lastAct);
            loginLogFacade.edit(log);
        }
        catch(Exception ex){
            
        }      
        
        String idS = request.getParameter("userId");
        String userName = request.getParameter("nam");
        String pass = request.getParameter("pass");
        String newpass = request.getParameter("newpass");
        String sotrIdS = request.getParameter("sotrudId");
        String roleIdS = request.getParameter("roleId");
        String actS = request.getParameter("act");
        
        Integer id = 0;
        try{
            id = Integer.parseInt(idS);
        }
        catch(Exception ex){}        
        Users user = null;
        try{
            user = usersFacade.findById(id);
        }
        catch(Exception ex){}
        
        Integer sotrId = 0;
        try{
            sotrId = Integer.parseInt(sotrIdS);
        }
        catch(Exception ex){}
        Sotrud sotrud = null;
        try{
            sotrud = sotrudFacade.findById(sotrId);
        }
        catch(Exception ex){}
        
        Integer roleId = 0;
        try{
            roleId = Integer.parseInt(roleIdS);
        }
        catch(Exception ex){}
        Roles role = null;
        try{
            role = rolesFacade.findById(roleId);
        }
        catch(Exception ex){}                
        
        Integer act = -1;
        try{
            act = Integer.parseInt(actS);
        }
        catch(Exception ex){}
        
        if (user != null){
            if (userName != null){
                user.setUserName(userName);
            }
            if (!newpass.equals("")){
                user.setUserPassword(newpass);
            }
            if (sotrud != null){
                user.setSotrudId(sotrud);
            }
            if (role != null){
                user.setRoleId(role);
            }
            if (act != -1){
                user.setUserActive(act);
            }
            user.setCenterRole(adminUser.getCenterRole());
           
            usersFacade.edit(user);
        }
        else{
            Users newUser = new Users();
            newUser.setUserName(userName);
            newUser.setUserPassword(pass);
           
            if (sotrud != null){
                newUser.setSotrudId(sotrud);
            }
            if (role != null){
                newUser.setRoleId(role);
            }
            newUser.setUserActive(1);
            newUser.setCenterRole(adminUser.getCenterRole());
            
            usersFacade.create(newUser);
        }
        
        String userPath = "/pup/goodsave";
        String url="/WEB-INF/pages" + userPath + ".jsp";
        try{
            request.getRequestDispatcher(url).forward(request, response);
        }
        catch (Exception ex){
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
