/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    var sort = document.getElementById("sort").value;
    var url = "ipra2018spisok?action=searchipra&result=search&fam=&name=&patr=&reg=&npr=&dpr=&sort=" + sort;
    requ(url, "GET");
    document.getElementById("sortascdo").onclick = sortIpra;
    document.getElementById("sortdescdo").onclick = sortIpra;
    document.getElementById("sortascomsu").onclick = sortIpra;
    document.getElementById("sortdescomsu").onclick = sortIpra;
    document.getElementById("sortascpr").onclick = sortIpra;
    document.getElementById("sortdescpr").onclick = sortIpra;
    document.getElementById("sortascdo").style = "border : 1px solid";
}

function initArchive() {
    var sort = document.getElementById("sort").value;
    var url = "ipra2018spisok?action=searcharchive&result=search&fam=&name=&patr=&reg=&npr=&dpr=&sort=" + sort;
    requ(url, "GET");
    document.getElementById("sortascdo").onclick = sortIpraArchive;
    document.getElementById("sortdescdo").onclick = sortIpraArchive;
    document.getElementById("sortascomsu").onclick = sortIpraArchive;
    document.getElementById("sortdescomsu").onclick = sortIpraArchive;
    document.getElementById("sortascpr").onclick = sortIpraArchive;
    document.getElementById("sortdescpr").onclick = sortIpraArchive;
    document.getElementById("sortascdo").style = "border : 1px solid";
}

function sortIpra() {
    var inpSort = document.getElementById("sort");

    if (this.id == "sortascdo") {
        inpSort.value = "do1";
    } else if (this.id == "sortdescdo") {
        inpSort.value = "do2";
    }

    if (this.id == "sortascomsu") {
        inpSort.value = "omsu1";
    } else if (this.id == "sortdescomsu") {
        inpSort.value = "omsu2";
    }

    if (this.id == "sortascpr") {
        inpSort.value = "pr1";
    } else if (this.id == "sortdescpr") {
        inpSort.value = "pr2";
    }

    document.getElementById("sortascdo").style = "border : none";
    document.getElementById("sortdescdo").style = "border : none";
    document.getElementById("sortascomsu").style = "border : none";
    document.getElementById("sortdescomsu").style = "border : none";
    document.getElementById("sortascpr").style = "border : none";
    document.getElementById("sortdescpr").style = "border : none";
    this.style = "border : 1px solid";
    searchIpra();
}

function sortIpraArchive() {
    var inpSort = document.getElementById("sort");

    if (this.id == "sortascdo") {
        inpSort.value = "do1";
    } else if (this.id == "sortdescdo") {
        inpSort.value = "do2";
    }

    if (this.id == "sortascomsu") {
        inpSort.value = "omsu1";
    } else if (this.id == "sortdescomsu") {
        inpSort.value = "omsu2";
    }

    if (this.id == "sortascpr") {
        inpSort.value = "pr1";
    } else if (this.id == "sortdescpr") {
        inpSort.value = "pr2";
    }

    document.getElementById("sortascdo").style = "border : none";
    document.getElementById("sortdescdo").style = "border : none";
    document.getElementById("sortascomsu").style = "border : none";
    document.getElementById("sortdescomsu").style = "border : none";
    document.getElementById("sortascpr").style = "border : none";
    document.getElementById("sortdescpr").style = "border : none";
    this.style = "border : 1px solid";
    searchIpraArchive();
}

function searchIpra() {
    var fam = document.getElementById("searchFam").value;
    var name = document.getElementById("searchName").value;
    var patr = document.getElementById("searchPatr").value;
    var reg = document.getElementById("selReg").value;
    var npr = document.getElementById("searchNPr").value;
    var dpr = document.getElementById("searchDPr").value;
    var sort = document.getElementById("sort").value;
    var url = "ipra2018spisok?action=searchipra&result=search&fam=" + fam + "&name=" + name
            + "&patr=" + patr + "&reg=" + reg + "&sort=" + sort + "&npr=" + npr + "&dpr=" + dpr;
    requ(url, "GET");
    clearTable();
}

function searchIpraArchive() {
    var fam = document.getElementById("searchFam").value;
    var name = document.getElementById("searchName").value;
    var patr = document.getElementById("searchPatr").value;
    var reg = document.getElementById("selReg").value;
    var npr = document.getElementById("searchNPr").value;
    var dpr = document.getElementById("searchDPr").value;
    var sort = document.getElementById("sort").value;
    var url = "ipra2018spisok?action=searcharchive&result=search&fam=" + fam + "&name=" + name
            + "&patr=" + patr + "&reg=" + reg + "&sort=" + sort + "&npr=" + npr + "&dpr=" + dpr;
    requ(url, "GET");
    clearTable();
}

function clearSearch() {
    document.getElementById("searchFam").value = "";
    document.getElementById("searchName").value = "";
    document.getElementById("searchPatr").value = "";
    document.getElementById("selReg").value = "";
    document.getElementById("searchNPr").value = "";
    document.getElementById("searchDPr").value = "";
    var sort = document.getElementById("sort").value;
    var url = "ipra2018spisok?action=searchipra&result=search&fam=&name=&patr=&reg=&sort=" + sort + "&npr=&dpr=";
    requ(url, "GET");
    clearTable();
}

function clearSearchArchive() {
    document.getElementById("searchFam").value = "";
    document.getElementById("searchName").value = "";
    document.getElementById("searchPatr").value = "";
    document.getElementById("selReg").value = "";
    document.getElementById("searchNPr").value = "";
    document.getElementById("searchDPr").value = "";
    var sort = document.getElementById("sort").value;
    var url = "ipra2018spisok?action=searcharchive&result=search&fam=&name=&patr=&reg=&sort=" + sort + "&npr=&dpr=";
    requ(url, "GET");
    clearTable();
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
        var ipras = responseXML.getElementsByTagName("ipras")[0];
        if (ipras != null) {
            if (ipras.childNodes.length > 0) {
                for (loop = 0; loop < ipras.childNodes.length; loop++) {
                    var ipra = ipras.childNodes[loop];
                    var id = ipra.getElementsByTagName("id")[0];
                    var fam = ipra.getElementsByTagName("fam")[0];
                    var name = ipra.getElementsByTagName("name")[0];
                    var patr = ipra.getElementsByTagName("patr")[0];
                    var dr = ipra.getElementsByTagName("dr")[0];
                    var region = ipra.getElementsByTagName("region")[0];
                    var prikazn = ipra.getElementsByTagName("prikazn")[0];
                    var prikaz = ipra.getElementsByTagName("prikaz")[0];
                    var omsudate = ipra.getElementsByTagName("omsudate")[0];
                    var omsun = ipra.getElementsByTagName("omsun")[0];
                    var otchomsu = ipra.getElementsByTagName("otchomsu")[0];
                    var otchcenter = ipra.getElementsByTagName("otchcenter")[0];
                    var otchdo = ipra.getElementsByTagName("otchdo")[0];
                    //if (archive == null) {
                    appendIpra(id.childNodes[0].nodeValue, fam.childNodes[0].nodeValue, name.childNodes[0].nodeValue,
                            patr.childNodes[0].nodeValue, dr.childNodes[0].nodeValue, region.childNodes[0].nodeValue,
                            prikazn.childNodes[0].nodeValue, prikaz.childNodes[0].nodeValue, omsudate.childNodes[0].nodeValue,
                            omsun.childNodes[0].nodeValue, otchomsu.childNodes[0].nodeValue, otchcenter.childNodes[0].nodeValue,
                            otchdo.childNodes[0].nodeValue);
                    /*} else {
                     appendArchive(id.childNodes[0].nodeValue, fam.childNodes[0].nodeValue, name.childNodes[0].nodeValue,
                     patr.childNodes[0].nodeValue, dr.childNodes[0].nodeValue, region.childNodes[0].nodeValue,
                     otchdo.childNodes[0].nodeValue);
                     }*/
                }
            }
        }
    }
}

function appendIpra(id, fam, name, patr, dr, region, prikazn, prikaz, omsudate, omsun, otchomsu, otchcenter, otchdo) {
    var tbody = document.getElementById("tabbody");
    if (tbody != null) {
        var now = new Date();
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        now.setMilliseconds(0);
        var tr = document.createElement("tr");
        var tdId = document.createElement("td");
        tdId.appendChild(document.createTextNode(id));
        var tdFam = document.createElement("td");
        tdFam.appendChild(document.createTextNode(fam));
        var tdName = document.createElement("td");
        tdName.appendChild(document.createTextNode(name));
        var tdPatr = document.createElement("td");
        tdPatr.appendChild(document.createTextNode(patr));
        var tdDr = document.createElement("td");
        tdDr.appendChild(document.createTextNode(dr));
        var tdReg = document.createElement("td");
        tdReg.appendChild(document.createTextNode(region));
        var tdPrikazN = document.createElement("td");
        tdPrikazN.appendChild(document.createTextNode(prikazn));
        var tdPrikaz = document.createElement("td");
        tdPrikaz.appendChild(document.createTextNode(prikaz));
        var tdOmsudate = document.createElement("td");
        tdOmsudate.appendChild(document.createTextNode(omsudate));
        if (omsun == " ") {
            tdOmsudate.style = "color: #00cc00";
        }
        var omsuD = new Date(1 * omsudate.substr(6), 1 * omsudate.substr(3, 2) - 1, 1 * omsudate.substr(0, 2), 0, 0, 0, 0);
        if ((omsun == " ") && (omsuD.getTime() - now.getTime() <= 5 * 1000 * 60 * 60 * 24)) {
            tdOmsudate.style = "color: orange";
        }
        if ((omsun == " ") && (omsuD.getTime() - now.getTime() < 0)) {
            tdOmsudate.style = "color: red";
        }

        var otchomsuD = new Date(1 * otchomsu.substr(6), 1 * otchomsu.substr(3, 2) - 1, 1 * otchomsu.substr(0, 2), 0, 0, 0, 0);
        var tdOtchomsu = document.createElement("td");
        if (otchomsuD.getTime() - now.getTime() <= 2592000000) {
            tdOtchomsu.style = "color: red";
        }
        tdOtchomsu.appendChild(document.createTextNode(otchomsu));

        var otchcenterD = new Date(1 * otchcenter.substr(6), 1 * otchcenter.substr(3, 2) - 1, 1 * otchcenter.substr(0, 2), 0, 0, 0, 0);
        var tdOtchcenter = document.createElement("td");
        if (otchcenterD.getTime() - now.getTime() <= 2592000000) {
            tdOtchcenter.style = "color: red";
        }
        tdOtchcenter.appendChild(document.createTextNode(otchcenter));

        var otchdoD = new Date(1 * otchdo.substr(6), 1 * otchdo.substr(3, 2) - 1, 1 * otchdo.substr(0, 2), 0, 0, 0, 0);
        var tdOtchdo = document.createElement("td");
        if (otchdoD.getTime() - now.getTime() <= 2592000000) {
            tdOtchdo.style = "color: red";
        }
        tdOtchdo.appendChild(document.createTextNode(otchdo));
        tr.appendChild(tdId);
        tr.appendChild(tdFam);
        tr.appendChild(tdName);
        tr.appendChild(tdPatr);
        tr.appendChild(tdDr);
        tr.appendChild(tdReg);
        tr.appendChild(tdPrikazN);
        tr.appendChild(tdPrikaz);
        tr.appendChild(tdOmsudate);
        tr.appendChild(tdOtchomsu);
        tr.appendChild(tdOtchcenter);
        tr.appendChild(tdOtchdo);
        tr.onclick = openIpra;
        tbody.appendChild(tr);
    }
}

function clearTable() {
    var tbody = document.getElementById("tabbody");
    if (tbody != null) {
        tbody.innerHTML = "";
    }
}

function openIpra() {
    var id = this.childNodes[0].childNodes[0].nodeValue;
    var url = "ipra2018spisok?action=open&id=" + id;
    window.open(url, '_blank');
}

function printRed() {
    var url = "print?type=ipra18red";
    window.open(url, '_blank');
}

function printSvod() {
    var url = "print?type=ipra18svod";
    window.open(url, '_blank');
}

function openPrintNoInfo(){
    var url = "ipra2018spisok?action=opennoinfo";
    window.open(url, '_blank');
}

function printNoInfo(){
    var date1 = document.getElementById("date1").value;
    var date2 = document.getElementById("date2").value;
    var reg = document.getElementById("selReg").value;    
    var url = "print?type=ipra18noinfo&date1=" + date1 + "&date2=" + date2 + "&reg=" + reg;
    window.open(url, '_blank');
}
