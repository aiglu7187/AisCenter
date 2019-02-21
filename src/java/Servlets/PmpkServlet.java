/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.LoginLog;
import Entities.Pmpk;
import Entities.PmpkDoc;
import Entities.PmpkOu;
import Entities.PmpkParent;
import Entities.PmpkReason;
import Entities.PmpkResult;
import Entities.Priyom;
import Entities.PriyomClient;
import Entities.PriyomSotrud;
import Entities.SotrudDolgn;
import Entities.SprDoc;
import Entities.SprIniciator;
import Entities.SprObr;
import Entities.SprObrType;
import Entities.SprObrVar;
import Entities.SprOu;
import Entities.SprParentType;
import Entities.SprPlaces;
import Entities.SprReason;
import Entities.SprRegion;
import Entities.SprStage;
import Entities.SprStat;
import Entities.SprUsl;
import Entities.Users;
import Sessions.ChildrenFacade;
import Sessions.LoginLogFacade;
import Sessions.PmpkDocFacade;
import Sessions.PmpkFacade;
import Sessions.PmpkOuFacade;
import Sessions.PmpkParentFacade;
import Sessions.PmpkReasonFacade;
import Sessions.PmpkResultFacade;
import Sessions.PriyomClientFacade;
import Sessions.PriyomFacade;
import Sessions.PriyomSotrudFacade;
import Sessions.RegionPlacesFacade;
import Sessions.SotrudDolgnFacade;
import Sessions.SprDocFacade;
import Sessions.SprIniciatorFacade;
import Sessions.SprObrFacade;
import Sessions.SprObrTypeFacade;
import Sessions.SprObrVarFacade;
import Sessions.SprOuFacade;
import Sessions.SprParentTypeFacade;
import Sessions.SprReasonFacade;
import Sessions.SprRegionFacade;
import Sessions.SprStageFacade;
import Sessions.SprStatFacade;
import Sessions.SprUslFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
// сервлет для перехода на главную страницу ПМПК
@WebServlet(name = "PmpkServlet",
        loadOnStartup = 1,
        urlPatterns = "/pmpk")

public class PmpkServlet extends HttpServlet {

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
    PriyomFacade priyomFacade = new PriyomFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    SprUslFacade sprUslFacade = new SprUslFacade();
    @EJB
    SotrudDolgnFacade sotrudDolgnFacade = new SotrudDolgnFacade();
    @EJB
    PriyomSotrudFacade priyomSotrudFacade = new PriyomSotrudFacade();
    @EJB
    PriyomClientFacade priyomClientFacade = new PriyomClientFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    RegionPlacesFacade regionPlacesFacade = new RegionPlacesFacade();
    @EJB
    PmpkFacade pmpkFacade = new PmpkFacade();
    @EJB
    PmpkParentFacade pmpkParentFacade = new PmpkParentFacade();
    @EJB
    SprParentTypeFacade sprParentTypeFacade = new SprParentTypeFacade();
    @EJB
    SprIniciatorFacade sprIniciatorFacade = new SprIniciatorFacade();
    @EJB
    SprReasonFacade sprReasonFacade = new SprReasonFacade();
    @EJB
    PmpkReasonFacade pmpkReasonFacade = new PmpkReasonFacade();
    @EJB
    PmpkDocFacade pmpkDocFacade = new PmpkDocFacade();
    @EJB
    SprDocFacade sprDocFacade = new SprDocFacade();
    @EJB
    PmpkOuFacade pmpkOuFacade = new PmpkOuFacade();
    @EJB
    SprOuFacade sprOuFacade = new SprOuFacade();
    @EJB
    SprStageFacade sprStageFacade = new SprStageFacade();
    @EJB
    SprObrFacade sprObrFacade = new SprObrFacade();
    @EJB
    SprObrTypeFacade sprObrTypeFacade = new SprObrTypeFacade();
    @EJB
    SprObrVarFacade sprObrVarFacade = new SprObrVarFacade();
    @EJB
    SprStatFacade sprStatFacade = new SprStatFacade();
    @EJB
    PmpkResultFacade pmpkResultFacade = new PmpkResultFacade();

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

        Boolean itsOk = true;   // флаг для обозначения успешности выполнения запроса
        StringBuilder sb = new StringBuilder(); // для xml
        String url = "";
        String userPath = request.getServletPath();
        // запрашиваемое действие
        String action = request.getParameter("action");
        if (action.equals("main")) {
            userPath = "/pmpk/pmpkmain";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("pmpksearch")) {
            userPath = "/pmpk/pmpksearch";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("addpmpk")) {
            userPath = "/pmpk/pmpk";
            url = "/WEB-INF/pages" + userPath + ".html";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("sprou")) {// справочник ОУ            
            List<SprOu> ouList = sprOuFacade.findAllOu();
            sb.append("<ous>");
            for (SprOu ou : ouList) {
                sb.append("<ou>");
                sb.append("<ouid>").append(ou.getSprouId()).append("</ouid>");
                sb.append("<ouname>").append(ou.getSprouShname()).append("</ouname>");
                sb.append("<oureg>").append(ou.getSprregId().getSprregName()).append("</oureg>");
                sb.append("</ou>");
            }
            sb.append("</ous>");
            if (!ouList.isEmpty()) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("children")) {
            String idS = request.getParameter("id");
            Priyom priyom = null;
            try {
                priyom = priyomFacade.findById(Integer.parseInt(idS));
            } catch (Exception ex) {
                itsOk = false;
            }

            // справочник инциаторов обращения
            List<SprIniciator> inicList = sprIniciatorFacade.findAll();
            // справочник типов зак.представителей
            List<SprParentType> parenttypes = sprParentTypeFacade.findAllSprParentType();
            // справочник причин обращения
            List<SprReason> reasonList = sprReasonFacade.findAll();
            // справочник документов
            List<SprDoc> docList = sprDocFacade.findAllDoc();
            // справочник ступеней
            List<SprStage> stageList = sprStageFacade.findAllStagesForSpr();
            // справочник типов образовательных программ
            List<SprObrType> obrTypeList = sprObrTypeFacade.findAllSprObrType();
            // справочник образовательных программ
            List<SprObr> obrList = sprObrFacade.findAllActObr(1);
            // справочник вариантов образовательных программ
            List<SprObrVar> obrVarList = sprObrVarFacade.findAll();
            // справочник статусов
            List<SprStat> statList = sprStatFacade.findAllStat();

            if (priyom != null) {
                List<PriyomClient> clients = priyomClientFacade.findByPriyom(priyom);
                sb.append("<clients>");
                for (PriyomClient client : clients) {
                    if (client.getPrclKatcl().equals("children")) {
                        List<Pmpk> pmpk = pmpkFacade.findByPrcl(client);
                        sb.append("<client>");
                        Children child = childrenFacade.findById(client.getClientId());
                        sb.append("<id>").append(child.getChildId()).append("</id>");
                        sb.append("<nom>").append(child.getChildNom()).append("</nom>");
                        sb.append("<fam>").append(child.getChildFam()).append("</fam>");
                        sb.append("<name>").append(child.getChildName()).append("</name>");
                        if (child.getChildPatr() != null) {
                            sb.append("<patr>").append(child.getChildPatr()).append("</patr>");
                        } else {
                            sb.append("<patr>").append(" ").append("</patr>");
                        }
                        sb.append("<dr>").append(child.getFormatDr()).append("</dr>");
                        sb.append("<reg>").append(child.getSprregId().getSprregName()).append("</reg>");
                        // ищем законных представителей
                        if (!pmpk.isEmpty()) {
                            List<PmpkParent> parents = pmpkParentFacade.findByPmpk(pmpk.get(0));
                            sb.append("<parents>");
                            for (PmpkParent parent : parents) {
                                sb.append("<parent>");
                                sb.append("<parid>").append(parent.getParentId().getParentId()).append("</parid>");
                                sb.append("<parnom>").append(parent.getParentId().getParentNom()).append("</parnom>");
                                sb.append("<parfam>").append(parent.getParentId().getParentFam()).append("</parfam>");
                                sb.append("<parname>").append(parent.getParentId().getParentName()).append("</parname>");
                                sb.append("<parpatr>").append(parent.getParentId().getParentPatr()).append("</parpatr>");
                                sb.append("<parreg>").append(parent.getParentId().getSprregId().getSprregName()).append("</parreg>");
                                sb.append("<partypes>");
                                for (SprParentType pt : parenttypes) {
                                    sb.append("<partype>");
                                    if (parent.getSprparenttypeId().equals(pt)) {
                                        sb.append("<ptselected>").append("1").append("</ptselected>");
                                    } else {
                                        sb.append("<ptselected>").append("0").append("</ptselected>");
                                    }
                                    sb.append("<ptid>").append(pt.getSprparenttypeId()).append("</ptid>");
                                    sb.append("<ptname>").append(pt.getSprparenttypeName()).append("</ptname>");
                                    sb.append("</partype>");
                                }
                                sb.append("</partypes>");
                                sb.append("</parent>");
                            }
                            sb.append("</parents>");

                            // инициатор обращения
                            sb.append("<iniciators>");
                            for (SprIniciator inic : inicList) {
                                sb.append("<iniciator>");
                                sb.append("<inicselected>");
                                if (pmpk.get(0).getSpriniciatorId() != null) {
                                    if (pmpk.get(0).getSpriniciatorId().equals(inic)) {
                                        sb.append("1");
                                    } else {
                                        sb.append("0");
                                    }
                                } else {
                                    sb.append("unselected");
                                }
                                sb.append("</inicselected>");
                                sb.append("<inicid>").append(inic.getSpriniciatorId()).append("</inicid>");
                                sb.append("<inicname>").append(inic.getSpriniciatorName()).append("</inicname>");
                                sb.append("<inicoth>").append(inic.getSpriniciatorOth()).append("</inicoth>");
                                sb.append("</iniciator>");
                            }
                            sb.append("</iniciators>");
                            // другой инициатор
                            String otheriniciator = " ";
                            if (pmpk.get(0).getPmpkIniciatorName() != null) {
                                otheriniciator = pmpk.get(0).getPmpkIniciatorName();
                            }
                            sb.append("<otheriniciator>").append(otheriniciator).append("</otheriniciator>");

                            // причины обращения
                            List<PmpkReason> reasons = pmpkReasonFacade.findByPmpk(pmpk.get(0));
                            // соотносим со справочником
                            sb.append("<reasons>");
                            for (SprReason reason : reasonList) {
                                sb.append("<reason>");
                                sb.append("<reasonchecked>");
                                boolean flag = false;
                                for (PmpkReason r : reasons) {
                                    if (r.getSprreasonId().equals(reason)) {
                                        sb.append("1");
                                        flag = true;
                                    }
                                }
                                if (!flag) {
                                    sb.append("0");
                                }
                                sb.append("</reasonchecked>");
                                sb.append("<reasonid>").append(reason.getSprreasonId()).append("</reasonid>");
                                sb.append("<reasonname>").append(reason.getSprreasonName()).append("</reasonname>");
                                sb.append("<reasonoth>").append(reason.getSprreasonOth()).append("</reasonoth>");
                                sb.append("</reason>");
                            }
                            sb.append("</reasons>");
                            // другая причина обращения
                            sb.append("<otherreason>");
                            String otherreason = " ";
                            for (PmpkReason r : reasons) {
                                if (r.getPmpkreasonName() != null) {
                                    otherreason = r.getPmpkreasonName();
                                }
                            }
                            sb.append(otherreason);
                            sb.append("</otherreason>");
                            // впервые/повторно на ПМПК
                            Integer first = 1;
                            if (pmpk.get(0).getPmpkFirst() != null) {
                                first = pmpk.get(0).getPmpkFirst();
                            }
                            sb.append("<pmpkfirst>").append(first).append("</pmpkfirst>");

                            // документы
                            List<PmpkDoc> docs = pmpkDocFacade.findByPmpk(pmpk.get(0));
                            // соотносим со справочником
                            sb.append("<docs>");
                            for (SprDoc doc : docList) {
                                sb.append("<doc>");
                                sb.append("<docchecked>");
                                boolean flag = false;
                                for (PmpkDoc d : docs) {
                                    if (d.getSprdocId().equals(doc)) {
                                        sb.append("1");
                                        flag = true;
                                    }
                                }
                                if (!flag) {
                                    sb.append("0");
                                }
                                sb.append("</docchecked>");
                                sb.append("<docid>").append(doc.getSprdocId()).append("</docid>");
                                sb.append("<docname>").append(doc.getSprdocName()).append("</docname>");
                                Integer main = 0;
                                if (doc.getSprdocMain() != null) {
                                    main = doc.getSprdocMain();
                                }
                                sb.append("<docmain>").append(main).append("</docmain>");
                                sb.append("<docoth>").append(doc.getSprdocOth()).append("</docoth>");
                                sb.append("</doc>");
                            }
                            sb.append("</docs>");
                            // другие документы
                            String otherdoc = " ";
                            for (PmpkDoc d : docs) {
                                if (d.getPmpkdocName() != null) {
                                    otherdoc = d.getPmpkdocName();
                                }
                            }
                            sb.append("<otherdoc>").append(otherdoc).append("</otherdoc>");
                            // обучается/нет
                            Integer o = pmpk.get(0).getPmpkStudy();
                            sb.append("<obuchenie>");
                            sb.append("<ob>").append(o).append("</ob>");
                            if (o != 0) {
                                PmpkOu pmpkOu = null;
                                try {   // ищем информацию об обучении
                                    pmpkOu = pmpkOuFacade.findByPmpk(pmpk.get(0));
                                } catch (Exception ex) {
                                }
                                if (pmpkOu != null) {
                                    // ОУ
                                    if (pmpkOu.getSprouId() != null) {
                                        sb.append("<ou>");
                                        sb.append("<ouid>").append(pmpkOu.getSprouId().getSprouId()).append("</ouid>");
                                        sb.append("<ouname>").append(pmpkOu.getSprouId().getSprouShname()).append("</ouname>");
                                        sb.append("<oureg>").append(pmpkOu.getSprouId().getSprregId().getSprregName()).append("</oureg>");
                                        sb.append("</ou>");
                                    } else {
                                        sb.append("<ou>");
                                        sb.append("<ouid>").append("0").append("</ouid>");
                                        sb.append("<ouname>").append(" ").append("</ouname>");
                                        sb.append("<oureg>").append(" ").append("</oureg>");
                                        sb.append("</ou>");
                                    }
                                    // класс/группа
                                    sb.append("<stages>");
                                    for (SprStage stage : stageList) {
                                        sb.append("<stage>");
                                        sb.append("<stageselected>");
                                        if (pmpkOu.getSprstageId() != null) {
                                            if (stage.equals(pmpkOu.getSprstageId())) {
                                                sb.append("1");
                                            } else {
                                                sb.append("0");
                                            }
                                        } else {
                                            sb.append("0");
                                        }
                                        sb.append("</stageselected>");
                                        sb.append("<stid>").append(stage.getSprstageId()).append("</stid>");
                                        sb.append("<stname>").append(stage.getSprstageName()).append("</stname>");
                                        sb.append("</stage>");
                                    }
                                    sb.append("</stages>");
                                    // тип обр.программы
                                    sb.append("<obrtypes>");
                                    for (SprObrType type : obrTypeList) {
                                        sb.append("<obrtype>");
                                        sb.append("<otselected>");
                                        boolean flag = false;
                                        if (pmpkOu.getSprobrtypeId() != null) {
                                            if (type.equals(pmpkOu.getSprobrtypeId())) {
                                                flag = true;
                                            }
                                        }
                                        if (flag) {
                                            sb.append("1");
                                        } else {
                                            sb.append("0");
                                        }
                                        sb.append("</otselected>");
                                        sb.append("<otid>").append(type.getSprobrtypeId()).append("</otid>");
                                        sb.append("<otname>").append(type.getSprobrtypeName()).append("</otname>");
                                        sb.append("</obrtype>");
                                    }
                                    sb.append("</obrtypes>");
                                    // обр.программа
                                    sb.append("<obrs>");
                                    for (SprObr obr : obrList) {
                                        sb.append("<obr>");
                                        sb.append("<obrselected>");
                                        if (obr.equals(pmpkOu.getSprobrId())) {
                                            sb.append("1");
                                        } else {
                                            sb.append("0");
                                        }
                                        sb.append("</obrselected>");
                                        sb.append("<obrid>").append(obr.getSprobrId()).append("</obrid>");
                                        sb.append("<obrname>").append(obr.getSprobrShname()).append("</obrname>");
                                        sb.append("<obrtypeid>").append(obr.getSprobrtypeId().getSprobrtypeId()).append("</obrtypeid>");
                                        sb.append("</obr>");
                                    }
                                    sb.append("</obrs>");
                                    // вариант обр.программы
                                    sb.append("<vars>");
                                    for (SprObrVar var : obrVarList) {
                                        sb.append("<var>");
                                        sb.append("<varselected>");
                                        if (var.equals(pmpkOu.getSprobrvarId())) {
                                            sb.append("1");
                                        } else {
                                            sb.append("0");
                                        }
                                        sb.append("</varselected>");
                                        sb.append("<varid>").append(var.getSprobrvarId()).append("</varid>");
                                        sb.append("<varname>").append(var.getSprobrvarName()).append("</varname>");
                                        sb.append("<varobrid>").append(var.getSprobrId().getSprobrId()).append("</varobrid>");
                                        sb.append("</var>");
                                    }
                                    sb.append("</vars>");
                                    if ((pmpkOu.getPmpkouObrName() != null) && (!pmpkOu.getPmpkouObrName().equals(""))) {
                                        sb.append("<otherobr>").append(pmpkOu.getPmpkouObrName()).append("</otherobr>");
                                    } else {
                                        sb.append("<otherobr>").append("0").append("</otherobr>");
                                    }
                                }
                            } else {
                                sb.append("<ou>");
                                sb.append("<ouid>").append("0").append("</ouid>");
                                sb.append("<ouname>").append(" ").append("</ouname>");
                                sb.append("<oureg>").append(" ").append("</oureg>");
                                sb.append("</ou>");
                                sb.append("<stages>");
                                for (SprStage stage : stageList) {
                                    sb.append("<stage>");
                                    sb.append("<stageselected>").append("0").append("</stageselected>");
                                    sb.append("<stid>").append(stage.getSprstageId()).append("</stid>");
                                    sb.append("<stname>").append(stage.getSprstageName()).append("</stname>");
                                    sb.append("</stage>");
                                }
                                sb.append("</stages>");
                                sb.append("<obrtypes>");
                                for (SprObrType type : obrTypeList) {
                                    sb.append("<obrtype>");
                                    sb.append("<otselected>").append("0").append("</otselected>");
                                    sb.append("<otid>").append(type.getSprobrtypeId()).append("</otid>");
                                    sb.append("<otname>").append(type.getSprobrtypeName()).append("</otname>");
                                    sb.append("</obrtype>");
                                }
                                sb.append("</obrtypes>");
                                sb.append("<obrs>");
                                for (SprObr obr : obrList) {
                                    sb.append("<obr>");
                                    sb.append("<obrselected>").append("0").append("</obrselected>");
                                    sb.append("<obrid>").append(obr.getSprobrId()).append("</obrid>");
                                    sb.append("<obrname>").append(obr.getSprobrShname()).append("</obrname>");
                                    sb.append("<obrtypeid>").append(obr.getSprobrtypeId().getSprobrtypeId()).append("</obrtypeid>");
                                    sb.append("</obr>");
                                }
                                sb.append("</obrs>");
                                sb.append("<vars>");
                                for (SprObrVar var : obrVarList) {
                                    sb.append("<var>");
                                    sb.append("<varselected>").append("0").append("</varselected>");
                                    sb.append("<varid>").append(var.getSprobrvarId()).append("</varid>");
                                    sb.append("<varname>").append(var.getSprobrvarName()).append("</varname>");
                                    sb.append("<varobrid>").append(var.getSprobrId().getSprobrId()).append("</varobrid>");
                                    sb.append("</var>");
                                }
                                sb.append("</vars>");
                                sb.append("<otherobr>").append(0).append("</otherobr>");
                            }
                            sb.append("</obuchenie>");

                            // результаты обследования
                            List<PmpkResult> results = pmpkResultFacade.findByPmpk(pmpk.get(0));
                            if (results.isEmpty()) {
                                sb.append("<res>");
                                sb.append("<npr>").append(" ").append("</npr>");
                                sb.append("<statuses>");
                                for (SprStat stat : statList) {
                                    sb.append("<status>");
                                    sb.append("<statuschecked>").append(0).append("</statuschecked>");
                                    sb.append("<statusid>").append(stat.getSprstatId()).append("</statusid>");
                                    sb.append("<statusname>").append(stat.getSprstatName()).append("</statusname>");
                                    sb.append("<statusfullname>").append(stat.getSprstatFullname()).append("</statusfullname>");
                                    sb.append("<statusv>").append(stat.getSprstatV()).append("</statusv>");
                                    String main = " ";
                                    if (stat.getSprstatMain() != null) {
                                        main = stat.getSprstatMain().toString();
                                    }
                                    sb.append("<statusmain>").append(main).append("</statusmain>");
                                    String pr = " ";
                                    if (stat.getSprstatInv().equals(1)) {
                                        pr = "inv";
                                    } else if (stat.getSprstatN().equals(1)) {
                                        pr = "norma";
                                    } else if (stat.getSprstatOvz().equals(1)) {
                                        pr = "ovz";
                                    }
                                    sb.append("<statuspr>").append(pr).append("</statuspr>");
                                    sb.append("</status>");
                                }
                                sb.append("</statuses>");
                                sb.append("</res>");
                            } else {
                                sb.append("<res>");
                                String np = " ";
                                if (pmpk.get(0).getPmpkNp() != null) {
                                    np = pmpk.get(0).getPmpkNp();
                                }
                                sb.append("<npr>").append(np).append("</npr>");
                                sb.append("<statuses>");
                                for (SprStat stat : statList) {
                                    sb.append("<status>");
                                    sb.append("<statuschecked>");
                                    boolean flag = false;
                                    for (PmpkResult r : results) {
                                        if (r.getSprstatId().equals(stat)) {
                                            sb.append("1");
                                            flag = true;
                                        }
                                    }
                                    if (!flag) {
                                        sb.append("0");
                                    }
                                    sb.append("</statuschecked>");
                                    sb.append("<statusid>").append(stat.getSprstatId()).append("</statusid>");
                                    sb.append("<statusname>").append(stat.getSprstatName()).append("</statusname>");
                                    sb.append("<statusfullname>").append(stat.getSprstatFullname()).append("</statusfullname>");
                                    sb.append("<statusv>").append(stat.getSprstatV()).append("</statusv>");
                                    String main = " ";
                                    if (stat.getSprstatMain() != null) {
                                        main = stat.getSprstatMain().toString();
                                    }
                                    sb.append("<statusmain>").append(main).append("</statusmain>");
                                    String pr = " ";
                                    if (stat.getSprstatInv().equals(1)) {
                                        pr = "inv";
                                    } else if (stat.getSprstatN().equals(1)) {
                                        pr = "norma";
                                    } else if (stat.getSprstatOvz().equals(1)) {
                                        pr = "ovz";
                                    }
                                    sb.append("<statuspr>").append(pr).append("</statuspr>");
                                    sb.append("</status>");
                                }
                                sb.append("</statuses>");
                                sb.append("</res>");
                            }
                        }
                        sb.append("</client>");
                    }
                }
                if (clients.isEmpty()) {
                    sb.append("<client>");
                    sb.append("<id>").append(" ").append("</id>");
                    sb.append("<nom>").append(" ").append("</nom>");
                    sb.append("<fam>").append(" ").append("</fam>");
                    sb.append("<name>").append(" ").append("</name>");
                    sb.append("<patr>").append(" ").append("</patr>");
                    sb.append("<dr>").append(" ").append("</dr>");
                    sb.append("<reg>").append(" ").append("</reg>");
                    // инициатор обращения
                    sb.append("<iniciators>");
                    for (SprIniciator inic : inicList) {
                        sb.append("<iniciator>");
                        sb.append("<inicselected>").append("0").append("</inicselected>");
                        sb.append("<inicid>").append(inic.getSpriniciatorId()).append("</inicid>");
                        sb.append("<inicname>").append(inic.getSpriniciatorName()).append("</inicname>");
                        sb.append("<inicoth>").append(inic.getSpriniciatorOth()).append("</inicoth>");
                        sb.append("</iniciator>");
                    }
                    sb.append("</iniciators>");
                    // другой инициатор
                    sb.append("<otheriniciator>").append(" ").append("</otheriniciator>");
                    // причины обращения
                    sb.append("<reasons>");
                    for (SprReason reason : reasonList) {
                        sb.append("<reason>");
                        sb.append("<reasonchecked>").append("0").append("</reasonchecked>");
                        sb.append("<reasonid>").append(reason.getSprreasonId()).append("</reasonid>");
                        sb.append("<reasonname>").append(reason.getSprreasonName()).append("</reasonname>");
                        sb.append("<reasonoth>").append(reason.getSprreasonOth()).append("</reasonoth>");
                        sb.append("</reason>");
                    }
                    sb.append("</reasons>");
                    // другая причина обращения
                    sb.append("<otherreason>").append(" ").append("</otherreason>");
                    // впервые/повторно на ПМПК
                    sb.append("<pmpkfirst>").append("0").append("</pmpkfirst>");
                    // документы
                    sb.append("<docs>");
                    for (SprDoc doc : docList) {
                        sb.append("<doc>");
                        sb.append("<docchecked>").append("0").append("</docchecked>");
                        sb.append("<docid>").append(doc.getSprdocId()).append("</docid>");
                        sb.append("<docname>").append(doc.getSprdocName()).append("</docname>");
                        Integer main = 0;
                        if (doc.getSprdocMain() != null) {
                            main = doc.getSprdocMain();
                        }
                        sb.append("<docmain>").append(main).append("</docmain>");
                        sb.append("<docoth>").append(doc.getSprdocOth()).append("</docoth>");
                        sb.append("</doc>");
                    }
                    sb.append("</docs>");
                    // другой документ
                    sb.append("<otherdoc>").append(" ").append("</otherdoc>");
                    // обучается или нет
                    sb.append("<obuchenie>");
                    sb.append("<ob>").append(0).append("</ob>");
                    sb.append("<ou>");
                    sb.append("<ouid>").append("0").append("</ouid>");
                    sb.append("<ouname>").append(" ").append("</ouname>");
                    sb.append("<oureg>").append(" ").append("</oureg>");
                    sb.append("</ou>");
                    sb.append("<stages>");
                    for (SprStage stage : stageList) {
                        sb.append("<stage>");
                        sb.append("<stageselected>").append(0).append("</stageselected>");
                        sb.append("<stid>").append(stage.getSprstageId()).append("</stid>");
                        sb.append("<stname>").append(stage.getSprstageName()).append("</stname>");
                        sb.append("</stage>");
                    }
                    sb.append("</stages>");
                    sb.append("<obrtypes>");
                    for (SprObrType type : obrTypeList) {
                        sb.append("<obrtype>");
                        sb.append("<otselected>").append(0).append("</otselected>");
                        sb.append("<otid>").append(type.getSprobrtypeId()).append("</otid>");
                        sb.append("<otname>").append(type.getSprobrtypeName()).append("</otname>");
                        sb.append("</obrtype>");
                    }
                    sb.append("</obrtypes>");
                    sb.append("<obrs>");
                    for (SprObr obr : obrList) {
                        sb.append("<obr>");
                        sb.append("<obrselected>").append(0).append("</obrselected>");
                        sb.append("<obrid>").append(obr.getSprobrId()).append("</obrid>");
                        sb.append("<obrname>").append(obr.getSprobrShname()).append("</obrname>");
                        sb.append("<obrtypeid>").append(obr.getSprobrtypeId().getSprobrtypeId()).append("</obrtypeid>");
                        sb.append("</obr>");
                    }
                    sb.append("</obrs>");
                    sb.append("<vars>");
                    for (SprObrVar var : obrVarList) {
                        sb.append("<var>");
                        sb.append("<varselected>").append(0).append("</varselected>");
                        sb.append("<varid>").append(var.getSprobrvarId()).append("</varid>");
                        sb.append("<varname>").append(var.getSprobrvarName()).append("</varname>");
                        sb.append("<varobrid>").append(var.getSprobrId().getSprobrId()).append("</varobrid>");
                        sb.append("</var>");
                    }
                    sb.append("</vars>");
                    sb.append("<otherobr>").append(0).append("</otherobr>");
                    sb.append("</obuchenie>");

                    // результаты обследования
                    sb.append("<res>");
                    sb.append("<npr>").append(" ").append("</npr>");
                    sb.append("<statuses>");
                    for (SprStat stat : statList) {
                        sb.append("<status>");
                        sb.append("<statuschecked>").append(0).append("</statuschecked>");
                        sb.append("<statusid>").append(stat.getSprstatId()).append("</statusid>");
                        sb.append("<statusname>").append(stat.getSprstatName()).append("</statusname>");
                        sb.append("<statusfullname>").append(stat.getSprstatFullname()).append("</statusfullname>");
                        sb.append("<statusv>").append(stat.getSprstatV()).append("</statusv>");
                        String main = " ";
                        if (stat.getSprstatMain() != null) {
                            main = stat.getSprstatMain().toString();
                        }
                        sb.append("<statusmain>").append(main).append("</statusmain>");
                        String pr = " ";
                        if (stat.getSprstatInv().equals(1)) {
                            pr = "inv";
                        } else if (stat.getSprstatN().equals(1)) {
                            pr = "norma";
                        } else if (stat.getSprstatOvz().equals(1)) {
                            pr = "ovz";
                        }
                        sb.append("<statuspr>").append(pr).append("</statuspr>");
                        sb.append("</status>");
                    }
                    sb.append("</statuses>");
                    sb.append("</res>");
                    sb.append("</client>");
                }
                sb.append("</clients>");
            }

            // если всё нормально - передаём клиенту
            if (itsOk) {
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else if (action.equals("places")) {
            String regIdS = request.getParameter("regid");
            SprRegion reg = null;
            try {
                reg = sprRegionFacade.findById(Integer.parseInt(regIdS));
            } catch (Exception ex) {
            }
            if (reg != null) {
                List<SprPlaces> places = regionPlacesFacade.findByRegion(reg);
                sb.append("<places>");
                for (SprPlaces place : places) {
                    sb.append("<place>");
                    sb.append("<id>").append(place.getSprplaceId()).append("</id>");
                    sb.append("<name>").append(place.getSprplaceName()).append("</name>");
                    sb.append("</place>");
                }
                sb.append("</places>");
                if (places.isEmpty()) {
                    itsOk = false;
                }
            }
            // если всё нормально - передаём клиенту
            if (itsOk) {
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
        Boolean itsOk = true;   // флаг успешности сохранения приёма
        Boolean someTrouble = false; // флаг проблем, не влияющих на сохранение приёма

        // карта всех параметров передаваемых клиентом
        Map<String, String[]> params = request.getParameterMap();

        // запрашиваемое действие
        String action = params.get("action")[0];
        if (action.equals("saveadd")) { // сохранить новое заседание ПМПК
            Priyom priyom = new Priyom();
            String priyomDateS = params.get("prDate")[0];   // дата заседания
            Date priyomDate = null;
            try {
                priyomDate = format.parse(priyomDateS); // преобразовываем в дату
            } catch (ParseException ex) {
                Logger.getLogger(PmpkServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            priyom.setPriyomDate(priyomDate);
            String regIdS = params.get("regSel")[0];    // ИД района проведения
            Integer regId = null;
            try {
                regId = Integer.parseInt(regIdS);   // преобразовываем в число
            } catch (Exception ex) {
            }
            SprRegion reg = null;
            if (regId != null) {
                reg = sprRegionFacade.findById(regId);  // ищем в справочнике район по ИД
            }
            priyom.setSprregId(reg);
            SprUsl usl = sprUslFacade.findById(1);  // ищем услугу - ПМПК            
            priyom.setSpruslId(usl);
            priyom.setUserId(user);
            priyom.setDateUpd(curDate);
            // создаём запись в БД
            try {
                priyomFacade.create(priyom);
            } catch (Exception ex) {
                itsOk = false;
            }

            // список сотрудников
            if (itsOk) {
                // председатель
                String chmIdS = params.get("sotrSel0")[0];  // сотрудник-должность
                SotrudDolgn chm = null;
                try {
                    chm = sotrudDolgnFacade.findById(Integer.parseInt(chmIdS));
                } catch (Exception ex) {
                    someTrouble = true;
                }
                // вставляем в БД
                if (!someTrouble) {
                    PriyomSotrud chmPs = new PriyomSotrud();
                    chmPs.setPriyomId(priyom);
                    chmPs.setPrsotrPmpkChm(1);  // признак председателя
                    chmPs.setSotruddolgnId(chm);
                    chmPs.setDateUpd(curDate);
                    chmPs.setUserId(user);
                    try {
                        priyomSotrudFacade.create(chmPs);
                    } catch (Exception ex) {
                        someTrouble = true;
                    }
                }

                // состав ПМПК
                List<String> sotrudList = new ArrayList<>();
                for (Map.Entry<String, String[]> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String[] value = entry.getValue();
                    if (key.startsWith("sotrSel") && !key.equals("sotrSel0")) {
                        sotrudList.add(value[0]);
                    }
                }
                for (String sotr : sotrudList) {
                    SotrudDolgn sd = null;
                    try {
                        sd = sotrudDolgnFacade.findById(Integer.parseInt(sotr));
                    } catch (Exception ex) {
                        someTrouble = true;
                    }
                    if (!someTrouble) {
                        PriyomSotrud ps = new PriyomSotrud();
                        ps.setPriyomId(priyom);
                        ps.setSotruddolgnId(sd);
                        ps.setDateUpd(curDate);
                        ps.setUserId(user);
                        try {
                            priyomSotrudFacade.create(ps);
                        } catch (Exception ex) {
                            someTrouble = true;
                        }
                    }
                }

            }

            // если всё нормально - передаём клиенту
            if (itsOk) {
                sb.append("<answer>");
                if (someTrouble) {
                    sb.append("<result>").append("saveadd-sometrouble").append("</result>");
                } else {
                    sb.append("<result>").append("saveadd-success").append("</result>");
                }
                sb.append("<id>").append(priyom.getPriyomId()).append("</id>");
                sb.append("</answer>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            } else {
                sb.append("<answer>").append("0").append("</answer>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
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
