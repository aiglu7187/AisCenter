<%-- 
    Document   : pmpkmain
    Created on : 10.02.2017, 12:44:27
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>ПМПК</title>
    </head>
    <body>
        <h3>ПМПК</h3>
        <table class="puptab">
                <tr>
                    <td onclick="window.open('pmpk?action=addpmpk', '_blank')">
                        Новое заседание ПМПК
                    </td>                         
                </tr>            
            </table>
        <c:if test="${user.getRoleId().getRoleName().equals('administrator')}">
            <h3>Отчеты</h3>
            <table class="puptab">
                <tr>
                    <td onclick="window.open('otchet?id=statpmpk', '_blank')">
                        Статистический отчет по ПМПК
                    </td>     
                    <td onclick="window.open('otchet?id=statpmpkstatus', '_blank')">
                        Отчет ПМПК по статусам
                    </td> 
                    <td onclick="window.open('otchet?id=statpmpkrek', '_blank')">
                        Отчет ПМПК по рекомендациям
                    </td> 
                    <c:if test="${rolesRight.isVologda()}">
                        <td onclick="window.open('otchet?id=statpmpkpar', '_blank')">
                            Отчет ПМПК по параметрам (ИПР, ГИА, ТПМПК)
                        </td> 
                    </c:if>
                </tr>            
            </table>    
            <h3>Реестры</h3>
            <table class="puptab">
                <tr>                
                    <td onclick="window.open('otchet?id=rpmpk', '_blank')">
                        Реестр детей, прошедших ПМПК (ОВЗ и ОП)
                    </td>     
                    <td onclick="window.open('otchet?id=rpmpkfull', '_blank')">
                        Реестр детей, прошедших ПМПК (полный, с возрастом, полом и рекомендациями)
                    </td> 
                    <td onclick="window.open('otchet?id=pmpkstatus', '_blank')">
                        Реестр детей по статусам
                    </td>
                    <c:if test="${rolesRight.isVologda()}">
                        <td onclick="window.open('otchet?id=pmpkipr', '_blank')">
                            Реестр детей ИПР
                        </td>
                    </c:if>
                    <td onclick="window.open('otchet?id=pmpkgia', '_blank')">
                        Реестр детей ГИА
                    </td>
                    <c:if test="${rolesRight.isVologda()}">
                        <td onclick="window.open('otchet?id=pmpkter', '_blank')">
                            Реестр детей ТПМПК
                        </td>
                    </c:if>
                    <td onclick="window.open('otchet?id=pmpkfirstovz', '_blank')">
                        Реестр детей, у которых впервые выявлены ОВЗ
                    </td>
                    <td onclick="window.open('otchet?id=pmpkrek', '_blank')">
                        Реестр детей с рекомендациями
                    </td>
                    <td onclick="window.open('pmpk?action=pmpksearch', '_blank')">
                        Поиск по ПМПК
                    </td>
                </tr>            
            </table>            
            <c:if test="${rolesRight.isVologda()}">
                <h3>Выгрузка реестров для мониторинга</h3>
                <table class="puptab">
                    <tr>                
                        <td onclick="window.open('otchet?id=rpmpkmonit', '_blank')">
                            Выгрузка реестров детей, прошедших ПМПК
                        </td> 
                        <td onclick="window.open('otchet?id=toovzfgos', '_blank')">
                            Выгрузка реестра по ФГОС
                        </td>                 
                        <td onclick="window.open('otchet?id=toovzarch', '_blank')">
                            Выгрузка для мониторинга ОВЗ (в архивы по районам)
                        </td>                        
                    </tr>            
                </table> 
                 
                <h3>Загрузка данных мониторинга</h3>
                <table class="puptab">
                    <tr>
                        <td onclick="window.open('monitoring?id=totestreestr', '_blank')">
                            Проверка файлов перед загрузкой
                        </td>                 
                        <td onclick="window.open('monitoring?id=touploadreestr', '_blank')">
                            Загрузка файлов мониторинга
                        </td> 
                        <td onclick="window.open('monitoring?id=toovzcompare', '_blank')">
                            Сверка мониторинга (общая)
                        </td>  
                        <td onclick="window.open('monitoring?id=toovzupload', '_blank')">
                            Загрузка результатов (ОВЗ, место обучения)
                        </td>
                        <td onclick="window.open('monitoring?id=upload', '_blank')">
                            Загрузка мониторинга (ПМПК)
                        </td> 
                        <td onclick="window.open('monitoring?id=view', '_blank')">
                            Просмотр результатов мониторинга
                        </td> 
                    </tr>            
                </table> 
            </c:if>
        </c:if>
    </body>
</html>
