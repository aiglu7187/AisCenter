<%-- 
    Document   : reestrupload
    Created on : 22.06.2017, 16:38:56
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="ipraspisok0517.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Загрузка реестра ИПРА</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <p>Загрузка реестра ИПРА</p>
            <p>Файлы: <br>
                <c:forEach var="file" items="${reestrFiles}">
                    <label>
                        <input type="radio" value="${file}" name="files" >
                        <c:out value="${file}" /><br>
                    </label>
                </c:forEach>
            </p>
            <input type="button" value="Загрузить" onclick="uploadReestr()">
        </c:if>
    </body>
</html>
