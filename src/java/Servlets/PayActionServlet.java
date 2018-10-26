/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.Kalendar;
import Entities.LoginLog;
import Entities.Parents;
import Entities.PayDogovor;
import Entities.PayUsl;
import Entities.Payment;
import Entities.PayuslClient;
import Entities.PayuslDolgntype;
import Entities.PayuslLesson;
import Entities.PayuslSotrud;
import Entities.Payusllespos;
import Entities.Ped;
import Entities.Sotrud;
import Entities.SprDolgn;
import Entities.SprDolgnType;
import Entities.SprPayUsl;
import Entities.SprPos;
import Entities.Telephon;
import Entities.Users;
import Other.ChildrenComparator;
import Other.LessonComparator;
import Other.MyCalendar;
import Other.SotrudComparator;
import Sessions.ChildrenFacade;
import Sessions.KalendarFacade;
import Sessions.LoginLogFacade;
import Sessions.ParentsFacade;
import Sessions.PayDogovorFacade;
import Sessions.PayUslFacade;
import Sessions.PaymentFacade;
import Sessions.PayuslClientFacade;
import Sessions.PayuslDolgntypeFacade;
import Sessions.PayuslLessonFacade;
import Sessions.PayuslSotrudFacade;
import Sessions.PayusllesposFacade;
import Sessions.PedFacade;
import Sessions.SotrudFacade;
import Sessions.SprDolgnFacade;
import Sessions.SprPayUslFacade;
import Sessions.SprPosFacade;
import Sessions.TelephonFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
@WebServlet(name = "PayActionServlet",
        loadOnStartup = 1,
        urlPatterns = "/payaction")
public class PayActionServlet extends HttpServlet {

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
    PayuslDolgntypeFacade payuslDolgntypeFacade = new PayuslDolgntypeFacade();
    @EJB
    SprDolgnFacade sprDolgnFacade = new SprDolgnFacade();
    @EJB
    KalendarFacade kalendarFacade = new KalendarFacade();
    @EJB
    PayUslFacade payUslFacade = new PayUslFacade();
    @EJB
    PayuslSotrudFacade payuslSotrudFacade = new PayuslSotrudFacade();
    @EJB
    SotrudFacade sotrudFacade = new SotrudFacade();
    @EJB
    PayuslClientFacade payuslClientFacade = new PayuslClientFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    ParentsFacade parentsFacade = new ParentsFacade();
    @EJB
    PedFacade pedFacade = new PedFacade();
    @EJB
    PayuslLessonFacade payuslLessonFacade = new PayuslLessonFacade();
    @EJB
    PayDogovorFacade payDogovorFacade = new PayDogovorFacade();
    @EJB
    TelephonFacade telephonFacade = new TelephonFacade();
    @EJB
    SprPosFacade sprPosFacade = new SprPosFacade();
    @EJB
    PayusllesposFacade payusllesposFacade = new PayusllesposFacade();
    @EJB
    PaymentFacade paymentFacade = new PaymentFacade();

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
        StringBuilder sb = new StringBuilder();
        String action = request.getParameter("action");
        if (action.equals("add")) {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Integer year = now.get(Calendar.YEAR);
            List<Kalendar> allDaysOfYear = kalendarFacade.findAllDaysOfYear(year);
            List<MyCalendar> cList = new ArrayList<>();
            for (Kalendar day : allDaysOfYear) {
                MyCalendar c = new MyCalendar(day.getKalendarDate());
                Integer weekend = day.getKalendarWeekend();
                if (weekend == 1) {
                    c.setIsWeekend(Boolean.TRUE);
                } else if (weekend == 0) {
                    c.setIsWeekend(Boolean.FALSE);
                }
                cList.add(c);
            }
            try {
                session.removeAttribute("year");
            } catch (Exception ex) {

            }
            try {
                session.removeAttribute("cList");
            } catch (Exception ex) {

            }
            session.setAttribute("year", year);
            session.setAttribute("cList", cList);

            String userPath = "/pay/add";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("param")) {
            String uslIdS = request.getParameter("uslid");
            Integer uslId = 0;
            if (uslIdS != null) {
                uslId = Integer.parseInt(uslIdS);
            }
            SprPayUsl payUsl = null;
            if (uslId != 0) {
                payUsl = sprPayUslFacade.findById(uslId);
            }
            sb.append("<param>");
            if (payUsl != null) {
                sb.append("<stat>").append(payUsl.getSprpayuslStat()).append("</stat>");
                sb.append("<group>").append(payUsl.getSprpayuslGroup()).append("</group>");
                sb.append("<lesson>").append(payUsl.getSprpayuslLesson()).append("</lesson>");
            }
            sb.append("</param>");
            response.setContentType("text/xml; charset=windows-1251");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(sb.toString());
        } else if (action.equals("addlist")) {
            List<PayUsl> addPayUslList = payUslFacade.findByStatus("подготовлено");
            List<PayuslSotrud> payuslSotrud = new ArrayList<>();
            for (PayUsl pu : addPayUslList) {
                List<PayuslSotrud> puSotrud = payuslSotrudFacade.findByPayUsl(pu);
                for (PayuslSotrud puS : puSotrud) {
                    payuslSotrud.add(puS);
                }
            }

            try {
                session.removeAttribute("addPayUslList");
            } catch (Exception ex) {
            }
            session.setAttribute("addPayUslList", addPayUslList);

            try {
                session.removeAttribute("payuslSotrud");
            } catch (Exception ex) {
            }
            session.setAttribute("payuslSotrud", payuslSotrud);
            String userPath = "/pay/addlist";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("view")) {
            String idS = request.getParameter("id");
            Integer id = 0;
            try {
                id = Integer.parseInt(idS);
            } catch (Exception ex) {
            }
            PayUsl usl = null;
            if (id != 0) {
                usl = payUslFacade.findById(id);
            }
            try {
                session.removeAttribute("usl");
            } catch (Exception ex) {
            }
            session.setAttribute("usl", usl);
            List<PayuslLesson> payuslLessons = payuslLessonFacade.findByPayUsl(usl);

            Collections.sort(payuslLessons, new LessonComparator());
            try {
                session.removeAttribute("payuslLessons");
            } catch (Exception ex) {
            }
            session.setAttribute("payuslLessons", payuslLessons);

            List<SprPayUsl> payUslList = sprPayUslFacade.findAllLessonUsl(); // список услуг из справочника
            try {
                session.removeAttribute("payUslList");
            } catch (Exception ex) {
            }
            session.setAttribute("payUslList", payUslList);

            List<PayuslSotrud> sotruds = payuslSotrudFacade.findByPayUsl(usl);
            try {
                session.removeAttribute("sotruds");
            } catch (Exception ex) {
            }
            session.setAttribute("sotruds", sotruds);

            List<SprDolgnType> dolgnTypes = new ArrayList<>();
            if (usl != null) {
                List<PayuslDolgntype> uslDolgnList = payuslDolgntypeFacade.findByUsl(usl.getSprpayuslId());
                for (PayuslDolgntype ud : uslDolgnList) {
                    dolgnTypes.add(ud.getSprdolgntypeId());
                }
            }
            List<SprDolgn> dolgns = new ArrayList<>();
            for (SprDolgnType dt : dolgnTypes) {
                List<SprDolgn> dolgnByType = sprDolgnFacade.findByType(dt);
                for (SprDolgn d : dolgnByType) {
                    dolgns.add(d);
                }
            }
            try {
                session.removeAttribute("dolgns");
            } catch (Exception ex) {
            }
            session.setAttribute("dolgns", dolgns);

            List<Sotrud> allSotrud = sotrudFacade.findAll();
            Collections.sort(allSotrud, new SotrudComparator());
            try {
                session.removeAttribute("allsotrud");
            } catch (Exception ex) {
            }
            session.setAttribute("allsotrud", allSotrud);
            List<PayuslClient> puClient = payuslClientFacade.findByPayusl(usl);
            List<Children> children = new ArrayList();
            List<Parents> parents = new ArrayList();
            List<Ped> ped = new ArrayList();
            for (PayuslClient puc : puClient) {
                Integer clId = puc.getClientId();
                if (puc.getPayuslclientKatcl().equals("children")) {
                    children.add(childrenFacade.findById(clId));
                } else if (puc.getPayuslclientKatcl().equals("parents")) {
                    parents.add(parentsFacade.findById(clId));
                } else if (puc.getPayuslclientKatcl().equals("ped")) {
                    ped.add(pedFacade.findById(clId));
                }
            }

            try {
                session.removeAttribute("children");
            } catch (Exception ex) {
            }
            session.setAttribute("children", children);
            try {
                session.removeAttribute("parents");
            } catch (Exception ex) {
            }
            session.setAttribute("parents", parents);

            try {
                session.removeAttribute("ped");
            } catch (Exception ex) {
            }
            session.setAttribute("ped", ped);

            List<PayDogovor> payDogovors = payDogovorFacade.findByPayUsl(usl);
            try {
                session.removeAttribute("payDogovors");
            } catch (Exception ex) {
            }
            session.setAttribute("payDogovors", payDogovors);

            List<Telephon> telephons = new ArrayList<>();
            for (PayDogovor pd : payDogovors) {
                List<Telephon> tel = telephonFacade.findByParent(pd.getParentId());
                for (Telephon t : tel) {
                    telephons.add(t);
                }
            }

            try {
                session.removeAttribute("telephons");
            } catch (Exception ex) {
            }
            session.setAttribute("telephons", telephons);

            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Integer year = now.get(Calendar.YEAR);
            List<Kalendar> allDaysOfYear = kalendarFacade.findAllDaysOfYear(year);
            List<MyCalendar> cList = new ArrayList<>();
            for (Kalendar day : allDaysOfYear) {
                MyCalendar c = new MyCalendar(day.getKalendarDate());
                Integer weekend = day.getKalendarWeekend();
                if (weekend == 1) {
                    c.setIsWeekend(Boolean.TRUE);
                } else if (weekend == 0) {
                    c.setIsWeekend(Boolean.FALSE);
                }
                cList.add(c);
            }
            try {
                session.removeAttribute("year");
            } catch (Exception ex) {

            }
            try {
                session.removeAttribute("cList");
            } catch (Exception ex) {

            }
            session.setAttribute("year", year);
            session.setAttribute("cList", cList);

            String userPath = "/pay/payview";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("dogreestr")) {
            String userPath = "/pay/paydogreestr";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("tolist")) {
            String group = request.getParameter("group");
            List<SprPayUsl> sprPayUsl = sprPayUslFacade.findUslByGroup(Integer.parseInt(group));

            List<PayUsl> payUslList = new ArrayList<>();
            for (SprPayUsl spu : sprPayUsl) {
                List<PayUsl> payUsl = payUslFacade.findBySprPayUsl(spu);
                for (PayUsl pu : payUsl) {
                    if (pu.getPayuslStatus().equals("готово")) {
                        payUslList.add(pu);
                    }
                }
            }

            try {
                session.removeAttribute("payUslList");
            } catch (Exception ex) {
            }
            session.setAttribute("payUslList", payUslList);

            List<PayuslSotrud> payuslSotrudList = new ArrayList<>();
            for (PayUsl pu : payUslList) {
                List<PayuslSotrud> payuslSotrud = payuslSotrudFacade.findByPayUsl(pu);
                for (PayuslSotrud pus : payuslSotrud) {
                    payuslSotrudList.add(pus);
                }
            }

            try {
                session.removeAttribute("payuslSotrud");
            } catch (Exception ex) {
            }
            session.setAttribute("payuslSotrud", payuslSotrudList);

            String userPath = "/pay/payusllist";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (action.equals("dolgn")) {
            String uslIdS = request.getParameter("uslid");
            Integer uslId = 0;
            try {
                uslId = Integer.parseInt(uslIdS);
            } catch (Exception ex) {
            }
            SprPayUsl usl = null;
            if (uslId != 0) {
                usl = sprPayUslFacade.findById(uslId);
            }
            List<SprDolgnType> dolgnTypes = new ArrayList<>();
            if (usl != null) {
                List<PayuslDolgntype> uslDolgnList = payuslDolgntypeFacade.findByUsl(usl);
                for (PayuslDolgntype ud : uslDolgnList) {
                    dolgnTypes.add(ud.getSprdolgntypeId());
                }
            }
            List<SprDolgn> dolgns = new ArrayList<>();

            for (SprDolgnType dt : dolgnTypes) {
                List<SprDolgn> dolgnByType = sprDolgnFacade.findByType(dt);
                for (SprDolgn d : dolgnByType) {
                    dolgns.add(d);
                }
            }

            sb.append("<dolgns>");
            for (SprDolgn d : dolgns) {
                sb.append("<dolgn>");
                sb.append("<id>").append(d.getSprdolgnId()).append("</id>");
                sb.append("<name>").append(d.getSprdolgnName()).append("</name>");
                sb.append("</dolgn>");
            }

            sb.append("</dolgns>");
            response.setContentType("text/xml; charset=windows-1251");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write(sb.toString());
        } else if (action.equals("delete")) {
            String idS = request.getParameter("id");
            Integer id = 0;
            try {
                id = Integer.parseInt(idS);
            } catch (Exception ex) {
            }
            if (id != 0) {
                PayUsl payUsl = payUslFacade.findById(id);
                payUslFacade.remove(payUsl);
                String userPath = "/pup/gooddelete";
                String url = "/WEB-INF/pages" + userPath + ".jsp";
                try {
                    request.getRequestDispatcher(url).forward(request, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (action.equals("findtel")) {
            String parentIdS = request.getParameter("idparent");
            String n = request.getParameter("n");
            Integer parentId = 0;
            try {
                parentId = Integer.parseInt(parentIdS);
            } catch (Exception ex) {
            }
            Parents parent = null;
            if (parentId != 0) {
                parent = parentsFacade.findById(parentId);
            }
            List<Telephon> telephon = new ArrayList<>();
            if (parent != null) {
                telephon = telephonFacade.findByParent(parent);
            }

            if (!telephon.isEmpty()) {
                sb.append("<telephon>");
                if (n.equals("")) {
                    n = " ";
                }
                sb.append("<n>").append(n).append("</n>");
                sb.append("<tel>").append(telephon.get(0).getTel()).append("</tel>");
                sb.append("</telephon>");
                response.setContentType("text/xml; charset=windows-1251");
                response.setHeader("Cache-Control", "no-cache");
                response.getWriter().write(sb.toString());
            }
        } else if (action.equals("dogview")) {
            String idS = request.getParameter("id");
            Integer id = 0;
            try {
                id = Integer.parseInt(idS);
            } catch (Exception ex) {
            }
            PayDogovor dogovor = null;
            if (id != 0) {
                dogovor = payDogovorFacade.findById(id);
            }
            try {
                session.removeAttribute("dogovor");
            } catch (Exception ex) {
            }
            if (dogovor != null) {
                session.setAttribute("dogovor", dogovor);
                List<Telephon> telephons = telephonFacade.findByParent(dogovor.getParentId());
                try {
                    session.removeAttribute("telephon");
                } catch (Exception ex) {
                }
                if (!telephons.isEmpty()) {
                    session.setAttribute("telephon", telephons.get(0));
                }
                List<Payment> paymentList = paymentFacade.findByPayDogovor(dogovor);
                try {
                    session.removeAttribute("paymentList");
                } catch (Exception ex) {
                }
                if (!paymentList.isEmpty()) {
                    session.setAttribute("paymentList", paymentList);
                }
            }

            String userPath = "/pay/paydogview";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("viewfinal")) {
            String payUslIdS = request.getParameter("id");
            Integer payUslId = 0;
            try {
                payUslId = Integer.parseInt(payUslIdS);
            } catch (Exception ex) {
            }
            PayUsl payUsl = null;
            if (payUslId != 0) {
                payUsl = payUslFacade.findById(payUslId);
            }
            try {
                session.removeAttribute("payUsl");
            } catch (Exception ex) {
            }
            if (payUsl != null) {
                session.setAttribute("payUsl", payUsl);
            }

            List<PayuslClient> clients = payuslClientFacade.findByPayusl(payUsl);
            List<Children> children = new ArrayList<>();
            for (PayuslClient client : clients) {
                if (client.getPayuslclientKatcl().equals("children")) {
                    Children child = childrenFacade.findById(client.getClientId());
                    children.add(child);
                }
            }
            Collections.sort(children, new ChildrenComparator());

            try {
                session.removeAttribute("payUslChildren");
            } catch (Exception ex) {
            }
            session.setAttribute("payUslChildren", children);

            List<PayDogovor> dogovors = payDogovorFacade.findByPayUsl(payUsl);
            try {
                session.removeAttribute("dogovors");
            } catch (Exception ex) {
            }
            session.setAttribute("dogovors", dogovors);

            List<SprPos> sprPos = sprPosFacade.findAll();
            try {
                session.removeAttribute("sprPos");
            } catch (Exception ex) {
            }
            session.setAttribute("sprPos", sprPos);

            List<Payusllespos> poses = new ArrayList<>();
            for (PayuslClient client : clients) {
                List<Payusllespos> pos = payusllesposFacade.findByPayuslclient(client);
                poses.addAll(pos);
            }
            try {
                session.removeAttribute("poses");
            } catch (Exception ex) {
            }
            session.setAttribute("poses", poses);

            String userPath = "/pay/payviewfinal";
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

}
