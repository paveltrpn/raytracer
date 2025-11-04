/**
 * The one and only WebGL entry point
 */
export let gl = null;

export async function initWebGLContext() {
    const canvasId = "#glcanvas";
    const textField = "log_out";

    const htmlCanvas = <HTMLCanvasElement>document.querySelector(canvasId);

    document.getElementById("fetch_test").addEventListener("click", () => {
        console.log(" === ");
        alert("Button clicked!");
    });

    gl = htmlCanvas.getContext("webgl2", {
        antialias: true,
        depth: true,
    });

    console.log(gl);

    if (!gl) {
        alert("cube_c::setup(): Unable to initialize WebGL. Your browser or machine may not support it.");
        return;
    }

    const logOut = document.getElementById(textField);

    let accumulatedText: string =
        gl.getParameter(gl.VERSION) +
        "\n " +
        gl.getParameter(gl.SHADING_LANGUAGE_VERSION) +
        "\n " +
        gl.getParameter(gl.VENDOR) +
        "\n";

    const glExtString = gl.getSupportedExtensions();

    for (let i = 0; i < glExtString.length; i++) {
        accumulatedText += `${glExtString[i]}\n`;
    }
    logOut.innerText = accumulatedText;
}
