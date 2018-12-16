<%-- 
    Document   : ipramain
    Created on : 17.05.2017, 18:59:01
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>ИПРА</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <h3>Календарь</h3>
            <table class="puptab">
                <tr>
                    <td onclick="window.open('kalendar?action=toview', '_blank')">
                        Просмотр календаря
                    </td>     
                    <td onclick="window.open('kalendar?action=toadd', '_blank')">
                        Добавить календарь
                    </td> 
                </tr>            
            </table>    
        </c:if>
        <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
            <h3>Справочники</h3>
            <table class="puptab">
                <tr>
                    <td onclick="window.open('sprmse?action=sprlist', '_blank')">
                        Справочник бюро МСЭ
                    </td>     
                    <td onclick="window.open('spromsu?action=sprlist', '_blank')">
                        Справочник ОМСУ
                    </td>                      
                </tr>            
            </table>
            <h3>Исходящая корреспонденция</h3>
            <table class="puptab">
                <tr>
                    <td onclick="window.open('ishcorresp?action=list', '_blank')">
                        Реестр исходящих писем
                    </td>  
                    <td onclick="window.open('ipraishnom?action=open', '_blank')">
                        Настройка нумерации писем
                    </td> 
                </tr>            
            </table>
            <h3>ИПРА</h3>
            <table class="puptab">
                <tr>
                    <td onclick="window.open('ipraspisok?action=spisok', '_blank')">
                        Список ИПРА
                    </td>     
                    <td onclick="window.open('ipraspisok?action=add', '_blank')">
                        Добавить ИПРА
                    </td>                     
                    <td onclick="window.open('ipraspisok?action=archive', '_blank')">
                        Архив
                    </td> 
                    <%--
                <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
                    <td onclick="window.open('ipraspisok?action=upload', '_blank')">
                        Загрузка реестра
                    </td>
                </c:if>--%>
                </tr>            
            </table>
            <h3>ИПРА 2018</h3>
            <table class="puptab">
                <tr>
                    <td onclick="window.open('ipra2018spisok?action=add', '_blank')">
                        Добавить ИПРА
                    </td>
                    <td onclick="window.open('ipra2018spisok?action=spisok', '_blank')">
                        Список ИПРА
                    </td>    
                    <td onclick="window.open('ipra2018spisok?action=tpmpkreqlist', '_blank')">
                        Запросы в ТПМПК
                    </td>
                    <td onclick="window.open('ipra2018spisok?action=archive', '_blank')">
                        Архив
                    </td>                     
                </tr>            
            </table>
            
        <%--    <h3>Новая версия ИПРА 2018</h3>
            <table class="puptab">
                <tr>
                    <td onclick="window.open('ipra2018new?action=openlist&tab=1', '_blank')">
                        Список ИПРА
                    </td>             
                </tr>            
            </table>--%>
        </c:if>
    </body>
</html>
