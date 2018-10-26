/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.IpraIshnom;
import Entities.LoginLog;
import Entities.Users;
import Sessions.IpraIshnomFacade;
import Sessions.LoginLogFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
@WebServlet(name = "IpraIshnomServlet",
        loadOnStartup = 1,
        urlPatterns = "/ipraishnom")

public class IpraIshnomServlet extends HttpServlet {

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
    IpraIshnomFacade ipraIshnomFacade = new IpraIshnomFacade();

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
        if (user == null) {
            String url = "/index.jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }
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
        String userPath;
        String url;
        if (action.equals("open")) {
            userPath = "/ipra/ipraishnom";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            List<IpraIshnom> ipraIshnomList = ipraIshnomFacade.findAll();
            Integer nom = 0;
            String suffix = "";
            if (!ipraIshnomList.isEmpty()) {
                nom = ipraIshnomList.get(0).getIpraishnomNom();
                suffix = ipraIshnomList.get(0).getIpraishnomSuffix();
            }
            try {
                session.removeAttribute("nom");
            } catch (Exception ex) {
            }
            session.setAttribute("nom", nom);
            try {
                session.removeAttribute("suffix");
            } catch (Exception ex) {
            }
            session.setAttribute("suffix", suffix);

            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        if (user == null) {
            String url = "/index.jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }
        String sessId = session.getId();
        try {
            List<LoginLog> logs = loginLogFacade.findLog(user, sessId);
            LoginLog log = logs.get(0);
            Date lastAct = new Date(session.getLastAccessedTime());
            log.setLoginlogLastact(lastAct);
            loginLogFacade.edit(log);
        } catch (Exception ex) {
        }

        request.setCharacterEncoding("UTF-8");
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");

        Map<String, String[]> params = request.getParameterMap();

        String action = params.get("action")[0];
        if (action.equals("save")) {
            StringBuilder sb = new StringBuilder();
            String nomS = params.get("nom")[0];
            String suffix = params.get("suffix")[0];
            Integer nom = 0;
            boolean isNom = true;
            try {
                nom = Integer.parseInt(nomS);
            } catch (Exception ex) {
                isNom = false;
            }

            if (!isNom) {
                sb.append("<result>").append("0").append("</result>");
            } else {
                IpraIshnom ipraIshnom = null;
                boolean isNew = false;
                try {
                    ipraIshnom = ipraIshnomFacade.findAll().get(0);
                } catch (Exception ex) {
                    ipraIshnom = new IpraIshnom();
                    isNew = true;
                }
                ipraIshnom.setIpraishnomNom(nom);
                ipraIshnom.setIpraishnomSuffix(suffix);
                try {
                    if (isNew) {
                        ipraIshnomFacade.create(ipraIshnom);
                    } else {
                        ipraIshnomFacade.edit(ipraIshnom);
                    }
                    sb.append("<result>").append("1").append("</result>");
                } catch (Exception ex){
                    sb.append("<result>").append("0").append("</result>");
                }
            }

            if (sb.length() > 0) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
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
