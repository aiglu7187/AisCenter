/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Count.BigOtch;
import Count.CountClient;
import Count.CountDate;
import Count.CountKatBig;
import Count.CountKatPriyom;
import Entities.Priyom;
import Entities.PriyomSotrud;
import Other.Xls;
import Count.CountPriyom;
import Count.CountProblem;
import Count.CountProblemKod;
import Count.CountProblemUsl;
import Count.CountStatPmpk;
import Count.CountStatus;
import Count.CountStatusDolgn;
import Count.CountStatusReg;
import Count.CountStatusUsl;
import Count.Gz;
import Count.OtchetStatPmpk;
import Count.StandartCount;
import Count.Statistic;
import Entities.ChildStatus;
import Entities.Children;
import Entities.Ipra;
import Entities.Ipra18;
import Entities.Ipra18Prikaz;
import Entities.IpraEduCondition;
import Entities.IpraPerechen;
import Entities.LoginLog;
import Entities.Parents;
import Entities.Ped;
import Entities.Pmpk;
import Entities.PmpkRek;
import Entities.PriyomClient;
import Entities.PriyomSubject;
import Entities.SotrudDolgn;
import Entities.SprObr;
import Entities.SprObrType;
import Entities.SprObrVar;
import Entities.SprOmsu;
import Entities.SprOsnusl;
import Entities.SprOtherPmpk;
import Entities.SprProblem;
import Entities.SprProblemType;
import Entities.SprRegion;
import Entities.SprRekomend;
import Entities.SprStat;
import Entities.SprUsl;
import Entities.Users;
import Other.Age;
import Other.Ipra18Comparator;
import Other.MySingleton;
import Other.OvzFgos;
import Other.ProblemTypeComparator;
import Other.ReestrComparator;
import Other.ReestrPmpkComparator;
import Other.ReestrFullComparator;
import Other.ReestrMonitComparator;
import Other.RolesRight;
import Other.Wrd;
import Other.Zip;
import Reestr.PMPKR;
import Reestr.PMPKStatus;
import Reestr.PMPKTer;
import Reestr.Reestr;
import Reestr.ReestrEnt;
import Reestr.ReestrMonitPMPK;
import Reestr.ReestrPMPK;
import Reestr.ReestrPMPKFull;
import Reestr.ReestrUsl;
import Sessions.CenterNameFacade;
import Sessions.ChildEduplaceFacade;
import Sessions.ChildStatFacade;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenFacade;
import Sessions.Ipra18Facade;
import Sessions.Ipra18PrikazFacade;
import Sessions.IpraEduConditionFacade;
import Sessions.IpraFacade;
import Sessions.IpraIshnomFacade;
import Sessions.IpraPerechenFacade;
import Sessions.LoginLogFacade;
import Sessions.ParentsFacade;
import Sessions.PedFacade;
import Sessions.PmpkFacade;
import Sessions.PmpkRekFacade;
import Sessions.PriyomClientFacade;
import Sessions.PriyomFacade;
import Sessions.PriyomProblemFacade;
import Sessions.PriyomSotrudFacade;
import Sessions.PriyomSubjectFacade;
import Sessions.SotrudDolgnFacade;
import Sessions.SotrudFacade;
import Sessions.SprMseFacade;
import Sessions.SprObrFacade;
import Sessions.SprObrTypeFacade;
import Sessions.SprObrVarFacade;
import Sessions.SprOsnuslFacade;
import Sessions.SprProblemFacade;
import Sessions.SprProblemTypeFacade;
import Sessions.SprRegionFacade;
import Sessions.SprRekomendFacade;
import Sessions.SprStatFacade;
import Sessions.SprUslFacade;
import Sessions.UsersRegionFacade;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.write.WriteException;

/**
 *
 * @author Aiglu
 */
// сервлет для печати отчетов
@WebServlet(name = "PrintServlet",
        loadOnStartup = 1,
        urlPatterns = "/print")

public class PrintServlet extends HttpServlet {

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
    SotrudFacade sotrudFacade = new SotrudFacade();
    @EJB
    PriyomFacade priyomFacade = new PriyomFacade();
    @EJB
    PriyomSotrudFacade priyomSotrudFacade = new PriyomSotrudFacade();
    @EJB
    SprUslFacade sprUslFacade = new SprUslFacade();
    @EJB
    SprOsnuslFacade sprOsnuslFacade = new SprOsnuslFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    SprObrFacade sprObrFacade = new SprObrFacade();
    @EJB
    SprObrVarFacade sprObrVarFacade = new SprObrVarFacade();
    @EJB
    ChildStatFacade childStatFacade = new ChildStatFacade();
    @EJB
    PriyomProblemFacade priyomProblemFacade = new PriyomProblemFacade();
    @EJB
    PriyomClientFacade priyomClientFacade = new PriyomClientFacade();
    @EJB
    SprProblemFacade sprProblemFacade = new SprProblemFacade();
    @EJB
    SprProblemTypeFacade sprProblemTypeFacade = new SprProblemTypeFacade();
    @EJB
    PriyomSubjectFacade priyomSubjectFacade = new PriyomSubjectFacade();
    @EJB
    UsersRegionFacade usersRegionFacade = new UsersRegionFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    SprRekomendFacade sprRekomendFacade = new SprRekomendFacade();
    @EJB
    IpraFacade ipraFacade = new IpraFacade();
    @EJB
    ChildEduplaceFacade childEduplaceFacade = new ChildEduplaceFacade();
    @EJB
    PmpkRekFacade pmpkRekFacade = new PmpkRekFacade();
    @EJB
    PmpkFacade pmpkFacade = new PmpkFacade();
    @EJB
    SprObrTypeFacade sprObrTypeFacade = new SprObrTypeFacade();
    @EJB
    ParentsFacade parentsFacade = new ParentsFacade();
    @EJB
    PedFacade pedFacade = new PedFacade();
    @EJB
    CenterNameFacade centerNameFacade = new CenterNameFacade();
    @EJB
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    SotrudDolgnFacade sotrudDolgnFacade = new SotrudDolgnFacade();
    @EJB
    SprMseFacade sprMseFacade = new SprMseFacade();
    @EJB
    Ipra18Facade ipra18Facade = new Ipra18Facade();
    @EJB
    Ipra18PrikazFacade ipra18PrikazFacade = new Ipra18PrikazFacade();
    @EJB
    IpraIshnomFacade ipraIshnomFacade = new IpraIshnomFacade();
    @EJB
    IpraPerechenFacade ipraPerechenFacade = new IpraPerechenFacade();
    @EJB
    IpraEduConditionFacade ipraEduConditionFacade = new IpraEduConditionFacade();
    @EJB
    SprStatFacade sprStatFacade = new SprStatFacade();

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

        String type = request.getParameter("type");
        String sotruddolgnIdS = null;
        String date1 = request.getParameter("date1");
        String date2 = request.getParameter("date2");

        if (type.equals("sotrud")) {    // отчёт сотрудника
            sotruddolgnIdS = request.getParameter("id");

            Integer sotrdolgnId = null;
            if (sotruddolgnIdS != null) {
                try {
                    sotrdolgnId = Integer.parseInt(sotruddolgnIdS);
                } catch (Exception ex) {
                    ex.printStackTrace();
                };
            }

            SotrudDolgn sotrudDolgn = null;

            if (sotrdolgnId != null) {
                sotrudDolgn = sotrudDolgnFacade.findById(sotrdolgnId);
            }

            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            List<Priyom> priyomListAll = priyomFacade.findPriyom(d1, d2, null, null);

            List<Priyom> priyomList = new ArrayList<>();
            if ((sotrudDolgn != null) && (d1 != null) && (d2 != null)) {
                for (Priyom pr : priyomListAll) {
                    List<PriyomSotrud> prsotr = null;
                    try {
                        prsotr = priyomSotrudFacade.findByPriyom(pr);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (prsotr != null) {
                        for (PriyomSotrud ps : prsotr) {
                            SotrudDolgn s = ps.getSotruddolgnId();
                            if (s.equals(sotrudDolgn)) {
                                priyomList.add(pr);
                            }
                        }
                    }
                }

                List<CountPriyom> countListC = priyomFacade.countPriyomSotrudC(sotrudDolgn, d1, d2);
                List<CountPriyom> countList = priyomFacade.countPriyomSotrud(sotrudDolgn, d1, d2);
                List<CountClient> countClListC = priyomFacade.countClientSotrudC(sotrudDolgn, d1, d2);
                List<CountClient> countClList = priyomFacade.countClientSotrud(sotrudDolgn, d1, d2);

                List<Statistic> statistic = new ArrayList<>();

                List<SprUsl> sprusl = sprUslFacade.findAllUsl();
                Integer i = 0;
                for (SprUsl usl : sprusl) {
                    Statistic stat = new Statistic();
                    i++;
                    stat.setN(i);
                    stat.setUsl(usl.getSpruslName());
                    Integer countP = 0;
                    for (CountPriyom c : countListC) {
                        if (c.getUsl().equals(usl.getSpruslName())) {
                            countP += c.getCount();
                        }
                    }
                    stat.setPriyomCenter(countP);
                    countP = 0;
                    for (CountPriyom c : countList) {
                        if (c.getUsl().equals(usl.getSpruslName())) {
                            countP += c.getCount();
                        }
                    }
                    stat.setPriyomNotCenter(countP);
                    stat.setPriyomAll(stat.getPriyomCenter() + stat.getPriyomNotCenter());

                    Integer countCl = 0;
                    for (CountClient c : countClListC) {
                        if (c.getUsl().equals(usl.getSpruslName())) {
                            countCl += c.getCount();
                        }
                    }
                    stat.setClientCenter(countCl);
                    countCl = 0;
                    for (CountClient c : countClList) {
                        if (c.getUsl().equals(usl.getSpruslName())) {
                            countCl += c.getCount();
                        }
                    }
                    stat.setClientNotCenter(countCl);
                    stat.setClientAll(stat.getClientCenter() + stat.getClientNotCenter());

                    statistic.add(stat);
                }

                Integer childKol = 0;
                Integer parentsKol = 0;
                Integer pedKol = 0;

                for (CountClient c : countClListC) {
                    switch (c.getKatClient()) {
                        case "children":
                            childKol += c.getCount();
                            break;
                        case "parents":
                            parentsKol += c.getCount();
                            break;
                        case "ped":
                            pedKol += c.getCount();
                            break;
                        default:
                            break;
                    }
                }

                for (CountClient c : countClList) {
                    switch (c.getKatClient()) {
                        case "children":
                            childKol += c.getCount();
                            break;
                        case "parents":
                            parentsKol += c.getCount();
                            break;
                        case "ped":
                            pedKol += c.getCount();
                            break;
                        default:
                            break;
                    }
                }

                List<CountKatPriyom> countKat = priyomFacade.countKatPriyom(sotrudDolgn, d1, d2);
                Integer childKolP = 0;
                Integer parentsKolP = 0;
                Integer pedKolP = 0;
                for (CountKatPriyom kat : countKat) {
                    switch (kat.getKatClient()) {
                        case "children":
                            childKolP = kat.getCount();
                            break;
                        case "parents":
                            parentsKolP += kat.getCount();
                            break;
                        case "ped":
                            pedKolP += kat.getCount();
                            break;
                        default:
                            break;
                    }
                }

                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_" + sotrudDolgn.getSotrudId().getSotrudId() + "_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                String pmpkShName = centerNameFacade.findById(1).getShortName();
                try {
                    Xls.printOtchetSotrud(response.getOutputStream(), sotrudDolgn, d1, d2, statistic, childKol,
                            parentsKol, pedKol, childKolP, parentsKolP, pedKolP, pmpkShName);
                } catch (WriteException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("sotrudBig")) {  // расширенный отчёт сотрудника
            sotruddolgnIdS = request.getParameter("id");

            Integer sotrdolgnId = null;
            if (sotruddolgnIdS != null) {
                try {
                    sotrdolgnId = Integer.parseInt(sotruddolgnIdS);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            SotrudDolgn sotrudDolgn = null;

            if (sotrdolgnId != null) {
                sotrudDolgn = sotrudDolgnFacade.findById(sotrdolgnId);
            }

            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if ((sotrudDolgn != null) && (d1 != null) && (d2 != null)) {
                // индивидуальное консультирование в центре
                SprUsl usl = sprUslFacade.findById(4);
                Integer cent = 1;
                BigOtch indKonsCenter = getBigOtch(d1, d2, usl, sotrudDolgn, cent);

                // индивидуальное консультирование не в центре    
                cent = 0;
                BigOtch indKonsNoCenter = getBigOtch(d1, d2, usl, sotrudDolgn, cent);

                // всё индивидуальное консультирование
                List<BigOtch> otch = new ArrayList<>();
                otch.add(indKonsCenter);
                otch.add(indKonsNoCenter);

                // групповое консультирование педагогов
                usl = sprUslFacade.findById(5);
                String kat = "ped";
                BigOtch grKonsPed = getBigOtchDate(d1, d2, usl, sotrudDolgn, kat);
                Map<String, BigOtch> konsMap = new HashMap<>();
                konsMap.put(kat, grKonsPed);
                // групповое консультирование родителей
                usl = sprUslFacade.findById(5);
                kat = "parents";
                BigOtch grKonsParents = getBigOtchDate(d1, d2, usl, sotrudDolgn, kat);
                konsMap.put(kat, grKonsParents);
                // групповое консультирование детей
                usl = sprUslFacade.findById(5);
                kat = "children";
                BigOtch grKonsChildren = getBigOtchDate(d1, d2, usl, sotrudDolgn, kat);
                konsMap.put(kat, grKonsChildren);

                // индивидуальное обследование в центре
                usl = sprUslFacade.findById(2);
                cent = 1;
                BigOtch indObsled = getBigOtch(d1, d2, usl, sotrudDolgn, cent);

                // индивидуальное обследование в районах
                usl = sprUslFacade.findById(2);
                cent = 0;
                kat = "children";
                BigOtch indObsledReg = getBigOtchDateReg(d1, d2, usl, sotrudDolgn, kat, cent);
                BigOtch indObsledRegAll = getBigOtch(d1, d2, usl, sotrudDolgn, cent);
                // групповое обследование в районах
                usl = sprUslFacade.findById(3);
                cent = 0;
                BigOtch grObsledReg = getBigOtchDateReg(d1, d2, usl, sotrudDolgn, kat, cent);
                BigOtch grObsledRegAll = getBigOtch(d1, d2, usl, sotrudDolgn, cent);

                // обследование в р-нах
                List<BigOtch> obsledReg = new ArrayList<>();
                obsledReg.add(indObsledReg);
                obsledReg.add(grObsledReg);
                List<BigOtch> obsledRegAll = new ArrayList<>();
                obsledRegAll.add(indObsledRegAll);
                obsledRegAll.add(grObsledRegAll);

                // ПМПК
                List<StandartCount> countPmpk = priyomSotrudFacade.countPmpk(d1, d2, sotrudDolgn);

                // КРЗ индивидуальное
                usl = sprUslFacade.findById(6);
                cent = 1;
                BigOtch indKrz = getBigOtch(d1, d2, usl, sotrudDolgn, cent);
                // КРЗ групповое
                usl = sprUslFacade.findById(7);
                cent = 1;
                BigOtch grKrz = getBigOtch(d1, d2, usl, sotrudDolgn, cent);

                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_" + sotrudDolgn.getSotrudId().getSotrudId() + "_" + date1 + "-" + date2 + ".docx";
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessin‌​gml.document");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                String centerName = centerNameFacade.findById(1).getName();
                String centerShName = centerNameFacade.findById(1).getShortName();
                try {
                    Wrd.bigOtchet(response.getOutputStream(), d1, d2, sotrudDolgn, otch, konsMap,
                            indObsled, obsledReg, obsledRegAll, countPmpk, indKrz, grKrz, centerName, centerShName);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("gz")) { // госзадание
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            List<CountClient> countCl = priyomFacade.countClient(d1, d2);
            List<CountClient> countClC = priyomFacade.countClientC(d1, d2);
            List<CountPriyom> countPr = priyomFacade.countPriyom(d1, d2);
            List<CountPriyom> countPrC = priyomFacade.countPriyomC(d1, d2);
            List<CountClient> countOsnCl = priyomFacade.countOsnClient(d1, d2);
            List<CountClient> countOsnClC = priyomFacade.countOsnClientC(d1, d2);
            List<CountPriyom> countPrCl = priyomFacade.countPrCl(d1, d2);
            List<CountPriyom> countPrClC = priyomFacade.countPrClC(d1, d2);

            List<Gz> gz = new ArrayList<>();

            List<SprUsl> sprusl = sprUslFacade.findAllUsl();
            for (SprUsl usl : sprusl) {
                Gz gzUsl = new Gz();
                gzUsl.setOsnUsl(usl.getSprosnuslId().getSprosnuslName());
                gzUsl.setUsl(usl.getSpruslName());
                gzUsl.setCenter(Boolean.FALSE);
                Integer count = 0;
                for (CountClient cl : countCl) {
                    if (cl.getUsl().equals(usl.getSpruslName())) {
                        count += cl.getCount();
                    }
                }
                gzUsl.setClient(count);
                count = 0;
                for (CountPriyom pr : countPr) {
                    if (pr.getUsl().equals(usl.getSpruslName())) {
                        count += pr.getCount();
                    }
                }
                gzUsl.setPriyom(count);
                count = 0;
                for (CountPriyom pr : countPrCl) {
                    if (pr.getUsl().equals(usl.getSpruslName())) {
                        count += pr.getCount();
                    }
                }
                gzUsl.setPrcl(count);
                gz.add(gzUsl);
            }
            List<SprOsnusl> sprOsnusl = sprOsnuslFacade.findAll();
            for (SprOsnusl osnUsl : sprOsnusl) {
                Gz gzOsnUsl = new Gz();
                gzOsnUsl.setOsnUsl(osnUsl.getSprosnuslName());
                gzOsnUsl.setUsl("итого");
                gzOsnUsl.setCenter(Boolean.FALSE);
                Integer count = 0;
                for (CountClient cl : countOsnCl) {
                    if (cl.getOsnUsl().equals(osnUsl.getSprosnuslName())) {
                        count += cl.getCount();
                    }
                }
                gzOsnUsl.setClient(count);
                count = 0;
                for (CountPriyom pr : countPr) {
                    if (pr.getOsnUsl().equals(osnUsl.getSprosnuslName())) {
                        count += pr.getCount();
                    }
                }
                gzOsnUsl.setPriyom(count);
                count = 0;
                for (CountPriyom pr : countPrCl) {
                    if (pr.getOsnUsl().equals(osnUsl.getSprosnuslName())) {
                        count += pr.getCount();
                    }
                }
                gzOsnUsl.setPrcl(count);
                gz.add(gzOsnUsl);
            }

            for (SprUsl usl : sprusl) {
                Gz gzUsl = new Gz();
                gzUsl.setOsnUsl(usl.getSprosnuslId().getSprosnuslName());
                gzUsl.setUsl(usl.getSpruslName());
                gzUsl.setCenter(Boolean.TRUE);
                Integer count = 0;
                for (CountClient cl : countClC) {
                    if (cl.getUsl().equals(usl.getSpruslName())) {
                        count += cl.getCount();
                    }
                }
                gzUsl.setClient(count);
                count = 0;
                for (CountPriyom pr : countPrC) {
                    if (pr.getUsl().equals(usl.getSpruslName())) {
                        count += pr.getCount();
                    }
                }
                gzUsl.setPriyom(count);
                count = 0;
                for (CountPriyom pr : countPrClC) {
                    if (pr.getUsl().equals(usl.getSpruslName())) {
                        count += pr.getCount();
                    }
                }
                gzUsl.setPrcl(count);
                gz.add(gzUsl);
            }

            for (SprOsnusl osnUsl : sprOsnusl) {
                Gz gzOsnUsl = new Gz();
                gzOsnUsl.setOsnUsl(osnUsl.getSprosnuslName());
                gzOsnUsl.setUsl("итого");
                gzOsnUsl.setCenter(Boolean.TRUE);
                Integer count = 0;
                for (CountClient cl : countOsnClC) {
                    if (cl.getOsnUsl().equals(osnUsl.getSprosnuslName())) {
                        count += cl.getCount();
                    }
                }
                gzOsnUsl.setClient(count);
                count = 0;
                for (CountPriyom pr : countPrC) {
                    if (pr.getOsnUsl().equals(osnUsl.getSprosnuslName())) {
                        count += pr.getCount();
                    }
                }
                gzOsnUsl.setPriyom(count);
                count = 0;
                for (CountPriyom pr : countPrClC) {
                    if (pr.getOsnUsl().equals(osnUsl.getSprosnuslName())) {
                        count += pr.getCount();
                    }
                }
                gzOsnUsl.setPrcl(count);
                gz.add(gzOsnUsl);
            }

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_gz_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printOtchetGz(response.getOutputStream(), d1, d2, gz);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("rpmpk")) { // Реестр детей с ОВЗ и ОП, прошедших ПМПК
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }

            List<ReestrPMPK> reestr = new ArrayList<>();

            if (regId == 0) {
                reestr = priyomFacade.reestrChildren(d1, d2);
            } else {
                SprRegion region = sprRegionFacade.findById(regId);
                reestr = priyomFacade.reestrChildrenReg(d1, d2, region);
            }
            Collections.sort(reestr, new ReestrPmpkComparator());

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_reestr_PMPK_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                String pmpkShname = centerNameFacade.findById(1).getPmpkShname();
                try {
                    Xls.printReestrPMPK(response.getOutputStream(), d1, d2, reestr, pmpkShname);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("statpmpk")) { // статистика ПМПК
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            List<SprRegion> regList = sprRegionFacade.findNoCenter();
            List<SprRegion> regions = new ArrayList<>();
            for (SprRegion reg : regList) {
                regions.add(reg);
            }

            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            int i = 1;
            // всего обследованных
            List<CountStatPmpk> allStatBase = childrenFacade.childrenPMPK(d1, d2);
            List<CountStatPmpk> allStat = new ArrayList<>();
            Integer sum = 0;
            for (CountStatPmpk s : allStatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        allStat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatAll = new CountStatPmpk();
            sumStatAll.setReg("Всего");
            sumStatAll.setCount(sum);
            allStat.add(sumStatAll);
            OtchetStatPmpk all = new OtchetStatPmpk();
            all.setN("1.");
            all.setPar("Количество детей, обследованных ПМПК");
            all.setStat(allStat);
            // дети по возрастам            
            // до 3 лет
            List<CountStatPmpk> age3StatBase = childrenFacade.childrenAge3(d1, d2);
            List<CountStatPmpk> age3Stat = new ArrayList<>();
            sum = 0;

            for (CountStatPmpk s : age3StatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        age3Stat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatAge3 = new CountStatPmpk();
            sumStatAge3.setReg("Всего");
            sumStatAge3.setCount(sum);
            age3Stat.add(sumStatAge3);
            OtchetStatPmpk age3 = new OtchetStatPmpk();
            age3.setN("1.1.");
            age3.setPar("от 0 до 3 лет");
            age3.setStat(age3Stat);
            // с 3 до 7 лет
            List<CountStatPmpk> age37StatBase = childrenFacade.childrenAge37(d1, d2);
            List<CountStatPmpk> age37Stat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : age37StatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        age37Stat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatAge37 = new CountStatPmpk();
            sumStatAge37.setReg("Всего");
            sumStatAge37.setCount(sum);
            age37Stat.add(sumStatAge37);
            OtchetStatPmpk age37 = new OtchetStatPmpk();
            age37.setN("1.2.");
            age37.setPar("от 3 до 7 лет");
            age37.setStat(age37Stat);
            // от 7 до 18
            List<CountStatPmpk> age7StatBase = childrenFacade.childrenAge7(d1, d2);
            List<CountStatPmpk> age7Stat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : age7StatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        age7Stat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatAge7 = new CountStatPmpk();
            sumStatAge7.setReg("Всего");
            sumStatAge7.setCount(sum);
            age7Stat.add(sumStatAge7);
            OtchetStatPmpk age7 = new OtchetStatPmpk();
            age7.setN("1.3.");
            age7.setPar("от 7 до 18 лет");
            age7.setStat(age7Stat);
            // старше 18
            List<CountStatPmpk> age18StatBase = childrenFacade.childrenAge18(d1, d2);
            List<CountStatPmpk> age18Stat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : age18StatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        age18Stat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatAge18 = new CountStatPmpk();
            sumStatAge18.setReg("Всего");
            sumStatAge18.setCount(sum);
            age18Stat.add(sumStatAge18);
            OtchetStatPmpk age18 = new OtchetStatPmpk();
            age18.setN("1.4.");
            age18.setPar("обучающиеся с ОВЗ старше 18 лет");
            age18.setStat(age18Stat);
            // не посещающие ОУ
            List<CountStatPmpk> noOuStatBase = childrenFacade.childrenNoOu(d1, d2);
            List<CountStatPmpk> noOuStat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : noOuStatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        noOuStat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatNoOu = new CountStatPmpk();
            sumStatNoOu.setReg("Всего");
            sumStatNoOu.setCount(sum);
            noOuStat.add(sumStatNoOu);
            OtchetStatPmpk noOu = new OtchetStatPmpk();
            noOu.setN("1.5.");
            noOu.setPar("дети, не посещающие образовательные организации (н/о)");
            noOu.setStat(noOuStat);
            List<CountStatPmpk> noOu3StatBase = childrenFacade.childrenAge3NoOu(d1, d2);
            List<CountStatPmpk> noOu3Stat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : noOu3StatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        noOu3Stat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatNoOu3 = new CountStatPmpk();
            sumStatNoOu3.setReg("Всего");
            sumStatNoOu3.setCount(sum);
            noOu3Stat.add(sumStatNoOu3);
            OtchetStatPmpk noOu3 = new OtchetStatPmpk();
            noOu3.setN("1.6.");
            noOu3.setPar("н/о дети от 0 до 3 лет");
            noOu3.setStat(noOu3Stat);
            List<CountStatPmpk> noOu37StatBase = childrenFacade.childrenAge37NoOu(d1, d2);
            List<CountStatPmpk> noOu37Stat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : noOu37StatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        noOu37Stat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatNoOu37 = new CountStatPmpk();
            sumStatNoOu37.setReg("Всего");
            sumStatNoOu37.setCount(sum);
            noOu37Stat.add(sumStatNoOu37);
            OtchetStatPmpk noOu37 = new OtchetStatPmpk();
            noOu37.setN("1.7.");
            noOu37.setPar("н/о дети от 3 до 7 лет");
            noOu37.setStat(noOu37Stat);
            List<CountStatPmpk> noOu7StatBase = childrenFacade.childrenAge7NoOu(d1, d2);
            List<CountStatPmpk> noOu7Stat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : noOu7StatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        noOu7Stat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatNoOu7 = new CountStatPmpk();
            sumStatNoOu7.setReg("Всего");
            sumStatNoOu7.setCount(sum);
            noOu7Stat.add(sumStatNoOu7);
            OtchetStatPmpk noOu7 = new OtchetStatPmpk();
            noOu7.setN("1.8.");
            noOu7.setPar("н/о дети от 7 до 18 лет");
            noOu7.setStat(noOu7Stat);

            // по статусам (категориям)
            List<CountStatPmpk> statusStatBase = childrenFacade.childrenPmpkStat(d1, d2);
            Set<String> parList = new HashSet<>();
            for (CountStatPmpk st : statusStatBase) {
                parList.add(st.getParS());
            }
            i = 9;
            List<OtchetStatPmpk> status = new ArrayList<>();
            for (String p : parList) {
                List<CountStatPmpk> statusStat = new ArrayList<>();
                sum = 0;
                for (CountStatPmpk s : statusStatBase) {
                    if (s.getParS().equals(p)) {
                        for (SprRegion r : regList) {
                            if (s.getReg().equals(r.getSprregName())) {
                                statusStat.add(s);
                                sum += s.getCount();
                            }
                        }
                    }
                }
                CountStatPmpk sumStatStatus = new CountStatPmpk();
                sumStatStatus.setParS(p);
                sumStatStatus.setReg("Всего");
                sumStatStatus.setCount(sum);
                statusStat.add(sumStatStatus);
                OtchetStatPmpk otchet = new OtchetStatPmpk();
                otchet.setN("1." + Integer.toString(i++) + ".");
                otchet.setPar(p);
                otchet.setStat(statusStat);
                status.add(otchet);
            }

            // заключения
            List<SprObr> opList = sprObrFacade.findAllActObr(1);
            List<SprObr> opListNoAct = sprObrFacade.findAllActObr(0);
            List<SprObrVar> opvarList = sprObrVarFacade.findAll();
            List<CountStatPmpk> opStatBase = childrenFacade.childrenPmpkOp(d1, d2);
            List<CountStatPmpk> opvarStatBase = childrenFacade.childrenPmpkVar(d1, d2);
            List<OtchetStatPmpk> obr = new ArrayList<>();
            i = 1;
            for (SprObr op : opList) {
                List<CountStatPmpk> obrStat = new ArrayList<>();
                sum = 0;
                for (CountStatPmpk o : opStatBase) {
                    if (o.getParS().equals(op.getSprobrShname())) {
                        for (SprRegion r : regList) {
                            if (o.getReg().equals(r.getSprregName())) {
                                obrStat.add(o);
                                sum += o.getCount();
                            }
                        }
                    }
                }
                CountStatPmpk sumStatObr = new CountStatPmpk();
                sumStatObr.setParS(op.getSprobrShname());
                sumStatObr.setReg("Всего");
                sumStatObr.setCount(sum);
                obrStat.add(sumStatObr);
                OtchetStatPmpk otchet = new OtchetStatPmpk();
                otchet.setN(Integer.toString(i++) + ".");
                otchet.setPar(op.getSprobrShname());
                otchet.setStat(obrStat);
                obr.add(otchet);
                for (SprObrVar v : opvarList) {
                    if (v.getSprobrId().getSprobrId().equals(op.getSprobrId())) {
                        List<CountStatPmpk> obrvarStat = new ArrayList<>();
                        sum = 0;
                        for (CountStatPmpk var : opvarStatBase) {
                            if (var.getParS().equals(v.getSprobrvarName())) {
                                for (SprRegion r : regList) {
                                    if (var.getReg().equals(r.getSprregName())) {
                                        obrvarStat.add(var);
                                        sum += var.getCount();
                                    }
                                }
                            }
                        }
                        CountStatPmpk sumStatObrVar = new CountStatPmpk();
                        sumStatObrVar.setParS(v.getSprobrvarName());
                        sumStatObrVar.setReg("Всего");
                        sumStatObrVar.setCount(sum);
                        obrvarStat.add(sumStatObrVar);
                        OtchetStatPmpk otchetVar = new OtchetStatPmpk();
                        otchetVar.setN(Integer.toString(i++) + ".");
                        otchetVar.setPar(v.getSprobrvarName());
                        otchetVar.setStat(obrvarStat);
                        obr.add(otchetVar);
                    }
                }
            }

            for (SprObr op : opListNoAct) {
                List<CountStatPmpk> obrStat = new ArrayList<>();
                sum = 0;
                for (CountStatPmpk o : opStatBase) {
                    if (o.getParS().equals(op.getSprobrShname())) {
                        for (SprRegion r : regList) {
                            if (o.getReg().equals(r.getSprregName())) {
                                obrStat.add(o);
                                sum += o.getCount();
                            }
                        }
                    }
                }
                CountStatPmpk sumStatObr = new CountStatPmpk();
                sumStatObr.setParS(op.getSprobrShname());
                sumStatObr.setReg("Всего");
                sumStatObr.setCount(sum);
                obrStat.add(sumStatObr);
                OtchetStatPmpk otchet = new OtchetStatPmpk();
                otchet.setPar(op.getSprobrShname());
                otchet.setStat(obrStat);
                if (sum != 0) {
                    otchet.setN(Integer.toString(i++) + ".");
                    obr.add(otchet);
                }
            }

            // занятия
            List<SprRekomend> rekList = sprRekomendFacade.findAllRekomend();
            List<CountStatPmpk> chRekBase = childrenFacade.childrenRekomend(d1, d2);
            List<OtchetStatPmpk> chRek = new ArrayList<>();
            for (SprRekomend rek : rekList) {
                List<CountStatPmpk> rekStat = new ArrayList<>();
                sum = 0;
                for (CountStatPmpk s : chRekBase) {
                    if (s.getParS().equals(rek.getSprrekName())) {
                        for (SprRegion r : regList) {
                            if (s.getReg().equals(r.getSprregName())) {
                                rekStat.add(s);
                                sum += s.getCount();
                            }
                        }
                    }
                }
                CountStatPmpk sumStatRek = new CountStatPmpk();
                sumStatRek.setParS(rek.getSprrekName());
                sumStatRek.setReg("Всего");
                sumStatRek.setCount(sum);
                rekStat.add(sumStatRek);
                OtchetStatPmpk otchet = new OtchetStatPmpk();
                otchet.setN(Integer.toString(i++) + ".");
                otchet.setPar(rek.getSprrekName());
                otchet.setStat(rekStat);
                chRek.add(otchet);
            }

            // ГИА-9
            List<CountStatPmpk> gia9StatBase = childrenFacade.childrenPmpkGia9(d1, d2);
            List<CountStatPmpk> gia9Stat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : gia9StatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        gia9Stat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatGia9 = new CountStatPmpk();
            sumStatGia9.setReg("Всего");
            sumStatGia9.setCount(sum);
            gia9Stat.add(sumStatGia9);
            OtchetStatPmpk gia9 = new OtchetStatPmpk();
            gia9.setN(Integer.toString(i++) + ".");
            gia9.setPar("ГИА-9");
            gia9.setStat(gia9Stat);
            // ГИА-11
            List<CountStatPmpk> gia11StatBase = childrenFacade.childrenPmpkGia11(d1, d2);
            List<CountStatPmpk> gia11Stat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : gia11StatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        gia11Stat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatGia11 = new CountStatPmpk();
            sumStatGia11.setReg("Всего");
            sumStatGia11.setCount(sum);
            gia11Stat.add(sumStatGia11);
            OtchetStatPmpk gia11 = new OtchetStatPmpk();
            gia11.setN(Integer.toString(i++) + ".");
            gia11.setPar("ГИА-11");
            gia11.setStat(gia11Stat);

            // доп.обследование
            List<CountStatPmpk> dopStatBase = childrenFacade.childrenPmpkDop(d1, d2);
            List<CountStatPmpk> dopStat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : dopStatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        dopStat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatDop = new CountStatPmpk();
            sumStatDop.setReg("Всего");
            sumStatDop.setCount(sum);
            dopStat.add(sumStatDop);
            OtchetStatPmpk dop = new OtchetStatPmpk();
            dop.setN(Integer.toString(i++) + ".");
            dop.setPar("Доп. обследование");
            dop.setStat(dopStat);

            // ИПР
            i = 1;
            List<CountStatPmpk> iprStatBase = childrenFacade.childrenPmpkIpr(d1, d2);
            List<CountStatPmpk> iprStat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : iprStatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        iprStat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatIpr = new CountStatPmpk();
            sumStatIpr.setReg("Всего");
            sumStatIpr.setCount(sum);
            iprStat.add(sumStatIpr);
            OtchetStatPmpk ipr = new OtchetStatPmpk();
            ipr.setN(Integer.toString(i++) + ".");
            ipr.setPar("Оказание помощи МСЭ в разработке ИПРА");
            ipr.setStat(iprStat);

            // удовлетворен
            i = 1;
            List<CountStatPmpk> udovlStatBase = childrenFacade.childrenPmpkUdovl(d1, d2);
            List<CountStatPmpk> udovlStat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : udovlStatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        udovlStat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatUdovl = new CountStatPmpk();
            sumStatUdovl.setReg("Всего");
            sumStatUdovl.setCount(sum);
            udovlStat.add(sumStatUdovl);
            OtchetStatPmpk udovl = new OtchetStatPmpk();
            udovl.setN(Integer.toString(i++) + ".");
            udovl.setPar("Удовлетворен");
            udovl.setStat(udovlStat);
            // не удовлетворен
            List<CountStatPmpk> noudovlStatBase = childrenFacade.childrenPmpkNoUdovl(d1, d2);
            List<CountStatPmpk> noudovlStat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : noudovlStatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        noudovlStat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatNoUdovl = new CountStatPmpk();
            sumStatNoUdovl.setReg("Всего");
            sumStatNoUdovl.setCount(sum);
            noudovlStat.add(sumStatNoUdovl);
            OtchetStatPmpk noudovl = new OtchetStatPmpk();
            noudovl.setN(Integer.toString(i++) + ".");
            noudovl.setPar("Не удовлетворен");
            noudovl.setStat(noudovlStat);
            // согласен
            List<CountStatPmpk> soglStatBase = childrenFacade.childrenPmpkSogl(d1, d2);
            List<CountStatPmpk> soglStat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : soglStatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        soglStat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatSogl = new CountStatPmpk();
            sumStatSogl.setReg("Всего");
            sumStatSogl.setCount(sum);
            soglStat.add(sumStatSogl);
            OtchetStatPmpk sogl = new OtchetStatPmpk();
            sogl.setN(Integer.toString(i++) + ".");
            sogl.setPar("Согласен с заключением ");
            sogl.setStat(soglStat);
            // не согласен
            List<CountStatPmpk> noSoglStatBase = childrenFacade.childrenPmpkNoSogl(d1, d2);
            List<CountStatPmpk> noSoglStat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : noSoglStatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        noSoglStat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatNoSogl = new CountStatPmpk();
            sumStatNoSogl.setReg("Всего");
            sumStatNoSogl.setCount(sum);
            noSoglStat.add(sumStatNoSogl);
            OtchetStatPmpk noSogl = new OtchetStatPmpk();
            noSogl.setN(Integer.toString(i++) + ".");
            noSogl.setPar("Не согласен с заключением ");
            noSogl.setStat(noSoglStat);

            // ТПМПК
            List<CountStatPmpk> tpmpkNStatBase = childrenFacade.childrenPmpkTpmpkNapr(d1, d2);
            List<CountStatPmpk> tpmpkNStat = new ArrayList<>();
            sum = 0;
            i = 1;
            for (CountStatPmpk s : tpmpkNStatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        tpmpkNStat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatTpmpkN = new CountStatPmpk();
            sumStatTpmpkN.setReg("Всего");
            sumStatTpmpkN.setCount(sum);
            tpmpkNStat.add(sumStatTpmpkN);
            OtchetStatPmpk tpmpkN = new OtchetStatPmpk();
            tpmpkN.setN(Integer.toString(i++) + ".");
            tpmpkN.setPar("Направление ТПМПК");
            tpmpkN.setStat(tpmpkNStat);
            List<CountStatPmpk> tpmpkOStatBase = childrenFacade.childrenPmpkTpmpkOb(d1, d2);
            List<CountStatPmpk> tpmpkOStat = new ArrayList<>();
            sum = 0;
            for (CountStatPmpk s : tpmpkOStatBase) {
                for (SprRegion r : regList) {
                    if (s.getReg().equals(r.getSprregName())) {
                        tpmpkOStat.add(s);
                        sum += s.getCount();
                    }
                }
            }
            CountStatPmpk sumStatTpmpkO = new CountStatPmpk();
            sumStatTpmpkO.setReg("Всего");
            sumStatTpmpkO.setCount(sum);
            tpmpkOStat.add(sumStatTpmpkO);
            OtchetStatPmpk tpmpkO = new OtchetStatPmpk();
            tpmpkO.setN(Integer.toString(i++) + ".");
            tpmpkO.setPar("Обжалование заключения ТПМПК");
            tpmpkO.setStat(tpmpkOStat);

            // всего заседаний ПМПК
            List<CountStatPmpk> countPmpkBase = priyomFacade.countPmpk(d1, d2);
            List<SprRegion> regListAll = sprRegionFacade.findAll();
            List<CountStatPmpk> countPmpk = new ArrayList<>();
            sum = 0;
            Integer main = 0;
            for (CountStatPmpk p : countPmpkBase) {
                for (SprRegion r : regListAll) {
                    if (p.getReg().equals(r.getSprregName())) {
                        if ((p.getReg().indexOf("Вологда") != -1) || (r.getSprregCenter() == 1)) {
                            main += p.getCount();
                            sum += p.getCount();
                        } else {
                            countPmpk.add(p);
                            sum += p.getCount();
                        }
                    }
                }
            }
            CountStatPmpk sumStatCountPmpk = new CountStatPmpk();
            sumStatCountPmpk.setReg("Всего");
            sumStatCountPmpk.setCount(sum);
            countPmpk.add(sumStatCountPmpk);
            CountStatPmpk mainStatCountPmpk = new CountStatPmpk();
            mainStatCountPmpk.setReg(" г.Вологда");
            mainStatCountPmpk.setCount(main);
            countPmpk.add(mainStatCountPmpk);
            OtchetStatPmpk pmpk = new OtchetStatPmpk();
            pmpk.setN(" ");
            pmpk.setPar("Организовано заседаний ПМПК");
            pmpk.setStat(countPmpk);

            List<OtchetStatPmpk> statPmpk = new ArrayList<>();
            statPmpk.add(all);
            statPmpk.add(age3);
            statPmpk.add(age37);
            statPmpk.add(age7);
            statPmpk.add(age18);
            statPmpk.add(noOu);
            statPmpk.add(noOu3);
            statPmpk.add(noOu37);
            statPmpk.add(noOu7);
            /*    statPmpk.add(noOuOvz);
            statPmpk.add(noOuInv);
            statPmpk.add(norm);*/

            for (OtchetStatPmpk c : status) {
                statPmpk.add(c);
            }
            List<OtchetStatPmpk> statPmpkRek = new ArrayList<>();
            for (OtchetStatPmpk c : obr) {
                statPmpkRek.add(c);
            }
            for (OtchetStatPmpk c : chRek) {
                statPmpkRek.add(c);
            }
            statPmpkRek.add(gia9);
            statPmpkRek.add(gia11);
            statPmpkRek.add(dop);

            List<OtchetStatPmpk> statPmpkIpr = new ArrayList<>();
            statPmpkIpr.add(ipr);

            List<OtchetStatPmpk> statPmpkUd = new ArrayList<>();
            statPmpkUd.add(udovl);
            statPmpkUd.add(noudovl);
            statPmpkUd.add(sogl);
            statPmpkUd.add(noSogl);

            List<OtchetStatPmpk> statPmpkT = new ArrayList<>();
            statPmpkT.add(tpmpkN);
            statPmpkT.add(tpmpkO);
            statPmpkT.add(pmpk);

            /*  for (CountStatPmpk c : countPmpk){
                statPmpk.add(c);
            }
             */
            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_stat_PMPK_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                String pmpkNameRod = centerNameFacade.findById(1).getPmpkNameRod();
                RolesRight rolesRight = new RolesRight(user.getCenterRole());
                try {
                    Xls.printStatPMPK(response.getOutputStream(), d1, d2, regions, statPmpk,
                            statPmpkRek, statPmpkIpr, statPmpkUd, statPmpkT, pmpkNameRod, rolesRight);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("status")) { // отчёт по статусам
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);
            // все статусы
            List<CountStatus> status1 = childStatusFacade.countStatV(1, d1, d2);
            List<CountStatus> status2 = childStatusFacade.countStatV(2, d1, d2);
            List<CountStatus> status3 = childStatusFacade.countStatV(3, d1, d2);
            List<CountStatus> status4 = childStatusFacade.countStatV(4, d1, d2);

            List<CountStatus> status = new ArrayList<>();
            for (CountStatus s : status1) {
                status.add(s);
            }
            for (CountStatus s : status4) {
                status.add(s);
            }
            for (CountStatus s : status2) {
                status.add(s);
            }
            for (CountStatus s : status3) {
                status.add(s);
            }
            // статусы по услугам
            List<CountStatusUsl> stUsl = childStatusFacade.countStatUsl(d1, d2);

            // статусы по основным услугам
            List<CountStatusUsl> stOsnUsl = childStatusFacade.countStatOsnUsl(d1, d2);

            // обследование
            List<CountStatusDolgn> stDolgn = childStatusFacade.countStatDolgn(d1, d2);

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_status" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printStatus(response.getOutputStream(), d1, d2, status, stUsl, stOsnUsl, stDolgn);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("problem")) {  // отчёт по проблемам
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            List<CountProblem> problemAll = priyomProblemFacade.countProblemAll(d1, d2);
            List<CountProblemUsl> problemUsl = priyomProblemFacade.countProblemUsl(d1, d2);

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_problem" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printProblem(response.getOutputStream(), problemAll, problemUsl, d1, d2);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("age")) {    // отчёт по возрастам
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            String ageNS = request.getParameter("agen");
            String ageKS = request.getParameter("agek");
            Integer ageN = 0;
            Integer ageK = 0;
            try {
                ageN = Integer.parseInt(ageNS);
                ageK = Integer.parseInt(ageKS);
            } catch (Exception ex) {
            }

            List<StandartCount> uslAges = childrenFacade.uslAges(d1, d2, ageN, ageK);

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_vozrast" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printAge(response.getOutputStream(), uslAges, d1, d2, ageN, ageK);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("pmpkstatus")) { // статусы ПМПК
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            SprRegion region = null;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }

            List<PMPKStatus> reestr = new ArrayList<>();

            if (regId == 0) {
                reestr = priyomFacade.pmpkStatus(d1, d2);
            } else {
                region = sprRegionFacade.findById(regId);
                reestr = priyomFacade.pmpkStatusReg(d1, d2, region);
            }

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_PMPK_status_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printPMPKStatus(response.getOutputStream(), d1, d2, reestr);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("pmpkipr")) {    // ПМПК для ИПРА
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            SprRegion region = null;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }

            List<PMPKR> reestr = new ArrayList<>();

            if (regId == 0) {
                reestr = priyomFacade.pmpkIpr(d1, d2);
            } else {
                region = sprRegionFacade.findById(regId);
                reestr = priyomFacade.pmpkIprReg(d1, d2, region);
            }

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_PMPK_ipr_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printPMPKIpr(response.getOutputStream(), d1, d2, reestr);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("pmpkgia")) {    // ПМПК для ГИА
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            SprRegion region = null;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }

            List<PMPKR> reestr = new ArrayList<>();

            if (regId == 0) {
                reestr = priyomFacade.pmpkGia(d1, d2);
            } else {
                region = sprRegionFacade.findById(regId);
                reestr = priyomFacade.pmpkGiaReg(d1, d2, region);
            }

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_PMPK_gia_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printPMPKGia(response.getOutputStream(), d1, d2, reestr);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("pmpkfirstovz")) {   // ПМПК впервые ОВЗ
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            SprRegion region = null;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }

            List<PMPKR> reestr = new ArrayList<>();

            if (regId == 0) {
                reestr = priyomFacade.pmpkFirstOvz(d1, d2);
            } else {
                region = sprRegionFacade.findById(regId);
                reestr = priyomFacade.pmpkFirstOvzReg(d1, d2, region);
            }

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_PMPK_first_OVZ_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printPMPKFirstOvz(response.getOutputStream(), d1, d2, reestr);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("pmpkter")) {    // ПМПК ТПМПК
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            SprRegion region = null;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }

            List<PMPKTer> reestr = new ArrayList<>();

            if (regId == 0) {
                reestr = priyomFacade.pmpkTer(d1, d2);
            } else {
                region = sprRegionFacade.findById(regId);
                reestr = priyomFacade.pmpkTerReg(d1, d2, region);
            }

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_PMPK_ter_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printPMPKTer(response.getOutputStream(), d1, d2, reestr);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("pmpkrek")) {    // рекомндации ПМПК
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            SprRegion region = null;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }

            List<PMPKTer> reestr = new ArrayList<>();

            if (regId == 0) {
                reestr = priyomFacade.pmpkRek(d1, d2);
            } else {
                region = sprRegionFacade.findById(regId);
                reestr = priyomFacade.pmpkRekReg(d1, d2, region);
            }

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_PMPK_rek_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printPMPKRek(response.getOutputStream(), d1, d2, reestr);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("statpmpkstatus")) { // статистика статусы ПМПК
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            List<CountStatusReg> countStatus = new ArrayList<>();
            countStatus = priyomFacade.countStatusPmpk(d1, d2);

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_stat_PMPK_status_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printStatPmpkStatus(response.getOutputStream(), d1, d2, countStatus);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("statpmpkrek")) {    // статистика рекомендации ПМПК
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            List<CountStatusReg> countStatus = new ArrayList<>();
            countStatus = priyomFacade.countRekPmpk(d1, d2);

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_stat_PMPK_rek_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printStatPmpkRek(response.getOutputStream(), d1, d2, countStatus);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("statpmpkpar")) {    // статистика ПМПК разные
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            List<CountStatusReg> countStatus = new ArrayList<>();
            countStatus = priyomFacade.countParPmpk(d1, d2);

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_stat_PMPK_par_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printStatPmpkPar(response.getOutputStream(), d1, d2, countStatus);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("monitpmpk")) {  // мониторинг выполнения рекомендаций ПМПК
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }

            List<ReestrMonitPMPK> reestr = new ArrayList<>();
            SprRegion region = sprRegionFacade.findById(regId);
            reestr = priyomFacade.reestrMonitChildrenReg(d1, d2, region);
            Collections.sort(reestr, new ReestrMonitComparator());
            List<Users> us = usersRegionFacade.getUserByRegion(region);

            if ((d1 != null) && (d2 != null)) {
                String regNom = "";
                if (regId < 10) {
                    regNom = "0" + regId.toString();
                } else if (regId >= 10) {
                    regNom = regId.toString();
                }

                for (Users u : us) {
                    String fileName = regNom + "_" + u.getUserName() + "_reestrPMPK_" + date1 + "-" + date2 + ".zip";
                    response.setContentType("application/zip");
                    response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                    String pass = u.getUserPassword();
                    String xlsName = u.getUserName() + "_reestrPMPK_" + date1 + "-" + date2 + ".xls";
                    String pmpkShname = centerNameFacade.findById(1).getPmpkShname();
                    try {
                        File freestr = Xls.printMonitReestrPMPK(d1, d2, reestr, xlsName, pmpkShname);
                        File zip = Zip.archive(pass, freestr);
                        byte[] b = Files.readAllBytes(zip.toPath());
                        response.getOutputStream().write(b);

                    } catch (Exception ex) {
                        Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("iprared")) {    // ИПРА сроки Б месяца
            List<Ipra> findRed = ipraFacade.findRed();
            SimpleDateFormat curDateFormat = new SimpleDateFormat();
            curDateFormat.applyPattern("ddMMyyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);
            String printDate = curDateFormat.format(curDate);
            String fileName = "Ipra-" + printDate + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
            try {
                Xls.printIpraRed(response.getOutputStream(), findRed);
            } catch (Exception ex) {
                Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (type.equals("ovzfgos")) {    // выгрузка ОВЗ по ФГОС из ПМПК
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String pmpkShname = centerNameFacade.findById(1).getPmpkShname();
            List<OvzFgos> ovzFgosList = childEduplaceFacade.findOvzFgos(d1, d2, pmpkShname);
            for (OvzFgos ovzFgos : ovzFgosList) {
                Pmpk pmpk = pmpkFacade.findById(ovzFgos.getPmpkId());
                List<PmpkRek> rekList = pmpkRekFacade.findByPmpk(pmpk);
                for (PmpkRek rek : rekList) {
                    if ((rek.getSprrekId().getSprrekId() == 1) || (rek.getSprrekId().getSprrekId() == 2)) {
                        ovzFgos.setRekAssist("+");
                    } else if (rek.getSprrekId().getSprrekId() == 3) {
                        ovzFgos.setRekPsy("+");
                    } else if (rek.getSprrekId().getSprrekId() == 4) {
                        ovzFgos.setRekLogo("+");
                    } else if (rek.getSprrekId().getSprrekId() == 5) {
                        ovzFgos.setRekDefOl("+");
                    } else if (rek.getSprrekId().getSprrekId() == 6) {
                        ovzFgos.setRekDefTiflo("+");
                    } else if (rek.getSprrekId().getSprrekId() == 7) {
                        ovzFgos.setRekDefSurdo("+");
                    } else if (rek.getSprrekId().getSprrekId() == 9) {
                        ovzFgos.setRekEquip("+");
                    }
                }
            }

            SimpleDateFormat curDateFormat = new SimpleDateFormat();
            curDateFormat.applyPattern("ddMMyyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);
            String printDate = curDateFormat.format(curDate);
            String fileName = "OVZ_FGOS-" + printDate + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
            try {
                Xls.printOvzFgos(response.getOutputStream(), ovzFgosList);
            } catch (Exception ex) {
                Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (type.equals("ovzarch")) {    // реестр детей с ОВЗ
            Date d1 = null;
            Date d2 = null;
            Date d3 = null;
            Date d4 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat regularDateFormat = new SimpleDateFormat("dd.MM.yyyy");

            String date3 = request.getParameter("date3");
            String date4 = request.getParameter("date4");
            String obrtype = request.getParameter("obrtype");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (date3 != null) {
                try {
                    d3 = dateFormat.parse(date3);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (date4 != null) {
                try {
                    d4 = dateFormat.parse(date4);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }
            SprRegion region = null;
            if (regId != 0) {
                region = sprRegionFacade.findById(regId);

            }

            if (d3 == null) {
                try {
                    d3 = regularDateFormat.parse("01.01.1950");
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (d4 == null) {
                long curTime = System.currentTimeMillis();
                d4 = new Date(curTime);
            }

            String[] types = obrtype.split(";");
            List<SprObrType> sprObrTypes = new ArrayList<>();
            for (String t : types) {
                if (!t.equals("")) {
                    SprObrType sprObrType = null;
                    try {
                        sprObrType = sprObrTypeFacade.findBySprObrTypeId(Integer.parseInt(t));
                    } catch (Exception ex) {
                    }
                    if (sprObrType != null) {
                        sprObrTypes.add(sprObrType);
                    }
                }
            }
            String pmpkShname = centerNameFacade.findById(1).getPmpkShname();
            List<OvzFgos> ovzList = childEduplaceFacade.findOvzByRegion(d1, d2, region, d3, d4, sprObrTypes, pmpkShname);
            for (OvzFgos ovz : ovzList) {
                Pmpk pmpk = pmpkFacade.findById(ovz.getPmpkId());
                List<PmpkRek> rekList = pmpkRekFacade.findByPmpk(pmpk);
                for (PmpkRek rek : rekList) {
                    if ((rek.getSprrekId().getSprrekId() == 1) || (rek.getSprrekId().getSprrekId() == 2)) {
                        ovz.setRekAssist("+");
                    } else if (rek.getSprrekId().getSprrekId() == 3) {
                        ovz.setRekPsy("+");
                    } else if (rek.getSprrekId().getSprrekId() == 4) {
                        ovz.setRekLogo("+");
                    } else if (rek.getSprrekId().getSprrekId() == 5) {
                        ovz.setRekDefOl("+");
                    } else if (rek.getSprrekId().getSprrekId() == 6) {
                        ovz.setRekDefTiflo("+");
                    } else if (rek.getSprrekId().getSprrekId() == 7) {
                        ovz.setRekDefSurdo("+");
                    } else if (rek.getSprrekId().getSprrekId() == 9) {
                        ovz.setRekEquip("+");
                    }
                }
            }

            List<Users> us = usersRegionFacade.getUserByRegion(region);

            if ((d1 != null) && (d2 != null)) {
                String regNom = "";
                if (regId < 10) {
                    regNom = "0" + regId.toString();
                } else if (regId >= 10) {
                    regNom = regId.toString();
                }

                for (Users u : us) {
                    String fileName = regNom + "_" + u.getUserName() + "_reestrOVZ_" + date1 + "-" + date2 + ".zip";
                    response.setContentType("application/zip");
                    response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                    String pass = u.getUserPassword();
                    String xlsName = u.getUserName() + "_reestrOVZ_" + date1 + "-" + date2 + ".xls";
                    try {
                        File freestr = Xls.printOvz(ovzList, xlsName);
                        File zip = Zip.archive(pass, freestr);
                        byte[] b = Files.readAllBytes(zip.toPath());
                        response.getOutputStream().write(b);

                    } catch (Exception ex) {
                        Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("reestrusl")) {  // реестр по услугам
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String uslIdS = request.getParameter("usl");
            Integer uslId = 0;
            try {
                uslId = Integer.parseInt(uslIdS);
            } catch (Exception ex) {
            }
            SprUsl usl = null;
            if (uslId != 0) {
                try {
                    usl = sprUslFacade.findById(uslId);
                } catch (Exception ex) {
                }
            }
            List<ReestrUsl> reestrUslList = new ArrayList<>();
            List<Priyom> priyomList = new ArrayList<>();
            if (usl != null) {
                priyomList = priyomFacade.findPriyom(d1, d2, usl, null);
            }
            for (Priyom priyom : priyomList) {
                List<PriyomClient> clients = priyomClientFacade.findByPriyom(priyom);
                List<PriyomSotrud> sotruds = priyomSotrudFacade.findByPriyom(priyom);
                for (PriyomClient client : clients) {
                    ReestrUsl ru = new ReestrUsl();
                    ru.setDate(priyom.getPriyomDate());
                    String kat = client.getPrclKatcl();
                    if (kat.equals("children")) {
                        Children child = childrenFacade.findById(client.getClientId());
                        ru.setFio(child.getFIO());
                        ru.setDr(child.getChildDr());
                    } else if (kat.equals("parents")) {
                        Parents parent = parentsFacade.findById(client.getClientId());
                        ru.setFio(parent.getFIO());
                    } else if (kat.equals("ped")) {
                        Ped ped = pedFacade.findById(client.getClientId());
                        ru.setFio(ped.getFIO());
                    }
                    String s = "";
                    for (PriyomSotrud sotrud : sotruds) {
                        s += sotrud.getSotruddolgnId().getSotrudId().getSotrudFIO();
                        s += " (" + sotrud.getSotruddolgnId().getSprdolgnId().getSprdolgnName() + ")";
                        s += " ";
                    }
                    ru.setSpec(s);
                    reestrUslList.add(ru);
                }
            }

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_Reestr_usl_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printReestrUsl(response.getOutputStream(), d1, d2, reestrUslList, usl);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("rpmpkfull")) {  // Полный реестр детей, прошедших ПМПК
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }

            List<ReestrPMPKFull> reestr = new ArrayList<>();

            if (regId == 0) {
                reestr = priyomFacade.reestrChildrenFull(d1, d2);
            }
            /*else {
                SprRegion region = sprRegionFacade.findById(regId);
                reestr = priyomFacade.reestrChildrenRegd1, d2, region);
            }*/
            Collections.sort(reestr, new ReestrFullComparator());
            // вычисляем возраст
            for (ReestrPMPKFull r : reestr) {
                Calendar pr = Calendar.getInstance();
                pr.setTime(r.getDatep());
                Calendar dr = Calendar.getInstance();
                dr.setTime(r.getDr());
                int age = pr.get(Calendar.YEAR) - dr.get(Calendar.YEAR);
                // корректируем, если текущий месяц в дате проверки меньше месяца даты рождения
                int prMonth = pr.get(Calendar.MONTH);
                int drMonth = dr.get(Calendar.MONTH);
                if (prMonth < drMonth) {
                    age--;
                } else if (prMonth == drMonth
                        && pr.get(GregorianCalendar.DAY_OF_MONTH) < dr.get(GregorianCalendar.DAY_OF_MONTH)) {
                    // отдельный случай - в случае равенства месяцев, 
                    // но меньшего дня месяца в дате проверки - корректируем
                    age--;
                }
                r.setAge(age);
            }

            // вычисляем пол
            String sex = "";
            for (ReestrPMPKFull r : reestr) {
                String fio = r.getFio();
                if ((fio.endsWith("на")) || (fio.toUpperCase().endsWith("КЫЗЫ"))
                        || (fio.toUpperCase().endsWith("КИЗИ"))) {
                    sex = "ж";
                } else if ((fio.endsWith("ич")) || (fio.toUpperCase().endsWith("ОГЛЫ"))
                        || (fio.toUpperCase().endsWith("ОГЛИ")) || (fio.toUpperCase().endsWith("УГЛИ"))) {
                    sex = "м";
                }
                r.setSex(sex);
            }

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_reestr_PMPK_full_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                String pmpkShname = centerNameFacade.findById(1).getPmpkShname();
                try {
                    Xls.printReestrPMPKFull(response.getOutputStream(), d1, d2, reestr, pmpkShname);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("ipraprikaz")) { // печать приказа ИПРА
            String ipraIdS = request.getParameter("ipra");
            Ipra18 ipra = null;
            try {
                ipra = ipra18Facade.findById(Integer.parseInt(ipraIdS));
            } catch (Exception ex) {
            }

            if (ipra != null) {
                Ipra18Prikaz prikaz = ipra18PrikazFacade.findByIpra(ipra);
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String prikazDate = curDateFormat.format(curDate);
                String fileName = prikazDate + "_prikaz_ipra.docx";
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessin‌​gml.document");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Wrd.ipraPrikaz(response.getOutputStream(), ipra, prikaz);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("ipraotchetcenter")) {   // ИПРА отчет ОЦППМСП
            String ipraIdS = request.getParameter("ipra");
            String old = request.getParameter("old");
            Ipra ipra = null;
            Ipra18 ipra18 = null;
            Ipra18Prikaz prikaz = null;
            IpraPerechen perechen = null;
            List<IpraEduCondition> conditions = new ArrayList<>();

            if (old.equals("isold")) {
                try {
                    ipra = ipraFacade.findById(Integer.parseInt(ipraIdS));
                } catch (Exception ex) {
                }

            } else {
                try {
                    ipra18 = ipra18Facade.findById(Integer.parseInt(ipraIdS));
                } catch (Exception ex) {
                }
                if (ipra18 != null) {
                    prikaz = ipra18PrikazFacade.findByIpra(ipra18);
                    try {
                        perechen = ipraPerechenFacade.findByIpra18(ipra18);
                        conditions = ipraEduConditionFacade.findByIpraPerechen(perechen);
                    } catch (Exception ex) {
                    }
                }
            }
            if ((ipra18 != null) || (ipra != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String prikazDate = curDateFormat.format(curDate);
                String fileName = prikazDate + "_ipra_otchet_center.docx";
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessin‌​gml.document");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    if (ipra18 != null) {
                        Wrd.ipra18OtchetCenter(response.getOutputStream(), ipra18, prikaz, conditions);
                    } else if (ipra != null) {
                        Wrd.ipraOtchetCenter(response.getOutputStream(), ipra);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("ipralettertodo")) { // ИПРА письмо в ДО 
            String ipraIdS = request.getParameter("ipra");
            String old = request.getParameter("old");
            Ipra ipra = null;
            Ipra18 ipra18 = null;
            Ipra18Prikaz prikaz = null;

            if (old.equals("isold")) {
                try {
                    ipra = ipraFacade.findById(Integer.parseInt(ipraIdS));
                } catch (Exception ex) {
                }

            } else {
                try {
                    ipra18 = ipra18Facade.findById(Integer.parseInt(ipraIdS));
                } catch (Exception ex) {
                }
                if (ipra18 != null) {
                    prikaz = ipra18PrikazFacade.findByIpra(ipra18);
                }
            }
            if ((ipra18 != null) || (ipra != null)) {
                MySingleton mySingleton = MySingleton.getInstance();
                if (ipra18 != null) {
                    if (prikaz.getIpra18prikazOtchcenterN() == null) {
                        prikaz.setIpra18prikazOtchcenterN(mySingleton.getNextNomer(ipraIshnomFacade));
                        ipra18PrikazFacade.edit(prikaz);
                    }
                } else if (ipra != null) {
                    if (ipra.getIpraOtchcenterN() == null) {
                        ipra.setIpraOtchcenterN(mySingleton.getNextNomer(ipraIshnomFacade));
                        ipraFacade.edit(ipra);
                    }
                }

                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String prikazDate = curDateFormat.format(curDate);
                String fileName = prikazDate + "_ipra_to_DO.docx";
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessin‌​gml.document");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    if (ipra18 != null) {
                        Wrd.ipra18LetterToDO(response.getOutputStream(), ipra18, prikaz, user);
                    } else if (ipra != null) {
                        Wrd.ipraLetterToDo(response.getOutputStream(), ipra, user);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("ipraotchetdo")) {   // ИПРА отчёт ДО
            String ipraIdS = request.getParameter("ipra");
            String old = request.getParameter("old");
            Ipra ipra = null;
            Ipra18 ipra18 = null;
            Ipra18Prikaz prikaz = null;

            if (old.equals("isold")) {
                try {
                    ipra = ipraFacade.findById(Integer.parseInt(ipraIdS));
                } catch (Exception ex) {
                }

            } else {
                try {
                    ipra18 = ipra18Facade.findById(Integer.parseInt(ipraIdS));
                } catch (Exception ex) {
                }
                if (ipra18 != null) {
                    prikaz = ipra18PrikazFacade.findByIpra(ipra18);
                }
            }
            if ((ipra18 != null) || (ipra != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String prikazDate = curDateFormat.format(curDate);
                String fileName = prikazDate + "_ipra_otchet_DO.docx";
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessin‌​gml.document");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    if (ipra18 != null) {
                        Wrd.ipra18OtchetDO(response.getOutputStream(), ipra18, prikaz);
                    } else if (ipra != null) {
                        Wrd.ipraOtchetDO(response.getOutputStream(), ipra);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("ipralettertomse")) {    // ИПРА письмо в МСЭ
            String ipraIdS = request.getParameter("ipra");
            String old = request.getParameter("old");
            Ipra ipra = null;
            Ipra18 ipra18 = null;
            Ipra18Prikaz prikaz = null;

            if (old.equals("isold")) {
                try {
                    ipra = ipraFacade.findById(Integer.parseInt(ipraIdS));
                } catch (Exception ex) {
                }

            } else {
                try {
                    ipra18 = ipra18Facade.findById(Integer.parseInt(ipraIdS));
                } catch (Exception ex) {
                }
                if (ipra18 != null) {
                    prikaz = ipra18PrikazFacade.findByIpra(ipra18);
                }
            }
            if ((ipra18 != null) || (ipra != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String prikazDate = curDateFormat.format(curDate);
                String fileName = prikazDate + "_ipra_to_MSE.docx";
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessin‌​gml.document");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    if (ipra18 != null) {
                        Wrd.ipra18LetterToMSE(response.getOutputStream(), ipra18, prikaz);
                    } else if (ipra != null) {
                        Wrd.ipraLetterToMSE(response.getOutputStream(), ipra);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("ipra18red")) {  // ИПРА2018 сроки
            List<Ipra18> findRed = ipra18Facade.findRed();
            List<Ipra18Prikaz> ipraPrikazList = new ArrayList<>();
            for (Ipra18 ipra : findRed) {
                Ipra18Prikaz ip = ipra18PrikazFacade.findByIpra(ipra);
                ipraPrikazList.add(ip);
            }
            SimpleDateFormat curDateFormat = new SimpleDateFormat();
            curDateFormat.applyPattern("ddMMyyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);
            String printDate = curDateFormat.format(curDate);
            String fileName = "Ipra-" + printDate + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
            try {
                Xls.printIpra18Red(response.getOutputStream(), findRed, ipraPrikazList);
            } catch (Exception ex) {
                Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (type.equals("ipra18svod")) { // ИПРА2018 свод
            List<Ipra18> ipraAllCur = ipra18Facade.findAllCur();
            Collections.sort(ipraAllCur, new Ipra18Comparator());
            List<Ipra18Prikaz> ipraPrikazList = new ArrayList<>();
            for (Ipra18 ipra : ipraAllCur) {
                Ipra18Prikaz ip = ipra18PrikazFacade.findByIpra(ipra);
                ipraPrikazList.add(ip);
            }
            SimpleDateFormat curDateFormat = new SimpleDateFormat();
            curDateFormat.applyPattern("ddMMyyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);
            String printDate = curDateFormat.format(curDate);
            String fileName = "Ipra18Svod-" + printDate + ".xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
            try {
                Xls.printIpra18Svod(response.getOutputStream(), ipraAllCur, ipraPrikazList);
            } catch (Exception ex) {
                Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (type.equals("tpmpkreq")) {   // ИПРА запрос в ТПМПК
            String idVS = request.getParameter("ipra");
            String[] idV = idVS.split(";");
            List<Ipra18> ipraList = new ArrayList<>();
            for (String id : idV) {
                try {
                    Ipra18 ipra = ipra18Facade.findById(Integer.parseInt(id));
                    ipraList.add(ipra);
                } catch (Exception ex) {
                }
            }
            // простановка номеров и выбор списка детей
            MySingleton mySingleton = MySingleton.getInstance();
            String nomer = mySingleton.getNextNomer(ipraIshnomFacade);
            List<Children> children = new ArrayList<>();
            List<SprOmsu> omsu = new ArrayList<>();
            for (Ipra18 ri : ipraList) {
                Ipra18Prikaz pr = ipra18PrikazFacade.findByIpra(ri);
                omsu.add(pr.getSpromsuId());
                pr.setIpra18prikazTpmpkN(nomer);
                ipra18PrikazFacade.edit(pr);
                children.add(ri.getChildId());
            }

            // выгрузка
            SprOtherPmpk tpmpk = ipra18PrikazFacade.findByIpra(ipraList.get(0)).getSprotherpmpkId();
            Date reqDate = ipra18PrikazFacade.findByIpra(ipraList.get(0)).getIpra18prikazPerechD();

            SimpleDateFormat curDateFormat = new SimpleDateFormat();
            curDateFormat.applyPattern("ddMMyyyy");
            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);
            String printDate = curDateFormat.format(curDate);
            String fileName = printDate + "_ipra_req_tpmpk.docx";
            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessin‌​gml.document");
            response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
            try {
                Wrd.printIpra18ReqTpmpk(response.getOutputStream(), children, omsu, tpmpk, reqDate, nomer);
            } catch (Exception ex) {
                Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (type.equals("ipraperechen")) {   // ИПРА перечень
            String ipraIdS = request.getParameter("ipra");
            Ipra18 ipra = null;
            try {
                ipra = ipra18Facade.findById(Integer.parseInt(ipraIdS));
            } catch (Exception ex) {
            }

            if (ipra != null) {
                Ipra18Prikaz prikaz = ipra18PrikazFacade.findByIpra(ipra);
                IpraPerechen perechen = ipraPerechenFacade.findByIpra18(ipra);
                List<IpraEduCondition> conditions = ipraEduConditionFacade.findByIpraPerechen(perechen);
                // простановка номеров
                MySingleton mySingleton = MySingleton.getInstance();
                if (prikaz.getIpra18prikazPerechN() == null) {
                    String nomer = mySingleton.getNextNomer(ipraIshnomFacade);
                    prikaz.setIpra18prikazPerechN(nomer);
                    ipra18PrikazFacade.edit(prikaz);
                }

                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String perechenDate = curDateFormat.format(curDate);
                String fileName = perechenDate + "_perechen_ipra.docx";
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessin‌​gml.document");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Wrd.ipraPerechen(response.getOutputStream(), ipra, prikaz, conditions, user);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }

        } else if (type.equals("ipra18noinfo")) {    // ИПРА2018 реестр ИПРА без запроса/отказа
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateN = null;
            try {
                dateN = dateFormat.parse(date1);
            } catch (ParseException ex) {
                Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            Date dateK = null;
            try {
                dateK = dateFormat.parse(date2);
            } catch (ParseException ex) {
                Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            String reg = request.getParameter("reg");
            Integer regId = 0;
            try {
                regId = Integer.parseInt(reg);
            } catch (Exception ex) {
            }
            SprRegion region = null;
            if (regId > 0) {
                region = sprRegionFacade.findById(regId);
            }
            List<Ipra18> ipra18NoInfoList = new ArrayList<>();
            if ((dateN != null) && (dateK != null)) {
                if (region != null) {
                    ipra18NoInfoList = ipra18Facade.findNoInfoReg(dateN, dateK, region);
                } else {
                    ipra18NoInfoList = ipra18Facade.findNoInfo(dateN, dateK);
                }
            }

            if (ipra18NoInfoList.size() > 0) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String printDate = curDateFormat.format(curDate);
                String fileName = "Ipra18NoInfo-" + printDate + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printIpra18NoInfo(response.getOutputStream(), ipra18NoInfoList, dateN, dateK);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (type.equals("consultage")) {

        } else if (type.equals("consultagereestr")) {
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            long curTime = System.currentTimeMillis();
            Date curDate = new Date(curTime);

            String ageNS = request.getParameter("agen");
            String ageKS = request.getParameter("agek");
            Integer ageN = 0;
            Integer ageK = 0;
            try {
                ageN = Integer.parseInt(ageNS);
                ageK = Integer.parseInt(ageKS);
            } catch (Exception ex) {
            }
/////!!!
            List<StandartCount> uslAges = childrenFacade.uslAges(d1, d2, ageN, ageK);

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_consult_vozrast" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printAge(response.getOutputStream(), uslAges, d1, d2, ageN, ageK);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("statuslkat")) {  // статистика по услугам и категориям
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            List<CountClient> countCl = priyomFacade.countClientUslKat(d1, d2);
            //    List<CountPriyom> countPr = priyomFacade.countPriyomUslKat(d1, d2);
            List<CountClient> countOsnCl = priyomFacade.countOsnClientUslKat(d1, d2);
            List<CountPriyom> countPrCl = priyomFacade.countPrClUslKat(d1, d2);

            List<Gz> gz = new ArrayList<>();

            List<SprUsl> sprusl = sprUslFacade.findAllUsl();
            for (SprUsl usl : sprusl) {
                Gz gzUsl = new Gz();
                gzUsl.setOsnUsl(usl.getSprosnuslId().getSprosnuslName());
                gzUsl.setUsl(usl.getSpruslName());
                gzUsl.setCenter(Boolean.FALSE);
                Integer countCh = 0;
                Integer countPar = 0;
                Integer countPed = 0;
                for (CountClient cl : countCl) {
                    if ((cl.getUsl().equals(usl.getSpruslName()))
                            && (cl.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (cl.getKatClient().equals("children")) {
                            countCh += cl.getCount();
                        } else if (cl.getKatClient().equals("parents")) {
                            countPar += cl.getCount();
                        } else if (cl.getKatClient().equals("ped")) {
                            countPed += cl.getCount();
                        }
                    }
                }
                gzUsl.setClientCh(countCh);
                gzUsl.setClientPar(countPar);
                gzUsl.setClientPed(countPed);
                /*    countCh = 0;
                countPar = 0;
                countPed = 0;
                for (CountPriyom pr : countPr) {
                    if ((pr.getUsl().equals(usl.getSpruslName()))
                            && (pr.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (pr.getKatClient().equals("children")) {
                            countCh += pr.getCount();
                        } else if (pr.getKatClient().equals("parents")) {
                            countPar += pr.getCount();
                        } else if (pr.getKatClient().equals("ped")) {
                            countPed += pr.getCount();
                        }
                    }
                }
                gzUsl.setPriyomCh(countCh);
                gzUsl.setPriyomPar(countPar);
                gzUsl.setPriyomPed(countPed);*/
                countCh = 0;
                countPar = 0;
                countPed = 0;
                for (CountPriyom pr : countPrCl) {
                    if ((pr.getUsl().equals(usl.getSpruslName()))
                            && (pr.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (pr.getKatClient().equals("children")) {
                            countCh += pr.getCount();
                        } else if (pr.getKatClient().equals("parents")) {
                            countPar += pr.getCount();
                        } else if (pr.getKatClient().equals("ped")) {
                            countPed += pr.getCount();
                        }
                    }
                }
                gzUsl.setPrclCh(countCh);
                gzUsl.setPrclPar(countPar);
                gzUsl.setPrclPed(countPed);
                gz.add(gzUsl);
            }
            // в ОЦППМСП
            for (SprUsl usl : sprusl) {
                Gz gzUsl = new Gz();
                gzUsl.setOsnUsl(usl.getSprosnuslId().getSprosnuslName());
                gzUsl.setUsl(usl.getSpruslName());
                gzUsl.setCenter(Boolean.TRUE);
                Integer countCh = 0;
                Integer countPar = 0;
                Integer countPed = 0;
                for (CountClient cl : countCl) {
                    if ((cl.getUsl().equals(usl.getSpruslName()))
                            && (cl.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (cl.getKatClient().equals("children")) {
                            countCh += cl.getCount();
                        } else if (cl.getKatClient().equals("parents")) {
                            countPar += cl.getCount();
                        } else if (cl.getKatClient().equals("ped")) {
                            countPed += cl.getCount();
                        }
                    }
                }
                gzUsl.setClientCh(countCh);
                gzUsl.setClientPar(countPar);
                gzUsl.setClientPed(countPed);
                /*    countCh = 0;
                countPar = 0;
                countPed = 0;
                for (CountPriyom pr : countPr) {
                    if ((pr.getUsl().equals(usl.getSpruslName()))
                            && (pr.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (pr.getKatClient().equals("children")) {
                            countCh += pr.getCount();
                        } else if (pr.getKatClient().equals("parents")) {
                            countPar += pr.getCount();
                        } else if (pr.getKatClient().equals("ped")) {
                            countPed += pr.getCount();
                        }
                    }
                }
                gzUsl.setPriyomCh(countCh);
                gzUsl.setPriyomPar(countPar);
                gzUsl.setPriyomPed(countPed);*/
                countCh = 0;
                countPar = 0;
                countPed = 0;
                for (CountPriyom pr : countPrCl) {
                    if ((pr.getUsl().equals(usl.getSpruslName()))
                            && (pr.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (pr.getKatClient().equals("children")) {
                            countCh += pr.getCount();
                        } else if (pr.getKatClient().equals("parents")) {
                            countPar += pr.getCount();
                        } else if (pr.getKatClient().equals("ped")) {
                            countPed += pr.getCount();
                        }
                    }
                }
                gzUsl.setPrclCh(countCh);
                gzUsl.setPrclPar(countPar);
                gzUsl.setPrclPed(countPed);
                gz.add(gzUsl);
            }

// по основным услугам
            List<SprOsnusl> sprOsnusl = sprOsnuslFacade.findAll();
            for (SprOsnusl osn : sprOsnusl) {
                Gz gzUsl = new Gz();
                gzUsl.setOsnUsl(osn.getSprosnuslName());
                gzUsl.setUsl("итого");
                gzUsl.setCenter(Boolean.FALSE);
                Integer countCh = 0;
                Integer countPar = 0;
                Integer countPed = 0;
                for (CountClient cl : countOsnCl) {
                    if ((cl.getOsnUsl().equals(osn.getSprosnuslName()))
                            && (cl.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (cl.getKatClient().equals("children")) {
                            countCh += cl.getCount();
                        } else if (cl.getKatClient().equals("parents")) {
                            countPar += cl.getCount();
                        } else if (cl.getKatClient().equals("ped")) {
                            countPed += cl.getCount();
                        }
                    }
                }
                gzUsl.setClientCh(countCh);
                gzUsl.setClientPar(countPar);
                gzUsl.setClientPed(countPed);
                /*countCh = 0;
                countPar = 0;
                countPed = 0;
                for (CountPriyom pr : countPr) {
                    if ((pr.getUsl().equals(osn.getSprosnuslName()))
                            && (pr.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (pr.getKatClient().equals("children")) {
                            countCh += pr.getCount();
                        } else if (pr.getKatClient().equals("parents")) {
                            countPar += pr.getCount();
                        } else if (pr.getKatClient().equals("ped")) {
                            countPed += pr.getCount();
                        }
                    }
                }
                gzUsl.setPriyomCh(countCh);
                gzUsl.setPriyomPar(countPar);
                gzUsl.setPriyomPed(countPed);*/
                countCh = 0;
                countPar = 0;
                countPed = 0;
                for (CountPriyom pr : countPrCl) {
                    if ((pr.getOsnUsl().equals(osn.getSprosnuslName()))
                            && (pr.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (pr.getKatClient().equals("children")) {
                            countCh += pr.getCount();
                        } else if (pr.getKatClient().equals("parents")) {
                            countPar += pr.getCount();
                        } else if (pr.getKatClient().equals("ped")) {
                            countPed += pr.getCount();
                        }
                    }
                }
                gzUsl.setPrclCh(countCh);
                gzUsl.setPrclPar(countPar);
                gzUsl.setPrclPed(countPed);
                gz.add(gzUsl);
            }

            // в ОЦППМСП
            for (SprOsnusl osn : sprOsnusl) {
                Gz gzUsl = new Gz();
                gzUsl.setOsnUsl(osn.getSprosnuslName());
                gzUsl.setUsl("итого");
                gzUsl.setCenter(Boolean.TRUE);
                Integer countCh = 0;
                Integer countPar = 0;
                Integer countPed = 0;
                for (CountClient cl : countOsnCl) {
                    if ((cl.getOsnUsl().equals(osn.getSprosnuslName()))
                            && (cl.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (cl.getKatClient().equals("children")) {
                            countCh += cl.getCount();
                        } else if (cl.getKatClient().equals("parents")) {
                            countPar += cl.getCount();
                        } else if (cl.getKatClient().equals("ped")) {
                            countPed += cl.getCount();
                        }
                    }
                }
                gzUsl.setClientCh(countCh);
                gzUsl.setClientPar(countPar);
                gzUsl.setClientPed(countPed);
                /*countCh = 0;
                countPar = 0;
                countPed = 0;
                for (CountPriyom pr : countPr) {
                    if ((pr.getUsl().equals(osn.getSprosnuslName()))
                            && (pr.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (pr.getKatClient().equals("children")) {
                            countCh += pr.getCount();
                        } else if (pr.getKatClient().equals("parents")) {
                            countPar += pr.getCount();
                        } else if (pr.getKatClient().equals("ped")) {
                            countPed += pr.getCount();
                        }
                    }
                }
                gzUsl.setPriyomCh(countCh);
                gzUsl.setPriyomPar(countPar);
                gzUsl.setPriyomPed(countPed);*/
                countCh = 0;
                countPar = 0;
                countPed = 0;
                for (CountPriyom pr : countPrCl) {
                    if ((pr.getOsnUsl().equals(osn.getSprosnuslName()))
                            && (pr.getIsCenter().equals(gzUsl.getCenter()))) {
                        if (pr.getKatClient().equals("children")) {
                            countCh += pr.getCount();
                        } else if (pr.getKatClient().equals("parents")) {
                            countPar += pr.getCount();
                        } else if (pr.getKatClient().equals("ped")) {
                            countPed += pr.getCount();
                        }
                    }
                }
                gzUsl.setPrclCh(countCh);
                gzUsl.setPrclPar(countPar);
                gzUsl.setPrclPed(countPed);
                gz.add(gzUsl);
            }

            if ((d1 != null) && (d2 != null)) {
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_stat_uslkat" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printOtchetUslKat(response.getOutputStream(), d1, d2, gz);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }

        } else if (type.equals("ranniy")) {  // реестры по раннему возрасту
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if ((d1 != null) && (d2 != null)) {
                // дети РВ на период
                Calendar dateC = Calendar.getInstance();
                dateC.setTime(d1);
                dateC.set(Calendar.YEAR, dateC.get(Calendar.YEAR) - 3); // определяем дату за 3 года до начала периода
                Date date = dateC.getTime();
                List<Children> allChildren = childrenFacade.findAllChildrenDrAfter(date);  // все дети, которым на начало периода не больше 3 лет
                List<Children> rvChildren = new ArrayList<>();
                Age age = new Age(3, 0);
                for (Children child : allChildren) {
                    if (child.getAgeOnDate(d1).younger(age)) {
                        rvChildren.add(child);
                    }
                }
                // дети РВ прошедшие ПМПК                
                List<Reestr> pmpkReestr = new ArrayList<>();
                // дети РВ - массаж
                List<Reestr> massajReestr = new ArrayList<>();
                // дети РВ - прочие услуги
                List<Reestr> uslReestr = new ArrayList<>();
                // законные представители детей РВ - консультирование
                List<Reestr> consultReestr = new ArrayList<>();
                for (Children child : rvChildren) {
                    List<Pmpk> pmpkList = pmpkFacade.findPmpkByChildAndDate(child, d1, d2);    // ищем ПМПК
                    for (Pmpk pmpk : pmpkList) {
                        // если ребёнок на момент приёма младше 3 лет
                        if (child.getAgeOnDate(pmpk.getPrclId().getPriyomId().getPriyomDate()).younger(age)) {
                            // создаём запись для реестра
                            Reestr r = new Reestr();
                            r.setId(child.getChildId());
                            r.setFam(child.getChildFam());
                            r.setName(child.getChildName());
                            r.setPatr(child.getChildPatr());
                            r.setDr(child.getChildDr());
                            r.setReg(child.getSprregId().getSprregName());
                            r.setDate(pmpk.getPrclId().getPriyomId().getPriyomDate());  // дата ПМПК
                            r.setRegPr(pmpk.getPrclId().getPriyomId().getSprregId().getSprregName());   // место проведения ПМПК
                            pmpkReestr.add(r);
                        }
                    }
                    List<PriyomClient> priyomList = priyomClientFacade.findByClientAndDate(child.getChildId(), "children", d1, d2);
                    for (PriyomClient pc : priyomList) {
                        // если ребёнок на момент приёма младше 3 лет
                        if (child.getAgeOnDate(pc.getPriyomId().getPriyomDate()).younger(age)) {
                            // если не ПМПК
                            if (pc.getPriyomId().getSpruslId().getSpruslId() > 1) {
                                // создаём запись для реестра
                                Reestr r = new Reestr();
                                r.setId(child.getChildId());
                                r.setFam(child.getChildFam());
                                r.setName(child.getChildName());
                                r.setPatr(child.getChildPatr());
                                r.setDr(child.getChildDr());
                                r.setReg(child.getSprregId().getSprregName());
                                r.setDate(pc.getPriyomId().getPriyomDate());    // дата приёма
                                // проверяем, массаж или другая услуга
                                if (!pc.getPriyomId().getSpruslId().getSpruslName().equalsIgnoreCase("массаж")) {
                                    r.setRegPr(pc.getPriyomId().getSprregId().getSprregName()); // место проведения услуги
                                    r.setUsl(pc.getPriyomId().getSpruslId().getSpruslName());   // название услуги
                                    // ищем сотрудников к приёму
                                    List<PriyomSotrud> sotrList = priyomSotrudFacade.findByPriyom(pc.getPriyomId());
                                    String sotrud = "";
                                    for (PriyomSotrud s : sotrList) {
                                        if (!sotrud.equals("")) {
                                            sotrud += ", ";
                                        }
                                        sotrud += s.getSotruddolgnId().getSotrudId().getSotrudFIO();
                                    }
                                    r.setInfo(sotrud);
                                    // добавляем к реестру других услуг
                                    uslReestr.add(r);
                                } else {
                                    // добавляем к реестру массажа
                                    massajReestr.add(r);
                                }
                            }
                        }
                    }
                    // ребенок как субъект в услугах
                    List<PriyomSubject> subPriyom = priyomSubjectFacade.findBySubjectAndDate(child, d1, d2);
                    for (PriyomSubject subj : subPriyom) {
                        if (child.getAgeOnDate(subj.getPriyomId().getPriyomDate()).younger(age)) {
                            // ищем законых представителей (клиентов) к приёму
                            List<PriyomClient> pcList = priyomClientFacade.findByPriyom(subj.getPriyomId());
                            for (PriyomClient pc : pcList) {
                                if (pc.getPrclKatcl().equals("parents")) {
                                    Reestr r = new Reestr();
                                    Parents parent = parentsFacade.findById(pc.getClientId());
                                    r.setIdPar(parent.getParentId());
                                    r.setFamPar(parent.getParentFam());
                                    r.setNamePar(parent.getParentName());
                                    r.setPatrPar(parent.getParentPatr());
                                    r.setId(child.getChildId());
                                    r.setFam(child.getChildFam());
                                    r.setName(child.getChildName());
                                    r.setPatr(child.getChildPatr());
                                    r.setDr(child.getChildDr());
                                    r.setReg(child.getSprregId().getSprregName());
                                    r.setDate(subj.getPriyomId().getPriyomDate());
                                    r.setRegPr(subj.getPriyomId().getSprregId().getSprregName()); // место проведения услуги
                                    r.setUsl(subj.getPriyomId().getSpruslId().getSpruslName());   // название услуги
                                    // ищем сотрудников к приёму
                                    List<PriyomSotrud> sotrList = priyomSotrudFacade.findByPriyom(subj.getPriyomId());
                                    String sotrud = "";
                                    for (PriyomSotrud s : sotrList) {
                                        if (!sotrud.equals("")) {
                                            sotrud += ", ";
                                        }
                                        sotrud += s.getSotruddolgnId().getSotrudId().getSotrudFIO();
                                    }
                                    r.setInfo(sotrud);
                                    // добавляем к реестру консультаций
                                    consultReestr.add(r);
                                }
                            }
                        }
                    }
                }

                // статистика
                Map<List<String>, Integer> countChildren = new HashMap<>(); // кол-во детей
                Map<List<String>, Integer> countParents = new HashMap<>();  // кол-во родителей
                // неповторяющиеся дети
                Set<Integer> childrenSet = new HashSet<>();
                // неповторяющиеся родители
                Set<Integer> parentsSet = new HashSet<>();
                // заодно соберём районы
                Set<String> childrenRegSet = new HashSet<>();
                Set<String> parentsRegSet = new HashSet<>();
                // ... и услуги
                Set<String> childrenUslSet = new TreeSet<>();
                Set<String> parentsUslSet = new TreeSet<>();
                for (Reestr r : pmpkReestr) {
                    childrenSet.add(r.getId());
                    childrenRegSet.add(r.getReg());
                }
                for (Reestr r : massajReestr) {
                    childrenSet.add(r.getId());
                    childrenRegSet.add(r.getReg());
                }
                for (Reestr r : uslReestr) {
                    childrenSet.add(r.getId());
                    childrenRegSet.add(r.getReg());
                    childrenUslSet.add(r.getUsl());
                }
                for (Reestr r : consultReestr) {
                    parentsSet.add(r.getIdPar());
                    parentsRegSet.add(r.getReg());
                    parentsUslSet.add(r.getUsl());
                }

                String vsego = "Всего";
                String kol = "количество";
                String obr = "обращения";
                String keyCh = "Количество детей";
                String keyPar = "Количество законных представителей";
                String keyObrCh = "Количество обращений детей";
                String keyObrPar = "Количество обращений законных представителей";
                List<String> key1 = Arrays.asList(keyCh, kol, vsego);
                countChildren.put(key1, childrenSet.size());
                List<String> key2 = Arrays.asList(keyObrCh, obr, vsego);
                countChildren.put(key2, pmpkReestr.size() + massajReestr.size() + uslReestr.size());
                List<String> key3 = Arrays.asList(keyPar, kol, vsego);
                countParents.put(key3, parentsSet.size());
                List<String> key4 = Arrays.asList(keyObrPar, obr, vsego);
                countParents.put(key4, consultReestr.size());
                // по районам дети                
                for (String reg : childrenRegSet) {
                    childrenSet = new HashSet<>();
                    Integer c = 0;
                    for (Reestr r : pmpkReestr) {
                        if (r.getReg().equals(reg)) {
                            childrenSet.add(r.getId());
                            c++;
                        }
                    }
                    for (Reestr r : massajReestr) {
                        if (r.getReg().equals(reg)) {
                            childrenSet.add(r.getId());
                            c++;
                        }
                    }
                    for (Reestr r : uslReestr) {
                        if (r.getReg().equals(reg)) {
                            childrenSet.add(r.getId());
                            c++;
                        }
                    }
                    List<String> key5 = Arrays.asList(keyCh, kol, reg);
                    countChildren.put(key5, childrenSet.size());
                    List<String> key6 = Arrays.asList(keyObrCh, obr, reg);
                    countChildren.put(key6, c);
                }
                // по районам родители
                for (String reg : parentsRegSet) {
                    parentsSet = new HashSet<>();
                    Integer c = 0;
                    for (Reestr r : consultReestr) {
                        if (r.getReg().equals(reg)) {
                            parentsSet.add(r.getIdPar());
                            c++;
                        }
                    }
                    List<String> key5 = Arrays.asList(keyPar, kol, reg);
                    countParents.put(key5, parentsSet.size());
                    List<String> key6 = Arrays.asList(keyObrPar, obr, reg);
                    countParents.put(key6, c);
                }
                // по услугам и районам дети
                // ПМПК
                Integer sObr = 0;
                Integer sKol = 0;
                for (String reg : childrenRegSet) {
                    childrenSet = new HashSet<>();
                    Integer c = 0;
                    for (Reestr r : pmpkReestr) {
                        if (r.getReg().equals(reg)) {
                            childrenSet.add(r.getId());
                            c++;
                        }
                    }
                    List<String> key5 = Arrays.asList("ПМПК", kol, reg);
                    countChildren.put(key5, childrenSet.size());
                    List<String> key6 = Arrays.asList("ПМПК", obr, reg);
                    countChildren.put(key6, c);
                    sKol += childrenSet.size();
                }
                sObr = pmpkReestr.size();
                List<String> key9 = Arrays.asList("ПМПК", kol, vsego);
                countChildren.put(key9, sKol);
                List<String> key10 = Arrays.asList("ПМПК", obr, vsego);
                countChildren.put(key10, sObr);
                // Массаж
                sKol = 0;
                for (String reg : childrenRegSet) {
                    childrenSet = new HashSet<>();
                    Integer c = 0;
                    for (Reestr r : massajReestr) {
                        if (r.getReg().equals(reg)) {
                            childrenSet.add(r.getId());
                            c++;
                        }
                    }
                    List<String> key5 = Arrays.asList("Массаж", kol, reg);
                    countChildren.put(key5, childrenSet.size());
                    List<String> key6 = Arrays.asList("Массаж", obr, reg);
                    countChildren.put(key6, c);
                    sKol += childrenSet.size();
                }
                sObr = massajReestr.size();
                List<String> key7 = Arrays.asList("Массаж", kol, vsego);
                countChildren.put(key7, sKol);
                List<String> key8 = Arrays.asList("Массаж", obr, vsego);
                countChildren.put(key8, sObr);

                // Другие услуги
                for (String usl : childrenUslSet) {
                    sObr = 0;
                    sKol = 0;
                    for (String reg : childrenRegSet) {
                        childrenSet = new HashSet<>();
                        Integer c = 0;
                        for (Reestr r : uslReestr) {
                            if (r.getReg().equals(reg) && r.getUsl().equals(usl)) {
                                childrenSet.add(r.getId());
                                c++;
                            }
                        }
                        List<String> key5 = Arrays.asList(usl, kol, reg);
                        countChildren.put(key5, childrenSet.size());
                        List<String> key6 = Arrays.asList(usl, obr, reg);
                        countChildren.put(key6, c);
                        sObr += c;
                        sKol += childrenSet.size();
                    }
                    List<String> key5 = Arrays.asList(usl, kol, vsego);
                    countChildren.put(key5, sKol);
                    List<String> key6 = Arrays.asList(usl, obr, vsego);
                    countChildren.put(key6, sObr);
                }
                // по услугам и районам родители                
                for (String usl : parentsUslSet) {
                    sObr = 0;
                    sKol = 0;
                    for (String reg : parentsRegSet) {
                        parentsSet = new HashSet<>();
                        Integer c = 0;
                        for (Reestr r : consultReestr) {
                            if (r.getReg().equals(reg) && r.getUsl().equals(usl)) {
                                parentsSet.add(r.getIdPar());
                                c++;
                            }
                        }
                        List<String> key5 = Arrays.asList(usl, kol, reg);
                        countParents.put(key5, parentsSet.size());
                        List<String> key6 = Arrays.asList(usl, obr, reg);
                        countParents.put(key6, c);
                        sObr += c;
                        sKol += parentsSet.size();
                    }
                    List<String> key5 = Arrays.asList(usl, kol, vsego);
                    countParents.put(key5, sKol);
                    List<String> key6 = Arrays.asList(usl, obr, vsego);
                    countParents.put(key6, sObr);
                }
                Set<String> regSet = new TreeSet<>();
                regSet.addAll(childrenRegSet);
                regSet.addAll(parentsRegSet);
                childrenUslSet.add("ПМПК");
                childrenUslSet.add("Массаж");
                Collections.sort(pmpkReestr, new ReestrComparator());
                Collections.sort(massajReestr, new ReestrComparator());
                Collections.sort(uslReestr, new ReestrComparator());
                Collections.sort(consultReestr, new ReestrComparator());

                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_ranniy" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printRanniy(response.getOutputStream(), d1, d2, countChildren, countParents,
                            pmpkReestr, massajReestr, uslReestr, consultReestr, regSet, childrenUslSet, parentsUslSet);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("consultpar")) {  // отчёт по консультированию родителей (категории)
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if ((d1 != null) && (d2 != null)) {
                // консультирование в период
                String kat = "parents";
                List<PriyomSubject> priyomSubj = priyomSubjectFacade.findByKatClientOnPeriod(kat, d1, d2);
                // всего родителей
                List<PriyomClient> clients = new ArrayList<>();
                for (PriyomSubject ps : priyomSubj) {
                    clients.addAll(priyomClientFacade.findByPriyom(ps.getPriyomId()));
                }
                // ранний возраст                
                List<PriyomClient> clients3 = new ArrayList<>();
                Age age = new Age(2, 11);   // 2 года 11 месяцев
                for (PriyomSubject ps : priyomSubj) {
                    if (ps.getChildId().getAgeOnDate(ps.getPriyomId().getPriyomDate()).younger(age)) {
                        clients3.addAll(priyomClientFacade.findByPriyom(ps.getPriyomId()));
                    }
                }
                // дети от 3 до 7 лет                
                List<PriyomClient> clients37 = new ArrayList<>();
                Age age2 = new Age(6, 11);  // 6 лет 11 месяцев
                for (PriyomSubject ps : priyomSubj) {
                    if ((ps.getChildId().getAgeOnDate(ps.getPriyomId().getPriyomDate()).older(age)) && (ps.getChildId().getAgeOnDate(ps.getPriyomId().getPriyomDate()).younger(age2))) {
                        clients37.addAll(priyomClientFacade.findByPriyom(ps.getPriyomId()));
                    }
                }
                // дети с ОВЗ и инвалидностью
                List<PriyomClient> clientsInvOVZ = new ArrayList<>();
                // дети с девиантным поведением
                List<PriyomClient> clientsOp = new ArrayList<>();
                // дети из замещающих семей
                List<PriyomClient> clientsZS = new ArrayList<>();

                for (PriyomSubject ps : priyomSubj) {
                    List<ChildStatus> statList = childStatusFacade.findByChildActOnDate(ps.getChildId(), ps.getPriyomId().getPriyomDate());
                    // проверка статусов
                    for (ChildStatus cs : statList) {
                        if (cs.getSprstatId().getSprstatName().equals("ДИ")) {
                            clientsInvOVZ.addAll(priyomClientFacade.findByPriyom(ps.getPriyomId()));
                        } else if (cs.getSprstatId().getSprstatName().equals("ОВЗ")) {
                            clientsInvOVZ.addAll(priyomClientFacade.findByPriyom(ps.getPriyomId()));
                        } else if (cs.getSprstatId().getSprstatName().equals("ОП")) {
                            clientsOp.addAll(priyomClientFacade.findByPriyom(ps.getPriyomId()));
                        } else if (cs.getSprstatId().getSprstatName().equals("ЗС")) {
                            clientsZS.addAll(priyomClientFacade.findByPriyom(ps.getPriyomId()));
                        }
                    }
                }
                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_consult" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                try {
                    Xls.printOtchetConsultPar(response.getOutputStream(), d1, d2, clients.size(), clients3.size(), clients37.size(),
                            clientsInvOVZ.size(), clientsOp.size(), clientsZS.size());
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (type.equals("pmpkageondate")) {   // реестр ПМПК полный с возрастом на дату
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String dateageS = request.getParameter("dateage");
            Date dateage = null;

            if (date1 != null) {
                try {
                    d1 = dateFormat.parse(date1);
                    d2 = dateFormat.parse(date2);
                    dateage = dateFormat.parse(dateageS);
                } catch (ParseException ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            String regIdS = request.getParameter("reg");
            Integer regId = 0;
            try {
                regId = Integer.parseInt(regIdS);
            } catch (Exception ex) {
            }
            SprRegion reg = null;
            if (regId != 0) {
                reg = sprRegionFacade.findById(regId);
            }
            if ((d1 != null) && (d2 != null) && (reg != null) && (dateage != null)) {
                SprStat stat = sprStatFacade.findByName("ОВЗ");
                List<ReestrEnt> reestrEnt = priyomFacade.reestrPmpkEnt(d1, d2, reg, stat);

                List<ReestrPMPKFull> reestr = new ArrayList<>();
                for (ReestrEnt re : reestrEnt) {
                    ReestrPMPKFull r = new ReestrPMPKFull();
                    r.setId(re.getChild().getChildId());
                    r.setFio(re.getChild().getFIO());
                    r.setDr(re.getChild().getChildDr());
                    r.setReg(re.getChild().getSprregId().getSprregName());
                    r.setDatep(re.getPriyom().getPriyomDate());
                    if (re.getPmpk().getSprobrId() != null) {
                        r.setObr(re.getPmpk().getSprobrId().getSprobrShname());
                    } else {
                        r.setObr("");
                    }
                    r.setDatek(re.getPmpk().getPmpkDatek());
                    r.setAge(re.getChild().getAgeOnDate(dateage).getYears());
                    r.setSex(re.getChild().getChildPol());
                    List<PmpkRek> reks = pmpkRekFacade.findByPmpk(re.getPmpk());
                    r.setAssist(Boolean.FALSE);
                    r.setTutor(Boolean.FALSE);
                    r.setPsy(Boolean.FALSE);
                    r.setLogo(Boolean.FALSE);
                    r.setDefolig(Boolean.FALSE);
                    r.setDeftiflo(Boolean.FALSE);
                    r.setDefsurdo(Boolean.FALSE);
                    r.setSoc(Boolean.FALSE);

                    for (PmpkRek rek : reks) {
                        if (rek.getSprrekId().getSprrekName().equals("ассистент (помощник)")) {
                            r.setAssist(Boolean.TRUE);
                        } else if (rek.getSprrekId().getSprrekName().equals("тьютор")) {
                            r.setTutor(Boolean.TRUE);
                        } else if (rek.getSprrekId().getSprrekName().equals("занятия педагога-психолога")) {
                            r.setPsy(Boolean.TRUE);
                        } else if (rek.getSprrekId().getSprrekName().equals("занятия учителя-логопеда")) {
                            r.setLogo(Boolean.TRUE);
                        } else if (rek.getSprrekId().getSprrekName().equals("занятия учителя-дефектолога (олигофренопедагога)")) {
                            r.setDefolig(Boolean.TRUE);
                        } else if (rek.getSprrekId().getSprrekName().equals("занятия учителя-дефектолога (тифлопедагога)")) {
                            r.setDeftiflo(Boolean.TRUE);
                        } else if (rek.getSprrekId().getSprrekName().equals("занятия учителя-дефектолога (сурдопедагога)")) {
                            r.setDefsurdo(Boolean.TRUE);
                        } else if (rek.getSprrekId().getSprrekName().equals("занятия социального педагога")) {
                            r.setSoc(Boolean.TRUE);
                        }
                    }
                    reestr.add(r);
                }

                SimpleDateFormat curDateFormat = new SimpleDateFormat();
                curDateFormat.applyPattern("ddMMyyyy");
                long curTime = System.currentTimeMillis();
                Date curDate = new Date(curTime);
                String otchetDate = curDateFormat.format(curDate);
                String fileName = otchetDate + "_reestr_PMPK_full_" + date1 + "-" + date2 + ".xls";
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "Attachment;filename= " + fileName);
                String pmpkShname = centerNameFacade.findById(1).getPmpkShname();
                try {
                    Xls.printReestrPMPKAgeOnDate(response.getOutputStream(), d1, d2, reestr, pmpkShname);
                } catch (Exception ex) {
                    Logger.getLogger(PrintServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

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

    private BigOtch getBigOtch(Date d1, Date d2, SprUsl usl, SotrudDolgn sotrudDolgn, Integer cent) {
        // статистика
        List<CountKatBig> countKatBigList = new ArrayList<>();
        // все дети
        List<CountKatBig> countChildren = priyomClientFacade.countKat("children", d1, d2, usl, sotrudDolgn, 0, cent);
        // дети старше 14 лет
        List<CountKatBig> countChildren14 = priyomClientFacade.countKat("children", d1, d2, usl, sotrudDolgn, 14, cent);
        // законные представители
        List<CountKatBig> countParents = priyomClientFacade.countKat("parents", d1, d2, usl, sotrudDolgn, 0, cent);
        // педагоги 
        List<CountKatBig> countPed = priyomClientFacade.countKat("ped", d1, d2, usl, sotrudDolgn, 0, cent);
        for (CountKatBig c : countChildren) {
            countKatBigList.add(c);
        }
        for (CountKatBig c : countChildren14) {
            countKatBigList.add(c);
        }
        for (CountKatBig c : countParents) {
            countKatBigList.add(c);
        }
        for (CountKatBig c : countPed) {
            countKatBigList.add(c);
        }

        // статистика по головам
        List<CountKatBig> countKatDistList = new ArrayList<>();
        // все дети
        List<CountKatBig> countChildrenD = priyomClientFacade.countKatDist("children", d1, d2, usl, sotrudDolgn, 0, cent);
        // дети старше 14 лет
        List<CountKatBig> countChildrenD14 = priyomClientFacade.countKatDist("children", d1, d2, usl, sotrudDolgn, 14, cent);
        // законные представители
        List<CountKatBig> countParentsD = priyomClientFacade.countKatDist("parents", d1, d2, usl, sotrudDolgn, 0, cent);
        // педагоги 
        List<CountKatBig> countPedD = priyomClientFacade.countKatDist("ped", d1, d2, usl, sotrudDolgn, 0, cent);
        for (CountKatBig c : countChildrenD) {
            countKatDistList.add(c);
        }
        for (CountKatBig c : countChildrenD14) {
            countKatDistList.add(c);
        }
        for (CountKatBig c : countParentsD) {
            countKatDistList.add(c);
        }
        for (CountKatBig c : countPedD) {
            countKatDistList.add(c);
        }

        // статусы
        List<CountStatus> countStatus = new ArrayList<>();
        // основные
        List<CountStatus> countStatus1 = priyomClientFacade.countStatVSotrud(1, d1, d2, sotrudDolgn, usl, cent);
        // дополнительные
        List<CountStatus> countStatus2 = priyomClientFacade.countStatVSotrud(2, d1, d2, sotrudDolgn, usl, cent);
        // подстатусы
        List<CountStatus> countStatus3 = priyomClientFacade.countStatVSotrud(3, d1, d2, sotrudDolgn, usl, cent);
        // социальные
        List<CountStatus> countStatus4 = priyomClientFacade.countStatVSotrud(4, d1, d2, sotrudDolgn, usl, cent);

        countStatus.addAll(countStatus1);
        countStatus.addAll(countStatus4);
        countStatus.addAll(countStatus2);
        countStatus.addAll(countStatus3);

        // 2 и больше статуса
        Integer manyStatusC = priyomClientFacade.manyStatusC(d1, d2, sotrudDolgn, usl, cent);
        Integer manyStatusS = priyomSubjectFacade.manyStatusS(d1, d2, sotrudDolgn, usl, cent);
        Integer manyStatus = manyStatusC + manyStatusS;

        // возрасты
        List<StandartCount> countAgeList = new ArrayList<>();
        StandartCount age03 = new StandartCount();
        age03.setTitle("ранний возраст (до 3-х лет)");
        Integer countC03 = priyomClientFacade.countAgesSotrud(d1, d2, sotrudDolgn, usl, 0, 3, cent);
        Integer countS03 = priyomSubjectFacade.countAgesSotrud(d1, d2, sotrudDolgn, usl, 0, 3, cent);
        age03.setCount(countC03 + countS03);
        StandartCount age37 = new StandartCount();
        age37.setTitle("дошкольный возраст (3-7 лет)");
        Integer countC37 = priyomClientFacade.countAgesSotrud(d1, d2, sotrudDolgn, usl, 3, 7, cent);
        Integer countS37 = priyomSubjectFacade.countAgesSotrud(d1, d2, sotrudDolgn, usl, 3, 7, cent);
        age37.setCount(countC37 + countS37);
        StandartCount age711 = new StandartCount();
        age711.setTitle("младший школьный возраст (7-11 лет)");
        Integer countC711 = priyomClientFacade.countAgesSotrud(d1, d2, sotrudDolgn, usl, 7, 11, cent);
        Integer countS711 = priyomSubjectFacade.countAgesSotrud(d1, d2, sotrudDolgn, usl, 7, 11, cent);
        age711.setCount(countC711 + countS711);
        StandartCount age1114 = new StandartCount();
        age1114.setTitle("подростковый возраст (11-14 лет)");
        Integer countC1114 = priyomClientFacade.countAgesSotrud(d1, d2, sotrudDolgn, usl, 11, 14, cent);
        Integer countS1114 = priyomSubjectFacade.countAgesSotrud(d1, d2, sotrudDolgn, usl, 11, 14, cent);
        age1114.setCount(countC1114 + countS1114);
        StandartCount age1418 = new StandartCount();
        age1418.setTitle("юношеский возраст (14-18 лет)");
        Integer countC1418 = priyomClientFacade.countAgesSotrud(d1, d2, sotrudDolgn, usl, 14, 18, cent);
        Integer countS1418 = priyomSubjectFacade.countAgesSotrud(d1, d2, sotrudDolgn, usl, 14, 18, cent);
        age1418.setCount(countC1418 + countS1418);
        countAgeList.add(age03);
        countAgeList.add(age37);
        countAgeList.add(age711);
        countAgeList.add(age1114);
        countAgeList.add(age1418);

        // мониторинг
        List<StandartCount> countMonitList = new ArrayList<>();
        StandartCount allUsl = new StandartCount();
        allUsl.setTitle("Количество услуг (кол-во заявок)");
        allUsl.setCount(priyomClientFacade.countUslSotrud(d1, d2, sotrudDolgn, usl, cent));
        StandartCount udovl = new StandartCount();
        udovl.setTitle("Удовлетворен (кол-во характеристик)");
        udovl.setCount(priyomClientFacade.countUdovlSotrud(d1, d2, sotrudDolgn, usl, cent));
        StandartCount neudovl = new StandartCount();
        neudovl.setTitle("Не удовлетворен (кол-во характеристик)");
        neudovl.setCount(priyomClientFacade.countNeudovlSotrud(d1, d2, sotrudDolgn, usl, cent));
        countMonitList.add(allUsl);
        countMonitList.add(udovl);
        countMonitList.add(neudovl);

        // проблематика
        List<CountProblemKod> countProblemList = new ArrayList<>();
        List<CountProblemKod> countProblemType = priyomClientFacade.countProblemType(d1, d2, sotrudDolgn, usl, cent);
        List<CountProblemKod> countProblem = priyomClientFacade.countProblem(d1, d2, sotrudDolgn, usl, cent);
        List<SprProblemType> sprProblemTypeList = sprProblemTypeFacade.findAll();

        Collections.sort(sprProblemTypeList, new ProblemTypeComparator());
        for (SprProblemType pt : sprProblemTypeList) {
            Boolean k = false;
            for (CountProblemKod countT : countProblemType) {
                if (pt.getSprproblemtypeKod().equals(countT.getKod())) {
                    countProblemList.add(countT);
                    k = true;
                }
            }
            if (!k) {
                CountProblemKod c = new CountProblemKod();
                c.setType(Boolean.TRUE);
                c.setKod(pt.getSprproblemtypeKod());
                c.setProblem(pt.getSprproblemtypeName());
                c.setCount(0);
            }

            List<SprProblem> sprProblemList = sprProblemFacade.findByProblemType(pt);

            for (SprProblem p : sprProblemList) {
                Boolean l = false;
                for (CountProblemKod countP : countProblem) {
                    if (countP.getKod().equals(p.getSprproblemKod())) {
                        countProblemList.add(countP);
                        l = true;
                    }
                }
                if (!l) {
                    CountProblemKod c = new CountProblemKod();
                    c.setType(Boolean.FALSE);
                    c.setKod(p.getSprproblemKod());
                    c.setProblem(p.getSprproblemName());
                    c.setCount(0);
                    countProblemList.add(c);
                }
            }
        }

        BigOtch bigOtch = new BigOtch(usl.getSpruslName(), cent, countKatBigList, countStatus, countAgeList, manyStatus, countMonitList, countProblemList, countKatDistList);
        return bigOtch;
    }

    private BigOtch getBigOtchDate(Date d1, Date d2, SprUsl usl, SotrudDolgn sotrudDolgn, String kat) {
        List<CountDate> countDateList = priyomClientFacade.countPriyomDate(d1, d2, usl, sotrudDolgn, kat);
        BigOtch bigOtch = new BigOtch(usl.getSpruslName(), countDateList);
        return bigOtch;
    }

    private BigOtch getBigOtchDateReg(Date d1, Date d2, SprUsl usl, SotrudDolgn sotrudDolgn, String kat, Integer cent) {
        List<CountDate> countDateList = priyomClientFacade.countPriyomDateReg(d1, d2, usl, sotrudDolgn, kat, cent);
        BigOtch bigOtch = new BigOtch(usl.getSpruslName(), countDateList);
        return bigOtch;
    }

    /*    private synchronized String getNextNomer() {       
        IpraIshnom ipraIshnom = ipraIshnomFacade.getIpraIshnom();
        ipraIshnom.setIpraishnomNom(ipraIshnom.getIpraishnomNom() + 1);
        ipraIshnomFacade.edit(ipraIshnom);
        String nomer = ipraIshnom.getIpraishnomNom() + ipraIshnom.getIpraishnomSuffix();        
        return nomer;
    }*/
}
