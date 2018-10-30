<%-- 
    Document   : nipra18view
    Created on : 17.10.2018, 14:17:45
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
        <title>ИПРА - <c:out value="${ipra.getChildId().getFIO()}" /></title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
            <form id="ipraForm">
                <input type="hidden" id="ipraId" name="ipraId" value="${ipra.getIpra18Id()}"> 
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
                        <input type="hidden" id="childId" name="childId" value="${ipra.getChildId().getChildId()}">
                        <label><c:out value="${ipra.getChildId().getChildNom()}" /></label>
                        <br>
                        <label class = "clickable" onclick = "window.open('client?kat=children&id='${ipra.getChildId().getChildId()}, '_blank');">
                            <c:out value="${ipra.getChildId().getFIO()} ${ipra.getChildId().getFormat2Dr()}" />
                        </label>
                        <br>
                        <c:out value="${ipra.getChildId().getSprregId().getSprregName()}" />
                        <br>
                        <br>
                    </div>
                </div>
                <div class="tabs">
                    <input id="tab1" type="radio" name="tabs" checked>
                    <label for="tab1" title="Основные данные">Основные данные</label>
                    <input id="tab2" type="radio" name="tabs">                       
                    <label for="tab2" title="Корреспонденция">Корреспонденция</label> 
                    <c:if test="${prikaz != null}">
                        <input id="tab3" type="radio" name="tabs">                       
                        <label for="tab3" title="Приказ ДО">Приказ ДО</label>
                    </c:if>
                    <c:if test="${!perechenList.isEmpty()}">
                        <input id="tab4" type="radio" name="tabs">                       
                        <label for="tab4" title="Перечни мероприятий">Перечни мероприятий</label>
                    </c:if>
                    <input id="tab5" type="radio" name="tabs">                       
                    <label for="tab5" title="Отчёты">Отчёты</label>
                    <section id="content-tab1">
                        <div id="divIpra">                            
                            <p class="stp">
                            <table class="noborder">
                                <tr>
                                    <td>
                                        Номер ИПРА: 
                                    </td>
                                    <td>
                                        <input type="text" id="ipraN" name="ipraN" maxlength="50" style="width: 150px;" value="${ipra.getIpra18N()}">
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Номер протокола экспертизы:
                                    </td>
                                    <td>
                                        <input type="text" id="ipraNexp" name="ipraNexp" maxlength="50" style="width: 150px;" value="${ipra.getIpra18Nexp()}">
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Дата экспертизы:
                                    </td>
                                    <td>
                                        <input type="date" id="ipraDateExp" name="ipraDateExp" style="width: 150px; height: 20px;" value="${ipra.getFormatDate(ipra.getIpra18Dateexp())}">
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Дата окончания ИПРА
                                    </td>
                                    <td>
                                        <input type="date" id="ipraDateOk" name="ipraDateOk" style="width: 150px; height: 20px;" value="${ipra.getFormatDate(ipra.getIpra18Dateok())}">
                                    </td>
                                </tr>
                            </table>
                            </p>
                            <p class="stp">
                                Бюро МСЭ:
                                <select id="selMse">
                                    <option value="0"></option>
                                    <c:forEach var="mse" items="${mseList}">
                                        <c:if test="${ipra.getSprmseId() == mse}">
                                            <option selected value="${mse.getSprmseId()}">
                                                <c:out value="${mse.getSprmseShname()}" />
                                            </option>
                                        </c:if>
                                        <c:if test="${ipra.getSprmseId() != mse}">
                                            <option value="${mse.getSprmseId()}">
                                                <c:out value="${mse.getSprmseShname()}" />
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <input type="hidden" id="mseId" name="mseId" value="${ipra.getSprmseId().getSprmseId()}">
                            </p>        
                            <p class="stp">
                                <strong>Входящее письмо в ДО</strong>
                                <br>
                                номер: <input type="text" id="ipraVhToDON" name="ipraVhToDON" maxlength="50" style="width: 150px;" value="${ipra.getIpra18VhtodoN()}"> 
                                дата: <input type="date" id="ipraVhToDOD" name="ipraVhToDOD" style="height: 20px;" value="${ipra.getFormatDate(ipra.getIpra18VhtodoD())}">
                            </p>
                            <p class="stp">
                                <strong>Даты отчётов</strong>
                            <table class="noborder">
                                <tr>
                                    <td>
                                        ОМСУ: 
                                    </td>
                                    <td>
                                        <input type="date" id="ipraOtchomsu" name="ipraOtchomsu" style="width: 150px; height: 20px;" value="${ipra.getFormatDate(ipra.getIpra18Otchomsu())}">
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        ОЦППМСП: 
                                    </td>
                                    <td>
                                        <input type="date" id="ipraOtchcenter" name="ipraOtchcenter" style="width: 150px; height: 20px;" value="${ipra.getFormatDate(ipra.getIpra18Otchcenter())}">
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        ДО: 
                                    </td>
                                    <td>
                                        <input type="date" id="ipraOtchdo" name="ipraOtchdo" style="width: 150px; height: 20px;" value="${ipra.getFormatDate(ipra.getIpra18Otchdo())}">
                                    </td>
                                </tr>
                            </table>
                            </p>
                        </div>
                    </section>
                    <section id="content-tab2">
                        <div id="divVh">
                            <strong>Входящая корреспонденция</strong>
                            <table class="regularlist">
                                <thead>
                                    <tr>
                                        <td>
                                            ИД
                                        </td>
                                        <td>
                                            Дата
                                        </td>
                                        <td>
                                            Номер
                                        </td>
                                        <td>
                                            От кого
                                        </td>
                                        <td>
                                            Тема
                                        </td>
                                        <td>
                                            Дата исходящего
                                        </td>
                                        <td>
                                            Номер исходящего
                                        </td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="vh" items="${vhCorr}">
                                        <tr>
                                            <td>
                                                <c:out value="${vh.getId()}"/>
                                            </td>
                                            <td>
                                                <c:out value="${vh.getFormat2Date()}"/>
                                            </td>
                                            <td>
                                                <c:out value="${vh.getNomer()}"/>
                                            </td>
                                            <td>
                                                <c:out value="${vh.getSender()}"/>
                                            </td>
                                            <td>
                                                <c:out value="${vh.getTheme()}"/>
                                            </td>
                                            <td>
                                                <c:out value="${vh.getFormat2IshDate()}"/>
                                            </td>
                                            <td>
                                                <c:out value="${vh.getIshNomer()}"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div id="divIsh">
                            <strong>Исходящая корреспонденция</strong>
                            <table class="regularlist">
                                <thead>
                                    <tr>
                                        <td>
                                            ИД
                                        </td>
                                        <td>
                                            Дата
                                        </td>
                                        <td>
                                            Номер
                                        </td>
                                        <td>
                                            Кому
                                        </td>
                                        <td>
                                            Тема
                                        </td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ish" items="${ishCorr}">
                                        <tr>
                                            <td>
                                                <c:out value="${ish.getId()}"/>
                                            </td>
                                            <td>
                                                <c:out value="${ish.getFormat2Date()}"/>
                                            </td>
                                            <td>
                                                <c:out value="${ish.getNomer()}"/>
                                            </td>
                                            <td>
                                                <c:out value="${ish.getRecipient()}"/>
                                            </td>
                                            <td>
                                                <c:out value="${ish.getTheme()}"/>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </section>
                    <c:if test="${prikaz != null}">
                        <section id="content-tab3">
                            <div id="divPrikaz">
                                <table class="noborder">
                                    <tr>
                                        <td>
                                            Дата приказа:                                
                                        </td>
                                        <td>
                                            <input type="date" value="${prikaz.getFormatDate()}" style="height: 20px;">                                
                                        </td>
                                    </tr>
                                    <c:if test="${prikaz.getIpra18prikazN() != null}">
                                        <tr>
                                            <td>
                                                Номер:
                                            </td>
                                            <td>
                                                <input type="text" value="${prikaz.getIpra18prikazN()}" style="width: 150px;">
                                            </td>
                                        </tr>
                                    </c:if>
                                </table>
                            </div>
                        </section>
                    </c:if>
                    <c:if test="${perechenList != null}">
                        <section id="content-tab4">
                            <div id="divPerechen">
                                <c:forEach var="perechen" items="${perechenList}">
                                    <table class="noborder">                                    
                                        <tr>
                                            <c:if test="${perechen.getIshcorrId().getIshcorrN() == null}">
                                                <td>
                                                    Дата отправки перечня:                                
                                                <td>
                                                    <input type="date" value="${perechen.getIshcorrId().getFormatDate(perechen.getIshcorrId().getIshcorrD())}" style="height: 20px;">                                
                                                </td>

                                                </td>
                                            </c:if>
                                            <c:if test="${perechen.getIshcorrId().getIshcorrN() != null}">
                                                <td>
                                                    Отправлен в ОМСУ 
                                                    <strong>
                                                        <c:out value="${perechen.getIshcorrId().getFormat2Date(perechen.getIshcorrId().getIshcorrD())}"/>
                                                    </strong>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </table>
                                </c:forEach>
                            </div>
                        </section>
                        <selection id="content-tab5">
                            <div id="divOtchet">
                            </div>
                        </section>
                    </c:if>
                    <p style="margin-top: 15px; margin-bottom: 15px;">
                        <a class="greybtn" id="saveBtn" name="saveBtn">
                            <img id="saveImg" name="saveImg" src="img/save.png" width="17" style="padding-right: 1px;">
                            Сохранить
                        </a>  
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                            <a class="greybtn" id="deleteBtn" name="deleteBtn" style="margin-left: 30px">
                                <img id="deleteImg" name="deleteImg" src="img/delete.png" width="17" style="padding-right: 1px;" >
                                Удалить
                            </a>    
                        </c:if>                                           
                    </p>
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
    </c:if>
</html>

