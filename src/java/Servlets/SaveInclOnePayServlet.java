/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.LoginLog;
import Entities.Nom;
import Entities.Parents;
import Entities.PayDogovor;
import Entities.PayUsl;
import Entities.PayuslClient;
import Entities.PayuslLesson;
import Entities.Payusllespos;
import Entities.SprRegion;
import Entities.Telephon;
import Entities.Users;
import Sessions.ChildrenFacade;
import Sessions.LoginLogFacade;
import Sessions.NomFacade;
import Sessions.ParentsFacade;
import Sessions.PayDogovorFacade;
import Sessions.PayuslClientFacade;
import Sessions.PayuslLessonFacade;
import Sessions.PayusllesposFacade;
import Sessions.SprRegionFacade;
import Sessions.TelephonFacade;
import java.io.IOException;
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
@WebServlet(name = "SaveInclOnePayServlet",
        loadOnStartup = 1,
        urlPatterns = "/saveinclone")
public class SaveInclOnePayServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    PayuslClientFacade payuslClientFacade = new PayuslClientFacade();
    @EJB
    ParentsFacade parentsFacade = new ParentsFacade();
    @EJB
    TelephonFacade telephonFacade = new TelephonFacade();
    @EJB
    NomFacade nomFacade = new NomFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    PayDogovorFacade payDogovorFacade = new PayDogovorFacade();
    @EJB
    PayuslLessonFacade payuslLessonFacade = new PayuslLessonFacade();
    @EJB
    PayusllesposFacade payusllesposFacade = new PayusllesposFacade();
    @EJB
    SprRegionFacade sprRegionFacade1 = new SprRegionFacade();

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

        String url = "";

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");

        PayUsl usl = (PayUsl) session.getAttribute("payUsl");

        String clIdS = request.getParameter("clId");
        Children child = null;
        if (!clIdS.equals("")) {
            Integer clId = Integer.parseInt(clIdS);
            try {
                child = childrenFacade.findById(clId);
            } catch (Exception ex) {
            }
        } else {
            String fam = request.getParameter("clFam");
            String name = request.getParameter("clNam");
            String patr = request.getParameter("clPatr");
            String drS = request.getParameter("clDr");
            String regS = request.getParameter("regCl");
            Date dr = null;
            try {
                dr = format.parse(drS);
            } catch (Exception ex) {
            }
            child = new Children();
            child.setChildFam(fam);
            child.setChildName(name);
            child.setChildPatr(patr);
            if (dr != null) {
                child.setChildDr(dr);
            }
            Integer reg = 0;
            try {
                reg = Integer.parseInt(regS);
            } catch (Exception ex) {
            }
            SprRegion sprReg = null;
            if (reg != 0) {
                try {
                    sprReg = sprRegionFacade.findById(reg);
                } catch (Exception ex) {
                }
            }
            if (sprReg != null) {
                child.setSprregId(sprReg);
            }
            child.setDateUpd(curDate);
            child.setUserId(user);
            childrenFacade.create(child);
        }
        String dateS = request.getParameter("inclDate");
        Date date = null;
        try {
            date = format.parse(dateS);
        } catch (Exception ex) {
        }

        PayuslClient pc = new PayuslClient();
        if (child != null) {
            pc.setClientId(child.getChildId());
            pc.setPayuslId(usl);
            pc.setUserId(user);
            pc.setDateUpd(curDate);
            if (date != null) {
                pc.setPayuslDaten(date);
            }
            pc.setPayuslclientKatcl("children");
            payuslClientFacade.create(pc);

            String dogClIdS = request.getParameter("dogClId");
            String dogN = request.getParameter("dogN");
            String dogDS = request.getParameter("dogD");
            String telephon = request.getParameter("telephon");
            if (dogDS != null) {
                Parents parent = null;
                if (dogClIdS != null) {
                    try {
                        parent = parentsFacade.findById(Integer.parseInt(dogClIdS));
                    } catch (Exception ex) {
                    }
                    if (parent != null) {
                        List<Telephon> tel = telephonFacade.findByParent(parent);
                        if (!telephon.equals("")) {
                            if (!tel.isEmpty()) {
                                tel.get(0).setTel(telephon);
                                telephonFacade.edit(tel.get(0));
                            } else {
                                Telephon t = new Telephon();
                                t.setParentId(parent);
                                t.setTel(telephon);
                                telephonFacade.create(t);
                            }
                        }
                    }
                } else {
                    parent = new Parents();
                    String dogClFam = request.getParameter("dogClFam");
                    String dogClName = request.getParameter("dogClNam");
                    String dogClPatr = request.getParameter("dogClPatr");
                    String regDog = request.getParameter("regDog");
                    if (dogClFam != null) {
                        Nom nom = new Nom();
                        nomFacade.create(nom);
                        parent = new Parents();
                        parent.setParentNom(nom.getNom());
                        parent.setParentFam(dogClFam);
                        parent.setParentName(dogClName);
                        parent.setParentPatr(dogClPatr);
                        parent.setDateUpd(curDate);
                        parent.setSprregId(sprRegionFacade.findById(Integer.parseInt(regDog)));
                        parent.setUserId(user);
                        parentsFacade.create(parent);
                        if (telephon != null) {
                            Telephon tel = new Telephon();
                            tel.setParentId(parent);
                            tel.setTel(telephon);
                            telephonFacade.create(tel);
                        }
                    }
                }

                if (parent != null) {
                    PayDogovor pd = new PayDogovor();
                    pd.setChildId(child);
                    pd.setParentId(parent);
                    pd.setUserId(user);
                    pd.setDateUpd(curDate);
                    Date dogD = null;
                    try {
                        dogD = format.parse(dogDS);
                    } catch (Exception ex) {
                    }
                    if (dogD != null) {
                        pd.setPaydogD(dogD);
                    }
                    pd.setPaydogN(dogN);
                    pd.setPayuslId(usl);
                    payDogovorFacade.create(pd);
                }
            }
            List<PayuslLesson> pul = payuslLessonFacade.findByPayUsl(usl);
            for (PayuslLesson pl : pul) {
                if (pl.getPayusllessonDate().getTime() > pc.getPayuslDaten().getTime()) {
                    Payusllespos pos = new Payusllespos();
                    pos.setPayuslclientId(pc);
                    pos.setDateUpd(curDate);
                    pos.setPayusllessonId(pl);
                    pos.setUserId(user);
                    payusllesposFacade.create(pos);
                }
            }
        }
        String userPath = "/pup/goodsave";
        url = "/WEB-INF/pages" + userPath + ".jsp";

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
