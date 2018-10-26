/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

document.addEventListener("click", removeDop);

function removeDop(e){
    var div = document.getElementById("mdatedop");
    if (div != null){
        var mouseX = window.event.clientX;
        var mouseY = window.event.clientY;
        var top = div.getBoundingClientRect().top;
        var bottom = div.getBoundingClientRect().bottom;
        var left = div.getBoundingClientRect().left;
        var right = div.getBoundingClientRect().right;
        if ((mouseX < left) || (mouseX > right)||(mouseY < top)||(mouseY > bottom)){
            div.remove();
        }
    }
    
}

function test(){
    var files = document.getElementsByName("files");
    var fileName = "";
    for (var i = 0; i < files.length; i++) {
        if (files[i].type == "radio" && files[i].checked) {
            fileName = files[i].value;
        }
    }
    if (fileName == ""){
        alert("Выберите файл");
    }
    else{
        var url = "monitupload?filename=" + fileName + "&id=test";
        window.open(url);
    }
}

function upload(){
    var dateMonit = document.getElementById("dateMonit");
    var date;
    if (dateMonit != null){
        date = dateMonit.value;
    }
    if (date == ""){
        alert("Укажите дату мониторинга");
    }
    else if (date.length > 10){
        alert("Неверная дата");
    }
    else{
        var fileName = document.getElementById("filename");
        var file = "";
        if (fileName != null){
            file = fileName.value;
        }
        var url = "monitupload?filename=" + file + "&id=load&date=" + date;
        window.open(url);    
    }
}

function init(){
    var tr = document.getElementsByTagName("tr");
    for (loop = 1; loop < tr.length; loop++){
        tr[loop].onclick = selectMonit;
    }
}

function selectMonit(){
    var row = this;
    var id = row.childNodes[0];
    var idMon = id.childNodes[0].nodeValue;
    var url = "monitview?id=" + idMon;
    window.open(url,'_blank');
}

function result(e){
    if (document.getElementById("mdatedop") == null){
        var div = document.createElement("div");        
        var id = e.parentNode.childNodes[1].childNodes[0].nodeValue.trim();
        var fio = e.parentNode.childNodes[5].childNodes[0].nodeValue.trim();
        div.id = "mdatedop";
        div.style.position = "fixed";
        div.style.top = e.getBoundingClientRect().top + "px";
        div.style.left = e.getBoundingClientRect().left + "px";
        div.style.height = "500px";
        div.style.width = "700px";    
        div.style.background = "white";
        div.style.border = "solid 3px gray";
        
        var indiv = document.createElement("div");
        indiv.id = "in";
        indiv.style.overflow = "auto";
        var strong = document.createElement("strong");
        strong.appendChild(document.createTextNode(fio));
        indiv.appendChild(strong);
        indiv.appendChild(document.createElement("br"));
        div.appendChild(indiv);
        document.body.appendChild(div);
        var url = "monitviewaction?id=" + id;
        requ(url, "GET");
    }
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
        var mdata = responseXML.getElementsByTagName("mdata")[0];
        if (mdata != null){             
            if (mdata.childNodes.length > 0) {       
                var res = mdata.getElementsByTagName("result")[0];
                var form = mdata.getElementsByTagName("form")[0];
                var org = mdata.getElementsByTagName("org")[0];
                var stage = mdata.getElementsByTagName("stage")[0];
                var obr = mdata.getElementsByTagName("obr")[0];
                var obrvar = mdata.getElementsByTagName("obrvar")[0];
                var reks = mdata.getElementsByTagName("reks")[0];                
                var pmpkobr = mdata.getElementsByTagName("pmpkobr")[0];
                var pmpkobrvar = mdata.getElementsByTagName("pmpkobrvar")[0];
                var pmpkreks = mdata.getElementsByTagName("pmpkreks")[0];                
                appendMdata(res.childNodes[0].nodeValue, form.childNodes[0].nodeValue, org.childNodes[0].nodeValue, stage.childNodes[0].nodeValue,
                    obr.childNodes[0].nodeValue, obrvar.childNodes[0].nodeValue,
                    reks, pmpkobr.childNodes[0].nodeValue, pmpkobrvar.childNodes[0].nodeValue,
                    pmpkreks);                
            }
        }
    }    
}

function appendMdata(res, form, org, stage, obr, obrvar, reks, pmpkobr, pmpkobrvar, pmpkreks){
    var div = document.getElementById("in");
    if (div != null){
        var lbl = document.createElement("label");
        lbl.appendChild(document.createTextNode(res));
        div.appendChild(lbl);
        
        var tbl = document.createElement("table");
        var tr = document.createElement("tr");
        var td = document.createElement("td");
        
        
        var lbl1 = document.createElement("label");
        var b1 = document.createElement("b");
        b1.appendChild(document.createTextNode("Форма обучения:"));
        lbl1.appendChild(b1);
        var lbl11 = document.createElement("label");
        lbl1.appendChild(document.createElement("br"));
        lbl1.appendChild(document.createTextNode(form));
        td.appendChild(lbl1);
        td.appendChild(lbl11);
        
        td.appendChild(document.createElement("br"));
        
        var lbl2 = document.createElement("label");
        var b2 = document.createElement("b");
        b2.appendChild(document.createTextNode("Образовательное учреждение: "));        
        lbl2.appendChild(b2);
        lbl2.appendChild(document.createElement("br"));
        var lbl22 = document.createElement("label");
        lbl22.appendChild(document.createTextNode(org));        
        td.appendChild(lbl2);
        td.appendChild(lbl22);
        
        td.appendChild(document.createElement("br"));
        
        var lbl3 = document.createElement("label");
        var b3 = document.createElement("b");
        b3.appendChild(document.createTextNode("Класс/группа:"));
        lbl3.appendChild(b3);
        lbl3.appendChild(document.createElement("br"));
        var lbl33 = document.createElement("label");
        lbl33.appendChild(document.createTextNode(stage));
        td.appendChild(lbl3);
        td.appendChild(lbl33);
        
        td.appendChild(document.createElement("br"));
        
        var lbl4 = document.createElement("label");
        var b4 = document.createElement("b");
        b4.appendChild(document.createTextNode("Образовательная программа:"));
        lbl4.appendChild(b4);
        lbl4.appendChild(document.createElement("br"));
        var inp4 = document.createElement("input");
        inp4.type = "text";        
        inp4.value = obr;
        if (obrvar != " "){
            inp4.value += "(" + obrvar + ")";
        }
        lbl4.appendChild(inp4);
        td.appendChild(lbl4);
        
        td.appendChild(document.createElement("br"));
        
        var lbl5 = document.createElement("label");
        var b5 = document.createElement("b");
        b5.appendChild(document.createTextNode("Выполнены рекомендации:"));
        lbl5.appendChild(b5);
        lbl5.appendChild(document.createElement("br"));
        var lbl55 = document.createElement("label");
        for (loop = 0; loop < reks.childNodes.length; loop++){            
            lbl55.appendChild(document.createTextNode(reks.childNodes[loop].childNodes[0].nodeValue));
            lbl55.appendChild(document.createElement("br"));
        }
        
        td.appendChild(lbl5);
        td.appendChild(lbl55);
        tr.appendChild(td);
        
        var td2 = document.createElement("td");               
        var lbl6 = document.createElement("label");
        var b6 = document.createElement("b");
        b6.appendChild(document.createTextNode("Образовательная программа:"));
        lbl6.appendChild(b6);
        lbl6.appendChild(document.createElement("br"));
        var lbl66 = document.createElement("label");
        lbl66.appendChild(document.createTextNode(pmpkobr));
        if (pmpkobrvar != " "){
            lbl66.appendChild(document.createTextNode("(" + pmpkobrvar + ")"));
        }
        td2.appendChild(lbl6);
        td2.appendChild(lbl66);
        
        td2.appendChild(document.createElement("br"));
        
        var lbl7 = document.createElement("label");
        var b7 = document.createElement("b");
        b7.appendChild(document.createTextNode("Рекомендовано:"));
        lbl7.appendChild(b7);
        lbl7.appendChild(document.createElement("br"));
        var lbl02 = document.createElement("label");
        for (loop = 0; loop < pmpkreks.childNodes.length; loop++){            
            lbl02.appendChild(document.createTextNode(pmpkreks.childNodes[loop].childNodes[0].nodeValue));
            lbl02.appendChild(document.createElement("br"));
        }
        td2.appendChild(lbl7);
        td2.appendChild(lbl02);
        tr.appendChild(td2);
        
        tbl.appendChild(tr);
        div.appendChild(tbl);
    }
}

function compare(){
    var files = document.getElementsByName("files");
    var fileName = "";
    for (var i = 0; i < files.length; i++) {
        if (files[i].type == "radio" && files[i].checked) {
            fileName = files[i].value;
        }
    }
    if (fileName == ""){
        alert("Выберите файл");
    }
    else{
        var url = "monitupload?filename=" + fileName + "&id=ovzcompare";
        window.open(url);
    }
}

function ovzupload(){
    var files = document.getElementsByName("files");
    var fileName = "";
    for (var i = 0; i < files.length; i++) {
        if (files[i].type == "radio" && files[i].checked) {
            fileName = files[i].value;
        }
    }
    if (fileName == ""){
        alert("Выберите файл");
    }
    else{
        var url = "monitupload?filename=" + fileName + "&id=ovzupload";
        window.open(url);
    }
}

