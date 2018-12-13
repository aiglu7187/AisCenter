<%-- 
    Document   : statpmpk
    Created on : 10.05.2016, 11:41:07
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="otchet1207.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Статистический отчет по ПМПК</title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <p>Статистический отчет по ПМПК за период 
            <br>с: <input type="date" name="date1" id="date1">
                по: <input type="date" name="date2" id="date2">
        </p> 
            <input type="button" value="Получить отчет" onclick="printstatpmpk()">        
    </c:if>
    </body>
</html>