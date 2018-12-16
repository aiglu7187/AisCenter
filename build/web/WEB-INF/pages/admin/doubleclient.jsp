<%-- 
    Document   : doubleclient
    Created on : 13.06.2016, 14:39:56
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        <script type="text/javascript" src="doubleclient0507.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles1216.css" rel="stylesheet" type="text/css">
        <title>Слияние дублей клиентов</title>
    </head>
    <body>
        <h3>Слияние дублей клиентов</h3>
        <p>Выберите клиента, который будет основным</p>
        <div id = "osncl">
            <select id="CBClient" name="kat" onchange="clearAll()">
                <option value="children">ребенок</option>
                <option value="parents">родитель</option>
                <option value="ped">педагог</option>
            </select>
            <a class="dial" id="searchOsnCl">Найти</a>
            <p id="osnclient"></p>
        </div>            
        <p>Выберите клиента для слияния (после слияния к основному будет удалён)</p>
        <div id = "doubcl">            
            <a class="dial" id="searchDoubCl">Найти</a>
            <p id="doubclient"></p>
        </div>
        <input type="button" value="Объединить" name="okBtn" id="okBtn" onClick="union()">
        <script type="text/javascript">
            init();                        
        </script>
    </body>
    
    <dialog id="searchClDialog" class="searchDlg">
        <div class="overflow">
        <p id="dialogTitle"></p>
        <form id="dialogForm">
            <p>Фамилия: <input type="text" name="fam" id="fam" onkeyup="search()">
                Имя: <input type="text" name="nam" id="nam" onkeyup="search()">
                Отчество: <input type="text" name="patr" id="patr" onkeyup="search()">                
            </p>
            <table class="no">
                <tr>
                    <td></td>                    
                    <td><a class="dial" id = "cancelClient">Отмена</a> </td>
                </tr>
            </table>
            <table class="regular" id="dialogTable"> 
            </table>
            <input type="hidden" name="kat" id="kat">
            <input type="hidden" name="pr" id="pr">
        </form>
        </div>
    </dialog>    
</html>
