package com.app.ktorcrud.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Priyanka.
 */
@Serializable
data class UsersListResponse(
    @SerialName("page") var page: Int? = 1,
    @SerialName("per_page") var perPage: Int? = 6,
    @SerialName("total") var total: Int? = 1,
    @SerialName("total_pages") var total_pages: Int? = 1,
    @SerialName("data") var data: ArrayList<Data> = arrayListOf(),
    @SerialName("support") var support: Support? = Support()
)

@Serializable
data class Data(
    @SerialName("id") var id: Int? = null,
    @SerialName("email") var email: String? = null,
    @SerialName("first_name") var first_name: String? = null,
    @SerialName("last_name") var last_name: String? = null,
    @SerialName("avatar") var avatar: String? = null
)


@Serializable
data class Support(
    @SerialName("url") var url: String? = null,
    @SerialName("text") var text: String? = null
)