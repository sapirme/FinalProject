
function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();

    console.log('ID: ' + profile.getId());
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    console.log('id_token: ' + googleUser.getAuthResponse().id_token);
    //var msg = [profile.getEmail(),];

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/login", true);
    //xhr.send('idtoken=' + googleUser.getAuthResponse().id_token);
    xhr.send( googleUser.getAuthResponse().id_token);

    //do not post above info to the server because that is not safe.
    //just send the id_token
    /*
            var redirectUrl = 'login';
            //using jquery to post data dynamically
            var form = $('<form action="' + redirectUrl + '" method="post">' +
                '<input type="text" name="id_token" value="' +
                googleUser.getAuthResponse().id_token + '" />' +
                '</form>');
            $('body').append(form);
            form.submit();*/
}



function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });

    var xhr = new XMLHttpRequest();
    xhr.open("Get", "/login", true);
    xhr.send(null);
}