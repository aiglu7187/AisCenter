<%-- 
    Document   : priyom
    Created on : 14.02.2016, 16:15:56
    Author     : Aiglu
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="spisokpriyom0428.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Список приема</title>
    </head>
    <body>
        <c:if test="${user != null}">
            <p id="sotrP">
                <c:if test="${user.getRoleId().getRoleName().equals('administrator')||user.getRoleId().getRoleName().equals('registrator')}">
                    <c:if test="${sotr.equals('sotr')}">
                        Выберите сотрудника: 
                        <select id="sotrud"  onchange="sotrchange()">   
                            <option value="0"></option>
                            <c:forEach var="s" items="${sotrListPriyom}">
                                <c:if test="${(s.getSotruddolgnActive() == 0)||(s.getSotrudId().getSotrudActive() == 0)}">
                                    <option style="color: lightgrey;" value="${s.getSotruddolgnId()}">
                                        <c:out value="${s.getSotrudId().getSotrudFIO()} (${s.getSprdolgnId().getSprdolgnName()})" />
                                    </option>
                                </c:if>
                                <c:if test="${(s.getSotruddolgnActive() == 1)&&(s.getSotrudId().getSotrudActive() == 1)}">--%>
                                    <option value="${s.getSotruddolgnId()}">
                                        <c:out value="${s.getSotrudId().getSotrudFIO()} (${s.getSprdolgnId().getSprdolgnName()})" />
                                    </option>
                                </c:if>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="sotrdolgnId" id="sotrdolgnId" value="0">
                    </c:if>
                </c:if>
                <c:if test="${user.getRoleId().getRoleName().equals('specialist')||user.getRoleId().getRoleName().equals('ipra')}">
                    <c:if test="${sotr.equals('sotr')}">
                        Выберите сотрудника: 
                        <select id="sotrud" onchange="sotrchange()">   
                            <c:forEach var="s" items="${sotrListPriyom}">
                                <c:if test="${s.getSotrudId().equals(user.getSotrudId())}" >
                                    <option value="${s.getSotruddolgnId()}">
                                        <c:out value="${s.getSotrudId().getSotrudFIO()} (${s.getSprdolgnId().getSprdolgnName()})" />
                                    </option>                
                                </c:if>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="sotrdolgnId" id="sotrdolgnId" value="0">
                    </c:if>
                </c:if>
            </p>
            <p>Поиск</p>
            <form>
                <p>Период проведения с: <input type="date" name="datepr1" id="datepr1">
                    по: <input type="date" name="datepr2" id="datepr2">                
                    <br>
                    Услуга: 
                    <select id="uslSel">       
                        <option value="0">Все</option>
                        <c:forEach var="usl" items="${uslListPriyom}">
                            <option value="${usl.getSpruslId()}">
                                <c:out value="${usl.getSpruslName()}"/>
                            </option>
                        </c:forEach>
                    </select>                        
                    Район: 
                    <select id="regSel">
                        <option value="0">Все</option>
                        <c:forEach var="reg" items="${regListPriyom}">
                            <option value="${reg.getSprregId()}">
                                <c:out value="${reg.getSprregName()}"/>
                            </option>
                        </c:forEach>                    
                    </select>                
                </p>
                <input type="button" id="searchBtn" name="searchBtn" value="Поиск" onclick="searchPriyom()">
                <p class="kol" id="kol"></p>
            </form>
            <table class="regular" id="priyomTable"> 
                <col class="c1"><col span="2">
            </table>
            <dialog id="otchetDialog">
                <input type="hidden" id="otchetType" name="otchetType">
                <p>Отчет по сотруднику за период 
                    <br>с: <input type="date" name="date1" id="date1">
                    по: <input type="date" name="date2" id="date2">
                </p> 
                <input type="button" value="Получить отчет" onclick="print()">
            </dialog>
            <script type="text/javascript">
                init();
            </script>
        </c:if>
    </body>
</html>
