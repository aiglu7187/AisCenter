/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.SprMse;
import Entities.Users;
import Sessions.LoginLogFacade;
import Sessions.SprMseFacade;
import java.io.IOException;
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
// сервлет для работы со справочником МСЭ
@WebServlet(name = "SprMseServlet",
        loadOnStartup = 1,
        urlPatterns = "/sprmse")
public class SprMseServlet extends HttpServlet {

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
    SprMseFacade sprMseFacade = new SprMseFacade();

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
        Boolean itsOk = false;
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

        String action = request.getParameter("action");
        if (action.equals("sprlist")) {
            try {
                session.removeAttribute("sprmse");
            } catch (Exception ex) {
            }
            List<SprMse> sprMseList = sprMseFacade.findAllSprMse();
            session.setAttribute("sprmse", sprMseList);

            String userPath = "/ipra/sprmse";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("add")) {
            String userPath = "/ipra/sprmseadd";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("view")) {
            try {
                session.removeAttribute("sprMse");
            } catch (Exception ex) {
            }

            String sprMseIdS = request.getParameter("id");
            Integer sprMseId = 0;
            try {
                sprMseId = Integer.parseInt(sprMseIdS);
            } catch (Exception ex) {
            }
            SprMse sprMse = null;
            try {
                sprMse = sprMseFacade.findById(sprMseId);
            } catch (Exception ex) {
            }

            if (sprMse != null) {
                session.setAttribute("sprMse", sprMse);
                itsOk = true;
            } else {
                itsOk = false;
            }

            if (itsOk) {
                String userPath = "/ipra/sprmseview";
                String url = "/WEB-INF/pages" + userPath + ".jsp";
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (action.equals("simplelist")) {
            StringBuilder sb = new StringBuilder();
            List<SprMse> sprMseList = sprMseFacade.findAllSprMse();
            if (!sprMseList.isEmpty()) {
                sb.append("<mselist>");
                for (SprMse sprMse : sprMseList) {
                    sb.append("<mse>");
                    sb.append("<id>").append(sprMse.getSprmseId()).append("</id>");
                    sb.append("<name>").append(sprMse.getSprmseShname()).append("</name>");
                    sb.append("</mse>");
                }
                sb.append("</mselist>");
            }
            if (sb.length() > 0) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
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

        request.setCharacterEncoding("UTF-8");
        //<url-pattern>/sprmse</url-pattern>
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);
        Map<String, String[]> params = request.getParameterMap();
        /*for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String par = entry.getKey();
            String[] val = entry.getValue();
        }*/

        String action = params.get("action")[0];
        if (action.equals("save")) {
            String msename = null;
            String mseshname = null;
            String msechief = null;
            String mseadr = null;
            String msenamerod = null;
            String msenametv = null;
            try {
                msename = params.get("msename")[0];
            } catch (Exception ex) {
            }
            try {
                mseshname = params.get("mseshname")[0];
            } catch (Exception ex) {
            }
            try {
                msechief = params.get("msechief")[0];
            } catch (Exception ex) {
            }
            try {
                mseadr = params.get("mseadr")[0];
            } catch (Exception ex) {
            }
            try {
                msenamerod = params.get("msenamerod")[0];
            } catch (Exception ex) {
            }
            try {
                msenametv = params.get("msenametv")[0];
            } catch (Exception ex) {
            }
            StringBuilder sb = new StringBuilder();
            // вставка в бд или сохранение изменений
            String mseidS = null;
            try {
                mseidS = params.get("mseid")[0];
            } catch (Exception ex) {
            }
            SprMse mse = null;

            if (mseidS == null) {
                mse = new SprMse();
            } else {
                try {
                    mse = sprMseFacade.findById(Integer.parseInt(mseidS));
                } catch (Exception ex) {
                }
            }
            if (mse != null) {
                mse.setDateUpd(curDate);
                mse.setUserId(user);
                mse.setSprmseName(msename);
                mse.setSprmseShname(mseshname);
                mse.setSprmseChief(msechief);
                mse.setSprmseAdr(mseadr);
                mse.setSprmseNameRod(msenamerod);
                mse.setSprmseNameTv(msenametv);
                try {
                    if (mseidS == null) {
                        sprMseFacade.create(mse);
                    } else {
                        sprMseFacade.edit(mse);
                    }
                    sb.append("<result>").append(mse.getSprmseId()).append("</result>");
                } catch (Exception ex) {
                    sb.append("<result>").append("0").append("</result>");
                }
            }

            if (sb.length() > 0) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
