<%-- 
    Document   : pupmain
    Created on : Jan 12, 2016, 4:45:35 PM
    Author     : admin_ai
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Сопровождение</title>
    </head>
    <body>
    <c:if test="${user != null}">
        <h3>Основные услуги</h3>
        <table class="puptab">
            <tr>            
            <c:forEach var="Osnusl" items="${osnuslList}" >
                <td onclick="window.open('priyom?id=${Osnusl.getSprosnuslId()}', '_blank')">
                    <c:out  value="${Osnusl.getSprosnuslName()}"/>                     
                </td>
            </c:forEach>
            </tr>
        </table>
        <h3>Списки</h3>
        <table class="puptab">
            <tr>
                <td onclick="window.open('spisok?id=children', '_blank')">
                    Дети
                </td>
                <td onclick="window.open('spisok?id=parents', '_blank')">
                    Законные представители
                </td>                    
                <td onclick="window.open('spisok?id=ped', '_blank')">
                    Педагоги
                </td>
            </tr>
            <tr>
                <td colspan="3" onclick="window.open('clientusl', '_blank')">
                    Клиенты по услугам
                </td>                    
            </tr>
        </table>
        <h3>Просмотр приема</h3>
        <table class="puptab">
            <tr>
                <td onclick="window.open('spisokpriyom?id=all', '_blank')">
                    Список приема
                </td>                            
                <td onclick="window.open('spisokpriyom?id=sotr', '_blank')">
                    Список приема по сотрудникам
                </td>                                            
            </tr>
        </table>
    </c:if>
    </body>
</html>
