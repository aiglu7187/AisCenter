/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {   // функция инициализации - првязываем всякие функции на поля и кнопки
// вешаем на документ запрет выделения текста элементов интерфейса
    preventSelection(document);
    // привязываем действие на щелчок мыши кнопке поиска
    var searchBtn1 = document.getElementById("searchBtn1");
    searchBtn1.onclick = search;
    // запускаем поиск
    searchBtn1.onclick();
    // привязываем действие на щелчок мыши кнопке очистки поиска
    var clearSearchBtn = document.getElementById("clearSearchBtn1");
    clearSearchBtn.onclick = clearSearch;
    // привязываем проверку даты в поле на корректность
    var dPr = document.getElementById("searchDPr1");
    dPr.onkeyup = checkDate;
    // аналогично на вкладке архива
    var searchBtn6 = document.getElementById("searchBtn6");
    searchBtn6.onclick = searchArch;
    // запускаем поиск
    searchBtn6.onclick();
    // привязываем действие на щелчок мыши кнопке очистки поиска
    var clearSearchBtn6 = document.getElementById("clearSearchBtn6");
    clearSearchBtn6.onclick = clearSearchArch;
    // привязываем действие на щелчок мыши кнопке "Добавить ИПРА"
    var addBtn = document.getElementById("addBtn1");
    addBtn.onclick = function () {
        window.open('ipra2018new?action=add', '_blank');
    };
    // формируем список для вкладки "Нет приказа"
    var url = "ipra2018new?action=noprikaz";
    requ(url, "GET", null);
    // формируем список для вкладки "Нет перечня мероприятий"
    var url = "ipra2018new?action=noperechen";
    requ(url, "GET", null);
    // формируем список для вкладки "Для запросов в ТПМПК"
    var url = "ipra2018new?action=tpmpk";
    requ(url, "GET", null);
    // формируем список для вкладки "Ближайшие отчёты"
    var url = "ipra2018new?action=upcomingotchet";
    requ(url, "GET", null);
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

function checkDate() {  // функция проверки даты на корректность
// нижний предел даты - 01.01.2010
    var dateLimit1 = new Date();
    dateLimit1.setDate(1);
    dateLimit1.setMonth(0);
    dateLimit1.setFullYear(2010);
    dateLimit1.setHours(0);
    dateLimit1.setMinutes(0);
    dateLimit1.setSeconds(0);
    dateLimit1.setMilliseconds(0);
    // верхний предел даты - 01.01.3000
    var dateLimit2 = new Date();
    dateLimit2.setDate(1);
    dateLimit2.setMonth(0);
    dateLimit2.setFullYear(3000);
    dateLimit2.setHours(0);
    dateLimit2.setMinutes(0);
    dateLimit2.setSeconds(0);
    dateLimit2.setMilliseconds(0);
    // переводим значение поля в дату
    var inputDate = new Date(1 * this.value.substr(0, 4), 1 * this.value.substr(5, 2) - 1, 1 * this.value.substr(8));
    // проверяем, что дата находится в пределах ограничений
    if ((inputDate.getTime() < dateLimit1.getTime()) || (inputDate.getTime() > dateLimit2.getTime())) {
        this.className = "wrong"; // выделяем поле как неверное
    } else {
        this.classList.remove("wrong"); // убераем выделение
    }
}

function search() { // функция поиска
    if (!this.classList.contains("dsbldbtn")) { // если кнопка не "заблокирована"
// берём значения из полей для поиска
        var fam = document.getElementById("searchFam1").value;
        var name = document.getElementById("searchName1").value;
        var patr = document.getElementById("searchPatr1").value;
        var reg = document.getElementById("selReg1").value;
        var dPr = document.getElementById("searchDPr1");
        var dPrVal = dPr.value;
        if (dPr.classList.contains("wrong")) {   // если дата неверная - берём пустое значение
            dPrVal = "";
        }
        var nPr = document.getElementById("searchNPr1").value;
        // составляем url для поиска
        var url = "search?type=ipra&fam=" + fam + "&name=" + name + "&patr=" + patr
                + "&reg=" + reg + "&dpr=" + dPrVal + "&npr=" + nPr;
        // отправляем запрос
        requ(url, "GET", null);
        // блокируем кнопки
        var searchBtn = document.getElementById("searchBtn1");
        var clearSearchBtn = document.getElementById("clearSearchBtn1");
        searchBtn.className = "dsbldbtn";
        clearSearchBtn.className = "dsbldbtn";
    }
}

function clearSearch() {    // очищаем поля поиска
    if (!this.classList.contains("dsbldbtn")) { // если кнопка не "заблокирована"
        document.getElementById("searchFam1").value = "";
        document.getElementById("searchName1").value = "";
        document.getElementById("searchPatr1").value = "";
        document.getElementById("selReg1").value = "";
        document.getElementById("searchDPr1").value = "";
        document.getElementById("searchNPr1").value = "";
    }
// запускаем поиск с пустыми полями
    var searchBtn = document.getElementById("searchBtn1");
    searchBtn.onclick();
}

function searchArch() { // функция поиска
    if (!this.classList.contains("dsbldbtn")) { // если кнопка не "заблокирована"
// берём значения из полей для поиска
        var fam = document.getElementById("searchFam6").value;
        var name = document.getElementById("searchName6").value;
        var patr = document.getElementById("searchPatr6").value;
        var reg = document.getElementById("selReg6").value;
        // составляем url для поиска
        var url = "search?type=ipraarch&fam=" + fam + "&name=" + name + "&patr=" + patr
                + "&reg=" + reg;
        // отправляем запрос
        requ(url, "GET", null);
        // блокируем кнопки
        var searchBtn = document.getElementById("searchBtn6");
        var clearSearchBtn = document.getElementById("clearSearchBtn6");
        searchBtn.className = "dsbldbtn";
        clearSearchBtn.className = "dsbldbtn";
    }
}

function clearSearchArch() {    // очищаем поля поиска
    if (!this.classList.contains("dsbldbtn")) { // если кнопка не "заблокирована"
        document.getElementById("searchFam6").value = "";
        document.getElementById("searchName6").value = "";
        document.getElementById("searchPatr6").value = "";
        document.getElementById("selReg6").value = "";
    }
// запускаем поиск с пустыми полями
    var searchBtn = document.getElementById("searchBtn6");
    searchBtn.onclick();
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
            alert("Ошибка сервера " + request.statusText + "\n Обратитесь к администратору");
            var searchBtn = document.getElementById("searchBtn1");
            resetBtn(searchBtn);
            var clearSearchBtn = document.getElementById("clearSearchBtn1");
            resetBtn(clearSearchBtn);
            var searchBtn = document.getElementById("searchBtn6");
            resetBtn(searchBtn);
            var clearSearchBtn = document.getElementById("clearSearchBtn6");
            resetBtn(clearSearchBtn);
        } else if (request.status == 204) {
        } else {
            alert("Ошибка подключения к серверу");
            var searchBtn = document.getElementById("searchBtn1");
            resetBtn(searchBtn);
            var clearSearchBtn = document.getElementById("clearSearchBtn1");
            resetBtn(clearSearchBtn);
            var searchBtn = document.getElementById("searchBtn6");
            resetBtn(searchBtn);
            var clearSearchBtn = document.getElementById("clearSearchBtn6");
            resetBtn(clearSearchBtn);
        }
    }
}

function parseMessages(responseXML) {   // преобразование полученного xml
    if (responseXML == null) {
        return false;
    } else {
        var ipras = responseXML.getElementsByTagName("ipras")[0];
        var ipraarchs = responseXML.getElementsByTagName("ipraarchs")[0];
        var ipranoprikaz = responseXML.getElementsByTagName("ipranoprikaz")[0];
        var ipranoperechen = responseXML.getElementsByTagName("ipranoperechen")[0];
        var ipratpmpk = responseXML.getElementsByTagName("ipratpmpk")[0];
        var ipraotchet = responseXML.getElementsByTagName("ipraotchet")[0];
        var tpmpkreqs = responseXML.getElementsByTagName("tpmpkreqs")[0];
        var spromsu = responseXML.getElementsByTagName("spromsu")[0];
        var iprasdlg = responseXML.getElementsByTagName("iprasdlg")[0];
        var result = responseXML.getElementsByTagName("result")[0];
        var selectedomsu = responseXML.getElementsByTagName("selectedomsu")[0];
        var prikaz = responseXML.getElementsByTagName("prikaz")[0];
        if (result != null) {
            var action = result.getElementsByTagName("action")[0];
            var value = result.getElementsByTagName("value")[0];
            appendResult(action.childNodes[0].nodeValue, value.childNodes[0].nodeValue);
        } else if (ipras != null) {    // основной список ИПРА
            var thead = document.getElementById("tabHead1"); // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody1"); // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipras.childNodes.length > 0) {
                var head = ipras.childNodes[0];
                appendHead(thead, head); // отрисовываем заголовок
                n = 1;
                if (ipras.childNodes.length > 1) {
                    for (loop = 1; loop < ipras.childNodes.length; loop++) {
                        var ipra = ipras.childNodes[loop];
                        appendBody(tbody, ipra, selectIpra); // отрисовываем строки в теле таблицы
                        n++;
                    }
                }
            }
            ipras = null;
            var searchBtn = document.getElementById("searchBtn1");
            resetBtn(searchBtn);
            var clearSearchBtn = document.getElementById("clearSearchBtn1");
            resetBtn(clearSearchBtn);
            if (n > 50) {
                appendNextBtn();
            } else {
                removeNextBtn();
            }
        } else if (ipraarchs != null) { // архив
            var thead = document.getElementById("tabHead6"); // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody6"); // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipraarchs.childNodes.length > 0) {
                var head = ipraarchs.childNodes[0];
                appendHead(thead, head); // отрисовываем заголовок
                n = 0;
                if (ipraarchs.childNodes.length > 1) {
                    for (loop = 1; loop < ipraarchs.childNodes.length; loop++) {
                        var ipra = ipraarchs.childNodes[loop];
                        appendBody(tbody, ipra, selectIpra); // отрисовываем строки в теле таблицы                       
                    }
                }
            }
            ipraarchs = null;
            var searchBtn = document.getElementById("searchBtn6");
            resetBtn(searchBtn);
            var clearSearchBtn = document.getElementById("clearSearchBtn6");
            resetBtn(clearSearchBtn);
        } else if (ipranoprikaz != null) {  // приказы
            var thead = document.getElementById("tabHead2"); // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody2"); // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipranoprikaz.childNodes.length > 0) {
                var head = ipranoprikaz.childNodes[0];
                appendHead(thead, head); // отрисовываем заголовок
                n = 0;
                if (ipranoprikaz.childNodes.length > 1) {
                    for (loop = 1; loop < ipranoprikaz.childNodes.length; loop++) {
                        var ipra = ipranoprikaz.childNodes[loop];
                        appendBody(tbody, ipra, selectIpra); // отрисовываем строки в теле таблицы                       
                    }
                }
            }
            ipranoprikaz = null;
        } else if (ipranoperechen != null) {    // перечни
            var thead = document.getElementById("tabHead3"); // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody3"); // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipranoperechen.childNodes.length > 0) {
                var head = ipranoperechen.childNodes[0];
                appendHead(thead, head); // отрисовываем заголовок
                n = 0;
                if (ipranoperechen.childNodes.length > 1) {
                    for (loop = 1; loop < ipranoperechen.childNodes.length; loop++) {
                        var ipra = ipranoperechen.childNodes[loop];
                        appendBody(tbody, ipra, selectIpra); // отрисовываем строки в теле таблицы                       
                    }
                }
            }
            ipranoperechen = null;
        } else if (ipratpmpk != null) { // запросы к ТПМПК
            var thead = document.getElementById("tabHead4"); // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody4"); // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipratpmpk.childNodes.length > 0) {
                var head = ipratpmpk.childNodes[0];
                appendHead(thead, head); // отрисовываем заголовок
                n = 0;
                if (ipratpmpk.childNodes.length > 1) {
                    for (loop = 1; loop < ipratpmpk.childNodes.length; loop++) {
                        var ipra = ipratpmpk.childNodes[loop];
                        appendBody(tbody, ipra, check); // отрисовываем строки в теле таблицы                       
                    }
                }
            }
            ipratpmpk = null;
        } else if (ipraotchet != null) { // сроки отчётов
            var thead = document.getElementById("tabHead5"); // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody5"); // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipraotchet.childNodes.length > 0) {
                var head = ipraotchet.childNodes[0];
                appendHead(thead, head); // отрисовываем заголовок
                n = 0;
                if (ipraotchet.childNodes.length > 1) {
                    for (loop = 1; loop < ipraotchet.childNodes.length; loop++) {
                        var ipra = ipraotchet.childNodes[loop];
                        appendBody(tbody, ipra, selectIpra); // отрисовываем строки в теле таблицы                       
                    }
                }
            }
            ipraotchet = null;
        } else if (tpmpkreqs != null) { // сформированные запросы к ТПМПК
            clearReqDlg("Запросы в ТПМПК:", 4, "reqtpmpk");
            if (tpmpkreqs.childNodes.length > 0) {
                for (loop = 0; loop < tpmpkreqs.childNodes.length; loop++) {
                    var tpmpkreq = tpmpkreqs.childNodes[loop];
                    var n1 = tpmpkreq.getElementsByTagName("n")[0];
                    var iptids = tpmpkreq.getElementsByTagName("iptids")[0];
                    var tpmpkname = tpmpkreq.getElementsByTagName("tpmpkname")[0];
                    var reqdate = tpmpkreq.getElementsByTagName("reqdate")[0];
                    var childrencount = tpmpkreq.getElementsByTagName("childrencount")[0];
                    appendTpmpkReq(n1.childNodes[0].nodeValue, iptids.childNodes[0].nodeValue, tpmpkname.childNodes[0].nodeValue,
                            reqdate.childNodes[0].nodeValue, childrencount.childNodes[0].nodeValue);
                }
            }
            appendReqDlgBtn("cancel", cancelReqDlg);
        } else if (spromsu != null) {    // справочник ОМСУ
            clearSelOmsu();
            if (spromsu.childNodes.length > 0) {
                for (loop = 0; loop < spromsu.childNodes.length; loop++) {
                    var omsu = spromsu.childNodes[loop];
                    var id = omsu.getElementsByTagName("id")[0];
                    var name = omsu.getElementsByTagName("name")[0];
                    appendSelOmsu(id.childNodes[0].nodeValue, name.childNodes[0].nodeValue);
                }
            }
        } else if (iprasdlg != null) {    // список ИПРА в диалоговом окне поиска
            var thead = document.getElementById("dlgTabHead"); // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("dlgTabBody"); // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (iprasdlg.childNodes.length > 0) {
                var head = iprasdlg.childNodes[0];
                appendHead(thead, head); // отрисовываем заголовок
                n = 1;
                if (iprasdlg.childNodes.length > 1) {
                    for (loop = 1; loop < iprasdlg.childNodes.length; loop++) {
                        var ipra = iprasdlg.childNodes[loop];
                        appendBody(tbody, ipra, selectDlgIpra); // отрисовываем строки в теле таблицы
                        n++;
                    }
                }
            }
            iprasdlg = null;
        } else if (selectedomsu != null) {
            appendSelectedOmsu(selectedomsu.childNodes[0].nodeValue);
        } else if (prikaz != null) {
            var prikazdate = prikaz.getElementsByTagName("prikazdate")[0];
            var prikazreason = prikaz.getElementsByTagName("prikazreason")[0];
            var ipra = prikaz.getElementsByTagName("n")[0];
            appendPrikaz(prikazdate.childNodes[0].nodeValue, prikazreason.childNodes[0].nodeValue,
                    ipra.childNodes[0].nodeValue);
        }
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
        if (head.childNodes[loop].childNodes[0].nodeValue == "checkAll") {
            var chbox = document.createElement("input");
            chbox.type = "checkbox";
            chbox.id = head.childNodes[loop].childNodes[0].nodeValue;
            chbox.onclick = checkAll;
            td.appendChild(chbox);
        } else {
            td.appendChild(document.createTextNode(head.childNodes[loop].childNodes[0].nodeValue));
        }
        tr.appendChild(td);
    }
    thead.appendChild(tr);
}

function appendBody(tbody, body, func) {   // отображение тела таблицы    
    var tr = document.createElement("tr");
    for (loop1 = 0; loop1 < body.childNodes.length; loop1++) {
        var td = document.createElement("td");
        if (body.childNodes[loop1].childNodes[0].nodeValue.substring(0, 5) == "check") {
            var chbox = document.createElement("input");
            chbox.type = "checkbox";
            chbox.id = body.childNodes[loop1].childNodes[0].nodeValue;
            chbox.onclick = check;
            td.appendChild(chbox);
        } else {
            td.appendChild(document.createTextNode(body.childNodes[loop1].childNodes[0].nodeValue));
        }
        tr.appendChild(td);
    }
    tr.onclick = func;
    tr.id = "tr" + n;
    if (n > 50) {   // скрываем строки после 50
        tr.className = "hidden";
    }
    tbody.appendChild(tr);
}

function selectIpra() {     // функция выбора ИПРА для отображения "карточки" ИПРА со всеми сведениями
    var id = this.childNodes[0].childNodes[0].nodeValue;
    window.open("ipra2018new?action=open&id=" + id, "_blank");
}

function resetBtn(btn) {       // разблокируем кнопку    
    btn.classList.remove("dsbldbtn");
    btn.className = "greybtn";
}

function appendNextBtn() {  // добавление кнопки "Показать следующие 50 строк
    var nextBtn = document.createElement("a");
    nextBtn.className = "assistbtn";
    nextBtn.id = "nextBtn1";
    nextBtn.name = "nextBtn";
    nextBtn.appendChild(document.createTextNode("[Показать следующие 50 строк]"));
    nextBtn.onclick = showNext;
    var div = document.getElementById("div1");
    div.appendChild(nextBtn);
}

function showNext() {    // показать следующие 50
// все скрытые строки
    var hidden = document.getElementsByClassName("hidden");
    // находим ИД первой скрытой строки
    var k = hidden[0].id.substring(2);
    // вычисляем кол-во отображаемых строк (50 или меньше, если их осталось меньше 50)
    var max = k * 1 + 50;
    if (max > hidden[hidden.length - 1].id.substring(2)) {
        max = hidden[hidden.length - 1].id.substring(2);
        this.remove();
    }
// отображаем скрытые строки
    for (loop = k; loop <= max; loop++) {
        var tr = document.getElementById("tr" + loop);
        tr.classList.remove("hidden");
    }
}

function removeNextBtn() {  // удаление кнопки "Показать следующие 50 строк"
    var nextBtn = document.getElementById("nextBtn1");
    if (nextBtn != null) {
        nextBtn.remove();
    }
}

function check() {  // функция отметки checkbox в таблице
    if (this.checked) {
        this.parentNode.parentNode.className = "checked";
    } else {
        this.parentNode.parentNode.classList.remove("checked");
    }
    var checkedTr = document.getElementsByClassName("checked");
    if (checkedTr.length > 0) {
        var btn = document.getElementById("formreqBtn4"); // "активируем" кнопку формирования запроса
        btn.classList.remove("dsbldbtn");
        btn.className = "greybtn";
    } else {
        var btn = document.getElementById("formreqBtn4"); // "блокируем" кнопку формирования запроса
        btn.classList.remove("greybtn");
        btn.className = "dsbldbtn";
    }
}

function checkAll() {   // функция для галочки в заголовке таблицы (отметить всё)
    var inps = document.getElementsByTagName("input");
    for (loop = 0; loop < inps.length; loop++) {
        if (inps[loop].type == "checkbox") {
            if (this.checked) {
                inps[loop].checked = "checked";
                if (inps[loop].id != "checkAll") {
                    inps[loop].onclick();
                }
            } else {
                inps[loop].checked = 0;
                if (inps[loop].id != "checkAll") {
                    inps[loop].onclick();
                }
            }
        }
    }
}

function formReq() { // реакция на кнопку "Сформировать запрос"
    var dialog = document.getElementById("reqDlg");
    dialog.showModal();
    var checkedTr = document.getElementsByClassName("checked");
    var url = "ipra2018new?action=formreqtotpmpk&id=";
    for (loop = 0; loop < checkedTr.length; loop++) {
        var tds = checkedTr[loop].getElementsByTagName("td");
        url += tds[0].childNodes[0].nodeValue.trim() + ";";
    }
    requ(url, "GET", null);
}

function doNothing() {  // функция ничегонеделания
    return false;
}

function clearReqDlg(str, tab, act) {    // очистка диалога запросов и заполнение отличительных признаков диалога
    var dlg = document.getElementById("reqDlg");
    dlg.innerHTML = "";
    var strong = document.createElement("strong");
    strong.appendChild(document.createTextNode(str));
    dlg.appendChild(strong);
    dlg.appendChild(document.createElement("br"));
    // сохраняем номер вкладки, с которой открыт диалог
    var hid = document.createElement("input");
    hid.type = "hidden";
    hid.id = "tabN";
    hid.value = tab;
    dlg.appendChild(hid);
    // прописываем, что делает этот диалог, чтобы можно было определить какие-то специфические для него действия
    var hid2 = document.createElement("input");
    hid2.type = "hidden";
    hid2.id = "act";
    hid2.value = act;
    dlg.appendChild(hid2);
}

function appendTpmpkReq(n1, ids, tpmpkname, reqdate, childrencount) {   // добавление информации о сформированном запросе    
    var tbl = document.getElementById("ipttbl");
    var isFirst = false;
    if (tbl == null) {
        isFirst = true;
        tbl = document.createElement("table");
        tbl.id = "ipttbl";
        tbl.style="margin-top: 15px; margin-bottom : 15px;";
    }
    var tr = document.createElement("tr");
    tr.style = "height: 40px;";
    var td1 = document.createElement("td");
    var iptids = document.createElement("input");
    iptids.type = "hidden";
    iptids.id = "iptids" + n1;
    iptids.name = "iptids" + n1;
    iptids.value = ids;
    td1.appendChild(iptids);
    var string = "";
    if ((childrencount == 1) || (childrencount >= 5)) {
        string = "человек";
    } else if ((childrencount > 1) && (childrencount < 5)) {
        string = "человека";
    }
    td1.appendChild(document.createTextNode(reqdate + " " + tpmpkname + " (" + childrencount + " " + string + ")  "));
    var td2 = document.createElement("td");
    // кнопка печати запроса
    var printBtn = document.createElement("a");
    printBtn.className = "greybtn";
    printBtn.onclick = printTpmpkReq;
    var img = document.createElement("img");
    printBtn.id = "printBtn";
    printBtn.name = "printBtn";
    img.id = "printImg";
    img.name = "printImg";
    img.src = "img/print.png";
    img.width = "17";
    img.style = "padding-right: 5px;";
    printBtn.appendChild(img);
    printBtn.appendChild(document.createTextNode("Зарегистрировать и выгрузить"));
    td2.appendChild(printBtn);
    tr.appendChild(td1);
    tr.appendChild(td2);
    tbl.appendChild(tr);    
    if (isFirst){
        var dlg = document.getElementById("reqDlg");
        dlg.appendChild(tbl);
    }
}

function appendReqDlgBtn(type, func) { // добавление в диалог запросов кнопки     
    var div = document.getElementById("divDialogBtn");
    if (div == null) {  // если ещё нет мест для кнопок - создаём
        var dlg = document.getElementById("reqDlg");
        div = document.createElement("div");
        div.id = "divDialogBtn";
        div.style = "margin-top: 15px; margin-bottom: 15px;";
        var tbl = document.createElement("table");
        tbl.id = "btnTbl";
        tbl.className = "no";
        var tr = document.createElement("tr");
        var td1 = document.createElement("td");
        td1.style = "text-align: left";
        var td2 = document.createElement("td");
        var td3 = document.createElement("td");
        td3.style = "text-align: right";
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tbl.appendChild(tr);
        div.appendChild(tbl);
        dlg.appendChild(div);
    }
    var tbl = document.getElementById("btnTbl");
    // создаём кнопку
    var btn = document.createElement("a");
    btn.className = "greybtn";
    btn.onclick = func;
    var img = document.createElement("img");
    var td;
    if (type == "cancel") { // "Отмена"
        btn.id = "cancelBtn";
        btn.name = "cancelBtn";
        img.id = "cancelImg";
        img.name = "cancelImg";
        img.src = "img/delete.png";
        img.width = "17";
        img.style = "padding-right: 1px;";
        btn.appendChild(img);
        btn.appendChild(document.createTextNode("Отмена"));
        td = tbl.childNodes[0].childNodes[2];
    } else if (type == "save") {    // "Сохранить"
        btn.id = "saveBtn";
        btn.name = "saveBtn";
        img.id = "saveImg";
        img.name = "saveImg";
        img.src = "img/save.png";
        img.width = "17";
        img.style = "padding-right: 1px;";
        btn.appendChild(img);
        btn.appendChild(document.createTextNode("Сохранить"));
        td = tbl.childNodes[0].childNodes[0];
    }
// вставляем кнопку на место
    td.appendChild(btn);
}

function cancelReqDlg() {    // закрытие диалога запросов
    var dialog = document.getElementById("reqDlg");
    dialog.close();
    var tab = document.getElementById("tabN");
    var tabN = 1;
    if (tab != null) {
        if (tab.value != "") {
            tabN = tab.value;
        }
    }
// обновление открытой страницы
    window.location = "ipra2018new?action=openlist&tab=" + tabN;
}

function printTpmpkReq() {  // выгрузить запрос в ТПМПК
    var n = this.id.substring(8);
    var iptids = document.getElementById("iptids" + n);
    if (iptids != null) {
        var url = "print?type=tpmpkreq&ipt=" + iptids.value;
        window.open(url, '_blank');
    }
}

function addReqDo() {   // открыть диалог запроса из ОМСУ в ДО
    var dlg = document.getElementById("reqDlg");
    dlg.showModal();
    clearReqDlg("Добавить запрос на приказ или отказ из ОМСУ в ДО", 2, "reqtodo");
    dlg.appendChild(document.createElement("br"));
    var labelR = document.createElement("label");
    labelR.id = "labelR";
    var radioR = document.createElement("input");
    radioR.type = "radio";
    radioR.name = "radioReq";
    radioR.id = "radioReq";
    radioR.value = "req";
    radioR.checked = "checked";
    labelR.appendChild(radioR);
    labelR.appendChild(document.createTextNode("запрос на приказ"));
    dlg.appendChild(labelR);
    dlg.appendChild(document.createElement("br"));
    var labelD = document.createElement("label");
    labelD.id = "labelD";
    var radioD = document.createElement("input");
    radioD.type = "radio";
    radioD.name = "radioReq";
    radioD.id = "radioReq";
    radioD.value = "dis";
    labelD.appendChild(radioD);
    labelD.appendChild(document.createTextNode("отказ"));
    dlg.appendChild(labelD);
    dlg.appendChild(document.createElement("br"));
    dlg.appendChild(document.createElement("br"));
    dlg.appendChild(document.createTextNode("ОМСУ: "));
    // список ОМСУ
    var selOmsu = document.createElement("select");
    selOmsu.id = "selOmsu";
    selOmsu.style = "height: 20px;";
    dlg.appendChild(selOmsu);
    // запрос к серверу на список ОМСУ
    var url = "search?type=omsu";
    requ(url, "GET", null);
    dlg.appendChild(document.createElement("br"));
    var tbl = document.createElement("table");
    tbl.className = "noborder";
    var tr1 = document.createElement("tr");
    var td11 = document.createElement("td");
    td11.appendChild(document.createTextNode("Исходящий номер "));
    var td12 = document.createElement("td");
    var inpIshN = document.createElement("input");
    inpIshN.type = "text";
    inpIshN.id = "ishN";
    inpIshN.style = "width: 150px;";
    td12.appendChild(inpIshN);
    var td13 = document.createElement("td");
    td13.appendChild(document.createTextNode(" от "));
    var inpIshD = document.createElement("input");
    inpIshD.type = "date";
    inpIshD.id = "ishD";
    inpIshD.style = "width: 150px; height: 20px;";
    td13.appendChild(inpIshD);
    tr1.appendChild(td11);
    tr1.appendChild(td12);
    tr1.appendChild(td13);
    tbl.appendChild(tr1);
    var tr2 = document.createElement("tr");
    var td21 = document.createElement("td");
    td21.appendChild(document.createTextNode("Входящий номер "));
    var td22 = document.createElement("td");
    var inpVhN = document.createElement("input");
    inpVhN.type = "text";
    inpVhN.id = "vhN";
    inpVhN.style = "width: 150px;";
    td22.appendChild(inpVhN);
    var td23 = document.createElement("td");
    td23.appendChild(document.createTextNode(" от "));
    var inpVhD = document.createElement("input");
    inpVhD.type = "date";
    inpVhD.id = "vhD";
    inpVhD.style = "width: 150px; height: 20px;";
    td23.appendChild(inpVhD);
    tr2.appendChild(td21);
    tr2.appendChild(td22);
    tr2.appendChild(td23);
    tbl.appendChild(tr2);
    dlg.appendChild(tbl);
    var div = document.createElement("div");
    div.id = "divIpras";
    div.appendChild(document.createTextNode("ИПРА ребёнка-инвалида: "));
    div.style = "margin-top: 15px; margin-bottom: 15px;"
    var btn = document.createElement("a");
    btn.className = "greybtn";
    btn.id = "searchBtn";
    btn.name = "searchBtn";
    btn.onclick = openDlg;
    var img = document.createElement("img");
    img.id = "addImg";
    img.name = "addImg";
    img.src = "img/plus.png";
    img.width = "16";
    img.style = "margin-right: 3px;";
    btn.appendChild(img);
    btn.appendChild(document.createTextNode("Добавить"));
    div.appendChild(btn);
    div.appendChild(document.createElement("br"));
    div.appendChild(document.createElement("br"));
    dlg.appendChild(div);
    appendReqDlgBtn("save", saveReqDo);
    appendReqDlgBtn("cancel", cancelReqDlg);
}

function clearSelOmsu() {
    var selOmsu = document.getElementById("selOmsu");
    if (selOmsu != null) {
        selOmsu.innerHTML = "";
    }
}

function appendSelOmsu(id, name) {
    var selOmsu = document.getElementById("selOmsu");
    if (selOmsu != null) {
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        selOmsu.appendChild(opt);
    }
}

function addReqCenter() {   // открыть диалог запроса ОМСУ в Центр
    var dlg = document.getElementById("reqDlg");
    dlg.showModal();
    clearReqDlg("Добавить запрос на разработку перечня мероприятий или отказ из ОМСУ в ОЦППМСП", 3, "reqtocenter");
    dlg.appendChild(document.createElement("br"));
    var labelR = document.createElement("label");
    labelR.id = "labelR";
    var radioR = document.createElement("input");
    radioR.type = "radio";
    radioR.name = "radioReq";
    radioR.id = "radioReq";
    radioR.value = "req";
    radioR.checked = "checked";
    labelR.appendChild(radioR);
    labelR.appendChild(document.createTextNode("запрос на разработку перечня"));
    dlg.appendChild(labelR);
    dlg.appendChild(document.createElement("br"));
    var labelD = document.createElement("label");
    labelD.id = "labelD";
    var radioD = document.createElement("input");
    radioD.type = "radio";
    radioD.name = "radioReq";
    radioD.id = "radioReq";
    radioD.value = "dis";
    labelD.appendChild(radioD);
    labelD.appendChild(document.createTextNode("отказ"));
    dlg.appendChild(labelD);
    dlg.appendChild(document.createElement("br"));
    dlg.appendChild(document.createElement("br"));
    dlg.appendChild(document.createTextNode("ОМСУ: "));
    // список ОМСУ
    var selOmsu = document.createElement("select");
    selOmsu.id = "selOmsu";
    selOmsu.style = "height: 20px;";
    dlg.appendChild(selOmsu);
    // запрос к серверу на список ОМСУ
    var url = "search?type=omsu";
    requ(url, "GET", null);
    dlg.appendChild(document.createElement("br"));
    var tbl = document.createElement("table");
    tbl.className = "noborder";
    var tr1 = document.createElement("tr");
    var td11 = document.createElement("td");
    td11.appendChild(document.createTextNode("Исходящий номер "));
    var td12 = document.createElement("td");
    var inpIshN = document.createElement("input");
    inpIshN.type = "text";
    inpIshN.id = "ishN";
    inpIshN.style = "width: 150px;";
    td12.appendChild(inpIshN);
    var td13 = document.createElement("td");
    td13.appendChild(document.createTextNode(" от "));
    var inpIshD = document.createElement("input");
    inpIshD.type = "date";
    inpIshD.id = "ishD";
    inpIshD.style = "width: 150px; height: 20px;";
    td13.appendChild(inpIshD);
    tr1.appendChild(td11);
    tr1.appendChild(td12);
    tr1.appendChild(td13);
    tbl.appendChild(tr1);
    var tr2 = document.createElement("tr");
    var td21 = document.createElement("td");
    td21.appendChild(document.createTextNode("Дата входящего "));
    var td22 = document.createElement("td");
    var inpVhD = document.createElement("input");
    inpVhD.type = "date";
    inpVhD.id = "vhD";
    inpVhD.style = "width: 150px; height: 20px;";
    td22.appendChild(inpVhD);
    var td23 = document.createElement("td");
    tr2.appendChild(td21);
    tr2.appendChild(td22);
    tr2.appendChild(td23);
    tbl.appendChild(tr2);
    dlg.appendChild(tbl);
    var div = document.createElement("div");
    div.id = "divIpras";
    div.appendChild(document.createTextNode("ИПРА ребёнка-инвалида: "));
    div.style = "margin-top: 15px; margin-bottom: 15px;"
    var btn = document.createElement("a");
    btn.className = "greybtn";
    btn.id = "searchBtn";
    btn.name = "searchBtn";
    btn.onclick = openDlg;
    var img = document.createElement("img");
    img.id = "addImg";
    img.name = "addImg";
    img.src = "img/plus.png";
    img.width = "16";
    img.style = "margin-right: 3px;";
    btn.appendChild(img);
    btn.appendChild(document.createTextNode("Добавить"));
    div.appendChild(btn);
    div.appendChild(document.createElement("br"));
    div.appendChild(document.createElement("br"));
    dlg.appendChild(div);
    appendReqDlgBtn("save", saveReqCenter);
    appendReqDlgBtn("cancel", cancelReqDlg);
}

function searchChild() {    // поиск ребёнка
    var fam = document.getElementById("fam").value;
    var name = document.getElementById("nam").value;
    var patr = document.getElementById("patr").value;
    var url = "search?type=ipradlg&fam=" + fam + "&name=" + name + "&patr=" + patr;
    requ(url, "GET", null);
}

function saveReqDo() {    // сохранить запрос из ОМСУ в ДО
    if (validReqDo()) {
// составляем тело запроса на сохранение
        var body = "action=savereqdo";
        ;
        body += "&req=" + document.querySelector('input[name="radioReq"]:checked').value;
        var selOmsu = document.getElementById("selOmsu");
        body += "&omsu=" + selOmsu.value;
        var ishN = document.getElementById("ishN");
        body += "&ishn=" + ishN.value;
        var ishD = document.getElementById("ishD");
        body += "&ishd=" + ishD.value;
        var vhN = document.getElementById("vhN");
        body += "&vhn=" + vhN.value;
        var vhD = document.getElementById("vhD");
        body += "&vhd=" + vhD.value;
        body += "&ipraids=";
        var divs = document.getElementsByTagName("div");
        for (loop = 0; loop < divs.length; loop++) {
            if (divs[loop].id.substring(0, 11) == "divSelected") {
                body += divs[loop].id.substring(11) + ";";
            }
        }
// делаем кнопку неактивной
        var saveBtn = document.getElementById("saveBtn");
        saveBtn.className = "dsbldbtn";
        saveBtn.onclick = doNothing;
        // отправляем
        requ("ipra2018new", "POST", body);
    }
}

function validReqDo() { // проверка заполнения полей в запросе к ДО
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
// проверяем, чтобы все необходимые поля были заполнены
    var ishN = document.getElementById("ishN");
    if (ishN.value == "") {
        result = false;
        ishN.className = "wrong";
    }
    var ishD = document.getElementById("ishD");
    if ((ishD.value == "") || (ishD.value.length > 10)) {
        result = false;
        ishD.className = "wrong";
    }
    var vhN = document.getElementById("vhN");
    if (vhN.value == "") {
        result = false;
        vhN.className = "wrong";
    }
    var vhD = document.getElementById("vhD");
    if ((vhD.value == "") || (vhD.value.length > 10)) {
        result = false;
        vhD.className = "wrong";
    }

    var divs = document.getElementsByTagName("div");
    var flag = false;
    for (loop = 0; loop < divs.length; loop++) {
        if (divs[loop].id.substring(0, 11) == "divSelected") {
            flag = true;
        }
    }
    if (!flag) {
        result = false;
        document.getElementById("divIpras").className = "wrong";
    }
    return result;
}

function openDlg() {
    var searchDlg = document.getElementById("searchDlg");
    if (searchDlg != null) {
        searchDlg.showModal();
        document.getElementById("fam").value = "";
        document.getElementById("nam").value = "";
        document.getElementById("patr").value = "";
        searchChild();
    }
}

function cancelSearchDlg() {
    var searchDlg = document.getElementById("searchDlg");
    if (searchDlg != null) {
        searchDlg.close();
        document.getElementById("fam").value = "";
        document.getElementById("nam").value = "";
        document.getElementById("patr").value = "";
        document.getElementById("dlgTabBody").innerHTML = "";
    }
}

function saveReqCenter() {  // сохранить запрос из ОМСУ в ОЦППМСП
    if (validReqCenter()) {
// составляем тело запроса на сохранение
        var body = "action=savereqcenter";
        body += "&req=" + document.querySelector('input[name="radioReq"]:checked').value;
        var selOmsu = document.getElementById("selOmsu");
        body += "&omsu=" + selOmsu.value;
        var ishN = document.getElementById("ishN");
        body += "&ishn=" + ishN.value;
        var ishD = document.getElementById("ishD");
        body += "&ishd=" + ishD.value;
        var vhD = document.getElementById("vhD");
        body += "&vhd=" + vhD.value;
        body += "&ipraids=";
        var divs = document.getElementsByTagName("div");
        for (loop = 0; loop < divs.length; loop++) {
            if (divs[loop].id.substring(0, 11) == "divSelected") {
                body += divs[loop].id.substring(11) + ";";
            }
        }
// делаем кнопку неактивной
        var saveBtn = document.getElementById("saveBtn");
        saveBtn.className = "dsbldbtn";
        saveBtn.onclick = doNothing;
        // отправляем
        requ("ipra2018new", "POST", body);
    }
}

function validReqCenter() { // проверка заполнения полей в запросе к ОЦППМСП
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
// проверяем, чтобы все необходимые поля были заполнены
    var ishN = document.getElementById("ishN");
    if (ishN.value == "") {
        result = false;
        ishN.className = "wrong";
    }
    var ishD = document.getElementById("ishD");
    if ((ishD.value == "") || (ishD.value.length > 10)) {
        result = false;
        ishD.className = "wrong";
    }
    var vhD = document.getElementById("vhD");
    if ((vhD.value == "") || (vhD.value.length > 10)) {
        result = false;
        vhD.className = "wrong";
    }

    var divs = document.getElementsByTagName("div");
    var flag = false;
    for (loop = 0; loop < divs.length; loop++) {
        if (divs[loop].id.substring(0, 11) == "divSelected") {
            flag = true;
        }
    }
    if (!flag) {
        result = false;
        document.getElementById("divIpras").className = "wrong";
    }
    return result;
}

function selectDlgIpra() {  // реакция на выбор ИПРА в списке диалога поиска
    var searchDlg = document.getElementById("searchDlg");
    searchDlg.close();
    var divIpras = document.getElementById("divIpras");
    if (divIpras != null) {
        var divI = document.createElement("div");
        divI.id = "divSelected" + this.childNodes[0].childNodes[0].nodeValue;
        var img = document.createElement("img");
        img.className = "btn";
        img.id = "delBtn";
        img.width = "17";
        img.src = "img/delete.png";
        img.onclick = delIprafromDlg;
        divI.appendChild(img);
        divI.appendChild(document.createTextNode("№" + this.childNodes[6].childNodes[0].nodeValue + " " + this.childNodes[1].childNodes[0].nodeValue + " " +
                this.childNodes[2].childNodes[0].nodeValue + " " + this.childNodes[3].childNodes[0].nodeValue + " " + this.childNodes[5].childNodes[0].nodeValue));
        divIpras.appendChild(divI);
        var act = document.getElementById("act");
        if ((act.value == "reqtodo") || (act.value == "reqtocenter")) {
// запрос к серверу определить к какому ОМСУ относится район проживания ребёнка
            var url = "ipra2018new?action=findomsubyregion&name=" + this.childNodes[5].childNodes[0].nodeValue;
            requ(url, "GET", null);
        } else if (act.value == "prikaz") {
// запрос к серверу на дату приказа
            var url = "ipra2018new?action=findprikazdate&ipra=" + this.childNodes[0].childNodes[0].nodeValue;
            requ(url, "GET", null);

        }
    }
}

function appendResult(action, value) {  // обработка ответа о результате
    if (value == 0) { // ошибка 
        alert("Данные не были сохранены. Проверьте корректность\nПри повторении ошибки обратитесь к администратору");
        var saveBtn = document.getElementById("saveBtn");
        saveBtn.classList.remove("dsbldbtn"); // возвращаем кнопку в активное состояние
        saveBtn.className = "greybtn";
        if (action == "savereqdo") {
            saveBtn.onclick = saveReqDo;
        } else if (action == "savereqcenter") {
            saveBtn.onclick = saveReqCenter;
        } else if (action == "saveprikaz") {
            saveBtn.onclick = savePrikaz;
        }
    } else {
        alert("Данные сохранены");
        var tab = document.getElementById("tabN");
        var tabVal = 1;
        if (tab != null) {
            tabVal = tab.value;
        }
        window.location = "ipra2018new?action=openlist&tab=" + tabVal;
    }
}

function delIprafromDlg() {
    this.parentNode.remove();
}

function appendSelectedOmsu(omsuId) {
    var selOmsu = document.getElementById("selOmsu");
    var options = selOmsu.getElementsByTagName("option");
    for (loop = 0; loop < options.length; loop++) {
        if (options[loop].value == omsuId) {
            options[loop].selected = "selected";
        }
    }
}

function addPrikaz() {   // открыть диалог письма с приказом из ДО
    var dlg = document.getElementById("reqDlg");
    dlg.showModal();
    clearReqDlg("Добавить письмо с приказом из ДО", 2, "prikaz");
    dlg.appendChild(document.createElement("br"));
    var tbl = document.createElement("table");
    tbl.className = "noborder";
    var tr1 = document.createElement("tr");
    var td11 = document.createElement("td");
    td11.appendChild(document.createTextNode("Исходящий номер "));
    var td12 = document.createElement("td");
    var inpIshN = document.createElement("input");
    inpIshN.type = "text";
    inpIshN.id = "ishN";
    inpIshN.style = "width: 150px;";
    td12.appendChild(inpIshN);
    var td13 = document.createElement("td");
    td13.appendChild(document.createTextNode(" от "));
    var inpIshD = document.createElement("input");
    inpIshD.type = "date";
    inpIshD.id = "ishD";
    inpIshD.style = "width: 150px; height: 20px;";
    td13.appendChild(inpIshD);
    tr1.appendChild(td11);
    tr1.appendChild(td12);
    tr1.appendChild(td13);
    tbl.appendChild(tr1);
    var tr2 = document.createElement("tr");
    var td21 = document.createElement("td");
    td21.appendChild(document.createTextNode("Дата входящего "));
    var td22 = document.createElement("td");
    var inpVhD = document.createElement("input");
    inpVhD.type = "date";
    inpVhD.id = "vhD";
    inpVhD.style = "width: 150px; height: 20px;";
    td22.appendChild(inpVhD);
    var td23 = document.createElement("td");
    tr2.appendChild(td21);
    tr2.appendChild(td22);
    tr2.appendChild(td23);
    tbl.appendChild(tr2);
    dlg.appendChild(tbl);
    // список ИПРА
    var div = document.createElement("div");
    div.id = "divIpras";
    div.appendChild(document.createTextNode("ИПРА ребёнка-инвалида: "));
    div.style = "margin-top: 15px; margin-bottom: 15px;"
    var btn = document.createElement("a");
    btn.className = "greybtn";
    btn.id = "searchBtn";
    btn.name = "searchBtn";
    btn.onclick = openDlg;
    var img = document.createElement("img");
    img.id = "addImg";
    img.name = "addImg";
    img.src = "img/plus.png";
    img.width = "16";
    img.style = "margin-right: 3px;";
    btn.appendChild(img);
    btn.appendChild(document.createTextNode("Добавить"));
    div.appendChild(btn);
    div.appendChild(document.createElement("br"));
    div.appendChild(document.createElement("br"));
    dlg.appendChild(div);
    appendReqDlgBtn("save", savePrikaz);
    appendReqDlgBtn("cancel", cancelReqDlg);
}

function savePrikaz() { // сохранить письмо с приказом
    if (validPrikaz()) {
// составляем тело запроса на сохранение
        var body = "action=saveprikaz";
        var ishN = document.getElementById("ishN");
        body += "&ishn=" + ishN.value;
        var ishD = document.getElementById("ishD");
        body += "&ishd=" + ishD.value;
        var vhD = document.getElementById("vhD");
        body += "&vhd=" + vhD.value;
        body += "&ipraids=";
        var prikaz = "";
        var divs = document.getElementsByTagName("div");
        for (loop = 0; loop < divs.length; loop++) {
            if (divs[loop].id.substring(0, 11) == "divSelected") { // если div с выбранной ИПРА
                var ipraid = divs[loop].id.substring(11);
                var prikazN = document.getElementById("prikazN" + ipraid);
                if (prikazN != null) {  // проверяем, есть ли поле для приказа
                    // добавляем данные к телу запроса
                    prikaz += "&prikazn" + ipraid + "=" + prikazN.value;
                    var prikazD = document.getElementById("prikazD" + ipraid);
                    prikaz += "&prikazd" + ipraid + "=" + prikazD.value;
                    body += ipraid + ";";
                }

            }
        }
        body += prikaz;
// делаем кнопку неактивной
        var saveBtn = document.getElementById("saveBtn");
        saveBtn.className = "dsbldbtn";
        saveBtn.onclick = doNothing;
        // отправляем
        requ("ipra2018new", "POST", body);
    }
}

function validPrikaz() { // проверка заполнения полей письма с приказом
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
// проверяем, чтобы все необходимые поля были заполнены
    var ishN = document.getElementById("ishN");
    if (ishN.value == "") {
        result = false;
        ishN.className = "wrong";
    }
    var ishD = document.getElementById("ishD");
    if ((ishD.value == "") || (ishD.value.length > 10)) {
        result = false;
        ishD.className = "wrong";
    }
    var vhD = document.getElementById("vhD");
    if ((vhD.value == "") || (vhD.value.length > 10)) {
        result = false;
        vhD.className = "wrong";
    }
    for (loop = 0; loop < inp.length; loop++) {
        if (inp[loop].id.substring(0, 7) == "prikazN") {
            if (inp[loop].value == "") {
                result = false;
                inp[loop].className = "wrong";
            }
        } else if (inp[loop].id.substring(0, 7) == "prikazD") {
            if ((inp[loop].value == "") || (inp[loop].value.length > 10)) {
                result = false;
                inp[loop].className = "wrong";
            }
        }
    }

    var divs = document.getElementsByTagName("div");
    var flag = false;
    for (loop = 0; loop < divs.length; loop++) {
        if (divs[loop].id.substring(0, 11) == "divSelected") {
            flag = true;
        }
    }
    if (!flag) {
        result = false;
        document.getElementById("divIpras").className = "wrong";
    }
    return result;
}

function appendPrikaz(prikazdate, prikazreason, ipra) {
    var divI = document.getElementById("divSelected" + ipra);
    if (divI != null) {
        if (prikazdate != "-") {
            // данные приказа       
            divI.appendChild(document.createElement("br"));
            var strong = document.createElement("strong");
            strong.appendChild(document.createTextNode("Данные приказа"));
            divI.appendChild(strong);
            divI.appendChild(document.createElement("br"));
            var prTbl = document.createElement("table");
            var prTr1 = document.createElement("tr");
            var prTd11 = document.createElement("td");
            prTd11.appendChild(document.createTextNode("номер "));
            var prikazN = document.createElement("input");
            prikazN.type = "text";
            prikazN.id = "prikazN" + ipra;
            prikazN.style = "width: 150px;";
            prTd11.appendChild(prikazN);
            prTd11.appendChild(document.createTextNode(" от "));
            var prikazD = document.createElement("input");
            prikazD.type = "date";
            prikazD.id = "prikazD" + ipra;
            prikazD.style = "width: 150px; height: 20px;"
            prikazD.value = prikazdate;
            prTd11.appendChild(prikazD);
            prTr1.appendChild(prTd11);
            prTbl.appendChild(prTr1);
            divI.appendChild(prTbl);
            var saveBtn = document.getElementById("saveBtn");
            if (saveBtn.classList.contains("dsbldbtn")) {
                saveBtn.classList.remove("dsbldbtn"); // возвращаем кнопку в активное состояние
                saveBtn.className = "greybtn";
                saveBtn.onclick = savePrikaz;
            }
        } else if (prikazreason != "-") {
            var p = document.createElement("p");
            p.style = "color: red;";
            p.appendChild(document.createTextNode(prikazreason));
            divI.appendChild(p);
            // делаем кнопку неактивной
            var saveBtn = document.getElementById("saveBtn");
            saveBtn.className = "dsbldbtn";
            saveBtn.onclick = doNothing;
        }
    }
}