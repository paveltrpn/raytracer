package window

import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL46.*
import org.lwjgl.system.MemoryUtil.*
import java.nio.IntBuffer
import java.util.function.IntFunction
import org.lwjgl.system.MemoryStack.*
import org.lwjgl.system.MemoryUtil.*

class Window {
    private var allocator: GLFWAllocator? = null
    private var window: Long = 0

    init {
        allocator = GLFWAllocator.calloc()
            .allocate(GLFWAllocateCallbackI { size: Long, user: Long -> nmemAllocChecked(size) })
            .reallocate(GLFWReallocateCallbackI { block: Long, size: Long, user: Long ->
                nmemReallocChecked(
                    block,
                    size
                )
            })
            .deallocate(GLFWDeallocateCallbackI { block: Long, user: Long -> nmemFree(block) })

        glfwInitAllocator(allocator)

        GLFWErrorCallback.createPrint().set()
        check(glfwInit()) { "Unable to initialize glfw" }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)
        glfwWindowHint(GLFW_SCALE_TO_MONITOR, GLFW_TRUE)
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE)

        // Set latest OpenGL version context.
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6)

        val WIDTH = 300
        val HEIGHT = 300

        window = glfwCreateWindow(WIDTH, HEIGHT, "GLFW Gears Demo", NULL, NULL)
        if (window == NULL) {
            throw RuntimeException("Failed to create the GLFW window")
        }

        glfwSetWindowSizeLimits(window, WIDTH, HEIGHT, GLFW_DONT_CARE, GLFW_DONT_CARE)

        //glfwSetWindowAspectRatio(window, 1, 1);
        val monitor = glfwGetPrimaryMonitor()

        val vidmode: GLFWVidMode? = glfwGetVideoMode(monitor)

        glfwSetWindowPos(
            window,
            (vidmode!!.width() - WIDTH) / 2,
            (vidmode.height() - HEIGHT) / 2
        )


        // STACK MEMORY USAGE EXAMPLE
//        stackPush().use { stack ->
//            val pWidth: IntBuffer = stack.mallocInt(1) // int*
//            val pHeight: IntBuffer = stack.mallocInt(1) // int*
//
//            // Get the window size passed to glfwCreateWindow
//            glfwGetWindowSize(window, pWidth, pHeight)
//
//            // Get the resolution of the primary monitor
//            val vidmode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!
//
//            // Center the window
//            glfwSetWindowPos(
//                window,
//                (vidmode.width() - pWidth.get(0)) / 2,
//                (vidmode.height() - pHeight.get(0)) / 2
//            )
//        }

//        glfwSetFramebufferSizeCallback(window, this::framebufferSizeChanged)

//        glfwSetWindowRefreshCallback(window, { windowHnd ->
//            gears.render()
//            gears.animate()
//            glfwSwapBuffers(windowHnd)
//        })

        glfwMakeContextCurrent(window)
        GL.createCapabilities(IntFunction { num: Int -> memCallocPointer(num) })
//        debugProc = GLUtil.setupDebugMessageCallback()


        glfwSetKeyCallback(window, { window, key, scancode, action, mods ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(
                window,
                true
            ) // We will detect this in the rendering loop
        })

        glfwSwapInterval(1)
        glfwShowWindow(window)

//        glfwInvoke(window, null, this::framebufferSizeChanged)
    }

    fun loop() {
//        var lastUpdate = System.currentTimeMillis()

        glClearColor(1.0F, 1.0F, 1.0F, 1.0F)

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents()

            glViewport(0, 0, 300, 300)
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            glfwSwapBuffers(window)
        }
    }

    fun destroy() {
        memFree(GL.getCapabilities().getAddressBuffer())
        GL.setCapabilities(null)

//        if (window !== NULL) {
//            glfwFreeCallbacks(window)
//            glfwDestroyWindow(window)
//        }

        glfwTerminate()
//        Objects.requireNonNull<T?>(glfwSetErrorCallback(null)).free()

        allocator!!.deallocate().free()
        allocator!!.reallocate().free()
        allocator!!.allocate().free()
        allocator!!.free()
    }
}
