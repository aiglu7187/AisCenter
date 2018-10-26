/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    var reg = document.getElementById("reg");
    reg.onchange = changeReg;

    var org = document.getElementById("org");
    if (org != null) {
        org.onchange = changeOrg;
    }
    var dolg = document.getElementById("dolg");
    if (dolg != null) {
        dolg.onchange = changeDolg;
    }

    var usltab = document.getElementById("usltab");
    if (usltab != null) {
        var trs = usltab.getElementsByTagName("tr");
        for (loop = 1; loop < trs.length; loop++) {
            trs[loop].onclick = selectPriyom;
        }
    }

    var uslsubjtab = document.getElementById("uslsubjtab");
    if (uslsubjtab != null) {
        var trs = uslsubjtab.getElementsByTagName("tr");
        for (loop = 1; loop < trs.length; loop++) {
            trs[loop].onclick = selectPriyom;
        }
    }

    var payusltab = document.getElementById("payusltab");
    if (payusltab != null) {
        var trs = payusltab.getElementsByTagName("tr");
        for (loop = 1; loop < trs.length; loop++) {
            trs[loop].onclick = selectPayUsl;
        }
    }

    var changeFamBtn = document.getElementById("changeFamBtn");
    changeFamBtn.onclick = changeFIO;
    var changeNameBtn = document.getElementById("changeNameBtn");
    changeNameBtn.onclick = changeFIO;
    var changePatrBtn = document.getElementById("changePatrBtn");
    changePatrBtn.onclick = changeFIO;

    var polR = document.getElementsByName("polR");
    for (loop = 0; loop < polR.length; loop++) {
        polR[loop].onclick = clickPol;
    }
}

function validate() {
    var btn = document.getElementById("saveBtn");
    btn.disabled = "disabled";
    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        inp[loop].classList.remove("wrong");
    }
    var fioLimit = 100;
    var drLimit = new Date();
    drLimit.setDate(1);
    drLimit.setMonth(0);
    drLimit.setFullYear(1970);
    drLimit.setHours(0);
    drLimit.setMinutes(0);
    drLimit.setSeconds(0);
    drLimit.setMilliseconds(0);

    var result = true;
    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        var id = inp[loop].id;
        var val = inp[loop].value;
        if ((id.substring(0, 5) == "clFam") || (id.substring(0, 5) == "clNam")) {
            if ((val == "") || (val.length > fioLimit)) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if ((id.substring(0, 6) == "clPatr")) {
            if (val.length > fioLimit) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id.substring(0, 4) == "clDr") {
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
        }
    }

    if (!result) {
        alert("Проверьте данные в полях, отмеченных КРАСНЫМ ЦВЕТОМ (проверяется заполнение, корректность и количество символов)");
        var wrong = document.getElementsByClassName("wrong");
        wrong[0].focus();
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
    } else if (result) {
        document.getElementById("formClient").submit();
    }
}

function changeReg() {
    var regId = document.getElementById("regId");
    regId.value = this.value;
}

function changeOrg() {
    var orgId = document.getElementById("orgId");
    orgId.value = this.value;
}

function changeDolg() {
    var dolgId = document.getElementById("dolgId");
    dolgId.value = this.value;
}

function delClient() {
    if (confirm("Вы уверены, что хотите удалить этого человека и всё, что с ним связано?")) {
        var idClient = document.getElementById("idClient").value;
        var kat = document.getElementById("kat").value;
        var url = "deleteclient?kat=" + kat + "&id=" + idClient;
        document.location.href = url;
    } else {
    }
}

function selectPriyom() {
    var row = this;
    var id = row.childNodes[1];
    var id = id.childNodes[0].nodeValue;
    var url = "priyomedit?&id=" + id;
    window.open(url, '_blank');
}

function histEdupl() {
    var dialHist = document.getElementById("histDialog");
    if (dialHist != null) {
        dialHist.showModal();
    }
}

function addEdupl() {
    var div = document.getElementById("divNewChOu");
    if (div != null) {
        div.appendChild(document.createTextNode("Район:"));
        var selReg = document.createElement("select");
        selReg.id = "selRegOu";
        selReg.onchange = regSelect;
        div.appendChild(selReg);
        div.appendChild(document.createElement("br"));
        div.appendChild(document.createTextNode("Образовательное учреждение:"));
        var selOu = document.createElement("select");
        selOu.id = "selOu";
        selOu.onchange = ouSelect;
        div.appendChild(selOu);
        var ouId = document.createElement("input");
        ouId.type = "hidden";
        ouId.id = "ouId";
        ouId.name = "ouId";
        div.appendChild(ouId);
        div.appendChild(document.createElement("br"));
        div.appendChild(document.createTextNode("Класс/группа:"));
        var selStage = document.createElement("select");
        selStage.id = "selStage";
        selStage.onchange = stageSelect;
        var optSt = document.createElement("option");
        optSt.value = 0;
        selStage.appendChild(optSt);
        div.appendChild(selStage);
        var stId = document.createElement("input");
        stId.type = "hidden";
        stId.id = "stageId";
        stId.name = "stageId";
        div.appendChild(stId);
        div.appendChild(document.createElement("br"));

        var idCl = document.getElementById("idClient");
        if (idCl != null) {
            var id = idCl.value;
            var url = "clientaction?action=ou&client=" + id;
            requ(url, 'GET');
        }
        var btn = document.getElementById("addEduplace");
        if (btn != null) {
            btn.remove();
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
        }
    }
}

function parseMessages(responseXML) {   // преобразование полученного xml
    if (responseXML == null) {
        return false;
    } else {
        // районы
        var regions = responseXML.getElementsByTagName("regions")[0];
        var stages = responseXML.getElementsByTagName("stages")[0];
        var regoo = responseXML.getElementsByTagName("regoo")[0];
        var oldFamOk = responseXML.getElementsByTagName("oldFamOk")[0];
        if (regions != null) {  // если список районов
            if (regions.childNodes.length > 0) {
                for (loop = 0; loop < regions.childNodes.length; loop++) {
                    var region = regions.childNodes[loop];
                    var rName = region.getElementsByTagName("name")[0];
                    var rId = region.getElementsByTagName("id")[0];
                    var rIs = region.getElementsByTagName("is")[0]
                    appendRegion(rId.childNodes[0].nodeValue, rName.childNodes[0].nodeValue, rIs.childNodes[0].nodeValue);
                }
            }
        }
        if (stages != null) {
            if (stages.childNodes.length > 0) {
                for (loop = 0; loop < stages.childNodes.length; loop++) {
                    var stage = stages.childNodes[loop];
                    var sName = stage.getElementsByTagName("name")[0];
                    var sId = stage.getElementsByTagName("id")[0];
                    appendStage(sId.childNodes[0].nodeValue, sName.childNodes[0].nodeValue);
                }
            }
        }
        if (regoo != null) {
            if (regoo.childNodes.length > 0) {
                clearOu();
                for (loop = 0; loop < regoo.childNodes.length; loop++) {
                    var oo = regoo.childNodes[loop];
                    var oName = oo.getElementsByTagName("name")[0];
                    var oId = oo.getElementsByTagName("id")[0];
                    appendOo(oId.childNodes[0].nodeValue, oName.childNodes[0].nodeValue);
                }
            }
        }
        if (oldFamOk != null) {
            onOkSaveOldFam();
        }
    }

}

function appendRegion(rId, rName, rIs) {
    var sel = document.getElementById("selRegOu");
    if (sel != null) {
        var opt = document.createElement("option");
        opt.value = rId;
        opt.appendChild(document.createTextNode(rName));
        if (rIs == "1") {
            opt.selected = "selected";
        }
        sel.appendChild(opt);
        if (rIs == "1") {
            sel.onchange();
        }
    }
}

function appendStage(sId, sName) {
    var sel = document.getElementById("selStage");
    if (sel != null) {
        var opt = document.createElement("option");
        opt.value = sId;
        opt.appendChild(document.createTextNode(sName));
        sel.appendChild(opt);
        sel.onchange();
    }
}

function regSelect() {
    var reg = this.value;
    var url = "clientaction?action=selreg&reg=" + reg;
    requ(url, "GET");
}

function appendOo(oId, oName) {
    var sel = document.getElementById("selOu");
    if (sel != null) {
        var opt = document.createElement("option");
        opt.value = oId;
        opt.appendChild(document.createTextNode(oName));
        sel.appendChild(opt);
        sel.onchange();
    }
}

function clearOu() {
    var sel = document.getElementById("selOu");
    if (sel != null) {
        sel.innerHTML = "";
    }
}

function ouSelect() {
    var id = this.value;
    var ouId = document.getElementById("ouId");
    if (ouId != null) {
        ouId.value = id;
    }
}

function stageSelect() {
    var id = this.value;
    var stageId = document.getElementById("stageId");
    if (stageId != null) {
        stageId.value = id;
    }
}

function histR() {
    var dialog = document.getElementById("histRegDialog");
    if (dialog != null) {
        dialog.showModal();
    }
}

function selectPayUsl() {
    var row = this;
    var id = row.childNodes[1];
    var id = id.childNodes[0].nodeValue;
    var url = "payaction?action=viewfinal&id=" + id;
    window.open(url, '_blank');
}

function changeFIO() {
    var title = "Внимание! В этой форме вносятся изменения в ФИО, только если была замена в ДОКУМЕНТАХ клиента (паспорте, свидетельстве о рождении)";
    var dialog = document.getElementById("changeFIODialog");
    if (dialog != null) {
        var label = "";
        var hid = document.createElement("input");
        hid.type = "hidden";
        hid.id = "changeType";
        if (this.id == "changeFamBtn") {
            label = "Новая фамилия";
            hid.value = "fam";
        } else if (this.id == "changeNameBtn") {
            label = "Новое имя";
            hid.value = "name";
        } else if (this.id == "changePatrBtn") {
            label = "Новое отчество";
            hid.value = "patr";
        }
        dialog.innerHTML = "";
        dialog.appendChild(hid);
        dialog.appendChild(document.createTextNode(title));
        dialog.appendChild(document.createElement("br"));
        dialog.appendChild(document.createElement("br"));
        dialog.appendChild(document.createTextNode(label + ": "));
        var inp = document.createElement("input");
        inp.type = "text";
        inp.id = "changeText";
        dialog.appendChild(inp);
        dialog.appendChild(document.createElement("br"));
        dialog.appendChild(document.createElement("br"));
        var saveBtn = document.createElement("input");
        saveBtn.type = "button";
        saveBtn.value = "Сохранить";
        saveBtn.onclick = saveChangeFIO;
        dialog.appendChild(saveBtn);
        var clsBtn = document.createElement("input");
        clsBtn.type = "button";
        clsBtn.value = "Отмена";
        clsBtn.onclick = closeChangeFIODialog;
        dialog.appendChild(clsBtn);
        dialog.showModal();
    }
}

function closeChangeFIODialog() {
    document.getElementById("changeFIODialog").close()
}

function saveChangeFIO() {
    var type = document.getElementById("changeType").value;
    var text = document.getElementById("changeText").value;
    var kat = document.getElementById("kat").value;
    var id = document.getElementById("idClient").value;
    if (text != "") {
        var url = "clientaction?action=changefio&changetype=" + type
                + "&changetext=" + text + "&kat=" + kat + "&id=" + id;
        requ(url, "GET");
    }
}

function onOkSaveOldFam() {
    document.location.reload();
}

function clickPol() {
    var pol = document.getElementById("pol");
    pol.value = this.value;
}
