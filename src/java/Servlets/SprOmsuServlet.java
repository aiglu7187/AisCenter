/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.SprOmsu;
import Entities.SprRegion;
import Entities.Users;
import Sessions.LoginLogFacade;
import Sessions.SprOmsuFacade;
import Sessions.SprRegionFacade;
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
@WebServlet(name = "SprOmsuServlet",
        loadOnStartup = 1,
        urlPatterns = "/spromsu")
public class SprOmsuServlet extends HttpServlet {

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
    SprOmsuFacade sprOmsuFacade = new SprOmsuFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();

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
                session.removeAttribute("spromsu");
            } catch (Exception ex) {
            }
            List<SprOmsu> sprOmsuList = sprOmsuFacade.findAllSprOmsu();
            session.setAttribute("spromsu", sprOmsuList);

            String userPath = "/ipra/spromsu";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("add")) {
            List<SprRegion> regList = sprRegionFacade.findNoCenter();
            try {
                session.removeAttribute("regList");
            } catch (Exception ex) {
            }
            session.setAttribute("regList", regList);

            String userPath = "/ipra/spromsuadd";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("view")) {
            List<SprRegion> regList = sprRegionFacade.findNoCenter();
            try {
                session.removeAttribute("regList");
            } catch (Exception ex) {
            }
            session.setAttribute("regList", regList);
            
            try {
                session.removeAttribute("sprOmsu");
            } catch (Exception ex) {
            }

            String sprOmsuIdS = request.getParameter("id");
            Integer sprOmsuId = 0;
            try {
                sprOmsuId = Integer.parseInt(sprOmsuIdS);
            } catch (Exception ex) {
            }
            SprOmsu sprOmsu = null;
            try {
                sprOmsu = sprOmsuFacade.findById(sprOmsuId);
            } catch (Exception ex) {
            }
            
            if (sprOmsu != null) {
                session.setAttribute("sprOmsu", sprOmsu);
                itsOk = true;
            } else {
                itsOk = false;
            }

            if (itsOk) {
                String userPath = "/ipra/spromsuview";
                String url = "/WEB-INF/pages" + userPath + ".jsp";
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
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
        //<url-pattern>/spromsu</url-pattern>
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);
        Map<String, String[]> params = request.getParameterMap();
        /*for (Map.Entry<String, String[]> entry : params.entrySet()) {
            String par = entry.getKey();
            String[] val = entry.getValue();
        }*/

        String action = params.get("action")[0];
        if (action.equals("save")) {
            String omsuname = null;
            String omsuchieffam = null;
            String omsuchiefname = null;
            String omsuchiefpatr = null;
            String omsuchiefdolgn = null;
            String omsuchiefdolgndat = null;
            String omsuadr = null;
            String omsureg = null;
            String omsunamedat = null;
            String omsunamerod = null;

            try {
                omsuname = params.get("omsuname")[0];
            } catch (Exception ex) {
            }
            try {
                omsuchieffam = params.get("omsuchieffam")[0];
            } catch (Exception ex) {
            }
            try {
                omsuchiefname = params.get("omsuchiefname")[0];
            } catch (Exception ex) {
            }
            try {
                omsuchiefpatr = params.get("omsuchiefpatr")[0];
            } catch (Exception ex) {
            }
            try {
                omsuchiefdolgn = params.get("omsuchiefdolgn")[0];
            } catch (Exception ex) {
            }
            try {
                omsuchiefdolgndat = params.get("omsuchiefdolgndat")[0];
            } catch (Exception ex) {
            }
            try {
                omsuadr = params.get("omsuadr")[0];
            } catch (Exception ex) {
            }
            try {
                omsureg = params.get("regId")[0];
            } catch (Exception ex) {
            }
            try {
                omsunamedat = params.get("omsunamedat")[0];
            } catch (Exception ex) {
            }
            try {
                omsunamerod = params.get("omsunamerod")[0];
            } catch (Exception ex) {
            }
            StringBuilder sb = new StringBuilder();
            // вставка в бд или сохранение изменений
            String omsuidS = null;
            try {
                omsuidS = params.get("omsuid")[0];
            } catch (Exception ex) {
            }
            SprOmsu omsu = null;
            if (omsuidS == null) {
                omsu = new SprOmsu();
            } else {
                try {
                    omsu = sprOmsuFacade.findById(Integer.parseInt(omsuidS));
                } catch (Exception ex) {
                }
            }
            if (omsu != null) {
                omsu.setDateUpd(curDate);
                omsu.setUserId(user);
                omsu.setSpromsuName(omsuname);
                omsu.setSpromsuChiefFam(omsuchieffam);
                omsu.setSpromsuChiefName(omsuchiefname);
                omsu.setSpromsuChiefPatr(omsuchiefpatr);
                omsu.setSpromsuChiefDolgn(omsuchiefdolgn);
                omsu.setSpromsuChiefDolgnDat(omsuchiefdolgndat);
                omsu.setSpromsuAdr(omsuadr);
                omsu.setSpromsuNameDat(omsunamedat);
                omsu.setSpromsuNameRod(omsunamerod);
                Integer regId = 0;
                try {
                    regId = Integer.parseInt(omsureg);
                } catch (Exception ex) {
                }
                if (regId != 0) {
                    SprRegion region = null;
                    try {
                        region = sprRegionFacade.findById(regId);
                    } catch (Exception ex) {
                    }
                    if (region != null) {
                        omsu.setSprregId(region);
                    }
                }

                try {
                    if (omsuidS == null) {
                        sprOmsuFacade.create(omsu);
                    } else {
                        sprOmsuFacade.edit(omsu);
                    }
                    sb.append("<result>").append(omsu.getSpromsuId()).append("</result>");
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
