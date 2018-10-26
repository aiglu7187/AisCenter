<%-- 
    Document   : users
    Created on : 25.05.2016, 11:58:46
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="users0215.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Пользователи</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <h2>Пользователи</h2>
        <input type="button" id="newUserBtn" name="newUserBtn" value="Добавить нового пользователя" onclick="newUser()">  
        <br>
        <br>
        <table class="regular" id="usersTable"> 
            <col class="c1"><col span="2">
        </table>            
        <script type="text/javascript">
            init();
        </script>
        </c:if>
    </body>
</html>
