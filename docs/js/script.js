function mostrar(seccion) {
    document.getElementById("inicio").classList.remove("activa");
    document.getElementById("inst").classList.remove("activa");
    document.getElementById("cred").classList.remove("activa");
    document.getElementById("videos").classList.remove("activa");

    document.getElementById(seccion).classList.add("activa");
}

function cambiarIdioma(lang) {

    document.getElementById("inicio-es").style.display = (lang === 'es') ? "block" : "none";
    document.getElementById("inicio-en").style.display = (lang === 'en') ? "block" : "none";

    document.getElementById("inst-es").style.display = (lang === 'es') ? "block" : "none";
    document.getElementById("inst-en").style.display = (lang === 'en') ? "block" : "none";
    document.getElementById("cred-es").style.display = (lang === 'es') ? "block" : "none";
    document.getElementById("cred-en").style.display = (lang === 'en') ? "block" : "none";

    if (lang === 'es') {
        document.getElementById("btn-inicio").innerText = "> INICIO";
        document.getElementById("btn-inst").innerText = "> COMO JUGAR";
        document.getElementById("btn-cred").innerText = "> CREDITOS";
        document.getElementById("btn-videos").innerText = "> VIDEOS";
        document.getElementById("btn-descarga").innerText = "DESCARGAR";
        document.getElementById("videos-titulo").innerText = "Videos de Gameplay";
	document.getElementById("btn-encuesta").innerText = "> COMPLETA LA ENCUESTA";
        document.getElementById("btn-source").innerText = "> CODIGO FUENTE";


    } else {
        document.getElementById("btn-inicio").innerText = "> START";
        document.getElementById("btn-inst").innerText = "> HOW TO PLAY";
        document.getElementById("btn-cred").innerText = "> CREDITS";
        document.getElementById("btn-videos").innerText = "> VIDEOS";
        document.getElementById("btn-descarga").innerText = "DOWNLOAD";
        document.getElementById("videos-titulo").innerText = "Gameplay Videos";
 	document.getElementById("btn-encuesta").innerText = "> TAKE THE SURVEY";
	document.getElementById("btn-source").innerText = "> SOURCE CODE";





    }
}

/* 🎮 LISTA DE VIDEOS (EDITAR SOLO ACA) */
const videos = [
    { id: "KRwpEIry5AA", nombre: "Presentacion del juego" },
    { id: "H8aBByjD8V8", nombre: "GamePlay version Clasica" },
    { id: "cYVkXzHvKhk", nombre: "GamePlay version extendida (Episodio 1)" },
    { id: "bee__RF9VZ4", nombre: "GamePlay version extendida (Episodio 2)" },
    { id: "XXF8hEAmDw0", nombre: "GamePlay version extendida (Episodio 3)" }
];

function cargarVideos() {
    const contenedor = document.getElementById("videos-lista");

    videos.forEach(video => {
        const html = `
            <div class="video">
                <a href="https://youtu.be/${video.id}" target="_blank">
                    <img src="https://img.youtube.com/vi/${video.id}/hqdefault.jpg">
                    <div class="play">▶</div>
                </a>
                <div class="video-titulo">${video.nombre}</div>
            </div>
        `;
        contenedor.innerHTML += html;
    });
}

window.onload = cargarVideos;

function abrirEncuesta() {
    window.open("https://forms.gle/LE89CsxJQHYsVrsA9", "_blank");
}

function abrirFuente() {
    window.open("https://github.com/guillesky/Public_LibGDX_Kings_Valley", "_blank");
}