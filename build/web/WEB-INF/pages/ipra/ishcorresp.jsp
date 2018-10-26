<%-- 
    Document   : ishcorresp
    Created on : 18.06.2018, 13:38:29
    Author     : Aiglu
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="spromsu0618.js" charset="utf-8"></script>
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Реестр исходящих писем</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
            <h3>Реестр исходящих писем</h3>
            <%--<input type="button" id="printReestrBtn" value="Печать реестра" onclick="printreestr()"> 
            <br>
            <br>--%>
            <table class="regular" id="ishCorrespTable">
                <thead>
                    <tr>
                        <td></td>                        
                        <td>Дата отправки</td>
                        <td>Номер письма</td>
                        <td>Назначение письма</td>
                        <td>ФИО ребёнка</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="letter" items="${letterList}">                            
                        <tr>
                            <td>
                                <c:out value="${letter.getType()};${letter.getId()}" />
                            </td>
                            <td>
                                <c:out value="${letter.getFormat2Date()}" />                                    
                            </td>        
                            <td>
                                <c:out value="${letter.getNomer()}" />                                    
                            </td>  
                            <td>
                                <c:out value="${letter.getName()}" /> 
                            </td>
                            <td>
                                <c:out value="${letter.getFio()}" /> 
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </body>
</html>
