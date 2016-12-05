function loginButtonPress() {
    var username = document.getElementById('name').value;
    var password = document.getElementById('PasswordInput').value;
    console.log(username);
    console.log(password);
    var valid = false;
    if (username == "user" && password == "pass") {
        valid = true;
    }
    if (valid){
        window.location.href = "../HTML/userMap.html";
    } else {
        alert("Invalid Username/Password");
    }
}
function cancelButtonPress() {
    window.location.href = "../HTML/index.html";
}