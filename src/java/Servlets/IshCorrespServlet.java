/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Ipra;
import Entities.Ipra18Prikaz;
import Entities.LoginLog;
import Entities.Users;
import Other.Letter;
import Other.LetterComparator;
import Sessions.Ipra18PrikazFacade;
import Sessions.IpraFacade;
import Sessions.LoginLogFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
// сервлет для реестра исходящей корреспонденции
@WebServlet(name = "IshCorrespServlet",
        loadOnStartup = 1,
        urlPatterns = "/ishcorresp")

public class IshCorrespServlet extends HttpServlet {

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
    IpraFacade ipraFacade = new IpraFacade();
    @EJB
    Ipra18PrikazFacade ipra18PrikazFacade = new Ipra18PrikazFacade();

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
        if (action.equals("list")) {
            // сопроводительные письма к отчётам ОЦППМСП в ДО из старой ИПРА
            List<Ipra> ipraList = ipraFacade.findAllCorresp();
            // сопроводительные письма к отчётам ОЦППМСП в ДО, письма в ТПМПК (запросы), 
            // письма в ОМСУ (перечни) из новой ИПРА
            List<Ipra18Prikaz> ipra18PrikazList = ipra18PrikazFacade.findAllCorresp();
            List<Letter> letterList = new ArrayList<>();
            for (Ipra ipra : ipraList) {
                Letter letter = new Letter();
                letter.setDate(ipra.getIpraOtchcenter());
                letter.setId(ipra.getIpraId());
                letter.setName("Сопроводительное письмо к отчёту ОЦППМСП в ДО");
                letter.setNomer(ipra.getIpraOtchcenterN());
                letter.setType(Letter.IPRA_OTCHET_CENTER);
                letter.setFio(ipra.getChildId().getFIO());
                letterList.add(letter);
            }

            for (Ipra18Prikaz ip : ipra18PrikazList) {
                if (ip.getIpra18prikazOtchcenterN() != null) {
                    Letter letter = new Letter();
                    letter.setDate(ip.getIpra18prikazOtchcenter());
                    letter.setId(ip.getIpra18prikazId());
                    letter.setName("Сопроводительное письмо к отчёту ОЦППМСП в ДО");
                    letter.setNomer(ip.getIpra18prikazOtchcenterN());
                    letter.setType(Letter.IPRA18_OTCHET_CENTER);
                    letter.setFio(ip.getIpra18Id().getChildId().getFIO());
                    letterList.add(letter);
                }
                if (ip.getIpra18prikazPerechN() != null) {
                    Letter letter = new Letter();
                    letter.setDate(ip.getIpra18prikazPerechD());
                    letter.setId(ip.getIpra18prikazId());
                    letter.setName("Письмо в ОМСУ (перечень мероприятий)");
                    letter.setNomer(ip.getIpra18prikazPerechN());
                    letter.setType(Letter.IPRA18_PERECHEN);
                    letter.setFio(ip.getIpra18Id().getChildId().getFIO());
                    letterList.add(letter);
                }
                if (ip.getIpra18prikazTpmpkN() != null) {
                    Letter letter = new Letter();
                    letter.setDate(ip.getIpra18prikazTpmpkD());
                    letter.setId(ip.getIpra18prikazId());
                    letter.setName("Запрос в ТПМПК");
                    letter.setNomer(ip.getIpra18prikazTpmpkN());
                    letter.setType(Letter.IPRA18_TPMPK);
                    letter.setFio(ip.getIpra18Id().getChildId().getFIO());
                    letterList.add(letter);
                }

            }
            
            Collections.sort(letterList, new LetterComparator());

            try {
                session.removeAttribute("letterList");
            } catch (Exception ex) {
            }
            
            session.setAttribute("letterList", letterList);

            userPath = "/ipra/ishcorresp";
            url = "/WEB-INF/pages" + userPath + ".jsp";

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
