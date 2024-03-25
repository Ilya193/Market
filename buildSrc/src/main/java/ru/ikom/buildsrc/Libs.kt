package ru.ikom.buildsrc

object Libs {
    const val koin = "io.insert-koin:koin-android:${Versions.koin}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val kotlinxSerializationConverter =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.kotlinxSerializationConverter}"
    const val kotlinxSerializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerializationJson}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val shimmer = "com.facebook.shimmer:shimmer:${Versions.shimmer}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
}

object Versions {
    const val koin = "3.3.2"
    const val retrofit = "2.9.0"
    const val okhttp = "4.12.0"
    const val kotlinxSerializationConverter = "1.0.0"
    const val kotlinxSerializationJson = "1.6.0"
    const val coil = "2.5.0"
    const val shimmer = "0.5.0"
    const val room = "2.6.1"
}