package com.example.photoweathertask.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.WeatherDataBase
import com.example.data.db.WeatherPhotoDao
import com.example.data.remote.ApiService
import com.example.data.repositries.LocalRepositoryImp
import com.example.data.repositries.RemoteRepositoryImp
import com.example.domain.dataInterface.LocalRepository
import com.example.domain.dataInterface.RemoteRepository
import com.example.domain.models.WeatherPhoto
import com.example.photoweathertask.BuildConfig
import com.example.photoweathertask.base.BaseHeaderInterceptor
import com.example.photoweathertask.base.ConnectionInterceptor
import com.example.photoweathertask.utils.validation.network.NetworkValidator
import com.example.photoweathertask.utils.validation.network.NetworkValidatorImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {
    @Binds
    @Singleton
    abstract fun getRemoteRepository(imp: RemoteRepositoryImp): RemoteRepository

    @Binds
    @Singleton
    abstract fun getLocalRepository(imp: LocalRepositoryImp): LocalRepository


    @Binds
    @Singleton
    abstract fun bindNetworkValidator(networkValidatorImp: NetworkValidatorImp): NetworkValidator

}

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    companion object {
        private const val TIME_OUT = 60L
    }

    @Provides
    @Singleton
    fun providesRetrofitBuilder(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun getAuthApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    internal fun provideHttpInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG)
                this.level = HttpLoggingInterceptor.Level.BODY
            else
                this.level = HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        connectionInterceptor: ConnectionInterceptor,
        baseHeaderInterceptor: BaseHeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(connectionInterceptor)
            .addInterceptor(baseHeaderInterceptor)
            .addInterceptor(loggingInterceptor)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun getRemoteRepositoryImp(
        apiService: ApiService,
    ): RemoteRepositoryImp =
        RemoteRepositoryImp(apiService)

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext appContext: Context) =
         Room.databaseBuilder(
            appContext,
            WeatherDataBase::class.java, "weather_db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()


    @Provides
    @Singleton
    fun getDao(dataBase: WeatherDataBase)=dataBase.getWeatherPhotoDao()

    @Provides
    @Singleton
    fun getLocalRepositoryImp(
        dao: WeatherPhotoDao,
    ): LocalRepositoryImp =
        LocalRepositoryImp(dao)

    @Provides
    fun provideEntity() = WeatherPhoto()



}