/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.Ipra;
import Entities.Kalendar;
import Entities.Nom;
import Entities.SprRegion;
import Sessions.ChildrenFacade;
import Sessions.IpraFacade;
import Sessions.KalendarFacade;
import Sessions.NomFacade;
import Sessions.SprRegionFacade;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Aiglu
 */
@WebServlet(name = "IpraUploadServlet",
        loadOnStartup = 1,
        urlPatterns = "/ipraupload")
public class IpraUploadServlet extends HttpServlet {

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
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    IpraFacade ipraFacade = new IpraFacade();
    @EJB
    KalendarFacade kalendarFacade = new KalendarFacade();
    @EJB
    NomFacade nomFacade = new NomFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String fileName = request.getParameter("filename");
        String path = "reestr//" + fileName;
        File file = new File(path);
        String id = request.getParameter("id");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd.MM.yyyy");
        StringBuilder sb = new StringBuilder();
        if (id.equals("upload")) {
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
                        sb.append(i + 1).append("-");
                        Cell[] row = sheet.getRow(i);
                        if (row.length > 0) {
                            Children child = null;
                            String fio = "";
                            try {
                                fio = row[1].getContents().trim();
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
                                    drS = row[2].getContents().trim();
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
                                    try {
                                        findChild = childrenFacade.findByFioDr(fam, name, patr, dr);
                                    } catch (Exception ex) {
                                    }
                                    if (findChild.size() > 0) {
                                        child = findChild.get(0);
                                    } else {
                                        Nom nom = new Nom();
                                        nomFacade.create(nom);
                                        child = new Children();
                                        child.setChildFam(fam);
                                        child.setChildName(name);
                                        child.setChildPatr(patr);
                                        child.setChildNom(nom.getNom());
                                        child.setChildDr(dr);
                                        String regS = null;
                                        try {
                                            regS = row[5].getContents().trim();
                                        } catch (Exception ex) {
                                        }
                                        SprRegion reg = null;
                                        if (regS != null) {
                                            List<SprRegion> regList = sprRegionFacade.findByName(regS);
                                            if (!(regList.isEmpty())) {
                                                reg = regList.get(0);
                                            }
                                        }
                                        if (reg != null) {
                                            child.setSprregId(reg);
                                        }
                                        childrenFacade.create(child);
                                    }
                                } else {
                                    sb.append("нет ДР,");
                                }
                            }

                            if (child != null) {
                                Ipra ipra = new Ipra();
                                ipra.setChildId(child);
                                // информация МСЭ
                                String exp = row[3].getContents().trim();
                                if (!exp.equals("")) {
                                    exp = exp.replaceAll("\\n", "");
                                    exp = exp.replaceAll(" ", "");
                                    String[] exps = exp.split("№");
                                    String ipraN = "";
                                    String expN = "";
                                    String expDS = "";
                                    if (exps.length >= 3) {
                                        ipraN = exps[1];
                                        String[] exps2 = exps[2].split("от");
                                        if (exps2.length >= 2) {
                                            expN = exps2[0];
                                            expDS = exps2[1];
                                        }
                                    }
                                    if (exps.length == 2) {
                                        String[] exps2 = exps[1].split("от");
                                        if (exps2.length >= 2) {
                                            ipraN = exps2[0];
                                            expDS = exps2[1];
                                        }
                                    }
                                    if (ipraN.equals("") || expDS.equals("")) {
                                        sb.append("неверный формат поля экспертизы");
                                    }
                                    Date expD = null;
                                    try {
                                        expD = simpleDateFormat2.parse(expDS);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(IpraUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    ipra.setIpraN(ipraN);
                                    ipra.setIpraNexp(expN);
                                    if (expD != null) {
                                        ipra.setIpraDateexp(expD);
                                    }
                                }

                                // дата окончания ИПРА
                                String dateOkS = row[4].getContents().trim();
                                if (!dateOkS.equals("")) {
                                    Date dateOk = null;
                                    try {
                                        dateOk = simpleDateFormat.parse(dateOkS);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(IpraUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if (dateOk != null) {
                                        ipra.setIpraDateok(dateOk);
                                    } else {
                                        sb.append("неверный формат поля дата окончания ИПРА,");
                                    }
                                }
                                // исх.номер и дата письма МСЭ
                                String ishMse = row[6].getContents().trim();
                                if (!ishMse.equals("")) {
                                    ishMse = ishMse.replaceAll("\\n", "");
                                    ishMse = ishMse.replaceAll("№", "");
                                    ishMse = ishMse.replaceAll(" ", "");
                                    String[] ishMseM = ishMse.split("от");
                                    String ishMseN = "";
                                    String ishMseDS = "";
                                    try {
                                        ishMseN = ishMseM[0];
                                        ishMseDS = ishMseM[1];
                                    } catch (Exception ex) {
                                    }
                                    if (ishMseN.equals("") || ishMseDS.equals("")) {
                                        sb.append("неверный формат поля с исх.письмом МСЭ,");
                                    }
                                    ipra.setIpraIshmseN(ishMseN);
                                    Date ishMseD = null;
                                    try {
                                        ishMseD = simpleDateFormat2.parse(ishMseDS);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(IpraUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if (ishMseD != null) {
                                        ipra.setIpraIshmseD(ishMseD);
                                    }
                                }

                                // вх.номер и дата в ДО
                                String vhDo = row[7].getContents().trim();
                                if (!vhDo.equals("")) {
                                    vhDo = vhDo.replaceAll("\\n", "");
                                    vhDo = vhDo.replaceAll("№", "");
                                    vhDo = vhDo.replaceAll(" ", "");
                                    String[] vhDoM = vhDo.split("от");
                                    String vhDoN = "";
                                    String vhDoDS = "";
                                    try {
                                        vhDoN = vhDoM[0];
                                        vhDoDS = vhDoM[1];
                                    } catch (Exception ex) {
                                    }
                                    if (vhDoN.equals("") || vhDoDS.equals("")) {
                                        sb.append("неверный формат поля с вх.письмом в ДО,");
                                    }
                                    ipra.setIpraVhdoN(vhDoN);
                                    Date vhDoD = null;
                                    try {
                                        vhDoD = simpleDateFormat2.parse(vhDoDS);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(IpraUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if (vhDoD != null) {
                                        ipra.setIpraVhdoD(vhDoD);
                                    }
                                }

                                // номер и дата приказа ДО
                                String prDo = row[8].getContents().trim();
                                if (!prDo.equals("")) {
                                    prDo = prDo.replaceAll("\\n", "");
                                    prDo = prDo.replaceAll("№", "");
                                    prDo = prDo.replaceAll(" ", "");
                                    String[] prDoM = prDo.split("от");
                                    String prDoN = "";
                                    String prDoDS = "";
                                    try {
                                        prDoN = prDoM[0];
                                        prDoDS = prDoM[1];
                                    } catch (Exception ex) {
                                    }
                                    if (prDoN.equals("") || prDoDS.equals("")) {
                                        sb.append("неверный формат поля с приказом ДО,");
                                    }
                                    ipra.setIpraPrikazN(prDoN);
                                    Date prDoD = null;
                                    try {
                                        prDoD = simpleDateFormat2.parse(prDoDS);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(IpraUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if (prDoD != null) {
                                        ipra.setIpraPrikazD(prDoD);
                                    }
                                }
                                // номер и дата запроса ТПМПК
                                String tpmpk = row[9].getContents().trim();
                                if (!tpmpk.equals("")) {
                                    tpmpk = tpmpk.replaceAll("\\n", "");
                                    tpmpk = tpmpk.replaceAll("№", "");
                                    tpmpk = tpmpk.replaceAll(" ", "");
                                    String[] tpmpkM = tpmpk.split("от");
                                    String tpmpkN = "";
                                    String tpmpkDS = "";
                                    try {
                                        tpmpkN = tpmpkM[0];
                                        tpmpkDS = tpmpkM[1];
                                    } catch (Exception ex) {
                                    }
                                    if (tpmpkN.equals("") || tpmpkDS.equals("")) {
                                        sb.append("неверный формат поля с запросом в ТПМПК,");
                                    }
                                    Date tpmpkD = null;
                                    try {
                                        tpmpkD = simpleDateFormat2.parse(tpmpkDS);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(IpraUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if (tpmpkD != null) {
                                        ipra.setIpraTpmpkD(tpmpkD);
                                    }
                                }

                                // номер и дата письма с перечнем
                                String perech = row[10].getContents().trim();
                                if (!perech.equals("")) {
                                    perech = perech.replaceAll("\\n", "");
                                    perech = perech.replaceAll("№", "");
                                    perech = perech.replaceAll(" ", "");
                                    String[] perechM = perech.split("от");
                                    String perechN = "";
                                    String perechDS = "";
                                    try {
                                        perechN = perechM[0];
                                        perechDS = perechM[1];
                                    } catch (Exception ex) {
                                    }
                                    if (perechN.equals("") || perechDS.equals("")) {
                                        sb.append("неверный формат поля с перечнем,");
                                    }
                                    ipra.setIpraOmsuN(perechN);
                                    Date perechD = null;
                                    try {
                                        perechD = simpleDateFormat2.parse(perechDS);
                                    } catch (ParseException ex) {
                                        Logger.getLogger(IpraUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if (perechD != null) {
                                        ipra.setIpraPerechD(perechD);
                                    }

                                }

                                // дата отчета ОЦППМСП
                                String dateOtchCenterS = null;
                                try {
                                    dateOtchCenterS = row[11].getContents().trim();
                                } catch (Exception ex) {

                                }
                                if (dateOtchCenterS != null) {
                                    if (!dateOtchCenterS.equals("")) {
                                        Date dateOtchCenter = null;
                                        try {
                                            dateOtchCenter = simpleDateFormat.parse(dateOtchCenterS);
                                        } catch (ParseException ex) {
                                            Logger.getLogger(IpraUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        if (dateOtchCenter != null) {
                                            ipra.setIpraOtchcenter(dateOtchCenter);
                                        } else {
                                            sb.append("неверный формат поля дата отчета ОЦППМСП");
                                        }
                                    }
                                }

                                if (ipra.getIpraDateok() != null) {
                                    Date dateOtchCenter = countDate(40, "days", "back", ipra.getIpraDateok());
                                    ipra.setIpraOtchcenter(dateOtchCenter);
                                    Date dateOtchDO = countDate(1, "month", "back", ipra.getIpraDateok());
                                    ipra.setIpraOtchdo(dateOtchDO);
                                    Date dateOtchOmsu = countDate(50, "days", "back", ipra.getIpraDateok());
                                    ipra.setIpraOtchomsu(dateOtchOmsu);
                                }

                                // статус
                                String status = null;
                                try {
                                    status = row[13].getContents().trim();
                                } catch (Exception ex) {
                                    ipra.setIpraStatus(1);
                                }
                                if (status != null) {
                                    if (!status.equals("")) {
                                        if (status.equals("закрыто")) {
                                            ipra.setIpraStatus(0);
                                        } else {
                                            ipra.setIpraStatus(1);
                                        }
                                    } else {
                                        ipra.setIpraStatus(1);
                                    }
                                }
                                try {
                                    ipraFacade.create(ipra);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                            sb.append("|");
                        }
                    }
                }
            } catch (BiffException ex) {
                Logger.getLogger(IpraUploadServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                session.removeAttribute("result");
            } catch (Exception ex) {
            }
            session.setAttribute("result", sb.toString());

            String userPath = "/ipra/reestruploadresult";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
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

    public Date countDate(Integer period, String type, String direct, Date date) {
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
}
