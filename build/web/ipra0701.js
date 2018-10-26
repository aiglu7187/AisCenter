/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initDates() {
    var prikazDoD = document.getElementById("prikazDoD");
    if (prikazDoD != null) {
        prikazDoD.onclick = prikazDate;
    }
    var omsuD = document.getElementById("omsuD");
    if (omsuD != null) {
        omsuD.onclick = omsuDate;
    }
    var prOmsuD = document.getElementById("prOmsuD");
    if (prOmsuD != null) {
        prOmsuD.onclick = prOmsuDate;
    }

    var oznakD = document.getElementById("oznakD");
    if (oznakD != null) {
        oznakD.onclick = oznakDate;
    }

    var tpmpkD = document.getElementById("tpmpkD");
    if (tpmpkD != null) {
        tpmpkD.onclick = tpmpkDate
    }
    var otchOmsu = document.getElementById("otchOmsu");
    if (otchOmsu != null) {
        otchOmsu.onclick = otchOmsuDate;
    }

    var otchCenter = document.getElementById("otchCenter");
    if (otchCenter != null) {
        otchCenter.onclick = otchCenterDate;
    }

    var otchDo = document.getElementById("otchDo");
    if (otchDo != null) {
        otchDo.onclick = otchDoDate;
    }

    var status = document.getElementById("selStatus");
    if (status != null) {
        status.onchange = chStatus;
    }
    
    var selMse = document.getElementById("selMse");
    if (selMse != null){
        selMse.onchange();
    }    
}

function callSearchDialog() {
    var dialog = document.getElementById("searchClDialog");
    if (dialog != null) {
        dialog.showModal();
        searchChild();
    }

}

function cancelDialog() {
    var dialog = document.getElementById("searchClDialog");
    if (dialog != null) {
        dialog.close();
        var fam = document.getElementById("fam");
        var nam = document.getElementById("nam");
        var patr = document.getElementById("patr");
        if (fam != null) {
            fam.value = "";
        }
        if (nam != null) {
            nam.value = "";
        }
        if (patr != null) {
            patr.value = "";
        }
    }
}

function newChild() {
    var dialog = document.getElementById("searchClDialog");
    var f, n, p;
    if (dialog != null) {
        f = document.getElementById("fam").value;
        n = document.getElementById("nam").value;
        p = document.getElementById("patr").value;
        dialog.close();
    }

    var divInfoChild = document.getElementById("divInfoChild");
    if (divInfoChild != null) {
        var divChild = document.getElementById("divChild");
        if (divChild != null) {
            divChild.innerHTML = "";
        } else {
            divChild = document.createElement("div");
            divChild.id = "divChild";
            divInfoChild.appendChild(divChild);
        }
        var lblFam = document.createElement("label");
        lblFam.appendChild(document.createTextNode("Фамилия: "));
        var inpFam = document.createElement("input");
        inpFam.type = "text";
        inpFam.id = "childFam";
        inpFam.name = "childFam";
        inpFam.value = f;
        divChild.appendChild(lblFam);
        divChild.appendChild(inpFam);

        var lblName = document.createElement("label");
        lblName.appendChild(document.createTextNode("Имя: "));
        var inpName = document.createElement("input");
        inpName.type = "text";
        inpName.id = "childName";
        inpName.name = "childName";
        inpName.value = n;
        divChild.appendChild(lblName);
        divChild.appendChild(inpName);

        var lblPatr = document.createElement("label");
        lblPatr.appendChild(document.createTextNode("Отчество: "));
        var inpPatr = document.createElement("input");
        inpPatr.type = "text";
        inpPatr.id = "childPatr";
        inpPatr.name = "childPatr";
        inpPatr.value = p;
        divChild.appendChild(lblPatr);
        divChild.appendChild(inpPatr);
        divChild.appendChild(document.createElement("br"));

        var lblDr = document.createElement("label");
        lblDr.appendChild(document.createTextNode("Дата рождения: "));
        var inpDr = document.createElement("input");
        inpDr.type = "date";
        inpDr.id = "childDr";
        inpDr.name = "childDr";
        divChild.appendChild(lblDr);
        divChild.appendChild(inpDr);
        divChild.appendChild(document.createElement("br"));

        var regId = document.createElement("input");
        regId.type = "hidden";
        regId.name = "regId";
        regId.id = "regId";
        var lblReg = document.createElement("label");
        lblReg.appendChild(document.createTextNode("Район проживания: "));
        var selReg = document.createElement("select");
        selReg.id = "childReg";
        selReg.onchange = regionSelect;
        divChild.appendChild(regId);
        divChild.appendChild(lblReg);
        divChild.appendChild(selReg);
        divChild.appendChild(document.createElement("br"));
        divChild.appendChild(document.createElement("br"));
        var url = "ipraspisok?action=regions";
        requ(url, "GET");

        var divIpra = document.getElementById("divIpra");
        if (divIpra == null) {
            appendIpra(divInfoChild);
        }

    }
}

function regionSelect() {
    var regId = document.getElementById("regId");
    var selReg = document.getElementById("childReg");
    if ((regId != null) && (selReg != null)) {
        regId.value = selReg.value;
    }
}

function searchChild() {
    var fam = document.getElementById("fam").value;
    var nam = document.getElementById("nam").value;
    var patr = document.getElementById("patr").value;
    var url = "search?type=client&kat=children&fam=" + fam + "&nam=" + nam + "&patr=" + patr;
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
        var clients = responseXML.getElementsByTagName("clients")[0];
        var ipradate = responseXML.getElementsByTagName("ipradate")[0];
        var regions = responseXML.getElementsByTagName("regions")[0];
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
        if (ipradate != null) {
            var id = ipradate.getElementsByTagName("id")[0];
            var date = ipradate.getElementsByTagName("date")[0];
            appendDate(id.childNodes[0].nodeValue, date.childNodes[0].nodeValue);
        }
        if (regions != null) {  // если список районов
            clearReg();
            if (regions.childNodes.length > 0) {
                for (loop = 0; loop < regions.childNodes.length; loop++) {
                    var region = regions.childNodes[loop];
                    var rName = region.getElementsByTagName("name")[0];
                    var rId = region.getElementsByTagName("id")[0];
                    appendRegion(rId.childNodes[0].nodeValue, rName.childNodes[0].nodeValue);
                }
            }
        }
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
    var dialog = document.getElementById("searchClDialog");
    if (dialog != null) {
        dialog.close();
    }

    var divInfoChild = document.getElementById("divInfoChild");
    if (divInfoChild != null) {
        var divChild = document.getElementById("divChild");
        if (divChild != null) {
            divChild.innerHTML = "";
        } else {
            divChild = document.createElement("div");
            divChild.id = "divChild";
            divInfoChild.appendChild(divChild);
        }
        var inpId = document.createElement("input");
        inpId.type = "hidden";
        inpId.id = "childId";
        inpId.name = "childId";
        inpId.value = this.childNodes[0].childNodes[0].nodeValue;
        divChild.appendChild(inpId);
        var lbl = document.createElement("label");
        lbl.appendChild(document.createTextNode(this.childNodes[1].childNodes[0].nodeValue));
        divChild.appendChild(lbl);
        divChild.appendChild(document.createElement("br"));
        var lblCl = document.createElement("label");
        lblCl.className = "clickable";
        lblCl.onclick = clickChild;
        lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue + " " +
                this.childNodes[5].childNodes[0].nodeValue));
        divChild.appendChild(lblCl);
        divChild.appendChild(document.createElement("br"));
        divChild.appendChild(document.createTextNode(this.childNodes[6].childNodes[0].nodeValue));
        divChild.appendChild(document.createElement("br"));
        divChild.appendChild(document.createElement("br"));

        var divIpra = document.getElementById("divIpra");
        if (divIpra == null) {
            appendIpra(divInfoChild);
        }
    }
}

function appendIpra(divInfoChild) {
    var divIpra = document.createElement("div");
    divIpra.id = "divIpra";

    divIpra.appendChild(document.createElement("br"));
    var lblIpra = document.createElement("strong");
    lblIpra.appendChild(document.createTextNode("ИПРА "));
    divIpra.appendChild(lblIpra);
    divIpra.appendChild(document.createElement("br"));

    var lblIpraN = document.createElement("label");
    lblIpraN.appendChild(document.createTextNode("Номер ИПРА: "));
    var inpIpraN = document.createElement("input");
    inpIpraN.type = "text";
    inpIpraN.id = "ipraN";
    inpIpraN.name = "ipraN";
    var lblExpDate = document.createElement("label");
    lblExpDate.appendChild(document.createTextNode("Дата экспертизы: "));
    var inpExpDate = document.createElement("input");
    inpExpDate.type = "date";
    inpExpDate.id = "expDate";
    inpExpDate.name = "expDate";
    var lblIpraDateOk = document.createElement("label");
    lblIpraDateOk.appendChild(document.createTextNode("Дата окончания ИПРА: "));
    var inpIpraDateOk = document.createElement("input");
    inpIpraDateOk.type = "date";
    inpIpraDateOk.id = "ipraDateOk";
    inpIpraDateOk.name = "ipraDateOk";
    divIpra.appendChild(lblIpraN);
    divIpra.appendChild(inpIpraN);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(lblExpDate);
    divIpra.appendChild(inpExpDate);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(lblIpraDateOk);
    divIpra.appendChild(inpIpraDateOk);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(document.createElement("br"));

    var lblIshMse = document.createElement("strong");
    lblIshMse.appendChild(document.createTextNode("Исходящее письмо из МСЭ"));
    divIpra.appendChild(lblIshMse);
    divIpra.appendChild(document.createElement("br"));
    var lblIshMseN = document.createElement("label");
    lblIshMseN.appendChild(document.createTextNode("номер: "));
    var inpIshMseN = document.createElement("input");
    inpIshMseN.type = "text";
    inpIshMseN.id = "ishMseN";
    inpIshMseN.name = "ishMseN";
    var lblIshMseD = document.createElement("label");
    lblIshMseD.appendChild(document.createTextNode(" дата: "));
    var inpIshMseD = document.createElement("input");
    inpIshMseD.type = "date";
    inpIshMseD.id = "ishMseD";
    inpIshMseD.name = "ishMseD";
    divIpra.appendChild(lblIshMseN);
    divIpra.appendChild(inpIshMseN);
    divIpra.appendChild(lblIshMseD);
    divIpra.appendChild(inpIshMseD);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(document.createElement("br"));

    var lblVhDo = document.createElement("strong");
    lblVhDo.appendChild(document.createTextNode("Входящее письмо в ДО"));
    divIpra.appendChild(lblVhDo);
    divIpra.appendChild(document.createElement("br"));
    var lblVhDoN = document.createElement("label");
    lblVhDoN.appendChild(document.createTextNode("номер: "));
    var inpVhDoN = document.createElement("input");
    inpVhDoN.type = "text";
    inpVhDoN.id = "vhDoN";
    inpVhDoN.name = "vhDoN";
    var lblVhDoD = document.createElement("label");
    lblVhDoD.appendChild(document.createTextNode(" дата: "));
    var inpVhDoD = document.createElement("input");
    inpVhDoD.type = "date";
    inpVhDoD.id = "vhDoD";
    inpVhDoD.name = "vhDoD";
    divIpra.appendChild(lblVhDoN);
    divIpra.appendChild(inpVhDoN);
    divIpra.appendChild(lblVhDoD);
    divIpra.appendChild(inpVhDoD);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(document.createElement("br"));

    var lblPrikazDo = document.createElement("strong");
    lblPrikazDo.appendChild(document.createTextNode("Приказ ДО"));
    divIpra.appendChild(lblPrikazDo);
    divIpra.appendChild(document.createElement("br"));
    var lblPrikazDoN = document.createElement("label");
    lblPrikazDoN.appendChild(document.createTextNode("номер: "));
    var inpPrikazDoN = document.createElement("input");
    inpPrikazDoN.type = "text";
    inpPrikazDoN.id = "prikazDoN";
    inpPrikazDoN.name = "prikazDoN";
    var lblPrikazDoD = document.createElement("label");
    lblPrikazDoD.appendChild(document.createTextNode(" дата: "));
    var inpPrikazDoD = document.createElement("input");
    inpPrikazDoD.type = "date";
    inpPrikazDoD.id = "prikazDoD";
    inpPrikazDoD.name = "prikazDoD";
    inpPrikazDoD.onclick = prikazDate;
    divIpra.appendChild(lblPrikazDoN);
    divIpra.appendChild(inpPrikazDoN);
    divIpra.appendChild(lblPrikazDoD);
    divIpra.appendChild(inpPrikazDoD);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(document.createElement("br"));

    var lblOmsuD = document.createElement("label");
    lblOmsuD.appendChild(document.createTextNode("Дата письма в ОМСУ (п.1.1): "));
    var inpOmsuD = document.createElement("input");
    inpOmsuD.type = "date";
    inpOmsuD.id = "omsuD";
    inpOmsuD.name = "omsuD";
    inpOmsuD.onclick = omsuDate;
    divIpra.appendChild(lblOmsuD);
    divIpra.appendChild(inpOmsuD);

    var lblOmsuN = document.createElement("label");
    lblOmsuN.appendChild(document.createTextNode("Номер письма в ОМСУ: "));
    var inpOmsuN = document.createElement("input");
    inpOmsuN.type = "text";
    inpOmsuN.id = "omsuN";
    inpOmsuN.name = "omsuN";
    divIpra.appendChild(lblOmsuN);
    divIpra.appendChild(inpOmsuN);

    divIpra.appendChild(document.createElement("br"));
    var lblPrOmsuD = document.createElement("label");
    lblPrOmsuD.appendChild(document.createTextNode("Дата приказа ОМСУ (п.2.1): "));
    var inpPrOmsuD = document.createElement("input");
    inpPrOmsuD.type = "date";
    inpPrOmsuD.id = "prOmsuD";
    inpPrOmsuD.name = "prOmsuD";
    inpPrOmsuD.onclick = prOmsuDate;
    divIpra.appendChild(lblPrOmsuD);
    divIpra.appendChild(inpPrOmsuD);
    divIpra.appendChild(document.createElement("br"));
    var lblOznak = document.createElement("label");
    lblOznak.appendChild(document.createTextNode("Дата ознакомления родителей (законных представителей) (п.2.2): "));
    var inpOznak = document.createElement("input");
    inpOznak.type = "date";
    inpOznak.id = "oznakD";
    inpOznak.name = "oznakD";
    inpOznak.onclick = oznakDate;
    divIpra.appendChild(lblOznak);
    divIpra.appendChild(inpOznak);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(document.createElement("br"));

    var lblTpmpkD = document.createElement("label");
    lblTpmpkD.appendChild(document.createTextNode("Дата запроса в ТПМПК: "));
    var inpTpmpkD = document.createElement("input");
    inpTpmpkD.type = "date";
    inpTpmpkD.id = "tpmpkD";
    inpTpmpkD.name = "tpmpkD";
    inpTpmpkD.onclick = tpmpkDate;
    divIpra.appendChild(lblTpmpkD);
    divIpra.appendChild(inpTpmpkD);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(document.createElement("br"));

    var lblOtch = document.createElement("strong");
    lblOtch.appendChild(document.createTextNode("Даты отчетов"));
    divIpra.appendChild(lblOtch);
    divIpra.appendChild(document.createElement("br"));
    var lblOtchOmsu = document.createElement("label");
    lblOtchOmsu.appendChild(document.createTextNode("ОМСУ (п.2.3): "));
    var inpOtchOmsu = document.createElement("input");
    inpOtchOmsu.type = "date";
    inpOtchOmsu.id = "otchOmsu";
    inpOtchOmsu.name = "otchOmsu";
    inpOtchOmsu.onclick = otchOmsuDate;
    var lblOtchCenter = document.createElement("label");
    lblOtchCenter.appendChild(document.createTextNode("ОЦППМСП (п.1.2): "));
    var inpOtchCenter = document.createElement("input");
    inpOtchCenter.type = "date";
    inpOtchCenter.id = "otchCenter";
    inpOtchCenter.name = "otchCenter";
    inpOtchCenter.onclick = otchCenterDate;
    var lblOtchDo = document.createElement("label");
    lblOtchDo.appendChild(document.createTextNode("ДО: "));
    var inpOtchDo = document.createElement("input");
    inpOtchDo.type = "date";
    inpOtchDo.id = "otchDo";
    inpOtchDo.name = "otchDo";
    inpOtchDo.onclick = otchDoDate;
    divIpra.appendChild(lblOtchOmsu);
    divIpra.appendChild(inpOtchOmsu);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(lblOtchCenter);
    divIpra.appendChild(inpOtchCenter);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(lblOtchDo);
    divIpra.appendChild(inpOtchDo);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(document.createElement("br"));
    divInfoChild.appendChild(divIpra);

    var saveBtn = document.createElement("input");
    saveBtn.type = "button";
    saveBtn.id = "saveBtn";
    saveBtn.value = "Сохранить";
    saveBtn.onclick = validateIpra;
    divInfoChild.appendChild(saveBtn);
}

function prikazDate() {
    var period = 3;
    var type = "rab";
    var direct = "forward";
    var inp = document.getElementById("vhDoD");
    var date = inp.value;
    if (date != "") {
        var url = "ipraspisok?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET");
    } else {
        alert("Заполните дату входящего письма в ДО");
    }

}

function omsuDate() {
    var period = 15;
    var type = "rab";
    var direct = "forward";
    var inp = document.getElementById("prikazDoD");
    var date = inp.value;
    if (date != "") {
        var url = "ipraspisok?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET");
    } else {
        alert("Заполните дату приказа ДО");
    }
}

function tpmpkDate() {
    var period = 3;
    var type = "rab";
    var direct = "forward";
    var inp = document.getElementById("prikazDoD");
    var date = inp.value;
    if (date != "") {
        var url = "ipraspisok?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET");
    } else {
        alert("Заполните дату приказа ДО");
    }
}

function otchOmsuDate() {
    var period = 50;
    var type = "days";
    var direct = "back";
    var inp = document.getElementById("ipraDateOk");
    var date = inp.value;
    if (date != "") {
        var url = "ipraspisok?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET");
    } else {
        alert("Заполните дату окончания ИПРА");
    }
}

function otchCenterDate() {
    var period = 40;
    var type = "days";
    var direct = "back";
    var inp = document.getElementById("ipraDateOk");
    var date = inp.value;
    if (date != "") {
        var url = "ipraspisok?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET");
    } else {
        alert("Заполните дату окончания ИПРА");
    }
}

function otchDoDate() {
    var period = 1;
    var type = "month";
    var direct = "back";
    var inp = document.getElementById("ipraDateOk");
    var date = inp.value;
    if (date != "") {
        var url = "ipraspisok?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET");
    } else {
        alert("Заполните дату окончания ИПРА");
    }
}

function appendDate(id, date) {
    var inp = document.getElementById(id);
    if (inp != null) {
        inp.value = date;
    }
}

function validateIpra() {
    var now = new Date();
    now.setHours(0);
    now.setMinutes(0);
    now.setSeconds(0);
    now.setMilliseconds(0);
    var dateLimit = new Date();
    dateLimit.setDate(1);
    dateLimit.setMonth(0);
    dateLimit.setFullYear(2010);
    dateLimit.setHours(0);
    dateLimit.setMinutes(0);
    dateLimit.setSeconds(0);
    dateLimit.setMilliseconds(0);
    var drLimit = new Date();
    drLimit.setDate(1);
    drLimit.setMonth(0);
    drLimit.setFullYear(1970);
    drLimit.setHours(0);
    drLimit.setMinutes(0);
    drLimit.setSeconds(0);
    drLimit.setMilliseconds(0);
    var fioLimit = 100;
    var nLimit = 50;

    var btn = document.getElementById("saveBtn");
    btn.disabled = "disabled";
    var result = true;
    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        inp[loop].classList.remove("wrong");
    }

    for (loop = 0; loop < inp.length; loop++) {
        var id = inp[loop].id;
        var val = inp[loop].value;
        if ((id == "childFam") || (id == "childName")) {
            if ((val == "") || (val.length > fioLimit)) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "childPatr") {
            if (val.length > fioLimit) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "childDr") {
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            } else if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else {
                var drD = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (drD.getTime() < drLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                } else if (drD.getTime() > now.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "ipraN") {
            if ((val == "") || (val.length > nLimit)) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "expDate") {
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            } else if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "ipraDateOk") {
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            } else if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "ishMseN") {
            if (val.length > nLimit) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "ishMseD") {
            if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else if (val != "") {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "vhDoN") {
            if (val.length > nLimit) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "vhDoD") {
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            } else if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "prikazDoN") {
            if (val.length > nLimit) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "prikazDoD") {
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            } else if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "omsuN") {
            if (val.length > nLimit) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "omsuD") {
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            } else if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "prOmsuD") {
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            } else if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "oznakD") {
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            } else if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "tpmpkD") {
            if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else if (val != "") {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "otchOmsu") {
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            } else if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "otchCenter") {
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            } else if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        } else if (id == "otchDo") {
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            } else if (val.length > 10) {
                inp[loop].className = "wrong";
                result = false;
            } else {
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date.getTime() < dateLimit.getTime()) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            }
        }
    }
    if (!result) {
        alert("Проверьте данные в полях, отмеченных КРАСНЫМ ЦВЕТОМ (проверяется заполнение, корректность и количество символов)");
        var wrong = document.getElementsByClassName("wrong");
        wrong[0].focus();
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
    } else if (result) {
        document.getElementById("ipraForm").submit();
    }
}

function clearReg() {
    var reg = document.getElementById("childReg");
    if (reg != null) {
        reg.innerHTML = "";
    }
}

function appendRegion(id, name) {
    var reg = document.getElementById("childReg");
    if (reg != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        reg.appendChild(opt);
        reg.onchange();
    }
}

function delIpra() {
    var ipraId = document.getElementById("ipraId");
    var id = 0;
    if (ipraId != null) {
        id = ipraId.value;
    }
    if (id != 0) {
        if (confirm("Вы уверены, что хотите удалить эту ИПРА?")) {
            var url = "ipraspisok?action=deleteipra&id=" + id;
            document.location.href = url;
        } else {
        }

    }
}

function prOmsuDate() {
    var period = 10;
    var type = "rab";
    var direct = "forward";
    var inp = document.getElementById("omsuD");
    var date = inp.value;
    if (date != "") {
        var url = "ipraspisok?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET");
    } else {
        alert("Заполните дату письма в ОМСУ");
    }
}

function oznakDate() {
    var period = 10;
    var type = "rab";
    var direct = "forward";
    var inp = document.getElementById("prOmsuD");
    var date = inp.value;
    if (date != "") {
        var url = "ipraspisok?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET");
    } else {
        alert("Заполните дату приказа ОМСУ");
    }
}

function chStatus() {
    var st = document.getElementById("status");
    if (st != null) {
        st.value = this.value;
    }
}

function clickChild() {
    var id = document.getElementById("childId");
    if (id != null) {
        window.open("client?kat=children&id=" + id.value, "_blank");
    }
}

function clickPrintOtchetBtn(){
    var dialog = document.getElementById("ipraOtchetDialog");
    if (dialog != null){
        dialog.showModal();
    }
}

function printOtchetCenter(){
    var ipraId = document.getElementById("ipraId");
    if (ipraId != null) {
        var url = "print?type=ipraotchetcenter&old=isold&ipra=" + ipraId.value;
        window.open(url, '_blank');
    }
}

function printLetterToDO(){
    var ipraId = document.getElementById("ipraId");
    if (ipraId != null) {
        var url = "print?type=ipralettertodo&old=isold&ipra=" + ipraId.value;
        window.open(url, '_blank');
    }
}

function printOtchetDO(){
    var ipraId = document.getElementById("ipraId");
    if (ipraId != null) {
        var url = "print?type=ipraotchetdo&old=isold&ipra=" + ipraId.value;
        window.open(url, '_blank');
    }
}

function printLetterToMse(){
    var ipraId = document.getElementById("ipraId");
    if (ipraId != null) {
        var url = "print?type=ipralettertomse&old=isold&ipra=" + ipraId.value;
        window.open(url, '_blank');
    }
}

function closeOthetDialog(){
    var dialog = document.getElementById("ipraOtchetDialog");
    if (dialog != null){
        dialog.close();
    }
}