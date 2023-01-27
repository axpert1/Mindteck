package com.app.mindteck.domain

import com.app.mindteck.comman.Resource
import com.app.mindteck.utils.convertToResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UseCase @Inject constructor(val repository: Repository) {
    inline operator fun <reified T> invoke(url: String): Flow<Resource<T>> = flow {
        try {
            emit(Resource.Loading())
            val users = repository.get<T>(url)?.let { it.convertToResponse<T>() }
            users?.let {
                emit(Resource.Success(it))
            } ?: run {
                emit(Resource.Error("Response Error"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error("${e.message}"))
        }
    }
}