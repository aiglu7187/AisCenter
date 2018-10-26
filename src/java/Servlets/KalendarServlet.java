/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Kalendar;
import Entities.LoginLog;
import Entities.Users;
import Other.MyCalendar;
import Sessions.KalendarFacade;
import Sessions.LoginLogFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
@WebServlet(name = "KalendarServlet",
        loadOnStartup = 1,
        urlPatterns = "/kalendar")

public class KalendarServlet extends HttpServlet {

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
    KalendarFacade kalendarFacade = new KalendarFacade();

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
        } catch (Exception ex) {

        }

        String action = request.getParameter("action");
        String userPath;
        String url;
        if (action.equals("toview")) {
            userPath = "/ipra/kalendarview";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("toadd")) {
            userPath = "/ipra/kalendaradd";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("lastyear")) {
            Date lastDate = kalendarFacade.getLastDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lastDate);
            int lastYear = calendar.get(Calendar.YEAR);
            StringBuffer sb = new StringBuffer();
            sb.append("<lastyear>").append(lastYear).append("</lastyear>");
            response.setContentType("text/xml; charset=windows-1251");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(sb.toString());

        } else if (action.equals("basecalendar")) {
            String yearS = request.getParameter("year");
            Integer year = 0;
            try {
                year = Integer.parseInt(yearS);
            } catch (Exception ex) {
            }
            if (year != 0) {
                List<MyCalendar> cList = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    Calendar firstDay = Calendar.getInstance();
                    firstDay.set(year, i, 1);
                    int countDays = firstDay.getActualMaximum(Calendar.DATE);
                    for (int j = 1; j <= countDays; j++) {
                        Calendar date = Calendar.getInstance();
                        date.set(year, i, j);
                        MyCalendar myDate = new MyCalendar(date.getTime());
                        cList.add(myDate);
                    }
                }
                try {
                    session.removeAttribute("year");
                } catch (Exception ex) {

                }
                try {
                    session.removeAttribute("cList");
                } catch (Exception ex) {

                }
                session.setAttribute("year", yearS);
                session.setAttribute("cList", cList);

                userPath = "/ipra/basekalendar";
                url = "/WEB-INF/pages" + userPath + ".jsp";
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (action.equals("savenewkalendar")) {
            String weekendS = request.getParameter("weekend");
            String[] weekendList = weekendS.split(",");

            String date1 = weekendList[0];
            String[] dates1 = date1.split("\\.");
            String yearS = dates1[2];
            Integer year = Integer.parseInt(yearS);

            // удалить из БД все записи за год
            List<Kalendar> allYear = kalendarFacade.findAllDaysOfYear(year);
            if (allYear.size() > 0) {
                for (Kalendar day : allYear) {
                    kalendarFacade.remove(day);
                }
            }

            List<MyCalendar> cList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                Calendar firstDay = Calendar.getInstance();
                firstDay.set(year, i, 1, 0, 0, 0);
                int countDays = firstDay.getActualMaximum(Calendar.DATE);
                for (int j = 1; j <= countDays; j++) {
                    Calendar date = Calendar.getInstance();
                    date.set(year, i, j, 0, 0, 0);
                    date.set(Calendar.MILLISECOND, 0);
                    MyCalendar myDate = new MyCalendar(date.getTime());
                    cList.add(myDate);
                }
            }

            for (MyCalendar mC : cList) {
                mC.setIsWeekend(Boolean.FALSE);
                for (String weekend : weekendList) {
                    String[] weekendDate = weekend.split("\\.");
                    Calendar wDate = Calendar.getInstance();
                    wDate.set(Integer.parseInt(weekendDate[2]), Integer.parseInt(weekendDate[1]) - 1, Integer.parseInt(weekendDate[0]), 0, 0, 0);
                    wDate.set(Calendar.MILLISECOND, 0);
                    Date wd = wDate.getTime();
                    Date mc = mC.getDate();
                    if (mc.equals(wd)) {
                        mC.setIsWeekend(Boolean.TRUE);
                    }
                }
                Kalendar kalendar = new Kalendar();
                kalendar.setKalendarDate(mC.getDate());
                if (mC.getIsWeekend()) {
                    kalendar.setKalendarWeekend(1);
                } else {
                    kalendar.setKalendarWeekend(0);
                }
                kalendarFacade.create(kalendar);
            }
        } else if (action.equals("allyears")) {
            List<Kalendar> allDays = kalendarFacade.findAllDays();
            List<MyCalendar> allMyDays = new ArrayList<>();
            for (Kalendar day : allDays) {
                MyCalendar myDay = new MyCalendar(day.getKalendarDate());
                allMyDays.add(myDay);
            }
            Set<Integer> years = new HashSet<>();
            for (MyCalendar myDay : allMyDays) {
                years.add(myDay.getYear());
            }
            StringBuilder sb = new StringBuilder();
            sb.append("<years>");
            for (Integer year : years) {
                sb.append("<year>");
                sb.append(year.toString());
                sb.append("</year>");
            }
            sb.append("</years>");

            response.setContentType("text/xml; charset=windows-1251");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(sb.toString());
        } else if (action.equals("calendar")) {
            String yearS = request.getParameter("year");
            Integer year = Integer.parseInt(yearS);
            List<Kalendar> allDaysOfYear = kalendarFacade.findAllDaysOfYear(year);
            List<MyCalendar> cList = new ArrayList<>();
            for (Kalendar day : allDaysOfYear) {
                MyCalendar c = new MyCalendar(day.getKalendarDate());
                Integer weekend = day.getKalendarWeekend();
                if (weekend == 1) {
                    c.setIsWeekend(Boolean.TRUE);
                } else if (weekend == 0) {
                    c.setIsWeekend(Boolean.FALSE);
                }

                cList.add(c);
            }
            try {
                session.removeAttribute("year");
            } catch (Exception ex) {

            }
            try {
                session.removeAttribute("cList");
            } catch (Exception ex) {

            }
            session.setAttribute("year", yearS);
            session.setAttribute("cList", cList);

            userPath = "/ipra/editkalendar";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
