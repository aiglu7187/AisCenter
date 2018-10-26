/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function save() {
    var form = document.getElementById("nomForm");
    var body = "action=save&" + getRequestBody(form);

    requ("ipraishnom", "POST", body);
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

function validate() {
    var btn = document.getElementById("saveBtn");
    btn.disabled = "disabled";
    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        inp[loop].classList.remove("wrong");
    }
    var limit = 10; // максимальная длина поля суффикса

    var result = true;

    var suffix = document.getElementById("suffix");
    if (suffix.value.length > limit) {
        suffix.className = "wrong";
        result = false;
    }

    if (!result) {
        alert("Проверьте данные в полях, отмеченных КРАСНЫМ ЦВЕТОМ\n(проверяется корректность и количество символов)");
        var wrong = document.getElementsByClassName("wrong");
        wrong[0].focus();
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
    } else {
        save();
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
            alert("Ошибка сервера " + request.statusText + "\nДанные не были сохранены. Обратитесь к администратору");
            var btn = document.getElementById("saveBtn");
            btn.disabled = 0;
        } else if (request.status == 204) {
        } else {
            alert("Ошибка подключения к серверу\nДанные не были сохранены");
            var btn = document.getElementById("saveBtn");
            btn.disabled = 0;
        }
    }
}

function parseMessages(responseXML) {   // преобразование полученного xml
    if (responseXML == null) {
        return false;
    } else {
        var result = responseXML.getElementsByTagName("result")[0];
        if (result != null) {
            appendResult(result.childNodes[0].nodeValue);
        }
    }
}

function appendResult(result) {
    if (result == 0) { // ошибка 
        alert("Данные не были сохранены. Проверьте корректность\nПри повторении ошибки обратитесь к администратору");
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
    } else {
        alert("Данные сохранены");
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
        window.location = "ipraishnom?action=open";
    }
}
