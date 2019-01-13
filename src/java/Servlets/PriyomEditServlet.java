/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.ChildStatus;
import Entities.Children;
import Entities.LoginLog;
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
import Entities.SotrudDolgn;
import Entities.SprDolgn;
import Entities.SprObr;
import Entities.SprObrType;
import Entities.SprObrVar;
import Entities.SprParentType;
import Entities.SprProblem;
import Entities.SprProblemType;
import Entities.SprRegion;
import Entities.SprRekomend;
import Entities.SprStat;
import Entities.SprStatPod;
import Entities.SprUsl;
import Entities.Users;
import Entities.UslDolgntype;
import Entities.UslStatus;
import Other.ChildrenComparator;
import Other.ParentsComparator;
import Other.PedComparator;
import Other.RegionComparator;
import Other.SotrudDolgnComparator;
import Sessions.ChildStatusFacade;
import Sessions.ChildrenFacade;
import Sessions.LoginLogFacade;
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
import Sessions.SprDolgnFacade;
import Sessions.SprObrFacade;
import Sessions.SprObrTypeFacade;
import Sessions.SprObrVarFacade;
import Sessions.SprParentTypeFacade;
import Sessions.SprProblemFacade;
import Sessions.SprProblemTypeFacade;
import Sessions.SprRegionFacade;
import Sessions.SprRekomendFacade;
import Sessions.SprStatFacade;
import Sessions.SprStatPodFacade;
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
import Other.Status;
import Sessions.UslStatusFacade;

/**
 *
 * @author Aiglu
 */
// сервлет для страницы с информацией о клиенте
@WebServlet(name = "PriyomEditServlet",
        loadOnStartup = 1,
        urlPatterns = "/priyomedit")

public class PriyomEditServlet extends HttpServlet {

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
    PriyomSotrudFacade priyomSotrudFacade = new PriyomSotrudFacade();
    @EJB
    PriyomClientFacade priyomClientFacade = new PriyomClientFacade();
    @EJB
    PriyomProblemFacade priyomProblemFacade = new PriyomProblemFacade();
    @EJB
    SprRegionFacade sprRegionFacade = new SprRegionFacade();
    @EJB
    UslDolgntypeFacade uslDolgntypeFacade = new UslDolgntypeFacade();
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
    ChildStatusFacade childStatusFacade = new ChildStatusFacade();
    @EJB
    SprStatFacade sprStatFacade = new SprStatFacade();
    @EJB
    SprStatPodFacade sprStatPodFacade = new SprStatPodFacade();
    @EJB
    PmpkFacade pmpkFacade = new PmpkFacade();
    @EJB
    SprObrTypeFacade sprObrTypeFacade = new SprObrTypeFacade();
    @EJB
    SprObrFacade sprObrFacade = new SprObrFacade();
    @EJB
    SprObrVarFacade sprObrVarFacade = new SprObrVarFacade();
    @EJB
    SprRekomendFacade sprRekomendFacade = new SprRekomendFacade();
    @EJB
    PmpkRekFacade pmpkRekFacade = new PmpkRekFacade();
    @EJB
    PriyomSubjectFacade priyomSubjectFacade = new PriyomSubjectFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    SprUslFacade sprUslFacade = new SprUslFacade();
    @EJB
    PmpkParentFacade pmpkParentFacade = new PmpkParentFacade();
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

        String idS = request.getParameter("id");
        Integer id = Integer.parseInt(idS);
        Boolean itsOk = true;
        String userPath = "";
        Priyom priyom = null;
        try {
            priyom = priyomFacade.findById(id);
        } catch (Exception ex) {
            itsOk = false;
        }

        if (itsOk) {
            try {
                session.removeAttribute("priyom");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("sotruds");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("problems");
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
                session.removeAttribute("pmpklist");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("problemtypes");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("sprproblem");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("osnStatusList");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("dopStatusList");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("socStatusList");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("podStatusList");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("obrType");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("obr");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("obrVar");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("rekomend");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("pmpkRek");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("uslList");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("pmpkParents");
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
            try {
                session.removeAttribute("parentTypeList");
            } catch (Exception ex) {
            }

            List<PriyomSotrud> prSotrud = priyomSotrudFacade.findByPriyom(priyom);
            List<PriyomProblem> prProblem = priyomProblemFacade.findByPriyom(priyom);

            session.setAttribute("priyom", priyom);
            session.setAttribute("sotruds", prSotrud);
            session.setAttribute("problems", prProblem);

            Boolean copy = Boolean.FALSE;
            session.setAttribute("copy", copy);

            Integer cnt = priyom.getSpruslId().getSpruslCenter();
            List<SprRegion> reg = new ArrayList();
            if (cnt == 1) {
                reg = sprRegionFacade.findCenter();
                Collections.sort(reg, new RegionComparator());
            } else if (cnt == 0) {
                reg = sprRegionFacade.findNoOther();
                Collections.sort(reg, new RegionComparator());
            }
            session.setAttribute("reg", reg);

            List<SprParentType> sprParentTypeList = sprParentTypeFacade.findAllSprParentType();
            session.setAttribute("parentTypeList", sprParentTypeList);

            Date datePr = priyom.getPriyomDate();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String datePriyom = format.format(datePr);
            session.setAttribute("datepriyom", datePriyom);

            List<UslDolgntype> uslDolgntype = uslDolgntypeFacade.findByUsl(priyom.getSpruslId());
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

            List<PriyomClient> prClient = priyomClientFacade.findByPriyom(priyom);
            List<PriyomSubject> prSubject = priyomSubjectFacade.findByPriyom(priyom);
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

            List<Pmpk> pmpk = new ArrayList();
            for (PriyomClient prCl : prClient) {
                List<Pmpk> pmpkPrCl = pmpkFacade.findByPrcl(prCl);
                if (pmpkPrCl.size() > 0) {
                    for (Pmpk p : pmpkPrCl) {
                        pmpk.add(p);
                    }
                }
            }

            List<PmpkParent> pmpkParents = new ArrayList<>();
            for (Pmpk pk : pmpk) {
                List<PmpkParent> pkPar = pmpkParentFacade.findByPmpk(pk);
                for (PmpkParent pp : pkPar) {
                    pmpkParents.add(pp);
                }
            }

            Collections.sort(children, new ChildrenComparator());
            Collections.sort(parents, new ParentsComparator());
            Collections.sort(ped, new PedComparator());

            session.setAttribute("priyomclient", prClient);
            session.setAttribute("priyomsubject", prSubject);
            session.setAttribute("children", children);
            session.setAttribute("childrenSub", childrenSub);
            session.setAttribute("parents", parents);
            session.setAttribute("peds", ped);
            session.setAttribute("pmpklist", pmpk);

            List<SprProblemType> problemTypes = sprProblemTypeFacade.findAllType();
            session.setAttribute("problemtypes", problemTypes);
            List<SprProblem> sprProblem = sprProblemFacade.findAll();
            session.setAttribute("sprproblem", sprProblem);

            // выбираем все статусы по справочникам
            List<SprStat> sprStatOsn = sprStatFacade.findByStatV(1);
            List<SprStat> sprStatDop = sprStatFacade.findByStatV(2);
            List<SprStat> sprStatSoc = sprStatFacade.findByStatV(4);
            List<SprStatPod> sprAllStatPod = sprStatPodFacade.findAllStatPod();
            // выбираем все статусы для всех детей и создаём списки для отображения в jsp 
            List<Status> osnStatusList = new ArrayList<>();
            List<Status> dopStatusList = new ArrayList<>();
            List<Status> socStatusList = new ArrayList<>();
            List<Status> podStatusList = new ArrayList<>();
            for (Children child : children) {
                for (SprStat st : sprStatOsn) {
                    Status status = new Status();
                    status.setChild(child);
                    status.setStatus(st);
                    UslStatus uslStatus = uslStatusFacade.findByUslAndStat(priyom.getSpruslId(), st);
                    if (uslStatus.getUslstatusEnabled() == 1) {
                        status.setEnabled(true);
                    } else {
                        status.setEnabled(false);
                    }
                    List<ChildStatus> childStatusList = childStatusFacade.findByChildAndStatusActOnDate(child, st, datePr);
                    if (!childStatusList.isEmpty()) {
                        status.setChecked(true);
                    } else {
                        status.setChecked(false);
                    }
                    osnStatusList.add(status);
                }
                for (SprStat st : sprStatDop) {
                    Status status = new Status();
                    status.setChild(child);
                    status.setStatus(st);
                    UslStatus uslStatus = uslStatusFacade.findByUslAndStat(priyom.getSpruslId(), st);
                    if (uslStatus.getUslstatusEnabled() == 1) {
                        status.setEnabled(true);
                    } else {
                        status.setEnabled(false);
                    }
                    List<ChildStatus> childStatusList = childStatusFacade.findByChildAndStatusActOnDate(child, st, datePr);
                    if (!childStatusList.isEmpty()) {
                        status.setChecked(true);
                    } else {
                        status.setChecked(false);
                    }
                    dopStatusList.add(status);
                }
                for (SprStat st : sprStatSoc) {
                    Status status = new Status();
                    status.setChild(child);
                    status.setStatus(st);
                    UslStatus uslStatus = uslStatusFacade.findByUslAndStat(priyom.getSpruslId(), st);
                    if (uslStatus.getUslstatusEnabled() == 1) {
                        status.setEnabled(true);
                    } else {
                        status.setEnabled(false);
                    }
                    List<ChildStatus> childStatusList = childStatusFacade.findByChildAndStatusActOnDate(child, st, datePr);
                    if (!childStatusList.isEmpty()) {
                        status.setChecked(true);
                    } else {
                        status.setChecked(false);
                    }
                    socStatusList.add(status);
                }
                for (SprStatPod st : sprAllStatPod) {
                    Status status = new Status();
                    status.setChild(child);
                    status.setStatus(st.getSprstatIdPod());
                    status.setMainStatus(st.getSprstatIdMain());
                    UslStatus uslStatus = uslStatusFacade.findByUslAndStat(priyom.getSpruslId(), st.getSprstatIdPod());
                    if (uslStatus.getUslstatusEnabled() == 1) {
                        status.setEnabled(true);
                    } else {
                        status.setEnabled(false);
                    }
                    List<ChildStatus> childStatusList = childStatusFacade.findByChildAndStatusActOnDate(child, st.getSprstatIdPod(), datePr);
                    if (!childStatusList.isEmpty()) {
                        status.setChecked(true);
                    } else {
                        status.setChecked(false);
                    }
                    podStatusList.add(status);
                }
            }
            for (Children child : childrenSub) {
                for (SprStat st : sprStatOsn) {
                    Status status = new Status();
                    status.setChild(child);
                    status.setStatus(st);
                    UslStatus uslStatus = uslStatusFacade.findByUslAndStat(priyom.getSpruslId(), st);
                    if (uslStatus.getUslstatusEnabled() == 1) {
                        status.setEnabled(true);
                    } else {
                        status.setEnabled(false);
                    }
                    List<ChildStatus> childStatusList = childStatusFacade.findByChildAndStatusActOnDate(child, st, datePr);
                    if (!childStatusList.isEmpty()) {
                        status.setChecked(true);
                    } else {
                        status.setChecked(false);
                    }
                    osnStatusList.add(status);
                }
                for (SprStat st : sprStatDop) {
                    Status status = new Status();
                    status.setChild(child);
                    status.setStatus(st);
                    UslStatus uslStatus = uslStatusFacade.findByUslAndStat(priyom.getSpruslId(), st);
                    if (uslStatus.getUslstatusEnabled() == 1) {
                        status.setEnabled(true);
                    } else {
                        status.setEnabled(false);
                    }
                    List<ChildStatus> childStatusList = childStatusFacade.findByChildAndStatusActOnDate(child, st, datePr);
                    if (!childStatusList.isEmpty()) {
                        status.setChecked(true);
                    } else {
                        status.setChecked(false);
                    }
                    dopStatusList.add(status);
                }
                for (SprStat st : sprStatSoc) {
                    Status status = new Status();
                    status.setChild(child);
                    status.setStatus(st);
                    UslStatus uslStatus = uslStatusFacade.findByUslAndStat(priyom.getSpruslId(), st);
                    if (uslStatus.getUslstatusEnabled() == 1) {
                        status.setEnabled(true);
                    } else {
                        status.setEnabled(false);
                    }
                    List<ChildStatus> childStatusList = childStatusFacade.findByChildAndStatusActOnDate(child, st, datePr);
                    if (!childStatusList.isEmpty()) {
                        status.setChecked(true);
                    } else {
                        status.setChecked(false);
                    }
                    socStatusList.add(status);
                }
                for (SprStatPod st : sprAllStatPod) {
                    Status status = new Status();
                    status.setChild(child);
                    status.setStatus(st.getSprstatIdPod());
                    status.setMainStatus(st.getSprstatIdMain());
                    UslStatus uslStatus = uslStatusFacade.findByUslAndStat(priyom.getSpruslId(), st.getSprstatIdPod());
                    if (uslStatus.getUslstatusEnabled() == 1) {
                        status.setEnabled(true);
                    } else {
                        status.setEnabled(false);
                    }
                    List<ChildStatus> childStatusList = childStatusFacade.findByChildAndStatusActOnDate(child, st.getSprstatIdPod(), datePr);
                    if (!childStatusList.isEmpty()) {
                        status.setChecked(true);
                    } else {
                        status.setChecked(false);
                    }
                    podStatusList.add(status);
                }
            }
            session.setAttribute("osnStatusList", osnStatusList);
            session.setAttribute("dopStatusList", dopStatusList);
            session.setAttribute("socStatusList", socStatusList);
            session.setAttribute("podStatusList", podStatusList);

            List<SprObrType> sprObrTypeList = sprObrTypeFacade.findAll();
            session.setAttribute("obrType", sprObrTypeList);
            List<SprObr> sprObrList = sprObrFacade.findAll();
            session.setAttribute("obr", sprObrList);
            List<SprObrVar> sprObrVarList = sprObrVarFacade.findAll();
            session.setAttribute("obrVar", sprObrVarList);

            List<SprRekomend> sprRekomendList = sprRekomendFacade.findAllRekomend();
            session.setAttribute("rekomend", sprRekomendList);

            session.setAttribute("pmpkParents", pmpkParents);

            List<PmpkRek> pmpkRekList = new ArrayList<>();
            for (Pmpk p : pmpk) {
                List<PmpkRek> pmpkRek = pmpkRekFacade.findByPmpk(p);
                if (pmpkRek.size() > 0) {
                    for (PmpkRek pr : pmpkRek) {
                        pmpkRekList.add(pr);
                    }
                }
            }
            session.setAttribute("pmpkRek", pmpkRekList);

            List<SprUsl> uslList = new ArrayList<>();
            SprUsl usl1 = sprUslFacade.findByName("Групповое консультирование");
            SprUsl usl2 = sprUslFacade.findByName("Групповое обследование");
            uslList.add(usl1);
            uslList.add(usl2);
            session.setAttribute("uslList", uslList);            

            userPath = "/pup/priyomview";
        } else {
            userPath = "/pup/priyom";
        }

        String url = "/WEB-INF/pages" + userPath + ".jsp";
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
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
