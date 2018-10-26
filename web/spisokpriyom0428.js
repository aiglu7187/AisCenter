/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    sotrchange();
}

function searchPriyom() {
    var btn = document.getElementById("searchBtn");
    btn.disabled = "disabled";
    var uslId = document.getElementById("uslSel").value;
    var regId = document.getElementById("regSel").value;
    var datePr1 = document.getElementById("datepr1").value;
    var datePr2 = document.getElementById("datepr2").value;
    var sotrId = document.getElementById("sotrdolgnId");
    if (sotrId != null) {
        var url = "spisokpriyomaction?usl=" + uslId + "&reg=" + regId + "&datepr1=" + datePr1 + "&datepr2=" + datePr2 + "&sotr=" + sotrId.value;
        requ(url, "GET");
    } else {
        var url = "spisokpriyomaction?usl=" + uslId + "&reg=" + regId + "&datepr1=" + datePr1 + "&datepr2=" + datePr2;
        requ(url, "GET");
    }
}

function sotrchange() {
    clearPriyom();
    var sId = document.getElementById("sotrud");
    if (sId != null) {
        var sotrId = document.getElementById("sotrdolgnId");
        if (sotrId != null) {
            sotrId.value = sId.value;
        }
        if (sotrId.value != "0") {
            appendOtchet();
        } else {
            clearPriyom();
            clearOtchet();
        }
    }
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
            var btn = document.getElementById("searchBtn");
            btn.disabled = false;
        }
    }
}

function parseMessages(responseXML) {   // преобразование полученного xml
    if (responseXML == null) {
        return false;
    } else {
        var priyoms = responseXML.getElementsByTagName("priyoms")[0];

        if (priyoms != null) {  // если список сотрудников
            clearPriyom();
            appendKol(0);
            if (priyoms.childNodes.length > 0) {
                for (loop = 0; loop < priyoms.childNodes.length; loop++) {
                    var priyom = priyoms.childNodes[loop];
                    var id = priyom.getElementsByTagName("id")[0];
                    var date = priyom.getElementsByTagName("date")[0];
                    var usl = priyom.getElementsByTagName("usl")[0];
                    var reg = priyom.getElementsByTagName("reg")[0];
                    var sotr = priyom.getElementsByTagName("sotr")[0];
                    appendPriyom(id.childNodes[0].nodeValue, date.childNodes[0].nodeValue,
                            usl.childNodes[0].nodeValue, reg.childNodes[0].nodeValue, sotr.childNodes[0].nodeValue);
                }
                appendKol(priyoms.childNodes.length);
            }
        }
    }
}

function clearPriyom() {
    var tab = document.getElementById("priyomTable");
    for (loop = tab.childNodes.length - 1; loop >= 0; loop--) {
        tab.removeChild(tab.childNodes[loop]);
    }
    var tr = document.createElement("thead");
    var tdId = document.createElement("td");
    tdId.appendChild(document.createTextNode("id"));
    tr.appendChild(tdId);
    var tdDate = document.createElement("td");
    tdDate.appendChild(document.createTextNode("Дата проведения"));
    tr.appendChild(tdDate);
    var tdUsl = document.createElement("td");
    tdUsl.appendChild(document.createTextNode("Услуга"));
    tr.appendChild(tdUsl);
    var tdReg = document.createElement("td");
    tdReg.appendChild(document.createTextNode("Место проведения"));
    tr.appendChild(tdReg);
    var tdSotr = document.createElement("td");
    tdSotr.appendChild(document.createTextNode("Специалисты"));
    tr.appendChild(tdSotr);
    tab.appendChild(tr);
    var tbody = document.createElement("tbody");
    tbody.id = "bodyPriyom";
    tab.appendChild(tbody);
    appendKol(0);
}

function appendPriyom(id, date, usl, reg, sotr) {
    var tab = document.getElementById("bodyPriyom");
    var tr = document.createElement("tr");
    var tdId = document.createElement("td");
    tdId.appendChild(document.createTextNode(id));
    tr.appendChild(tdId);
    var tdDate = document.createElement("td");
    tdDate.appendChild(document.createTextNode(date));
    tr.appendChild(tdDate);
    var tdUsl = document.createElement("td");
    tdUsl.appendChild(document.createTextNode(usl));
    tr.appendChild(tdUsl);
    var tdReg = document.createElement("td");
    tdReg.appendChild(document.createTextNode(reg));
    tr.appendChild(tdReg);
    var tdSotr = document.createElement("td");
    tdSotr.appendChild(document.createTextNode(sotr));    
    tr.appendChild(tdSotr);
    tr.onclick = selectPriyom;
    tab.appendChild(tr);
}

function selectPriyom() {
    var row = this;
    var id = row.childNodes[0];
    var id = id.childNodes[0].nodeValue;
    var url = "priyomedit?&id=" + id;
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

function appendOtchet() {
    clearOtchet();
    var btn = document.createElement("input");
    btn.type = "button";
    btn.name = "otchetBtn";
    btn.id = "otchetBtn";
    btn.value = "Отчет по сотруднику";
    btn.onclick = otchet;

    var btnBig = document.createElement("input");
    btnBig.type = "button";
    btnBig.name = "bigOtchetBtn";
    btnBig.id = "bigOtchetBtn";
    btnBig.value = "Расширенный отчет по сотруднику";
    btnBig.onclick = bigOtchet;

    var p = document.getElementById("sotrP");
    if (p != null) {
        p.appendChild(btn);
        p.appendChild(btnBig);
    }
}

function clearOtchet() {
    var btn = document.getElementById("otchetBtn");
    if (btn != null) {
        btn.remove();
    }
    var btnBig = document.getElementById("bigOtchetBtn");
    if (btnBig != null) {
        btnBig.remove();
    }
}

function otchet() {
    var dialog = document.getElementById("otchetDialog");
    dialog.showModal();
    var type = document.getElementById("otchetType");
    type.value = "otchet";
    var date1 = document.getElementById("date1");
    date1.value = "";
    var date2 = document.getElementById("date2");
    date2.value = "";
}

function print() {
    var sotrud = document.getElementById("sotrud");
    var sotrId = "";
    if (sotrud != null) {
        sotrId = sotrud.value;
    }
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }

    var result = true;
    if (d1 == "") {
        alert("Поставьте дату начала периода");
        result = false;
    }
    ;
    if (result != false) {
        if (d2 == "") {
            alert("Поставьте дату конца периода");
            result = false;
        }
    }
    ;
    if (result != false) {
        if (d1.length > 10) {
            alert("Проверьте дату начала периода");
            result = false;
        }
    }
    ;
    if (result != false) {
        if (d2.length > 10) {
            alert("Проверьте дату конца периода");
            result = false;
        }
    }
    ;

    var type = document.getElementById("otchetType").value;

    if (result == true) {
        var url;
        if (type == "otchet") {
            url = "print?type=sotrud&id=" + sotrId + "&date1=" + d1 + "&date2=" + d2;
        } else if (type == "big") {
            url = "print?type=sotrudBig&id=" + sotrId + "&date1=" + d1 + "&date2=" + d2;
        }
        window.open(url, '_blank');
        var dialog = document.getElementById("otchetDialog");
        dialog.close();
    }
}

function bigOtchet() {
    var dialog = document.getElementById("otchetDialog");
    dialog.showModal();
    var type = document.getElementById("otchetType");
    type.value = "big";
    var date1 = document.getElementById("date1");
    date1.value = "";
    var date2 = document.getElementById("date2");
    date2.value = "";
}