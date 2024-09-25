package com.app.ktorcrud.di

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Created by Priyanka.
 */
private const val TIME_OUT = 60_000

val ktorHttpClient = HttpClient(Android) {

    defaultRequest {
        host = "reqres.in/api"
//        host = "thumbsnap.com/api"
        url {
            protocol = URLProtocol.HTTPS
        }
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }
    }



    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Log.v("Logger Ktor =>", message)
            }

        }
        level = LogLevel.ALL
    }

    install(ResponseObserver) {
        onResponse { response ->
            Log.d("HTTP status:", "${response.status.value}")
        }
    }

    install(DefaultRequest) {
//        header(HttpHeaders.Authorization, "Bearer ${BuildConfig.TOKEN}")
    }
}

