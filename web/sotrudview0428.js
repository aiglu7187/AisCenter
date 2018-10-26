/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    var dolgn = document.getElementById("dolgn");
    if (dolgn != null) {
        dolgn.onchange = changeDolgn;
    }

    var active = document.getElementById("active");
    if (active != null) {
        active.onchange = changeActive;
    }
}

function changeDolgn() {
    var dolgnId = document.getElementById("dolgnId");
    if (dolgnId != null) {
        dolgnId.value = this.value;
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
    var fioLimit = 100;
    var result = true;
    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        inp[loop].classList.remove("wrong");
    }
    var flag = false;
    for (loop = 0; loop < inp.length; loop++) {
        var id = inp[loop].id;
        var val = inp[loop].value;
        if ((id == "fam") || (id == "nam") || (id == "patr")) {
            if ((val == "") || (val.length > fioLimit)) {
                inp[loop].className = "wrong";
                result = false;
            }
        } else if (id.substring(0, 5) == "dolgn") {
            if (inp[loop].checked) {
                flag = true;
            }
        }
    }
    if (!flag) {
        var divDolgn = document.getElementById("divDolgn");
        divDolgn.className = "wrong";
        result = false;
    }

    if (!result) {
        alert("Проверьте данные в полях, отмеченных КРАСНЫМ ЦВЕТОМ (проверяется заполнение, корректность и количество символов)");
        var wrong = document.getElementsByClassName("wrong");
        wrong[0].focus();
        var btn = document.getElementById("saveBtn");
        btn.disabled = 0;
    } else if (result) {
        document.getElementById("formSotrud").submit();
    }
}

