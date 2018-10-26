/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Ipra18;
import Entities.Ipra18Prikaz;
import Entities.Kalendar;
import Entities.SprOmsu;
import Entities.SprOtherPmpk;
import Entities.SprRegion;
import Entities.Users;
import Sessions.Ipra18Facade;
import Sessions.Ipra18PrikazFacade;
import Sessions.KalendarFacade;
import Sessions.OtherpmpkRegionFacade;
import Sessions.SprOmsuFacade;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
@WebServlet(name = "CorrIpraServlet",
        loadOnStartup = 1,
        urlPatterns = "/corripra")

public class CorrIpraServlet extends HttpServlet {

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
    Ipra18Facade ipra18Facade = new Ipra18Facade();
    @EJB
    Ipra18PrikazFacade ipra18PrikazFacade = new Ipra18PrikazFacade();
    @EJB
    KalendarFacade kalendarFacade = new KalendarFacade();
    @EJB
    SprOmsuFacade sprOmsuFacade = new SprOmsuFacade();
    @EJB
    OtherpmpkRegionFacade otherpmpkRegionFacade = new OtherpmpkRegionFacade();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        if (!user.getUserId().equals(55)) {
            return;
        }

        List<Ipra18> ipraList = ipra18Facade.findAll();
        for (Ipra18 i : ipraList) {
            Ipra18Prikaz prikaz = ipra18PrikazFacade.findByIpra(i);
            if (i.getIpra18Status() != null) {
                if (!i.getIpra18Status().equals(0)) {
                    i.setIpra18Status(1);
                }
            }
            Date prikazDO = countDate(i.getIpra18VhdoD(), 3, "rab", "forward");
            prikaz.setIpra18prikazDoD(prikazDO);

            SprRegion reg = i.getChildId().getSprregId();
            // ОМСУ            
            List<SprOmsu> omsu = sprOmsuFacade.findByRegion(reg);
            if (omsu.size() > 0) {
                prikaz.setSpromsuId(omsu.get(0));
            }
            Date perech = countDate(i.getIpra18VhdoD(), 15, "rab", "forward");
            prikaz.setIpra18prikazPerechD(perech);

            // ТПМПК
            SprOtherPmpk tpmpk = null;
            try {
                tpmpk = otherpmpkRegionFacade.findByRegion(reg).getSprotherpmpkId();
            } catch (Exception ex) {
            }
            if (tpmpk != null) {
                prikaz.setSprotherpmpkId(tpmpk);
                Date tpmpkD = countDate(i.getIpra18VhdoD(), 3, "rab", "forward");
                prikaz.setIpra18prikazTpmpkD(tpmpkD);
            }
            prikaz.setIpra18prikazOmsuprD(null);
            prikaz.setIpra18prikazOznakD(null);

            ipra18PrikazFacade.edit(prikaz);
            ipra18Facade.edit(i);
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

    public Date countDate(Date date, Integer period, String type, String direct) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date resultDate = null;
        if ((period != 0) && (date != null)) {
            List<Kalendar> allDays = kalendarFacade.findAllDays();
            if (type.equals("rab")) {
                int k = 0;
                if (direct.equals("forward")) {
                    for (Kalendar day : allDays) {
                        if (date.equals(day.getKalendarDate())) {
                            k = 1;
                        }
                        if ((k > 0) && (k <= period)) {
                            if (day.getKalendarWeekend() == 0) {
                                resultDate = day.getKalendarDate();
                                k++;
                            }
                        }
                    }
                } else if (direct.equals("back")) {
                    k = 0;
                    for (int i = allDays.size() - 1; i >= 0; i--) {
                        Kalendar day = allDays.get(i);
                        if (date.equals(day.getKalendarDate())) {
                            k = 1;
                        }
                        if ((k > 0) && (k <= period)) {
                            if (day.getKalendarWeekend() == 0) {
                                resultDate = day.getKalendarDate();
                                k++;
                            }
                        }
                    }
                }

            } else if (type.equals("days")) {
                int k = 0;
                if (direct.equals("forward")) {
                    for (Kalendar day : allDays) {
                        if (date.equals(day.getKalendarDate())) {
                            k = 1;
                        }
                        if ((k > 0) && (k <= period)) {
                            resultDate = day.getKalendarDate();
                            k++;

                        }
                    }
                } else if (direct.equals("back")) {
                    for (int i = allDays.size() - 1; i >= 0; i--) {
                        Kalendar day = allDays.get(i);
                        if (date.equals(day.getKalendarDate())) {
                            k = 1;
                        }
                        if ((k > 0) && (k <= period)) {
                            resultDate = day.getKalendarDate();
                            k++;
                        }
                    }
                }
            } else if (type.equals("month")) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                if (direct.equals("forward")) {
                    if (c.get(Calendar.MONTH) + period < 11) {
                        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + period);
                    } else if (c.get(Calendar.MONTH) + period >= 11) {
                        c.set(Calendar.MONTH, period - (12 - c.get(Calendar.MONTH)));
                        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
                    }
                } else if (direct.equals("back")) {
                    if (c.get(Calendar.MONTH) - period > 0) {
                        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - period);
                    } else if (c.get(Calendar.MONTH) - period <= 0) {
                        c.set(Calendar.MONTH, 12 + (c.get(Calendar.MONTH) - period));
                        c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
                    }
                }
                resultDate = c.getTime();
            }
        }
        return resultDate;
    }
}
