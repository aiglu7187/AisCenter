/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.Sotrud;
import Entities.SotrudDolgn;
import Entities.SprDolgn;
import Entities.Users;
import Sessions.LoginLogFacade;
import Sessions.SotrudDolgnFacade;
import Sessions.SotrudFacade;
import Sessions.SprDolgnFacade;
import Sessions.UsersFacade;
import java.io.IOException;
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
@WebServlet(name = "SaveSotrudServlet",
        loadOnStartup = 1,
        urlPatterns = "/savesotrud")

public class SaveSotrudServlet extends HttpServlet {

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
    SotrudFacade sotrudFacade = new SotrudFacade();
    @EJB
    SprDolgnFacade sprDolgnFacade = new SprDolgnFacade();
    @EJB
    UsersFacade usersFacade = new UsersFacade();
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
        List<String> dolgnList = new ArrayList<>();
        Map<String, String[]> param = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : param.entrySet()) {
            String par = entry.getKey();
            String[] val = entry.getValue();
            if (par.startsWith("dolgn")) {
                dolgnList.add(val[0]);
            }
        }
        String idS = request.getParameter("sotrudId");
        String fam = request.getParameter("fam");
        String nam = request.getParameter("nam");
        String patr = request.getParameter("patr");
        String actS = request.getParameter("act");

        Integer id = 0;
        try {
            id = Integer.parseInt(idS);
        } catch (Exception ex) {
        }

        Sotrud sotrud = null;
        if (id != 0) {
            sotrud = sotrudFacade.findById(id);
        }

        if (sotrud != null) {
            sotrud.setSotrudFam(fam);
            sotrud.setSotrudName(nam);
            sotrud.setSotrudPatr(patr);
            List<SotrudDolgn> sdList = sotrudDolgnFacade.findBySotrud(sotrud);

            for (String dolgnIdS : dolgnList) {
                Integer dolgnId = 0;
                try {
                    dolgnId = Integer.parseInt(dolgnIdS);
                } catch (Exception ex) {
                }

                SprDolgn dolgn = null;
                if (dolgnId != 0) {
                    dolgn = sprDolgnFacade.findById(dolgnId);
                }
                boolean flag = false;
                for (SotrudDolgn sd : sdList) {
                    if (sd.getSprdolgnId().equals(dolgn)) {
                        sd.setSotruddolgnActive(1);
                        sotrudDolgnFacade.edit(sd);
                        flag = true;
                    }
                }
                if (!flag) {
                    SotrudDolgn sotrudDolgn = new SotrudDolgn();
                    sotrudDolgn.setSotrudId(sotrud);
                    sotrudDolgn.setSotruddolgnActive(1);
                    sotrudDolgn.setSprdolgnId(dolgn);
                    sotrudDolgnFacade.create(sotrudDolgn);
                }
            }

            sdList = sotrudDolgnFacade.findBySotrud(sotrud);
            for (SotrudDolgn sd : sdList) {
                boolean flag = false;
                for (String dolgnIdS : dolgnList) {
                    Integer dolgnId = 0;
                    try {
                        dolgnId = Integer.parseInt(dolgnIdS);
                    } catch (Exception ex) {
                    }
                    if (sd.getSprdolgnId().getSprdolgnId().equals(dolgnId)){
                        flag = true;
                    }
                }
                if (!flag){
                    sd.setSotruddolgnActive(0);
                    sotrudDolgnFacade.edit(sd);
                }
            }

            Integer act = -1;
            try {
                act = Integer.parseInt(actS);
            } catch (Exception ex) {
            }

            if (act != -1) {
                sotrud.setSotrudActive(act);
            }
            sotrudFacade.edit(sotrud);

            if (actS.equals("0")) {
                List<Users> users = usersFacade.findBySotrud(sotrud);
                if (users.size() > 0) {
                    for (Users u : users) {
                        u.setUserActive(0);
                        usersFacade.edit(u);
                    }
                }
            }
        } else {
            sotrud = new Sotrud();
            sotrud.setSotrudFam(fam);
            sotrud.setSotrudName(nam);
            sotrud.setSotrudPatr(patr);
            sotrud.setSotrudActive(1);
            sotrudFacade.create(sotrud);
            
            for (String dolgnIdS : dolgnList) {
                Integer dolgnId = 0;
                try {
                    dolgnId = Integer.parseInt(dolgnIdS);
                } catch (Exception ex) {
                }

                SprDolgn dolgn = null;
                if (dolgnId != 0) {
                    dolgn = sprDolgnFacade.findById(dolgnId);
                }

                if (dolgn != null) {
                    SotrudDolgn sotrudDolgn = new SotrudDolgn();
                    sotrudDolgn.setSotrudId(sotrud);
                    sotrudDolgn.setSotruddolgnActive(1);
                    sotrudDolgn.setSprdolgnId(dolgn);
                    sotrudDolgnFacade.create(sotrudDolgn);
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
