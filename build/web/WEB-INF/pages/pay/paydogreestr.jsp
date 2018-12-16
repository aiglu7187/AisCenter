<%-- 
    Document   : paydogreestr
    Created on : 20.09.2017, 11:08:51
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="payadd0205.js" charset="utf-8"></script>       
        <title>Реестр договоров</title>
    </head>
    <body>
    <c:if test="${user != null}">
        <h3>Реестр договоров</h3>
        <div id="search">
            Номер: <input type="text" id="searchN" name="searchN">            
            Дата: <input type="date" id="searchD" name="searchD">
            <br>
            <br>
            С кем заключен договор (родитель)
            <br>
            Фамилия: <input type="text" id="searchFam" name="searchFam">
            Имя: <input type="text" id="searchName" name="searchName">
            Отчество: <input type="text" id="searchPatr" name="searchPatr">
            <br>
            <br>
            <input class="btn" type="button" id="btnSearch" name="btnSearch" value="Поиск" onclick="searchDog()">
            <input class="btn" type="button" id="btnSearch" name="btnSearch" value="Очистить поиск" onclick="clearSearchDog()">
        </div>
        <div id="divDogTable">
            <table class="regular" id="dogTable">
                
            </table>
        </div>
    </c:if>
    </body>
    <script type="text/javascript">
        initDogReestr();
    </script>
</html>
