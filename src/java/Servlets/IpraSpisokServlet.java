/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.ChildrenView;
import Entities.Ipra;
import Entities.Kalendar;
import Entities.LoginLog;
import Entities.SprRegion;
import Entities.Users;
import Other.IpraComparator;
import Other.RegionComparator;
import Sessions.ChildrenFacade;
import Sessions.ChildrenViewFacade;
import Sessions.IpraFacade;
import Sessions.KalendarFacade;
import Sessions.LoginLogFacade;
import Sessions.SprRegionFacade;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "IpraSpisokServlet",
        loadOnStartup = 1,
        urlPatterns = "/ipraspisok")

public class IpraSpisokServlet extends HttpServlet {

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
    ChildrenViewFacade childrenViewFacade = new ChildrenViewFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    KalendarFacade kalendarFacade = new KalendarFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    IpraFacade ipraFacade = new IpraFacade();

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
        if (action.equals("spisok")) {
            userPath = "/ipra/ipraspisok";
            url = "/WEB-INF/pages" + userPath + ".jsp";

            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {

            }

            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());

            session.setAttribute("regions", regions);

            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("add")) {
            userPath = "/ipra/ipraadd";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("search")) {
            StringBuffer sb = new StringBuffer();
            boolean itsOk = false;
            String childFam = request.getParameter("fam");
            String childName = request.getParameter("name");
            String childPatr = request.getParameter("patr");
            if (childFam != null) {
                childFam = childFam.trim().toUpperCase();
            } else {
                childFam = "";
            }
            if (childName != null) {
                childName = childName.trim().toUpperCase();
            } else {
                childName = "";
            }
            if (childPatr != null) {
                childPatr = childPatr.trim().toUpperCase();
            } else {
                childPatr = "";
            }

            String fam = childFam.replace("Ё", "Е");
            String nam = childName.replace("Ё", "Е");
            String patr = childPatr.replace("Ё", "Е");
            List<ChildrenView> childrenV = null;
            childrenV = childrenViewFacade.searchChildS(fam, nam, patr);

            List<Children> children = new ArrayList();
            if (childrenV != null) {
                for (ChildrenView childV : childrenV) {
                    children.add(childrenFacade.findById(childV.getChildId()));
                }
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            sb.append("<children>");
            for (Children child : children) {
                sb.append("<child>");
                sb.append("<id>").append(child.getChildId()).append("</id>");
                sb.append("<nom>").append(child.getChildNom()).append("</nom>");
                sb.append("<fam>").append(child.getChildFam()).append("</fam>");
                sb.append("<name>").append(child.getChildName()).append("</name>");
                String patrS = child.getChildPatr();
                if ((patrS == null) || (patrS.equals(""))) {
                    patrS = " ";
                }
                sb.append("<patr>").append(patrS).append("</patr>");
                String dr = " ";
                try {
                    dr = simpleDateFormat.format(child.getChildDr());
                } catch (Exception ex) {
                }
                sb.append("<dr>").append(dr).append("</dr>");
                sb.append("<reg>").append(child.getSprregId().getSprregName()).append("</reg>");
                sb.append("</child>");
            }
            sb.append("</children>");
            itsOk = true;

            if (itsOk) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("regions")) {
            List<SprRegion> allRegions = sprRegionFacade.findNoCenter();
            Collections.sort(allRegions, new RegionComparator());
            StringBuffer sb = new StringBuffer();
            Boolean itsOk = Boolean.FALSE;
            if (!allRegions.isEmpty()) {
                sb.append("<regions>");
                for (SprRegion reg : allRegions) {
                    sb.append("<region>");
                    sb.append("<id>").append(reg.getSprregId().toString()).append("</id>");
                    sb.append("<name>").append(reg.getSprregName()).append("</name>");
                    sb.append("</region>");
                }
                sb.append("</regions>");
                itsOk = Boolean.TRUE;
            }
            if (itsOk) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("date")) {
            String periodS = request.getParameter("period");
            String type = request.getParameter("type");
            String direct = request.getParameter("direct");
            String dateS = request.getParameter("date");
            Integer period = 0;
            try {
                period = Integer.parseInt(periodS);

            } catch (Exception ex) {
            }

            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd");
            Date date = null;
            try {
                date = format.parse(dateS);
            } catch (Exception ex) {
            }
            Date resultDate = null;
            if ((period != 0) && (date != null)) {
                List<Kalendar> allDays = kalendarFacade.findAllDays();
                if (type.equals("rab")) {
                    int k = 0;
                    if (direct.equals("forward")) {
                        for (Kalendar day : allDays) {
                            if (date.equals(day.getKalendarDate())) {
                                k = 1;
                            }
                            if ((k > 0) && (k <= period)) {
                                if (day.getKalendarWeekend() == 0) {
                                    resultDate = day.getKalendarDate();
                                    k++;
                                }
                            }
                        }
                    } else if (direct.equals("back")) {
                        k = 0;
                        for (int i = allDays.size() - 1; i >= 0; i--) {
                            Kalendar day = allDays.get(i);
                            if (date.equals(day.getKalendarDate())) {
                                k = 1;
                            }
                            if ((k > 0) && (k <= period)) {
                                if (day.getKalendarWeekend() == 0) {
                                    resultDate = day.getKalendarDate();
                                    k++;
                                }
                            }
                        }
                    }

                } else if (type.equals("days")) {
                    int k = 0;
                    if (direct.equals("forward")) {
                        for (Kalendar day : allDays) {
                            if (date.equals(day.getKalendarDate())) {
                                k = 1;
                            }
                            if ((k > 0) && (k <= period)) {
                                resultDate = day.getKalendarDate();
                                k++;

                            }
                        }
                    } else if (direct.equals("back")) {
                        for (int i = allDays.size() - 1; i >= 0; i--) {
                            Kalendar day = allDays.get(i);
                            if (date.equals(day.getKalendarDate())) {
                                k = 1;
                            }
                            if ((k > 0) && (k <= period)) {
                                resultDate = day.getKalendarDate();
                                k++;
                            }
                        }
                    }
                } else if (type.equals("month")) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    if (direct.equals("forward")) {
                        if (c.get(Calendar.MONTH) + period < 11) {
                            c.set(Calendar.MONTH, c.get(Calendar.MONTH) + period);
                        } else if (c.get(Calendar.MONTH) + period >= 11) {
                            c.set(Calendar.MONTH, period - (12 - c.get(Calendar.MONTH)));
                            c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
                        }
                    } else if (direct.equals("back")) {
                        if (c.get(Calendar.MONTH) - period > 0) {
                            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - period);
                        } else if (c.get(Calendar.MONTH) - period <= 0) {
                            c.set(Calendar.MONTH, 12 + (c.get(Calendar.MONTH) - period));
                            c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);
                        }
                    }
                    resultDate = c.getTime();
                }
            }
            StringBuffer sb = new StringBuffer();
            if (resultDate != null) {
                String id = request.getParameter("id");
                String result = format.format(resultDate);
                sb.append("<ipradate>");
                sb.append("<id>").append(id).append("</id>");
                sb.append("<date>").append(result).append("</date>");
                sb.append("</ipradate>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("searchipra")) {
            String result = request.getParameter("result");
            StringBuilder sb = new StringBuilder();
            List<Ipra> resIpra = new ArrayList<>();
            if (result.equals("all")) {
                resIpra = ipraFacade.findAllCur();
            } else if (result.equals("search")) {
                String fam = request.getParameter("fam");
                String name = request.getParameter("name");
                String patr = request.getParameter("patr");
                String regS = request.getParameter("reg");
                String npr = request.getParameter("npr");
                String dpr = request.getParameter("dpr");
                String sort = request.getParameter("sort");
                Integer reg = 0;
                try {
                    reg = Integer.parseInt(regS);
                } catch (Exception ex) {
                }
                SprRegion region = null;
                if (reg != 0) {
                    region = sprRegionFacade.findById(reg);
                }
                Integer status = 1;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date datePr = null;
                try {
                    datePr = simpleDateFormat.parse(dpr);
                } catch (ParseException ex) {
                    Logger.getLogger(IpraSpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                resIpra = ipraFacade.findBySearch(fam, name, patr, region, status, datePr, npr, sort);

            }
            if (!resIpra.isEmpty()) {
                //Collections.sort(resIpra, new IpraComparator());
                sb.append("<ipras>");
                for (Ipra ipra : resIpra) {
                    sb.append("<ipra>");
                    sb.append("<id>").append(ipra.getIpraId()).append("</id>");
                    sb.append("<fam>").append(ipra.getChildId().getChildFam()).append("</fam>");
                    sb.append("<name>").append(ipra.getChildId().getChildName()).append("</name>");
                    String patr = ipra.getChildId().getChildPatr();
                    if ((patr == null) || (patr.equals(""))) {
                        patr = " ";
                    }
                    sb.append("<patr>").append(patr).append("</patr>");
                    sb.append("<dr>").append(ipra.getChildId().getFormat2Dr()).append("</dr>");
                    sb.append("<region>").append(ipra.getChildId().getSprregId().getSprregName()).append("</region>");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

                    String nomPrikaz = ipra.getIpraPrikazN();
                    if ((nomPrikaz == null) || (nomPrikaz.equals(""))) {
                        nomPrikaz = " ";
                    }
                    sb.append("<prikazn>").append(nomPrikaz).append("</prikazn>");
                    String datePrikaz = " ";
                    try {
                        datePrikaz = simpleDateFormat.format(ipra.getIpraPrikazD());
                    } catch (Exception ex) {
                    }
                    sb.append("<prikaz>").append(datePrikaz).append("</prikaz>");
                    String datePerech = " ";
                    try {
                        datePerech = simpleDateFormat.format(ipra.getIpraPerechD());
                    } catch (Exception ex) {
                    }
                    sb.append("<omsudate>").append(datePerech).append("</omsudate>");
                    String omsuN = ipra.getIpraOmsuN();
                    if (omsuN == null) {
                        omsuN = " ";
                    } else if (omsuN.equals("")) {
                        omsuN = " ";
                    }
                    sb.append("<omsun>").append(omsuN).append("</omsun>");
                    String dateOtchOmsu = " ";
                    try {
                        dateOtchOmsu = simpleDateFormat.format(ipra.getIpraOtchomsu());
                    } catch (Exception ex) {
                    }
                    sb.append("<otchomsu>").append(dateOtchOmsu).append("</otchomsu>");
                    String dateOtchCenter = " ";
                    try {
                        dateOtchCenter = simpleDateFormat.format(ipra.getIpraOtchcenter());
                    } catch (Exception ex) {
                    }
                    sb.append("<otchcenter>").append(dateOtchCenter).append("</otchcenter>");
                    String dateOtchDo = " ";
                    try {
                        dateOtchDo = simpleDateFormat.format(ipra.getIpraOtchdo());
                    } catch (Exception ex) {
                    }
                    sb.append("<otchdo>").append(dateOtchDo).append("</otchdo>");
                    sb.append("</ipra>");
                }
                sb.append("</ipras>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("open")) {
            String idS = request.getParameter("id");
            Integer id = 0;
            try {
                id = Integer.parseInt(idS);
            } catch (Exception ex) {
            }
            Ipra ipra = null;
            if (id != 0) {
                ipra = ipraFacade.findById(id);
            }
            if (ipra != null) {
                try {
                    session.removeAttribute("ipra");
                } catch (Exception ex) {
                }

                session.setAttribute("ipra", ipra);

                userPath = "/ipra/ipraview";
                url = "/WEB-INF/pages" + userPath + ".jsp";
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        } else if (action.equals("deleteipra")) {
            String idS = request.getParameter("id");
            Integer id = 0;
            try {
                id = Integer.parseInt(idS);
            } catch (Exception ex) {
            }
            if (id != 0) {
                Ipra ipra = ipraFacade.findById(id);
                if (ipra != null) {
                    ipraFacade.remove(ipra);
                }
                userPath = "/pup/gooddelete";
                url = "/WEB-INF/pages" + userPath + ".jsp";
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (action.equals("archive")) {
            userPath = "/ipra/ipraarchive";
            url = "/WEB-INF/pages" + userPath + ".jsp";

            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {

            }

            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());

            session.setAttribute("regions", regions);

            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (action.equals("upload")) {
            try {
                session.removeAttribute("reestrFiles");
            } catch (Exception ex) {
            }
            File folder = new File("reestr//");
            String[] files = folder.list(new FilenameFilter() {
                @Override
                public boolean accept(File folder, String name) {
                    return name.endsWith(".xls");
                }
            });
            session.setAttribute("reestrFiles", files);

            userPath = "/ipra/reestrupload";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("searcharchive")) {
            String result = request.getParameter("result");
            StringBuilder sb = new StringBuilder();
            List<Ipra> resIpra = new ArrayList<>();
            if (result.equals("all")) {
                resIpra = ipraFacade.findAllArchive();
            } else if (result.equals("search")) {
                String fam = request.getParameter("fam");
                String name = request.getParameter("name");
                String patr = request.getParameter("patr");
                String regS = request.getParameter("reg");
                String npr = request.getParameter("npr");
                String dpr = request.getParameter("dpr");
                String sort = request.getParameter("sort");
                Integer reg = 0;
                try {
                    reg = Integer.parseInt(regS);
                } catch (Exception ex) {
                }
                SprRegion region = null;
                if (reg != 0) {
                    region = sprRegionFacade.findById(reg);
                }
                Integer status = 0;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date datePr = null;
                try {
                    datePr = simpleDateFormat.parse(dpr);
                } catch (ParseException ex) {
                    Logger.getLogger(IpraSpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                resIpra = ipraFacade.findBySearch(fam, name, patr, region, status, datePr, npr, sort);
            }
            if (!resIpra.isEmpty()) {
                Collections.sort(resIpra, new IpraComparator());
                sb.append("<ipras>");
                for (Ipra ipra : resIpra) {
                    sb.append("<ipra>");
                    sb.append("<id>").append(ipra.getIpraId()).append("</id>");
                    sb.append("<fam>").append(ipra.getChildId().getChildFam()).append("</fam>");
                    sb.append("<name>").append(ipra.getChildId().getChildName()).append("</name>");
                    String patr = ipra.getChildId().getChildPatr();
                    if ((patr == null) || (patr.equals(""))) {
                        patr = " ";
                    }
                    sb.append("<patr>").append(patr).append("</patr>");
                    sb.append("<dr>").append(ipra.getChildId().getFormat2Dr()).append("</dr>");
                    sb.append("<region>").append(ipra.getChildId().getSprregId().getSprregName()).append("</region>");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

                    String nomPrikaz = ipra.getIpraPrikazN();
                    if ((nomPrikaz == null) || (nomPrikaz.equals(""))) {
                        nomPrikaz = " ";
                    }
                    sb.append("<prikazn>").append(nomPrikaz).append("</prikazn>");
                    String datePrikaz = " ";
                    try {
                        datePrikaz = simpleDateFormat.format(ipra.getIpraPrikazD());
                    } catch (Exception ex) {
                    }
                    sb.append("<prikaz>").append(datePrikaz).append("</prikaz>");
                    String datePerech = " ";
                    try {
                        datePerech = simpleDateFormat.format(ipra.getIpraPerechD());
                    } catch (Exception ex) {
                    }
                    sb.append("<omsudate>").append(datePerech).append("</omsudate>");
                    String dateOtchOmsu = " ";
                    try {
                        dateOtchOmsu = simpleDateFormat.format(ipra.getIpraOtchomsu());
                    } catch (Exception ex) {
                    }
                    String omsuN = ipra.getIpraOmsuN();
                    if (omsuN == null) {
                        omsuN = " ";
                    } else if (omsuN.equals("")) {
                        omsuN = " ";
                    }
                    sb.append("<omsun>").append(omsuN).append("</omsun>");
                    sb.append("<otchomsu>").append(dateOtchOmsu).append("</otchomsu>");
                    String dateOtchCenter = " ";
                    try {
                        dateOtchCenter = simpleDateFormat.format(ipra.getIpraOtchcenter());
                    } catch (Exception ex) {
                    }
                    sb.append("<otchcenter>").append(dateOtchCenter).append("</otchcenter>");
                    String dateOtchDo = " ";
                    try {
                        dateOtchDo = simpleDateFormat.format(ipra.getIpraOtchdo());
                    } catch (Exception ex) {
                    }
                    sb.append("<otchdo>").append(dateOtchDo).append("</otchdo>");
                    sb.append("<archive>").append(1).append("</archive>");
                    sb.append("</ipra>");
                }
                sb.append("</ipras>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
