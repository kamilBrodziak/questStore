

function changeActivePage(actualPage) {

    if("active" === actualPage.getAttribute("id")) {
        return;
    }
    if(document.getElementById("active") !== null) {
        document.getElementById("active").removeAttribute("id");
    }
    actualPage.setAttribute("id", "active");

}