/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.ChildStat;
import Entities.ChildStatus;
import Entities.Children;
import Entities.Ipra;
import Entities.SprStat;
import Entities.Users;
import Sessions.ChildStatFacade;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenFacade;
import Sessions.IpraFacade;
import Sessions.SprStatFacade;
import java.io.IOException;
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
@WebServlet(name = "MoveStatusServlet",
        loadOnStartup = 1,
        urlPatterns = "/movestatus")

public class MoveStatusServlet extends HttpServlet {

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
    ChildStatFacade childStatFacade = new ChildStatFacade();
    @EJB
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    IpraFacade ipraFacade = new IpraFacade();
    @EJB
    SprStatFacade sprStatFacade = new SprStatFacade();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        if (!user.getUserId().equals(55)){
            return;
        }
        
        List<Children> children = childrenFacade.findAll();
        for (Children child : children) {
            List<ChildStat> childStats = childStatFacade.findByChild(child);
            Set<SprStat> childStatSet = new HashSet<>();
            for (ChildStat cs : childStats) {
                if (cs.getPriyomId().getSpruslId().getSpruslPmpk() != 1) {
                    if (cs.getSprstatId().getSprstatV() != 3) {
                        childStatSet.add(cs.getSprstatId());
                    }
                } else {
                    childStatSet.add(cs.getSprstatId());
                }
            }
            for (SprStat csSet : childStatSet) {
                ChildStatus childStatus = new ChildStatus();
                childStatus.setChildId(child);
                childStatus.setSprstatId(csSet);
                Date dateN = null;
                for (ChildStat cs : childStats) {
                    if (cs.getSprstatId().equals(csSet)) {
                        if (dateN == null) {
                            dateN = cs.getPriyomId().getPriyomDate();
                        } else if (cs.getPriyomId().getPriyomDate().before(dateN)) {
                            dateN = cs.getPriyomId().getPriyomDate();
                        }
                    }
                }
                if (dateN != null) {
                    childStatus.setChildstatusDateN(dateN);
                }
                childStatusFacade.create(childStatus);
            }
            List<ChildStatus> childStatusList = childStatusFacade.findByChild(child);
            List<Ipra> ipraList = ipraFacade.findByChild(child);
            if (!ipraList.isEmpty()) {                
                Date ipraN = null;
                Date ipraK = null;
                for (Ipra ipra : ipraList) {
                    if (ipraN == null) {
                        ipraN = ipra.getIpraDateexp();
                    } else{
                        if (ipra.getIpraDateexp().before(ipraN)){
                            ipraN = ipra.getIpraDateexp();
                        }
                    }
                    if (ipraK == null){
                        ipraK = ipra.getIpraDateok();
                    } else{
                        if (ipra.getIpraDateok().after(ipraK)){
                            ipraK = ipra.getIpraDateok();
                        }
                    }
                }
                Boolean isDI = false;
                Boolean isTR = false;
                for (ChildStatus cs : childStatusList) {
                    if (cs.getSprstatId().getSprstatInv() == 1) {
                        isDI = true;
                        cs.setChildstatusDateK(ipraK);
                        childStatusFacade.edit(cs);
                    } else if (cs.getSprstatId().getSprstatName().equals("ТР")) {
                        isTR = true;
                    }
                }
                if (!isDI) {
                    ChildStatus childStatus = new ChildStatus();
                    childStatus.setChildId(child);
                    childStatus.setSprstatId(sprStatFacade.findById(6));
                    if (ipraN != null){
                        childStatus.setChildstatusDateN(ipraN);
                    }
                    if (ipraK != null){
                        childStatus.setChildstatusDateK(ipraK);
                    }
                    childStatusFacade.create(childStatus);
                }
                if (!isTR) {
                    ChildStatus childStatus = new ChildStatus();
                    childStatus.setChildId(child);
                    childStatus.setSprstatId(sprStatFacade.findById(2));
                    if (ipraN != null){
                        childStatus.setChildstatusDateN(ipraN);
                    }                    
                    childStatusFacade.create(childStatus);
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
