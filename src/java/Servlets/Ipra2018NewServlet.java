/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.ChildStatus;
import Entities.Children;
import Entities.Ipra18N;
import Entities.Ipra18PrikazN;
import Entities.IpraEduConditionN;
import Entities.IpraIshcorr;
import Entities.IpraOmsu;
import Entities.IpraPerechenN;
import Entities.IpraPerechenTpmpk;
import Entities.IpraVhcorr;
import Entities.IpraeducondType;
import Entities.IshCorr;
import Entities.Kalendar;
import Entities.LoginLog;
import Entities.Nom;
import Entities.OtherpmpkRegion;
import Entities.Pmpk;
import Entities.SprCorr;
import Entities.SprIshType;
import Entities.SprMse;
import Entities.SprOmsu;
import Entities.SprRegion;
import Entities.SprVhType;
import Entities.Users;
import Entities.VhCorr;
import Other.Correspond;
import Other.CorrespondComparator;
import Other.IpraTpmpkRequestNew;
import Other.MySingleton;
import Other.RegionComparator;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenFacade;
import Sessions.Ipra18NFacade;
import Sessions.Ipra18PrikazNFacade;
import Sessions.IpraEduConditionNFacade;
import Sessions.IpraIshcorrFacade;
import Sessions.IpraOmsuFacade;
import Sessions.IpraPerechenNFacade;
import Sessions.IpraPerechenTpmpkFacade;
import Sessions.IpraVhcorrFacade;
import Sessions.IpraVhnomFacade;
import Sessions.IpraeducondTypeFacade;
import Sessions.IshCorrFacade;
import Sessions.KalendarFacade;
import Sessions.LoginLogFacade;
import Sessions.NomFacade;
import Sessions.OtherpmpkRegionFacade;
import Sessions.PmpkFacade;
import Sessions.SprCorrFacade;
import Sessions.SprIshTypeFacade;
import Sessions.SprMseFacade;
import Sessions.SprOmsuFacade;
import Sessions.SprRegionFacade;
import Sessions.SprStatFacade;
import Sessions.SprVhTypeFacade;
import Sessions.VhCorrFacade;
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
@WebServlet(name = "Ipra2018NewServlet",
        loadOnStartup = 1,
        urlPatterns = "/ipra2018new")
public class Ipra2018NewServlet extends HttpServlet {

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
    Ipra18NFacade ipra18NFacade = new Ipra18NFacade();
    @EJB
    Ipra18PrikazNFacade ipra18PrikazNFacade = new Ipra18PrikazNFacade();
    @EJB
    SprMseFacade sprMseFacade = new SprMseFacade();
    @EJB
    KalendarFacade kalendarFacade = new KalendarFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    NomFacade nomFacade = new NomFacade();
    @EJB
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    SprStatFacade sprStatFacade = new SprStatFacade();
    @EJB
    IpraPerechenNFacade ipraPerechenNFacade = new IpraPerechenNFacade();
    @EJB
    IpraPerechenTpmpkFacade ipraPerechenTpmpkFacade = new IpraPerechenTpmpkFacade();
    @EJB
    IshCorrFacade ishCorrFacade = new IshCorrFacade();
    @EJB
    SprIshTypeFacade sprIshTypeFacade = new SprIshTypeFacade();
    @EJB
    VhCorrFacade vhCorrFacade = new VhCorrFacade();
    @EJB
    SprCorrFacade sprCorrFacade = new SprCorrFacade();
    @EJB
    IpraeducondTypeFacade ipraeducondTypeFacade = new IpraeducondTypeFacade();
    @EJB
    IpraEduConditionNFacade ipraEduConditionNFacade = new IpraEduConditionNFacade();
    @EJB
    PmpkFacade pmpkFacade = new PmpkFacade();
    @EJB
    SprOmsuFacade sprOmsuFacade = new SprOmsuFacade();
    @EJB
    IpraOmsuFacade ipraOmsuFacade = new IpraOmsuFacade();
    @EJB
    SprVhTypeFacade sprVhTypeFacade = new SprVhTypeFacade();
    @EJB
    IpraVhcorrFacade ipraVhcorrFacade = new IpraVhcorrFacade();
    @EJB
    IpraIshcorrFacade ipraIshcorrFacade = new IpraIshcorrFacade();
    @EJB
    IpraVhnomFacade ipraVhnomFacade = new IpraVhnomFacade();
    @EJB
    OtherpmpkRegionFacade otherpmpkRegionFacade = new OtherpmpkRegionFacade();

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
        // action - параметр, обозначающий запрашиваемое действие
        String action = request.getParameter("action");
        StringBuilder sb = new StringBuilder(); // буфер для xml
        boolean itsOk = false;  // признак готовности результата к отправке
        // форматы для дат
        SimpleDateFormat regularDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // пути
        String userPath;
        String url;
        if (action.equals("openlist")) {    // открыть страницу со списками ИПРА            
            // удаляем аттрибут со списком районов (для его замены)
            try {
                session.removeAttribute("regions");
            } catch (Exception ex) {
            }
            // удаляем аттрибут с номером вкладки (для замены)
            try {
                session.removeAttribute("tab");
            } catch (Exception ex) {
            }
            // список районов для поиска
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            // подставляем список районов в соответствующий атрибут сессии
            session.setAttribute("regions", regions);
            // смотрим номер вкладки в запросе
            String tabS = request.getParameter("tab");
            Integer tab = 0;
            try {
                tab = Integer.parseInt(tabS);
            } catch (Exception ex) {
            }
            // пишем в атрибут номер вкладки, которую нужно открыть
            session.setAttribute("tab", tab);
            // формируем адрес страницы
            userPath = "/ipra/nipra18list";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            // передаём адрес страницы клиенту
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("noprikaz")) {  // список ИПРА с запросом на приказ, но без номера приказа
            List<Ipra18PrikazN> noPrikazList = ipra18PrikazNFacade.findNoPrikaz();
            // составляем xml для передачи клиенту
            // заголовок таблицы
            sb.append("<ipranoprikaz>");
            sb.append("<ipra>");
            sb.append("<id>").append("ИД").append("</id>");
            sb.append("<fam>").append("Фамилия").append("</fam>");
            sb.append("<name>").append("Имя").append("</name>");
            sb.append("<patr>").append("Отчество").append("</patr>");
            sb.append("<dr>").append("Дата рождения").append("</dr>");
            sb.append("<reg>").append("Район").append("</reg>");
            sb.append("<datepr>").append("Дата приказа").append("</datepr>");
            sb.append("</ipra>");
            // содержимое таблицы
            if (!noPrikazList.isEmpty()) {
                for (Ipra18PrikazN ip : noPrikazList) {
                    sb.append("<ipra>");
                    sb.append("<id>").append(ip.getIpra18Id().getIpra18Id()).append("</id>");
                    sb.append("<fam>").append(ip.getIpra18Id().getChildId().getChildFam()).append("</fam>");
                    sb.append("<name>").append(ip.getIpra18Id().getChildId().getChildName()).append("</name>");
                    String p = " ";
                    if ((ip.getIpra18Id().getChildId().getChildPatr() != null)
                            && (!ip.getIpra18Id().getChildId().getChildPatr().equals(""))) {
                        p = ip.getIpra18Id().getChildId().getChildPatr();
                    }
                    sb.append("<patr>").append(p).append("</patr>");
                    Date dr = ip.getIpra18Id().getChildId().getChildDr();
                    sb.append("<dr>").append(regularDateFormat.format(dr)).append("</dr>");
                    sb.append("<reg>").append(ip.getIpra18Id().getChildId().getSprregId().getSprregName()).append("</reg>");
                    Date prikazDate = ip.getIpra18prikazD();
                    String prikazDateS = " ";
                    try {
                        prikazDateS = regularDateFormat.format(prikazDate);
                    } catch (Exception ex) {
                    }
                    sb.append("<datepr>").append(prikazDateS).append("</datepr>");
                    sb.append("</ipra>");
                }
            }
            sb.append("</ipranoprikaz>");
            itsOk = true;
            // если всё нормально - передаём клиенту
            if (itsOk) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("noperechen")) {    // список ИПРА, где запрос на перечень есть, 
            // а номера перечня нет (он не был сформирован)
            List<IpraPerechenN> noPerechenList = ipraPerechenNFacade.findNoPerechen();
            // составляем xml для передачи клиенту
            // заголовок таблицы
            sb.append("<ipranoperechen>");
            sb.append("<ipra>");
            sb.append("<id>").append("ИД").append("</id>");
            sb.append("<fam>").append("Фамилия").append("</fam>");
            sb.append("<name>").append("Имя").append("</name>");
            sb.append("<patr>").append("Отчество").append("</patr>");
            sb.append("<dr>").append("Дата рождения").append("</dr>");
            sb.append("<reg>").append("Район").append("</reg>");
            sb.append("<dateper>").append("Дата отправки перечня").append("</dateper>");
            sb.append("</ipra>");
            // содержимое таблицы
            if (!noPerechenList.isEmpty()) {
                for (IpraPerechenN ip : noPerechenList) {
                    sb.append("<ipra>");
                    sb.append("<id>").append(ip.getIpra18Id().getIpra18Id()).append("</id>");
                    sb.append("<fam>").append(ip.getIpra18Id().getChildId().getChildFam()).append("</fam>");
                    sb.append("<name>").append(ip.getIpra18Id().getChildId().getChildName()).append("</name>");
                    String p = " ";
                    if ((ip.getIpra18Id().getChildId().getChildPatr() != null)
                            && (!ip.getIpra18Id().getChildId().getChildPatr().equals(""))) {
                        p = ip.getIpra18Id().getChildId().getChildPatr();
                    }
                    sb.append("<patr>").append(p).append("</patr>");
                    Date dr = ip.getIpra18Id().getChildId().getChildDr();
                    sb.append("<dr>").append(regularDateFormat.format(dr)).append("</dr>");
                    sb.append("<reg>").append(ip.getIpra18Id().getChildId().getSprregId().getSprregName()).append("</reg>");
                    Date perechenDate = ip.getIshcorrId().getIshcorrD();
                    String perechenDateS = " ";
                    try {
                        perechenDateS = regularDateFormat.format(perechenDate);
                    } catch (Exception ex) {
                    }
                    sb.append("<dateper>").append(perechenDateS).append("</dateper>");
                    sb.append("</ipra>");
                }
            }
            sb.append("</ipranoperechen>");
            itsOk = true;
            // если всё нормально - передаём клиенту
            if (itsOk) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("tpmpk")) {    // список ИПРА с запросом на перечень и ТПМПК, где нет номера запроса к ТПМПК
            List<IpraPerechenTpmpk> perechenTpmpkList = ipraPerechenTpmpkFacade.findNoNomTpmpk();
            // составляем xml для передачи клиенту
            // заголовок таблицы
            sb.append("<ipratpmpk>");
            sb.append("<ipra>");
            sb.append("<id>").append("ИД").append("</id>");
            sb.append("<check>").append("checkAll").append("</check>");
            sb.append("<fam>").append("Фамилия").append("</fam>");
            sb.append("<name>").append("Имя").append("</name>");
            sb.append("<patr>").append("Отчество").append("</patr>");
            sb.append("<dr>").append("Дата рождения").append("</dr>");
            sb.append("<reg>").append("Район").append("</reg>");
            sb.append("<tpmpk>").append("ТПМПК").append("</tpmpk>");
            sb.append("<datereq>").append("Дата отправки запроса").append("</datereq>");
            sb.append("</ipra>");
            // содержимое таблицы
            if (!perechenTpmpkList.isEmpty()) {
                for (IpraPerechenTpmpk ip : perechenTpmpkList) {
                    SprIshType type = sprIshTypeFacade.findByName("req_tpmpk");
                    List<IshCorr> ishCorr = ishCorrFacade.findByTypeNoNomer(type, ip.getIpraperechennId().getIpra18Id());
                    for (IshCorr ic : ishCorr) {
                        sb.append("<ipra>");
                        sb.append("<id>").append(ip.getIpraperechentpmpkId()).append("</id>");
                        sb.append("<check>").append("check").append(ip.getIpraperechennId().getIpra18Id().getIpra18Id()).append("</check>");
                        sb.append("<fam>").append(ip.getIpraperechennId().getIpra18Id().getChildId().getChildFam()).append("</fam>");
                        sb.append("<name>").append(ip.getIpraperechennId().getIpra18Id().getChildId().getChildName()).append("</name>");
                        String p = " ";
                        if ((ip.getIpraperechennId().getIpra18Id().getChildId().getChildPatr() != null)
                                && (!ip.getIpraperechennId().getIpra18Id().getChildId().getChildPatr().equals(""))) {
                            p = ip.getIpraperechennId().getIpra18Id().getChildId().getChildPatr();
                        }
                        sb.append("<patr>").append(p).append("</patr>");
                        Date dr = ip.getIpraperechennId().getIpra18Id().getChildId().getChildDr();
                        sb.append("<dr>").append(regularDateFormat.format(dr)).append("</dr>");
                        sb.append("<reg>").append(ip.getIpraperechennId().getIpra18Id().getChildId().getSprregId().getSprregName()).append("</reg>");
                        sb.append("<tpmpk>").append(ip.getSprotherpmpkId().getSprotherpmpkShname()).append("</tpmpk>");
                        Date reqDate = ic.getIshcorrD();
                        String reqDateS = " ";
                        try {
                            reqDateS = regularDateFormat.format(reqDate);
                        } catch (Exception ex) {
                        }
                        sb.append("<datereq>").append(reqDateS).append("</datereq>");
                        sb.append("</ipra>");
                    }
                }
            }
            sb.append("</ipratpmpk>");
            itsOk = true;
            // если всё нормально - передаём клиенту
            if (itsOk) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("upcomingotchet")) {    // список ИПРА с ближайшими сроками отчётов (в течение месяца)
            List<Ipra18N> ipraList = ipra18NFacade.findUpcomingOthcet();
            // составляем xml для передачи клиенту
            // заголовок таблицы
            sb.append("<ipraotchet>");
            sb.append("<ipra>");
            sb.append("<id>").append("ИД").append("</id>");
            sb.append("<fam>").append("Фамилия").append("</fam>");
            sb.append("<name>").append("Имя").append("</name>");
            sb.append("<patr>").append("Отчество").append("</patr>");
            sb.append("<dr>").append("Дата рождения").append("</dr>");
            sb.append("<reg>").append("Район").append("</reg>");
            sb.append("<otchomsu>").append("Дата отчёта ОМСУ").append("</otchomsu>");
            sb.append("<otchcenter>").append("Дата отчёта ОЦППМСП").append("</otchcenter>");
            sb.append("<otchdo>").append("Дата отчёта ДО").append("</otchdo>");
            sb.append("</ipra>");
            // содержимое таблицы
            if (!ipraList.isEmpty()) {
                for (Ipra18N ip : ipraList) {
                    sb.append("<ipra>");
                    sb.append("<id>").append(ip.getIpra18Id()).append("</id>");
                    sb.append("<fam>").append(ip.getChildId().getChildFam()).append("</fam>");
                    sb.append("<name>").append(ip.getChildId().getChildName()).append("</name>");
                    String p = " ";
                    if ((ip.getChildId().getChildPatr() != null)
                            && (!ip.getChildId().getChildPatr().equals(""))) {
                        p = ip.getChildId().getChildPatr();
                    }
                    sb.append("<patr>").append(p).append("</patr>");
                    Date dr = ip.getChildId().getChildDr();
                    sb.append("<dr>").append(regularDateFormat.format(dr)).append("</dr>");
                    sb.append("<reg>").append(ip.getChildId().getSprregId().getSprregName()).append("</reg>");
                    Date otchomsuDate = ip.getIpra18Otchomsu();
                    String otchomsuDateS = " ";
                    try {
                        otchomsuDateS = regularDateFormat.format(otchomsuDate);
                    } catch (Exception ex) {
                    }
                    sb.append("<otchomsu>").append(otchomsuDateS).append("</otchomsu>");
                    Date otchcenterDate = ip.getIpra18Otchcenter();
                    String otchcenterDateS = " ";
                    try {
                        otchcenterDateS = regularDateFormat.format(otchcenterDate);
                    } catch (Exception ex) {
                    }
                    sb.append("<otchcenter>").append(otchcenterDateS).append("</otchcenter>");
                    Date otchdoDate = ip.getIpra18Otchdo();
                    String otchdoDateS = " ";
                    try {
                        otchdoDateS = regularDateFormat.format(otchdoDate);
                    } catch (Exception ex) {
                    }
                    sb.append("<otchdo>").append(otchdoDateS).append("</otchdo>");
                    sb.append("</ipra>");
                }
            }
            sb.append("</ipraotchet>");
            itsOk = true;
            // если всё нормально - передаём клиенту
            if (itsOk) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("add")) {
            // удаляем аттрибут со списком МСЭ (для его замены)
            try {
                session.removeAttribute("mseList");
            } catch (Exception ex) {
            }
            // список МСЭ
            List<SprMse> mseList = sprMseFacade.findAllSprMse();
            // подставляем список районов в соответствующий атрибут сессии
            session.setAttribute("mseList", mseList);
            // формируем адрес страницы
            userPath = "/ipra/nipra18add";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            // передаём адрес страницы клиенту
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
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
        } else if (action.equals("open")) { // открыть "карточку" ИПРА
            String idS = request.getParameter("id");    // ИД ИПРА
            Integer id = 0;
            try {
                id = Integer.parseInt(idS);
            } catch (Exception ex) {
            }
            Ipra18N ipra = null;
            if (id != 0) {  // ищем ИПРА по ИД
                try {
                    ipra = ipra18NFacade.findById(id);
                } catch (Exception ex) {
                }
            }
            if (ipra != null) { // если нашли
                try {
                    session.removeAttribute("ipra");    // очищаем атрибут сессии ИПРА
                } catch (Exception ex) {
                }
                // устанавливает аттрибут сессии - ИПРА для отображении информации на jsp
                session.setAttribute("ipra", ipra);

                // удаляем аттрибут со списком МСЭ (для его замены)
                try {
                    session.removeAttribute("mseList");
                } catch (Exception ex) {
                }
                // список МСЭ
                List<SprMse> mseList = sprMseFacade.findAllSprMse();
                // подставляем список МСЭ в соответствующий атрибут сессии
                session.setAttribute("mseList", mseList);

                // проверяем, нет ли отказа от реализации ИПРА
                // отказ от приказа
                List<IpraOmsu> ipraOmsuDis = ipraOmsuFacade.findDisByIpra(ipra);
                try {
                    session.removeAttribute("ipraOmsuDis");
                } catch (Exception ex) {
                }
                session.setAttribute("ipraOmsuDis", ipraOmsuDis);

                // ищем корреспонденцию для данной ИПРА
                List<IshCorr> ishCorrList = ishCorrFacade.findByIpra(ipra);
                List<VhCorr> vhCorrList = vhCorrFacade.findByIpra(ipra);
                // создаём списки корреспонденции в специальной структуре                
                List<Correspond> ishCorr = new ArrayList<>();   // исходящие
                for (IshCorr ic : ishCorrList) {
                    String recepient = sprCorrFacade.findName(ic.getSprcorrId());
                    Correspond corr = new Correspond(ic.getIshcorrId(), ic.getIshcorrD(),
                            ic.getIshcorrN(), recepient, ic.getSprishtypeId().getSprishtypeRuName());
                    ishCorr.add(corr);
                }
                List<Correspond> vhCorr = new ArrayList<>();   // входящие
                for (VhCorr vc : vhCorrList) {
                    String sender = sprCorrFacade.findName(vc.getSprcorrId());
                    Correspond corr = new Correspond(vc.getVhcorrId(), vc.getVhcorrD(), vc.getVhcorrN(),
                            sender, vc.getSprvhtypeId().getSprvhtypeRuName(), vc.getVhcorrIshd(), vc.getVhcorrIshn());
                    vhCorr.add(corr);
                }
                // сортируем
                Collections.sort(ishCorr, new CorrespondComparator());
                Collections.sort(vhCorr, new CorrespondComparator());
                // очищаем атрибуты сессии для корреспонденции
                try {
                    session.removeAttribute("ishCorr");
                } catch (Exception ex) {
                }
                try {
                    session.removeAttribute("vhCorr");
                } catch (Exception ex) {
                }
                // устанавливаем атрибуты сессии заново
                session.setAttribute("ishCorr", ishCorr);
                session.setAttribute("vhCorr", vhCorr);

                // проверяем, есть ли приказ (или "заготовка" под него - появляется при внесении запроса на приказ)
                Ipra18PrikazN prikaz = null;
                try {
                    prikaz = ipra18PrikazNFacade.findByIpra(ipra);
                } catch (Exception ex) {
                }
                // очищаем атрибуты сессии для приказа
                try {
                    session.removeAttribute("prikaz");
                } catch (Exception ex) {
                }
                // устанавливаем атрибут сессии 
                session.setAttribute("prikaz", prikaz);
                // проверяем, есть ли перечни мероприятий или "заготовка"
                List<IpraPerechenN> perechenList = ipraPerechenNFacade.findByIpra18(ipra);
                // очищаем атрибуты сессии для перечней
                try {
                    session.removeAttribute("perechenList");
                } catch (Exception ex) {
                }
                // устанавливаем атрибут сессии 
                session.setAttribute("perechenList", perechenList);
                // типы мероприятий
                List<IpraeducondType> types = ipraeducondTypeFacade.findAll();
                try {
                    session.removeAttribute("types");
                } catch (Exception ex) {
                }
                session.setAttribute("types", types);

                // ищем условия обучения (для всех перечней)
                List<IpraEduConditionN> conditions = new ArrayList<>();
                for (IpraPerechenN perechen : perechenList) {
                    conditions.addAll(ipraEduConditionNFacade.findByIpraPerechen(perechen));
                }
                try {
                    session.removeAttribute("conditions");
                } catch (Exception ex) {
                }
                session.setAttribute("conditions", conditions);

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

                // формируем адрес страницы
                userPath = "/ipra/nipra18view";
                url = "/WEB-INF/pages" + userPath + ".jsp";
                // передаём адрес страницы клиенту
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (action.equals("formreqtotpmpk")) {   // формирование запросов к ТПМПК
            String idVS = request.getParameter("id");
            String[] idV = idVS.split(";");
            List<IpraPerechenTpmpk> perechenTpmpkList = new ArrayList<>();
            for (String id : idV) {
                try {
                    IpraPerechenTpmpk perechenTpmpk = ipraPerechenTpmpkFacade.findById(Integer.parseInt(id));
                    perechenTpmpkList.add(perechenTpmpk);
                } catch (Exception ex) {
                }
            }
            List<IpraTpmpkRequestNew> requests = new ArrayList<>();
            for (IpraPerechenTpmpk pt : perechenTpmpkList) {
                boolean flag = false;
                for (IpraTpmpkRequestNew req : requests) {
                    if (req.getSprOtherPmpk().equals(pt.getSprotherpmpkId())) {
                        if (req.getReqDate().equals(pt.getIpraperechennId().getIshcorrId().getIshcorrD())) {
                            flag = true;
                            List<IpraPerechenTpmpk> newIpraPerechenTpmpkList = req.getIpraPerechenTpmpkList();
                            newIpraPerechenTpmpkList.add(pt);
                            req.setIpraPerechenTpmpkList(newIpraPerechenTpmpkList);
                        }
                    }

                }
                if (!flag) {
                    IpraTpmpkRequestNew newReq = new IpraTpmpkRequestNew();
                    newReq.setSprOtherPmpk(pt.getSprotherpmpkId());
                    newReq.setReqDate(pt.getIpraperechennId().getIshcorrId().getIshcorrD());
                    List<IpraPerechenTpmpk> newIpraPerechenTpmpkList = new ArrayList<>();
                    newIpraPerechenTpmpkList.add(pt);
                    newReq.setIpraPerechenTpmpkList(newIpraPerechenTpmpkList);
                    requests.add(newReq);
                }
            }
            if (!requests.isEmpty()) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                sb.append("<tpmpkreqs>");
                int i = 0;
                for (IpraTpmpkRequestNew req : requests) {
                    String formatDate = format.format(req.getReqDate());
                    sb.append("<tpmpkreq>");
                    i++;
                    sb.append("<n>").append(i).append("</n>");
                    List<IpraPerechenTpmpk> ipraPerechenTpmpkList = req.getIpraPerechenTpmpkList();
                    String iptids = "";
                    for (IpraPerechenTpmpk it : ipraPerechenTpmpkList) {
                        iptids += it.getIpraperechentpmpkId() + ";";
                    }
                    sb.append("<iptids>").append(iptids).append("</iptids>");
                    sb.append("<tpmpkname>").append(req.getSprOtherPmpk().getSprotherpmpkShname()).append("</tpmpkname>");
                    sb.append("<reqdate>").append(formatDate).append("</reqdate>");
                    sb.append("<childrencount>").append(req.getIpraPerechenTpmpkList().size()).append("</childrencount>");
                    sb.append("</tpmpkreq>");
                }
                sb.append("</tpmpkreqs>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("findomsubyregion")) { // найти ОМСУ по названию района
            String regName = request.getParameter("name");
            List<SprRegion> regionList = new ArrayList<>();
            try {
                regionList = sprRegionFacade.findByName(regName);
            } catch (Exception ex) {
            }
            if (!regionList.isEmpty()) {
                SprRegion reg = regionList.get(0);
                List<SprOmsu> omsuByReg = sprOmsuFacade.findByRegion(reg);    // ищем ОМСУ по району
                /*List<SprOmsu> allOmsu = sprOmsuFacade.findAllSprOmsu(); // весь список для вывода в select у клиента
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
                        if (flag) { // устанавливаем признак того, что выбран именно этот пункт
                            sb.append("<selected>").append(1).append("</selected>");
                        } else {
                            sb.append("<selected>").append(0).append("</selected>");
                        }
                        sb.append("</omsu>");
                    }*/
                if (!omsuByReg.isEmpty()) {
                    sb.append("<selectedomsu>").append(omsuByReg.get(0).getSpromsuId()).append("</selectedomsu>");
                    response.setContentType("text/xml; charset=windows-1251");
                    response.setHeader("Cache-Control", "no-cache");
                    response.getWriter().write(sb.toString());
                } else {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            }
        } else if (action.equals("findprikazdate")) {
            String ipraIdS = request.getParameter("ipra");
            Integer ipraId = 0;
            try {
                ipraId = Integer.parseInt(ipraIdS);
            } catch (Exception ex) {
            }
            Ipra18N ipra = null;
            if (ipraId != 0) {
                ipra = ipra18NFacade.findById(ipraId);
            }
            if (ipra != null) {
                sb.append("<prikaz>");
                try {
                    Ipra18PrikazN prikaz = ipra18PrikazNFacade.findByIpra(ipra);
                    if ((prikaz.getIpra18prikazN() == null) || (prikaz.getIpra18prikazN().equals(""))) {
                        sb.append("<prikazdate>").append(prikaz.getFormatDate()).append("</prikazdate>");
                        sb.append("<prikazreason>").append("-").append("</prikazreason>");
                        sb.append("<n>").append(ipraIdS).append("</n>");
                    } else {
                        sb.append("<prikazdate>").append("-").append("</prikazdate>");
                        sb.append("<prikazreason>").append("Номер приказа уже внесён").append("</prikazreason>");
                        sb.append("<n>").append(ipraIdS).append("</n>");
                    }
                } catch (Exception ex) {
                    sb.append("<prikazdate>").append("-").append("</prikazdate>");
                    sb.append("<prikazreason>").append("Не было запроса на приказ").append("</prikazreason>");
                    sb.append("<n>").append(ipraIdS).append("</n>");
                }
                sb.append("</prikaz>");
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
        String sessId = session.getId();
        try {
            List<LoginLog> logs = loginLogFacade.findLog(user, sessId);
            LoginLog log = logs.get(0);
            Date lastAct = new Date(session.getLastAccessedTime());
            log.setLoginlogLastact(lastAct);
            loginLogFacade.edit(log);
        } catch (Exception ex) {
        }

        request.setCharacterEncoding("UTF-8");  // устанавливаем кодировку запроса

        long curTime = System.currentTimeMillis();  // текущая дата
        Date curDate = new Date(curTime);
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");  // формат даты

        StringBuilder sb = new StringBuilder(); // для составления xml для передачи клиенту

        // карта всех параметров передаваемых клиентом
        Map<String, String[]> params = request.getParameterMap();

        // запрашиваемое действие
        String action = params.get("action")[0];
        if (action.equals("save")) {     // сохранение ИПРА
            Ipra18N ipra = null;
            String ipraIdS = null;
            try {
                ipraIdS = params.get("ipraId")[0];     // ИД ИПРА, если она уже существовала
            } catch (Exception ex) {
            }
            if (ipraIdS != null) {  // пытаемся найти ИПРА по ИД
                try {
                    ipra = ipra18NFacade.findById(Integer.parseInt(ipraIdS));
                } catch (Exception ex) {
                }
            }

            boolean isNew = false;  // признак новая ли ИПРА
            if (ipra == null) {
                ipra = new Ipra18N();
                isNew = true;
            }

            // ребёнок
            String childIdS = null;
            try {
                childIdS = params.get("childId")[0];    // ИД ребёнка, если он уже был в БД
            } catch (Exception ex) {
            }

            Children child = null;
            if (childIdS != null) { // ищем ребёнка по ИД
                Integer childId = 0;
                try {
                    childId = Integer.parseInt(childIdS);
                } catch (Exception ex) {

                }
                if (childId != 0) {
                    child = childrenFacade.findById(childId);
                }
            } else {    // или создаём нового
                String childFam = params.get("childFam")[0];
                String childName = params.get("childName")[0];
                String childPatr = params.get("childPatr")[0];
                String childDrS = params.get("childDr")[0];
                String childPol = params.get("pol")[0];
                Date childDr = null;
                try {   // парсинг даты от клиента
                    childDr = format.parse(childDrS);
                } catch (ParseException ex) {
                    Logger.getLogger(SaveIpraServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                String regIdS = params.get("regId")[0]; // ищем район по ИД
                Integer regId = 0;
                try {
                    regId = Integer.parseInt(regIdS);
                } catch (Exception ex) {
                }
                SprRegion reg = null;
                if (regId != 0) {
                    reg = sprRegionFacade.findById(regId);
                }

                Nom nom = new Nom();    // создаём новый порядковый номер
                nomFacade.create(nom);

                child = new Children(); // создаём нового ребёнка
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
            // привязываем ребёнка к ИПРА
            ipra.setChildId(child);

            // номер ИПРА
            String ipraN = params.get("ipraN")[0];
            ipra.setIpra18N(ipraN);
            // номер экспертизы
            String ipraNexp = params.get("ipraNexp")[0];
            ipra.setIpra18Nexp(ipraNexp);
            // дата экспертизы
            String ipraDateexpS = params.get("ipraDateExp")[0];
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
            Date oldDateOk = null;
            if (ipraDateok != null) {
                if (!ipraDateok.equals(ipra.getIpra18Dateok())) {
                    oldDateOk = ipra.getIpra18Dateok();
                    ipra.setIpra18Dateok(ipraDateok);
                }
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

            // дата входящего письма в ДО из МСЭ
            String ipraVhToDODS = null;
            try {
                ipraVhToDODS = params.get("ipraVhToDOD")[0];
            } catch (Exception ex) {
            }
            Date ipraVhToDOD = null;
            if (ipraVhToDODS != null) {
                try {
                    ipraVhToDOD = format.parse(ipraVhToDODS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            ipra.setIpra18VhtodoD(ipraVhToDOD);

            // номер входящего письма в ДО из МСЭ
            String ipraVhToDON = null;
            try {
                ipraVhToDON = params.get("ipraVhToDON")[0];
            } catch (Exception ex) {
            }
            ipra.setIpra18VhtodoN(ipraVhToDON);

            // дата отчёта ОМСУ
            String otchOmsuS = params.get("ipraOtchomsu")[0];
            Date otchOmsu = null;
            try {
                otchOmsu = format.parse(otchOmsuS);
            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (otchOmsu != null) {
                ipra.setIpra18Otchomsu(otchOmsu);
            }
            // дата отчёта ОЦППМСП
            String otchCenterS = params.get("ipraOtchcenter")[0];
            Date otchCenter = null;
            try {
                otchCenter = format.parse(otchCenterS);

            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (otchCenter != null) {
                ipra.setIpra18Otchcenter(otchCenter);
            }

            // дата отчёта ДО
            String otchDoS = params.get("ipraOtchdo")[0];
            Date otchDo = null;
            try {
                otchDo = format.parse(otchDoS);
            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (otchDo != null) {
                ipra.setIpra18Otchdo(otchDo);
            }

            // дата приказа ДО
            String prikazDS = null;
            try {
                prikazDS = params.get("prikazD")[0];
            } catch (Exception ex) {
            }
            Date prikazD = null;
            if (prikazDS != null) {
                try {
                    prikazD = format.parse(prikazDS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // номер приказа ДО
            String prikazN = null;
            try {
                prikazN = params.get("prikazN")[0];
            } catch (Exception ex) {
            }
            // пробуем найти приказ
            Ipra18PrikazN prikaz = null;
            try {
                prikaz = ipra18PrikazNFacade.findByIpra(ipra);
            } catch (Exception ex) {
            }
            if (prikaz != null) {    // если он существует
                if (prikazD != null) {   // дата от клиента пришла корректная
                    prikaz.setIpra18prikazD(prikazD);
                }
                if (prikazN != null) {   // номер пришёл от клиента
                    prikaz.setIpra18prikazN(prikazN);
                }
                ipra18PrikazNFacade.edit(prikaz);
            }

            // перечни мероприятий
            List<String> perechenIdS = new ArrayList<>();   // список ИД перечней
            // ищем в переданных клиентом параметров ИД перечней
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                String par = entry.getKey();
                String[] val = entry.getValue();
                if (par.startsWith("perechenId_")) {
                    perechenIdS.add(val[0]);
                }
            }
            for (String perechenId : perechenIdS) {
                IpraPerechenN perechen = null;
                try {   // ищем перечень по ИД
                    perechen = ipraPerechenNFacade.findById(Integer.parseInt(perechenId));
                } catch (Exception ex) {
                }
                if (perechen != null) {
                    // проставляем дату изменения и пользователя
                    perechen.setDateUpd(curDate);
                    perechen.setUserId(user);
                    List<String> condList = new ArrayList<>();
                    List<String> datecondList = new ArrayList<>();
                    for (Map.Entry<String, String[]> entry : params.entrySet()) {
                        String par = entry.getKey();
                        String[] val = entry.getValue();
                        if (par.startsWith("cond") && (par.endsWith("_" + perechenId))) {
                            condList.add(par);
                        } else if (par.startsWith("datecond") && (par.endsWith("_" + perechenId))) {
                            datecondList.add(par);
                        }
                    }
                    // предварительно удалить все условия обучения и мероприятия
                    List<IpraEduConditionN> conditionList = ipraEduConditionNFacade.findByIpraPerechen(perechen);
                    for (IpraEduConditionN cond : conditionList) {
                        ipraEduConditionNFacade.remove(cond);
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
                                            Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        if (date != null) {
                                            String typeIdS = cond.substring(4, cond.indexOf("_"));
                                            IpraeducondType ipraeducondType = null;
                                            try {
                                                ipraeducondType = ipraeducondTypeFacade.findById(Integer.parseInt(typeIdS));
                                            } catch (Exception ex) {
                                            }
                                            if (ipraeducondType != null) {
                                                IpraEduConditionN ipraEduCondition = new IpraEduConditionN();
                                                ipraEduCondition.setIpraeducondContext(condVal);
                                                ipraEduCondition.setIpraeducondDate(date);
                                                ipraEduCondition.setIpraeducondtypeId(ipraeducondType);
                                                ipraEduCondition.setIpraperechennId(perechen);
                                                ipraEduConditionNFacade.create(ipraEduCondition);
                                                ipraPerechenNFacade.edit(perechen);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // статус (1 - в работе, 0 - архив)
            String statusS = "";
            Integer status = 1;
            try {
                statusS = params.get("status")[0];
            } catch (Exception ex) {
            }
            if (statusS != null) {
                try {
                    status = Integer.parseInt(statusS);
                } catch (Exception ex) {
                }
            }
            ipra.setIpra18Status(status);

            // привязываем пользователя и дату обновления информации
            ipra.setUserId(user);
            ipra.setDateUpd(curDate);

            try {
                // если ИПРА новая - вставляем в БД, если нет - обновляем
                if (isNew) {
                    ipra18NFacade.create(ipra);
                } else {
                    ipra18NFacade.edit(ipra);
                }

                // дата входящего письма в ЦППМСП из ДО
                String ipraVhToCenterDS = null;
                try {
                    ipraVhToCenterDS = params.get("ipraVhToCenterD")[0];
                } catch (Exception ex) {
                }
                Date ipraVhToCenterD = null;
                try {
                    ipraVhToCenterD = format.parse(ipraVhToCenterDS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                // исходящий номер входящего письма в ЦППМСП из ДО
                String ipraToCenterIshN = null;
                try {
                    ipraToCenterIshN = params.get("ipraToCenterIshN")[0];
                } catch (Exception ex) {
                }
                // исходящая дата входящего письма в ЦППМСП из ДО
                String ipraToCenterIshDS = null;
                try {
                    ipraToCenterIshDS = params.get("ipraToCenterIshD")[0];
                } catch (Exception ex) {
                }
                Date ipraToCenterIshD = null;
                try {
                    ipraToCenterIshD = format.parse(ipraToCenterIshDS);
                } catch (ParseException ex) {
                    Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (ipraVhToCenterD != null) {
                    // создаём новое входящее письмо
                    VhCorr vh = new VhCorr();
                    // регистрируем новый номер входящего письма в ЦППМСП из ДО
                    // получаем номер (синглетон нужен для синхрониронизированного выполнения запроса)
                    MySingleton mySingleton = MySingleton.getInstance();
                    String nomer = mySingleton.getNextNomer(ipraVhnomFacade);
                    SprCorr corr = sprCorrFacade.findByName("Департамент образования");  // корреспондент
                    vh.setSprcorrId(corr);
                    SprVhType type = sprVhTypeFacade.findByName("ipra");    // тип письма - ИПРА
                    vh.setSprvhtypeId(type);
                    // даты и номера
                    vh.setVhcorrD(ipraVhToCenterD);
                    vh.setVhcorrN(nomer);
                    vh.setVhcorrIshd(ipraToCenterIshD);
                    vh.setVhcorrIshn(ipraToCenterIshN);
                    vh.setUserId(user);
                    vh.setDateUpd(curDate);
                    vhCorrFacade.create(vh);
                    // привязка к ИПРА
                    IpraVhcorr ipraVh = new IpraVhcorr();
                    ipraVh.setIpra18Id(ipra);
                    ipraVh.setVhcorrId(vh);
                    ipraVhcorrFacade.create(ipraVh);
                }

                List<ChildStatus> chstList = childStatusFacade.findByChildAct(child);
                if (isNew) {
                    // проставляем статус ДИ и ТР детям                    
                    Boolean isDI = false;
                    Boolean isTR = false;
                    // проверяем, есть ли среди действующих статусов ДИ и ТР
                    for (ChildStatus chst : chstList) {
                        if ((chst.getSprstatId().getSprstatInv() == 1)
                                && (chst.getChildstatusDateK() == null)) { // есть ДИ без даты окончания
                            isDI = true;
                            chst.setChildstatusDateK(ipra.getIpra18Dateok());   // устанавливаем срок в соответствии с ИПРА
                            childStatusFacade.edit(chst);
                        } else if (chst.getSprstatId().equals(sprStatFacade.findById(2))) { // есть ТР
                            isTR = true;
                        }
                    }
                    if (!isDI) {    // если нет ДИ, вставляем со сроками в соответствии с ИПРА
                        ChildStatus childStatus = new ChildStatus();
                        childStatus.setChildId(child);
                        childStatus.setSprstatId(sprStatFacade.findById(6));
                        childStatus.setChildstatusDateN(ipra.getIpra18Dateexp());
                        childStatus.setChildstatusDateK(ipra.getIpra18Dateok());
                        childStatusFacade.create(childStatus);
                    }
                    if (!isTR) {    // если нет ТР, вставляем в таблицу-связку ребёнок-статус
                        ChildStatus childStatus = new ChildStatus();
                        childStatus.setChildId(child);
                        childStatus.setSprstatId(sprStatFacade.findById(2));
                        childStatus.setChildstatusDateN(ipra.getIpra18Dateexp());
                        childStatusFacade.create(childStatus);
                    }
                } else if (oldDateOk != null) {  // если поменялся срок ИПРА - меняем дату окончания соответствующего статуса ДИ
                    for (ChildStatus chst : chstList) {
                        if ((chst.getSprstatId().getSprstatInv() == 1)
                                && (chst.getChildstatusDateK().equals(oldDateOk))) {
                            chst.setChildstatusDateK(ipra.getIpra18Dateok());   // устанавливаем новый срок в соответствии с ИПРА
                            childStatusFacade.edit(chst);
                        }
                    }
                }
                sb.append("<result>").append(ipra.getIpra18Id()).append("</result>");   // если всё хорошо передаём ИД ИПРА
            } catch (Exception ex) {
                sb.append("<result>").append("0").append("</result>");  // если что-то не так
            }

            if (sb.length() > 0) {  // если удалось составить xml - передаём клиенту
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {    // если нет - передаём статус "нет контента"
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("savereqdo")) { // сохранение запроса из ОМСУ в ДО
            String req = params.get("req")[0]; // признак запрос или отказ
            String omsuIdS = params.get("omsu")[0]; // ИД ОМСУ
            Integer omsuId = 0;
            try {
                omsuId = Integer.parseInt(omsuIdS);
            } catch (Exception ex) {
            }
            SprOmsu omsu = null;
            if (omsuId != 0) {
                omsu = sprOmsuFacade.findById(omsuId);
            }
            String ishn = params.get("ishn")[0];    // исходящий номер
            String ishdS = params.get("ishd")[0];   // дата исходящего
            Date ishd = null;
            try {
                ishd = format.parse(ishdS);
            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String vhn = params.get("vhn")[0];  // входящий номер
            String vhdS = params.get("vhd")[0];  // дата входящего
            Date vhd = null;
            try {
                vhd = format.parse(vhdS);
            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ipraids = params.get("ipraids")[0];  // ИД ИПРА
            String[] ipraidV = ipraids.split(";");
            List<Ipra18N> ipraList = new ArrayList<>(); // составляем список ИПРА для привязки к письму
            for (String ipraId : ipraidV) {
                try {
                    ipraList.add(ipra18NFacade.findById(Integer.parseInt(ipraId)));
                } catch (Exception ex) {
                }
            }

            // формируем запрос к ДО от ОМСУ, заготовку под приказ и сохраняем всё в БД            
            try {
                for (Ipra18N ipra : ipraList) {
                    // запрос
                    IpraOmsu ipraOmsu = new IpraOmsu();
                    ipraOmsu.setSpromsuId(omsu);
                    ipraOmsu.setIpraomsuIshN(ishn);
                    ipraOmsu.setIpraomsuIshD(ishd);
                    ipraOmsu.setIpraomsuVhN(vhn);
                    ipraOmsu.setIpraomsuVhD(vhd);
                    ipraOmsu.setIpra18Id(ipra);
                    if (req.equals("req")) {
                        ipraOmsu.setIpraomsuReq(1); // признак того, что это запрос на приказ
                    } else if (req.equals("dis")) {
                        ipraOmsu.setIpraomsuReq(0); // признак того, что это отказ
                    }
                    ipraOmsu.setDateUpd(curDate);
                    ipraOmsu.setUserId(user);
                    ipraOmsuFacade.create(ipraOmsu);
                    if (req.equals("req")) {    // если пришёл запрос
                        // приказ
                        Ipra18PrikazN prikaz = new Ipra18PrikazN();
                        prikaz.setIpra18Id(ipra);
                        prikaz.setIpra18prikazD(countDate(3, "rab", "forward", vhd));  // дата вычисляется
                        prikaz.setIpraomsuId(ipraOmsu);
                        ipra18PrikazNFacade.create(prikaz);
                    }
                }
                // xml с результатом
                sb.append("<result>");
                sb.append("<action>").append(action).append("</action>");
                sb.append("<value>").append(1).append("</value>");
                sb.append("</result>");
            } catch (Exception ex) {
                // xml с результатом
                sb.append("<result>");
                sb.append("<action>").append(action).append("</action>");
                sb.append("<value>").append(0).append("</value>");
                sb.append("</result>");
            }

            if (sb.length() > 0) {  // если удалось составить xml - передаём клиенту
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {    // если нет - передаём статус "нет контента"
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }

        } else if (action.equals("savereqcenter")) {    // сохранение запроса из ОМСУ в ОЦППМСП
            String req = params.get("req")[0]; // признак запрос или отказ
            String omsuIdS = params.get("omsu")[0]; // ИД ОМСУ
            Integer omsuId = 0;
            try {
                omsuId = Integer.parseInt(omsuIdS);
            } catch (Exception ex) {
            }
            SprOmsu omsu = null;
            if (omsuId != 0) {
                omsu = sprOmsuFacade.findById(omsuId);
            }
            String ishn = params.get("ishn")[0];    // исходящий номер
            String ishdS = params.get("ishd")[0];   // дата исходящего
            Date ishd = null;
            try {
                ishd = format.parse(ishdS);
            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            String vhdS = params.get("vhd")[0];  // дата входящего
            Date vhd = null;
            try {
                vhd = format.parse(vhdS);
            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ipraids = params.get("ipraids")[0];  // ИД ИПРА
            String[] ipraidV = ipraids.split(";");
            List<Ipra18N> ipraList = new ArrayList<>(); // составляем список ИПРА для привязки к письму
            for (String ipraId : ipraidV) {
                try {
                    ipraList.add(ipra18NFacade.findById(Integer.parseInt(ipraId)));
                } catch (Exception ex) {
                }
            }

            try {
                // входящее письмо
                VhCorr vhCorr = new VhCorr();
                SprCorr corr = sprCorrFacade.findByTableAndId("SPR_OMSU", omsu.getSpromsuId());
                vhCorr.setSprcorrId(corr);
                if (req.equals("req")) {
                    vhCorr.setSprvhtypeId(sprVhTypeFacade.findByName("req_perechen"));
                } else if (req.equals("dis")) {
                    vhCorr.setSprvhtypeId(sprVhTypeFacade.findByName("dis_perechen"));
                }
                vhCorr.setVhcorrD(vhd);
                vhCorr.setVhcorrIshd(ishd);
                vhCorr.setVhcorrIshn(ishn);
                // регистрация входящего номера
                // получаем номер (синглетон нужен для синхрониронизированного выполнения запроса)
                MySingleton mySingleton = MySingleton.getInstance();
                String nomer = mySingleton.getNextNomer(ipraVhnomFacade);
                vhCorr.setVhcorrN(nomer);
                vhCorr.setUserId(user);
                vhCorr.setDateUpd(curDate);
                vhCorrFacade.create(vhCorr);
                // для каждой ИПРА из списка к письму
                for (Ipra18N ipra : ipraList) {
                    // привязка письма к ИПРА    
                    IpraVhcorr ipraVhcorr = new IpraVhcorr();
                    ipraVhcorr.setIpra18Id(ipra);
                    ipraVhcorr.setVhcorrId(vhCorr);
                    ipraVhcorrFacade.create(ipraVhcorr);
                    if (req.equals("req")) {
                        // проверяем, наш ли район
                        if (ipra.getChildId().getSprregId().getSprregOth() == 1) {
                            // если не наш, определяем ТПМПК и составляем заготовку запроса в ТПМПК
                            OtherpmpkRegion otherpmpkReg = otherpmpkRegionFacade.findByRegion(ipra.getChildId().getSprregId());
                            SprCorr tpmpk = sprCorrFacade.findByTableAndId("SPR_OTHER_PMPK", otherpmpkReg.getSprotherpmpkId().getSprotherpmpkId());
                            // исходящее письмо в ТПМПК
                            IshCorr ishCorr = new IshCorr();
                            ishCorr.setIshcorrD(countDate(3, "rab", "forward", vhd));
                            ishCorr.setSprcorrId(tpmpk);
                            ishCorr.setSprishtypeId(sprIshTypeFacade.findByName("req_tpmpk"));
                            ishCorr.setUserId(user);
                            ishCorr.setDateUpd(curDate);
                            ishCorrFacade.create(ishCorr);
                            // привязка исходящего к ИПРА
                            IpraIshcorr ipraIshcorr = new IpraIshcorr();
                            ipraIshcorr.setIpra18Id(ipra);
                            ipraIshcorr.setIshcorrId(ishCorr);
                            ipraIshcorrFacade.create(ipraIshcorr);
                        }
                        // исходящее в ОМСУ письмо с перечнем (заготовка)
                        IshCorr ishCorr = new IshCorr();
                        ishCorr.setIshcorrD(countDate(15, "rab", "forward", vhd));
                        ishCorr.setSprcorrId(corr);
                        ishCorr.setSprishtypeId(sprIshTypeFacade.findByName("perechen"));
                        ishCorr.setUserId(user);
                        ishCorr.setDateUpd(curDate);
                        ishCorrFacade.create(ishCorr);
                        // привязка исходящего к ИПРА
                        IpraIshcorr ipraIshcorr = new IpraIshcorr();
                        ipraIshcorr.setIpra18Id(ipra);
                        ipraIshcorr.setIshcorrId(ishCorr);
                        ipraIshcorrFacade.create(ipraIshcorr);
                        // перечень мероприятий
                        IpraPerechenN perechen = new IpraPerechenN();
                        perechen.setIpra18Id(ipra);
                        perechen.setIshcorrId(ishCorr);
                        perechen.setVhcorrId(vhCorr);
                        perechen.setUserId(user);
                        perechen.setDateUpd(curDate);
                        ipraPerechenNFacade.create(perechen);
                    }
                }
                // xml с результатом
                sb.append("<result>");
                sb.append("<action>").append(action).append("</action>");
                sb.append("<value>").append(1).append("</value>");
                sb.append("</result>");
            } catch (Exception ex) {
                // xml с результатом
                sb.append("<result>");
                sb.append("<action>").append(action).append("</action>");
                sb.append("<value>").append(0).append("</value>");
                sb.append("</result>");
            }
            if (sb.length() > 0) {  // если удалось составить xml - передаём клиенту
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {    // если нет - передаём статус "нет контента"
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("saveprikaz")){
            String ishn = params.get("ishn")[0];    // исходящий номер
            String ishdS = params.get("ishd")[0];   // дата исходящего
            Date ishd = null;
            try {
                ishd = format.parse(ishdS);
            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            String vhdS = params.get("vhd")[0];  // дата входящего
            Date vhd = null;
            try {
                vhd = format.parse(vhdS);
            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ipraids = params.get("ipraids")[0];  // ИД ИПРА
            String[] ipraidV = ipraids.split(";");
            List<Ipra18N> ipraList = new ArrayList<>(); // составляем список ИПРА для привязки к письму
            for (String ipraId : ipraidV) {
                try {
                    ipraList.add(ipra18NFacade.findById(Integer.parseInt(ipraId)));
                } catch (Exception ex) {
                }
            }

            try {
                // входящее письмо
                VhCorr vhCorr = new VhCorr();
                SprCorr corr = sprCorrFacade.findByName("Департамент образования");
                vhCorr.setSprcorrId(corr);
                vhCorr.setSprvhtypeId(sprVhTypeFacade.findByName("prikaz"));                
                vhCorr.setVhcorrD(vhd);
                vhCorr.setVhcorrIshd(ishd);
                vhCorr.setVhcorrIshn(ishn);
                // регистрация входящего номера
                // получаем номер (синглетон нужен для синхрониронизированного выполнения запроса)
                MySingleton mySingleton = MySingleton.getInstance();
                String nomer = mySingleton.getNextNomer(ipraVhnomFacade);
                vhCorr.setVhcorrN(nomer);
                vhCorr.setUserId(user);
                vhCorr.setDateUpd(curDate);
                vhCorrFacade.create(vhCorr);
                // для каждой ИПРА из списка к письму
                for (Ipra18N ipra : ipraList) {
                    // привязка письма к ИПРА    
                    IpraVhcorr ipraVhcorr = new IpraVhcorr();
                    ipraVhcorr.setIpra18Id(ipra);
                    ipraVhcorr.setVhcorrId(vhCorr);
                    ipraVhcorrFacade.create(ipraVhcorr);                  
                    // сохранение данных приказа
                    String prN = params.get("prikazn" + ipra.getIpra18Id())[0];
                    String prDS = params.get("prikazd" + ipra.getIpra18Id())[0];
                    Date prD = null;
                    try{
                        prD = format.parse(prDS);
                    } catch (ParseException ex) {
                        Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // ищем заготовку приказа
                    Ipra18PrikazN prikaz = ipra18PrikazNFacade.findByIpra(ipra);
                    prikaz.setIpra18prikazN(prN);
                    prikaz.setIpra18prikazD(prD);
                    prikaz.setUserId(user);
                    prikaz.setDateUpd(curDate);
                    ipra18PrikazNFacade.edit(prikaz);
                }
                // xml с результатом
                sb.append("<result>");
                sb.append("<action>").append(action).append("</action>");
                sb.append("<value>").append(1).append("</value>");
                sb.append("</result>");
            } catch (Exception ex) {
                // xml с результатом
                sb.append("<result>");
                sb.append("<action>").append(action).append("</action>");
                sb.append("<value>").append(0).append("</value>");
                sb.append("</result>");
            }
            if (sb.length() > 0) {  // если удалось составить xml - передаём клиенту
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {    // если нет - передаём статус "нет контента"
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
    }

    private Date countDate(Integer period, String type, String direct, Date date) { // функция вычисления даты по календарю
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
        return resultDate;
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
