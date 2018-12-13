/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function uslSelect() {   // услуга выбрана -> надо получить её параметры, 
    //а также список должностей, районов и сотрудников
    var uslValue = document.getElementById("selUsl").value;
    var uslId = document.getElementById("uslId");
    if (uslValue == "111") { // пустая
        clearAll();
    } else {
        clearAll();
        uslId.value = uslValue;
        appendDatePr();
        var url = "priyomaction?action=center&uslid=" + uslValue;    // формируем запрос на параметры услуги
        requ(url, "GET");
        appendDivClients();
        appendDivPlusClient();
        appendDivButton();
    }

}

function clearAll() { // очистка всей страницы
    // функция удаления
    Element.prototype.remove = function () {
        return this.parentNode.removeChild(this);
    };

    // очистка скрытых полей
    document.getElementById("uslId").value = "";
    document.getElementById("problem").value = "";
    document.getElementById("stat").value = "";
    document.getElementById("monit").value = "";
    document.getElementById("seans").value = "";
    document.getElementById("pmpk").value = "";
    document.getElementById("subj").value = "";

    var div = document.getElementById("datePr");
    div.innerHTML = "";
    div = document.getElementById("reg");
    div.innerHTML = "";
    div = document.getElementById("divSpecialists");
    div.innerHTML = "";
    div = document.getElementById("divPlusSotr");
    div.innerHTML = "";
    div = document.getElementById("divClients");
    div.innerHTML = "";
    div = document.getElementById("divPlusClient");
    div.innerHTML = "";
    div = document.getElementById("divSubjects");
    div.innerHTML = "";
    div = document.getElementById("divPlusSubj");
    div.innerHTML = "";
    div = document.getElementById("divProblem");
    div.innerHTML = "";
    div = document.getElementById("divPlusProblem");
    div.innerHTML = "";
    div = document.getElementById("divSeans");
    div.innerHTML = "";
    div = document.getElementById("divButton");
    div.innerHTML = "";
}

function validate() { // проверка данных на корректность
    var now = new Date();
    now.setHours(0);
    now.setMinutes(0);
    now.setSeconds(0);
    now.setMilliseconds(0);
    var priyomLimit = new Date();
    priyomLimit.setDate(1);
    priyomLimit.setMonth(0);
    priyomLimit.setFullYear(2010);
    priyomLimit.setHours(0);
    priyomLimit.setMinutes(0);
    priyomLimit.setSeconds(0);
    priyomLimit.setMilliseconds(0);
    var drLimit = new Date();
    drLimit.setDate(1);
    drLimit.setMonth(0);
    drLimit.setFullYear(1970);
    drLimit.setHours(0);
    drLimit.setMinutes(0);
    drLimit.setSeconds(0);
    drLimit.setMilliseconds(0);
    var fioLimit = 100;
    var npLimit = 20;

    var btn = document.getElementById("saveBtn");
    btn.disabled = "disabled";
    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        inp[loop].classList.remove("wrong");
    }
    var sel = document.getElementsByTagName("select");
    for (loop = 0; loop < sel.length; loop++) {
        sel[loop].classList.remove("wrong");
    }
    var divs = document.getElementsByTagName("div");
    for (loop = 0; loop < divs.length; loop++) {
        divs[loop].classList.remove("wrong");
    }
    var flag = false;
    for (loop = 0; loop < inp.length; loop++) {
        var id = inp[loop].id;
        if ((id.substring(0, 4) == "clId") || (id.substring(0, 5) == "clFam")) {
            if (inp[loop].value != "") {
                flag = true;
            }
        }
    }
    if (!flag) {
        alert("Должен быть указан хотя бы один клиент");
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
    }

    var result = true;
    if (flag) {
        var datePriyom = document.getElementById("datePriyom");
        if (datePriyom.value == "") {
            datePriyom.className = "wrong";
            result = false;
        }

        if (datePriyom.value.length > 10) {
            datePriyom.className = "wrong";
            result = false;
        }

        var datePriyomD = new Date(1 * datePriyom.value.substr(0, 4), 1 * datePriyom.value.substr(5, 2) - 1, 1 * datePriyom.value.substr(8));
        if (datePriyomD == null) {
            datePriyom.className = "wrong";
            result = false;
        } else {
            if (datePriyomD.getTime() > now.getTime()) {
                datePriyom.className = "wrong";
                result = false;
            } else if (datePriyomD.getTime() < priyomLimit.getTime()) {
                datePriyom.className = "wrong";
                result = false;
            }
        }

        for (loop = 0; loop < inp.length; loop++) {
            var id = inp[loop].id;
            var val = inp[loop].value;
            if ((id.substring(0, 5) == "clFam") || (id.substring(0, 5) == "clNam")
                    || (id.substring(0, 6) == "subFam") || (id.substring(0, 6) == "subNam")) {
                if ((val == "") || (val.length > fioLimit)) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            } else if ((id.substring(0, 6) == "clPatr") || (id.substring(0, 7) == "subPatr")) {
                if (val.length > fioLimit) {
                    inp[loop].className = "wrong";
                    result = false;
                }
            } else if ((id.substring(0, 4) == "clDr") || (id.substring(0, 5) == "subDr")) {
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
            } else if (id.substring(0, 3) == "pol") {
                if (val == "") {
                    var polR = document.getElementsByName("polR" + id.substring(3));
                    for (loop4 = 0; loop4 < polR.length; loop4++) {
                        polR[loop4].parentNode.parentNode.className = "wrong";
                    }
                    result = false;
                }
            } else if (id.substring(0, 6) == "subPol") {
                if (val == "0") {
                    var subPolR = document.getElementsByName("subPolR" + id.substring(6));
                    for (loop4 = 0; loop4 < subPolR.length; loop4++) {
                        subPolR[loop4].parentNode.parentNode.className = "wrong";
                    }
                    result = false;
                }
            } else if (id.substring(0, 4) == "prId") {
                if (val == "") {
                    var selPrtype = document.getElementById("selPrtype" + id.substring(4));
                    if (selPrtype != null) {
                        if (selPrtype.value == "0") {
                            selPrtype.className = "wrong";
                            result = false;
                        }
                    }
                }
            }
        }

        var pmpk = document.getElementById("pmpk");
        if (pmpk != null) {
            if (pmpk.value == "1") {
                for (loop = 0; loop < inp.length; loop++) {
                    var id = inp[loop].id;
                    var val = inp[loop].value;
                    if (id.substring(0, 5) == "nomPr") {
                        if ((val == "") || (val.length > npLimit)) {
                            inp[loop].className = "wrong";
                            result = false;
                        }
                    } else if (id.substring(0, 9) == "dopObsled") {
                        if (!inp[loop].checked) {
                            var op = document.getElementById("opId" + id.substring(9));
                            if (op == null) {
                                var selOptype = document.getElementById("selOptype" + id.substring(9));
                                if (selOptype != null) {
                                    if (selOptype.value == "0") {
                                        selOptype.className = "wrong";
                                        result = false;
                                    }
                                }
                            } else {
                                if (op.value == "0") {
                                    var selOptype = document.getElementById("selOptype" + id.substring(9));
                                    if (selOptype != null) {
                                        if (selOptype.value == "0") {
                                            selOptype.className = "wrong";
                                            result = false;
                                        }
                                    }
                                    var selOp = document.getElementById("selOp" + id.substring(9));
                                    if (selOp != null) {
                                        if (selOp.value == "0") {
                                            selOp.className = "wrong";
                                            result = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                for (loop = 0; loop < divs.length; loop++) {
                    if (divs[loop].id.substring(0, 10) == "divParents") {
                        var n = divs[loop].id.substring(10);
                        var stats = document.getElementById("divStat" + n).getElementsByTagName("input");
                        var ds = false;
                        for (loop2 = 0; loop2 < stats.length; loop2++) {
                            if ((stats[loop2].checked) && (stats[loop2].value == 8)) {  // дети-сироты
                                ds = true;
                            }
                        }

                        var clDr = document.getElementById("clDr" + n);
                        var age18 = false;
                        if (clDr != null) {
                            var dr = clDr.value;
                            var dat = datePriyom.value;
                            // возраст
                            var years = dat.substr(0, 4) - dr.substr(0, 4);
                            var datMonth = dat.substr(5, 2);
                            var drMonth = dr.substr(5, 2);
                            if (years > 0) {
                                if (datMonth < drMonth) {
                                    years--;
                                } else if ((datMonth == drMonth) && (dat.substr(8) < dr.substr(8))) {
                                    years--;
                                }
                            }
                            if (years >= 18) {
                                age18 = true;
                            }
                        }
                        if (!(ds || age18)) {
                            var parInp = divs[loop].getElementsByTagName("input");
                            var flag3 = false;
                            for (loop2 = 0; loop2 < parInp.length; loop2++) {
                                if ((parInp[loop2].id.substring(0, 13) == "pmpk1ParentId") || (parInp[loop2].id.substring(0, 13) == "pmpk2ParentId")) {
                                    flag3 = true;
                                } else if ((parInp[loop2].id.substring(0, 11) == "pmpkPar1Fam") || (parInp[loop2].id.substring(0, 11) == "pmpkPar2Fam")
                                        || (parInp[loop2].id.substring(0, 11) == "pmpkPar1Nam") || (parInp[loop2].id.substring(0, 11) == "pmpkPar2Nam")) {
                                    flag3 = true;
                                    var val = parInp[loop2].value;
                                    if (val == "") {
                                        parInp[loop2].className = "wrong";
                                        result = false;
                                    }
                                }
                            }
                            if (!flag3) {
                                divs[loop].className = "wrong";
                                result = false;
                            }
                        }

                    }
                }
            }
        }

        for (loop = 0; loop < divs.length; loop++) {
            var id = divs[loop].id;
            var chflag = false;
            if (id.substring(0, 10) == "divStatosn") {
                var checks = divs[loop].getElementsByTagName("input");
                for (loop2 = 0; loop2 < checks.length; loop2++) {
                    if (checks[loop2].checked) {
                        chflag = true;
                    }
                }
                if (!chflag) {
                    divs[loop].className = "wrong";
                    result = false;
                }
            } else if ((pmpk != null) && (id.substring(0, 10) == "divStatpod")) {
                if (pmpk.value == "1") {
                    var checks = divs[loop].getElementsByTagName("input");
                    if (checks.length > 0) {
                        for (loop2 = 0; loop2 < checks.length; loop2++) {
                            if (checks[loop2].checked) {
                                chflag = true;
                            }
                        }
                        if (!chflag) {
                            divs[loop].className = "wrong";
                            result = false;
                        }
                    }
                }
            }
        }

        for (loop = 0; loop < divs.length; loop++) {
            var id = divs[loop].id;
            var chflag = false;
            if (id.substring(0, 13) == "divSubstatosn") {
                var checks = divs[loop].getElementsByTagName("input");
                for (loop2 = 0; loop2 < checks.length; loop2++) {
                    if (checks[loop2].checked) {
                        chflag = true;
                    }
                }
                if (!chflag) {
                    divs[loop].className = "wrong";
                    result = false;
                }
            }
        }
    }

    if (!result) {
        alert("Проверьте данные в полях, отмеченных КРАСНЫМ ЦВЕТОМ \n(проверяется заполнение, корректность и количество символов)");
        var wrong = document.getElementsByClassName("wrong");
        wrong[0].focus();
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
    } else if (result & flag) {
        document.getElementById("formPriyom").submit();
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
        // параметры услуги
        var param = responseXML.getElementsByTagName("param")[0];
        // должности
        var dolgntype = responseXML.getElementsByTagName("dolgntype")[0];
        // сотрудники
        var sotruds = responseXML.getElementsByTagName("sotruds")[0];
        // основные статусы
        var statosns = responseXML.getElementsByTagName("statosns")[0];
        // социальные статусы
        var statsocs = responseXML.getElementsByTagName("statsocs")[0];
        // дополнительные статусы
        var statdops = responseXML.getElementsByTagName("statdops")[0];
        // подстатус
        var statpod = responseXML.getElementsByTagName("statpod")[0];
        // виды образовательных программ
        var optypes = responseXML.getElementsByTagName("optypes")[0];
        // рекомендации
        var rekomends = responseXML.getElementsByTagName("rekomends")[0];
        // типы проблем
        var problemTypes = responseXML.getElementsByTagName("problemTypes")[0];
        // проблемы
        var problems = responseXML.getElementsByTagName("problems")[0];
        // курс
        var priyomList = responseXML.getElementsByTagName("kurs")[0];
        // образовательные программы
        var ops = responseXML.getElementsByTagName("ops")[0];
        // варианты образовательных программ
        var vars = responseXML.getElementsByTagName("vars")[0];
        // районы проживания клиентов
        var regionsCl = responseXML.getElementsByTagName("regionsCl")[0];
        // организации и должности педагогов
        var orgdolg = responseXML.getElementsByTagName("orgdolg")[0];
        // занятия
        var lesson = responseXML.getElementsByTagName("lesson")[0];
        // семья
        var family = responseXML.getElementsByTagName("family")[0];
        // клиенты
        var clients = responseXML.getElementsByTagName("clients")[0];
        // законные представители (справочник)
        var parenttypes = responseXML.getElementsByTagName("parenttypes")[0];

        if (lesson != null) { // если услуга предполагает занятия
            isLesson();
        }

        if (regions != null) {  // если список районов
            appendDivReg();
            if (regions.childNodes.length > 0) {
                for (loop = 0; loop < regions.childNodes.length; loop++) {
                    var region = regions.childNodes[loop];
                    var rName = region.getElementsByTagName("rname")[0];
                    var rId = region.getElementsByTagName("id")[0];
                    appendRegion(rId.childNodes[0].nodeValue, rName.childNodes[0].nodeValue);
                }
            }
        }
        if (sotruds != null) {  // если список сотрудников
            var sotrud1 = sotruds.childNodes[0];
            var sN1 = sotrud1.getElementsByTagName("n")[0];
            clearSotr(sN1.childNodes[0].nodeValue);
            if (sotruds.childNodes.length > 0) {
                for (loop = 0; loop < sotruds.childNodes.length; loop++) {
                    var sotrud = sotruds.childNodes[loop];
                    var sN = sotrud.getElementsByTagName("n")[0];
                    var sId = sotrud.getElementsByTagName("id")[0];
                    var sFIO = sotrud.getElementsByTagName("fio")[0];
                    var us = sotrud.getElementsByTagName("user")[0];
                    appendSotrud(sN.childNodes[0].nodeValue, sId.childNodes[0].nodeValue, sFIO.childNodes[0].nodeValue, us.childNodes[0].nodeValue);
                }
            }
        } else if (family != null) {
            clearFamilyDialog();
            if (family.childNodes.length > 0) {
                var head = family.childNodes[0];
                appendFamilyHead(head);
                for (loop = 1; loop < family.childNodes.length; loop++) {
                    var member = family.childNodes[loop];
                    appendFamilyBody(member, selectClient);
                }
            }
            family = null;
        } else if (clients != null) {
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
        } else if (statosns != null) {
            if (statosns.childNodes.length > 0) {
                for (loop = 0; loop < statosns.childNodes.length; loop++) {
                    var statosn = statosns.childNodes[loop];
                    var statosnN = statosn.getElementsByTagName("n")[0];
                    var statosnType = statosn.getElementsByTagName("type")[0];
                    var statosnId = statosn.getElementsByTagName("id")[0];
                    var statosnName = statosn.getElementsByTagName("name")[0];
                    var statosnNorma = statosn.getElementsByTagName("norma")[0];
                    var statosnCheck = statosn.getElementsByTagName("checked")[0];
                    var statosnEnabled = statosn.getElementsByTagName("enabled")[0];
                    appendStatOsn(statosnN.childNodes[0].nodeValue, statosnType.childNodes[0].nodeValue,
                            statosnId.childNodes[0].nodeValue, statosnName.childNodes[0].nodeValue,
                            statosnNorma.childNodes[0].nodeValue, statosnCheck.childNodes[0].nodeValue,
                            statosnEnabled.childNodes[0].nodeValue);
                }
            }
        } else if (statdops != null) {
            if (statdops.childNodes.length > 0) {
                for (loop = 0; loop < statdops.childNodes.length; loop++) {
                    var statdop = statdops.childNodes[loop];
                    var statdopN = statdop.getElementsByTagName("n")[0];
                    var statdopType = statdop.getElementsByTagName("type")[0];
                    var statdopId = statdop.getElementsByTagName("id")[0];
                    var statdopName = statdop.getElementsByTagName("name")[0];
                    var statdopCheck = statdop.getElementsByTagName("checked")[0];
                    var statdopEnabled = statdop.getElementsByTagName("enabled")[0];
                    appendStatDop(statdopN.childNodes[0].nodeValue, statdopType.childNodes[0].nodeValue,
                            statdopId.childNodes[0].nodeValue, statdopName.childNodes[0].nodeValue,
                            statdopCheck.childNodes[0].nodeValue, statdopEnabled.childNodes[0].nodeValue);
                }
            }
        } else if (statsocs != null) {
            if (statsocs.childNodes.length > 0) {
                for (loop = 0; loop < statsocs.childNodes.length; loop++) {
                    var statsoc = statsocs.childNodes[loop];
                    var statsocN = statsoc.getElementsByTagName("n")[0];
                    var statsocType = statsoc.getElementsByTagName("type")[0];
                    var statsocId = statsoc.getElementsByTagName("id")[0];
                    var statsocName = statsoc.getElementsByTagName("name")[0];
                    var statsocCheck = statsoc.getElementsByTagName("checked")[0];
                    var statsocEnabled = statsoc.getElementsByTagName("enabled")[0];
                    appendStatSoc(statsocN.childNodes[0].nodeValue, statsocType.childNodes[0].nodeValue,
                            statsocId.childNodes[0].nodeValue, statsocName.childNodes[0].nodeValue,
                            statsocCheck.childNodes[0].nodeValue, statsocEnabled.childNodes[0].nodeValue);
                }
            }
        } else if (statpod != null) {
            if (statpod.childNodes.length > 0) {
                var stat1 = statpod.childNodes[0];
                var statDop1 = stat1.getElementsByTagName("dop")[0];
                var statN1 = stat1.getElementsByTagName("n")[0];
                var statType1 = stat1.getElementsByTagName("type")[0];
                clearStatPod(statDop1.childNodes[0].nodeValue, statN1.childNodes[0].nodeValue,
                        statType1.childNodes[0].nodeValue);
                for (loop = 0; loop < statpod.childNodes.length; loop++) {
                    var stat = statpod.childNodes[loop];
                    var statN = stat.getElementsByTagName("n")[0];
                    var statType = stat.getElementsByTagName("type")[0];
                    var statId = stat.getElementsByTagName("id")[0];
                    var statName = stat.getElementsByTagName("name")[0];
                    var statCheck = stat.getElementsByTagName("checked")[0];
                    var statEnabled = stat.getElementsByTagName("enabled")[0];
                    appendStatPod(statN.childNodes[0].nodeValue, statType.childNodes[0].nodeValue,
                            statDop1.childNodes[0].nodeValue, statId.childNodes[0].nodeValue,
                            statName.childNodes[0].nodeValue, statCheck.childNodes[0].nodeValue,
                            statEnabled.childNodes[0].nodeValue);
                }
                var pmpk = document.getElementById("pmpk");
                if (pmpk.value != 1) {
                    statPodTest(statDop1.childNodes[0].nodeValue, statN1.childNodes[0].nodeValue,
                            statType1.childNodes[0].nodeValue);
                }
            }
        } else if (optypes != null) {
            if (optypes.childNodes.length > 0) {
                var optype1 = optypes.childNodes[0];
                var optypeN1 = optype1.getElementsByTagName("n")[0];
                clearSelOpTypes(optypeN1.childNodes[0].nodeValue);
                for (loop = 0; loop < optypes.childNodes.length; loop++) {
                    var optype = optypes.childNodes[loop];
                    var optypeN = optype.getElementsByTagName("n")[0];
                    var optypeName = optype.getElementsByTagName("name")[0];
                    var optypeId = optype.getElementsByTagName("id")[0];
                    appendOpType(optypeN.childNodes[0].nodeValue,
                            optypeName.childNodes[0].nodeValue, optypeId.childNodes[0].nodeValue);
                }
            }
        } else if (ops != null) {
            if (ops.childNodes.length > 0) {
                var op1 = ops.childNodes[0];
                var opN1 = op1.getElementsByTagName("n")[0];
                clearSelOp(opN1.childNodes[0].nodeValue);
                delSelVar(opN1.childNodes[0].nodeValue);
                for (loop = 0; loop < ops.childNodes.length; loop++) {
                    var op = ops.childNodes[loop];
                    var opN = op.getElementsByTagName("n")[0];
                    var opName = op.getElementsByTagName("name")[0];
                    var opId = op.getElementsByTagName("id")[0];
                    appendOp(opN.childNodes[0].nodeValue,
                            opName.childNodes[0].nodeValue, opId.childNodes[0].nodeValue);
                }
            }
        } else if (vars != null) {
            if (vars.childNodes.length > 0) {
                var var1 = vars.childNodes[0];
                var varN1 = var1.getElementsByTagName("n")[0];
                addSelVar(varN1.childNodes[0].nodeValue);
                for (loop = 0; loop < vars.childNodes.length; loop++) {
                    var variant = vars.childNodes[loop];
                    var varN = variant.getElementsByTagName("n")[0];
                    var varName = variant.getElementsByTagName("name")[0];
                    var varId = variant.getElementsByTagName("id")[0];
                    appendVar(varN.childNodes[0].nodeValue,
                            varName.childNodes[0].nodeValue, varId.childNodes[0].nodeValue);
                }
            }
        } else if (rekomends != null) {
            if (rekomends.childNodes.length > 0) {
                var rek1 = rekomends.childNodes[0];
                var rekN1 = rek1.getElementsByTagName("n")[0];
                clearRekomend(rekN1.childNodes[0].nodeValue);
                for (loop = 0; loop < rekomends.childNodes.length; loop++) {
                    var rekomend = rekomends.childNodes[loop];
                    var rekN = rekomend.getElementsByTagName("n")[0];
                    var rekId = rekomend.getElementsByTagName("id")[0];
                    var rekName = rekomend.getElementsByTagName("name")[0];
                    appendRekomend(rekN.childNodes[0].nodeValue, rekId.childNodes[0].nodeValue,
                            rekName.childNodes[0].nodeValue);
                }
            }
        } else if (problemTypes != null) {
            if (problemTypes.childNodes.length > 0) {
                var problemType1 = problemTypes.childNodes[0];
                var prTN1 = problemType1.getElementsByTagName("n")[0];
                clearProblemType(prTN1.childNodes[0].nodeValue);
                for (loop = 0; loop < problemTypes.childNodes.length; loop++) {
                    var problemType = problemTypes.childNodes[loop];
                    var prTN = problemType.getElementsByTagName("n")[0];
                    var prTName = problemType.getElementsByTagName("name")[0];
                    var prTId = problemType.getElementsByTagName("id")[0];
                    appendProblemType(prTN.childNodes[0].nodeValue,
                            prTName.childNodes[0].nodeValue, prTId.childNodes[0].nodeValue);
                }
            }
        } else if (problems != null) {
            if (problems.childNodes.length > 0) {
                for (loop = 0; loop < problems.childNodes.length; loop++) {
                    var problem = problems.childNodes[loop];
                    var prN = problem.getElementsByTagName("n")[0];
                    var prName = problem.getElementsByTagName("name")[0];
                    var prId = problem.getElementsByTagName("id")[0];
                    appendProblem(prN.childNodes[0].nodeValue,
                            prName.childNodes[0].nodeValue, prId.childNodes[0].nodeValue);
                }
            }
        } else if (priyomList != null) {
            clearKursPr();
            if (priyomList.childNodes.length > 0) {
                for (loop = 0; loop < priyomList.childNodes.length; loop++) {
                    var priyom = priyomList.childNodes[loop];
                    var prDate = priyom.getElementsByTagName("datepr")[0];
                    appendKursPr(prDate.childNodes[0].nodeValue);
                }
            }
        } else if (orgdolg != null) {
            if (orgdolg.childNodes.length > 0) {
                var orgs = orgdolg.getElementsByTagName("orgs")[0];
                if (orgs.childNodes.length > 0) {
                    for (loop = 0; loop < orgs.childNodes.length; loop++) {
                        var org = orgs.childNodes[loop];
                        var orgN = org.getElementsByTagName("n")[0];
                        var orgType = org.getElementsByTagName("type")[0];
                        var orgId = org.getElementsByTagName("id")[0];
                        var orgName = org.getElementsByTagName("name")[0];
                        appendOrgN(orgN.childNodes[0].nodeValue, orgType.childNodes[0].nodeValue,
                                orgId.childNodes[0].nodeValue, orgName.childNodes[0].nodeValue);
                    }
                }
                var peddolgns = orgdolg.getElementsByTagName("peddolgns")[0];
                if (peddolgns.childNodes.length > 0) {
                    for (loop = 0; loop < peddolgns.childNodes.length; loop++) {
                        var dolg = peddolgns.childNodes[loop];
                        var dolgN = dolg.getElementsByTagName("n")[0];
                        var dolgType = dolg.getElementsByTagName("type")[0];
                        var dolgId = dolg.getElementsByTagName("id")[0];
                        var dolgName = dolg.getElementsByTagName("name")[0];
                        appendDolgN(dolgN.childNodes[0].nodeValue, dolgType.childNodes[0].nodeValue,
                                dolgId.childNodes[0].nodeValue, dolgName.childNodes[0].nodeValue);
                    }

                }
            }
        } else if (regionsCl != null) {  // если список районов  
            if (regionsCl.childNodes.length > 0) {
                for (loop = 0; loop < regionsCl.childNodes.length; loop++) {
                    var region = regionsCl.childNodes[loop];
                    var rN = region.getElementsByTagName("n")[0];
                    var rType = region.getElementsByTagName("type")[0];
                    var rName = region.getElementsByTagName("rname")[0];
                    var rId = region.getElementsByTagName("id")[0];
                    appendRegionCl(rId.childNodes[0].nodeValue,
                            rName.childNodes[0].nodeValue, rN.childNodes[0].nodeValue,
                            rType.childNodes[0].nodeValue);
                }
            }

        } else if (parenttypes != null) {  // если законные представители  
            if (parenttypes.childNodes.length > 0) {
                for (loop = 0; loop < parenttypes.childNodes.length; loop++) {
                    var parenttype = parenttypes.childNodes[loop];
                    var ptN = parenttype.getElementsByTagName("n")[0];
                    var ptType = parenttype.getElementsByTagName("type")[0];
                    var ptName = parenttype.getElementsByTagName("name")[0];
                    var ptId = parenttype.getElementsByTagName("id")[0];
                    appendParentType(ptId.childNodes[0].nodeValue,
                            ptName.childNodes[0].nodeValue, ptN.childNodes[0].nodeValue,
                            ptType.childNodes[0].nodeValue);
                }
            }

        }

        if (param != null) { // если параметры
            if (param.childNodes.length > 0) {
                var problem = param.getElementsByTagName("problem")[0];
                var stat = param.getElementsByTagName("stat")[0];
                var monit = param.getElementsByTagName("monit")[0];
                var seans = param.getElementsByTagName("seans")[0];
                var pmpk = param.getElementsByTagName("pmpk")[0];
                var subj = param.getElementsByTagName("subj")[0];
                appendParam(problem.childNodes[0].nodeValue, stat.childNodes[0].nodeValue,
                        monit.childNodes[0].nodeValue, seans.childNodes[0].nodeValue,
                        pmpk.childNodes[0].nodeValue, subj.childNodes[0].nodeValue);
            }
        }
        if (dolgntype != null) { // если должности
            appendDivSpec();
            if (dolgntype.childNodes.length > 0) {
                for (loop = 0; loop < dolgntype.childNodes.length; loop++) {
                    var dolgn = dolgntype.childNodes[loop];
                    var dId = dolgn.getElementsByTagName("id")[0];
                    var dName = dolgn.getElementsByTagName("name")[0];
                    var us = dolgn.getElementsByTagName("user")[0];
                    appendDolgn(1, dId.childNodes[0].nodeValue, dName.childNodes[0].nodeValue,
                            us.childNodes[0].nodeValue);
                }
                document.getElementById("selDolgn1").onchange();
            }
        }
    }
}

function appendDatePr() {    // div с датой приёма
    var datepr = document.getElementById("datePr");
    if (datepr != null) {
        var b = document.createElement("strong");
        b.appendChild(document.createTextNode("Дата проведения:"));
        datepr.appendChild(b);
        var inp = document.createElement("input");
        inp.type = "date";
        inp.name = "datePriyom";
        inp.id = "datePriyom";
        inp.onkeyup = actDatePr;
        datepr.appendChild(inp);
    }
}

function appendDivReg() {    // div с районами
    var reg = document.getElementById("reg");
    if (reg != null) {
        var b = document.createElement("strong");
        b.appendChild(document.createTextNode("Место проведения:"));
        reg.appendChild(b);
        var sel = document.createElement("select");
        sel.id = "selReg";
        sel.onchange = regSelect;
        reg.appendChild(sel);
        var inp = document.createElement("input");
        inp.type = "hidden";
        inp.id = "regId";
        inp.name = "regId";
        inp.value = "";
        reg.appendChild(inp);
    }
}

function appendRegion(rId, rName) { // формируется список для выбора района
    var sel = document.getElementById("selReg");
    if (sel != null) {
        var opt = document.createElement("option");
        opt.value = rId;
        opt.appendChild(document.createTextNode(rName));
        sel.appendChild(opt);
        sel.onchange();
    }
}

function regSelect() {   // реакция на выбор района
    var idR = this.value;
    document.getElementById("regId").value = idR;
    var subj = document.getElementById("subj").value;
    if ((subj == 1) && (idR != 1)) {
        var div = document.getElementById("divSubjects");
        div.innerHTML = "";
        var divPl = document.getElementById("divPlusSubj");
        divPl.innerHTML = "";
    } else if ((subj == 1) && (idR == 1)) {
        var divs = document.getElementById("divSubjects").getElementsByTagName("div");
        if (divs.length == 0) {
            appendDivSubj();
            appendDivPlusSubj();
        }
    }
}

function appendParam(problem, stat, monit, seans, pmpk, subj) {    // параметры услуги
    document.getElementById("problem").value = problem;
    document.getElementById("stat").value = stat;
    document.getElementById("monit").value = monit;
    document.getElementById("seans").value = seans;
    document.getElementById("pmpk").value = pmpk;
    document.getElementById("subj").value = subj;
    if (problem == 1) {
        appendDivProblem();
        appendDivPlusProblem();
    }
    if (seans == 1) {
        appendDivSeans();
    }
    if (subj == 1) {
        appendDivSubj();
        appendDivPlusSubj();
    }
}

function appendDivProblem() {
    var div = document.getElementById("divProblem");
    if (div != null) {
        div.appendChild(document.createTextNode("Выявленные проблемы:"));
        var problem = document.createElement("div");
        problem.id = "problem1";
        var selType = document.createElement("select");
        selType.id = "selPrtype1";
        selType.onchange = changeProblemType;
        var selPr = document.createElement("select");
        selPr.id = "selPr1";
        selPr.onchange = changeProblem;
        problem.appendChild(selType);
        problem.appendChild(selPr);
        var inp = document.createElement("input");
        inp.type = "hidden";
        inp.id = "prId1";
        inp.name = "prId1";
        problem.appendChild(inp);
        var img = document.createElement("img");
        img.className = "btn";
        img.id = "delProblem1";
        img.src = "img/delete.png";
        img.width = "20";
        img.alt = "Удалить";
        img.title = "Удалить";
        img.onclick = delProblem;
        problem.appendChild(img);
        div.appendChild(problem);
        var url = "priyomaction?action=problemtype&n=" + 1; // для заполнения списка районов проживания
        requ(url, "GET");
    }
}

function appendDivPlusProblem() {
    var div = document.getElementById("divPlusProblem");
    if (div != null) {
        var img = document.createElement("img");
        img.className = "btn";
        img.id = "plusProbl";
        img.src = "img/plus.png";
        img.width = "20";
        img.alt = "Добавить проблему";
        img.title = "Добавить проблему";
        img.onclick = addProblem;
        div.appendChild(img);
    }
}

function delProblem() {
    var divs = document.getElementsByTagName("div");
    var k = 0;
    for (loop = 0; loop < divs.length; loop++) {
        if (divs[loop].id.substring(0, 7) == "problem") {
            k++;
        }
    }
    if (k > 1) {
        var n = this.id.substring(10);
        var problem = document.getElementById("problem" + n);
        if (problem != null) {
            problem.remove();
        }
    }
}

function addProblem() {
    var div = document.getElementById("divProblem");
    var divs = document.getElementsByTagName("div");
    var k = 0;
    for (loop = 0; loop < divs.length; loop++) {
        if (divs[loop].id.substring(0, 7) == "problem") {
            if (k < divs[loop].id.substring(7)) {
                k = divs[loop].id.substring(7);
            }
        }
    }
    if (k > 0) {
        var n = Number(k) + 1;
        var problem = document.createElement("div");
        problem.id = "problem" + n;
        var selType = document.createElement("select");
        selType.id = "selPrtype" + n;
        selType.onchange = changeProblemType;
        var selPr = document.createElement("select");
        selPr.id = "selPr" + n;
        selPr.onchange = changeProblem;
        problem.appendChild(selType);
        problem.appendChild(selPr);
        var inp = document.createElement("input");
        inp.type = "hidden";
        inp.id = "prId" + n;
        inp.name = "prId" + n;
        problem.appendChild(inp);
        var img = document.createElement("img");
        img.className = "btn";
        img.id = "delProblem" + n;
        img.src = "img/delete.png";
        img.width = "20";
        img.alt = "Удалить";
        img.title = "Удалить";
        img.onclick = delProblem;
        problem.appendChild(img);
        div.appendChild(problem);
        var url = "priyomaction?action=problemtype&n=" + n; // для заполнения списка районов проживания
        requ(url, "GET");
    }
}

function appendDivSeans() {
    var div = document.getElementById("divSeans");
    if (div != null) {
        var lbl = document.createElement("label");
        var chb = document.createElement("input");
        chb.type = "checkbox";
        chb.id = "chbSeans";
        chb.name = "chbSeans";
        chb.onchange = checkSeans;
        lbl.appendChild(chb);
        lbl.appendChild(document.createTextNode("Курс занятий"));
        div.appendChild(lbl);
    }
}

function appendDivSubj() {
    var div = document.getElementById("divSubjects");
    if (div != null) {
        div.innerHTML = "";
        var b = document.createElement("strong");
        b.appendChild(document.createTextNode("Субъекты консультирования:"));
        div.appendChild(b);
        var sub = document.createElement("div");
        sub.className = "clients-1";
        sub.id = "sub1";
        div.appendChild(sub);
        var subKat = document.createElement("input");
        subKat.type = "hidden";
        subKat.id = "subKat1";
        subKat.name = "subKat1";
        subKat.value = "children";
        sub.appendChild(subKat);
        var img = document.createElement("img");
        img.className = "btn";
        img.id = "editSub1";
        img.src = "img/edit.png";
        img.width = "20";
        img.alt = "Редактировать";
        img.title = "Редактировать";
        img.onclick = searchClient;
        sub.appendChild(img);
        div.appendChild(sub);
    }
}

function appendDivPlusSubj() {
    var div = document.getElementById("divPlusSubj");
    if (div != null) {
        var img = document.createElement("img");
        img.className = "btn";
        img.id = "plusSub";
        img.src = "img/plus.png";
        img.width = "20";
        img.alt = "Добавить субъект";
        img.title = "Добавить субъект";
        img.onclick = addSubj;
        div.appendChild(img);
    }
}

function appendDivSpec() {   // div со специалистами
    var specialists = document.getElementById("divSpecialists");
    if (specialists != null) {
        var b = document.createElement("strong");
        b.appendChild(document.createTextNode("Специалисты:"));
        specialists.appendChild(b);
        specialists.appendChild(document.createElement("br"));
        var spec = document.createElement("div");
        spec.id = "spec1";
        var selDolgn = document.createElement("select");
        selDolgn.id = "selDolgn1";
        selDolgn.onchange = dolgnSelect;
        spec.appendChild(selDolgn);
        var inpDolgn = document.createElement("input");
        inpDolgn.type = "hidden";
        inpDolgn.id = "dolgnId1";
        inpDolgn.name = "dolgnId1";
        inpDolgn.value = "";
        spec.appendChild(inpDolgn);
        var selSotr = document.createElement("select");
        selSotr.id = "selSotr1";
        selSotr.onchange = sotrSelect;
        spec.appendChild(selSotr);
        var inpSotr = document.createElement("input");
        inpSotr.type = "hidden";
        inpSotr.id = "sotrId1";
        inpSotr.name = "sotrId1";
        inpSotr.value = "";
        spec.appendChild(inpSotr);
        var img = document.createElement("img");
        img.className = "btn";
        img.id = "delSotr1";
        img.src = "img/delete.png";
        img.width = "16";
        img.alt = "Удалить";
        img.title = "Удалить";
        img.onclick = delSotr;
        spec.appendChild(img);
        specialists.appendChild(spec);
    }
    appendPlusSotr();
}

function dolgnSelect() { // реакция на выбор должности
    var idD = this.value;
    var n = this.id.substring(8);
    var inp = document.getElementById("dolgnId" + n);
    inp.value = idD;
    // запрос на список сотрудников
    var url = "priyomaction?action=sotr&id=" + idD + "&n=" + n;
    requ(url, "GET");
}

function sotrSelect() {    // реакция на выбор сотрудника
    var inp = document.getElementById("sotrId" + this.id.substring(7));
    inp.value = this.value;
}

function appendDolgn(dN, dId, dName, us) {    // добавление в список должностей
    var sel = document.getElementById("selDolgn" + dN);
    var opt = document.createElement("option");
    opt.value = dId;
    opt.appendChild(document.createTextNode(dName));
    if (us == 1) {
        opt.selected = "selected";
    }
    if (sel != null) {
        sel.appendChild(opt);
    }
}

function  clearSotr(n) { // очистка списка сотрудников
    var sel = document.getElementById("selSotr" + n);
    if (sel != null) {
        sel.innerHTML = "";
    }
}

function appendSotrud(sN, sId, sFIO, us) {   // добавление в список сотрудников
    var sel = document.getElementById("selSotr" + sN);
    var opt = document.createElement("option");
    opt.value = sId;
    opt.appendChild(document.createTextNode(sFIO));
    if (us == 1) {
        opt.selected = "selected";
    }
    if (sel != null) {
        sel.appendChild(opt);
        sel.onchange();
    }
}

function delSotr() { // удаление сотрудника из списка
    var divs = document.getElementsByTagName("div");
    var k = 0;
    for (loop = 0; loop < divs.length; loop++) {
        if (divs[loop].id.substring(0, 4) == "spec") {
            k++;
        }
    }
    if (k > 1) {
        var n = this.id.substring(7);
        var spec = document.getElementById("spec" + n);
        if (spec != null) {
            spec.remove();
        }
    }
}

function appendPlusSotr() {  // добавление плюсика для специалистов
    var div = document.getElementById("divPlusSotr");
    if (div != null) {
        var img = document.createElement("img");
        img.className = "btn";
        img.id = "plusSotr";
        img.src = "img/plus.png";
        img.width = "16";
        img.alt = "Добавить";
        img.title = "Добавить";
        img.onclick = addSotr;
        div.appendChild(img);
        div.appendChild(document.createElement("br"));
        div.appendChild(document.createElement("br"));
    }
}

function addSotr() { // добавление сотрудника  
    var specialists = document.getElementById("divSpecialists");
    var divs = document.getElementsByTagName("div");
    var k = 0;
    for (loop = 0; loop < divs.length; loop++) {
        if (divs[loop].id.substring(0, 4) == "spec") {
            if (k < divs[loop].id.substring(4)) {
                k = divs[loop].id.substring(4);
            }
        }
    }
    if (k > 0) {
        var sotr = document.createElement("div");
        var n = Number(k) + 1;
        sotr.id = "spec" + n;
        var selDolgn = document.getElementById("selDolgn" + k).cloneNode(true);
        selDolgn.id = "selDolgn" + n;
        selDolgn.onchange = dolgnSelect;
        var dolgnId = document.getElementById("dolgnId" + k).cloneNode(true);
        dolgnId.id = "dolgnId" + n;
        dolgnId.name = "dolgnId" + n;
        var selSotr = document.getElementById("selSotr" + k).cloneNode(true);
        selSotr.id = "selSotr" + n;
        selSotr.onchange = sotrSelect;
        var sotrId = document.getElementById("sotrId" + k).cloneNode(true);
        sotrId.id = "sotrId" + n;
        sotrId.name = "sotrId" + n;
        var sotrDel = document.getElementById("delSotr" + k).cloneNode(true);
        sotrDel.id = "delSotr" + n;
        sotrDel.onclick = delSotr;
        sotr.appendChild(selDolgn);
        sotr.appendChild(dolgnId);
        sotr.appendChild(selSotr);
        sotr.appendChild(sotrId);
        sotr.appendChild(sotrDel);
        if (specialists != null) {
            specialists.appendChild(sotr);
            selDolgn.onchange();
        }
    }
}

function appendDivClients() {
    var div = document.getElementById("divClients");
    if (div != null) {
        div.innerHTML = "";
        var b = document.createElement("strong");
        b.appendChild(document.createTextNode("Клиенты:"));
        div.appendChild(b);
        var cl = document.createElement("div");
        cl.className = "clients-1";
        cl.id = "cl1";
        var clId = document.createElement("input");
        clId.type = "hidden";
        clId.id = "clId1";
        clId.name = "clId1";
        cl.appendChild(clId);
        var clKat = document.createElement("input");
        clKat.type = "hidden";
        clKat.id = "clKat1";
        clKat.name = "clKat1";
        cl.appendChild(clKat);
        var img = document.createElement("img");
        img.className = "btn";
        img.id = "editCl1";
        img.src = "img/edit.png";
        img.width = "20";
        img.alt = "Редактировать";
        img.title = "Редактировать";
        img.onclick = searchClient;
        cl.appendChild(img);
        div.appendChild(cl);
    }
}

function appendDivPlusClient() {
    var div = document.getElementById("divPlusClient");
    if (div != null) {
        var img = document.createElement("img");
        img.className = "btn";
        img.id = "plusCl";
        img.src = "img/plus.png";
        img.width = "20";
        img.alt = "Добавить клиента";
        img.title = "Добавить клиента";
        img.onclick = addClient;
        div.appendChild(img);
    }
}

function appendDivButton() {
    var div = document.getElementById("divButton");
    if (div != null) {
        var br = document.createElement("br");
        div.appendChild(br);
        var btn = document.createElement("input");
        btn.className = "btn";
        btn.type = "button";
        btn.name = "saveBtn";
        btn.id = "saveBtn";
        btn.value = "Сохранить";
        btn.onclick = validate;
        div.appendChild(btn);
    }
}

function checkSeans() {
    var chb = document.getElementById("chbSeans");
    if ((chb != null) && (chb.checked)) {
        appendKurs();
    } else if ((chb != null) && (!chb.checked)) {
        clearKurs();
    }
}

function searchClient() {
    var dialog = document.getElementById("searchClDialog");
    dialog.showModal();
    var type = document.getElementById("type");
    var n;
    if (this.parentNode.id.substring(0, 2) == "cl") {
        n = this.parentNode.id.substring(2);
        type.value = "cl";
        var kat = document.getElementById("clKat" + n).value;
        if (kat == "") {
            kat = "children";
        }
        var dialogKat = document.getElementById("selKat");
        dialogKat.disabled = 0;
        var opts = dialogKat.getElementsByTagName("option");
        for (loop = 0; loop < opts.length; loop++) {
            if (opts[loop].value == kat) {
                opts[loop].selected = "selected";
            }
        }
    } else if (this.parentNode.id.substring(0, 3) == "sub") {
        n = this.parentNode.id.substring(3);
        type.value = "sub";
        var kat = "children";
        var dialogKat = document.getElementById("selKat");
        var opts = dialogKat.getElementsByTagName("option");
        for (loop = 0; loop < opts.length; loop++) {
            if (opts[loop].value == kat) {
                opts[loop].selected = "selected";
            }
        }
        dialogKat.disabled = "disabled";
    }
    var nomPp = document.getElementById("nomClPp");
    nomPp.value = n;
    var fam = document.getElementById("fam");
    fam.value = "";
    var nam = document.getElementById("nam");
    nam.value = "";
    var patr = document.getElementById("patr");
    patr.value = "";
    search();
    findFamily();
}

function cancelDialog() {
    var dialog = document.getElementById("searchClDialog");
    dialog.close();
}

function search() {
    var kat = document.getElementById("selKat").value;
    var fam = document.getElementById("fam").value;
    var nam = document.getElementById("nam").value;
    var patr = document.getElementById("patr").value;
    var url = "search?type=client&kat=" + kat + "&fam=" + fam + "&nam=" + nam + "&patr=" + patr;
    requ(url, "GET");
}

function changeKat() {
    search();
    findFamily();
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

function selectClient() {
    var dialog = document.getElementById("searchClDialog");
    dialog.close();
    var n = document.getElementById("nomClPp").value;
    var type = document.getElementById("type").value;
    var uslId = document.getElementById("uslId").value;

    if (type.substring(0, 10) == "pmpkParent") {
        var npar = type.substring(10);
        var divParent = document.getElementById("div" + npar + "Parent" + n);
        divParent.innerHTML = "";
        divParent.appendChild(document.createTextNode(npar + ". "));
        var editImg = document.createElement("img");
        editImg.className = "btn";
        editImg.id = "edit" + npar + "Parent" + n;
        editImg.src = "img/edit.png";
        editImg.width = "15";
        editImg.alt = "Редактировать";
        editImg.title = "Редактировать";
        editImg.onclick = searchPmpkParent;
        divParent.appendChild(editImg);
        var delImg = document.createElement("img");
        delImg.className = "btn";
        delImg.id = "del" + npar + "Parent" + n;
        delImg.src = "img/delete.png";
        delImg.width = "15";
        delImg.alt = "Удалить";
        delImg.title = "Удалить";
        delImg.onclick = deleteParent;
        divParent.appendChild(delImg);
        divParent.appendChild(document.createElement("br"));
        var parentTypeSel = document.createElement("select");
        parentTypeSel.id = "parentTypeSel" + npar + "PmpkPar" + n;
        parentTypeSel.name = "parentTypeSel" + npar + "PmpkPar" + n;
        parentTypeSel.onchange = changeParentType;
        divParent.appendChild(parentTypeSel);
        var parentTypeId = document.createElement("input");
        parentTypeId.type = "hidden";
        parentTypeId.id = "parentTypeId" + npar + "PmpkPar" + n;
        parentTypeId.name = "parentTypeId" + npar + "PmpkPar" + n;
        divParent.appendChild(parentTypeId);
        var pmpkParId = document.createElement("input");
        pmpkParId.type = "hidden";
        pmpkParId.id = "pmpk" + npar + "ParentId" + n;
        pmpkParId.name = "pmpk" + npar + "ParentId" + n;
        pmpkParId.value = this.childNodes[0].childNodes[0].nodeValue;
        divParent.appendChild(pmpkParId);
        divParent.appendChild(document.createElement("br"));
        var strong = document.createElement("strong");
        strong.appendChild(document.createTextNode(this.childNodes[1].childNodes[0].nodeValue));
        divParent.appendChild(strong);
        divParent.appendChild(document.createElement("br"));
        var lblCl = document.createElement("label");
        lblCl.className = "clickable";
        lblCl.onclick = clickClient;
        lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue));
        divParent.appendChild(lblCl);
        divParent.appendChild(document.createElement("br"));
        divParent.appendChild(document.createTextNode(this.childNodes[5].childNodes[0].nodeValue));
        var url = "search?type=parenttype&n=" + n + "&typecl=" + type; // для заполнения списка законных представителей
        requ(url, "GET");
        document.getElementById("edit" + npar + "Parent" + n).focus();
    } else {
        if (type == "sub") {
            var subId = document.getElementById("subId" + n);
            if (subId == null) {
                subId = document.createElement("input");
                subId.type = "hidden";
                subId.id = "subId" + n;
                subId.name = "subId" + n;
                document.getElementById("sub" + n).appendChild(subId);
            }
        } else if (type == "cl") {
            var clId = document.getElementById("clId" + n);
            if (clId == null) {
                clId = document.createElement("input");
                clId.type = "hidden";
                clId.id = "clId" + n;
                clId.name = "clId" + n;
                document.getElementById("cl" + n).appendChild(clId);
            }
        }

        var cl = document.getElementById(type + n);
        var clId = document.getElementById(type + "Id" + n);
        clId.value = this.childNodes[0].childNodes[0].nodeValue;
        var clKat = document.getElementById(type + "Kat" + n);
        var kat = document.getElementById("selKat").value;
        clKat.value = kat;

        if (type == "cl") {
            var client = document.getElementById("infoClient" + n);
            if (client == null) {
                client = document.createElement("div");
                client.id = "infoClient" + n;
                var img = document.getElementById("delCl" + n);
                if (img == null) {
                    img = document.createElement("img");
                    img.className = "btn";
                    img.id = "delCl" + n;
                    img.src = "img/delete.png";
                    img.width = "20";
                    img.alt = "Удалить";
                    img.title = "Удалить";
                    img.onclick = delClient;
                    cl.appendChild(img);
                }
                cl.appendChild(document.createElement("br"));
            } else {
                client.innerHTML = "";
            }
            if (kat == "parents") {
                var parentTypeSel = document.createElement("select");
                parentTypeSel.id = "parentTypeSel" + n;
                parentTypeSel.name = "parentTypeSel" + n;
                parentTypeSel.onchange = changeParentType;
                client.appendChild(parentTypeSel);
                var parentTypeId = document.createElement("input");
                parentTypeId.type = "hidden";
                parentTypeId.id = "parentTypeId" + n;
                parentTypeId.name = "parentTypeId" + n;
                client.appendChild(parentTypeId);
                client.appendChild(document.createElement("br"));
            }
            var strong = document.createElement("strong");
            strong.appendChild(document.createTextNode(this.childNodes[1].childNodes[0].nodeValue));
            client.appendChild(strong);
            client.appendChild(document.createElement("br"));
            var lblCl = document.createElement("label");
            lblCl.className = "clickable";
            lblCl.onclick = clickClient;
            if (kat == "children") {
                lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                        this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue + " " +
                        this.childNodes[5].childNodes[0].nodeValue));
                client.appendChild(lblCl);
                client.appendChild(document.createElement("br"));
                client.appendChild(document.createTextNode(this.childNodes[6].childNodes[0].nodeValue));
            } else if (kat == "parents") {
                lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                        this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue));
                client.appendChild(lblCl);
                client.appendChild(document.createElement("br"));
                if (this.childNodes[5].childNodes[0].nodeValue == " ") {
                    client.appendChild(document.createTextNode(this.childNodes[6].childNodes[0].nodeValue));
                } else {
                    client.appendChild(document.createTextNode(this.childNodes[5].childNodes[0].nodeValue));
                }
            } else if (kat == "ped") {
                lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                        this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue));
                client.appendChild(lblCl);
                client.appendChild(document.createElement("br"));
                client.appendChild(document.createTextNode(this.childNodes[7].childNodes[0].nodeValue));
                client.appendChild(document.createElement("br"));
                client.appendChild(document.createTextNode("Учреждение: " + this.childNodes[5].childNodes[0].nodeValue));
                client.appendChild(document.createElement("br"));
                client.appendChild(document.createTextNode("Должность: " + this.childNodes[6].childNodes[0].nodeValue));
            }
            client.appendChild(document.createElement("br"));
            cl.appendChild(client);
            if (kat == "parents") {
                var url = "search?type=parenttype&n=" + n + "&typecl=" + type; // для заполнения списка законных представителей
                requ(url, "GET");
            }

            var pmpk = document.getElementById("pmpk").value;
            if (pmpk == 1) {
                var divParents = document.createElement("div");
                divParents.id = "divParents" + n;
                var strongP = document.createElement("strong");
                strongP.appendChild(document.createTextNode("Законные представители: "));
                divParents.appendChild(strongP);
                var div1Parent = document.createElement("div");
                div1Parent.id = "div1Parent" + n;
                div1Parent.appendChild(document.createTextNode("1. "));
                var editImg = document.createElement("img");
                editImg.className = "btn";
                editImg.id = "edit1Parent" + n;
                editImg.src = "img/edit.png";
                editImg.width = "15";
                editImg.alt = "Редактировать";
                editImg.title = "Редактировать";
                editImg.onclick = searchPmpkParent;
                div1Parent.appendChild(editImg);
                var delImg = document.createElement("img");
                delImg.className = "btn";
                delImg.id = "del1Parent" + n;
                delImg.src = "img/delete.png";
                delImg.width = "15";
                delImg.alt = "Удалить";
                delImg.title = "Удалить";
                delImg.onclick = deleteParent;
                div1Parent.appendChild(delImg);
                divParents.appendChild(div1Parent);
                var div2Parent = document.createElement("div");
                div2Parent.id = "div2Parent" + n;
                div2Parent.appendChild(document.createTextNode("2. "));
                var editImg = document.createElement("img");
                editImg.className = "btn";
                editImg.id = "edit2Parent" + n;
                editImg.src = "img/edit.png";
                editImg.width = "15";
                editImg.alt = "Редактировать";
                editImg.title = "Редактировать";
                editImg.onclick = searchPmpkParent;
                div2Parent.appendChild(editImg);
                var delImg = document.createElement("img");
                delImg.className = "btn";
                delImg.id = "del2Parent" + n;
                delImg.src = "img/delete.png";
                delImg.width = "15";
                delImg.alt = "Удалить";
                delImg.title = "Удалить";
                delImg.onclick = deleteParent;
                div2Parent.appendChild(delImg);
                divParents.appendChild(div2Parent);
                client.appendChild(divParents);
            }

            var stat = document.getElementById("stat").value;
            if ((stat == 1) && (kat == "children")) {
                var divStat = document.createElement("div");
                divStat.id = "divStat" + n;
                var inp = document.createElement("input");
                inp.type = "hidden";
                inp.id = "check" + n;
                inp.name = "check" + n;
                divStat.appendChild(inp);
                var strong = document.createElement("strong");
                strong.appendChild(document.createTextNode("Статус ребенка"));
                divStat.appendChild(strong);
                divStat.appendChild(document.createElement("br"));
                var divOsn = document.createElement("div");
                divOsn.id = "divStatosn" + n;
                divStat.appendChild(divOsn);
                client.appendChild(divStat);
                cl.appendChild(client);
                var clId = document.getElementById("clId" + n).value;
                var datePriyom = document.getElementById("datePriyom").value;
                var url = "priyomaction?action=statosn&date=" + datePriyom + "&usl=" + uslId + "&type=cl&n=" + n + "&clid=" + clId;
                requ(url, "GET");
            }

            if (pmpk == 1) {
                var divFirstOvz = document.createElement("div");
                divFirstOvz.id = "divFirstOvz" + n;
                divFirstOvz.appendChild(document.createElement("br"));
                var lblFirstOvz = document.createElement("label");                
                var chbFirstOvz = document.createElement("input");
                chbFirstOvz.type = "checkbox";
                chbFirstOvz.id = "firstOvz" + n;
                chbFirstOvz.name = "firstOvz" + n;                
                lblFirstOvz.appendChild(chbFirstOvz);
                lblFirstOvz.appendChild(document.createTextNode("ОВЗ выявлены впервые"));
                divFirstOvz.appendChild(lblFirstOvz);
                divFirstOvz.appendChild(document.createElement("br"));
                divFirstOvz.appendChild(document.createElement("br"));
                                
                var divOu = document.createElement("div");
                divOu.id = "divOu" + n;
                divOu.appendChild(document.createTextNode("Образовательное учреждение: "));
                var inpOu = document.createElement("input");
                inpOu.type = "hidden";
                inpOu.id = "ou" + n;
                inpOu.name = "ou" + n;
                inpOu.value = "1";
                divOu.appendChild(inpOu);
                var selOu = document.createElement("select");
                selOu.id = "selOu" + n;
                selOu.onchange = changeOU;
                var opt1 = document.createElement("option");
                opt1.value = "1";
                opt1.appendChild(document.createTextNode("посещает"));
                var opt2 = document.createElement("option");
                opt2.value = "0";
                opt2.appendChild(document.createTextNode("не посещает"));
                selOu.appendChild(opt1);
                selOu.appendChild(opt2);
                divOu.appendChild(selOu);

                var divPmpk = document.createElement("div");
                divPmpk.id = "divPmpk" + n;
                divPmpk.appendChild(document.createTextNode("Номер протокола: "));
                var inpNom = document.createElement("input");
                inpNom.type = "text";
                inpNom.id = "nomPr" + n;
                inpNom.name = "nomPr" + n;
                divPmpk.appendChild(inpNom);
                var divOp = document.createElement("div");
                divOp.id = "divOp" + n;
                divOp.appendChild(document.createTextNode("Вид образовательной программы: "));
                var selOptype = document.createElement("select");
                selOptype.id = "selOptype" + n;
                selOptype.onchange = changeOpType;
                divOp.appendChild(selOptype);
                var op = document.createElement("div");
                op.id = "op" + n;
                divOp.appendChild(op);
                var divVar = document.createElement("div");
                divVar.id = "var" + n;
                divOp.appendChild(divVar);
                var url = "priyomaction?action=optype&n=" + n;
                requ(url, "GET");
                divPmpk.appendChild(divOp);

                var rolesRight = document.getElementById("isVologda");
                if (rolesRight.value == "true") {
                    var divTpmpk = document.createElement("divTpmpk");
                    divTpmpk.id = "divTpmpk" + n;
                    divTpmpk.appendChild(document.createTextNode("Территориальная ПМПК: "));
                    var inpT = document.createElement("input");
                    inpT.type = "hidden";
                    inpT.id = "tpmpk" + n;
                    inpT.name = "tpmpk" + n;
                    inpT.value = 0;
                    divTpmpk.appendChild(inpT);
                    var selT = document.createElement("select");
                    selT.id = "selTpmpk" + n;
                    selT.onchange = changeTpmpk;
                    var optT1 = document.createElement("option");
                    optT1.value = "0";
                    optT1.appendChild(document.createTextNode("нет"));
                    var optT2 = document.createElement("option");
                    optT2.value = "1";
                    optT2.appendChild(document.createTextNode("по направлению"));
                    var optT3 = document.createElement("option");
                    optT3.value = "0";
                    optT3.appendChild(document.createTextNode("обжалование"));
                    selT.appendChild(optT1);
                    selT.appendChild(optT2);
                    selT.appendChild(optT3);
                    divTpmpk.appendChild(selT);
                    divPmpk.appendChild(divTpmpk);
                }
                var divGia = document.createElement("div");
                divGia.id = "divGia" + n;
                divGia.appendChild(document.createTextNode("Государственная итоговая аттестация: "));
                var gia = document.createElement("input");
                gia.type = "hidden";
                gia.id = "gia" + n;
                gia.name = "gia" + n;
                gia.value = 0;
                divGia.appendChild(gia);
                var selGia = document.createElement("select");
                selGia.id = "selGia" + n;
                selGia.onchange = changeGia;
                var optG1 = document.createElement("option");
                optG1.value = "0";
                optG1.appendChild(document.createTextNode("нет"));
                var optG2 = document.createElement("option");
                optG2.value = "1";
                optG2.appendChild(document.createTextNode("ГИА-9"));
                var optG3 = document.createElement("option");
                optG3.value = "2";
                optG3.appendChild(document.createTextNode("ГИА-11"));
                selGia.appendChild(optG1);
                selGia.appendChild(optG2);
                selGia.appendChild(optG3);
                divGia.appendChild(selGia);
                divPmpk.appendChild(divGia);

                var rolesRight = document.getElementById("isVologda");
                if (rolesRight.value == "true") {
                    var divIpr = document.createElement("div");
                    divIpr.id = "divIpr" + n;
                    var lblIpr = document.createElement("label");
                    var inpIpr = document.createElement("input");
                    inpIpr.type = "checkbox";
                    inpIpr.id = "ipr" + n;
                    inpIpr.name = "ipr" + n;
                    lblIpr.appendChild(inpIpr);
                    lblIpr.appendChild(document.createTextNode("Оказание содействия в разработке ИПР ребенка-инвалида"));
                    divIpr.appendChild(lblIpr);
                    divPmpk.appendChild(divIpr);
                }
                var divRek = document.createElement("div");
                divRek.id = "divRekomend" + n;
                var url = "priyomaction?action=rekomend&n=" + n;
                requ(url, "GET");
                divPmpk.appendChild(divRek);
                var divDopObs = document.createElement("div");
                divDopObs.id = "divDopObs" + n;
                var lblDopObs = document.createElement("label");
                var inpDopObs = document.createElement("input");
                inpDopObs.type = "checkbox";
                inpDopObs.id = "dopObsled" + n;
                inpDopObs.name = "dopObsled" + n;
                lblDopObs.appendChild(inpDopObs);
                lblDopObs.appendChild(document.createTextNode("Дополнительное обследование"));
                divDopObs.appendChild(lblDopObs);
                divPmpk.appendChild(divDopObs);
                var divSrok = document.createElement("div");
                divSrok.id = "divSrok" + n;
                var lblSrok = document.createElement("label");
                lblSrok.appendChild(document.createTextNode("Срок действия заключения: "));
                divSrok.appendChild(lblSrok);
                var selSrok = document.createElement("select");
                selSrok.id = "selSrok" + n;
                selSrok.name = "selSrok" + n;
                var optSrok1 = document.createElement("option");
                optSrok1.value = 0;
                optSrok1.appendChild(document.createTextNode("до окончания ступени обучения"));
                selSrok.appendChild(optSrok1);
                var optSrok2 = document.createElement("option");
                optSrok2.value = 1;
                optSrok2.appendChild(document.createTextNode("до"));
                selSrok.appendChild(optSrok2);
                selSrok.onchange = clickSrokDate;
                divSrok.appendChild(selSrok);
                divPmpk.appendChild(divSrok);

                var divSogl = document.createElement("div");
                divSogl.id = "divSogl" + n;
                divSogl.appendChild(document.createTextNode("С заключением: "));
                var inpSogl = document.createElement("input");
                inpSogl.type = "hidden";
                inpSogl.id = "sogl" + n;
                inpSogl.name = "sogl" + n;
                inpSogl.value = 1;
                divSogl.appendChild(inpSogl);
                var selSogl = document.createElement("select");
                selSogl.id = "selSogl" + n;
                selSogl.onchange = changeSogl;
                var optS1 = document.createElement("option");
                optS1.value = "1";
                optS1.appendChild(document.createTextNode("согласен"));
                var optS2 = document.createElement("option");
                optS2.value = "0";
                optS2.appendChild(document.createTextNode("не согласен"));
                selSogl.appendChild(optS1);
                selSogl.appendChild(optS2);
                divSogl.appendChild(selSogl);
                divPmpk.appendChild(divSogl);

                client.appendChild(divFirstOvz);
                client.appendChild(divOu);
                client.appendChild(divPmpk);
                cl.appendChild(client);
            }

            var monit = document.getElementById("monit").value;
            if (monit == 1) {
                var div = document.createElement("div");
                div.id = "divMonit" + n;
                div.appendChild(document.createTextNode("Оказаной услугой: "));
                var select = document.createElement("select");
                select.id = "selMonit" + n;
                select.onchange = changeMonit;
                var opt1 = document.createElement("option");
                opt1.value = "1";
                opt1.appendChild(document.createTextNode("удовлетворен"));
                var opt2 = document.createElement("option");
                opt2.value = "0";
                opt2.appendChild(document.createTextNode("не удовлетворен"));
                select.appendChild(opt1);
                select.appendChild(opt2);
                select.onchange = changeMonit;
                div.appendChild(select);
                var inp = document.createElement("input");
                inp.type = "hidden";
                inp.id = "monit" + n;
                inp.name = "monit" + n;
                inp.value = 1;
                div.appendChild(inp);
                client.appendChild(div);
                cl.appendChild(client);
            }
            document.getElementById("editCl" + n).focus();

        } else if (type == "sub") {
            var sub = document.getElementById("infoSubject" + n);
            if (sub == null) {
                sub = document.createElement("div");
                sub.id = "infoSubject" + n;
                var img = document.getElementById("delSub" + n);
                if (img == null) {
                    img = document.createElement("img");
                    img.className = "btn";
                    img.id = "delSub" + n;
                    img.src = "img/delete.png";
                    img.width = "20";
                    img.alt = "Удалить";
                    img.title = "Удалить";
                    img.onclick = delSub;
                    cl.appendChild(img);
                }
                cl.appendChild(document.createElement("br"));
            } else {
                sub.innerHTML = "";
            }
            var strong = document.createElement("strong");
            strong.appendChild(document.createTextNode(this.childNodes[1].childNodes[0].nodeValue));
            sub.appendChild(strong);
            sub.appendChild(document.createElement("br"));
            var lblCl = document.createElement("label");
            lblCl.className = "clickable";
            lblCl.onclick = clickClient;
            lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                    this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue + " " +
                    this.childNodes[5].childNodes[0].nodeValue));
            sub.appendChild(lblCl);
            sub.appendChild(document.createElement("br"));
            sub.appendChild(document.createTextNode(this.childNodes[6].childNodes[0].nodeValue));
            sub.appendChild(document.createElement("br"));
            cl.appendChild(sub);

            var stat = document.getElementById("stat").value;
            if (stat == 1) {
                var divStat = document.createElement("div");
                divStat.id = "divSubstat" + n;
                var inp = document.createElement("input");
                inp.type = "hidden";
                inp.id = "checkSub" + n;
                inp.name = "checkSub" + n;
                divStat.appendChild(inp);
                var strong = document.createElement("strong");
                strong.appendChild(document.createTextNode("Статус ребенка"));
                divStat.appendChild(strong);
                divStat.appendChild(document.createElement("br"));
                var divOsn = document.createElement("div");
                divOsn.id = "divSubstatosn" + n;
                divStat.appendChild(divOsn);
                sub.appendChild(divStat);
                cl.appendChild(sub);
                var subId = document.getElementById("subId" + n).value;
                var datePriyom = document.getElementById("datePriyom").value;
                var url = "priyomaction?action=statosn&date=" + datePriyom + "&usl=" + uslId + "&type=" + type + "&n=" + n + "&clid=" + subId;
                requ(url, "GET");
            }
            document.getElementById("editSub" + n).focus();
        }
    }
}

function changeMonit() {
    var n = this.id.substring(8);
    var mon = document.getElementById("monit" + n);
    mon.value = this.value;
}

function clearSelOpTypes(n) {
    var sel = document.getElementById("selOptype" + n);
    sel.innerHTML = "";
    var opt = document.createElement("option");
    opt.value = "0";
    sel.appendChild(opt);
}

function appendOpType(n, name, id) {
    var sel = document.getElementById("selOptype" + n);
    if (sel != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        sel.appendChild(opt);
    }
}

function clearRekomend(n) {
    var div = document.getElementById("divRekomend" + n);
    div.innerHTML = "";
    div.appendChild(document.createTextNode("Рекомендовано:"));
    div.appendChild(document.createElement("br"));
}

function appendRekomend(rekN, rekId, rekName) {
    var divrek = document.getElementById("divRekomend" + rekN);
    if (divrek != null) {
        var lbl = document.createElement("label");
        var chb = document.createElement("input");
        chb.type = "checkbox";
        chb.id = "rek" + rekId + "_" + rekN;
        chb.name = "rek" + rekId + "_" + rekN;
        chb.value = rekId;
        lbl.appendChild(chb);
        lbl.appendChild(document.createTextNode(rekName));
        divrek.appendChild(lbl);
        divrek.appendChild(document.createElement("br"));
    }
}

function delClient() {
    var n = this.id.substring(5);
    var cl = document.getElementById("cl" + n);
    var clients = document.getElementById("divClients");
    var divs = clients.getElementsByTagName("div");
    var k = 0;
    cl.innerHTML = "";
    for (loop = 0; loop < divs.length; loop++) {
        if (divs[loop].id.substring(0, 2) == "cl") {
            k++;
        }
    }
    cl.remove();
    var divhr = document.getElementById("divhr" + n);
    if (divhr != null) {
        divhr.remove();
    }
    if (k == 1) {
        appendDivClients();
    }
}

function delSub() {
    var n = this.id.substring(6);
    var cl = document.getElementById("sub" + n);
    var subs = document.getElementById("divSubjects");
    var divs = subs.getElementsByTagName("div");
    var k = 0;
    cl.innerHTML = "";
    for (loop = 0; loop < divs.length; loop++) {
        if (divs[loop].id.substring(0, 3) == "sub") {
            k++;
        }
    }
    cl.remove();
    var divhr = document.getElementById("divhr" + n);
    if (divhr != null) {
        divhr.remove();
    }
    if (k == 1) {
        appendDivSubj();
    }
}

function addClient() {
    var clients = document.getElementById("divClients");
    var divs = clients.getElementsByTagName("div");
    var n;
    var classN = -1;
    for (loop = 0; loop < divs.length; loop++) {
        if (divs[loop].id.substring(0, 2) == "cl") {
            n = divs[loop].id.substring(2);
            classN = classN * (-1);
        }
    }
    n++;
    var divhr = document.createElement("div");
    divhr.id = "divhr" + n;
    divhr.appendChild(document.createElement("hr"));
    clients.appendChild(divhr);
    var cl = document.createElement("div");
    cl.className = "clients" + classN;
    cl.id = "cl" + n;
    var clKat = document.createElement("input");
    clKat.type = "hidden";
    clKat.id = "clKat" + n;
    clKat.name = "clKat" + n;
    cl.appendChild(clKat);
    var img = document.createElement("img");
    img.className = "btn";
    img.id = "editCl" + n;
    img.src = "img/edit.png";
    img.width = "20";
    img.alt = "Редактировать";
    img.title = "Редактировать";
    img.onclick = searchClient;
    cl.appendChild(img);
    var delImg = document.createElement("img");
    delImg.className = "btn";
    delImg.id = "delCl" + n;
    delImg.src = "img/delete.png";
    delImg.width = "20";
    delImg.alt = "Удалить";
    delImg.title = "Удалить";
    delImg.onclick = delClient;
    cl.appendChild(delImg);
    clients.appendChild(cl);
}

function addSubj() {
    var subs = document.getElementById("divSubjects");
    var divs = subs.getElementsByTagName("div");
    var n;
    var classN = -1;
    for (loop = 0; loop < divs.length; loop++) {
        if (divs[loop].id.substring(0, 3) == "sub") {
            n = divs[loop].id.substring(3);
            classN = classN * (-1);
        }
    }
    n++;
    var divhr = document.createElement("div");
    divhr.id = "divhr" + n;
    divhr.appendChild(document.createElement("hr"));
    subs.appendChild(divhr);
    var cl = document.createElement("div");
    cl.className = "clients" + classN;
    cl.id = "sub" + n;
    var clKat = document.createElement("input");
    clKat.type = "hidden";
    clKat.id = "subKat" + n;
    clKat.name = "subKat" + n;
    cl.appendChild(clKat);
    var img = document.createElement("img");
    img.className = "btn";
    img.id = "editSub" + n;
    img.src = "img/edit.png";
    img.width = "20";
    img.alt = "Редактировать";
    img.title = "Редактировать";
    img.onclick = searchClient;
    cl.appendChild(img);
    var delImg = document.createElement("img");
    delImg.className = "btn";
    delImg.id = "delSub" + n;
    delImg.src = "img/delete.png";
    delImg.width = "20";
    delImg.alt = "Удалить";
    delImg.title = "Удалить";
    delImg.onclick = delSub;
    cl.appendChild(delImg);
    subs.appendChild(cl);
}

function appendProblemType(n, prTName, prTId) {
    var sel = document.getElementById("selPrtype" + n);
    var opt = document.createElement("option");
    opt.value = prTId;
    opt.appendChild(document.createTextNode(prTName));
    sel.appendChild(opt);
}

function appendProblem(n, prName, prId) {
    var sel = document.getElementById("selPr" + n);
    var opt = document.createElement("option");
    opt.value = prId;
    opt.appendChild(document.createTextNode(prName));
    sel.appendChild(opt);
    sel.onchange();
}

function clearProblemType(n) {
    var s = document.getElementById("selPrtype" + n);
    if (s.childNodes.length > 0) {
        for (loop = s.childNodes.length - 1; loop >= 0; loop--) {
            s.removeChild(s.childNodes[loop]);
        }
    }
    var opt = document.createElement("option");
    opt.value = 0;
    opt.appendChild(document.createTextNode(""));
    s.appendChild(opt);
}

function clearProblem(n) {
    var s = document.getElementById("selPr" + n);
    for (loop = s.childNodes.length - 1; loop >= 0; loop--) {
        s.removeChild(s.childNodes[loop]);
    }
}

function changeProblemType() {
    var n = this.id.substring(9);
    var type = this.value;
    clearProblem(n);
    var url = "priyomaction?action=problem&n=" + n + "&type=" + type; // для заполнения списка районов проживания
    requ(url, "GET");
}

function changeProblem() {
    var n = this.id.substring(5);
    var prId = document.getElementById("prId" + n);
    prId.value = this.value;
}

function appendKurs() {
    var seans = document.getElementById("divSeans");
    var p = document.createElement("p");
    p.appendChild(document.createTextNode("Количество занятий: "));
    var inp = document.createElement("input");
    inp.type = "text";
    inp.name = "kol";
    inp.id = "kol";
    inp.onkeyup = actkurs;
    p.appendChild(inp);
    seans.appendChild(p);

    var p2 = document.createElement("p");
    p2.id = "chast";
    p2.appendChild(document.createTextNode("Частота занятий: "));
    var sel = document.createElement("select");
    sel.id = "selChast";
    var opt = document.createElement("option");
    opt.value = 0;
    opt.appendChild(document.createTextNode("ежедневно (понедельник-пятница)"));
    sel.appendChild(opt);
    var opt1 = document.createElement("option");
    opt1.value = 1;
    opt1.appendChild(document.createTextNode("ежедневно (вторник-суббота)"));
    sel.appendChild(opt1);
    var opt2 = document.createElement("option");
    opt2.value = 2;
    opt2.appendChild(document.createTextNode("еженедельно"));
    sel.appendChild(opt2);
    sel.onchange = changeChast;
    p2.appendChild(sel);
    seans.appendChild(p2);
}

function clearKurs() {
    var divseans = document.getElementById("divSeans");
    if (divseans != null) {
        divseans.innerHTML = "";
    }
}

function changeChast() {
    var sel = document.getElementById("selChast");
    if (sel != null) {
        if (sel.value == 2) {
            var p = document.getElementById("chast");
            if (p != null) {
                var selDW = document.createElement("select");
                selDW.id = "selDW";
                selDW.onchange = actkurs;
                var opt1 = document.createElement("option");
                opt1.value = 1;
                opt1.appendChild(document.createTextNode("понедельник"));
                selDW.appendChild(opt1);
                var opt2 = document.createElement("option");
                opt2.value = 2;
                opt2.appendChild(document.createTextNode("вторник"));
                selDW.appendChild(opt2);
                var opt3 = document.createElement("option");
                opt3.value = 3;
                opt3.appendChild(document.createTextNode("среда"));
                selDW.appendChild(opt3);
                var opt4 = document.createElement("option");
                opt4.value = 4;
                opt4.appendChild(document.createTextNode("четверг"));
                selDW.appendChild(opt4);
                var opt5 = document.createElement("option");
                opt5.value = 5;
                opt5.appendChild(document.createTextNode("пятница"));
                selDW.appendChild(opt5);
                var opt6 = document.createElement("option");
                opt6.value = 6;
                opt6.appendChild(document.createTextNode("суббота"));
                selDW.appendChild(opt6);
                p.appendChild(selDW);
                actkurs();
            }
        } else {
            var selDW = document.getElementById("selDW");
            if (selDW != null) {
                selDW.remove();
            }
            actkurs();
        }
    }
}

function actkurs() {
    var inpKol = document.getElementById("kol");
    if (inpKol != null) {
        if ((inpKol.value != null) && (inpKol.value != 0)) {
            var kol = inpKol.value;
            if (isNaN(kol)) {
                kol = 0;
                inpKol.value = "";
            } else if (kol >= 50) {
                kol = 1;
            }
            var chast = document.getElementById("selChast");
            var ch = chast.value;
            var selDW = document.getElementById("selDW");
            var dw = 0;
            if (selDW != null) {
                dw = selDW.value;
            }
            var datepr = document.getElementById("datePriyom").value;
            if (datepr != "") {
                if (datepr.length == 10) {
                    var url = "priyomaction?action=kurs&kol=" + kol + "&ch=" + ch + "&dw=" + dw + "&datepr=" + datepr;
                    requ(url, "GET");
                } else {
                    alert("Проверьте дату первого занятия (дата приема наверху)");
                }
            } else {
                alert("Поставьте дату первого занятия (дата приема наверху)");
            }
        } else {
            clearKursPr();
        }
    }
}

function checkdate() {
    var datepr = document.getElementById("datePriyom").value;
    if (datepr.length == 10) {
        actkurs();
    }
}

function  appendKursPr(datePr) {
    var kurspr = document.getElementById("kurspr");
    var inp = document.createElement("input");
    inp.type = "date";
    var inps = kurspr.getElementsByTagName("input");
    var k = 1;
    for (loop = 0; loop < inps.length; loop++) {
        if (inps[loop].id.substring(0, 6) == "kurspr") {
            k++;
        }
    }
    inp.id = "kurspr" + k;
    inp.name = "kurspr" + k;
    inp.value = datePr;
    kurspr.appendChild(inp);
    kurspr.appendChild(document.createElement("br"));
}

function clearKursPr() {
    var kurspr = document.getElementById("kurspr");
    if (kurspr != null) {
        kurspr.remove();
    }
    var div = document.getElementById("divSeans");
    kurspr = document.createElement("div");
    kurspr.id = "kurspr";
    div.appendChild(kurspr);
}

function changeOU() {
    var n = this.id.substring(5);
    var ou = document.getElementById("ou" + n);
    ou.value = this.value;
}

function changeTpmpk() {
    var n = this.id.substring(8);
    var tpmpk = document.getElementById("tpmpk" + n);
    tpmpk.value = this.value;
}

function changeGia() {
    var n = this.id.substring(6);
    var gia = document.getElementById("gia" + n);
    gia.value = this.value;
}

function changeSogl() {
    var n = this.id.substring(7);
    var sogl = document.getElementById("sogl" + n);
    sogl.value = this.value;
}

function changeOpType() {
    var val = this.value;
    var n = this.id.substring(9);
    if (val != "0") {
        var url = "priyomaction?action=op&n=" + n + "&optype=" + val;
        requ(url, "GET");
    } else {
        var url = "priyomaction?action=optype&n=" + n;
        requ(url, "GET");
    }
}

function clearSelOpTypes(n) {
    var divop = document.getElementById("divOp" + n);
    if (divop != null) {
        divop.innerHTML = "";
        divop.appendChild(document.createTextNode("Вид образовательной программы: "));
        var sel = document.createElement("select");
        sel.id = "selOptype" + n;
        var opt = document.createElement("option");
        opt.value = "0";
        sel.appendChild(opt);
        sel.onchange = changeOpType;
        divop.appendChild(sel);
    }
}

function appendOpType(n, name, id) {
    var sel = document.getElementById("selOptype" + n);
    if (sel != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        sel.appendChild(opt);
    }
}

function clearSelOp(n) {
    var divop = document.getElementById("divOp" + n);
    if (divop != null) {
        var div = document.getElementById("op" + n);
        if (div != null) {
            div.remove();
        }
        var op = document.createElement("div");
        op.id = "op" + n;
        op.appendChild(document.createTextNode("Образовательная программа: "));
        var sel = document.createElement("select");
        sel.id = "selOp" + n;
        var opt = document.createElement("option");
        opt.value = "0";
        sel.appendChild(opt);
        sel.onchange = changeOp;
        op.appendChild(sel);
        var inp = document.createElement("input");
        inp.type = "hidden";
        inp.id = "opId" + n;
        inp.name = "opId" + n;
        inp.value = "0";
        op.appendChild(inp);
        divop.appendChild(op);
    }
}

function appendOp(n, name, id) {
    var sel = document.getElementById("selOp" + n);
    if (sel != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        sel.appendChild(opt);
    }
}

function changeOp() {
    var val = this.value;
    var n = this.id.substring(5);
    if (val != "0") {
        var url = "priyomaction?action=opvar&n=" + n + "&op=" + val;
        requ(url, "GET");
        var opId = document.getElementById("opId" + n);
        if (opId != null) {
            opId.value = val;
        }
        clearSelVar(n);
    } else {
        clearSelVar(n);
        var id = document.getElementById("opId" + n);
        id.value = "0";
    }
}

function clearSelVar(n) {
    var divop = document.getElementById("divOp" + n);
    if (divop != null) {
        var div = document.getElementById("var" + n);
        if (div != null) {
            div.remove();
        }
    }
}

function addSelVar(n) {
    var divop = document.getElementById("divOp" + n);
    if (divop != null) {
        var v = document.createElement("div");
        v.id = "var" + n;
        v.appendChild(document.createTextNode("Вариант образовательной программы: "));
        var sel = document.createElement("select");
        sel.id = "selVar" + n;
        var opt = document.createElement("option");
        opt.value = "0";
        sel.appendChild(opt);
        sel.onchange = changeVar;
        v.appendChild(sel);
        var inp = document.createElement("input");
        inp.type = "hidden";
        inp.id = "varId" + n;
        inp.name = "varId" + n;
        inp.value = "0";
        v.appendChild(inp);
        divop.appendChild(v);
    }
}

function appendVar(n, name, id) {
    var sel = document.getElementById("selVar" + n);
    if (sel != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        sel.appendChild(opt);
    }
}

function changeVar() {
    var val = this.value;
    var n = this.id.substring(6);
    var varId = document.getElementById("varId" + n);
    if (varId != null) {
        varId.value = val;
    }
}

function  delSelVar(n) {
    var divop = document.getElementById("divOp" + n);
    if (divop != null) {
        var div = document.getElementById("var" + n);
        if (div != null) {
            div.remove();
        }
    }
}

function appendStatOsn(n, statosnType, statosnId, statosnName, statosnNorma, statosnCheck, statosnEnabled) {
    var div;
    if (statosnType == "cl") {
        div = document.getElementById("divStatosn" + n);
        var lbl = document.createElement("label");
        var chb = document.createElement("input");
        chb.type = "checkbox";
        chb.id = "statosn" + statosnId + "_" + n;
        chb.name = "statosn" + statosnId + "_" + n;
        chb.value = statosnId;
        if (statosnNorma == "1") {
            chb.className = "norma";
        }
        if (statosnCheck == 1) {
            chb.checked = "checked";
        }
        if (statosnEnabled == 0) {
            chb.onclick = doNothing;
            lbl.style = "color: grey";
        }
        chb.onchange = checkStatOsn;
        lbl.appendChild(chb);
        lbl.appendChild(document.createTextNode(statosnName));
        div.appendChild(lbl);
        if (statosnCheck == 1) {
            chb.onchange();
        }
    } else if (statosnType == "sub") {
        div = document.getElementById("divSubstatosn" + n);
        var lbl = document.createElement("label");
        var chb = document.createElement("input");
        chb.type = "checkbox";
        chb.id = "subStatosn" + statosnId + "_" + n;
        chb.name = "subStatosn" + statosnId + "_" + n;
        chb.value = statosnId;
        if (statosnCheck == 1) {
            chb.checked = "checked";
        }
        if (statosnNorma == "1") {
            chb.className = "norma";
        }
        if (statosnEnabled == 0) {
            chb.onclick = doNothing;
            lbl.style = "color: grey";
        }
        chb.onchange = checkStatOsn;
        lbl.appendChild(chb);
        lbl.appendChild(document.createTextNode(statosnName));
        div.appendChild(lbl);
        if (statosnCheck == 1) {
            chb.onchange();
        }
    }

}

function checkStatOsn() {
    var uslId = document.getElementById("uslId").value;
    var k = this.id.indexOf("_");
    var n = this.id.substring(k + 1);
    var check;
    var stat;
    var type;
    if (this.id.substring(0, 10) == "subStatosn") {
        type = "sub";
        check = document.getElementById("checkSub" + n);
        stat = document.getElementById("divSubstat" + n);
    } else if (this.id.substring(0, 7) == "statosn") {
        type = "cl";
        check = document.getElementById("check" + n);
        stat = document.getElementById("divStat" + n);
    }

    if (this.checked) {
        if (this.classList.contains("norma")) {
            if (type == "sub") {
                var div = document.getElementById("divSubstat" + n);
                var divs = div.getElementsByTagName("div");
                if (divs != null) {
                    for (loop = 0; loop < divs.length; loop++) {
                        if ((divs[loop].id != "divSubstatosn" + n) && (divs[loop].id != "divSubstatsoc" + n)) {
                            divs[loop].remove();
                            loop--;
                        }
                    }
                }
                var div = document.getElementById("divSubstatosn" + n);
                var checks = div.getElementsByTagName("input");
                if (checks != null) {
                    for (loop = 0; loop < checks.length; loop++) {
                        if (checks[loop] != this) {
                            checks[loop].checked = false;
                            checks[loop].onclick = doNothing;
                            checks[loop].parentNode.style = "color: grey"
                        }
                    }
                }
                var divSoc = document.getElementById("divSubstatsoc" + n);
                if (divSoc == null) {
                    divSoc = document.createElement("div");
                    divSoc.id = "divSubstatsoc" + n;
                    stat.appendChild(divSoc);
                    var datePriyom = document.getElementById("datePriyom").value;
                    var url = "priyomaction?action=statsoc&date=" + datePriyom + "&usl=" + uslId + "&n=" + n + "&type=" + type;
                    var subId = document.getElementById("subId" + n);
                    if (subId != null) {
                        url += "&clid=" + subId.value;
                    }
                    requ(url, "GET");
                }
            } else if (type == "cl") {
                var div = document.getElementById("divStat" + n);
                var divs = div.getElementsByTagName("div");
                if (divs != null) {
                    for (loop = 0; loop < divs.length; loop++) {
                        if ((divs[loop].id != "divStatosn" + n) && (divs[loop].id != "divStatsoc" + n)) {
                            divs[loop].remove();
                            loop--;
                        }
                    }
                }
                var div = document.getElementById("divStatosn" + n);
                var checks = div.getElementsByTagName("input");
                if (checks != null) {
                    for (loop = 0; loop < checks.length; loop++) {
                        if (checks[loop] != this) {
                            checks[loop].checked = false;
                            checks[loop].onclick = doNothing;
                            checks[loop].parentNode.style = "color: grey";
                        }
                    }
                }
                var divSoc = document.getElementById("divStatsoc" + n);
                if (divSoc == null) {
                    divSoc = document.createElement("div");
                    divSoc.id = "divStatsoc" + n;
                    stat.appendChild(divSoc);
                    var datePriyom = document.getElementById("datePriyom").value;
                    var url = "priyomaction?action=statsoc&date=" + datePriyom + "&usl=" + uslId + "&n=" + n + "&type=" + type;
                    var clId = document.getElementById("clId" + n);
                    if (clId != null) {
                        url += "&clid=" + clId.value;
                    }
                    requ(url, "GET");
                }
            }
            check.value = "1";
        } else {
            if ((check.value == "0") || (check.value == "")) {
                var divSoc = document.createElement("div");
                if (type == "sub") {
                    divSoc = document.getElementById("divSubstatsoc" + n);
                    if (divSoc == null) {
                        divSoc = document.createElement("div");
                        divSoc.id = "divSubstatsoc" + n;
                    }
                } else if (type == "cl") {
                    divSoc = document.getElementById("divStatsoc" + n);
                    if (divSoc == null) {
                        divSoc = document.createElement("div");
                        divSoc.id = "divStatsoc" + n;
                    }
                }
                stat.appendChild(divSoc);
                var datePriyom = document.getElementById("datePriyom").value;
                var url = "priyomaction?action=statsoc&date=" + datePriyom + "&usl=" + uslId + "&n=" + n + "&type=" + type;
                if (type == "cl") {
                    var clId = document.getElementById("clId" + n);
                    if (clId != null) {
                        url += "&clid=" + clId.value;
                    }
                } else if (type == "sub") {
                    var subId = document.getElementById("subId" + n);
                    if (subId != null) {
                        url += "&clid=" + subId.value;
                    }
                }
                requ(url, "GET");
                var div;
                if (type == "sub") {
                    div = document.getElementById("divSubstatdop" + n);
                    if (div == null) {
                        div = document.createElement("div");
                        div.id = "divSubstatdop" + n;
                    }

                } else if (type == "cl") {
                    div = document.getElementById("divStatdop" + n);
                    if (div == null) {
                        div = document.createElement("div");
                        div.id = "divStatdop" + n;
                    }
                }
                stat.appendChild(div);
                var datePriyom = document.getElementById("datePriyom").value;
                var url = "priyomaction?action=statdop&date=" + datePriyom + "&usl=" + uslId + "&n=" + n + "&type=" + type;
                if (type == "cl") {
                    var clId = document.getElementById("clId" + n);
                    if (clId != null) {
                        url += "&clid=" + clId.value;
                    }
                } else if (type == "sub") {
                    var subId = document.getElementById("subId" + n);
                    if (subId != null) {
                        url += "&clid=" + subId.value;
                    }
                }
                requ(url, "GET");
            }

            if (check.value != "") {
                var i = parseInt(check.value) + 1;
                check.value = String(i);
            } else if (check.value == "") {
                check.value = "1";
            }
        }
    }
    if (!this.checked) {
        if (this.classList.contains("norma")) {
            var div;
            if (type == "cl") {
                div = document.getElementById("divStatosn" + n);
            } else if (type == "sub") {
                div = document.getElementById("divSubstatosn" + n);
            }
            var checks = div.getElementsByTagName("input");
            if (checks != null) {
                for (loop = 0; loop < checks.length; loop++) {
                    if (checks[loop] != this) {
                        checks[loop].onclick = checkStatOsn;
                        checks[loop].parentNode.style = "color : black";
                    }
                }
            }
            var divSoc;
            if (type == "cl") {
                divSoc = document.getElementById("divStatsoc" + n);
            } else if (type == "sub") {
                divSoc = document.getElementById("divSubstatsoc" + n);
            }
            if (divSoc != null) {
                divSoc.remove();
            }
            check.value = "0";
        } else {
            var i = parseInt(check.value) - 1;
            check.value = String(i);
            if (check.value == "0") {
                if (type == "cl") {
                    var div = document.getElementById("divStatdop" + n);
                    if (div != null) {
                        div.remove();
                    }
                    var divSoc = document.getElementById("divStatsoc" + n);
                    if (divSoc != null) {
                        divSoc.remove();
                    }
                    var inp = document.getElementsByTagName("div");
                    for (loop = 0; loop < inp.length; loop++) {
                        if (inp[loop].id.substring(0, 10) == "divStatpod") {
                            inp[loop].remove();
                        }
                    }
                } else if (type == "sub") {
                    var div = document.getElementById("divSubstatdop" + n);
                    if (div != null) {
                        div.remove();
                    }
                    var divSoc = document.getElementById("divSubstatsoc" + n);
                    if (divSoc != null) {
                        divSoc.remove();
                    }
                    var inp = document.getElementsByTagName("div");
                    for (loop = 0; loop < inp.length; loop++) {
                        if (inp[loop].id.substring(0, 13) == "divSubstatpod") {
                            inp[loop].remove();
                        }
                    }
                }
            }
        }
    }
}


function appendStatDop(n, type, id, name, check, enabled) {
    var client;
    if (type == "cl") {
        client = document.getElementById("divStatdop" + n);
        var lbl = document.createElement("label");
        var chb = document.createElement("input");
        chb.type = "checkbox";
        chb.id = "statdop" + id + "_" + n;
        chb.name = "statdop" + id + "_" + n;
        chb.value = id;
        if (check == 1) {
            chb.checked = "checked";
        }
        if (enabled == 0) {
            chb.onclick = doNothing;
            lbl.style = "color: grey";
        }
        chb.onchange = checkStatDop;
        lbl.appendChild(chb);
        lbl.appendChild(document.createTextNode(name));
        client.appendChild(lbl);
        if (check == 1) {
            chb.onchange();
        }
    } else if (type == "sub") {
        client = document.getElementById("divSubstatdop" + n);
        var lbl = document.createElement("label");
        var chb = document.createElement("input");
        chb.type = "checkbox";
        chb.id = "subStatdop" + id + "_" + n;
        chb.name = "subStatdop" + id + "_" + n;
        chb.value = id;
        if (check == 1) {
            chb.checked = "checked";
        }
        if (enabled == 0) {
            chb.onclick = doNothing;
            lbl.style = "color: grey";
        }
        chb.onchange = checkStatDop;
        lbl.appendChild(chb);
        lbl.appendChild(document.createTextNode(name));
        client.appendChild(lbl);
        if (check == 1) {
            chb.onchange();
        }
    }

}

function appendStatSoc(n, type, id, name, check, enabled) {
    var client;
    if (type == "cl") {
        client = document.getElementById("divStatsoc" + n);
        var lbl = document.createElement("label");
        var chb = document.createElement("input");
        chb.type = "checkbox";
        chb.id = "statsoc" + id + "_" + n;
        chb.name = "statsoc" + id + "_" + n;
        chb.value = id;
        if (check == 1) {
            chb.checked = "checked";
        }
        if (enabled == 0) {
            chb.onclick = doNothing;
            lbl.style = "color: grey";
        }
        lbl.appendChild(chb);
        lbl.appendChild(document.createTextNode(name));
        client.appendChild(lbl);
        if (check == 1) {
            chb.onchange();
        }
    } else if (type == "sub") {
        client = document.getElementById("divSubstatsoc" + n);
        var lbl = document.createElement("label");
        var chb = document.createElement("input");
        chb.type = "checkbox";
        chb.id = "subStatsoc" + id + "_" + n;
        chb.name = "subStatsoc" + id + "_" + n;
        chb.value = id;
        if (check == 1) {
            chb.checked = "checked";
        }
        if (enabled == 0) {
            chb.onclick = doNothing;
            lbl.style = "color: grey";
        }
        lbl.appendChild(chb);
        lbl.appendChild(document.createTextNode(name));
        client.appendChild(lbl);
        if (check == 1) {
            chb.onchange();
        }
    }
}

function checkStatDop() {
    var uslId = document.getElementById("uslId").value;
    var dop = this.value;
    var k = this.id.indexOf("_");
    var n = this.id.substring(k + 1);
    var datePriyom = document.getElementById("datePriyom").value;
    var type;
    if (this.id.substring(0, 7) == "statdop") {
        type = "cl";
    } else if (this.id.substring(0, 10) == "subStatdop") {
        type = "sub";
    }
    if (this.checked) {
        var url = "priyomaction?action=pod&date=" + datePriyom + "&usl=" + uslId + "&dop=" + dop + "&n=" + n + "&type=" + type;
        if (type == "cl") {
            var clId = document.getElementById("clId" + n);
            if (clId != null) {
                url += "&clid=" + clId.value;
            }
        } else if (type == "sub") {
            var subId = document.getElementById("subId" + n);
            if (subId != null) {
                url += "&clid=" + subId.value;
            }
        }
        requ(url, "GET");
    } else {
        var client;
        if (type == "cl") {
            client = document.getElementById("divStat" + n);
            if (client != null) {
                var divpod = document.getElementById("divStatpod" + dop + "_" + n);
                if (divpod != null) {
                    divpod.remove();
                }
            }
        } else if (type == "sub") {
            client = document.getElementById("divSubstat" + n);
            if (client != null) {
                var divpod = document.getElementById("divSubstatpod" + dop + "_" + n);
                if (divpod != null) {
                    divpod.remove();
                }
            }
        }
    }

}

function clearStatPod(dop, n, type) {
    if (type == "cl") {
        var client = document.getElementById("divStat" + n);
        if (client != null) {
            var divpod = document.getElementById("divStatpod" + dop + "_" + n);
            if (divpod != null) {
                divpod.remove();
            }
            var divpodnew = document.createElement("div");
            divpodnew.id = "divStatpod" + dop + "_" + n;
            client.appendChild(divpodnew);
        }
    } else if (type == "sub") {
        var client = document.getElementById("divSubstat" + n);
        if (client != null) {
            var divpod = document.getElementById("divSubstatpod" + dop + "_" + n);
            if (divpod != null) {
                divpod.remove();
            }
            var divpodnew = document.createElement("div");
            divpodnew.id = "divSubstatpod" + dop + "_" + n;
            client.appendChild(divpodnew);
        }
    }

}

function appendStatPod(statN, statType, statDop, statId, statName, statCheck, statEnabled) {
    if (statType == "cl") {
        var div = document.getElementById("divStatpod" + statDop + "_" + statN);
        var chb = document.createElement("input");
        var lbl = document.createElement("label");
        chb.type = "checkbox";
        chb.id = "statpod" + statId + "_" + statN;
        chb.name = "statpod" + statId + "_" + statN;
        chb.value = statId;
        if (statCheck == 1) {
            chb.checked = "checked";
        }
        if (statEnabled == 0) {
            chb.onclick = doNothing;
            lbl.style = "color: grey";
        }
        lbl.appendChild(chb);
        lbl.appendChild(document.createTextNode(statName));
        div.appendChild(lbl);

    } else if (statType == "sub") {
        var div = document.getElementById("divSubstatpod" + statDop + "_" + statN);
        var chb = document.createElement("input");
        var lbl = document.createElement("label");
        chb.type = "checkbox";
        chb.id = "subStatpod" + statId + "_" + statN;
        chb.name = "subStatpod" + statId + "_" + statN;
        chb.value = statId;
        if (statCheck == 1) {
            chb.checked = "checked";
        }
        if (statEnabled == 0) {
            chb.onclick = doNothing;
            lbl.style = "color: grey";
        }
        lbl.appendChild(chb);
        lbl.appendChild(document.createTextNode(statName));
        div.appendChild(lbl);
    }
}

function newClient() {
    var uslId = document.getElementById("uslId").value;
    var dialog = document.getElementById("searchClDialog");
    dialog.close();
    var n = document.getElementById("nomClPp").value;
    var type = document.getElementById("type").value;
    var fam = document.getElementById("fam").value;
    var nam = document.getElementById("nam").value;
    var patr = document.getElementById("patr").value;
    if (type.substring(0, 10) == "pmpkParent") {
        var npar = type.substring(10);
        var divParent = document.getElementById("div" + npar + "Parent" + n);
        divParent.innerHTML = "";
        divParent.appendChild(document.createTextNode(npar + ". "));
        var editImg = document.createElement("img");
        editImg.className = "btn";
        editImg.id = "edit" + npar + "Parent" + n;
        editImg.src = "img/edit.png";
        editImg.width = "15";
        editImg.alt = "Редактировать";
        editImg.title = "Редактировать";
        editImg.onclick = searchPmpkParent;
        divParent.appendChild(editImg);
        var delImg = document.createElement("img");
        delImg.className = "btn";
        delImg.id = "del" + npar + "Parent" + n;
        delImg.src = "img/delete.png";
        delImg.width = "15";
        delImg.alt = "Удалить";
        delImg.title = "Удалить";
        delImg.onclick = deleteParent;
        divParent.appendChild(delImg);
        divParent.appendChild(document.createElement("br"));
        var parentTypeSel = document.createElement("select");
        parentTypeSel.id = "parentTypeSel" + npar + "PmpkPar" + n;
        parentTypeSel.name = "parentTypeSel" + npar + "PmpkPar" + n;
        parentTypeSel.onchange = changeParentType;
        divParent.appendChild(parentTypeSel);
        var parentTypeId = document.createElement("input");
        parentTypeId.type = "hidden";
        parentTypeId.id = "parentTypeId" + npar + "PmpkPar" + n;
        parentTypeId.name = "parentTypeId" + npar + "PmpkPar" + n;
        divParent.appendChild(parentTypeId);
        divParent.appendChild(document.createElement("br"));
        divParent.appendChild(document.createTextNode("Фамилия: "));
        var inpFam = document.createElement("input");
        inpFam.name = "pmpkPar" + npar + "Fam" + n;
        inpFam.id = "pmpkPar" + npar + "Fam" + n;
        inpFam.type = "text";
        inpFam.value = fam;
        divParent.appendChild(inpFam);
        divParent.appendChild(document.createTextNode(" Имя: "));
        var inpNam = document.createElement("input");
        inpNam.name = "pmpkPar" + npar + "Nam" + n;
        inpNam.id = "pmpkPar" + npar + "Nam" + n;
        inpNam.type = "text";
        inpNam.value = nam;
        divParent.appendChild(inpNam);
        divParent.appendChild(document.createTextNode(" Отчество: "));
        var inpPatr = document.createElement("input");
        inpPatr.name = "pmpkPar" + npar + "Patr" + n;
        inpPatr.id = "pmpkPar" + npar + "Patr" + n;
        inpPatr.type = "text";
        inpPatr.value = patr;
        divParent.appendChild(inpPatr);
        divParent.appendChild(document.createElement("br"));
        divParent.appendChild(document.createTextNode("Район проживания: "));
        var selReg = document.createElement("select");
        selReg.id = "selReg" + npar + "PmpkPar" + n;
        selReg.onchange = changeRegCl;
        divParent.appendChild(selReg);
        var hidText = document.createElement("input");
        hidText.id = "reg" + npar + "PmpkPar" + n;
        hidText.name = "reg" + npar + "PmpkPar" + n;
        hidText.type = "hidden";
        divParent.appendChild(hidText);
        document.getElementById("pmpkPar" + npar + "Fam" + n).focus();
        var url = "priyomaction?action=region&n=" + n + "&type=" + type; // для заполнения списка районов проживания
        requ(url, "GET");
        var url = "search?type=parenttype&n=" + n + "&typecl=" + type; // для заполнения списка законных представителей
        requ(url, "GET");
    } else {
        if (type == "sub") {
            var subId = document.getElementById("subId" + n);
            if (subId == null) {
                subId = document.createElement("input");
                subId.type = "hidden";
                subId.id = "subId" + n;
                subId.name = "subId" + n;
                document.getElementById("sub" + n).appendChild(subId);
            } else {
                subId.value = "";
            }
        } else if (type == "cl") {
            var clId = document.getElementById("clId" + n);
            if (clId == null) {
                clId = document.createElement("input");
                clId.type = "hidden";
                clId.id = "clId" + n;
                clId.name = "clId" + n;
                document.getElementById("cl" + n).appendChild(clId);
            } else {
                clId.value = "";
            }
        }
        var cl = document.getElementById(type + n);
        var clKat = document.getElementById(type + "Kat" + n);
        var kat = document.getElementById("selKat").value;
        clKat.value = kat;

        if (type == "cl") {
            var client = document.getElementById("infoClient" + n);
            if (client == null) {
                client = document.createElement("div");
                client.id = "infoClient" + n;
                var img = document.getElementById("delCl" + n);
                if (img == null) {
                    img = document.createElement("img");
                    img.className = "btn";
                    img.id = "delCl" + n;
                    img.src = "img/delete.png";
                    img.width = "20";
                    img.alt = "Удалить";
                    img.title = "Удалить";
                    img.onclick = delClient;
                    cl.appendChild(img);
                }
                cl.appendChild(document.createElement("br"));
            } else {
                client.innerHTML = "";
            }
            if (kat == "parents") {
                var parentTypeSel = document.createElement("select");
                parentTypeSel.id = "parentTypeSel" + n;
                parentTypeSel.name = "parentTypeSel" + n;
                parentTypeSel.onchange = changeParentType;
                client.appendChild(parentTypeSel);
                var parentTypeId = document.createElement("input");
                parentTypeId.type = "hidden";
                parentTypeId.id = "parentTypeId" + n;
                parentTypeId.name = "parentTypeId" + n;
                client.appendChild(parentTypeId);
                client.appendChild(document.createElement("br"));
            }
            client.appendChild(document.createTextNode("Фамилия: "));
            var inpFam = document.createElement("input");
            inpFam.name = "clFam" + n;
            inpFam.id = "clFam" + n;
            inpFam.type = "text";
            inpFam.value = fam;
            client.appendChild(inpFam);
            client.appendChild(document.createTextNode(" Имя: "));
            var inpNam = document.createElement("input");
            inpNam.name = "clNam" + n;
            inpNam.id = "clNam" + n;
            inpNam.type = "text";
            inpNam.value = nam;
            client.appendChild(inpNam);
            client.appendChild(document.createTextNode(" Отчество: "));
            var inpPatr = document.createElement("input");
            inpPatr.name = "clPatr" + n;
            inpPatr.id = "clPatr" + n;
            inpPatr.type = "text";
            inpPatr.value = patr;
            client.appendChild(inpPatr);
            if (kat == "children") {
                client.appendChild(document.createElement("br"));
                client.appendChild(document.createTextNode("Дата рождения: "));
                var inpDr = document.createElement("input");
                inpDr.type = "date";
                inpDr.name = "clDr" + n;
                inpDr.id = "clDr" + n;
                client.appendChild(inpDr);

                var divPol = document.createElement("div");                
                divPol.appendChild(document.createTextNode("Пол: "));
                divPol.appendChild(document.createElement("br"));
                var labelM = document.createElement("label");
                labelM.id = "labelPolM" + n;
                var radioM = document.createElement("input");
                radioM.type = "radio";
                radioM.name = "polR" + n;
                radioM.id = "polR" + n;
                radioM.value = "м";
                labelM.appendChild(radioM);
                labelM.onclick = clickPol;
                labelM.appendChild(document.createTextNode("мужской"));
                divPol.appendChild(labelM);
                divPol.appendChild(document.createElement("br"));
                var labelW = document.createElement("label");
                labelW.id = "labelPolW" + n;
                var radioW = document.createElement("input");
                radioW.type = "radio";
                radioW.name = "polR" + n;
                radioM.id = "polR" + n;
                radioW.value = "ж";
                labelW.appendChild(radioW);
                labelW.onclick = clickPol;
                labelW.appendChild(document.createTextNode("женский"));
                divPol.appendChild(labelW);
                var hidPol = document.createElement("input");
                hidPol.type = "hidden";
                hidPol.name = "pol" + n;
                hidPol.id = "pol" + n;
                divPol.appendChild(hidPol);
                client.appendChild(divPol);

                client.appendChild(document.createElement("br"));
                client.appendChild(document.createTextNode("Район проживания: "));
                var selReg = document.createElement("select");
                selReg.id = "selRegCl" + n;
                selReg.onchange = changeRegCl;
                client.appendChild(selReg);
                var hidText = document.createElement("input");
                hidText.id = "regCl" + n;
                hidText.name = "regCl" + n;
                hidText.type = "hidden";
                client.appendChild(hidText);
            } else if (kat == "parents") {
                client.appendChild(document.createElement("br"));
                client.appendChild(document.createTextNode("Район проживания: "));
                var selReg = document.createElement("select");
                selReg.id = "selRegCl" + n;
                selReg.onchange = changeRegCl;
                client.appendChild(selReg);
                var hidText = document.createElement("input");
                hidText.id = "regCl" + n;
                hidText.name = "regCl" + n;
                hidText.type = "hidden";
                client.appendChild(hidText);
            } else if (kat == "ped") {
                client.appendChild(document.createElement("br"));
                client.appendChild(document.createTextNode("Район проживания: "));
                var selReg = document.createElement("select");
                selReg.id = "selRegCl" + n;
                selReg.onchange = changeRegCl;
                client.appendChild(selReg);
                var hidText = document.createElement("input");
                hidText.id = "regCl" + n;
                hidText.name = "regCl" + n;
                hidText.type = "hidden";
                client.appendChild(hidText);
                client.appendChild(document.createElement("br"));
                client.appendChild(document.createTextNode("Организация: "));
                var selOrg = document.createElement("select");
                selOrg.id = "selClOrg" + n;
                selOrg.onchange = orgChange;
                client.appendChild(selOrg);
                var inpOrg = document.createElement("input");
                inpOrg.type = "hidden";
                inpOrg.name = "clOrg" + n;
                inpOrg.id = "clOrg" + n;
                client.appendChild(inpOrg);
                client.appendChild(document.createTextNode("Должность: "));
                var selPedD = document.createElement("select");
                selPedD.id = "selClPedD" + n;
                selPedD.onchange = peddolgChange;
                client.appendChild(selPedD);
                var inpDol = document.createElement("input");
                inpDol.type = "hidden";
                inpDol.name = "clDol" + n;
                inpDol.id = "clDol" + n;
                client.appendChild(inpDol);
                var url = "priyomaction?action=org&n=" + n + "&type=" + type;
                requ(url, "GET");
            }
            client.appendChild(document.createElement("br"));
            cl.appendChild(client);
            if (kat == "parents") {
                var url = "search?type=parenttype&n=" + n + "&typecl=" + type; // для заполнения списка законных представителей
                requ(url, "GET");
            }

            var url = "priyomaction?action=region&n=" + n + "&type=" + type; // для заполнения списка районов проживания
            requ(url, "GET");

            var pmpk = document.getElementById("pmpk").value;
            if (pmpk == 1) {
                var divParents = document.createElement("div");
                divParents.id = "divParents" + n;
                var strongP = document.createElement("strong");
                strongP.appendChild(document.createTextNode("Законные представители: "));
                divParents.appendChild(strongP);
                var div1Parent = document.createElement("div");
                div1Parent.id = "div1Parent" + n;
                div1Parent.appendChild(document.createTextNode("1. "));
                var editImg = document.createElement("img");
                editImg.className = "btn";
                editImg.id = "edit1Parent" + n;
                editImg.src = "img/edit.png";
                editImg.width = "15";
                editImg.alt = "Редактировать";
                editImg.title = "Редактировать";
                editImg.onclick = searchPmpkParent;
                div1Parent.appendChild(editImg);
                var delImg = document.createElement("img");
                delImg.className = "btn";
                delImg.id = "del1Parent" + n;
                delImg.src = "img/delete.png";
                delImg.width = "15";
                delImg.alt = "Удалить";
                delImg.title = "Удалить";
                delImg.onclick = deleteParent;
                div1Parent.appendChild(delImg);
                divParents.appendChild(div1Parent);
                var div2Parent = document.createElement("div");
                div2Parent.id = "div2Parent" + n;
                div2Parent.appendChild(document.createTextNode("2. "));
                var editImg = document.createElement("img");
                editImg.className = "btn";
                editImg.id = "edit2Parent" + n;
                editImg.src = "img/edit.png";
                editImg.width = "15";
                editImg.alt = "Редактировать";
                editImg.title = "Редактировать";
                editImg.onclick = searchPmpkParent;
                div2Parent.appendChild(editImg);
                var delImg = document.createElement("img");
                delImg.className = "btn";
                delImg.id = "del2Parent" + n;
                delImg.src = "img/delete.png";
                delImg.width = "15";
                delImg.alt = "Удалить";
                delImg.title = "Удалить";
                delImg.onclick = deleteParent;
                div2Parent.appendChild(delImg);
                divParents.appendChild(div2Parent);
                client.appendChild(divParents);
            }

            var stat = document.getElementById("stat").value;
            if ((stat == 1) && (kat == "children")) {
                var divStat = document.createElement("div");
                divStat.id = "divStat" + n;
                var inp = document.createElement("input");
                inp.type = "hidden";
                inp.id = "check" + n;
                inp.name = "check" + n;
                divStat.appendChild(inp);
                var strong = document.createElement("strong");
                strong.appendChild(document.createTextNode("Статус ребенка"));
                divStat.appendChild(strong);
                divStat.appendChild(document.createElement("br"));
                var divOsn = document.createElement("div");
                divOsn.id = "divStatosn" + n;
                divStat.appendChild(divOsn);
                client.appendChild(divStat);
                cl.appendChild(client);
                var datePriyom = document.getElementById("datePriyom").value;
                var url = "priyomaction?action=statosn&date=" + datePriyom + "&usl=" + uslId + "&type=" + type + "&n=" + n;
                requ(url, "GET");
            }

            var pmpk = document.getElementById("pmpk").value;
            if (pmpk == 1) {
                var divFirstOvz = document.createElement("div");
                divFirstOvz.id = "divFirstOvz" + n;
                divFirstOvz.appendChild(document.createElement("br"));
                var lblFirstOvz = document.createElement("label");                
                var chbFirstOvz = document.createElement("input");
                chbFirstOvz.type = "checkbox";
                chbFirstOvz.id = "firstOvz" + n;
                chbFirstOvz.name = "firstOvz" + n;                
                lblFirstOvz.appendChild(chbFirstOvz);
                lblFirstOvz.appendChild(document.createTextNode("ОВЗ выявлены впервые"));
                divFirstOvz.appendChild(lblFirstOvz);
                divFirstOvz.appendChild(document.createElement("br"));
                divFirstOvz.appendChild(document.createElement("br"));
                
                var divOu = document.createElement("div");
                divOu.id = "divOu" + n;
                divOu.appendChild(document.createTextNode("Образовательное учреждение: "));
                var inpOu = document.createElement("input");
                inpOu.type = "hidden";
                inpOu.id = "ou" + n;
                inpOu.name = "ou" + n;
                inpOu.value = "1";
                divOu.appendChild(inpOu);
                var selOu = document.createElement("select");
                selOu.id = "selOu" + n;
                selOu.onchange = changeOU;
                var opt1 = document.createElement("option");
                opt1.value = "1";
                opt1.appendChild(document.createTextNode("посещает"));
                var opt2 = document.createElement("option");
                opt2.value = "0";
                opt2.appendChild(document.createTextNode("не посещает"));
                selOu.appendChild(opt1);
                selOu.appendChild(opt2);
                divOu.appendChild(selOu);

                var divPmpk = document.createElement("div");
                divPmpk.id = "divPmpk" + n;
                divPmpk.appendChild(document.createTextNode("Номер протокола: "));
                var inpNom = document.createElement("input");
                inpNom.type = "text";
                inpNom.id = "nomPr" + n;
                inpNom.name = "nomPr" + n;
                divPmpk.appendChild(inpNom);
                var divOp = document.createElement("div");
                divOp.id = "divOp" + n;
                divOp.appendChild(document.createTextNode("Вид образовательной программы: "));
                var selOptype = document.createElement("select");
                selOptype.id = "selOptype" + n;
                selOptype.onchange = changeOpType;
                divOp.appendChild(selOptype);
                var op = document.createElement("div");
                op.id = "op" + n;
                divOp.appendChild(op);
                var divVar = document.createElement("div");
                divVar.id = "var" + n;
                divOp.appendChild(divVar);
                var url = "priyomaction?action=optype&n=" + n;
                requ(url, "GET");
                divPmpk.appendChild(divOp);

                var rolesRight = document.getElementById("isVologda");
                if (rolesRight.value == "true") {
                    var divTpmpk = document.createElement("divTpmpk");
                    divTpmpk.id = "divTpmpk" + n;
                    divTpmpk.appendChild(document.createTextNode("Территориальная ПМПК: "));
                    var inpT = document.createElement("input");
                    inpT.type = "hidden";
                    inpT.id = "tpmpk" + n;
                    inpT.name = "tpmpk" + n;
                    inpT.value = 0;
                    divTpmpk.appendChild(inpT);
                    var selT = document.createElement("select");
                    selT.id = "selTpmpk" + n;
                    selT.onchange = changeTpmpk;
                    var optT1 = document.createElement("option");
                    optT1.value = "0";
                    optT1.appendChild(document.createTextNode("нет"));
                    var optT2 = document.createElement("option");
                    optT2.value = "1";
                    optT2.appendChild(document.createTextNode("по направлению"));
                    var optT3 = document.createElement("option");
                    optT3.value = "0";
                    optT3.appendChild(document.createTextNode("обжалование"));
                    selT.appendChild(optT1);
                    selT.appendChild(optT2);
                    selT.appendChild(optT3);
                    divTpmpk.appendChild(selT);
                    divPmpk.appendChild(divTpmpk);
                }
                var divGia = document.createElement("div");
                divGia.id = "divGia" + n;
                divGia.appendChild(document.createTextNode("Государственная итоговая аттестация: "));
                var gia = document.createElement("input");
                gia.type = "hidden";
                gia.id = "gia" + n;
                gia.name = "gia" + n;
                gia.value = 0;
                divGia.appendChild(gia);
                var selGia = document.createElement("select");
                selGia.id = "selGia" + n;
                selGia.onchange = changeGia;
                var optG1 = document.createElement("option");
                optG1.value = "0";
                optG1.appendChild(document.createTextNode("нет"));
                var optG2 = document.createElement("option");
                optG2.value = "1";
                optG2.appendChild(document.createTextNode("ГИА-9"));
                var optG3 = document.createElement("option");
                optG3.value = "2";
                optG3.appendChild(document.createTextNode("ГИА-11"));
                selGia.appendChild(optG1);
                selGia.appendChild(optG2);
                selGia.appendChild(optG3);
                divGia.appendChild(selGia);
                divPmpk.appendChild(divGia);

                var rolesRight = document.getElementById("isVologda");
                if (rolesRight.value == "true") {
                    var divIpr = document.createElement("div");
                    divIpr.id = "divIpr" + n;
                    var lblIpr = document.createElement("label");
                    var inpIpr = document.createElement("input");
                    inpIpr.type = "checkbox";
                    inpIpr.id = "ipr" + n;
                    inpIpr.name = "ipr" + n;
                    lblIpr.appendChild(inpIpr);
                    lblIpr.appendChild(document.createTextNode("Оказание содействия в разработке ИПР ребенка-инвалида"));
                    divIpr.appendChild(lblIpr);
                    divPmpk.appendChild(divIpr);
                }
                var divRek = document.createElement("div");
                divRek.id = "divRekomend" + n;
                var url = "priyomaction?action=rekomend&n=" + n;
                requ(url, "GET");
                divPmpk.appendChild(divRek);
                var divDopObs = document.createElement("div");
                divDopObs.id = "divDopObs" + n;
                var lblDopObs = document.createElement("label");
                var inpDopObs = document.createElement("input");
                inpDopObs.type = "checkbox";
                inpDopObs.id = "dopObsled" + n;
                inpDopObs.name = "dopObsled" + n;
                lblDopObs.appendChild(inpDopObs);
                lblDopObs.appendChild(document.createTextNode("Дополнительное обследование"));
                divDopObs.appendChild(lblDopObs);
                divPmpk.appendChild(divDopObs);
                var divSrok = document.createElement("div");
                divSrok.id = "divSrok" + n;
                var lblSrok = document.createElement("label");
                lblSrok.appendChild(document.createTextNode("Срок действия заключения: "));
                divSrok.appendChild(lblSrok);
                var selSrok = document.createElement("select");
                selSrok.id = "selSrok" + n;
                selSrok.name = "selSrok" + n;
                var optSrok1 = document.createElement("option");
                optSrok1.value = 0;
                optSrok1.appendChild(document.createTextNode("до окончания ступени обучения"));
                selSrok.appendChild(optSrok1);
                var optSrok2 = document.createElement("option");
                optSrok2.value = 1;
                optSrok2.appendChild(document.createTextNode("до"));
                selSrok.appendChild(optSrok2);
                selSrok.onchange = clickSrokDate;
                divSrok.appendChild(selSrok);
                divPmpk.appendChild(divSrok);

                var divSogl = document.createElement("div");
                divSogl.id = "divSogl" + n;
                divSogl.appendChild(document.createTextNode("С заключением: "));
                var inpSogl = document.createElement("input");
                inpSogl.type = "hidden";
                inpSogl.id = "sogl" + n;
                inpSogl.name = "sogl" + n;
                inpSogl.value = 1;
                divSogl.appendChild(inpSogl);
                var selSogl = document.createElement("select");
                selSogl.id = "selSogl" + n;
                selSogl.onchange = changeSogl;
                var optS1 = document.createElement("option");
                optS1.value = "1";
                optS1.appendChild(document.createTextNode("согласен"));
                var optS2 = document.createElement("option");
                optS2.value = "1";
                optS2.appendChild(document.createTextNode("не согласен"));
                selSogl.appendChild(optS1);
                selSogl.appendChild(optS2);
                divSogl.appendChild(selSogl);
                divPmpk.appendChild(divSogl);
                
                client.appendChild(divFirstOvz);
                client.appendChild(divOu);
                client.appendChild(divPmpk);
                cl.appendChild(client);
            }

            var monit = document.getElementById("monit").value;
            if (monit == 1) {
                var div = document.createElement("div");
                div.id = "divMonit" + n;
                div.appendChild(document.createTextNode("Оказанной услугой: "));
                var select = document.createElement("select");
                select.id = "selMonit" + n;
                select.onchange = changeMonit;
                var opt1 = document.createElement("option");
                opt1.value = "1";
                opt1.appendChild(document.createTextNode("удовлетворен"));
                var opt2 = document.createElement("option");
                opt2.value = "0";
                opt2.appendChild(document.createTextNode("не удовлетворен"));
                select.appendChild(opt1);
                select.appendChild(opt2);
                select.onchange = changeMonit;
                div.appendChild(select);
                var inp = document.createElement("input");
                inp.type = "hidden";
                inp.id = "monit" + n;
                inp.name = "monit" + n;
                inp.value = "1";
                div.append(inp);
                client.appendChild(div);
                cl.appendChild(client);
            }
            document.getElementById("clFam" + n).focus();

        } else if (type == "sub") {
            var sub = document.getElementById("infoSubject" + n);
            if (sub == null) {
                sub = document.createElement("div");
                sub.id = "infoSubject" + n;
                var img = document.getElementById("delSub" + n);
                if (img == null) {
                    img = document.createElement("img");
                    img.className = "btn";
                    img.id = "delSub" + n;
                    img.src = "img/delete.png";
                    img.width = "20";
                    img.alt = "Удалить";
                    img.title = "Удалить";
                    img.onclick = delSub;
                    cl.appendChild(img);
                }
                cl.appendChild(document.createElement("br"));
            } else {
                sub.innerHTML = "";
            }

            sub.appendChild(document.createTextNode("Фамилия: "));
            var inpFam = document.createElement("input");
            inpFam.name = "subFam" + n;
            inpFam.id = "subFam" + n;
            inpFam.type = "text";
            inpFam.value = fam;
            sub.appendChild(inpFam);
            sub.appendChild(document.createTextNode(" Имя: "));
            var inpNam = document.createElement("input");
            inpNam.name = "subNam" + n;
            inpNam.id = "subNam" + n;
            inpNam.type = "text";
            inpNam.value = nam;
            sub.appendChild(inpNam);
            sub.appendChild(document.createTextNode(" Отчество: "));
            var inpPatr = document.createElement("input");
            inpPatr.name = "subPatr" + n;
            inpPatr.id = "subPatr" + n;
            inpPatr.type = "text";
            inpPatr.value = patr;
            sub.appendChild(inpPatr);

            sub.appendChild(document.createElement("br"));
            sub.appendChild(document.createTextNode("Дата рождения: "));
            var inpDr = document.createElement("input");
            inpDr.type = "date";
            inpDr.name = "subDr" + n;
            inpDr.id = "subDr" + n;
            sub.appendChild(inpDr);

            var divSubPol = document.createElement("div");  
            divSubPol.appendChild(document.createTextNode("Пол: "));
            divSubPol.appendChild(document.createElement("br"));
            var labelM = document.createElement("label");
            labelM.id = "labelSubPolM" + n;
            var radioM = document.createElement("input");
            radioM.type = "radio";
            radioM.name = "subPolR" + n;
            radioM.id = "subPolR" + n;
            radioM.value = "м";            
            labelM.appendChild(radioM);
            labelM.onclick = clickPol;
            labelM.appendChild(document.createTextNode("мужской"));
            divSubPol.appendChild(labelM);
            divSubPol.appendChild(document.createElement("br"));
            var labelW = document.createElement("label");
            labelW.id = "labelSubPolW" + n;
            var radioW = document.createElement("input");
            radioW.type = "radio";
            radioW.name = "subPolR" + n;
            radioW.id = "subPolR" + n;
            radioW.value = "ж";
            labelW.appendChild(radioW);
            labelW.onclick = clickPol;
            labelW.appendChild(document.createTextNode("женский"));
            divSubPol.appendChild(labelW);
            var hidPol = document.createElement("input");
            hidPol.type = "hidden";
            hidPol.name = "subPol" + n;
            hidPol.id = "subPol" + n;
            divSubPol.appendChild(hidPol);
            sub.appendChild(divSubPol);

            sub.appendChild(document.createElement("br"));
            sub.appendChild(document.createTextNode("Район проживания: "));
            var selReg = document.createElement("select");
            selReg.id = "selRegSub" + n;
            selReg.onchange = changeRegCl;
            sub.appendChild(selReg);
            var hidText = document.createElement("input");
            hidText.id = "regSub" + n;
            hidText.name = "regSub" + n;
            hidText.type = "hidden";
            sub.appendChild(hidText);

            sub.appendChild(document.createElement("br"));
            cl.appendChild(sub);
            var url = "priyomaction?action=region&n=" + n + "&type=" + type; // для заполнения списка районов проживания
            requ(url, "GET");

            var stat = document.getElementById("stat").value;
            if (stat == 1) {
                var divStat = document.createElement("div");
                divStat.id = "divSubstat" + n;
                var inp = document.createElement("input");
                inp.type = "hidden";
                inp.id = "checkSub" + n;
                inp.name = "checkSub" + n;
                divStat.appendChild(inp);
                var strong = document.createElement("strong");
                strong.appendChild(document.createTextNode("Статус ребенка"));
                divStat.appendChild(strong);
                divStat.appendChild(document.createElement("br"));

                var divOsn = document.createElement("div");
                divOsn.id = "divSubstatosn" + n;
                divStat.appendChild(divOsn);
                sub.appendChild(divStat);
                cl.appendChild(sub);
                var datePriyom = document.getElementById("datePriyom").value;
                var url = "priyomaction?action=statosn&date=" + datePriyom + "&usl=" + uslId + "&type=" + type + "&n=" + n;
                requ(url, "GET");
            }
            document.getElementById("subFam" + n).focus();
        }
    }
}

function changeRegCl() {
    var n;
    var idReg;
    if (this.id.substring(0, 8) == "selRegCl") {
        n = this.id.substring(8);
        idReg = document.getElementById("regCl" + n);
    } else if (this.id.substring(0, 9) == "selRegSub") {
        n = this.id.substring(9);
        idReg = document.getElementById("regSub" + n);
    } else if (this.id.substring(0, 14) == "selReg1PmpkPar") {
        n = this.id.substring(14);
        idReg = document.getElementById("reg1PmpkPar" + n);
    } else if (this.id.substring(0, 14) == "selReg2PmpkPar") {
        n = this.id.substring(14);
        idReg = document.getElementById("reg2PmpkPar" + n);
    }
    idReg.setAttribute("value", this.value);
}

function orgChange() {
    var n;
    var inp;
    if (this.id.substring(0, 8) == "selClOrg") {
        n = this.id.substring(8);
        inp = document.getElementById("clOrg" + n);
    } else if (this.id.substring(0, 9) == "selSubOrg") {
        n = this.id.substring(9);
        inp = document.getElementById("subOrg" + n);
    }
    inp.value = this.value;
}

function peddolgChange() {
    var n;
    var inp;
    if (this.id.substring(0, 9) == "selClPedD") {
        n = this.id.substring(9);
        inp = document.getElementById("clDol" + n);
    } else if (this.id.substring(0, 10) == "selSubPedD") {
        n = this.id.substring(10);
        inp = document.getElementById("subDol" + n);
    }
    inp.value = this.value;
}

function appendRegionCl(rId, rName, rN, rType) {
    var selReg;
    if (rType == "cl") {
        selReg = document.getElementById("selRegCl" + rN);
    } else if (rType == "sub") {
        selReg = document.getElementById("selRegSub" + rN);
    } else if (rType.substring(0, 10) == "pmpkParent") {
        var npar = rType.substring(10);
        selReg = document.getElementById("selReg" + npar + "PmpkPar" + rN);
    }
    var opt = document.createElement("option");
    selReg.appendChild(opt);
    opt.setAttribute("value", rId);
    opt.appendChild(document.createTextNode(rName));
    selReg.onchange();
}

function appendParentType(ptId, ptName, ptN, ptType) {
    var parentTypeSel;
    if (ptType == "cl") {
        parentTypeSel = document.getElementById("parentTypeSel" + ptN);
    } else if (ptType == "sub") {
        parentTypeSel = document.getElementById("parentTypeSubSel" + ptN);
    } else if (ptType.substring(0, 10) == "pmpkParent") {
        var npar = ptType.substring(10);
        parentTypeSel = document.getElementById("parentTypeSel" + npar + "PmpkPar" + ptN);
    }
    var opt = document.createElement("option");
    parentTypeSel.appendChild(opt);
    opt.setAttribute("value", ptId);
    opt.appendChild(document.createTextNode(ptName));
    parentTypeSel.onchange();
}

function appendOrgN(orgN, orgType, orgId, orgName) {
    var selOrg;
    if (orgType == "cl") {
        selOrg = document.getElementById("selClOrg" + orgN);
    } else if (orgType == "sub") {
        selOrg = document.getElementById("selSubOrg" + orgN);
    }

    var opt = document.createElement("option");
    opt.value = orgId;
    opt.appendChild(document.createTextNode(orgName));
    selOrg.appendChild(opt);
    selOrg.onchange();
}

function appendDolgN(dolgN, dolgType, dolgId, dolgName) {
    var selPedD;
    if (dolgType == "cl") {
        selPedD = document.getElementById("selClPedD" + dolgN);
    } else if (dolgType == "sub") {
        selPedD = document.getElementById("selSubPedD" + dolgN);
    }
    var opt = document.createElement("option");
    opt.value = dolgId;
    opt.appendChild(document.createTextNode(dolgName));
    selPedD.appendChild(opt);
    selPedD.onchange();
}

function clickClient() {
    var div = this.parentNode;
    if (div != null) {
        var n;
        var kat;
        var id;
        if (div.id.substring(0, 10) == "div1Parent") {
            n = div.id.substring(10);
            kat = "parents";
            id = document.getElementById("pmpk1ParId" + n);
        } else if (div.id.substring(0, 10) == "div2Parent") {
            n = div.id.substring(10);
            kat = "parents";
            id = document.getElementById("pmpk2ParId" + n);
        } else if (div.id.substring(0, 10) == "infoClient") {
            n = div.id.substring(10);
            kat = document.getElementById("clKat" + n).value;
            id = document.getElementById("clId" + n);
        } else if (div.id.substring(0, 11) == "infoSubject") {
            n = div.id.substring(11);
            kat = document.getElementById("subKat" + n).value;
            id = document.getElementById("subId" + n);
        }

        if (kat != null) {
            window.open("client?kat=" + kat + "&id=" + id.value, "_blank");
        }
    }
}

function clickSrokDate() {
    if (this.value == 1) {
        var n = this.id.substring(7);
        var inpDate = document.createElement("input");
        inpDate.id = "srokDate" + n;
        inpDate.name = "srokDate" + n;
        inpDate.type = "date";
        this.parentNode.appendChild(inpDate);
    }
}

function searchPmpkParent() {
    var dialog = document.getElementById("searchClDialog");
    dialog.showModal();
    var type = document.getElementById("type");
    type.value = "pmpkParent" + this.parentNode.id.substring(3, 4);
    var n = this.parentNode.id.substring(10);
    var kat = "parents";

    var dialogKat = document.getElementById("selKat");
    var opts = dialogKat.getElementsByTagName("option");
    for (loop = 0; loop < opts.length; loop++) {
        if (opts[loop].value == kat) {
            opts[loop].selected = "selected";
        }
    }
    dialogKat.disabled = "disabled";
    var nomPp = document.getElementById("nomClPp");
    nomPp.value = n;
    var fam = document.getElementById("fam");
    fam.value = "";
    var nam = document.getElementById("nam");
    nam.value = "";
    var patr = document.getElementById("patr");
    patr.value = "";
    search();
    clearFamilyDialog();
    findFamilyPmpkParent();
}

function deleteParent() {
    var div = this.parentNode;
    if (div != null) {
        div.innerHTML = "";
        var i = div.id.substring(3, 4);
        var n = div.id.substring(10);
        div.appendChild(document.createTextNode(i + ". "));
        var editImg = document.createElement("img");
        editImg.className = "btn";
        editImg.id = "edit" + i + "Parent" + n;
        editImg.src = "img/edit.png";
        editImg.width = "15";
        editImg.alt = "Редактировать";
        editImg.title = "Редактировать";
        editImg.onclick = searchPmpkParent;
        div.appendChild(editImg);
        var delImg = document.createElement("img");
        delImg.className = "btn";
        delImg.id = "del" + i + "Parent" + n;
        delImg.src = "img/delete.png";
        delImg.width = "15";
        delImg.alt = "Удалить";
        delImg.title = "Удалить";
        delImg.onclick = deleteParent;
        div.appendChild(delImg);
    }
}

function findFamily() {
    clearFamilyDialog();
    var subj = document.getElementById("subj").value;
    var request = "";
    if (subj == 1) {
        var inp = document.getElementsByTagName("input");
        for (loop1 = 0; loop1 < inp.length; loop1++) {
            if (inp[loop1].id.substring(0, 4) == "clId") {
                if (inp[loop1].value != "") {
                    var kat = document.getElementById("clKat" + inp[loop1].id.substring(4)).value;
                    if (kat != "ped") {
                        request += kat + "-" + inp[loop1].value + ";";
                    }
                }
            } else if (inp[loop1].id.substring(0, 5) == "subId") {
                if (inp[loop1].value != "") {
                    var kat = document.getElementById("subKat" + inp[loop1].id.substring(5)).value;
                    request += kat + "-" + inp[loop1].value + ";";
                }
            }
        }
    }
    var selKat = document.getElementById("selKat").value;
    if (request != "") {
        var url = "search?type=findfamily&req=" + request + "&kat=" + selKat;
        requ(url, "GET");
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

function clearFamilyDialog() {
    var div = document.getElementById("family");
    div.innerHTML = "";
    var strong = document.createElement("strong");
    strong.appendChild(document.createTextNode("Семья: "));
    div.appendChild(strong);
    var tab = document.createElement("table");
    tab.id = document.getElementById("dialogFamilyTable");
    tab.className = "regular";
    var thead = document.createElement("thead");
    thead.id = "famhead";
    var tbody = document.createElement("tbody");
    tbody.id = "fambody";
    tab.appendChild(thead);
    tab.appendChild(tbody);
    div.appendChild(tab);
}

function appendFamilyHead(head) {
    var thead = document.getElementById("famhead");
    var tr = document.createElement("tr");
    for (loop = 0; loop < head.childNodes.length; loop++) {
        var td = document.createElement("td");
        td.appendChild(document.createTextNode(head.childNodes[loop].childNodes[0].nodeValue));
        tr.appendChild(td);
    }
    thead.appendChild(tr);
}

function appendFamilyBody(body, onclickFunc) {
    var tbody = document.getElementById("fambody");
    var tr = document.createElement("tr");
    for (loop1 = 0; loop1 < body.childNodes.length; loop1++) {
        var td = document.createElement("td");
        td.appendChild(document.createTextNode(body.childNodes[loop1].childNodes[0].nodeValue));
        tr.appendChild(td);
    }
    tr.onclick = onclickFunc;
    tbody.appendChild(tr);
}

function findFamilyPmpkParent() {
    var request = "";
    var n = document.getElementById("nomClPp").value;
    var clId = document.getElementById("clId" + n);
    if (clId != null) {
        request += "children-" + clId.value + ";";
    }
    if (request != "") {
        var url = "search?type=findfamily&req=" + request + "&kat=parents";
        requ(url, "GET");
    }

}

function statPodTest(dop, n, type) {
    var divpod;
    if (type == "cl") {
        var client = document.getElementById("divStat" + n);
        if (client != null) {
            divpod = document.getElementById("divStatpod" + dop + "_" + n);


        }
    } else if (type == "sub") {
        var client = document.getElementById("divSubstat" + n);
        if (client != null) {
            var divpod = document.getElementById("divSubstatpod" + dop + "_" + n);
        }
    }

    if (divpod != null) {
        var pods = divpod.getElementsByTagName("input");
        var flag = false;
        for (loop = 0; loop < pods.length; loop++) {
            if (pods[loop].checked) {
                flag = true;
            }
        }
        if (!flag) {
            divpod.innerHTML = "";
        }
    }

}

function changeParentType() {
    var n;
    var idPT;
    if (this.id.substring(0, 13) == "parentTypeSel") {
        n = this.id.substring(13);
        idPT = document.getElementById("parentTypeId" + n);
    } else if (this.id.substring(0, 16) == "parentTypeSubSel") {
        n = this.id.substring(16);
        idPT = document.getElementById("parentTypeSubId" + n);
    } else if (this.id.substring(0, 21) == "parentTypeSel1PmpkPar") {
        n = this.id.substring(21);
        idPT = document.getElementById("parentTypeId1PmpkPar" + n);
    } else if (this.id.substring(0, 21) == "parentTypeSel2PmpkPar") {
        n = this.id.substring(21);
        idPT = document.getElementById("parentTypeId2PmpkPar" + n);
    }
    idPT.setAttribute("value", this.value);
}

function clickPol() {
    if ((this.id.substring(0, 11) == "labelSubPol") 
            && (this.childNodes[0].checked)) {
        var n = this.id.substring(12);
        var subPol = document.getElementById("subPol" + n);
        subPol.value = this.childNodes[0].value;
    } else if ((this.id.substring(0, 8) == "labelPol") 
            && (this.childNodes[0].checked)) {
        var n = this.id.substring(9);
        var pol = document.getElementById("pol" + n);
        pol.value = this.childNodes[0].value;
    }
}

function doNothing() {
    return false;
}

function actDatePr() {
    var kol = document.getElementById("kol");
    if (kol != null) {
        if ((kol.value != "") && (kol.value > 0)) {
            actkurs();
        }
    }
}