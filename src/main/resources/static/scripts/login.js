
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
  
function validateRegister() {
    if (validateEmail(emailRegister.value)) {
        emailRegister.removeAttribute("class");
        return true;
    }
    emailRegister.setAttribute("class", "invalidOutput");
    return false;
}

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

var submitLoginButton = document.get("submit_login_button");

function login() {
    var email = document.getElementById("loginEmail").value;
    var password = document.getElementById("loginPassword").value;
    if(validateLogin()) {
        var form = document.getElementById("loginForm");
        if(validateLogin && email === "student@cc.cc" && password === "123") {
            setCookie("accountAnchor", "/student/studentPanel", 1);
            form.setAttribute("action", "/student/studentPanel");
            return true;
        } else if(validateLogin && email === "mentor@cc.cc" && password === "123") {
            setCookie("accountAnchor", "mentorMenu.html", 1);
            form.setAttribute("action", "mentorMenu.html");
            return true;
        } else if(validateLogin && email === "admin@cc.cc" && password === "123") {
            setCookie("accountAnchor", "warlord/warlord-main.html", 1);
            form.setAttribute("action", "warlord/warlord-main.html");
            return true;
        }
    }
    document.getElementById("message").innerHTML = "Incorrect email or password!";
    return false;
}