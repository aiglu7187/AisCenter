/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    var prikazDoD = document.getElementById("prikazDoD");
    if (prikazDoD != null) {
        prikazDoD.onclick = prikazDate;
    }
    var omsuD = document.getElementById("omsuD");
    if (omsuD != null) {
        omsuD.onclick = omsuDate;
    }
    /*    var prOmsuD = document.getElementById("prOmsuD");
     if (prOmsuD != null) {
     prOmsuD.onclick = prOmsuDate;
     }
     
     var oznakD = document.getElementById("oznakD");
     if (oznakD != null) {
     oznakD.onclick = oznakDate;
     }
     */
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
    if (selMse != null) {
        selMse.onchange();
    }

    var img = document.getElementsByTagName("img");
    for (loop = 0; loop < img.length; loop++) {
        if (img[loop].id.substring(0, 7) == "delCond") {
            img[loop].onclick = deleteCond;
        } else if (img[loop].id.substring(0, 8) == "plusCond") {
            img[loop].onclick = plusCond;
        }
    }

    var omsuId = document.getElementById("omsuId");
    if (omsuId.value == "") {
        findOmsuAndTpmpk();
    }
}


function saveipra() {
    var form = document.getElementById("ipraForm");
    var body = "action=save&" + getRequestBody(form);
    requ("ipra2018spisok", "POST", body);
}

function getRequestBody(form) {
    var params = new Array();
    for (var i = 0; i < form.elements.length; i++) {
        var param = encodeURIComponent(form.elements[i].name);
        param += "=";
        param += encodeURIComponent(form.elements[i].value);
        params.push(param);
    }
    return params.join("&");
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

        var divPol = document.createElement("div");
        divPol.appendChild(document.createTextNode("Пол: "));
        divPol.appendChild(document.createElement("br"));
        var labelM = document.createElement("label");
        labelM.id = "labelPolM";
        var radioM = document.createElement("input");
        radioM.type = "radio";
        radioM.name = "polR";
        radioM.id = "polR";
        radioM.value = "м";
        labelM.appendChild(radioM);
        labelM.onclick = clickPol;
        labelM.appendChild(document.createTextNode("мужской"));
        divPol.appendChild(labelM);
        divPol.appendChild(document.createElement("br"));
        var labelW = document.createElement("label");
        labelW.id = "labelPolW";
        var radioW = document.createElement("input");
        radioW.type = "radio";
        radioW.name = "polR";
        radioM.id = "polR";
        radioW.value = "ж";
        labelW.appendChild(radioW);
        labelW.onclick = clickPol;
        labelW.appendChild(document.createTextNode("женский"));
        divPol.appendChild(labelW);
        var hidPol = document.createElement("input");
        hidPol.type = "hidden";
        hidPol.name = "pol";
        hidPol.id = "pol";
        divPol.appendChild(hidPol);
        divChild.appendChild(divPol);

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
        var url = "ipra2018spisok?action=regions";
        requ(url, "GET", null);

        var divIpra = document.getElementById("divIpra");
        if (divIpra == null) {
            appendIpra(divInfoChild);
        }

    }
}

function regionSelect() {
    var regId = document.getElementById("regId");
    var selReg = document.getElementById("childReg");
    regId.value = selReg.value;
    var url = "ipra2018spisok?action=findomsu&regid=" + regId.value;
    requ(url, "GET", null);
    url = "ipra2018spisok?action=findtpmpk&regid=" + regId.value;
    requ(url, "GET", null);
}

function findOmsuAndTpmpk() {
    var regId = document.getElementById("regId");
    var url = "ipra2018spisok?action=findomsu&regid=" + regId.value;
    requ(url, "GET", null);
    url = "ipra2018spisok?action=findtpmpk&regid=" + regId.value;
    requ(url, "GET", null);
}

function searchChild() {
    var fam = document.getElementById("fam").value;
    var nam = document.getElementById("nam").value;
    var patr = document.getElementById("patr").value;
    var url = "search?type=client&kat=children&fam=" + fam + "&nam=" + nam + "&patr=" + patr;
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
            document.getElementById("saveBtn").disabled = 0;
            var savePerechenBtn = document.getElementById("savePerechenBtn");
            if (savePerechenBtn != null) {
                savePerechenBtn.disabled = 0;
            }
            var printPrikazBtn = document.getElementById("printPrikazBtn");
            if (printPrikazBtn != null) {
                printPrikazBtn.disabled = 0;
            }
            var printPerechenBtn = document.getElementById("printPerechenBtn");
            if (printPerechenBtn != null) {
                printPerechenBtn.disabled = 0;
            }
        } else if (request.status == 204) {
        } else {
            alert("Ошибка подключения к серверу\nДанные не были сохранены");
            document.getElementById("saveBtn").disabled = 0;
            var savePerechenBtn = document.getElementById("savePerechenBtn");
            if (savePerechenBtn != null) {
                savePerechenBtn.disabled = 0;
            }
            var printPrikazBtn = document.getElementById("printPrikazBtn");
            if (printPrikazBtn != null) {
                printPrikazBtn.disabled = 0;
            }
            var printPerechenBtn = document.getElementById("printPerechenBtn");
            if (printPerechenBtn != null) {
                printPerechenBtn.disabled = 0;
            }
        }
    }
}

function parseMessages(responseXML) {   // преобразование полученного xml
    if (responseXML == null) {
        return false;
    } else {
        var result = responseXML.getElementsByTagName("result")[0];
        var clients = responseXML.getElementsByTagName("clients")[0];
        var ipradate = responseXML.getElementsByTagName("ipradate")[0];
        var regions = responseXML.getElementsByTagName("regions")[0];
        var mselist = responseXML.getElementsByTagName("mselist")[0];
        var omsulist = responseXML.getElementsByTagName("omsulist")[0];
        var tpmpklist = responseXML.getElementsByTagName("tpmpklist")[0];
        var regid = responseXML.getElementsByTagName("regid")[0];
        if (result != null) {
            appendResult(result.childNodes[0].nodeValue);
        }
        if (clients != null) {
            clearDialog();
            if (clients.childNodes.length > 0) {
                var head = clients.childNodes[0];
                appendHead(head);
                for (loop = 1; loop < clients.childNodes.length; loop++) {
                    var client = clients.childNodes[loop];
                    appendBody(client, selectChild);
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
        if (mselist != null) {
            clearMse();
            if (mselist.childNodes.length > 0) {
                for (loop = 0; loop < mselist.childNodes.length; loop++) {
                    var mse = mselist.childNodes[loop];
                    var mseName = mse.getElementsByTagName("name")[0];
                    var mseId = mse.getElementsByTagName("id")[0];
                    appendMse(mseId.childNodes[0].nodeValue, mseName.childNodes[0].nodeValue);
                }
            }
        }
        if (omsulist != null) {
            clearOmsu();
            if (omsulist.childNodes.length > 0) {
                for (loop = 0; loop < omsulist.childNodes.length; loop++) {
                    var omsu = omsulist.childNodes[loop];
                    var omsuName = omsu.getElementsByTagName("name")[0];
                    var omsuId = omsu.getElementsByTagName("id")[0];
                    var omsuSelected = omsu.getElementsByTagName("selected")[0];
                    appendOmsu(omsuId.childNodes[0].nodeValue, omsuName.childNodes[0].nodeValue,
                            omsuSelected.childNodes[0].nodeValue);
                }
            }
        }
        if (tpmpklist != null) {
            clearTpmpk();
            if (tpmpklist.childNodes.length > 0) {
                for (loop = 0; loop < tpmpklist.childNodes.length; loop++) {
                    var tpmpk = tpmpklist.childNodes[loop];
                    var tpmpkName = tpmpk.getElementsByTagName("name")[0];
                    var tpmpkId = tpmpk.getElementsByTagName("id")[0];
                    var tpmpkSelected = tpmpk.getElementsByTagName("selected")[0];
                    appendTpmpk(tpmpkId.childNodes[0].nodeValue, tpmpkName.childNodes[0].nodeValue,
                            tpmpkSelected.childNodes[0].nodeValue);
                }
            }
        }
        if (regid != null) {
            appendRegId(regid.childNodes[0].nodeValue);
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

function selectChild() {
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
        var regId = document.createElement("input");
        regId.type = "hidden";
        regId.id = "regId";
        regId.name = "regId";
        divChild.appendChild(regId);
        var url = "ipra2018spisok?action=findregion&name=" + this.childNodes[6].childNodes[0].nodeValue;
        requ(url, "GET", null);

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
    var url;

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
    var lblIpraNexp = document.createElement("label");
    lblIpraNexp.appendChild(document.createTextNode("Номер протокола экспертизы: "));
    var inpIpraNexp = document.createElement("input");
    inpIpraNexp.type = "text";
    inpIpraNexp.id = "ipraNexp";
    inpIpraNexp.name = "ipraNexp";
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
    divIpra.appendChild(lblIpraNexp);
    divIpra.appendChild(inpIpraNexp);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(lblExpDate);
    divIpra.appendChild(inpExpDate);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(lblIpraDateOk);
    divIpra.appendChild(inpIpraDateOk);
    divIpra.appendChild(document.createElement("br"));
    divIpra.appendChild(document.createElement("br"));

    var lblMse = document.createElement("label");
    lblMse.appendChild(document.createTextNode("Бюро МСЭ: "));
    var hidMse = document.createElement("input");
    hidMse.type = "hidden";
    hidMse.id = "mseId";
    hidMse.name = "mseId";
    var selMse = document.createElement("select");
    selMse.id = "selMse";
    selMse.name = "selMse";
    selMse.onchange = mseSelect;
    divIpra.appendChild(lblMse);
    divIpra.appendChild(hidMse);
    divIpra.appendChild(selMse);
    url = "sprmse?action=simplelist";
    requ(url, "GET", null);
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

    var ipraId = document.getElementById("ipraId");
    if (ipraId != null) {
        var lblOmsu = document.createElement("strong");
        lblOmsu.appendChild(document.createTextNode("Запрос от ОМСУ "));
        var hidOmsu = document.createElement("input");
        hidOmsu.type = "hidden";
        hidOmsu.id = "omsuId";
        hidOmsu.name = "omsuId";
        var selOmsu = document.createElement("select");
        selOmsu.id = "selOmsu";
        selOmsu.name = "selOmsu";
        selOmsu.onchange = omsuSelect;
        divIpra.appendChild(lblOmsu);
        divIpra.appendChild(document.createElement("br"));
        divIpra.appendChild(hidOmsu);
        divIpra.appendChild(selOmsu);
        divIpra.appendChild(document.createElement("br"));
        var lblOmsuReq = document.createElement("label");
        lblOmsuReq.appendChild(document.createTextNode("номер: "));
        var omsuReqN = document.createElement("input");
        omsuReqN.type = "text";
        omsuReqN.id = "omsuReqN";
        omsuReqN.name = "omsuReqN";
        var lblOmsuReq2 = document.createElement("label");
        lblOmsuReq2.appendChild(document.createTextNode(" дата: "));
        var omsuReqD = document.createElement("input");
        omsuReqD.type = "date";
        omsuReqD.id = "omsuReqD";
        omsuReqD.name = "omsuReqD";
        divIpra.appendChild(lblOmsuReq);
        divIpra.appendChild(omsuReqN);
        divIpra.appendChild(lblOmsuReq2);
        divIpra.appendChild(omsuReqD);
        divIpra.appendChild(document.createElement("br"));
        divIpra.appendChild(document.createElement("br"));

        var lblVhDo = document.createElement("strong");
        lblVhDo.appendChild(document.createTextNode("Входящее письмо в ДО из ОМСУ"));
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
        var printPrikazBtn = document.createElement("input");
        printPrikazBtn.type = "button";
        printPrikazBtn.id = "printPrikazBtn";
        printPrikazBtn.value = "Выгрузка приказа";
        printPrikazBtn.onclick = clickPrintPrikazBtn;
        var printHid = document.createElement("input");
        printHid.type = "hidden";
        printHid.id = "printFlag";
        printHid.value = "0";
        divIpra.appendChild(lblPrikazDoN);
        divIpra.appendChild(inpPrikazDoN);
        divIpra.appendChild(lblPrikazDoD);
        divIpra.appendChild(inpPrikazDoD);
        divIpra.appendChild(document.createElement("br"));
        divIpra.appendChild(printPrikazBtn);
        divIpra.appendChild(printHid);
        divIpra.appendChild(document.createElement("br"));
        divIpra.appendChild(document.createElement("br"));

        var lblTpmpk = document.createElement("strong");
        lblTpmpk.appendChild(document.createTextNode("ТПМПК "));
        var hidTpmpk = document.createElement("input");
        hidTpmpk.type = "hidden";
        hidTpmpk.id = "tpmpkId";
        hidTpmpk.name = "tpmpkId";
        var selTpmpk = document.createElement("select");
        selTpmpk.id = "selTpmpk";
        selTpmpk.name = "selTpmpk";
        selTpmpk.onchange = tpmpkSelect;
        divIpra.appendChild(lblTpmpk);
        divIpra.appendChild(hidTpmpk);
        divIpra.appendChild(document.createElement("br"));
        divIpra.appendChild(selTpmpk);
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
        var lblTpmpkN = document.createElement("label");
        lblTpmpkN.appendChild(document.createTextNode("Номер письма в ТПМПК: "));
        var inpTpmpkN = document.createElement("input");
        inpTpmpkN.type = "text";
        inpTpmpkN.id = "tpmpkN";
        inpTpmpkN.name = "tpmpkN";
        divIpra.appendChild(lblTpmpkN);
        divIpra.appendChild(inpTpmpkN);
        divIpra.appendChild(document.createElement("br"));
        divIpra.appendChild(document.createElement("br"));

        var lblPerechen = document.createElement("strong");
        lblPerechen.appendChild(document.createTextNode("Перечень мероприятий"));
        var lblOmsuD = document.createElement("label");
        lblOmsuD.appendChild(document.createTextNode("Дата письма в ОМСУ (п. 1.1): "));
        var inpOmsuD = document.createElement("input");
        inpOmsuD.type = "date";
        inpOmsuD.id = "omsuD";
        inpOmsuD.name = "omsuD";
        inpOmsuD.onclick = omsuDate;
        var lblOmsuN = document.createElement("label");
        lblOmsuN.appendChild(document.createTextNode("Номер письма в ОМСУ: "));
        var inpOmsuN = document.createElement("input");
        inpOmsuN.type = "text";
        inpOmsuN.id = "omsuN";
        inpOmsuN.name = "omsuN";
        divIpra.appendChild(lblPerechen);
        divIpra.appendChild(document.createElement("br"));
        divIpra.appendChild(lblOmsuD);
        divIpra.appendChild(document.createElement("br"));
        divIpra.appendChild(inpOmsuD);
        divIpra.appendChild(document.createElement("br"));
        divIpra.appendChild(lblOmsuN);
        divIpra.appendChild(document.createElement("br"));
        divIpra.appendChild(inpOmsuN);
        divIpra.appendChild(document.createElement("br"));

        /*var lblPrOmsuD = document.createElement("label");
         lblPrOmsuD.appendChild(document.createTextNode("Дата приказа ОМСУ: "));
         var inpPrOmsuD = document.createElement("input");
         inpPrOmsuD.type = "date";
         inpPrOmsuD.id = "prOmsuD";
         inpPrOmsuD.name = "prOmsuD";
         inpPrOmsuD.onclick = prOmsuDate;
         divIpra.appendChild(lblPrOmsuD);
         divIpra.appendChild(inpPrOmsuD);
         divIpra.appendChild(document.createElement("br"));
         var lblOznak = document.createElement("label");
         lblOznak.appendChild(document.createTextNode("Дата ознакомления родителей (законных представителей): "));
         var inpOznak = document.createElement("input");
         inpOznak.type = "date";
         inpOznak.id = "oznakD";
         inpOznak.name = "oznakD";
         inpOznak.onclick = oznakDate;
         divIpra.appendChild(lblOznak);
         divIpra.appendChild(inpOznak);
         divIpra.appendChild(document.createElement("br"));*/
        divIpra.appendChild(document.createElement("br"));
    }
    var lblOtch = document.createElement("strong");
    lblOtch.appendChild(document.createTextNode("Даты отчетов"));
    divIpra.appendChild(lblOtch);
    divIpra.appendChild(document.createElement("br"));
    var lblOtchOmsu = document.createElement("label");
    lblOtchOmsu.appendChild(document.createTextNode("ОМСУ (п. 2.2): "));
    var inpOtchOmsu = document.createElement("input");
    inpOtchOmsu.type = "date";
    inpOtchOmsu.id = "otchOmsu";
    inpOtchOmsu.name = "otchOmsu";
    inpOtchOmsu.onclick = otchOmsuDate;
    var lblOtchCenter = document.createElement("label");
    lblOtchCenter.appendChild(document.createTextNode("ОЦППМСП (п. 1.2): "));
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

function otchDoDate() {
    var period = 1;
    var type = "month";
    var direct = "back";
    var inp = document.getElementById("ipraDateOk");
    var date = inp.value;
    if (date != "") {
        var url = "ipra2018spisok?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET", null);
    } else {
        alert("Заполните дату окончания ИПРА");
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
    var printPrikazBtn = document.getElementById("printPrikazBtn");
    if (printPrikazBtn != null) {
        printPrikazBtn.disabled = "disabled";
    }
    document.getElementById("saveBtn").disabled = "disabled";
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
        } else if (id == "pol") {    // пол
            if (val == "") {
                var polR = document.getElementsByName("polR");
                for (loop2 = 0; loop2 < polR.length; loop2++) {
                    polR[loop2].parentNode.parentNode.className = "wrong";
                }
                result = false;
            }
        } else if (id == "ipraN") {
            if ((val == "") || (val.length > nLimit)) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "ipraNexp") {
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
            if ((val == "") || (val.length > nLimit)) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "ishMseD") {
            if ((val == "") || (val.length > 10)) {
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
            } else if (val == "") {
                if (!inp[loop].disabled) {
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
        } else if (id == "omsuReqD") {
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
        document.getElementById("saveBtn").disabled = 0;
        var printPrikazBtn = document.getElementById("printPrikazBtn");
        if (printPrikazBtn != null) {
            printPrikazBtn.disabled = 0;
        }
        var savePerechenBtn = document.getElementById("savePerechenBtn");
        if (savePerechenBtn != null) {
            savePerechenBtn.disabled = 0;
        }
        var printPerechenBtn = document.getElementById("printPerechenBtn");
        if (printPerechenBtn != null) {
            printPerechenBtn.disabled = 0;
        }

    } else if (result) {
        saveipra();
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
            var url = "ipra2018spisok?action=deleteipra&id=" + id;
            document.location.href = url;
        } else {
        }

    }
}

function clickChild() {
    var id = document.getElementById("childId");
    if (id != null) {
        window.open("client?kat=children&id=" + id.value, "_blank");
    }
}

function appendResult(result) {
    if (result == 0) { // ошибка 
        alert("Данные не были сохранены. Проверьте корректность\nПри повторении ошибки обратитесь к администратору");
        document.getElementById("saveBtn").disabled = 0;
        document.getElementById("savePerechenBtn").disabled = 0;
        document.getElementById("printPrikazBtn").disabled = 0;
        document.getElementById("printPerechenBtn").disabled = 0;
    } else {
        var printFlag = document.getElementById("printFlag");
        var printPerechenFlag = document.getElementById("printPerechenFlag");

        if ((printFlag != null) && (printFlag.value == "1")) {
            printPrikaz();
            printFlag.value = "0";
            window.location = "ipra2018spisok?action=open&id=" + result;
        } else if ((printPerechenFlag != null) && (printPerechenFlag.value == "1")) {
            printPerechen();
            printPerechenFlag.value = "0";
            window.location = "ipra2018spisok?action=open&id=" + result;
        } else {
            alert("Данные сохранены");
            window.location = "ipra2018spisok?action=open&id=" + result;
        }

    }
}

function appendDate(id, date) {
    var inp = document.getElementById(id);
    if (inp != null) {
        inp.value = date;
    }
}

function clearMse() {
    var mse = document.getElementById("selMse");
    if (mse != null) {
        mse.innerHTML = "";
    }
}

function appendMse(id, name) {
    var mse = document.getElementById("selMse");
    if (mse != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        mse.appendChild(opt);
        mse.onchange();
    }
}

function mseSelect() {
    var mseId = document.getElementById("mseId");
    var selMse = document.getElementById("selMse");
    mseId.value = selMse.value;
}

function clearOmsu() {
    var omsu = document.getElementById("selOmsu");
    if (omsu != null) {
        omsu.innerHTML = "";
    }
}

function appendOmsu(id, name, selected) {
    var omsu = document.getElementById("selOmsu");
    if (omsu != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        if (selected == 1) {
            opt.selected = "selected";
        }
        omsu.appendChild(opt);
        omsu.onchange();
    }
}

function omsuSelect() {
    var omsuId = document.getElementById("omsuId");
    var selOmsu = document.getElementById("selOmsu");
    omsuId.value = selOmsu.value;
}

function clearTpmpk() {
    var tpmpk = document.getElementById("selTpmpk");
    if (tpmpk != null) {
        tpmpk.innerHTML = "";
    }
}

function appendTpmpk(id, name, selected) {
    var tpmpk = document.getElementById("selTpmpk");
    if (tpmpk != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        if (selected == 1) {
            opt.selected = "selected";
        }
        tpmpk.appendChild(opt);
        tpmpk.onchange();
    }
}

function tpmpkSelect() {
    var tpmpkId = document.getElementById("tpmpkId");
    var selTpmpk = document.getElementById("selTpmpk");
    tpmpkId.value = selTpmpk.value;
    var tpmpkD = document.getElementById("tpmpkD");
    var tpmpkN = document.getElementById("tpmpkN");
    if (selTpmpk.value == 0) {
        tpmpkD.value = "";
        tpmpkD.disabled = "disabled";
        tpmpkN.value = "";
        tpmpkN.disabled = "disabled";
    } else {
        tpmpkD.disabled = 0;
        tpmpkN.disabled = 0;
    }
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
    var inp = document.getElementById("omsuReqD");
    var date = inp.value;
    if (date != "") {
        var url = "ipraspisok?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET");
    } else {
        alert("Заполните дату запроса от ОМСУ");
    }
}

function tpmpkDate() {
    var period = 3;
    var type = "rab";
    var direct = "forward";
    var inp = document.getElementById("vhDoD");
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

function appendRegId(regid) {
    var regId = document.getElementById("regId");
    regId.value = regid;
    var url = "ipra2018spisok?action=findomsu&regid=" + regId.value;
    requ(url, "GET", null);
    url = "ipra2018spisok?action=findtpmpk&regid=" + regId.value;
    requ(url, "GET", null);
}

function clickPrintPrikazBtn() {
    var printFlag = document.getElementById("printFlag");
    printFlag.value = "1";
    validateIpra();
}

function printPrikaz() {
    var ipraId = document.getElementById("ipraId");
    if (ipraId != null) {
        var url = "print?type=ipraprikaz&ipra=" + ipraId.value;
        window.open(url, '_blank');
    }
}

function clickPrintOtchetBtn() {
    var dialog = document.getElementById("ipraOtchetDialog");
    if (dialog != null) {
        dialog.showModal();
    }
}

function printOtchetCenter() {
    var ipraId = document.getElementById("ipraId");
    if (ipraId != null) {
        var url = "print?type=ipraotchetcenter&old=&ipra=" + ipraId.value;
        window.open(url, '_blank');
    }
}

function printLetterToDO() {
    var ipraId = document.getElementById("ipraId");
    if (ipraId != null) {
        var url = "print?type=ipralettertodo&old=&ipra=" + ipraId.value;
        window.open(url, '_blank');
    }
}

function printOtchetDO() {
    var ipraId = document.getElementById("ipraId");
    if (ipraId != null) {
        var url = "print?type=ipraotchetdo&old=&ipra=" + ipraId.value;
        window.open(url, '_blank');
    }
}

function printLetterToMse() {
    var ipraId = document.getElementById("ipraId");
    if (ipraId != null) {
        var url = "print?type=ipralettertomse&old=&ipra=" + ipraId.value;
        window.open(url, '_blank');
    }
}

function closeOthetDialog() {
    var dialog = document.getElementById("ipraOtchetDialog");
    if (dialog != null) {
        dialog.close();
    }
}

function chStatus() {
    var st = document.getElementById("status");
    if (st != null) {
        st.value = this.value;
    }
}

function deleteCond() {
    var fullN = this.id.substring(7);
    var n = this.id.substring(7, this.id.indexOf("_"));
    var tr = document.getElementById("rowcond" + fullN);
    var tabel = document.getElementById("pertype" + n);
    var rows = tabel.getElementsByTagName("tr");
    if (rows.length > 1) {
        tr.remove();
    } else if (rows.length == 1) {
        var textarea = tr.getElementsByTagName("textarea");
        if (textarea != null) {
            for (loop = 0; loop < textarea.length; loop++) {
                textarea[loop].innerHTML = "";
                textarea[loop].value = "";
            }
        }
        var inp = tr.getElementsByTagName("input");
        if (inp != null) {
            for (loop = 0; loop < inp.length; loop++) {
                inp[loop].value = "";
            }
        }
    }
}

function plusCond() {
    var n = this.id.substring(8);
    var tabel = document.getElementById("pertype" + n);
    var rows = tabel.getElementsByTagName("tr");
    var maxN = 1;
    for (loop = 0; loop < rows.length; loop++) {
        if (rows[loop].id.substring(rows[loop].id.indexOf("_") + 1) * 1 > maxN) {
            maxN = rows[loop].id.substring(rows[loop].id.indexOf("_") + 1) * 1;
        }
    }
    var tr = document.createElement("tr");
    tr.id = "rowcond" + n + "_" + (maxN + 1);
    var td1 = document.createElement("td");
    td1.style = "width:80%";
    var textarea1 = document.createElement("textarea");
    textarea1.className = "intable";
    textarea1.id = "cond" + n + "_" + (maxN + 1);
    textarea1.name = "cond" + n + "_" + (maxN + 1);
    textarea1.rows = "3";
    td1.appendChild(textarea1);
    var td2 = document.createElement("td");
    td2.appendChild(document.createTextNode("До: "));
    var inpDate = document.createElement("input");
    inpDate.type = "date";
    inpDate.id = "datecond" + n + "_" + (maxN + 1);
    inpDate.name = "datecond" + n + "_" + (maxN + 1);
    var delImg = document.createElement("img");
    delImg.className = "btn";
    delImg.id = "delCond" + n + "_" + (maxN + 1);
    delImg.src = "img/delete.png";
    delImg.width = "16";
    delImg.alt = "Удалить";
    delImg.title = "Удалить";
    delImg.onclick = deleteCond;
    td2.appendChild(inpDate);
    td2.appendChild(delImg);

    tr.appendChild(td1);
    tr.appendChild(td2);
    tabel.appendChild(tr);
}

function savePerechen() {
    var div = document.getElementById("ipraForm");
    var body = "action=saveperechen&" + getRequestBody(div);
    requ("ipra2018spisok", "POST", body);
}

function validatePerechen() {
    document.getElementById("printPerechenBtn").disabled = "disabled";
    document.getElementById("savePerechenBtn").disabled = "disabled";
    var result = true;
    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        inp[loop].classList.remove("wrong");
    }
    var areas = document.getElementsByTagName("textarea");
    for (loop = 0; loop < areas.length; loop++) {
        if (areas[loop].value != "") {
            var n = areas[loop].id.substring(4);
            var date = document.getElementById("datecond" + n);
            if (date.value == "") {
                date.className = "wrong";
                result = false;
            }
        }
    }
    if (!result) {
        alert("Проверьте данные в полях, отмеченных КРАСНЫМ ЦВЕТОМ (проверяется заполнение, корректность и количество символов)");
        var wrong = document.getElementsByClassName("wrong");
        wrong[0].focus();
        document.getElementById("savePerechenBtn").disabled = 0;
        document.getElementById("printPerechenBtn").disabled = 0;
    } else if (result) {
        savePerechen();
    }
}

function clickPrintPerechen() {
    var printPerechenFlag = document.getElementById("printPerechenFlag");
    printPerechenFlag.value = "1";
    validatePerechen();
}

function printPerechen() {
    var ipraId = document.getElementById("ipraId");
    if (ipraId != null) {
        var url = "print?type=ipraperechen&ipra=" + ipraId.value;
        window.open(url, '_blank');
    }
}

function clickPol() {
    if (this.childNodes[0].checked) {
        var pol = document.getElementById("pol");
        pol.value = this.childNodes[0].value;
    }
}