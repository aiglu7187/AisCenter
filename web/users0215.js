/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init(){
    var url = "search?type=users";
    requ(url, "GET");
}

function requ(url, method){ // формирование запроса
    var requ = null;   
    requ = initRequest();
    requ.open(method, url, true);
    requ.onreadystatechange = callback;
    requ.send(null);
}

function initRequest(){
    return new XMLHttpRequest();
}

function callback(){
    call(this);
}

function call(request) {   // получаем результат
    if (request.readyState == 4) {
        if (request.status == 200) {            
            parseMessages(request.responseXML);
        }
    }
}

function parseMessages(responseXML) {   // преобразование полученного xml
    if (responseXML == null) {
        return false;
    } 
    else {
        var users = responseXML.getElementsByTagName("users")[0];
        if (users != null) {
            clearUsersTable();
            if (users.childNodes.length > 0) {
                var head = users.childNodes[0];
                appendHead(head);
                for (loop = 1; loop < users.childNodes.length; loop++) {
                    var client = users.childNodes[loop];
                    appendBody(client, selectUser);
                }
            }
            users = null;
        }
    }
}

function clearUsersTable(){
    var tab = document.getElementById("usersTable");
    tab.innerHTML = "";
    var thead = document.createElement("thead");
    thead.id = "head";
    var tbody = document.createElement("tbody");
    tbody.id = "body";
    tab.appendChild(thead);
    tab.appendChild(tbody);
}

function appendHead(head) {
    var thead = document.getElementById("head");
    var tr = document.createElement("tr");
    for (loop = 0; loop < head.childNodes.length-1; loop++) {
        var td = document.createElement("td");
        td.appendChild(document.createTextNode(head.childNodes[loop].childNodes[0].nodeValue));
        tr.appendChild(td);
    }
    thead.appendChild(tr);
}

function appendBody(body, onclickFunc) {
    var tbody = document.getElementById("body");
    var tr = document.createElement("tr");
    for (loop1 = 0; loop1 < body.childNodes.length-1; loop1++) {
        var td = document.createElement("td");
        td.appendChild(document.createTextNode(body.childNodes[loop1].childNodes[0].nodeValue));
        tr.appendChild(td);
    }
    if (body.childNodes[body.childNodes.length-1].childNodes[0].nodeValue == 0){
        tr.className = "noact";
    }
    tr.onclick = onclickFunc;
    tbody.appendChild(tr);
}

function selectUser(){
    var row = this;
    var id = row.childNodes[0];
    var idUser = id.childNodes[0].nodeValue;
    var url = "userview?id=" + idUser;
    window.open(url,'_blank');
}

function newUser(){
    var url = "newuser";
    window.open(url,'_blank');
}