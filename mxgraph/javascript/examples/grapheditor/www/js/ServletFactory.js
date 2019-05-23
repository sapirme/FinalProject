//boolean test = false;


function ServletFactory(){
    this.llusionServlet = function (servletType,httpReqType) {
        var xhr = new XMLHttpRequest();
        if (servletType == true){
            xhr.open(httpReqType, "/run", true);
        }
        else{
            xhr.open(httpReqType, "/runDemo", true);
        }
        return xhr;
    }

}