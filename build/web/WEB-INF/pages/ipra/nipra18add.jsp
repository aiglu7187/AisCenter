<%-- 
    Document   : nipra18add
    Created on : 09.10.2018, 20:13:19
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="nipra18_1011.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Добавить ИПРА 2018</title>
    </head>
    <body>
        <form id="ipraForm">
            <h3>Индивидуальная программа реабилитации и абилитации</h3>
            <div id="divChild">
                <strong>Ребёнок-инвалид:</strong><br>
                <p style="margin-top: 15px; margin-bottom: 15px;">
                    <a class="greybtn" id="searchBtn" name="searchBtn" onclick="openDlg()">
                        <img id="searchImg" name="searchImg" src="img/search.png" width="24" style="margin-right: 3px;">
                        Найти
                    </a>
                </p>  
                <div id="divInfoChild">
                </div>
            </div>
            <div class="tabs">
                <input id="tab1" type="radio" name="tabs" checked>
                <label for="tab1" title="Данные">Данные</label>    
                <section id="content-tab1">
                    <div id="divIpra">
                        <p class="stp">
                        <table class="noborder">
                            <tr>
                                <td>
                                    Номер ИПРА: 
                                </td>
                                <td>
                                    <input type="text" id="ipraN" name="ipraN" maxlength="50" style="width: 150px;">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Номер протокола экспертизы:
                                </td>
                                <td>
                                    <input type="text" id="ipraNexp" name="ipraNexp" maxlength="50" style="width: 150px;">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Дата экспертизы:
                                </td>
                                <td>
                                    <input type="date" id="ipraDateExp" name="ipraDateExp" style="width: 150px; height: 20px;">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Дата окончания ИПРА
                                </td>
                                <td>
                                    <input type="date" id="ipraDateOk" name="ipraDateOk" style="width: 150px; height: 20px;">
                                </td>
                            </tr>
                        </table>
                        </p>
                        <p class="stp">
                            Бюро МСЭ:
                            <select id="selMse">
                                <option value="0"></option>
                                <c:forEach var="mse" items="${mseList}">
                                    <option value="${mse.getSprmseId()}">
                                        <c:out value="${mse.getSprmseShname()}" />
                                    </option>
                                </c:forEach>
                            </select>
                            <input type="hidden" id="mseId" name="mseId" >
                        </p>        
                        <p class="stp">
                            <strong>Входящее письмо в ДО</strong>
                            <br>
                            номер: <input type="text" id="ipraVhToDON" name="ipraVhToDON" maxlength="50" style="width: 150px;"> 
                            дата: <input type="date" id="ipraVhToDOD" name="ipraVhToDOD" style="height: 20px;">
                        </p>                        
                        <p class="stp">
                            <strong>Даты отчётов</strong>
                        <table class="noborder">
                            <tr>
                                <td>
                                    ОМСУ: 
                                </td>
                                <td>
                                    <input type="date" id="ipraOtchomsu" name="ipraOtchomsu" style="width: 150px; height: 20px;">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    ОЦППМСП: 
                                </td>
                                <td>
                                    <input type="date" id="ipraOtchcenter" name="ipraOtchcenter" style="width: 150px; height: 20px;">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    ДО: 
                                </td>
                                <td>
                                    <input type="date" id="ipraOtchdo" name="ipraOtchdo" style="width: 150px; height: 20px;">
                                </td>
                            </tr>
                        </table>
                        </p>
                    </div>
                    <p style="margin-top: 15px; margin-bottom: 15px;">
                        <a class="greybtn" id="saveBtn" name="saveBtn">
                            <img id="saveImg" name="saveImg" src="img/save.png" width="17" style="padding-right: 1px;">
                            Сохранить
                        </a>                    
                    </p>
                </section>
            </div>    
        </form>
    </body>
    <dialog class="searchDlg" id="searchDlg">
        <div id="search">
            <br>
            Фамилия: <input type="text" name="fam" id="fam" onkeyup="searchChild()" maxlength="100">
            Имя: <input type="text" name="nam" id="nam" onkeyup="searchChild()" maxlength="100">
            Отчество: <input type="text" name="patr" id="patr" onkeyup="searchChild()" maxlength="100">                
        </div>
        <table class="no">
            <tr style="height: 60px;">                    
                <td>
                    <a class="greybtn" id="newChildDlgBtn" name="newChildDlgBtn" onclick="newChild()">
                        <img id="newChildDlgImg" name="newChildDlgImg" src="img/plus.png" width="16" style="margin-right: 2px; margin-top: 10px;">
                        Новый
                    </a>
                </td>
                <td>
                    <a class="greybtn" id="cancelDlgBtn" name="cancelDlgBtn" onclick="closeDlg()">
                        <img id="cancelDlgImg" name="cancelDlgImg" src="img/delete.png" width="16" style="margin-right: 2px; margin-top: 10px;">
                        Отмена
                    </a> 
                </td>
            </tr>
        </table>
        <table class="regularlist" id="dlgTable">   
            <thead id="dlgTabHead">
            </thead>
            <tbody id="dlgTabBody">
            </tbody>
        </table>
    </dialog>
    <dialog class="smallDlg" id="letterRegDlg">
        <div>
            <strong>Регистрация входящего письма</strong>
            <br>
            Отправитель: 
            <select id="selSender"></select>
            <br>
            Дата: <input type="date" id="letterDate" name="letterDate">
            Номер: <input type="text" id="letterNom" name="letterNom" disabled>
            <br>
            <a class="greybtn" id="letterRegBtn" name="letterRegBtn" onclick="registration()">
                <img id="letterRegImg" name="letterRegImg" src="img/reg.png" width="16" style="margin-right: 2px; margin-top: 10px;">
                Новый
            </a>
        </div>
    </dialog>
    <script type="text/javascript">
        init();
    </script>
</html>
