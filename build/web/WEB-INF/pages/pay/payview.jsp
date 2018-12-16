<%-- 
    Document   : payview
    Created on : 12.09.2017, 10:54:10
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="payadd0205.js" charset="utf-8"></script>
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Cписок на зачисление</title>
    </head>
    <body>
    <c:if test="${user != null}">
        <input type="hidden" id="role" value="${user.getRoleId().getRoleName()}">
        <form id="payListForm" action="savepaylist" accept-charset="windows-1251" method="POST">
            <input type="hidden" id="inclDate" name="inclDate">
            <input type="hidden" id="incl" name="incl">
            <h3>Выберите услугу:</h3>
            <div id="divUsl">            
                <select id="selUsl" onchange="changeUsl()">
                    <option value="0"></option>
                    <c:forEach var="sprUsl" items="${payUslList}">
                        <c:if test="${usl.getSprpayuslId().equals(sprUsl)}">
                            <option selected value="${sprUsl.getSprpayuslId()}">
                                <c:if test="${sprUsl.getSprpayuslTime() == null}">
                                    <c:out value="${sprUsl.getSprpayuslName()}" />
                                </c:if>
                                <c:if test="${sprUsl.getSprpayuslTime() != null}">
                                    <c:out value="${sprUsl.getSprpayuslName()} ${sprUsl.getSprpayuslTime()} минут (${sprUsl.getSprpayuslPrice()} рублей)" />
                                </c:if>
                            </option>
                        </c:if>
                        <c:if test="${!usl.getSprpayuslId().equals(sprUsl)}">
                            <option value="${sprUsl.getSprpayuslId()}">
                                <c:if test="${sprUsl.getSprpayuslTime() == null}">
                                    <c:out value="${sprUsl.getSprpayuslName()}" />
                                </c:if>
                                <c:if test="${sprUsl.getSprpayuslTime() != null}">
                                    <c:out value="${sprUsl.getSprpayuslName()} ${sprUsl.getSprpayuslTime()} минут (${sprUsl.getSprpayuslPrice()} рублей)" />
                                </c:if>
                            </option>
                        </c:if>
                    </c:forEach>
                </select>
                <input type="hidden" id="stat" name="stat" value="${usl.getSprpayuslId().getSprpayuslStat()}">
                <input type="hidden" id="group" name="group" value="${usl.getSprpayuslId().getSprpayuslGroup()}">
                <input type="hidden" id="lesson" name="lesson" value="${usl.getSprpayuslId().getSprpayuslLesson()}">
                <input type="hidden" id="payUslId" name="payUslId" value="${usl.getSprpayuslId().getSprpayuslId()}">
                <input type="hidden" id="uslId" name="uslId" value="${usl.getPayuslId()}">
            </div>
            <div id="divGroup">            
                <c:if test="${usl.getSprpayuslId().getSprpayuslGroup() == 1}">
                    <label>
                        Название группы: 
                        <input type="text" id = "inpGroup" name = "inpGroup" value="${usl.getPayuslName()}">
                    </label>        
                </c:if>
            </div>
            <div id="divLesson">   
                <label>
                    Дата начала занятий: 
                    <input type = "date" disabled="disabled" id = "dateN" name = "dateN" value="${payuslLessons.get(0).getFormatLessonDate()}">
                </label>
                <br>
                <label>
                    Количество занятий:  
                    <input type = "text" disabled="disabled" id = "kol" name = "kol" value="${payuslLessons.size()}">
                </label>
                <input type="hidden" id = "dates" name = "dates">
            </div>
            <input type="button" id="showKalendar" value="Показать календарь" onclick="clickShow()">
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
                    <c:set var="flag2" value="0" />
                    <c:forEach var="b" begin="1" end="7" step="1">                                       
                        <c:if test="${flag == 0}">
                            <c:if test="${date.getDayOfWeek() == b}">
                                <c:if test="${date.getIsWeekend()}">
                                    <c:if test="${flag2 == 0}">
                                        <c:forEach var="less" items="${payuslLessons}">
                                            <c:if test="${less.getPayusllessonDate().getTime() == date.getDate().getTime()}">
                                                <td class="checkedday weekend" id="${date.getDay()}.${date.getMonth()}.${date.getYear()}">
                                                    <c:out value="${date.getDay()}"/>
                                                </td>
                                                <c:set var="flag2" value="1" />    
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${flag2 == 0}">
                                        <td class="weekend" id="${date.getDay()}.${date.getMonth()}.${date.getYear()}">
                                            <c:out value="${date.getDay()}"/>
                                        </td>
                                    </c:if>
                                    <c:set var="flag" value="1" />
                                </c:if>
                                <c:if test="${!date.getIsWeekend()}"> 
                                    <c:if test="${flag2 == 0}">
                                        <c:forEach var="less" items="${payuslLessons}">
                                            <c:if test="${less.getPayusllessonDate().getTime() == date.getDate().getTime()}">
                                                <td class="regularday checkedday" id="${date.getDay()}.${date.getMonth()}.${date.getYear()}">
                                                    <c:out value="${date.getDay()}"/>
                                                </td>                                           
                                                <c:set var="flag2" value="1" />
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${flag2 == 0}">
                                        <td class="regularday" id="${date.getDay()}.${date.getMonth()}.${date.getYear()}">
                                            <c:out value="${date.getDay()}"/>
                                        </td>    
                                    </c:if>
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
                <b>Специалисты:</b>
                <br>            
                <c:forEach var="spec" items="${sotruds}">
                    <div id="spec${i=i+1}">
                        <select id="selDolgn${i}">
                            <c:forEach var="dolgn" items="${dolgns}">
                                <c:if test="${dolgn == spec.getSotrudId().getSprdolgnId()}">
                                    <option selected value="${dolgn.getSprdolgnId()}">
                                        <c:out value="${dolgn.getSprdolgnName()}"/>
                                    </option>
                                </c:if>
                                <c:if test="${dolgn != spec.getSotrudId().getSprdolgnId()}">
                                    <option value="${dolgn.getSprdolgnId()}">
                                        <c:out value="${dolgn.getSprdolgnName()}"/>
                                    </option>
                                </c:if>
                            </c:forEach>      
                        </select>
                        <input type="hidden" name="dolgnId${i}" id="dolgnId${i}" value="${spec.getSotrudId().getSprdolgnId().getSprdolgnId()}">
                        <select id="selSotr${i}">  
                            <c:forEach var="sotr" items="${allsotrud}">
                                <c:if test="${sotr.getSprdolgnId() == spec.getSotrudId().getSprdolgnId()}">
                                    <c:if test="${sotr == spec.getSotrudId()}">
                                        <option selected value="${sotr.getSotrudId()}">
                                            <c:out value="${sotr.getSotrudFIO()}" />
                                        </option>
                                    </c:if>
                                    <c:if test="${sotr != spec.getSotrudId()}">
                                        <option value="${sotr.getSotrudId()}">
                                            <c:out value="${sotr.getSotrudFIO()}" />
                                        </option>
                                    </c:if>    
                                </c:if>
                            </c:forEach>                        
                        </select>
                        <input type="hidden" name="sotrId${i}" id="sotrId${i}" value="${spec.getSotrudId().getSotrudId()}">     
                        <img class="btn" id="delSotr${i}" src="img/delete.png" width="16" alt="Удалить" title="Удалить">                
                    </div>
                </c:forEach>
            </div>
            <div id="divPlusSotr">
                <img class="btn" id="plusSotr" src="img/plus.png" width="16" alt="Добавить" title="Добавить">
                <br> 
                <br> 
            </div>
                <c:set var="c" value="1" /> 
            <div id = "divClients">
                <b>Клиенты:</b>
                <c:forEach var="child" items="${children}">                    
                    <c:set var="l" value="0" />    
                    <c:set var="c" value="${c * (-1)}" />   
                    <c:set var="j" value="${j+1}" />
                    <c:if test="${j > 1}">
                        <div id="divhr${j}">
                            <hr>
                        </div>
                    </c:if>
                    <div class="clients${c}" id = "cl${j}">  
                        <input type="hidden" name="clId${j}" id="clId${j}" value="${child.getChildId()}">
                        <input type="hidden" name="clKat${j}" id="clKat${j}" value="children">
                        <img class="btn" id="editCl${j}" src="img/edit.png" width="20" alt="Редактировать" title="Редактировать">
                        <img class="btn" id="delCl${j}" src="img/delete.png" width="20" alt="Удалить" title="Удалить">   
                        <br>
                        <div id="infoClient${j}">
                            <strong><c:out value="${child.getChildNom()}" /></strong>
                            <br>
                            <label class="clickable" onclick="javascript:window.open('client?kat=children&id=' + ${child.getChildId()}, '_blank')">
                                <c:out value="${child.getChildFam()} ${child.getChildName()} ${child.getChildPatr()} ${child.getFormat2Dr()}" />
                            </label>
                            <br> 
                            <c:out value="${child.getSprregId().getSprregName()}" />
                            <br>
                            <br>
                        </div>
                        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">    
                            <c:set var="d" value="0" />
                            <c:forEach var="dogovor" items="${payDogovors}">
                                <c:if test="${dogovor.getChildId() == child}">
                                    <c:set var="d" value="1" />
                                    <div id="dogovor${j}">
                                        Договор
                                        <br>
                                        Номер: 
                                        <input type="text" id="dogN${j}" name="dogN${j}" value="${dogovor.getPaydogN()}">
                                        Дата:
                                        <input type="date" id="dogD${j}" name="dogD${j}" value="${dogovor.getFormatDateDog()}">
                                        <br>
                                        С кем заключен (родитель):
                                        <input type="button" value="Найти" id="findDogCl${j}">
                                        <br>
                                        <input type="hidden" id="dogClId${j}" name="dogClId${j}" value="${dogovor.getParentId().getParentId()}">
                                        <div id="infoDogCl${j}">                                            
                                            <label class="clickable" onclick="javascript:window.open('client?kat=parents&id=' + ${dogovor.getParentId().getParentId()}, '_blank')">
                                                <c:out value="${dogovor.getParentId().getFIO()}" />
                                            </label>
                                            <br> 
                                            <c:out value="${dogovor.getParentId().getSprregId().getSprregName()}" />
                                            <br>
                                            <c:set var="t" value="0" />
                                            <c:forEach items="${telephons}" var="tel">
                                                <c:if test="${tel.getParentId() == dogovor.getParentId()}">
                                                    Телефон: <input type="text" id="telephon${j}" name="telephon${j}" value="${tel.getTel()}">
                                                    <c:set var="t" value="1" />
                                                </c:if>
                                            </c:forEach>
                                            <c:if test="${t==0}">
                                                Телефон: <input type="text" id="telephon${j}" name="telephon${j}" value="">
                                            </c:if>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                            <c:if test="${d == 0}">
                                <div id="dogovor${j}">
                                    Договор
                                    <br>
                                    Номер: 
                                    <input type="text" id="dogN${j}" name="dogN${j}">
                                    Дата:
                                    <input type="date" id="dogD${j}" name="dogD${j}">
                                    <br>
                                    С кем заключен (родитель):
                                    <input type="button" value="Найти" id="findDogCl${j}">
                                    <br>
                                    <input type="hidden" id="dogClId${j}" name="dogClId${j}">
                                    <div id="infoDogCl${j}">
                                        
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </div>        
                </c:forEach>
                <c:forEach var="parent" items="${parents}">
                    <c:set var="j" value="${j+1}" />
                    <c:if test="${j > 1}">
                        <div id="divhr${j}">
                            <hr>
                        </div>
                    </c:if>                    
                    <c:set var="c" value="${c * (-1)}" />
                    <div class="clients${c}" id = "cl${j}">
                        <input type="hidden" name="clId${j}" id="clId${j}" value="${parent.getParentId()}">
                        <input type="hidden" name="clKat${j}" id="clKat${j}" value="parents">
                        <img class="btn" id="editCl${j}" src="img/edit.png" width="20" alt="Редактировать" title="Редактировать">
                        <img class="btn" id="delCl${j}" src="img/delete.png" width="20" alt="Удалить" title="Удалить">                
                        <br> 
                        <div id="infoClient${j}">
                            <strong><c:out value="${parent.getParentNom()}" /></strong>
                            <br>
                            <label class="clickable" onclick="javascript:window.open('client?kat=parents&id=' + ${parent.getParentId()}, '_blank')">
                                <c:out value="${parent.getParentFam()} ${parent.getParentName()} ${parent.getParentPatr()}" />
                            </label>
                            <br>
                            <c:out value="${parent.getSprregId().getSprregName()}" />
                            <br>
                            <br>                            
                        </div>                
                    </div>            
                </c:forEach>
                <c:forEach var="ped" items="${peds}">  
                    <c:set var="j" value="${j+1}" />
                    <c:if test="${j > 1}">
                        <div id="divhr${j}">
                            <hr>
                        </div>
                    </c:if>                   
                    <c:set var="c" value="${c * (-1)}" />
                    <div class="clients${c}" id = "cl${j}">
                        <input type="hidden" name="clId${j}" id="clId${j}" value="${ped.getPedId()}">
                        <input type="hidden" name="clKat${j}" id="clKat${j}" value="ped">
                        <img class="btn" id="editCl${j}" src="img/edit.png" width="20" alt="Редактировать" title="Редактировать">
                        <img class="btn" id="delCl${j}" src="img/delete.png" width="20" alt="Удалить" title="Удалить">                
                        <br>                     
                        <div id="infoClient${j}">
                            <strong><c:out value="${ped.getPedNom()}" /></strong>
                            <br>
                            <label class="clickable" onclick="javascript:window.open('client?kat=ped&id=' + ${ped.getPedId()}, '_blank')">
                                <c:out value="${ped.getPedFam()} ${ped.getPedName()} ${ped.getPedPatr()}" />
                            </label>
                            <br>
                            <c:out value="${ped.getSprregId().getSprregName()}" />
                            <br>
                            Учреждение:  
                            <c:out value="${ped.getSprorgId().getSprorgName()}" />
                            <br>
                            Должность: 
                            <c:out value="${ped.getSprpeddolgId().getSprpeddolgName()}" />
                            <br>
                            <br>                            
                        </div>                
                    </div>            
                </c:forEach>
            </div>
            <div id="divPlusClient">
                <img class="btn" id="plusCl" src="img/plus.png" width="20" alt="Добавить клиента" title="Добавить клиента">
                <br>
            </div>
            <div id="divButton">
                <br>                           
                <input class="btn" type="button" name="saveBtn" id = "saveBtn" value="Сохранить" onclick="validate();">                
                <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                    <input class="btn" type="button" name="delBtn" id = "delBtn" value="Удалить список" onclick="delPayusl()">
                    <br>
                    <input class="btn" type="button" name="inclBtn" id="inclBtn" value="Зачислить на занятия" onclick="toIncludeDialog()">
                </c:if> 
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
    <dialog id="includeDialog">
        Зачисление клиентов на занятия
        <br>
        Дата зачисления: <input type="date" id="inclDateDialog" name="inclDateDialog">
        <br>
        <input class="btn" type="button" id="inclBtnDialog" name="inclBtnDialog" onclick="include()" value="Зачислить">
    </dialog>
    <script type="text/javascript">
        initPayView();
    </script>
</html>
