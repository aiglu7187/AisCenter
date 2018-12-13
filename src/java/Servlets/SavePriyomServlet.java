/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.ChildStatus;
import Entities.Children;
import Entities.Family;
import Entities.FamilyNom;
import Entities.LoginLog;
import Entities.Nom;
import Entities.Parents;
import Entities.Ped;
import Entities.Pmpk;
import Entities.PmpkParent;
import Entities.PmpkRek;
import Entities.Priyom;
import Entities.PriyomClient;
import Entities.PriyomProblem;
import Entities.PriyomSotrud;
import Entities.PriyomSubject;
import Entities.SprObr;
import Entities.SprObrVar;
import Entities.SprParentType;
import Entities.SprRegion;
import Entities.SprStat;
import Entities.SprUsl;
import Entities.Users;
import Entities.UslStatus;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenFacade;
import Sessions.FamilyFacade;
import Sessions.FamilyNomFacade;
import Sessions.LoginLogFacade;
import Sessions.NomFacade;
import Sessions.ParentsFacade;
import Sessions.PedFacade;
import Sessions.PmpkFacade;
import Sessions.PmpkParentFacade;
import Sessions.PmpkRekFacade;
import Sessions.PriyomClientFacade;
import Sessions.PriyomFacade;
import Sessions.PriyomProblemFacade;
import Sessions.PriyomSotrudFacade;
import Sessions.PriyomSubjectFacade;
import Sessions.SotrudDolgnFacade;
import Sessions.SotrudFacade;
import Sessions.SprObrFacade;
import Sessions.SprObrVarFacade;
import Sessions.SprOrgFacade;
import Sessions.SprParentTypeFacade;
import Sessions.SprPeddolgFacade;
import Sessions.SprProblemFacade;
import Sessions.SprRegionFacade;
import Sessions.SprRekomendFacade;
import Sessions.SprStatFacade;
import Sessions.SprStatPodFacade;
import Sessions.SprUslFacade;
import Sessions.UslStatusFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
 * @author admin_ai
 */
// сервлет сохранения информации о приёме
@WebServlet(name = "SavePriyomServlet",
        loadOnStartup = 1,
        urlPatterns = "/savepriyom")

public class SavePriyomServlet extends HttpServlet {

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
    PriyomFacade priyomFacade = new PriyomFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    ParentsFacade parentsFacade = new ParentsFacade();
    @EJB
    PedFacade pedFacade = new PedFacade();
    @EJB
    SprUslFacade sprUslFacade = new SprUslFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    SotrudFacade sotrudFacade = new SotrudFacade();
    @EJB
    PriyomSotrudFacade priyomSotrudFacade = new PriyomSotrudFacade();
    @EJB
    PriyomClientFacade priyomClientFacade = new PriyomClientFacade();
    @EJB
    NomFacade nomFacade = new NomFacade();
    @EJB
    SprProblemFacade sprProblemFacade = new SprProblemFacade();
    @EJB
    PriyomProblemFacade priyomProblemFacade = new PriyomProblemFacade();
    @EJB
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    SprStatFacade sprStatusFacade = new SprStatFacade();
    @EJB
    SprOrgFacade sprOrgFacade = new SprOrgFacade();
    @EJB
    SprPeddolgFacade sprPeddolgFacade = new SprPeddolgFacade();
    @EJB
    SprObrFacade sprObrFacade = new SprObrFacade();
    @EJB
    SprObrVarFacade sprObrVarFacade = new SprObrVarFacade();
    @EJB
    PmpkFacade pmpkFacade = new PmpkFacade();
    @EJB
    SprRekomendFacade sprRekomendFacade = new SprRekomendFacade();
    @EJB
    PmpkRekFacade pmpkRekFacade = new PmpkRekFacade();
    @EJB
    PriyomSubjectFacade priyomSubjectFacade = new PriyomSubjectFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    PmpkParentFacade pmpkParentFacade = new PmpkParentFacade();
    @EJB
    FamilyFacade familyFacade = new FamilyFacade();
    @EJB
    FamilyNomFacade familyNomFacade = new FamilyNomFacade();
    @EJB
    SprStatFacade sprStatFacade = new SprStatFacade();
    @EJB
    SprStatPodFacade sprStatPodFacade = new SprStatPodFacade();
    @EJB
    SotrudDolgnFacade sotrudDolgnFacade = new SotrudDolgnFacade();
    @EJB
    SprParentTypeFacade sprParentTypeFacade = new SprParentTypeFacade();
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
        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);

        String userPath = "";
        String url = "";

        Priyom priyom = null;
        String prId = request.getParameter("priyomId");
        if (prId != null) {
            Integer priyomId = Integer.parseInt(prId);
            priyom = priyomFacade.findById(priyomId);
            List<PriyomClient> prclList = priyomClientFacade.findByPriyom(priyom);
            List<PriyomProblem> prprList = priyomProblemFacade.findByPriyom(priyom);
            List<PriyomSotrud> prsotrList = priyomSotrudFacade.findByPriyom(priyom);
            List<PriyomSubject> prsubList = priyomSubjectFacade.findByPriyom(priyom);

            for (PriyomClient prcl : prclList) {
                priyomClientFacade.remove(prcl);
            }
            for (PriyomProblem prpr : prprList) {
                priyomProblemFacade.remove(prpr);
            }
            for (PriyomSotrud prsotr : prsotrList) {
                priyomSotrudFacade.remove(prsotr);
            }
            for (PriyomSubject prsubj : prsubList) {
                priyomSubjectFacade.remove(prsubj);
            }

            userPath = "/pup/goodsave";
            url = "/WEB-INF/pages" + userPath + ".jsp";
        } else {
            userPath = "/pup/addpriyom";
            url = "/WEB-INF/pages" + userPath + ".jsp";
        }

        String uslId = null;
        String regId = null;
        String datePriyom = null;
        List<String> sotr = new ArrayList<>();
        List<String> probl = new ArrayList<>();
        List<String> client = new ArrayList<>();
        List<String> newClient = new ArrayList<>();
        List<String> subject = new ArrayList<>();
        List<String> newSubject = new ArrayList<>();
        List<String> statosn = new ArrayList<>();
        List<String> statsoc = new ArrayList<>();
        List<String> statdop = new ArrayList<>();
        List<String> statpod = new ArrayList<>();
        List<String> subStatosn = new ArrayList<>();
        List<String> subStatsoc = new ArrayList<>();
        List<String> subStatdop = new ArrayList<>();
        List<String> subStatpod = new ArrayList<>();
        List<String> kurspr = new ArrayList<>();
        List<String> rek = new ArrayList<>();

        Map<String, String[]> param = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : param.entrySet()) {
            String par = entry.getKey();
            String[] val = entry.getValue();
            if (val.length > 1) {
                int k = 0;
            }
            if (par.equals("uslId")) {
                uslId = val[0];
            } else if (par.equals("regId")) {
                regId = val[0];
            } else if (par.equals("datePriyom")) {
                datePriyom = val[0];
            } else if (par.startsWith("sotrId")) {  // в поле sotrId содержится sotruddolgnId
                sotr.add(val[0]);
            } else if (par.startsWith("prId")) {
                probl.add(val[0]);
            } else if (par.startsWith("clId")) {
                if (!val[0].equals("")) {
                    client.add(par.substring(4));
                } else {
                    newClient.add(par.substring(4));
                }
            } else if (par.startsWith("subId")) {
                if (!val[0].equals("")) {
                    subject.add(par.substring(5));
                } else {
                    newSubject.add(par.substring(5));
                }
            } else if (par.startsWith("statosn")) {
                statosn.add(val[0]);
            } else if (par.startsWith("statsoc")) {
                statsoc.add(val[0]);
            } else if (par.startsWith("statdop")) {
                statdop.add(val[0]);
            } else if (par.startsWith("statpod")) {
                statpod.add(val[0]);
            } else if (par.startsWith("subStatosn")) {
                subStatosn.add(val[0]);
            } else if (par.startsWith("subStatsoc")) {
                subStatsoc.add(val[0]);
            } else if (par.startsWith("subStatdop")) {
                subStatdop.add(val[0]);
            } else if (par.startsWith("subStatpod")) {
                subStatpod.add(val[0]);
            } else if (par.startsWith("kurspr")) {
                kurspr.add(val[0]);
            } else if (par.startsWith("rek")) {
                rek.add(val[0]);
            }
        }

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");

        List<Priyom> priyomList = new ArrayList<>();

        if (kurspr.isEmpty()) {
            kurspr.add(datePriyom);
        }

        SprUsl usl = sprUslFacade.findById(Integer.parseInt(uslId));
        SprRegion uslReg = sprRegionFacade.findById(Integer.parseInt(regId));
        if (priyom == null) {    //  новый приём
            for (String kurs : kurspr) {
                priyom = new Priyom();
                Date datePr = null;
                try {
                    datePr = format.parse(kurs);
                } catch (Exception ex) {
                }
                if (datePr != null) {
                    priyom.setPriyomDate(datePr);
                    priyom.setSpruslId(usl);
                    priyom.setSprregId(uslReg);
                    priyom.setUserId(user);
                    priyom.setDateUpd(curDate);
                    priyomFacade.create(priyom);
                    priyomList.add(priyom);
                }
            }
        } else {    // приём уже существует
            Date datePr = null;
            try {
                datePr = format.parse(kurspr.get(0));
            } catch (Exception ex) {
            }
            priyom.setPriyomDate(datePr);
            priyom.setSpruslId(sprUslFacade.findById(Integer.parseInt(uslId)));
            priyom.setSprregId(sprRegionFacade.findById(Integer.parseInt(regId)));
            priyom.setUserId(user);
            priyom.setDateUpd(curDate);
            priyomFacade.edit(priyom);
            priyomList.add(priyom);
        }

        if (!sotr.isEmpty()) {
            for (String s : sotr) {
                for (Priyom pri : priyomList) {
                    PriyomSotrud priyomSotrud = new PriyomSotrud();
                    priyomSotrud.setSotruddolgnId(sotrudDolgnFacade.findById(Integer.parseInt(s)));
                    priyomSotrud.setPriyomId(pri);
                    priyomSotrud.setUserId(user);
                    priyomSotrud.setDateUpd(curDate);
                    priyomSotrudFacade.create(priyomSotrud);
                }
            }
        }

        if (!probl.isEmpty()) {
            for (String pr : probl) {
                for (Priyom pri : priyomList) {
                    PriyomProblem priyomProblem = new PriyomProblem();
                    priyomProblem.setPriyomId(pri);
                    priyomProblem.setSprproblemId(sprProblemFacade.findById(Integer.parseInt(pr)));
                    priyomProblem.setUserId(user.getUserId());
                    priyomProblem.setDateUpd(curDate);
                    priyomProblemFacade.create(priyomProblem);
                }
            }
        }

        if (!client.isEmpty()) {
            for (String cl : client) {
                String clId = request.getParameter("clId" + cl);
                String clKat = request.getParameter("clKat" + cl);
                String clUdovl = request.getParameter("monit" + cl);
                String clFirstOvz = request.getParameter("firstOvz" + cl);
                String clOu = request.getParameter("ou" + cl);
                String clNP = request.getParameter("nomPr" + cl);
                String clOpId = request.getParameter("opId" + cl);
                String clVarId = request.getParameter("varId" + cl);
                String clTpmpk = request.getParameter("tpmpk" + cl);
                String clGia = request.getParameter("gia" + cl);
                String clDopOb = request.getParameter("dopObsled" + cl);
                String clIpr = request.getParameter("ipr" + cl);
                String clSogl = request.getParameter("sogl" + cl);
                String clSrokS = request.getParameter("srokDate" + cl);
                String parentTypeId = request.getParameter("parentTypeId" + cl);
                SprParentType parentType = null;
                if (clKat.equals("parents")) {
                    try {
                        parentType = sprParentTypeFacade.findById(Integer.parseInt(parentTypeId));
                    } catch (Exception ex) {
                    }
                }
                for (Priyom pri : priyomList) {
                    PriyomClient priyomClient = new PriyomClient();
                    priyomClient.setClientId(Integer.parseInt(clId));
                    priyomClient.setPrclKatcl(clKat);
                    priyomClient.setPriyomId(pri);
                    if (clUdovl != null) {
                        priyomClient.setPrclUdovl(Integer.parseInt(clUdovl));
                    }
                    if (parentType != null) {
                        priyomClient.setSprparenttypeId(parentType);
                    }
                    priyomClient.setUserId(user);
                    priyomClient.setDateUpd(curDate);
                    priyomClientFacade.create(priyomClient);

                    if (clOu != null) {
                        Pmpk pmpk = new Pmpk();
                        pmpk.setPrclId(priyomClient);
                        if (clFirstOvz != null) {
                            if (clFirstOvz.equals("on")) {
                                pmpk.setPmpkFirstOvz(1);
                            }
                        } else {
                            pmpk.setPmpkFirstOvz(0);
                        }
                        pmpk.setPmpkNp(clNP);
                        try {
                            pmpk.setPmpkOu(Integer.parseInt(clOu));
                        } catch (Exception ex) {
                        }
                        try {
                            pmpk.setPmpkTpmpk(Integer.parseInt(clTpmpk));
                        } catch (Exception ex) {
                        }
                        try {
                            pmpk.setPmpkGia(Integer.parseInt(clGia));
                        } catch (Exception ex) {
                        }
                        if (clDopOb != null) {
                            if (clDopOb.equals("on")) {
                                pmpk.setPmpkDop(1);
                            }
                        } else {
                            pmpk.setPmpkDop(0);
                        }
                        if (clIpr != null) {
                            if (clIpr.equals("on")) {
                                pmpk.setPmpkIpr(1);
                            }
                        } else {
                            pmpk.setPmpkIpr(0);
                        }

                        try {
                            pmpk.setPmpkSogl(Integer.parseInt(clSogl));
                        } catch (Exception ex) {
                        }
                        Integer opId = 0;
                        try {
                            opId = Integer.parseInt(clOpId);
                        } catch (Exception ex) {
                        }
                        if (opId != 0) {
                            SprObr obr = null;
                            try {
                                obr = sprObrFacade.findObrById(opId);
                            } catch (Exception ex) {
                            }
                            if (obr != null) {
                                pmpk.setSprobrId(obr);
                            }
                        }

                        Integer varId = 0;
                        try {
                            varId = Integer.parseInt(clVarId);
                        } catch (Exception ex) {
                        }
                        if (varId != 0) {
                            SprObrVar var = null;
                            try {
                                var = sprObrVarFacade.findById(varId);
                            } catch (Exception ex) {
                            }
                            if (var != null) {
                                pmpk.setSprobrvarId(var);
                            }
                        }
                        Date clSrok = null;
                        if (clSrokS != null) {
                            try {
                                clSrok = format.parse(clSrokS);
                            } catch (ParseException ex) {
                            }
                        }
                        if (clSrok != null) {
                            pmpk.setPmpkDatek(clSrok);
                        }
                        pmpkFacade.create(pmpk);

                        String pmpk1ParentId = request.getParameter("pmpk1ParentId" + cl);
                        String pmpk1ParentTypeId = request.getParameter("parentTypeId1PmpkPar" + cl);
                        String pmpk2ParentId = request.getParameter("pmpk2ParentId" + cl);
                        String pmpk2ParentTypeId = request.getParameter("parentTypeId2PmpkPar" + cl);
                        if (pmpk1ParentId != null) {
                            try {
                                Parents parent = null;
                                SprParentType parType = null;
                                parent = parentsFacade.findById(Integer.parseInt(pmpk1ParentId));
                                PmpkParent pmpkParent = new PmpkParent();
                                pmpkParent.setParentId(parent);
                                pmpkParent.setPmpkId(pmpk);
                                parType = sprParentTypeFacade.findById(Integer.parseInt(pmpk1ParentTypeId));
                                pmpkParent.setSprparenttypeId(parType);
                                pmpkParentFacade.create(pmpkParent);
                            } catch (Exception ex) {
                            }
                        }
                        if (pmpk2ParentId != null) {
                            try {
                                Parents parent = null;
                                SprParentType parType = null;
                                parent = parentsFacade.findById(Integer.parseInt(pmpk2ParentId));
                                PmpkParent pmpkParent = new PmpkParent();
                                pmpkParent.setParentId(parent);
                                pmpkParent.setPmpkId(pmpk);
                                parType = sprParentTypeFacade.findById(Integer.parseInt(pmpk2ParentTypeId));
                                pmpkParent.setSprparenttypeId(parType);
                                pmpkParentFacade.create(pmpkParent);
                            } catch (Exception ex) {
                            }
                        }

                        if (pmpk1ParentId == null) {
                            String pmpkPar1Fam = request.getParameter("pmpkPar1Fam" + cl);
                            String pmpkPar1Nam = request.getParameter("pmpkPar1Nam" + cl);
                            String pmpkPar1Patr = request.getParameter("pmpkPar1Patr" + cl);
                            String reg1PmpkPar = request.getParameter("reg1PmpkPar" + cl);
                            if (pmpkPar1Fam != null) {
                                Parents parent = new Parents();
                                try {
                                    pmpkPar1Fam = pmpkPar1Fam.substring(0, 1).toUpperCase() + pmpkPar1Fam.substring(1);
                                } catch (Exception ex) {
                                }
                                parent.setParentFam(pmpkPar1Fam);
                                try {
                                    pmpkPar1Nam = pmpkPar1Nam.substring(0, 1).toUpperCase() + pmpkPar1Nam.substring(1);
                                } catch (Exception ex) {
                                }
                                parent.setParentName(pmpkPar1Nam);
                                try {
                                    pmpkPar1Patr = pmpkPar1Patr.substring(0, 1).toUpperCase() + pmpkPar1Patr.substring(1);
                                } catch (Exception ex) {
                                }
                                parent.setParentPatr(pmpkPar1Patr);
                                Integer reg = 0;
                                try {
                                    reg = Integer.parseInt(reg1PmpkPar);
                                } catch (Exception ex) {
                                }
                                if (reg != 0) {
                                    SprRegion region = sprRegionFacade.findById(reg);
                                    parent.setSprregId(region);
                                }
                                parent.setDateUpd(curDate);
                                parent.setUserId(user);
                                Nom nom = new Nom();
                                nomFacade.create(nom);
                                parent.setParentNom(nom.getNom());
                                parentsFacade.create(parent);
                                PmpkParent pmpkParent = new PmpkParent();
                                pmpkParent.setParentId(parent);
                                pmpkParent.setPmpkId(pmpk);
                                SprParentType parType = sprParentTypeFacade.findById(Integer.parseInt(pmpk1ParentTypeId));
                                pmpkParent.setSprparenttypeId(parType);
                                pmpkParentFacade.create(pmpkParent);
                            }
                        }
                        if (pmpk2ParentId == null) {
                            String pmpkPar2Fam = request.getParameter("pmpkPar2Fam" + cl);
                            String pmpkPar2Nam = request.getParameter("pmpkPar2Nam" + cl);
                            String pmpkPar2Patr = request.getParameter("pmpkPar2Patr" + cl);
                            String reg2PmpkPar = request.getParameter("reg2PmpkPar" + cl);
                            if (pmpkPar2Fam != null) {
                                Parents parent = new Parents();
                                try {
                                    pmpkPar2Fam = pmpkPar2Fam.substring(0, 1).toUpperCase() + pmpkPar2Fam.substring(1);
                                } catch (Exception ex) {
                                }
                                parent.setParentFam(pmpkPar2Fam);
                                try {
                                    pmpkPar2Nam = pmpkPar2Nam.substring(0, 1).toUpperCase() + pmpkPar2Nam.substring(1);
                                } catch (Exception ex) {
                                }
                                parent.setParentName(pmpkPar2Nam);
                                try {
                                    pmpkPar2Patr = pmpkPar2Patr.substring(0, 1).toUpperCase() + pmpkPar2Patr.substring(1);
                                } catch (Exception ex) {
                                }
                                parent.setParentPatr(pmpkPar2Patr);
                                Integer reg = 0;
                                try {
                                    reg = Integer.parseInt(reg2PmpkPar);
                                } catch (Exception ex) {
                                }
                                if (reg != 0) {
                                    SprRegion region = sprRegionFacade.findById(reg);
                                    parent.setSprregId(region);
                                }
                                parent.setDateUpd(curDate);
                                parent.setUserId(user);
                                Nom nom = new Nom();
                                nomFacade.create(nom);
                                parent.setParentNom(nom.getNom());
                                parentsFacade.create(parent);
                                PmpkParent pmpkParent = new PmpkParent();
                                pmpkParent.setParentId(parent);
                                pmpkParent.setPmpkId(pmpk);
                                SprParentType parType = sprParentTypeFacade.findById(Integer.parseInt(pmpk2ParentTypeId));
                                pmpkParent.setSprparenttypeId(parType);
                                pmpkParentFacade.create(pmpkParent);
                            }
                        }

                        if (!rek.isEmpty()) {
                            Set<String> rekomendStr = new HashSet<>();
                            for (String r : rek) {
                                String rekId = request.getParameter("rek" + r + "_" + cl);
                                if (rekId != null) {
                                    rekomendStr.add(rekId);
                                }
                            }
                            if (!rekomendStr.isEmpty()) {
                                for (String rekEl : rekomendStr) {
                                    PmpkRek pmpkRek = new PmpkRek();
                                    pmpkRek.setPmpkId(pmpk);
                                    try {
                                        pmpkRek.setSprrekId(sprRekomendFacade.findById(Integer.parseInt(rekEl)));
                                    } catch (Exception ex) {
                                    }
                                    pmpkRekFacade.create(pmpkRek);
                                }
                            }
                        }
                    }
                    if (clKat.equals("children")) {
                        Children child = childrenFacade.findById(Integer.parseInt(clId));

                        if (usl.getSpruslStat() == 1) {
                            List<ChildStatus> childStats = childStatusFacade.findByChild(child);
                            Set<String> st = new HashSet<>();
                            if (!statosn.isEmpty()) {
                                for (String stat : statosn) {
                                    String statId = request.getParameter("statosn" + stat + "_" + cl);
                                    if (statId != null) {
                                        st.add(statId);
                                    }
                                }
                            }
                            if (!statsoc.isEmpty()) {
                                for (String stat : statsoc) {
                                    String statId = request.getParameter("statsoc" + stat + "_" + cl);
                                    if (statId != null) {
                                        st.add(statId);
                                    }
                                }
                            }
                            if (!statdop.isEmpty()) {
                                for (String stat : statdop) {
                                    String statId = request.getParameter("statdop" + stat + "_" + cl);
                                    if (statId != null) {
                                        st.add(statId);
                                    }
                                }
                            }
                            if (!statpod.isEmpty()) {
                                for (String stat : statpod) {
                                    String statId = request.getParameter("statpod" + stat + "_" + cl);
                                    if (statId != null) {
                                        st.add(statId);
                                    }
                                }
                            }
                            for (String s : st) {
                                boolean flag = false;
                                Integer stId = Integer.parseInt(s);
                                SprStat status = sprStatFacade.findById(stId);
                                UslStatus uslStatus = uslStatusFacade.findByUslAndStat(usl, status);
                                if (uslStatus.getUslstatusEnabled() == 1) {
                                    for (ChildStatus cst : childStats) {
                                        if (stId.equals(cst.getSprstatId().getSprstatId())) {
                                            if (cst.getChildstatusDateK() == null) {
                                                flag = true;
                                                break;
                                            } else if (cst.getChildstatusDateK().after(pri.getPriyomDate())) {
                                                flag = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (!flag) {
                                        ChildStatus childStat = new ChildStatus();
                                        childStat.setChildId(child);
                                        childStat.setSprstatId(status);
                                        childStat.setChildstatusDateN(pri.getPriyomDate());
                                        childStat.setUserId(user);
                                        childStat.setDateUpd(curDate);
                                        childStatusFacade.create(childStat);
                                    }
                                }
                            }
                            childStats = childStatusFacade.findByChildActOnDate(child, pri.getPriyomDate());
                            for (ChildStatus cst : childStats) {
                                SprStat status = cst.getSprstatId();
                                UslStatus uslStatus = uslStatusFacade.findByUslAndStat(usl, status);
                                if (uslStatus.getUslstatusEnabled() == 1) {
                                    boolean flag = false;
                                    for (String s : st) {
                                        Integer stId = Integer.parseInt(s);
                                        if (stId.equals(cst.getSprstatId().getSprstatId())) {
                                            flag = true;
                                        }
                                    }
                                    if (!flag) {
                                        Date preDate = new Date(pri.getPriyomDate().getTime() - 24 * 60 * 60 * 1000);
                                        cst.setChildstatusDateK(preDate);
                                        cst.setUserId(user);
                                        cst.setDateUpd(curDate);
                                        childStatusFacade.edit(cst);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!newClient.isEmpty()) {
            for (String newCl : newClient) {
                String clKat = request.getParameter("clKat" + newCl);
                String clFam = request.getParameter("clFam" + newCl).trim();
                String clName = request.getParameter("clNam" + newCl).trim();
                String clPatr = request.getParameter("clPatr" + newCl).trim();
                String clReg = request.getParameter("regCl" + newCl);
                String clUdovl = request.getParameter("monit" + newCl);
                if (clKat.equals("children")) {
                    Nom nom = new Nom();
                    nomFacade.create(nom);
                    String clDr = request.getParameter("clDr" + newCl);

                    Date dr = null;
                    try {
                        dr = format.parse(clDr);
                    } catch (Exception ex) {
                    }
                    String clPol = request.getParameter("pol" + newCl);

                    List<Children> findChild = childrenFacade.findByFioDr(clFam, clName, clPatr, dr);
                    Children child = null;
                    if (findChild.isEmpty()) {
                        child = new Children();
                        child.setChildNom(nom.getNom());
                        try {
                            clFam = clFam.substring(0, 1).toUpperCase() + clFam.substring(1);
                        } catch (Exception ex) {
                        }
                        child.setChildFam(clFam);
                        try {
                            clName = clName.substring(0, 1).toUpperCase() + clName.substring(1);
                        } catch (Exception ex) {
                        }
                        child.setChildName(clName);
                        try {
                            clPatr = clPatr.substring(0, 1).toUpperCase() + clPatr.substring(1);
                        } catch (Exception ex) {
                        }
                        child.setChildPatr(clPatr);
                        child.setChildPol(clPol);
                        child.setChildDr(dr);
                        child.setSprregId(sprRegionFacade.findById(Integer.parseInt(clReg)));
                        child.setUserId(user);
                        child.setDateUpd(curDate);
                        childrenFacade.create(child);
                    } else {
                        child = findChild.get(0);
                    }

                    String clOu = request.getParameter("ou" + newCl);
                    String clNP = request.getParameter("nomPr" + newCl);
                    String clFirstOvz = request.getParameter("firstOvz" + newCl);
                    String clOpId = request.getParameter("opId" + newCl);
                    String clVarId = request.getParameter("varId" + newCl);
                    String clTpmpk = request.getParameter("tpmpk" + newCl);
                    String clGia = request.getParameter("gia" + newCl);
                    String clDopOb = request.getParameter("dopObsled" + newCl);
                    String clIpr = request.getParameter("ipr" + newCl);
                    String clSogl = request.getParameter("sogl" + newCl);
                    String clSrokS = request.getParameter("srokDate" + newCl);
                    // приём-клиент
                    for (Priyom pri : priyomList) {
                        PriyomClient priyomClient = new PriyomClient();
                        priyomClient.setClientId(child.getChildId());
                        priyomClient.setPrclKatcl(clKat);
                        if (clUdovl != null) {
                            priyomClient.setPrclUdovl(Integer.parseInt(clUdovl));
                        }
                        priyomClient.setPriyomId(pri);
                        priyomClient.setUserId(user);
                        priyomClient.setDateUpd(curDate);
                        priyomClientFacade.create(priyomClient);

                        if (clOu != null) {
                            Pmpk pmpk = new Pmpk();
                            pmpk.setPrclId(priyomClient);
                            if (clFirstOvz != null) {
                                if (clFirstOvz.equals("on")) {
                                    pmpk.setPmpkFirstOvz(1);
                                }
                            } else {
                                pmpk.setPmpkFirstOvz(0);
                            }
                            pmpk.setPmpkNp(clNP);
                            try {
                                pmpk.setPmpkOu(Integer.parseInt(clOu));
                            } catch (Exception ex) {
                            }
                            try {
                                pmpk.setPmpkTpmpk(Integer.parseInt(clTpmpk));
                            } catch (Exception ex) {
                            }
                            try {
                                pmpk.setPmpkGia(Integer.parseInt(clGia));
                            } catch (Exception ex) {
                            }
                            if (clDopOb != null) {
                                if (clDopOb.equals("on")) {
                                    pmpk.setPmpkDop(1);
                                }
                            } else {
                                pmpk.setPmpkDop(0);
                            }
                            if (clIpr != null) {
                                if (clIpr.equals("on")) {
                                    pmpk.setPmpkIpr(1);
                                }
                            } else {
                                pmpk.setPmpkIpr(0);
                            }

                            try {
                                pmpk.setPmpkSogl(Integer.parseInt(clSogl));
                            } catch (Exception ex) {
                            }

                            Integer opId = 0;
                            try {
                                opId = Integer.parseInt(clOpId);
                            } catch (Exception ex) {
                            }
                            if (opId != 0) {
                                SprObr obr = null;
                                try {
                                    obr = sprObrFacade.findObrById(opId);
                                } catch (Exception ex) {
                                }
                                if (obr != null) {
                                    pmpk.setSprobrId(obr);
                                }
                            }

                            Integer varId = 0;
                            try {
                                varId = Integer.parseInt(clVarId);
                            } catch (Exception ex) {
                            }
                            if (varId != 0) {
                                SprObrVar var = null;
                                try {
                                    var = sprObrVarFacade.findById(varId);
                                } catch (Exception ex) {
                                }
                                if (var != null) {
                                    pmpk.setSprobrvarId(var);
                                }
                            }
                            Date clSrok = null;
                            if (clSrokS != null) {
                                try {
                                    clSrok = format.parse(clSrokS);
                                } catch (ParseException ex) {
                                }
                            }
                            if (clSrok != null) {
                                pmpk.setPmpkDatek(clSrok);
                            }
                            pmpkFacade.create(pmpk);

                            String pmpk1ParentId = request.getParameter("pmpk1ParentId" + newCl);
                            String pmpk1ParentTypeId = request.getParameter("parentTypeId1PmpkPar" + newCl);
                            String pmpk2ParentId = request.getParameter("pmpk2ParentId" + newCl);
                            String pmpk2ParentTypeId = request.getParameter("parentTypeId2PmpkPar" + newCl);
                            if (pmpk1ParentId != null) {
                                try {
                                    Parents parent = null;
                                    SprParentType parType = null;
                                    parent = parentsFacade.findById(Integer.parseInt(pmpk1ParentId));
                                    PmpkParent pmpkParent = new PmpkParent();
                                    pmpkParent.setParentId(parent);
                                    pmpkParent.setPmpkId(pmpk);
                                    parType = sprParentTypeFacade.findById(Integer.parseInt(pmpk1ParentTypeId));
                                    pmpkParent.setSprparenttypeId(parType);
                                    pmpkParentFacade.create(pmpkParent);
                                } catch (Exception ex) {
                                }
                            }
                            if (pmpk2ParentId != null) {
                                try {
                                    Parents parent = null;
                                    SprParentType parType = null;
                                    parent = parentsFacade.findById(Integer.parseInt(pmpk2ParentId));
                                    PmpkParent pmpkParent = new PmpkParent();
                                    pmpkParent.setParentId(parent);
                                    pmpkParent.setPmpkId(pmpk);
                                    parType = sprParentTypeFacade.findById(Integer.parseInt(pmpk2ParentTypeId));
                                    pmpkParent.setSprparenttypeId(parType);
                                    pmpkParentFacade.create(pmpkParent);
                                } catch (Exception ex) {
                                }
                            }

                            if (pmpk1ParentId == null) {
                                String pmpkPar1Fam = request.getParameter("pmpkPar1Fam" + newCl);
                                String pmpkPar1Nam = request.getParameter("pmpkPar1Nam" + newCl);
                                String pmpkPar1Patr = request.getParameter("pmpkPar1Patr" + newCl);
                                String reg1PmpkPar = request.getParameter("reg1PmpkPar" + newCl);
                                if (pmpkPar1Fam != null) {
                                    Parents parent = new Parents();
                                    try {
                                        pmpkPar1Fam = pmpkPar1Fam.substring(0, 1).toUpperCase() + pmpkPar1Fam.substring(1);
                                    } catch (Exception ex) {
                                    }
                                    parent.setParentFam(pmpkPar1Fam);
                                    try {
                                        pmpkPar1Nam = pmpkPar1Nam.substring(0, 1).toUpperCase() + pmpkPar1Nam.substring(1);
                                    } catch (Exception ex) {
                                    }
                                    parent.setParentName(pmpkPar1Nam);
                                    try {
                                        pmpkPar1Patr = pmpkPar1Patr.substring(0, 1).toUpperCase() + pmpkPar1Patr.substring(1);
                                    } catch (Exception ex) {
                                    }
                                    parent.setParentPatr(pmpkPar1Patr);
                                    Integer reg = 0;
                                    try {
                                        reg = Integer.parseInt(reg1PmpkPar);
                                    } catch (Exception ex) {
                                    }
                                    if (reg != 0) {
                                        SprRegion region = sprRegionFacade.findById(reg);
                                        parent.setSprregId(region);
                                    }
                                    parent.setDateUpd(curDate);
                                    parent.setUserId(user);
                                    Nom n = new Nom();
                                    nomFacade.create(n);
                                    parent.setParentNom(n.getNom());
                                    parentsFacade.create(parent);
                                    PmpkParent pmpkParent = new PmpkParent();
                                    pmpkParent.setParentId(parent);
                                    pmpkParent.setPmpkId(pmpk);
                                    SprParentType parType = sprParentTypeFacade.findById(Integer.parseInt(pmpk1ParentTypeId));
                                    pmpkParent.setSprparenttypeId(parType);
                                    pmpkParentFacade.create(pmpkParent);
                                }
                            }
                            if (pmpk2ParentId == null) {
                                String pmpkPar2Fam = request.getParameter("pmpkPar2Fam" + newCl);
                                String pmpkPar2Nam = request.getParameter("pmpkPar2Nam" + newCl);
                                String pmpkPar2Patr = request.getParameter("pmpkPar2Patr" + newCl);
                                String reg2PmpkPar = request.getParameter("reg2PmpkPar" + newCl);
                                if (pmpkPar2Fam != null) {
                                    Parents parent = new Parents();
                                    try {
                                        pmpkPar2Fam = pmpkPar2Fam.substring(0, 1).toUpperCase() + pmpkPar2Fam.substring(1);
                                    } catch (Exception ex) {
                                    }
                                    parent.setParentFam(pmpkPar2Fam);
                                    try {
                                        pmpkPar2Nam = pmpkPar2Nam.substring(0, 1).toUpperCase() + pmpkPar2Nam.substring(1);
                                    } catch (Exception ex) {
                                    }
                                    parent.setParentName(pmpkPar2Nam);
                                    try {
                                        pmpkPar2Patr = pmpkPar2Patr.substring(0, 1).toUpperCase() + pmpkPar2Patr.substring(1);
                                    } catch (Exception ex) {
                                    }
                                    parent.setParentPatr(pmpkPar2Patr);
                                    Integer reg = 0;
                                    try {
                                        reg = Integer.parseInt(reg2PmpkPar);
                                    } catch (Exception ex) {
                                    }
                                    if (reg != 0) {
                                        SprRegion region = sprRegionFacade.findById(reg);
                                        parent.setSprregId(region);
                                    }
                                    parent.setDateUpd(curDate);
                                    parent.setUserId(user);
                                    Nom n = new Nom();
                                    nomFacade.create(n);
                                    parent.setParentNom(n.getNom());
                                    parentsFacade.create(parent);
                                    PmpkParent pmpkParent = new PmpkParent();
                                    pmpkParent.setParentId(parent);
                                    pmpkParent.setPmpkId(pmpk);
                                    SprParentType parType = sprParentTypeFacade.findById(Integer.parseInt(pmpk2ParentTypeId));
                                    pmpkParent.setSprparenttypeId(parType);
                                    pmpkParentFacade.create(pmpkParent);
                                }
                            }
                            if (!rek.isEmpty()) {
                                Set<String> rekomendStr = new HashSet<>();
                                for (String r : rek) {
                                    String rekId = request.getParameter("rek" + r + "_" + newCl);
                                    if (rekId != null) {
                                        rekomendStr.add(rekId);
                                    }
                                }
                                if (!rekomendStr.isEmpty()) {
                                    for (String rekEl : rekomendStr) {
                                        PmpkRek pmpkRek = new PmpkRek();
                                        pmpkRek.setPmpkId(pmpk);
                                        try {
                                            pmpkRek.setSprrekId(sprRekomendFacade.findById(Integer.parseInt(rekEl)));
                                        } catch (Exception ex) {
                                        }
                                        pmpkRekFacade.create(pmpkRek);
                                    }
                                }
                            }
                        }
                        if (usl.getSpruslStat() == 1) {
                            Set<String> st = new HashSet<>();
                            if (!statosn.isEmpty()) {
                                for (String stat : statosn) {
                                    String statId = request.getParameter("statosn" + stat + "_" + newCl);
                                    if (statId != null) {
                                        st.add(statId);
                                    }
                                }
                            }
                            if (!statsoc.isEmpty()) {
                                for (String stat : statsoc) {
                                    String statId = request.getParameter("statsoc" + stat + "_" + newCl);
                                    if (statId != null) {
                                        st.add(statId);
                                    }
                                }
                            }
                            if (!statdop.isEmpty()) {
                                for (String stat : statdop) {
                                    String statId = request.getParameter("statdop" + stat + "_" + newCl);
                                    if (statId != null) {
                                        st.add(statId);
                                    }
                                }
                            }
                            if (!statpod.isEmpty()) {
                                for (String stat : statpod) {
                                    String statId = request.getParameter("statpod" + stat + "_" + newCl);
                                    if (statId != null) {
                                        st.add(statId);
                                    }
                                }
                            }
                            for (String s : st) {
                                Integer stId = Integer.parseInt(s);
                                SprStat status = sprStatFacade.findById(stId);
                                UslStatus uslStatus = uslStatusFacade.findByUslAndStat(usl, status);
                                if (uslStatus.getUslstatusEnabled() == 1) {
                                    ChildStatus childStat = new ChildStatus();
                                    childStat.setChildId(child);
                                    childStat.setSprstatId(status);
                                    childStat.setChildstatusDateN(pri.getPriyomDate());
                                    childStat.setUserId(user);
                                    childStat.setDateUpd(curDate);
                                    childStatusFacade.create(childStat);
                                }
                            }
                        }
                    }

                } else if (clKat.equals("ped")) {
                    Nom nom = new Nom();
                    nomFacade.create(nom);
                    String clOrg = request.getParameter("clOrg" + newCl);
                    String clDol = request.getParameter("clDol" + newCl);
                    Ped ped = new Ped();
                    ped.setPedNom(nom.getNom());
                    try {
                        clFam = clFam.substring(0, 1).toUpperCase() + clFam.substring(1);
                    } catch (Exception ex) {
                    }
                    ped.setPedFam(clFam);
                    try {
                        clName = clName.substring(0, 1).toUpperCase() + clName.substring(1);
                    } catch (Exception ex) {
                    }
                    ped.setPedName(clName);
                    try {
                        clPatr = clPatr.substring(0, 1).toUpperCase() + clPatr.substring(1);
                    } catch (Exception ex) {
                    }
                    ped.setPedPatr(clPatr);
                    ped.setSprorgId(sprOrgFacade.findById(Integer.parseInt(clOrg)));
                    ped.setSprpeddolgId(sprPeddolgFacade.findById(Integer.parseInt(clDol)));
                    ped.setSprregId(sprRegionFacade.findById(Integer.parseInt(clReg)));
                    ped.setUserId(user.getUserId());
                    ped.setDateUpd(curDate);
                    pedFacade.create(ped);
                    // приём-клиент
                    for (Priyom pri : priyomList) {
                        PriyomClient priyomClient = new PriyomClient();
                        priyomClient.setClientId(ped.getPedId());
                        priyomClient.setPrclKatcl(clKat);
                        priyomClient.setPriyomId(pri);
                        if (clUdovl != null) {
                            priyomClient.setPrclUdovl(Integer.parseInt(clUdovl));
                        }
                        priyomClient.setUserId(user);
                        priyomClient.setDateUpd(curDate);
                        priyomClientFacade.create(priyomClient);
                    }
                } else if (clKat.equals("parents")) {
                    Nom nom = new Nom();
                    nomFacade.create(nom);
                    Parents parent = new Parents();
                    parent.setParentNom(nom.getNom());
                    try {
                        clFam = clFam.substring(0, 1).toUpperCase() + clFam.substring(1);
                    } catch (Exception ex) {
                    }
                    parent.setParentFam(clFam);
                    try {
                        clName = clName.substring(0, 1).toUpperCase() + clName.substring(1);
                    } catch (Exception ex) {
                    }
                    parent.setParentName(clName);
                    try {
                        clPatr = clPatr.substring(0, 1).toUpperCase() + clPatr.substring(1);
                    } catch (Exception ex) {
                    }
                    parent.setParentPatr(clPatr);
                    parent.setSprregId(sprRegionFacade.findById(Integer.parseInt(clReg)));
                    parent.setUserId(user);
                    parent.setDateUpd(curDate);
                    parentsFacade.create(parent);
                    String parentTypeId = request.getParameter("parentTypeId" + newCl);
                    SprParentType parentType = null;
                    try {
                        parentType = sprParentTypeFacade.findById(Integer.parseInt(parentTypeId));
                    } catch (Exception ex) {
                    }
                    // приём-клиент
                    for (Priyom pri : priyomList) {
                        PriyomClient priyomClient = new PriyomClient();
                        priyomClient.setClientId(parent.getParentId());
                        priyomClient.setPrclKatcl(clKat);
                        priyomClient.setPriyomId(pri);
                        if (clUdovl != null) {
                            priyomClient.setPrclUdovl(Integer.parseInt(clUdovl));
                        }
                        if (parentType != null) {
                            priyomClient.setSprparenttypeId(parentType);
                        }
                        priyomClient.setUserId(user);
                        priyomClient.setDateUpd(curDate);
                        priyomClientFacade.create(priyomClient);
                    }
                }
            }
        }

        if (!subject.isEmpty()) {
            for (String sub : subject) {
                String subId = request.getParameter("subId" + sub);
                Children child = childrenFacade.findById(Integer.parseInt(subId));
                for (Priyom pri : priyomList) {
                    PriyomSubject priyomSubject = new PriyomSubject();
                    Children childId = childrenFacade.findById(Integer.parseInt(subId));
                    priyomSubject.setChildId(childId);
                    priyomSubject.setPriyomId(pri);
                    priyomSubjectFacade.create(priyomSubject);
                    if (usl.getSpruslStat() == 1) {
                        List<ChildStatus> childStats = childStatusFacade.findByChild(child);
                        Set<String> st = new HashSet<>();
                        if (!subStatosn.isEmpty()) {
                            for (String stat : subStatosn) {
                                String statId = request.getParameter("subStatosn" + stat + "_" + sub);
                                if (statId != null) {
                                    st.add(statId);
                                }
                            }
                        }
                        if (!subStatsoc.isEmpty()) {
                            for (String stat : subStatsoc) {
                                String statId = request.getParameter("subStatsoc" + stat + "_" + sub);
                                if (statId != null) {
                                    st.add(statId);
                                }
                            }
                        }
                        if (!subStatdop.isEmpty()) {
                            for (String stat : subStatdop) {
                                String statId = request.getParameter("subStatdop" + stat + "_" + sub);
                                if (statId != null) {
                                    st.add(statId);
                                }
                            }
                        }
                        if (!subStatpod.isEmpty()) {
                            for (String stat : subStatpod) {
                                String statId = request.getParameter("subStatpod" + stat + "_" + sub);
                                if (statId != null) {
                                    st.add(statId);
                                }
                            }
                        }
                        for (String s : st) {
                            boolean flag = false;
                            Integer stId = Integer.parseInt(s);
                            SprStat status = sprStatFacade.findById(stId);
                            UslStatus uslStatus = uslStatusFacade.findByUslAndStat(usl, status);
                            if (uslStatus.getUslstatusEnabled() == 1) {
                                for (ChildStatus cst : childStats) {
                                    if (stId.equals(cst.getSprstatId().getSprstatId())) {
                                        if (cst.getChildstatusDateK() == null) {
                                            flag = true;
                                        } else if (cst.getChildstatusDateK().after(pri.getPriyomDate())) {
                                            flag = true;
                                        }
                                    }
                                }
                                if (!flag) {
                                    ChildStatus childStat = new ChildStatus();
                                    childStat.setChildId(child);
                                    childStat.setSprstatId(status);
                                    childStat.setChildstatusDateN(pri.getPriyomDate());
                                    childStat.setUserId(user);
                                    childStat.setDateUpd(curDate);
                                    childStatusFacade.create(childStat);
                                }
                            }
                        }
                        childStats = childStatusFacade.findByChildActOnDate(child, pri.getPriyomDate());
                        for (ChildStatus cst : childStats) {
                            SprStat status = cst.getSprstatId();
                            UslStatus uslStatus = uslStatusFacade.findByUslAndStat(usl, status);
                            if (uslStatus.getUslstatusEnabled() == 1) {
                                boolean flag = false;
                                for (String s : st) {
                                    Integer stId = Integer.parseInt(s);
                                    if (stId.equals(cst.getSprstatId().getSprstatId())) {
                                        flag = true;
                                    }
                                }
                                if (!flag) {
                                    // предыдущий день
                                    Date preDate = new Date(pri.getPriyomDate().getTime() - 24 * 60 * 60 * 1000);
                                    cst.setChildstatusDateK(preDate);
                                    cst.setUserId(user);
                                    cst.setDateUpd(curDate);
                                    childStatusFacade.edit(cst);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!newSubject.isEmpty()) {
            for (String newSub : newSubject) {
                String subFam = request.getParameter("subFam" + newSub).trim();
                String subName = request.getParameter("subNam" + newSub).trim();
                String subPatr = request.getParameter("subPatr" + newSub).trim();
                String subPol = request.getParameter("subPol" + newSub);
                String subReg = request.getParameter("regSub" + newSub);
                Nom nom = new Nom();
                nomFacade.create(nom);
                String subDr = request.getParameter("subDr" + newSub);
                Children child = new Children();
                child.setChildNom(nom.getNom());
                try {
                    subFam = subFam.substring(0, 1).toUpperCase() + subFam.substring(1);
                } catch (Exception ex) {
                }
                child.setChildFam(subFam);
                try {
                    subName = subName.substring(0, 1).toUpperCase() + subName.substring(1);
                } catch (Exception ex) {
                }
                child.setChildName(subName);
                try {
                    subPatr = subPatr.substring(0, 1).toUpperCase() + subPatr.substring(1);
                } catch (Exception ex) {
                }
                child.setChildPatr(subPatr);
                child.setChildPol(subPol);
                Date dr = null;
                try {
                    dr = format.parse(subDr);
                } catch (Exception ex) {
                }
                child.setChildDr(dr);
                child.setSprregId(sprRegionFacade.findById(Integer.parseInt(subReg)));
                child.setUserId(user);
                child.setDateUpd(curDate);
                childrenFacade.create(child);

                for (Priyom pri : priyomList) {
                    PriyomSubject priyomSubject = new PriyomSubject();
                    priyomSubject.setChildId(child);
                    priyomSubject.setPriyomId(pri);
                    priyomSubjectFacade.create(priyomSubject);
                    if (usl.getSpruslStat() == 1) {
                        List<ChildStatus> childStats = childStatusFacade.findByChild(child);
                        Set<String> st = new HashSet<>();
                        if (!subStatosn.isEmpty()) {
                            for (String stat : subStatosn) {
                                String statId = request.getParameter("subStatosn" + stat + "_" + newSub);
                                if (statId != null) {
                                    st.add(statId);
                                }
                            }
                        }
                        if (!subStatsoc.isEmpty()) {
                            for (String stat : subStatsoc) {
                                String statId = request.getParameter("subStatsoc" + stat + "_" + newSub);
                                if (statId != null) {
                                    st.add(statId);
                                }
                            }
                        }
                        if (!subStatdop.isEmpty()) {
                            for (String stat : subStatdop) {
                                String statId = request.getParameter("subStatdop" + stat + "_" + newSub);
                                if (statId != null) {
                                    st.add(statId);
                                }
                            }
                        }
                        if (!subStatpod.isEmpty()) {
                            for (String stat : subStatpod) {
                                String statId = request.getParameter("subStatpod" + stat + "_" + newSub);
                                if (statId != null) {
                                    st.add(statId);
                                }
                            }
                        }
                        for (String s : st) {
                            boolean flag = false;
                            Integer stId = Integer.parseInt(s);
                            SprStat status = sprStatFacade.findById(stId);
                            UslStatus uslStatus = uslStatusFacade.findByUslAndStat(usl, status);
                            if (uslStatus.getUslstatusEnabled() == 1) {
                                for (ChildStatus cst : childStats) {
                                    if (stId.equals(cst.getSprstatId().getSprstatId())) {
                                        if (cst.getChildstatusDateK() == null) {
                                            flag = true;
                                        } else if (cst.getChildstatusDateK().after(pri.getPriyomDate())) {
                                            flag = true;
                                        }
                                    }
                                }
                                if (!flag) {
                                    ChildStatus childStat = new ChildStatus();
                                    childStat.setChildId(child);
                                    childStat.setSprstatId(status);
                                    childStat.setChildstatusDateN(pri.getPriyomDate());
                                    childStat.setUserId(user);
                                    childStat.setDateUpd(curDate);
                                    childStatusFacade.create(childStat);
                                }
                            }
                        }
                        childStats = childStatusFacade.findByChildActOnDate(child, pri.getPriyomDate());
                        for (ChildStatus cst : childStats) {
                            SprStat status = cst.getSprstatId();
                            UslStatus uslStatus = uslStatusFacade.findByUslAndStat(usl, status);
                            if (uslStatus.getUslstatusEnabled() == 1) {
                                boolean flag = false;
                                for (String s : st) {
                                    Integer stId = Integer.parseInt(s);
                                    if (stId.equals(cst.getSprstatId().getSprstatId())) {
                                        flag = true;
                                    }
                                }
                                if (!flag) {
                                    // предыдущий день
                                    Date preDate = new Date(pri.getPriyomDate().getTime() - 24 * 60 * 60 * 1000);
                                    cst.setChildstatusDateK(preDate);
                                    cst.setUserId(user);
                                    cst.setDateUpd(curDate);
                                    childStatusFacade.edit(cst);
                                }
                            }
                        }
                    }
                }
            }
        }

        for (Priyom pri : priyomList) {
            List<PriyomClient> prClList = priyomClientFacade.findByPriyom(pri);
            for (PriyomClient prCl : prClList) {
                Boolean flag = true;
                if (prCl.getSprparenttypeId() != null) {
                    if (prCl.getSprparenttypeId().getSprparenttypeName().equals("по доверенности")) {
                        flag = false;
                    }
                }
                if (flag) {
                    List<Pmpk> pmpk = pmpkFacade.findByPrcl(prCl);
                    Integer clientFamilyNom = 0;
                    List<Integer> existFamilyNom = new ArrayList<>();
                    for (Pmpk pk : pmpk) {
                        List<Family> familyClientSearch = familyFacade.findByClient(prCl.getPrclKatcl(), prCl.getClientId());
                        if (!familyClientSearch.isEmpty()) {
                            clientFamilyNom = familyClientSearch.get(0).getFamNom();
                        }
                        List<PmpkParent> parentsList = pmpkParentFacade.findByPmpk(pk);
                        if (!parentsList.isEmpty()) {
                            for (PmpkParent pkParent : parentsList) {
                                if (!pkParent.getSprparenttypeId().getSprparenttypeName().equals("по доверенности")) {
                                    int i = 0;
                                    List<Family> familySearch = familyFacade.findByClient("parents", pkParent.getParentId().getParentId());
                                    if (!familySearch.isEmpty()) {
                                        existFamilyNom.add(familySearch.get(0).getFamNom());
                                    }
                                    i++;
                                }
                            }
                        }
                        if ((clientFamilyNom == 0) && (existFamilyNom.isEmpty())) {
                            Family family = new Family();
                            FamilyNom familyNom = new FamilyNom();
                            familyNomFacade.create(familyNom);
                            family.setFamNom(familyNom.getFamNom());
                            family.setClientId(prCl.getClientId());
                            family.setFamKatcl(prCl.getPrclKatcl());
                            familyFacade.create(family);
                            for (PmpkParent pkParent : parentsList) {
                                if (!pkParent.getSprparenttypeId().getSprparenttypeName().equals("по доверенности")) {
                                    Family parentFam = new Family();
                                    parentFam.setClientId(pkParent.getParentId().getParentId());
                                    parentFam.setFamKatcl("parents");
                                    parentFam.setFamNom(familyNom.getFamNom());
                                    familyFacade.create(parentFam);
                                }
                            }
                        } else if ((clientFamilyNom != 0) && (existFamilyNom.isEmpty())) {
                            for (PmpkParent pkParent : parentsList) {
                                if (!pkParent.getSprparenttypeId().getSprparenttypeName().equals("по доверенности")) {
                                    Family parentFam = new Family();
                                    parentFam.setClientId(pkParent.getParentId().getParentId());
                                    parentFam.setFamKatcl("parents");
                                    parentFam.setFamNom(clientFamilyNom);
                                    familyFacade.create(parentFam);
                                }
                            }
                        } else if ((clientFamilyNom == 0) && (!existFamilyNom.isEmpty())) {
                            Family family = new Family();
                            family.setClientId(prCl.getClientId());
                            family.setFamKatcl(prCl.getPrclKatcl());
                            Integer nomer = existFamilyNom.get(0);
                            family.setFamNom(nomer);
                            familyFacade.create(family);
                            for (PmpkParent pkParent : parentsList) {
                                if (!pkParent.getSprparenttypeId().getSprparenttypeName().equals("по доверенности")) {
                                    List<Family> parentFamily = familyFacade.findByClient("parents", pkParent.getParentId().getParentId());
                                    if (parentFamily.isEmpty()) {
                                        Family parentFam = new Family();
                                        parentFam.setClientId(pkParent.getParentId().getParentId());
                                        parentFam.setFamKatcl("parents");
                                        parentFam.setFamNom(nomer);
                                        familyFacade.create(parentFam);
                                    } else {
                                        for (Family parF : parentFamily) {
                                            if (!parF.getFamNom().equals(nomer)) {
                                                List<Family> families = familyFacade.findByFamNom(parF.getFamNom());
                                                for (Family fam : families) {
                                                    fam.setFamNom(nomer);
                                                    familyFacade.edit(fam);
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }

            List<PriyomSubject> prSubList = priyomSubjectFacade.findByPriyom(pri);
            if (!prSubList.isEmpty()) {
                List<Integer> subFamNoms = new ArrayList<>();
                List<Integer> clFamNoms = new ArrayList<>();
                for (PriyomSubject prSub : prSubList) {
                    List<Family> subFamList = familyFacade.findByClient("children", prSub.getChildId().getChildId());
                    if (!subFamList.isEmpty()) {
                        subFamNoms.add(subFamList.get(0).getFamNom());
                    }
                }
                for (PriyomClient prCl : prClList) {
                    if (!prCl.getPrclKatcl().equals("ped")) {
                        Boolean flag = true;
                        if (prCl.getSprparenttypeId() != null) {
                            if (prCl.getSprparenttypeId().getSprparenttypeName().equals("по доверенности")) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            List<Family> clFamList = familyFacade.findByClient(prCl.getPrclKatcl(), prCl.getClientId());
                            if (!clFamList.isEmpty()) {
                                clFamNoms.add(clFamList.get(0).getFamNom());
                            }
                        }
                    }
                }

                if ((clFamNoms.isEmpty()) && (subFamNoms.isEmpty())) {
                    FamilyNom familyNom = new FamilyNom();
                    familyNomFacade.create(familyNom);
                    for (PriyomSubject prSub : prSubList) {
                        Family family = new Family();
                        family.setFamNom(familyNom.getFamNom());
                        family.setFamKatcl("children");
                        family.setClientId(prSub.getChildId().getChildId());
                        familyFacade.create(family);
                    }
                    for (PriyomClient prCl : prClList) {
                        if (!prCl.getPrclKatcl().equals("ped")) {
                            Boolean flag = true;
                            if (prCl.getSprparenttypeId() != null) {
                                if (prCl.getSprparenttypeId().getSprparenttypeName().equals("по доверенности")) {
                                    flag = false;
                                }
                            }
                            if (flag) {
                                Family family = new Family();
                                family.setFamNom(familyNom.getFamNom());
                                family.setFamKatcl(prCl.getPrclKatcl());
                                family.setClientId(prCl.getClientId());
                                familyFacade.create(family);
                            }
                        }
                    }
                } else if ((!clFamNoms.isEmpty()) && (subFamNoms.isEmpty())) {
                    Integer nomer = clFamNoms.get(0);
                    for (PriyomSubject prSub : prSubList) {
                        Family family = new Family();
                        family.setClientId(prSub.getChildId().getChildId());
                        family.setFamKatcl("children");
                        family.setFamNom(nomer);
                        familyFacade.create(family);
                    }
                    for (PriyomClient prCl : prClList) {
                        if (!prCl.getPrclKatcl().equals("ped")) {
                            Boolean flag = true;
                            if (prCl.getSprparenttypeId() != null) {
                                if (prCl.getSprparenttypeId().getSprparenttypeName().equals("по доверенности")) {
                                    flag = false;
                                }
                            }
                            if (flag) {
                                List<Family> parentFamily = familyFacade.findByClient(prCl.getPrclKatcl(), prCl.getClientId());
                                if (parentFamily.isEmpty()) {
                                    Family parentFam = new Family();
                                    parentFam.setClientId(prCl.getClientId());
                                    parentFam.setFamKatcl(prCl.getPrclKatcl());
                                    parentFam.setFamNom(nomer);
                                    familyFacade.create(parentFam);
                                } else {
                                    for (Family parF : parentFamily) {
                                        if (!parF.getFamNom().equals(nomer)) {
                                            List<Family> families = familyFacade.findByFamNom(parF.getFamNom());
                                            for (Family fam : families) {
                                                fam.setFamNom(nomer);
                                                familyFacade.edit(fam);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if ((clFamNoms.isEmpty()) && (!subFamNoms.isEmpty())) {
                    Integer nomer = subFamNoms.get(0);
                    for (PriyomSubject prSub : prSubList) {
                        List<Family> subjFamily = familyFacade.findByClient("children", prSub.getChildId().getChildId());
                        if (subjFamily.isEmpty()) {
                            Family subjFam = new Family();
                            subjFam.setClientId(prSub.getChildId().getChildId());
                            subjFam.setFamKatcl("children");
                            subjFam.setFamNom(nomer);
                            familyFacade.create(subjFam);
                        } else {
                            for (Family sF : subjFamily) {
                                sF.setFamNom(nomer);
                                familyFacade.edit(sF);
                            }
                        }
                    }
                    for (PriyomClient prCl : prClList) {
                        if (!prCl.getPrclKatcl().equals("ped")) {
                            Boolean flag = true;
                            if (prCl.getSprparenttypeId() != null) {
                                if (prCl.getSprparenttypeId().getSprparenttypeName().equals("по доверенности")) {
                                    flag = false;
                                }
                            }
                            if (flag) {
                                Family clFam = new Family();
                                clFam.setClientId(prCl.getClientId());
                                clFam.setFamKatcl(prCl.getPrclKatcl());
                                clFam.setFamNom(nomer);
                                familyFacade.create(clFam);
                            }
                        }
                    }
                }
            }
        }
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
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
