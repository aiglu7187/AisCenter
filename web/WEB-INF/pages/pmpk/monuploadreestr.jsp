<%-- 
    Document   : monuploadreestr
    Created on : 12.07.2018, 14:04:14
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
        <title>Загрузка файлов мониторинга</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <form id="uploadreestrForm">
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
                    Дата мониторинга: 
                    <input id = "monDate" name = "monDate" type = "date">
                    <br>
                    <br>
                    Номер строки, с которой начинаются данные: 
                    <input id = "inpRow" name = "inpRow" type = "text">
                </div>
                <div id="divFields">
                    <strong>
                        Поля для загрузки:
                    </strong>
                    <div>
                        Фамилия: <input type="text" id="field_fam" name="field_fam">
                        <br>
                        Имя: <input type="text" id="field_name" name="field_name">
                        <br>
                        Отчество: <input type="text" id="field_patr" name="field_patr">
                        <br>
                        Дата рождения: <input type="text" id="field_dr" name="field_dr">
                        <br>
                        Дата ПМПК: <input type="text" id="field_pmpkd" name="field_pmpkd">
                        <br>
                        Заключение ПМПК (предъявлено/нет): <input type="text" id="field_zakl" name="field_zakl">
                        <br>
                        Образовательная организация: <input type="text" id="field_ou" name="field_ou">
                        <br>
                        Форма образования: <input type="text" id="field_form" name="field_form">
                        <br>
                        Ступень обучения (класс, группа): <input type="text" id="field_stage" name="field_stage">
                        <br>
                        Программа обучения: <input type="text" id="field_op" name="field_op">
                        <br>
                        Вариант: <input type="text" id="field_var" name="field_var">
                        <br>
                        Занятия: <input type="text" id="field_les" name="field_les">
                        <br>
                        Услуги ассистента и т.д.: <input type="text" id="field_as" name="field_as">
                        <br>
                        Спец.оборудование: <input type="text" id="field_eq" name="field_eq">
                        <br>
                        Причины невыполнения рекомендаций: <input type="text" id="field_reas" name="field_reas">
                    </div>
                </div>
                <div id="divBtn">
                    <input type="button" id="uploadBtn" value="Загрузить" onclick="uploadfiles()">
                </div>
            </form>
        </c:if>
    </body>
    <script type="text/javascript">
        regSelect();
    </script>
</html>
