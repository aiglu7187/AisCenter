<%-- 
    Document   : addpriyom
    Created on : Jan 18, 2016, 4:10:38 PM
    Author     : admin_ai
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="addpriyom0310.js" charset="utf-8"></script>
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Прием</title>
    </head>
    <body>
    <c:if test="${user != null}">
        <form id="formPriyom" action="savepriyom" accept-charset="windows-1251" method="POST">
            <h3><c:out value="${osnuslName}" /></h3>
            <div id="divUsl">Выберите услугу:        
                <select id="selUsl" onchange="uslSelect()">       
                    <option selected value="111"></option>
                    <c:forEach var="usl" items="${uslList}">
                        <option value="${usl.getSpruslId()}">
                            <c:out value="${usl.getSpruslName()}"/>
                        </option>
                    </c:forEach>
                </select>
                <input type="hidden" name="uslId" id="uslId" value="">
                <input type="hidden" name="problem" id="problem" value="">
                <input type="hidden" name="stat" id="stat" value="">
                <input type="hidden" name="monit" id="monit" value="">
                <input type="hidden" name="seans" id="seans" value="">
                <input type="hidden" name="pmpk" id="pmpk" value="">
                <input type="hidden" name="subj" id="subj" value="">
                <input type="hidden" name="isVologda" id="isVologda" value="${rolesRight.isVologda()}">
            </div>        
            <div id = "datePr">
            </div>
            <div id="reg">
            </div>
            <div id="divSpecialists">
            </div>
            <div id="divPlusSotr">
            </div>
            <div id = "divClients">
            </div>
            <div id="divPlusClient">
            </div>
            <div id="divSubjects">
            </div>
            <div id="divPlusSubj">
            </div>
            <div id="divProblem">
            </div>
            <div id="divPlusProblem">
            </div>
            <div id="divSeans">
            </div>
            <div id="divButton">
            </div>            
        </form> 
    </c:if>
    </body>    
    
    <dialog id="searchClDialog" class="searchDlg">
        <div id="family">                                   
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
</html>