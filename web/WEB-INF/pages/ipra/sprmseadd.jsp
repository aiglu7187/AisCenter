<%-- 
    Document   : sprmse
    Created on : 21.02.2018, 15:20:36
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="sprmse0410.js" charset="utf-8"></script>
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Добавить бюро МСЭ в справочник</title>
    </head>
    <body>
        <c:if test="${user != null}">
            <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
                <h3>Добавить бюро МСЭ в справочник</h3>
                <form id="mseForm">
                    Название: <input type="text" id="msename" name="msename" size="50">
                    <br>
                    <br>
                    Короткое название: <input type="text" id="mseshname" name="mseshname" size="50">
                    <br>
                    <br>
                    <%--Руководитель: <input type="text" id="msechief" name="msechief" size="46">
                    <br>
                    <br>
                    Адрес: <input type="text" id="mseadr" name="mseadr"  size="53">
                    <br><br>--%>
                    Название в родительном падеже (для приказа): <input type="text" id="msenamerod" name="msenamerod" size="50">
                    <br>
                    <br>
                    Название в творительном падеже (для приказа): <input type="text" id="msenametv" name="msenametv" size="50">
                    <br>
                    <br>
                </form>
                <input type="button" id="saveBtn" value="Сохранить" onclick="validate()">                
            </c:if>
        </c:if>
    </body>
</html>
