/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initAdd() {    // инициализация формы добавления ПМПК
    // список районов проведения ПМПК
    var url = "search?type=uslregions";
    requ(url, "GET", null);
    // список должностей для председателя
    url = "search?type=usldolgn&usl=1&n=0";
    requ(url, "GET", null);
    // устанавливаем действие на кнопку "Сохранить"
    var saveAddBtn = document.getElementById("saveAddBtn");
    saveAddBtn.onclick = saveAdd;
    //initPmpk(23488);
    initPmpk(23530);
}

function init() {    // инициализация формы    
    var saveErrorDlgBtn = document.getElementById("saveErrorDlgBtn");
    saveErrorDlgBtn.onclick = closeDlg;
    var serverErrorDlgBtn = document.getElementById("serverErrorDlgBtn");
    serverErrorDlgBtn.onclick = closeDlg;
    var connectErrorDlgBtn = document.getElementById("connectErrorDlgBtn");
    connectErrorDlgBtn.onclick = closeDlg;
    var verifyDlgBtn = document.getElementById("verifyDlgBtn");
    verifyDlgBtn.onclick = closeDlg;
    var someTroubleDlgBtn = document.getElementById("someTroubleDlgBtn");
    someTroubleDlgBtn.onclick = closeDlg;
}

function initPmpk(id) {  // инициализация формы ПМПК после сохранения
    var sved = document.getElementById("sved");
    // добавляем скрытое поле с ИД приёма
    var inpId = document.createElement("input");
    inpId.type = "hidden";
    inpId.id = "priyomId";
    inpId.name = "priyomId";
    inpId.value = id;
    sved.appendChild(inpId);
    // добавляем div-ы для вкладок с детьми
    var content = document.getElementById("content");
    var tabs = document.createElement("div");
    tabs.id = "jstabs";
    var tabPlus = document.createElement("div");
    tabPlus.classList.add("jstab");
    tabPlus.id = "tabPlus";
    tabPlus.onclick = clickTab;
    var img = document.createElement("img");
    img.src = "img/plus.png";
    img.height = "15";
    img.alt = "Добавить";
    img.title = "Добавить";
    tabPlus.appendChild(img);
    tabs.appendChild(tabPlus);
    content.appendChild(tabs);
    // отправляем запрос на сервер за списком детей
    var url = "pmpk?action=children&id=" + id;
    requ(url, "GET", null);
    // отправляем запрос на сервер за справочником учреждений
    var url2 = "pmpk?action=sprou";
    requ(url2, "GET", null);
}

function change() { // реакция на изменения
    var change = document.getElementById("change");
    if (change != null) {
        if (change.value == "0") {
            change.value = "1";
        }
    }
}

function changeReg() {   // поменяли район проведения
    change();
    var regSel = document.getElementById("regSel");
    // отправляем запрос за списком места проведения
    var url = "pmpk?action=places&regid=" + regSel.value;
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
            // показываем сообщение об ошибке
            var serverError = document.getElementById("serverError");
            serverError.showModal();
            // возвращаем кнопку в активное состояние
            var btns = document.getElementsByClassName("dsbldbtn");
            for (var i = btns.length - 1; i >= 0; i--) {
                btns[i].classList.add("greybtn");
                btns[i].classList.remove("dsbldbtn");
            }
        } else if (request.status == 204) {
        } else {
// показываем сообщение об ошибке
            var connectError = document.getElementById("connectError");
            connectError.showModal();
            // возвращаем кнопку в активное состояние
            var btns = document.getElementsByClassName("dsbldbtn");
            for (var i = btns.length - 1; i >= 0; i--) {
                btns[i].classList.add("greybtn");
                btns[i].classList.remove("dsbldbtn");
            }
        }
    }
}

function parseMessages(responseXML) {   // преобразование полученного xml
    if (responseXML == null) {
        return false;
    } else {
        var dolgns = responseXML.getElementsByTagName("dolgns")[0];
        var uslregions = responseXML.getElementsByTagName("uslregions")[0];
        var sotruds = responseXML.getElementsByTagName("sotruds")[0];
        var answer = responseXML.getElementsByTagName("answer")[0];
        var clients = responseXML.getElementsByTagName("clients")[0];
        var places = responseXML.getElementsByTagName("places")[0];
        var regions = responseXML.getElementsByTagName("regions")[0];
        var parenttypes = responseXML.getElementsByTagName("parenttypes")[0];
        var ous = responseXML.getElementsByTagName("ous")[0];
        if (dolgns != null) {    // список должностей
            var dolgn0 = dolgns.childNodes[0];
            var n0 = dolgn0.getElementsByTagName("n")[0];
            clearDolgn(n0.childNodes[0].nodeValue); // очищаем список
            for (var i = 0; i < dolgns.childNodes.length; i++) {
                var dolgn = dolgns.childNodes[i];
                var n = dolgn.getElementsByTagName("n")[0];
                var id = dolgn.getElementsByTagName("id")[0];
                var name = dolgn.getElementsByTagName("name")[0];
                appendDolgn(n.childNodes[0].nodeValue, id.childNodes[0].nodeValue, name.childNodes[0].nodeValue);
            }
            changeDolgn(n0.childNodes[0].nodeValue); // должность поменялась - будет вызвано событие
        } else if (uslregions != null) {  // если список районов
            clearRegSel(); // очищаем список
            if (uslregions.childNodes.length > 0) {
                for (var i = 0; i < uslregions.childNodes.length; i++) {
                    var region = uslregions.childNodes[i];
                    var name = region.getElementsByTagName("name")[0];
                    var id = region.getElementsByTagName("id")[0];
                    appendRegion(id.childNodes[0].nodeValue, name.childNodes[0].nodeValue);
                }
            }
            changeReg();
        } else if (sotruds != null) {    // список должностей
            var sotrud0 = sotruds.childNodes[0];
            var n0 = sotrud0.getElementsByTagName("n")[0];
            clearSotr(n0.childNodes[0].nodeValue); // очищаем список
            for (var i = 0; i < sotruds.childNodes.length; i++) {
                var sotrud = sotruds.childNodes[i];
                var n = sotrud.getElementsByTagName("n")[0];
                var id = sotrud.getElementsByTagName("id")[0];
                var name = sotrud.getElementsByTagName("name")[0];
                appendSotrud(n.childNodes[0].nodeValue, id.childNodes[0].nodeValue, name.childNodes[0].nodeValue);
            }
        } else if (answer != null) { // пришёл ответ от сервера
            appendAnswer(answer);
        } else if (clients != null) {    // список клиентов
            for (var i = 0; i < clients.childNodes.length; i++) {
                var client = clients.childNodes[i];
                appendTab(client);
            }
        } else if (places != null) { // список мест
            clearPlaceSel(); // очищаем список
            if (places.childNodes.length > 0) {
                for (var i = 0; i < places.childNodes.length; i++) {
                    var place = places.childNodes[i];
                    var name = place.getElementsByTagName("name")[0];
                    var id = place.getElementsByTagName("id")[0];
                    appendPlace(id.childNodes[0].nodeValue, name.childNodes[0].nodeValue);
                }
            }
        } else if (regions != null) {
            var type = regions.getElementsByTagName("type")[0].childNodes[0].nodeValue;
            var n = regions.getElementsByTagName("n")[0].childNodes[0].nodeValue;
            clearTypedReg(type, n);
            var regionList = regions.getElementsByTagName("region");
            for (var i = 0; i < regionList.length; i++) {
                var region = regionList[i];
                appendTypedReg(region, type, n);
            }
        } else if (parenttypes != null) {
            appendParentTypes(parenttypes);
        } else if (ous != null) {
            apendSprOu(ous);
        }
    }
}

function clearRegSel() { // очистка списка районов
    var regSel = document.getElementById("regSel");
    regSel.innerHTML = "";
}

function clearPlaceSel() { // очистка списка мест
    var placeSel = document.getElementById("placeSel");
    placeSel.innerHTML = "";
}

function appendRegion(id, name) {    // добавление районов в список
    var regSel = document.getElementById("regSel");
    var opt = document.createElement("option");
    opt.value = id;
    opt.appendChild(document.createTextNode(name));
    regSel.appendChild(opt);
}

function appendPlace(id, name) {    // добавление мест в список
    var placeSel = document.getElementById("placeSel");
    var opt = document.createElement("option");
    opt.value = id;
    opt.appendChild(document.createTextNode(name));
    placeSel.appendChild(opt);
}

function clearDolgn(n) { // очистка списка должности специалиста номер n
    var dolgnSel = document.getElementById("dolgnSel" + n);
    if (dolgnSel != null) {
        dolgnSel.innerHTML = "";
        dolgnSel.onchange = getSotr;
    }
}

function appendDolgn(n, id, name) { // добавление в список должностей для специалиста номер n
    var dolgnSel = document.getElementById("dolgnSel" + n);
    if (dolgnSel != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        dolgnSel.appendChild(opt);
    }
}

function clearSotr(n) {  // очистка списка сотрудников номер n
    var sotrSel = document.getElementById("sotrSel" + n);
    if (sotrSel != null) {
        sotrSel.innerHTML = "";
        sotrSel.onchange = change;
    }
}

function appendSotrud(n, id, name) { // добавление в список сотрудников для специалиста номер n
    var sotrSel = document.getElementById("sotrSel" + n);
    if (sotrSel != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        sotrSel.appendChild(opt);
    }
}

function changeDolgn(n) {    // запустить событие смены должности
    var dolgnSel = document.getElementById("dolgnSel" + n);
    dolgnSel.onchange();
}

function getSotr() { // запросить список сотрудников по должности
    change();
    var n = this.id.substring(8);
    var dolgnId = this.value;
    var url = "search?type=dolgnsotr&dolgn=" + dolgnId + "&n=" + n;
    requ(url, "GET", null);
}

function addSotr() {    // добавить сотрудника в состав комиссии
    var sostavTbl = document.getElementById("sostavTbl");
    if (sostavTbl != null) {
// вычисляем количество строк в таблице и номер следующей строки
        var rows = sostavTbl.getElementsByTagName("tr");
        var n = rows.length - 2;
        // добавляем строку в таблице
        var tr = document.createElement("tr");
        var td1 = document.createElement("td");
        var td2 = document.createElement("td");
        var td3 = document.createElement("td");
        // выбор должности
        var dolgnSel = document.createElement("select");
        dolgnSel.id = "dolgnSel" + n;
        dolgnSel.name = "dolgnSel" + n;
        var sotrSel = document.createElement("select");
        sotrSel.id = "sotrSel" + n;
        sotrSel.name = "sotrSel" + n;
        // запрос на поиск списка должностей
        var url = "search?type=usldolgn&usl=1&n=" + n;
        requ(url, "GET", null);
        td1.appendChild(dolgnSel);
        td2.appendChild(sotrSel);
        // крестик для удаления
        var deleteImg = document.createElement("img");
        deleteImg.id = "deleteImg";
        deleteImg.name = "deleteImg";
        deleteImg.src = "img/delete.png";
        deleteImg.width = "18";
        deleteImg.classList.add("btn");
        deleteImg.onclick = deleteSotr;
        td3.appendChild(deleteImg);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        sostavTbl.appendChild(tr);
    }
}

function deleteSotr() {  // удалить сотрудника из списка
    this.parentNode.parentNode.remove();
}

function saveAdd() { // запрос на сохранение новой ПМПК
    if (this.classList.contains("dsbldbtn")) {
        doNothing();
    } else if (this.classList.contains("greybtn")) {
        var change = document.getElementById("change"); // смотрим, были ли изменения, хотя конкретно здесь изменения будут всегда        
        if (change.value == "1") {
            var div = document.getElementById("sved");
            this.classList.remove("greybtn");
            this.classList.add("dsbldbtn"); // кнопка становится неактивной                    
            if (valid(div)) {
                var url = "pmpk";
                var body = "action=saveadd&" + getBody(div);
                requ(url, "POST", body);
            } else {
                var verify = document.getElementById("verify");
                verify.showModal();
                // возвращаем кнопку в активное состояние
                this.classList.add("greybtn");
                this.classList.remove("dsbldbtn");
            }
            change.value = "0";
        } else if (change.value == "0") {
            var noContentToSave = document.getElementById("noContentToSave");
            noContentToSave.showModal();
            setTimeout(function () {
                noContentToSave.close();
            }, 1500);
            // возвращаем кнопку в активное состояние
            this.classList.add("greybtn");
            this.classList.remove("dsbldbtn");
        }
    }
}

function doNothing() {
    return false;
}

function getBody(div) {  // "собираем" тело запроса из элементов формы
    var params = new Array();
    var inputs = div.getElementsByTagName("input");
    var selects = div.getElementsByTagName("select");
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].id != "change") {
            var param = encodeURIComponent(inputs[i].name);
            param += "=";
            param += encodeURIComponent(inputs[i].value);
            params.push(param);
        }
    }
    for (var i = 0; i < selects.length; i++) {
        var param = encodeURIComponent(selects[i].name);
        param += "=";
        param += encodeURIComponent(selects[i].value);
        params.push(param);
    }
    return params.join("&");
}

function valid(div) {   // проверка заполнения полей
// предел даты (меньше которой не должна быть дата)
    var dateLimit = new Date();
    dateLimit.setDate(1);
    dateLimit.setMonth(0);
    dateLimit.setFullYear(2010);
    dateLimit.setHours(0);
    dateLimit.setMinutes(0);
    dateLimit.setSeconds(0);
    dateLimit.setMilliseconds(0);
    // текущая дата
    var now = new Date();
    now.setHours(0);
    now.setMinutes(0);
    now.setSeconds(0);
    now.setMilliseconds(0);
    // переменная для результата, по умолчанию задаём true
    var result = true;
    // выбираем все поля для ввода
    var inputs = div.getElementsByTagName("input");
    for (var i = 0; i < inputs.length; i++) {
        if (inputs[i].classList.contains("wrong")) {
            inputs[i].classList.remove("wrong");
        }
    }
    for (var i = 0; i < inputs.length; i++) {
        var val = inputs[i].value;
        if (inputs[i].classList.contains("required")) {   // если поле обязательно для заполнения
            if (val == "") {   // проверяем, заполнено ли
                inputs[i].classList.add("wrong");
                result = false;
            } else if (inputs[i].type == "date") { // если дата - дополнительно проверяем правильность заполнения даты
                var date = new Date(1 * val.substr(0, 4), 1 * val.substr(5, 2) - 1, 1 * val.substr(8));
                if (date == null) {
                    inputs[i].classList.add("wrong");
                    result = false;
                } else {
                    if (date.getTime() > now.getTime()) {
                        inputs[i].classList.add("wrong");
                        result = false;
                    } else if (date.getTime() < dateLimit.getTime()) {
                        inputs[i].classList.add("wrong");
                        result = false;
                    }
                }
            }
        }
    }
    return result;
}

function appendAnswer(answer) {
    if (answer.childNodes[0].nodeValue == "0") {// не удалось сохранить
// показываем сообщение об ошибке
        var saveError = document.getElementById("saveError");
        saveError.showModal();
        // возвращаем кнопку в активное состояние
        var btns = document.getElementsByClassName("dsbldbtn");
        for (var i = btns.length - 1; i >= 0; i--) {
            btns[i].classList.add("greybtn");
            btns[i].classList.remove("dsbldbtn");
        }
    } else {
        var result = answer.getElementsByTagName("result")[0];
        var id = answer.getElementsByTagName("id")[0];
        if (result.childNodes[0].nodeValue == "saveadd-success") { // успешное сохранение новой ПМПК
            var goodSave = document.getElementById("goodSave");
            goodSave.showModal();
            setTimeout(function () {
                goodSave.close();
            }, 1500);
            initPmpk(id);
        } else if (result.childNodes[0].nodeValue == "saveadd-sometrouble") {  // возникли некоторые проблемы
// показываем сообщение об ошибке
            var someTrouble = document.getElementById("someTrouble");
            someTrouble.showModal();
            initPmpk(id);
        }
    }
}
function closeDlg() {   // закрывает диалог
    this.parentNode.parentNode.close();
}

function appendTab(client) {  // добавление вкладки с клиентом
    var id = client.getElementsByTagName("id")[0];
    var nom = client.getElementsByTagName("nom")[0];
    var fam = client.getElementsByTagName("fam")[0];
    var name = client.getElementsByTagName("name")[0];
    var patr = client.getElementsByTagName("patr")[0];
    var dr = client.getElementsByTagName("dr")[0];
    var reg = client.getElementsByTagName("reg")[0];
    var parents = client.getElementsByTagName("parents")[0];
    var iniciators = client.getElementsByTagName("iniciators")[0];
    var otheriniciator = client.getElementsByTagName("otheriniciator")[0];
    var reasons = client.getElementsByTagName("reasons")[0];
    var otherreason = client.getElementsByTagName("otherreason")[0];
    var pmpkfirst = client.getElementsByTagName("pmpkfirst")[0];
    var docs = client.getElementsByTagName("docs")[0];
    var otherdoc = client.getElementsByTagName("otherdoc")[0];
    var obuchenie = client.getElementsByTagName("obuchenie")[0];
    var obrs = obuchenie.getElementsByTagName("obrs")[0];
    var vars = obuchenie.getElementsByTagName("vars")[0];
    var stages = obuchenie.getElementsByTagName("stages")[0];
    var ou = obuchenie.getElementsByTagName("ou")[0];
    var otherobr = obuchenie.getElementsByTagName("otherobr")[0];
    var res = client.getElementsByTagName("res")[0];
    var npr = res.getElementsByTagName("npr")[0];
    var statuses = res.getElementsByTagName("statuses")[0];
    // добавляем вкладку
    var tabs = document.getElementById("jstabs"); // div с вкладками
    var tabList = document.getElementsByClassName("jstab");
    var max = 0;
    for (var i = 0; i < tabList.length; i++) {  // определяем максимальный номер вкладки
        var n = tabList[i].id.substring(3) * 1;
        if (n != "Plus") {
            if (max < n) {
                max = n;
            }
        }
    }
    max++;
    var tabPlus = document.getElementById("tabPlus"); // вкладка с плюсиком, перед ней будем вставлять новую вкладку
    // новая вкладка
    var tab = document.createElement("div");
    tab.classList.add("jstab");
    tab.id = "tab" + max;
    var fio = "Обследуемый"; // ФИО для заголовка вкладки
    if (fam.childNodes[0].nodeValue == " " && name.childNodes[0].nodeValue == " "
            && patr.childNodes[0].nodeValue == " ") {
    } else if (patr.childNodes[0].nodeValue != " ") {
        fio = fam.childNodes[0].nodeValue
                + " " + name.childNodes[0].nodeValue.substring(0, 1) + "."
                + patr.childNodes[0].nodeValue.substring(0, 1) + ".";
    } else if (patr.childNodes[0].nodeValue == " ") {
        fio = fam.childNodes[0].nodeValue + " " + name.childNodes[0].nodeValue.substring(0, 1) + ".";
    }

    tab.appendChild(document.createTextNode(fio));
    tab.onclick = clickTab;
    // её контент
    var tabContent = document.createElement("div");
    tabContent.classList.add("jstabContent");
    tabContent.id = "tabContent" + max;
    // таблица для размещения контента по столбцам
    var mainTbl = document.createElement("table");
    mainTbl.classList.add("maintbl");
    var mainTr = document.createElement("tr");
    var mainTd1 = document.createElement("td");
    mainTd1.classList.add("toleftborder");
    var mainTd2 = document.createElement("td");
    mainTd2.classList.add("leftborder");
    mainTd2.classList.add("toleftborder");
    var mainTd3 = document.createElement("td");
    mainTd3.classList.add("leftborder");
    mainTr.appendChild(mainTd1);
    mainTr.appendChild(mainTd2);
    mainTr.appendChild(mainTd3);
    mainTbl.appendChild(mainTr);
    // поля для ФИО, ДР и района
    var divCl = document.createElement("div");
    divCl.classList.add("lineonbot");
    var clTbl = document.createElement("table");
    clTbl.id = "clientTbl" + max;
    clTbl.classList.add("noborder");
    var clRowBtn = document.createElement("tr");
    var clCellBtn1 = document.createElement("td");
    var clCellBtn2 = document.createElement("td");
    var clRowNom = document.createElement("tr");
    var clRowFam = document.createElement("tr");
    var clRowName = document.createElement("tr");
    var clRowPatr = document.createElement("tr");
    var clRowDr = document.createElement("tr");
    var clRowReg = document.createElement("tr");
    var clCellNom = document.createElement("td");
    clCellNom.appendChild(document.createTextNode("Номер:"));
    var clCellFam = document.createElement("td");
    clCellFam.appendChild(document.createTextNode("Фамилия:"));
    var clCellName = document.createElement("td");
    clCellName.appendChild(document.createTextNode("Имя:"));
    var clCellPatr = document.createElement("td");
    clCellPatr.appendChild(document.createTextNode("Отчество:"));
    var clCellDr = document.createElement("td");
    clCellDr.appendChild(document.createTextNode("Дата рождения:"));
    var clCellReg = document.createElement("td");
    clCellReg.appendChild(document.createTextNode("Район:"));
    var clCellNomInp = document.createElement("td");
    var inpNom = document.createElement("input");
    inpNom.type = "text";
    var inpId = document.createElement("input");
    inpId.type = "hidden";
    clCellNomInp.appendChild(inpNom);
    clCellNomInp.appendChild(inpId);
    var clCellFamInp = document.createElement("td");
    var inpFam = document.createElement("input");
    inpFam.type = "text";
    clCellFamInp.appendChild(inpFam);
    var clCellNameInp = document.createElement("td");
    var inpName = document.createElement("input");
    inpName.type = "text";
    clCellNameInp.appendChild(inpName);
    var clCellPatrInp = document.createElement("td");
    var inpPatr = document.createElement("input");
    inpPatr.type = "text";
    clCellPatrInp.appendChild(inpPatr);
    var clCellDrInp = document.createElement("td");
    var inpDr = document.createElement("input");
    inpDr.type = "date";
    clCellDrInp.appendChild(inpDr);
    var clCellRegInp = document.createElement("td");
    // заполнение полей
    if (id.childNodes[0].nodeValue != " ") {
        inpId.value = id.childNodes[0].nodeValue;
        inpNom.value = nom.childNodes[0].nodeValue;
        inpNom.disabled = "disabled";
        inpFam.value = fam.childNodes[0].nodeValue;
        inpFam.disabled = "disabled";
        inpName.value = name.childNodes[0].nodeValue;
        inpName.disabled = "disabled";
        inpPatr.value = patr.childNodes[0].nodeValue;
        inpPatr.disabled = "disabled";
        inpDr.value = dr.childNodes[0].nodeValue;
        inpDr.disabled = "disabled";
        var inpReg = document.createElement("input");
        inpReg.type = "text";
        inpReg.value = reg.childNodes[0].nodeValue;
        inpReg.disabled = "disabled";
        clCellRegInp.appendChild(inpReg);
        clRowNom.appendChild(clCellNom);
        clRowNom.appendChild(clCellNomInp);
        var imgClEdit = createImg("btn", "editClient", "Найти", "img/edit.png");
        imgClEdit.width = "20";
        clCellBtn1.appendChild(imgClEdit);
        var imgClDel = createImg("btn", "delClient", "Удалить из списка", "img/delete.png");
        imgClDel.style = "margin-left: 10px;";
        imgClDel.width = "20";
        clCellBtn1.appendChild(imgClDel);
    } else {
        var selReg = document.createElement("select"); // выбор района
        selReg.id = "selRegCl" + max;
        clCellRegInp.appendChild(selReg);
        // запрос списка районов
        var url = "search?type=clregions&n=" + max;
        requ(url, "GET", null);
        if (max > 1) {
            var imgClDel = createImg("btn", "delClient", "Удалить из списка", "img/delete.png");
            imgClDel.width = "20";
            clCellBtn1.appendChild(imgClDel);
        }
    }
    clRowBtn.appendChild(clCellBtn1);
    clRowBtn.appendChild(clCellBtn2);
    clRowFam.appendChild(clCellFam);
    clRowFam.appendChild(clCellFamInp);
    clRowName.appendChild(clCellName);
    clRowName.appendChild(clCellNameInp);
    clRowPatr.appendChild(clCellPatr);
    clRowPatr.appendChild(clCellPatrInp);
    clRowDr.appendChild(clCellDr);
    clRowDr.appendChild(clCellDrInp);
    clRowReg.appendChild(clCellReg);
    clRowReg.appendChild(clCellRegInp);
    clTbl.appendChild(clRowBtn);
    clTbl.appendChild(clRowNom);
    clTbl.appendChild(clRowFam);
    clTbl.appendChild(clRowName);
    clTbl.appendChild(clRowPatr);
    clTbl.appendChild(clRowDr);
    clTbl.appendChild(clRowReg);
    divCl.appendChild(clTbl);
    mainTd1.appendChild(divCl);

    // законные представители
    var divPar = document.createElement("div");
    divPar.id = "divPar" + max;
    divPar.classList.add("lineonbot");
    var titPar = document.createElement("strong");
    titPar.appendChild(document.createTextNode("Законные представители:"));
    divPar.appendChild(titPar);
    if (parents != null) {
        var parentList = parents.getElementsByTagName("parent");
        for (var i = 0; i < parentList.length; i++) {
            var parent = parentList[i];
            var nom = i * 1 + 1;
            var parTbl = document.createElement("table");
            parTbl.id = "parTbl" + max + "_" + nom;
            var parid = parent.getElementsByTagName("parid")[0];
            var parnom = parent.getElementsByTagName("parnom")[0];
            var parfam = parent.getElementsByTagName("parfam")[0];
            var parname = parent.getElementsByTagName("parname")[0];
            var parpatr = parent.getElementsByTagName("parpatr")[0];
            var parreg = parent.getElementsByTagName("parreg")[0];
            var partypes = parent.getElementsByTagName("partypes")[0];
            var parTr1 = document.createElement("tr");
            var parTd11 = document.createElement("td");
            var parTd12 = document.createElement("td");
            parTd11.appendChild(document.createTextNode(nom + ". "));
            var imgParEdit = createImg("btn", "editPar" + nom, "Редактировать", "img/edit.png");
            imgParEdit.width = "18";
            var imgParDel = createImg("btn", "delPar" + nom, "Удалить", "img/delete.png");
            imgParDel.style = "margin-left: 5px;";
            imgParDel.width = "18";
            parTd11.appendChild(imgParEdit);
            parTd11.appendChild(imgParDel);
            parTr1.appendChild(parTd11);
            parTr1.appendChild(parTd12);
            var parTrType = document.createElement("tr");
            var parTdType1 = document.createElement("td");
            parTdType1.colSpan = "2";
            var parTdType2 = document.createElement("td");
            var selParType = document.createElement("select");
            selParType.id = "selParType" + max + "_" + nom;
            for (var i = 0; i < partypes.childNodes.length; i++) {
                var partype = partypes.childNodes[i];
                var ptopt = createOption(partype.getElementsByTagName("ptid")[0].childNodes[0].nodeValue,
                        partype.getElementsByTagName("ptname")[0].childNodes[0].nodeValue);
                if (partype.getElementsByTagName("ptselected")[0].childNodes[0].nodeValue == "1") {
                    ptopt.selected = "selected";
                }
                selParType.appendChild(ptopt);
            }
            selParType.onchange = change;
            parTdType1.appendChild(selParType);
            parTrType.appendChild(parTdType1);
            parTrType.appendChild(parTdType2);
            var parTrNom = document.createElement("tr");
            var parTdNom1 = document.createElement("td");
            var parTdNom2 = document.createElement("td");
            var inpId = createHidden("parId" + nom, parid.childNodes[0].nodeValue);
            parTdNom1.appendChild(inpId);
            parTdNom1.appendChild(document.createTextNode("Номер: "));
            var inpNom = createTextInput("parNom" + nom, parnom.childNodes[0].nodeValue);
            inpNom.disabled = "disabled";
            parTdNom2.appendChild(inpNom);
            parTrNom.appendChild(parTdNom1);
            parTrNom.appendChild(parTdNom2);
            var parTrFam = document.createElement("tr");
            var parTdFam1 = document.createElement("td");
            var parTdFam2 = document.createElement("td");
            parTdFam1.appendChild(document.createTextNode("Фамилия: "));
            var inpFam = createTextInput("parFam" + nom, parfam.childNodes[0].nodeValue);
            inpFam.disabled = "disabled";
            parTdFam2.appendChild(inpFam);
            parTrFam.appendChild(parTdFam1);
            parTrFam.appendChild(parTdFam2);
            var parTrName = document.createElement("tr");
            var parTdName1 = document.createElement("td");
            var parTdName2 = document.createElement("td");
            parTdName1.appendChild(document.createTextNode("Имя: "));
            var inpName = createTextInput("parName" + nom, parname.childNodes[0].nodeValue);
            inpName.disabled = "disabled";
            parTdName2.appendChild(inpName);
            parTrName.appendChild(parTdName1);
            parTrName.appendChild(parTdName2);
            var parTrPatr = document.createElement("tr");
            var parTdPatr1 = document.createElement("td");
            var parTdPatr2 = document.createElement("td");
            parTdPatr1.appendChild(document.createTextNode("Отчество: "));
            var inpPatr = createTextInput("parPatr" + nom, parpatr.childNodes[0].nodeValue);
            inpPatr.disabled = "disabled";
            parTdPatr2.appendChild(inpPatr);
            parTrPatr.appendChild(parTdPatr1);
            parTrPatr.appendChild(parTdPatr2);
            var parTrReg = document.createElement("tr");
            var parTdReg1 = document.createElement("td");
            var parTdReg2 = document.createElement("td");
            parTdReg1.appendChild(document.createTextNode("Район: "));
            var inpReg = createTextInput("parReg" + nom, parreg.childNodes[0].nodeValue);
            inpReg.disabled = "disabled";
            parTdReg2.appendChild(inpReg);
            parTrReg.appendChild(parTdReg1);
            parTrReg.appendChild(parTdReg2);
            parTbl.appendChild(parTr1);
            parTbl.appendChild(parTrType);
            parTbl.appendChild(parTrNom);
            parTbl.appendChild(parTrFam);
            parTbl.appendChild(parTrName);
            parTbl.appendChild(parTrPatr);
            parTbl.appendChild(parTrReg);
            divPar.appendChild(parTbl);
        }
        if (parentList.length == 1) {
            var plusPar = createImg("btn", "plusPar", "Добавить законного представителя", "img/plus.png");
            plusPar.width = "18";
            divPar.appendChild(plusPar);
        }
    } else {
        divPar.appendChild(document.createElement("br"));
        var plusPar = createImg("btn", "plusPar", "Добавить законного представителя", "img/plus.png");
        plusPar.width = "18";
        divPar.appendChild(plusPar);
    }
    mainTd1.appendChild(divPar);

    // галочка ребёнок-сирота        
    var divSirota = document.createElement("div");
    divSirota.id = "divSirota" + max;
    var lblSirota = createCheckbox("chbSirota" + max, "", "ребёнок-сирота или оставшийся без попечения родителей");
    lblSirota.onclick = sirotaCheck;
    divSirota.appendChild(lblSirota);
    divSirota.appendChild(document.createElement("br"));
    // ДДИ и ПНИ (появляются при отметке галочки "сирота"    
    var lblDdi = createCheckbox("chbDdi" + max, "", "ДДИ");
    lblDdi.style = "display:none";
    var lblPni = createCheckbox("chbPni" + max, "", "ПНИ");
    lblPni.style = "display:none";
    divSirota.appendChild(lblDdi);
    divSirota.appendChild(lblPni);
    mainTd1.appendChild(divSirota);

    // инициатор обращения    
    var divInic = document.createElement("div");
    divInic.classList.add("lineonbot");
    divInic.id = "divInic" + max;
    var titleInic = document.createElement("strong");
    titleInic.appendChild(document.createTextNode("Инициатор обращения:"));
    divInic.appendChild(titleInic);
    divInic.appendChild(document.createElement("br"));
    var iniciatorList = iniciators.getElementsByTagName("iniciator");
    var selInic = document.createElement("select");
    for (var i = 0; i < iniciatorList.length; i++) {
        var iniciator = iniciatorList[i];
        var optInic = createOption(iniciator.getElementsByTagName("inicid")[0].childNodes[0].nodeValue,
                iniciator.getElementsByTagName("inicname")[0].childNodes[0].nodeValue);
        if (iniciator.getElementsByTagName("inicoth")[0].childNodes[0].nodeValue == "1") {
            var inicOthIdx = i;
        }
        if (iniciator.getElementsByTagName("inicselected")[0].childNodes[0].nodeValue == "1") {
            optInic.selected = "selected";
        }
        selInic.appendChild(optInic);
    }
    selInic.onchange = function () {
        if (this.selectedIndex == inicOthIdx) {
            document.getElementById("otherInic" + max).style = "";
        } else {
            document.getElementById("otherInic" + max).style = "display: none;";
            document.getElementById("otherInic" + max).value = "";
        }
    };
    divInic.appendChild(selInic);
    divInic.appendChild(document.createElement("br"));
    // другой инициатор
    var othiniciator = "";
    if (otheriniciator.childNodes[0].nodeValue != " ") {
        othiniciator = otheriniciator.childNodes[0].nodeValue;
    }
    var otherInic = createTextInput("otherInic" + max, othiniciator);
    otherInic.style = "display: none;";
    divInic.appendChild(otherInic);

    divInic.appendChild(document.createElement("br"));
    var chbObjTpmpk = createCheckbox("chbObjTpmpk" + max, "", "обжалование заключения ТПМПК");
    divInic.appendChild(chbObjTpmpk);
    mainTd2.appendChild(divInic);

    // причины обращения
    var divReason = document.createElement("div");
    divReason.classList.add("lineonbot");
    divReason.id = "divReason" + max;
    var titleReason = document.createElement("strong");
    titleReason.appendChild(document.createTextNode("Причины обращения:"));
    divReason.appendChild(titleReason);
    divReason.appendChild(document.createElement("br"));
    var reasonList = reasons.getElementsByTagName("reason");
    for (var i = 0; i < reasonList.length; i++) {
        var reason = reasonList[i];
        var chbReason = createCheckbox("chbReason" + max + "_" + reason.getElementsByTagName("reasonid")[0].childNodes[0].nodeValue,
                reason.getElementsByTagName("reasonid")[0].childNodes[0].nodeValue,
                reason.getElementsByTagName("reasonname")[0].childNodes[0].nodeValue);
        if (reason.getElementsByTagName("reasonchecked")[0].childNodes[0].nodeValue == "1") {
            chbReason.childNodes[0].checked = "checked";
        }
        if (reason.getElementsByTagName("reasonoth")[0].childNodes[0].nodeValue == "1") {
            chbReason.onclick = function () {
                if (this.childNodes[0].checked) {
                    document.getElementById("inpOthReason" + max).style = "margin-left: 10px;";
                } else {
                    document.getElementById("inpOthReason" + max).style = "display: none;";
                    document.getElementById("inpOthReason" + max).value = "";
                }
            }
        }
        divReason.appendChild(chbReason);
    }
    // другая причина
    var othreason = "";
    if (otherreason.childNodes[0].nodeValue != " ") {
        othreason = otherreason.childNodes[0].nodeValue;
    }
    var inpOthReason = createTextInput("inpOthReason" + max, othreason);
    inpOthReason.style = "display: none;";
    divReason.appendChild(inpOthReason);
    mainTd2.appendChild(divReason);

    // впервые/повторно на ПМПК
    var divPmpkFirst = document.createElement("div");
    divPmpkFirst.id = "divPmpkFirst" + max;
    divPmpkFirst.appendChild(document.createTextNode("На ПМПК "));
    var selPmpkFirst = document.createElement("select");
    selPmpkFirst.id = "selPmpkFirst" + max;
    var optPmpkFirst0 = createOption("0", "повторно");
    var optPmpkFirst1 = createOption("1", "впервые");
    if (pmpkfirst.childNodes[0].nodeValue == "0") {
        optPmpkFirst0.selected = "selected";
    } else {
        optPmpkFirst1.selected = "selected";
    }
    selPmpkFirst.appendChild(optPmpkFirst1);
    selPmpkFirst.appendChild(optPmpkFirst0);
    divPmpkFirst.appendChild(selPmpkFirst);
    mainTd2.appendChild(divPmpkFirst);

    // документы
    var divDoc = document.createElement("div");
    divDoc.id = "divDoc" + max;
    divDoc.classList.add("lineonbot");
    var titleDoc = document.createElement("strong");
    titleDoc.appendChild(document.createTextNode("Документы:"));
    divDoc.appendChild(titleDoc);
    var docList = docs.getElementsByTagName("doc");
    for (var i = 0; i < docList.length; i++) {
        var doc = docList[i];
        var chbDoc = createCheckbox("chbDoc" + max + "_" + doc.getElementsByTagName("docid")[0].childNodes[0].nodeValue,
                doc.getElementsByTagName("docid")[0].childNodes[0].nodeValue,
                doc.getElementsByTagName("docname")[0].childNodes[0].nodeValue);
        if (doc.getElementsByTagName("docchecked")[0].childNodes[0].nodeValue == "1") {
            chbDoc.childNodes[0].checked = "checked";
        }
        if (doc.getElementsByTagName("docmain")[0].childNodes[0].nodeValue != "0") {
            chbDoc.style = "display: none;";
            chbDoc.childNodes[0].name = "doc" + max + "_" + doc.getElementsByTagName("docmain")[0].childNodes[0].nodeValue;   // фиксируем в имени родительский пункт справочника
        } else {
            divDoc.appendChild(document.createElement("br"));
        }
        if (doc.getElementsByTagName("docoth")[0].childNodes[0].nodeValue == "1") {
            chbDoc.onclick = function () {
                if (this.childNodes[0].checked) {
                    document.getElementById("inpOthDoc" + max).style = "margin-left: 10px;";
                } else {
                    document.getElementById("inpOthDoc" + max).style = "display: none;";
                    document.getElementById("inpOthDoc" + max).lastChild.value = "";
                }
            }
        } else {
            chbDoc.onclick = function () {
                var chb = document.getElementsByName("doc" + this.childNodes[0].id.substring(6));
                if (this.childNodes[0].checked) {
                    for (var i = 0; i < chb.length; i++) {
                        chb[i].parentNode.style = "";
                        chb[i].style = "margin-left: 20px;";
                        chb[i].parentNode.insertBefore(document.createElement("br"), chb[i]);
                    }
                } else {
                    for (var i = 0; i < chb.length; i++) {
                        chb[i].parentNode.style = "display: none;";
                        if (chb[i].previousSibling != null) {
                            chb[i].previousSibling.remove();
                        }
                    }
                }
            }
        }
        divDoc.appendChild(chbDoc);
    }
    // другой документ
    var othdoc = "";
    if (otherdoc.childNodes[0].nodeValue != " ") {
        othdoc = otherdoc.childNodes[0].nodeValue;
    }
    var inpOthDoc = createTextInput("inpOthDoc" + max, othdoc);
    inpOthDoc.style = "display: none;";
    divDoc.appendChild(inpOthDoc);
    mainTd3.appendChild(divDoc);

    // образовательное учреждение
    var ob = obuchenie.getElementsByTagName("ob")[0].childNodes[0].nodeValue;
    var divOuSved = document.createElement("div");
    divOuSved.id = "divOuSved" + max;
    var titleOu = document.createElement("strong");
    titleOu.appendChild(document.createTextNode("Сведения о получаемом образовании"));
    divOuSved.appendChild(titleOu);
    divOuSved.appendChild(document.createElement("br"));
    var radioOu1 = createRadio("radioOuYes" + max, "radioOu" + max, 1, "обучается в ОО");
    radioOu1.onclick = function () {
        document.getElementById("divOo" + max).style = "";
        document.getElementById("divOp" + max).style = "";
    };
    var radioOu0 = createRadio("radioOuNo" + max, "radioOu" + max, 0, "не обучается");
    radioOu0.style = "margin-left : 10px;";
    radioOu0.onclick = function () {
        document.getElementById("divOp" + max).style = "display: none;";
        clear(document.getElementById("divOp" + max));
        document.getElementById("divOo" + max).style = "display: none;";
        clear(document.getElementById("divOo" + max));
    };
    var radioOu2 = createRadio("radioOuOut" + max, "radioOu" + max, 2, "обучается вне ОО");
    radioOu2.style = "margin-left : 10px;";
    radioOu2.onclick = function () {
        document.getElementById("divOp" + max).style = "";
        document.getElementById("divOo" + max).style = "display: none;";
        clear(document.getElementById("divOo" + max));
    };
    if (ob == 1) {
        radioOu1.childNodes[0].checked = "checked";
    } else if (ob == 0) {
        radioOu0.childNodes[0].checked = "checked";
    } else if (ob == 2) {
        radioOu2.childNodes[0].checked = "checked";
    }
    divOuSved.appendChild(radioOu1);
    divOuSved.appendChild(radioOu2);
    divOuSved.appendChild(radioOu0);
    // образовательная организация
    var divOo = document.createElement("div");  // div с данными об ОО
    divOo.id = "divOo" + max;
    divOo.style = "display : none;";
    divOo.appendChild(document.createTextNode("Образовательная организация: "));
    divOo.appendChild(document.createElement("br"));
    var ouId = ou.getElementsByTagName("ouid")[0].childNodes[0].nodeValue;
    var hidOuId = createHidden("ouid" + max, ouId);
    var ouName = "";
    if (ou.getElementsByTagName("ouname")[0].childNodes[0].nodeValue != " ") {
        ouName = ou.getElementsByTagName("ouname")[0].childNodes[0].nodeValue
                + "(" + ou.getElementsByTagName("oureg")[0].childNodes[0].nodeValue + ")"
    }
    var inpOo = createTextInput("inpOo" + max, ouName);
    inpOo.style = "width: 400px;";
    inpOo.onfocus = openSearchOo;
    inpOo.onclick = openSearchOo;
    inpOo.onkeyup = searchingOo;
    divOo.appendChild(hidOuId);
    divOo.appendChild(inpOo);
    divOo.appendChild(document.createElement("br"));
    divOo.appendChild(document.createTextNode("Класс/группа: "));
    divOo.appendChild(document.createElement("br"));
    var selStage = document.createElement("select");
    selStage.id = "selStage" + max;
    var stageList = stages.getElementsByTagName("stage");
    var optStage0 = createOption("0", "");
    selStage.appendChild(optStage0);
    for (var i = 0; i < stageList.length; i++) {
        var stage = stageList[i];
        var optStage = createOption(stage.getElementsByTagName("stid")[0].childNodes[0].nodeValue,
                stage.getElementsByTagName("stname")[0].childNodes[0].nodeValue);
        if (stage.getElementsByTagName("stageselected")[0].childNodes[0].nodeValue == 1) {
            optStage.selected = "selected";
        }
        selStage.appendChild(optStage);
    }
    divOo.appendChild(selStage);
    divOuSved.appendChild(divOo);
    // div с данными об ОП
    var divOp = document.createElement("div");
    divOp.id = "divOp" + max;
    divOp.style = "display : none;";
    // тип образовательной программы
    var obrtypes = obuchenie.getElementsByTagName("obrtypes")[0];
    divOp.appendChild(document.createTextNode("Вид образовательной программы: "));
    divOp.appendChild(document.createElement("br"));
    var selObrType = document.createElement("select");
    selObrType.id = "selObrType" + max;
    var optOT0 = createOption(0, "");
    selObrType.appendChild(optOT0);
    var obrTypeList = obrtypes.getElementsByTagName("obrtype");
    for (var i = 0; i < obrTypeList.length; i++) {
        var ot = obrTypeList[i];
        var optOT = createOption(ot.getElementsByTagName("otid")[0].childNodes[0].nodeValue,
                ot.getElementsByTagName("otname")[0].childNodes[0].nodeValue)
        if (ot.getElementsByTagName("otselected")[0].childNodes[0].nodeValue == 1) {
            optOT.selected = "selected";
        }
        selObrType.appendChild(optOT);
    }
    selObrType.onchange = function () {
        change();
        if (this.value != 0) {
            var oth = document.getElementById("chbOtherObr" + max);
            if (!oth.checked) {
                var divObr = document.getElementById("divObr" + max);
                divObr.style = "";
                for (var j = 0; j < divObr.childNodes.length; j++) {
                    if (divObr.childNodes[j].tagName != "BR") {
                        divObr.childNodes[j].style = "display : none;";
                    }
                }
                var selObrByType = document.getElementById("selObr" + max + "_" + this.value);
                selObrByType.style = "";
                selObrByType.onchange();
            }
        } else {
            var divObr = document.getElementById("divObr" + max);
            divObr.style = "display : none;";
            clear(divObr);
            var divVar = document.getElementById("divVar" + max);
            divVar.style = "display : none;";
            clear(divVar);
        }
    };
    divOp.appendChild(selObrType);
    var divObr = document.createElement("div");
    divObr.id = "divObr" + max;
    divObr.appendChild(document.createTextNode("Образовательная программа: "));
    divObr.style = "display : none;"
    divObr.appendChild(document.createElement("br"));
    var obrList = obrs.getElementsByTagName("obr");
    for (var i = 0; i < obrTypeList.length; i++) {   // создаём списки выбора образовательных программ в соответствии с видами
        var type = obrTypeList[i];
        var selObr = document.createElement("select");
        selObr.id = "selObr" + max + "_" + type.getElementsByTagName("otid")[0].childNodes[0].nodeValue;
        selObr.name = type.getElementsByTagName("otid")[0].childNodes[0].nodeValue;
        var optObr0 = createOption(0, "");
        selObr.appendChild(optObr0);
        for (var j = 0; j < obrList.length; j++) {
            var obr = obrList[j];
            if (type.getElementsByTagName("otid")[0].childNodes[0].nodeValue ==
                    obr.getElementsByTagName("obrtypeid")[0].childNodes[0].nodeValue) {
                var optObr = createOption(obr.getElementsByTagName("obrid")[0].childNodes[0].nodeValue,
                        obr.getElementsByTagName("obrname")[0].childNodes[0].nodeValue)
                if (obr.getElementsByTagName("obrselected")[0].childNodes[0].nodeValue == 1) {
                    optObr.selected = "selected";
                }
                selObr.appendChild(optObr);
            }
        }
        selObr.style = "display : none;";
        selObr.onchange = function () {
            change();
            if (this.value != 0) {
                var selVarByObr = document.getElementById("selVar" + max + "_" + this.value);
                if (selVarByObr != null) {
                    var divVar = document.getElementById("divVar" + max);
                    divVar.style = "";
                    for (var j = 0; j < divVar.childNodes.length; j++) {
                        if (divVar.childNodes[j].tagName != "BR") {
                            divVar.childNodes[j].style = "display : none;";
                        }
                    }
                    selVarByObr.style = "";
                }
            } else {
                var divVar = document.getElementById("divVar" + max);
                divVar.style = "display : none;";
                clear(divVar);
            }
        };
        divObr.appendChild(selObr);
    }

    divOp.appendChild(divObr);
    var divVar = document.createElement("div");
    divVar.id = "divVar" + max;
    divVar.appendChild(document.createTextNode("Вариант: "));
    divVar.style = "display : none;"
    divVar.appendChild(document.createElement("br"));
    var varList = vars.getElementsByTagName("var");
    for (var i = 0; i < obrList.length; i++) { // создаём списки выбора вариантов в соответствии с программами
        var obr = obrList[i];
        var opts = new Array();  // массив для пунктов селекта
        for (var j = 0; j < varList.length; j++) {
            var v = varList[j];
            if (obr.getElementsByTagName("obrid")[0].childNodes[0].nodeValue ==
                    v.getElementsByTagName("varobrid")[0].childNodes[0].nodeValue) {
                var optVar = createOption(v.getElementsByTagName("varid")[0].childNodes[0].nodeValue,
                        v.getElementsByTagName("varname")[0].childNodes[0].nodeValue)
                if (v.getElementsByTagName("varselected")[0].childNodes[0].nodeValue == 1) {
                    optVar.selected = "selected";
                }
                opts.push(optVar);
            }
        }
        if (opts.length > 0) {
            var selVar = document.createElement("select");
            selVar.id = "selVar" + max + "_" + obr.getElementsByTagName("obrid")[0].childNodes[0].nodeValue;
            selVar.name = obr.getElementsByTagName("obrid")[0].childNodes[0].nodeValue;
            var optVar0 = createOption(0, "");
            selVar.appendChild(optVar0);
            for (var j = 0; j < opts.length; j++) {
                selVar.appendChild(opts[j]);
            }
            selVar.style = "display : none;";
            divVar.appendChild(selVar);
        }
    }
    divOp.appendChild(divVar);
    // галочка "другая"
    var chbOtherObr = createCheckbox("chbOtherObr" + max, "", "другая программа");
    chbOtherObr.onclick = function () {
        var divOther = document.getElementById("divOtherObr" + max);
        var divObrP = document.getElementById("divObr" + max);
        var divVarP = document.getElementById("divVar" + max);
        if (this.childNodes[0].checked) {
            divOther.style = "";
            divObrP.style = "display: none;";
            clear(divObrP);
            divVarP.style = "display: none;";
            clear(divVarP);
        } else {
            divOther.style = "display: none;";
            clear(divOther);
            var selType = document.getElementById("selObrType" + max);
            selType.onchange();
        }

    };
    divOp.appendChild(document.createElement("br"));
    divOp.append(chbOtherObr);
    // поле для названия другой программы
    var divOtherObr = document.createElement("div");
    divOtherObr.id = "divOtherObr" + max;
    divOtherObr.style = "display : none;";
    var lblOtherObr = document.createElement("label");
    lblOtherObr.appendChild(document.createTextNode("Название программы: "));
    divOtherObr.appendChild(lblOtherObr);
    divOtherObr.appendChild(document.createElement("br"));
    var othobr = "";
    if (otherobr.childNodes[0].nodeValue != 0) {
        othobr = otherobr.childNodes[0].nodeValue;
    }
    var inpOtherObr = createTextInput("otherObr" + max, othobr);
    inpOtherObr.style = "width: 280px;";
    divOtherObr.appendChild(inpOtherObr);
    divOp.appendChild(divOtherObr);
    divOuSved.appendChild(divOp);
    mainTd3.appendChild(divOuSved);

    // вставляем таблицу в контент
    tabContent.appendChild(mainTbl);

    // результаты обследования
    var divResult = document.createElement("div");
    divResult.id = "divResult" + max;
    divResult.classList.add("contentwithlines");
    var titleResult = document.createElement("strong");
    titleResult.appendChild(document.createTextNode("Результаты обследования"));
    divResult.appendChild(titleResult);
    divResult.appendChild(document.createElement("br"));
    divResult.appendChild(document.createTextNode("Номер протокола: "));
    var nPr = "";
    if (npr.childNodes[0].nodeValue != "") {
        nPr = npr.childNodes[0].nodeValue;
    }
    var inpNPr = createTextInput("inpNPr" + max, nPr);
    divResult.appendChild(inpNPr);
    // статусы
    var tblStatus = document.createElement("table");
    var statusList = statuses.getElementsByTagName("status");
    for (var i = 1; i < statusList.length; i++) {
        var status = statusList[i];
        if (status.getElementsByTagName("statusmain")[0].childNodes[0].nodeValue == " ") {
            var trStat = document.createElement("tr");
            var tdStat1 = document.createElement("td");
            tdStat1.appendChild(document.createTextNode(status.getElementsByTagName("statusname")[0].childNodes[0].nodeValue));
            trStat.appendChild(tdStat1);
            tblStatus.appendChild(trStat);
        }
    }

    divResult.appendChild(tblStatus);
    tabContent.appendChild(divResult);

    // рекомендовано
    var divRecomend = document.createElement("div");
    divRecomend.id = "divRecomend" + max;
    divRecomend.classList.add("content");
    var titleRecomend = document.createElement("strong");
    titleRecomend.appendChild(document.createTextNode("Рекомендации"));
    divRecomend.appendChild(titleRecomend);
    tabContent.appendChild(divRecomend);

    tabs.insertBefore(tab, tabPlus); // вставляем новую вкладку перед плюсом
    tabs.appendChild(tabContent);
    selInic.onchange();
    selObrType.onchange();
    tab.onclick();
    // сбрасываем метку об изменении (срабатывает из-за места проведения и сотрудников)
    var changeInp = document.getElementById("change");
    changeInp.value = "0";
}

function clear(div) {
    var inps = div.getElementsByTagName("input");
    for (var k = 0; k < inps.length; k++) {
        if (inps[k].type == "text") {
            inps[k].value = "";
        } else if (inps[k].type == "checkbox") {
            inps[k].checked = "0";
        }
    }
    var sels = div.getElementsByTagName("select");
    for (var k = 0; k < sels.length; k++) {
        sels[k].childNodes[0].selected = "selected";
    }
}

function clickTab() {   // обработка щелчка на вкладку
    var n = this.id.substring(3); // номер вкладки
    if (n != "Plus") {
        if (!this.classList.contains("active")) {
            var content = document.getElementById("tabContent" + n);
            if (content != null) {
                hideAll();
                showContent(this, content);
            }
        }
    } else {
        hideAll();
        var stringClient = "<client><id> </id><nom> </nom><fam> </fam><name> </name><patr> </patr><dr> </dr></client>";
        var parser = new DOMParser();
        var xmlClient = parser.parseFromString(stringClient, "text/xml");
        appendTab(xmlClient);
    }
}

function showContent(tab, content) {    // показать выбранную вкладку
    tab.classList.add("active");
    content.classList.remove("hide");
    content.classList.add("show");
}

function hideAll() {    // скрыть все видимые вкладки
    var active = document.getElementsByClassName("active");
    for (var i = active.length - 1; i >= 0; i--) {
        active[i].classList.remove("active");
    }
    var show = document.getElementsByClassName("show");
    for (var i = 0; i < show.length; i++) {
        show[i].classList.add("hide");
    }
    for (var i = show.length - 1; i >= 0; i--) {
        show[i].classList.remove("show");
    }
}

function clearTypedReg(type, n) {   // очистка списка мест проведения
    if (type == "none") {
        var selReg = document.getElementById("selRegCl" + n);
        selReg.innerHTML = "";
    }
}

function appendTypedReg(region, type, n) {  // добавление в список мест
    if (type == "none") {
        var selReg = document.getElementById("selRegCl" + n);
        var id = region.getElementsByTagName("id")[0].childNodes[0].nodeValue;
        var name = region.getElementsByTagName("name")[0].childNodes[0].nodeValue;
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        selReg.appendChild(opt);
    }
}

function createImg(imgClass, id, title, src) {  // создать рисунок
    var img = document.createElement("img");
    img.classList.add(imgClass);
    img.id = id;
    img.src = src;
    img.alt = title;
    img.title = title;
    return img;
}

function createTextInput(id, value) {   // создать текстовое поле
    var inp = document.createElement("input");
    inp.type = "text";
    inp.id = id;
    inp.value = value;
    return inp;
}

function createHidden(id, value) {  // создать скрытое поле
    var inp = document.createElement("input");
    inp.type = "hidden";
    inp.id = id;
    inp.value = value;
    return inp;
}

function createCheckbox(id, value, text) {  // создание чекбокса
    var lbl = document.createElement("label");
    var chb = document.createElement("input");
    chb.type = "checkbox";
    chb.id = id;
    if (value != "") {
        chb.value = value;
    }
    lbl.appendChild(chb);
    lbl.appendChild(document.createTextNode(text));
    return lbl;
}

function sirotaCheck() {    // обработка отметки "Сирота"
    if (this.childNodes[0].checked) {
        this.parentNode.childNodes[2].style = "";
        this.parentNode.childNodes[2].checked = "0";
        this.parentNode.childNodes[3].style = "";
        this.parentNode.childNodes[3].checked = "0";
    } else {
        try {
            this.parentNode.childNodes[2].style = "display: none";
            this.parentNode.childNodes[3].style = "display: none";
        } catch (err) {
        }

    }
}

function createOption(value, text) {    // создание пункта селекта
    var opt = document.createElement("option");
    opt.value = value;
    opt.appendChild(document.createTextNode(text));
    return opt;
}

function createRadio(id, name, value, text) {   // создание радио-кнопки
    var lbl = document.createElement("label");
    var radio = document.createElement("input");
    radio.type = "radio";
    radio.id = id;
    radio.name = name;
    radio.value = value;
    lbl.appendChild(radio);
    lbl.appendChild(document.createTextNode(text));
    return lbl;
}

function apendSprOu(ous) {  // заполнение div справочника ОО
    var otherregions = document.getElementById("otherregions");
    var ouList = ous.getElementsByTagName("ou");
    var ouTbl = document.createElement("table");
    ouTbl.classList.add("noborder");
    for (var i = 0; i < ouList.length; i++) {
        var ou = ouList[i];
        var ouTr = document.createElement("tr");
        //ouTr.style = "display: none;";        
        ouTr.id = "ou" + ou.getElementsByTagName("ouid")[0].childNodes[0].nodeValue;
        var ouTd1 = document.createElement("td");
        ouTd1.appendChild(document.createTextNode(ou.getElementsByTagName("ouname")[0].childNodes[0].nodeValue));
        var ouTd2 = document.createElement("td");
        ouTd2.appendChild(document.createTextNode(ou.getElementsByTagName("oureg")[0].childNodes[0].nodeValue));
        ouTr.appendChild(ouTd1);
        ouTr.appendChild(ouTd2);
        ouTbl.appendChild(ouTr);
    }
    otherregions.appendChild(ouTbl);
}

function openSearchOo() {   // открытие справочника ОО
    var searchOo = document.getElementById("searchOo");
    if (!searchOo.classList.contains("searchDiv")) {
        searchOo.classList.add("searchDiv");
        var rect = getOffsetRect(this);
        var top = rect.top * 1 + 20;
        searchOo.style = "top: " + top + "px; left: " + rect.left + "px;";
        var ous = searchOo.getElementsByTagName("tr");
        var inpOo = this;
        var ouId = document.getElementById("ouid" + this.id.substring(5));
        for (var i = 0; i < ous.length; i++) {
            ous[i].onclick = function () {  // функция выбора ОУ из справочника
                inpOo.value = this.childNodes[0].childNodes[0].nodeValue + " ("
                        + this.childNodes[1].childNodes[0].nodeValue + ")";
                ouId.value = this.id.substring(2);
                searchOo.style = "display: none";
                searchOo.classList.remove("searchDiv");
                for (var j = 0; j < ous.length; j++) {
                    ous[j].style = "";
                }
            };
        }
    }
}

function getOffsetRect(elem) {  // узнать расположение элемента
    var box = elem.getBoundingClientRect();
    var body = document.body;
    var docElem = document.documentElement;
    var scrollTop = window.pageYOffset || docElem.scrollTop || body.scrollTop;
    var scrollLeft = window.pageXOffset || docElem.scrollLeft || body.scrollLeft;
    var clientTop = docElem.clientTop || body.clientTop || 0;
    var clientLeft = docElem.clientLeft || body.clientLeft || 0;
    var top = box.top + scrollTop - clientTop;
    var left = box.left + scrollLeft - clientLeft;
    return {top: Math.round(top), left: Math.round(left)}
}

function searchingOo() {    // поиск по справочнику ОО
    var ouId = document.getElementById("ouid" + this.id.substring(5));
    ouId.value = 0;
    var searchOo = document.getElementById("searchOo");
    var ous = searchOo.getElementsByTagName("tr");
    // проверяем, не была ли нажата кнопка Enter или Esc
    if ((window.event.keyCode == 13) || (window.event.keyCode == 27)) {
        searchOo.style = "display: none";
        searchOo.classList.remove("searchDiv");
        for (var j = 0; j < ous.length; j++) {
            ous[j].style = "";
        }
    } else {
        var text = this.value.toUpperCase();
        if (text != "") {
            var words = text.split(" ");
            for (var j = 0; j < ous.length; j++) {
                var flag = true;
                var ou = ous[j].childNodes[0].childNodes[0].nodeValue.toUpperCase();
                for (var i = 0; i < words.length; i++) {
                    if (ou.indexOf(words[i]) + 1) {
                        ous[j].style = "";
                    } else {
                        flag = false;
                    }
                }
                if (!flag) {
                    ous[j].style = "display: none;";
                }
            }
        }
    }
}
