<%-- 
    Document   : sprmse
    Created on : 21.02.2018, 15:20:36
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="sprmse0410.js" charset="utf-8"></script>
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Справочник бюро МСЭ</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
            <h3>Справочник бюро МСЭ</h3>
            <input type="button" id="addBtn" value="Добавить" onclick="addmse()">
            <br>
            <br>
            <table class="regular" id="sprmseTable">
                <thead>
                    <tr>
                        <td></td>
                        <td>Название бюро МСЭ</td>
                        <%--<td>Руководитель</td>
                        <td>Адрес</td>--%>  
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="mse" items="${sprmse}">                            
                        <tr onclick = "javascript:window.open('sprmse?action=view&id=${mse.getSprmseId()}', '_blank')">
                            <td>
                                <c:out value="${mse.getSprmseId()}" />
                            </td>
                            <td>
                                <c:out value="${mse.getSprmseName()}" />                                    
                            </td>
                            <%--<td>
                                <c:out value="${mse.getSprmseChief()}" />                                    
                            </td>
                            <td>
                                <c:out value="${mse.getSprmseAdr()}" />                                    
                            </td>--%>    
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>
