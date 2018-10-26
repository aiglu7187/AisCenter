/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.Nom;
import Entities.Parents;
import Entities.PayDogovor;
import Entities.SprRegion;
import Entities.Telephon;
import Entities.Users;
import Sessions.DogovorFacade;
import Sessions.LoginLogFacade;
import Sessions.NomFacade;
import Sessions.ParentsFacade;
import Sessions.PayDogovorFacade;
import Sessions.SprRegionFacade;
import Sessions.TelephonFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "SavePayDogServlet",
        loadOnStartup = 1,
        urlPatterns = "/savepaydog")

public class SavePayDogServlet extends HttpServlet {

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
    ParentsFacade parentsFacade = new ParentsFacade();
    @EJB
    NomFacade nomFacade = new NomFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    TelephonFacade telephonFacade = new TelephonFacade();

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
        String dogN = request.getParameter("dogN1");
        String dogDS = request.getParameter("dogD1");
        String dogClIdS = request.getParameter("dogClId1");
        Integer dogId = 0;
        try {
            dogId = Integer.parseInt(dogIdS);
        } catch (Exception ex) {
        }
        Date dogD = null;
        try {
            dogD = format.parse(dogDS);
        } catch (ParseException ex) {
            Logger.getLogger(SavePayDogServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        PayDogovor dogovor = null;
        if (dogId != 0) {
            dogovor = payDogovorFacade.findById(dogId);
        }
        if (dogovor != null) {
            dogovor.setPaydogN(dogN);
            dogovor.setPaydogD(dogD);
        }

        Integer dogClId = 0;
        try {
            dogClId = Integer.parseInt(dogClIdS);
        } catch (Exception ex) {
        }
        Parents parent = null;
        if (dogClId != 0) {
            parent = parentsFacade.findById(dogClId);
            dogovor.setParentId(parent);
        } else {
            parent = new Parents();
            Nom nom = new Nom();
            nomFacade.create(nom);
            parent.setParentNom(nom.getNom());
            String dogClFam = request.getParameter("dogClFam1");
            String dogClName = request.getParameter("dogClNam1");
            String dogClPatr = request.getParameter("dogClPatr1");
            parent.setParentFam(dogClFam);
            parent.setParentName(dogClName);
            parent.setParentPatr(dogClPatr);
            String regDogS = request.getParameter("regDog1");
            Integer regId = 0;
            try {
                regId = Integer.parseInt(regDogS);
            } catch (Exception ex) {
            }
            SprRegion reg = null;
            if (regId != 0) {
                reg = sprRegionFacade.findById(regId);
            }
            if (reg != null) {
                parent.setSprregId(reg);
            }
            parent.setUserId(user);
            parent.setDateUpd(curDate);
            parentsFacade.create(parent);
        }
        if (parent != null) {
            String tel = request.getParameter("telephon1");
            List<Telephon> telephons = telephonFacade.findByParent(parent);
            Telephon t = null;
            if (!telephons.isEmpty()) {
                t = telephons.get(0);
            }
            if (t == null) {
                t = new Telephon();
            }
            t.setParentId(parent);
            t.setTel(tel);            
            if (t.getTelId() != null) {
                telephonFacade.edit(t);
            } else {
                telephonFacade.create(t);
            }
            dogovor.setParentId(parent);
        }
        dogovor.setUserId(user);
        dogovor.setDateUpd(curDate);
        payDogovorFacade.edit(dogovor);
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
