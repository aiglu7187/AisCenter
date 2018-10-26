/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    var sotr = document.getElementById("sotr");
    if (sotr != null) {
        sotr.onchange = changeSotr;
    }

    var roles = document.getElementById("roles");
    if (roles != null) {
        roles.onchange = changeRole;
    }

    var active = document.getElementById("active");
    if (active != null) {
        active.onchange = changeActive;
    }

    var e_onchange = document.createEvent("Event");
    e_onchange.initEvent("change", false, true);
    if (sotr != null) {
        sotr.dispatchEvent(e_onchange);
    }
    if (roles != null) {
        roles.dispatchEvent(e_onchange);
    }


}

function changeSotr() {
    var sotrudId = document.getElementById("sotrudId");
    if (sotrudId != null) {
        sotrudId.value = this.value;
    }
}

function changeRole() {
    var roleId = document.getElementById("roleId");
    if (roleId != null) {
        roleId.value = this.value;
    }
}

function changeActive() {
    var act = document.getElementById("act");
    if (act != null) {
        act.value = this.value;
    }
}

function validate() {
    var btn = document.getElementById("saveBtn");
    btn.disabled = "disabled";
    var nameLimit = 20;
    var passLimit = 50;
    var result = true;
    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        inp[loop].classList.remove("wrong");
    }
    for (loop = 0; loop < inp.length; loop++) {
        var id = inp[loop].id;
        var val = inp[loop].value;
        if (id == "nam") {
            if ((val == "")||(val.length > nameLimit)) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id == "pass") {
            if ((val == "")||(val.length > passLimit)) {
                inp[loop].className = "wrong";
                result = false;
            }
        }
    }

    if (!result) {
        alert("Проверьте данные в полях, отмеченных КРАСНЫМ ЦВЕТОМ (проверяется заполнение, корректность и количество символов)");
        var wrong = document.getElementsByClassName("wrong");
        wrong[0].focus();
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
    } else if (result) {
        document.getElementById("formUser").submit();
    }
}

