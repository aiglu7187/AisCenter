/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.ChildStatus;
import Entities.Children;
import Entities.Ipra18;
import Entities.Ipra18Prikaz;
import Entities.IpraEduCondition;
import Entities.IpraPerechen;
import Entities.IpraeducondType;
import Entities.Kalendar;
import Entities.LoginLog;
import Entities.Nom;
import Entities.OtherpmpkRegion;
import Entities.Pmpk;
import Entities.SprMse;
import Entities.SprOmsu;
import Entities.SprOtherPmpk;
import Entities.SprRegion;
import Entities.Users;
import Other.IpraTpmpkRequest;
import Other.RegionComparator;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenFacade;
import Sessions.ChildrenViewFacade;
import Sessions.Ipra18Facade;
import Sessions.Ipra18PrikazFacade;
import Sessions.IpraEduConditionFacade;
import Sessions.IpraPerechenFacade;
import Sessions.IpraeducondTypeFacade;
import Sessions.KalendarFacade;
import Sessions.LoginLogFacade;
import Sessions.NomFacade;
import Sessions.OtherpmpkRegionFacade;
import Sessions.PmpkFacade;
import Sessions.SprMseFacade;
import Sessions.SprOmsuFacade;
import Sessions.SprOtherPmpkFacade;
import Sessions.SprRegionFacade;
import Sessions.SprStatFacade;
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
import java.util.Map;
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
@WebServlet(name = "Ipra2018SpisokServlet",
        loadOnStartup = 1,
        urlPatterns = "/ipra2018spisok")

public class Ipra2018SpisokServlet extends HttpServlet {

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
    Ipra18Facade ipra18Facade = new Ipra18Facade();
    @EJB
    NomFacade nomFacade = new NomFacade();
    @EJB
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    SprStatFacade sprStatFacade = new SprStatFacade();
    @EJB
    SprMseFacade sprMseFacade = new SprMseFacade();
    @EJB
    OtherpmpkRegionFacade otherpmpkRegionFacade = new OtherpmpkRegionFacade();
    @EJB
    SprOmsuFacade sprOmsuFacade = new SprOmsuFacade();
    @EJB
    SprOtherPmpkFacade sprOtherPmpkFacade = new SprOtherPmpkFacade();
    @EJB
    Ipra18PrikazFacade ipra18PrikazFacade = new Ipra18PrikazFacade();
    @EJB
    IpraeducondTypeFacade ipraeducondTypeFacade = new IpraeducondTypeFacade();
    @EJB
    IpraPerechenFacade ipraPerechenFacade = new IpraPerechenFacade();
    @EJB
    IpraEduConditionFacade ipraEduConditionFacade = new IpraEduConditionFacade();
    @EJB
    PmpkFacade pmpkFacade = new PmpkFacade();

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
        if (action.equals("spisok")) {
            userPath = "/ipra/ipra18spisok";
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
            userPath = "/ipra/ipra18add";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
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
            List<Ipra18> resIpra = new ArrayList<>();
            if (result.equals("all")) {
                resIpra = ipra18Facade.findAllCur();
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

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date datePr = null;
                try {
                    datePr = simpleDateFormat.parse(dpr);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                resIpra = ipra18Facade.findBySearch(fam, name, patr, region, sort, datePr, npr, 1);
            }
            if (!resIpra.isEmpty()) {
                sb.append("<ipras>");
                for (Ipra18 ipra : resIpra) {
                    sb.append("<ipra>");
                    sb.append("<id>").append(ipra.getIpra18Id()).append("</id>");
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

                    Ipra18Prikaz ipraPrikaz = null;
                    try {
                        ipraPrikaz = ipra18PrikazFacade.findByIpra(ipra);
                    } catch (Exception ex) {
                    }
                    if (ipraPrikaz != null) {
                        String nomPrikaz = ipraPrikaz.getIpra18prikazDoN();
                        if ((nomPrikaz == null) || (nomPrikaz.equals(""))) {
                            nomPrikaz = " ";
                        }
                        sb.append("<prikazn>").append(nomPrikaz).append("</prikazn>");
                        String datePrikaz = " ";
                        try {
                            datePrikaz = simpleDateFormat.format(ipraPrikaz.getIpra18prikazDoD());
                        } catch (Exception ex) {
                        }
                        sb.append("<prikaz>").append(datePrikaz).append("</prikaz>");
                        String datePerech = " ";
                        try {
                            datePerech = simpleDateFormat.format(ipraPrikaz.getIpra18prikazPerechD());
                        } catch (Exception ex) {
                        }
                        sb.append("<omsudate>").append(datePerech).append("</omsudate>");
                        String omsuN = ipraPrikaz.getIpra18prikazPerechN();
                        if (omsuN == null) {
                            omsuN = " ";
                        } else if (omsuN.equals("")) {
                            omsuN = " ";
                        }
                        sb.append("<omsun>").append(omsuN).append("</omsun>");
                        String dateOtchOmsu = " ";
                        try {
                            dateOtchOmsu = simpleDateFormat.format(ipraPrikaz.getIpra18prikazOtchomsu());
                        } catch (Exception ex) {
                        }
                        sb.append("<otchomsu>").append(dateOtchOmsu).append("</otchomsu>");
                        String dateOtchCenter = " ";
                        try {
                            dateOtchCenter = simpleDateFormat.format(ipraPrikaz.getIpra18prikazOtchcenter());
                        } catch (Exception ex) {
                        }
                        sb.append("<otchcenter>").append(dateOtchCenter).append("</otchcenter>");
                    } else {
                        sb.append("<prikazn>").append(" ").append("</prikazn>");
                        sb.append("<prikaz>").append(" ").append("</prikaz>");
                        sb.append("<omsun>").append(" ").append("</omsun>");
                        sb.append("<omsudate>").append(" ").append("</omsudate>");
                        sb.append("<otchomsu>").append(" ").append("</otchomsu>");
                        sb.append("<otchcenter>").append(" ").append("</otchcenter>");
                    }
                    String dateOtchDo = " ";
                    try {
                        dateOtchDo = simpleDateFormat.format(ipra.getIpra18Otchdo());
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
            Ipra18 ipra = null;
            if (id != 0) {
                ipra = ipra18Facade.findById(id);
            }
            if (ipra != null) {
                try {
                    session.removeAttribute("ipra");
                } catch (Exception ex) {
                }
                session.setAttribute("ipra", ipra);

                try {
                    session.removeAttribute("ipraPrikaz");
                } catch (Exception ex) {
                }
                Ipra18Prikaz ipraPrikaz = null;
                try {
                    ipraPrikaz = ipra18PrikazFacade.findByIpra(ipra);
                } catch (Exception ex) {
                }
                if (ipraPrikaz != null) {
                    session.setAttribute("ipraPrikaz", ipraPrikaz);
                }

                List<SprMse> sprmseList = sprMseFacade.findAllSprMse();
                try {
                    session.removeAttribute("sprmseList");
                } catch (Exception ex) {
                }
                session.setAttribute("sprmseList", sprmseList);

                List<SprOtherPmpk> sprotherpmpkList = sprOtherPmpkFacade.findAllSprOtherPmpk();
                try {
                    session.removeAttribute("sprotherpmpkList");
                } catch (Exception ex) {
                }
                session.setAttribute("sprotherpmpkList", sprotherpmpkList);

                List<SprOmsu> spromsuList = sprOmsuFacade.findAllSprOmsu();
                try {
                    session.removeAttribute("spromsuList");
                } catch (Exception ex) {
                }
                session.setAttribute("spromsuList", spromsuList);

                List<IpraeducondType> types = ipraeducondTypeFacade.findAll();
                try {
                    session.removeAttribute("types");
                } catch (Exception ex) {
                }
                session.setAttribute("types", types);

                IpraPerechen ipraPerechen = null;
                try {
                    ipraPerechen = ipraPerechenFacade.findByIpra18(ipra);
                } catch (Exception ex) {
                }
                try {
                    session.removeAttribute("ipraPerechen");
                } catch (Exception ex) {
                }
                session.setAttribute("ipraPerechen", ipraPerechen);

                if (ipraPerechen != null) {
                    List<IpraEduCondition> conditions = ipraEduConditionFacade.findByIpraPerechen(ipraPerechen);
                    try {
                        session.removeAttribute("conditions");
                    } catch (Exception ex) {
                    }
                    session.setAttribute("conditions", conditions);
                }

                // пробуем найти рекомендации ПМПК
                List<Pmpk> pmpkList = pmpkFacade.findPmpkByChild(ipra.getChildId());
                String obr = "";
                if (!pmpkList.isEmpty()) {
                    Date prDate = pmpkList.get(0).getPrclId().getPriyomId().getPriyomDate();
                    if (pmpkList.get(0).getSprobrId() != null) {
                        obr = pmpkList.get(0).getSprobrId().getSprobrFullname();
                    }
                    if (pmpkList.get(0).getSprobrvarId() != null) {
                        obr += " (" + pmpkList.get(0).getSprobrvarId().getSprobrvarName() + ")";
                    }
                    for (Pmpk pmpk : pmpkList) {
                        if (prDate.before(pmpk.getPrclId().getPriyomId().getPriyomDate())) {
                            prDate = pmpk.getPrclId().getPriyomId().getPriyomDate();
                            if (pmpk.getSprobrId() != null) {
                                obr = pmpk.getSprobrId().getSprobrFullname();
                            }
                            if (pmpk.getSprobrvarId() != null) {
                                obr += " (" + pmpk.getSprobrvarId().getSprobrvarName() + ")";
                            }
                        }
                    }
                }
                try {
                    session.removeAttribute("obr");
                } catch (Exception ex) {
                }
                session.setAttribute("obr", obr);

                // расчёт ступеней обучения
                Date dr = ipra.getChildId().getChildDr();
                Calendar drC = Calendar.getInstance();
                drC.setTime(dr);
                // дошкольное образование
                int year = drC.get(Calendar.YEAR) + 7;
                Calendar dateDO = Calendar.getInstance();
                dateDO.set(Calendar.DATE, 31);
                dateDO.set(Calendar.MONTH, 4);
                dateDO.set(Calendar.YEAR, year);
                // начальное образование (2 варианта в зависимости от программы) 
                int year1 = year + 4;
                Calendar dateNOO1 = Calendar.getInstance();
                dateNOO1.set(Calendar.DATE, 31);
                dateNOO1.set(Calendar.MONTH, 4);
                dateNOO1.set(Calendar.YEAR, year1);
                int year2 = year + 5;
                Calendar dateNOO2 = Calendar.getInstance();
                dateNOO2.set(Calendar.DATE, 31);
                dateNOO2.set(Calendar.MONTH, 4);
                dateNOO2.set(Calendar.YEAR, year2);
                // основное образование (2 варианта в зависимости от НОО)
                year1 += 5;
                Calendar dateOOO1 = Calendar.getInstance();
                dateOOO1.set(Calendar.DATE, 31);
                dateOOO1.set(Calendar.MONTH, 4);
                dateOOO1.set(Calendar.YEAR, year1);
                year2 += 5;
                Calendar dateOOO2 = Calendar.getInstance();
                dateOOO2.set(Calendar.DATE, 31);
                dateOOO2.set(Calendar.MONTH, 4);
                dateOOO2.set(Calendar.YEAR, year2);
                // среднее образование
                year1 += 2;
                Calendar dateSOO1 = Calendar.getInstance();
                dateSOO1.set(Calendar.DATE, 31);
                dateSOO1.set(Calendar.MONTH, 4);
                dateSOO1.set(Calendar.YEAR, year1);
                year2 += 2;
                Calendar dateSOO2 = Calendar.getInstance();
                dateSOO2.set(Calendar.DATE, 31);
                dateSOO2.set(Calendar.MONTH, 4);
                dateSOO2.set(Calendar.YEAR, year2);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    session.removeAttribute("datedo");
                } catch (Exception ex) {
                }
                session.setAttribute("datedo", dateFormat.format(dateDO.getTime()));
                try {
                    session.removeAttribute("var1");
                } catch (Exception ex) {
                }
                session.setAttribute("datevar1", "НОО - " + dateFormat.format(dateNOO1.getTime()) + ", ООО - "
                        + dateFormat.format(dateOOO1.getTime()) + ", СОО - " + dateFormat.format(dateSOO1.getTime()));
                try {
                    session.removeAttribute("var2");
                } catch (Exception ex) {
                }
                session.setAttribute("datevar2", "НОО - " + dateFormat.format(dateNOO2.getTime()) + ", ООО - "
                        + dateFormat.format(dateOOO2.getTime()) + ", СОО - " + dateFormat.format(dateSOO2.getTime()));

                userPath = "/ipra/ipra18view";
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
                Ipra18 ipra = ipra18Facade.findById(id);
                if (ipra != null) {
                    ipra18Facade.remove(ipra);
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
            userPath = "/ipra/ipra18archive";
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
            List<Ipra18> resIpra = new ArrayList<>();
            if (result.equals("all")) {
                resIpra = ipra18Facade.findAllArchive();
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

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date datePr = null;
                try {
                    datePr = simpleDateFormat.parse(dpr);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                resIpra = ipra18Facade.findBySearch(fam, name, patr, region, sort, datePr, npr, 0);
            }
            if (!resIpra.isEmpty()) {
                sb.append("<ipras>");
                for (Ipra18 ipra : resIpra) {
                    sb.append("<ipra>");
                    sb.append("<id>").append(ipra.getIpra18Id()).append("</id>");
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

                    Ipra18Prikaz ipraPrikaz = null;
                    try {
                        ipraPrikaz = ipra18PrikazFacade.findByIpra(ipra);
                    } catch (Exception ex) {
                    }
                    if (ipraPrikaz != null) {
                        String nomPrikaz = ipraPrikaz.getIpra18prikazDoN();
                        if ((nomPrikaz == null) || (nomPrikaz.equals(""))) {
                            nomPrikaz = " ";
                        }
                        sb.append("<prikazn>").append(nomPrikaz).append("</prikazn>");
                        String datePrikaz = " ";
                        try {
                            datePrikaz = simpleDateFormat.format(ipraPrikaz.getIpra18prikazDoD());
                        } catch (Exception ex) {
                        }
                        sb.append("<prikaz>").append(datePrikaz).append("</prikaz>");
                        String datePerech = " ";
                        try {
                            datePerech = simpleDateFormat.format(ipraPrikaz.getIpra18prikazPerechD());
                        } catch (Exception ex) {
                        }
                        sb.append("<omsudate>").append(datePerech).append("</omsudate>");
                        String omsuN = ipraPrikaz.getIpra18prikazPerechN();
                        if (omsuN == null) {
                            omsuN = " ";
                        } else if (omsuN.equals("")) {
                            omsuN = " ";
                        }
                        sb.append("<omsun>").append(omsuN).append("</omsun>");
                        String dateOtchOmsu = " ";
                        try {
                            dateOtchOmsu = simpleDateFormat.format(ipraPrikaz.getIpra18prikazOtchomsu());
                        } catch (Exception ex) {
                        }
                        sb.append("<otchomsu>").append(dateOtchOmsu).append("</otchomsu>");
                        String dateOtchCenter = " ";
                        try {
                            dateOtchCenter = simpleDateFormat.format(ipraPrikaz.getIpra18prikazOtchcenter());
                        } catch (Exception ex) {
                        }
                        sb.append("<otchcenter>").append(dateOtchCenter).append("</otchcenter>");
                    } else {
                        sb.append("<prikazn>").append(" ").append("</prikazn>");
                        sb.append("<prikaz>").append(" ").append("</prikaz>");
                        sb.append("<omsun>").append(" ").append("</omsun>");
                        sb.append("<omsudate>").append(" ").append("</omsudate>");
                        sb.append("<otchomsu>").append(" ").append("</otchomsu>");
                        sb.append("<otchcenter>").append(" ").append("</otchcenter>");
                    }
                    String dateOtchDo = " ";
                    try {
                        dateOtchDo = simpleDateFormat.format(ipra.getIpra18Otchdo());
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

        } else if (action.equals("findomsu")) {
            String regIdS = request.getParameter("regid");
            Integer regId = 0;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }
            SprRegion reg = null;
            try {
                reg = sprRegionFacade.findById(regId);
            } catch (Exception ex) {
            }

            List<SprOmsu> omsuByReg = new ArrayList<>();
            if (reg != null) {
                omsuByReg = sprOmsuFacade.findByRegion(reg);
            }

            List<SprOmsu> allOmsu = sprOmsuFacade.findAllSprOmsu();
            StringBuilder sb = new StringBuilder();
            if (!allOmsu.isEmpty()) {
                sb.append("<omsulist>");
                for (SprOmsu sprOmsu : allOmsu) {
                    sb.append("<omsu>");
                    sb.append("<id>").append(sprOmsu.getSpromsuId()).append("</id>");
                    sb.append("<name>").append(sprOmsu.getSpromsuName()).append("</name>");
                    boolean flag = false;
                    for (SprOmsu omsu : omsuByReg) {
                        if (omsu.equals(sprOmsu)) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        sb.append("<selected>").append(1).append("</selected>");
                    } else {
                        sb.append("<selected>").append(0).append("</selected>");
                    }
                    sb.append("</omsu>");
                }
                sb.append("</omsulist>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }

        } else if (action.equals("findtpmpk")) {
            String regIdS = request.getParameter("regid");
            Integer regId = 0;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }
            SprRegion reg = null;
            try {
                reg = sprRegionFacade.findById(regId);
            } catch (Exception ex) {
            }

            SprOtherPmpk tpmpk = null;
            OtherpmpkRegion tpmpkByReg = null;
            try {
                tpmpkByReg = otherpmpkRegionFacade.findByRegion(reg);
            } catch (Exception ex) {
            }

            if (tpmpkByReg != null) {
                tpmpk = tpmpkByReg.getSprotherpmpkId();
            }

            List<SprOtherPmpk> allTpmpk = sprOtherPmpkFacade.findAllSprOtherPmpk();
            StringBuilder sb = new StringBuilder();

            if (!allTpmpk.isEmpty()) {
                sb.append("<tpmpklist>");
                sb.append("<tpmpk>");
                sb.append("<id>").append("0").append("</id>");
                sb.append("<name>").append(" ").append("</name>");
                if (tpmpk == null) {
                    sb.append("<selected>").append(1).append("</selected>");
                } else {
                    sb.append("<selected>").append(0).append("</selected>");
                }
                sb.append("</tpmpk>");
                for (SprOtherPmpk t : allTpmpk) {
                    sb.append("<tpmpk>");
                    sb.append("<id>").append(t.getSprotherpmpkId()).append("</id>");
                    sb.append("<name>").append(t.getSprotherpmpkShname()).append("</name>");
                    boolean flag = false;
                    if (tpmpk != null) {
                        if (tpmpk.equals(t)) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        sb.append("<selected>").append(1).append("</selected>");
                    } else {
                        sb.append("<selected>").append(0).append("</selected>");
                    }
                    sb.append("</tpmpk>");
                }
                sb.append("</tpmpklist>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }

        } else if (action.equals("findregion")) {
            String regName = request.getParameter("name");
            List<SprRegion> regionList = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            try {
                regionList = sprRegionFacade.findByName(regName);
            } catch (Exception ex) {
            }
            if (!regionList.isEmpty()) {
                sb.append("<regid>").append(regionList.get(0).getSprregId()).append("</regid>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("tpmpkreqlist")) {
            List<Ipra18Prikaz> ipraPrikazList = ipra18PrikazFacade.findNoTpmpkN();
            try {
                session.removeAttribute("ipraPrikazList");
            } catch (Exception ex) {
            }
            session.setAttribute("ipraPrikazList", ipraPrikazList);
            Date now = new Date();
            Calendar nowC = Calendar.getInstance();
            nowC.setTime(now);
            nowC.set(Calendar.MILLISECOND, 0);
            nowC.set(Calendar.SECOND, 0);
            nowC.set(Calendar.MINUTE, 0);
            nowC.set(Calendar.HOUR, 0);
            now = nowC.getTime();
            try {
                session.removeAttribute("now");
            } catch (Exception ex) {
            }
            session.setAttribute("now", now);
            userPath = "/ipra/ipra18tpmpkspisok";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("formreqtotpmpk")) {
            StringBuilder sb = new StringBuilder();
            String idVS = request.getParameter("id");
            String[] idV = idVS.split(";");
            List<Ipra18> ipraList = new ArrayList<>();
            for (String id : idV) {
                try {
                    Ipra18 ipra = ipra18Facade.findById(Integer.parseInt(id));
                    ipraList.add(ipra);
                } catch (Exception ex) {
                }
            }
            List<IpraTpmpkRequest> requests = new ArrayList<>();
            for (Ipra18 ipra : ipraList) {
                Ipra18Prikaz prikaz = ipra18PrikazFacade.findByIpra(ipra);
                boolean flag = false;
                for (IpraTpmpkRequest req : requests) {
                    if (req.getSprOtherPmpk().equals(prikaz.getSprotherpmpkId())) {
                        if (req.getReqDate().equals(prikaz.getIpra18prikazTpmpkD())) {
                            flag = true;
                            List<Ipra18> newIpra18List = req.getIpra18List();
                            newIpra18List.add(ipra);
                            req.setIpra18List(newIpra18List);
                        }
                    }

                }
                if (!flag) {
                    IpraTpmpkRequest newReq = new IpraTpmpkRequest();
                    newReq.setSprOtherPmpk(prikaz.getSprotherpmpkId());
                    newReq.setReqDate(prikaz.getIpra18prikazTpmpkD());
                    List<Ipra18> newIpra18List = new ArrayList<>();
                    newIpra18List.add(ipra);
                    newReq.setIpra18List(newIpra18List);
                    requests.add(newReq);
                }
            }
            if (!requests.isEmpty()) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                sb.append("<tpmpkreqs>");
                int i = 0;
                for (IpraTpmpkRequest req : requests) {
                    String formatDate = format.format(req.getReqDate());
                    sb.append("<tpmpkreq>");
                    i++;
                    sb.append("<n>").append(i).append("</n>");
                    List<Ipra18> ipra18List = req.getIpra18List();
                    String ipraids = "";
                    for (Ipra18 ipra : ipra18List) {
                        ipraids += ipra.getIpra18Id() + ";";
                    }
                    sb.append("<ipraids>").append(ipraids).append("</ipraids>");
                    sb.append("<tpmpkname>").append(req.getSprOtherPmpk().getSprotherpmpkShname()).append("</tpmpkname>");
                    sb.append("<reqdate>").append(formatDate).append("</reqdate>");
                    sb.append("<childrencount>").append(req.getIpra18List().size()).append("</childrencount>");
                    sb.append("</tpmpkreq>");
                }
                sb.append("</tpmpkreqs>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("opennoinfo")) {
            userPath = "/ipra/ipra18noinfo";
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
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");

        Map<String, String[]> params = request.getParameterMap();

        String action = params.get("action")[0];
        if (action.equals("save")) {
            StringBuilder sb = new StringBuilder();

            String ipraId = null;
            try {
                ipraId = params.get("ipraId")[0];
            } catch (Exception ex) {
            }
            Ipra18 ipra = null;
            Ipra18Prikaz ipraPrikaz = null;
            if (ipraId == null) {
                ipra = new Ipra18();
                ipraPrikaz = new Ipra18Prikaz();
                ipraPrikaz.setIpra18Id(ipra);
            } else {
                ipra = ipra18Facade.findById(Integer.parseInt(ipraId));
                ipraPrikaz = ipra18PrikazFacade.findByIpra(ipra);
            }

            String statusS = "";
            Integer status = 1;
            try {
                statusS = params.get("status")[0];
                status = Integer.parseInt(statusS);
            } catch (Exception ex) {
            }

            String childIdS = null;
            try {
                childIdS = params.get("childId")[0];
            } catch (Exception ex) {
            }

            Children child = null;
            if (childIdS != null) {
                Integer childId = 0;
                try {
                    childId = Integer.parseInt(childIdS);
                } catch (Exception ex) {

                }
                if (childId != 0) {
                    child = childrenFacade.findById(childId);
                }
            } else {
                String childFam = params.get("childFam")[0];
                String childName = params.get("childName")[0];
                String childPatr = params.get("childPatr")[0];
                String childDrS = params.get("childDr")[0];
                String childPol = params.get("pol")[0];
                Date childDr = null;
                try {
                    childDr = format.parse(childDrS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                String regIdS = params.get("regId")[0];
                Integer regId = 0;
                try {
                    regId = Integer.parseInt(regIdS);
                } catch (Exception ex) {
                }
                SprRegion reg = null;
                if (regId != 0) {
                    reg = sprRegionFacade.findById(regId);
                }
                Nom nom = new Nom();
                nomFacade.create(nom);

                child = new Children();
                child.setChildNom(nom.getNom());
                child.setChildFam(childFam);
                child.setChildName(childName);
                child.setChildPatr(childPatr);
                if (childDr != null) {
                    child.setChildDr(childDr);
                }
                if (reg != null) {
                    child.setSprregId(reg);
                }
                child.setChildPol(childPol);
                child.setUserId(user);
                child.setDateUpd(curDate);
                childrenFacade.create(child);
            }
            ipra.setChildId(child);
            if (status == 1) {
                // номер ИПРА
                String ipraN = params.get("ipraN")[0];
                ipra.setIpra18N(ipraN);
                // номер экспертизы
                String ipraNexp = params.get("ipraNexp")[0];
                ipra.setIpra18Nexp(ipraNexp);
                // дата экспертизы
                String ipraDateexpS = params.get("expDate")[0];
                Date ipraDateexp = null;
                try {
                    ipraDateexp = format.parse(ipraDateexpS);

                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (ipraDateexp != null) {
                    ipra.setIpra18Dateexp(ipraDateexp);
                }
                // дата окончания ИПРА
                String ipraDateokS = params.get("ipraDateOk")[0];
                Date ipraDateok = null;
                try {
                    ipraDateok = format.parse(ipraDateokS);

                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (ipraDateok != null) {
                    ipra.setIpra18Dateok(ipraDateok);
                }
                // бюро МСЭ    
                String mseIdS = params.get("mseId")[0];
                Integer mseId = 0;
                try {
                    mseId = Integer.parseInt(mseIdS);
                } catch (Exception ex) {
                }
                SprMse sprMse = null;
                if (mseId != 0) {
                    sprMse = sprMseFacade.findById(mseId);
                }
                if (sprMse != null) {
                    ipra.setSprmseId(sprMse);
                }
                // исходящее письмо МСЭ номер
                String ipraIshmseN = params.get("ishMseN")[0];
                ipra.setIpra18IshmseN(ipraIshmseN);
                // исходящее письмо МСЭ дата
                String ipraIshmseDS = params.get("ishMseD")[0];
                Date ipraIshmseD = null;
                try {
                    ipraIshmseD = format.parse(ipraIshmseDS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (ipraIshmseD != null) {
                    ipra.setIpra18IshmseD(ipraIshmseD);
                }
                // входящее письмо ДО номер
                String ipraVhdoN = null;
                try {
                    ipraVhdoN = params.get("vhDoN")[0];
                    ipra.setIpra18VhdoN(ipraVhdoN);
                } catch (Exception ex) {
                }
                // входящее письмо ДО дата
                String ipraVhdoDS = "";
                try {
                    ipraVhdoDS = params.get("vhDoD")[0];
                } catch (Exception ex) {
                }
                Date ipraVhdoD = null;
                try {
                    ipraVhdoD = format.parse(ipraVhdoDS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (ipraVhdoD != null) {
                    ipra.setIpra18VhdoD(ipraVhdoD);
                }

                // приказ ДО номер
                String prikazDoN = "";
                try {
                    prikazDoN = params.get("prikazDoN")[0];
                    ipraPrikaz.setIpra18prikazDoN(prikazDoN);
                } catch (Exception ex) {
                }
                // приказ ДО дата
                String prikazDoDS = "";
                try {
                    prikazDoDS = params.get("prikazDoD")[0];
                } catch (Exception ex) {
                }
                Date prikazDoD = null;
                try {
                    prikazDoD = format.parse(prikazDoDS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (prikazDoD != null) {
                    ipraPrikaz.setIpra18prikazDoD(prikazDoD);
                }
                // ТПМПК
                String tpmpkIdS = "";
                try {
                    tpmpkIdS = params.get("tpmpkId")[0];
                } catch (Exception ex) {
                }
                Integer tpmpkId = 0;
                try {
                    tpmpkId = Integer.parseInt(tpmpkIdS);
                } catch (Exception ex) {
                }
                SprOtherPmpk tpmpk = null;
                if (tpmpkId != 0) {
                    try {
                        tpmpk = sprOtherPmpkFacade.findById(tpmpkId);
                    } catch (Exception ex) {
                    }
                }
                ipraPrikaz.setSprotherpmpkId(tpmpk);
                // дата запроса в ТПМПК
                String tpmpkDS = "";
                try {
                    tpmpkDS = params.get("tpmpkD")[0];
                } catch (Exception ex) {
                }
                Date tpmpkD = null;
                try {
                    tpmpkD = format.parse(tpmpkDS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (tpmpkD != null) {
                    ipraPrikaz.setIpra18prikazTpmpkD(tpmpkD);
                }
                // номер письма в ТПМПК
                String tpmpkN = "";
                try {
                    tpmpkN = params.get("tpmpkN")[0];
                    ipraPrikaz.setIpra18prikazTpmpkN(tpmpkN);
                } catch (Exception ex) {
                }

                // ОМСУ
                String omsuIdS = "";
                try {
                    omsuIdS = params.get("omsuId")[0];
                } catch (Exception ex) {
                }
                Integer omsuId = 0;
                try {
                    omsuId = Integer.parseInt(omsuIdS);
                } catch (Exception ex) {
                }
                SprOmsu omsu = null;
                if (omsuId != 0) {
                    try {
                        omsu = sprOmsuFacade.findById(omsuId);
                    } catch (Exception ex) {
                    }
                }
                if (omsu != null) {
                    ipraPrikaz.setSpromsuId(omsu);
                }
                // отказ
                String selOtkaz = null;
                try {
                    selOtkaz = params.get("selOtkaz")[0];
                } catch (Exception ex) {
                }
                if (selOtkaz != null) {
                    if (selOtkaz.equals("1")) {
                        ipraPrikaz.setIpra18prikazOtkaz(1);
                    }
                } else {
                    ipraPrikaz.setIpra18prikazOtkaz(0);
                }

                // номер запроса из ОМСУ
                String omsuReqN = "";
                try {
                    omsuReqN = params.get("omsuReqN")[0];
                    ipraPrikaz.setIpra18prikazReqN(omsuReqN);
                } catch (Exception ex) {
                }

                // дата запроса из ОМСУ
                String omsuReqDS = "";
                try {
                    omsuReqDS = params.get("omsuReqD")[0];
                } catch (Exception ex) {
                }
                Date omsuReqD = null;
                try {
                    omsuReqD = format.parse(omsuReqDS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (omsuReqD != null) {
                    ipraPrikaz.setIpra18prikazReqD(omsuReqD);
                }
                // дата письма в ОМСУ
                String omsuDS = "";
                try {
                    omsuDS = params.get("omsuD")[0];
                } catch (Exception ex) {
                }
                Date omsuD = null;
                try {
                    omsuD = format.parse(omsuDS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (omsuD != null) {
                    ipraPrikaz.setIpra18prikazPerechD(omsuD);
                }
                // номер письма в ОМСУ
                String omsuN = "";
                try {
                    omsuN = params.get("omsuN")[0];
                } catch (Exception ex) {
                }
                if (omsuN != null) {
                    ipraPrikaz.setIpra18prikazPerechN(omsuN);
                }
                /*    // дата приказа ОМСУ
            String prOmsuDS = params.get("prOmsuD")[0];
            Date prOmsuD = null;
            try {
                prOmsuD = format.parse(prOmsuDS);
            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (prOmsuD != null) {
                ipraPrikaz.setIpra18prikazOmsuprD(prOmsuD);
            }
            // дата ознакомления родителей
            String oznakDS = params.get("oznakD")[0];
            Date oznakD = null;
            try {
                oznakD = format.parse(oznakDS);
            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (oznakD != null) {
                ipraPrikaz.setIpra18prikazOznakD(oznakD);
            }
                 */
                // дата отчёта ОМСУ
                String otchOmsuS = "";
                try {
                    otchOmsuS = params.get("otchOmsu")[0];
                } catch (Exception ex) {
                }
                Date otchOmsu = null;
                try {
                    otchOmsu = format.parse(otchOmsuS);

                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (otchOmsu != null) {
                    ipraPrikaz.setIpra18prikazOtchomsu(otchOmsu);
                }
                // дата отчёта ОЦППМСП
                String otchCenterS = "";
                try {
                    otchCenterS = params.get("otchCenter")[0];
                } catch (Exception ex) {
                }
                Date otchCenter = null;
                try {
                    otchCenter = format.parse(otchCenterS);

                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (otchCenter != null) {
                    ipraPrikaz.setIpra18prikazOtchcenter(otchCenter);
                }
                // номер сопроводительного письма к отчёту ОЦППМСП в ДО
                String otchCenterN = "";
                try {
                    otchCenterN = params.get("otchCenterN")[0];
                } catch (Exception ex) {
                }
                if (otchCenterN != null) {
                    ipraPrikaz.setIpra18prikazOtchcenterN(otchCenterN);
                }
                // дата отчёта ДО
                String otchDoS = "";
                try {
                    otchDoS = params.get("otchDo")[0];
                } catch (Exception ex) {
                }
                Date otchDo = null;
                try {
                    otchDo = format.parse(otchDoS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (otchDo != null) {
                    ipra.setIpra18Otchdo(otchDo);
                }
            }
            ipra.setIpra18Status(status);
            ipra.setUserId(user);
            ipra.setDateUpd(curDate);

            try {
                if (ipraId == null) {
                    ipra18Facade.create(ipra);
                    ipra18PrikazFacade.create(ipraPrikaz);
                } else {
                    ipra18Facade.edit(ipra);
                    ipra18PrikazFacade.edit(ipraPrikaz);
                }

                List<ChildStatus> chstList = childStatusFacade.findByChildAct(child);
                Boolean isDI = false;
                Boolean isTR = false;
                for (ChildStatus chst : chstList) {
                    if (chst.getSprstatId().getSprstatInv() == 1) {
                        isDI = true;
                        chst.setChildstatusDateK(ipra.getIpra18Dateok());
                        childStatusFacade.edit(chst);
                    } else if (chst.getSprstatId().getSprstatName().equals("ТР")) {
                        isTR = true;
                    }
                }
                if (!isDI) {
                    ChildStatus childStatus = new ChildStatus();
                    childStatus.setChildId(child);
                    childStatus.setSprstatId(sprStatFacade.findById(6));
                    childStatus.setChildstatusDateN(ipra.getIpra18Dateexp());
                    childStatus.setChildstatusDateK(ipra.getIpra18Dateok());
                    childStatusFacade.create(childStatus);
                }
                if (!isTR) {
                    ChildStatus childStatus = new ChildStatus();
                    childStatus.setChildId(child);
                    childStatus.setSprstatId(sprStatFacade.findById(2));
                    childStatus.setChildstatusDateN(ipra.getIpra18Dateexp());
                    childStatusFacade.create(childStatus);
                }
                sb.append("<result>").append(ipra.getIpra18Id()).append("</result>");
            } catch (Exception ex) {
                sb.append("<result>").append("0").append("</result>");
            }

            if (sb.length() > 0) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("saveperechen")) {
            String perechenIdS = null;
            try {
                perechenIdS = params.get("perechenId")[0];
            } catch (Exception ex) {
            }
            IpraPerechen perechen = null;
            if (perechenIdS != null) {
                try {
                    perechen = ipraPerechenFacade.findById(Integer.parseInt(perechenIdS));
                } catch (Exception ex) {
                }
            }
            if (perechen == null) {
                perechen = new IpraPerechen();
            }
            Ipra18 ipra = null;
            try {
                String ipraId = params.get("ipraId")[0];
                ipra = ipra18Facade.findById(Integer.parseInt(ipraId));
            } catch (Exception ex) {
            }

            Boolean itsOk = false;
            if (ipra != null) {
                perechen.setIpra18Id(ipra);
                perechen.setDateUpd(curDate);
                perechen.setUserId(user);

                List<String> condList = new ArrayList<>();
                List<String> datecondList = new ArrayList<>();
                for (Map.Entry<String, String[]> entry : params.entrySet()) {
                    String par = entry.getKey();
                    String[] val = entry.getValue();
                    if (par.startsWith("cond")) {
                        condList.add(par);
                    } else if (par.startsWith("datecond")) {
                        datecondList.add(par);
                    }
                }

                // предварительно удалить все IpraEduCondition
                List<IpraEduCondition> conditionList = ipraEduConditionFacade.findByIpraPerechen(perechen);
                for (IpraEduCondition cond : conditionList) {
                    ipraEduConditionFacade.remove(cond);
                }

                for (String cond : condList) {
                    String n = cond.substring(4);
                    String condVal = "";
                    try {
                        condVal = params.get(cond)[0];
                    } catch (Exception ex) {
                    }
                    if (!condVal.equals("")) {
                        for (String datecond : datecondList) {
                            if (datecond.equals("datecond" + n)) {
                                String datecondVal = "";
                                try {
                                    datecondVal = params.get(datecond)[0];
                                } catch (Exception ex) {
                                }
                                if (!datecondVal.equals("")) {
                                    Date date = null;
                                    try {
                                        date = format.parse(datecondVal);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(Ipra2018SpisokServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if (date != null) {
                                        String typeIdS = cond.substring(4, cond.indexOf("_"));
                                        IpraeducondType ipraeducondType = null;
                                        try {
                                            ipraeducondType = ipraeducondTypeFacade.findById(Integer.parseInt(typeIdS));
                                        } catch (Exception ex) {
                                        }
                                        if (ipraeducondType != null) {
                                            IpraEduCondition ipraEduCondition = new IpraEduCondition();
                                            ipraEduCondition.setIpraeducondContext(condVal);
                                            ipraEduCondition.setIpraeducondDate(date);
                                            ipraEduCondition.setIpraeducondtypeId(ipraeducondType);
                                            if (perechen.getIpraperechenId() != null) {
                                                ipraPerechenFacade.edit(perechen);
                                            } else {
                                                ipraPerechenFacade.create(perechen);
                                            }
                                            ipraEduCondition.setIpraperechenId(perechen);
                                            ipraEduConditionFacade.create(ipraEduCondition);
                                            itsOk = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            if (itsOk) {
                sb.append("<result>").append(ipra.getIpra18Id()).append("</result>");
            } else {
                sb.append("<result>").append("0").append("</result>");
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
