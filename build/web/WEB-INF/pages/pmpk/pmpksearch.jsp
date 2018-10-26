<%-- 
    Document   : pmpksearch
    Created on : 28.05.2018, 9:32:43
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="pmpksearch.js" charset="utf-8"></script>
        <title>Поиск по ПМПК</title>
    </head>
    <body>
        <h3>Поиск по ПМПК</h3>
        <div id="divSearch">
            Номер протокола: 
            <input type="text" id="nomPr" name="nomPr">
            <br>
            <input type="button" id="searchBtn" value="Поиск" onclick="search()">
            <br>
        </div>
        <div id="divContent">
            <table id="tabContent" class="regular">
                
            </table>
        </div>
    </body>
    <script type="text/javascript">
        search();
    </script>
</html>
