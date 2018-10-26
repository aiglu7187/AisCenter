<%-- 
    Document   : spromsu
    Created on : 21.02.2018, 15:20:36
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="spromsu0618.js" charset="utf-8"></script>
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Добавить ОМСУ в справочник</title>
    </head>
    <body>
        <c:if test="${user != null}">
            <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
                <h3>Добавить ОМСУ в справочник</h3>
                <form id="omsuForm">
                    <strong>Название:</strong> <input type="text" id="omsuname" name="omsuname" size="100">
                    <br>
                    <br>
                    <strong>Руководитель:</strong>
                    <br>
                    Фамилия: <input type="text" id="omsuchieffam" name="omsuchieffam" size="20">
                    Имя: <input type="text" id="omsuchiefname" name="omsuchiefname" size="20">
                    Отчество: <input type="text" id="omsuchiefpatr" name="omsuchiefpatr" size="20">
                    <br>
                    Должность: <input type="text" id="omsuchiefdolgn" name="omsuchiefdolgn" size="20">
                    <br>
                    Должность в дательном падеже: <input type="text" id="omsuchiefdolgndat" name="omsuchiefdolgndat" size="20">
                    <br>
                    <br>
                    <strong>Район:</strong> 
                    <input type="hidden" id="regId" name="regId" value="${regList.get(0).getSprregId()}">
                    <select id="selReg" onchange="regSelect()">
                        <c:forEach var="reg" items="${regList}" >
                            <option value="${reg.getSprregId()}">
                                <c:out value="${reg.getSprregName()}" />
                            </option>
                        </c:forEach>
                    </select>
                    <br>
                    <br>
                    <%--Адрес: <input type="text" id="omsuadr" name="omsuadr"  size="53">
                    <br>
                    <br>--%>
                    Название в дательном падеже: <input type="text" id="omsunamedat" name="omsunamedat" size="100">
                    <br>
                    Название в родительном падеже: 
                    <input type="text" id="omsunamerod" name="omsunamerod" size="100">
                    <br>
                    <br>
                </form>
                <input type="button" id="saveBtn" value="Сохранить" onclick="validate()">                
            </c:if>
        </c:if>
    </body>
</html>
