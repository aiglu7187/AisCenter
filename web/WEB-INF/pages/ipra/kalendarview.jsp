<%-- 
    Document   : kalendarview
    Created on : 17.05.2017, 19:19:57
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="kalendar0215.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Календарь - Просмотр</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <div id="divYear">
                Выберите год: <br>
                <select id="kalend">
                </select>
                <input type="button" value="Показать календарь" onclick="viewKalendarFromBase()">
            </div>
        </c:if>
    </body>
    <script type="text/javascript">
        initview();
    </script>
</html>
