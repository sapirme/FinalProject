
function onSignIn(googleUser) {
    console.log("conected.");
    var profile = googleUser.getBasicProfile();
    var welcome = document.getElementById("welcome");
    welcome.innerText = "Welcome "+profile.getName();
    SendPostLogin(googleUser.getAuthResponse().id_token);
}



function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });
    var welcome = document.getElementById("welcome");
    welcome.innerText = "";

    SendPostLogOut();
}