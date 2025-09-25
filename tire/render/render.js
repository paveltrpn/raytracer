/**
 * @brief - Class manages all WebGL rendering related things.
 */
class RenderWGL {
    /**
     *
     * @param {HTML canvas} canvasHandle
     */
    constructor(canvasHandle) {
        this.glHandle = canvasHandle.getContext("webgl2", {
            antialias: true,
            depth: true,
        });

        if (!this.glHandle) {
            alert("cube_c::setup(): Unable to initialize WebGL. Your browser or machine may not support it.");
            return;
        }

        this.width = this.glHandle.drawingBufferWidth;
        this.height = this.glHandle.drawingBufferHeight;
        this.aspect = this.width / this.height;
    }

    getExtensionsList() {
        return this.glHandle.getSupportedExtensions();
    }

    getGlVersion() {
        return this.glHandle.getParameter(gl.VERSION);
    }

    getGLSLVersion() {
        return this.glHandle.getParameter(gl.SHADING_LANGUAGE_VERSION);
    }

    getGLVendor() {
        return this.glHandle.getParameter(gl.SHADING_LANGUAGE_VERSION);
    }
}
