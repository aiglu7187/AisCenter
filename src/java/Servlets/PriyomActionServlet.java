/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.ChildStatus;
import Entities.Children;
import Entities.ChildrenView;
import Entities.LoginLog;
import Entities.Parents;
import Entities.ParentsView;
import Entities.Ped;
import Entities.PedView;
import Entities.SotrudDolgn;
import Entities.SprDolgn;
import Entities.SprObr;
import Entities.SprObrType;
import Entities.SprObrVar;
import Entities.SprOrg;
import Entities.SprPeddolg;
import Entities.SprProblem;
import Entities.SprProblemType;
import Entities.SprRegion;
import Entities.SprRekomend;
import Entities.SprStat;
import Entities.SprUsl;
import Entities.Users;
import Entities.UslDolgntype;
import Entities.UslStatus;
import Other.RegionComparator;
import Other.SotrudDolgnComparator;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenFacade;
import Sessions.ChildrenViewFacade;
import Sessions.FamilyFacade;
import Sessions.LoginLogFacade;
import Sessions.ParentsFacade;
import Sessions.ParentsViewFacade;
import Sessions.PedFacade;
import Sessions.PedViewFacade;
import Sessions.SotrudDolgnFacade;
import Sessions.SotrudFacade;
import Sessions.SprDolgnFacade;
import Sessions.SprObrFacade;
import Sessions.SprObrTypeFacade;
import Sessions.SprObrVarFacade;
import Sessions.SprOrgFacade;
import Sessions.SprPeddolgFacade;
import Sessions.SprProblemFacade;
import Sessions.SprProblemTypeFacade;
import Sessions.SprRegionFacade;
import Sessions.SprRekomendFacade;
import Sessions.SprStatFacade;
import Sessions.SprStatPodFacade;
import Sessions.SprUslFacade;
import Sessions.UslDolgntypeFacade;
import Sessions.UslStatusFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
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
 * @author admin_ai
 */
@WebServlet(name = "PriyomActionServlet",
        loadOnStartup = 1,
        urlPatterns = "/priyomaction")

public class PriyomActionServlet extends HttpServlet {

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
    SprUslFacade sprUslFacade = new SprUslFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    SprDolgnFacade sprDolgnFacade = new SprDolgnFacade();
    @EJB
    SotrudFacade sotrudFacade = new SotrudFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    ParentsFacade parentsFacade = new ParentsFacade();
    @EJB
    PedFacade pedFacade = new PedFacade();
    @EJB
    SprProblemTypeFacade sprProblemTypeFacade = new SprProblemTypeFacade();
    @EJB
    SprProblemFacade sprProblemFacade = new SprProblemFacade();
    @EJB
    SprStatFacade sprStatFacade = new SprStatFacade();
    @EJB
    ChildrenViewFacade childrenViewFacade = new ChildrenViewFacade();
    @EJB
    ParentsViewFacade parentsViewFacade = new ParentsViewFacade();
    @EJB
    PedViewFacade pedViewFacade = new PedViewFacade();
    @EJB
    SprOrgFacade sprOrgFacade = new SprOrgFacade();
    @EJB
    SprPeddolgFacade sprPeddolgFacade = new SprPeddolgFacade();
    @EJB
    UslDolgntypeFacade uslDolgntypeFacade = new UslDolgntypeFacade();
    @EJB
    SprStatPodFacade sprStatPodFacade = new SprStatPodFacade();
    @EJB
    SprObrTypeFacade sprObrTypeFacade = new SprObrTypeFacade();
    @EJB
    SprObrFacade sprObrFacade = new SprObrFacade();
    @EJB
    SprObrVarFacade sprObrVarFacade = new SprObrVarFacade();
    @EJB
    SprRekomendFacade sprRekomendFacade = new SprRekomendFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    FamilyFacade familyFacade = new FamilyFacade();
    @EJB
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    SotrudDolgnFacade sotrudDolgnFacade = new SotrudDolgnFacade();
    @EJB
    UslStatusFacade uslStatusFacade = new UslStatusFacade();

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

        String action = request.getParameter("action");
        StringBuilder sb = new StringBuilder();
        boolean itsOk = false;
        String role = user.getRoleId().getRoleName();

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");

        if (action.equals("center")) {  // где может проводится услуга и прочие параметры           
            SprDolgn userDolgn = null;
            if (role.equals("specialist") || role.equals("ipra")) {
                userDolgn = sotrudDolgnFacade.findBySotrudAct(user.getSotrudId()).get(0).getSprdolgnId();
            }
            String uslid = request.getParameter("uslid");
            Integer id = Integer.parseInt(uslid);
            SprUsl usl = sprUslFacade.findById(id);
            Integer lesson = usl.getSpruslLesson();
            if (lesson == 1) {
                sb.append("<lesson>").append(lesson).append("</lesson>");
                itsOk = true;
            } else {
                Integer cnt = usl.getSpruslCenter();
                Integer problem = usl.getSpruslProblem();
                Integer stat = usl.getSpruslStat();
                Integer monit = usl.getSpruslMonit();
                Integer seans = usl.getSpruslSeans();
                Integer pmpk = usl.getSpruslPmpk();
                Integer subj = usl.getSpruslSubj();
                List<UslDolgntype> uslDolgntype = uslDolgntypeFacade.findByUsl(usl);
                List<SprDolgn> dolgnList = new ArrayList();
                if (!uslDolgntype.isEmpty()) {
                    for (UslDolgntype uslDolgn : uslDolgntype) {
                        List<SprDolgn> dolgns = sprDolgnFacade.findByType(uslDolgn.getSprdolgntypeId());
                        for (SprDolgn d : dolgns) {
                            dolgnList.add(d);
                        }
                    }
                }
                sb.append("<params>");
                sb.append("<param>");
                sb.append("<problem>").append(problem.toString()).append("</problem>");
                sb.append("<stat>").append(stat.toString()).append("</stat>");
                sb.append("<monit>").append(monit.toString()).append("</monit>");
                sb.append("<seans>").append(seans.toString()).append("</seans>");
                sb.append("<pmpk>").append(pmpk.toString()).append("</pmpk>");
                sb.append("<subj>").append(subj.toString()).append("</subj>");
                sb.append("</param>");

                if (!uslDolgntype.isEmpty()) {
                    sb.append("<dolgntype>");
                    for (SprDolgn dolgn : dolgnList) {
                        sb.append("<dolgn>");
                        sb.append("<id>").append(dolgn.getSprdolgnId()).append("</id>");
                        sb.append("<name>").append(dolgn.getSprdolgnName()).append("</name>");
                        if ((userDolgn != null) && (userDolgn.equals(dolgn))) {
                            sb.append("<user>1</user>");
                        } else if ((userDolgn == null) || (!userDolgn.equals(dolgn))) {
                            sb.append("<user>0</user>");
                        }
                        sb.append("</dolgn>");
                    }
                    sb.append("</dolgntype>");
                }

                if (cnt == 0) {
                    List<SprRegion> regListNoOther = sprRegionFacade.findNoOther();
                    Collections.sort(regListNoOther, new RegionComparator());
                    sb.append("<regions>");
                    for (SprRegion reg : regListNoOther) {
                        sb.append("<region>");
                        sb.append("<id>").append(reg.getSprregId()).append("</id>");
                        sb.append("<rname>").append(reg.getSprregName()).append("</rname>");
                        sb.append("</region>");
                    }
                    sb.append("</regions>");
                    sb.append("</params>");
                    itsOk = true;
                } else if (cnt == 1) {
                    List<SprRegion> regListCenter = sprRegionFacade.findCenter();
                    Collections.sort(regListCenter, new RegionComparator());
                    sb.append("<regions>");
                    for (SprRegion reg : regListCenter) {
                        sb.append("<region>");
                        sb.append("<id>").append(reg.getSprregId()).append("</id>");
                        sb.append("<rname>").append(reg.getSprregName()).append("</rname>");
                        sb.append("</region>");
                    }
                    sb.append("</regions>");
                    sb.append("</params>");
                    itsOk = true;
                }
            }
        } else if (action.equals("sotr")) {   // выбор сотрудников по должности
            String n = request.getParameter("n");
            String dolgnId = request.getParameter("id");
            Integer id = Integer.parseInt(dolgnId);
            SprDolgn dol = sprDolgnFacade.findById(id);
            List<SotrudDolgn> listSotrud = sotrudDolgnFacade.findActSotrudByDolgnAct(dol);
            Collections.sort(listSotrud, new SotrudDolgnComparator());
            sb.append("<sotruds>");
            for (SotrudDolgn sd : listSotrud) {
                sb.append("<sotrud>");
                sb.append("<n>").append(n).append("</n>");
                sb.append("<id>").append(sd.getSotruddolgnId()).append("</id>");
                sb.append("<fio>").append(sd.getSotrudId().getSotrudFIO()).append("</fio>");
                if (user.getSotrudId().equals(sd.getSotrudId())) {
                    sb.append("<user>1</user>");
                } else {
                    sb.append("<user>0</user>");
                }
                sb.append("</sotrud>");
            }
            sb.append("</sotruds>");
            itsOk = true;
        } else if (action.equals("search") || action.equals("searchall")) {  // подготовка данных для поиска по клиентам
            String katCl = request.getParameter("kat");
            String famCl = request.getParameter("fam");
            String namCl = request.getParameter("nam");
            String patrCl = request.getParameter("patr");
            if (famCl != null) {
                famCl = famCl.trim().toUpperCase();
            } else {
                famCl = "";
            }
            if (namCl != null) {
                namCl = namCl.trim().toUpperCase();
            } else {
                namCl = "";
            }
            if (patrCl != null) {
                patrCl = patrCl.trim().toUpperCase();
            } else {
                patrCl = "";
            }
            if (katCl.equals("children")) {
                String fam = famCl.replace("Ё", "Е");
                String nam = namCl.replace("Ё", "Е");
                String patr = patrCl.replace("Ё", "Е");
                List<ChildrenView> childrenV = null;
                if (action.equals("search")) {
                    childrenV = childrenViewFacade.searchChildS(fam, nam, patr);
                } else if (action.equals("searchall")) {
                    childrenV = childrenViewFacade.searchChild(fam, nam, patr);
                }
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
                    if (patrS == null) {
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

            } else if (katCl.equals("parents")) {
                String fam = famCl.replace("Ё", "Е");
                String nam = namCl.replace("Ё", "Е");
                String patr = patrCl.replace("Ё", "Е");
                List<ParentsView> parentsV = null;
                if (action.equals("search")) {
                    parentsV = parentsViewFacade.searchParentS(fam, nam, patr);
                } else if (action.equals("searchall")) {
                    parentsV = parentsViewFacade.searchParent(fam, nam, patr);
                }

                List<Parents> parents = new ArrayList();
                if (parentsV != null) {
                    for (ParentsView parentV : parentsV) {
                        parents.add(parentsFacade.findById(parentV.getParentId()));
                    }
                }

                sb.append("<parents>");
                for (Parents parent : parents) {
                    sb.append("<parent>");
                    sb.append("<id>").append(parent.getParentId()).append("</id>");
                    sb.append("<nom>").append(parent.getParentNom()).append("</nom>");
                    sb.append("<fam>").append(parent.getParentFam()).append("</fam>");
                    sb.append("<name>").append(parent.getParentName()).append("</name>");
                    String patrS = parent.getParentPatr();
                    if (patrS == null) {
                        patrS = " ";
                    }
                    sb.append("<patr>").append(patrS).append("</patr>");
                    sb.append("<reg>").append(parent.getSprregId().getSprregName()).append("</reg>");
                    sb.append("</parent>");
                }
                sb.append("</parents>");
                itsOk = true;

            } else if (katCl.equals("ped")) {
                String fam = famCl.replace("Ё", "Е");
                String nam = namCl.replace("Ё", "Е");
                String patr = patrCl.replace("Ё", "Е");
                List<PedView> pedsV = null;
                if (action.equals("search")) {
                    pedsV = pedViewFacade.searchPedS(fam, nam, patr);
                }
                if (action.equals("searchall")) {
                    pedsV = pedViewFacade.searchPed(fam, nam, patr);
                }
                List<Ped> peds = new ArrayList();
                if (pedsV != null) {
                    for (PedView pedV : pedsV) {
                        peds.add(pedFacade.findById(pedV.getPedId()));
                    }
                }

                sb.append("<peds>");
                for (Ped ped : peds) {
                    sb.append("<ped>");
                    sb.append("<id>").append(ped.getPedId()).append("</id>");
                    sb.append("<nom>").append(ped.getPedNom()).append("</nom>");
                    sb.append("<fam>").append(ped.getPedFam()).append("</fam>");
                    sb.append("<name>").append(ped.getPedName()).append("</name>");
                    String patrS = ped.getPedPatr();
                    if (patrS == null) {
                        patrS = " ";
                    }
                    sb.append("<patr>").append(patrS).append("</patr>");
                    sb.append("<org>").append(ped.getSprorgId().getSprorgName()).append("</org>");
                    sb.append("<dol>").append(ped.getSprpeddolgId().getSprpeddolgName()).append("</dol>");
                    sb.append("<reg>").append(ped.getSprregId().getSprregName()).append("</reg>");
                    sb.append("</ped>");
                }
                sb.append("</peds>");
                itsOk = true;
            }

        } else if (action.equals("region")) {
            String n = request.getParameter("n");
            String type = request.getParameter("type");
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            Collections.sort(regions, new RegionComparator());
            sb.append("<regionsCl>");
            for (SprRegion reg : regions) {
                sb.append("<region>");
                if (n.equals("")) {
                    n = " ";
                }
                sb.append("<n>").append(n).append("</n>");
                sb.append("<type>").append(type).append("</type>");
                sb.append("<id>").append(reg.getSprregId()).append("</id>");
                sb.append("<rname>").append(reg.getSprregName()).append("</rname>");
                sb.append("</region>");
            }
            sb.append("</regionsCl>");
            itsOk = true;
        } else if (action.equals("problemtype")) {
            String n = request.getParameter("n");

            List<SprProblemType> problemTypeList = sprProblemTypeFacade.findAllType();

            if (problemTypeList != null) {
                sb.append("<problemTypes>");
                for (SprProblemType problemType : problemTypeList) {
                    sb.append("<problemType>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<id>").append(problemType.getSprproblemtypeId()).append("</id>");
                    sb.append("<name>").append(problemType.getSprproblemtypeKod()).append(" ").append(problemType.getSprproblemtypeName()).append("</name>");
                    sb.append("</problemType>");
                }
                sb.append("</problemTypes>");
                itsOk = true;
            }
        } else if (action.equals("problem")) {
            String n = request.getParameter("n");
            String type = request.getParameter("type");
            SprProblemType problemType = sprProblemTypeFacade.findById(Integer.parseInt(type));
            List<SprProblem> problemList = sprProblemFacade.findByProblemType(problemType);
            if (problemList != null) {
                sb.append("<problems>");
                for (SprProblem problem : problemList) {
                    sb.append("<problem>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<id>").append(problem.getSprproblemId()).append("</id>");
                    sb.append("<name>").append(problem.getSprproblemKod()).append(" ").append(problem.getSprproblemName()).append("</name>");
                    sb.append("</problem>");
                }
                sb.append("</problems>");
                itsOk = true;
            }
        } else if (action.equals("statosn")) {
            String n = request.getParameter("n");
            String type = request.getParameter("type");
            String clIdS = request.getParameter("clid");
            String uslIdS = request.getParameter("usl");
            String dateS = request.getParameter("date");
            Date date = null;            
            try {
                date = format.parse(dateS);
            } catch (ParseException ex) {
                Logger.getLogger(PriyomActionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Integer uslId = 0;
            try {
                uslId = Integer.parseInt(uslIdS);
            } catch (Exception ex) {
            }
            SprUsl usl = null;
            if (uslId != 0) {
                usl = sprUslFacade.findById(uslId);
            }
            Integer clId = 0;
            try {
                clId = Integer.parseInt(clIdS);
            } catch (Exception ex) {
            }
            Children child = null;
            if (clId > 0) {
                child = childrenFacade.findById(clId);
            }
            List<ChildStatus> stats = new ArrayList<>();
            if (child != null) {
                stats = childStatusFacade.findByChildActOnDate(child, date);
            }

            List<SprStat> sprStatOsn = sprStatFacade.findByStatV(1);
            if (sprStatOsn != null) {
                sb.append("<statosns>");
                for (SprStat stat : sprStatOsn) {
                    sb.append("<statosn>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<type>").append(type).append("</type>");
                    sb.append("<id>").append(stat.getSprstatId()).append("</id>");
                    sb.append("<name>").append(stat.getSprstatName()).append("</name>");
                    sb.append("<norma>").append(stat.getSprstatN()).append("</norma>");
                    String checked = "0";
                    for (ChildStatus chSt : stats) {
                        if (chSt.getSprstatId().equals(stat)) {
                            checked = "1";
                        }
                    }
                    sb.append("<checked>").append(checked).append("</checked>");
                    String enabled = "0";
                    if (usl != null) {
                        try {
                            UslStatus uslStatus = uslStatusFacade.findByUslAndStat(usl, stat);
                            enabled = uslStatus.getUslstatusEnabled().toString();
                        } catch (Exception ex) {
                        }
                    }
                    sb.append("<enabled>").append(enabled).append("</enabled>");
                    sb.append("</statosn>");
                }
                sb.append("</statosns>");
                itsOk = true;
            }
        } else if (action.equals("statdop")) {
            String n = request.getParameter("n");
            String type = request.getParameter("type");
            List<SprStat> sprStatDop = sprStatFacade.findByStatV(2);
            String dateS = request.getParameter("date");
            Date date = null;            
            try {
                date = format.parse(dateS);
            } catch (ParseException ex) {
                Logger.getLogger(PriyomActionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }            
            String uslIdS = request.getParameter("usl");            
            Integer uslId = 0;
            try {
                uslId = Integer.parseInt(uslIdS);
            } catch (Exception ex) {
            }
            SprUsl usl = null;
            if (uslId != 0) {
                usl = sprUslFacade.findById(uslId);
            }
            String clIdS = request.getParameter("clid");
            Integer clId = 0;
            try {
                clId = Integer.parseInt(clIdS);
            } catch (Exception ex) {
            }
            Children child = null;
            if (clId > 0) {
                child = childrenFacade.findById(clId);
            }
            List<ChildStatus> stats = new ArrayList<>();
            if (child != null) {
                stats = childStatusFacade.findByChildActOnDate(child, date);
            }

            if (sprStatDop != null) {
                sb.append("<statdops>");
                for (SprStat stat : sprStatDop) {
                    sb.append("<statdop>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<type>").append(type).append("</type>");
                    sb.append("<id>").append(stat.getSprstatId()).append("</id>");
                    sb.append("<name>").append(stat.getSprstatName()).append("</name>");
                    String checked = "0";
                    for (ChildStatus chSt : stats) {
                        if (chSt.getSprstatId().equals(stat)) {
                            checked = "1";
                        }
                    }
                    sb.append("<checked>").append(checked).append("</checked>");
                    String enabled = "0";
                    if (usl != null) {
                        try {
                            UslStatus uslStatus = uslStatusFacade.findByUslAndStat(usl, stat);
                            enabled = uslStatus.getUslstatusEnabled().toString();
                        } catch (Exception ex) {
                        }
                    }
                    sb.append("<enabled>").append(enabled).append("</enabled>");
                    sb.append("</statdop>");
                }
                sb.append("</statdops>");
                itsOk = true;
            }
        } else if (action.equals("statsoc")) {
            String n = request.getParameter("n");
            String type = request.getParameter("type");
            List<SprStat> sprStatSoc = sprStatFacade.findByStatV(4);
            String dateS = request.getParameter("date");
            Date date = null;            
            try {
                date = format.parse(dateS);
            } catch (ParseException ex) {
                Logger.getLogger(PriyomActionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String uslIdS = request.getParameter("usl");
            Integer uslId = 0;
            try {
                uslId = Integer.parseInt(uslIdS);
            } catch (Exception ex) {
            }
            SprUsl usl = null;
            if (uslId != 0) {
                usl = sprUslFacade.findById(uslId);
            }
            String clIdS = request.getParameter("clid");
            Integer clId = 0;
            try {
                clId = Integer.parseInt(clIdS);
            } catch (Exception ex) {
            }
            Children child = null;
            if (clId > 0) {
                child = childrenFacade.findById(clId);
            }
            List<ChildStatus> stats = new ArrayList<>();
            if (child != null) {
                stats = childStatusFacade.findByChildActOnDate(child, date);
            }
            if (!sprStatSoc.isEmpty()) {
                sb.append("<statsocs>");
                for (SprStat stat : sprStatSoc) {
                    sb.append("<statsoc>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<type>").append(type).append("</type>");
                    sb.append("<id>").append(stat.getSprstatId()).append("</id>");
                    sb.append("<name>").append(stat.getSprstatName()).append("</name>");
                    String checked = "0";
                    for (ChildStatus chSt : stats) {
                        if (chSt.getSprstatId().equals(stat)) {
                            checked = "1";
                        }
                    }
                    sb.append("<checked>").append(checked).append("</checked>");
                    String enabled = "0";
                    if (usl != null) {
                        try {
                            UslStatus uslStatus = uslStatusFacade.findByUslAndStat(usl, stat);
                            enabled = uslStatus.getUslstatusEnabled().toString();
                        } catch (Exception ex) {
                        }
                    }
                    sb.append("<enabled>").append(enabled).append("</enabled>");
                    sb.append("</statsoc>");
                }
                sb.append("</statsocs>");
                itsOk = true;
            }
        } else if (action.equals("org")) {
            String n = request.getParameter("n");
            String type = request.getParameter("type");
            List<SprOrg> sprOrgList = sprOrgFacade.findAll();
            List<SprPeddolg> sprPeddolgList = sprPeddolgFacade.findAll();
            sb.append("<orgdolg>");
            if (sprOrgList != null) {
                sb.append("<orgs>");
                for (SprOrg org : sprOrgList) {
                    sb.append("<org>");
                    if (n.equals("")) {
                        n = " ";
                    }
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<type>").append(type).append("</type>");
                    sb.append("<id>").append(org.getSprorgId()).append("</id>");
                    sb.append("<name>").append(org.getSprorgName()).append("</name>");
                    sb.append("</org>");
                }
                sb.append("</orgs>");
            }
            if (sprPeddolgList != null) {
                sb.append("<peddolgns>");
                for (SprPeddolg peddolg : sprPeddolgList) {
                    sb.append("<dolgn>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<type>").append(type).append("</type>");
                    sb.append("<id>").append(peddolg.getSprpeddolgId()).append("</id>");
                    sb.append("<name>").append(peddolg.getSprpeddolgName()).append("</name>");
                    sb.append("</dolgn>");
                }
                sb.append("</peddolgns>");
            }
            sb.append("</orgdolg>");
            itsOk = true;
        } else if (action.equals("kurs")) {
            String kolS = request.getParameter("kol");
            String ch = request.getParameter("ch");
            String dwS = request.getParameter("dw");
            String dateprS = request.getParameter("datepr");
            Integer kol = Integer.parseInt(kolS);
            Integer dw = Integer.parseInt(dwS) + 1; // сдвигаем, чтобы совпадал день недели

            Date datePr = null;
            try {
                datePr = format.parse(dateprS);
            } catch (Exception ex) {
            }

            Calendar cal = new GregorianCalendar();

            List<Date> dateList = new ArrayList<>();

            if (ch.equals("0")) {    // ежедневно (пнд-птн)
                int i = 1;
                cal.setTime(datePr);
                while (i <= kol) {
                    if ((cal.get(Calendar.DAY_OF_WEEK) != 1) && (cal.get(Calendar.DAY_OF_WEEK) != 7)) {    // не вскр (1), не сбт (7)
                        dateList.add(cal.getTime());
                        i++;
                    }
                    cal.add(Calendar.DATE, 1);
                }
            }
            if (ch.equals("1")) {    // ежедневно (втр-сбт)
                int i = 1;
                cal.setTime(datePr);
                while (i <= kol) {
                    if ((cal.get(Calendar.DAY_OF_WEEK) != 1) && (cal.get(Calendar.DAY_OF_WEEK) != 2)) {    // не вскр (1), не пнд (2)
                        dateList.add(cal.getTime());
                        i++;
                    }
                    cal.add(Calendar.DATE, 1);
                }
            }
            if (ch.equals("2")) {    // еженедельно
                int i = 1;
                cal.setTime(datePr);
                while (i <= kol) {
                    if (cal.get(Calendar.DAY_OF_WEEK) == dw) {    // конкретный день недели
                        dateList.add(cal.getTime());
                        i++;
                    }
                    cal.add(Calendar.DATE, 1);
                }
            }

            sb.append("<kurs>");
            for (Date datpr : dateList) {
                sb.append("<priyom>");
                sb.append("<datepr>").append(format.format(datpr)).append("</datepr>");
                sb.append("</priyom>");
            }
            sb.append("</kurs>");
            itsOk = true;
        } else if (action.equals("pod")) {
            String n = request.getParameter("n");
            String dopS = request.getParameter("dop");
            String type = request.getParameter("type");
            String dateS = request.getParameter("date");
            Date date = null;            
            try {
                date = format.parse(dateS);
            } catch (ParseException ex) {
                Logger.getLogger(PriyomActionServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String uslIdS = request.getParameter("usl");
            Integer uslId = 0;
            try {
                uslId = Integer.parseInt(uslIdS);
            } catch (Exception ex) {
            }
            SprUsl usl = null;
            if (uslId != 0) {
                usl = sprUslFacade.findById(uslId);
            }
            String clIdS = request.getParameter("clid");
            Integer clId = 0;
            try {
                clId = Integer.parseInt(clIdS);
            } catch (Exception ex) {
            }
            Children child = null;
            if (clId > 0) {
                child = childrenFacade.findById(clId);
            }
            List<ChildStatus> stats = new ArrayList<>();
            if (child != null) {
                stats = childStatusFacade.findByChildActOnDate(child, date);
            }
            Integer dop = 0;
            try {
                dop = Integer.parseInt(dopS);
            } catch (Exception ex) {
            }
            SprStat statDop = null;
            if (dop != 0) {
                try {
                    statDop = sprStatFacade.findById(dop);
                } catch (Exception ex) {
                }
            }
            List<SprStat> statPodList = new ArrayList<SprStat>();
            if (statDop != null) {
                statPodList = sprStatPodFacade.findByMainStat(statDop);
            }
            if (statPodList.size() > 0) {
                sb.append("<statpod>");
                for (SprStat st : statPodList) {
                    sb.append("<stat>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<type>").append(type).append("</type>");
                    sb.append("<dop>").append(dopS).append("</dop>");
                    sb.append("<id>").append(st.getSprstatId()).append("</id>");
                    sb.append("<name>").append(st.getSprstatName()).append("</name>");
                    String checked = "0";
                    for (ChildStatus chSt : stats) {
                        if (chSt.getSprstatId().equals(st)) {
                            checked = "1";
                        }
                    }
                    sb.append("<checked>").append(checked).append("</checked>");
                    String enabled = "0";
                    if (usl != null) {
                        try {
                            UslStatus uslStatus = uslStatusFacade.findByUslAndStat(usl, st);
                            enabled = uslStatus.getUslstatusEnabled().toString();
                        } catch (Exception ex) {
                        }
                    }
                    sb.append("<enabled>").append(enabled).append("</enabled>");
                    sb.append("</stat>");
                }
                sb.append("</statpod>");
                itsOk = true;
            }
        } else if (action.equals("optype")) {
            String n = request.getParameter("n");
            List<SprObrType> obrTypeList = sprObrTypeFacade.findAll();
            if (obrTypeList.size() > 0) {
                sb.append("<optypes>");
                for (SprObrType opt : obrTypeList) {
                    sb.append("<optype>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<id>").append(opt.getSprobrtypeId()).append("</id>");
                    sb.append("<name>").append(opt.getSprobrtypeName()).append("</name>");
                    sb.append("</optype>");
                }
                sb.append("</optypes>");
                itsOk = true;
            }
        } else if (action.equals("op")) {
            String n = request.getParameter("n");
            String optypeS = request.getParameter("optype");
            Integer optype = 0;
            try {
                optype = Integer.parseInt(optypeS);
            } catch (Exception ex) {
            }
            SprObrType type = null;
            if (optype != 0) {
                try {
                    type = sprObrTypeFacade.findBySprObrTypeId(optype);
                } catch (Exception ex) {
                }
            }
            List<SprObr> opList = new ArrayList<>();
            if (type != null) {
                opList = sprObrFacade.findObrByType(type);
            }
            if (opList.size() > 0) {
                sb.append("<ops>");
                for (SprObr op : opList) {
                    sb.append("<op>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<id>").append(op.getSprobrId()).append("</id>");
                    sb.append("<name>").append(op.getSprobrShname()).append("</name>");
                    sb.append("</op>");
                }
                sb.append("</ops>");
                itsOk = true;
            }
        } else if (action.equals("opvar")) {
            String n = request.getParameter("n");
            String opS = request.getParameter("op");
            Integer op = 0;
            try {
                op = Integer.parseInt(opS);
            } catch (Exception ex) {
            }
            SprObr obr = null;
            if (op != 0) {
                try {
                    obr = sprObrFacade.findObrById(op);
                } catch (Exception ex) {
                }
            }
            List<SprObrVar> varList = new ArrayList<>();
            if (obr != null) {
                varList = sprObrVarFacade.findVarByObr(obr);
            }

            if (varList.size() > 0) {
                sb.append("<vars>");
                for (SprObrVar var : varList) {
                    sb.append("<var>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<id>").append(var.getSprobrvarId()).append("</id>");
                    sb.append("<name>").append(var.getSprobrvarName()).append("</name>");
                    sb.append("</var>");
                }
                sb.append("</vars>");
                itsOk = true;
            }
        } else if (action.equals("rekomend")) {
            String n = request.getParameter("n");
            List<SprRekomend> allRek = new ArrayList<>();
            allRek = sprRekomendFacade.findAllRekomend();
            if (!allRek.isEmpty()) {
                sb.append("<rekomends>");
                for (SprRekomend rek : allRek) {
                    sb.append("<rekomend>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<id>").append(rek.getSprrekId()).append("</id>");
                    sb.append("<name>").append(rek.getSprrekName()).append("</name>");
                    sb.append("</rekomend>");
                }
                sb.append("</rekomends>");
                itsOk = true;
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

}
