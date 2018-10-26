<%-- 
    Document   : payviewfinal
    Created on : 23.09.2017, 12:58:01
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
        <title><c:out value="${payUsl.getSprpayuslId().getSprpayuslName()} ${payUsl.getPayuslName()}" /></title>
    </head>
    <body>
    <c:if test="${user != null}">
        <form id="payPosForm" action="savepaypos" accept-charset="windows-1251" method="POST">
            <h3><c:out value="${payUsl.getSprpayuslId().getSprpayuslName()}" />
                <br>
                <c:if test="${payUsl.getSprpayuslId().getSprpayuslTime() != null}">
                    <c:out value="${payUsl.getSprpayuslId().getSprpayuslTime()} минут" />
                    <br>
                </c:if>
                <c:if test="${payUsl.getPayuslName() != null}">
                    "<c:out value="${payUsl.getPayuslName()}" />"
                </c:if>
            </h3>
            <input type="hidden" id="payUslId" name="payUslId" value="${payUsl.getPayuslId()}">
            <div id="divSpecialists">
                <strong>Специалисты:</strong> <br>
                <c:forEach var="sp" items="${payUsl.getPayuslSotrudCollection().toArray()}">
                    <c:out value="${sp.getSotrudId().getSotrudFIO()} (${sp.getSotrudId().getSprdolgnId().getSprdolgnName()})" />
                    <br>
                </c:forEach>
                <br>
            </div>
            <div id="divClients">
                <table id="listTable" class="unclickable">
                    <thead>
                    <td>
                    </td>
                    <td>
                        Дети
                    </td>
                    <c:forEach var="date" items="${payUsl.getPayuslLessonCollection().toArray()}">
                        <td>
                            <c:out value="${date.getRegularFormatLessonDate()}" />
                        </td>
                    </c:forEach>
                    </thead>
                    <tbody>
                        <c:forEach var="child" items="${payUslChildren}">
                            <tr>
                                <td>
                                    <c:out value="${child.getChildId()}" />
                                </td>
                                <td>
                                    <label class="clickable" onclick="javascript:window.open('client?kat=children&id=' + ${child.getChildId()}, '_blank')">
                                        <c:out value="${child.getFIO()}" />
                                    </label>
                                    <br>
                                    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                                        <c:forEach var="dog" items="${dogovors}">
                                            <label class="clickable" onclick="javascript:window.open('payaction?action=dogview&id=' + ${dog.getPaydogId()}, '_blank')">
                                                <c:if test="${dog.getChildId() == child}">
                                                    <c:out value="(договор №${dog.getPaydogN()} от ${dog.getRegularFormatDateDog()})" />
                                                </c:if>
                                            </label>
                                        </c:forEach>
                                    </c:if>
                                </td>
                                <c:forEach var="date" items="${payUsl.getPayuslLessonCollection().toArray()}">
                                    <td>                                            
                                        <c:forEach var="pos" items="${poses}">
                                            <c:if test="${(pos.getPayuslclientId().getPayuslclientKatcl().equals('children')) 
                                                          && (pos.getPayuslclientId().getClientId() == child.getChildId())}">
                                                <c:if test="${pos.getPayusllessonId() == date}">
                                                    <select id="pos${pos.getPayusllessonId().getPayusllessonId()}_${child.getChildId()}" name="pos${pos.getPayusllessonId().getPayusllessonId()}_${child.getChildId()}">
                                                        <option value="0"></option>
                                                        <c:forEach var="sprpos" items="${sprPos}">
                                                            <c:if test="${pos.getSprposId() == sprpos}" >
                                                                <option selected value="${sprpos.getSprposId()}">
                                                                    <c:out value="${sprpos.getSprposName()}"/>
                                                                </option>
                                                            </c:if>
                                                            <c:if test="${pos.getSprposId() != sprpos}" >
                                                                <option value="${sprpos.getSprposId()}">
                                                                    <c:out value="${sprpos.getSprposName()}"/>
                                                                </option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>                                                    
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <br>
            </div>
        </form>        
        <div id="divButton">
            <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                <input type="button" class="btn" value="Дозачисление" id="inclBtn" onclick="includeOne()">
                <input type="button" class="btn" value="Отчисление" id="expBtn" onclick="expOne()">
                <br>
                <br>
            </c:if>
            <input type="button" class="btn" value="Сохранить" id="saveBtn" onclick="savePos()">
            <input type="button" class="btn" value="Закрыть" id="closeBtn" onclick="javascript:window.close()">
        </div>
    </c:if>
    </body>
    <dialog id="inclDialog">
        <form id="inclForm" action="saveinclone" accept-charset="windows-1251" method="POST">
            Дата зачисления: <input type="date" name="inclDate" id="inclDate">
            <br>
            <input type="button" id="searchBtn" value="Найти" onclick="searchIncl()">
            <div id="divClient">
                <input type="hidden" name="clId" id="clId">
                <div id="divClientInfo">
                </div> 
            </div>
            <input type="button" id="inclBtnOk" value="Зачислить" onclick="incl()">
            <input type="button" id="btnCloseIncl" value="Закрыть" onclick="closeInclDialog()">
        </form>
    </dialog>
    <dialog id="expDialog">
        Дата отчисления: <input type="date" name="expDate" id="expDate">
        <br>
        <c:forEach var="child" items="${payUslChildren}">
            <label>
                <input type="checkbox" id="chb${child.getChildId()}" value="${child.getChildId()}">
                <c:out value="${child.getFIO()}" />
            </label>
            <br>
        </c:forEach>
        <input type="button" id="expBtnOk" value="Отчислить" onclick="expd()">
        <input type="button" id="btnCloseExp" value="Закрыть" onclick="closeExpDialog()">
    </dialog>
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
            <input type="hidden" id="katCl" name="katCl">
            <input type="hidden" id="type">
        </form>        
    </dialog> 
</html>
