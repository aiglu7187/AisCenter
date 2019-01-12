/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.Priyom;
import Entities.SprRegion;
import Entities.SprUsl;
import Entities.Users;
import Sessions.LoginLogFacade;
import Sessions.PriyomFacade;
import Sessions.SprRegionFacade;
import Sessions.SprUslFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
// сервлет для перехода на главную страницу ПМПК
@WebServlet(name = "PmpkServlet",
        loadOnStartup = 1,
        urlPatterns = "/pmpk")

public class PmpkServlet extends HttpServlet {

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
    PriyomFacade priyomFacade = new PriyomFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    SprUslFacade sprUslFacade = new SprUslFacade();

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

        String url = "";
        String userPath = request.getServletPath();
        // запрашиваемое действие
        String action = request.getParameter("action");
        if (action.equals("main")) {
            userPath = "/pmpk/pmpkmain";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("pmpksearch")) {
            userPath = "/pmpk/pmpksearch";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("addpmpk")) {
            userPath = "/pmpk/addpmpk";
            url = "/WEB-INF/pages" + userPath + ".html";
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
        String sessId = session.getId();
        try {
            List<LoginLog> logs = loginLogFacade.findLog(user, sessId);
            LoginLog log = logs.get(0);
            Date lastAct = new Date(session.getLastAccessedTime());
            log.setLoginlogLastact(lastAct);
            loginLogFacade.edit(log);
        } catch (Exception ex) {

        }

        request.setCharacterEncoding("UTF-8");  // устанавливаем кодировку запроса

        long curTime = System.currentTimeMillis();  // текущая дата
        Date curDate = new Date(curTime);
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");  // формат даты

        StringBuilder sb = new StringBuilder(); // для составления xml для передачи клиенту        

        // карта всех параметров передаваемых клиентом
        Map<String, String[]> params = request.getParameterMap();

        // запрашиваемое действие
        String action = params.get("action")[0];
        if (action.equals("saveadd")) { // сохранить новое заседание ПМПК
            Priyom priyom = new Priyom();
            String priyomDateS = params.get("prDate")[0];   // дата заседания
            Date priyomDate = null;
            try {
                priyomDate = format.parse(priyomDateS); // преобразовываем в дату
            } catch (ParseException ex) {
                Logger.getLogger(PmpkServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            priyom.setPriyomDate(priyomDate);
            String regIdS = params.get("regSel")[0];    // ИД района проведения
            Integer regId = null;
            try {
                regId = Integer.parseInt(regIdS);   // преобразовываем в число
            } catch (Exception ex) {
            }
            SprRegion reg = null;
            if (regId != null) {
                reg = sprRegionFacade.findById(regId);  // ищем в справочнике район по ИД
            }
            priyom.setSprregId(reg);
            SprUsl usl = sprUslFacade.findById(1);  // ищем услугу - ПМПК            
            priyom.setSpruslId(usl);
            priyom.setUserId(user);
            priyom.setDateUpd(curDate);
            // создаём запись в БД
            try {
                priyomFacade.create(priyom);
            } catch (Exception ex) {
            }

            // если всё нормально - передаём клиенту
            if (priyom.getPriyomId() != null) {
                sb.append("<result>").append("saveadd").append("</result>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                sb.append("<result>").append("0").append("</result>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
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
