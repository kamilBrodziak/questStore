
const loginButton = document.getElementById("loginButton");
const registerButton = document.getElementById("registerButton");
const loginCase = document.getElementById("loginCase");
const registerCase = document.getElementById("registerCase");


if (!loginCase.hasAttribute("display")) {
    loginCase.style.display = "none";
}
if (!registerCase.hasAttribute("display")) {
    registerCase.style.display = "none";
}

loginButton.onclick = function() {
    if(loginCase.style.display === "none") {
        loginCase.style.display = "block";
        registerCase.style.display = "none";
        registerButton.removeAttribute("class");
        loginButton.setAttribute("class", "activated");
    } else {
        loginCase.style.display = "none";
        loginButton.removeAttribute("class");
    }
};

registerButton.onclick = function() {
    if(registerCase.style.display === "none") {
        loginCase.style.display = "none";
        loginButton.removeAttribute("class");
        registerButton.setAttribute("class", "activated");
        registerCase.style.display = "block";
    } else {
        registerCase.style.display = "none";
        registerButton.removeAttribute("class");
    }
};



const emailLogin = document.getElementById("loginEmail");
emailLogin.addEventListener("focusout", validateLogin);
const emailRegister = document.getElementById("registerEmail");
emailRegister.addEventListener("focusout", validateRegister);


function validateEmail(email) {
    var re  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return re.test(email);
}
  
function validateLogin() {
    if (validateEmail(emailLogin.value)) {
        emailLogin.removeAttribute("class");
        return true;
    }
    emailLogin.setAttribute("class", "invalidOutput");
    return false;
}
  
function validateRegister() {
    if (validateEmail(emailRegister.value)) {
        emailRegister.removeAttribute("class");
        return true;
    }
    emailRegister.setAttribute("class", "invalidOutput");
    return false;
}