package com.example.testapp.auth.signUp.source

import com.example.testapp.auth.signUp.Country
import com.example.testapp.books.model.Book
import com.example.testapp.utils.CONSTANTS
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class SigUpRemoteRepo @Inject constructor(
    @Named("CountryRetrofit")retrofitCountry: Retrofit,
    @Named("BooksRetrofit")retrofitBook: Retrofit,
    @Named("CountryMapRetrofit")retrofitCountryMap: Retrofit
){
    private val service1 = retrofitBook.create(ApiRequest::class.java)
    private val service2 = retrofitBook.create(APiCurrentCountryRequest::class.java)
    private val service3 = retrofitBook.create(APiCountryMapRequest::class.java)

    suspend fun getCountryList(): Result<List<Country>> {
        return try {
            val response = service1.getCountryList()
            Result.success(response)
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun getCurrentCountry(): Result<Country> {
        return try {
            val response = service2.getCurrentCountry()
            Result.success(response)
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }

    suspend fun getMapCountry(): Result<HashMap<String, String>> {
        return try {
            val response = service3.getMapCountry("ipinfo.io")
            Result.success(response)
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }

    interface ApiRequest {
        @GET(CONSTANTS.COUNTRY_LIST)
        suspend fun getCountryList(): List<Country>
    }

    interface APiCurrentCountryRequest{
        @GET(CONSTANTS.IP_COUNTRY_BASE_URL)
        suspend fun getCurrentCountry(): Country
    }

    interface APiCountryMapRequest{
        @GET(CONSTANTS.COUNTRY_CODE_MAP)
        suspend fun getMapCountry(
            //ref=ipinfo.io
            @Query("ref") ref: String
        ): HashMap<String,String>
    }

}