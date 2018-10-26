<%-- 
    Document   : adminmain
    Created on : 05.04.2016, 11:08:23
    Author     : Aiglu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Администрирование</title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <h3>Сотрудники и пользователи</h3>
        <table class="puptab">
            <tr>
                <td onclick="window.open('sotrud', '_blank')">
                    Список сотрудников
                </td>
                <td onclick="window.open('users', '_blank')">
                    Список пользователей
                </td>
            </tr>            
        </table>
        <h3>Клиенты</h3>
        <table class="puptab">
            <tr>
                <td onclick="window.open('todouble', '_blank')">
                    Слияние дублей клиентов
                </td>                
            </tr>                
        </table>
        <br>
        <br>
        <br>
        <h3>Служебные скрипты (только для программиста!)</h3>
        <table class="puptab">
            <tr>
                <td onclick="window.open('movestatus', '_blank')">
                    Перенос статусов
                </td>       
                <td onclick="window.open('corrstatus?action=corr', '_blank')">
                    Исправление сроков статусов
                </td> 
                <td onclick="window.open('moveipra?action=open', '_blank')">
                    Перенос из ИПРА в ИПРА2018
                </td>
                <td onclick="window.open('corripra', '_blank')">
                    Исправления в ИПРА2018
                </td>                
                <td onclick="window.open('setchildrenpol', '_blank')">
                    Определение пола детей
                </td>
                <td onclick="window.open('moveipra?action=18tonew', '_blank')">
                    Перенос ИПРА2018 в новую структуру
                </td>
                <td onclick="window.open('corrstatus?action=datek', '_blank')">
                    Исправление сроков окончания статусов
                </td>
            </tr>                
        </table>
    </c:if>
</body>
</html>
