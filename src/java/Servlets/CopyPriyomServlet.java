/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Children;
import Entities.LoginLog;
import Entities.Parents;
import Entities.Ped;
import Entities.Priyom;
import Entities.PriyomClient;
import Entities.PriyomProblem;
import Entities.PriyomSotrud;
import Entities.PriyomSubject;
import Entities.SotrudDolgn;
import Entities.SprDolgn;
import Entities.SprParentType;
import Entities.SprRegion;
import Entities.SprUsl;
import Entities.Users;
import Entities.UslDolgntype;
import Other.ChildrenComparator;
import Other.ParentsComparator;
import Other.PedComparator;
import Other.RegionComparator;
import Other.SotrudDolgnComparator;
import Sessions.ChildrenFacade;
import Sessions.LoginLogFacade;
import Sessions.ParentsFacade;
import Sessions.PedFacade;
import Sessions.PriyomClientFacade;
import Sessions.PriyomFacade;
import Sessions.PriyomSotrudFacade;
import Sessions.PriyomSubjectFacade;
import Sessions.SotrudDolgnFacade;
import Sessions.SprDolgnFacade;
import Sessions.SprRegionFacade;
import Sessions.SprUslFacade;
import Sessions.UslDolgntypeFacade;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * @author Aiglu сервлет для копирования приёма
 */
@WebServlet(name = "CopyPriyomServlet",
        loadOnStartup = 1,
        urlPatterns = "/copypriyom")

public class CopyPriyomServlet extends HttpServlet {

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
    SprUslFacade sprUslFacade = new SprUslFacade();
    @EJB
    PriyomFacade priyomFacade = new PriyomFacade();
    @EJB
    PriyomSotrudFacade priyomSotrudFacade = new PriyomSotrudFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    UslDolgntypeFacade uslDolgntypeFacade = new UslDolgntypeFacade();
    @EJB
    SprDolgnFacade sprDolgnFacade = new SprDolgnFacade();
    @EJB
    SotrudDolgnFacade sotrudDolgnFacade = new SotrudDolgnFacade();
    @EJB
    PriyomClientFacade priyomClientFacade = new PriyomClientFacade();
    @EJB
    PriyomSubjectFacade priyomSubjectFacade = new PriyomSubjectFacade();
    @EJB
    ChildrenFacade childrenFacade = new ChildrenFacade();
    @EJB
    ParentsFacade parentsFacade = new ParentsFacade();
    @EJB
    PedFacade pedFacade = new PedFacade();

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

        // услуга, в которую выполняется копирование
        String uslIdS = request.getParameter("uslid");
        Integer uslId = 0;
        try {
            uslId = Integer.parseInt(uslIdS);
        } catch (Exception ex) {
        }
        SprUsl usl = null;
        if (uslId != 0) {
            usl = sprUslFacade.findById(uslId);
        }
        // приём с которого выполняется копирование
        String priyomIdS = request.getParameter("priyomid");
        Integer priyomId = 0;
        try {
            priyomId = Integer.parseInt(priyomIdS);
        } catch (Exception ex) {
        }
        Priyom oldPriyom = null;
        if (priyomId != 0) {
            oldPriyom = priyomFacade.findById(priyomId);
        }
        if (oldPriyom != null) {
            // удаляем аттрибуты
            try {
                session.removeAttribute("priyom");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("sotruds");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("reg");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("datepriyom");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("dolgns");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("allsotrud");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("priyomclient");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("children");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("priyomsubject");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("childrenSub");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("parents");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("peds");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("copy");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("kol");
            } catch (Exception ex) {
            }
            // устанавливаем аттрибут, что этот приём - копия
            Boolean copy = Boolean.TRUE;
            session.setAttribute("copy", copy);
            
            // находим данные по приёму для переноса и устанавливаем соответствующие аттрибуты
            List<PriyomSotrud> prSotrud = priyomSotrudFacade.findByPriyom(oldPriyom);
            session.setAttribute("sotruds", prSotrud);
            
            Integer cnt = oldPriyom.getSpruslId().getSpruslCenter();
            List<SprRegion> reg = new ArrayList();
            if (cnt == 1) {
                reg = sprRegionFacade.findCenter();
                Collections.sort(reg, new RegionComparator());
            } else if (cnt == 0) {
                reg = sprRegionFacade.findNoOther();
                Collections.sort(reg, new RegionComparator());
            }
            session.setAttribute("reg", reg);

            Date datePr = oldPriyom.getPriyomDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String datePriyom = format.format(datePr);
            session.setAttribute("datepriyom", datePriyom);

            List<UslDolgntype> uslDolgntype = uslDolgntypeFacade.findByUsl(oldPriyom.getSpruslId());
            List<SprDolgn> dolgnList = new ArrayList();
            for (UslDolgntype uslDolgn : uslDolgntype) {
                List<SprDolgn> dolgns = sprDolgnFacade.findByType(uslDolgn.getSprdolgntypeId());
                for (SprDolgn d : dolgns) {
                    dolgnList.add(d);
                }
            }
            session.setAttribute("dolgns", dolgnList);

            List<SotrudDolgn> allSotrud = sotrudDolgnFacade.findAll();
            Collections.sort(allSotrud, new SotrudDolgnComparator());
            session.setAttribute("allsotrud", allSotrud);

            List<PriyomClient> prClient = priyomClientFacade.findByPriyom(oldPriyom);
            List<PriyomSubject> prSubject = priyomSubjectFacade.findByPriyom(oldPriyom);
            List<Children> children = new ArrayList();
            List<Children> childrenSub = new ArrayList();
            List<Parents> parents = new ArrayList();
            List<Ped> ped = new ArrayList();
            for (PriyomClient prCl : prClient) {
                if (prCl.getPrclKatcl().equals("children")) {
                    children.add(childrenFacade.findById(prCl.getClientId()));
                } else if (prCl.getPrclKatcl().equals("parents")) {
                    parents.add(parentsFacade.findById(prCl.getClientId()));
                } else if (prCl.getPrclKatcl().equals("ped")) {
                    ped.add(pedFacade.findById(prCl.getClientId()));
                }
            }
            for (PriyomSubject prSub : prSubject) {
                childrenSub.add(prSub.getChildId());
            }

            Integer kol = children.size() + childrenSub.size() + parents.size() + ped.size();
            session.setAttribute("kol", kol);
            
            Collections.sort(children, new ChildrenComparator());
            Collections.sort(parents, new ParentsComparator());
            Collections.sort(ped, new PedComparator());
            
            session.setAttribute("priyomclient", prClient);
            session.setAttribute("priyomsubject", prSubject);
            session.setAttribute("children", children);
            session.setAttribute("childrenSub", childrenSub);
            session.setAttribute("parents", parents);
            session.setAttribute("peds", ped);
            
            // создаём новый приём для того, чтобы на jsp можно было взять параметры услуги
            Priyom priyom = new Priyom();
            priyom.setSprregId(oldPriyom.getSprregId());
            priyom.setPriyomDate(oldPriyom.getPriyomDate());            
            priyom.setSpruslId(usl);
            session.setAttribute("priyom", priyom);      
        }
        String userPath = "/pup/priyomview";
        String url = "/WEB-INF/pages" + userPath + ".jsp";
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
