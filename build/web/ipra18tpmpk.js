/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    var inps = document.getElementsByTagName("input");
    for (loop = 0; loop < inps.length; loop++) {
        if (inps[loop].type == "checkbox") {
            if (inps[loop].id != "checkAll") {
                inps[loop].onclick = check;
            }
        }
    }
}

function checkAll() {
    var ch = document.getElementById("checkAll");
    var inps = document.getElementsByTagName("input");
    if (ch != null) {
        for (loop = 0; loop < inps.length; loop++) {
            if (inps[loop].type == "checkbox") {
                if (ch.checked) {
                    inps[loop].checked = "checked";
                    if (inps[loop].id != "checkAll") {
                        inps[loop].onclick();
                    }
                } else {
                    inps[loop].checked = 0;
                    if (inps[loop].id != "checkAll") {
                        inps[loop].onclick();
                    }
                }
            }
        }

    }
}

function check() {
    if (this.checked) {
        this.parentNode.parentNode.className = "checked";
    } else {
        this.parentNode.parentNode.classList.remove("checked");
    }
    var checkedTr = document.getElementsByClassName("checked");
    if (checkedTr.length > 0) {
        var btn = document.getElementById("formreqbtn");
        btn.disabled = 0;
    } else {
        var btn = document.getElementById("formreqbtn");
        btn.disabled = "disabled";
    }
}

function formreq() {
    var dialog = document.getElementById("formreqDialog");
    dialog.showModal();
    var checkedTr = document.getElementsByClassName("checked");
    var url = "ipra2018spisok?action=formreqtotpmpk&id="
    for (loop = 0; loop < checkedTr.length; loop++) {
        var tds = checkedTr[loop].getElementsByTagName("td");
        url += tds[0].childNodes[0].nodeValue.trim() + ";";
    }
    requ(url, "GET", null);
}

function requ(url, method, body) { // формирование запроса
    var requ = null;
    requ = initRequest();
    requ.open(method, url, true);
    requ.onreadystatechange = callback;
    if (body != null) {
        requ.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded')
    }
    requ.send(body);
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
        } else if (request.status == 500) {
            alert("Ошибка сервера " + request.statusText + "\nДанные не были сохранены. Обратитесь к администратору");
            var btn = document.getElementById("saveBtn");
            btn.disabled = 0;
        } else if (request.status == 204) {
        } else {
            alert("Ошибка подключения к серверу\nДанные не были сохранены");
            var btn = document.getElementById("saveBtn");
            btn.disabled = 0;
        }
    }
}

function parseMessages(responseXML) {   // преобразование полученного xml
    if (responseXML == null) {
        return false;
    } else {
        var result = responseXML.getElementsByTagName("result")[0];
        var tpmpkreqs = responseXML.getElementsByTagName("tpmpkreqs")[0];
        if (result != null) {
            appendResult(result.childNodes[0].nodeValue);
        }
        if (tpmpkreqs != null) {  // если список районов
            clearDialog();
            if (tpmpkreqs.childNodes.length > 0) {
                for (loop = 0; loop < tpmpkreqs.childNodes.length; loop++) {
                    var tpmpkreq = tpmpkreqs.childNodes[loop];
                    var n = tpmpkreq.getElementsByTagName("n")[0];
                    var ipraids = tpmpkreq.getElementsByTagName("ipraids")[0];
                    var tpmpkname = tpmpkreq.getElementsByTagName("tpmpkname")[0];
                    var reqdate = tpmpkreq.getElementsByTagName("reqdate")[0];
                    var childrencount = tpmpkreq.getElementsByTagName("childrencount")[0];
                    appendTpmpkReq(n.childNodes[0].nodeValue, ipraids.childNodes[0].nodeValue, tpmpkname.childNodes[0].nodeValue,
                            reqdate.childNodes[0].nodeValue, childrencount.childNodes[0].nodeValue);
                }
            }
            appendDialogBtn();
        }

    }
}

function clearDialog() {
    var dialog = document.getElementById("formreqDialog");
    dialog.innerHTML = "";
    dialog.appendChild(document.createTextNode("Запросы в ТПМПК:"));
    dialog.appendChild(document.createElement("br"));
}

function appendTpmpkReq(n, ids, tpmpkname, reqdate, childrencount) {
    var dialog = document.getElementById("formreqDialog");
    var div = document.createElement("div");
    var ipraids = document.createElement("input");
    ipraids.type = "hidden";
    ipraids.id = "ipraids" + n;
    ipraids.name = "ipraids" + n;
    ipraids.value = ids;
    div.appendChild(ipraids);    
    var string = "";
    if ((childrencount == 1) || (childrencount >= 5)) {
        string = "человек";
    } else if ((childrencount > 1) && (childrencount < 5)) {
        string = "человека";
    }
    div.appendChild(document.createTextNode(reqdate + " " + tpmpkname + " (" + childrencount + " " + string + ")  "));
    var printBtn = document.createElement("input");
    printBtn.type = "button";
    printBtn.id = "printBtn" + n;
    printBtn.value = "Выгрузить запрос";
    printBtn.onclick = printTpmpkReq;
    div.appendChild(printBtn);
    dialog.appendChild(div);
}

function appendDialogBtn(){
    var dialog = document.getElementById("formreqDialog");
    var div = document.createElement("div");
    div.id = "divDialogBtn";
    var cancelBtn = document.createElement("input");
    cancelBtn.type = "button";
    cancelBtn.id = "cancelBtn";
    cancelBtn.value = "Отмена";
    cancelBtn.onclick = cancelDialog;
    div.appendChild(cancelBtn);
    dialog.appendChild(div);
}

function cancelDialog(){
    var dialog = document.getElementById("formreqDialog");
    dialog.close();
    window.location = "ipra2018spisok?action=tpmpkreqlist";
}

function printTpmpkReq(){
    var n = this.id.substring(8);
    var ipraids = document.getElementById("ipraids" + n);
    if (ipraids != null) {
        var url = "print?type=tpmpkreq&ipra=" + ipraids.value;
        window.open(url, '_blank');
    }
}

