<%-- 
    Document   : reestrpmpkageondate
    Created on : 05.04.2019, 15:06:33
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
        <title>Реестр ПМПК возраст на дату</title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <p>Реестр детей, прошедших ПМПК (полный, возраст на дату)
            <br>с: <input type="date" name="date1" id="date1">
                по: <input type="date" name="date2" id="date2">
            <br>
            район:            
            <select id="selReg">
                <option value="0"></option>
                <c:forEach var="reg" items="${regions}">
                    <option value="${reg.getSprregId()}">
                        <c:out value="${reg.getSprregName()}" />
                    </option>
                </c:forEach>
            </select>
            <br>
            Вычислить возраст на дату:
            <input type="date" name="dateage" id="dateage">
        </p> 
            <input type="button" value="Получить реестр" onclick="printrpmpkageondate()">
    </c:if>
    </body>
</html>
