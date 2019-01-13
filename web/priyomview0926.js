/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function initPriyom() {
    Element.prototype.remove = function () {
        return this.parentNode.removeChild(this);
    }

    var a = document.getElementsByTagName("img");
    for (loop = 0; loop < a.length; loop++) {
        if (a[loop].id.substring(0, 10) == "delProblem") {
            a[loop].onclick = delProblem;
        } else if (a[loop].id.substring(0, 9) == "plusProbl") {
            a[loop].onclick = addProblem;
        } else if (a[loop].id.substring(0, 7) == "editSub") {
            a[loop].onclick = searchClient;
        } else if (a[loop].id.substring(0, 7) == "plusSub") {
            a[loop].onclick = addSubj;
        } else if (a[loop].id.substring(0, 6) == "delSub") {
            a[loop].onclick = delSub;
        } else if (a[loop].id.substring(0, 6) == "plusCl") {
            a[loop].onclick = addClient;
        } else if (a[loop].id.substring(0, 5) == "delCl") {
            a[loop].onclick = delClient;
        } else if (a[loop].id.substring(0, 8) == "plusSotr") {
            a[loop].onclick = addSotr;
        } else if (a[loop].id.substring(0, 7) == "delSotr") {
            a[loop].onclick = delSotr;
        } else if (a[loop].id.substring(0, 6) == "editCl") {
            a[loop].onclick = searchClient;
        } else if ((a[loop].id.substring(0, 11) == "edit1Parent")||(a[loop].id.substring(0, 11) == "edit2Parent")) {
            a[loop].onclick = searchPmpkParent;
        } else if ((a[loop].id.substring(0, 10) == "del1Parent")||(a[loop].id.substring(0, 10) == "del2Parent")) {
            a[loop].onclick = deleteParent;
        }

    }

    var select = document.getElementsByTagName("select");
    for (loop = 0; loop < select.length; loop++) {
        if (select[loop].id.substring(0, 8) == "selClOrg") {
            select[loop].onchange = orgChange;
        } else if (select[loop].id.substring(0, 9) == "selSubOrg") {
            select[loop].onchange = orgChange;
        } else if (select[loop].id.substring(0, 9) == "selClPedD") {
            select[loop].onchange = peddolgChange;
        } else if (select[loop].id.substring(0, 10) == "selSubPedD") {
            select[loop].onchange = peddolgChange;
        } else if (select[loop].id.substring(0, 8) == "selRegCl") {
            select[loop].onchange = changeRegCl;
        } else if (select[loop].id.substring(0, 9) == "selRegSub") {
            select[loop].onchange = changeRegCl;
        } else if (select[loop].id.substring(0, 8) == "selMonit") {
            select[loop].onchange = changeMonit;
        } else if (select[loop].id.substring(0, 9) == "selPrtype") {
            select[loop].onchange = changeProblemType;
        } else if (select[loop].id.substring(0, 5) == "selPr") {
            select[loop].onchange = changeProblem;
        } else if (select[loop].id.substring(0, 8) == "selDolgn") {
            select[loop].onchange = dolgnSelect;
        } else if (select[loop].id.substring(0, 7) == "selSotr") {
            select[loop].onchange = sotrSelect;
        } else if (select[loop].id.substring(0, 5) == "selOu") {
            select[loop].onchange = changeOU;
        } else if (select[loop].id.substring(0, 9) == "selOptype") {
            select[loop].onchange = changeOpType;
        } else if (select[loop].id.substring(0, 5) == "selOp") {
            select[loop].onchange = changeOp;
        } else if (select[loop].id.substring(0, 6) == "selVar") {
            select[loop].onchange = changeVar;
        } else if (select[loop].id.substring(0, 8) == "selTpmpk") {
            select[loop].onchange = changeTpmpk;
        } else if (select[loop].id.substring(0, 6) == "selGia") {
            select[loop].onchange = changeGia;
        } else if (select[loop].id.substring(0, 7) == "selSogl") {
            select[loop].onchange = changeSogl;
        } else if (select[loop].id.substring(0, 6) == "selReg") {
            select[loop].onchange = regSelect;
        } else if (select[loop].id.substring(0, 7) == "selSrok") {
            select[loop].onchange = clickSrokDate;
        }  else if ((select[loop].id.substring(0, 13) == "parentTypeSel") || 
                (select[loop].id.substring(0, 21) == "parentTypeSel1PmpkPar") ||
                (select[loop].id.substring(0, 21) == "parentTypeSel2PmpkPar"))  {
            select[loop].onchange = changeParentType;
        }
        
    }

    var inp = document.getElementsByTagName("input");
    for (loop = 0; loop < inp.length; loop++) {
        if (inp[loop].id.substring(0, 7) == "statosn") {
            inp[loop].onchange = checkStatOsn;
            var k = inp[loop].id.indexOf("_");
            var n = inp[loop].id.substring(k + 1);
            var check = document.getElementById("check" + n);
            if (inp[loop].checked) {
                if (check.value != "") {
                    var i = parseInt(check.value) + 1;
                    check.value = String(i);
                } else if (check.value == "") {
                    check.value = "1";
                }
            }
        } else if (inp[loop].id.substring(0, 10) == "subStatosn") {
            inp[loop].onchange = checkStatOsn;
            var k = inp[loop].id.indexOf("_");
            var n = inp[loop].id.substring(k + 1);
            var check = document.getElementById("checkSub" + n);
            if (inp[loop].checked) {
                if (check.value != "") {
                    var i = parseInt(check.value) + 1;
                    check.value = String(i);
                } else if (check.value == "") {
                    check.value = "1";
                }
            }
        } else if (inp[loop].id.substring(0, 7) == "statdop") {
            inp[loop].onchange = checkStatDop;
        } else if (inp[loop].id.substring(0, 10) == "subStatdop") {
            inp[loop].onchange = checkStatDop;
        } else if (inp[loop].id.substring(0, 5) == "monit") {
            var n = inp[loop].id.substring(5);
            if (n != "") {
                var udovl = document.getElementById("selMonit" + n);
                inp[loop].value = udovl.value;
            }
        } else if (inp[loop].id.substring(0, 4) == "prId") {
            var n = inp[loop].id.substring(4);
            if (n != "") {
                var pr = document.getElementById("selPr" + n);
                inp[loop].value = pr.value;
            }
        }
    }
    for (loop = 0; loop < inp.length; loop++) {
        if (inp[loop].id.substring(0, 5) == "check") {
            var n = inp[loop].id.substring(5);
            var check = document.getElementById("check" + n);
            if (check.value == "") {
                var div = document.getElementById("divStatdop" + n);
                if (div != null) {
                    div.remove();
                }
                var div2 = document.getElementById("divStatpod" + n);
                if (div2 != null) {
                    div2.remove();
                }
                var div3 = document.getElementById("divStatsoc" + n);
                if (div3 != null) {
                    div3.remove();
                }
            }
        }
    }
    for (loop = 0; loop < inp.length; loop++) {
        if (inp[loop].id.substring(0, 8) == "checkSub") {
            var n = inp[loop].id.substring(8);
            var check = document.getElementById("checkSub" + n);
            if (check.value == "") {
                var div = document.getElementById("divSubstatdop" + n);
                if (div != null) {
                    div.remove();
                }
                var div2 = document.getElementById("divSubstatpod" + n);
                if (div2 != null) {
                    div2.remove();
                }
                var div3 = document.getElementById("divStatsoc" + n);
                if (div3 != null) {
                    div3.remove();
                }
            }
        }
    }

    var inpstats = document.getElementsByClassName("norma");
    if (inpstats != null) {
        for (loop = 0; loop < inpstats.length; loop++) {
            if (inpstats[loop].checked) {
                var divstatosn = inpstats[loop].parentNode.parentNode;
                if (divstatosn != null) {
                    var checks = divstatosn.getElementsByTagName("input");
                    if (checks != null) {
                        for (loop1 = 0; loop1 < checks.length; loop1++) {
                            if (checks[loop1] != inpstats[loop]) {
                                checks[loop1].checked = false;
                                checks[loop1].onclick = doNothing;
                                checks[loop1].parentNode.style = "color: grey";
                            }
                        }
                    }
                }
                var div = inpstats[loop].parentNode.parentNode.parentNode;
                if (div != null) {
                    var divs = div.getElementsByTagName("div");
                    if (divs != null) {
                        for (loop2 = 0; loop2 < divs.length; loop2++) {
                            if ((divs[loop2].id.substring(0,13) == "divSubstatdop") || (divs[loop2].id.substring(0,13) == "divSubstatpod")
                                    || (divs[loop2].id.substring(0,10) == "divStatdop") || (divs[loop2].id.substring(0,10)== "divStatpod")) {
                                divs[loop2].remove();
                                loop2--;
                            }
                        }
                    }
                }

            }

        }
    }
    var doNothingChecks = document.getElementsByClassName("doNothing");
    for(loop = 0; loop < doNothingChecks.length; loop++){
        doNothingChecks[loop].onclick = doNothing;
    }
}

function delPriyom() {
    if (confirm("Вы уверены, что хотите удалить этот прием?")) {
        var idPriyom = document.getElementById("priyomId").value;
        var url = "deletepriyom?id=" + idPriyom;
        document.location.href = url;
    } else {
    }
}

function copyPriyom() {
    var dialog = document.getElementById("copyPriyomDialog");
    if (dialog != null) {
        dialog.showModal();
    }
}

function okCopy() {
    var sel = document.getElementById("selUsl");
    var uslId;
    if (sel != null) {
        uslId = sel.value;
    }
    var priyomId = document.getElementById("priyomId");
    var url = "copypriyom?priyomid=" + priyomId.value + "&uslid=" + uslId;
    var copyPriyomDialog = document.getElementById("copyPriyomDialog");
    copyPriyomDialog.close();
    window.open(url, "_blank");
    
}

