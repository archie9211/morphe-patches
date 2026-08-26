package com.user.patches.medium

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.removeInstructions
import app.morphe.patcher.patch.bytecodePatch

val pairIpPatch = bytecodePatch(
    name = "Bypass Play Store Check",
    description = "Bypasses PairIP signature and license checks (Play Store version error).",
    default = true
) {
    compatibleWith(mediumCompatibility)

    execute {
        // Bypass SignatureCheck.verifyIntegrity
        try {
            val signatureCheckClass = classDefBy("Lcom/pairip/SignatureCheck;")
            val mutableSigClass = mutableClassDefBy(signatureCheckClass)
            
            val verifyMethod = mutableSigClass.methods.first { it.name == "verifyIntegrity" }
            verifyMethod.removeInstructions(0)
            verifyMethod.addInstructions(0, "return-void")
        } catch (e: Exception) {
            // Ignore if class not found
        }

        // Bypass LicenseClient.checkLicense
        try {
            val licenseClientClass = classDefBy("Lcom/pairip/licensecheck/LicenseClient;")
            val mutableLicClass = mutableClassDefBy(licenseClientClass)
            
            val checkLicenseMethod = mutableLicClass.methods.first { it.name == "checkLicense" }
            checkLicenseMethod.removeInstructions(0)
            checkLicenseMethod.addInstructions(0, "return-void")
        } catch (e: Exception) {
            // Ignore if class not found
        }
    }
}
