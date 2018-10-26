/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function changeUsl() {
    var usl = document.getElementById("selUsl").value;
    document.getElementById("payUslId").value = usl;
    var url = "payaction?action=param&uslid=" + usl;
    clearParam();
    clearGroup();
    clearLesson();
    clearSpecialists();
    clearClients();
    clearKalendar();
    clearButton();
    requ(url, "GET");
}

function initPayView() {
    var a = document.getElementsByTagName("img");
    for (loop = 0; loop < a.length; loop++) {
        if (a[loop].id.substring(0, 6) == "plusCl") {
            a[loop].onclick = addClient;
        } else if (a[loop].id.substring(0, 5) == "delCl") {
            a[loop].onclick = delClient;
        } else if (a[loop].id.substring(0, 8) == "plusSotr") {
            a[loop].onclick = addSotr;
        } else if (a[loop].id.substring(0, 7) == "delSotr") {
            a[loop].onclick = delSotr;
        } else if (a[loop].id.substring(0, 6) == "editCl") {
            a[loop].onclick = searchClient;
        }
    }

    var select = document.getElementsByTagName("select");
    for (loop = 0; loop < select.length; loop++) {
        if (select[loop].id.substring(0, 8) == "selClOrg") {
            select[loop].onchange = orgChange;
        } else if (select[loop].id.substring(0, 9) == "selClPedD") {
            select[loop].onchange = peddolgChange;
        } else if (select[loop].id.substring(0, 8) == "selRegCl") {
            select[loop].onchange = changeRegCl;
        } else if (select[loop].id.substring(0, 8) == "selDolgn") {
            select[loop].onchange = dolgnSelect;
        } else if (select[loop].id.substring(0, 7) == "selSotr") {
            select[loop].onchange = sotrSelect;
        }
    }

    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        if (inp[loop].id.substring(0, 9) == "findDogCl") {
            inp[loop].onclick = findParent;
        }
    }
}

function initDogReestr() {
    searchDog();
}

function initDogView() {
    var btn = document.getElementById("findDogCl1");
    btn.onclick = findParent;
}

function searchDog() {
    var nom = document.getElementById("searchN").value;
    var date = document.getElementById("searchD").value;
    var fam = document.getElementById("searchFam").value;
    var name = document.getElementById("searchName").value;
    var patr = document.getElementById("searchPatr").value;
    var url = "search?type=paydog&nom=" + nom + "&date=" + date + "&fam=" + fam
            + "&name=" + name + "&patr=" + patr;
    requ(url, "GET");
}

function findParent() {
    var dialog = document.getElementById("searchClDialog");
    dialog.showModal();
    var n = this.id.substring(9);
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
    var type = document.getElementById("type");
    type.value = "dogovor";
    search();
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
        var param = responseXML.getElementsByTagName("param")[0];
        // должности
        var dolgns = responseXML.getElementsByTagName("dolgns")[0];
        // сотрудники
        var sotruds = responseXML.getElementsByTagName("sotruds")[0];
        // клиенты
        var clients = responseXML.getElementsByTagName("clients")[0];
        // основные статусы
        var statosns = responseXML.getElementsByTagName("statosns")[0];
        // социальные статусы
        var statsocs = responseXML.getElementsByTagName("statsocs")[0];
        // дополнительные статусы
        var statdops = responseXML.getElementsByTagName("statdops")[0];
        // подстатус
        var statpod = responseXML.getElementsByTagName("statpod")[0];
        // районы проживания клиентов
        var regionsCl = responseXML.getElementsByTagName("regionsCl")[0];
        // телефон
        var telephon = responseXML.getElementsByTagName("telephon")[0];
        // договоры
        var dogovors = responseXML.getElementsByTagName("dogovors")[0];

        if (param != null) {
            if (param.childNodes.length > 0) {
                var stat = param.getElementsByTagName("stat")[0];
                var group = param.getElementsByTagName("group")[0];
                var lesson = param.getElementsByTagName("lesson")[0];
                appendParam(stat.childNodes[0].nodeValue, group.childNodes[0].nodeValue,
                        lesson.childNodes[0].nodeValue);
            }
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
        } else if (dolgns != null) {
            if (dolgns.childNodes.length > 0) {
                for (loop = 0; loop < dolgns.childNodes.length; loop++) {
                    var dolgn = dolgns.childNodes[loop];
                    var dId = dolgn.getElementsByTagName("id")[0];
                    var dName = dolgn.getElementsByTagName("name")[0];
                    appendDolgn(1, dId.childNodes[0].nodeValue, dName.childNodes[0].nodeValue);
                }
                document.getElementById("selDolgn1").onchange();
            }
        } else if (sotruds != null) {  // если список сотрудников
            var sotrud1 = sotruds.childNodes[0];
            var sN1 = sotrud1.getElementsByTagName("n")[0];
            clearSotr(sN1.childNodes[0].nodeValue);
            if (sotruds.childNodes.length > 0) {
                for (loop = 0; loop < sotruds.childNodes.length; loop++) {
                    var sotrud = sotruds.childNodes[loop];
                    var sN = sotrud.getElementsByTagName("n")[0];
                    var sId = sotrud.getElementsByTagName("id")[0];
                    var sFIO = sotrud.getElementsByTagName("fio")[0];
                    appendSotrud(sN.childNodes[0].nodeValue, sId.childNodes[0].nodeValue, sFIO.childNodes[0].nodeValue);
                }
                document.getElementById("selSotr" + sN1.childNodes[0].nodeValue).onchange();
            }
        } else if (statosns != null) {
            if (statosns.childNodes.length > 0) {
                for (loop = 0; loop < statosns.childNodes.length; loop++) {
                    var statosn = statosns.childNodes[loop];
                    var statosnN = statosn.getElementsByTagName("n")[0];
                    var statosnType = statosn.getElementsByTagName("type")[0];
                    var statosnId = statosn.getElementsByTagName("id")[0];
                    var statosnName = statosn.getElementsByTagName("name")[0];
                    appendStatOsn(statosnN.childNodes[0].nodeValue, statosnType.childNodes[0].nodeValue,
                            statosnId.childNodes[0].nodeValue, statosnName.childNodes[0].nodeValue);
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
                    appendStatDop(statdopN.childNodes[0].nodeValue, statdopType.childNodes[0].nodeValue,
                            statdopId.childNodes[0].nodeValue, statdopName.childNodes[0].nodeValue);
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
                    appendStatSoc(statsocN.childNodes[0].nodeValue, statsocType.childNodes[0].nodeValue,
                            statsocId.childNodes[0].nodeValue, statsocName.childNodes[0].nodeValue);
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
                    appendStatPod(statN.childNodes[0].nodeValue, statType.childNodes[0].nodeValue, statDop1.childNodes[0].nodeValue,
                            statId.childNodes[0].nodeValue, statName.childNodes[0].nodeValue);
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
        } else if (telephon != null) {
            var n = telephon.getElementsByTagName("n")[0];
            var tel = telephon.getElementsByTagName("tel")[0];
            appendTelephon(n.childNodes[0].nodeValue, tel.childNodes[0].nodeValue);
        } else if (dogovors != null) {
            clearDogTable();
            if (dogovors.childNodes.length > 0) {
                var head = dogovors.childNodes[0];
                appendHead(head);
                for (loop = 1; loop < dogovors.childNodes.length; loop++) {
                    var dogovor = dogovors.childNodes[loop];
                    appendBody(dogovor, selectDogovor);
                }
            }
            dogovors = null;
        }
    }
}

function appendParam(stat, group, lesson) {
    var inpStat = document.getElementById("stat");
    if (inpStat != null) {
        inpStat.value = stat;
    }
    var inpGroup = document.getElementById("group");
    if (inpGroup != null) {
        inpGroup.value = group;
        if (group == 1) {
            appendGroup();
        }
    }
    var inpLesson = document.getElementById("lesson");
    if (inpLesson != null) {
        inpLesson.value = lesson;
        if (lesson == 1) {
            appendLesson();
        }
    }
    appendDivSpecialists();
    appendDivClients();
    appendPlusClient();
    appendDivButton();
}

function clearParam() {
    var inpStat = document.getElementById("stat");
    if (inpStat != null) {
        inpStat.value = 0;
    }
    var inpGroup = document.getElementById("group");
    if (inpGroup != null) {
        inpGroup.value = 0;
    }
    var inpLesson = document.getElementById("lesson");
    if (inpLesson != null) {
        inpLesson.value = 0;
    }
}


function clearGroup() {
    var div = document.getElementById("divGroup");
    if (div != null) {
        div.innerHTML = "";
    }
}

function clearLesson() {
    var div = document.getElementById("divLesson");
    if (div != null) {
        div.innerHTML = "";
    }
}

function clearButton() {
    var div = document.getElementById("divButton");
    if (div != null) {
        div.innerHTML = "";
    }
}

function appendGroup() {
    var div = document.getElementById("divGroup");
    if (div != null) {
        var lbl = document.createElement("label");
        lbl.appendChild(document.createTextNode("Название группы: "));
        var inp = document.createElement("input");
        inp.type = "text";
        inp.id = "inpGroup";
        inp.name = "inpGroup";
        lbl.appendChild(inp);
        div.appendChild(lbl);
    }
}

function appendLesson() {
    var div = document.getElementById("divLesson");
    if (div != null) {
        var lbl = document.createElement("label");
        lbl.appendChild(document.createTextNode("Дата начала занятий: "));
        var dat = document.createElement("input");
        dat.type = "date";
        dat.id = "dateN";
        dat.name = "dateN";
        dat.disabled = "disabled";
        lbl.appendChild(dat);
        div.appendChild(lbl);
        div.appendChild(document.createElement("br"));
        var lbl2 = document.createElement("label");
        lbl2.appendChild(document.createTextNode("Количество занятий: "));
        var inp2 = document.createElement("input");
        inp2.type = "text";
        inp2.id = "kol";
        inp2.name = "kol";
        inp2.value = 0;
        inp2.disabled = "disabled";
        lbl2.appendChild(inp2);
        div.appendChild(lbl2);
        var dates = document.createElement("input");
        dates.type = "hidden";
        dates.id = "dates";
        dates.name = "dates";
        div.appendChild(dates);

        viewKalendar();
    }
}

function viewKalendar() {
    var div = document.getElementById("kalendar");
    div.style = "display:block;";
    var weekendTd = document.getElementsByClassName("weekend");
    var regulardayTd = document.getElementsByClassName("regularday");
    for (loop = 0; loop < weekendTd.length; loop++) {
        weekendTd[loop].onclick = dateclick;
    }
    for (loop = 0; loop < regulardayTd.length; loop++) {
        regulardayTd[loop].onclick = dateclick;
    }
}

function dateclick() {
    var kol = document.getElementById("kol");
    if (!this.classList.contains("checkedday")) {
        this.classList.add("checkedday");
        var n = Number(kol.value);
        n++;
        kol.value = n;
    } else {
        this.classList.remove("checkedday");
        var n = Number(kol.value);
        n--;
        kol.value = n;
    }
    // проверка на дату начала занятий
    var checked = document.getElementsByClassName("checkedday");
    if (checked.length != 0) {
        var minD = checked[0].id;
        var n1 = minD.indexOf(".");
        var n2 = minD.lastIndexOf(".");
        var minDate = new Date(1 * minD.substr(n2 + 1), 1 * minD.substr(n1 + 1, n2) - 1, 1 * minD.substr(0, n1), 0, 0, 0);
        for (loop = 1; loop < checked.length; loop++) {
            var d = checked[loop].id;
            var k1 = d.indexOf(".");
            var k2 = d.lastIndexOf(".");
            var date = new Date(1 * d.substr(k2 + 1), 1 * d.substr(k1 + 1, k2) - 1, 1 * d.substr(0, k1), 0, 0, 0);
            if (date.getTime() - minDate.getTime() < 0) {
                minDate = date;
            }
        }
        var month = Number(minDate.getMonth() + 1);
        if (month < 10) {
            month = "0" + month;
        }
        var day = Number(minDate.getDate());
        if (day < 10) {
            day = "0" + day;
        }
        document.getElementById("dateN").value = minDate.getFullYear() + "-" + month + "-" + day;
    } else {
        document.getElementById("dateN").value = "";
    }
}

function appendDivSpecialists() {
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
    var selUsl = document.getElementById("selUsl");
    if (selUsl != null) {
        var usl = selUsl.value;
    }
    var url = "payaction?action=dolgn&uslid=" + usl;
    requ(url, "GET");
}

function appendPlusSotr() {
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

function appendPlusClient() {
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

function clearSpecialists() {
    var div = document.getElementById("divSpecialists");
    if (div != null) {
        div.innerHTML = "";
    }

    var div = document.getElementById("divPlusSotr");
    if (div != null) {
        div.innerHTML = "";
    }
}

function clearClients() {
    var div = document.getElementById("divClients");
    if (div != null) {
        div.innerHTML = "";
    }

    var div = document.getElementById("divPlusClient");
    if (div != null) {
        div.innerHTML = "";
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

function addSotr() {
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

function dolgnSelect() { // реакция на выбор должности
    var idD = this.value;
    var n = this.id.substring(8);
    var inp = document.getElementById("dolgnId" + n);
    inp.value = idD;
    // запрос на список сотрудников
    var url = "priyomaction?action=sotr&id=" + idD + "&n=" + n;
    requ(url, "GET");
}

function sotrSelect() {
    var inp = document.getElementById("sotrId" + this.id.substring(7));
    inp.value = this.value;
}

function delSotr() {
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

function searchClient() {
    var dialog = document.getElementById("searchClDialog");
    dialog.showModal();
    var n = this.parentNode.id.substring(2);
    var kat = document.getElementById("clKat" + n).value;

    if (kat == "") {
        kat = "children";
    }
    var dialogKat = document.getElementById("selKat");
    var opts = dialogKat.getElementsByTagName("option");
    for (loop = 0; loop < opts.length; loop++) {
        if (opts[loop].value == kat) {
            opts[loop].selected = "selected";
        }
    }
    var nomPp = document.getElementById("nomClPp");
    nomPp.value = n;
    var fam = document.getElementById("fam");
    fam.value = "";
    var nam = document.getElementById("nam");
    nam.value = "";
    var patr = document.getElementById("patr");
    patr.value = "";
    var type = document.getElementById("type");
    type.value = "client";
    search();
}

function search() {
    var kat = document.getElementById("selKat").value;
    document.getElementById("katCl").value = kat;
    var fam = document.getElementById("fam").value;
    var nam = document.getElementById("nam").value;
    var patr = document.getElementById("patr").value;
    var url = "search?type=client&kat=" + kat + "&fam=" + fam + "&nam=" + nam + "&patr=" + patr;
    requ(url, "GET");
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

function appendDolgn(dN, dId, dName) {
    var sel = document.getElementById("selDolgn" + dN);
    var opt = document.createElement("option");
    opt.value = dId;
    opt.appendChild(document.createTextNode(dName));
    if (sel != null) {
        sel.appendChild(opt);
    }
}

function appendSotrud(sN, sId, sFIO) {
    var sel = document.getElementById("selSotr" + sN);
    var opt = document.createElement("option");
    opt.value = sId;
    opt.appendChild(document.createTextNode(sFIO));
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

function clearKalendar() {
    var div = document.getElementById("kalendar");
    div.style = "display:none;";
    var checked = document.getElementsByClassName("checkedday");
    var cl = new Array();
    for (loop = 0; loop < checked.length; loop++) {
        cl.push(checked[loop]);
    }
    for (loop = 0; loop < cl.length; loop++) {
        cl[loop].classList.remove("checkedday");
    }

}

function selectClient() {
    var dialog = document.getElementById("searchClDialog");
    dialog.close();
    var nom = document.getElementById("nomClPp");
    var n;
    if (nom != null) {
        n = nom.value;
    }
    var kat = document.getElementById("katCl").value;
    var type = document.getElementById("type").value;

    if (type == "incl") {
        var clId = document.getElementById("clId");
        if (clId != null) {
            clId.value = this.childNodes[0].childNodes[0].nodeValue;
        }
        var divClientInfo = document.getElementById("divClientInfo");
        if (divClientInfo != null) {
            divClientInfo.innerHTML = "";
            var strong = document.createElement("strong");
            strong.appendChild(document.createTextNode(this.childNodes[1].childNodes[0].nodeValue));
            divClientInfo.appendChild(strong);
            divClientInfo.appendChild(document.createElement("br"));
            var lblCl = document.createElement("label");
            lblCl.className = "clickable";
            lblCl.onclick = clickClient;
            if (kat == "children") {
                lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                        this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue + " " +
                        this.childNodes[5].childNodes[0].nodeValue));
                divClientInfo.appendChild(lblCl);
                divClientInfo.appendChild(document.createElement("br"));
                divClientInfo.appendChild(document.createTextNode(this.childNodes[6].childNodes[0].nodeValue));
            } else if (kat == "parents") {
                lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                        this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue));
                divClientInfo.appendChild(lblCl);
                divClientInfo.appendChild(document.createElement("br"));
                divClientInfo.appendChild(document.createTextNode(this.childNodes[5].childNodes[0].nodeValue));
            } else if (kat == "ped") {
                lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                        this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue));
                divClientInfo.appendChild(lblCl);
                divClientInfo.appendChild(document.createElement("br"));
                divClientInfo.appendChild(document.createTextNode(this.childNodes[7].childNodes[0].nodeValue));
                divClientInfo.appendChild(document.createElement("br"));
                divClientInfo.appendChild(document.createTextNode("Учреждение: " + this.childNodes[5].childNodes[0].nodeValue));
                divClientInfo.appendChild(document.createElement("br"));
                divClientInfo.appendChild(document.createTextNode("Должность: " + this.childNodes[6].childNodes[0].nodeValue));
            }
            divClientInfo.appendChild(document.createElement("br"));

            var dog = document.getElementById("dogovor");
            if (dog == null) {
                dog = document.createElement("div");
                dog.id = "dogovor";
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("Договор"));
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("Номер: "));
                var inpDogN = document.createElement("input");
                inpDogN.type = "text";
                inpDogN.id = "dogN";
                inpDogN.name = "dogN";
                dog.appendChild(inpDogN);
                dog.appendChild(document.createTextNode(" Дата: "));
                var inpDogD = document.createElement("input");
                inpDogD.type = "date";
                inpDogD.id = "dogD";
                inpDogD.name = "dogD";
                dog.appendChild(inpDogD);
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("С кем заключен (родитель): "));
                var inpBtn = document.createElement("input");
                inpBtn.type = "button";
                inpBtn.id = "findDogCl";
                inpBtn.value = "Найти";
                inpBtn.onclick = findParentIncl;
                dog.appendChild(inpBtn);
                dog.appendChild(document.createElement("br"));
                var inpDogClId = document.createElement("input");
                inpDogClId.type = "hidden";
                inpDogClId.id = "dogClId";
                inpDogClId.name = "dogClId";
                dog.appendChild(inpDogClId);
                var infoDog = document.createElement("div");
                infoDog.id = "infoDogCl";
                dog.appendChild(infoDog);
                divClientInfo.appendChild(dog);
            }
        }
    } else if (type == "dogovorIncl") {
        var dog = document.getElementById("dogovor");
        var dogClId = document.getElementById("dogClId");
        if (dogClId == null) {
            dogClId = document.createElement("input");
            dogClId.type = "hidden";
            dogClId.id = "dogClId";
            dogClId.name = "dogClId";
            dog.appendChild(dogClId);
        }
        var dogClId = document.getElementById("dogClId");
        dogClId.value = this.childNodes[0].childNodes[0].nodeValue;
        var info = document.getElementById("infoDogCl");
        if (info != null) {
            info.innerHTML = "";
        }
        var lblCl = document.createElement("label");
        lblCl.className = "clickable";
        lblCl.onclick = clickParentDog;
        lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue));
        info.appendChild(lblCl);
        info.appendChild(document.createElement("br"));
        info.appendChild(document.createTextNode(this.childNodes[5].childNodes[0].nodeValue));
        info.appendChild(document.createElement("br"));
        info.appendChild(document.createTextNode("Телефон: "));
        var inpTel = document.createElement("input");
        inpTel.id = "telephon";
        inpTel.name = "telephon";
        inpTel.type = "text";
        info.appendChild(inpTel);
        var url = "payaction?action=findtel&idparent=" + dogClId.value + "&n=";
        requ(url, "GET");
    } else if (type == "client") {
        var cl = document.getElementById("cl" + n);
        var clId = document.getElementById("clId" + n);
        if (clId == null) {
            clId = document.createElement("input");
            clId.type = "hidden";
            clId.id = "clId" + n;
            clId.name = "clId" + n;
            document.getElementById("cl" + n).appendChild(clId);
        }

        var clId = document.getElementById("clId" + n);
        clId.value = this.childNodes[0].childNodes[0].nodeValue;
        var clKat = document.getElementById("clKat" + n);
        clKat.value = kat;

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
            cl.appendChild(client);
        } else {
            client.innerHTML = "";
        }
        var client = document.getElementById("infoClient" + n);
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
            client.appendChild(document.createTextNode(this.childNodes[5].childNodes[0].nodeValue));
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
            var url = "priyomaction?action=statosn&type=cl&n=" + n;
            requ(url, "GET");
        }
        var role = document.getElementById("role");
        if (role.value == "administrator") {
            var dog = document.getElementById("dogovor" + n);
            if (dog == null) {
                dog = document.createElement("div");
                dog.id = "dogovor" + n;
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("Договор"));
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("Номер: "));
                var inpDogN = document.createElement("input");
                inpDogN.type = "text";
                inpDogN.id = "dogN" + n;
                inpDogN.name = "dogN" + n;
                dog.appendChild(inpDogN);
                dog.appendChild(document.createTextNode(" Дата: "));
                var inpDogD = document.createElement("input");
                inpDogD.type = "date";
                inpDogD.id = "dogD" + n;
                inpDogD.name = "dogD" + n;
                dog.appendChild(inpDogD);
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("С кем заключен (родитель): "));
                var inpBtn = document.createElement("input");
                inpBtn.type = "button";
                inpBtn.id = "findDogCl" + n;
                inpBtn.value = "Найти";
                inpBtn.onclick = findParent;
                dog.appendChild(inpBtn);
                dog.appendChild(document.createElement("br"));
                var inpDogClId = document.createElement("input");
                inpDogClId.type = "hidden";
                inpDogClId.id = "dogClId" + n;
                inpDogClId.name = "dogClId" + n;
                dog.appendChild(inpDogClId);
                var infoDog = document.createElement("div");
                infoDog.id = "infoDogCl" + n;
                dog.appendChild(infoDog);
                cl.appendChild(dog);
            }
        }

    } else if (type == "dogovor") {
        var dog = document.getElementById("dogovor" + n);
        var dogClId = document.getElementById("dogClId" + n);
        if (dogClId == null) {
            dogClId = document.createElement("input");
            dogClId.type = "hidden";
            dogClId.id = "dogClId" + n;
            dogClId.name = "dogClId" + n;
            dog.appendChild(dogClId);
        }
        var dogClId = document.getElementById("dogClId" + n);
        dogClId.value = this.childNodes[0].childNodes[0].nodeValue;
        var info = document.getElementById("infoDogCl" + n);
        if (info != null) {
            info.innerHTML = "";
        }
        var lblCl = document.createElement("label");
        lblCl.className = "clickable";
        lblCl.onclick = clickParentDog;
        lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
                this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue));
        info.appendChild(lblCl);
        info.appendChild(document.createElement("br"));
        info.appendChild(document.createTextNode(this.childNodes[5].childNodes[0].nodeValue));
        info.appendChild(document.createElement("br"));
        info.appendChild(document.createTextNode("Телефон: "));
        var inpTel = document.createElement("input");
        inpTel.id = "telephon" + n;
        inpTel.name = "telephon" + n;
        inpTel.type = "text";
        info.appendChild(inpTel);
        var url = "payaction?action=findtel&idparent=" + dogClId.value + "&n=" + n;
        requ(url, "GET");
    }
}

function newClient() {
    var dialog = document.getElementById("searchClDialog");
    dialog.close();
    var nom = document.getElementById("nomClPp");
    var n;
    if (nom != null) {
        n = nom.value;
    }
    var fam = document.getElementById("fam").value;
    var nam = document.getElementById("nam").value;
    var patr = document.getElementById("patr").value;
    var type = document.getElementById("type").value;

    if (type == "incl") {
        var divClientInfo = document.getElementById("divClientInfo");
        var kat = document.getElementById("katCl").value;
        if (divClientInfo != null) {
            divClientInfo.innerHTML = "";
            var clId = document.getElementById("clId");
            if (clId != null) {
                clId.value = "";
            }
            divClientInfo.appendChild(document.createTextNode("Фамилия: "));
            var inpFam = document.createElement("input");
            inpFam.name = "clFam";
            inpFam.id = "clFam";
            inpFam.type = "text";
            inpFam.value = fam;
            divClientInfo.appendChild(inpFam);
            divClientInfo.appendChild(document.createTextNode(" Имя: "));
            var inpNam = document.createElement("input");
            inpNam.name = "clNam";
            inpNam.id = "clNam";
            inpNam.type = "text";
            inpNam.value = nam;
            divClientInfo.appendChild(inpNam);
            divClientInfo.appendChild(document.createTextNode(" Отчество: "));
            var inpPatr = document.createElement("input");
            inpPatr.name = "clPatr";
            inpPatr.id = "clPatr";
            inpPatr.type = "text";
            inpPatr.value = patr;
            divClientInfo.appendChild(inpPatr);
            if (kat == "children") {
                divClientInfo.appendChild(document.createElement("br"));
                divClientInfo.appendChild(document.createTextNode("Дата рождения: "));
                var inpDr = document.createElement("input");
                inpDr.type = "date";
                inpDr.name = "clDr";
                inpDr.id = "clDr";
                inpDr.min = "1950-01-01";
                divClientInfo.appendChild(inpDr);
                divClientInfo.appendChild(document.createElement("br"));
                divClientInfo.appendChild(document.createTextNode("Район проживания: "));
                var selReg = document.createElement("select");
                selReg.id = "selRegCl";
                selReg.onchange = changeRegCl;
                divClientInfo.appendChild(selReg);
                var hidText = document.createElement("input");
                hidText.id = "regCl";
                hidText.name = "regCl";
                hidText.type = "hidden";
                divClientInfo.appendChild(hidText);
            } else if (kat == "parents") {
                divClientInfo.appendChild(document.createElement("br"));
                divClientInfo.appendChild(document.createTextNode("Район проживания: "));
                var selReg = document.createElement("select");
                selReg.id = "selRegCl";
                selReg.onchange = changeRegCl;
                divClientInfo.appendChild(selReg);
                var hidText = document.createElement("input");
                hidText.id = "regCl";
                hidText.name = "regCl";
                hidText.type = "hidden";
                divClientInfo.appendChild(hidText);
            } else if (kat == "ped") {
                divClientInfo.appendChild(document.createElement("br"));
                divClientInfo.appendChild(document.createTextNode("Район проживания: "));
                var selReg = document.createElement("select");
                selReg.id = "selRegCl";
                selReg.onchange = changeRegCl;
                divClientInfo.appendChild(selReg);
                var hidText = document.createElement("input");
                hidText.id = "regCl";
                hidText.name = "regCl";
                hidText.type = "hidden";
                divClientInfo.appendChild(hidText);
                divClientInfo.appendChild(document.createElement("br"));
                divClientInfo.appendChild(document.createTextNode("Организация: "));
                var selOrg = document.createElement("select");
                selOrg.id = "selClOrg";
                selOrg.onchange = orgChange;
                divClientInfo.appendChild(selOrg);
                var inpOrg = document.createElement("input");
                inpOrg.type = "hidden";
                inpOrg.name = "clOrg";
                inpOrg.id = "clOrg";
                divClientInfo.appendChild(inpOrg);
                divClientInfo.appendChild(document.createTextNode("Должность: "));
                var selPedD = document.createElement("select");
                selPedD.id = "selClPedD";
                selPedD.onchange = peddolgChange;
                divClientInfo.appendChild(selPedD);
                var inpDol = document.createElement("input");
                inpDol.type = "hidden";
                inpDol.name = "clDol";
                inpDol.id = "clDol";
                divClientInfo.appendChild(inpDol);
                var url = "priyomaction?action=org&n=&type=cl";
                requ(url, "GET");
            }
            divClientInfo.appendChild(document.createElement("br"));
            var url = "priyomaction?action=region&n=&type=cl"; // для заполнения списка районов проживания
            requ(url, "GET");
            var dog = document.getElementById("dogovor");
            if (dog == null) {
                dog = document.createElement("div");
                dog.id = "dogovor";
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("Договор"));
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("Номер: "));
                var inpDogN = document.createElement("input");
                inpDogN.type = "text";
                inpDogN.id = "dogN";
                inpDogN.name = "dogN";
                dog.appendChild(inpDogN);
                dog.appendChild(document.createTextNode(" Дата: "));
                var inpDogD = document.createElement("input");
                inpDogD.type = "date";
                inpDogD.id = "dogD";
                inpDogD.name = "dogD";
                dog.appendChild(inpDogD);
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("С кем заключен (родитель): "));
                var inpBtn = document.createElement("input");
                inpBtn.type = "button";
                inpBtn.id = "findDogCl";
                inpBtn.value = "Найти";
                inpBtn.onclick = findParentIncl;
                dog.appendChild(inpBtn);
                dog.appendChild(document.createElement("br"));
                var inpDogClId = document.createElement("input");
                inpDogClId.type = "hidden";
                inpDogClId.id = "dogClId";
                inpDogClId.name = "dogClId";
                dog.appendChild(inpDogClId);
                var infoDog = document.createElement("div");
                infoDog.id = "infoDogCl";
                dog.appendChild(infoDog);
                divClientInfo.appendChild(dog);
            }
        }
    } else if (type == "dogovorIncl") {
        var dog = document.getElementById("dogovor");
        var info = document.getElementById("infoDogCl");
        if (info != null) {
            info.innerHTML = "";
        }
        var dogClId = document.getElementById("dogClId");
        if (dogClId == null) {
            dogClId = document.createElement("input");
            dogClId.type = "hidden";
            dogClId.id = "dogClId";
            dogClId.name = "dogClId";
            dog.appendChild(dogClId);
        } else {
            dogClId.value = "";
        }
        info.appendChild(document.createTextNode("Фамилия: "));
        var inpFam = document.createElement("input");
        inpFam.name = "dogClFam";
        inpFam.id = "dogClFam";
        inpFam.type = "text";
        inpFam.value = fam;
        info.appendChild(inpFam);
        info.appendChild(document.createTextNode(" Имя: "));
        var inpNam = document.createElement("input");
        inpNam.name = "dogClNam";
        inpNam.id = "dogClNam";
        inpNam.type = "text";
        inpNam.value = nam;
        info.appendChild(inpNam);
        info.appendChild(document.createTextNode(" Отчество: "));
        var inpPatr = document.createElement("input");
        inpPatr.name = "dogClPatr";
        inpPatr.id = "dogClPatr";
        inpPatr.type = "text";
        inpPatr.value = patr;
        info.appendChild(inpPatr);
        info.appendChild(document.createElement("br"));
        info.appendChild(document.createTextNode("Район проживания: "));
        var selReg = document.createElement("select");
        selReg.id = "selRegDog";
        selReg.onchange = changeRegCl;
        info.appendChild(selReg);
        var hidText = document.createElement("input");
        hidText.id = "regDog";
        hidText.name = "regDog";
        hidText.type = "hidden";
        info.appendChild(hidText);
        info.appendChild(document.createElement("br"));
        info.appendChild(document.createTextNode("Телефон: "));
        var inpTel = document.createElement("input");
        inpTel.id = "telephon";
        inpTel.name = "telephon";
        inpTel.type = "text";
        info.appendChild(inpTel);

        var url = "priyomaction?action=region&n=&type=dog"; // для заполнения списка районов проживания
        requ(url, "GET");
    } else if (type == "client") {
        var kat = document.getElementById("katCl").value;
        var cl = document.getElementById("cl" + n);
        var clKat = document.getElementById("clKat" + n);
        clKat.value = kat;
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
            inpDr.min = "1950-01-01";
            client.appendChild(inpDr);
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
            var url = "priyomaction?action=org&n=" + n + "&type=cl";
            requ(url, "GET");
        }
        client.appendChild(document.createElement("br"));
        cl.appendChild(client);

        var url = "priyomaction?action=region&n=" + n + "&type=cl"; // для заполнения списка районов проживания
        requ(url, "GET");

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
            var url = "priyomaction?action=statosn&type=cl&n=" + n;
            requ(url, "GET");
        }
        var role = document.getElementById("role");
        if (role.value == "administrator") {
            var dog = document.getElementById("dogovor" + n);
            if (dog == null) {
                dog = document.createElement("div");
                dog.id = "dogovor" + n;
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("Договор"));
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("Номер: "));
                var inpDogN = document.createElement("input");
                inpDogN.type = "text";
                inpDogN.id = "dogN" + n;
                inpDogN.name = "dogN" + n;
                dog.appendChild(inpDogN);
                dog.appendChild(document.createTextNode(" Дата: "));
                var inpDogD = document.createElement("input");
                inpDogD.type = "date";
                inpDogD.id = "dogD" + n;
                inpDogD.name = "dogD" + n;
                dog.appendChild(inpDogD);
                dog.appendChild(document.createElement("br"));
                dog.appendChild(document.createTextNode("С кем заключен (родитель): "));
                var inpBtn = document.createElement("input");
                inpBtn.type = "button";
                inpBtn.id = "findDogCl" + n;
                inpBtn.value = "Найти";
                inpBtn.onclick = findParent;
                dog.appendChild(inpBtn);
                dog.appendChild(document.createElement("br"));
                var inpDogClId = document.createElement("input");
                inpDogClId.type = "hidden";
                inpDogClId.id = "dogClId" + n;
                inpDogClId.name = "dogClId" + n;
                dog.appendChild(inpDogClId);
                var infoDog = document.createElement("div");
                infoDog.id = "infoDogCl" + n;
                dog.appendChild(infoDog);
                cl.appendChild(dog);
            }
        }
    } else if (type == "dogovor") {
        var dog = document.getElementById("dogovor" + n);
        var info = document.getElementById("infoDogCl" + n);
        if (info != null) {
            info.innerHTML = "";
        }
        var dogClId = document.getElementById("dogClId" + n);
        if (dogClId == null) {
            dogClId = document.createElement("input");
            dogClId.type = "hidden";
            dogClId.id = "dogClId" + n;
            dogClId.name = "dogClId" + n;
            dog.appendChild(dogClId);
        } else {
            dogClId.value = "";
        }
        info.appendChild(document.createTextNode("Фамилия: "));
        var inpFam = document.createElement("input");
        inpFam.name = "dogClFam" + n;
        inpFam.id = "dogClFam" + n;
        inpFam.type = "text";
        inpFam.value = fam;
        info.appendChild(inpFam);
        info.appendChild(document.createTextNode(" Имя: "));
        var inpNam = document.createElement("input");
        inpNam.name = "dogClNam" + n;
        inpNam.id = "dogClNam" + n;
        inpNam.type = "text";
        inpNam.value = nam;
        info.appendChild(inpNam);
        info.appendChild(document.createTextNode(" Отчество: "));
        var inpPatr = document.createElement("input");
        inpPatr.name = "dogClPatr" + n;
        inpPatr.id = "dogClPatr" + n;
        inpPatr.type = "text";
        inpPatr.value = patr;
        info.appendChild(inpPatr);
        info.appendChild(document.createElement("br"));
        info.appendChild(document.createTextNode("Район проживания: "));
        var selReg = document.createElement("select");
        selReg.id = "selRegDog" + n;
        selReg.onchange = changeRegCl;
        info.appendChild(selReg);
        var hidText = document.createElement("input");
        hidText.id = "regDog" + n;
        hidText.name = "regDog" + n;
        hidText.type = "hidden";
        info.appendChild(hidText);
        info.appendChild(document.createElement("br"));
        info.appendChild(document.createTextNode("Телефон: "));
        var inpTel = document.createElement("input");
        inpTel.id = "telephon" + n;
        inpTel.name = "telephon" + n;
        inpTel.type = "text";
        info.appendChild(inpTel);
        //    dog.appendChild(info);

        var url = "priyomaction?action=region&n=" + n + "&type=dog"; // для заполнения списка районов проживания
        requ(url, "GET");
    }
}

function clickClient() {
    var div = this.parentNode;
    if (div != null) {
        var n = div.id.substring(10);
        var kat = document.getElementById("clKat" + n);
        var id = document.getElementById("clId" + n);
        if (kat != null) {
            window.open("client?kat=" + kat.value + "&id=" + id.value, "_blank");
        }
    }
}
function clickParentDog() {
    var div = this.parentNode;
    if (div != null) {
        var n = div.id.substring(9);
        var kat = "parents";
        var id = document.getElementById("dogClId" + n);
        if (kat != null) {
            window.open("client?kat=" + kat + "&id=" + id.value, "_blank");
        }
    }
}

function cancelDialog() {
    var dialog = document.getElementById("searchClDialog");
    dialog.close();
}

function changeRegCl() {
    var n;
    var idReg;
    if (this.id.substring(0, 8) == "selRegCl") {
        n = this.id.substring(8);
        idReg = document.getElementById("regCl" + n);
    } else if (this.id.substring(0, 9) == "selRegDog") {
        n = this.id.substring(9);
        idReg = document.getElementById("regDog" + n);
    }
    idReg.setAttribute("value", this.value);
}

function changeRegDog() {
    var n;
    var idReg;
    n = this.id.substring(9);
    idReg = document.getElementById("regDog" + n);
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
    if (rN == " ") {
        rN = "";
    }
    if (rType == "cl") {
        selReg = document.getElementById("selRegCl" + rN);
    } else if (rType == "dog") {
        selReg = document.getElementById("selRegDog" + rN);
    }
    var opt = document.createElement("option");
    selReg.appendChild(opt);
    opt.setAttribute("value", rId);
    opt.appendChild(document.createTextNode(rName));
    selReg.onchange();
}

function appendStatOsn(n, statosnType, statosnId, statosnName) {
    var div;
    div = document.getElementById("divStatosn" + n);
    var lbl = document.createElement("label");
    var chb = document.createElement("input");
    chb.type = "checkbox";
    chb.id = "statosn" + statosnId + "_" + n;
    chb.name = "statosn" + statosnId + "_" + n;
    chb.value = statosnId;
    chb.onchange = checkStatOsn;
    lbl.appendChild(chb);
    lbl.appendChild(document.createTextNode(statosnName));
    div.appendChild(lbl);
}

function checkStatOsn() {
    var k = this.id.indexOf("_");
    var n = this.id.substring(k + 1);
    var check;
    var stat;
    var type;
    type = "cl";
    check = document.getElementById("check" + n);
    stat = document.getElementById("divStat" + n);
    if (this.checked) {
        if (this.classList.contains("norma")) {
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
                        checks[loop].disabled = "disabled";
                    }
                }
            }
            check.value = "1";
            var divSoc = document.createElement("div");
            divSoc.id = "divStatsoc" + n;
            stat.appendChild(divSoc);
            var url = "priyomaction?action=statsoc&n=" + n + "&type=" + type;
            requ(url, "GET");
        } else {
            if ((check.value == "0") || (check.value == "")) {
                var divSoc = document.createElement("div");
                divSoc.id = "divStatsoc" + n;
                stat.appendChild(divSoc);
                var url = "priyomaction?action=statsoc&n=" + n + "&type=" + type;
                requ(url, "GET");
                var div = document.createElement("div");
                div.id = "divStatdop" + n;
                stat.appendChild(div);
                var url = "priyomaction?action=statdop&n=" + n + "&type=" + type;
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
            var div = document.getElementById("divStatosn" + n);
            var checks = div.getElementsByTagName("input");
            if (checks != null) {
                for (loop = 0; loop < checks.length; loop++) {
                    if (checks[loop] != this) {
                        checks[loop].disabled = 0;
                    }
                }
            }
            check.value = "0";
        } else {
            var i = parseInt(check.value) - 1;
            check.value = String(i);
            if (check.value == "0") {
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
            }
        }
    }
}

function appendStatDop(n, type, id, name) {
    var client;
    client = document.getElementById("divStatdop" + n);
    var lbl = document.createElement("label");
    var chb = document.createElement("input");
    chb.type = "checkbox";
    chb.id = "statdop" + id + "_" + n;
    chb.name = "statdop" + id + "_" + n;
    chb.value = id;
    chb.onchange = checkStatDop;
    lbl.appendChild(chb);
    lbl.appendChild(document.createTextNode(name));
    client.appendChild(lbl);
}

function checkStatDop() {

    var dop = this.value;
    var k = this.id.indexOf("_");
    var n = this.id.substring(k + 1);
    var type;
    if (this.id.substring(0, 7) == "statdop") {
        type = "cl";
    } else if (this.id.substring(0, 10) == "subStatdop") {
        type = "sub";
    }
    if (this.checked) {
        var url = "priyomaction?action=pod&dop=" + dop + "&n=" + n + "&type=" + type;
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

function appendStatSoc(n, type, id, name) {
    var client;
    client = document.getElementById("divStatsoc" + n);
    var lbl = document.createElement("label");
    var chb = document.createElement("input");
    chb.type = "checkbox";
    chb.id = "statsoc" + id + "_" + n;
    chb.name = "statsoc" + id + "_" + n;
    chb.value = id;
    lbl.appendChild(chb);
    lbl.appendChild(document.createTextNode(name));
    client.appendChild(lbl);
}

function clearStatPod(dop, n, type) {
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
}

function appendStatPod(statN, statType, statDop, statId, statName) {
    var div = document.getElementById("divStatpod" + statDop + "_" + statN);
    var chb = document.createElement("input");
    var lbl = document.createElement("label");
    chb.type = "checkbox";
    chb.id = "statpod" + statId + "_" + statN;
    chb.name = "statpod" + statId + "_" + statN;
    chb.value = statId;
    lbl.appendChild(chb);
    lbl.appendChild(document.createTextNode(statName));
    div.appendChild(lbl);
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

    var btn = document.getElementById("saveBtn");
    btn.disabled = "disabled";
    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        inp[loop].classList.remove("wrong");
    }

    // заполнение отмеченных дат
    var checked = document.getElementsByClassName("checkedday");
    var dates = document.getElementById("dates");
    if ((checked != null) && (dates != null)) {
        for (loop = 0; loop < checked.length; loop++) {
            dates.value += checked[loop].id;
            if (checked.length - loop > 1) {
                dates.value += ";";
            }
        }
    }
    // проверка
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

    if (flag) {
        if (dates.value == "") {
            flag = false;

        }
        if (!flag) {
            alert("Укажите хотя бы одну дату занятий на календаре");
        }
    }

    if (flag) {
        for (loop = 0; loop < inp.length; loop++) {
            var val = inp[loop].value;
            if ((val == "") || (val == "0")) {
                var id = inp[loop].id;
                if (id.substring(0, 5) == "check") {
                    flag = false;
                }
            }
        }
        if (!flag) {
            alert("Укажите статус ребенка");
            var btn = document.getElementById("saveBtn");
            btn.disabled = 0;
        }
    }

    var result = true;
    var inp = document.getElementsByTagName("input");
    if (flag) {
        for (loop = 0; loop < inp.length; loop++) {
            var id = inp[loop].id;
            var val = inp[loop].value;
            if ((id.substring(0, 5) == "clFam") || (id.substring(0, 5) == "clNam")) {
                if (val == "") {
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
    }

    if (!result) {
        alert("Проверьте данные в полях, отмеченных КРАСНЫМ ЦВЕТОМ (проверяется заполнение, корректность и количество символов)");
        var wrong = document.getElementsByClassName("wrong");
        wrong[0].focus();
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
    } else if (result & flag) {
        document.getElementById("payListForm").submit();
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

function clickShow() {
    var show = document.getElementById("showKalendar");
    var kalendar = document.getElementById("kalendar");
    if (show.value == "Показать календарь") {
        viewKalendar();
        show.value = "Скрыть календарь";
    } else if (show.value == "Скрыть календарь") {
        kalendar.style = "display:none;";
        show.value = "Показать календарь";
    }
}

function delPayusl() {
    if (confirm("Вы уверены, что хотите удалить этот список?")) {
        var idPayusl = document.getElementById("uslId").value;
        var url = "payaction?action=delete&id=" + idPayusl;
        document.location.href = url;
    } else {
    }
}

function include() {
    var btn = document.getElementById("inclBtnDialog");
    btn.disabled = "disabled";
    var result = true;
    var date = document.getElementById("inclDateDialog");
    var dia = false;
    if (date.value == "") {
        alert("Укажите дату зачисления");
        result = false;
        dia = true;
    }
    if (result) {
        if (date.length > 10) {
            alert("Проверьте дату зачисления");
            result = false;
            dia = true;
        }
    }
    if (result) {
        var inp = document.getElementsByTagName("input");
        for (loop = 0; loop < inp.length; loop++) {
            var val = inp[loop].value;
            if (val == "") {
                var id = inp[loop].id;
                if (id.substring(0, 4) == "dogN") {
                    result = false;
                }
            }
        }
        if (!result) {
            alert("Заполните все номера договоров");
        }
    }
    if (result) {
        var inp = document.getElementsByTagName("input");
        for (loop = 0; loop < inp.length; loop++) {
            var val = inp[loop].value;
            if (val == "") {
                var id = inp[loop].id;
                if (id.substring(0, 4) == "dogD") {
                    result = false;
                }
            }
        }
        if (!result) {
            alert("Заполните все даты договоров");
        }
    }
    if (result) {
        var inp = document.getElementsByTagName("input");
        for (loop = 0; loop < inp.length; loop++) {
            var val = inp[loop].value;
            if (val == "") {
                var id = inp[loop].id;
                if (id.substring(0, 7) == "dogClId") {
                    result = false;
                }
            }
        }
        if (!result) {
            alert("Укажите всех родителей, с которыми заключены договоры");
        }
    }

    if (!result) {
        btn.disabled = 0;
        if (!dia) {
            var dialog = document.getElementById("includeDialog");
            dialog.close();
        }
    }
    if (result) {
        var dateIncl = document.getElementById("inclDate");
        dateIncl.value = date.value;
        var incl = document.getElementById("incl");
        incl.value = 1;
        validate();
    }
}

function toIncludeDialog() {
    var dialog = document.getElementById("includeDialog");
    dialog.showModal();
}

function appendTelephon(n, tel) {
    if (n == " ") {
        n = "";
    }
    var telephon = document.getElementById("telephon" + n);
    if (telephon != null) {
        if (tel != 'null') {
            telephon.value = tel;
        }
    }
}

function clearDogTable() {
    var tab = document.getElementById("dogTable");
    tab.innerHTML = "";
    var thead = document.createElement("thead");
    thead.id = "head";
    var tbody = document.createElement("tbody");
    tbody.id = "body";
    tab.appendChild(thead);
    tab.appendChild(tbody);
}

function selectDogovor() {
    var id = this.childNodes[0].childNodes[0].nodeValue;
    window.open("payaction?action=dogview&id=" + id, "_blank");
}

function validateDog() {
    var btn = document.getElementById("findDogCl1");
    btn.disabled = "disabled";
    var dogN = document.getElementById("dogN1");
    var dogD = document.getElementById("dogD1");
    var result = true;
    if (dogN.value == "") {
        result = false;
        dogN.className = "wrong";
    }
    if (dogD.value == "") {
        result = false;
        dogD.className = "wrong";
    }
    if (!result) {
        alert("Проверьте данные в полях, отмеченных КРАСНЫМ ЦВЕТОМ (проверяется заполнение, корректность и количество символов)");
        var wrong = document.getElementsByClassName("wrong");
        wrong[0].focus();
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
    } else if (result & flag) {
        document.getElementById("payDogForm").submit();
    }
}

function clearSearchDog() {
    document.getElementById("searchN").value = "";
    document.getElementById("searchD").value = "";
    document.getElementById("searchFam").value = "";
    document.getElementById("searchName").value = "";
    document.getElementById("searchPatr").value = "";
    var url = "search?type=paydog&nom=&date=&fam=&name=&patr=";
    requ(url, "GET");
}

function savePos() {
    var form = document.getElementById("payPosForm");
    form.submit();
}

function includeOne() {
    var inclDialog = document.getElementById("inclDialog");
    if (inclDialog != null) {
        inclDialog.showModal();
        var divClientInfo = document.getElementById("divClientInfo");
        divClientInfo.innerHTML = "";
        var clientId = document.getElementById("clientId");
        clientId.innerHTML = "";
    }
}

function expOne() {
    var expDialog = document.getElementById("expDialog");
    if (expDialog != null) {
        expDialog.showModal();
    }
}

function closeInclDialog() {
    var inclDialog = document.getElementById("inclDialog");
    if (inclDialog != null) {
        inclDialog.close();
    }
}

function closeExpDialog() {
    var expDialog = document.getElementById("expDialog");
    if (expDialog != null) {
        expDialog.close();
    }
}

function incl() {
    var inclDate = document.getElementById("inclDate");
    var result = true;
    if (inclDate != null) {
        if (inclDate.value == "") {
            alert("Укажите дату зачисления");
            result = false;
        }
    }
    if (result) {
        var id = document.getElementById("clId");
        var fam = document.getElementById("clFam");
        if ((id.value == "") && (fam == null)) {
            alert("Укажите клиента");
            result = false;
        }
    }
    if (result) {
        var inclForm = document.getElementById("inclForm");
        inclForm.submit();
    }
}

function expd() {
    var expDate = document.getElementById("expDate");
    var result = true;
    if (expDate != null) {
        if (expDate.value == "") {
            alert("Укажите дату отчисления");
            result = false;
        }
    }
    var inp = document.getElementsByTagName("input");
    var k = false;
    for (loop = 0; loop < inp.length; loop++) {
        if (inp[loop].id.substring(0, 3) == "chb") {
            if (inp[loop].checked) {
                k = true;
                break;
            }
        }
    }
    if (result) {
        if (!k) {
            alert("Отметьте хотя бы одного клиента");
            result = false;
        }
    }
    if (result) {
        var usl = document.getElementById("payUslId");
        var clid = "";
        for (loop = 0; loop < inp.length; loop++) {
            if (inp[loop].id.substring(0, 3) == "chb") {
                if (inp[loop].checked) {
                    clid += inp[loop].id.substring(3);
                    clid += ";";
                }
            }
        }
        var date = document.getElementById("expDate");
        window.open("exppay?usl=" + usl.value + "&clid=" + clid + "&date=" + date.value);
    }
}

function searchIncl() {
    var searchClDialog = document.getElementById("searchClDialog");
    searchClDialog.showModal();
    var kat = "children";
    var dialogKat = document.getElementById("selKat");
    var opts = dialogKat.getElementsByTagName("option");
    for (loop = 0; loop < opts.length; loop++) {
        if (opts[loop].value == kat) {
            opts[loop].selected = "selected";
        }
    }
    dialogKat.disabled = "disabled";
    var type = document.getElementById("type");
    if (type != null) {
        type.value = "incl";
    }
    search();
}

function findParentIncl() {
    var dialog = document.getElementById("searchClDialog");
    dialog.showModal();
    var n = this.id.substring(9);
    var kat = "parents";
    var dialogKat = document.getElementById("selKat");
    var opts = dialogKat.getElementsByTagName("option");
    for (loop = 0; loop < opts.length; loop++) {
        if (opts[loop].value == kat) {
            opts[loop].selected = "selected";
        }
    }
    dialogKat.disabled = "disabled";
    var fam = document.getElementById("fam");
    fam.value = "";
    var nam = document.getElementById("nam");
    nam.value = "";
    var patr = document.getElementById("patr");
    patr.value = "";
    var type = document.getElementById("type");
    type.value = "dogovorIncl";
    search();
}

function pay() {
    var dialog = document.getElementById("paymentDialog");
    if (dialog != null) {
        dialog.showModal();
    }
}

function savePayment() {
    var fio = document.getElementById("paymentFio");
    var date = document.getElementById("paymentDate");
    var sum = document.getElementById("paymentSum");
    var result = true;
    if (fio.value == "") {
        result = false;
        alert("Укажите ФИО плательщика");
    }
    if (result) {
        if (date.value == "") {
            result = false;
            alert("Укажите дату платежа");
        }
    }
    if (result) {
        if (sum.value == "") {
            result = false;
            alert("Укажите сумму платежа");
        } else {
            var val = sum.value;
            val = val.replace(',', '.');
            if (isNaN(val)) {
                result = false;
                alert("Проверьте правильность ввода суммы платежа");
            }
        }
    }
    if (result) {
        document.getElementById("paymentForm").submit();
        document.getElementById("paymentDialog").close();
    }

}