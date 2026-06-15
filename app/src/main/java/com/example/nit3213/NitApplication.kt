package com.example.nit3213

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class. @HiltAndroidApp turns this into the root of Hilt's
 * dependency-injection graph, so every Activity/ViewModel can request
 * dependencies (Retrofit, Repository, etc.) without creating them by hand.
 */
@HiltAndroidApp
class NitApplication : Application()
