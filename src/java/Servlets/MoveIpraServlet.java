/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.Ipra;
import Entities.Ipra18;
import Entities.Ipra18N;
import Entities.Ipra18Prikaz;
import Entities.Ipra18PrikazN;
import Entities.IpraEduCondition;
import Entities.IpraEduConditionN;
import Entities.IpraIshcorr;
import Entities.IpraOmsu;
import Entities.IpraPerechen;
import Entities.IpraPerechenN;
import Entities.IpraPerechenTpmpk;
import Entities.IpraVhcorr;
import Entities.IshCorr;
import Entities.SprCorr;
import Entities.SprIshType;
import Entities.SprVhType;
import Entities.Users;
import Entities.VhCorr;
import Sessions.Ipra18Facade;
import Sessions.Ipra18NFacade;
import Sessions.Ipra18PrikazFacade;
import Sessions.Ipra18PrikazNFacade;
import Sessions.IpraEduConditionFacade;
import Sessions.IpraEduConditionNFacade;
import Sessions.IpraFacade;
import Sessions.IpraIshcorrFacade;
import Sessions.IpraOmsuFacade;
import Sessions.IpraPerechenFacade;
import Sessions.IpraPerechenNFacade;
import Sessions.IpraPerechenTpmpkFacade;
import Sessions.IpraVhcorrFacade;
import Sessions.IshCorrFacade;
import Sessions.SprCorrFacade;
import Sessions.SprIshTypeFacade;
import Sessions.SprVhTypeFacade;
import Sessions.VhCorrFacade;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

/**
 *
 * @author Aiglu
 */
@WebServlet(name = "MoveIpraServlet",
        loadOnStartup = 1,
        urlPatterns = "/moveipra")

public class MoveIpraServlet extends HttpServlet {

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
    IpraFacade ipraFacade = new IpraFacade();
    @EJB
    Ipra18Facade ipra18Facade = new Ipra18Facade();
    @EJB
    Ipra18PrikazFacade ipra18PrikazFacade = new Ipra18PrikazFacade();
    @EJB
    Ipra18NFacade ipra18NFacade = new Ipra18NFacade();
    @EJB
    Ipra18PrikazNFacade ipra18PrikazNFacade = new Ipra18PrikazNFacade();
    @EJB
    IpraEduConditionFacade ipraEduConditionFacade = new IpraEduConditionFacade();
    @EJB
    IpraPerechenFacade ipraPerechenFacade = new IpraPerechenFacade();
    @EJB
    IpraIshcorrFacade ipraIshcorrFacade = new IpraIshcorrFacade();
    @EJB
    IshCorrFacade ishCorrFacade = new IshCorrFacade();
    @EJB
    SprIshTypeFacade sprIshTypeFacade = new SprIshTypeFacade();
    @EJB
    SprCorrFacade sprCorrFacade = new SprCorrFacade();
    @EJB
    IpraOmsuFacade ipraOmsuFacade = new IpraOmsuFacade();
    @EJB
    SprVhTypeFacade sprVhTypeFacade = new SprVhTypeFacade();
    @EJB
    VhCorrFacade vhCorrFacade = new VhCorrFacade();
    @EJB
    IpraVhcorrFacade ipraVhcorrFacade = new IpraVhcorrFacade();
    @EJB
    IpraPerechenNFacade ipraPerechenNFacade = new IpraPerechenNFacade();
    @EJB
    IpraEduConditionNFacade ipraEduConditionNFacade = new IpraEduConditionNFacade();
    @EJB
    IpraPerechenTpmpkFacade ipraPerechenTpmpkFacade = new IpraPerechenTpmpkFacade();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Users user = (Users) session.getAttribute("user");
        if (!user.getUserId().equals(55)) {
            return;
        }

        String action = request.getParameter("action");
        if (action.equals("open")) {
            String userPath = "/admin/adminmoveipra";
            String url = "/WEB-INF/pages" + userPath + ".jsp";
            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (action.equals("move")) {
            String prDateS = request.getParameter("date");
            Date prDate = null;
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd");
            try {
                prDate = format.parse(prDateS);
            } catch (ParseException ex) {
                Logger.getLogger(MoveIpraServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (prDate != null) {
                List<Ipra> ipraList = ipraFacade.findBySearch("", "", "", null, 1, prDate, "", "do1");
                for (Ipra ipra : ipraList) {
                    Ipra18 ipra18 = new Ipra18();
                    Ipra18Prikaz ipra18Prikaz = new Ipra18Prikaz();
                    ipra18.setChildId(ipra.getChildId());
                    ipra18.setDateUpd(ipra.getDateUpd());
                    ipra18.setIpra18Dateexp(ipra.getIpraDateexp());
                    ipra18.setIpra18Dateok(ipra.getIpraDateok());
                    ipra18.setIpra18IshmseD(ipra.getIpraIshmseD());
                    ipra18.setIpra18IshmseN(ipra.getIpraIshmseN());
                    ipra18.setIpra18N(ipra.getIpraN());
                    ipra18.setIpra18Otchdo(ipra.getIpraOtchdo());
                    ipra18.setIpra18VhdoD(ipra.getIpraVhdoD());
                    ipra18.setIpra18VhdoN(ipra.getIpraVhdoN());
                    ipra18.setUserId(ipra.getUserId());
                    ipra18Facade.create(ipra18);

                    ipra18Prikaz.setIpra18Id(ipra18);
                    ipra18Prikaz.setDateUpd(ipra.getDateUpd());
                    ipra18Prikaz.setIpra18prikazDoD(ipra.getIpraPrikazD());
                    ipra18Prikaz.setIpra18prikazDoN(ipra.getIpraPrikazN());
                    ipra18Prikaz.setIpra18prikazOmsuprD(ipra.getIpraPrikazOmsuD());
                    ipra18Prikaz.setIpra18prikazOtchcenter(ipra.getIpraOtchcenter());
                    ipra18Prikaz.setIpra18prikazOtchomsu(ipra.getIpraOtchomsu());
                    ipra18Prikaz.setIpra18prikazOznakD(ipra.getIpraOznakom());
                    ipra18Prikaz.setIpra18prikazPerechD(ipra.getIpraPerechD());
                    ipra18Prikaz.setIpra18prikazPerechN(ipra.getIpraOmsuN());
                    ipra18Prikaz.setIpra18prikazTpmpkD(ipra.getIpraTpmpkD());
                    ipra18Prikaz.setUserId(ipra.getUserId());
                    ipra18PrikazFacade.create(ipra18Prikaz);

                    ipraFacade.remove(ipra);
                }
                if (!ipraList.isEmpty()) {
                    String userPath = "/pup/goodsave";
                    String url = "/WEB-INF/pages" + userPath + ".jsp";
                    try {
                        request.getRequestDispatcher(url).forward(request, response);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else if (action.equals("18tonew")) {  // перенос ИПРА2018 в новую структуру
            // выбираем весь список ИПРА
            List<Ipra18> ipra18List = ipra18Facade.findAll();
            for (Ipra18 ipra18 : ipra18List) {
                // ищем связанную строку из таблицы с приказами
                Ipra18Prikaz prikaz = ipra18PrikazFacade.findByIpra(ipra18);
                // создаём новую запись в новой структуре
                Ipra18N ipra18n = new Ipra18N();
                // переносим основные данные
                ipra18n.setChildId(ipra18.getChildId());
                ipra18n.setIpra18N(ipra18.getIpra18N());
                ipra18n.setIpra18Nexp(ipra18.getIpra18Nexp());
                ipra18n.setIpra18Dateexp(ipra18.getIpra18Dateexp());
                ipra18n.setIpra18Dateok(ipra18.getIpra18Dateok());
                ipra18n.setIpra18IshmseN(ipra18.getIpra18IshmseN());
                ipra18n.setIpra18IshmseD(ipra18.getIpra18IshmseD());
                ipra18n.setSprmseId(ipra18.getSprmseId());
                ipra18n.setIpra18Status(ipra18.getIpra18Status());
                ipra18n.setIpra18Otchomsu(prikaz.getIpra18prikazOtchomsu());
                ipra18n.setIpra18Otchcenter(prikaz.getIpra18prikazOtchcenter());
                ipra18n.setIpra18Otchdo(ipra18.getIpra18Otchdo());
                ipra18n.setUserId(ipra18.getUserId());
                ipra18n.setDateUpd(ipra18.getDateUpd());
                ipra18NFacade.create(ipra18n);
                // если есть номер отчёта центра - создаём исходящее письмо в ДО
                if (prikaz.getIpra18prikazOtchcenterN() != null) {
                    // письмо
                    IshCorr ishOtchCenter = new IshCorr();
                    ishOtchCenter.setIshcorrD(prikaz.getIpra18prikazOtchcenter());  // дата
                    ishOtchCenter.setIshcorrN(prikaz.getIpra18prikazOtchcenterN()); // номер
                    SprIshType ishType = null;
                    try {
                        ishType = sprIshTypeFacade.findByName("otch_center");  // ищем нужный тип письма
                    } catch (Exception ex) {
                    }
                    ishOtchCenter.setSprishtypeId(ishType);
                    SprCorr corr = null;
                    try {
                        corr = sprCorrFacade.findByName("ДО"); // ищем корреспондента
                    } catch (Exception ex) {
                    }
                    ishOtchCenter.setSprcorrId(corr);
                    ishCorrFacade.create(ishOtchCenter);
                    // привязываем письмо к ИПРА
                    IpraIshcorr ishOtchCenterIpra = new IpraIshcorr();
                    ishOtchCenterIpra.setIshcorrId(ishOtchCenter);
                    ishOtchCenterIpra.setIpra18Id(ipra18n);
                    ipraIshcorrFacade.create(ishOtchCenterIpra);
                }

                // если есть запрос от ОМСУ на приказ, создаём новую запись с приказом и запросом
                if (prikaz.getIpra18prikazReqD() != null) {
                    // создаём запись в таблице с запросами от ОМСУ - запрос на приказ
                    IpraOmsu ipraOmsu = new IpraOmsu();
                    ipraOmsu.setIpra18Id(ipra18n);
                    ipraOmsu.setSpromsuId(prikaz.getSpromsuId());
                    ipraOmsu.setIpraomsuIshN(prikaz.getIpra18prikazReqN());
                    ipraOmsu.setIpraomsuIshD(prikaz.getIpra18prikazReqD());
                    ipraOmsu.setIpraomsuVhN(ipra18.getIpra18VhdoN());
                    ipraOmsu.setIpraomsuVhD(ipra18.getIpra18VhdoD());
                    ipraOmsu.setIpraomsuReq(1); // признак того, что это запрос на приказ
                    ipraOmsu.setUserId(prikaz.getUserId());
                    ipraOmsuFacade.create(ipraOmsu);
                    // если есть дата приказа, создаём запись в таблице с приказами
                    if (prikaz.getIpra18prikazDoD() != null) {
                        Ipra18PrikazN prikazN = new Ipra18PrikazN();
                        prikazN.setIpra18Id(ipra18n);
                        prikazN.setIpraomsuId(ipraOmsu);
                        prikazN.setIpra18prikazN(prikaz.getIpra18prikazDoN());
                        prikazN.setIpra18prikazD(prikaz.getIpra18prikazDoD());
                        prikazN.setUserId(prikaz.getUserId());
                        prikazN.setDateUpd(prikaz.getDateUpd());
                        ipra18PrikazNFacade.create(prikazN);
                    }
                    // создаём запись в таблице письмами - запрос на перечень
                    VhCorr vhPerechen = new VhCorr();
                    vhPerechen.setVhcorrIshd(prikaz.getIpra18prikazReqD());  // дата
                    vhPerechen.setVhcorrIshn(prikaz.getIpra18prikazReqN()); // номер
                    SprVhType vhType = null;
                    try {
                        vhType = sprVhTypeFacade.findByName("req_perechen");  // ищем нужный тип письма
                    } catch (Exception ex) {
                    }
                    vhPerechen.setSprvhtypeId(vhType);
                    SprCorr corr = null;
                    try {
                        corr = sprCorrFacade.findByTableAndId("SPR_OMSU", prikaz.getSpromsuId().getSpromsuId()); // ищем корреспондента
                    } catch (Exception ex) {
                    }
                    vhPerechen.setSprcorrId(corr);
                    vhCorrFacade.create(vhPerechen);
                    // привязываем письмо к ИПРА
                    IpraVhcorr vhPerechenIpra = new IpraVhcorr();
                    vhPerechenIpra.setVhcorrId(vhPerechen);
                    vhPerechenIpra.setIpra18Id(ipra18n);
                    ipraVhcorrFacade.create(vhPerechenIpra);

                    // если есть дата перечня, создаём запись в таблице с перечнями
                    if (prikaz.getIpra18prikazPerechD() != null) {
                        IpraPerechenN perechen = new IpraPerechenN();
                        perechen.setIpra18Id(ipra18n);
                        perechen.setUserId(prikaz.getUserId());
                        perechen.setDateUpd(prikaz.getDateUpd());
                        perechen.setVhcorrId(vhPerechen);
                        ipraPerechenNFacade.create(perechen);
                        // если есть контекст перечня, создаём записи в таблице с содержимым перечней
                        IpraPerechen ipraPerechen = null;
                        try {
                            ipraPerechen = ipraPerechenFacade.findByIpra18(ipra18);
                        } catch (Exception ex) {
                        }
                        if (ipraPerechen != null) {
                            List<IpraEduCondition> iecList = ipraEduConditionFacade.findByIpraPerechen(ipraPerechen);
                            for (IpraEduCondition iec : iecList) {
                                IpraEduConditionN iecN = new IpraEduConditionN();
                                iecN.setDateUpd(iec.getDateUpd());
                                iecN.setIpraeducondContext(iec.getIpraeducondContext());
                                iecN.setIpraeducondDate(iec.getIpraeducondDate());
                                iecN.setIpraeducondtypeId(iec.getIpraeducondtypeId());
                                iecN.setIpraperechennId(perechen);
                                iecN.setUserId(iec.getUserId());
                                ipraEduConditionNFacade.create(iecN);
                            }
                        }
                        // создаём исходящее письмо
                        IshCorr ishPerechen = new IshCorr();
                        ishPerechen.setIshcorrD(prikaz.getIpra18prikazPerechD());  // дата
                        ishPerechen.setIshcorrN(prikaz.getIpra18prikazPerechN()); // номер
                        SprIshType ishType = null;
                        try {
                            ishType = sprIshTypeFacade.findByName("perechen");  // ищем нужный тип письма
                        } catch (Exception ex) {
                        }
                        ishPerechen.setSprishtypeId(ishType);
                        SprCorr corr1 = null;   // ищем корреспондента
                        try {
                            corr1 = sprCorrFacade.findByTableAndId("SPR_OMSU", prikaz.getSpromsuId().getSpromsuId()); // ищем корреспондента
                        } catch (Exception ex) {
                        }
                        ishPerechen.setSprcorrId(corr1);
                        ishCorrFacade.create(ishPerechen);
                        // привязываем письмо к ИПРА
                        IpraIshcorr ishPerechenIpra = new IpraIshcorr();
                        ishPerechenIpra.setIshcorrId(ishPerechen);
                        ishPerechenIpra.setIpra18Id(ipra18n);
                        ipraIshcorrFacade.create(ishPerechenIpra);
                        // привязываем письмо к перечню
                        perechen.setIshcorrId(ishPerechen);
                        ipraPerechenNFacade.edit(perechen);

                        // если есть ТПМПК, создаём привязку к ТПМПК
                        if (prikaz.getSprotherpmpkId() != null) {
                            IpraPerechenTpmpk perTpmpk = new IpraPerechenTpmpk();
                            perTpmpk.setIpraperechennId(perechen);
                            perTpmpk.setSprotherpmpkId(prikaz.getSprotherpmpkId());
                            ipraPerechenTpmpkFacade.create(perTpmpk);
                        }
                        // если есть дата запроса в ТПМПК, создаём письмо
                        if (prikaz.getIpra18prikazTpmpkD() != null) {
                            // письмо
                            IshCorr ishReqTpmpk = new IshCorr();
                            ishReqTpmpk.setIshcorrD(prikaz.getIpra18prikazTpmpkD());  // дата
                            ishReqTpmpk.setIshcorrN(prikaz.getIpra18prikazTpmpkN()); // номер
                            SprIshType ishTpmpkType = null;
                            try {
                                ishTpmpkType = sprIshTypeFacade.findByName("req_tpmpk");  // ищем нужный тип письма
                            } catch (Exception ex) {
                            }
                            ishReqTpmpk.setSprishtypeId(ishTpmpkType);
                            SprCorr corr2 = null;   // ищем корреспондента
                            try {
                                corr2 = sprCorrFacade.findByTableAndId("SPR_OTHER_PMPK", prikaz.getSprotherpmpkId().getSprotherpmpkId()); // ищем корреспондента
                            } catch (Exception ex) {
                            }
                            ishReqTpmpk.setSprcorrId(corr2);
                            ishCorrFacade.create(ishReqTpmpk);
                            // привязываем письмо к ИПРА
                            IpraIshcorr ishReqTpmpkIpra = new IpraIshcorr();
                            ishReqTpmpkIpra.setIshcorrId(ishReqTpmpk);
                            ishReqTpmpkIpra.setIpra18Id(ipra18n);
                            ipraIshcorrFacade.create(ishReqTpmpkIpra);
                        }
                    }

                }
            }

            String userPath = "/pup/goodsave";
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
