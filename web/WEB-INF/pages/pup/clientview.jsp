<%-- 
    Document   : client
    Created on : 09.03.2016, 19:48:25
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="clientview0926.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title><c:out  value="${client.getFIO()}"/></title>
    </head>
    <body>
        <c:if test="${user != null}">
            <h3><c:out  value="${client.getFIO()}"/></h3>
            <c:forEach var="oldfio" items="${clientOldFio}">
                (<c:out value="${oldfio.getFIO()}" /> до <c:out value="${oldfio.getFormatDateUpd()}" />)<br>
            </c:forEach>
            <br>
            <div class="right" id="family">
                <c:if test="${!familyList.isEmpty()}">
                    <strong>Семья</strong>
                    <br>
                    <c:forEach var="famMember" items="${familyList}">                    
                        <label class="clickable" onclick="javascript:window.open('client?kat=${famMember.getKat()}&id=${famMember.getId()}', '_blank')">
                            <c:out value="${famMember.getFio()} ${famMember.getFormatDateR()}"/>
                        </label>
                        <br>
                    </c:forEach>
                </c:if>
            </div>
            <form id="formClient" action="saveclient" accept-charset="windows-1251" method="POST">
                <input type="hidden" id="kat" name="kat" value="${kat}">            
                <c:choose>
                    <c:when test="${kat.equals('children')}">
                        <input type="hidden" id="idClient" name="idClient" value="${client.getChildId()}">
                        Номер: <c:out value="${client.getChildNom()}"/>
                        <br>
                        <br>
                        Фамилия:
                        <input type="text" name="fam" id="fam" value="${client.getChildFam()}">                    
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                            <input type="button" name="changeFamBtn" id ="changeFamBtn" value="Смена фамилии">
                        </c:if>
                        <br>
                        <br>
                        Имя:
                        <input type="text" name="nam" id="nam" value="${client.getChildName()}">
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                            <input type="button" name="changeNameBtn" id ="changeNameBtn" value="Смена имени">
                        </c:if>
                        <br>
                        <br>
                        Отчество:
                        <input type="text" name="patr" id="patr" value="${client.getChildPatr()}">
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                            <input type="button" name="changePatrBtn" id ="changePatrBtn" value="Смена отчества">
                        </c:if>
                        <br>
                        <div>
                            Пол:
                            <br>
                            <label>
                                <c:if test="${client.getChildPol() == 'м'}">
                                    <input checked type = "radio" name = "polR" id = "polR" value = "м">
                                </c:if>
                                <c:if test="${client.getChildPol() != 'м'}">
                                    <input type = "radio" name = "polR" id = "polR" value = "м">
                                </c:if>
                                мужской
                            </label>
                            <br>
                            <label>
                                <c:if test="${client.getChildPol() == 'ж'}">                                
                                    <input checked type = "radio" name = "polR" id = "polR" value = "ж">
                                </c:if>
                                <c:if test="${client.getChildPol() != 'ж'}">                                
                                    <input type = "radio" name = "polR" id = "polR" value = "ж">
                                </c:if>
                                женский
                            </label>
                            <input type = "hidden" name = "pol" id = "pol" value="${client.getChildPol()}">
                        </div>                                                
                        <br>
                        Дата рождения:
                        <input type="date" name="dr" id="dr" value="${client.getFormatDr()}">
                        <br>
                        <br>
                        <input type="hidden" id="regId" name="regId" value="${client.getSprregId().getSprregId()}">
                        Район проживания:
                        <select id="reg">       
                            <c:forEach var="region" items="${reg}">
                                <c:if test="${region == client.getSprregId()}">
                                    <option selected value="${region.getSprregId()}">
                                        <c:out value="${region.getSprregName()}"/>
                                    </option>
                                </c:if>
                                <c:if test="${region != client.getSprregId()}">
                                    <option value="${region.getSprregId()}">
                                        <c:out value="${region.getSprregName()}"/>
                                    </option>
                                </c:if>
                            </c:forEach>                
                        </select>
                        <br>
                        <c:if test="${childreg.size() > 0}">
                            <input type="button" value="История районов" id="histReg" onclick="histR()">
                        </c:if>
                        <br>
                        <c:if test="${!childstats.isEmpty()}">
                            <div id="divStat">
                                <strong>Статусы:</strong>
                                <br>
                                <c:forEach var="cs" items="${childstats}">
                                    <c:out value="${cs.getSprstatId().getSprstatName()}"/>
                                    <c:if test="${cs.getChildstatusDateK() != null}">
                                        <c:out value="(до ${cs.getChildstatusFormatDateK()})"/>
                                    </c:if>
                                    <br>
                                </c:forEach>
                            </div>
                            <br>
                        </c:if>                    
                        <div id="divChOu">
                            <b>Образовательное учреждение:</b>
                            <br>                    
                            <c:if test="${eduplace.size() > 0}">
                                <c:forEach var="eduplc" items="${eduplace}">
                                    <c:if test="${eduplc.getChildeduplaceDatek() == null}">
                                        <c:out value="${eduplc.getSprooId().getSprooName()}" />
                                        <c:if test="${eduplc.getSprstageId() != null}">
                                            <c:out value=", " />
                                            <c:out value="${eduplc.getSprstageId().getSprstageName()}" />
                                        </c:if> 
                                    </c:if>
                                </c:forEach>
                                <br>                    
                                <input type="button" value="История" id="histEduplace" onclick="histEdupl()">                    
                            </c:if>
                            <input type="button" value="Добавить место обучения" id="addEduplace" onclick="addEdupl()">                
                            <div id="divNewChOu">
                            </div>
                        </div>
                        <br>
                        <c:if test="${educondList.size() > 0}">
                            <div id="divEducond">
                                <strong>Условия получения образования (по данным мониторинга)</strong>
                                <br>
                                <br>
                                <c:forEach var="mec" items="${monitEducondList}">        
                                    <strong><c:out value="${mec.getFormat2MonDate()}" /></strong>
                                    <c:if test="${(mec.getChildreneducondId().getChildreneducondZakl() != null)}">
                                        <br>
                                        <ins>Образовательная организация</ins>: <c:out value="${mec.getChildreneducondId().getSprouId().getSprouShname()}" />
                                        <c:out value="${mec.getChildreneducondId().getSprstageId().getSprstageName()}" />
                                        <br>
                                        Заключение ПМПК 
                                        <c:if test="${mec.getChildreneducondId().getChildreneducondZakl() == 1}">
                                            предъявлено
                                        </c:if>
                                        <c:if test="${mec.getChildreneducondId().getChildreneducondZakl() == 0}">
                                            не предъявлено
                                        </c:if>
                                        <br>
                                        <ins>Форма образования</ins>: <c:out value="${mec.getChildreneducondId().getSpreduformId().getSpreduformName()}" />
                                        <br>
                                        <ins>Программа обучения</ins>: <c:out value="${mec.getChildreneducondId().getSprobrId().getSprobrShname()}" />
                                        <c:if test="${mec.getChildreneducondId().getSprvarId() != null}">
                                            (<c:out value="${mec.getChildreneducondId().getSprvarId().getSprvarName()}" />)
                                        </c:if>
                                        <br>
                                        <c:if test="${educondrekList.size() > 0}">
                                            <ins>Занятия со специалистами, услуги ассистента (помощника), тьютора</ins>: <br>
                                            <c:forEach var="rek" items="${educondrekList}">
                                                <c:if test="${rek.getChildreneducondId() == mec.getChildreneducondId()}">
                                                    <c:out value="${rek.getSprrekId().getSprrekName()}" />
                                                    <br>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${educondeqList.size() > 0}">
                                            <ins>Специальное оборудование</ins>: <br>       
                                            <c:forEach var="equ" items="${educondeqList}">
                                                <c:if test="${equ.getChildreneducondId() == mec.getChildreneducondId()}">
                                                    <c:out value="${equ.getSprequipId().getSprequipName()}" />
                                                    <br>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${(mec.getChildreneducondId().getChildreneducondZakl() == null)}">
                                        <br>
                                        нет данных
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:if>
                        <br>
                        <c:if test="${childipra.size() > 0}" >
                            <div id="divIpra">
                                <b>ИПРА</b>
                                <br>
                                <c:forEach var="ipra" items="${childipra}">
                                    <label class="clickable" onclick="javascript:window.open('ipraspisok?action=open&id=' + ${ipra.getIpraId()}, 'blank_')">
                                        <c:out value="№${ipra.getIpraN()} от ${ipra.getFormat2Date(ipra.getIpraDateexp())} до ${ipra.getFormat2Date(ipra.getIpraDateok())}" />
                                    </label>
                                    <br>
                                </c:forEach>
                            </div>  
                        </c:if>
                        <br>  
                        <c:if test="${childipra18.size() > 0}" >
                            <div id="divIpra18">
                                <b>ИПРА</b>
                                <br>
                                <c:forEach var="ipra18" items="${childipra18}">
                                    <label class="clickable" onclick="javascript:window.open('ipra2018spisok?action=open&id=' + ${ipra18.getIpra18Id()}, 'blank_')">
                                        <c:out value="№${ipra18.getIpra18N()} от ${ipra18.getFormat2Date(ipra18.getIpra18Dateexp())} до ${ipra18.getFormat2Date(ipra18.getIpra18Dateok())}" />
                                    </label>
                                    <br>
                                </c:forEach>
                            </div>  
                        </c:if>
                        <br> 
                    </c:when>
                    <c:when test="${kat.equals('parents')}">
                        <input type="hidden" id="idClient" name="idClient" value="${client.getParentId()}">
                        Номер: <c:out value="${client.getParentNom()}"/>
                        <br>
                        <br>
                        Фамилия:
                        <input type="text" name="fam" id="fam" value="${client.getParentFam()}">
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                            <input type="button" name="changeFamBtn" id ="changeFamBtn" value="Смена фамилии">
                        </c:if>
                        <br>
                        <br>
                        Имя:
                        <input type="text" name="nam" id="nam" value="${client.getParentName()}">
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                            <input type="button" name="changeNameBtn" id ="changeNameBtn" value="Смена имени">
                        </c:if>
                        <br>
                        <br>
                        Отчество:
                        <input type="text" name="patr" id="patr" value="${client.getParentPatr()}">
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                            <input type="button" name="changePatrBtn" id ="changePatrBtn" value="Смена отчества">
                        </c:if>
                        <br>
                        <br>
                        <input type="hidden" id="regId" name="regId" value="${client.getSprregId().getSprregId()}">
                        Район проживания:
                        <select id="reg">       
                            <c:forEach var="region" items="${reg}">
                                <c:if test="${region == client.getSprregId()}">
                                    <option selected value="${region.getSprregId()}">
                                        <c:out value="${region.getSprregName()}"/>
                                    </option>
                                </c:if>
                                <c:if test="${region != client.getSprregId()}">
                                    <option value="${region.getSprregId()}">
                                        <c:out value="${region.getSprregName()}"/>
                                    </option>
                                </c:if>
                            </c:forEach>                
                        </select>
                        <br>
                        <c:if test="${parentreg.size() > 0}">
                            <input type="button" value="История районов" id="histReg" onclick="histR()">
                        </c:if>
                        <br>                    
                        Контактный телефон:                    
                        <c:if test="${parenttel.size() == 0}">
                            <input type="text" id="tel" name="tel">
                        </c:if>
                        <c:if test="${parenttel.size() > 0}">
                            <input type="text" id="tel" name="tel" value="${parenttel.get(0).getTel()}">
                        </c:if>
                        <br>
                        <br>
                    </c:when>
                    <c:when test="${kat.equals('ped')}">
                        <input type="hidden" id="idClient" name="idClient" value="${client.getPedId()}">
                        Номер: <c:out value="${client.getPedNom()}"/>
                        <br>
                        <br>
                        Фамилия:
                        <input type="text" name="fam" id="fam" value="${client.getPedFam()}">
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                            <input type="button" name="changeFamBtn" id ="changeFamBtn" value="Смена фамилии">
                        </c:if>
                        <br>
                        <br>
                        Имя:
                        <input type="text" name="nam" id="nam" value="${client.getPedName()}">
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                            <input type="button" name="changeNameBtn" id ="changeNameBtn" value="Смена имени">
                        </c:if>
                        <br>
                        <br>
                        Отчество:
                        <input type="text" name="patr" id="patr" value="${client.getPedPatr()}">
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                            <input type="button" name="changePatrBtn" id ="changePatrBtn" value="Смена отчества">
                        </c:if>
                        <br>
                        <br>
                        <input type="hidden" id="regId" name="regId" value="${client.getSprregId().getSprregId()}">
                        Район проживания:
                        <select id="reg">       
                            <c:forEach var="region" items="${reg}">
                                <c:if test="${region == client.getSprregId()}">
                                    <option selected value="${region.getSprregId()}">
                                        <c:out value="${region.getSprregName()}"/>
                                    </option>
                                </c:if>
                                <c:if test="${region != client.getSprregId()}">
                                    <option value="${region.getSprregId()}">
                                        <c:out value="${region.getSprregName()}"/>
                                    </option>
                                </c:if>
                            </c:forEach>                
                        </select>
                        <br>
                        <c:if test="${pedreg.size() > 0}">
                            <input type="button" value="История районов" id="histReg" onclick="histR()">
                        </c:if>
                        <br>
                        <br>
                        <input type="hidden" id="orgId" name="orgId" value="${client.getSprorgId().getSprorgId()}">
                        Организация:
                        <select id="org">       
                            <c:forEach var="organiz" items="${org}">
                                <c:if test="${organiz == client.getSprorgId()}">
                                    <option selected value="${organiz.getSprorgId()}">
                                        <c:out value="${organiz.getSprorgName()}"/>
                                    </option>
                                </c:if>
                                <c:if test="${organiz != client.getSprorgId()}">
                                    <option value="${organiz.getSprorgId()}">
                                        <c:out value="${organiz.getSprorgName()}"/>
                                    </option>
                                </c:if>
                            </c:forEach>                
                        </select>
                        <br>
                        <br>
                        <input type="hidden" id="dolgId" name="dolgId" value="${client.getSprpeddolgId().getSprpeddolgId()}">
                        Должность:
                        <select id="dolg">       
                            <c:forEach var="dolgnost" items="${dolg}">
                                <c:if test="${dolgnost == client.getSprpeddolgId()}">
                                    <option selected value="${dolgnost.getSprpeddolgId()}">
                                        <c:out value="${dolgnost.getSprpeddolgName()}"/>
                                    </option>
                                </c:if>
                                <c:if test="${dolgnost != client.getSprpeddolgId()}">
                                    <option value="${dolgnost.getSprpeddolgId()}">
                                        <c:out value="${dolgnost.getSprpeddolgName()}"/>
                                    </option>
                                </c:if>
                            </c:forEach>                
                        </select>
                        <br>
                        <br>
                    </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
                <input type="button" name="saveBtn" id = "saveBtn" value="Сохранить" onclick="validate()">
                <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                    <input type="button" name="delBtn" id = "delBtn" value="Удалить клиента" onclick="delClient()">
                </c:if> 
                <input type="button" name="closeBtn" id = "closeBtn" value="Закрыть" onclick="javascript:window.close();">
            </form>
            <c:if test="${(prcl != null)&&(prcl.size() != 0)}">
                <div id="usl">
                    <p>
                        <strong>
                            Оказанные услуги
                        </strong>
                    </p>                     
                    <table class="regular" id="usltab">
                        <thead>
                            <tr>
                                <td></td>
                                <td>Дата</td>
                                <td>Услуга</td>
                                <td>Специалисты</td>
                                <td>Место проведения</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pr" items="${prcl}">
                                <tr>
                                    <td><c:out value="${pr.getPriyomId().getPriyomId()}" /></td>                            
                                    <td><c:out value="${pr.getPriyomId().getFormatDate()}" /></td>      
                                    <td><c:out value="${pr.getPriyomId().getSpruslId().getSpruslName()}" /></td>                          
                                    <td>
                                        <c:forEach var="prs" items="${prsotr}">
                                            <c:if test="${pr.getPriyomId() == prs.getPriyomId()}">
                                                <c:out value="${prs.getSotruddolgnId().getSotrudId().getSotrudFIO()}" /><br>
                                            </c:if>
                                        </c:forEach>    
                                    </td>                    
                                    <td><c:out value="${pr.getPriyomId().getSprregId().getSprregName()}" /></td>  
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <c:if test="${(prsub != null)&&(prsub.size() != 0)}">
                <div id="uslSubj">
                    <p>
                        <strong>
                            Как субъект:
                        </strong>
                    </p>                     
                    <table class="regular" id="uslsubjtab">
                        <thead>
                            <tr>
                                <td></td>
                                <td>Дата</td>
                                <td>Услуга</td>
                                <td>Специалисты</td>
                                <td>Место проведения</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pr" items="${prsub}">
                                <tr>
                                    <td><c:out value="${pr.getPriyomId().getPriyomId()}" /></td>                            
                                    <td><c:out value="${pr.getPriyomId().getFormatDate()}" /></td>      
                                    <td><c:out value="${pr.getPriyomId().getSpruslId().getSpruslName()}" /></td>                          
                                    <td>
                                        <c:forEach var="prs" items="${prsotr}">
                                            <c:if test="${pr.getPriyomId() == prs.getPriyomId()}">
                                                <c:out value="${prs.getSotruddolgnId().getSotrudId().getSotrudFIO()}" /><br>
                                            </c:if>
                                        </c:forEach>    
                                    </td>                    
                                    <td><c:out value="${pr.getPriyomId().getSprregId().getSprregName()}" /></td>  
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <c:if test="${(pucl != null)&&(pucl.size() != 0)}">
                <div id="payusl">
                    <p>
                        <strong>
                            Оказанные платные услуги
                        </strong>
                    </p>                     
                    <table class="regular" id="payusltab">
                        <thead>
                            <tr>
                                <td></td>
                                <td>Дата</td>
                                <td>Услуга</td>
                                <td>Специалисты</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="pu" items="${pucl}">
                                <tr>
                                    <td><c:out value="${pu.getPayuslId().getPayuslId()}" /></td>                            
                                    <td><c:out value="${pu.getPayuslId().getRegularFormatDate()}" /></td>      
                                    <td><c:out value="${pu.getPayuslId().getSprpayuslId().getSprpayuslName()}" /></td>                          
                                    <td>
                                        <c:forEach var="pustr" items="${pusotr}">
                                            <c:if test="${pu.getPayuslId() == pustr.getPayuslId()}">
                                                <c:out value="${pustr.getSotruddolgnId().getSotrudId().getSotrudFIO()}" /><br>
                                            </c:if>
                                        </c:forEach>    
                                    </td>  
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <script type="text/javascript">
                init();
            </script>
        </c:if>
    </body>
    <dialog id="histDialog">
        Сведения о местах обучения: 
        <br>
        <c:forEach var="eduplc" items="${eduplace}">
            <c:if test="${eduplc.getChildeduplaceDatek() != null}">
                <c:out value="${eduplc.getSprooId().getSprooName()}" />
                <c:if test="${eduplc.getSprstageId() != null}">
                    <c:out value=", " />
                    <c:out value="${eduplc.getSprstageId().getSprstageName()}" />
                </c:if> 
                <c:if test="${eduplc.getChildeduplaceDatek() != null}">
                    <c:out value=" (актуально до " />
                    <c:out value="${eduplc.getFormatDateK()}" />
                    <c:out value=")" />
                </c:if> 
                <br>
            </c:if>
        </c:forEach>
        <br>
        <input type="button" value="Закрыть" onclick="javascript: document.getElementById('histDialog').close()">
    </dialog>
    <dialog id="histRegDialog">
        Сведения о районах проживания: 
        <br>
        <c:if test="${childreg.size() > 0}">        
            <c:forEach var="reg" items="${childreg}">
                <c:out value="${reg.getSprregId().getSprregName()}" />
                до <c:out value="${reg.getFormatDate()}" />
                <br>
            </c:forEach>
        </c:if>
        <c:if test="${parentreg.size() > 0}">        
            <c:forEach var="reg" items="${parentreg}">
                <c:out value="${reg.getSprregId().getSprregName()}" />
                до <c:out value="${reg.getFormatDate()}" />
                <br>
            </c:forEach>
        </c:if>
        <c:if test="${pedreg.size() > 0}">        
            <c:forEach var="reg" items="${pedreg}">
                <c:out value="${reg.getSprregId().getSprregName()}" />
                до <c:out value="${reg.getFormatDate()}" />
                <br>
            </c:forEach>
        </c:if>
        <br>
        <input type="button" value="Закрыть" onclick="javascript: document.getElementById('histRegDialog').close()">
    </dialog>
    <dialog id="changeFIODialog">          
    </dialog>    
</html>
