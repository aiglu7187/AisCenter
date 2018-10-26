/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.PayDogovor;
import Entities.Payment;
import Entities.Users;
import Sessions.LoginLogFacade;
import Sessions.PayDogovorFacade;
import Sessions.PaymentFacade;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "SavePaymentServlet",
        loadOnStartup = 1,
        urlPatterns = "/savepayment")
public class SavePaymentServlet extends HttpServlet {

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
    PayDogovorFacade payDogovorFacade = new PayDogovorFacade();
    @EJB
    PaymentFacade paymentFacade = new PaymentFacade();

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
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");

        String dogIdS = request.getParameter("dogId");
        String paymentFio = request.getParameter("paymentFio");
        String paymentDateS = request.getParameter("paymentDate");
        String paymentSumS = request.getParameter("paymentSum");

        Integer dogId = 0;
        if (dogIdS != null) {
            try {
                dogId = Integer.parseInt(dogIdS);
            } catch (Exception ex) {
            }
        }
        PayDogovor dogovor = null;
        if (dogId != 0) {
            dogovor = payDogovorFacade.findById(dogId);
        }

        if (dogovor != null) {
            Payment payment = new Payment();
            payment.setUserId(user);
            payment.setDateUpd(curDate);
            payment.setPaydogId(dogovor);
            payment.setPaymentFio(paymentFio);
            Date paymentDate = null;
            if (paymentDateS != null) {
                try {
                    paymentDate = format.parse(paymentDateS);
                } catch (Exception ex) {
                }
            }
            if (paymentDate != null) {
                payment.setPaymentDate(paymentDate);
            }
            paymentSumS = paymentSumS.replaceAll(",", ".");
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();            
            symbols.setDecimalSeparator('.');
            String pattern = "##0.0#";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);
            BigDecimal paymentSum = null;    
            try {
                paymentSum = (BigDecimal) decimalFormat.parse(paymentSumS);
            } catch (Exception ex) {
            }            
            if (paymentSum != null) {
                payment.setPaymentSum(paymentSum);
            }
            paymentFacade.create(payment);
            String userPath = "/pup/goodsave";
            String url = "/WEB-INF/pages" + userPath + ".jsp";

            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
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
