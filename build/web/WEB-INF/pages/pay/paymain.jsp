<%-- 
    Document   : paymain
    Created on : 13.07.2017, 12:04:37
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Платные услуги</title>
    </head>
    <body>
    <c:if test="${user != null}">
        <h3>Зачисление и договоры</h3>
        <table class="puptab">
            <tr>
                <td onclick="window.open('payaction?action=addlist', '_blank')">
                    Списки на зачисление
                </td>
                <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                    <td onclick="window.open('payaction?action=dogreestr', '_blank')">
                        Реестр договоров
                    </td>    
                </c:if>
            </tr>            
        </table>
        <h3>Услуги</h3>
        <table class="puptab">
            <tr>
                <td onclick="window.open('payaction?action=tolist&group=0', '_blank')">
                    Индивидуальные услуги
                </td>
                <td onclick="window.open('payaction?action=tolist&group=1', '_blank')">
                    Групповые услуги
                </td>
            </tr>            
        </table>
    </c:if>
    </body>
</html>
