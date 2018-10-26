<%-- 
    Document   : spisokclient
    Created on : 13.02.2016, 12:45:14
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="spisokclient0215.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title><c:out value="${katru}" /></title>
    </head>
    <body>
    <c:if test="${user != null}">
        <h2><c:out value="${katru}" /></h2>
        <p>Поиск</p>
        <form>
            <input type="hidden" name="kat" id="kat" value="<%= session.getAttribute("kat") %>">
            <p>Номер: <input type="text" name="nom" id="nom" onkeyup="searchnom()">
                Фамилия: <input type="text" name="fam" id="fam" onkeyup="search()">
                Имя: <input type="text" name="nam" id="nam" onkeyup="search()">
                Отчество: <input type="text" name="patr" id="patr" onkeyup="search()">                
            </p>
            <p class="kol" id="kol"></p>
        </form>
        <table class="regular" id="clientTable"> 
            <col class="c1"><col span="2">
        </table>            
        <script type="text/javascript">
            init();
        </script>
    </c:if>
    </body>
</html>
