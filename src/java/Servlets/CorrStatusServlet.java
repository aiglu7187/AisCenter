/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.ChildStatus;
import Entities.Children;
import Entities.Ipra;
import Entities.Priyom;
import Entities.PriyomClient;
import Entities.PriyomSubject;
import Entities.Users;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenFacade;
import Sessions.IpraFacade;
import Sessions.PriyomClientFacade;
import Sessions.PriyomSubjectFacade;
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
@WebServlet(name = "CorrStatusServlet",
        loadOnStartup = 1,
        urlPatterns = "/corrstatus")

public class CorrStatusServlet extends HttpServlet {

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
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    PriyomClientFacade priyomClientFacade = new PriyomClientFacade();
    @EJB
    IpraFacade ipraFacade = new IpraFacade();
    @EJB
    PriyomSubjectFacade priyomSubjectFacade = new PriyomSubjectFacade();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        if (!user.getUserId().equals(55)) {
            return;
        }

        String action = request.getParameter("action");

        List<Children> children = childrenFacade.findAll();
        if (action.equals("corr")) {
            for (Children child : children) {
                List<ChildStatus> childStatusList = childStatusFacade.findByChild(child);
                for (ChildStatus cs : childStatusList) {
                    if ((cs.getChildstatusDateK() != null) && (!cs.getSprstatId().getSprstatInv().equals(1))) {
                        // найти для данного ребёнка приём датированный сhildstatusDateK и при этом не являющийся ПМПК
                        List<Priyom> priyomList = new ArrayList<>();
                        List<PriyomClient> priyomClientList = priyomClientFacade.findByClient(child.getChildId(), "children");
                        for (PriyomClient pc : priyomClientList) {
                            priyomList.add(pc.getPriyomId());
                        }
                        List<PriyomSubject> priyomSubjectList = priyomSubjectFacade.findBySubject(child);
                        for (PriyomSubject ps : priyomSubjectList) {
                            priyomList.add(ps.getPriyomId());
                        }

                        Boolean flag = false;
                        for (Priyom p : priyomList) {
                            if ((p.getPriyomDate().equals(cs.getChildstatusDateK()))
                                    && (!p.getSpruslId().getSpruslPmpk().equals(1))) {
                                flag = true;
                            }
                        }
                        // если найден - обнулить сhildstatusDateK
                        if (flag) {
                            cs.setChildstatusDateK(null);
                        }
                        childStatusFacade.edit(cs);
                    } else if ((cs.getChildstatusDateK() != null) && (cs.getSprstatId().getSprstatInv().equals(1))) {
                        // для инвалидов выставляем ChildstatusDateK в соответствии с ИПРА
                        List<Ipra> ipraList = ipraFacade.findByChild(child);
                        Date ipraDateK = null;
                        for (Ipra ipra : ipraList) {
                            if ((ipraDateK == null) || (ipraDateK.before(ipra.getIpraDateok()))) {
                                ipraDateK = ipra.getIpraDateok();
                            }
                        }
                        if ((ipraDateK != null) && (!cs.getChildstatusDateK().equals(ipraDateK))) {
                            cs.setChildstatusDateK(ipraDateK);
                        }
                        childStatusFacade.edit(cs);
                    }
                }
            }
        } else if (action.equals("datek")) {
            for (Children child : children) {
                List<ChildStatus> childStatusList = childStatusFacade.findByChild(child);
                for (ChildStatus cs : childStatusList) {
                    if ((cs.getChildstatusDateK() != null) && (!cs.getSprstatId().getSprstatInv().equals(1))) {
                        // найти для данного ребёнка приём датированный сhildstatusDateK
                        List<Priyom> priyomList = new ArrayList<>();
                        List<PriyomClient> priyomClientList = priyomClientFacade.findByClient(child.getChildId(), "children");
                        for (PriyomClient pc : priyomClientList) {
                            priyomList.add(pc.getPriyomId());
                        }
                        List<PriyomSubject> priyomSubjectList = priyomSubjectFacade.findBySubject(child);
                        for (PriyomSubject ps : priyomSubjectList) {
                            priyomList.add(ps.getPriyomId());
                        }
                        Boolean flag = false;
                        for (Priyom p : priyomList) {
                            if (p.getPriyomDate().equals(cs.getChildstatusDateK())) {
                                flag = true;
                            }
                        }
                        if (flag) {
                            Date date = new Date(cs.getChildstatusDateK().getTime() - 1000 * 60 * 60 * 24);
                            cs.setChildstatusDateK(date);
                        }
                        childStatusFacade.edit(cs);
                    }
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
