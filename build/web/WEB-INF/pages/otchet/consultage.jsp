<%-- 
    Document   : consultage
    Created on : 07.12.2018, 12:40:59
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="otchet1207.js" charset="utf-8"></script>
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Отчет по консультированию законных представителей (с учётом возраста детей)</title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <p>Отчет по консультированию законных представителей (с учётом возраста детей) за период 
            <br>с: <input type="date" name="date1" id="date1">
                по: <input type="date" name="date2" id="date2">
            <br>
            возраст с: <input type="text" name="agen" id="agen"> по: <input type="text" name="agek" id="agek">
            <br>
            (начало в период не включается, конец - включается)
        </p> 
            <input type="button" value="Получить отчет" onclick="printconsultage()">        
    </c:if>
    </body>
</html>
