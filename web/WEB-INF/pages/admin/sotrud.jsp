<%-- 
    Document   : sotrud
    Created on : 25.05.2016, 11:58:36
    Author     : Aiglu
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="sotrud0215.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Сотрудники</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <h2>Сотрудники</h2>
        <input type="button" id="newSotrudBtn" name="newSotrudBtn" value="Добавить нового сотрудника" onclick="newSotrud()">
        <form>  
            <p>Должность:                 
                <select id="dolgn" onchange="search()">
                    <option selected value="0">Все</option>
                    <c:forEach var="d" items="${dolgnList}">
                        <option value="${d.getSprdolgnId()}">
                            <c:out value="${d.getSprdolgnName()}" />
                        </option>
                    </c:forEach>
                </select>
            </p>
            <p>Фамилия: <input type="text" name="fam" id="fam" onkeyup="search()">               
            </p>            
        </form>
        <table class="regular" id="sotrudTable"> 
            <col class="c1"><col span="2">
        </table>            
        <script type="text/javascript">
            init();
        </script>
        </c:if>
    </body>
</html>
