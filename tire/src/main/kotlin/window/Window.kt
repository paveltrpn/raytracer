package window

import org.lwjgl.glfw.*
import org.lwjgl.glfw.*;
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
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
        if (glfwGetPlatform() === GLFW_PLATFORM_COCOA) {
            glfwWindowHint(GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW_FALSE)
        }

        val WIDTH = 300
        val HEIGHT = 300

        val window: Long = glfwCreateWindow(WIDTH, HEIGHT, "GLFW Gears Demo", NULL, NULL)
        if (window == NULL) {
            throw RuntimeException("Failed to create the GLFW window")
        }

    }

}
