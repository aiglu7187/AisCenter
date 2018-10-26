<%-- 
    Document   : monovz
    Created on : 11.08.2017, 10:41:44
    Author     : Aiglu
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="monitoring1007.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Сверка результатов мониторинга</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">            
            <p>Файлы с результатами мониторинга: <br>
                <c:forEach var="file" items="${monitFiles}">
                    <label>
                        <input type="radio" value="${file}" name="files" >
                        <c:out value="${file}" /><br>
                    </label>
                </c:forEach>
            </p>
            <input type="button" value="Сверить" onclick="compare()">
        </c:if>
    </body>
</html>
