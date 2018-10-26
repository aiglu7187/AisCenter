/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.Priyom;
import Entities.PriyomSotrud;
import Entities.SotrudDolgn;
import Entities.SprRegion;
import Entities.SprUsl;
import Entities.Users;
import Sessions.LoginLogFacade;
import Sessions.PriyomFacade;
import Sessions.PriyomSotrudFacade;
import Sessions.SotrudDolgnFacade;
import Sessions.SotrudFacade;
import Sessions.SprRegionFacade;
import Sessions.SprUslFacade;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
// сервлет для обработки событий на странице списка приёма
@WebServlet(name = "SpisokPriyomActionServlet",
        loadOnStartup = 1,
        urlPatterns = "/spisokpriyomaction")
public class SpisokPriyomActionServlet extends HttpServlet {

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
    PriyomFacade priyomFacade = new PriyomFacade();
    @EJB
    SprUslFacade sprUslFacade = new SprUslFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    SotrudFacade sotrudFacade = new SotrudFacade();
    @EJB
    PriyomSotrudFacade priyomSotrudFacade = new PriyomSotrudFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    SotrudDolgnFacade sotrudDolgnFacade = new SotrudDolgnFacade();

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

        String usl = request.getParameter("usl");
        String reg = request.getParameter("reg");
        String datepr1 = request.getParameter("datepr1");
        String datepr2 = request.getParameter("datepr2");
        Integer uslId = 0;
        try {
            uslId = Integer.parseInt(usl);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Integer regId = 0;
        try {
            regId = Integer.parseInt(reg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Integer sotrdolgnId = 0;
        String sotr = "";

        try {
            sotr = request.getParameter("sotr");
            sotrdolgnId = Integer.parseInt(sotr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date datePr1 = null;
        try {
            datePr1 = format.parse(datepr1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Date datePr2 = null;
        try {
            datePr2 = format.parse(datepr2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SprUsl sprUsl = null;
        try {
            sprUsl = sprUslFacade.findById(uslId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SprRegion sprReg = null;
        try {
            sprReg = sprRegionFacade.findById(regId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        List<Priyom> priyomListAll = priyomFacade.findPriyom(datePr1, datePr2, sprUsl, sprReg);

        List<Priyom> priyomList = new ArrayList<>();
        SotrudDolgn sotrudDolgn = null;
        if (sotrdolgnId != 0) {
            sotrudDolgn = sotrudDolgnFacade.findById(sotrdolgnId);
            for (Priyom pr : priyomListAll) {
                List<PriyomSotrud> prsotr = new ArrayList<>();
                try {
                    prsotr = priyomSotrudFacade.findByPriyom(pr);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (!prsotr.isEmpty()) { 
                    for (PriyomSotrud ps : prsotr) {                        
                        if (ps.getSotruddolgnId().equals(sotrudDolgn)) {
                            priyomList.add(pr);
                        }
                    }
                }
            }
        } else if (sotrdolgnId == 0) {
            priyomList.addAll(priyomListAll);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("dd.MM.yyyy");

        StringBuilder sb = new StringBuilder();
        sb.append("<priyoms>");
        for (Priyom priyom : priyomList) {
            List<PriyomSotrud> sotrudPriyom = priyomSotrudFacade.findByPriyom(priyom);
            sb.append("<priyom>");
            sb.append("<id>").append(priyom.getPriyomId()).append("</id>");
            String dPr = " ";
            try {
                dPr = simpleDateFormat.format(priyom.getPriyomDate());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            sb.append("<date>").append(dPr).append("</date>");
            sb.append("<usl>").append(priyom.getSpruslId().getSpruslName()).append("</usl>");
            sb.append("<reg>").append(priyom.getSprregId().getSprregName()).append("</reg>");
            sb.append("<sotr>");
            for (PriyomSotrud s : sotrudPriyom) {                
                sb.append(s.getSotruddolgnId().getSotrudId().getSotrudFIO()).append(" ");
            }
            sb.append("</sotr>");
            sb.append("</priyom>");
        }
        sb.append("</priyoms>");
        Boolean itsOk = true;

        if (itsOk) {
            response.setContentType("text/xml; charset=windows-1251");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(sb.toString());
        } else {
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
