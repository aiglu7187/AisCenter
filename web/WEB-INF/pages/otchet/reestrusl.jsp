<%-- 
    Document   : reestrusl
    Created on : 30.11.2017, 16:07:05
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="otchet1226.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Список клиентов по услуге со специалистами</title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <p>Список клиентов по услуге со специалистами 
            <br>
            За период с: <input type="date" name="date1" id="date1">
            по: <input type="date" name="date2" id="date2">
            <br>
            по услуге: 
            <select id="selUsl">
                <c:forEach var="usl" items="${sprUslList}">
                    <option value="${usl.getSpruslId()}">
                        <c:out value="${usl.getSpruslName()}" />
                    </option>
                </c:forEach>
            </select>
        </p> 
            <input type="button" value="Получить реестр" onclick="printreestrusl()">        
    </c:if>
    </body>
</html>
