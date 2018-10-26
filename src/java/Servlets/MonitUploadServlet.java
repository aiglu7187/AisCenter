/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Sessions.ObrObrFacade;
import Entities.ChildEduplace;
import Entities.Children;
import Entities.LoginLog;
import Entities.MonitEquip;
import Entities.MonitRekomend;
import Entities.Monitoring;
import Entities.MonitoringData;
import Entities.ObrObr;
import Entities.Pmpk;
import Entities.PmpkRek;
import Entities.Priyom;
import Entities.PriyomClient;
import Entities.SprEduform;
import Entities.SprEquip;
import Entities.SprObr;
import Entities.SprObrVar;
import Entities.SprOo;
import Entities.SprRegion;
import Entities.SprRekomend;
import Entities.SprStage;
import Entities.SprUsl;
import Entities.Users;
import Other.OvzMonit;
import Other.Protokol;
import Other.Xls;
import Sessions.ChildEduplaceFacade;
import Sessions.ChildrenFacade;
import Sessions.LoginLogFacade;
import Sessions.MonitEquipFacade;
import Sessions.MonitRekomendFacade;
import Sessions.MonitoringDataFacade;
import Sessions.MonitoringFacade;
import Sessions.PmpkFacade;
import Sessions.PriyomClientFacade;
import Sessions.PriyomFacade;
import Sessions.SprEduformFacade;
import Sessions.SprEquipFacade;
import Sessions.SprObrFacade;
import Sessions.SprObrVarFacade;
import Sessions.SprOoFacade;
import Sessions.SprRegionFacade;
import Sessions.SprRekomendFacade;
import Sessions.SprStageFacade;
import Sessions.SprUslFacade;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

/**
 *
 * @author Aiglu
 */
@WebServlet(name = "MonitUploadServlet",
        loadOnStartup = 1,
        urlPatterns = "/monitupload")

public class MonitUploadServlet extends HttpServlet {

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
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    PriyomFacade priyomFacade = new PriyomFacade();
    @EJB
    SprUslFacade sprUslFacade = new SprUslFacade();
    @EJB
    SprOoFacade sprOoFacade = new SprOoFacade();
    @EJB
    SprEduformFacade eduformFacade = new SprEduformFacade();
    @EJB
    SprRekomendFacade sprRekomendFacade = new SprRekomendFacade();
    @EJB
    ObrObrFacade obrObrFacade = new ObrObrFacade();
    @EJB
    SprStageFacade sprStageFacade = new SprStageFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    MonitoringFacade monitoringFacade = new MonitoringFacade();
    @EJB
    MonitoringDataFacade monitoringDataFacade = new MonitoringDataFacade();
    @EJB
    MonitRekomendFacade monitRekomendFacade = new MonitRekomendFacade();
    @EJB
    PriyomClientFacade priyomClientFacade = new PriyomClientFacade();
    @EJB
    PmpkFacade pmpkFacade = new PmpkFacade();
    @EJB
    ChildEduplaceFacade childEduplaceFacade = new ChildEduplaceFacade();
    @EJB
    SprObrVarFacade sprObrVarFacade = new SprObrVarFacade();
    @EJB
    SprEquipFacade sprEquipFacade = new SprEquipFacade();
    @EJB
    MonitEquipFacade monitEquipFacade = new MonitEquipFacade();
    @EJB
    SprObrFacade sprObrFacade = new SprObrFacade();

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

        String fileName = request.getParameter("filename");
        String regIdS = fileName.substring(0, 2);
        Integer regId = 0;
        try {
            regId = Integer.parseInt(regIdS);
        } catch (Exception ex) {
        }
        SprRegion reg = null;
        if (regId > 0) {
            try {
                reg = sprRegionFacade.findById(regId);
            } catch (Exception ex) {
            }
        }
        String path = "mon//" + fileName;
        File file = new File(path);
        String id = request.getParameter("id");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        List<Protokol> protokolList = new ArrayList<>();

        if (id.equals("test")) {
            try {
                Workbook xlsfile = Workbook.getWorkbook(file);
                Sheet sheet = xlsfile.getSheet(0);
                int begin = 0;
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell[] row = sheet.getRow(i);
                    if (row.length > 0) {
                        if (row[0].getContents().equals("1")) {
                            begin = i;
                            break;
                        }
                    }
                }
                if (begin > 0) {
                    for (int i = begin; i < sheet.getRows(); i++) {
                        Cell[] row = sheet.getRow(i);
                        if (row.length > 0) {
                            Protokol protokol = new Protokol();
                            protokol.setN(i + 1);
                            // проверяем номер ребёнка в БД
                            String nomS = row[1].getContents().trim();
                            Integer nom = 0;
                            try {
                                nom = Integer.parseInt(nomS);
                            } catch (Exception ex) {
                            }
                            Children child = null;
                            if (nom != 0) {
                                try {
                                    child = childrenFacade.findByNom(nom);
                                } catch (Exception ex) {
                                }
                            } else {
                                protokol.setChild("Неверный формат номера");
                            }
                            if (child == null) {
                                // если не удалось найти по номеру - ищем ребёнка по ФИО и ДР
                                protokol.setChild("В БД нет такого номера");
                                String fio = "";
                                try {
                                    fio = row[2].getContents().trim();
                                } catch (Exception ex) {
                                };
                                if (fio.equals("")) {
                                    protokol.setChild(protokol.getChild() + ", нет ФИО");
                                } else {
                                    String[] f = fio.split(" ");
                                    String fam = "";
                                    String name = "";
                                    String patr = "";
                                    try {
                                        fam = f[0];
                                        name = f[1];
                                        patr = f[2];
                                    } catch (Exception ex) {
                                    }
                                    String drS = "";
                                    try {
                                        drS = row[3].getContents().trim();
                                    } catch (Exception ex) {
                                    }
                                    Date dr = null;
                                    try {
                                        dr = simpleDateFormat.parse(drS);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(MonitUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    List<Children> findChild = new ArrayList<>();
                                    if (dr != null) {
                                        findChild = childrenFacade.findByFioDr(fam, name, patr, dr);
                                    } else {
                                        protokol.setChild(protokol.getChild() + ", ошибка в дате рождения");
                                    }
                                    if (findChild.isEmpty()) {
                                        protokol.setChild(protokol.getChild() + ", ребёнок по ФИО и ДР не найден");
                                    }
                                }
                            }

                            // проверяем ПМПК по дате
                            String datePmpkS = "";
                            try {
                                datePmpkS = row[4].getContents().trim();
                            } catch (Exception ex) {
                            }
                            if (datePmpkS.equals("")) {
                                protokol.setPmpk("Нет даты ПМПК");
                            } else {
                                Date datePmpk = null;
                                try {
                                    datePmpk = simpleDateFormat.parse(datePmpkS);
                                } catch (ParseException ex) {
                                    Logger.getLogger(MonitUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (datePmpk == null) {
                                    protokol.setPmpk("Неверный формат даты ПМПК");
                                } else {
                                    SprUsl sprUsl = sprUslFacade.findById(1);
                                    List<Priyom> priyom = priyomFacade.isPriyom(datePmpk, sprUsl);
                                    if (priyom.isEmpty()) {
                                        protokol.setPmpk("Нет ПМПК на дату " + datePmpkS);
                                    }
                                }
                            }

                            // проверяем правильность заполнения столбика предъявлено/не предъявлено заключение
                            String zakl = "";
                            try {
                                zakl = row[6].getContents().trim();
                            } catch (Exception ex) {
                            }
                            if (zakl.equals("")) {
                                protokol.setZakl("Не заполнен столбец про заключение");
                            } else {
                                zakl = zakl.toLowerCase();
                                if (!((zakl.equals("да")) || (zakl.equals("нет")) || (zakl.equals("предъявлено")) || (zakl.equals("не предъявлено")))) {
                                    protokol.setZakl("Неверно заполнен столбец про заключение");
                                }
                            }

                            // проверяем ОУ                        
                            List<SprOo> allOo = sprOoFacade.findAllOo();
                            List<String> allOoStr = new ArrayList<>();
                            for (SprOo oo : allOo) {
                                String str = oo.getSprooName();
                                str = str.replaceAll("«", " ");
                                str = str.replaceAll("»", " ");
                                str = str.replaceAll("-", " ");
                                str = str.replaceAll("№", " ");
                                str = str.replaceAll(" ", "");
                                str = str.toLowerCase();
                                allOoStr.add(str);
                            }
                            String org = "";
                            try {
                                org = row[7].getContents().trim();
                            } catch (Exception ex) {
                            }
                            if (org.equals("")) {
                                protokol.setOo("Не указана образовательная организация");
                            } else {
                                org = org.replaceAll("\"", " ");
                                org = org.replaceAll("-", " ");
                                org = org.replaceAll("«", " ");
                                org = org.replaceAll("»", " ");
                                org = org.replaceAll("№", " ");
                                org = org.replaceAll(" ", "");
                                org = org.toLowerCase();
                                boolean flag = Boolean.FALSE;
                                for (String str : allOoStr) {
                                    if (!flag) {
                                        if (org.contains(str)) {
                                            flag = Boolean.TRUE;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (!flag) {
                                    protokol.setOo("Образовательной организации нет в справочнике");
                                }
                            }

                            // проверить класс/группу                        
                            List<SprStage> stages = sprStageFacade.findAll();
                            if (!org.equals("")) {
                                org = org.replaceAll("\"", " ");
                                org = org.replaceAll("-", " ");
                                org = org.replaceAll("«", " ");
                                org = org.replaceAll("»", " ");
                                org = org.replaceAll("№", " ");
                                org = org.replaceAll(" ", "");
                                org = org.toLowerCase();
                                boolean flag = Boolean.FALSE;
                                for (SprStage stage : stages) {
                                    if (!flag) {
                                        String stName = stage.getSprstageName();
                                        stName = stName.replaceAll(" ", "");
                                        if (stName.length() > 3) {
                                            stName = stName.substring(0, stName.length() - 3);
                                        }
                                        if (org.contains(stName)) {
                                            flag = Boolean.TRUE;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (!flag) {
                                    protokol.setStage("Ступени обучения нет в справочнике");
                                }
                            }

                            // проверить форму получения образования
                            List<SprEduform> eduforms = eduformFacade.findAll();
                            String form = "";
                            try {
                                form = row[8].getContents().trim();
                            } catch (Exception ex) {
                            }
                            if (form.equals("")) {
                                protokol.setForm("Нет сведений о форме обучения");
                            } else {
                                boolean flag = Boolean.FALSE;
                                form = form.toLowerCase();
                                for (SprEduform eduform : eduforms) {
                                    if (!flag) {
                                        String eduf = eduform.getSpreduformName();
                                        eduf = eduf.toLowerCase();
                                        if (form.equals(eduf)) {
                                            flag = Boolean.TRUE;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                                if (!flag) {
                                    protokol.setForm("Неверно заполнен столбец с формой обучения");
                                }
                            }

                            // проверить программу обучения
                            List<ObrObr> obrObr = obrObrFacade.findAll();
                            Set<SprObr> obrs = new HashSet<>();
                            for (ObrObr obr : obrObr) {
                                obrs.add(obr.getSprobrIdMain());
                            }
                            String op = "";
                            try {
                                op = row[14].getContents().trim();
                            } catch (Exception ex) {
                            }
                            SprObr obrProg = null;
                            String var = "";
                            if (!op.equals("")) {
                                op = op.replaceAll(" ", "");
                                op = op.toLowerCase();
                                int i1 = op.indexOf("(");
                                int i2 = op.indexOf(")");
                                for (SprObr obr : obrs) {
                                    String o = obr.getSprobrShname();
                                    o = o.replaceAll(" ", "");
                                    o = o.toLowerCase();
                                    if ((i1 >= 0) && (i2 > 0)) {
                                        var = op.substring(i1 + 1, i2);
                                    }
                                    if (op.contains(o)) {
                                        obrProg = obr;
                                    }

                                }
                            }
                            if (obrProg != null) {
                                SprObrVar obrVar = null;
                                protokol.setPo(obrProg.getSprobrShname());
                                if (!var.equals("")) {
                                    List<SprObrVar> varList = sprObrVarFacade.findByName(var);
                                    if (varList.size() > 0) {
                                        obrVar = varList.get(0);
                                    }
                                }
                                if (obrVar != null) {
                                    protokol.setPo(protokol.getPo() + " " + obrVar.getSprobrvarName());
                                }
                            }

                            // проверить занятия со специалистами
                            List<SprRekomend> allRekomend = sprRekomendFacade.findAllRekomend();
                            String zanyat = "";
                            try {
                                zanyat = row[10].getContents().trim();
                            } catch (Exception ex) {
                            }
                            if (!zanyat.equals("")) {
                                zanyat = zanyat.toLowerCase();
                                for (SprRekomend rek : allRekomend) {
                                    String rekName = "";
                                    try {
                                        rekName = rek.getSprrekShortname();
                                    } catch (Exception ex) {
                                    }
                                    if (rekName != null) {
                                        if (!rekName.equals("")) {
                                            if (zanyat.contains(rekName)) {
                                                protokol.setZan(protokol.getZan() + rekName + ", ");
                                            }
                                        }
                                    }
                                }
                                if (protokol.getZan().equals("")) {
                                    protokol.setZan("Неверно заполнена информация про занятия");
                                }
                            }

                            // проверить тьюьтора
                            String assist = "";
                            try {
                                assist = row[11].getContents().trim();
                            } catch (Exception ex) {
                            }
                            if (!assist.equals("")) {
                                assist = assist.toLowerCase();
                                for (SprRekomend rek : allRekomend) {
                                    String rekName = "";
                                    try {
                                        rekName = rek.getSprrekName();
                                    } catch (Exception ex) {
                                    }
                                    if (rekName != null) {
                                        if (!rekName.equals("")) {
                                            if (assist.contains(rekName)) {
                                                protokol.setAssist(protokol.getAssist() + rekName);
                                            }
                                        }
                                    }
                                }
                                if (protokol.getAssist().equals("")) {
                                    if (!assist.equals("нет")) {
                                        protokol.setAssist("Неверно заполнена информация про ассистента");
                                    }
                                }
                            }

// проверить оборудование
                            protokolList.add(protokol);
                        }
                    }
                }
            } catch (BiffException ex) {
                Logger.getLogger(MonitUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                session.removeAttribute("protokol");
            } catch (Exception ex) {
            }

            try {
                session.removeAttribute("filename");
            } catch (Exception ex) {
            }
            session.setAttribute("protokol", protokolList);
            session.setAttribute("filename", fileName);

            String url = "";
            String userPath = request.getServletPath();
            userPath = "/pmpk/monittestfile";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("load")) {
            try {
                Workbook xlsfile = Workbook.getWorkbook(file);
                Sheet sheet = xlsfile.getSheet(0);
                int begin = 0;
                for (int i = 0; i < sheet.getRows(); i++) {
                    Cell[] row = sheet.getRow(i);
                    if (row.length > 0) {
                        if (row[0].getContents().equals("1")) {
                            begin = i;
                            break;
                        }
                    }
                }
                if (begin > 0) {
                    Monitoring monitoring = new Monitoring();
                    if (reg != null) {
                        monitoring.setSprregId(reg);
                    }
                    String dateS = request.getParameter("date");
                    Date dateM = null;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        dateM = format.parse(dateS);
                    } catch (Exception ex) {
                    }
                    if (dateM != null) {
                        monitoring.setMonitoringDate(dateM);
                    }
                    try {
                        monitoringFacade.create(monitoring);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    for (int i = begin; i < sheet.getRows(); i++) {
                        Cell[] row = sheet.getRow(i);
                        if (row.length > 0) {
                            // проверяем номер ребёнка в БД
                            String nomS = row[1].getContents().trim();
                            Integer nom = 0;
                            try {
                                nom = Integer.parseInt(nomS);
                            } catch (Exception ex) {
                            }
                            Children child = null;
                            if (nom != 0) {
                                try {
                                    child = childrenFacade.findByNom(nom);
                                } catch (Exception ex) {
                                }
                            }
                            if (child == null) {
                                // если не удалось найти по номеру - ищем ребёнка по ФИО и ДР
                                String fio = "";
                                try {
                                    fio = row[2].getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!fio.equals("")) {
                                    String[] f = fio.split(" ");
                                    String fam = "";
                                    String name = "";
                                    String patr = "";
                                    try {
                                        fam = f[0];
                                        name = f[1];
                                        patr = f[2];
                                    } catch (Exception ex) {
                                    }
                                    String drS = "";
                                    try {
                                        drS = row[3].getContents().trim();
                                    } catch (Exception ex) {
                                    }
                                    Date dr = null;
                                    try {
                                        dr = simpleDateFormat.parse(drS);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(MonitUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    List<Children> findChild = new ArrayList<>();
                                    if (dr != null) {
                                        findChild = childrenFacade.findByFioDr(fam, name, patr, dr);
                                        if (findChild.size() > 0) {
                                            child = findChild.get(0);
                                        }
                                    }
                                }
                            }
                            MonitoringData monData = null;
                            if (child != null) {
                                monData = new MonitoringData();
                                monData.setMonitoringId(monitoring);
                                monData.setChildId(child);

                                // проверяем ПМПК по дате
                                String datePmpkS = "";
                                PriyomClient pr = null;
                                Pmpk pmpk = null;
                                try {
                                    datePmpkS = row[4].getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!datePmpkS.equals("")) {
                                    Date datePmpk = null;
                                    try {
                                        datePmpk = simpleDateFormat.parse(datePmpkS);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(MonitUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if (datePmpk != null) {
                                        SprUsl sprUsl = sprUslFacade.findById(1);
                                        List<Priyom> priyom = priyomFacade.isPriyom(datePmpk, sprUsl);
                                        List<PriyomClient> childPriyom = priyomClientFacade.findByClient(child.getChildId(), "children");
                                        for (Priyom p : priyom) {
                                            for (PriyomClient pc : childPriyom) {
                                                if (pc.getPriyomId().equals(p)) {
                                                    pr = pc;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (pr != null) {
                                    List<Pmpk> pmpkList = new ArrayList<>();
                                    try {
                                        pmpkList = pmpkFacade.findByPrcl(pr);
                                    } catch (Exception ex) {
                                    }
                                    if (pmpkList.size() > 0) {
                                        pmpk = pmpkList.get(0);
                                    }
                                    if (pmpk != null) {
                                        monData.setPmpkId(pmpk);
                                    }
                                }

                                // проверяем правильность заполнения столбика предъявлено/не предъявлено заключение
                                String zakl = "";
                                try {
                                    zakl = row[6].getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!zakl.equals("")) {
                                    zakl = zakl.toLowerCase();
                                    if ((zakl.equals("да")) || (zakl.equals("предъявлено"))) {
                                        monData.setMonitdataZakl(2);
                                    } else if ((zakl.equals("нет")) || (zakl.equals("не предъявлено"))) {
                                        monData.setMonitdataZakl(1);
                                    } else {
                                        monData.setMonitdataZakl(0);
                                    }
                                } else {
                                    monData.setMonitdataZakl(0);
                                }

                                // проверяем ОУ                        
                                List<SprOo> allOo = sprOoFacade.findAllOo();
                                SprOo oo = null;
                                String org = "";
                                try {
                                    org = row[7].getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!org.equals("")) {
                                    org = org.replaceAll("\"", " ");
                                    org = org.replaceAll("-", " ");
                                    org = org.replaceAll("«", " ");
                                    org = org.replaceAll("»", " ");
                                    org = org.replaceAll("№", " ");
                                    org = org.replaceAll(" ", "");
                                    org = org.toLowerCase();
                                    for (SprOo ao : allOo) {
                                        String str = ao.getSprooName();
                                        str = str.replaceAll("«", " ");
                                        str = str.replaceAll("»", " ");
                                        str = str.replaceAll("-", " ");
                                        str = str.replaceAll("№", " ");
                                        str = str.replaceAll(" ", "");
                                        str = str.toLowerCase();
                                        if (org.contains(str)) {
                                            oo = ao;
                                            break;
                                        }
                                    }
                                }
                                ChildEduplace childEduplace = null;
                                if (oo != null) {
                                    childEduplace = new ChildEduplace();
                                    childEduplace.setChildId(child);
                                    childEduplace.setChildeduplaceDaten(dateM);
                                    childEduplace.setSprooId(oo);
                                    childEduplaceFacade.create(childEduplace);
                                }

                                if (childEduplace != null) {
                                    // проверить класс/группу                        
                                    SprStage st = null;
                                    List<SprStage> stages = sprStageFacade.findAll();
                                    if (!org.equals("")) {
                                        org = org.replaceAll("\"", " ");
                                        org = org.replaceAll("-", " ");
                                        org = org.replaceAll("«", " ");
                                        org = org.replaceAll("»", " ");
                                        org = org.replaceAll("№", " ");
                                        org = org.replaceAll(" ", "");
                                        org = org.toLowerCase();
                                        for (SprStage stage : stages) {
                                            String stName = stage.getSprstageName();
                                            stName = stName.replaceAll(" ", "");
                                            if (stName.length() > 3) {
                                                stName = stName.substring(0, stName.length() - 3);
                                            }
                                            if (org.contains(stName)) {
                                                st = stage;
                                                break;
                                            }
                                        }
                                    }
                                    if (st != null) {
                                        childEduplace.setSprstageId(st);
                                        childEduplaceFacade.edit(childEduplace);
                                    }
                                }

                                if (childEduplace != null) {
                                    monData.setChildeduplaceId(childEduplace);
                                }

                                // проверить форму получения образования
                                List<SprEduform> eduforms = eduformFacade.findAll();
                                String form = "";
                                try {
                                    form = row[8].getContents().trim();
                                } catch (Exception ex) {
                                }
                                SprEduform eduF = null;
                                if (!form.equals("")) {
                                    form = form.toLowerCase();
                                    for (SprEduform eduform : eduforms) {
                                        String eduf = eduform.getSpreduformName();
                                        eduf = eduf.toLowerCase();
                                        if (form.equals(eduf)) {
                                            eduF = eduform;
                                            break;
                                        }
                                    }
                                }
                                if (eduF != null) {
                                    monData.setSpreduformId(eduF);
                                }

                                // проверить программу обучения
                                List<ObrObr> obrObr = obrObrFacade.findAll();
                                Set<SprObr> obrs = new HashSet<>();
                                for (ObrObr obr : obrObr) {
                                    obrs.add(obr.getSprobrIdMain());
                                }
                                String op = "";
                                try {
                                    op = row[14].getContents().trim();
                                } catch (Exception ex) {
                                }
                                SprObr obrProg = null;
                                String var = "";
                                if (!op.equals("")) {
                                    // op = op.replaceAll(" ", "");
                                    int i1 = op.indexOf("(");
                                    int i2 = op.indexOf(")");
                                    if ((i1 >= 0) && (i2 > 0)) {
                                        var = op.substring(i1 + 1, i2);
                                        op = op.substring(0, i1 - 1);
                                    }
                                    for (SprObr obr : obrs) {
                                        String o = obr.getSprobrShname();
                                        if (op.equals(o)) {
                                            obrProg = obr;
                                        }

                                    }
                                }
                                if (obrProg != null) {
                                    SprObrVar obrVar = null;
                                    monData.setSprobrId(obrProg);
                                    if (!var.equals("")) {
                                        List<SprObrVar> varList = sprObrVarFacade.findByName(var);
                                        if (varList.size() > 0) {
                                            obrVar = varList.get(0);
                                        }
                                    }
                                    if (obrVar != null) {
                                        monData.setSprobrvarId(obrVar);
                                    }
                                }

                                String prich = "";
                                try {
                                    prich = row[13].getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!prich.equals("")) {
                                    monData.setMonitdataPrich(prich);
                                }
                                monitoringDataFacade.create(monData);

                                // проверить занятия со специалистами                                
                                List<SprRekomend> allRekomend = sprRekomendFacade.findAllRekomend();
                                String zanyat = "";
                                try {
                                    zanyat = row[10].getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!zanyat.equals("")) {
                                    zanyat = zanyat.toLowerCase();
                                    for (SprRekomend rek : allRekomend) {
                                        String rekName = "";
                                        try {
                                            rekName = rek.getSprrekShortname();
                                        } catch (Exception ex) {
                                        }
                                        if (rekName != null) {
                                            if (!rekName.equals("")) {
                                                if (zanyat.contains(rekName)) {
                                                    MonitRekomend monitRekomend = new MonitRekomend();
                                                    monitRekomend.setSprrekId(rek);
                                                    monitRekomend.setMonitdataId(monData);
                                                    monitRekomendFacade.create(monitRekomend);
                                                }
                                            }
                                        }
                                    }
                                }

                                // проверить тьюьтора
                                String assist = "";
                                try {
                                    assist = row[11].getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!assist.equals("")) {
                                    assist = assist.toLowerCase();
                                    for (SprRekomend rek : allRekomend) {
                                        String rekName = "";
                                        try {
                                            rekName = rek.getSprrekName();
                                        } catch (Exception ex) {
                                        }
                                        if (rekName != null) {
                                            if (!rekName.equals("")) {
                                                if (assist.contains(rekName)) {
                                                    MonitRekomend monitRekomend = new MonitRekomend();
                                                    monitRekomend.setSprrekId(rek);
                                                    monitRekomend.setMonitdataId(monData);
                                                    monitRekomendFacade.create(monitRekomend);
                                                }
                                            }
                                        }
                                    }
                                }
                                // проверить оборудование
                                List<SprEquip> allEquip = sprEquipFacade.findAll();
                                String equip = "";
                                try {
                                    equip = row[12].getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!equip.equals("")) {
                                    equip = equip.replaceAll(" ", "");
                                    equip = equip.toLowerCase();
                                    for (SprEquip e : allEquip) {
                                        String eName = "";
                                        try {
                                            eName = e.getSprequipName();
                                        } catch (Exception ex) {
                                        }
                                        if (eName != null) {
                                            if (!eName.equals("")) {
                                                eName = eName.replaceAll(" ", "");
                                                if (equip.contains(eName)) {
                                                    MonitEquip monitEquip = new MonitEquip();
                                                    monitEquip.setSprequipId(e);
                                                    monitEquip.setMonitdataId(monData);
                                                    monitEquipFacade.create(monitEquip);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    List<MonitoringData> monitoringDataList = monitoringDataFacade.findByMonitoring(monitoring);
                    for (MonitoringData mD : monitoringDataList) {
                        if (mD.getMonitdataZakl() == 0) {
                            mD.setMonitoringResult("нет информации");
                        } else if (mD.getMonitdataZakl() == 1) {
                            mD.setMonitoringResult("рекомендации не выполнены");
                        } else if (mD.getMonitdataZakl() == 2) {
                            boolean flagObr = Boolean.FALSE;
                            boolean flagObrvar = Boolean.FALSE;
                            boolean flagRek = Boolean.TRUE;
                            Pmpk pmpk = mD.getPmpkId();
                            List<ObrObr> pmpkObrList = new ArrayList<>();
                            try {
                                pmpkObrList = obrObrFacade.findBySoo(pmpk.getSprobrId());
                            } catch (Exception ex) {
                            }
                            SprObr obr = mD.getSprobrId();
                            if (obr != null) {
                                for (ObrObr obrObr : pmpkObrList) {
                                    if (obr.equals(obrObr.getSprobrIdMain())) {
                                        flagObr = Boolean.TRUE;
                                        break;
                                    }
                                }
                            }
                            /*
                            SprObrVar sprobrvar = pmpk.getSprobrvarId();
                            if (sprobrvar != null) {
                                SprObrVar obrVar = mD.getSprobrvarId();
                                if (obrVar != null) {
                                    if (obrVar.equals(sprobrvar)) {
                                        flagObrvar = Boolean.TRUE;
                                    }
                                }
                            }
                            else {
                                flagObrvar = Boolean.TRUE;
                            }
                             */
                            List<MonitRekomend> rekList = monitRekomendFacade.findByMonitData(mD);
                            Collection<PmpkRek> pmpkRekCollection = pmpk.getPmpkRekCollection();
                            for (PmpkRek pmpkRek : pmpkRekCollection) {
                                for (MonitRekomend rek : rekList) {
                                    if (!rek.getSprrekId().equals(pmpkRek.getSprrekId())) {
                                        flagRek = Boolean.FALSE;
                                    }
                                }
                            }
                            if (rekList.isEmpty()) {
                                flagRek = Boolean.FALSE;
                            }

                            if (flagObr && flagRek) {
                                mD.setMonitoringResult("рекомендации выполнены в полном объеме");
                            } else if (flagObr && (!flagRek)) {
                                mD.setMonitoringResult("рекомендации выполнены частично: только программа");
                            } else if ((!flagObr) && flagRek) {
                                mD.setMonitoringResult("рекомендации выполнены частично: только занятия");
                            } else if ((!flagObr) && (!flagRek)) {
                                mD.setMonitoringResult("рекомендации не выполнены");
                            }

                        }
                        monitoringDataFacade.edit(mD);

                    }
                }
            } catch (BiffException ex) {
                Logger.getLogger(MonitUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            String url = "";
            String userPath = request.getServletPath();
            userPath = "/pup/goodsave";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (id.equals("ovzcompare")) {
            // добавить даты!!!
            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yy");
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
            Workbook xlsfile;
            List<OvzMonit> monitList = new ArrayList<>();
            try {
                xlsfile = Workbook.getWorkbook(file);
                Sheet sheet = xlsfile.getSheet(0);
                for (int i = 1; i < sheet.getRows(); i++) {
                    Cell[] row = sheet.getRow(i);
                    if (row.length > 0) {
                        OvzMonit om = new OvzMonit();
                        String info = "";
                        String fio = "";
                        try {
                            fio = row[0].getContents().trim();
                        } catch (Exception ex) {
                        }
                        om.setFio(fio);
                        String fam = "";
                        String nam = "";
                        String patr = "";
                        if (!fio.equals("")) {
                            String[] fioSplit = fio.split(" ");
                            for (String st : fioSplit) {
                                if (!st.equals("")) {
                                    if (fam.equals("")) {
                                        fam = st;
                                    } else if (nam.equals("")) {
                                        nam = st;
                                    } else if (patr.equals("")) {
                                        patr = st;
                                    } else if (!(fam.equals("") || nam.equals("") || patr.equals(""))) {
                                        patr += st;
                                    }
                                }

                            }
                        }
                        String drS = "";
                        try {
                            drS = row[1].getContents().trim();
                        } catch (Exception ex) {
                        }
                        Date dr = null;
                        try {
                            dr = format2.parse(drS);
                        } catch (ParseException ex) {
                            Logger.getLogger(MonitUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            dr = format1.parse(drS);
                        } catch (ParseException ex) {
                            Logger.getLogger(MonitUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        List<Children> children = null;
                        Children child = null;
                        if (dr == null) {
                            info = "нет данных в базе";
                        } else {
                            om.setDr(simpleDateFormat.format(dr));
                            children = childrenFacade.findByFioDr(fam, nam, patr, dr);
                            if (children.size() > 0) {
                                child = children.get(0);
                            } else {
                                info = "нет данных в базе";
                            }
                        }
                        if (child != null) {
                            om.setChild(child);
                            List<PriyomClient> priyomClient = priyomClientFacade.findByClient(child.getChildId(), "children");
                            List<Pmpk> pmpkList = new ArrayList<>();
                            for (PriyomClient pc : priyomClient) {
                                if (pc.getPriyomId().getSpruslId().getSpruslPmpk() == 1) {
                                    List<Pmpk> findByPrcl = pmpkFacade.findByPrcl(pc);
                                    for (Pmpk p : findByPrcl) {
                                        pmpkList.add(p);
                                    }
                                }
                            }
                            Date maxDate = null;
                            Integer maxPmpkId = 0;
                            for (Pmpk pmpk : pmpkList) {
                                if (maxDate == null) {
                                    maxDate = pmpk.getPrclId().getPriyomId().getPriyomDate();
                                    maxPmpkId = pmpk.getPmpkId();
                                } else if (maxDate.getTime() - pmpk.getPrclId().getPriyomId().getPriyomDate().getTime() < 0) {
                                    maxDate = pmpk.getPrclId().getPriyomId().getPriyomDate();
                                    maxPmpkId = pmpk.getPmpkId();
                                }
                            }
                            if (maxPmpkId > 0) {
                                Pmpk pmpk = pmpkFacade.findById(maxPmpkId);
                                try {
                                    om.setSprObrVar(pmpk.getSprobrvarId());
                                } catch (Exception ex) {
                                }
                                if (om.getSprObrVar() == null) {
                                    try {
                                        om.setSprObr(pmpk.getSprobrId());
                                    } catch (Exception ex) {
                                    }
                                }
                            }
                            if ((om.getSprObrVar() == null) && (om.getSprObr() == null)) {
                                info = "нет образовательной программы";
                            }
                        }
                        om.setInfo(info);
                        try {
                            om.setOp(row[2].getContents().trim());
                        } catch (Exception ex) {
                        }
                        try {
                            om.setReg(row[3].getContents().trim());
                        } catch (Exception ex) {
                        }
                        monitList.add(om);
                    }
                }
            } catch (BiffException ex) {
                Logger.getLogger(MonitUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            List<OvzMonit> baseList = new ArrayList<>();
            List<Pmpk> allPmpk = pmpkFacade.findPmpkWithVar();
            HashSet<Children> allChildren = new HashSet<>();
            for (Pmpk pmpk : allPmpk) {
                allChildren.add(childrenFacade.findById(pmpk.getPrclId().getClientId()));
            }
            for (Children c : allChildren) {
                OvzMonit om = new OvzMonit();
                List<Pmpk> childPmpk = new ArrayList<>();
                for (Pmpk pmpk : allPmpk) {
                    if (pmpk.getPrclId().getClientId().equals(c.getChildId())) {
                        childPmpk.add(pmpk);
                    }
                }
                Date maxDate = null;
                Integer maxPmpkId = 0;
                for (Pmpk pmpk : childPmpk) {
                    if (maxDate == null) {
                        maxDate = pmpk.getPrclId().getPriyomId().getPriyomDate();
                        maxPmpkId = pmpk.getPmpkId();
                    } else if (maxDate.getTime() - pmpk.getPrclId().getPriyomId().getPriyomDate().getTime() < 0) {
                        maxDate = pmpk.getPrclId().getPriyomId().getPriyomDate();
                        maxPmpkId = pmpk.getPmpkId();
                    }
                }
                Pmpk pmpk = pmpkFacade.findById(maxPmpkId);
                om.setChild(c);
                om.setSprRegion(c.getSprregId());
                om.setSprObrVar(pmpk.getSprobrvarId());
                om.setSprObr(pmpk.getSprobrId());
                om.setDatePmpk(simpleDateFormat.format(pmpk.getPrclId().getPriyomId().getPriyomDate()));
                baseList.add(om);
            }

            List<OvzMonit> resList = new ArrayList<>();
            for (OvzMonit b : baseList) {
                Boolean k = false;
                for (OvzMonit m : monitList) {
                    if (m.getChildren() != null) {
                        if (m.getChildren().equals(b.getChildren())) {
                            b.setFio(m.getFio());
                            b.setDr(m.getDr());
                            b.setOp(m.getOp());
                            b.setInfo(m.getInfo());
                            b.setReg(m.getReg());
                            resList.add(b);
                            k = true;
                        }
                    }
                }
                if (!k) {
                    b.setFio(b.getChildren().getChildFam() + " " + b.getChildren().getChildName() + " " + b.getChildren().getChildPatr());
                    b.setDr(b.getChildren().getFormat2Dr());

                    resList.add(b);
                }
            }

            for (OvzMonit m : monitList) {
                Boolean k = false;
                for (OvzMonit b : baseList) {
                    if (m.getChildren() != null) {
                        if (m.getChildren().equals(b.getChildren())) {
                            k = true;
                        }
                    }
                }
                if (!k) {
                    resList.add(m);
                }
            }

            String fileName2 = "MonitOvz.xls";
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "Attachment;filename= " + fileName2);
            try {
                Xls.printOvzMonitResult(response.getOutputStream(), resList);
            } catch (WriteException ex) {
                Logger.getLogger(MonitUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (id.equals("ovzupload")) {
            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yy");
            Workbook xlsfile;
            StringBuilder sb = new StringBuilder();
            try {
                xlsfile = Workbook.getWorkbook(file);
                Sheet sheet = xlsfile.getSheet(0);
                for (int i = 1; i < sheet.getRows(); i++) {
                    Cell[] row = sheet.getRow(i);
                    if (row.length > 0) {
                        String regS = "";
                        try {
                            regS = row[0].getContents().trim();
                        } catch (Exception ex) {
                        }
                        SprRegion region = null;
                        if (!regS.equals("")) {
                            List<SprRegion> regions = sprRegionFacade.findByName(regS);
                            if (!regions.isEmpty()) {
                                region = regions.get(0);
                            }
                        }
                        String ooS = "";
                        SprOo oo = null;
                        if (region != null) {
                            try {
                                ooS = row[1].getContents().trim();
                            } catch (Exception ex) {
                            }
                            if (!ooS.equals("")) {
                                try {
                                    oo = sprOoFacade.findByName(ooS, region);
                                } catch (Exception ex) {
                                }
                            }
                        }
                        if (oo == null) {
                            sb.append(ooS).append("|");
                        }
                        String fam = "";
                        String name = "";
                        String patr = "";
                        String drS = "";
                        String dBeginEduS = "";
                        String clsS = "";
                        try {
                            fam = row[2].getContents().trim();
                        } catch (Exception ex) {
                        }
                        try {
                            name = row[3].getContents().trim();
                        } catch (Exception ex) {
                        }
                        try {
                            patr = row[4].getContents().trim();
                        } catch (Exception ex) {
                        }
                        try {
                            drS = row[5].getContents().trim();
                        } catch (Exception ex) {
                        }
                        Date dr = null;
                        if (!drS.equals("")) {
                            try {
                                dr = format1.parse(drS);
                            } catch (Exception ex) {
                            }
                        }
                        List<Children> children = new ArrayList<>();
                        if (dr != null) {
                            try {
                                children = childrenFacade.findByFioDr(fam, name, patr, dr);
                            } catch (Exception ex) {
                            }
                        } else {
                            try {
                                children = childrenFacade.search(fam, name, patr);
                            } catch (Exception ex) {
                            }
                        }
                        Children child = null;
                        if (children.size() == 1) {
                            child = children.get(0);
                        } else {
                            sb.append("|").append(fam).append(" ").append(name).append(" ").append(patr).append("-");
                            for (Children ch : children) {
                                sb.append(ch.getChildId()).append(",");
                            }
                        }
                        try {
                            dBeginEduS = row[6].getContents().trim();
                        } catch (Exception ex) {
                        }
                        Date dBeginEdu = null;
                        if (!dBeginEduS.equals("")) {
                            dBeginEdu = format1.parse(dBeginEduS);
                        }
                        try {
                            clsS = row[7].getContents().trim();
                        } catch (Exception ex) {
                        }
                        SprStage cls = null;
                        if (!clsS.equals("")) {
                            try {
                                cls = sprStageFacade.findByName(clsS + " класс");
                            } catch (Exception ex) {
                            }
                        }
                        if (child != null) {
                            List<ChildEduplace> childEdupl = childEduplaceFacade.findByChild(child);
                            if (oo != null) {
                                long curTime = System.currentTimeMillis();
                                Date curDate = new Date(curTime);
                                Boolean flag = false;
                                for (ChildEduplace ce : childEdupl) {
                                    if (ce.getSprooId().equals(oo)) {
                                        if (cls != null) {
                                            ce.setSprstageId(cls);
                                        }
                                        if (dBeginEdu != null) {
                                            ce.setChildeduplaceBeginedu(dBeginEdu);
                                        }
                                        flag = true;
                                    } else {
                                        ce.setChildeduplaceDatek(curDate);
                                    }
                                    childEduplaceFacade.edit(ce);
                                }
                                if (!flag) {
                                    ChildEduplace childEdu = new ChildEduplace();
                                    childEdu.setChildId(child);
                                    childEdu.setChildeduplaceDaten(curDate);
                                    childEdu.setChildeduplaceBeginedu(dBeginEdu);
                                    childEdu.setSprooId(oo);
                                    childEdu.setSprstageId(cls);
                                    childEduplaceFacade.create(childEdu);
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
            }
            String url = "";
            String userPath = "/pup/goodsave";
            url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                session.removeAttribute("SB");
            } catch (Exception ex) {
            }
            session.setAttribute("SB", sb);
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
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
