/**
 * The one and only WebGL entry point
 */
export let gl = null;

export async function initWebGLContext() {
    const canvas_id = "#glcanvas";
    const text_field = "log_out";

    let html_canvas = document.querySelector(canvas_id);

    document.getElementById("fetch_test").addEventListener("click", function () {
        console.log(" === ");
        alert("Button clicked!");
    });

    gl = html_canvas.getContext("webgl2", {
        antialias: true,
        depth: true,
    });

    console.log(gl);

    if (!gl) {
        alert("cube_c::setup(): Unable to initialize WebGL. Your browser or machine may not support it.");
        return;
    }

    let log_out = document.getElementById(text_field);

    log_out.innerText =
        gl.getParameter(gl.VERSION) +
        "\n " +
        gl.getParameter(gl.SHADING_LANGUAGE_VERSION) +
        "\n " +
        gl.getParameter(gl.VENDOR) +
        "\n";

    const glExtString = gl.getSupportedExtensions();

    for (let i = 0; i < glExtString.length; i++) {
        log_out.innerText = log_out.innerText + glExtString[i] + "\n";
    }
}
