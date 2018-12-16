<%-- 
    Document   : ipraishnom
    Created on : 26.04.2018, 10:29:00
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="ipraishnom0426.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Нумерация исходящих писем</title>
    </head>
    <body>
        <form id="nomForm">
            Последний использованный номер: 
            <br>
            <input type="text" id="nom" name="nom" value="${nom}"> Только <strong>ЦИФРЫ</strong>!!! 
            <br>
            <br>
            Суффикс (будет автоматически добавляться в конец номера):
            <br>
            <input type="text" id="suffix" name="suffix" value="${suffix}">        
            <br>
            <br>
            <input type="button" id="saveBtn" value="Сохранить" onclick="validate()">
        </form>
    </body>
</html>
