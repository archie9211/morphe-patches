package com.user.patches.audiorelay

import app.morphe.patcher.fingerprint.Fingerprint
import app.morphe.patcher.fingerprint.filter.string

val premiumResponseFingerprint = Fingerprint(
    name = "PremiumResponse toString",
    returnType = "Ljava/lang/String;",
    parameters = emptyList(),
    filters = listOf(
        string("PremiumResponse(value=")
    )
)
