package com.app.ktorcrud.apicall

import com.app.ktorcrud.response.UsersListResponse
import com.app.ktorcrud.utils.Either

/**
 * Created by Priyanka.
 */
interface ApiServiceClass {
    suspend fun getUserList(page: Int): Either<String, UsersListResponse>
}