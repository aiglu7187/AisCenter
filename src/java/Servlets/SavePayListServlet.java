/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.LoginLog;
import Entities.Nom;
import Entities.Parents;
import Entities.PayDogovor;
import Entities.PayUsl;
import Entities.PayuslClient;
import Entities.PayuslLesson;
import Entities.PayuslSotrud;
import Entities.Payusllespos;
import Entities.Ped;
import Entities.Sotrud;
import Entities.SprPayUsl;
import Entities.Telephon;
import Entities.Users;
import Sessions.ChildrenFacade;
import Sessions.LoginLogFacade;
import Sessions.NomFacade;
import Sessions.ParentsFacade;
import Sessions.PayDogovorFacade;
import Sessions.PayUslFacade;
import Sessions.PayuslClientFacade;
import Sessions.PayuslLessonFacade;
import Sessions.PayuslSotrudFacade;
import Sessions.PayusllesposFacade;
import Sessions.PedFacade;
import Sessions.SotrudFacade;
import Sessions.SprOrgFacade;
import Sessions.SprPayUslFacade;
import Sessions.SprPeddolgFacade;
import Sessions.SprRegionFacade;
import Sessions.TelephonFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
@WebServlet(name = "SavePayListServlet",
        loadOnStartup = 1,
        urlPatterns = "/savepaylist")

public class SavePayListServlet extends HttpServlet {

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
    SprPayUslFacade sprPayUslFacade = new SprPayUslFacade();
    @EJB
    SotrudFacade sotrudFacade = new SotrudFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    NomFacade nomFacade = new NomFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    PayUslFacade payUslFacade = new PayUslFacade();
    @EJB
    PayuslLessonFacade payuslLessonFacade = new PayuslLessonFacade();
    @EJB
    PayuslClientFacade payuslClientFacade = new PayuslClientFacade();
    @EJB
    ParentsFacade parentsFacade = new ParentsFacade();
    @EJB
    PedFacade pedFacade = new PedFacade();
    @EJB
    SprOrgFacade sprOrgFacade = new SprOrgFacade();
    @EJB
    SprPeddolgFacade sprPeddolgFacade = new SprPeddolgFacade();
    @EJB
    PayuslSotrudFacade payuslSotrudFacade = new PayuslSotrudFacade();
    @EJB
    PayDogovorFacade payDogovorFacade = new PayDogovorFacade();
    @EJB
    TelephonFacade telephonFacade = new TelephonFacade();
    @EJB
    PayusllesposFacade payusllesposFacade = new PayusllesposFacade();

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

        String payListIdS = request.getParameter("uslId");
        Integer payListId = 0;
        try {
            payListId = Integer.parseInt(payListIdS);
        } catch (Exception ex) {
        }
        PayUsl payUsl = null;

        String payUslIdS = null;
        String datesS = null;
        String kolS = null;
        List<String> sotr = new ArrayList<>();
        List<String> client = new ArrayList<>();
        List<String> newClient = new ArrayList<>();

        Map<String, String[]> param = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : param.entrySet()) {
            String par = entry.getKey();
            String[] val = entry.getValue();
            if (par.equals("payUslId")) {
                payUslIdS = val[0];
            } else if (par.equals("kol")) {
                kolS = val[0];
            } else if (par.equals("dates")) {
                datesS = val[0];
            } else if (par.startsWith("sotrId")) {
                sotr.add(val[0]);
            } else if (par.startsWith("clId")) {
                if (!val[0].equals("")) {
                    client.add(par.substring(4));
                } else {
                    newClient.add(par.substring(4));
                }
            }
        }

        if (payListId != 0) {
            payUsl = payUslFacade.findById(payListId);
        } else {
            payUsl = new PayUsl();
        }
        String inpGroup = request.getParameter("inpGroup");
        payUsl.setPayuslName(inpGroup);
        Integer payUslId = 0;
        try {
            payUslId = Integer.parseInt(payUslIdS);
        } catch (Exception ex) {
        }
        SprPayUsl sprPayUsl = null;
        if (payUslId != 0) {
            sprPayUsl = sprPayUslFacade.findById(payUslId);
        }
        payUsl.setSprpayuslId(sprPayUsl);
        payUsl.setUserId(user);
        payUsl.setDateUpd(curDate);
        payUsl.setPayuslStatus("подготовлено");

        List<PayuslLesson> puLessonsOld = new ArrayList<>();
        List<PayuslClient> puClientsOld = new ArrayList<>();
        List<PayuslSotrud> puSotrudsOld = new ArrayList<>();
        List<PayDogovor> puDogovorOld = new ArrayList<>();
        if (payListId != 0) {
            payUslFacade.edit(payUsl);
            puLessonsOld = payuslLessonFacade.findByPayUsl(payUsl);
            puClientsOld = payuslClientFacade.findByPayusl(payUsl);
            puSotrudsOld = payuslSotrudFacade.findByPayUsl(payUsl);
            puDogovorOld = payDogovorFacade.findByPayUsl(payUsl);
        } else {
            payUslFacade.create(payUsl);
        }

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");

        List<Date> dateList = new ArrayList<>();
        if (datesS != null) {
            String[] dates = datesS.split(";");
            for (String date : dates) {
                Calendar c = Calendar.getInstance();
                String[] splitDate = date.split("\\.");
                Integer day = Integer.parseInt(splitDate[0]);
                Integer mon = Integer.parseInt(splitDate[1]) - 1;
                Integer year = Integer.parseInt(splitDate[2]);
                c.set(Calendar.DATE, day);
                c.set(Calendar.MONTH, mon);
                c.set(Calendar.YEAR, year);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                dateList.add(c.getTime());
            }
        }

        Integer kol = 0;
        try {
            kol = Integer.parseInt(kolS);
        } catch (Exception ex) {
        }

        for (Date date : dateList) {
            boolean flag = false;
            for (PayuslLesson puLO : puLessonsOld) {
                if (puLO.getPayusllessonDate().equals(date)) {
                    flag = true;
                }
            }
            if (!flag) {
                PayuslLesson puLesson = new PayuslLesson();
                puLesson.setPayuslId(payUsl);
                puLesson.setPayusllessonDate(date);
                puLesson.setUserId(user);
                puLesson.setDateUpd(curDate);
                payuslLessonFacade.create(puLesson);
            }
        }
        List<PayuslLesson> puLessons = payuslLessonFacade.findByPayUsl(payUsl);
        for (PayuslLesson puL : puLessons) {
            boolean flag = false;
            for (Date date : dateList) {
                if (puL.getPayusllessonDate().equals(date)) {
                    flag = true;
                }
            }
            if (!flag) {
                payuslLessonFacade.remove(puL);
            }
        }

        List<Sotrud> sotruds = new ArrayList<>();
        for (String s : sotr) {
            Sotrud sotrud = null;
            try {
                sotrud = sotrudFacade.findById(Integer.parseInt(s));
            } catch (Exception ex) {
            }
            if (sotrud != null) {
                sotruds.add(sotrud);
            }
        }
        for (Sotrud s : sotruds) {
            boolean flag = false;
            for (PayuslSotrud puSO : puSotrudsOld) {
                if (puSO.getSotrudId().equals(s)) {
                    flag = true;
                }
            }
            if (!flag) {
                PayuslSotrud puSotrud = new PayuslSotrud();
                puSotrud.setPayuslId(payUsl);
                puSotrud.setSotrudId(s);
                puSotrud.setUserId(user);
                puSotrud.setDateUpd(curDate);
                payuslSotrudFacade.create(puSotrud);
            }
        }

        List<PayuslSotrud> puSotruds = payuslSotrudFacade.findByPayUsl(payUsl);
        for (PayuslSotrud puS : puSotruds) {
            boolean flag = false;
            for (Sotrud s : sotruds) {
                if (puS.getSotrudId().equals(s)) {
                    flag = true;
                }
            }
            if (!flag) {
                payuslSotrudFacade.remove(puS);
            }
        }
// клиенты
        for (String cl : client) {
            String clIdS = request.getParameter("clId" + cl);
            String clKat = request.getParameter("clKat" + cl);
            Integer clId = 0;
            try {
                clId = Integer.parseInt(clIdS);
            } catch (Exception ex) {
            }
            if (clId != 0) {
                boolean flag = false;
                for (PayuslClient puCO : puClientsOld) {
                    if ((puCO.getPayuslclientKatcl().equals(clKat)) && (puCO.getClientId().equals(clId))) {
                        flag = true;
                    }
                }
                if (!flag) {
                    PayuslClient puClient = new PayuslClient();
                    puClient.setPayuslId(payUsl);
                    puClient.setClientId(clId);
                    puClient.setPayuslclientKatcl(clKat);
                    puClient.setUserId(user);
                    puClient.setDateUpd(curDate);
                    payuslClientFacade.create(puClient);
                }

                if (clKat.equals("children")) {
                    Children child = childrenFacade.findById(clId);
                    String dogN = request.getParameter("dogN" + cl);
                    String dogDS = request.getParameter("dogD" + cl);
                    String dogClId = request.getParameter("dogClId" + cl);
                    if (dogDS != null) {
                        boolean flag2 = false;
                        for (PayDogovor puD : puDogovorOld) {
                            if (puD.getChildId().equals(child)) {
                                flag2 = true;
                                puD.setPaydogN(dogN);
                                Date dogD = null;
                                try {
                                    dogD = format.parse(dogDS);
                                } catch (ParseException ex) {
                                    Logger.getLogger(SavePayListServlet.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                puD.setPaydogD(dogD);
                                Parents parent = null;
                                String telephon = request.getParameter("telephon" + cl);
                                if ((dogClId != null) && (!dogClId.equals(""))) {
                                    parent = parentsFacade.findById(Integer.parseInt(dogClId));
                                    if (telephon != null) {
                                        List<Telephon> telPar = telephonFacade.findByParent(parent);
                                        if (telPar.isEmpty()) {
                                            Telephon tel = new Telephon();
                                            tel.setParentId(parent);
                                            tel.setTel(telephon);
                                            telephonFacade.create(tel);
                                        } else {
                                            for (Telephon t : telPar) {
                                                t.setTel(telephon);
                                                telephonFacade.edit(t);
                                            }
                                        }
                                    }
                                } else {
                                    String dogClFam = request.getParameter("dogClFam" + cl);
                                    String dogClName = request.getParameter("dogClNam" + cl);
                                    String dogClPatr = request.getParameter("dogClPatr" + cl);
                                    String regDog = request.getParameter("regDog" + cl);
                                    if (dogClFam != null) {
                                        Nom nom = new Nom();
                                        nomFacade.create(nom);
                                        parent = new Parents();
                                        parent.setParentNom(nom.getNom());
                                        parent.setParentFam(dogClFam);
                                        parent.setParentName(dogClName);
                                        parent.setParentPatr(dogClPatr);
                                        parent.setDateUpd(curDate);
                                        parent.setSprregId(sprRegionFacade.findById(Integer.parseInt(regDog)));
                                        parent.setUserId(user);
                                        parentsFacade.create(parent);
                                        if (telephon != null) {
                                            Telephon tel = new Telephon();
                                            tel.setParentId(parent);
                                            tel.setTel(telephon);
                                            telephonFacade.create(tel);
                                        }
                                    }
                                }
                                puD.setParentId(parent);
                                puD.setUserId(user);
                                puD.setDateUpd(curDate);
                                payDogovorFacade.edit(puD);
                            }
                        }
                        if (!flag2) {
                            PayDogovor dogovor = new PayDogovor();
                            dogovor.setPaydogN(dogN);
                            dogovor.setChildId(child);
                            Date dogD = null;
                            try {
                                dogD = format.parse(dogDS);
                            } catch (ParseException ex) {
                                Logger.getLogger(SavePayListServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            dogovor.setPaydogD(dogD);
                            Parents parent = null;
                            String telephon = request.getParameter("telephon" + cl);
                            if ((dogClId != null) && (!dogClId.equals(""))) {
                                parent = parentsFacade.findById(Integer.parseInt(dogClId));
                                if (telephon != null) {
                                    List<Telephon> telPar = telephonFacade.findByParent(parent);
                                    if (telPar.isEmpty()) {
                                        Telephon tel = new Telephon();
                                        tel.setParentId(parent);
                                        tel.setTel(telephon);
                                        telephonFacade.create(tel);
                                    } else {
                                        for (Telephon t : telPar) {
                                            t.setTel(telephon);
                                            telephonFacade.edit(t);
                                        }
                                    }
                                }
                            } else {
                                String dogClFam = request.getParameter("dogClFam" + cl);
                                String dogClName = request.getParameter("dogClNam" + cl);
                                String dogClPatr = request.getParameter("dogClPatr" + cl);
                                String regDog = request.getParameter("regDog" + cl);
                                if (dogClFam != null) {
                                    Nom nom = new Nom();
                                    nomFacade.create(nom);
                                    parent = new Parents();
                                    parent.setParentNom(nom.getNom());
                                    parent.setParentFam(dogClFam);
                                    parent.setParentName(dogClName);
                                    parent.setParentPatr(dogClPatr);
                                    parent.setDateUpd(curDate);
                                    parent.setSprregId(sprRegionFacade.findById(Integer.parseInt(regDog)));
                                    parent.setUserId(user);
                                    parentsFacade.create(parent);
                                    if (telephon != null) {
                                        Telephon tel = new Telephon();
                                        tel.setParentId(parent);
                                        tel.setTel(telephon);
                                        telephonFacade.create(tel);
                                    }
                                }
                            }
                            dogovor.setParentId(parent);
                            dogovor.setUserId(user);
                            dogovor.setDateUpd(curDate);
                            dogovor.setPayuslId(payUsl);
                            payDogovorFacade.create(dogovor);

                        }
                    }
                }

            }
        }

        List<PayuslClient> puClients = payuslClientFacade.findByPayusl(payUsl);
        for (PayuslClient puC : puClients) {
            boolean flag = false;
            for (String cl : client) {
                String clIdS = request.getParameter("clId" + cl);
                String clKat = request.getParameter("clKat" + cl);
                Integer clId = 0;
                try {
                    clId = Integer.parseInt(clIdS);
                } catch (Exception ex) {
                }
                if (clId != 0) {
                    if ((puC.getClientId().equals(clId)) && (puC.getPayuslclientKatcl().equals(clKat))) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                List<PayDogovor> dogovors = payDogovorFacade.findByPayUslAndChild(payUsl, childrenFacade.findById(puC.getClientId()));
                payuslClientFacade.remove(puC);
                for (PayDogovor dogovor : dogovors) {
                    payDogovorFacade.remove(dogovor);
                }
            }
        }

        for (String newCl : newClient) {
            String clFam = request.getParameter("clFam" + newCl).trim();
            String clName = request.getParameter("clNam" + newCl).trim();
            String clPatr = request.getParameter("clPatr" + newCl).trim();
            String clReg = request.getParameter("regCl" + newCl);
            String clKat = request.getParameter("clKat" + newCl);
            if (clKat.equals("children")) {
                Nom nom = new Nom();
                nomFacade.create(nom);
                String clDr = request.getParameter("clDr" + newCl);
                Date dr = null;
                try {
                    dr = format.parse(clDr);
                } catch (Exception ex) {
                }
                List<Children> findChild = childrenFacade.findByFioDr(clFam, clName, clPatr, dr);
                Children child = null;
                if (findChild.isEmpty()) {
                    child = new Children();
                    child.setChildNom(nom.getNom());
                    child.setChildFam(clFam);
                    child.setChildName(clName);
                    child.setChildPatr(clPatr);
                    child.setChildDr(dr);
                    child.setSprregId(sprRegionFacade.findById(Integer.parseInt(clReg)));
                    child.setUserId(user);
                    child.setDateUpd(curDate);
                    childrenFacade.create(child);
                } else {
                    child = findChild.get(0);
                }
                if (child != null) {
                    PayuslClient puClient = new PayuslClient();
                    puClient.setPayuslId(payUsl);
                    puClient.setClientId(child.getChildId());
                    puClient.setPayuslclientKatcl(clKat);
                    puClient.setUserId(user);
                    puClient.setDateUpd(curDate);
                    payuslClientFacade.create(puClient);
                }
                String dogN = request.getParameter("dogN" + newCl);
                String dogDS = request.getParameter("dogD" + newCl);
                String dogClId = request.getParameter("dogClId" + newCl);
                if (dogDS != null) {
                    PayDogovor dogovor = new PayDogovor();
                    dogovor.setPaydogN(dogN);
                    Date dogD = null;
                    try {
                        dogD = format.parse(dogDS);
                    } catch (ParseException ex) {
                        Logger.getLogger(SavePayListServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    dogovor.setPaydogD(dogD);
                    Parents parent = null;
                    String telephon = request.getParameter("telephon" + newCl);
                    if ((dogClId != null) && (!dogClId.equals(""))) {
                        parent = parentsFacade.findById(Integer.parseInt(dogClId));
                        if (telephon != null) {
                            Telephon tel = new Telephon();
                            tel.setParentId(parent);
                            tel.setTel(telephon);
                            telephonFacade.create(tel);
                        }
                    } else {
                        String dogClFam = request.getParameter("dogClFam" + newCl);
                        String dogClName = request.getParameter("dogClNam" + newCl);
                        String dogClPatr = request.getParameter("dogClPatr" + newCl);
                        String regDog = request.getParameter("regDog" + newCl);
                        if (dogClFam != null) {
                            Nom nom2 = new Nom();
                            nomFacade.create(nom2);
                            parent = new Parents();
                            parent.setParentNom(nom2.getNom());
                            parent.setParentFam(dogClFam);
                            parent.setParentName(dogClName);
                            parent.setParentPatr(dogClPatr);
                            parent.setDateUpd(curDate);
                            parent.setSprregId(sprRegionFacade.findById(Integer.parseInt(regDog)));
                            parent.setUserId(user);
                            parentsFacade.create(parent);
                            if (telephon != null) {
                                Telephon tel = new Telephon();
                                tel.setParentId(parent);
                                tel.setTel(telephon);
                                telephonFacade.create(tel);
                            }
                        }
                    }
                    dogovor.setParentId(parent);
                    dogovor.setUserId(user);
                    dogovor.setDateUpd(curDate);
                    payDogovorFacade.create(dogovor);
                }
            } else if (clKat.equals("ped")) {
                Nom nom = new Nom();
                nomFacade.create(nom);
                String clOrg = request.getParameter("clOrg" + newCl);
                String clDol = request.getParameter("clDol" + newCl);
                Ped ped = new Ped();
                ped.setPedNom(nom.getNom());
                ped.setPedFam(clFam);
                ped.setPedName(clName);
                ped.setPedPatr(clPatr);
                ped.setSprorgId(sprOrgFacade.findById(Integer.parseInt(clOrg)));
                ped.setSprpeddolgId(sprPeddolgFacade.findById(Integer.parseInt(clDol)));
                ped.setSprregId(sprRegionFacade.findById(Integer.parseInt(clReg)));
                ped.setUserId(user.getUserId());
                ped.setDateUpd(curDate);
                pedFacade.create(ped);
                PayuslClient puClient = new PayuslClient();
                puClient.setPayuslId(payUsl);
                puClient.setClientId(ped.getPedId());
                puClient.setPayuslclientKatcl(clKat);
                puClient.setUserId(user);
                puClient.setDateUpd(curDate);
                payuslClientFacade.create(puClient);
            } else if (clKat.equals("parents")) {
                Nom nom = new Nom();
                nomFacade.create(nom);
                Parents parent = new Parents();
                parent.setParentNom(nom.getNom());
                parent.setParentFam(clFam);
                parent.setParentName(clName);
                parent.setParentPatr(clPatr);
                parent.setSprregId(sprRegionFacade.findById(Integer.parseInt(clReg)));
                parent.setUserId(user);
                parent.setDateUpd(curDate);
                parentsFacade.create(parent);
                PayuslClient puClient = new PayuslClient();
                puClient.setPayuslId(payUsl);
                puClient.setClientId(parent.getParentId());
                puClient.setPayuslclientKatcl(clKat);
                puClient.setUserId(user);
                puClient.setDateUpd(curDate);
                payuslClientFacade.create(puClient);
            }
        }

        String incl = request.getParameter("incl");
        String inclDate = request.getParameter("inclDate");
        if (incl != null) {
            if (incl.equals("1")) {
                List<PayuslClient> allClients = payuslClientFacade.findByPayusl(payUsl);
                List<PayuslLesson> allLessons = payuslLessonFacade.findByPayUsl(payUsl);
                Date inDate = null;
                try {
                    inDate = format.parse(inclDate);
                } catch (ParseException ex) {
                    Logger.getLogger(SavePayListServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (inDate != null) {
                    for (PayuslClient cl : allClients) {
                        cl.setPayuslDaten(inDate);
                        payuslClientFacade.edit(cl);
                    }
                }
                payUsl.setPayuslDate(inDate);
                payUsl.setPayuslStatus("готово");
                payUslFacade.edit(payUsl);
                for (PayuslClient cl : allClients) {
                    for (PayuslLesson lesson : allLessons) {
                        Payusllespos pulp = new Payusllespos();
                        pulp.setDateUpd(curDate);
                        pulp.setUserId(user);
                        pulp.setPayuslclientId(cl);
                        pulp.setPayusllessonId(lesson);
                        payusllesposFacade.create(pulp);
                    }
                }
            }
        }
        userPath = "/pup/goodsave";
        url = "/WEB-INF/pages" + userPath + ".jsp";

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
