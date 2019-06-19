function sendPostCreateIllusion(message) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/run", true);
    xhr.send(message);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            handleCanCreate();
        } else if (xhr.readyState == 4 && xhr.status == 10) {
            alertAndNone('Can not create an appropriate 3D object.');
        } else if (xhr.readyState == 4 && xhr.status == 50) {
            alertAndNone('You used too many shapes.');
        }
    };
}

function sendGetCreateIllusion() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/run", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e){
        if (xhr.readyState == 4 && xhr.status == 200) {
            handleShowCreation(null);
        }
        else if (xhr.readyState == 4 && xhr.status == 20) {
            handleShowCreation('The connection with the server may be lost. \nThe object is not saved in the system.');
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function sendGetMyObjects() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/myObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var array= JSON.parse(xhr.responseText);
            showObjectArray(array);
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            alertAndNone('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }
        else if (xhr.readyState == 4 && xhr.status == 21){
            alertAndNone('No user is logged in.\nPlease login and try again.');
        }
    };
}


function sendGetAllObjectsOpen() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/allObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var array= JSON.parse(xhr.responseText);
            showObjectArray(array);
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            alertAndNone('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }

    };
}

function sendGetAllObjects(editor,mxUtils) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/allObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            openAllObjectWin(xhr.responseText,editor,mxUtils);
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            alertAndNone('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }

    };
}

function sendGetAllObjects(editor,mxUtils) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/allObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            openAllObjectWin(xhr.responseText,editor,mxUtils);
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            alertAndNone('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }

    };
}

function sendGetNextObjects()
{
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/NextObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var array= JSON.parse(xhr.responseText);
            showObjectArray(array);
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            alertAndNone('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }
    };
}

function sendGetPrevObjects()
{
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/PrevObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var array= JSON.parse(xhr.responseText);
            showObjectArray(array);
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            alertAndNone('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }
    };
}


function sendGetLoadFunc(index)
{
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/LoadObject", true);
    xhr.send(index);

    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var xml= xhr.responseText;
            updateScreenXml(xml);
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            alertOnly('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }
    };
}

function SendGetAllSimilarObjects(message,editor,mxUtils) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/allSimilar", true);
    xhr.send(message);

    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var array= xhr.responseText;
            openAllSimilar(array,editor,mxUtils);
        }
        else if (xhr.readyState == 4 && xhr.status == 20){
            alertAndNone('The connection with the server may be lost. \nPlease check your internet connection\nor try again later');
        }
    };
}

function SendPostLogin(message){
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/login", true);
    xhr.send(message);
}

function SendPostLogOut(){
    var xhr = new XMLHttpRequest();
    xhr.open("Get", "/login", true);
    xhr.send(null);
}

