/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    var chb_fio = document.getElementById("chb_fio");
    chb_fio.onclick = checkFio;
    var chb_oo = document.getElementById("chb_oo");
    chb_oo.onclick = checkOo;
    var chb_stage = document.getElementById("chb_stage");
    chb_stage.onclick = checkStage;
    var chb_op = document.getElementById("chb_op");
    chb_op.onclick = checkOp;
    var chb_formobr = document.getElementById("chb_formobr");
    chb_formobr.onclick = checkFormobr;
    regSelect();
}

function testfiles() {
    var btn = document.getElementById("testBtn");
    btn.disabled = "disabled";
    var inps = document.getElementsByTagName("input");
    for (loop = 0; loop < inps.length; loop++) {
        inps[loop].classList.remove("wrong");
    }
    var files = document.getElementsByName("files");
    var fileName = "";
    for (var i = 0; i < files.length; i++) {
        if (files[i].type == "radio" && files[i].checked) {
            fileName = files[i].value;
        }
    }
    var result = true;
    if (fileName == "") {
        alert("Выберите файл");
        result = false;
        btn.disabled = 0;
    } else {
        if (result) {
            for (loop = 0; loop < inps.length; loop++) {
                if (inps[loop].value == "") {
                    inps[loop].className = "wrong";
                    result = false;
                }
            }
        }
        if (!result) {
            alert("Необходимо заполнить все поля");
            var wrong = document.getElementsByClassName("wrong");
            wrong[0].focus();
            btn.disabled = 0;
        } else {
            var form = document.getElementById("testreestrForm");
            var body = "action=testreestr&" + getRequestBody(form);
            for (var i = 0; i < files.length; i++) {
                if (files[i].type == "radio" && files[i].checked) {
                    fileName = files[i].value;
                }
            }
            body += "&filename=" + fileName;
            requ("monitoring", "POST", body);
        }
    }
}

function checkFio() {
    var divFioDetails = document.getElementById("divFioDetails");
    if (!this.checked) {
        divFioDetails.innerHTML = "";
        this.value = 0;
    } else {
        this.value = 1;
        divFioDetails.appendChild(document.createTextNode("Номер столбца с фамилией: "));
        var inpFamCol = document.createElement("input");
        inpFamCol.id = "inpFamCol";
        inpFamCol.name = "inpFamCol";
        inpFamCol.type = "text";
        divFioDetails.appendChild(inpFamCol);
        divFioDetails.appendChild(document.createElement("br"));
        divFioDetails.appendChild(document.createTextNode("Номер столбца с именем: "));
        var inpNameCol = document.createElement("input");
        inpNameCol.id = "inpNameCol";
        inpNameCol.name = "inpNameCol";
        inpNameCol.type = "text";
        divFioDetails.appendChild(inpNameCol);
        divFioDetails.appendChild(document.createElement("br"));
        divFioDetails.appendChild(document.createTextNode("Номер столбца с отчеством: "));
        var inpPatrCol = document.createElement("input");
        inpPatrCol.id = "inpPatrCol";
        inpPatrCol.name = "inpPatrCol";
        inpPatrCol.type = "text";
        divFioDetails.appendChild(inpPatrCol);
        divFioDetails.appendChild(document.createElement("br"));
        divFioDetails.appendChild(document.createTextNode("Номер столбца с датой рождения: "));
        var inpDrCol = document.createElement("input");
        inpDrCol.id = "inpDrCol";
        inpDrCol.name = "inpDrCol";
        inpDrCol.type = "text";
        divFioDetails.appendChild(inpDrCol);
        divFioDetails.appendChild(document.createElement("br"));
    }
}

function checkOo() {
    var divOoDetails = document.getElementById("divOoDetails");
    if (!this.checked) {
        divOoDetails.innerHTML = "";
        this.value = 0;
    } else {
        this.value = 1;
        divOoDetails.appendChild(document.createTextNode("Номер столбца с образовательной организацией: "));
        var inpOoCol = document.createElement("input");
        inpOoCol.id = "inpOoCol";
        inpOoCol.name = "inpOoCol";
        inpOoCol.type = "text";
        divOoDetails.appendChild(inpOoCol);
    }
}

function checkStage() {
    var divStageDetails = document.getElementById("divStageDetails");
    if (!this.checked) {
        divStageDetails.innerHTML = "";
        this.value = 0;
    } else {
        this.value = 1;
        divStageDetails.appendChild(document.createTextNode("Номер столбца со ступенью обучения: "));
        var inpStageCol = document.createElement("input");
        inpStageCol.id = "inpStageCol";
        inpStageCol.name = "inpStageCol";
        inpStageCol.type = "text";
        divStageDetails.appendChild(inpStageCol);
    }
}

function checkOp() {
    var divOpDetails = document.getElementById("divOpDetails");
    if (!this.checked) {
        divOpDetails.innerHTML = "";
        this.value = 0;
    } else {
        this.value = 1;
        divOpDetails.appendChild(document.createTextNode("Номер столбца с образовательной программой: "));
        var inpOpCol = document.createElement("input");
        inpOpCol.id = "inpOpCol";
        inpOpCol.name = "inpOpCol";
        inpOpCol.type = "text";
        divOpDetails.appendChild(inpOpCol);
        divOpDetails.appendChild(document.createElement("br"));
        divOpDetails.appendChild(document.createTextNode("Номер столбца с вариантом образовательной программы: "));
        var inpVarCol = document.createElement("input");
        inpVarCol.id = "inpVarCol";
        inpVarCol.name = "inpVarCol";
        inpVarCol.type = "text";
        divOpDetails.appendChild(inpVarCol);
    }
}

function checkFormobr() {
    var divFormobrDetails = document.getElementById("divFormobrDetails");
    if (!this.checked) {
        divFormobrDetails.innerHTML = "";
        this.value = 0;
    } else {
        this.value = 1;
        divFormobrDetails.appendChild(document.createTextNode("Номер столбца с формой образования: "));
        var inpFormobrCol = document.createElement("input");
        inpFormobrCol.id = "inpFormobrCol";
        inpFormobrCol.name = "inpFormobrCol";
        inpFormobrCol.type = "text";
        divFormobrDetails.appendChild(inpFormobrCol);
    }
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
            alert("Ошибка сервера " + request.statusText);
            var btn = document.getElementById("testBtn");
            if (btn != null) {
                btn.disabled = 0;
            }
            var btn2 = document.getElementById("uploadBtn");
            if (btn2 != null) {
                btn2.disabled = 0;
            }
        } else {
            alert("Ошибка подключения к серверу");
            var btn = document.getElementById("testBtn");
            if (btn != null) {
                btn.disabled = 0;
            }
            var btn2 = document.getElementById("uploadBtn");
            if (btn2 != null) {
                btn2.disabled = 0;
            }
        }
    }
}

function parseMessages(responseXML) {   // преобразование полученного xml
    if (responseXML == null) {
        return false;
    } else {
        var result = responseXML.getElementsByTagName("result")[0];
        var user = responseXML.getElementsByTagName("user")[0];
        if (result != null) {
            appendResult(result.childNodes[0].nodeValue);
        } else if (user != null) {
            if (result.childNodes[0].nodeValue == 0) {
                window.open("", "_blank");
            }
        }
    }
}

function appendResult(result) {
    var btn = document.getElementById("testBtn");
    if (btn != null) {
        if (result == 0) { // ошибка 
            alert("Ошибка проверки. Проверьте файл");
        } else {
            alert("Проверка выполнена\nГотовый файл разположен в директории tested");
        }
        btn.disabled = 0;
    }
    btn = document.getElementById("uploadBtn");
    if (btn != null) {
        if (result == 0) { // ошибка 
            alert("Ошибка загрузки. Проверьте файл");
        } else {
            alert("Загрузка выполнена");
        }
        btn.disabled = 0;
    }

}

function regSelect() {
    var regId = document.getElementById("regId");
    var selReg = document.getElementById("selReg");
    regId.value = selReg.value;
}

function uploadfiles() {
    var btn = document.getElementById("uploadBtn");
    btn.disabled = "disabled";
    var inps = document.getElementsByTagName("input");
    for (loop = 0; loop < inps.length; loop++) {
        inps[loop].classList.remove("wrong");
    }
    var files = document.getElementsByName("files");
    var fileName = "";
    for (var i = 0; i < files.length; i++) {
        if (files[i].type == "radio" && files[i].checked) {
            fileName = files[i].value;
        }
    }
    var result = true;
    if (fileName == "") {
        alert("Выберите файл");
        result = false;
        btn.disabled = 0;
    } else {
        if (result) {
            for (loop = 0; loop < inps.length; loop++) {
                if (inps[loop].value == "") {
                    inps[loop].className = "wrong";
                    result = false;
                }
            }
        }
        if (result) {
            var monDate = document.getElementById("monDate");
            if (monDate.value == "") {
                monDate.className = "wrong";
                result = false;
            }

            if (monDate.value.length > 10) {
                monDate.className = "wrong";
                result = false;
            }

            var monDateD = new Date(1 * monDate.value.substr(0, 4), 1 * monDate.value.substr(5, 2) - 1, 1 * monDate.value.substr(8));
            if (monDateD == null) {
                monDate.className = "wrong";
                result = false;
            }
        }
        if (!result) {
            alert("Необходимо заполнить (проверить) все поля");
            var wrong = document.getElementsByClassName("wrong");
            wrong[0].focus();
            btn.disabled = 0;
        } else {
            var form = document.getElementById("uploadreestrForm");
            var body = "action=upload&" + getRequestBody(form);
            for (var i = 0; i < files.length; i++) {
                if (files[i].type == "radio" && files[i].checked) {
                    fileName = files[i].value;
                }
            }
            body += "&filename=" + fileName;
            requ("monitoring", "POST", body);
        }
    }
}