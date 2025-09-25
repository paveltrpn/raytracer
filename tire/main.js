import { initWebGLContext } from "./context.js";
import { RenderWGL } from "./render/render.js";

/**
 * @brief - entry point.
 */
async function main() {
    // After this call global "gl" handle becomes valid!
    await initWebGLContext();

    const render = new RenderWGL();
    await render.init();
    render.run();
}

/**
 * START
 */
main();
