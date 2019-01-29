var showPasswordButton = document.getElementById("checkPassword");

showPasswordButton.onchange = function(){
    document.getElementById('password').type = showPasswordButton.checked ? 'text' : 'password';
};