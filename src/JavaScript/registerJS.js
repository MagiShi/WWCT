function registerButtonPress() {
    var username = document.getElementById('username').value;
    var name = document.getElementById('name').value;
    var password = document.getElementById('PasswordInput').value;
    var password2 = document.getElementById('PasswordInput2').value;
    var type = document.getElementById('selectTypeBox').value;
    console.log(username);
    console.log(password);
    console.log(type);
    var valid = true;
    if (password != password2) {
        valid = false
        alert("Passwords do not match.");
    }
    if (username == "") {
        valid = false;
        alert("Username cannot be blank.");
    }
    if (password == "") {
            valid = false;
            alert("Password cannot be blank.");
     }
     if (name == "") {
             valid = false;
             alert("Name cannot be blank.");
         }
    if (valid){
        var users = TAFFY();
        users.insert({name:name,username:username,password:password,usertype:type});
        users.store("users");
        window.location.href = "../HTML/userMap.html";
    }
}
function cancelButtonPress() {
    window.location.href = "../HTML/index.html";
}