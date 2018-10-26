/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init(){
    search();
}

function search(){
    var dolgn = document.getElementById("dolgn").value;    
    var fam = document.getElementById("fam").value;
    var url = "search?type=sotrud&dolgn=" + dolgn + "&fam=" + fam;
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
        var sotruds = responseXML.getElementsByTagName("sotruds")[0];
        if (sotruds != null) {
            clearSotrudTable();
            if (sotruds.childNodes.length > 0) {
                var head = sotruds.childNodes[0];
                appendHead(head);
                for (loop = 1; loop < sotruds.childNodes.length; loop++) {
                    var client = sotruds.childNodes[loop];
                    appendBody(client, selectSotrud);
                }
            }
            sotruds = null;
        }
    }
}

function clearSotrudTable(){
    var tab = document.getElementById("sotrudTable");
    tab.innerHTML = "";
    var thead = document.createElement("thead");
    thead.id = "head";
    var tbody = document.createElement("tbody");
    tbody.id = "body";
    tab.appendChild(thead);
    tab.appendChild(tbody);
}

function selectSotrud(){
    var row = this;
    var id = row.childNodes[0];
    var idSotrud = id.childNodes[0].nodeValue;
    var url = "sotrudview?id=" + idSotrud;
    window.open(url,'_blank');
}

function newSotrud(){
    var url = "newsotrud";
    window.open(url,'_blank');
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