/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginLog;
import Entities.MonitRekomend;
import Entities.MonitoringData;
import Entities.PmpkRek;
import Entities.Users;
import Sessions.LoginLogFacade;
import Sessions.MonitRekomendFacade;
import Sessions.MonitoringDataFacade;
import Sessions.MonitoringFacade;
import Sessions.ObrObrFacade;
import Sessions.PmpkRekFacade;
import java.io.IOException;
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
@WebServlet(name = "MonitViewActionServlet",
        loadOnStartup = 1,
        urlPatterns = "/monitviewaction")

public class MonitViewActionServlet extends HttpServlet {

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
    MonitoringFacade monitoringFacade = new MonitoringFacade();
    @EJB
    MonitoringDataFacade monitoringDataFacade = new MonitoringDataFacade();
    @EJB
    MonitRekomendFacade monitRekomendFacade = new MonitRekomendFacade();
    @EJB
    PmpkRekFacade pmpkRekFacade = new PmpkRekFacade();
    @EJB
    ObrObrFacade obrObrFacade = new ObrObrFacade();
    
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String idS = request.getParameter("id");
        Integer id = 0;
        try {
            id = Integer.parseInt(idS);
        } catch (Exception ex) {
        }
        MonitoringData mon = null;
        if (id != 0) {
            try{
                mon = monitoringDataFacade.findById(id);
            }
            catch(Exception ex){}
        }
        StringBuffer sb = new StringBuffer();
        boolean itsOk = Boolean.FALSE;
        if (mon != null){
            String res = " ";
            try{
                res = mon.getMonitoringResult();
            }
            catch(Exception ex){}
            
            String form = " ";
            try{
                form = mon.getSpreduformId().getSpreduformName();
            }
            catch(Exception ex){}
            
            String org = " ";
            try {
                org = mon.getChildeduplaceId().getSprooId().getSprooName();
            }       
            catch(Exception ex){}
            String stage = " ";
            try{
                stage = mon.getChildeduplaceId().getSprstageId().getSprstageName();
            }
            catch(Exception ex){}
            String obr = " ";
            try{
                obr = mon.getSprobrId().getSprobrShname();
            }
            catch(Exception ex){}
            String obrvar = " ";
            try{
                obrvar = mon.getSprobrvarId().getSprobrvarName();
            }
            catch(Exception ex){}
            
            List<MonitRekomend> rekList = monitRekomendFacade.findByMonitData(mon);
            
            String pmpkObr = " ";
            try{
                pmpkObr = mon.getPmpkId().getSprobrId().getSprobrShname();
            }
            catch(Exception ex){}
            String pmpkObrvar = " ";
            try{
                pmpkObrvar = mon.getPmpkId().getSprobrvarId().getSprobrvarName();
            }
            catch(Exception ex){}
            List<PmpkRek> pmpkRekList = pmpkRekFacade.findByPmpk(mon.getPmpkId());
            
            sb.append("<mdata>");
            sb.append("<result>").append(res).append("</result>");
            sb.append("<form>").append(form).append("</form>");
            sb.append("<org>").append(org).append("</org>");
            sb.append("<stage>").append(stage).append("</stage>");
            sb.append("<obr>").append(obr).append("</obr>");
            sb.append("<obrvar>").append(obrvar).append("</obrvar>");
            sb.append("<reks>");
            for (MonitRekomend rek : rekList) {
                String r = rek.getSprrekId().getSprrekName();
                sb.append("<rek>").append(r).append("</rek>");
            }
            sb.append("</reks>");
            sb.append("<pmpkobr>").append(pmpkObr).append("</pmpkobr>");
            sb.append("<pmpkobrvar>").append(pmpkObrvar).append("</pmpkobrvar>");
            sb.append("<pmpkreks>");            
            for (PmpkRek pmpkRek : pmpkRekList) {
                String r = pmpkRek.getSprrekId().getSprrekName();
                sb.append("<pmpkrek>").append(r).append("</pmpkrek>");
            }
            sb.append("</pmpkreks>");            
            sb.append("</mdata>");
            
            itsOk = Boolean.TRUE;
        }
        if (itsOk){
            response.setContentType("text/xml; charset=windows-1251");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(sb.toString()); 
        } 
        else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
