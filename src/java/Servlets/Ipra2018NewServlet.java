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
import Entities.IpraPerechenN;
import Entities.IpraPerechenTpmpk;
import Entities.IshCorr;
import Entities.Kalendar;
import Entities.LoginLog;
import Entities.Nom;
import Entities.SprIshType;
import Entities.SprMse;
import Entities.SprRegion;
import Entities.Users;
import Other.RegionComparator;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenFacade;
import Sessions.Ipra18NFacade;
import Sessions.Ipra18PrikazNFacade;
import Sessions.IpraPerechenNFacade;
import Sessions.IpraPerechenTpmpkFacade;
import Sessions.IshCorrFacade;
import Sessions.KalendarFacade;
import Sessions.LoginLogFacade;
import Sessions.NomFacade;
import Sessions.SprIshTypeFacade;
import Sessions.SprMseFacade;
import Sessions.SprRegionFacade;
import Sessions.SprStatFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            // список районов для поиска
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            // подставляем список районов в соответствующий атрибут сессии
            session.setAttribute("regions", regions);
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
                    if (ip.getIpra18Id().getChildId().getChildPatr() != null) {
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
                    if (ip.getIpra18Id().getChildId().getChildPatr() != null) {
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
                        sb.append("<id>").append(ip.getIpraperechennId().getIpra18Id().getIpra18Id()).append("</id>");
                        sb.append("<check>").append("check").append(ip.getIpraperechennId().getIpra18Id().getIpra18Id()).append("</check>");
                        sb.append("<fam>").append(ip.getIpraperechennId().getIpra18Id().getChildId().getChildFam()).append("</fam>");
                        sb.append("<name>").append(ip.getIpraperechennId().getIpra18Id().getChildId().getChildName()).append("</name>");
                        String p = " ";
                        if (ip.getIpraperechennId().getIpra18Id().getChildId().getChildPatr() != null) {
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
                    if (ip.getChildId().getChildPatr() != null) {
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
                // подставляем список районов в соответствующий атрибут сессии
                session.setAttribute("mseList", mseList);

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
        if (action.equals("save")) {     // сохранение
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

            // дата входящего письма в ДО из МСЭ
            String ipraVhToDODS = null;
            try {
                ipraVhToDODS = params.get("ipraVhToDOD")[0];
            } catch (Exception ex) {
            }
            Date ipraVhToDOD = null;
            try {
                ipraVhToDOD = format.parse(ipraVhToDODS);
            } catch (ParseException ex) {
                Logger.getLogger(Ipra2018NewServlet.class.getName()).log(Level.SEVERE, null, ex);
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

            // статус (1 - в работе, 0 - архив)
            String statusS = "";
            Integer status = 1;
            try {
                statusS = params.get("status")[0];
                status = Integer.parseInt(statusS);
            } catch (Exception ex) {
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

                // проставляем статус ДИ и ТР детям
                List<ChildStatus> chstList = childStatusFacade.findByChildAct(child);
                Boolean isDI = false;
                Boolean isTR = false;
                // проверяем, есть ли среди действующих статусов ДИ и ТР
                for (ChildStatus chst : chstList) {
                    if (chst.getSprstatId().getSprstatInv() == 1) { // есть ДИ
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
