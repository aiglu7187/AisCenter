/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Entities.LoginControl;
import Entities.LoginLog;
import Entities.Sotrud;
import Entities.Users;
import Other.RolesRight;
import Sessions.LoginControlFacade;
import Sessions.LoginLogFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Sessions.UsersFacade;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author admin_ai
 */
// сервлет для main.jsp
@WebServlet(name = "LoginServlet",
        loadOnStartup = 1,
        urlPatterns = "/gotomain")

public class LoginServlet extends HttpServlet {

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
    UsersFacade usersFacade = new UsersFacade();
    @EJB
    LoginLogFacade loginLogFacade = new LoginLogFacade();
    @EJB
    LoginControlFacade loginControlFacade = new LoginControlFacade();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

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
        // processRequest(request, response);
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

        String url = "";
        String userPath = request.getServletPath();

        String userName = request.getParameter("user_name");

        List<Users> users = null;
        try {
            users = usersFacade.findByName(userName);
        } catch (Exception ex) {
            String badConn = "Нет соединения с сервером";
            response.setCharacterEncoding("cp1251");
            response.getWriter().println(badConn);
        }

        if ((users != null) && (users.isEmpty())) {
            String wrongLogin = "Неверное имя пользователя";
            response.setCharacterEncoding("cp1251");
            response.getWriter().println(wrongLogin);
        }

        if ((users != null) && (users.size() > 1)) {
            String manyLogin = "Существует несколько пользователей с таким именем. Обратитесь к администратору";
            response.setCharacterEncoding("cp1251");
            response.getWriter().println(manyLogin);
        }

        if ((users != null) && (users.size() == 1)) {
            Users user = users.get(0);
            String userPassword = request.getParameter("user_password");
            try {
                session.removeAttribute("user");
            } catch (Exception ex) {
            }
            try {
                session.removeAttribute("sotr_io");
            } catch (Exception ex) {
            }

            if (user.getUserActive() == 1) {
                if (userPassword.equals(user.getUserPassword())) {
                    Sotrud sotr = user.getSotrudId();
                    String io = sotr.getSotrudName() + " " + sotr.getSotrudPatr();
                    session.setAttribute("sotr_io", io);
                    session.setAttribute("user", user);

                    List<LoginControl> lc = loginControlFacade.findByUser(user);
                    if (lc.size() > 0) {
                        for (LoginControl l : lc) {
                            loginControlFacade.remove(l);
                        }
                    }

                    LoginLog login = new LoginLog();
                    login.setUserId(user);
                    login.setSessId(session.getId());
                    Date loginTime = new Date(session.getCreationTime());
                    login.setLoginlogLogintime(loginTime);
                    loginLogFacade.create(login);

                    RolesRight rolesRight = new RolesRight(user.getCenterRole());
                    try {
                        session.removeAttribute("rolesRight");
                    } catch (Exception ex) {
                    }
                    session.setAttribute("rolesRight", rolesRight);

                    if (userPath.equals("/gotomain")) {
                        userPath = "/main";
                        url = "/WEB-INF/pages" + userPath + ".jsp";
                        try {
                            request.getRequestDispatcher(url).forward(request, response);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    String wrongPassword = "";
                    List<LoginControl> lc = loginControlFacade.findByUser(user);
                    if (lc.size() > 0) {
                        for (LoginControl l : lc) {
                            Date dt = l.getLogincontrolDatetime();
                            Date nowDt = new Date();
                            if (nowDt.getTime() - dt.getTime() < 300000) {
                                int n = l.getLogincontrolCount();
                                if (n < 3) {
                                    wrongPassword = "Неверный пароль";
                                    l.setLogincontrolCount(n + 1);
                                } else if (n == 3) {
                                    wrongPassword = "Неверный пароль. У вас осталась последняя попытка";
                                    l.setLogincontrolCount(n + 1);
                                } else if (n == 4) {
                                    wrongPassword = "Неверный пароль. Это была 5 попытка за 5 минут, пользователь будет заблокирован";
                                    user.setUserActive(0);
                                    usersFacade.edit(user);
                                    l.setLogincontrolCount(5);
                                } else if (n == 5) {
                                    if (user.getUserActive() == 0) {
                                        wrongPassword = "Пользователь отключен. Обратитесь к администратору";
                                    }
                                }
                                loginControlFacade.edit(l);
                            } else {
                                loginControlFacade.remove(l);
                            }
                        }
                    } else if (lc.isEmpty()) {
                        LoginControl newLc = new LoginControl();
                        newLc.setUserId(user);
                        newLc.setLogincontrolDatetime(new Date());
                        newLc.setLogincontrolCount(1);
                        loginControlFacade.create(newLc);
                        wrongPassword = "Неверный пароль";
                    }
                    response.setCharacterEncoding("cp1251");
                    response.getWriter().println(wrongPassword);
                }
            } else if (user.getUserActive() == 0) {
                String wrong = "Пользователь отключен. Обратитесь к администратору";
                response.setCharacterEncoding("cp1251");
                response.getWriter().println(wrong);
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

}
