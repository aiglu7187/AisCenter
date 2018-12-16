<%-- 
    Document   : monittestfile
    Created on : 30.03.2017, 19:45:17
    Author     : Aiglu
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="monitoring1007.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Загрузка результатов мониторинга - Проверка файла</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">   
            <input type="hidden" id="filename" value="${filename}">
            <p>Протокол проверки файла перед загрузкой: <br>
                <c:forEach var="pr" items="${protokol}">
                    <c:out value="${pr.getN()}" />                   
                    <c:out value="${pr.getChild()}" />
                    <c:out value="${pr.getPmpk()}" />
                    <c:out value="${pr.getZakl()}" />
                    <c:out value="${pr.getOo()}" />
                    <c:out value="${pr.getStage()}" />
                    <c:out value="${pr.getForm()}" />
                    <c:out value="${pr.getPo()}" />
                    <c:out value="${pr.getZan()}" />
                    <c:out value="${pr.getAssist()}" />
                    <c:out value="${pr.getEquip()}" />
                    <c:out value="${pr.getPrich()}" /><br>
                </c:forEach>
            </p>
            <input type="date" id="dateMonit" name="dateMonit"><br>
            <input type="button" value="Загрузить" onclick="upload()">
        </c:if>
    </body>
</html>

