package com.user.patches.medium

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.Fingerprint
import app.morphe.patcher.patch.stringOption
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions

val mediumCompatibility = Compatibility(
    name = "Medium",
    packageName = "com.medium.reader",
    targets = listOf(
        AppTarget(version = "4.5.1784910415")
    )
)

val processPostDataFingerprint = Fingerprint(
    returnType = "V",
    parameters = listOf("Lcom/medium/android/graphql/FullPostQuery\$Data;", "Z", "Ljava/util/ArrayList;")
)

val donkeyAppFingerprint = Fingerprint(
    definingClass = "Lcom/medium/android/donkey/DonkeyApplication;",
    name = "onCreate",
    returnType = "V"
)

val freediumPatch = bytecodePatch(
    name = "Freedium paywall bypass",
    description = "Opens a webview to Freedium for locked articles.",
    default = true
) {
    compatibleWith(mediumCompatibility)
    val mirrorUrlOption by stringOption(key = "freedium_mirror_url")

    extendWith("extensions/extension.mpe")

    execute {
        donkeyAppFingerprint.method.addInstructions(0, """
            invoke-static { p0 }, Lcom/user/patches/medium/ActivityTracker;->register(Landroid/app/Application;)V
        """)

        processPostDataFingerprint.method.addInstructions(0, """
            const-string v0, "$mirrorUrlOption"
            invoke-static { p1, v0 }, Lcom/user/patches/medium/FreediumInterceptor;->checkAndRedirect(Ljava/lang/Object;Ljava/lang/String;)V
        """)
    }
}
