<%-- 
    Document   : reestruploadresult
    Created on : 24.06.2017, 15:53:13
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
        <title>Результат загрузки реестра ИПРА</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <p>Результат загрузки реестра ИПРА</p>
            <p>
                <c:out value="${result}" />
            </p>
            
        </c:if>
    </body>
</html>
