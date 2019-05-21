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
    /*xhr.onreadystatechange = function(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {

        }
        else if (xhr.readyState == 4 && xhr.status == 10){
            handleCanNotCreate(loader,modal);
            mxUtils.alert('Can not create an appropriate 3D object.');
        }
        else if (xhr.readyState == 4 && xhr.status == 50){
            handleCanNotCreate(loader,modal);
            mxUtils.alert('You used too many shapes.');
        }*/
    xhr.onreadystatechange = function(e) {
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
    };
}