<%-- 
    Document   : payusllist
    Created on : 21.09.2017, 12:31:45
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Списки зачисленных на платные услуги</title>
    </head>
    <body>
    <c:if test="${user != null}">
        <c:if test="${payUslList.get(0).getSprpayuslId().getSprpayuslGroup() == 1}">
            <h3>Списки зачисленных на групповые платные услуги</h3>
        </c:if>
            <c:if test="${payUslList.get(0).getSprpayuslId().getSprpayuslGroup() == 0}">
            <h3>Списки зачисленных на индивидуальные платные услуги</h3>
        </c:if>
        <br>
        <table class="regular">
            <thead>
                <tr>
                    <td></td>
                    <td>Услуга</td>
                    <td>Специалисты</td>                 
                    <td>Дата зачисления</td>                 
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${payUslList}">
                    <tr onclick="javascript: window.open('payaction?action=viewfinal&id=' + ${p.getPayuslId()}, '_blank')">
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
                            <br>
                            <c:out value="${p.getPayuslName()}"/>
                        </td>
                        <td>
                            <c:forEach var="s" items="${payuslSotrud}">
                                <c:if test="${p == s.getPayuslId()}">
                                    <c:out value="${s.getSotrudId().getSotrudFIO()}" />
                                    <br>
                                </c:if>
                            </c:forEach>
                        </td>        
                        <td>
                            <c:out value="${p.getRegularFormatDate()}" />                            
                        </td>
                    </tr>                        
                </c:forEach>

            </tbody>
        </table>
    </c:if>
    </body>
</html>
