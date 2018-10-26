/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.LoginLog;
import Entities.PayDogovor;
import Entities.PayUsl;
import Entities.PayuslClient;
import Entities.Payusllespos;
import Entities.Users;
import Sessions.ChildrenFacade;
import Sessions.LoginLogFacade;
import Sessions.PayDogovorFacade;
import Sessions.PayUslFacade;
import Sessions.PayuslClientFacade;
import Sessions.PayusllesposFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@WebServlet(name = "ExpPayServlet",
        loadOnStartup = 1,
        urlPatterns = "/exppay")
public class ExpPayServlet extends HttpServlet {

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
    PayUslFacade payUslFacade = new PayUslFacade();
    @EJB
    PayuslClientFacade payuslClientFacade = new PayuslClientFacade();
    @EJB
    PayusllesposFacade payusllesposFacade = new PayusllesposFacade();
    @EJB
    PayDogovorFacade payDogovorFacade = new PayDogovorFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();

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
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");

        String userPath = "";
        String url = "";

        String uslS = request.getParameter("usl");
        String clid = request.getParameter("clid");
        String dateS = request.getParameter("date");
        Integer usl = 0;
        if (uslS != null) {
            try {
                usl = Integer.parseInt(uslS);
            } catch (Exception ex) {
            }
        }
        PayUsl payUsl = null;
        if (usl != 0) {
            try {
                payUsl = payUslFacade.findById(usl);
            } catch (Exception ex) {
            }
        }
        List<String> clientIds = new ArrayList<>();

        if (clid != null) {
            String[] clients = clid.split(";");
            for (String client : clients) {
                if (!client.equals("")) {
                    clientIds.add(client);
                }
            }
        }
        Date dateK = null;
        if (dateS != null) {
            try {
                dateK = format.parse(dateS);
            } catch (ParseException ex) {
                Logger.getLogger(ExpPayServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (payUsl != null) {
            for (String clientId : clientIds) {
                Integer clId = 0;
                try {
                    clId = Integer.parseInt(clientId);
                } catch (Exception ex) {
                }
                if (clId != 0) {
                    String status = "готово";
                    List<PayuslClient> puc = payuslClientFacade.findByClient(clId, "children", status);
                    for (PayuslClient p : puc) {
                        if (p.getPayuslId().equals(payUsl)) {
                            if (dateK != null) {
                                p.setPayuslDatek(dateK);
                            }
                            List<Payusllespos> pulp = payusllesposFacade.findByPayuslclient(p);
                            for (Payusllespos pp : pulp) {
                                if (pp.getPayusllessonId().getPayusllessonDate().getTime() > dateK.getTime()){
                                    payusllesposFacade.remove(pp);
                                }
                            }
                            payuslClientFacade.edit(p);
                        }
                    }
                }
            }
        }
        userPath = "/pup/goodsave";
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
