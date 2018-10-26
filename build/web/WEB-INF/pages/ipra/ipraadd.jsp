<%-- 
    Document   : ipraadd
    Created on : 25.05.2017, 11:50:29
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript" src="ipra0701.js" charset="utf-8"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Добавить ИПРА</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
            <h3>Добавить ИПРА</h3>
            <form id="ipraForm" action="saveipra" accept-charset="windows-1251" method="POST">
                <div id="divForm">
                    <strong>
                        Ребенок
                    </strong>
                    <br>
                    <input type="button" id="searchBtn" value="Найти" onclick="callSearchDialog()">
                    <br>
                    <div id="divInfoChild">

                    </div>
                </div>
            </form>
        </c:if>
    </body>    
    <dialog id="searchClDialog" class="searchDlg">                
        <form id="dialogForm">
            <div id="search">Фамилия: <input type="text" name="fam" id="fam" onkeyup="searchChild()">
                Имя: <input type="text" name="nam" id="nam" onkeyup="searchChild()">
                Отчество: <input type="text" name="patr" id="patr" onkeyup="searchChild()">                
            </div>
            <table class="no">
                <tr>                    
                    <td><a class="dial" id="newClient" onclick="newChild()">Новый</a> </td>
                    <td><a class="dial" id="cancelDialog" onclick="cancelDialog()">Отмена</a> </td>
                </tr>
            </table>
            <table class="regular" id="dialogTable">                 
            </table>
            <input type="hidden" name="nomClPp" id="nomClPp">
        </form>        
    </dialog> 
</html>
