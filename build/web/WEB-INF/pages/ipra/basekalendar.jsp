<%-- 
    Document   : basekalendar
    Created on : 19.05.2017, 12:16:01
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="kalendar0215.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Календарь - Добавить</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <div id="divYear">
                <strong>
                    <c:out value="${year}"/> год
                </strong>            
            </div>
            <div id="kalendar">
                <c:set var="a" value="1" />
                <c:forEach var="date" items="${cList}">                
                    <c:if test="${date.getDay() == 1}">
                        <p>
                            <c:out value="${date.getMonthName()}" />
                        </p>
                        <table class="kalendar">
                            <thead>
                                <tr>
                                    <td><strong>Пн</strong></td>
                                    <td><strong>Вт</strong></td>
                                    <td><strong>Ср</strong></td>
                                    <td><strong>Чт</strong></td>
                                    <td><strong>Пт</strong></td>
                                    <td style="background: lightgreen"><strong>Сб</strong></td>
                                    <td style="background: lightgreen"><strong>Вс</strong></td>
                                </tr>
                            </thead>
                            <tbody>
                            </c:if>
                            <c:if test="${a == 1}">
                                <tr>                                
                                </c:if>
                                <c:set var="flag" value="0" />
                                <c:forEach var="b" begin="1" end="7" step="1">               
                                    <c:if test="${flag == 0}">
                                        <c:if test="${date.getDayOfWeek() == b}">
                                            <c:if test="${date.getIsWeekend()}">
                                                <td class="weekend" id="${date.getDay()}.${date.getMonth()}.${date.getYear()}">
                                                    <c:out value="${date.getDay()}"/>
                                                </td>
                                                <c:set var="flag" value="1" />
                                            </c:if>
                                            <c:if test="${!date.getIsWeekend()}">
                                                <td class="regularday" id="${date.getDay()}.${date.getMonth()}.${date.getYear()}">
                                                    <c:out value="${date.getDay()}"/>
                                                </td>
                                                <c:set var="flag" value="1" />
                                            </c:if>
                                        </c:if>                            
                                    </c:if>
                                    <c:if test="${flag == 0}">
                                        <c:if test="${date.getDayOfWeek() != a}">
                                            <td></td>
                                            <c:set var="a" value="${a + 1}" />
                                        </c:if> 
                                    </c:if>
                                </c:forEach>
                                <c:if test="${a < 8}">
                                    <c:set var="a" value="${a + 1}" />   
                                </c:if>
                                <c:if test="${a == 8}">
                                    <c:set var="a" value="1" />    
                                </tr>
                            </c:if>
                            <c:if test="${date.getIsLastDay()}">
                                <c:set var="a" value="1" />  
                            </tbody>
                        </table>                   
                    </c:if>
                </c:forEach>                 
            </div>
            <div id="btn">
                <input type="button" value="Сохранить календарь" onclick="saveKalendar()">
            </div>
        </c:if>
    </body>
    <script type="text/javascript">
        initbase();
    </script>
</html>
