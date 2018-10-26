<%-- 
    Document   : add
    Created on : 13.07.2017, 13:43:12
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="payadd0205.js" charset="utf-8"></script>
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Подготовка списка на зачисление</title>
    </head>
    <body>
    <c:if test="${user != null}">    
        <input type="hidden" id="role" value="${user.getRoleId().getRoleName()}">
        <form id="payListForm" action="savepaylist" accept-charset="windows-1251" method="POST">
            <h3>Выберите услугу:</h3>
            <div id="divUsl">            
                <select id="selUsl" onchange="changeUsl()">
                    <option value="0"></option>
                    <c:forEach var="usl" items="${payUslList}">
                        <option value="${usl.getSprpayuslId()}">
                            <c:if test="${usl.getSprpayuslTime() == null}">
                                <c:out value="${usl.getSprpayuslName()} (${usl.getSprpayuslPrice()} рублей)" />
                            </c:if>
                            <c:if test="${usl.getSprpayuslTime() != null}">
                                <c:out value="${usl.getSprpayuslName()} ${usl.getSprpayuslTime()} минут (${usl.getSprpayuslPrice()} рублей)" />
                            </c:if>
                        </option>
                    </c:forEach>
                </select>
                <input type="hidden" id="stat" name="stat" value="">
                <input type="hidden" id="group" name="group" value="">
                <input type="hidden" id="lesson" name="lesson" value="">
                <input type="hidden" id="payUslId" name="payUslId" value="">
            </div>
            <div id="divGroup">            
            </div>
            <div id="divLesson">            
            </div>
            
            <div id="kalendar" style="display:none;">
                <c:set var="a" value="1" />
                <c:forEach var="date" items="${cList}">                
                    <c:if test="${date.getDay() == 1}">
                        <c:if test="${(date.getMonth() != 6) && (date.getMonth() != 12)}">
                            <table class="kalendar" style="float:left; margin-right: 10px">
                        </c:if>
                        <c:if test="${(date.getMonth() == 6) || (date.getMonth() == 12)}">
                            <table class="kalendar">
                        </c:if>
                        <thead>
                            <tr>
                                <td colspan="7" style="text-align: center">
                                    <strong>
                                        <c:out value="${date.getMonthName()}" />
                                    </strong>
                                </td>
                            </tr>
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
                        <c:if test="${(date.getMonth() == 6) || (date.getMonth() == 12)}">
                            <br>
                            <br>
                        </c:if>
                    </c:if>
                </c:forEach> 
            </div>
           
            <div id="divSpecialists">
            </div>
            <div id="divPlusSotr">
            </div>
            <div id = "divClients">
            </div>
            <div id="divPlusClient">
            </div>
            <div id="divButton">
            </div>
        </form>
    </c:if>
    </body>
    <dialog id="searchClDialog" class="searchDlg"> 
        <form id="dialogForm">
            <div id="dialogTitle">
                Категория клиента:
                <select id="selKat" onchange="search()">
                    <option value="children">дети</option>
                    <option value="parents">законные представители</option>
                    <option value="ped">педагоги</option>
                </select>            
            </div>
            <div id="search">Фамилия: <input type="text" name="fam" id="fam" onkeyup="search()">
                Имя: <input type="text" name="nam" id="nam" onkeyup="search()">
                Отчество: <input type="text" name="patr" id="patr" onkeyup="search()">                
            </div>
            <table class="no">
                <tr>                    
                    <td><a class="dial" id="newClient" onclick="newClient()">Новый</a> </td>
                    <td><a class="dial" id="cancelDialog" onclick="cancelDialog()">Отмена</a> </td>
                </tr>
            </table>
            <table class="regular" id="dialogTable">                 
            </table>
            <input type="hidden" name="nomClPp" id="nomClPp">
            <input type="hidden" name="katCl" id="katCl">
            <input type="hidden" name="type" id="type">
        </form>        
    </dialog> 
</html>
