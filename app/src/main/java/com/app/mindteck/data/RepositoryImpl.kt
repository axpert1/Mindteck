package com.app.mindteck.data

import com.app.mindteck.domain.Repository
import com.app.mindteck.remote.ApiService
import com.app.mindteck.remote.Request
import com.app.mindteck.remote.Resource
import com.app.mindteck.utils.asJsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService) : Repository {
    override suspend fun <ResponseType> get(apiUrl: String): ResponseType {
        return withContext(Dispatchers.IO) {
            apiService.get(apiUrl)
        }
    }


    override suspend fun <ResponseType> post(
        apiUrl: String,
        request: Request
    ): Resource<ResponseType> {
        return withContext(Dispatchers.IO) {
            apiService.post(apiUrl, request.asJsonObject())

        }
    }

    override suspend fun <ResponseType> multipart(
        apiUrl: String,
        requestBody: RequestBody
    ): Resource<ResponseType> {
        return withContext(Dispatchers.IO) {
            apiService.postMultipart(apiUrl, requestBody)

        }
    }
}