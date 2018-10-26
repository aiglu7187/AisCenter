<%-- 
    Document   : newuser
    Created on : 02.06.2016, 14:14:38
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="userview1122.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Новый пользователь</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <form id="formUser" action="saveuser" accept-charset="windows-1251" method="POST">
            <input id="userId" name="userId" type="hidden">
            Имя пользователя: <input id="nam" name="nam" type="text">
            <br>
            <br>
            Пароль: <input id="pass" name="pass" type="text" >
            <br>
            <br>
            Сотрудник: 
            <input id="sotrudId" name="sotrudId" type="hidden">
            <select id="sotr">
                <c:forEach var="s" items="${sotrud}">                    
                    <option value="${s.getSotrudId()}">
                        <c:out value="${s.getSotrudFIO()}" />
                    </option>                    
                </c:forEach>
            </select>     
            <br>
            <br>       
            Роль пользователя: 
            <input id="roleId" name="roleId" type="hidden">
            <select id="roles">
                <c:forEach var="r" items="${roles}">
                    <option value="${r.getRoleId()}">
                        <c:out value="${r.getRoleName()}" />
                    </option>                    
                </c:forEach>
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
