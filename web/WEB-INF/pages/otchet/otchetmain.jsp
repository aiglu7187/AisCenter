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
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Отчеты</title>
    </head>
    <body>
    <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
        <h3>Отчеты</h3>
        <table class="puptab">
            <tr>
                <td onclick="window.open('otchet?id=goszad', '_blank')">
                    Общий отчет по госзаданию
                </td>    
                <td onclick="window.open('otchet?id=statuslkat', '_blank')">
                    Статистический отчет по услугам и категориям клиентов
                </td>
                <td onclick="window.open('otchet?id=status', '_blank')">
                    Отчет по статусам детей
                </td>
                <td onclick="window.open('otchet?id=problem', '_blank')">
                    Отчет по выявленным проблемам
                </td>
                <td onclick="window.open('otchet?id=age', '_blank')">
                    Отчет по возрасту детей
                </td>         
                <td onclick="window.open('otchet?id=consultpar', '_blank')">
                    Отчет по консультированию родителей (категории)
                </td>
            </tr>            
        </table>      
        <h3>Реестры</h3>
        <table class="puptab">
            <tr>
                <td onclick="window.open('otchet?id=reestrusl', '_blank')">
                    Список клиентов по услуге со специалистами
                </td>  
            </tr>            
        </table>
        <h3>Ранний возраст</h3>
        <table class="puptab">
            <tr>
                <td onclick="window.open('otchet?id=ranniy', '_blank')">
                    Реестры и статистика
                </td>                  
            </tr>            
        </table>
    </c:if>
    </body>
</html>
