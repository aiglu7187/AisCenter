<%-- 
    Document   : ovzfgos
    Created on : 30.09.2017, 14:23:20
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="otchet1207.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Реестр детей с ОВЗ по ФГОС</title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <p>Реестр детей с ОВЗ, которым рекомендованы программы по ФГОС
            <br>с: <input type="date" name="date1" id="date1">
                по: <input type="date" name="date2" id="date2">
            <br>            
        </p> 
            <input type="button" value="Получить реестр" onclick="printovzfgos()">
    </c:if>
    </body>
</html>
