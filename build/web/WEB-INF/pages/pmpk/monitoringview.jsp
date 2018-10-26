<%-- 
    Document   : monitoringview
    Created on : 29.03.2017, 17:15:50
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
        <title>Просмотр результатов мониторинга</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <p>Результаты мониторинга</p>
            <table class="regular" >
                <thead>
                    <tr>
                        <td>                            
                        </td>
                        <td>
                            Район
                        </td>
                        <td>
                            Дата мониторинга
                        </td>
                    </tr>
                </thead>
                <c:forEach var="mon" items="${monitoring}">
                    <tr><td><c:out value="${mon.getMonitoringId()}" /></td><td><c:out value="${mon.sprregId.getSprregName()}" /></td><td><c:out value="${mon.getFormatDate()}" /></td></tr>
                </c:forEach>
            </table>
        </c:if>
        <script type="text/javascript">
            init();
        </script>    
    </body>
</html>