<%-- 
    Document   : otchetconsultpar
    Created on : 01.04.2019, 10:38:26
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="otchet0401.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Отчет по консультированию родителей (категории)</title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <p>Отчет по консультированию родителей (категории) 
            <br>с: <input type="date" name="date1" id="date1">
                по: <input type="date" name="date2" id="date2">
        </p> 
            <input type="button" value="Получить отчет" onclick="printconsultpar()">                    
            Внимание! Отчёт грузится достаточно долго.
    </c:if>
    </body>
</html>
