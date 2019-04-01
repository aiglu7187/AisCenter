<%-- 
    Document   : pmpkfirstovz
    Created on : 29.11.2018, 11:16:24
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
        <title>Реестр детей, прошедших ПМПК, у которых впервые выявлены ОВЗ</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <p>Реестр детей, прошедших ПМПК, у которых впервые выявлены ОВЗ
            <br>с: <input type="date" name="date1" id="date1">
                по: <input type="date" name="date2" id="date2">
            <br>
            район:
            <input type="hidden" id="regId" name="regId">
            <select id="selReg" onchange="regCh()">
                <option value="0"></option>
                <c:forEach var="reg" items="${regions}">
                    <option value="${reg.getSprregId()}">
                        <c:out value="${reg.getSprregName()}" />
                    </option>
                </c:forEach>
            </select>
        </p> 
            <input type="button" value="Получить реестр" onclick="printpmpkfirstovz()">
    </c:if>
    </body>
</html>
