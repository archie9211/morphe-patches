import app.morphe.patcher.patch.loadPatchesFromJar
import java.io.File
import java.net.URLClassLoader

fun main() {
    val patchFiles = setOf(File("patches/build/libs/patches-1.0.6.mpp"))
    val patchClassLoader = URLClassLoader(patchFiles.map { it.toURI().toURL() }.toTypedArray())
    try {
        val clazz = Class.forName("com.user.patches.medium.FreediumPatchKt", true, patchClassLoader)
        println("Loaded class: \$clazz")
        val method = clazz.getMethod("getFreediumPatch")
        val patch = method.invoke(null)
        println("Got patch: \$patch")
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}
