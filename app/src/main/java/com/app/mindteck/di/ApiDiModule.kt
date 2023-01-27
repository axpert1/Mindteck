package com.app.mindteck.di


import com.app.mindteck.remote.ApiService
import com.app.mindteck.remote.ServiceGenerator
import com.app.mindteck.remote.interceptor.NetworkConnectionInterceptor
import com.app.mindteck.domain.Repository
import com.app.mindteck.data.RepositoryImpl
import com.app.mindteck.domain.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ApiDiModule {
    @Provides
    fun getMyApi(networkConnectionInterceptor: NetworkConnectionInterceptor): ApiService {
        return ServiceGenerator(networkConnectionInterceptor)

    }

    @Provides
    fun apiRepositoryImpl(apiService: ApiService): Repository {
        return RepositoryImpl(apiService)
    }


    @Provides
    fun getUseCase(repository: Repository):UseCase{
        return UseCase(repository)
    }

}