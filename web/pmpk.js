/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var actionOnClick;

function initAdd() {    // инициализация формы
    // список районов проведения ПМПК
    var url = "search?type=regions";
    requ(url, "GET", null);
    // список должностей для председателя
    url = "search?type=usldolgn&usl=1&n=0";
    requ(url, "GET", null);
    // устанавливаем действие на кнопку "Сохранить"
    var saveAddBtn = document.getElementById("saveAddBtn");
    saveAddBtn.onclick = saveAdd;
}

function change() {
    var change = document.getElementById("change");
    if (change != null) {
        if (change.value === "0") {
            change.value = "1";
        }
    }
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
            for (var i = 0; i < btns.length; i++) {
                btns[i].onclick = actionOnClick;
                btns[i].className = "greybtn";
            }
        } else if (request.status == 204) {
        } else {
            // показываем сообщение об ошибке
            var connectError = document.getElementById("connectError");
            connectError.showModal();
            // возвращаем кнопку в активное состояние
            var btns = document.getElementsByClassName("dsbldbtn");
            for (var i = 0; i < btns.length; i++) {
                btns[i].onclick = actionOnClick;
                btns[i].className = "greybtn";
            }
        }
    }
}

function parseMessages(responseXML) {   // преобразование полученного xml
    if (responseXML == null) {
        return false;
    } else {
        var dolgns = responseXML.getElementsByTagName("dolgns")[0];
        var regions = responseXML.getElementsByTagName("regions")[0];
        var sotruds = responseXML.getElementsByTagName("sotruds")[0];
        var result = responseXML.getElementsByTagName("result")[0];
        if (dolgns != null) {    // список должностей
            var dolgn0 = dolgns.childNodes[0];
            var n0 = dolgn0.getElementsByTagName("n")[0];
            clearDolgn(n0.childNodes[0].nodeValue); // очищаем список
            for (loop = 0; loop < dolgns.childNodes.length; loop++) {
                var dolgn = dolgns.childNodes[loop];
                var n = dolgn.getElementsByTagName("n")[0];
                var id = dolgn.getElementsByTagName("id")[0];
                var name = dolgn.getElementsByTagName("name")[0];
                appendDolgn(n.childNodes[0].nodeValue, id.childNodes[0].nodeValue, name.childNodes[0].nodeValue);
            }
            changeDolgn(n0.childNodes[0].nodeValue);// должность поменялась - будет вызвано событие
        } else if (regions != null) {  // если список районов
            clearRegSel();// очищаем список
            if (regions.childNodes.length > 0) {
                for (loop = 0; loop < regions.childNodes.length; loop++) {
                    var region = regions.childNodes[loop];
                    var name = region.getElementsByTagName("name")[0];
                    var id = region.getElementsByTagName("id")[0];
                    appendRegion(id.childNodes[0].nodeValue, name.childNodes[0].nodeValue);
                }
            }
        } else if (sotruds != null) {    // список должностей
            var sotrud0 = sotruds.childNodes[0];
            var n0 = sotrud0.getElementsByTagName("n")[0];
            clearSotr(n0.childNodes[0].nodeValue);// очищаем список
            for (loop = 0; loop < sotruds.childNodes.length; loop++) {
                var sotrud = sotruds.childNodes[loop];
                var n = sotrud.getElementsByTagName("n")[0];
                var id = sotrud.getElementsByTagName("id")[0];
                var name = sotrud.getElementsByTagName("name")[0];
                appendSotrud(n.childNodes[0].nodeValue, id.childNodes[0].nodeValue, name.childNodes[0].nodeValue);
            }
        } else if (result != null) { // пришёл результат сохранения
            appendResult(result.childNodes[0].nodeValue);
        }
    }
}

function clearRegSel() { // очистка списка районов
    var regSel = document.getElementById("regSel");
    regSel.innerHTML = "";
}

function appendRegion(id, name) {    // добавление районов в список
    var regSel = document.getElementById("regSel");
    var opt = document.createElement("option");
    opt.value = id;
    opt.appendChild(document.createTextNode(name));
    regSel.appendChild(opt);
}

function clearDolgn(n) { // очистка списка должности специалиста номер n
    var dolgnSel = document.getElementById("dolgnSel" + n);
    if (dolgnSel !== null) {
        dolgnSel.innerHTML = "";
        dolgnSel.onchange = getSotr;
    }
}

function appendDolgn(n, id, name) { // добавление в список должностей для специалиста номер n
    var dolgnSel = document.getElementById("dolgnSel" + n);
    if (dolgnSel !== null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        dolgnSel.appendChild(opt);
    }
}

function clearSotr(n) {  // очистка списка сотрудников номер n
    var sotrSel = document.getElementById("sotrSel" + n);
    if (sotrSel !== null) {
        sotrSel.innerHTML = "";
        sotrSel.onchange = change;
    }
}

function appendSotrud(n, id, name) { // добавление в список сотрудников для специалиста номер n
    var sotrSel = document.getElementById("sotrSel" + n);
    if (sotrSel !== null) {
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
        deleteImg.className = "btn";
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
    var change = document.getElementById("change"); // смотрим, были ли изменения, хотя конкретно здесь изменения будут всегда
    actionOnClick = this.onclick;   // сохраняем действие, которое было на нажатие кнопки
    if (change.value === "1") {
        var div = document.getElementById("sved");
        this.className = "dsbldbtn";    // кнопка становится неактивной        
        this.onclick = doNothing;
        if (valid(div)) {
            var url = "pmpk";
            var body = "action=saveadd&" + getBody(div);
            requ(url, "POST", body);
        } else {
            var verify = document.getElementById("verify");
            verify.showModal();
            // возвращаем кнопку в активное состояние
            this.className = "greybtn";
            this.onclick = actionOnClick;
        }
        change.value = "0";
    } else if (change.value === "0") {
        var noContentToSave = document.getElementById("noContentToSave");
        noContentToSave.showModal();
        setTimeout(function () {
            noContentToSave.close();
        }, 1500);
        // возвращаем кнопку в активное состояние
        this.className = "greybtn";
        this.onclick = actionOnClick;
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
        if (inputs[i].id !== "change") {
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
            if (val === "") {   // проверяем, заполнено ли
                inputs[i].classList.add("wrong");
                result = false;
            } else if (inputs[i].type === "date") { // если дата - дополнительно проверяем правильность заполнения даты
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

function closeVerify() {
    var verify = document.getElementById("verify");
    verify.close();
}

function appendResult(result) {
    if (result === "saveadd") { // успешное сохранение новой ПМПК
        var goodSave = document.getElementById("goodSave");
        goodSave.showModal();
        setTimeout(function () {
            goodSave.close();
        }, 1500);
        // открытие формы на просмотр и внесение информации о клиентах
    } else if (result === "0") {    // не удалось сохранить
        // показываем сообщение об ошибке
        var saveError = document.getElementById("saveError");
        saveError.showModal();
        // возвращаем кнопку в активное состояние
        var btns = document.getElementsByClassName("dsbldbtn");
        for (var i = 0; i < btns.length; i++) {
            btns[i].onclick = actionOnClick;
            btns[i].className = "greybtn";
        }
    }
}
function closeConnectError() {
    var connectError = document.getElementById("connectError");
    connectError.close();
}
function closeServerError() {
    var serverError = document.getElementById("serverError");
    serverError.close();
}

function closeSaveError() {
    var saveError = document.getElementById("saveError");
    saveError.close();
}
