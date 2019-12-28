const val kotlinVersion = "1.3.41"

object BuildPlugins {

    private object Versions {
        const val buildToolsVersion = "3.3.1"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val navigationSafePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:2.1.0-alpha05"
}

object AppSdk {
    const val compile = 29
    const val min = 17
    const val target = 29
    const val versionCode = 1
    const val versionName = "1.0"
}

/**
 *  Versions
 */
object Versions {

    // Core
    const val androidX = "1.0.2"
    const val androidXAnnotation = "1.1.0"
    const val androidXTest = "1.0.0"
    const val navigation = "2.1.0-alpha02"
    const val lifecycle = "2.2.0-alpha02"
    const val multidex = "1.0.3"
    const val okHttp = "3.12.0"
    const val retrofit = "2.6.0"
    const val moshi = "1.8.0"

    const val dagger = "2.22"
    const val timber = "4.7.1"
    // UI
    const val constraintLayout = "1.1.3"
    const val googleAndroidMaterial = "1.2.0-alpha01"
    const val recyclerView = "1.1.0-beta02"
    const val circleImage = "3.0.0"
    const val glide = "4.9.0"
    const val baseRecyclerView = "2.9.35"
    const val fancyButton = "1.9.1"
    const val hawk = "2.0.1"
}
/**
 *  Libraries
 */
object Libs {
    // Core
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidX}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.androidX}"
    const val androidxAnnotation = "androidx.annotation:annotation:${Versions.androidXAnnotation}"
    const val androidxTest = "androidx.test:core:${Versions.androidXTest}"
    const val ktx = "androidx.core:core-ktx:${Versions.androidX}"

    const val multidex = "com.android.support:multidex:${Versions.multidex}"

    const val   lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okhttp3Interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"

    // UI
    const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val googleAndroidMaterial =
            "com.google.android.material:material:${Versions.googleAndroidMaterial}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    const val circleImage = "de.hdodenhof:circleimageview:${Versions.circleImage}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val baseRecyclerView = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.baseRecyclerView}"
    const val fancyButton = "com.github.medyo:fancybuttons:${Versions.fancyButton}"
    // Dagger
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Jetpack
    const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val navRuntime = "androidx.navigation:navigation-runtime:${Versions.navigation}"

    // Storage
    const val hawk = "com.orhanobut:hawk:${Versions.hawk}"
}

/**
 *   Test Libraries
 */
object TestLibs {
    // Test
    object Versions {
        // Test
        const val jUnit = "4.12"
        const val testRunner = "1.2.0"
        const val testEspresso = "3.2.0"
        const val roboElectric = "4.3"
        const val truth = "1.0"
    }
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val testEspresso = "androidx.test.espresso:espresso-core:${Versions.testEspresso}"
}

/**
 *   Annotation Processor
 */
object Annotations{
    const val glide = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val dagger = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
}

object DebugLibs {
    private object Versions {
        const val leakCanary = "2.0-beta-3"
    }
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
}

/**
 *   Modules
 */
object Modules {

}

/**
 *   Templates
 */
object Templates {

}