package com.app.mindteck.domain


import com.app.mindteck.remote.Request
import com.app.mindteck.remote.Resource

import okhttp3.RequestBody

interface Repository {

    suspend fun<ResponseType>  get(apiUrl: String): ResponseType

    suspend fun <ResponseType> post(
        apiUrl: String,
        request: Request
    ): Resource<ResponseType>

    suspend fun <ResponseType> multipart(apiUrl: String,requestBody: RequestBody) : Resource<ResponseType>



}