/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.ChildrenOldfio;
import Entities.LoginLog;
import Entities.Parents;
import Entities.ParentsOldfio;
import Entities.Ped;
import Entities.PedOldfio;
import Entities.SprOo;
import Entities.SprRegion;
import Entities.SprStage;
import Entities.Users;
import Sessions.ChildrenFacade;
import Sessions.ChildrenOldfioFacade;
import Sessions.LoginLogFacade;
import Sessions.ParentsFacade;
import Sessions.ParentsOldfioFacade;
import Sessions.PedFacade;
import Sessions.PedOldfioFacade;
import Sessions.SprOoFacade;
import Sessions.SprRegionFacade;
import Sessions.SprStageFacade;
import java.io.IOException;
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
@WebServlet(name = "ClientActionServlet",
        loadOnStartup = 1,
        urlPatterns = "/clientaction")

public class ClientActionServlet extends HttpServlet {

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
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    SprStageFacade sprStageFacade = new SprStageFacade();
    @EJB
    SprOoFacade sprOoFacade = new SprOoFacade();
    @EJB
    ParentsFacade parentsFacade = new ParentsFacade();
    @EJB
    PedFacade pedFacade = new PedFacade();
    @EJB
    ChildrenOldfioFacade childrenOldfioFacade = new ChildrenOldfioFacade();
    @EJB
    ParentsOldfioFacade parentsOldfioFacade = new ParentsOldfioFacade();
    @EJB
    PedOldfioFacade pedOldfioFacade = new PedOldfioFacade();

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

        String action = request.getParameter("action");
        StringBuffer sb = new StringBuffer();
        boolean itsOk = false;

        if (action.equals("ou")) {
            String idS = request.getParameter("client");
            Integer id = 0;
            try {
                id = Integer.parseInt(idS);
            } catch (Exception ex) {
            }
            if (id != 0) {
                Children child = null;
                try {
                    child = childrenFacade.findById(id);
                } catch (Exception ex) {
                }
                if (child != null) {
                    SprRegion childReg = child.getSprregId();
                    List<SprRegion> regions = sprRegionFacade.findNoCenter();
                    sb.append("<edu>");
                    sb.append("<regions>");
                    for (SprRegion region : regions) {
                        sb.append("<region>");
                        sb.append("<id>").append(region.getSprregId()).append("</id>");
                        sb.append("<name>").append(region.getSprregName()).append("</name>");
                        if (childReg.equals(region)) {
                            sb.append("<is>").append("1").append("</is>");
                        } else {
                            sb.append("<is>").append("0").append("</is>");
                        }
                        sb.append("</region>");
                    }
                    sb.append("</regions>");

                    List<SprStage> allStages = sprStageFacade.findAllStages();
                    sb.append("<stages>");
                    for (SprStage st : allStages) {
                        sb.append("<stage>");
                        sb.append("<id>").append(st.getSprstageId()).append("</id>");
                        sb.append("<name>").append(st.getSprstageName()).append("</name>");
                        sb.append("</stage>");
                    }
                    sb.append("</stages>");
                    sb.append("</edu>");
                }
                itsOk = true;
            }
        } else if (action.equals("selreg")) {
            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            if (regIdS != null) {
                regId = Integer.parseInt(regIdS);
            }
            SprRegion reg = null;
            if (regId != 0) {
                reg = sprRegionFacade.findById(regId);
            }
            List<SprOo> regOo = new ArrayList<>();
            if (reg != null) {
                regOo = sprOoFacade.findByRegion(reg);
            }
            if (regOo.size() > 0) {
                sb.append("<regoo>");
                for (SprOo o : regOo) {
                    sb.append("<oo>");
                    sb.append("<id>").append(o.getSprooId()).append("</id>");
                    sb.append("<name>").append(o.getSprooName()).append("</name>");
                    sb.append("</oo>");
                }
                sb.append("</regoo>");
                itsOk = true;
            }
        } else if (action.equals("changefio")) {
            String type = request.getParameter("changetype");
            String text = request.getParameter("changetext");
            String kat = request.getParameter("kat");
            String idS = request.getParameter("id");

            Integer id = 0;
            try {
                id = Integer.parseInt(idS);
            } catch (Exception ex) {
            }
            if (id != 0) {
                if (kat.equals("children")) {
                    Children child = childrenFacade.findById(id);
                    ChildrenOldfio childrenOldfio = new ChildrenOldfio();
                    childrenOldfio.setChildId(child);
                    String oldFam = child.getChildFam();
                    String oldName = child.getChildName();
                    String oldPatr = child.getChildPatr();
                    childrenOldfio.setChildoldfioFam(oldFam);
                    childrenOldfio.setChildoldfioName(oldName);
                    childrenOldfio.setChildoldfioPatr(oldPatr);
                    childrenOldfio.setDateUpd(curDate);
                    childrenOldfio.setUserId(user);
                    childrenOldfioFacade.create(childrenOldfio);
                    if (type.equals("fam")) {
                        child.setChildFam(text);
                    } else if (type.equals("name")) {
                        child.setChildName(text);
                    } else if (type.equals("patr")) {
                        child.setChildPatr(text);
                    }
                    childrenFacade.edit(child);
                } else if (kat.equals("parents")) {
                    Parents parent = parentsFacade.findById(id);
                    ParentsOldfio parentsOldfio = new ParentsOldfio();
                    parentsOldfio.setParentId(parent);
                    String oldFam = parent.getParentFam();
                    String oldName = parent.getParentName();
                    String oldPatr = parent.getParentPatr();
                    parentsOldfio.setParentoldfioFam(oldFam);
                    parentsOldfio.setParentoldfioName(oldName);
                    parentsOldfio.setParentoldfioPatr(oldPatr);
                    parentsOldfio.setDateUpd(curDate);
                    parentsOldfio.setUserId(user);
                    parentsOldfioFacade.create(parentsOldfio);
                    if (type.equals("fam")) {
                        parent.setParentFam(text);
                    } else if (type.equals("name")) {
                        parent.setParentName(text);
                    } else if (type.equals("patr")) {
                        parent.setParentPatr(text);
                    }
                    parentsFacade.edit(parent);
                } else if (kat.equals("ped")) {
                    Ped ped = pedFacade.findById(id);
                    PedOldfio pedOldfio = new PedOldfio();
                    pedOldfio.setPedId(ped);
                    String oldFam = ped.getPedFam();
                    String oldName = ped.getPedName();
                    String oldPatr = ped.getPedPatr();
                    pedOldfio.setPedoldfioFam(oldFam);
                    pedOldfio.setPedoldfioName(oldName);
                    pedOldfio.setPedoldfioPatr(oldPatr);
                    pedOldfio.setDateUpd(curDate);
                    pedOldfio.setUserId(user);
                    pedOldfioFacade.create(pedOldfio);
                    if (type.equals("fam")) {
                        ped.setPedFam(text);
                    } else if (type.equals("name")) {
                        ped.setPedName(text);
                    } else if (type.equals("patr")) {
                        ped.setPedPatr(text);
                    }
                    pedFacade.edit(ped);
                }
                sb.append("<oldFamOk>").append("1").append("</oldFamOk>");
                itsOk = true;
            }
        }

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
