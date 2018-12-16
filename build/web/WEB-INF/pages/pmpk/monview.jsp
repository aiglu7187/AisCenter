<%-- 
    Document   : monview
    Created on : 07.04.2017, 13:03:36
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="monitoring1007.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Просмотр результатов мониторинга</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <p>Результаты мониторинга</p>            
            <c:out value="${monData.size()}" />
            <table class="regular">
                <thead>
                    <tr>
                        <td>
                        </td>
                        <td>
                            Номер
                        </td>
                        <td>
                            ФИО ребенка
                        </td>
                        <td>
                            Дата рождения
                        </td>
                        <td>
                            ПМПК<br> (дата и № протокола)
                        </td>
                        <td>
                            Заключение
                        </td>
                        <td>
                            Результат мониторинга
                        </td>
                        <td>
                            Причины невыполнения
                        </td>
                    </tr>
                </thead>
                <c:forEach var="mon" items="${monData}">
                    <tr>
                        <td>
                            <c:out value="${mon.getMonitdataId()}" />
                        </td>
                        <td>
                            <c:out value="${mon.getChildId().getChildNom()}" /><br>
                            <c:out value="${mon.getChildId().getChildId()}" />
                        </td>    
                        <td onclick="javascript:window.open('client?kat=children&id=${mon.getChildId().getChildId()}','_blank')">
                            <c:out value="${mon.getChildId().getFIO()}" />
                        </td>
                        <td>
                            <c:out value="${mon.getChildId().getFormat2Dr()}" />
                        </td>
                        <td onclick="javascript:window.open('priyomedit?id=${mon.getPmpkId().getPrclId().getPriyomId().getPriyomId()}','_blank')">
                            <c:out value="${mon.getPmpkId().getPrclId().getPriyomId().getFormatDate()}" /><br> 
                            <c:out value="${mon.getPmpkId().getPmpkNp()}" />
                        </td>
                        <td>
                            <c:if test="${mon.getMonitdataZakl() == 1}">
                                не предъявлено 
                            </c:if>
                            <c:if test="${mon.getMonitdataZakl() == 2}">
                                предъявлено 
                            </c:if>
                        </td>
                        <td onclick="result(this)">
                            <c:out value="${mon.getMonitoringResult()}" />
                        </td>
                        <td>
                            <c:out value="${mon.getMonitdataPrich()}" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div style="height: 500px"></div>
        </c:if>        
    </body>
</html>
