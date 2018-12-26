<%-- 
    Document   : priyomview
    Created on : 14.03.2016, 17:19:19
    Author     : Aiglu
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="priyomview0926.js" charset="utf-8"></script>
        <script type="text/javascript" src="addpriyom1129.js" charset="utf-8"></script>
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>
            <c:if test="${!copy}">
                <c:out  value="${datepriyom}"/>
            </c:if>
            <c:if test="${copy}">
                Прием
            </c:if>
        </title>
    </head>
    <body>
    <c:if test="${user != null}">
        <form id="formPriyom" action="savepriyom" accept-charset="windows-1251" method="POST">
            <c:set var="i" value="0" />
            <c:set var="j" value="0" />
            <c:set var="k" value="0" />
            <c:set var="l" value="0" />
            <c:set var="m" value="0" />
            <c:set var="o" value="0" />
            <c:set var="r" value="0" />
            <c:set var="c" value="1" />
            <h3><c:out  value="${usl.getSpruslName()}"/></h3>
            <c:if test="${!copy}">
                <input type="hidden" name="priyomId" id="priyomId" value="${priyom.getPriyomId()}">
            </c:if>            
            <input type="hidden" name="uslId" id="uslId" value="${usl.getSpruslId()}">
            <input type="hidden" name="problem" id="problem" value="${priyom.getSpruslId().getSpruslProblem()}">
            <input type="hidden" name="stat" id="stat" value="${priyom.getSpruslId().getSpruslStat()}">
            <input type="hidden" name="monit" id="monit" value="${priyom.getSpruslId().getSpruslMonit()}">
            <input type="hidden" name="seans" id="seans" value="${priyom.getSpruslId().getSpruslSeans()}">
            <input type="hidden" name="pmpk" id="pmpk" value="${priyom.getSpruslId().getSpruslPmpk()}">
            <input type="hidden" name="subj" id="subj" value="${priyom.getSpruslId().getSpruslSubj()}">   
            <input type="hidden" name="isVologda" id="isVologda" value="${rolesRight.isVologda()}">
            <div id = "datePr">
                <c:if test="${!copy}">
                    <b>Дата проведения:</b>
                    <input type="date" name="datePriyom" id="datePriyom" value="${datepriyom}">
                    <br>
                </c:if>
                <c:if test="${copy}">
                    <script type="text/javascript">
                        appendDatePr();
                    </script>
                </c:if>
            </div>
            <div id="reg">
                <b>Место проведения:</b>       
                <select id="selReg" onchange="regSelect()"> 
                    <c:forEach var="region" items="${reg}">
                        <c:if test="${region == priyom.getSprregId()}">
                            <option selected value="${region.getSprregId()}">
                                <c:out value="${region.getSprregName()}"/>
                            </option>
                        </c:if>
                        <c:if test="${region != priyom.getSprregId()}">
                            <option value="${region.getSprregId()}">
                                <c:out value="${region.getSprregName()}"/>
                            </option>
                        </c:if>
                    </c:forEach>   
                </select>
                <input type="hidden" name="regId" id="regId" value="${priyom.getSprregId().getSprregId()}">
            </div>             
            <div id="divSpecialists">
                <b>Специалисты:</b>
                <br>            
                <c:forEach var="spec" items="${sotruds}">
                    <div id="spec${i=i+1}">
                        <select id="selDolgn${i}">
                            <c:forEach var="dolgn" items="${dolgns}">
                                <c:if test="${dolgn == spec.getSotruddolgnId().getSprdolgnId()}">
                                    <option selected value="${dolgn.getSprdolgnId()}">
                                        <c:out value="${dolgn.getSprdolgnName()}"/>
                                    </option>
                                </c:if>
                                <c:if test="${dolgn != spec.getSotruddolgnId().getSprdolgnId()}">
                                    <option value="${dolgn.getSprdolgnId()}">
                                        <c:out value="${dolgn.getSprdolgnName()}"/>
                                    </option>
                                </c:if>
                            </c:forEach>      
                        </select>
                        <input type="hidden" name="dolgnId${i}" id="dolgnId${i}" value="${spec.getSotrudId().getSprdolgnId().getSprdolgnId()}">
                        <select id="selSotr${i}">  
                            <c:forEach var="sotr" items="${allsotrud}">
                                <c:if test="${sotr.getSprdolgnId() == spec.getSotruddolgnId().getSprdolgnId()}">
                                    <c:if test="${sotr == spec.getSotruddolgnId()}">
                                        <option selected value="${sotr.getSotruddolgnId()}">
                                            <c:out value="${sotr.getSotrudId().getSotrudFIO()}" />
                                        </option>
                                    </c:if>
                                    <c:if test="${sotr != spec.getSotruddolgnId()}">
                                        <option value="${sotr.getSotruddolgnId()}">
                                            <c:out value="${sotr.getSotrudId().getSotrudFIO()}" />
                                        </option>
                                    </c:if>    
                                </c:if>
                            </c:forEach>                        
                        </select>
                        <input type="hidden" name="sotrId${i}" id="sotrId${i}" value="${spec.getSotruddolgnId().getSotruddolgnId()}">     
                        <img class="btn" id="delSotr${i}" src="img/delete.png" width="16" alt="Удалить" title="Удалить">                
                    </div>
                </c:forEach>
            </div>
            <div id="divPlusSotr">
                <img class="btn" id="plusSotr" src="img/plus.png" width="16" alt="Добавить" title="Добавить">
                <br> 
                <br> 
            </div>
            <div id = "divClients">
                <b>Клиенты:</b>
                <br>
                <c:if test="${kol > 2}">
                    (<c:out value="${kol}" /> чел.)
                </c:if>
                <c:forEach var="child" items="${children}"> 
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
                            <label class="clickable" onclick="javascript:window.open('client?kat=children&id=${child.getChildId()}', '_blank')">
                                <c:out value="${child.getChildFam()} ${child.getChildName()} ${child.getChildPatr()} ${child.getFormat2Dr()}" />
                            </label>
                            <br> 
                            <c:out value="${child.getSprregId().getSprregName()}" />
                            <br>
                            <br>                            
                            <c:if test="${priyom.getSpruslId().getSpruslPmpk() == 1}">
                                <c:if test="${pmpkParents.size() > 0}">
                                    <div id="divParents${j}">
                                        <strong>Законные представители:</strong>                                                                            
                                        <c:set var="ppnom" value="1"/>
                                        <c:forEach var="pp" items="${pmpkParents}">
                                            <c:if test="${pp.getPmpkId().getPrclId().getPrclKatcl().equals('children')}">
                                                <c:if test="${pp.getPmpkId().getPrclId().getClientId() == child.getChildId()}">
                                                    <div id="div${ppnom}Parent${j}">
                                                        <input type="hidden" id="pmpk${ppnom}ParentId${j}" name="pmpk${ppnom}ParentId${j}" value="${pp.getParentId().getParentId()}">
                                                        <c:out value="${ppnom}."/>
                                                        <img class="btn" id="edit${ppnom}Parent${j}" src="img/edit.png" width="15" alt="Редактировать" title="Редактировать">
                                                        <img class="btn" id="del${ppnom}Parent${j}" src="img/delete.png" width="15" alt="Удалить" title="Удалить">
                                                        <br>
                                                        <select id="parentTypeSel${ppnom}PmpkPar${j}" name="parentTypeSel${ppnom}PmpkPar${j}">
                                                        <c:forEach var="pt" items="${parentTypeList}">
                                                            <c:if test="${pt != pp.getSprparenttypeId()}">
                                                                <option value="${pt.getSprparenttypeId()}">
                                                                    <c:out value="${pt.getSprparenttypeName()}" />
                                                                </option>
                                                            </c:if>
                                                            <c:if test="${pt == pp.getSprparenttypeId()}">
                                                                <option selected value="${pt.getSprparenttypeId()}">
                                                                    <c:out value="${pt.getSprparenttypeName()}" />
                                                                </option>
                                                            </c:if>
                                                        </c:forEach>
                                                        </select>
                                                        <input type="hidden" id="parentTypeId${ppnom}PmpkPar${j}" name="parentTypeId${ppnom}PmpkPar${j}" value="${pp.getSprparenttypeId().getSprparenttypeId()}">
                                                        <br>
                                                        <strong><c:out value="${pp.getParentId().getParentNom()}" /></strong>
                                                        <br>
                                                        <label class="clickable" onclick="javascript:window.open('client?kat=parents&id=' + ${pp.getParentId().getParentId()}, '_blank')">
                                                            <c:out value="${pp.getParentId().getParentFam()} ${pp.getParentId().getParentName()} ${pp.getParentId().getParentPatr()}" />
                                                        </label>
                                                        <br>
                                                        <c:out value="${pp.getParentId().getSprregId().getSprregName()}" />
                                                    </div>
                                                    <c:set var="ppnom" value="${ppnom + 1}"/>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>     
                                        <c:if test="${ppnom == 2}">
                                            <div id="div${ppnom}Parent${j}">                                                
                                                <c:out value="2."/>
                                                <img class="btn" id="edit2Parent${j}" src="img/edit.png" width="15" alt="Редактировать" title="Редактировать">
                                                <img class="btn" id="del2Parent${j}" src="img/delete.png" width="15" alt="Удалить" title="Удалить">
                                            </div>
                                        </c:if>
                                    </div>
                                </c:if>
                            </c:if>                            
                            <c:if test="${priyom.getSpruslId().getSpruslStat() == 1}">   
                                <div id="divStat${j}">
                                    <input type="hidden" id="check${j}" name="check${j}"> 
                                    <strong>Статус ребенка:</strong>
                                    <br>
                                    <div id="divStatosn${j}">
                                        <c:forEach var="st" items="${osnStatusList}">
                                            <c:if test="${st.getChild() == child}">
                                                <c:if test="${st.getChecked()}">
                                                    <c:if test="${st.getEnabled()}">                                                        
                                                        <label>
                                                            <c:if test="${st.getStatus().getSprstatN() == 1}">
                                                                <input checked type="checkbox" class="norma" id="statosn${st.getStatus().getSprstatId()}_${j}" name="statosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            </c:if>                                                            
                                                            <c:if test="${st.getStatus().getSprstatN() == 0}">
                                                                <input checked type="checkbox" id="statosn${st.getStatus().getSprstatId()}_${j}" name="statosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            </c:if>
                                                            <c:out value="${st.getStatus().getSprstatName()}" />
                                                        </label>
                                                    </c:if>
                                                    <c:if test="${!st.getEnabled()}">                                                        
                                                        <label style="color: grey">
                                                            <c:if test="${st.getStatus().getSprstatN() == 1}">
                                                                <input checked type="checkbox" class="doNothing, norma" id="statosn${st.getStatus().getSprstatId()}_${j}" name="statosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            </c:if>
                                                            <c:if test="${st.getStatus().getSprstatN() == 0}">
                                                                <input checked type="checkbox" class="doNothing" id="statosn${st.getStatus().getSprstatId()}_${j}" name="statosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            </c:if>
                                                            <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                        </label>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${!st.getChecked()}">
                                                    <c:if test="${st.getEnabled()}">                                                        
                                                        <label>
                                                            <c:if test="${st.getStatus().getSprstatN() == 1}">
                                                                <input type="checkbox" class="norma" id="statosn${st.getStatus().getSprstatId()}_${j}" name="statosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            </c:if>                                                            
                                                            <c:if test="${st.getStatus().getSprstatN() == 0}">
                                                                <input type="checkbox" id="statosn${st.getStatus().getSprstatId()}_${j}" name="statosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            </c:if>
                                                            <c:out value="${st.getStatus().getSprstatName()}" />
                                                        </label>
                                                    </c:if>
                                                    <c:if test="${!st.getEnabled()}">                                                        
                                                        <label style="color: grey">
                                                            <c:if test="${st.getStatus().getSprstatN() == 1}">
                                                                <input type="checkbox" class="doNothing, norma" id="statosn${st.getStatus().getSprstatId()}_${j}" name="statosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            </c:if>
                                                            <c:if test="${st.getStatus().getSprstatN() == 0}">
                                                                <input type="checkbox" class="doNothing" id="statosn${st.getStatus().getSprstatId()}_${j}" name="statosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            </c:if>
                                                            <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                        </label>
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>                                                                                
                                    </div>   
                                    <div id="divStatsoc${j}"> 
                                        <c:forEach var="st" items="${socStatusList}">
                                            <c:if test="${st.getChild() == child}">
                                                <c:if test="${st.getChecked()}">
                                                    <c:if test="${st.getEnabled()}">                                                        
                                                        <label>
                                                            <input checked type="checkbox" id="statsoc${st.getStatus().getSprstatId()}_${j}" name="statsoc${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            <c:out value="${st.getStatus().getSprstatName()}" />
                                                        </label>
                                                    </c:if>
                                                    <c:if test="${!st.getEnabled()}">                                                        
                                                        <label style="color: grey">
                                                            <input checked type="checkbox" class="doNothing" id="statsoc${st.getStatus().getSprstatId()}_${j}" name="statsoc${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                        </label>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${!st.getChecked()}">
                                                    <c:if test="${st.getEnabled()}">                                                        
                                                        <label>
                                                            <input type="checkbox" id="statsoc${st.getStatus().getSprstatId()}_${j}" name="statsoc${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            <c:out value="${st.getStatus().getSprstatName()}" />
                                                        </label>
                                                    </c:if>
                                                    <c:if test="${!st.getEnabled()}">                                                        
                                                        <label style="color: grey">
                                                            <input type="checkbox" class="doNothing" id="statsoc${st.getStatus().getSprstatId()}_${j}" name="statsoc${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                        </label>
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </div>                                    
                                    <div id="divStatdop${j}">
                                        <c:forEach var="st" items="${dopStatusList}">
                                            <c:if test="${st.getChild() == child}">
                                                <c:if test="${st.getChecked()}">
                                                    <c:if test="${st.getEnabled()}">                                                        
                                                        <label>
                                                            <input checked type="checkbox" id="statdop${st.getStatus().getSprstatId()}_${j}" name="statdop${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            <c:out value="${st.getStatus().getSprstatName()}" />
                                                        </label>
                                                    </c:if>
                                                    <c:if test="${!st.getEnabled()}">                                                        
                                                        <label style="color: grey">
                                                            <input checked type="checkbox" class="doNothing" id="statdop${st.getStatus().getSprstatId()}_${j}" name="statdop${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                        </label>
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${!st.getChecked()}">
                                                    <c:if test="${st.getEnabled()}">                                                        
                                                        <label>
                                                            <input type="checkbox" id="statdop${st.getStatus().getSprstatId()}_${j}" name="statdop${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            <c:out value="${st.getStatus().getSprstatName()}" />
                                                        </label>
                                                    </c:if>
                                                    <c:if test="${!st.getEnabled()}">                                                        
                                                        <label style="color: grey">
                                                            <input type="checkbox" class="doNothing" id="statdop${st.getStatus().getSprstatId()}_${j}" name="statdop${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                        </label>
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <c:forEach var="dopst" items="${dopStatusList}">
                                        <c:if test="${dopst.getChild() == child}">
                                            <c:if test="${dopst.getChecked()}">
                                                <div id="divStatpod${dopst.getStatus().getSprstatId()}_${j}">
                                                    <c:forEach var="st" items="${podStatusList}">
                                                        <c:if test="${st.getChild() == child}">
                                                            <c:if test="${st.getMainStatus() == dopst.getStatus()}">
                                                                <c:if test="${st.getChecked()}">
                                                                    <c:if test="${st.getEnabled()}">                                                        
                                                                        <label>
                                                                            <input checked type="checkbox" id="statpod${st.getStatus().getSprstatId()}_${j}" name="statpod${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                            <c:out value="${st.getStatus().getSprstatName()}" />
                                                                        </label>
                                                                    </c:if>
                                                                    <c:if test="${!st.getEnabled()}">                                                        
                                                                        <label style="color: grey">
                                                                            <input checked type="checkbox" class="doNothing" id="statpod${st.getStatus().getSprstatId()}_${j}" name="statpod${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                            <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                                        </label>
                                                                    </c:if>
                                                                </c:if>
                                                                <c:if test="${!st.getChecked()}">
                                                                    <c:if test="${st.getEnabled()}">                                                        
                                                                        <label>
                                                                            <input type="checkbox" id="statpod${st.getStatus().getSprstatId()}_${j}" name="statpod${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                            <c:out value="${st.getStatus().getSprstatName()}" />
                                                                        </label>
                                                                    </c:if>
                                                                    <c:if test="${!st.getEnabled()}">                                                        
                                                                        <label style="color: grey">
                                                                            <input type="checkbox" class="doNothing" id="statpod${st.getStatus().getSprstatId()}_${j}" name="statpod${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                            <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                                        </label>
                                                                    </c:if>
                                                                </c:if>
                                                            </c:if>
                                                        </c:if>                                                            
                                                    </c:forEach>
                                                </div>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>                                     
                                </div>
                            </c:if>    

                            <c:if test="${priyom.getSpruslId().getSpruslPmpk() == 1}">
                                <c:forEach var="pmpk" items="${pmpklist}">
                                    <c:if test="${pmpk.getPrclId().getPrclKatcl().equals('children')}">
                                        <c:if test="${pmpk.getPrclId().getClientId() == child.getChildId()}">
                                            <div id = "divFirstOvz${j}">
                                                <br>
                                                <label>
                                                    <c:if test="${pmpk.getPmpkFirstOvz() == 0}">
                                                        <input type = "checkbox" id = "firstOvz${j}" name = "firstOvz${j}">
                                                    </c:if>
                                                    <c:if test="${pmpk.getPmpkFirstOvz() == 1}">
                                                        <input checked type = "checkbox" id = "firstOvz${j}" name = "firstOvz${j}">
                                                    </c:if>
                                                        ОВЗ выявлены впервые
                                                </label>
                                                <br>
                                                <br>
                                            </div>
                                            <div id="divOu${j}">
                                                Образовательное учреждение: 
                                                <input type="hidden" id="ou${j}" name="ou${j}" value="${pmpk.getPmpkOu()}">
                                                <select id="selOu${j}">
                                                    <c:choose>
                                                        <c:when test="${pmpk.getPmpkOu() == 1}">
                                                            <option selected value="1">посещает</option>
                                                            <option value="0">не посещает</option>
                                                        </c:when>
                                                        <c:when test="${pmpk.getPmpkOu() == 0}">
                                                            <option value="1">посещает</option>
                                                            <option selected value="0">не посещает</option>
                                                        </c:when>
                                                        <c:otherwise>    
                                                            <option selected value="1">посещает</option>
                                                            <option value="0">не посещает</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </select>
                                            </div>
                                            <div id="divPmpk${j}">    
                                                Номер протокола: 
                                                <input type="text" id="nomPr${j}" name="nomPr${j}" value="${pmpk.getPmpkNp()}">                                
                                                <div id="divOp${j}">
                                                    Вид образовательной программы: 
                                                    <c:if test="${pmpk.getSprobrId() != null}">                                    
                                                        <select id="selOptype${j}">
                                                            <option value="0"></option>
                                                            <c:forEach var="optype" items="${obrType}">
                                                                <c:if test="${pmpk.getSprobrId().sprobrtypeId == optype}">
                                                                    <option selected value="${optype.getSprobrtypeId()}">
                                                                        <c:out value="${optype.getSprobrtypeName()}" />
                                                                    </option>
                                                                </c:if>
                                                                <c:if test="${pmpk.getSprobrId().sprobrtypeId != optype}">
                                                                    <option value="${optype.getSprobrtypeId()}">
                                                                        <c:out value="${optype.getSprobrtypeName()}" />
                                                                    </option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                        <div id="op${j}">
                                                            Образовательная программа:
                                                            <select id="selOp${j}">
                                                                <option value="0"></option>
                                                                <c:forEach var="op" items="${obr}">
                                                                    <c:if test="${op.getSprobrtypeId() == pmpk.getSprobrId().sprobrtypeId}">
                                                                        <c:if test="${pmpk.getSprobrId() == op}">
                                                                            <option selected value="${op.getSprobrId()}">
                                                                                <c:out value="${op.getSprobrShname()}" />
                                                                            </option>
                                                                        </c:if>
                                                                        <c:if test="${pmpk.getSprobrId() != op}">
                                                                            <option value="${op.getSprobrId()}">
                                                                                <c:out value="${op.getSprobrShname()}" />
                                                                            </option>
                                                                        </c:if>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </select>
                                                            <input type="hidden" id="opId${j}" name="opId${j}" value="${pmpk.getSprobrId().getSprobrId()}">
                                                        </div>
                                                        <div id="var${j}">  
                                                            <c:set var="o" value="0" />
                                                            <c:forEach var="obrvar" items="${obrVar}">
                                                                <c:if test="${obrvar.getSprobrId() == pmpk.getSprobrId()}">
                                                                    <c:set var="o" value="1" />
                                                                </c:if>
                                                            </c:forEach>
                                                            <c:if test="${o == 1}">
                                                                Вариант образовательной программы:
                                                                <select id="selVar${j}">
                                                                    <option value="0"></option>
                                                                    <c:forEach var="obrvar" items="${obrVar}">
                                                                        <c:if test="${obrvar.getSprobrId() == pmpk.getSprobrId()}">
                                                                            <c:if test="${pmpk.getSprobrvarId() == obrvar}">
                                                                                <option selected value="${obrvar.getSprobrvarId()}">
                                                                                    <c:out value="${obrvar.getSprobrvarName()}" />
                                                                                </option>
                                                                            </c:if>
                                                                            <c:if test="${pmpk.getSprobrvarId() != obrvar}">
                                                                                <option value="${obrvar.getSprobrvarId()}">
                                                                                    <c:out value="${obrvar.getSprobrvarName()}" />
                                                                                </option>
                                                                            </c:if>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </select>
                                                                <input type="hidden" id="varId${j}" name="varId${j}" value="${pmpk.getSprobrvarId().getSprobrvarId()}">
                                                            </c:if>
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${pmpk.getSprobrId() == null}">
                                                        <select id="selOptype${j}">
                                                            <option value="0"></option>
                                                            <c:forEach var="optype" items="${obrType}">
                                                                <option value="${optype.getSprobrtypeId()}">
                                                                    <c:out value="${optype.getSprobrtypeName()}" />
                                                                </option>
                                                            </c:forEach>
                                                        </select>    
                                                    </c:if>
                                                </div>   
                                                <c:if test="${rolesRight.isVologda()}">
                                                <div id="divTpmpk${j}">
                                                    Территориальная ПМПК:
                                                    <input type="hidden" id="tpmpk${j}" name="tpmpk${j}" value="${pmpk.getPmpkTpmpk()}">
                                                    <select id="selTpmpk${j}">
                                                        <c:choose>
                                                            <c:when test="${pmpk.getPmpkTpmpk() == 0}">
                                                                <option selected value="0">нет</option>
                                                                <option value="1">по направлению</option>
                                                                <option value="2">обжалование</option>
                                                            </c:when>
                                                            <c:when test="${pmpk.getPmpkTpmpk() == 1}">
                                                                <option value="0">нет</option>
                                                                <option selected value="1">по направлению</option>
                                                                <option value="2">обжалование</option>
                                                            </c:when>
                                                            <c:when test="${pmpk.getPmpkTpmpk() == 2}">
                                                                <option value="0">нет</option>
                                                                <option value="1">по направлению</option>
                                                                <option selected value="2">обжалование</option>
                                                            </c:when>
                                                            <c:otherwise>    
                                                                <option selected value="0">нет</option>
                                                                <option value="1">по направлению</option>
                                                                <option value="2">обжалование</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </select>
                                                </div>
                                                </c:if>    
                                                <div id="divGia${j}">
                                                    Государственная итоговая аттестация:
                                                    <input type="hidden" id="gia${j}" name="gia${j}" value="${pmpk.getPmpkGia()}">
                                                    <select id="selGia${j}">
                                                        <c:choose>
                                                            <c:when test="${pmpk.getPmpkGia() == 0}">
                                                                <option selected value="0">нет</option>
                                                                <option value="1">ГИА-9</option>
                                                                <option value="2">ГИА-11</option>
                                                            </c:when>
                                                            <c:when test="${pmpk.getPmpkGia() == 1}">
                                                                <option value="0">нет</option>
                                                                <option selected value="1">ГИА-9</option>
                                                                <option value="2">ГИА-11</option>
                                                            </c:when>
                                                            <c:when test="${pmpk.getPmpkGia() == 2}">
                                                                <option value="0">нет</option>
                                                                <option value="1">ГИА-9</option>
                                                                <option selected value="2">ГИА-11</option>
                                                            </c:when>
                                                            <c:otherwise>    
                                                                <option selected value="0">нет</option>
                                                                <option value="1">ГИА-9</option>
                                                                <option value="2">ГИА-11</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </select>                                    
                                                </div>
                                                <c:if test="${rolesRight.isVologda()}">
                                                <div id="divIpr${j}">
                                                    <label>
                                                        <c:if test="${pmpk.getPmpkIpr() == 1}">                                    
                                                            <input checked type="checkbox" id="ipr${j}" name="ipr${j}">
                                                        </c:if>
                                                        <c:if test="${pmpk.getPmpkIpr() == 0}">
                                                            <input type="checkbox" id="ipr${j}" name="ipr${j}">
                                                        </c:if>
                                                        Оказание содействия в разработке ИПР ребенка-инвалида
                                                    </label>                               
                                                </div>
                                                </c:if>
                                                <div id="divRekomend${j}">
                                                    Рекомендовано:
                                                    <br>
                                                    <c:forEach var="rek" items="${rekomend}">
                                                        <c:set var="m" value="0" />
                                                        <c:forEach var="prek" items="${pmpkRek}">
                                                            <c:if test="${prek.getPmpkId() == pmpk}">
                                                                <c:if test="${prek.getSprrekId() == rek}">
                                                                    <c:set var="m" value="1" />
                                                                </c:if>
                                                            </c:if>                                            
                                                        </c:forEach>
                                                        <label>
                                                            <c:if test="${m == 1}">
                                                                <input checked type="checkbox" id="rek${rek.getSprrekId()}_${j}" name="rek${rek.getSprrekId()}_${j}" value="${rek.getSprrekId()}">
                                                                <c:out value="${rek.getSprrekName()}" />
                                                            </c:if>
                                                            <c:if test="${m != 1}">
                                                                <input type="checkbox" id="rek${rek.getSprrekId()}_${j}" name="rek${rek.getSprrekId()}_${j}" value="${rek.getSprrekId()}">
                                                                <c:out value="${rek.getSprrekName()}" />
                                                            </c:if>
                                                        </label>
                                                        <br>
                                                    </c:forEach>      
                                                </div>
                                                <div id="divDopobs${j}">
                                                    <label>
                                                        <c:if test="${pmpk.getPmpkDop() == 1}">
                                                            <input checked type="checkbox" id="dopObsled${j}" name="dopObsled${j}">
                                                        </c:if>
                                                        <c:if test="${pmpk.getPmpkDop() == 0}">
                                                            <input type="checkbox" id="dopObsled${j}" name="dopObsled${j}">
                                                        </c:if>
                                                        Дополнительное обследование
                                                    </label>
                                                </div>
                                                <div id = "divSrok${j}">
                                                    <label>
                                                        Срок действия заключения: 
                                                    </label>
                                                    <select id = "selSrok${j}" name = "selSrok${j}">                                                        
                                                        <option value = 0>
                                                            до окончания ступени обучения
                                                        </option>
                                                        <c:if test="${pmpk.getPmpkDatek() != null}">
                                                            <option selected value = 1>
                                                                до
                                                            </option>
                                                    </select>
                                                            <input id = "srokDate${j}" name = "srokDate${j}" type = "date" value = ${pmpk.getFormatDatek()}>
                                                        </c:if>         
                                                        <c:if test="${pmpk.getPmpkDatek() == null}">
                                                            <option value = 1>
                                                                до
                                                            </option>
                                                    </select>
                                                        </c:if>
                                                                                                        
                                                </div>    
                                                <div id="divSogl">
                                                    С заключением:  
                                                    <input type="hidden" id="sogl${j}" name="sogl${j}" value="${pmpk.getPmpkSogl()}">
                                                    <select id="selSogl${j}">
                                                        <c:choose>
                                                            <c:when test="${pmpk.getPmpkSogl() == 1}">
                                                                <option selected value="1">согласен</option>
                                                                <option value="0">не согласен</option>
                                                            </c:when>
                                                            <c:when test="${pmpk.getPmpkSogl() == 0}">
                                                                <option value="1">согласен</option>
                                                                <option selected value="0">не согласен</option>
                                                            </c:when>
                                                            <c:otherwise>    
                                                                <option selected value="1">согласен</option>
                                                                <option value="0">не согласен</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </select>
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${priyom.getSpruslId().getSpruslMonit() == 1}">
                                <div id="divMonit${j}">
                                    <br>
                                    Оказанной услугой:                    
                                    <select id="selMonit${j}">
                                        <c:forEach var="prcl" items="${priyomclient}">
                                            <c:if test="${prcl.getPrclKatcl().equals('children')}">
                                                <c:if test="${prcl.getClientId() == child.getChildId()}">
                                                    <c:choose>
                                                        <c:when test="${prcl.getPrclUdovl() == 1}">
                                                            <option selected value="1">удовлетворен</option>
                                                            <option value="0">не удовлетворен</option>
                                                        </c:when>
                                                        <c:when test="${prcl.getPrclUdovl() == 0}">
                                                            <option value="1">удовлетворен</option>
                                                            <option selected value="0">не удовлетворен</option>
                                                        </c:when>
                                                        <c:otherwise>    
                                                            <option selected value="1">удовлетворен</option>
                                                            <option value="0">не удовлетворен</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>                            
                                    </select>
                                    <input type="hidden" id="monit${j}" name="monit${j}" value=""> 
                                </div>
                            </c:if>
                        </div>
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
                            <select id="parentTypeSel${j}" name="parentTypeSel${j}">
                                <c:forEach var="pt" items="${parentTypeList}">
                                    <c:forEach var="prcl" items="${priyomclient}">
                                        <c:if test="${prcl.getPrclKatcl().equals('parents')}">
                                            <c:if test="${prcl.getClientId() == parent.getParentId()}">
                                                <c:if test="${pt != prcl.getSprparenttypeId()}">
                                                    <option value="${pt.getSprparenttypeId()}">
                                                        <c:out value="${pt.getSprparenttypeName()}" />
                                                    </option>
                                                </c:if>
                                                <c:if test="${pt == prcl.getSprparenttypeId()}">
                                                    <option selected value="${pt.getSprparenttypeId()}">
                                                        <c:out value="${pt.getSprparenttypeName()}" />
                                                    </option>
                                                </c:if>
                                            </c:if>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                            </select>
                            <c:forEach var="prcl" items="${priyomclient}">
                                <c:if test="${prcl.getPrclKatcl().equals('parents')}">
                                    <c:if test="${prcl.getClientId() == parent.getParentId()}">
                                        <input type="hidden" id="parentTypeId${j}" name="parentTypeId${j}" value="${prcl.getSprparenttypeId().getSprparenttypeId()}">
                                    </c:if>                                        
                                </c:if>
                            </c:forEach>
                            <br>
                            <label class="clickable" onclick="javascript:window.open('client?kat=parents&id=' + ${parent.getParentId()}, '_blank')">
                                <c:out value="${parent.getParentFam()} ${parent.getParentName()} ${parent.getParentPatr()}" />
                            </label>
                            <br>
                            <c:out value="${parent.getSprregId().getSprregName()}" />
                            <br>
                            <br>
                            <c:if test="${priyom.getSpruslId().getSpruslMonit() == 1}">
                                <div id="divMonit${j}">
                                    Оказанной услугой:
                                    <select id="selMonit${j}">
                                        <c:forEach var="prcl" items="${priyomclient}">
                                            <c:if test="${prcl.getPrclKatcl().equals('parents')}">
                                                <c:if test="${prcl.getClientId() == parent.getParentId()}">
                                                    <c:choose>
                                                        <c:when test="${prcl.getPrclUdovl() == 1}">
                                                            <option selected value="1">удовлетворен</option>
                                                            <option value="0">не удовлетворен</option>
                                                        </c:when>
                                                        <c:when test="${prcl.getPrclUdovl() == 0}">
                                                            <option value="1">удовлетворен</option>
                                                            <option selected value="0">не удовлетворен</option>
                                                        </c:when>
                                                        <c:otherwise>    
                                                            <option selected value="1">удовлетворен</option>
                                                            <option value="0">не удовлетворен</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>                            
                                    </select>
                                    <input type="hidden" id="monit${j}" name="monit${j}" value="">        
                                </div>
                            </c:if>
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
                            <c:if test="${priyom.getSpruslId().getSpruslMonit() == 1}">
                                <div id="divMonit${j}">
                                    Оказанной услугой:
                                    <select id="selMonit${j}">
                                        <c:forEach var="prcl" items="${priyomclient}">
                                            <c:if test="${prcl.getPrclKatcl().equals('ped')}">
                                                <c:if test="${prcl.getClientId() == ped.getPedId()}">
                                                    <c:choose>
                                                        <c:when test="${prcl.getPrclUdovl() == 1}">
                                                            <option selected value="1">удовлетворен</option>
                                                            <option value="0">не удовлетворен</option>
                                                        </c:when>
                                                        <c:when test="${prcl.getPrclUdovl() == 0}">
                                                            <option value="1">удовлетворен</option>
                                                            <option selected value="0">не удовлетворен</option>
                                                        </c:when>
                                                        <c:otherwise>    
                                                            <option selected value="1">удовлетворен</option>
                                                            <option value="0">не удовлетворен</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>                            
                                    </select>
                                    <input type="hidden" id="monit${j}" name="monit${j}">        
                                </div>
                            </c:if>
                        </div>                
                    </div>            
                </c:forEach>
            </div>    
            <div id="divPlusClient">
                <img class="btn" id="plusCl" src="img/plus.png" width="20" alt="Добавить клиента" title="Добавить клиента">
                <br>
            </div>
            <c:if test="${priyom.getSpruslId().getSpruslSubj() == 1}">
                <div id="divSubjects">
                    <strong>
                        Субъекты консультирования:
                    </strong>
                    <c:set var="c" value="1" />
                    <c:set var="j" value="0" />
                    <c:if test="${childrenSub.size() == 0}">
                        <div class="clients-1" id = "sub1">
                            <input type="hidden" name="subKat1" id="subKat1" value="children">
                            <img class="btn" id="editSub1" src="img/edit.png" width="20" alt="Редактировать" title="Редактировать">                        
                        </div>
                    </c:if>
                    <c:forEach var="child" items="${childrenSub}">                    
                        <c:set var="l" value="0" />    
                        <c:set var="c" value="${c * (-1)}" />  
                        <c:set var="j" value="${j+1}" />
                        <c:if test="${j > 1}">
                            <div id="divhr${j}">
                                <hr>
                            </div>
                        </c:if>
                        <div class="clients${c}" id = "sub${j}">  
                            <input type="hidden" name="subId${j}" id="subId${j}" value="${child.getChildId()}">
                            <input type="hidden" name="subKat${j}" id="subKat${j}" value="children">
                            <img class="btn" id="editSub${j}" src="img/edit.png" width="20" alt="Редактировать" title="Редактировать">
                            <img class="btn" id="delSub${j}" src="img/delete.png" width="20" alt="Удалить" title="Удалить">   
                            <br>
                            <div id="infoSubject${j}">
                                <strong><c:out value="${child.getChildNom()}" /></strong>
                                <br>
                                <label class="clickable" onclick="javascript:window.open('client?kat=children&id=' + ${child.getChildId()}, '_blank')">
                                    <c:out value="${child.getChildFam()} ${child.getChildName()} ${child.getChildPatr()} ${child.getFormat2Dr()}" />
                                </label>
                                <br> 
                                <c:out value="${child.getSprregId().getSprregName()}" />
                                <br>
                                <br>
                                <c:if test="${priyom.getSpruslId().getSpruslStat() == 1}">   
                                    <div id="divSubstat${j}">
                                        <input type="hidden" id="checkSub${j}" name="checkSub${j}"> 
                                        <strong>Статус ребенка:</strong>
                                        <br>
                                        <div id="divSubstatosn${j}">
                                            <c:forEach var="st" items="${osnStatusList}">
                                                <c:if test="${st.getChild() == child}">
                                                    <c:if test="${st.getChecked()}">
                                                        <c:if test="${st.getEnabled()}">                                                        
                                                            <label>
                                                                <c:if test="${st.getStatus().getSprstatN() == 1}">
                                                                    <input checked type="checkbox" class="norma" id="subStatosn${st.getStatus().getSprstatId()}_${j}" name="subStatosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                </c:if>                                                            
                                                                <c:if test="${st.getStatus().getSprstatN() == 0}">
                                                                    <input checked type="checkbox" id="subStatosn${st.getStatus().getSprstatId()}_${j}" name="subStatosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                </c:if>
                                                                <c:out value="${st.getStatus().getSprstatName()}" />
                                                            </label>
                                                        </c:if>
                                                        <c:if test="${!st.getEnabled()}">                                                        
                                                            <label style="color: grey">
                                                                <c:if test="${st.getStatus().getSprstatN() == 1}">
                                                                    <input checked type="checkbox" class="doNothing, norma" id="subStatosn${st.getStatus().getSprstatId()}_${j}" name="subStatosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                </c:if>
                                                                <c:if test="${st.getStatus().getSprstatN() == 0}">
                                                                    <input checked type="checkbox" class="doNothing" id="subStatosn${st.getStatus().getSprstatId()}_${j}" name="subStatosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                </c:if>
                                                                <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                            </label>
                                                        </c:if>
                                                    </c:if>
                                                    <c:if test="${!st.getChecked()}">
                                                        <c:if test="${st.getEnabled()}">                                                        
                                                            <label>
                                                                <c:if test="${st.getStatus().getSprstatN() == 1}">
                                                                    <input type="checkbox" class="norma" id="subStatosn${st.getStatus().getSprstatId()}_${j}" name="subStatosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                </c:if>                                                            
                                                                <c:if test="${st.getStatus().getSprstatN() == 0}">
                                                                    <input type="checkbox" id="subStatosn${st.getStatus().getSprstatId()}_${j}" name="subStatosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                </c:if>
                                                                <c:out value="${st.getStatus().getSprstatName()}" />
                                                            </label>
                                                        </c:if>
                                                        <c:if test="${!st.getEnabled()}">                                                        
                                                            <label style="color: grey">
                                                                <c:if test="${st.getStatus().getSprstatN() == 1}">
                                                                    <input type="checkbox" class="doNothing, norma" id="subStatosn${st.getStatus().getSprstatId()}_${j}" name="subStatosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                </c:if>
                                                                <c:if test="${st.getStatus().getSprstatN() == 0}">
                                                                    <input type="checkbox" class="doNothing" id="subStatosn${st.getStatus().getSprstatId()}_${j}" name="subStatosn${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                </c:if>
                                                                <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                            </label>
                                                        </c:if>
                                                    </c:if>
                                                </c:if>
                                            </c:forEach>                                                                                
                                        </div>   
                                        <div id="divSubstatsoc${j}">
                                            <c:forEach var="st" items="${socStatusList}">
                                                <c:if test="${st.getChild() == child}">
                                                    <c:if test="${st.getChecked()}">
                                                        <c:if test="${st.getEnabled()}">                                                        
                                                            <label>
                                                                <input checked type="checkbox" id="subStatsoc${st.getStatus().getSprstatId()}_${j}" name="subStatsoc${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                <c:out value="${st.getStatus().getSprstatName()}" />
                                                            </label>
                                                        </c:if>
                                                        <c:if test="${!st.getEnabled()}">                                                        
                                                            <label style="color: grey">
                                                                <input checked type="checkbox" class="doNothing" id="subStatsoc${st.getStatus().getSprstatId()}_${j}" name="subStatsoc${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                            </label>
                                                        </c:if>
                                                    </c:if>
                                                    <c:if test="${!st.getChecked()}">
                                                        <c:if test="${st.getEnabled()}">                                                        
                                                            <label>
                                                                <input type="checkbox" id="subStatsoc${st.getStatus().getSprstatId()}_${j}" name="subStatsoc${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                <c:out value="${st.getStatus().getSprstatName()}" />
                                                            </label>
                                                        </c:if>
                                                        <c:if test="${!st.getEnabled()}">                                                        
                                                            <label style="color: grey">
                                                                <input type="checkbox" class="doNothing" id="subStatsoc${st.getStatus().getSprstatId()}_${j}" name="subStatsoc${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                            </label>
                                                        </c:if>
                                                    </c:if>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                        <div id="divSubstatdop${j}">
                                            <c:forEach var="st" items="${dopStatusList}">
                                                <c:if test="${st.getChild() == child}">
                                                    <c:if test="${st.getChecked()}">
                                                        <c:if test="${st.getEnabled()}">                                                        
                                                            <label>
                                                                <input checked type="checkbox" id="subStatdop${st.getStatus().getSprstatId()}_${j}" name="subStatdop${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                <c:out value="${st.getStatus().getSprstatName()}" />
                                                            </label>
                                                        </c:if>
                                                        <c:if test="${!st.getEnabled()}">                                                        
                                                            <label style="color: grey">
                                                                <input checked type="checkbox" class="doNothing" id="subStatdop${st.getStatus().getSprstatId()}_${j}" name="subStatdop${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                            <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                            </label>
                                                        </c:if>
                                                    </c:if>
                                                    <c:if test="${!st.getChecked()}">
                                                        <c:if test="${st.getEnabled()}">                                                        
                                                            <label>
                                                                <input type="checkbox" id="statdop${st.getStatus().getSprstatId()}_${j}" name="statdop${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                <c:out value="${st.getStatus().getSprstatName()}" />
                                                            </label>
                                                        </c:if>
                                                        <c:if test="${!st.getEnabled()}">                                                        
                                                            <label style="color: grey">
                                                                <input type="checkbox" class="doNothing" id="statdop${st.getStatus().getSprstatId()}_${j}" name="statdop${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                            </label>
                                                        </c:if>
                                                    </c:if>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                        <c:forEach var="dopst" items="${dopStatusList}">
                                            <c:if test="${dopst.getChild() == child}">
                                                <c:if test="${dopst.getChecked()}">
                                                    <div id="divSubstatpod${dopst.getStatus().getSprstatId()}_${j}">
                                                        <c:forEach var="st" items="${podStatusList}">
                                                            <c:if test="${st.getChild() == child}">
                                                                <c:if test="${st.getMainStatus() == dopst.getStatus()}">
                                                                    <c:if test="${st.getChecked()}">
                                                                        <c:if test="${st.getEnabled()}">                                                        
                                                                            <label>
                                                                                <input checked type="checkbox" id="subStatpod${st.getStatus().getSprstatId()}_${j}" name="subStatpod${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                                <c:out value="${st.getStatus().getSprstatName()}" />
                                                                            </label>
                                                                        </c:if>
                                                                        <c:if test="${!st.getEnabled()}">                                                        
                                                                            <label style="color: grey">
                                                                                <input checked type="checkbox" class="doNothing" id="subStatpod${st.getStatus().getSprstatId()}_${j}" name="subStatpod${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                                <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                                            </label>
                                                                        </c:if>
                                                                    </c:if>
                                                                    <c:if test="${!st.getChecked()}">
                                                                        <c:if test="${st.getEnabled()}">                                                        
                                                                            <label>
                                                                                <input type="checkbox" id="subStatpod${st.getStatus().getSprstatId()}_${j}" name="subStatpod${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                                <c:out value="${st.getStatus().getSprstatName()}" />
                                                                            </label>
                                                                        </c:if>
                                                                        <c:if test="${!st.getEnabled()}">                                                        
                                                                            <label style="color: grey">
                                                                                <input type="checkbox" class="doNothing" id="subStatpod${st.getStatus().getSprstatId()}_${j}" name="subStatpod${st.getStatus().getSprstatId()}_${j}" value="${st.getStatus().getSprstatId()}">
                                                                                <c:out value="${st.getStatus().getSprstatName()}" /> 
                                                                            </label>
                                                                        </c:if>
                                                                    </c:if>
                                                                </c:if>
                                                            </c:if>                                                            
                                                        </c:forEach>
                                                    </div>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>                                     
                                    </div>
                                </c:if>
                            </div>
                        </div>  
                    </c:forEach>
                </div>
                <div id="divPlusSubj">
                    <img class="btn" id="plusSub" src="img/plus.png" width="20" alt="Добавить субъект" title="Добавить субъект">
                    <br>
                </div>
            </c:if>    
            <c:if test="${priyom.getSpruslId().getSpruslProblem() == 1}">
                <div id="divProblem">
                    Выявленные проблемы:
                    <c:forEach var="problem" items="${problems}">
                        <div id="problem${k=k+1}">
                            <select id="selPrtype${k}">
                                <c:forEach var="prtype" items="${problemtypes}">
                                    <c:if test="${prtype == problem.getSprproblemId().getSprproblemtypeId()}">
                                        <option selected value="${prtype.getSprproblemtypeId()}">
                                            <c:out value="${prtype.getSprproblemtypeKod()} ${prtype.getSprproblemtypeName()}" />
                                        </option>
                                    </c:if>
                                    <c:if test="${prtype != problem.getSprproblemId().getSprproblemtypeId()}">
                                        <option value="${prtype.getSprproblemtypeId()}">
                                            <c:out value="${prtype.getSprproblemtypeKod()} ${prtype.getSprproblemtypeName()}" />
                                        </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <select id="selPr${k}">         
                                <c:forEach var="pr" items="${sprproblem}">
                                    <c:if test="${pr.getSprproblemtypeId() == problem.getSprproblemId().getSprproblemtypeId()}">
                                        <c:if test="${pr == problem.getSprproblemId()}">
                                            <option selected value="${pr.getSprproblemId()}">
                                                <c:out value="${pr.getSprproblemKod()} ${pr.getSprproblemName()}" />
                                            </option>
                                        </c:if>
                                        <c:if test="${pr != problem.getSprproblemId()}">
                                            <option value="${pr.getSprproblemId()}">
                                                <c:out value="${pr.getSprproblemKod()} ${pr.getSprproblemName()}" />
                                            </option>
                                        </c:if>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <input type="hidden" id="prId${k}" name="prId${k}">
                            <img class="btn" id="delProblem${k}" src="img/delete.png" width="20" alt="Удалить" title="Удалить">                    
                        </div>
                    </c:forEach>        
                </div>
                <div id="divPlusProblem">
                    <img class="btn" id="plusProbl" src="img/plus.png" width="20" alt="Добавить проблему" title="Добавить проблему">
                </div>
            </c:if>
            <div id="divButton">
                <br>                           
                <input class="btn" type="button" name="saveBtn" id = "saveBtn" value="Сохранить" onclick="validate();">
                <input class="btn" type="button" name="copyBtn" id = "copyBtn" value="Копировать список в новый прием" onclick="copyPriyom()">
                <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                    <input class="btn" type="button" name="delBtn" id = "delBtn" value="Удалить прием" onclick="delPriyom()">
                </c:if> 
            </div>            
        </form> 
        <script type="text/javascript">
            initPriyom(); 
        </script>
    </c:if>
    </body>
    <dialog id="searchClDialog" class="searchDlg">      
        <div id="family">            
            <br>
        </div>
        <div id="dialogTitle">
            Категория клиента:
            <select id="selKat" onchange="changeKat()">
                <option value="children">дети</option>
                <option value="parents">законные представители</option>
                <option value="ped">педагоги</option>
            </select>
            <input type="hidden" id="type">
        </div>
        <form id="dialogForm">
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
        </form>        
    </dialog>  
    <dialog id="copyPriyomDialog">
        Выберите услугу:
        <br>
        <select id="selUsl">
            <c:forEach var="usl" items="${uslList}">
                <option value="${usl.getSpruslId()}">
                    <c:out value="${usl.getSpruslName()}" />
                </option>
            </c:forEach>
        </select>
        <input type="button" value="Копировать" onclick="okCopy()">
    </dialog>
</html>
