<%-- 
    Document   : nipra18list
    Created on : 27.09.2018, 15:01:40
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="nipra18list0927.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Список ИПРА 2018</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
            <div class="tabs">
                <input id="tab1" type="radio" name="tabs" checked>
                <label for="tab1" title="Список ИПРА">Список ИПРА</label>
                <input id="tab2" type="radio" name="tabs">
                <label for="tab2" title="Нет приказа">Нет приказа</label>
                <input id="tab3" type="radio" name="tabs">
                <label for="tab3" title="Нет перечня мероприятий">Нет перечня мероприятий</label>
                <input id="tab4" type="radio" name="tabs">
                <label for="tab4" title="Для запросов в ТПМПК">Для запросов в ТПМПК</label>
                <input id="tab5" type="radio" name="tabs">
                <label for="tab5" title="Ближайшие отчёты">Ближайшие отчёты</label>
                <input id="tab6" type="radio" name="tabs">
                <label for="tab6" title="Архив">Архив</label>
                <section id="content-tab1">
                    <div id="div1">
                        <div id="divSearch1" style="margin-bottom: 30px">                            
                            <p style="margin-top: 1px; margin-bottom: 5px">
                                Фамилия: <input type="text" id="searchFam1" name="searchFam" style="width: 150px;" maxlength="100">
                                Имя: <input type="text" id="searchName1" name="searchName" style="width: 150px;" maxlength="100">
                                Отчество: <input type="text" id="searchPatr1" name="searchPatr" style="width: 150px;" maxlength="100">                            
                                Район: 
                                <select id="selReg1" name="selReg">
                                    <option value="0"></option>
                                    <c:forEach var="reg" items="${regions}">
                                        <option value="${reg.getSprregId()}">
                                            <c:out value="${reg.getSprregName()}" />
                                        </option>
                                    </c:forEach>
                                </select>
                            </p>
                            <p style="margin-top: 1px; margin-bottom: 5px">
                                Дата приказа: <input type="date" id="searchDPr1" name="searchDPr" style="width: 150px; height: 20px;">
                                Номер приказа: <input type="text" id="searchNPr1" name="searchNPr" style="width: 150px;" maxlength="50">
                            </p>
                            <p style="margin-top: 15px; margin-bottom: 15px; float:left">
                                <a class="greybtn" id="searchBtn1" name="searchBtn">
                                    <img id="searchImg1" name="searchImg" src="img/search.png" width="24" style="margin-right: 3px;">
                                    Поиск
                                </a>
                                <a class="greybtn" id="clearSearchBtn1" name="clearSearchBtn" style="margin-left: 30px">
                                    <img id="clearsearchImg1" name="clearsearchImg" src="img/delete.png" width="16" style="margin-right: 2px; margin-top: 10px;">
                                    Очистить поиск
                                </a>
                            </p>
                            <p style="margin-top: 25px; margin-bottom: 15px; float:right">
                                <a class="greybtn" id="addBtn1" name="addBtn">
                                    <img id="addImg1" name="addImg" src="img/plus.png" width="16" style="margin-left: 3px;">
                                    Добавить ИПРА
                                </a> 
                            </p>    
                        </div>
                        <div id="divList1">
                            <table id="listTable1" class="regularlist">
                                <thead id="tabHead1"></thead>
                                <tbody id="tabBody1"></tbody>
                            </table>
                        </div>
                    </div>
                </section>
                <section id="content-tab2">
                    <div id="div2">
                        <div id="divList2">
                            <table id="listTable2" class="regularlist">
                                <thead id="tabHead2"></thead>
                                <tbody id="tabBody2"></tbody>
                            </table>
                        </div>
                    </div>
                </section>
                <section id="content-tab3">
                    <div id="div3">
                        <div id="divList3">
                            <table id="listTable3" class="regularlist">
                                <thead id="tabHead3"></thead>
                                <tbody id="tabBody3"></tbody>
                            </table>
                        </div>
                    </div>
                </section>
                <section id="content-tab4">
                    <div id="div4">
                        <div id="divBtn4">
                            <p style="margin-top: 15px; margin-bottom: 15px">
                                <a class="dsbldbtn" id="formreqBtn4" name="formreqBtn" onclick="formReq()">                                    
                                    Сформировать запрос
                                </a>
                            </p>
                        </div>
                        <div id="divList4">
                            <table id="listTable4" class="regularlist">
                                <thead id="tabHead4"></thead>
                                <tbody id="tabBody4"></tbody>
                            </table>
                        </div>
                    </div>
                </section>
                <section id="content-tab5">
                    <div id="div5">
                        <table id="listTable5" class="regularlist">
                            <thead id="tabHead5"></thead>
                            <tbody id="tabBody5"></tbody>
                        </table>
                    </div>
                </section>
                <section id="content-tab6">
                    <div id="div6">
                        <div id="divSearch6" style="margin-bottom: 30px">                            
                            <p style="margin-top: 1px; margin-bottom: 5px">
                                Фамилия: <input type="text" id="searchFam6" name="searchFam" style="width: 150px;" maxlength="100">
                                Имя: <input type="text" id="searchName6" name="searchName" style="width: 150px;" maxlength="100">
                                Отчество: <input type="text" id="searchPatr6" name="searchPatr" style="width: 150px;" maxlength="100">                            
                                Район: 
                                <select id="selReg6" name="selReg">
                                    <option value="0"></option>
                                    <c:forEach var="reg" items="${regions}">
                                        <option value="${reg.getSprregId()}">
                                            <c:out value="${reg.getSprregName()}" />
                                        </option>
                                    </c:forEach>
                                </select>
                            </p>                            
                            <p style="margin-top: 15px; margin-bottom: 5px">
                                <a class="greybtn" id="searchBtn6" name="searchBtn">
                                    <img id="searchImg6" name="searchImg" src="img/search.png" width="24" style="margin-right: 3px;">
                                    Поиск
                                </a>
                                <a class="greybtn" id="clearSearchBtn6" name="clearSearchBtn" style="margin-left: 30px">
                                    <img id="clearsearchImg6" name="clearsearchImg" src="img/delete.png" width="16" style="margin-right: 2px; margin-top: 10px;">
                                    Очистить поиск
                                </a>
                            </p>
                        </div>
                        <div id="divList6">
                            <table id="listTable6" class="regularlist">
                                <thead id="tabHead6"></thead>
                                <tbody id="tabBody6"></tbody>
                            </table>
                        </div>
                    </div>
                </section>
            </div>
        </c:if>
    </body>    
    <script type="text/javascript">
        init();
    </script>
</html>
