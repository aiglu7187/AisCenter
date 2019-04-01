<%-- 
    Document   : statpmpkpar
    Created on : 17.02.2017, 15:31:10
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="otchet0401.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Отчет ПМПК по параметрам</title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <p>Отчет ПМПК по параметрам (ИПР, ГИА, ТПМПК, впервые ОВЗ) за период 
            <br>с: <input type="date" name="date1" id="date1">
                по: <input type="date" name="date2" id="date2">
        </p> 
            <input type="button" value="Получить отчет" onclick="printstatpmpkpar()">        
    </c:if>
    </body>
</html>
