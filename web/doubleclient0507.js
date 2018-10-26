/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    var searchCl = document.getElementById("searchOsnCl");
    searchCl.onclick = searchClient;
    var searchCl = document.getElementById("searchDoubCl");
    searchCl.onclick = searchClient;
}

function searchClient() {    // поиск клиента
    var s = this;
    if (s.id == "searchOsnCl") {
        var pr = document.getElementById("pr");
        pr.value = "osn";
    } else if (s.id == "searchDoubCl") {
        var pr = document.getElementById("pr");
        pr.value = "doub";
    }
    var dialog = document.getElementById("searchClDialog");
    var katcl = document.getElementById("CBClient").value;
    var str = "";
    var title = document.getElementById("dialogTitle");
    var oldChild = title.childNodes[0];
    if (oldChild != null) {
        title.removeChild(oldChild);
    }
    title.appendChild(document.createTextNode(str));
    dialog.showModal();

    var cancelCl = document.getElementById("cancelClient");
    cancelCl.onclick = cancelClient;

    var hiddenText = document.getElementById("kat");
    hiddenText.value = katcl;

    var f = document.getElementById("dialogForm");
    f.appendChild(hiddenText);

    document.getElementById("fam").value = "";
    document.getElementById("nam").value = "";
    document.getElementById("patr").value = "";
    search();
}

function search() {
    var kat = document.getElementById("kat").value;
    var fam = document.getElementById("fam").value;
    var nam = document.getElementById("nam").value;
    var patr = document.getElementById("patr").value;
    var url = "search?type=client&kat=" + kat + "&fam=" + fam + "&nam=" + nam + "&patr=" + patr;
    requ(url, "GET");
}

function cancelClient() {
    var dialog = document.getElementById("searchClDialog");
    dialog.close();
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
            clearDialog();
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
    var id = row.childNodes[0].childNodes[0].nodeValue;
    var pr = document.getElementById("pr").value;
    var kat = document.getElementById("kat").value
    var clientInfo = "";
    for (loop = 1; loop < row.childNodes.length; loop++) {
        var td = row.childNodes[loop];
        clientInfo += " " + td.childNodes[0].nodeValue;
    }
    var dialog = document.getElementById("searchClDialog");
    dialog.close();
    appendClient(id, kat, pr, clientInfo);
}

function appendClient(id, kat, pr, clientInfo) {
    if (pr == "osn") {
        var div = document.getElementById("osncl");
        var p = document.getElementById("osnclient");
        if (p != null) {
            p.remove();
        }
        var newp = document.createElement("p");
        newp.id = "osnclient";
        var lblCl = document.createElement("label");
        lblCl.className = "clickable";
        lblCl.onclick = clickClient;
        lblCl.appendChild(document.createTextNode(clientInfo));
        newp.appendChild(lblCl);
        var hid = document.createElement("input");
        hid.id = "osnId";
        hid.name = "osnId";
        hid.type = "hidden";
        hid.value = id;
        newp.appendChild(hid);
        div.appendChild(newp);
    } else if (pr == "doub") {
        var div = document.getElementById("doubcl");
        var p = document.getElementById("doubclient");
        if (p != null) {
            p.remove();
        }
        var newp = document.createElement("p");
        newp.id = "doubclient";
        var lblCl = document.createElement("label");
        lblCl.className = "clickable";
        lblCl.onclick = clickClient;
        lblCl.appendChild(document.createTextNode(clientInfo));
        newp.appendChild(lblCl);
        var hid = document.createElement("input");
        hid.id = "doubId";
        hid.name = "doubId";
        hid.type = "hidden";
        hid.value = id;
        newp.appendChild(hid);
        div.appendChild(newp);
    }
}

function clearDialog() {
    var tab = document.getElementById("dialogTable");
    tab.innerHTML = "";
    var thead = document.createElement("thead");
    thead.id = "head";
    var tbody = document.createElement("tbody");
    tbody.id = "body";
    tab.appendChild(thead);
    tab.appendChild(tbody);
}

function clearAll() {
    var posn = document.getElementById("osnclient");
    if (posn != null) {
        posn.remove();
    }
    var pdoub = document.getElementById("doubclient");
    if (pdoub != null) {
        pdoub.remove();
    }
}

function union() {
    var osnId = document.getElementById("osnId");
    var doubId = document.getElementById("doubId");
    var flag = true;
    if (osnId == null) {
        alert("Не выбран основной клиент");
        flag = false;
    } else if (osnId.value == "") {
        alert("Не выбран основной клиент");
        flag = false;
    } else if (osnId.value != "") {
        if (doubId != null) {
            if (osnId.value == doubId.value) {
                alert("Выбран один и тот же клиент, должны быть разные");
                flag = false;
            }
        }
    }
    if (doubId == null) {
        alert("Не выбран клиент для слияния");
        flag = false;
    } else if (doubId.value == "") {
        alert("Не выбран клиент для слияния");
        flag = false;
    }

    if ((osnId != null) && (doubId != null) && (flag == true)) {
        var k = document.getElementById("CBClient");
        if (k != null) {
            var kat = k.value;
        }

        var url = "unionclient?kat=" + kat + "&osnid=" + osnId.value + "&doubid=" + doubId.value;
        document.location.href = url;
    }
}

function clickClient(){
    var p = this.parentNode;
    var kat = document.getElementById("CBClient").value;
    if (p.id == "osnclient"){
        var id = document.getElementById("osnId").value;
    } else if (p.id == "doubclient"){
        var id = document.getElementById("doubId").value;
    }
    if ((kat != null)&&(id != null)){
        window.open("client?kat=" + kat + "&id=" + id, "_blank");
    }
}