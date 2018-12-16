<%-- 
    Document   : newsotrud
    Created on : 26.05.2016, 12:39:34
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
        <title>Новый сотрудник</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <form id="formSotrud" action="savesotrud" accept-charset="windows-1251" method="POST">            
                Фамилия: <input id="fam" name="fam" type="text">
                <br>
                <br>
                Имя: <input id="nam" name="nam" type="text">
                <br>
                <br>
                Отчество: <input id="patr" name="patr" type="text">
                <br>
                <br>
                Должность: 
                <div id="divDolgn">
                    <c:forEach var="d" items="${dolgn}">
                        <label>
                            <input type="checkbox" id="dolgn${d.getSprdolgnId()}" name="dolgn${d.getSprdolgnId()}" value="${d.getSprdolgnId()}">
                            <c:out value="${d.getSprdolgnName()}" />
                        </label>
                        <br>
                    </c:forEach>
                </div>    
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
