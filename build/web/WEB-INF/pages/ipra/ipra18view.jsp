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
        <script type="text/javascript" src="ipra18_1015.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>ИПРА - ${ipra.getChildId().getChildFam()} ${ipra.getChildId().getChildName()} ${ipra.getChildId().getChildPatr()}</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
            <form id="ipraForm">                
                <input type="hidden" id="ipraId" name="ipraId" value="${ipra.getIpra18Id()}">            
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
                            <input type="hidden" id="regId" name="regId" value="${ipra.getChildId().getSprregId().getSprregId()}">
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

                        <%-- вкладки --%>
                        <div class="tabs">
                            <input id="tab1" type="radio" name="tabs" checked>
                            <label for="tab1" title="Основные данные">Основные данные</label>
                            <input id="tab2" type="radio" name="tabs">
                            <label for="tab2" title="Перечень мероприятий">Перечень мероприятий</label>

                            <section id="content-tab1">
                                <div id="divIpra">                                    
                                    <label>Статус: </label>
                                    <input type="hidden" id="status" name="status" value="${ipra.getIpra18Status()}">
                                    <select id="selStatus">
                                        <c:if test="${ipra.getIpra18Status() == 1}">
                                            <option selected value="1" >в работе</option>
                                            <option value="0">завершена</option>
                                        </c:if>
                                        <c:if test="${ipra.getIpra18Status() == 0}">
                                            <option value="1">в работе</option>
                                            <option selected value="0">завершена</option>
                                        </c:if>
                                    </select>                                    
                                    <br>
                                    <br>
                                    <strong>ИПРА</strong>
                                    <br>
                                    <label>Номер ИПРА: </label>
                                    <input type="text" id="ipraN" name="ipraN" value="${ipra.getIpra18N()}">
                                    <br>
                                    <label>Номер протокола экспертизы: </label>
                                    <input type="text" id="ipraNexp" name="ipraNexp" value="${ipra.getIpra18Nexp()}">
                                    <br>
                                    <label>Дата экспертизы: </label>
                                    <input type="date" id = "expDate" name = "expDate" value="${ipra.getFormatDate(ipra.getIpra18Dateexp())}">
                                    <br>
                                    <label>Дата окончания ИПРА: </label>
                                    <input type = "date" id = "ipraDateOk" name = "ipraDateOk" value="${ipra.getFormatDate(ipra.getIpra18Dateok())}">
                                    <br>                    
                                    <br>
                                    Бюро МСЭ: 
                                    <input type="hidden" name="mseId" id="mseId" value="${ipra.getSprmseId().getSprmseId()}">
                                    <select id = "selMse" name = "selMse" onchange = "mseSelect()">
                                        <c:forEach var="mse" items="${sprmseList}" >
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
                                    <br>                    
                                    <br>
                                    <strong>Исходящее письмо из МСЭ</strong>
                                    <br>
                                    <label>номер: </label>
                                    <input type = "text" id = "ishMseN" name = "ishMseN" value="${ipra.getIpra18IshmseN()}">
                                    <label> дата: </label>
                                    <input type = "date" id = "ishMseD" name = "ishMseD" value="${ipra.getFormatDate(ipra.getIpra18IshmseD())}">
                                    <br>
                                    <br>         
                                    <strong>Запрос от ОМСУ </strong>
                                    <br>
                                    <input type = "hidden" id = "omsuId" name = "omsuId" value="${ipraPrikaz.getSpromsuId().getSpromsuId()}">
                                    <select id = "selOmsu" onchange = "omsuSelect()">
                                        <c:forEach var="omsu" items="${spromsuList}">
                                            <c:if test="${omsu.equals(ipraPrikaz.getSpromsuId())}">
                                                <option selected value="${omsu.getSpromsuId()}">
                                                    <c:out value="${omsu.getSpromsuName()}"/>
                                                </option>
                                            </c:if>
                                            <c:if test="${!omsu.equals(ipraPrikaz.getSpromsuId())}">
                                                <option value="${omsu.getSpromsuId()}">
                                                    <c:out value="${omsu.getSpromsuName()}"/>
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <br>
                                    номер:
                                    <input type = "text" id = "omsuReqN" name = "omsuReqN" value="${ipraPrikaz.getIpra18prikazReqN()}" >
                                    дата: 
                                    <input type = "date" id = "omsuReqD" name = "omsuReqD" value="${ipra.getFormatDate(ipraPrikaz.getIpra18prikazReqD())}" >
                                    <br>
                                    <br>
                                    <strong>Входящее письмо в ДО из ОМСУ</strong>
                                    <br>
                                    <label>номер: </label>
                                    <input type = "text" id = "vhDoN" name = "vhDoN" value="${ipra.getIpra18VhdoN()}">
                                    <label> дата: </label>
                                    <input type = "date" id = "vhDoD" name = "vhDoD" value="${ipra.getFormatDate(ipra.getIpra18VhdoD())}">
                                    <br>
                                    <br>
                                    <strong>Приказ ДО</strong>
                                    <br>
                                    <label>номер: </label>
                                    <input type = "text" id = "prikazDoN" name = "prikazDoN" value="${ipraPrikaz.getIpra18prikazDoN()}">
                                    <label> дата: </label>
                                    <input type = "date" id = "prikazDoD" name = "prikazDoD" onclick = "prikazDate()" value="${ipra.getFormatDate(ipraPrikaz.getIpra18prikazDoD())}" >
                                    <br>
                                    <input type="button" id="printPrikazBtn" value="Выгрузка приказа" onclick="clickPrintPrikazBtn()">
                                    <input type="hidden" id="printFlag" value="0">
                                    <br>
                                    <br>
                                    <strong>ТПМПК </strong>
                                    <br>
                                    <input type = "hidden" id = "tpmpkId" name = "tpmpkId" value="${ipraPrikaz.getSprotherpmpkId().getSprotherpmpkId()}">
                                    <select id="selTpmpk" onchange="tpmpkSelect()">
                                        <option value = "0">                                
                                        </option>
                                        <c:forEach var = "tpmpk" items="${sprotherpmpkList}">
                                            <c:if test="${tpmpk.equals(ipraPrikaz.getSprotherpmpkId())}">
                                                <option selected value="${tpmpk.getSprotherpmpkId()}">
                                                    <c:out value="${tpmpk.getSprotherpmpkShname()}" />
                                                </option>
                                            </c:if>
                                            <c:if test="${!tpmpk.equals(ipraPrikaz.getSprotherpmpkId())}">
                                                <option value="${tpmpk.getSprotherpmpkId()}">
                                                    <c:out value="${tpmpk.getSprotherpmpkShname()}" />
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                    <br>
                                    Дата запроса в ТПМПК: 
                                    <c:if test="${ipraPrikaz.getSprotherpmpkId() == null}">
                                        <input type="date" id="tpmpkD" name="tpmpkD" disabled="disabled" onclick="tpmpkDate()">
                                    </c:if>
                                    <c:if test="${ipraPrikaz.getSprotherpmpkId() != null}">
                                        <input type="date" id="tpmpkD" name="tpmpkD" value="${ipra.getFormatDate(ipraPrikaz.getIpra18prikazTpmpkD())}" onclick="tpmpkDate()">
                                    </c:if>
                                    <br>
                                    Номер письма в ТПМПК: 
                                    <c:if test="${ipraPrikaz.getSprotherpmpkId() == null}">
                                        <input type="text" id="tpmpkN" name="tpmpkN" disabled="disabled">
                                    </c:if>
                                    <c:if test="${ipraPrikaz.getSprotherpmpkId() != null}">
                                        <c:if test="${ipraPrikaz.getIpra18prikazTpmpkN() != null}">
                                            <input type="text" id="tpmpkN" name="tpmpkN" value="${ipraPrikaz.getIpra18prikazTpmpkN()}">
                                        </c:if>
                                        <c:if test="${ipraPrikaz.getIpra18prikazTpmpkN() == null}">
                                            <input type="text" id="tpmpkN" name="tpmpkN" value="">
                                        </c:if>
                                    </c:if>
                                    <br>
                                    <br>
                                    <strong>Перечень мероприятий </strong>                            
                                    <br>
                                    <label>Дата письма в ОМСУ (п. 1.1): </label>
                                    <input type = "date" id = "omsuD" name = "omsuD" onclick = "omsuDate()" value="${ipra.getFormatDate(ipraPrikaz.getIpra18prikazPerechD())}" >                    
                                    <br>
                                    <label>Номер письма в ОМСУ: </label>
                                    <input type = "text" id = "omsuN" name = "omsuN" value="${ipraPrikaz.getIpra18prikazPerechN()}" >   
                                    <br>                            
                                    <br>
                                    <strong>Даты отчетов</strong> 
                                    <input type="button" id="printOtchetBtn" value="Выгрузка отчётов" onclick="clickPrintOtchetBtn()">
                                    <br>
                                    <label>ОМСУ (п. 2.2): </label>
                                    <input type = "date" id = "otchOmsu" name = "otchOmsu" onclick = "otchOmsuDate()" value="${ipra.getFormatDate(ipraPrikaz.getIpra18prikazOtchomsu())}" >
                                    <br>
                                    <label>ОЦППМСП (п. 1.2): </label>
                                    <input type = "date" id = "otchCenter" name = "otchCenter" onclick = "otchCenterDate()" value="${ipra.getFormatDate(ipraPrikaz.getIpra18prikazOtchcenter())}">
                                    Номер письма: <input type = "text" id = "otchCenterN" name = "otchCenterN" value="${ipraPrikaz.getIpra18prikazOtchcenterN()}">                        
                                    <br>
                                    <label>ДО: </label>
                                    <input type = "date" id = "otchDo" name = "otchDo" onclick = "otchDoDate()" value="${ipra.getFormatDate(ipra.getIpra18Otchdo())}" >
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
                            </section>
                            <section id="content-tab2">
                                <div id="divPerechen"> 
                                    <c:if test="${ipraPerechen != null}">
                                        <input type="hidden" id="perechenId" name="perechenId" value="${ipraPerechen.getIpraperechenId()}">
                                    </c:if>
                                    <i>Сроки обучения (приблизительно): </i>
                                    <br>
                                    ДО - <c:out value="${datedo}" />
                                    <br>
                                    для НОО 4 года: <c:out value="${datevar1}" />
                                    <br>
                                    для НОО 5 лет: <c:out value="${datevar2}" />
                                    <br>
                                    <br>
                                    <c:forEach var="type" items="${types}">                                        
                                        <strong>
                                            <c:out value="${type.getIpraeducondtypeName()}:" />
                                        </strong>
                                        <br>
                                        <c:set var="i" value="1"/>
                                        <table class="perechen" id="pertype${type.getIpraeducondtypeId()}">
                                            <c:if test="${ipraPerechen == null}">
                                                <tr id="rowcond${type.getIpraeducondtypeId()}_${i}">                                                    
                                                    <td style="width:80%">
                                                        <c:if test="${type.getIpraeducondtypeOp() == 1}">
                                                            <textarea class="intable" id="cond${type.getIpraeducondtypeId()}_${i}" 
                                                                      name="cond${type.getIpraeducondtypeId()}_${i}" 
                                                                      rows="3"><c:out value="${obr}" /></textarea>
                                                        </c:if>
                                                        <c:if test="${type.getIpraeducondtypeOp() != 1}">
                                                            <textarea class="intable" id="cond${type.getIpraeducondtypeId()}_${i}" 
                                                                      name="cond${type.getIpraeducondtypeId()}_${i}" 
                                                                      rows="3"></textarea>
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        До: <input type="date" id="datecond${type.getIpraeducondtypeId()}_${i}"
                                                                   name="datecond${type.getIpraeducondtypeId()}_${i}" value="">
                                                        <img class="btn" id="delCond${type.getIpraeducondtypeId()}_${i}" src="img/delete.png" width="16" alt="Удалить" title="Удалить">
                                                    </td>                                                   
                                                    <c:set var="i" value="${i+1}"/>
                                                </tr> 
                                            </c:if>
                                            <c:if test="${ipraPerechen != null}">     
                                                <c:set var="f" value="0"/>
                                                <c:forEach var="condition" items="${conditions}">                                                    
                                                    <c:if test="${condition.getIpraeducondtypeId().equals(type)}">
                                                        <tr id="rowcond${type.getIpraeducondtypeId()}_${i}">                                                    
                                                            <td style="width:80%">
                                                                <textarea class="intable" id="cond${type.getIpraeducondtypeId()}_${i}" 
                                                                          name="cond${type.getIpraeducondtypeId()}_${i}" 
                                                                          rows="3"><c:out value="${condition.getIpraeducondContext()}" /></textarea>
                                                                <c:set var="f" value="1"/>                                                            
                                                            </td>
                                                            <td>
                                                                До: <input type="date" id="datecond${type.getIpraeducondtypeId()}_${i}"
                                                                           name="datecond${type.getIpraeducondtypeId()}_${i}" value="${condition.getFormatDate()}">
                                                                <img class="btn" id="delCond${type.getIpraeducondtypeId()}_${i}" src="img/delete.png" width="16" alt="Удалить" title="Удалить">
                                                            </td>
                                                            <c:set var="i" value="${i+1}"/>
                                                        </tr>  
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${f == 0}">
                                                    <tr id="rowcond${type.getIpraeducondtypeId()}_${i}">                                                    
                                                        <td style="width:80%">
                                                            <c:if test="${type.getIpraeducondtypeOp() == 1}">
                                                                <textarea class="intable" id="cond${type.getIpraeducondtypeId()}_${i}" 
                                                                          name="cond${type.getIpraeducondtypeId()}_${i}" 
                                                                          rows="3"><c:out value="${obr}" /></textarea>
                                                            </c:if>
                                                            <c:if test="${type.getIpraeducondtypeOp() != 1}">
                                                                <textarea class="intable" id="cond${type.getIpraeducondtypeId()}_${i}" 
                                                                          name="cond${type.getIpraeducondtypeId()}_${i}" 
                                                                          rows="3"></textarea>
                                                            </c:if>
                                                        </td>
                                                        <td>
                                                            До: <input type="date" id="datecond${type.getIpraeducondtypeId()}_${i}"
                                                                       name="datecond${type.getIpraeducondtypeId()}_${i}" value="">
                                                            <img class="btn" id="delCond${type.getIpraeducondtypeId()}_${i}" src="img/delete.png" width="16" alt="Удалить" title="Удалить">
                                                        </td>                                                        
                                                    </tr>                                                     
                                                </c:if>
                                                <c:set var="i" value="${i+1}"/>   
                                            </c:if>
                                        </table>
                                        <img class="btn" id="plusCond${type.getIpraeducondtypeId()}" src="img/plus.png" width="16" alt="Добавить" title="Добавить">
                                        <br>
                                    </c:forEach>       
                                    <div>
                                        <input type = "button" id = "savePerechenBtn" value = "Сохранить перечень" onclick="validatePerechen()">
                                        <input type = "button" id = "printPerechenBtn" value = "Выгрузить перечень" onclick="clickPrintPerechen()">
                                        <input type="hidden" id="printPerechenFlag" value="0">
                                        <input type="button" name="closeBtn" id = "closeBtn" value="Закрыть" onclick="javascript:window.close();">
                                    </div>
                                </div>
                            </section>        
                        </div>
                    </div>
                </div>
            </form>
        </c:if>
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
        init();
    </script>
</html>

