<%-- 
    Document   : spisokclusl
    Created on : 24.03.2016, 16:39:22
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="spisokclusl0829.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Список клиентов по услугам</title>
    </head>
    <body>
    <c:if test="${user != null}">
        <p>Поиск</p>
        <form>
            <p>Вид услуги: 
                <select id="osnuslSel" onchange="changeOsnusl()">
                    <option value="0"></option>
                    <c:forEach var="osnusl" items="${osnuslList}">
                        <option value="${osnusl.getSprosnuslId()}">
                            <c:out value="${osnusl.getSprosnuslName()}" />
                        </option>
                    </c:forEach>
                </select>                                    
            </p>
            <p id="searchP">
                Услуга: 
                <select id="uslSel" onchange="search()">       
                    <option value="0">Все</option>                    
                </select>                        
                <br>
                <br>
                Период проведения с: <input type="date" name="datepr1" id="datepr1" onkeyup="search()">
                по: <input type="date" name="datepr2" id="datepr2" onkeyup="search()">                
                <br>                
                <br>
                Категория клиентов:
                <select id="katSel" onchange="search()">
                    <option value="0">Все</option>
                    <option value="1">Дети</option>
                    <option value="2">Законные представители</option>
                    <option value="3">Педагоги</option>
                </select>
                <br>
                <br>
                Район проведения услуги: 
                <select id="regSel" onchange="search()">
                    <option value="0">Все</option>
                    <c:forEach var="regUsl" items="${regListUsl}">
                        <option value="${regUsl.getSprregId()}">
                            <c:out value="${regUsl.getSprregName()}"/>
                        </option>
                    </c:forEach>                    
                </select>     
                Район проживания клиентов: 
                <select id="regClSel" onchange="search()">
                    <option value="0">Все</option>
                    <c:forEach var="regCl" items="${regListCl}">
                        <option value="${regCl.getSprregId()}">
                            <c:out value="${regCl.getSprregName()}"/>
                        </option>
                    </c:forEach>                    
                </select>
            </p>
            <p class="kol" id="kol"></p>
        </form>
        <table class="regular" id="clientTable"> 
            <col class="c1"><col span="2">
        </table>            
        <script type="text/javascript">
            initClUsl();
        </script>
    </c:if>
    </body>
</html>
