
function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    /*
    console.log('ID: ' + profile.getId());
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    console.log('id_token: ' + googleUser.getAuthResponse().id_token);
    */
    var welcome = document.getElementById("welcome");
    welcome.innerText = "Welcome "+profile.getName();

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/login", true);
    xhr.send( googleUser.getAuthResponse().id_token);

}



function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });
    var welcome = document.getElementById("welcome");
    welcome.innerText = "";

    var xhr = new XMLHttpRequest();
    xhr.open("Get", "/login", true);
    xhr.send(null);
}