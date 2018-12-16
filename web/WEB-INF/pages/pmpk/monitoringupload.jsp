<%-- 
    Document   : monitoringupload
    Created on : 29.03.2017, 17:16:02
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
        <title>Загрузка результатов мониторинга</title>
    </head>
    <body>
    <c:if test="${user != null}">
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <p>Загрузка результатов мониторинга</p>
            <p>Файлы с результатами мониторинга: <br>
                <c:forEach var="file" items="${monitFiles}">
                    <label>
                        <input type="radio" value="${file}" name="files" >
                        <c:out value="${file}" /><br>
                    </label>
                </c:forEach>
            </p>
            <input type="button" value="Загрузить" onclick="test()">
        </c:if>
    </c:if>
    </body>
</html>
