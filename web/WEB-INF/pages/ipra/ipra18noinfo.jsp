<%-- 
    Document   : ipra18noinfo
    Created on : 06.12.2018, 15:21:30
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="ipra18spisok1207.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Реестр ИПРА без запроса/отказа</title>
    </head>
    <body>        
        <p>Реестр ИПРА без запроса/отказа
            <br>с: <input type="date" name="date1" id="date1">
            по: <input type="date" name="date2" id="date2">
            <br>
            район:            
            <select id="selReg">
                <option value="0"></option>
                <c:forEach var="reg" items="${regions}">
                    <option value="${reg.getSprregId()}">
                        <c:out value="${reg.getSprregName()}" />
                    </option>
                </c:forEach>
            </select>
        </p> 
        <input type="button" value="Получить реестр" onclick="printNoInfo()">    
    </body>
</html>
