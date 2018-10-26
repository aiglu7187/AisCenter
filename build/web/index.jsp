<%-- 
    Document   : index
    Created on : Jan 10, 2016, 3:55:29 PM
    Author     : admin_ai
--%>

<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>АИС "Сопровождение" - Авторизация</title>
    </head>
    <body>        
        <form action="gotomain" method="POST">
            Имя пользователя: <br>
            <input type="text" name="user_name" maxlength="20">
            <br>
            Пароль: <br>
            <input type="password" name="user_password" maxlength="50">
            <br>
            <br>
            <input type="submit" name="submit_btn" value="Войти">            
        </form>
    </body>
</html>
