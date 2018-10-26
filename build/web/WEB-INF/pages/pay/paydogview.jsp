<%-- 
    Document   : paydogview
    Created on : 20.09.2017, 12:41:44
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
        <title>Договор №<c:out value="${dogovor.getPaydogN()}"/></title>
    </head>
    <body>
    <c:if test="${user != null}">    
        <h3>Договор об оказании платных услуг</h3>
        <form id="payDogForm" action="savepaydog" accept-charset="windows-1251" method="POST">
            <div id="dogovor1">
                <input id="dogId" name="dogId" type="hidden" value="${dogovor.getPaydogId()}">
                Номер: <input type="text" id="dogN1" name="dogN1" value ="${dogovor.getPaydogN()}">
                <br>
                Дата: <input type="date" id="dogD1" name="dogD1" value ="${dogovor.getFormatDateDog()}">
                <br>
                <br>
                <strong>С кем заключен (родитель):</strong> <input type="button" class="btn" id="findDogCl1" value="Найти">
                <input type="hidden" id="dogClId1" name="dogClId1" value="${dogovor.getParentId().getParentId()}">
                <div id="infoDogCl1">                                            
                    <label class="clickable" onclick="javascript:window.open('client?kat=parents&id=' + ${dogovor.getParentId().getParentId()}, '_blank')">
                        <c:out value="${dogovor.getParentId().getFIO()}" />
                    </label>
                    <br> 
                    <c:out value="${dogovor.getParentId().getSprregId().getSprregName()}" />
                    <br>
                    Телефон: <input type="text" id="telephon1" name="telephon1" value="${telephon.getTel()}">                
                </div>
                <br>
                <div id="children">
                    <strong>Ребенок: </strong>
                    <br>
                    <label class="clickable" onclick="javascript:window.open('client?kat=children&id=' + ${dogovor.getChildId().getChildId()}, '_blank')">
                        <c:out value="${dogovor.getChildId().getFIO()}" />
                    </label>
                    <br> 
                    <c:out value="${dogovor.getChildId().getSprregId().getSprregName()}" />
                    <br>
                </div>
                <div id = "usl">
                    <strong>Услуга:</strong>
                    <br>
                    <c:if test="${dogovor.getPayuslId().getSprpayuslId().getSprpayuslTime() != null}">
                        <c:out value="${dogovor.getPayuslId().getSprpayuslId().getSprpayuslName()} ${dogovor.getPayuslId().getSprpayuslId().getSprpayuslTime()} минут (${dogovor.getPayuslId().getSprpayuslId().getSprpayuslPrice()} рублей)" />
                    </c:if>
                    <c:if test="${dogovor.getPayuslId().getSprpayuslId().getSprpayuslTime() == null}">
                        <c:out value="${dogovor.getPayuslId().getSprpayuslId().getSprpayuslName()} (${dogovor.getPayuslId().getSprpayuslId().getSprpayuslPrice()} рублей)" />
                    </c:if>
                </div>
                <div id="payment">    
                    <strong>Оплата:</strong>
                    <br>
                    <c:forEach var="payment" items="${paymentList}">
                        <c:out value="${payment.getRegularFormatDate()} - ${payment.getPaymentSum()} руб." />
                        <br>
                    </c:forEach>
                    <input type="button" id="paymentBtn" value="Внести сведения об оплате" onclick="pay()">
                </div>
            </div>
            <div id="divButton">
                <br>                           
                <input class="btn" type="button" name="saveBtn" id = "saveBtn" value="Сохранить" onclick="validateDog()">            
                <input class="btn" type="button" name="closeBtn" id = "closeBtn" value="Закрыть" onclick="javascript:window.close()">            
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
    <dialog id="paymentDialog">
        <form id="paymentForm" action="savepayment" accept-charset="windows-1251" method="POST">
            <strong>Добавить платеж</strong>
            <input type="hidden" id="dogId" name="dogId" value="${dogovor.getPaydogId()}">
            <br>
            ФИО: <input type="text" id="paymentFio" name="paymentFio">
            <br>
            Дата: <input type="date" id="paymentDate" name="paymentDate">
            <br>
            Сумма: <input type="text" id="paymentSum" name="paymentSum">
            <br>
            <input type="button" id="savePaymentBtn" value="Сохранить" onclick="savePayment()">
            <input type="button" id="cancelPaymentBtn" value="Отмена" onclick="document.getElementById('paymentDialog').close()">
        </form>
    </dialog>
    <script type="text/javascript">
        initDogView();
    </script>
</html>
