<%-- 
    Document   : adminmoveipra
    Created on : 26.04.2018, 9:12:44
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Перенос ИПРА</title>
    </head>
    <body>
        Дата приказа:
        <input type="date" id="prDate" name="prDate">
        <br>
        <input type="button" id="okBtn" value="Перенести" 
               onclick="window.open('moveipra?action=move&date=' + document.getElementById('prDate').value)">
    </body>
</html>
