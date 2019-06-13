// creation functions

function createIllusion (xml,svg,factory) {
    //var servletFactory = new ServletFactory();
    //var xhr = servletFactory.llusionServlet(factory,'POST');
    var loader = document.getElementById('myLoader');
    loader.style.display = "block";
    var modal = document.getElementById('myModalGrey');
    modal.style.display = "block";
    var ans = [xml, svg];
    console.log(JSON.stringify(ans));
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/run", true);
    xhr.send(JSON.stringify(ans));
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            handleCanCreate(loader,modal);
        } else if (xhr.readyState == 4 && xhr.status == 10) {
            handleCanNotCreate(loader, modal);
            window.alert('Can not create an appropriate 3D object.');
        } else if (xhr.readyState == 4 && xhr.status == 50) {
            handleCanNotCreate(loader, modal);
            window.alert('You used too many shapes.');
        }
    };
}


function handleShowCreation(loader,modal){
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

function handleCanCreate(loader,modal){
    var r = confirm("The object can be produced.\n" +
        "Would you like to create it?");
    if (r == true) {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/run", true);
        xhr.send(null);
        xhr.onreadystatechange = function(e){
            if (xhr.readyState == 4 && xhr.status == 200) {
                handleShowCreation(loader,modal);
            }
            else if (xhr.readyState == 4 && xhr.status == 20) {
                handleShowCreation(loader,modal);
                window.alert('The connection with the server may be lost. \nThe object is not saved in the system.');
            }
        }
    } else {
        handleCanNotCreate(loader,modal);
    }
}

function handleCanNotCreate(loader,modal){
    loader.style.display = "none";
    modal.style.display = "none";
}




// db view functions

function getMyObjectsOpen(){
    localStorage.removeItem("update");
    localStorage.removeItem("theXml");
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/myObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var array= JSON.parse(xhr.responseText);
            console.log(array.length);
            if (array.length > 0){
                sessionStorage.setItem("array", JSON.stringify(array));
                sessionStorage.setItem("reloading", "true");
                window.location.reload(true );
            }
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            window.alert('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }
    };
}

function getAllObjectsOpen(){
    localStorage.removeItem("update");
    localStorage.removeItem("theXml");
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/allObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var array= JSON.parse(xhr.responseText);
            console.log(array.length);
            if (array.length > 0){
                sessionStorage.setItem("array", JSON.stringify(array));
                sessionStorage.setItem("reloading", "true");
                window.location.reload(true );
            }
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            window.alert('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }
    };
}

function getAllObjects(editor,mxUtils,factory)
{
    localStorage.removeItem("update");
    localStorage.removeItem("theXml");
    var modal = document.getElementById('myModalGrey');
    modal.style.display = "block";

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/allObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var array= xhr.responseText;
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
                    modal.style.display = "none";
                }
            }, 1000);
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            window.alert('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
            modal.style.display = "none";
        }
    };
}


function getNextObjects(factory)
{
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/NextObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var array= JSON.parse(xhr.responseText);
            console.log(array.length);
            if (array.length > 0){
                sessionStorage.setItem("array", JSON.stringify(array));
                sessionStorage.setItem("reloading", "true");
                window.location.reload(true );
            }
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            window.alert('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }
    };
}

function getPrevObjects(factory)
{
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/PrevObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var array= JSON.parse(xhr.responseText);
            console.log(array.length);
            if (array.length > 0){
                sessionStorage.setItem("array", JSON.stringify(array));
                sessionStorage.setItem("reloading", "true");
                window.location.reload(true);
            }
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            window.alert('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }
    };
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
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/LoadObject", true);
    xhr.send(index);

    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            //var xml = "<mxGraphModel grid=\"1\" gridSize=\"10\" guides=\"1\" tooltips=\"1\" connect=\"1\" arrows=\"0\" fold=\"1\" page=\"1\" pageScale=\"1\" pageWidth=\"827\" pageHeight=\"1169\" background=\"#ffffff\"><root><mxCell id=\"0\"/><mxCell id=\"1\" parent=\"0\"/><mxCell id=\"2\" value=\"\" style=\"rounded=0;endArrow=none;html=1;\" edge=\"1\" parent=\"1\"><mxGeometry width=\"50\" height=\"50\" relative=\"1\" as=\"geometry\"><mxPoint y=\"240\" as=\"sourcePoint\"/><mxPoint x=\"825\" y=\"240\" as=\"targetPoint\"/></mxGeometry></mxCell></root></mxGraphModel>";
            var xml= xhr.responseText;
            localStorage.setItem("update", "true");
            localStorage.setItem("theXml", xml);

            window.close();
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            window.alert('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }
    };
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


function getAllSimilarObjects(xml,svg,editor,mxUtils,factory)
{
    var modal = document.getElementById('myModalGrey');
    modal.style.display = "block";

    var ans = [xml, svg];

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/allSimilar", true);
    xhr.send(JSON.stringify(ans));

    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var array= xhr.responseText;
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
                }
            }, 1000);
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            window.alert('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
            modal.style.display = "none";
        }
    };
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



function handleSignIn(){
    var win = window.open('signIn.html',"_blank",
        "toolbar=yes,scrollbars=yes,resizable=yes,top=50%,left=50%,width=200,height=150");
}

