<%-- 
    Document   : monitpmpk
    Created on : 28.02.2017, 19:19:48
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="otchet0401.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Реестр ПМПК для мониторинга</title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <p>Реестр детей, прошедших ПМПК (выгрузка для мониторинга)
            <br>с: <input type="date" name="date1" id="date1">
                по: <input type="date" name="date2" id="date2">
            <br>
            район:
            <input type="hidden" id="regId" name="regId" value="2">
            <select id="selReg" onchange="regCh()">
                <c:forEach var="reg" items="${regions}">
                    <option value="${reg.getSprregId()}">
                        <c:out value="${reg.getSprregName()}" />
                    </option>
                </c:forEach>
            </select>
        </p> 
            <input type="button" value="Получить реестр" onclick="printrpmpkmonit()">
    </c:if>
    </body>
</html>

