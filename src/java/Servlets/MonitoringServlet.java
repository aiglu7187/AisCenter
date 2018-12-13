/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.ChildrenEducond;
import Entities.ChildrenEducondEq;
import Entities.ChildrenEducondRek;
import Entities.KeySprObrSpec;
import Entities.KeySprObrType;
import Entities.KeySprObrVid;
import Entities.KeySprOuType;
import Entities.LoginLog;
import Entities.Monitoring;
import Entities.MonitoringEducond;
import Entities.Pmpk;
import Entities.PmpkRek;
import Entities.Priyom;
import Entities.PriyomClient;
import Entities.SprEduform;
import Entities.SprEquip;
import Entities.SprObr;
import Entities.SprObrSpec;
import Entities.SprObrType;
import Entities.SprObrVar;
import Entities.SprObrVid;
import Entities.SprOu;
import Entities.SprOuType;
import Entities.SprRegion;
import Entities.SprRekomend;
import Entities.SprStage;
import Entities.SprUsl;
import Entities.SprVar;
import Entities.Users;
import Keys.KeyLevel;
import Keys.KeyElement;
import Keys.KeyOperation;
import Sessions.ChildrenEducondEqFacade;
import Sessions.ChildrenEducondFacade;
import Sessions.ChildrenEducondRekFacade;
import Sessions.ChildrenFacade;
import Sessions.KeySprObrSpecFacade;
import Sessions.KeySprObrTypeFacade;
import Sessions.KeySprObrVidFacade;
import Sessions.KeySprOuTypeFacade;
import Sessions.LoginLogFacade;
import Sessions.MonitoringEducondFacade;
import Sessions.MonitoringFacade;
import Sessions.PmpkFacade;
import Sessions.PmpkRekFacade;
import Sessions.PriyomClientFacade;
import Sessions.PriyomFacade;
import Sessions.SprEduformFacade;
import Sessions.SprEquipFacade;
import Sessions.SprObrFacade;
import Sessions.SprObrSpecFacade;
import Sessions.SprObrTypeFacade;
import Sessions.SprObrVidFacade;
import Sessions.SprOuFacade;
import Sessions.SprOuTypeFacade;
import Sessions.SprRegionFacade;
import Sessions.SprRekomendFacade;
import Sessions.SprStageFacade;
import Sessions.SprUslFacade;
import Sessions.SprVarFacade;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Aiglu
 */
@WebServlet(name = "MonitoringServlet",
        loadOnStartup = 1,
        urlPatterns = "/monitoring")

public class MonitoringServlet extends HttpServlet {

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
    MonitoringFacade monitoringFacade = new MonitoringFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    KeySprObrVidFacade keySprObrVidFacade = new KeySprObrVidFacade();
    @EJB
    KeySprObrSpecFacade keySprObrSpecFacade = new KeySprObrSpecFacade();
    @EJB
    KeySprObrTypeFacade keySprObrTypeFacade = new KeySprObrTypeFacade();
    @EJB
    SprObrFacade sprObrFacade = new SprObrFacade();
    @EJB
    SprStageFacade sprStageFacade = new SprStageFacade();
    @EJB
    KeySprOuTypeFacade keySprOuTypeFacade = new KeySprOuTypeFacade();
    @EJB
    SprOuFacade sprOuFacade = new SprOuFacade();
    @EJB
    SprOuTypeFacade sprOuTypeFacade = new SprOuTypeFacade();
    @EJB
    SprObrVidFacade sprObrVidFacade = new SprObrVidFacade();
    @EJB
    SprObrTypeFacade sprObrTypeFacade = new SprObrTypeFacade();
    @EJB
    SprObrSpecFacade sprObrSpecFacade = new SprObrSpecFacade();
    @EJB
    MonitoringEducondFacade monitoringEducondFacade = new MonitoringEducondFacade();
    @EJB
    SprEduformFacade sprEduformFacade = new SprEduformFacade();
    @EJB
    ChildrenEducondFacade childrenEducondFacade = new ChildrenEducondFacade();
    @EJB
    SprRekomendFacade sprRekomendFacade = new SprRekomendFacade();
    @EJB
    ChildrenEducondRekFacade childrenEducondRekFacade = new ChildrenEducondRekFacade();
    @EJB
    ChildrenEducondEqFacade childrenEducondEqFacade = new ChildrenEducondEqFacade();
    @EJB
    SprEquipFacade sprEquipFacade = new SprEquipFacade();
    @EJB
    SprVarFacade sprVarFacade = new SprVarFacade();
    @EJB
    SprUslFacade sprUslFacade = new SprUslFacade();
    @EJB
    PriyomFacade priyomFacade = new PriyomFacade();
    @EJB
    PriyomClientFacade priyomClientFacade = new PriyomClientFacade();
    @EJB
    PmpkFacade pmpkFacade = new PmpkFacade();
    @EJB
    PmpkRekFacade pmpkRekFacade = new PmpkRekFacade();

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        String id = request.getParameter("id");
        String url = "";
        String userPath = request.getServletPath();

        if (id.equals("view")) {
            try {
                session.removeAttribute("monitoring");
            } catch (Exception ex) {
            }
            List<Monitoring> monitoring = monitoringFacade.findAll();
            session.setAttribute("monitoring", monitoring);
            userPath = "/pmpk/monitoringview";
            url = "/WEB-INF/pages" + userPath + ".jsp";
        } else if (id.equals("upload")) {
            try {
                session.removeAttribute("monitFiles");
            } catch (Exception ex) {
            }
            File folder = new File("mon//");
            String[] files = folder.list(new FilenameFilter() {
                @Override
                public boolean accept(File folder, String name) {
                    return name.endsWith(".xls");
                }
            });
            session.setAttribute("monitFiles", files);

            userPath = "/pmpk/monitoringupload";
            url = "/WEB-INF/pages" + userPath + ".jsp";
        } else if (id.equals("toovzcompare")) {
            try {
                session.removeAttribute("monitFiles");
            } catch (Exception ex) {
            }
            File folder = new File("mon//");
            String[] files = folder.list(new FilenameFilter() {
                @Override
                public boolean accept(File folder, String name) {
                    return name.endsWith(".xls");
                }
            });
            session.setAttribute("monitFiles", files);

            userPath = "/pmpk/monovz";
            url = "/WEB-INF/pages" + userPath + ".jsp";
        } else if (id.equals("toovzupload")) {
            try {
                session.removeAttribute("monitFiles");
            } catch (Exception ex) {
            }
            File folder = new File("mon//");
            String[] files = folder.list(new FilenameFilter() {
                @Override
                public boolean accept(File folder, String name) {
                    return name.endsWith(".xls");
                }
            });
            session.setAttribute("monitFiles", files);

            userPath = "/pmpk/monovzupload";
            url = "/WEB-INF/pages" + userPath + ".jsp";
        } else if (id.equals("totestreestr")) {
            try {
                session.removeAttribute("monitFiles");
            } catch (Exception ex) {
            }
            File folder = new File("mon//");
            String[] files = folder.list(new FilenameFilter() {
                @Override
                public boolean accept(File folder, String name) {
                    return name.endsWith(".xls");
                }
            });
            session.setAttribute("monitFiles", files);
            try {
                session.removeAttribute("regList");
            } catch (Exception ex) {
            }
            List<SprRegion> regList = sprRegionFacade.findNoCenter();

            session.setAttribute("regList", regList);

            userPath = "/pmpk/montestreestr";
            url = "/WEB-INF/pages" + userPath + ".jsp";
        } else if (id.equals("touploadreestr")) {
            try {
                session.removeAttribute("monitFiles");
            } catch (Exception ex) {
            }
            File folder = new File("mon//");
            String[] files = folder.list(new FilenameFilter() {
                @Override
                public boolean accept(File folder, String name) {
                    return name.endsWith(".xls");
                }
            });
            session.setAttribute("monitFiles", files);
            try {
                session.removeAttribute("regList");
            } catch (Exception ex) {
            }
            List<SprRegion> regList = sprRegionFacade.findNoCenter();

            session.setAttribute("regList", regList);

            userPath = "/pmpk/monuploadreestr";
            url = "/WEB-INF/pages" + userPath + ".jsp";
        }
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
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
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        /*if (user == null) {
            String url = "/index.jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return;
        }*/
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
        StringBuilder sb = new StringBuilder();
        Boolean itsOk = false;

        Map<String, String[]> params = request.getParameterMap();

        String action = params.get("action")[0];
        if (action.equals("testreestr")) {
            String fileName = params.get("filename")[0];
            String path = "mon//" + fileName;
            String copyPath = "mon//tested//" + fileName;
            File file = new File(path);
            File copyfile = new File(copyPath);
            // всевозможные форматы дат
            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yy");
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat format3 = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.US);
            String inpRowS = params.get("inpRow")[0];
            Integer inpRow = 0;
            try {
                inpRow = Integer.parseInt(inpRowS);
            } catch (Exception ex) {
            }
            try {
                Workbook xlsfile;
                WritableWorkbook copy;
                xlsfile = Workbook.getWorkbook(file);
                copy = Workbook.createWorkbook(copyfile, xlsfile);

                WritableSheet sheet = copy.getSheet(0);

                // параметры проверки
                String chb_fio = params.get("chb_fio")[0];
                String chb_oo = params.get("chb_oo")[0];
                String chb_stage = params.get("chb_stage")[0];
                String chb_op = params.get("chb_op")[0];
                String chb_formobr = params.get("chb_formobr")[0];

                Integer inpFamCol = 0;
                Integer inpNameCol = 0;
                Integer inpPatrCol = 0;
                Integer inpDrCol = 0;
                Integer inpOoCol = 0;
                Integer inpStageCol = 0;
                Integer inpOpCol = 0;
                Integer inpVarCol = 0;
                Integer inpFormobrCol = 0;
                if (chb_fio.equals("1")) {
                    String inpFamColS = params.get("inpFamCol")[0];
                    try {
                        inpFamCol = Integer.parseInt(inpFamColS);
                    } catch (Exception ex) {
                    }
                    String inpNameColS = params.get("inpNameCol")[0];
                    try {
                        inpNameCol = Integer.parseInt(inpNameColS);
                    } catch (Exception ex) {
                    }
                    String inpPatrColS = params.get("inpPatrCol")[0];
                    try {
                        inpPatrCol = Integer.parseInt(inpPatrColS);
                    } catch (Exception ex) {
                    }
                    String inpDrColS = params.get("inpDrCol")[0];
                    try {
                        inpDrCol = Integer.parseInt(inpDrColS);
                    } catch (Exception ex) {
                    }
                }
                if (chb_oo.equals("1")) {
                    String inpOoColS = params.get("inpOoCol")[0];
                    try {
                        inpOoCol = Integer.parseInt(inpOoColS);
                    } catch (Exception ex) {
                    }
                }
                if (chb_stage.equals("1")) {
                    String inpStageColS = params.get("inpStageCol")[0];
                    try {
                        inpStageCol = Integer.parseInt(inpStageColS);
                    } catch (Exception ex) {
                    }
                }
                if (chb_op.equals("1")) {
                    String inpOpColS = params.get("inpOpCol")[0];
                    try {
                        inpOpCol = Integer.parseInt(inpOpColS);
                    } catch (Exception ex) {
                    }
                    String inpVarColS = params.get("inpVarCol")[0];
                    try {
                        inpVarCol = Integer.parseInt(inpVarColS);
                    } catch (Exception ex) {
                    }
                }
                if (chb_formobr.equals("1")) {
                    String inpFormobrColS = params.get("inpFormobrCol")[0];
                    try {
                        inpFormobrCol = Integer.parseInt(inpFormobrColS);
                    } catch (Exception ex) {
                    }
                }
                //for (int i = inpRow - 1; i < sheet.getRows(); i++) {
                if (chb_fio.equals("1")) {
                    for (int i = inpRow - 1; i < sheet.getRows(); i++) {
                        if ((inpFamCol != inpNameCol) && (inpFamCol != inpPatrCol)) {
                            // все данные в разных столбцах
                            String fam = "";
                            String name = "";
                            String patr = "";
                            String drS = "";

                            try {
                                fam = sheet.getWritableCell(inpFamCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }

                            try {
                                name = sheet.getWritableCell(inpNameCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }

                            try {
                                patr = sheet.getWritableCell(inpPatrCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }

                            try {
                                drS = sheet.getWritableCell(inpDrCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }
                            Date dr = null;
                            try {
                                dr = format2.parse(drS);
                            } catch (Exception ex) {
                            }
                            try {
                                dr = format1.parse(drS);
                            } catch (Exception ex) {
                            }
                            try {
                                dr = format3.parse(drS);
                            } catch (Exception ex) {
                            }
                            if (dr != null) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dr);
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                dr = calendar.getTime();
                            }

                            Children child = null;
                            try {
                                List<Children> children = childrenFacade.findByFioDr(fam, name, patr, dr);
                                child = children.get(0);
                            } catch (Exception ex) {
                            }
                            if (child == null) {
                                // ребёнок не найден в БД
                                WritableCellFormat cf = new WritableCellFormat();
                                try {
                                    cf.setBackground(Colour.RED);
                                    sheet.getWritableCell(inpFamCol - 1, i).setCellFormat(cf);
                                } catch (WriteException ex) {
                                    Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        } else if ((inpFamCol == inpNameCol) && (inpFamCol == inpPatrCol)
                                && (inpFamCol != inpDrCol)) {
                            // фио в одном столбце, др в другом
                            String fio = "";
                            String fam = "";
                            String name = "";
                            String patr = "";
                            String drS = "";

                            try {
                                fio = sheet.getWritableCell(inpFamCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }
                            String[] fioArray = fio.split(" ");
                            for (String f : fioArray) {
                                if ((!f.equals(" ")) && (!f.equals(""))) {
                                    if (fam.equals("")) {
                                        fam = f;
                                    } else if (name.equals("")) {
                                        name = f;
                                    } else if (patr.equals("")) {
                                        patr = f;
                                    } else if ((!fam.equals("")) && (!name.equals("")) && (!patr.equals(""))) {
                                        patr += " " + f;
                                    }
                                }
                            }

                            try {
                                drS = sheet.getWritableCell(inpDrCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }
                            Date dr = null;
                            try {
                                dr = format2.parse(drS);
                            } catch (Exception ex) {
                            }
                            try {
                                dr = format1.parse(drS);
                            } catch (Exception ex) {
                            }
                            try {
                                dr = format3.parse(drS);
                            } catch (Exception ex) {
                            }
                            if (dr != null) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dr);
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                dr = calendar.getTime();
                            }
                            Children child = null;
                            try {
                                List<Children> children = childrenFacade.findByFioDr(fam, name, patr, dr);
                                child = children.get(0);
                            } catch (Exception ex) {
                            }
                            if (child == null) {
                                // ребёнок не найден в БД
                                WritableCellFormat cf = new WritableCellFormat();
                                try {
                                    cf.setBackground(Colour.RED);
                                    sheet.getWritableCell(inpFamCol - 1, i).setCellFormat(cf);
                                } catch (WriteException ex) {
                                    Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        } else if ((inpFamCol == inpNameCol) && (inpFamCol == inpPatrCol)
                                && (inpFamCol == inpDrCol)) {
                            // фио и др в одном столбце
                            String fio = "";
                            String fam = "";
                            String name = "";
                            String patr = "";
                            String drS = "";

                            try {
                                fio = sheet.getWritableCell(inpFamCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }
                            String[] fioArray = fio.split(" ");
                            Date dr = null;
                            for (String f : fioArray) {
                                if ((!f.equals(" ")) && (!f.equals(""))) {
                                    if (fam.equals("")) {
                                        fam = f;
                                    } else if (name.equals("")) {
                                        name = f;
                                    } else if (patr.equals("")) {
                                        patr = f;
                                    } else if ((!fam.equals("")) && (!name.equals("")) && (!patr.equals(""))) {
                                        drS = f;
                                        try {
                                            dr = format1.parse(drS);
                                        } catch (Exception ex) {
                                        }
                                        if (dr == null) {
                                            patr += " " + f;
                                        }
                                    }
                                }
                            }

                            if (dr != null) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dr);
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                dr = calendar.getTime();
                            }
                            Children child = null;
                            try {
                                List<Children> children = childrenFacade.findByFioDr(fam, name, patr, dr);
                                child = children.get(0);
                            } catch (Exception ex) {
                            }
                            if (child == null) {
                                // ребёнок не найден в БД
                                WritableCellFormat cf = new WritableCellFormat();
                                try {
                                    cf.setBackground(Colour.RED);
                                    sheet.getWritableCell(inpFamCol - 1, i).setCellFormat(cf);
                                } catch (WriteException ex) {
                                    Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }

                }
                if (chb_oo.equals("1")) {    // проверка образовательной организации
                    /* парсинг строки ключа типа образовательных учреждений: 
                            ключ должен состоять из текстовых элементов, записанных строчными буквами, 
                            скобок, знаков +, |, ! и составлен по правилам:
                            () - определяют уровень выражения, н-р (a+b)|(b+c) - два уровня
                            ! - отрицание, ставится как перед текстовым элементов, так и перед скобками
                            | - логическое или
                            + - логическое и
                            | и + не могут быть на одном уровне (необходимо разделить скобками), н-р:
                            a+b|c - неверно, 
                            (a+b)|c - верно, 
                            a+(b|c) - верно
                            пробелы являются частью текстового элемента
                     */
                    List<KeySprOuType> tKeys = keySprOuTypeFacade.findAll();
                    List<KeyLevel> parsedKeys = new ArrayList<>();
                    for (KeySprOuType tk : tKeys) {
                        String key = tk.getKeysproutypesKeys();
                        KeyLevel l0 = parse(key);
                        l0.keyId = tk.getSproutypeId().getSproutypeId();
                        parsedKeys.add(l0);
                    }

                    for (int i = inpRow - 1; i < sheet.getRows(); i++) {
                        String ou = "";
                        try {
                            ou = sheet.getWritableCell(inpOoCol - 1, i).getContents().trim();
                        } catch (Exception ex) {
                        }

                        // пробуем найти образовательное учреждение
                        List<SprOu> ouList = new ArrayList<>();
                        List<SprOu> sprOuList = new ArrayList<>();

                        // находим район, к которому относится реестр
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

                        if (!ou.equals("")) {
                            // заменяем ё на е
                            ou = ou.toLowerCase().replaceAll("ё", "е");
                            // убираем двойные пробелы
                            ou = ou.replaceAll("  ", " ");

                            // исключаем класс и группу из строки (если они там есть)
                            int cl = -1;
                            String stageN = "";
                            int indx = ou.indexOf("класс".toLowerCase());
                            if (indx > 0) {
                                for (int j = indx; j >= 0; j--) {
                                    if (Character.isDigit(ou.charAt(j))) {
                                        stageN = ou.substring(j, j + 1) + stageN;
                                    } else if (!Character.isDigit(ou.charAt(j))) {
                                        if (!stageN.equals("")) {
                                            cl = j;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (cl > 0) {
                                ou = ou.substring(0, cl - 1);
                            } else if (cl == 0) {
                                ou = "";
                            }

                            int gr = -1;
                            if (ou.contains("группа".toLowerCase())) {
                                List<SprStage> stageList = sprStageFacade.findAllStages();
                                for (SprStage s : stageList) {
                                    if (ou.contains(s.getSprstageName().toLowerCase())) {
                                        gr = ou.indexOf(s.getSprstageName().toLowerCase());
                                    }
                                }
                            }
                            if (gr > 0) {
                                ou = ou.substring(0, gr - 1);
                            } else if (gr == 0) {
                                ou = "";
                            }

                            SprOuType ouType = null;
                            // проверяем тип учреждения по ключам
                            for (KeyLevel pKey : parsedKeys) {
                                if (pKey.test(ou)) {
                                    try {
                                        ouType = sprOuTypeFacade.findById(pKey.keyId);
                                    } catch (Exception ex) {
                                    }
                                }
                            }
                            if (ouType != null) {
                                ouList = sprOuFacade.findBySprOuTypeAndRegion(ouType, reg);
                            }
                            if (!ouList.isEmpty()) {
                                for (SprOu o : ouList) {
                                    String name = o.getSprouName(); // характерная часть названия ОУ                                    
                                    Integer nom = o.getSprouNom();  // номер ОУ
                                    String dopKey = o.getSprouDopKey(); // дополнительный параметр в виде выражения по типу ключей
                                    Boolean flag = false;
                                    if (name != null) {
                                        flag = false;
                                        name = name.replaceAll("ё", "е");
                                        if (ou.contains(name.toLowerCase())) { // проверяем, есть ли часть названия в нашей строке
                                            flag = true;
                                        }
                                    }

                                    if (nom != null) {   // проверяем есть ли такой номер в нашей строке (при этом исключаем класс)
                                        flag = false;
                                        if (!ou.equals("")) {
                                            String ouNS = "";
                                            for (int j = 0; j < ou.length(); j++) {
                                                if (Character.isDigit(ou.charAt(j))) {
                                                    if (j == ou.length() - 1) {
                                                        ouNS = ouNS + ou.substring(j);
                                                    } else {
                                                        ouNS = ouNS + ou.substring(j, j + 1);
                                                    }
                                                } else if (!Character.isDigit(ou.charAt(j))) {
                                                    if (!ouNS.equals("")) {
                                                        break;
                                                    }
                                                }
                                            }
                                            if (!ouNS.equals("")) {
                                                Integer ouN = 0;
                                                try {
                                                    ouN = Integer.parseInt(ouNS);
                                                } catch (Exception ex) {
                                                }
                                                if (ouN.equals(nom)) {
                                                    flag = true;
                                                }
                                            }
                                        }
                                    }
                                    if ((name == null) && (nom == null)) {
                                        flag = true;
                                    }

                                    if (flag && dopKey != null) {
                                        flag = false;
                                        if (!dopKey.equals("")) {
                                            KeyLevel l = parse(dopKey.toLowerCase());
                                            if (l.test(ou)) {
                                                flag = true;
                                            }
                                        }
                                    }
                                    if (flag) {
                                        sprOuList.add(o);
                                    }
                                }
                            }
                            if (sprOuList.isEmpty()) {   // если не найдено ни одно подходящее учреждение,
                                // пробуем поискать без учёта района
                                ouList = sprOuFacade.findBySprOuType(ouType);
                                for (SprOu o : ouList) {
                                    String name = o.getSprouName(); // характерная часть названия ОУ                                    
                                    Integer nom = o.getSprouNom();  // номер ОУ
                                    String dopKey = o.getSprouDopKey(); // дополнительный параметр в виде выражения по типу ключей
                                    Boolean flag = true;
                                    if (name != null) {
                                        flag = false;
                                        name = name.replaceAll("ё", "е");
                                        if (ou.contains(name.toLowerCase())) { // проверяем, есть ли часть названия в нашей строке
                                            flag = true;
                                        }
                                    }

                                    if (nom != null) {   // проверяем есть ли такой номер в нашей строке (при этом исключаем класс)
                                        flag = false;
                                        if (!ou.equals("")) {
                                            String ouNS = "";
                                            for (int j = 0; j < ou.length(); j++) {
                                                if (Character.isDigit(ou.charAt(j))) {
                                                    if (j == ou.length() - 1) {
                                                        ouNS = ouNS + ou.substring(j);
                                                    } else {
                                                        ouNS = ouNS + ou.substring(j, j + 1);
                                                    }
                                                } else if (!Character.isDigit(ou.charAt(j))) {
                                                    if (!ouNS.equals("")) {
                                                        break;
                                                    }
                                                }
                                            }
                                            if (!ouNS.equals("")) {
                                                Integer ouN = 0;
                                                try {
                                                    ouN = Integer.parseInt(ouNS);
                                                } catch (Exception ex) {
                                                }
                                                if (ouN.equals(nom)) {
                                                    flag = true;
                                                }
                                            }
                                        }
                                    }

                                    if (flag && dopKey != null) {
                                        flag = false;
                                        if (!dopKey.equals("")) {
                                            KeyLevel l = parse(dopKey.toLowerCase());
                                            if (l.test(ou)) {
                                                flag = true;
                                            }
                                        }
                                    }
                                    if (flag) {
                                        sprOuList.add(o);
                                    }
                                }
                            }

                            if (sprOuList.isEmpty()) {
                                // ОУ не найдено в БД
                                WritableCellFormat cf = new WritableCellFormat();
                                try {
                                    cf.setBackground(Colour.RED);
                                    sheet.getWritableCell(inpOoCol - 1, i).setCellFormat(cf);
                                } catch (WriteException ex) {
                                    Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (sprOuList.size() > 1) {
                                // неоднозначность
                                WritableCellFormat cf = new WritableCellFormat();
                                try {
                                    cf.setBackground(Colour.YELLOW);
                                    sheet.getWritableCell(inpOoCol - 1, i).setCellFormat(cf);
                                } catch (WriteException ex) {
                                    Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
                if (chb_stage.equals("1")) { // проверка ступени обучения
                    for (int i = inpRow - 1; i < sheet.getRows(); i++) {
                        String stage = "";
                        try {
                            stage = sheet.getWritableCell(inpStageCol - 1, i).getContents().trim();
                        } catch (Exception ex) {
                        }
                        String stageN = "";
                        if (!stage.equals("")) {    // пробуем найти класс в строке
                            int indx = stage.toUpperCase().indexOf("класс".toUpperCase());
                            for (int j = indx; j >= 0; j--) {
                                if (Character.isDigit(stage.charAt(j))) {
                                    stageN = stage.substring(j, j + 1) + stageN;
                                } else if (!Character.isDigit(stage.charAt(j))) {
                                    if (!stageN.equals("")) {
                                        break;
                                    }
                                }
                            }

                            SprStage st = null;
                            if (!stageN.equals("")) {   // проверяем, есть ли такой класс в справочнике
                                try {
                                    st = sprStageFacade.findByName(stageN + " класс");
                                } catch (Exception ex) {

                                }
                            }
                            if (st == null) {   // предполагаем, что у нас ДОУ, ищем группу
                                if (stage.toUpperCase().contains("группа".toUpperCase())) {
                                    List<SprStage> stageList = sprStageFacade.findAllStages();
                                    for (SprStage s : stageList) {
                                        if (stage.toUpperCase().contains(s.getSprstageName().toUpperCase())) {
                                            st = s;
                                        }
                                    }
                                }
                            }
                            if (st == null) {
                                WritableCellFormat cf = new WritableCellFormat();
                                try {
                                    cf.setBackground(Colour.RED);
                                    sheet.getWritableCell(inpStageCol - 1, i).setCellFormat(cf);
                                } catch (WriteException ex) {
                                    Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                }
                if (chb_op.equals("1")) {    // проверка образовательной программы
                    for (int i = inpRow - 1; i < sheet.getRows(); i++) {
                        String fullOp = "";
                        String op = "";
                        String var = "";
                        try {
                            fullOp = sheet.getWritableCell(inpOpCol - 1, i).getContents().trim();
                        } catch (Exception ex) {
                        }
                        if (inpOpCol != inpVarCol) {    // программа и вариант в разных столбцах
                            try {
                                op = sheet.getWritableCell(inpOpCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }
                            try {
                                var = sheet.getWritableCell(inpVarCol - 1, i).getContents().trim();
                                var = var.replaceAll(" ", "");
                                var = var.replaceAll("Пр.1599", "");
                                var = var.replaceAll("Пр.№1599", "");
                                var = var.replaceAll("ё", "е");
                                var = var.replaceAll("[А-Яа-я]", "");
                                var = var.replaceAll(",", "");
                                var = var.replaceAll("-", "");
                                if (var.indexOf(".") == 0) {
                                    var = var.substring(1);
                                }
                                if (!var.equals("")) {
                                    var = "Вариант " + var;
                                    if (!var.contains(".")) {

                                    } else if (var.lastIndexOf(".") < var.length() - 1) {
                                        var += ".";
                                    }
                                }
                            } catch (Exception ex) {
                            }
                        } else if (!fullOp.equals("")) { // программа и вариант в одном столбце
                            String[] opArray = fullOp.split("\\(");
                            if (opArray.length > 1) {
                                op = opArray[0];
                                var = opArray[1].replaceAll("\\)", "");
                                var = var.replaceAll(" ", "");
                                var = var.replaceAll("Пр.1599", "");
                                var = var.replaceAll("Пр.№1599", "");
                                var = var.replaceAll("ё", "е");
                                var = var.replaceAll("[А-Яа-я]", "");
                                var = var.replaceAll("-", "");
                                var = var.replaceAll(",", "");
                                if (var.indexOf(".") == 0) {
                                    var = var.substring(1);
                                }
                                if (!var.equals("")) {
                                    var = "Вариант " + var;
                                    if (!var.contains(".")) {

                                    } else if (var.lastIndexOf(".") < var.length() - 1) {
                                        var += ".";
                                    }
                                }
                            } else if (opArray.length == 1) {
                                op = opArray[0];
                            }
                            // пробуем найти образовательную программу
                            List<SprObr> obrList = new ArrayList<>();
                            // выбираем и преобразуем ключи 
                            List<KeySprObrVid> vidKeys = keySprObrVidFacade.findAll();
                            List<KeyLevel> parsedVidKeys = new ArrayList<>();
                            for (KeySprObrVid k : vidKeys) {
                                String key = k.getKeysprobrvidKeys().toLowerCase();
                                KeyLevel l0 = parse(key);
                                l0.keyId = k.getSprobrvidId().getSprobrvidId();
                                parsedVidKeys.add(l0);
                            }
                            List<KeySprObrType> typeKeys = keySprObrTypeFacade.findAll();
                            List<KeyLevel> parsedTypeKeys = new ArrayList<>();
                            for (KeySprObrType k : typeKeys) {
                                String key = k.getKeysprobrtypeKeys().toLowerCase();
                                KeyLevel l0 = parse(key);
                                l0.keyId = k.getSprobrtypeId().getSprobrtypeId();
                                parsedTypeKeys.add(l0);
                            }
                            List<KeySprObrSpec> specKeys = keySprObrSpecFacade.findAll();
                            List<KeyLevel> parsedSpecKeys = new ArrayList<>();
                            for (KeySprObrSpec k : specKeys) {
                                String key = k.getKeysprobrspecKeys();
                                KeyLevel l0 = parse(key);
                                l0.keyId = k.getSprobrspecId().getSprobrspecId();
                                parsedSpecKeys.add(l0);
                            }

                            if (!op.equals("")) {
                                SprObrVid obrVid = null;
                                // проверяем вид образовательной программы по ключам
                                for (KeyLevel pKey : parsedVidKeys) {
                                    if (pKey.test(op.toLowerCase())) {
                                        try {
                                            obrVid = sprObrVidFacade.findById(pKey.keyId);
                                        } catch (Exception ex) {
                                        }
                                    }
                                }

                                if (obrVid != null) {
                                    SprObrType obrType = null;
                                    // проверяем тип образовательной программы по ключам
                                    for (KeyLevel pKey : parsedTypeKeys) {
                                        if (pKey.test(op.toLowerCase())) {
                                            try {
                                                obrType = sprObrTypeFacade.findBySprObrTypeId(pKey.keyId);
                                            } catch (Exception ex) {
                                            }
                                        }
                                    }
                                    if (obrType != null) {
                                        SprObrSpec obrSpec = null;
                                        // проверяем специализированность образовательной программы по ключам
                                        for (KeyLevel pKey : parsedSpecKeys) {
                                            if (pKey.test(op)) {
                                                try {
                                                    obrSpec = sprObrSpecFacade.findById(pKey.keyId);
                                                } catch (Exception ex) {
                                                }
                                            }
                                        }
                                        obrList = sprObrFacade.findByTypeVidSpec(obrType, obrVid, obrSpec);
                                    }
                                }
                            }

                            if (obrList.isEmpty()) {
                                // ОП не найдена в БД
                                WritableCellFormat cf = new WritableCellFormat();
                                try {
                                    cf.setBackground(Colour.RED);
                                    sheet.getWritableCell(inpOpCol - 1, i).setCellFormat(cf);
                                } catch (WriteException ex) {
                                    Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (obrList.size() > 1) {
                                // неоднозначность
                                WritableCellFormat cf = new WritableCellFormat();
                                try {
                                    cf.setBackground(Colour.YELLOW);
                                    sheet.getWritableCell(inpOpCol - 1, i).setCellFormat(cf);
                                } catch (WriteException ex) {
                                    Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            // пробуем найти вариант
                            List<SprVar> varList = new ArrayList<>();
                            if (!var.equals("")) {
                                varList = sprVarFacade.findByName(var);
                            }
                            if ((var.equals("")) || (varList.isEmpty())) { // дополнительная обработка, если не нашлось
                                var = fullOp;
                                if (!var.equals("")) {
                                    var = var.replaceAll(" ", "");
                                    var = var.replaceAll("Пр.1599", "");
                                    var = var.replaceAll("Пр.№1599", "");
                                    var = var.replaceAll("ё", "е");
                                    var = var.replaceAll("[А-Яа-я]", "");
                                    var = var.replaceAll("\\(", "");
                                    var = var.replaceAll("\\)", "");
                                    var = var.replaceAll(",", "");
                                    var = var.replaceAll("-", "");
                                    if (var.indexOf(".") == 0) {
                                        var = var.substring(1);
                                    }
                                    if (!var.equals("")) {
                                        var = "Вариант " + var;
                                        if (!var.contains(".")) {
                                        } else if (var.lastIndexOf(".") < var.length() - 1) {
                                            var += ".";
                                        }
                                    }
                                }

                                if (!var.equals("")) {
                                    varList = sprVarFacade.findByName(var);
                                }
                            }

                            if (!var.equals("")) {
                                if (varList.isEmpty()) {
                                    // вариант не найден в БД
                                    WritableCellFormat cf = new WritableCellFormat();
                                    try {
                                        cf.setBackground(Colour.YELLOW);
                                        sheet.getWritableCell(inpVarCol - 1, i).setCellFormat(cf);
                                    } catch (WriteException ex) {
                                        Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        }
                    }
                }
                if (chb_formobr.equals("1")) {    // проверка формы обучения                    
                    for (int i = inpRow - 1; i < sheet.getRows(); i++) {
                        String formobr = "";
                        try {
                            formobr = sheet.getWritableCell(inpFormobrCol - 1, i).getContents().trim();
                        } catch (Exception ex) {
                        }
                        formobr = formobr.replaceAll("  ", " ");
                        if ((!formobr.toLowerCase().equals("вне оо")) && (!formobr.toLowerCase().equals("в оо"))
                                && (!formobr.toLowerCase().equals(""))) {
                            WritableCellFormat cf = new WritableCellFormat();
                            try {
                                cf.setBackground(Colour.RED);
                                sheet.getWritableCell(inpFormobrCol - 1, i).setCellFormat(cf);
                            } catch (WriteException ex) {
                                Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }
                }
                //}
                try {
                    copy.write();
                    copy.close();
                    itsOk = true;
                } catch (IOException e) {
                    // TODO Автоматически созданный блок catch
                    e.printStackTrace();
                } catch (WriteException e) {
                    // TODO Автоматически созданный блок catch
                    e.printStackTrace();
                }
            } catch (BiffException ex) {
                Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (itsOk) {
                sb.append("<result>").append("1").append("</result>");
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
        } else if (action.equals("upload")) {
            String fileName = params.get("filename")[0];
            String path = "mon//" + fileName;
            File file = new File(path);
            // всевозможные форматы дат
            SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yy");
            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat format3 = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.US);
            String inpRowS = params.get("inpRow")[0];
            Integer inpRow = 0;
            try {
                inpRow = Integer.parseInt(inpRowS);
            } catch (Exception ex) {
            }
            try {
                Workbook xlsfile;
                xlsfile = Workbook.getWorkbook(file);
                Sheet sheet = xlsfile.getSheet(0);

                // находим район, к которому относится реестр
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
                // дата мониторинга
                String monDateS = params.get("monDate")[0];
                Date monDate = null;
                try {
                    monDate = format0.parse(monDateS);
                } catch (Exception ex) {
                }

                // вставляем в БД информацию о мониторинге (если не было)
                Monitoring monitoring = null;
                try {
                    monitoring = monitoringFacade.findByDateReg(monDate, reg);
                } catch (Exception ex) {
                }
                if (monitoring == null) {
                    monitoring = new Monitoring();
                    monitoring.setMonitoringDate(monDate);
                    monitoring.setSprregId(reg);
                    monitoringFacade.create(monitoring);
                }

                // номера столбцов для вставки
                String fieldFam = params.get("field_fam")[0];
                String fieldName = params.get("field_name")[0];
                String fieldPatr = params.get("field_patr")[0];
                String fieldDr = params.get("field_dr")[0];
                String fieldPmpkD = params.get("field_pmpkd")[0];
                String fieldZakl = params.get("field_zakl")[0];
                String fieldOu = params.get("field_ou")[0];
                String fieldForm = params.get("field_form")[0];
                String fieldStage = params.get("field_stage")[0];
                String fieldOp = params.get("field_op")[0];
                String fieldVar = params.get("field_var")[0];
                String fieldLes = params.get("field_les")[0];
                String fieldAs = params.get("field_as")[0];
                String fieldEq = params.get("field_eq")[0];
                String fieldReas = params.get("field_reas")[0];

                Integer fieldFamCol = 0;
                Integer fieldNameCol = 0;
                Integer fieldPatrCol = 0;
                Integer fieldDrCol = 0;
                Integer fieldPmpkDCol = 0;
                Integer fieldZaklCol = 0;
                Integer fieldOuCol = 0;
                Integer fieldFormCol = 0;
                Integer fieldStageCol = 0;
                Integer fieldOpCol = 0;
                Integer fieldVarCol = 0;
                Integer fieldLesCol = 0;
                Integer fieldAsCol = 0;
                Integer fieldEqCol = 0;
                Integer fieldReasCol = 0;

                try {
                    fieldFamCol = Integer.parseInt(fieldFam);
                } catch (Exception ex) {
                }
                try {
                    fieldNameCol = Integer.parseInt(fieldName);
                } catch (Exception ex) {
                }
                try {
                    fieldPatrCol = Integer.parseInt(fieldPatr);
                } catch (Exception ex) {
                }
                try {
                    fieldDrCol = Integer.parseInt(fieldDr);
                } catch (Exception ex) {
                }
                try {
                    fieldPmpkDCol = Integer.parseInt(fieldPmpkD);
                } catch (Exception ex) {
                }
                try {
                    fieldZaklCol = Integer.parseInt(fieldZakl);
                } catch (Exception ex) {
                }
                try {
                    fieldOuCol = Integer.parseInt(fieldOu);
                } catch (Exception ex) {
                }
                try {
                    fieldFormCol = Integer.parseInt(fieldForm);
                } catch (Exception ex) {
                }
                try {
                    fieldStageCol = Integer.parseInt(fieldStage);
                } catch (Exception ex) {
                }
                try {
                    fieldOpCol = Integer.parseInt(fieldOp);
                } catch (Exception ex) {
                }
                try {
                    fieldVarCol = Integer.parseInt(fieldVar);
                } catch (Exception ex) {
                }
                try {
                    fieldLesCol = Integer.parseInt(fieldLes);
                } catch (Exception ex) {
                }
                try {
                    fieldAsCol = Integer.parseInt(fieldAs);
                } catch (Exception ex) {
                }
                try {
                    fieldEqCol = Integer.parseInt(fieldEq);
                } catch (Exception ex) {
                }
                try {
                    fieldReasCol = Integer.parseInt(fieldReas);
                } catch (Exception ex) {
                }

                /* парсинг строки ключей: 
                            ключ должен состоять из текстовых элементов, записанных строчными буквами, 
                            скобок, знаков +, |, ! и составлен по правилам:
                            () - определяют уровень выражения, н-р (a+b)|(b+c) - два уровня
                            ! - отрицание, ставится как перед текстовым элементов, так и перед скобками
                            | - логическое или 
                            + - логическое и
                            | и + не могут быть на одном уровне (необходимо разделить скобками), н-р:
                            a+b|c - неверно, 
                            (a+b)|c - верно, 
                            a+(b|c) - верно
                            пробелы являются частью текстового элемента
                 */
                // выбираем и преобразуем ключи для учреждений
                List<KeySprOuType> tKeys = keySprOuTypeFacade.findAll();
                List<KeyLevel> parsedKeys = new ArrayList<>();
                for (KeySprOuType tk : tKeys) {
                    String key = tk.getKeysproutypesKeys();
                    KeyLevel l0 = parse(key);
                    l0.keyId = tk.getSproutypeId().getSproutypeId();
                    parsedKeys.add(l0);
                }
                // выбираем и преобразуем ключи для образовательных программ
                List<KeySprObrVid> vidKeys = keySprObrVidFacade.findAll();
                List<KeyLevel> parsedVidKeys = new ArrayList<>();
                for (KeySprObrVid k : vidKeys) {
                    String key = k.getKeysprobrvidKeys().toLowerCase();
                    KeyLevel l0 = parse(key);
                    l0.keyId = k.getSprobrvidId().getSprobrvidId();
                    parsedVidKeys.add(l0);
                }
                List<KeySprObrType> typeKeys = keySprObrTypeFacade.findAll();
                List<KeyLevel> parsedTypeKeys = new ArrayList<>();
                for (KeySprObrType k : typeKeys) {
                    String key = k.getKeysprobrtypeKeys().toLowerCase();
                    KeyLevel l0 = parse(key);
                    l0.keyId = k.getSprobrtypeId().getSprobrtypeId();
                    parsedTypeKeys.add(l0);
                }
                List<KeySprObrSpec> specKeys = keySprObrSpecFacade.findAll();
                List<KeyLevel> parsedSpecKeys = new ArrayList<>();
                for (KeySprObrSpec k : specKeys) {
                    String key = k.getKeysprobrspecKeys();
                    KeyLevel l0 = parse(key);
                    l0.keyId = k.getSprobrspecId().getSprobrspecId();
                    parsedSpecKeys.add(l0);
                }

                for (int i = inpRow - 1; i < sheet.getRows(); i++) {
                    if ((fieldFamCol != 0) && (fieldNameCol != 0) // фио и дата рождения
                            && (fieldPatrCol != 0) && (fieldDrCol != 0)) {
                        Children child = null;
                        if ((!fieldFamCol.equals(fieldNameCol)) && (!fieldFamCol.equals(fieldPatrCol))
                                && (!fieldFamCol.equals(fieldDrCol))) {
                            // все данные в разных столбцах
                            String fam = "";
                            String name = "";
                            String patr = "";
                            String drS = "";

                            try {
                                fam = sheet.getCell(fieldFamCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }

                            try {
                                name = sheet.getCell(fieldNameCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }

                            try {
                                patr = sheet.getCell(fieldPatrCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }

                            try {
                                drS = sheet.getCell(fieldDrCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }
                            Date dr = null;
                            try {
                                dr = format2.parse(drS);
                            } catch (Exception ex) {
                            }
                            try {
                                dr = format1.parse(drS);
                            } catch (Exception ex) {
                            }
                            try {
                                dr = format3.parse(drS);
                            } catch (Exception ex) {
                            }
                            if (dr != null) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dr);
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                dr = calendar.getTime();
                            }
                            try {
                                List<Children> children = childrenFacade.findByFioDr(fam, name, patr, dr);
                                child = children.get(0);
                            } catch (Exception ex) {
                            }

                        } else if ((fieldFamCol.equals(fieldNameCol)) && (fieldFamCol.equals(fieldPatrCol))
                                && (!fieldFamCol.equals(fieldDrCol))) {
                            // фио в одном столбце, др в другом
                            String fio = "";
                            String fam = "";
                            String name = "";
                            String patr = "";
                            String drS = "";

                            try {
                                fio = sheet.getCell(fieldFamCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }
                            String[] fioArray = fio.split(" ");
                            for (String f : fioArray) {
                                if ((!f.equals(" ")) && (!f.equals(""))) {
                                    if (fam.equals("")) {
                                        fam = f;
                                    } else if (name.equals("")) {
                                        name = f;
                                    } else if (patr.equals("")) {
                                        patr = f;
                                    } else if ((!fam.equals("")) && (!name.equals("")) && (!patr.equals(""))) {
                                        patr += " " + f;
                                    }
                                }
                            }

                            try {
                                drS = sheet.getCell(fieldDrCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }
                            Date dr = null;
                            try {
                                dr = format2.parse(drS);
                            } catch (Exception ex) {
                            }
                            try {
                                dr = format1.parse(drS);
                            } catch (Exception ex) {
                            }
                            try {
                                dr = format3.parse(drS);
                            } catch (Exception ex) {
                            }
                            if (dr != null) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dr);
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                dr = calendar.getTime();
                            }
                            try {
                                List<Children> children = childrenFacade.findByFioDr(fam, name, patr, dr);
                                child = children.get(0);
                            } catch (Exception ex) {
                            }

                        } else if ((fieldFamCol.equals(fieldNameCol)) && (fieldFamCol.equals(fieldPatrCol))
                                && (fieldFamCol.equals(fieldDrCol))) {
                            // фио и др в одном столбце
                            String fio = "";
                            String fam = "";
                            String name = "";
                            String patr = "";
                            String drS = "";

                            try {
                                fio = sheet.getCell(fieldFamCol - 1, i).getContents().trim();
                            } catch (Exception ex) {
                            }
                            String[] fioArray = fio.split(" ");
                            Date dr = null;
                            for (String f : fioArray) {
                                if ((!f.equals(" ")) && (!f.equals(""))) {
                                    if (fam.equals("")) {
                                        fam = f;
                                    } else if (name.equals("")) {
                                        name = f;
                                    } else if (patr.equals("")) {
                                        patr = f;
                                    } else if ((!fam.equals("")) && (!name.equals("")) && (!patr.equals(""))) {
                                        drS = f;
                                        try {
                                            dr = format1.parse(drS);
                                        } catch (Exception ex) {
                                        }
                                        if (dr == null) {
                                            patr += " " + f;
                                        }
                                    }
                                }
                            }

                            if (dr != null) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(dr);
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                dr = calendar.getTime();
                            }
                            try {
                                List<Children> children = childrenFacade.findByFioDr(fam, name, patr, dr);
                                child = children.get(0);
                            } catch (Exception ex) {
                            }
                        }
                        if (child != null) {
                            MonitoringEducond monitEducond = new MonitoringEducond();
                            monitEducond.setMonitoringId(monitoring);
                            ChildrenEducond childrenEducond = new ChildrenEducond();
                            childrenEducond.setChildId(child);

                            if (fieldZaklCol != 0) {    // предъявлено/нет заключение ПМПК
                                String zakl = "";
                                try {
                                    zakl = sheet.getCell(fieldZaklCol - 1, i).getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (zakl.equals("")) {
                                    childrenEducond.setChildreneducondZakl(null);
                                } else {
                                    zakl = zakl.toLowerCase();
                                    if (zakl.equals("да")) {
                                        childrenEducond.setChildreneducondZakl(1);
                                    } else {
                                        childrenEducond.setChildreneducondZakl(0);
                                    }
                                }
                            }

                            if (fieldOuCol != 0) {      // образовательная организация
                                String ou = "";
                                try {
                                    ou = sheet.getCell(fieldOuCol - 1, i).getContents().trim();
                                } catch (Exception ex) {
                                }

                                // пробуем найти образовательное учреждение
                                List<SprOu> ouList = new ArrayList<>();
                                List<SprOu> sprOuList = new ArrayList<>();

                                if (!ou.equals("")) {
                                    // заменяем ё на е
                                    ou = ou.toLowerCase().replaceAll("ё", "е");
                                    // убираем двойные пробелы
                                    ou = ou.replaceAll("  ", " ");

                                    // исключаем класс и группу из строки (если они там есть)
                                    int cl = -1;
                                    String stageN = "";
                                    int indx = ou.indexOf("класс".toLowerCase());
                                    if (indx > 0) {
                                        for (int j = indx; j >= 0; j--) {
                                            if (Character.isDigit(ou.charAt(j))) {
                                                stageN = ou.substring(j, j + 1) + stageN;
                                            } else if (!Character.isDigit(ou.charAt(j))) {
                                                if (!stageN.equals("")) {
                                                    cl = j;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    if (cl > 0) {
                                        ou = ou.substring(0, cl - 1);
                                    } else if (cl == 0) {
                                        ou = "";
                                    }

                                    int gr = -1;
                                    if (ou.contains("группа".toLowerCase())) {
                                        List<SprStage> stageList = sprStageFacade.findAllStages();
                                        for (SprStage s : stageList) {
                                            if (ou.contains(s.getSprstageName().toLowerCase())) {
                                                gr = ou.indexOf(s.getSprstageName().toLowerCase());
                                            }
                                        }
                                    }
                                    if (gr > 0) {
                                        ou = ou.substring(0, gr - 1);
                                    } else if (gr == 0) {
                                        ou = "";
                                    }

                                    SprOuType ouType = null;
                                    // проверяем тип учреждения по ключам
                                    for (KeyLevel pKey : parsedKeys) {
                                        if (pKey.test(ou)) {
                                            try {
                                                ouType = sprOuTypeFacade.findById(pKey.keyId);
                                            } catch (Exception ex) {
                                            }
                                        }
                                    }
                                    if (ouType != null) {
                                        ouList = sprOuFacade.findBySprOuTypeAndRegion(ouType, reg);
                                    }
                                    if (!ouList.isEmpty()) {
                                        for (SprOu o : ouList) {
                                            String ouName = o.getSprouName(); // характерная часть названия ОУ                                    
                                            Integer nom = o.getSprouNom();  // номер ОУ
                                            String dopKey = o.getSprouDopKey(); // дополнительный параметр в виде выражения по типу ключей
                                            Boolean flag = true;
                                            if (ouName != null) {
                                                flag = false;
                                                ouName = ouName.replaceAll("ё", "е");
                                                if (ou.contains(ouName.toLowerCase())) { // проверяем, есть ли часть названия в нашей строке
                                                    flag = true;
                                                }
                                            }

                                            if (nom != null) {   // проверяем есть ли такой номер в нашей строке (при этом исключаем класс)
                                                flag = false;
                                                if (!ou.equals("")) {
                                                    String ouNS = "";
                                                    for (int j = 0; j < ou.length(); j++) {
                                                        if (Character.isDigit(ou.charAt(j))) {
                                                            if (j == ou.length() - 1) {
                                                                ouNS = ouNS + ou.substring(j);
                                                            } else {
                                                                ouNS = ouNS + ou.substring(j, j + 1);
                                                            }
                                                        } else if (!Character.isDigit(ou.charAt(j))) {
                                                            if (!ouNS.equals("")) {
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (!ouNS.equals("")) {
                                                        Integer ouN = 0;
                                                        try {
                                                            ouN = Integer.parseInt(ouNS);
                                                        } catch (Exception ex) {
                                                        }
                                                        if (ouN.equals(nom)) {
                                                            flag = true;
                                                        }
                                                    }
                                                }
                                            }

                                            if (flag && dopKey != null) {
                                                flag = false;
                                                if (!dopKey.equals("")) {
                                                    KeyLevel l = parse(dopKey.toLowerCase());
                                                    if (l.test(ou)) {
                                                        flag = true;
                                                    }
                                                }
                                            }

                                            if (flag) {
                                                sprOuList.add(o);
                                            }
                                        }
                                    }
                                    if (sprOuList.isEmpty()) {   // если не найдено ни одно подходящее учреждение,
                                        // пробуем поискать без учёта района
                                        ouList = sprOuFacade.findBySprOuType(ouType);
                                        for (SprOu o : ouList) {
                                            String ouName = o.getSprouName(); // характерная часть названия ОУ                                    
                                            Integer nom = o.getSprouNom();  // номер ОУ
                                            String dopKey = o.getSprouDopKey(); // дополнительный параметр в виде выражения по типу ключей
                                            Boolean flag = false;
                                            if (ouName != null) {
                                                flag = false;
                                                ouName = ouName.replaceAll("ё", "е");
                                                if (ou.contains(ouName.toLowerCase())) { // проверяем, есть ли часть названия в нашей строке
                                                    flag = true;
                                                }
                                            }

                                            if (nom != null) {   // проверяем есть ли такой номер в нашей строке (при этом исключаем класс)
                                                flag = false;
                                                if (!ou.equals("")) {
                                                    String ouNS = "";
                                                    for (int j = 0; j < ou.length(); j++) {
                                                        if (Character.isDigit(ou.charAt(j))) {
                                                            if (j == ou.length() - 1) {
                                                                ouNS = ouNS + ou.substring(j);
                                                            } else {
                                                                ouNS = ouNS + ou.substring(j, j + 1);
                                                            }
                                                        } else if (!Character.isDigit(ou.charAt(j))) {
                                                            if (!ouNS.equals("")) {
                                                                break;
                                                            }
                                                        }
                                                    }
                                                    if (!ouNS.equals("")) {
                                                        Integer ouN = 0;
                                                        try {
                                                            ouN = Integer.parseInt(ouNS);
                                                        } catch (Exception ex) {
                                                        }
                                                        if (ouN.equals(nom)) {
                                                            flag = true;
                                                        }
                                                    }
                                                }
                                            }
                                            if ((ouName == null) && (nom == null)) {
                                                flag = true;
                                            }

                                            if (flag && dopKey != null) {
                                                flag = false;
                                                if (!dopKey.equals("")) {
                                                    KeyLevel l = parse(dopKey.toLowerCase());
                                                    if (l.test(ou)) {
                                                        flag = true;
                                                    }
                                                }
                                            }
                                            if (flag) {
                                                sprOuList.add(o);
                                            }
                                        }
                                    }

                                    if (sprOuList.size() == 1) {
                                        childrenEducond.setSprouId(sprOuList.get(0));
                                    }
                                }
                            }

                            if (fieldFormCol != 0) {    // форма обучения
                                String formobr = "";
                                try {
                                    formobr = sheet.getCell(fieldFormCol - 1, i).getContents().trim();
                                } catch (Exception ex) {
                                }
                                formobr = formobr.replaceAll("  ", " ");
                                if (!formobr.equals("")) {
                                    SprEduform sprEduform = null;
                                    try {
                                        sprEduform = sprEduformFacade.findByName(formobr);
                                    } catch (Exception ex) {
                                    }
                                    if (sprEduform != null) {
                                        childrenEducond.setSpreduformId(sprEduform);
                                    }
                                }
                            }

                            if (fieldStageCol != 0) {    // ступень обучения
                                String stage = "";
                                try {
                                    stage = sheet.getCell(fieldStageCol - 1, i).getContents().trim();
                                } catch (Exception ex) {
                                }
                                String stageN = "";
                                if (!stage.equals("")) {    // пробуем найти класс в строке
                                    int indx = stage.toUpperCase().indexOf("класс".toUpperCase());
                                    for (int j = indx; j >= 0; j--) {
                                        if (Character.isDigit(stage.charAt(j))) {
                                            stageN = stage.substring(j, j + 1) + stageN;
                                        } else if (!Character.isDigit(stage.charAt(j))) {
                                            if (!stageN.equals("")) {
                                                break;
                                            }
                                        }
                                    }

                                    SprStage st = null;
                                    if (!stageN.equals("")) {   // проверяем, есть ли такой класс в справочнике
                                        try {
                                            st = sprStageFacade.findByName(stageN + " класс");
                                        } catch (Exception ex) {

                                        }
                                    }
                                    if (st == null) {   // предполагаем, что у нас ДОУ, ищем группу
                                        if (stage.toUpperCase().contains("группа".toUpperCase())) {
                                            List<SprStage> stageList = sprStageFacade.findAllStages();
                                            for (SprStage s : stageList) {
                                                if (stage.toUpperCase().contains(s.getSprstageName().toUpperCase())) {
                                                    st = s;
                                                }
                                            }
                                        }
                                    }
                                    if (st != null) {
                                        childrenEducond.setSprstageId(st);
                                    }
                                }
                            }

                            if ((fieldOpCol != 0) && (fieldVarCol != 0)) {  // образовательная программа и вариант
                                String fullOp = "";
                                String op = "";
                                String var = "";
                                try {
                                    fullOp = sheet.getCell(fieldOpCol - 1, i).getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (fieldOpCol != fieldVarCol) {    // программа и вариант в разных столбцах
                                    try {
                                        op = sheet.getCell(fieldOpCol - 1, i).getContents().trim();
                                    } catch (Exception ex) {
                                    }
                                    try {
                                        var = sheet.getCell(fieldVarCol - 1, i).getContents().trim();
                                        var = var.replaceAll(" ", "");
                                        var = var.replaceAll("Пр.1599", "");
                                        var = var.replaceAll("Пр.№1599", "");
                                        var = var.replaceAll("ё", "е");
                                        var = var.replaceAll("[А-Яа-я]", "");
                                        var = var.replaceAll("-", "");
                                        var = var.replaceAll(",", "");
                                        if (var.indexOf(".") == 0) {
                                            var = var.substring(1);
                                        }
                                        if (!var.equals("")) {
                                            var = "Вариант " + var;
                                            if (!var.contains(".")) {

                                            } else if (var.lastIndexOf(".") < var.length() - 1) {
                                                var += ".";
                                            }
                                        }
                                    } catch (Exception ex) {
                                    }
                                } else if (!fullOp.equals("")) { // программа и вариант в одном столбце
                                    String[] opArray = fullOp.split("\\(");
                                    if (opArray.length > 1) {
                                        op = opArray[0];
                                        var = opArray[1].replaceAll("\\)", "");
                                        var = var.replaceAll(" ", "");
                                        var = var.replaceAll("Пр.1599", "");
                                        var = var.replaceAll("Пр.№1599", "");
                                        var = var.replaceAll("ё", "е");
                                        var = var.replaceAll("[А-Яа-я]", "");
                                        var = var.replaceAll("-", "");
                                        var = var.replaceAll(",", "");
                                        if (var.indexOf(".") == 0) {
                                            var = var.substring(1);
                                        }
                                        if (!var.equals("")) {
                                            var = "Вариант " + var;
                                            if (!var.contains(".")) {

                                            } else if (var.lastIndexOf(".") < var.length() - 1) {
                                                var += ".";
                                            }
                                        }
                                    } else if (opArray.length == 1) {
                                        op = opArray[0];
                                    }
                                }
                                // пробуем найти образовательную программу
                                List<SprObr> obrList = new ArrayList<>();

                                if (!op.equals("")) {
                                    SprObrVid obrVid = null;
                                    // проверяем вид образовательной программы по ключам
                                    for (KeyLevel pKey : parsedVidKeys) {
                                        if (pKey.test(op.toLowerCase())) {
                                            try {
                                                obrVid = sprObrVidFacade.findById(pKey.keyId);
                                            } catch (Exception ex) {
                                            }
                                        }
                                    }

                                    if (obrVid != null) {
                                        SprObrType obrType = null;
                                        // проверяем тип образовательной программы по ключам
                                        for (KeyLevel pKey : parsedTypeKeys) {
                                            if (pKey.test(op.toLowerCase())) {
                                                try {
                                                    obrType = sprObrTypeFacade.findBySprObrTypeId(pKey.keyId);
                                                } catch (Exception ex) {
                                                }
                                            }
                                        }
                                        if (obrType != null) {
                                            SprObrSpec obrSpec = null;
                                            // проверяем специализированность образовательной программы по ключам
                                            for (KeyLevel pKey : parsedSpecKeys) {
                                                if (pKey.test(op)) {
                                                    try {
                                                        obrSpec = sprObrSpecFacade.findById(pKey.keyId);
                                                    } catch (Exception ex) {
                                                    }
                                                }
                                            }
                                            obrList = sprObrFacade.findByTypeVidSpec(obrType, obrVid, obrSpec);
                                        }
                                    }
                                }

                                // пробуем найти вариант ещё раз
                                List<SprVar> varList = new ArrayList<>();
                                if (!var.equals("")) {
                                    varList = sprVarFacade.findByName(var);
                                }
                                if ((var.equals("")) || (varList.isEmpty())) { // дополнительная обработка, если не нашлось
                                    var = fullOp;
                                    if (!var.equals("")) {
                                        var = var.replaceAll(" ", "");
                                        var = var.replaceAll("Пр.1599", "");
                                        var = var.replaceAll("Пр.№1599", "");
                                        var = var.replaceAll("ё", "е");
                                        var = var.replaceAll("[А-Яа-я]", "");
                                        var = var.replaceAll("\\(", "");
                                        var = var.replaceAll("\\)", "");
                                        var = var.replaceAll(",", "");
                                        var = var.replaceAll("-", "");
                                        if (var.indexOf(".") == 0) {
                                            var = var.substring(1);
                                        }
                                        if (!var.equals("")) {
                                            var = "Вариант " + var;
                                            if (!var.contains(".")) {
                                            } else if (var.lastIndexOf(".") < var.length() - 1) {
                                                var += ".";
                                            }
                                        }
                                    }

                                    if (!var.equals("")) {
                                        varList = sprVarFacade.findByName(var);
                                    }
                                }

                                // вставляем образовательную программу в условия обучения
                                if (obrList.size() == 1) {
                                    childrenEducond.setSprobrId(obrList.get(0));
                                }
                                // вставляем вариант образовательной программы в условия обучения
                                if (varList.size() == 1) {
                                    childrenEducond.setSprvarId(varList.get(0));
                                }
                            }
                            childrenEducondFacade.create(childrenEducond);

                            // привязываем занятия и ассистента (рекомендации)                                
                            if (fieldLesCol != 0) { // занятия со специалистами
                                String les = "";
                                try {
                                    les = sheet.getCell(fieldLesCol - 1, i).getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!les.equals("нет") && !les.equals("") && !les.equals("-")) {
                                    List<SprRekomend> sprRekList = sprRekomendFacade.findAllRekomend();
                                    for (SprRekomend sprRekomend : sprRekList) {
                                        if (les.toLowerCase().contains(sprRekomend.getSprrekShortname())) {
                                            ChildrenEducondRek rek = new ChildrenEducondRek();
                                            rek.setChildreneducondId(childrenEducond);
                                            rek.setSprrekId(sprRekomend);
                                            childrenEducondRekFacade.create(rek);
                                        }
                                    }
                                }
                            }

                            if (fieldAsCol != 0) {  // ассистент, тьютор и т.д.
                                String assist = "";
                                try {
                                    assist = sheet.getCell(fieldAsCol - 1, i).getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!assist.equals("нет") && !assist.equals("") && !assist.equals("-")) {
                                    List<SprRekomend> sprRekList = sprRekomendFacade.findAllRekomend();
                                    for (SprRekomend sprRekomend : sprRekList) {
                                        if (assist.toLowerCase().contains(sprRekomend.getSprrekShortname())) {
                                            ChildrenEducondRek rek = new ChildrenEducondRek();
                                            rek.setChildreneducondId(childrenEducond);
                                            rek.setSprrekId(sprRekomend);
                                            childrenEducondRekFacade.create(rek);
                                        }
                                    }
                                }
                            }

                            if (fieldEqCol != 0) {  // специальное оборудование
                                String eq = "";
                                try {
                                    eq = sheet.getCell(fieldEqCol - 1, i).getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!eq.equals("нет") && !eq.equals("") && !eq.equals("-")) {
                                    List<SprEquip> sprEqList = sprEquipFacade.findAll();
                                    for (SprEquip sprEquip : sprEqList) {
                                        if (eq.toLowerCase().contains(sprEquip.getSprequipName())) {
                                            ChildrenEducondEq childrenEducondEq = new ChildrenEducondEq();
                                            childrenEducondEq.setChildreneducondId(childrenEducond);
                                            childrenEducondEq.setSprequipId(sprEquip);
                                            childrenEducondEqFacade.create(childrenEducondEq);
                                        }
                                    }
                                }
                            }
                            // привязываем условия обучения к мониторингу
                            monitEducond.setChildreneducondId(childrenEducond);
                            if (fieldReasCol != 0) {    // причина невыполнения рекомендаций
                                String reason = "";
                                try {
                                    reason = sheet.getCell(fieldReasCol - 1, i).getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!reason.equals("")) {
                                    monitEducond.setMoniteducondReason(reason);
                                }
                            }
                            monitoringEducondFacade.create(monitEducond);

                            // проверяем выполнение рекомендаций
                            // ищем ПМПК по дате из файла
                            if (fieldPmpkDCol != 0) {
                                String datePmpkS = "";
                                PriyomClient pr = null;
                                Pmpk pmpk = null;
                                try {
                                    datePmpkS = sheet.getCell(fieldPmpkDCol - 1, i).getContents().trim();
                                } catch (Exception ex) {
                                }
                                if (!datePmpkS.equals("")) {
                                    Date datePmpk = null;
                                    try {
                                        datePmpk = format2.parse(datePmpkS);
                                    } catch (Exception ex) {
                                    }
                                    try {
                                        datePmpk = format1.parse(datePmpkS);
                                    } catch (Exception ex) {
                                    }
                                    try {
                                        datePmpk = format3.parse(datePmpkS);
                                    } catch (Exception ex) {
                                    }
                                    if (datePmpk != null) {
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(datePmpk);
                                        calendar.set(Calendar.HOUR_OF_DAY, 0);
                                        calendar.set(Calendar.MINUTE, 0);
                                        calendar.set(Calendar.SECOND, 0);
                                        calendar.set(Calendar.MILLISECOND, 0);
                                        datePmpk = calendar.getTime();
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
                                        // смотрим, что рекомендовано ПМПК
                                        SprObr pmpkObr = pmpk.getSprobrId(); // образовательная программа
                                        SprObrVar pmpkVar = pmpk.getSprobrvarId(); // вариант образовательной программы
                                        List<PmpkRek> pmpkRekList = pmpkRekFacade.findByPmpk(pmpk); // рекомендации
                                        if (monitEducond.getChildreneducondId().getChildreneducondZakl() == null) {  // нет информации
                                            monitEducond.setMoniteducondExec(3);    // выполнение - "не установлено"
                                            monitEducond.setMoniteducondDetails("не установлено");
                                        } else if (monitEducond.getChildreneducondId().getChildreneducondZakl() == 0) {  // заключение не предоставлено
                                            monitEducond.setMoniteducondExec(0);    // выполнение - "не выполнено"
                                            if ((monitEducond.getMoniteducondReason() == null) || (monitEducond.getMoniteducondReason().equals(""))) {
                                                monitEducond.setMoniteducondReason("родители не предоставили заключение ПМПК");
                                            }
                                        } else if (monitEducond.getChildreneducondId().getChildreneducondZakl() == 1) { // заключение предоставлено
                                            monitEducond.setMoniteducondExecOp(0);
                                            monitEducond.setMoniteducondExecVar(0);
                                            monitEducond.setMoniteducondExecLess(1);
                                            monitEducond.setMoniteducondExecEq(1);
                                            monitEducond.setMoniteducondExecAs(1);
                                            // проверяем совпадает ли программа (или хотя бы направление)                                            
                                            if (pmpkObr != null) {
                                                if (monitEducond.getChildreneducondId().getSprobrId() != null) {
                                                    if (pmpkObr.equals(monitEducond.getChildreneducondId().getSprobrId())) {
                                                        monitEducond.setMoniteducondExecOp(1);
                                                    } else if (pmpkObr.getSprobrspecId() != null) {
                                                        if (pmpkObr.getSprobrspecId().equals(monitEducond.getChildreneducondId().getSprobrId().getSprobrspecId())) {
                                                            monitEducond.setMoniteducondExecOp(1);
                                                        }
                                                    }
                                                }
                                            } else {
                                                monitEducond.setMoniteducondExecOp(1);
                                            }
                                            // проверяем совпадает ли вариант программы                                            
                                            if ((pmpkVar != null) && (monitEducond.getChildreneducondId().getSprvarId() != null)) {
                                                if (pmpkVar.getSprobrvarName().equals(monitEducond.getChildreneducondId().getSprvarId().getSprvarName())) {
                                                    monitEducond.setMoniteducondExecVar(1);
                                                }
                                            } else if ((pmpkVar == null) && (monitEducond.getChildreneducondId().getSprvarId() == null)) {
                                                monitEducond.setMoniteducondExecVar(1);
                                            }
                                            // проверяем совпадают ли рекомендации
                                            List<ChildrenEducondRek> rekList = childrenEducondRekFacade.findByChildreneducond(childrenEducond);
                                            for (PmpkRek pmpkRek : pmpkRekList) {
                                                boolean flag = false;
                                                if (!pmpkRek.getSprrekId().getSprrekShortname().equals("оборуд")
                                                        && (!pmpkRek.getSprrekId().getSprrekShortname().equals("ассист"))
                                                        && (!pmpkRek.getSprrekId().getSprrekShortname().equals("тьют"))) {
                                                    for (ChildrenEducondRek rek : rekList) {
                                                        if (rek.getSprrekId().equals(pmpkRek.getSprrekId())) {
                                                            flag = true;
                                                        }
                                                    }
                                                    if (!flag) {
                                                        monitEducond.setMoniteducondExecLess(0);
                                                        break;
                                                    }
                                                }
                                            }
                                            // проверяем необходимость помощника или тьютора
                                            for (PmpkRek pmpkRek : pmpkRekList) {
                                                boolean flag = false;
                                                if ((pmpkRek.getSprrekId().getSprrekShortname().equals("ассист"))
                                                        || (pmpkRek.getSprrekId().getSprrekShortname().equals("тьют"))) {
                                                    for (ChildrenEducondRek rek : rekList) {
                                                        if (rek.getSprrekId().equals(pmpkRek.getSprrekId())) {
                                                            flag = true;
                                                        }
                                                    }
                                                    if (!flag) {
                                                        monitEducond.setMoniteducondExecAs(0);
                                                        break;
                                                    }
                                                }
                                            }
                                            // проверяем необходимость специального оборудования
                                            List<ChildrenEducondEq> eqList = childrenEducondEqFacade.findByChildreneducond(childrenEducond);
                                            for (PmpkRek pmpkRek : pmpkRekList) {
                                                if (pmpkRek.getSprrekId().getSprrekShortname().equals("оборуд")) {
                                                    monitEducond.setMoniteducondExecEq(0);
                                                    if (eqList.size() > 0) {
                                                        monitEducond.setMoniteducondExecEq(0);
                                                    }
                                                }
                                            }
                                            // делаем вывод о выполнении рекомендаций                                            
                                            if ((monitEducond.getMoniteducondExecOp() == 1) && (monitEducond.getMoniteducondExecVar() == 1)
                                                    && (monitEducond.getMoniteducondExecLess() == 1)
                                                    && (monitEducond.getMoniteducondExecAs() == 1) && (monitEducond.getMoniteducondExecEq() == 1)) {
                                                monitEducond.setMoniteducondExec(1);    // выполнение - "выполнено полностью"                                                
                                            } else if ((monitEducond.getMoniteducondExecOp() == 1) || (monitEducond.getMoniteducondExecVar() == 1)
                                                    || (monitEducond.getMoniteducondExecLess() == 1)
                                                    || (monitEducond.getMoniteducondExecAs() == 1) || (monitEducond.getMoniteducondExecEq() == 1)) {
                                                monitEducond.setMoniteducondExec(2);    // выполнение - "выполнено частично"
                                            } else if (((monitEducond.getMoniteducondExecOp() == 0) && (monitEducond.getMoniteducondExecVar() == 0)
                                                    && (monitEducond.getMoniteducondExecLess() == 0)
                                                    && (monitEducond.getMoniteducondExecAs() == 0) && (monitEducond.getMoniteducondExecEq() == 0))) {
                                                monitEducond.setMoniteducondExec(0);    // выполнение - "не выполнено"
                                            }
                                        }
                                    } else {
                                        monitEducond.setMoniteducondExec(3);    // выполнение - "не установлено"
                                        monitEducond.setMoniteducondDetails("нет такой даты ПМПК");
                                    }
                                } else {
                                    monitEducond.setMoniteducondExec(3);    // выполнение - "не установлено"
                                    monitEducond.setMoniteducondDetails("нет такой даты ПМПК");
                                }
                                monitoringEducondFacade.edit(monitEducond);
                            }
                        }
                    }

                }
                xlsfile.close();
                itsOk = true;
            } catch (BiffException ex) {
                Logger.getLogger(MonitoringServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (itsOk) {
                sb.append("<result>").append("1").append("</result>");
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

    protected KeyLevel parse(String key) {
        KeyLevel l = new KeyLevel();
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (c == "(".charAt(0)) {
                KeyLevel l1 = new KeyLevel();
                KeyElement e = new KeyElement();
                e.level = l1;
                l.elementList.add(e);
                l1.parentLevel = l;
                l = l1;
            } else if ((c != "|".charAt(0)) && (c != "+".charAt(0)) && (c != ")".charAt(0))) {
                // элемент, следующий за знаком скобки
                KeyElement e = new KeyElement();
                String elementString = "";
                for (int j = i; j < key.length(); j++) {
                    char c2 = key.charAt(j);
                    if (c2 == "!".charAt(0)) {
                        e.isInverted = true;
                    } else if ((c2 != "|".charAt(0)) && (c2 != "+".charAt(0))
                            && (c2 != "(".charAt(0)) && (c2 != ")".charAt(0))
                            && (c2 != "!".charAt(0))) {
                        elementString += c2;
                        e.elementString = elementString;
                    } else if ((c2 == "|".charAt(0)) || (c2 == "+".charAt(0)) || (c2 == ")".charAt(0))) {
                        e.elementString = elementString;
                        break;
                    } else if ((c2 == "(".charAt(0))) {
                        String lString = "";
                        j++;
                        char c3 = key.charAt(j++);
                        while (c3 != ")".charAt(0)) {
                            lString += c3;
                            c3 = key.charAt(j++);
                        }
                        KeyLevel l1 = parse(lString);
                        e.level = l1;
                        l.elementList.add(e);   // добавляем элемент в список элементов уровня
                        j--;
                    }
                    i = j;
                }
                l.elementList.add(e); // добавляем его в список элементов уровня
            } else if (c == ")".charAt(0)) {
                // переходим к родительскому уровню
                l = l.parentLevel;

            } else if ((c == "|".charAt(0)) || (c == "+".charAt(0))) {  // если встречаем символ сложения или умножения
                KeyOperation op = new KeyOperation();
                if (c == "|".charAt(0)) {
                    op.operation = KeyOperation.OR;
                } else if (c == "+".charAt(0)) {
                    op.operation = KeyOperation.AND;
                }
                // элемент перед знаком операции (последний обработанный элемент)
                // проверяем, есть ли он
                if (l.elementList.size() > 0) {
                    op.elementList.add(l.elementList.get(l.elementList.size() - 1));
                }
                // элемент, следующий за знаком операции
                if (i < key.length() - 1) {
                    i++;
                }
                KeyElement e = new KeyElement();
                String elementString = "";
                for (int j = i; j < key.length(); j++) {
                    char c2 = key.charAt(j);
                    if (c2 == "!".charAt(0)) {
                        e.isInverted = true;
                    } else if ((c2 != "|".charAt(0)) && (c2 != "+".charAt(0))
                            && (c2 != "(".charAt(0)) && (c2 != ")".charAt(0))
                            && (c2 != "!".charAt(0))) {
                        elementString += c2;
                        if ((j == key.length() - 1) || (key.charAt(j + 1) == ")".charAt(0))) {
                            e.elementString = elementString;
                            op.elementList.add(e);  // добавляем его в список элементов операции                        
                            l.elementList.add(e);   // добавляем элемент в список элементов уровня
                        }
                    } else if (c2 == c) {
                        if (e.level == null) {
                            e.elementString = elementString;
                            op.elementList.add(e);  // добавляем его в список элементов операции                        
                            l.elementList.add(e);   // добавляем элемент в список элементов уровня
                        }
                        e = new KeyElement();
                        elementString = "";
                    } else if (c2 == "(".charAt(0)) {
                        String lString = "";
                        j++;
                        char c3 = key.charAt(j++);
                        while (c3 != ")".charAt(0)) {
                            lString += c3;
                            c3 = key.charAt(j++);
                        }
                        KeyLevel l1 = parse(lString);
                        e.level = l1;
                        op.elementList.add(e);  // добавляем его в список элементов операции
                        l.elementList.add(e);   // добавляем элемент в список элементов уровня  
                        j--;
                    } else if (c2 == ")".charAt(0)) {
                        break;
                    }
                    i = j;
                }
                l.operationList.add(op); // добавляем операцию в список операций уровня
            }
        }
        return l;
    }
}
