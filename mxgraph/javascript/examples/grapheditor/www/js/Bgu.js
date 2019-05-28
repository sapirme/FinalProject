function getAllObjects(factory)
{
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/allObjects", true);
    xhr.send(null);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            var a=xhr.responseText;
            mxUtils.alert(a);
            var win = window.open('DBView.html',"_blank",
                'toolbar=yes,scrollbars=yes,resizable=yes,top=20%,left=20%,fullscreen="yes",'+'height=' + screen.availHeight + ',width=' + screen.availWidth );
            win.moveTo(0, 0);


        }
    };
}



function createIllusion (xml,factory) {
    //var servletFactory = new ServletFactory();
    //var xhr = servletFactory.llusionServlet(factory,'POST');
    var loader = document.getElementById('myLoader');
    loader.style.display = "block";
    var modal = document.getElementById('myModalGrey');
    modal.style.display = "block";
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/run", true);
    xhr.send(xml);
    xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            handleCanCreate(loader,modal);
        } else if (xhr.readyState == 4 && xhr.status == 10) {
            handleCanNotCreate(loader, modal);
            mxUtils.alert('Can not create an appropriate 3D object.');
        } else if (xhr.readyState == 4 && xhr.status == 50) {
            handleCanNotCreate(loader, modal);
            mxUtils.alert('You used too many shapes.');
        }
    };
    /*xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            handleShowCreation(loader,modal);
        }
        else if (xhr.readyState == 4 && xhr.status == 20) {
            handleShowCreation(loader,modal);
            mxUtils.alert('The connection with the server may be lost. \nThe object is not saved in the system.');
        }
        else if (xhr.readyState == 4 && xhr.status == 10){
            handleCanNotCreate(loader,modal);
            mxUtils.alert('Can not create an appropriate 3D object.');
        }
        else if (xhr.readyState == 4 && xhr.status == 50){
            handleCanNotCreate(loader,modal);
            mxUtils.alert('You used too many shapes.');
        }
    };*/
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
                mxUtils.alert('The connection with the server may be lost. \nThe object is not saved in the system.');
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


function handleSignIn(){
    var win = window.open('signIn.html',"_blank",
        "toolbar=yes,scrollbars=yes,resizable=yes,top=50%,left=50%,width=200,height=150");


}

