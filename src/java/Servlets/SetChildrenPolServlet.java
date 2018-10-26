/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.Users;
import Sessions.ChildrenFacade;
import java.io.IOException;
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
 * @author Aiglu сервлет для выполнения определения и простановки пола детей
 * (разово)
 */
@WebServlet(name = "SetChildrenPolServlet",
        loadOnStartup = 1,
        urlPatterns = "/setchildrenpol")
public class SetChildrenPolServlet extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        if (!user.getUserId().equals(55)) {
            return;
        }

        List<Children> allChildren = childrenFacade.findAll();
        for (Children child : allChildren) {
            String patr = child.getChildPatr();
            String sex = null;
            if (patr != null) {
                patr = patr.trim();
                if (!patr.equals("")) {
                    if (patr.endsWith("ич")) {
                        sex = "м";
                    } else if (patr.endsWith("на")) {
                        sex = "ж";
                    } else if ((patr.toUpperCase().endsWith("ОГЛЫ")) || (patr.toUpperCase().endsWith("ОГЛИ"))
                            || (patr.toUpperCase().endsWith("УГЛИ"))) {
                        sex = "м";
                    } else if ((patr.toUpperCase().endsWith("КЫЗЫ")) || (patr.toUpperCase().endsWith("ГЫЗЫ")) || (patr.toUpperCase().endsWith("КИЗИ"))) {
                        sex = "ж";
                    }
                }
            }
            child.setChildPol(sex);
            childrenFacade.edit(child);
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
