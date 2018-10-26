<%-- 
    Document   : userview
    Created on : 02.06.2016, 11:29:51
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="userview1122.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Пользователь - <c:out value="${us.getUserName()}" /></title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <form id="formUser" action="saveuser" accept-charset="windows-1251" method="POST">
            <input id="userId" name="userId" type="hidden" value="${us.getUserId()}">           
            Имя пользователя: <input id="nam" name="nam" type="text" value="${us.getUserName()}">            
            <br>
            <br>
            Сотрудник: 
            <input id="sotrudId" name="sotrudId" type="hidden" value="${us.getSotrudId().getSotrudId()}">
            <select id="sotr">
                <c:forEach var="s" items="${sotrud}">
                    <c:if test="${s == us.getSotrudId()}">
                        <option selected value="${s.getSotrudId()}">
                            <c:out value="${s.getSotrudFIO()}" />
                        </option>
                    </c:if>
                    <c:if test="${s != us.getSotrudId()}">
                        <option value="${s.getSotrudId()}">
                            <c:out value="${s.getSotrudFIO()}" />
                        </option>
                    </c:if>
                </c:forEach>
            </select>     
            <br>
            <br>       
            Роль пользователя: 
            <input id="roleId" name="roleId" type="hidden" value="${us.getRoleId().getRoleId()}">
            <select id="roles">
                <c:forEach var="r" items="${roles}">
                    <c:if test="${r == us.getRoleId()}">
                        <option selected value="${r.getRoleId()}">
                            <c:out value="${r.getRoleName()}" />
                        </option>
                    </c:if>
                    <c:if test="${r != us.getRoleId()}">
                        <option value="${r.getRoleId()}">
                            <c:out value="${r.getRoleName()}" />
                        </option>
                    </c:if>
                </c:forEach>
            </select>     
            <br>
            <br>     
            <input type="hidden" id="act" name="act" value="${us.getUserActive()}">
            <select id="active">
                <c:if test="${us.getUserActive() == 1}">
                    <option selected value="1">Включен</option>
                    <option value="0">Отключен</option>
                </c:if>
                <c:if test="${us.getUserActive() == 0}">
                    <option value="1">Включен</option>
                    <option selected value="0">Отключен</option>
                </c:if>
            </select>
            <br>
            <br>
            Новый пароль (если необходимо сменить): <input id="newpass" name="newpass" type="text" >
            <br>
            <br>
            <input type="button" name="saveBtn" id = "saveBtn" value="Сохранить" onclick="validate()">
            <input type="button" name="closeBtn" id = "closeBtn" value="Закрыть" onclick="javascript:window.close();">
        </form>        
        <script type="text/javascript">
            init();
        </script>
        </c:if>
    </body>
</html>
