<%-- 
    Document   : ipra18tpmpkspisok
    Created on : 26.04.2018, 14:34:50
    Author     : Aiglu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="ipra18tpmpk.js" charset="utf-8"></script>
        <link href="css/styles0622.css" rel="stylesheet" type="text/css">
        <title>Запросы в ТПМПК</title>
    </head>
    <body>
        <c:if test="${user.getRoleId().getRoleName().equals('ipra') || user.getRoleId().getRoleName().equals('administrator')}">
            <div id="divButtons">
                <input type="button" id="formreqbtn" disabled value="Сформировать запрос" onclick="formreq()">
            </div>
            <div id="ipraTpmpkSpisok">                
                <table id="ipra" class="regular">
                    <thead>
                        <tr>
                            <td></td>
                            <td><input type="checkbox" id="checkAll" onclick="checkAll()"></td>
                            <td>
                                Фамилия
                            </td>                        
                            <td>
                                Имя
                            </td>
                            <td>
                                Отчество
                            </td>
                            <td>
                                Дата рождения
                            </td>
                            <td>
                                Район
                            </td>
                            <td>
                                ТПМПК
                            </td>
                            <td>
                                Дата запроса в ТПМПК
                            </td>                                         
                        </tr>
                    </thead>
                    <tbody id="tabbody">
                        <c:forEach var="ipraPrikaz" items="${ipraPrikazList}">
                            <tr>
                                <td>
                                    <c:out value="${ipraPrikaz.getIpra18Id().getIpra18Id()}" />
                                </td>
                                <td><input type="checkbox" id="check${ipraPrikaz.getIpra18Id().getIpra18Id()}"></td>
                                <td>
                                    <c:out value="${ipraPrikaz.getIpra18Id().getChildId().getChildFam()}" />
                                </td>                        
                                <td>
                                    <c:out value="${ipraPrikaz.getIpra18Id().getChildId().getChildName()}" />
                                </td>
                                <td>
                                    <c:out value="${ipraPrikaz.getIpra18Id().getChildId().getChildPatr()}" />
                                </td>
                                <td>
                                    <c:out value="${ipraPrikaz.getIpra18Id().getChildId().getFormat2Dr()}" />
                                </td>
                                <td>
                                    <c:out value="${ipraPrikaz.getIpra18Id().getChildId().getSprregId().getSprregName()}" />
                                </td>
                                <td>
                                    <c:out value="${ipraPrikaz.getSprotherpmpkId().getSprotherpmpkShname()}" />
                                </td>
                                <c:if test="${ipraPrikaz.getIpra18prikazTpmpkD().getTime() - now.getTime() <= 0}">
                                    <td style="color:red">                                    
                                        <c:out value="${ipraPrikaz.getFormat2Date(ipraPrikaz.getIpra18prikazTpmpkD())}" />
                                    </td> 
                                </c:if>                                
                                <c:if test="${ipraPrikaz.getIpra18prikazTpmpkD().getTime() - now.getTime() > 0}">
                                    <td>                                    
                                        <c:out value="${ipraPrikaz.getFormat2Date(ipraPrikaz.getIpra18prikazTpmpkD())}" />
                                    </td> 
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </body>
    <dialog id="formreqDialog">        
    </dialog>
    <script type="text/javascript">
        init();
    </script>
</html>
