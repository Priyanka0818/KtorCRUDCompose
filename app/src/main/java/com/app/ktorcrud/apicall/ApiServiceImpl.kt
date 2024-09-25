package com.app.ktorcrud.apicall

import com.app.ktorcrud.response.UsersListResponse
import com.app.ktorcrud.utils.Either
import com.app.ktorcrud.utils.Failure
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.bodyAsText
import io.ktor.utils.io.charsets.Charset

/**
 * Created by Priyanka.
 */
class ApiServiceImpl(private val apiService: ApiService) : ApiServiceClass {

    override suspend fun getUserList(page: Int): Either<String, UsersListResponse> {
        return try {
            Either.Right(apiService.getUsers(page))
        } catch (ex: Exception) {
            Either.Left(ex.errorMessage() as String)
        }
    }
}


suspend fun Exception.errorMessage(): Any =
    when (this) {
        is ClientRequestException -> {
            Charset.defaultCharset()
            response.bodyAsText()
        }

        is ResponseException -> {
            if (response.status.value == 404) {
                response.status.description
            } else {
                /* Gson().fromJson(
                     response.readText(Charset.defaultCharset()),
                     CommonErrorResponse::class.java
                 ).error!!*/
            }
        }

        else -> {
            localizedMessage!!
        }
    }

fun Exception.toCustomExceptions() = when (this) {
    is ServerResponseException -> Failure.HttpErrorInternalServerError(this)
    is ClientRequestException ->
        when (this.response.status.value) {
            400 -> Failure.HttpErrorBadRequest(this)
            401 -> Failure.HttpErrorUnauthorized(this)
            403 -> Failure.HttpErrorForbidden(this)
            404 -> Failure.HttpErrorNotFound(this)
            405 -> Failure.MethodNotAllowed(this)
            else -> Failure.HttpError(this)
        }
    is RedirectResponseException -> Failure.HttpError(this)
    else -> Failure.GenericError(this)
}
