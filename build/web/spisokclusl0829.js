/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initClUsl(){
    var searchP = document.getElementById("searchP");
    hide(searchP);
}

function hide(element) {
    element.realDisplay = element.currentStyle ? element.currentStyle["display"]
    : getComputedStyle(element, null)["display"];
    element.style.display = "none";
}

function show(element) {
    element.style.display = element.realDisplay || "";
}

function changeOsnusl(){
    var osnusl = document.getElementById("osnuslSel").value;
    if (osnusl != "0"){
        var url = "clientuslaction?action=osnusl&id=" + osnusl;
        requ(url, "GET");
        var searchP = document.getElementById("searchP");
        if((searchP.offsetHeight == 0 && searchP.offsetWidth == 0)) {
            show(searchP);
        }
        search();
    }
    else {
        var searchP = document.getElementById("searchP");
        if(!(searchP.offsetHeight == 0 && searchP.offsetWidth == 0)) {
            hide(searchP);
        }
    }    
}

function search(){
    var osnUslId = document.getElementById("osnuslSel").value;
    var uslId = document.getElementById("uslSel").value;
    var regId = document.getElementById("regSel").value;
    var regClId = document.getElementById("regClSel").value;
    var datepr1 = document.getElementById("datepr1").value;
    var datepr2 = document.getElementById("datepr2").value;
    var kat = document.getElementById("katSel").value;
    var kateg = "";
    if (kat == 0){
        kateg = "all";
    }
    else if (kat == 1){
        kateg = "children";
    }
    else if (kat == 2){
        kateg = "parents";
    }
    else if (kat == 3){
        kateg = "ped";
    }
    var url = "clientuslaction?action=search&osnusl=" + osnUslId + "&usl=" + uslId + "&reg=" + regId + 
            "&regcl=" + regClId + "&date1=" + datepr1 + "&date2=" + datepr2 + "&kat=" + kateg;
    requ(url, "GET");
}

function requ(url, method){ // формирование запроса
    var requ = null;   
    requ = initRequest();
    requ.open(method, url, true);
    requ.onreadystatechange = callback;
    requ.send(null);
}

function initRequest(){
    return new XMLHttpRequest();
}

function callback(){
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
    } 
    else {
        // услуги
        var usls = responseXML.getElementsByTagName("usls")[0];
        // клиенты
        var clients = responseXML.getElementsByTagName("clients")[0];
        
        if (usls != null){
            clearUsl();
            if (usls.childNodes.length > 0){
                for (loop = 0; loop < usls.childNodes.length; loop++){
                    var usl = usls.childNodes[loop];
                    var uslId = usl.getElementsByTagName("id")[0];
                    var uslName = usl.getElementsByTagName("name")[0];
                    appendUsl(uslId.childNodes[0].nodeValue, uslName.childNodes[0].nodeValue);
                }
            }
        }
        else if (clients != null){
            clearClients();
            appendKolC(0);
            if (clients.childNodes.length >0){
                for (loop = 0; loop < clients.childNodes.length; loop++){
                    var client = clients.childNodes[loop];
                    var clId = client.getElementsByTagName("id")[0];
                    var kat = client.getElementsByTagName("kat")[0];
                    var clNom = client.getElementsByTagName("nom")[0];
                    var clFam = client.getElementsByTagName("fam")[0];
                    var clName = client.getElementsByTagName("name")[0];
                    var clPatr = client.getElementsByTagName("patr")[0];
                    appendCl(clId.childNodes[0].nodeValue, kat.childNodes[0].nodeValue,
                        clNom.childNodes[0].nodeValue, clFam.childNodes[0].nodeValue, 
                        clName.childNodes[0].nodeValue, clPatr.childNodes[0].nodeValue);
                }
                appendKolC(clients.childNodes.length);
            }
        }
    }
}

function clearUsl(){
    var uslSel = document.getElementById("uslSel");
    if (uslSel != null){
        for (loop = uslSel.childNodes.length - 1; loop >= 0; loop--){
            uslSel.removeChild(uslSel.childNodes[loop]);
        }
        var opt = document.createElement("option");
        opt.value = "0";
        opt.appendChild(document.createTextNode("Все"));
        uslSel.appendChild(opt);
    }    
}

function appendUsl(id, name){
    var uslSel = document.getElementById("uslSel");
    if (uslSel != null){
        var opt = document.createElement("option");
        opt.value = id;
        opt.appendChild(document.createTextNode(name));
        uslSel.appendChild(opt);
    }
}

function clearClients(){
    var tab = document.getElementById("clientTable");
    for (loop = tab.childNodes.length -1; loop >= 0 ; loop--) {
        tab.removeChild(tab.childNodes[loop]);
    }
    var tr = document.createElement("thead");
    var tdId = document.createElement("td");
    tdId.appendChild(document.createTextNode(""));
    tr.appendChild(tdId);
    var tdKat = document.createElement("td");
    tdKat.appendChild(document.createTextNode("Категория"));
    tr.appendChild(tdKat);
    var tdNom = document.createElement("td");
    tdNom.appendChild(document.createTextNode("Номер"));
    tr.appendChild(tdNom);
    var tdFam = document.createElement("td");
    tdFam.appendChild(document.createTextNode("Фамилия"));
    tr.appendChild(tdFam);
    var tdName = document.createElement("td");
    tdName.appendChild(document.createTextNode("Имя"));
    tr.appendChild(tdName);
    var tdPatr = document.createElement("td");
    tdPatr.appendChild(document.createTextNode("Отчество"));
    tr.appendChild(tdPatr);
    var tbody = document.createElement("tbody");
    tbody.id = "bodyClient";
    tab.appendChild(tr);
    tab.appendChild(tbody);
}

function appendCl(clId, kat, clNom, clFam, clName, clPatr){
    var tab = document.getElementById("bodyClient");
    var tr = document.createElement("tr");
    var tdId = document.createElement("td");
    tdId.appendChild(document.createTextNode(clId));
    tr.appendChild(tdId);
    var tdKat = document.createElement("td");
    var katRu = "";
    if (kat == "children"){
        katRu = "дети";
    }
    if (kat == "parents"){
        katRu = "законные представители";
    }
    if (kat == "ped"){
        katRu = "педагоги";
    }
    tdKat.appendChild(document.createTextNode(katRu));
    tr.appendChild(tdKat);
    var tdNom = document.createElement("td");
    tdNom.appendChild(document.createTextNode(clNom));
    tr.appendChild(tdNom);
    var tdFam = document.createElement("td");
    tdFam.appendChild(document.createTextNode(clFam));
    tr.appendChild(tdFam);
    var tdName = document.createElement("td");
    tdName.appendChild(document.createTextNode(clName));
    tr.appendChild(tdName);
    var tdPatr = document.createElement("td");
    tdPatr.appendChild(document.createTextNode(clPatr));
    tr.appendChild(tdPatr);
    tr.onclick = selectClient;
    tab.appendChild(tr);
}

function appendKolC(n){
    var kol = document.getElementById("kol");
    if (kol != null){
        var chKol = kol.childNodes[0];
        if (chKol != null){
            kol.removeChild(chKol);
        }
        kol.appendChild(document.createTextNode("Количество: " + n));
    }
}

function selectClient(){
    var row = this;
    var id = row.childNodes[0];
    var idCl = id.childNodes[0].nodeValue;
    var katRu = row.childNodes[1].childNodes[0].nodeValue;
    var kat = null;
    if (katRu == "дети"){
        kat = "children"
    }
    else if (katRu == "законные представители"){
        kat = "parents"
    }
    else if (katRu == "педагоги"){
        kat = "ped"
    }
    
    var url = "client?kat=" + kat + "&id=" + idCl;
    window.open(url,'_blank');
}