/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.Family;
import Entities.Ipra18N;
import Entities.LoginLog;
import Entities.Parents;
import Entities.PayDogovor;
import Entities.Ped;
import Entities.Pmpk;
import Entities.Sotrud;
import Entities.SotrudDolgn;
import Entities.SprDolgn;
import Entities.SprParentType;
import Entities.SprRegion;
import Entities.Users;
import Other.Ipra18NBaseComparator;
import Other.UsersComparator;
import Sessions.ChildrenFacade;
import Sessions.FamilyFacade;
import Sessions.Ipra18NFacade;
import Sessions.LoginLogFacade;
import Sessions.ParentsFacade;
import Sessions.PayDogovorFacade;
import Sessions.PedFacade;
import Sessions.PmpkFacade;
import Sessions.SotrudDolgnFacade;
import Sessions.SotrudFacade;
import Sessions.SprDolgnFacade;
import Sessions.SprParentTypeFacade;
import Sessions.SprRegionFacade;
import Sessions.UsersFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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

/**
 *
 * @author Aiglu сервлет для поиска по БД
 */
@WebServlet(name = "SearchServlet",
        loadOnStartup = 1,
        urlPatterns = "/search")

public class SearchServlet extends HttpServlet {

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
    ParentsFacade parentsFacade = new ParentsFacade();
    @EJB
    PedFacade pedFacade = new PedFacade();
    @EJB
    PayDogovorFacade payDogovorFacade = new PayDogovorFacade();
    @EJB
    FamilyFacade familyFacade = new FamilyFacade();
    @EJB
    SprDolgnFacade sprDolgnFacade = new SprDolgnFacade();
    @EJB
    SotrudFacade sotrudFacade = new SotrudFacade();
    @EJB
    UsersFacade usersFacade = new UsersFacade();
    @EJB
    SotrudDolgnFacade sotrudDolgnFacade = new SotrudDolgnFacade();
    @EJB
    PmpkFacade pmpkFacade = new PmpkFacade();
    @EJB
    SprParentTypeFacade sprParentTypeFacade = new SprParentTypeFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    Ipra18NFacade ipra18NFacade = new Ipra18NFacade();

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
        // логирование времени последнего действия пользователя
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

        String type = request.getParameter("type"); // тип поиска
        StringBuilder sb = new StringBuilder(); // буфер для xml
        boolean itsOk = false;  // признак готовности результата к отправке
        // форматы для дат
        SimpleDateFormat regularDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (type.equals("client")) {
            // получаем параметры поискового запроса
            String katCl = request.getParameter("kat");
            String famCl = request.getParameter("fam");
            String namCl = request.getParameter("nam");
            String patrCl = request.getParameter("patr");
            // приводим их к нужному виду (обрезаем пробелы)
            if (famCl != null) {
                famCl = famCl.trim();
            } else {
                famCl = "";
            }
            if (namCl != null) {
                namCl = namCl.trim();
            } else {
                namCl = "";
            }
            if (patrCl != null) {
                patrCl = patrCl.trim();
            } else {
                patrCl = "";
            }
            if (katCl.equals("children")) {
                List<Children> children = childrenFacade.search(famCl, namCl, patrCl);
                sb.append("<clients>");
                // для заголовка таблицы
                sb.append("<client>");
                sb.append("<id>").append("ИД").append("</id>");
                sb.append("<nom>").append("Номер").append("</nom>");
                sb.append("<fam>").append("Фамилия").append("</fam>");
                sb.append("<name>").append("Имя").append("</name>");
                sb.append("<patr>").append("Отчество").append("</patr>");
                sb.append("<dr>").append("Дата рождения").append("</dr>");
                sb.append("<reg>").append("Район проживания").append("</reg>");
                //    sb.append("<age>").append("Возраст").append("</age>");
                sb.append("</client>");
                for (Children child : children) {
                    sb.append("<client>");
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
                        dr = regularDateFormat.format(child.getChildDr());
                    } catch (Exception ex) {
                    }
                    sb.append("<dr>").append(dr).append("</dr>");
                    sb.append("<reg>").append(child.getSprregId().getSprregName()).append("</reg>");
                    //    sb.append("<age>").append(child.getAge()).append("</age>");
                    sb.append("</client>");
                }
                sb.append("</clients>");
                itsOk = true;

            } else if (katCl.equals("parents")) {
                List<Parents> parents = parentsFacade.search(famCl, namCl, patrCl);
                sb.append("<clients>");
                sb.append("<client>");
                sb.append("<id>").append("ИД").append("</id>");
                sb.append("<nom>").append("Номер").append("</nom>");
                sb.append("<fam>").append("Фамлия").append("</fam>");
                sb.append("<name>").append("Имя").append("</name>");
                sb.append("<patr>").append("Отчество").append("</patr>");
                sb.append("<reg>").append("Район проживания").append("</reg>");
                sb.append("</client>");

                for (Parents parent : parents) {
                    sb.append("<client>");
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
                    sb.append("</client>");
                }
                sb.append("</clients>");
                itsOk = true;

            } else if (katCl.equals("ped")) {
                List<Ped> peds = pedFacade.search(famCl, namCl, patrCl);
                sb.append("<clients>");
                sb.append("<client>");
                sb.append("<id>").append("ИД").append("</id>");
                sb.append("<nom>").append("Номер").append("</nom>");
                sb.append("<fam>").append("Фамилия").append("</fam>");
                sb.append("<name>").append("Имя").append("</name>");
                sb.append("<patr>").append("Отчество").append("</patr>");
                sb.append("<org>").append("Организация").append("</org>");
                sb.append("<dol>").append("Должность").append("</dol>");
                sb.append("<reg>").append("Район проживания").append("</reg>");
                sb.append("</client>");
                for (Ped ped : peds) {
                    sb.append("<client>");
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
                    sb.append("</client>");
                }
                sb.append("</clients>");
                itsOk = true;
            }
        } else if (type.equals("paydog")) {
            String nom = request.getParameter("nom");
            String dateS = request.getParameter("date");
            String fam = request.getParameter("fam");
            String name = request.getParameter("name");
            String patr = request.getParameter("patr");
            Date date = null;
            try {
                date = inputDateFormat.parse(dateS);
            } catch (ParseException ex) {
                Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            List<PayDogovor> payDogovors = payDogovorFacade.findBySearch(nom, date, fam, name, patr);
            sb.append("<dogovors>");
            sb.append("<dogovor>");
            sb.append("<id>").append("ИД").append("</id>");
            sb.append("<fam>").append("Фамилия").append("</fam>");
            sb.append("<name>").append("Имя").append("</name>");
            sb.append("<patr>").append("Отчество").append("</patr>");
            sb.append("<nom>").append("Номер договора").append("</nom>");
            sb.append("<date>").append("Дата договора").append("</date>");
            sb.append("</dogovor>");
            for (PayDogovor pd : payDogovors) {
                sb.append("<dogovor>");
                sb.append("<id>").append(pd.getPaydogId()).append("</id>");
                sb.append("<fam>").append(pd.getParentId().getParentFam()).append("</fam>");
                sb.append("<name>").append(pd.getParentId().getParentName()).append("</name>");
                String patrS = pd.getParentId().getParentPatr();
                if (patrS == null) {
                    patrS = " ";
                }
                sb.append("<patr>").append(patrS).append("</patr>");
                sb.append("<nom>").append(pd.getPaydogN()).append("</nom>");
                sb.append("<date>").append(regularDateFormat.format(pd.getPaydogD())).append("</date>");
                sb.append("</dogovor>");
            }
            sb.append("</dogovors>");
            itsOk = true;
        } else if (type.equals("findfamily")) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String selKat = request.getParameter("kat");
            String req = request.getParameter("req");
            String[] clients = req.split(";");
            List<Family> familyList = new ArrayList<>();
            for (int i = 0; i < clients.length; i++) {
                String[] client = clients[i].split("-");
                if (client.length == 2) {
                    String kat = client[0];
                    String idS = client[1];
                    Integer id = 0;
                    try {
                        id = Integer.parseInt(idS);
                    } catch (Exception ex) {
                    }
                    if (id != 0) {
                        List<Family> family = familyFacade.findByClient(kat, id);
                        for (Family f : family) {
                            List<Family> familyNomList = familyFacade.findByFamNom(f.getFamNom());
                            for (Family famNom : familyNomList) {
                                if ((famNom.getClientId().equals(id)) && (famNom.getFamKatcl().equals(kat))) {
                                } else if (famNom.getFamKatcl().equals(selKat)) {
                                    familyList.add(famNom);
                                }
                            }
                        }
                    }
                }
            }
            if (!familyList.isEmpty()) {
                Set<Family> familySet = new LinkedHashSet<>(familyList);
                sb.append("<family>");
                sb.append("<member>");
                sb.append("<id>").append(" ").append("</id>");
                sb.append("<nom>").append("Номер").append("</nom>");
                sb.append("<fam>").append("Фамилия").append("</fam>");
                sb.append("<name>").append("Имя").append("</name>");
                sb.append("<patr>").append("Отчество").append("</patr>");
                sb.append("<dr>").append("Дата рождения").append("</dr>");
                sb.append("<reg>").append("Район").append("</reg>");
                sb.append("</member>");
                for (Family f : familySet) {
                    String k = f.getFamKatcl();
                    sb.append("<member>");
                    if (k.equals("children")) {
                        Children child = childrenFacade.findById(f.getClientId());
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
                    } else if (k.equals("parents")) {
                        Parents parent = parentsFacade.findById(f.getClientId());
                        sb.append("<id>").append(parent.getParentId()).append("</id>");
                        sb.append("<nom>").append(parent.getParentNom()).append("</nom>");
                        sb.append("<fam>").append(parent.getParentFam()).append("</fam>");
                        sb.append("<name>").append(parent.getParentName()).append("</name>");
                        String patrS = parent.getParentPatr();
                        if (patrS == null) {
                            patrS = " ";
                        }
                        sb.append("<patr>").append(patrS).append("</patr>");
                        sb.append("<dr>").append(" ").append("</dr>");
                        sb.append("<reg>").append(parent.getSprregId().getSprregName()).append("</reg>");
                    }
                    sb.append("</member>");
                }
                sb.append("</family>");
                itsOk = true;
            }

        } else if (type.equals(("clentbynom"))) {
            String nom = request.getParameter("nom");
            String kat = request.getParameter("kat");
            Integer n = 0;
            if (!nom.equals("")) {
                n = Integer.parseInt(nom);
            }
            if (kat.equals("children")) {
                sb.append("<clients>");
                // для заголовка таблицы
                sb.append("<client>");
                sb.append("<id>").append("ИД").append("</id>");
                sb.append("<nom>").append("Номер").append("</nom>");
                sb.append("<fam>").append("Фамилия").append("</fam>");
                sb.append("<name>").append("Имя").append("</name>");
                sb.append("<patr>").append("Отчество").append("</patr>");
                sb.append("<dr>").append("Дата рождения").append("</dr>");
                sb.append("<reg>").append("Район проживания").append("</reg>");
                sb.append("</client>");
                if (n == 0) {
                    List<Children> children = childrenFacade.findAllChildren();
                    for (Children child : children) {
                        sb.append("<client>");
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
                            dr = regularDateFormat.format(child.getChildDr());
                        } catch (Exception ex) {
                        }
                        sb.append("<dr>").append(dr).append("</dr>");
                        sb.append("<reg>").append(child.getSprregId().getSprregName()).append("</reg>");
                        sb.append("</client>");
                    }
                    sb.append("</clients>");
                    itsOk = true;
                } else {
                    Children child = null;
                    try {
                        child = childrenFacade.findByNom(n);
                    } catch (Exception ex) {
                    }
                    if (child != null) {
                        sb.append("<client>");
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
                            dr = regularDateFormat.format(child.getChildDr());
                        } catch (Exception ex) {
                        }
                        sb.append("<dr>").append(dr).append("</dr>");
                        sb.append("<reg>").append(child.getSprregId().getSprregName()).append("</reg>");
                        sb.append("</client>");
                        sb.append("</clients>");
                        itsOk = true;
                    }
                }
            } else if (kat.equals("parents")) {
                sb.append("<clients>");
                sb.append("<client>");
                sb.append("<id>").append("ИД").append("</id>");
                sb.append("<nom>").append("Номер").append("</nom>");
                sb.append("<fam>").append("Фамлия").append("</fam>");
                sb.append("<name>").append("Имя").append("</name>");
                sb.append("<patr>").append("Отчество").append("</patr>");
                sb.append("<reg>").append("Район проживания").append("</reg>");
                sb.append("</client>");

                if (n == 0) {
                    List<Parents> parents = parentsFacade.findAllParents();
                    for (Parents parent : parents) {
                        sb.append("<client>");
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
                        sb.append("</client>");
                    }
                    sb.append("</clients>");
                    itsOk = true;
                } else {
                    Parents parent = null;
                    try {
                        parent = parentsFacade.findByNom(n);
                    } catch (Exception ex) {
                    }
                    if (parent != null) {
                        sb.append("<client>");
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
                        sb.append("</client>");
                        sb.append("</clients>");
                        itsOk = true;
                    }
                }

            } else if (kat.equals("ped")) {
                if (n == 0) {
                    List<Ped> peds = pedFacade.findAllPed();
                    sb.append("<clients>");
                    sb.append("<client>");
                    sb.append("<id>").append("ИД").append("</id>");
                    sb.append("<nom>").append("Номер").append("</nom>");
                    sb.append("<fam>").append("Фамилия").append("</fam>");
                    sb.append("<name>").append("Имя").append("</name>");
                    sb.append("<patr>").append("Отчество").append("</patr>");
                    sb.append("<org>").append("Организация").append("</org>");
                    sb.append("<dol>").append("Должность").append("</dol>");
                    sb.append("<reg>").append("Район проживания").append("</reg>");
                    sb.append("</client>");
                    for (Ped ped : peds) {
                        sb.append("<client>");
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
                        sb.append("</client>");
                    }
                    sb.append("</clients>");
                    itsOk = true;
                } else {
                    Ped ped = null;
                    try {
                        ped = pedFacade.findByNom(n);
                    } catch (Exception ex) {
                    }
                    if (ped != null) {
                        sb.append("<client>");
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
                        sb.append("</client>");
                        sb.append("</clients>");
                        itsOk = true;
                    }
                }
            }
        } else if (type.equals("sotrud")) {
            String dolgnIdS = request.getParameter("dolgn");
            String fam = request.getParameter("fam");
            fam = fam.trim();
            Integer dolgnId = 0;
            try {
                dolgnId = Integer.parseInt(dolgnIdS);
            } catch (Exception ex) {
            }
            SprDolgn dolgn = null;
            try {
                dolgn = sprDolgnFacade.findById(dolgnId);
            } catch (Exception ex) {
            }
            List<Sotrud> sotrud = new ArrayList<>();
            if ((dolgn != null) && (fam.equals(""))) {
                sotrud = sotrudFacade.findByDolgnAct(dolgn);
            } else if ((dolgn == null) && (fam.equals(""))) {
                sotrud = sotrudFacade.findAllSotrud();
            } else if ((dolgn != null) && (!fam.equals(""))) {
                sotrud = sotrudFacade.findByDolgnFamAct(dolgn, fam);
            } else if ((dolgn == null) && (!fam.equals(""))) {
                sotrud = sotrudFacade.findByFam(fam);
            }

            if (sotrud != null) {
                sb.append("<sotruds>");
                sb.append("<sotrud>");
                sb.append("<id>").append("ИД").append("</id>");
                sb.append("<fam>").append("Фамилия").append("</fam>");
                sb.append("<name>").append("Имя").append("</name>");
                sb.append("<patr>").append("Отчество").append("</patr>");
                sb.append("<dolgn>").append("Должность").append("</dolgn>");
                sb.append("<action>").append(" ").append("</action>");
                sb.append("</sotrud>");
                for (Sotrud s : sotrud) {
                    List<SotrudDolgn> sotrDolgn = sotrudDolgnFacade.findBySotrudAct(s);
                    sb.append("<sotrud>");
                    sb.append("<id>").append(s.getSotrudId().toString()).append("</id>");
                    sb.append("<fam>").append(s.getSotrudFam()).append("</fam>");
                    sb.append("<name>").append(s.getSotrudName()).append("</name>");
                    sb.append("<patr>").append(s.getSotrudPatr()).append("</patr>");
                    String d = " ";
                    for (SotrudDolgn sd : sotrDolgn) {
                        if (!d.equals(" ")) {
                            d += ", ";
                        }
                        d += sd.getSprdolgnId().getSprdolgnName();
                    }
                    sb.append("<dolgn>").append(d).append("</dolgn>");
                    sb.append("<action>").append(s.getSotrudActive().toString()).append("</action>");
                    sb.append("</sotrud>");
                }
                sb.append("</sotruds>");
                itsOk = true;
            }
        } else if (type.equals("users")) {
            List<Users> users = usersFacade.findAll();
            Collections.sort(users, new UsersComparator());
            if (users.size() > 0) {
                sb.append("<users>");
                sb.append("<user>");
                sb.append("<id>").append("ИД").append("</id>");
                sb.append("<name>").append("Имя пользователя").append("</name>");
                sb.append("<sotr>").append("ФИО сотрудника").append("</sotr>");
                sb.append("<act>").append("Ак").append("</act>");
                sb.append("</user>");
                for (Users u : users) {
                    sb.append("<user>");
                    sb.append("<id>").append(u.getUserId().toString()).append("</id>");
                    sb.append("<name>").append(u.getUserName()).append("</name>");
                    if (u.getSotrudId() != null) {
                        sb.append("<sotr>").append(u.getSotrudId().getSotrudFIO()).append("</sotr>");
                    } else {
                        sb.append("<sotr>").append(" ").append("</sotr>");
                    }
                    sb.append("<act>").append(u.getUserActive().toString()).append("</act>");
                    sb.append("</user>");
                }
                sb.append("</users>");
                itsOk = true;
            }
        } else if (type.equals("parenttype")) {
            List<SprParentType> sprParentTypes = sprParentTypeFacade.findAllSprParentType();
            String n = request.getParameter("n");
            String typeCl = request.getParameter("typecl");
            if (!sprParentTypes.isEmpty()) {
                sb.append("<parenttypes>");
                for (SprParentType pt : sprParentTypes) {
                    sb.append("<parenttype>");
                    sb.append("<type>").append(typeCl).append("</type>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<id>").append(pt.getSprparenttypeId()).append("</id>");
                    sb.append("<name>").append(pt.getSprparenttypeName()).append("</name>");
                    sb.append("</parenttype>");
                }
                sb.append("</parenttypes>");
                itsOk = true;
            }
        } else if (type.equals("ipra")) {    // поиск ИПРА
            // параметры поискового запроса
            String fam = request.getParameter("fam");   // фамилия
            String name = request.getParameter("name"); // имя
            String patr = request.getParameter("patr"); // отчество
            String reg = request.getParameter("reg");   // район (ид в текстовом виде)
            String dpr = request.getParameter("dpr");   // дата приказа (текст в формате yyyy-mm-dd)
            String npr = request.getParameter("npr");   // номер приказа
            // если поле не null - обрезаем пробелы, если null - присваиваем пустую строку (для корректного поиска)
            if (fam != null) {
                fam = fam.trim();
            } else {
                fam = "";
            }
            if (name != null) {
                name = name.trim();
            } else {
                name = "";
            }
            if (patr != null) {
                patr = patr.trim();
            } else {
                patr = "";
            }
            if (npr != null) {
                npr = npr.trim();
            } else {
                npr = "";
            }
            // переводим ИД района в число и ищем соответствующий район в справочнике
            Integer regId = 0;
            try {
                regId = Integer.parseInt(reg);
            } catch (Exception e) {
            }
            SprRegion sprReg = null;
            if (regId > 0) {
                sprReg = sprRegionFacade.findById(regId);
            }
            // переводим дату приказа из текстового формата в дату
            Date dprDate = null;
            try {
                dprDate = inputDateFormat.parse(dpr);
            } catch (ParseException ex) {
                Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            // статус ИПРА 1 - в работе, 0 - в архиве
            Integer status = 1;

            // ищем ИПРА по заданным параметрам            
            List<Ipra18N> ipraList = ipra18NFacade.findBySearch(fam, name, patr, sprReg, npr, dprDate, status);
            // сортируем
            Collections.sort(ipraList, new Ipra18NBaseComparator());
            
            // составляем xml для передачи клиенту
            // заголовок таблицы
            sb.append("<ipras>");
            sb.append("<ipra>");
            sb.append("<id>").append("ИД").append("</id>");
            sb.append("<fam>").append("Фамилия").append("</fam>");
            sb.append("<name>").append("Имя").append("</name>");
            sb.append("<patr>").append("Отчество").append("</patr>");
            sb.append("<dr>").append("Дата рождения").append("</dr>");
            sb.append("<reg>").append("Район").append("</reg>");
            sb.append("<nipra>").append("№ ИПРА").append("</nipra>");
            sb.append("<otchomsu>").append("Дата отчёта ОМСУ").append("</otchomsu>");
            sb.append("<otchcenter>").append("Дата отчёта ОЦППМСП").append("</otchcenter>");
            sb.append("<otchdo>").append("Дата отчёта ДО").append("</otchdo>");
            sb.append("</ipra>");
            // содерждимое таблицы
            if (!ipraList.isEmpty()) {
                for (Ipra18N ipra : ipraList) {
                    sb.append("<ipra>");
                    sb.append("<id>").append(ipra.getIpra18Id()).append("</id>");
                    sb.append("<fam>").append(ipra.getChildId().getChildFam()).append("</fam>");
                    sb.append("<name>").append(ipra.getChildId().getChildName()).append("</name>");
                    String p = " ";
                    if (ipra.getChildId().getChildPatr() != null) {
                        p = ipra.getChildId().getChildPatr();
                    }
                    sb.append("<patr>").append(p).append("</patr>");
                    Date dr = ipra.getChildId().getChildDr();
                    sb.append("<dr>").append(regularDateFormat.format(dr)).append("</dr>");
                    sb.append("<reg>").append(ipra.getChildId().getSprregId().getSprregName()).append("</reg>");
                    sb.append("<nipra>").append(ipra.getIpra18N()).append("</nipra>");
                    Date otchomsu = ipra.getIpra18Otchomsu();
                    String otchomsuS = " ";
                    try {
                        otchomsuS = regularDateFormat.format(otchomsu);
                    } catch (Exception e) {
                    }
                    sb.append("<otchomsu>").append(otchomsuS).append("</otchomsu>");
                    Date otchcenter = ipra.getIpra18Otchcenter();
                    String otchcenterS = " ";
                    try {
                        otchcenterS = regularDateFormat.format(otchcenter);
                    } catch (Exception e) {
                    }
                    sb.append("<otchcenter>").append(otchcenterS).append("</otchcenter>");
                    Date otchdo = ipra.getIpra18Otchdo();
                    String otchdoS = " ";
                    try {
                        otchdoS = regularDateFormat.format(otchdo);
                    } catch (Exception e) {
                    }
                    sb.append("<otchdo>").append(otchdoS).append("</otchdo>");
                    sb.append("</ipra>");
                }
            }
            sb.append("</ipras>");
            itsOk = true;
        } else if (type.equals("ipraarch")) {    // поиск по архиву ИПРА
            // параметры поискового запроса
            String fam = request.getParameter("fam");   // фамилия
            String name = request.getParameter("name"); // имя
            String patr = request.getParameter("patr"); // отчество
            String reg = request.getParameter("reg");   // район (ид в текстовом виде)
            // если поле не null - обрезаем пробелы, если null - присваиваем пустую строку (для корректного поиска)
            if (fam != null) {
                fam = fam.trim();
            } else {
                fam = "";
            }
            if (name != null) {
                name = name.trim();
            } else {
                name = "";
            }
            if (patr != null) {
                patr = patr.trim();
            } else {
                patr = "";
            }
            // переводим ИД района в число и ищем соответствующий район в справочнике
            Integer regId = 0;
            try {
                regId = Integer.parseInt(reg);
            } catch (Exception e) {
            }
            SprRegion sprReg = null;
            if (regId > 0) {
                sprReg = sprRegionFacade.findById(regId);
            }

            // статус ИПРА 1 - в работе, 0 - в архиве
            Integer status = 0;
            String npr = "";
            Date dprDate = null;

            // ищем ИПРА по заданным параметрам            
            List<Ipra18N> ipraList = ipra18NFacade.findBySearch(fam, name, patr, sprReg, npr, dprDate, status);

            // составляем xml для передачи клиенту
            // заголовок таблицы
            sb.append("<ipraarchs>");
            sb.append("<ipra>");
            sb.append("<id>").append("ИД").append("</id>");
            sb.append("<fam>").append("Фамилия").append("</fam>");
            sb.append("<name>").append("Имя").append("</name>");
            sb.append("<patr>").append("Отчество").append("</patr>");
            sb.append("<dr>").append("Дата рождения").append("</dr>");
            sb.append("<reg>").append("Район").append("</reg>");
            sb.append("<nipra>").append("№ ИПРА").append("</nipra>");
            sb.append("<otchomsu>").append("Дата отчёта ОМСУ").append("</otchomsu>");
            sb.append("<otchcenter>").append("Дата отчёта ОЦППМСП").append("</otchcenter>");
            sb.append("<otchdo>").append("Дата отчёта ДО").append("</otchdo>");
            sb.append("</ipra>");
            // содерждимое таблицы
            if (!ipraList.isEmpty()) {
                for (Ipra18N ipra : ipraList) {
                    sb.append("<ipra>");
                    sb.append("<id>").append(ipra.getIpra18Id()).append("</id>");
                    sb.append("<fam>").append(ipra.getChildId().getChildFam()).append("</fam>");
                    sb.append("<name>").append(ipra.getChildId().getChildName()).append("</name>");
                    String p = " ";
                    if (ipra.getChildId().getChildPatr() != null) {
                        p = ipra.getChildId().getChildPatr();
                    }
                    sb.append("<patr>").append(p).append("</patr>");
                    Date dr = ipra.getChildId().getChildDr();
                    sb.append("<dr>").append(regularDateFormat.format(dr)).append("</dr>");
                    sb.append("<reg>").append(ipra.getChildId().getSprregId().getSprregName()).append("</reg>");
                    sb.append("<nipra>").append(ipra.getIpra18N()).append("</nipra>");
                    Date otchomsu = ipra.getIpra18Otchomsu();
                    String otchomsuS = " ";
                    try {
                        otchomsuS = regularDateFormat.format(otchomsu);
                    } catch (Exception e) {
                    }
                    sb.append("<otchomsu>").append(otchomsuS).append("</otchomsu>");
                    Date otchcenter = ipra.getIpra18Otchcenter();
                    String otchcenterS = " ";
                    try {
                        otchcenterS = regularDateFormat.format(otchcenter);
                    } catch (Exception e) {
                    }
                    sb.append("<otchcenter>").append(otchcenterS).append("</otchcenter>");
                    Date otchdo = ipra.getIpra18Otchdo();
                    String otchdoS = " ";
                    try {
                        otchdoS = regularDateFormat.format(otchdo);
                    } catch (Exception e) {
                    }
                    sb.append("<otchdo>").append(otchdoS).append("</otchdo>");
                    sb.append("</ipra>");
                }
            }
            sb.append("</ipraarchs>");
            itsOk = true;
        } else if (type.equals("allclregions")) {   // весь список районов проживания
            List<SprRegion> regions = sprRegionFacade.findNoCenter();
            String n = request.getParameter("n");
            if (n == null) {
                n = "0";
            }
            String typeCl = request.getParameter("typecl");
            if (typeCl == null) {
                typeCl = "none";
            }
            if (!regions.isEmpty()) {
                sb.append("<regions>");
                for (SprRegion reg : regions) {
                    sb.append("<region>");
                    sb.append("<type>").append(typeCl).append("</type>");
                    sb.append("<n>").append(n).append("</n>");
                    sb.append("<id>").append(reg.getSprregId()).append("</id>");
                    sb.append("<name>").append(reg.getSprregName()).append("</name>");
                    sb.append("</region>");
                }
                sb.append("</regions>");
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
        // логирование времени последнего действия пользователя
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

        StringBuilder sb = new StringBuilder(); // буфер для xml
        boolean itsOk = false;  // признак готовности результата к отправке
        // форматы для дат
        SimpleDateFormat regularDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String[]> params = request.getParameterMap();

        String type = params.get("type")[0]; // тип поиска
        if (type.equals("pmpk")) {
            String np = "";
            try {
                np = params.get("np")[0];
            } catch (Exception ex) {
            }
            List<Pmpk> pmpkList = new ArrayList<>();
            if (!np.equals("")) {
                pmpkList = pmpkFacade.findByPmpkNp(np);
            }
            sb.append("<pmpks>");
            sb.append("<pmpk>");
            sb.append("<id>").append("ИД").append("</id>");
            sb.append("<datepmpk>").append("Дата ПМПК").append("</datepmpk>");
            sb.append("<fio>").append("ФИО").append("</fio>");
            sb.append("<dr>").append("Дата рождения").append("</dr>");
            sb.append("</pmpk>");
            for (Pmpk pmpk : pmpkList) {
                sb.append("<pmpk>");
                Children child = childrenFacade.findById(pmpk.getPrclId().getClientId());
                sb.append("<id>").append(child.getChildId()).append("</id>");
                String date = regularDateFormat.format(pmpk.getPrclId().getPriyomId().getPriyomDate());
                sb.append("<datepmpk>").append(date).append("</datepmpk>");
                sb.append("<fio>").append(child.getFIO()).append("</fio>");
                sb.append("<dr>").append(child.getFormat2Dr()).append("</dr>");
                sb.append("</pmpk>");
            }
            sb.append("</pmpks>");
            itsOk = true;
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
