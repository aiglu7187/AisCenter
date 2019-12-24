<%-- 
    Document   : reestrpmpkfull
    Created on : 26.02.2018, 11:38:32
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
        <title>Реестр родителей ПМПК </title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <p>Реестр родителей детей, прошедших ПМПК
            <br>с: <input type="date" name="date1" id="date1">
                по: <input type="date" name="date2" id="date2">
            <br>
            <input type="hidden" id="regId" name="regId">            
        </p> 
            <input type="button" value="Получить реестр" onclick="printrpmpkconsult()">
    </c:if>
    </body>
</html>
