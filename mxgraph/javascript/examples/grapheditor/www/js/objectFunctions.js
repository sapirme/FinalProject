// creation functions

function createIllusion (xml,svg) {
    var loader = document.getElementById('myLoader');
    loader.style.display = "block";
    var modal = document.getElementById('myModalGrey');
    modal.style.display = "block";
    var ans = [xml, svg];
    console.log(JSON.stringify(ans));
    sendPostCreateIllusion(JSON.stringify(ans));
}


function handleShowCreation(message){
    var loader = document.getElementById('myLoader');
    var modal = document.getElementById('myModalGrey');
    if (message!=null)
        window.alert(message);
    loader.style.display = "none";
    var win = window.open('3DView.html',"_blank",
        "toolbar=yes,scrollbars=yes,resizable=yes,top=20%,left=20%,width=500,height=550");
    var timer = setInterval(function() {
        if(win.closed) {
            clearInterval(timer);
            modal.style.display = "none";
        }
    }, 1000);
}

function handleCanCreate(){
    var ans = confirm("The object can be produced.\n" +
        "Would you like to create it?");
    if (ans == true) {
        sendGetCreateIllusion();
    } else {
        alertAndNone(null);
    }
}

function alertAndNone(message){
    var loader = document.getElementById('myLoader');
    var modal = document.getElementById('myModalGrey');
    loader.style.display = "none";
    modal.style.display = "none";
    if (message!=null)
        window.alert(message);
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// db view functions

function getMyObjectsOpen(){
    var loader = document.getElementById('myLoader');
    var modal = document.getElementById('myModalGrey');
    loader.style.display = "block";
    modal.style.display = "block";

    localStorage.removeItem("update");
    localStorage.removeItem("theXml");
    sendGetMyObjects();
}

function showObjectArray(array) {
    var loader = document.getElementById('myLoader');
    var modal = document.getElementById('myModalGrey');
    if (array.length > 0){
        sessionStorage.setItem("array", JSON.stringify(array));
        sessionStorage.setItem("reloading", "true");
        window.location.reload(true );
    }
    loader.style.display = "none";
    modal.style.display = "none";
}


function getAllObjectsOpen(){
    var loader = document.getElementById('myLoader');
    var modal = document.getElementById('myModalGrey');
    loader.style.display = "block";
    modal.style.display = "block";
    localStorage.removeItem("update");
    localStorage.removeItem("theXml");
    sendGetAllObjectsOpen();
}

function openAllObjectWin(array,editor,mxUtils) {
    var loader = document.getElementById('myLoader');
    var modal = document.getElementById('myModalGrey');
    sessionStorage.setItem("array", array);
    var win = window.open('DBView.html',"_blank",
        'toolbar=yes,scrollbars=yes,resizable=yes,top=20%,left=20%,fullscreen="yes",'+'height=' + screen.availHeight + ',width=' + screen.availWidth );
    var timer = setInterval(function() {
        if(win.closed) {
            clearInterval(timer);

            var update = localStorage.getItem("update");
            console.log(update);
            if (update){
                var theXml = localStorage.getItem("theXml");

                var doc = mxUtils.parseXml(theXml);
                editor.graph.model.beginUpdate();
                editor.setGraphXml(doc.documentElement);
                editor.graph.model.endUpdate();
            }
            localStorage.removeItem("update");
            localStorage.removeItem("theXml");
            loader.style.display = "none";
            modal.style.display = "none";
        }
    }, 1000);
}

function getAllObjects(editor,mxUtils)
{
    localStorage.removeItem("update");
    localStorage.removeItem("theXml");
    var loader = document.getElementById('myLoader');
    var modal = document.getElementById('myModalGrey');
    loader.style.display = "block";
    modal.style.display = "block";
    sendGetAllObjects(editor,mxUtils);
}


function getNextObjects()
{
    var loader = document.getElementById('myLoader');
    loader.style.display = "block";
    var modal = document.getElementById('myModalGrey');
    modal.style.display = "block";

    sendGetNextObjects();

}

function getPrevObjects()
{
    var loader = document.getElementById('myLoader');
    loader.style.display = "block";
    var modal = document.getElementById('myModalGrey');
    modal.style.display = "block";
    sendGetPrevObjects();
}


function clearTable() {
    for (var i=0; i<8; i++){
        var theObj = document.getElementById("obj"+i);
        theObj.innerHTML = '';
        var loadBtn = document.getElementById("load"+i);
        loadBtn.innerHTML = '';
        var downloadBtn = document.getElementById("download"+i);
        downloadBtn.innerHTML = '';
    }
}

function loadFunc(index){
    sendGetLoadFunc(index);
}

function alertOnly(message){
    window.alert(message);
}

function updateScreenXml(xml) {
    localStorage.setItem("update", "true");
    localStorage.setItem("theXml", xml);

    window.close();
}

function updateTable(array) {
    var i=0;
    while (i<array.length){
        var obkText ='<canvas class="3dviewer" sourcefiles="../../../../../files/dbObject'+array[i]+'.stl" height="250px"></canvas>';

        var loadText ='<button class="buttonStyle2" type="button" onclick="loadFunc('+array[i]+')">Load</button>';

        var downloadText ='<a href="../../../../../files/dbObject'+array[i]+'.stl" download="myObject'+array[i]+'.stl">\n' +
            '                <button class="buttonStyle2" type="button" >Download</button>\n' +
            '            </a>';
        var theObj = window.document.getElementById("obj"+i);
        theObj.innerHTML = obkText;
        var loadBtn = window.document.getElementById("load"+i);
        loadBtn.innerHTML = loadText;
        var downloadBtn = window.document.getElementById("download"+i);
        downloadBtn.innerHTML = downloadText;
        i++;
    }
}




//similar objects

function openAllSimilar(array,editor,mxUtils){
    var loader = document.getElementById('myLoader');
    var modal = document.getElementById('myModalGrey');
    sessionStorage.setItem("array", array);
    var win = window.open('DBView.html',"_blank",
        'toolbar=yes,scrollbars=yes,resizable=yes,top=20%,left=20%,fullscreen="yes",'+'height=' + screen.availHeight + ',width=' + screen.availWidth );
    var timer = setInterval(function() {
        if(win.closed) {
            clearInterval(timer);

            var update = localStorage.getItem("update");

            if (update){
                localStorage.setItem("update","false");
                var theXml = localStorage.getItem("theXml");

                var doc = mxUtils.parseXml(theXml);
                editor.graph.model.beginUpdate();
                editor.setGraphXml(doc.documentElement);
                editor.graph.model.endUpdate();
            }
            modal.style.display = "none";
            loader.style.display = "none";
        }
    }, 1000);
}


function getAllSimilarObjects(xml,svg,editor,mxUtils)
{
    var loader = document.getElementById('myLoader');
    var modal = document.getElementById('myModalGrey');
    loader.style.display = "block";
    modal.style.display = "block";

    var ans = [xml, svg];
    SendGetAllSimilarObjects(JSON.stringify(ans),editor,mxUtils);
}





/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function myOnLoad() {
    console.log("onLoad");
    window.moveTo(0, 0);
    console.log("onLoad");
    var myArray= JSON.parse(sessionStorage.getItem("array"));
    var reloading = sessionStorage.getItem("reloading");
    if (reloading) {
        console.log("reloading");
        clearTable();
        updateTable(myArray);

    }
    else {
        updateTable(myArray);
    }
}



