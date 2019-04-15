<%-- 
    Document   : main
    Created on : Jan 10, 2016, 4:00:59 PM
    Author     : admin_ai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>АИС "Сопровождение"</title>
    </head>
    <body>
    <c:if test="${user != null}">
        <div>
            <p>Добро пожаловать, <c:out value="${sotr_io}" /></p>
            <a class="main" href="pup" target="_blank">Сопровождение</a>            
            <c:if test="${rolesRight.isVologda()}">
                <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
                    <a class="main" href="ipra" target="_blank">ИПРА</a>
                </c:if>
            </c:if>
            <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                <c:if test="${rolesRight.isVologda()}">
                    <a class="main" href="pay" target="_blank">Платные услуги</a>
                </c:if>
                <br>
                <br><br>
                <a class="main" href="tootchet" target="_blank">Отчеты</a>
                <a class="main" href="admin" target="_blank">Администрирование</a>
                <a class="main" href="pmpk" target="_blank">Отчеты и реестры ПМПК</a>
            </c:if>
            <c:if test="${(user.getRoleId().getRoleName().equals('administrator'))||(user.getUserId() == 116)}">
                <a class="main" href="otchet?id=ranniy" target="_blank">Отчеты службы ранней помощи</a>
            </c:if>
        </div>
    </c:if>
    </body>
</html>
