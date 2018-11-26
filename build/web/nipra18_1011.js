/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    // вешаем на документ запрет выделения текста элементов интерфейса
    preventSelection(document);

    // устанавливаем действия на поля с датами отчётов (расчёт дат)
    var otchOmsu = document.getElementById("ipraOtchomsu");
    otchOmsu.onclick = otchOmsuDate;

    var otchCenter = document.getElementById("ipraOtchcenter");
    otchCenter.onclick = otchCenterDate;

    var otchDo = document.getElementById("ipraOtchdo");
    otchDo.onclick = otchDoDate;

    // устанавливаем действие на кнопку "Сохранить"
    var saveBtn = document.getElementById("saveBtn");
    saveBtn.onclick = clickSaveBtn;

    // устанавливаем действие на выбор МСЭ
    var selMse = document.getElementById("selMse");
    selMse.onchange = mseSelect;

    // устанавливаем реакцию на попытку ввести номер входящего письма
    /*    var ipraVhN = document.getElementById("ipraVhN");
     ipraVhN.onclick = letterReg;*/

    // устанавливаем действие на выбор статуса
    var status = document.getElementById("selStatus");
    if (status != null) {
        status.onchange = chStatus;
    }
}

function preventSelection(element) {    // запрет выделения текста, кроме input и textarea
    var preventSelection = false;

    function addHandler(element, event, handler) {
        if (element.attachEvent)
            element.attachEvent('on' + event, handler);
        else
        if (element.addEventListener)
            element.addEventListener(event, handler, false);
    }
    function removeSelection() {
        if (window.getSelection) {
            window.getSelection().removeAllRanges();
        } else if (document.selection && document.selection.clear)
            document.selection.clear();
    }

    // не даем выделять текст мышкой
    addHandler(element, 'mousemove', function () {
        if (preventSelection)
            removeSelection();
    });
    addHandler(element, 'mousedown', function (event) {
        var event = event || window.event;
        var sender = event.target || event.srcElement;
        preventSelection = !sender.tagName.match(/INPUT|TEXTAREA/i);
    });

    // борем dblclick
    // если вешать функцию не на событие dblclick, можно избежать
    // временное выделение текста в некоторых браузерах
    addHandler(element, 'mouseup', function () {
        if (preventSelection)
            removeSelection();
        preventSelection = false;
    });
}

function openDlg() {
    var searchDlg = document.getElementById("searchDlg");
    searchDlg.showModal();
    searchChild();
}

function closeDlg() {
    var searchDlg = document.getElementById("searchDlg");
    searchDlg.close();
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
            var saveBtn = document.getElementById("saveBtn");
            saveBtn.classList.remove("dsbldbtn");
            saveBtn.className = "greybtn";
            saveBtn.onclick = clickSaveBtn;
            /* var savePerechenBtn = document.getElementById("savePerechenBtn");
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
             }*/
        } else if (request.status == 204) {
        } else {
            alert("Ошибка подключения к серверу\nДанные не были сохранены");
            var saveBtn = document.getElementById("saveBtn");
            saveBtn.classList.remove("dsbldbtn");
            saveBtn.className = "greybtn";
            saveBtn.onclick = clickSaveBtn;
            /* var savePerechenBtn = document.getElementById("savePerechenBtn");
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
             }*/
        }
    }
}

function parseMessages(responseXML) {   // преобразование полученного xml
    if (responseXML == null) {
        return false;
    } else {
        var result = responseXML.getElementsByTagName("result")[0];
        var clients = responseXML.getElementsByTagName("clients")[0];
        var regions = responseXML.getElementsByTagName("regions")[0];
        var ipradate = responseXML.getElementsByTagName("ipradate")[0];
        /* var mselist = responseXML.getElementsByTagName("mselist")[0];
         var omsulist = responseXML.getElementsByTagName("omsulist")[0];
         var tpmpklist = responseXML.getElementsByTagName("tpmpklist")[0];
         var regid = responseXML.getElementsByTagName("regid")[0];*/
        if (result != null) {
            appendResult(result.childNodes[0].nodeValue);
        }
        if (clients != null) {
            var thead = document.getElementById("dlgTabHead");    // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("dlgTabBody");    // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (clients.childNodes.length > 0) {
                var head = clients.childNodes[0];
                appendHead(thead, head);    // отрисовываем заголовок
                for (loop = 1; loop < clients.childNodes.length; loop++) {
                    var client = clients.childNodes[loop];
                    appendBody(tbody, client, selectChild);    // отрисовываем строки в теле таблицы  
                }
            }
            clients = null;
        }
        if (ipradate != null) { // если расчётная дата 
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
        /*
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
         }*/
    }
}

function clearTable(thead, tbody) {     // очистка таблицы со списком
    thead.innerHTML = "";
    tbody.innerHTML = "";
}

function appendHead(thead, head) {    // отображение заголовка таблицы    
    var tr = document.createElement("tr");
    for (loop = 0; loop < head.childNodes.length; loop++) {
        var td = document.createElement("td");
        td.appendChild(document.createTextNode(head.childNodes[loop].childNodes[0].nodeValue));
        tr.appendChild(td);
    }
    thead.appendChild(tr);
}

function appendBody(tbody, body, func) {   // отображение тела таблицы    
    var tr = document.createElement("tr");
    for (loop1 = 0; loop1 < body.childNodes.length; loop1++) {
        var td = document.createElement("td");
        td.appendChild(document.createTextNode(body.childNodes[loop1].childNodes[0].nodeValue));
        tr.appendChild(td);
    }
    tr.onclick = func;
    tbody.appendChild(tr);
}

function selectChild() { // выбор ребёнка из таблицы поиска
    var dialog = document.getElementById("searchDlg");
    if (dialog != null) {
        dialog.close();
    }

    // убираем из формы информацию о ребёнке, если она была отображена
    var divInfoChild = document.getElementById("divInfoChild");
    divInfoChild.innerHTML = "";
    // поле с ИД ребёнка
    var inpId = document.createElement("input");
    inpId.type = "hidden";
    inpId.id = "childId";
    inpId.name = "childId";
    inpId.value = this.childNodes[0].childNodes[0].nodeValue;
    divInfoChild.appendChild(inpId);
    // ФИО, дата рождения и район
    var lbl = document.createElement("label");
    lbl.appendChild(document.createTextNode(this.childNodes[1].childNodes[0].nodeValue));
    divInfoChild.appendChild(lbl);
    divInfoChild.appendChild(document.createElement("br"));
    var lblCl = document.createElement("label");
    lblCl.className = "clickable";
    lblCl.onclick = clickChild;
    lblCl.appendChild(document.createTextNode(this.childNodes[2].childNodes[0].nodeValue + " " +
            this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[4].childNodes[0].nodeValue + " " +
            this.childNodes[5].childNodes[0].nodeValue));
    divInfoChild.appendChild(lblCl);
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(document.createTextNode(this.childNodes[6].childNodes[0].nodeValue));
    divInfoChild.appendChild(document.createElement("br"));
    divInfoChild.appendChild(document.createElement("br"));
}

function newChild() {    // ввод данных нового ребёнка
    var dialog = document.getElementById("searchDlg");
    var f, n, p;
    if (dialog != null) {
        f = document.getElementById("fam").value;
        n = document.getElementById("nam").value;
        p = document.getElementById("patr").value;
        dialog.close();
    }

    var divInfoChild = document.getElementById("divInfoChild");
    // очищаем
    divInfoChild.innerHTML = "";
    // создаём поля для ввода данных ( + вставляем данные из диалога поиска)
    var table = document.createElement("table");
    table.className = "noborder";
    var tr1 = document.createElement("tr");
    var td11 = document.createElement("td");
    var td12 = document.createElement("td");
    var lblFam = document.createElement("label");
    lblFam.appendChild(document.createTextNode("Фамилия: "));
    var inpFam = document.createElement("input");
    inpFam.type = "text";
    inpFam.id = "childFam";
    inpFam.name = "childFam";
    inpFam.maxlength = 100;
    inpFam.value = f;
    inpFam.style = "width: 217px;";
    td11.appendChild(lblFam);
    td12.appendChild(inpFam);
    tr1.appendChild(td11);
    tr1.appendChild(td12);
    table.appendChild(tr1);

    var tr2 = document.createElement("tr");
    var td21 = document.createElement("td");
    var td22 = document.createElement("td");
    var lblName = document.createElement("label");
    lblName.appendChild(document.createTextNode("Имя: "));
    var inpName = document.createElement("input");
    inpName.type = "text";
    inpName.id = "childName";
    inpName.name = "childName";
    inpName.maxlength = 100;
    inpName.value = n;
    inpName.style = "width: 217px;";
    td21.appendChild(lblName);
    td22.appendChild(inpName);
    tr2.appendChild(td21);
    tr2.appendChild(td22);
    table.appendChild(tr2);

    var tr3 = document.createElement("tr");
    var td31 = document.createElement("td");
    var td32 = document.createElement("td");
    var lblPatr = document.createElement("label");
    lblPatr.appendChild(document.createTextNode("Отчество: "));
    var inpPatr = document.createElement("input");
    inpPatr.type = "text";
    inpPatr.id = "childPatr";
    inpPatr.name = "childPatr";
    inpPatr.maxlength = 100;
    inpPatr.value = p;
    inpPatr.style = "width: 217px;";
    td31.appendChild(lblPatr);
    td32.appendChild(inpPatr);
    tr3.appendChild(td31);
    tr3.appendChild(td32);
    table.appendChild(tr3);

    var tr4 = document.createElement("tr");
    var td41 = document.createElement("td");
    var td42 = document.createElement("td");
    var lblDr = document.createElement("label");
    lblDr.appendChild(document.createTextNode("Дата рождения: "));
    var inpDr = document.createElement("input");
    inpDr.type = "date";
    inpDr.id = "childDr";
    inpDr.name = "childDr";
    inpDr.style = "width: 150px; height: 20px;";
    td41.appendChild(lblDr);
    td42.appendChild(inpDr);
    tr4.appendChild(td41);
    tr4.appendChild(td42);
    table.appendChild(tr4);
    divInfoChild.appendChild(table);

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
    divInfoChild.appendChild(divPol);

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
    divInfoChild.appendChild(document.createElement("br"));
    var url = "search?type=allclregions";   // запрос списка районов проживания
    requ(url, "GET", null);
}

function clickChild() {  // реакция на щелчок по ФИО ребёнка - открыть карточку
    var id = document.getElementById("childId");
    if (id != null) {
        window.open("client?kat=children&id=" + id.value, "_blank");
    }
}

function regionSelect() {   // реакция на выбор района - зафиксировать ИД в скрытом поле
    var regId = document.getElementById("regId");
    var selReg = document.getElementById("childReg");
    regId.value = selReg.value;
}

function clickPol() {   // реакция на выбор пола - зафиксировать в скрытом поле
    if (this.childNodes[0].checked) {
        var pol = document.getElementById("pol");
        pol.value = this.childNodes[0].value;
    }
}

function clearReg() {   // очистка списка районов
    var reg = document.getElementById("childReg");
    if (reg != null) {
        reg.innerHTML = "";
    }
}

function appendRegion(id, name) {   // добавление района в список
    var reg = document.getElementById("childReg");
    if (reg != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        reg.appendChild(opt);
        reg.onchange();
    }
}

function otchOmsuDate() {   // формирование запроса на расчёт даты отчёта ОМСУ
    var period = 50;
    var type = "days";
    var direct = "back";
    var inp = document.getElementById("ipraDateOk");
    var date = inp.value;
    if (date != "") {
        var url = "ipra2018new?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET");
    } else {
        alert("Заполните дату окончания ИПРА");
    }
}

function otchCenterDate() { // формирование запроса на расчёт даты отчёта ОЦППМСП
    var period = 40;
    var type = "days";
    var direct = "back";
    var inp = document.getElementById("ipraDateOk");
    var date = inp.value;
    if (date != "") {
        var url = "ipra2018new?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET");
    } else {
        alert("Заполните дату окончания ИПРА");
    }
}

function otchDoDate() { // формирование запроса на расчёт даты отчёта ДО
    var period = 1;
    var type = "month";
    var direct = "back";
    var inp = document.getElementById("ipraDateOk");
    var date = inp.value;
    if (date != "") {
        var url = "ipra2018new?action=date&id=" + this.id + "&date=" + date + "&period=" + period + "&type=" + type + "&direct=" + direct;
        requ(url, "GET", null);
    } else {
        alert("Заполните дату окончания ИПРА");
    }
}

function appendDate(id, date) { // установка даты в нужное поле
    var inp = document.getElementById(id);
    if (inp != null) {
        inp.value = date;
    }
}

function validateIpra() {   // валидация данных формы
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
    drLimit.setFullYear(1975);
    drLimit.setHours(0);
    drLimit.setMinutes(0);
    drLimit.setSeconds(0);
    drLimit.setMilliseconds(0);

    var result = true;

    // выбираем все поля
    var inp = document.getElementsByTagName("input");
    var sel = document.getElementsByTagName("select");
    var div = document.getElementsByTagName("div");
    // очищаем следы предыдущей проверки
    for (loop = 0; loop < inp.length; loop++) {
        if (inp[loop].classList.contains("wrong")) {
            inp[loop].classList.remove("wrong");
        }
    }
    for (loop = 0; loop < sel.length; loop++) {
        if (sel[loop].classList.contains("wrong")) {
            sel[loop].classList.remove("wrong");
        }
    }
    for (loop = 0; loop < div.length; loop++) {
        if (div[loop].classList.contains("wrong")) {
            div[loop].classList.remove("wrong");
        }
    }

    // проверяем, чтобы был выбран ребёнок или выбран ввод данных нового
    var childId = document.getElementById("childId");
    if (childId == null) {
        var childFam = document.getElementById("childFam");
        if (childFam == null) {
            document.getElementById("divChild").className = "wrong";
        }
    }
    // выбираем все поля
    var inp = document.getElementsByTagName("input");

    // проверяем заполнение обязательных полей и корректность данных
    for (loop = 0; loop < inp.length; loop++) {
        var id = inp[loop].id;
        var val = inp[loop].value;
        if ((id == "childFam") || (id == "childName")) {    // фамилия и имя 
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "childDr") {   // дата рождения
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
        } else if (id == "ipraN") { // номер ИПРА
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "ipraNexp") {  // номер протокола экспертизы
            if (val == "") {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "ipraDateExp") {   // дата экспертизы
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
        } else if (id == "ipraDateOk") {    // дата окончания ИПРА
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
        } else if (id == "mseId") {  // бюро МСЭ
            if (val == "") {
                var selMse = document.getElementById("selMse");
                selMse.className = "wrong";
                result = false;
            }
        } else if (id == "ipraVhToDOD") {   // дата входящего письма с ИПРА в ДО из МСЭ
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
        } else if (id == "ipraVhD") {    // дата входящего письма с ИПРА в ОЦППМСП из ДО 
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


        /* else if (id == "prikazDoN") {
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
         }*/ else if (id == "ipraOtchomsu") {   // дата отчёта ОМСУ
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
        } else if (id == "ipraOtchcenter") {    // дата отчёта ОЦППМСП
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
        } else if (id == "ipraOtchdo") {    // дата отчёта ДО
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
        }/* else if (id == "omsuReqD") {
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
         }*/
    }
    return result;
    /*if (!result) {
     alert("Проверьте данные в полях, отмеченных КРАСНЫМ ЦВЕТОМ (проверяется заполнение и корректность)");
     var wrong = document.getElementsByClassName("wrong");
     wrong[0].focus();
     document.getElementById("saveBtn").disabled = 0;
     /*var printPrikazBtn = document.getElementById("printPrikazBtn");
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
     }*/

    /* } else if (result) {
     saveipra();
     }*/
}

function clickSaveBtn() {   // нажали кнопку "Сохранить"
    this.className = "dsbldbtn";    // кнопка становится неактивной
    this.onclick = doNothing;
    if (validateIpra()) {   // если проходит валидация - передаём данные на сервер для сохранения
        saveipra();
    } else {    // если ошибка валидации, выводим сообщение, возвращаем кнопку в активное состояние
        alert("Проверьте данные в полях, отмеченных КРАСНЫМ ЦВЕТОМ (проверяется заполнение и корректность)");
        var wrong = document.getElementsByClassName("wrong");
        wrong[0].focus();
        this.classList.remove("dsbldbtn");
        this.className = "greybtn";
        this.onclick = clickSaveBtn;
    }
}

function saveipra() {   // сохранение
    var form = document.getElementById("ipraForm");
    var body = "action=save&" + getRequestBody(form);
    requ("ipra2018new", "POST", body);
}

function getRequestBody(form) {     // извлечение из формы данных полей для передачи запроса на сервер
    var params = new Array();
    for (var i = 0; i < form.elements.length; i++) {
        var param = encodeURIComponent(form.elements[i].name);
        param += "=";
        param += encodeURIComponent(form.elements[i].value);
        params.push(param);
    }
    return params.join("&");
}

function mseSelect() {  // выбор МСЭ - фиксирование ИД в скрытом поле
    var mseId = document.getElementById("mseId");
    var selMse = document.getElementById("selMse");
    mseId.value = selMse.value;
}

function appendResult(result) {     // обработка результата
    if (result == 0) { // ошибка 
        alert("Данные не были сохранены. Проверьте корректность\nПри повторении ошибки обратитесь к администратору");
        var saveBtn = document.getElementById("saveBtn");
        saveBtn.classList.remove("dsbldbtn");   // возвращаем кнопку в активное состояние
        saveBtn.className = "greybtn";
        saveBtn.onclick = clickSaveBtn;
        /*    document.getElementById("savePerechenBtn").disabled = 0;
         document.getElementById("printPrikazBtn").disabled = 0;
         document.getElementById("printPerechenBtn").disabled = 0;*/
    } else {
        /*var printFlag = document.getElementById("printFlag");
         var printPerechenFlag = document.getElementById("printPerechenFlag");
         
         if ((printFlag != null) && (printFlag.value == "1")) {
         printPrikaz();
         printFlag.value = "0";
         window.location = "ipra2018spisok?action=open&id=" + result;
         } else if ((printPerechenFlag != null) && (printPerechenFlag.value == "1")) {
         printPerechen();
         printPerechenFlag.value = "0";
         window.location = "ipra2018spisok?action=open&id=" + result;
         } else {*/
        alert("Данные сохранены");
        window.location = "ipra2018new?action=open&id=" + result;
        //}

    }
}

function doNothing() {
    return false;
}

function letterReg() {   // открытие диалога регистрации письма
    var letterRegDlg = document.getElementById("letterRegDlg");
    var letterDate = document.getElementById("letterDate");
    if (this.id == "ipraVhN") {  // если диалог вызван с поля входящего письма из ДО с ИПРА
        var ipraVhD = document.getElementById("ipraVhD");
        letterDate.value = ipraVhD.value;   // устанавливаем дату (с формы, если она там внесена)
        var url1 = "ipra2018new?action=sender&id=ipra"; // отправляем запрос на список отправителей
        requ(url1, "GET", null);
        var url2 = "ipra2018new?action=getnom&id=vh";   // отправляем запрос на номер письма
        requ(url2, "GET", null);
        // тема письма
    }
    letterRegDlg.showModal();
}

function chStatus() {   // выбор статуса (в работе/завершена)
    var st = document.getElementById("status");
    if (st != null) {
        st.value = this.value;
    }
}