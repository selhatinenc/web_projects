
function hideiconbar(){
    var iconbar=document.getElementById("iconbar");
    var navigation= document.getElementById("navigation");
    iconbar.setAttribute("style","display:none;");
    navigation.classList.remove("hide");


}
function showiconbar(){
    var closeicon=document.getElementById("closeicon");
    var iconbar=document.getElementById("iconbar");
    var navigation= document.getElementById("navigation");
    iconbar.setAttribute("style","display:block;")

    navigation.classList.add("hide");


}