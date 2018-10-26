<%-- 
    Document   : montestreestr
    Created on : 27.02.2018, 16:44:38
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="montestreestr0822.js" charset="utf-8"></script>
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Проверка файлов перед загрузкой</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <form id="testreestrForm">
                <div id="divFiles">
                    Выберите файл для проверки (.xls):
                    <br>
                    <c:forEach var="file" items="${monitFiles}">
                        <label>
                            <input type="radio" value="${file}" name="files" >
                            <c:out value="${file}" />
                        </label>
                        <br>
                    </c:forEach>
                    <input type="hidden" id="regId" name="regId">
                    <select id="selReg" onchange="regSelect()">    
                        <c:forEach var="reg" items="${regList}">
                            <option value="${reg.getSprregId()}" >
                                <c:out value="${reg.getSprregName()}" />
                            </option>
                        </c:forEach>
                    </select>
                    <br>   
                    Номер строки, с которой начинаются данные: 
                    <input id = "inpRow" name = "inpRow" type = "text">
                </div>
                <div id="divFields">
                    Поля для проверки:
                    <div id="divFio">
                        <label>
                            <input type="checkbox" value="0" id="chb_fio" name="chb_fio">
                            <c:out value="ФИО и дата рождения (корректность даты и существование в БД)" />
                        </label>                
                        <div id="divFioDetails"></div>
                    </div>
                    <div id="divOo">
                        <label>
                            <input type="checkbox" value="0" id="chb_oo" name="chb_oo">
                            <c:out value="Образовательная организация (по справочнику)" />
                        </label>
                        <div id="divOoDetails"></div>
                    </div>
                    <div id="divStage">
                        <label>
                            <input type="checkbox" value="0" id="chb_stage" name="chb_stage">
                            <c:out value="Ступень обучения (по справочнику)" />
                        </label>
                        <div id="divStageDetails"></div>
                    </div>
                    <div id="divOp">
                        <label>
                            <input type="checkbox" value="0" id="chb_op" name="chb_op">
                            <c:out value="Образовательная программа (по справочнику)" />
                        </label>
                        <div id="divOpDetails"></div>
                    </div>
                    <div id="divFormobr">
                        <label>
                            <input type="checkbox" value="0" id="chb_formobr" name="chb_formobr">
                            <c:out value="Форма получения образования" />
                        </label>
                        <div id="divFormobrDetails"></div>
                    </div>
                </div>
                <div id="divBtn">
                    <input type="button" id="testBtn" value="Проверить" onclick="testfiles()">
                </div>
            </form>
        </c:if>
    </body>
    <script type="text/javascript">
        init();
    </script>
</html>
