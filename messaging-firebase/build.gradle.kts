@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    with(catalogPlugins.plugins) {
        plugin(android.library)
        plugin(kotlin.multiplatform)
        plugin(kotlin.serialization)
        id(mersey.kotlin.extension.id())
        id(mersey.android.extension.id())
    }
    `maven-publish-plugin`
}

android {
    namespace = "com.merseyside.merseyLib.messaging.firebase"
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
    }

    buildFeatures {
        dataBinding = true
    }
}

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }

    // Add apple targets
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()
}


kotlinExtension {
    debug = true
    setCompilerArgs(
        "-Xinline-classes",
        "-opt-in=kotlin.RequiresOptIn",
        "-Xskip-prerelease-check"
    )
}

val mppLibs = listOf(
    common.serialization,
    multiplatformLibs.koin,
    common.coroutines
)

val androidLibz = listOf(
    androidLibs.koin
)

dependencies {
    commonMainApi(projects.messagingCore)

    mppLibs.forEach { lib -> commonMainImplementation(lib) }
    androidLibz.forEach { lib -> implementation(lib) }

    implementation(platform(androidLibs.firebase.bom))
    api(androidLibs.firebase.messaging)

    if (isLocalKotlinExtLibrary()) {
        commonMainApi(project(Modules.MultiPlatform.MerseyLibs.kotlinExt))
    } else {
        commonMainApi(common.mersey.kotlin.ext)
    }
}