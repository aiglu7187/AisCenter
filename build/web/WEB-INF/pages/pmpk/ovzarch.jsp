<%-- 
    Document   : ovzarch
    Created on : 08.10.2017, 10:57:16
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="otchet0323.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Реестр детей с ОВЗ для мониторинга</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <p>Реестр детей с ОВЗ (выгрузка в архивы для мониторинга),
                <br>
                прошедших ПМПК
                <br>с: <input type="date" name="date1" id="date1">
                по: <input type="date" name="date2" id="date2">
                <br>
                родившихся после: <input type="date" name="date3" id="date3">  
                до: <input type="date" name="date4" id="date4"> 
                (если необходимо выгрузить всех - оставить пустыми)
                <br>
                вид образовательной программы:
                <br>
                <c:forEach var="obrtype" items="${sprObrTypes}">
                    <label>
                        <input type="checkbox" id="type${obrtype.getSprobrtypeId()}" name="type${obrtype.getSprobrtypeId()}" value="${obrtype.getSprobrtypeId()}">
                        <c:out value="${obrtype.getSprobrtypeName()}" />
                    </label>
                    <br>
                </c:forEach>
                <br>
                район:
                <input type="hidden" id="regId" name="regId" value="2">
                <select id="selReg" onchange="regCh()">                    
                    <c:forEach var="reg" items="${regions}">
                        <option value="${reg.getSprregId()}">
                            <c:out value="${reg.getSprregName()}" />
                        </option>
                    </c:forEach>
                </select>
            </p> 
            <input type="button" value="Получить архив" onclick="printovzarch()">
        </c:if>
    </body>
</html>
