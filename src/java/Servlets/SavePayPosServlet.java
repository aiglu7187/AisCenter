/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.LoginLog;
import Entities.PayuslClient;
import Entities.PayuslLesson;
import Entities.Payusllespos;
import Entities.SprPos;
import Entities.Users;
import Sessions.ChildrenFacade;
import Sessions.LoginLogFacade;
import Sessions.PayuslClientFacade;
import Sessions.PayuslLessonFacade;
import Sessions.PayusllesposFacade;
import Sessions.SprPosFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "SavePayPosServlet",
        loadOnStartup = 1,
        urlPatterns = "/savepaypos")

public class SavePayPosServlet extends HttpServlet {

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
    PayuslLessonFacade payuslLessonFacade = new PayuslLessonFacade();
    @EJB
    PayusllesposFacade payusllesposFacade = new PayusllesposFacade();
    @EJB
    PayuslClientFacade payuslClientFacade = new PayuslClientFacade();
    @EJB
    SprPosFacade sprPosFacade = new SprPosFacade();

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
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);

        Map<String, String[]> param = request.getParameterMap();
        List<String> poses = new ArrayList<>();
        for (Map.Entry<String, String[]> entry : param.entrySet()) {
            String par = entry.getKey();
            String[] val = entry.getValue();
            if (par.startsWith("pos")) {
                poses.add(par);
            }
        }
        for (String pos : poses) {
            String[] id = pos.split("_");
            String lessonIdS = id[0].substring(3);
            String childIdS = id[1];
            Integer lessonId = 0;
            if (lessonIdS != null) {
                lessonId = Integer.parseInt(lessonIdS);
            }
            Integer childId = 0;
            if (childIdS != null) {
                childId = Integer.parseInt(childIdS);
            }
            if ((lessonId != 0) && (childId != 0)) {
                PayuslLesson lesson = payuslLessonFacade.findById(lessonId);
                List<PayuslClient> clients = payuslClientFacade.findByPayusl(lesson.getPayuslId());
                Payusllespos pulpos = null;
                for (PayuslClient client : clients) {
                    if (client.getClientId().equals(childId)) {
                        pulpos = payusllesposFacade.findByLessonAndClient(lesson, client);
                    }
                }
                String sprposIdS = request.getParameter(pos);
                Integer sprposId = 0;
                if (sprposIdS != null) {
                    sprposId = Integer.parseInt(sprposIdS);
                }
                SprPos sprPos = null;
                if (sprposId != 0) {
                    sprPos = sprPosFacade.findById(sprposId);
                }
                if (pulpos != null) {
                    if (sprPos != null) {
                        pulpos.setSprposId(sprPos);
                        payusllesposFacade.edit(pulpos);
                    }
                }

            }
        }
        String userPath = "/pup/goodsave";
        String url = "/WEB-INF/pages" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
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
