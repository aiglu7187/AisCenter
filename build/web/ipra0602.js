/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initForm() {
    var form = document.getElementById("ipraForm");
    form.onsubmit = validateIpra;
}

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
}

function callSearchDialog() {
    var dialog = document.getElementById("searchClDialog");
    if (dialog != null) {
        dialog.showModal();
    }
    searchChild();
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
    if (dialog != null) {
        dialog.close();
    }

    var divInfoChild = document.getElementById("divInfoChild");
    if (divInfoChild != null) {
        divInfoChild.innerHTML = "";
        var lblFam = document.createElement("label");
        lblFam.appendChild(document.createTextNode("Фамилия: "));
        var inpFam = document.createElement("input");
        inpFam.type = "text";
        inpFam.id = "childFam";
        inpFam.name = "childFam";
        divInfoChild.appendChild(lblFam);
        divInfoChild.appendChild(inpFam);

        var lblName = document.createElement("label");
        lblName.appendChild(document.createTextNode("Имя: "));
        var inpName = document.createElement("input");
        inpName.type = "text";
        inpName.id = "childName";
        inpName.name = "childName";
        divInfoChild.appendChild(lblName);
        divInfoChild.appendChild(inpName);

        var lblPatr = document.createElement("label");
        lblPatr.appendChild(document.createTextNode("Отчество: "));
        var inpPatr = document.createElement("input");
        inpPatr.type = "text";
        inpPatr.id = "childPatr";
        inpPatr.name = "childPatr";
        divInfoChild.appendChild(lblPatr);
        divInfoChild.appendChild(inpPatr);
        divInfoChild.appendChild(document.createElement("br"));

        var lblDr = document.createElement("label");
        lblDr.appendChild(document.createTextNode("Дата рождения: "));
        var inpDr = document.createElement("input");
        inpDr.type = "date";
        inpDr.id = "childDr";
        inpDr.name = "childDr";
        divInfoChild.appendChild(lblDr);
        divInfoChild.appendChild(inpDr);
        divInfoChild.appendChild(document.createElement("br"));

        var regId = document.createElement("input");
        regId.type = "hidden";
        regId.name = "regId";
        regId.id = "regId";
        var lblReg = document.createElement("label");
        lblReg.appendChild(document.createTextNode("Район проживания: "));
        var selReg = document.createElement("select");
        selReg.id = "childReg";
        selReg.onchange = regionSelect;
        divInfoChild.appendChild(regId);
        divInfoChild.appendChild(lblReg);
        divInfoChild.appendChild(selReg);
        divInfoChild.appendChild(document.createElement("br"));
        var url = "ipraspisok?action=regions";
        requ(url, "GET");

        appendIpra(divInfoChild);

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
    var url = "ipraspisok?action=search&fam=" + fam + "&nam=" + nam + "&patr=" + patr;
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
        var children = responseXML.getElementsByTagName("children")[0];
        var ipradate = responseXML.getElementsByTagName("ipradate")[0];
        var regions = responseXML.getElementsByTagName("regions")[0];
        if (children != null) {
            clearDialog();
            if (children.childNodes.length > 0) {
                for (loop = 0; loop < children.childNodes.length; loop++) {
                    var child = children.childNodes[loop];
                    var chId = child.getElementsByTagName("id")[0];
                    var chNom = child.getElementsByTagName("nom")[0];
                    var chFam = child.getElementsByTagName("fam")[0];
                    var chName = child.getElementsByTagName("name")[0];
                    var chPatr = child.getElementsByTagName("patr")[0];
                    var chDr = child.getElementsByTagName("dr")[0];
                    var chReg = child.getElementsByTagName("reg")[0];
                    appendChildren(chId.childNodes[0].nodeValue, chNom.childNodes[0].nodeValue,
                            chFam.childNodes[0].nodeValue, chName.childNodes[0].nodeValue,
                            chPatr.childNodes[0].nodeValue, chDr.childNodes[0].nodeValue,
                            chReg.childNodes[0].nodeValue);
                }
            }
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
    var tr = document.createElement("thead");
    var td1 = document.createElement("td");
    td1.appendChild(document.createTextNode(""));
    var td2 = document.createElement("td");
    var b2 = document.createElement("b");
    b2.appendChild(document.createTextNode("Номер"));
    td2.appendChild(b2);
    var td3 = document.createElement("td");
    var b3 = document.createElement("b");
    b3.appendChild(document.createTextNode("Фамилия"));
    td3.appendChild(b3);
    var td4 = document.createElement("td");
    var b4 = document.createElement("b");
    b4.appendChild(document.createTextNode("Имя"));
    td4.appendChild(b4);
    var td5 = document.createElement("td");
    var b5 = document.createElement("b");
    b5.appendChild(document.createTextNode("Отчество"));
    td5.appendChild(b5);
    var td6 = document.createElement("td");
    var b6 = document.createElement("b");
    b6.appendChild(document.createTextNode("Дата рождения"));
    td6.appendChild(b6);
    var td9 = document.createElement("td");
    var b9 = document.createElement("b");
    b9.appendChild(document.createTextNode("Район проживания"));
    td9.appendChild(b9);
    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);
    tr.appendChild(td5);
    tr.appendChild(td6);
    tr.appendChild(td9);
    var tbody = document.createElement("tbody");
    tbody.id = "bodyChild";
    tab.appendChild(tr);
    tab.appendChild(tbody);
}

function appendChildren(chId, chNom, chFam, chName, chPatr, chDr, chReg) {
    var tab = document.getElementById("bodyChild");
    var tr = document.createElement("tr");
    var td1 = document.createElement("td");
    td1.appendChild(document.createTextNode(chId));
    var td2 = document.createElement("td");
    td2.appendChild(document.createTextNode(chNom));
    var td3 = document.createElement("td");
    td3.appendChild(document.createTextNode(chFam));
    var td4 = document.createElement("td");
    td4.appendChild(document.createTextNode(chName));
    var td5 = document.createElement("td");
    td5.appendChild(document.createTextNode(chPatr));
    var td6 = document.createElement("td");
    td6.appendChild(document.createTextNode(chDr));
    var td9 = document.createElement("td");
    td9.appendChild(document.createTextNode(chReg));
    tr.appendChild(td1);
    tr.appendChild(td2);
    tr.appendChild(td3);
    tr.appendChild(td4);
    tr.appendChild(td5);
    tr.appendChild(td6);
    tr.appendChild(td9);
    tr.onclick = selectChild;
    tab.appendChild(tr);
}

function selectChild() {
    var dialog = document.getElementById("searchClDialog");
    if (dialog != null) {
        dialog.close();
    }

    var divInfoChild = document.getElementById("divInfoChild");
    if (divInfoChild != null) {
        divInfoChild.innerHTML = "";
        var inpId = document.createElement("input");
        inpId.type = "hidden";
        inpId.id = "childId";
        inpId.name = "childId";
        inpId.value = this.childNodes[0].childNodes[0].nodeValue;
        divInfoChild.appendChild(inpId);
        var lbl = document.createElement("label");
        lbl.appendChild(document.createTextNode(this.childNodes[1].childNodes[0].nodeValue));
        divInfoChild.appendChild(lbl);
        divInfoChild.appendChild(document.createElement("br"));
        divInfoChild.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue + " " +
                this.childNodes[5].childNodes[0].nodeValue));
        divInfoChild.appendChild(document.createElement("br"));
        divInfoChild.appendChild(document.createTextNode(this.childNodes[6].childNodes[0].nodeValue));
        divInfoChild.appendChild(document.createElement("br"));
        appendIpra(divInfoChild);
    }

}

function appendIpra(divInfoChild) {
    divInfoChild.appendChild(document.createElement("br"));
    var lblIpra = document.createElement("strong");
    lblIpra.appendChild(document.createTextNode("ИПРА "));
    divInfoChild.appendChild(lblIpra);
    divInfoChild.appendChild(document.createElement("br"));

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
    divInfoChild.appendChild(lblIpraN);
    divInfoChild.appendChild(inpIpraN);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(lblExpDate);
    divInfoChild.appendChild(inpExpDate);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(lblIpraDateOk);
    divInfoChild.appendChild(inpIpraDateOk);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(document.createElement("br"));

    var lblIshMse = document.createElement("strong");
    lblIshMse.appendChild(document.createTextNode("Исходящее письмо из МСЭ"));
    divInfoChild.appendChild(lblIshMse);
    divInfoChild.appendChild(document.createElement("br"));
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
    divInfoChild.appendChild(lblIshMseN);
    divInfoChild.appendChild(inpIshMseN);
    divInfoChild.appendChild(lblIshMseD);
    divInfoChild.appendChild(inpIshMseD);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(document.createElement("br"));

    var lblVhDo = document.createElement("strong");
    lblVhDo.appendChild(document.createTextNode("Входящее письмо в ДО"));
    divInfoChild.appendChild(lblVhDo);
    divInfoChild.appendChild(document.createElement("br"));
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
    divInfoChild.appendChild(lblVhDoN);
    divInfoChild.appendChild(inpVhDoN);
    divInfoChild.appendChild(lblVhDoD);
    divInfoChild.appendChild(inpVhDoD);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(document.createElement("br"));

    var lblPrikazDo = document.createElement("strong");
    lblPrikazDo.appendChild(document.createTextNode("Приказ ДО"));
    divInfoChild.appendChild(lblPrikazDo);
    divInfoChild.appendChild(document.createElement("br"));
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
    divInfoChild.appendChild(lblPrikazDoN);
    divInfoChild.appendChild(inpPrikazDoN);
    divInfoChild.appendChild(lblPrikazDoD);
    divInfoChild.appendChild(inpPrikazDoD);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(document.createElement("br"));

    var lblOmsuD = document.createElement("label");
    lblOmsuD.appendChild(document.createTextNode("Дата письма в ОМСУ: "));
    var inpOmsuD = document.createElement("input");
    inpOmsuD.type = "date";
    inpOmsuD.id = "omsuD";
    inpOmsuD.name = "omsuD";
    inpOmsuD.onclick = omsuDate;
    divInfoChild.appendChild(lblOmsuD);
    divInfoChild.appendChild(inpOmsuD);
    divInfoChild.appendChild(document.createElement("br"));
    var lblPrOmsuD = document.createElement("label");
    lblPrOmsuD.appendChild(document.createTextNode("Дата приказа ОМСУ: "));
    var inpPrOmsuD = document.createElement("input");
    inpPrOmsuD.type = "date";
    inpPrOmsuD.id = "prOmsuD";
    inpPrOmsuD.name = "prOmsuD";
    inpPrOmsuD.onclick = prOmsuDate;
    divInfoChild.appendChild(lblPrOmsuD);
    divInfoChild.appendChild(inpPrOmsuD);
    divInfoChild.appendChild(document.createElement("br"));
    var lblOznak = document.createElement("label");
    lblOznak.appendChild(document.createTextNode("Дата ознакомления родителей (законных представителей): "));
    var inpOznak = document.createElement("input");
    inpOznak.type = "date";
    inpOznak.id = "oznakD";
    inpOznak.name = "oznakD";
    inpOznak.onclick = oznakDate;
    divInfoChild.appendChild(lblOznak);
    divInfoChild.appendChild(inpOznak);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(document.createElement("br"));

    var lblTpmpkD = document.createElement("label");
    lblTpmpkD.appendChild(document.createTextNode("Дата запроса в ТПМПК: "));
    var inpTpmpkD = document.createElement("input");
    inpTpmpkD.type = "date";
    inpTpmpkD.id = "tpmpkD";
    inpTpmpkD.name = "tpmpkD";
    inpTpmpkD.onclick = tpmpkDate;
    divInfoChild.appendChild(lblTpmpkD);
    divInfoChild.appendChild(inpTpmpkD);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(document.createElement("br"));

    var lblOtch = document.createElement("strong");
    lblOtch.appendChild(document.createTextNode("Даты отчетов"));
    divInfoChild.appendChild(lblOtch);
    divInfoChild.appendChild(document.createElement("br"));
    var lblOtchOmsu = document.createElement("label");
    lblOtchOmsu.appendChild(document.createTextNode("ОМСУ: "));
    var inpOtchOmsu = document.createElement("input");
    inpOtchOmsu.type = "date";
    inpOtchOmsu.id = "otchOmsu";
    inpOtchOmsu.name = "otchOmsu";
    inpOtchOmsu.onclick = otchOmsuDate;
    var lblOtchCenter = document.createElement("label");
    lblOtchCenter.appendChild(document.createTextNode("ОЦППМСП: "));
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
    divInfoChild.appendChild(lblOtchOmsu);
    divInfoChild.appendChild(inpOtchOmsu);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(lblOtchCenter);
    divInfoChild.appendChild(inpOtchCenter);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(lblOtchDo);
    divInfoChild.appendChild(inpOtchDo);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(document.createElement("br"));

    var saveBtn = document.createElement("input");
    saveBtn.type = "submit";
    saveBtn.id = "saveBtn";
    saveBtn.value = "Сохранить";
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
    var period = 30;
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

function appendDate(id, date) {
    var inp = document.getElementById(id);
    if (inp != null) {
        inp.value = date;
    }
}

function validateIpra() {
    var btn = document.getElementById("saveBtn");
    btn.disabled = "disabled";
    var result = true;
    if (result) {
        if (document.getElementById("childFam") != null) {
            if (document.getElementById("childFam").value == "") {
                alert("Заполните фамилию ребенка");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("childName") != null) {
            if (document.getElementById("childName").value == "") {
                alert("Заполните имя ребенка");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("childDr") != null) {
            if (document.getElementById("childDr").value == "") {
                alert("Заполните имя ребенка");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("ipraN") != null) {
            if (document.getElementById("ipraN").value == "") {
                alert("Заполните номер ИПРА");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("expDate") != null) {
            if (document.getElementById("expDate").value == "") {
                alert("Заполните дату экспертизы");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("expDate") != null) {
            if (document.getElementById("expDate").length > 10) {
                alert("Проверьте дату экспертизы");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("ipraDateOk") != null) {
            if (document.getElementById("ipraDateOk").value == "") {
                alert("Заполните дату окончания ИПРА");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("ipraDateOk") != null) {
            if (document.getElementById("ipraDateOk").length > 10) {
                alert("Проверьте дату окончания ИПРА");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("ishMseN") != null) {
            if (document.getElementById("ishMseN").value == "") {
                alert("Заполните номер исходящего письма МСЭ");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("ishMseD") != null) {
            if (document.getElementById("ishMseD").value == "") {
                alert("Заполните дату исходящего письма МСЭ");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("ishMseD") != null) {
            if (document.getElementById("ishMseD").length > 10) {
                alert("Проверьте дату исходящего письма МСЭ");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("vhDoN") != null) {
            if (document.getElementById("vhDoN").value == "") {
                alert("Заполните номер входящего письма ДО");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("vhDoD") != null) {
            if (document.getElementById("vhDoD").value == "") {
                alert("Заполните дату входящего письма ДО");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("vhDoD") != null) {
            if (document.getElementById("vhDoD").length > 10) {
                alert("Проверьте дату входящего письма ДО");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("prikazDoD") != null) {
            if (document.getElementById("prikazDoD").value == "") {
                alert("Заполните дату приказа ДО");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("prikazDoD") != null) {
            if (document.getElementById("prikazDoD").length > 10) {
                alert("Проверьте дату приказа ДО");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("omsuD") != null) {
            if (document.getElementById("omsuD").value == "") {
                alert("Заполните дату письма в ОМСУ");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("omsuD") != null) {
            if (document.getElementById("omsuD").length > 10) {
                alert("Проверьте дату письма в ОМСУ");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("prOmsuD") != null) {
            if (document.getElementById("prOmsuD").value == "") {
                alert("Заполните дату приказа ОМСУ");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("prOmsuD") != null) {
            if (document.getElementById("prOmsuD").length > 10) {
                alert("Проверьте дату приказа ОМСУ");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("oznakD") != null) {
            if (document.getElementById("prOmsuD").value == "") {
                alert("Заполните дату ознакомления родителей (законных представителей)");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("oznakD") != null) {
            if (document.getElementById("prOmsuD").length > 10) {
                alert("Проверьте дату ознакомления родителей (законных представителей)");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("otchOmsu") != null) {
            if (document.getElementById("otchOmsu").value == "") {
                alert("Заполните дату отчета ОМСУ");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("otchOmsu") != null) {
            if (document.getElementById("otchOmsu").length > 10) {
                alert("Проверьте дату отчета ОМСУ");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("otchCenter") != null) {
            if (document.getElementById("otchCenter").value == "") {
                alert("Заполните дату отчета ОЦППМСП");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("otchCenter") != null) {
            if (document.getElementById("otchCenter").length > 10) {
                alert("Проверьте дату отчета ОЦППМСП");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("otchDo") != null) {
            if (document.getElementById("otchDo").value == "") {
                alert("Заполните дату отчета ДО");
                result = false;
            }
        }
    }
    if (result) {
        if (document.getElementById("otchDo") != null) {
            if (document.getElementById("otchDo").length > 10) {
                alert("Проверьте дату отчета ДО");
                result = false;
            }
        }
    }
    if (result == false) {
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
    }
    return result;
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
        var url = "ipraspisok?action=deleteipra&id=" + id;
        document.location.href = url;
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