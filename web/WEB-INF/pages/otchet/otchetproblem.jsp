<%-- 
    Document   : otchetproblem
    Created on : 09.11.2016, 16:17:31
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="otchet1207.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Отчет по выявленным проблемам</title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <p>Отчет по выявленным проблемам за период 
            <br>с: <input type="date" name="date1" id="date1">
                по: <input type="date" name="date2" id="date2">
        </p> 
            <input type="button" value="Получить отчет" onclick="printproblem()">        
    </c:if>
    </body>
</html>
