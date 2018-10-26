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
    addBtn.onclick = function (){
        window.open('ipra2018new?action=add', '_blank');
    };

    // формируем список для вкладки "Нет приказа"
    var url = "ipra2018new?action=noprikaz";
    requ(url, "GET");
    // формируем список для вкладки "Нет перечня мероприятий"
    var url = "ipra2018new?action=noperechen";
    requ(url, "GET");
    // формируем список для вкладки "Для запросов в ТПМПК"
    var url = "ipra2018new?action=tpmpk";
    requ(url, "GET");
    // формируем список для вкладки "Ближайшие отчёты"
    var url = "ipra2018new?action=upcomingotchet";
    requ(url, "GET");
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
        this.className = "wrong";   // выделяем поле как неверное
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
        requ(url, "GET");
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
        requ(url, "GET");
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
        if (ipras != null) {    // основной список ИПРА
            var thead = document.getElementById("tabHead1");    // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody1");    // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipras.childNodes.length > 0) {
                var head = ipras.childNodes[0];
                appendHead(thead, head);    // отрисовываем заголовок
                n = 1;
                if (ipras.childNodes.length > 1) {
                    for (loop = 1; loop < ipras.childNodes.length; loop++) {
                        var ipra = ipras.childNodes[loop];
                        appendBody(tbody, ipra, selectIpra);    // отрисовываем строки в теле таблицы
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
            var thead = document.getElementById("tabHead6");    // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody6");    // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipraarchs.childNodes.length > 0) {
                var head = ipraarchs.childNodes[0];
                appendHead(thead, head);    // отрисовываем заголовок
                n = 0;
                if (ipraarchs.childNodes.length > 1) {
                    for (loop = 1; loop < ipraarchs.childNodes.length; loop++) {
                        var ipra = ipraarchs.childNodes[loop];
                        appendBody(tbody, ipra, selectIpra);    // отрисовываем строки в теле таблицы                       
                    }
                }
            }
            ipraarchs = null;
            var searchBtn = document.getElementById("searchBtn6");
            resetBtn(searchBtn);
            var clearSearchBtn = document.getElementById("clearSearchBtn6");
            resetBtn(clearSearchBtn);
        } else if (ipranoprikaz != null) {  // приказы
            var thead = document.getElementById("tabHead2");    // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody2");    // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipranoprikaz.childNodes.length > 0) {
                var head = ipranoprikaz.childNodes[0];
                appendHead(thead, head);    // отрисовываем заголовок
                n = 0;
                if (ipranoprikaz.childNodes.length > 1) {
                    for (loop = 1; loop < ipranoprikaz.childNodes.length; loop++) {
                        var ipra = ipranoprikaz.childNodes[loop];
                        appendBody(tbody, ipra, selectIpra);    // отрисовываем строки в теле таблицы                       
                    }
                }
            }
            ipranoprikaz = null;
        } else if (ipranoperechen != null) {    // перечни
            var thead = document.getElementById("tabHead3");    // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody3");    // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipranoperechen.childNodes.length > 0) {
                var head = ipranoperechen.childNodes[0];
                appendHead(thead, head);    // отрисовываем заголовок
                n = 0;
                if (ipranoperechen.childNodes.length > 1) {
                    for (loop = 1; loop < ipranoperechen.childNodes.length; loop++) {
                        var ipra = ipranoperechen.childNodes[loop];
                        appendBody(tbody, ipra, selectIpra);    // отрисовываем строки в теле таблицы                       
                    }
                }
            }
            ipranoperechen = null;
        } else if (ipratpmpk != null) { // запросы к ТПМПК
            var thead = document.getElementById("tabHead4");    // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody4");    // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipratpmpk.childNodes.length > 0) {
                var head = ipratpmpk.childNodes[0];
                appendHead(thead, head);    // отрисовываем заголовок
                n = 0;
                if (ipratpmpk.childNodes.length > 1) {
                    for (loop = 1; loop < ipratpmpk.childNodes.length; loop++) {
                        var ipra = ipratpmpk.childNodes[loop];
                        appendBody(tbody, ipra, selectIpra);    // отрисовываем строки в теле таблицы                       
                    }
                }
            }
            ipratpmpk = null;
        } else if (ipraotchet != null) { // сроки отчётов
            var thead = document.getElementById("tabHead5");    // заголовок таблицы (объект в браузере)
            var tbody = document.getElementById("tabBody5");    // тело таблицы (объект в браузере)
            clearTable(thead, tbody);
            if (ipraotchet.childNodes.length > 0) {
                var head = ipraotchet.childNodes[0];
                appendHead(thead, head);    // отрисовываем заголовок
                n = 0;
                if (ipraotchet.childNodes.length > 1) {
                    for (loop = 1; loop < ipraotchet.childNodes.length; loop++) {
                        var ipra = ipraotchet.childNodes[loop];
                        appendBody(tbody, ipra, selectIpra);    // отрисовываем строки в теле таблицы                       
                    }
                }
            }
            ipraotchet = null;
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
        var btn = document.getElementById("formreqBtn4");   // "активируем" кнопку формирования запроса
        btn.classList.remove("dsbldbtn");
        btn.className = "greybtn";
    } else {
        var btn = document.getElementById("formreqBtn4");   // "блокируем" кнопку формирования запроса
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

}
