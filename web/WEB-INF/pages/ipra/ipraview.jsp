<%-- 
    Document   : ipraview
    Created on : 31.05.2017, 12:28:44
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="ipra0701.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>ИПРА - ${ipra.getChildId().getChildFam()} ${ipra.getChildId().getChildName()} ${ipra.getChildId().getChildPatr()}</title>
    </head>
    <body>
        <form id="ipraForm" action="saveipra" accept-charset="windows-1251" method="POST">
            <input type="hidden" id="ipraId" name="ipraId" value="${ipra.getIpraId()}">            
            <div id="divForm">
                <strong>
                    Ребенок
                </strong>
                <br>
                <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
                    <input type="button" id="searchBtn" value="Найти" onclick="callSearchDialog()">
                </c:if>
                <br>
                <div id="divInfoChild">
                    <div id="divChild">
                        <input type="hidden" id="childId" name="childId" value="${ipra.getChildId().getChildId()}">
                        <label>
                            <c:out value="${ipra.getChildId().getChildNom()}" />
                        </label>
                        <br>
                        <label class="clickable" onclick="javascript:window.open('client?kat=children&id=' + ${ipra.getChildId().getChildId()}, '_blank')">
                            <c:out value="${ipra.getChildId().getChildFam()} ${ipra.getChildId().getChildName()} ${ipra.getChildId().getChildPatr()} ${ipra.getChildId().getFormat2Dr()}" />
                        </label>
                        <br>
                        <c:out value="${ipra.getChildId().getSprregId().getSprregName()}"/>
                        <br>
                        <br>
                    </div>
                    <div id="divIpra">
                        <strong>ИПРА</strong>
                        <br>
                        <label>Статус: </label>
                        <input type="hidden" id="status" name="status" value="${ipra.getIpraStatus()}">
                        <select id="selStatus">
                            <c:if test="${ipra.getIpraStatus() == 1}">
                                <option selected value="1" >в работе</option>
                                <option value="0">завершена</option>
                            </c:if>
                            <c:if test="${ipra.getIpraStatus() == 0}">
                                <option value="1">в работе</option>
                                <option selected value="0">завершена</option>
                            </c:if>
                        </select>
                        <br>
                        <br>
                        <label>Номер ИПРА: </label>
                        <input type="text" id="ipraN" name="ipraN" value="${ipra.getIpraN()}">
                        <br>
                        <label>Дата экспертизы: </label>
                        <input type="date" id = "expDate" name = "expDate" value="${ipra.getFormatDate(ipra.getIpraDateexp())}">
                        <br>
                        <label>Дата окончания ИПРА: </label>
                        <input type = "date" id = "ipraDateOk" name = "ipraDateOk" value="${ipra.getFormatDate(ipra.getIpraDateok())}">
                        <br>
                        <br>
                        <strong>Исходящее письмо из МСЭ</strong>
                        <br>
                        <label>номер: </label>
                        <input type = "text" id = "ishMseN" name = "ishMseN" value="${ipra.getIpraIshmseN()}">
                        <label> дата: </label>
                        <input type = "date" id = "ishMseD" name = "ishMseD" value="${ipra.getFormatDate(ipra.getIpraIshmseD())}">
                        <br>
                        <br>
                        <strong>Входящее письмо в ДО</strong>
                        <br>
                        <label>номер: </label>
                        <input type = "text" id = "vhDoN" name = "vhDoN" value="${ipra.getIpraVhdoN()}">
                        <label> дата: </label>
                        <input type = "date" id = "vhDoD" name = "vhDoD" value="${ipra.getFormatDate(ipra.getIpraVhdoD())}">
                        <br>
                        <br>
                        <strong>Приказ ДО</strong>
                        <br>
                        <label>номер: </label>
                        <input type = "text" id = "prikazDoN" name = "prikazDoN" value="${ipra.getIpraPrikazN()}">
                        <label> дата: </label>
                        <input type = "date" id = "prikazDoD" name = "prikazDoD" onclick = "prikazDate()" value="${ipra.getFormatDate(ipra.getIpraPrikazD())}" >
                        <br>
                        <br>
                        <label>Дата письма в ОМСУ (п.1.1): </label>
                        <input type = "date" id = "omsuD" name = "omsuD" onclick = "omsuDate()" value="${ipra.getFormatDate(ipra.getIpraPerechD())}" >                    
                        <label>Номер письма в ОМСУ: </label>
                        <input type = "text" id = "omsuN" name = "omsuN" value="${ipra.getIpraOmsuN()}" >   
                        <br>
                        <label>Дата приказа ОМСУ (п.2.1): </label>
                        <input type = "date" id = "prOmsuD" name = "prOmsuD" onclick = "prOmsuDate()" value="${ipra.getFormatDate(ipra.getIpraPrikazOmsuD())}">
                        <br>
                        <label>Дата ознакомления родителей (законных представителей) (п.2.2): </label>
                        <input type = "date" id = "oznakD" name = "oznakD" onclick = "oznakDate()" value="${ipra.getFormatDate(ipra.getIpraOznakom())}">
                        <br>
                        <br>
                        <label>Дата запроса в ТПМПК: </label>
                        <input type = "date" id = "tpmpkD" name = "tpmpkD" onclick = "tpmpkDate()" value="${ipra.getFormatDate(ipra.getIpraTpmpkD())}">
                        <br>
                        <br>                        
                        <strong>Даты отчетов</strong>
                        <input type="button" id="printOtchetBtn" value="Выгрузка отчётов" onclick="clickPrintOtchetBtn()">
                        <br>
                        <label>ОМСУ (п.2.3): </label>
                        <input type = "date" id = "otchOmsu" name = "otchOmsu" onclick = "otchOmsuDate()" value="${ipra.getFormatDate(ipra.getIpraOtchomsu())}" >
                        <br>
                        <label>ОЦППМСП (п.1.2): </label>
                        <input type = "date" id = "otchCenter" name = "otchCenter" onclick = "otchCenterDate()" value="${ipra.getFormatDate(ipra.getIpraOtchcenter())}">                                                
                        Номер письма: <input type = "text" id = "otchCenterN" name = "otchCenterN" value="${ipra.getIpraOtchcenterN()}">                        
                        <br>                        
                        <label>ДО: </label>
                        <input type = "date" id = "otchDo" name = "otchDo" onclick = "otchDoDate()" value="${ipra.getFormatDate(ipra.getIpraOtchdo())}" >
                        <br>
                        <br>
                        <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
                            <input type = "button" id = "saveBtn" value = "Сохранить" onclick="validateIpra()">
                        </c:if>
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                            <input type="button" name="delBtn" id = "delBtn" value="Удалить" onclick="delIpra()">
                        </c:if> 
                        <input type="button" name="closeBtn" id = "closeBtn" value="Закрыть" onclick="javascript:window.close();">
                    </div>
                </div>
            </div>
        </form>
    </body>
    <dialog id="searchClDialog" class="searchDlg">                
        <form id="dialogForm">
            <div id="search">Фамилия: <input type="text" name="fam" id="fam" onkeyup="searchChild()">
                Имя: <input type="text" name="nam" id="nam" onkeyup="searchChild()">
                Отчество: <input type="text" name="patr" id="patr" onkeyup="searchChild()">                
            </div>
            <table class="no">
                <tr>                    
                    <td><a class="dial" id="newClient" onclick="newChild()">Новый</a> </td>
                    <td><a class="dial" id="cancelDialog" onclick="cancelDialog()">Отмена</a> </td>
                </tr>
            </table>
            <table class="regular" id="dialogTable">                 
            </table>
            <input type="hidden" name="nomClPp" id="nomClPp">
        </form>        
    </dialog> 
    <dialog id="ipraOtchetDialog">
        <div style="color : red">
            Внимание! Если есть несохранённые данные, необходимо их предварительно сохранить.
        </div>
        <div id="divOtchet">
            <input type="button" id="" value="Выгрузить отчёт ОЦППМСП" onclick="printOtchetCenter()">
            <input type="button" id="" value="Выгрузить сопроводительное в ДО" onclick="printLetterToDO()">
            <br>
            <br>
            <input type="button" id="" value="Выгрузить отчёт ДО"  onclick="printOtchetDO()">
            <input type="button" id="" value="Выгрузить сопроводительное в МСЭ" onclick="printLetterToMse()">
            <br>
            <br>
            <input type="button" id="" value="Закрыть" onclick="closeOthetDialog()">
        </div>
    </dialog>
    <script type="text/javascript">
        initDates();
    </script>
</html>
