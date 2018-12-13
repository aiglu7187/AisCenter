<%-- 
    Document   : ipraspisok
    Created on : 25.05.2017, 11:50:18
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="ipra18spisok1207.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Список ИПРА 2018</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
            <div id="divSearch">
                <strong>
                    Поиск
                </strong>
                <br>
                Фамилия:
                <input type="text" id="searchFam">
                Имя: 
                <input type="text" id="searchName">
                Отчество:
                <input type="text" id="searchPatr">
                <br>
                Район проживания:
                <select id="selReg">
                    <option value="0"></option>
                    <c:forEach var="reg" items="${regions}">
                        <option value="${reg.getSprregId()}">
                            <c:out value="${reg.getSprregName()}" />
                        </option>
                    </c:forEach>
                </select>
                <br>
                Дата приказа:
                <input type="date" id="searchDPr">                
                Номер приказа:
                <input type="text" id="searchNPr">
                <br> 
                <input type="button" id="searchBtn" value="Поиск" onclick="searchIpra()">
                <input type="button" id="clearSearchBtn" value="Очистить поиск" onclick="clearSearch()">
                <br>
                <br>
                <input type="button" id="addIpra" value="Добавить ИПРА" onclick="javascript: window.open('ipra2018spisok?action=add', '_blank')">
                <br>
                <input type="button" id="printRed" value="Печать списка с ближайшими сроками" onclick="printRed()">
                <input type="button" id="printSvod" value="Печать сводного реестра" onclick="printSvod()">
                <input type="button" id="printNoInfo" value="Печать реестра ИПРА без запроса/отказа" onclick="openPrintNoInfo()">
            </div>
            <input type="hidden" id="sort" value="do1">
            <div id="ipraSpisok">                
                <table id="ipra" class="regular">
                    <thead>
                        <tr>
                            <td></td>
                            <td>
                                Фамилия
                            </td>                        
                            <td>
                                Имя
                            </td>
                            <td>
                                Отчество
                            </td>
                            <td>
                                Дата рождения
                            </td>
                            <td>
                                Район
                            </td>
                            <td>
                                Номер приказа ДО
                            </td>
                            <td>
                                Дата приказа ДО
                                <br>
                                <img id="sortascpr" class="btn" src="img/sortasc.png" width="16">
                                <img id="sortdescpr" class="btn" src="img/sortdesc.png" width="16">
                            </td>
                            <td>
                                Дата отправки в ОМСУ
                                <br>
                                <img id="sortascomsu" class="btn" src="img/sortasc.png" width="16">
                                <img id="sortdescomsu" class="btn" src="img/sortdesc.png" width="16">
                            </td>
                            <td>
                                Дата отчёта ОМСУ
                            </td>
                            <td>
                                Дата отчёта ОЦППМСП
                            </td>
                            <td>
                                Дата отчёта ДО
                                <br>
                                <img id="sortascdo" class="btn" src="img/sortasc.png" width="16">
                                <img id="sortdescdo" class="btn" src="img/sortdesc.png" width="16">                                
                            </td>                        
                        </tr>
                    </thead>
                    <tbody id="tabbody">

                    </tbody>
                </table>
            </div>
        </c:if>
    </body>
    <script type="text/javascript">
        init();
    </script>
</html>
