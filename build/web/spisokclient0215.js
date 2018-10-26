/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    search();
}

function search() {
    var kat = document.getElementById("kat").value;
    var fam = document.getElementById("fam").value;
    var nam = document.getElementById("nam").value;
    var patr = document.getElementById("patr").value;
    var url = "search?type=client&kat=" + kat + "&fam=" + fam + "&nam=" + nam + "&patr=" + patr;
    clearClientTable();
    requ(url, "GET");
}

function searchnom() {
    var kat = document.getElementById("kat").value;
    var nom = document.getElementById("nom").value;
    var fam = document.getElementById("fam");
    fam.value = "";
    var nam = document.getElementById("nam");
    nam.value = "";
    var patr = document.getElementById("patr");
    patr.value = "";
    var url = "search?type=clentbynom&kat=" + kat + "&nom=" + nom;
    clearClientTable();
    requ(url, "GET");
}

function requ(url, method) { // формирование запроса
    var requ = null;
    requ = initRequest();
    requ.open(method, url, true);
    requ.onreadystatechange = callback;
    requ.send(null);
}

function initRequest() {
    return new XMLHttpRequest();
}

function callback() {
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
    } else {
        // клиенты
        var clients = responseXML.getElementsByTagName("clients")[0];
        if (clients != null) {
            clearClientTable();
            if (clients.childNodes.length > 0) {
                var head = clients.childNodes[0];
                appendHead(head);
                for (loop = 1; loop < clients.childNodes.length; loop++) {
                    var client = clients.childNodes[loop];
                    appendBody(client, selectClient);
                }
            }
            clients = null;
        }
    }
}

function clearClientTable() {
    var tab = document.getElementById("clientTable");
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
    for (loop = 0; loop < head.childNodes.length; loop++) {
        var td = document.createElement("td");
        td.appendChild(document.createTextNode(head.childNodes[loop].childNodes[0].nodeValue));
        tr.appendChild(td);
    }
    thead.appendChild(tr);
}

function appendBody(body, onclickFunc) {
    var tbody = document.getElementById("body");
    var tr = document.createElement("tr");
    for (loop1 = 0; loop1 < body.childNodes.length; loop1++) {
        var td = document.createElement("td");
        td.appendChild(document.createTextNode(body.childNodes[loop1].childNodes[0].nodeValue));
        tr.appendChild(td);
    }
    tr.onclick = onclickFunc;
    tbody.appendChild(tr);
}

function selectClient() {
    var row = this;
    var id = row.childNodes[0];
    var idCl = id.childNodes[0].nodeValue;
    var url = "client?kat=" + document.getElementById("kat").value + "&id=" + idCl;
    window.open(url, '_blank');
}

function appendKol(n) {
    var kol = document.getElementById("kol");
    if (kol != null) {
        var chKol = kol.childNodes[0];
        if (chKol != null) {
            kol.removeChild(chKol);
        }
        kol.appendChild(document.createTextNode("Количество: " + n));
    }
}
