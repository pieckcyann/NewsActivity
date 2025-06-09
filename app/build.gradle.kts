plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.xiaoyou.newsdisplayactivity"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.xiaoyou.newsdisplayactivity"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
// sourceSets {
//    getByName("main") {
//        resources.srcDirs(
//            "src/main/res/layout/fragment",
//            "src/main/res/layout/item",
//            "src/main/res/layout",
//            "src/main/res/"
//        )
//    }
//}

sourceSets {
    create("analytics") {
        resources.srcDir("src/main/res/layout/fragment")
        resources.srcDir("src/main/res/layout/item")
    }
}


dependencies {

    // Kotlin DSL (build.gradle.kts) For TabLayout
    implementation(libs.appcompat.v170)
    implementation(libs.material.v1120)

    // // Gson
    // implementation(libs.gson)

    // RecyclerView
    implementation(libs.recyclerview)

    // Glide
    implementation(libs.glide)

    // ConstraintLayout
    // implementation(libs.constraint.layout)

    // @NonNull
    // implementation(libs.annotation)

    // CardView
    implementation(libs.cardview)

    // // OkHttp
    // implementation("com.squareup.okhttp3:okhttp:4.12.0")


    // 下拉刷新，上拉加载更多
    // https://github.com/scwang90/SmartRefreshLayout
    implementation(libs.refresh.layout.kernel)      // 核心必须依赖
    implementation(libs.refresh.header.classics)    // 经典刷新头
    implementation(libs.refresh.footer.classics)    // 经典加载

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}