function loginButtonPress() {
    var username = document.getElementById('name').value;
    var password = document.getElementById('PasswordInput').value;
    console.log(username);
    console.log(password);
    var users = TAFFY();
    var realPassword = users({name:username}).password;
    console.log(realPassword);
    if (username == user && password == pass) {
        window.location.href = "../HTML/userMap.html";
    }
alert("Invalid Username/Password");
}
function cancelButtonPress() {
    window.location.href = "../HTML/index.html";
}