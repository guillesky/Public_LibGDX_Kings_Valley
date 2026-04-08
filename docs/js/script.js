function mostrar(seccion) {
    document.getElementById("inicio").classList.remove("activa");
    document.getElementById("inst").classList.remove("activa");
    document.getElementById("cred").classList.remove("activa");

    document.getElementById(seccion).classList.add("activa");
}

function cambiarIdioma(lang) {

    // CONTENIDO
    document.getElementById("inst-es").style.display = (lang === 'es') ? "block" : "none";
    document.getElementById("inst-en").style.display = (lang === 'en') ? "block" : "none";
    document.getElementById("cred-es").style.display = (lang === 'es') ? "block" : "none";
    document.getElementById("cred-en").style.display = (lang === 'en') ? "block" : "none";

    // BOTONES + DESCARGA
    if (lang === 'es') {
        document.getElementById("btn-inicio").innerText = "> INICIO";
        document.getElementById("btn-inst").innerText = "> COMO JUGAR";
        document.getElementById("btn-cred").innerText = "> CREDITOS";
        document.getElementById("btn-descarga").innerText = "DESCARGAR";
    } else {
        document.getElementById("btn-inicio").innerText = "> START";
        document.getElementById("btn-inst").innerText = "> HOW TO PLAY";
        document.getElementById("btn-cred").innerText = "> CREDITS";
        document.getElementById("btn-descarga").innerText = "DOWNLOAD";
    }
}