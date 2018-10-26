/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function validotchet() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }

    var result = true;
    if (d1 == "") {
        alert("Поставьте дату начала периода");
        result = false;
    }
    ;
    if (result != false) {
        if (d2 == "") {
            alert("Поставьте дату конца периода");
            result = false;
        }
    }
    ;
    if (result != false) {
        if (d1.length > 10) {
            alert("Проверьте дату начала периода");
            result = false;
        }
    }
    ;
    if (result != false) {
        if (d2.length > 10) {
            alert("Проверьте дату конца периода");
            result = false;
        }
    }
    ;
    return result;
}

function printgz() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }
    if (validotchet()) {
        var url = "print?type=gz&date1=" + d1 + "&date2=" + d2;
        window.open(url, '_blank');
    }
}

function printrpmpk() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }

    var regId = document.getElementById("regId");
    var reg;
    if (regId != null) {
        reg = regId.value;
    }
    if (validotchet()) {
        var url = "print?type=rpmpk&date1=" + d1 + "&date2=" + d2 + "&reg=" + reg;
        window.open(url, '_blank');
    }
}

function printstatpmpk() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }
    if (validotchet()) {
        var url = "print?type=statpmpk&date1=" + d1 + "&date2=" + d2;
        window.open(url, '_blank');
    }
}

function printstatus() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }
    if (validotchet()) {
        var url = "print?type=status&date1=" + d1 + "&date2=" + d2;
        window.open(url, '_blank');
    }
}

function printproblem() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }
    if (validotchet()) {
        var url = "print?type=problem&date1=" + d1 + "&date2=" + d2;
        window.open(url, '_blank');
    }
}

function printage() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }
    var agen = document.getElementById("agen").value;
    if (agen == "") {
        agen = 0;
    }
    var agek = document.getElementById("agek").value;
    if (agek == "") {
        agek = 100;
    }

    if (validotchet()) {
        var url = "print?type=age&date1=" + d1 + "&date2=" + d2 + "&agen=" + agen + "&agek=" + agek;
        window.open(url, '_blank');
    }
}

function regCh() {
    var reg = document.getElementById("selReg");
    var regId;
    if (reg != null) {
        regId = reg.value;
    }
    var hid = document.getElementById("regId");
    if (hid != null) {
        hid.value = regId;
    }

}

function printpmpkstatus() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }

    var regId = document.getElementById("regId");
    var reg;
    if (regId != null) {
        reg = regId.value;
    }
    if (validotchet()) {
        var url = "print?type=pmpkstatus&date1=" + d1 + "&date2=" + d2 + "&reg=" + reg;
        window.open(url, '_blank');
    }
}

function printpmpkipr() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }

    var regId = document.getElementById("regId");
    var reg;
    if (regId != null) {
        reg = regId.value;
    }
    if (validotchet()) {
        var url = "print?type=pmpkipr&date1=" + d1 + "&date2=" + d2 + "&reg=" + reg;
        window.open(url, '_blank');
    }
}

function printpmpkgia() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }

    var regId = document.getElementById("regId");
    var reg;
    if (regId != null) {
        reg = regId.value;
    }
    if (validotchet()) {
        var url = "print?type=pmpkgia&date1=" + d1 + "&date2=" + d2 + "&reg=" + reg;
        window.open(url, '_blank');
    }
}

function printpmpkter() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }

    var regId = document.getElementById("regId");
    var reg;
    if (regId != null) {
        reg = regId.value;
    }
    if (validotchet()) {
        var url = "print?type=pmpkter&date1=" + d1 + "&date2=" + d2 + "&reg=" + reg;
        window.open(url, '_blank');
    }
}

function printpmpkrek() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }

    var regId = document.getElementById("regId");
    var reg;
    if (regId != null) {
        reg = regId.value;
    }
    if (validotchet()) {
        var url = "print?type=pmpkrek&date1=" + d1 + "&date2=" + d2 + "&reg=" + reg;
        window.open(url, '_blank');
    }
}

function printstatpmpkstatus() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }
    if (validotchet()) {
        var url = "print?type=statpmpkstatus&date1=" + d1 + "&date2=" + d2;
        window.open(url, '_blank');
    }
}

function printstatpmpkrek() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }
    if (validotchet()) {
        var url = "print?type=statpmpkrek&date1=" + d1 + "&date2=" + d2;
        window.open(url, '_blank');
    }
}

function printstatpmpkpar() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }
    if (validotchet()) {
        var url = "print?type=statpmpkpar&date1=" + d1 + "&date2=" + d2;
        window.open(url, '_blank');
    }
}

function printrpmpkmonit() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }

    var regId = document.getElementById("regId");
    var reg;
    if (regId != null) {
        reg = regId.value;
    }
    if (validotchet()) {
        var url = "print?type=monitpmpk&date1=" + d1 + "&date2=" + d2 + "&reg=" + reg;
        window.open(url, '_blank');
    }
}

function printovzfgos() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }
    if (validotchet()) {
        var url = "print?type=ovzfgos&date1=" + d1 + "&date2=" + d2;
        window.open(url, '_blank');
    }
}

function printovzarch() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }

    var regId = document.getElementById("regId");
    var reg;
    if (regId != null) {
        reg = regId.value;
    }
    var date3 = document.getElementById("date3");
    var d3 = "";
    if (date3 != null) {
        d3 = date3.value;
    }

    var date4 = document.getElementById("date4");
    var d4 = "";
    if (date4 != null) {
        d4 = date4.value;
    }

    var flag = false;
    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        if (inp[loop].type == "checkbox") {
            if (inp[loop].checked) {
                flag = true;
            }
        }
    }
    if (!flag) {
        alert("Должен быть отмечен хотя бы один тип образовательной программы");
    } else {
        if (validotchet()) {
            var url = "print?type=ovzarch&date1=" + d1 + "&date2=" + d2 + "&date3=" + d3 + "&date4=" + d4 + "&reg=" + reg + "&obrtype=";
            for (loop = 0; loop < inp.length; loop++) {
                if (inp[loop].type == "checkbox") {
                    if (inp[loop].checked) {
                        url += inp[loop].value + ";";
                    }
                }
            }
            window.open(url, '_blank');
        }
    }
}

function printreestrusl() {
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }
    var selUsl = document.getElementById("selUsl");
    var usl = "";
    if (selUsl != null){
        usl = selUsl.value;
    }
    if (validotchet()) {
        var url = "print?type=reestrusl&date1=" + d1 + "&date2=" + d2 + "&usl=" + usl;
        window.open(url, '_blank');
    }
}

function printrpmpkfull(){
    var date1 = document.getElementById("date1");
    var d1 = "";
    if (date1 != null) {
        d1 = date1.value;
    }
    var date2 = document.getElementById("date2");
    var d2 = "";
    if (date2 != null) {
        d2 = date2.value;
    }

    var regId = document.getElementById("regId");
    var reg;
    if (regId != null) {
        reg = regId.value;
    }
    if (validotchet()) {
        var url = "print?type=rpmpkfull&date1=" + d1 + "&date2=" + d2 + "&reg=" + reg;
        window.open(url, '_blank');
    }
}
