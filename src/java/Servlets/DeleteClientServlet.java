/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.Family;
import Entities.LoginLog;
import Entities.Parents;
import Entities.Ped;
import Entities.Priyom;
import Entities.PriyomClient;
import Entities.Users;
import Sessions.ChildrenFacade;
import Sessions.FamilyFacade;
import Sessions.LoginLogFacade;
import Sessions.ParentsFacade;
import Sessions.PedFacade;
import Sessions.PriyomClientFacade;
import Sessions.PriyomFacade;
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
// сервлет удаления клиента
@WebServlet(name = "DeleteClientServlet",
            loadOnStartup = 1,
            urlPatterns = "/deleteclient")
public class DeleteClientServlet extends HttpServlet {

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
    PriyomFacade priyomFacade = new PriyomFacade();    
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    FamilyFacade familyFacade = new FamilyFacade();
        
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
        }
        catch(Exception ex){
            
        }        
        
        String clientIdS = request.getParameter("id");
        String kat = request.getParameter("kat");
        Integer clientId = 0;
        try{
            clientId = Integer.parseInt(clientIdS);
        }
        catch(Exception ex){   
            ex.printStackTrace();
        }
        
        if (clientId != 0){
            switch (kat) {
                case "children":
                    Children child = childrenFacade.findById(clientId);
                    childrenFacade.remove(child);
                    break;
                case "parents":
                    Parents parent = parentsFacade.findById(clientId);
                    parentsFacade.remove(parent);
                    break;
                case "ped":
                    Ped ped = pedFacade.findById(clientId);
                    pedFacade.remove(ped);
                    break;
                default:
                    break;
            }
            List<PriyomClient> priyomClient = priyomClientFacade.findByClient(clientId, kat);
            for (PriyomClient pc : priyomClient){
                Priyom priyom = pc.getPriyomId();
                priyomClientFacade.remove(pc);
                List<PriyomClient> pcAnother = priyomClientFacade.findByPriyom(priyom);
                if (pcAnother.isEmpty()){
                    priyomFacade.remove(priyom);
                }
            }
            List<Family> clientFamily = familyFacade.findByClient(kat, clientId);
            for (Family family : clientFamily) {
                familyFacade.remove(family);
            }
        }
        String userPath = "/pup/gooddelete";
        String url="/WEB-INF/pages" + userPath + ".jsp";
        try{
            request.getRequestDispatcher(url).forward(request, response);
        }
        catch (Exception ex){
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
