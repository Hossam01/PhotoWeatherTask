package com.example.photoweathertask.utils.platform

import android.os.Build
import javax.inject.Inject

class Versions @Inject constructor() {
    fun isAndroidM(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}