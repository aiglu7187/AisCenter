/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initadd() {
    var url = "kalendar?action=lastyear";
    requ(url, "GET");
}

function initview() {
    var url = "kalendar?action=allyears";
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
        var lastyear = responseXML.getElementsByTagName("lastyear")[0];
        var years = responseXML.getElementsByTagName("years")[0];
        if (lastyear != null) {
            appendYear(lastyear.childNodes[0].nodeValue);
        }
        if (years != null) {
            if (years.childNodes.length > 0) {
                for (loop = 0; loop < years.childNodes.length; loop++) {
                    var year = years.childNodes[loop];
                    appendYearInSel(year.childNodes[0].nodeValue);
                }
            }
        }
    }
}

function appendYear(year) {
    var div = document.getElementById("divYear");
    if (div != null) {
        div.appendChild(document.createTextNode("Укажите год"));
        div.appendChild(document.createElement("br"));
        var inpYear = document.createElement("input");
        inpYear.style = "width: 50px";
        inpYear.type = "text";
        inpYear.id = "year";
        inpYear.name = "year";
        inpYear.value = Number(year) + 1;
        div.appendChild(inpYear);
        div.appendChild(document.createElement("br"));
        var btn = document.createElement("input");
        btn.type = "button";
        btn.id = "viewBtn";
        btn.name = "viewBtn";
        btn.value = "Показать календарь";
        btn.onclick = viewKalendar;
        div.appendChild(btn);
    }
}

function viewKalendar() {
    var inpYear = document.getElementById("year");
    if (inpYear != null) {
        var itsOk = true;
        var year = 0;
        var yearVal = inpYear.value;
        if (yearVal.length != 4) {
            alert("Проверьте формат года");
            itsOk = false;
        }
        if (itsOk) {
            year = Number(yearVal);
            if (isNaN(year)) {
                alert("Проверьте формат года");
                itsOk = false;
            }
        }
        if (itsOk) {
            // показываем базовый календарь
            var url = "kalendar?action=basecalendar&year=" + year;
            window.open(url);
        }
    }
}

function dateclick() {
    var cl = this.className;
    if (cl == "weekend") {
        this.className = "regularday";
    } else if (cl == "regularday") {
        this.className = "weekend";
    }

}

function initbase() {
    var weekendTd = document.getElementsByClassName("weekend");
    var regulardayTd = document.getElementsByClassName("regularday");
    for (loop = 0; loop < weekendTd.length; loop++) {
        weekendTd[loop].onclick = dateclick;
    }
    for (loop = 0; loop < regulardayTd.length; loop++) {
        regulardayTd[loop].onclick = dateclick;
    }
}

function saveKalendar() {
    var weekend = document.getElementsByClassName("weekend");
    var mass = new Array();
    for (loop = 0; loop < weekend.length; loop++) {
        mass.push(weekend[loop].id);
    }
    var url = "kalendar?action=savenewkalendar&weekend=" + mass;
    window.open(url);
}

function appendYearInSel(year) {
    var sel = document.getElementById("kalend");
    if (sel != null) {
        var opt = document.createElement("option");
        opt.value = year;
        opt.appendChild(document.createTextNode(year));
        sel.appendChild(opt);
    }
}

function viewKalendarFromBase() {
    var sel = document.getElementById("kalend");
    var year;
    if (sel != null) {
        year = sel.value;
    }
    var url = "kalendar?action=calendar&year=" + year;
    window.open(url);
}