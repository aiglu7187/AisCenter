<%-- 
    Document   : sotrudview
    Created on : 26.05.2016, 10:24:57
    Author     : Aiglu
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="sotrudview0428.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Сотрудник - <c:out value="${sotrud.getSotrudFIO()}" /></title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <form id="formSotrud" action="savesotrud" accept-charset="windows-1251" method="POST">
                <input id="sotrudId" name="sotrudId" type="hidden" value="${sotrud.getSotrudId()}">
                Фамилия: <input id="fam" name="fam" type="text" value="${sotrud.getSotrudFam()}">
                <br>
                <br>
                Имя: <input id="nam" name="nam" type="text" value="${sotrud.getSotrudName()}">
                <br>
                <br>
                Отчество: <input id="patr" name="patr" type="text" value="${sotrud.getSotrudPatr()}">
                <br>
                <br>
                Должности:
                <div id="divDolgn">

                    <c:forEach var="d" items="${dolgn}">
                        <c:set var="k" value="0"/>
                        <c:forEach var="sd" items="${sotrudDolgn}">
                            <label>
                                <c:if test="${sd.getSprdolgnId().equals(d)}">
                                    <input checked type="checkbox" id="dolgn${d.getSprdolgnId()}" name="dolgn${d.getSprdolgnId()}" value="${d.getSprdolgnId()}">
                                    <c:out value="${d.getSprdolgnName()}" />
                                    <c:set var="k" value="1"/>
                                </c:if>
                            </label>                        
                        </c:forEach>
                        <c:if test="${k == 0}">
                            <label>
                                <input type="checkbox" id="dolgn${d.getSprdolgnId()}" name="dolgn${d.getSprdolgnId()}" value="${d.getSprdolgnId()}">
                                <c:out value="${d.getSprdolgnName()}" />
                            </label>                        
                        </c:if>
                        <br>
                    </c:forEach>
                </div>
                <br>            
                <input type="hidden" id="act" name="act" value="${sotrud.getSotrudActive()}">
                <select id="active">
                    <c:if test="${sotrud.getSotrudActive() == 1}">
                        <option selected value="1">Работает</option>
                        <option value="0">Не работает</option>
                    </c:if>
                    <c:if test="${sotrud.getSotrudActive() == 0}">
                        <option value="1">Работает</option>
                        <option selected value="0">Не работает</option>
                    </c:if>
                </select>
                <br>
                <br>
                <input type="button" name="saveBtn" id = "saveBtn" value="Сохранить" onclick="validate()">
                <input type="button" name="closeBtn" id = "closeBtn" value="Закрыть" onclick="javascript:window.close();">
            </form>        
            <script type="text/javascript">
                init();
            </script>
        </c:if>
    </body>
</html>

