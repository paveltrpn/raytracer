package window

import org.lwjgl.glfw.*
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*
import org.lwjgl.system.*
import org.lwjgl.system.MemoryStack.*
import org.lwjgl.system.MemoryUtil.*
import java.util.*
import java.util.function.IntFunction


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
        if (glfwGetPlatform() === GLFW_PLATFORM_COCOA) {
            glfwWindowHint(GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW_FALSE)
        }

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

//        glfwSetFramebufferSizeCallback(window, this::framebufferSizeChanged)

//        glfwSetWindowRefreshCallback(window, { windowHnd ->
//            gears.render()
//            gears.animate()
//            glfwSwapBuffers(windowHnd)
//        })

        glfwMakeContextCurrent(window)
        GL.createCapabilities(IntFunction { num: Int -> memCallocPointer(num) })
//        debugProc = GLUtil.setupDebugMessageCallback()


        glfwSwapInterval(1)
        glfwShowWindow(window)

//        glfwInvoke(window, null, this::framebufferSizeChanged)
    }

    fun loop() {
        var lastUpdate = System.currentTimeMillis()

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents()

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
