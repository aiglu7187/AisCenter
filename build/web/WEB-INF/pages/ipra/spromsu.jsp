<%-- 
    Document   : spromsu
    Created on : 21.02.2018, 15:20:36
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="spromsu0618.js" charset="utf-8"></script>
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Справочник ОМСУ</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
            <h3>Справочник ОМСУ</h3>
            <input type="button" id="addBtn" value="Добавить" onclick="addomsu()">
            <br>
            <br>
            <table class="regular" id="spromsuTable">
                <thead>
                    <tr>
                        <td></td>
                        <td>Район</td>
                        <td>Название</td>
                        <td>Руководитель</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="omsu" items="${spromsu}">                            
                        <tr onclick = "javascript:window.open('spromsu?action=view&id=${omsu.getSpromsuId()}', '_blank')">
                            <td>
                                <c:out value="${omsu.getSpromsuId()}" />
                            </td>
                            <td>
                                <c:out value="${omsu.getSprregId().getSprregName()}" /> 
                            </td>
                            <td>
                                <c:out value="${omsu.getSpromsuName()}" />                                    
                            </td>        
                            <td>
                                <c:out value="${omsu.getSpromsuChiefFam()} ${omsu.getSpromsuChiefName()} ${omsu.getSpromsuChiefPatr()}" />                                    
                            </td>  
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>
