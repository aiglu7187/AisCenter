<%-- 
    Document   : addlist
    Created on : 31.07.2017, 11:40:44
    Author     : Aiglu
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Списки на зачисление</title>
    </head>
    <body>
    <c:if test="${user != null}">
        <h3>Списки на зачисление</h3>
        <input class="btn" type="button" id="addbtn" name="addbtn" value="Добавить список" onclick="javascript:window.open('payaction?action=add', '_blank')">
        <br>
        <br>
        <table class="regular">
            <thead>
                <tr>
                    <td></td>
                    <td>Услуга</td>
                    <td>Специалисты</td>                 
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${addPayUslList}">
                    <tr onclick="javascript: window.open('payaction?action=view&id=' + ${p.getPayuslId()}, '_blank')">
                        <td>
                            <c:out value="${p.getPayuslId()}" />
                        </td>
                        <td>
                            <c:if test="${p.getSprpayuslId().getSprpayuslTime() == null}">
                                <c:out value="${p.getSprpayuslId().getSprpayuslName()} (${p.getSprpayuslId().getSprpayuslPrice()} рублей)" />
                            </c:if>
                            <c:if test="${p.getSprpayuslId().getSprpayuslTime() != null}">
                                <c:out value="${p.getSprpayuslId().getSprpayuslName()} ${p.getSprpayuslId().getSprpayuslTime()} минут (${p.getSprpayuslId().getSprpayuslPrice()} рублей)" />
                            </c:if>
                        </td>
                        <td>
                            <c:forEach var="s" items="${payuslSotrud}">
                                <c:if test="${p == s.getPayuslId()}">
                                    <c:out value="${s.getSotrudId().getSotrudFIO()}" />
                                    <br>
                                </c:if>
                            </c:forEach>
                        </td>                        
                    </tr>                        
                </c:forEach>

            </tbody>
        </table>
    </c:if>
    </body>
</html>

