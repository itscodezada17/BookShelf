package com.example.testapp.books.source

import com.example.testapp.books.model.Book
import com.example.testapp.utils.CONSTANTS
import retrofit2.Retrofit
import retrofit2.http.GET
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class BooksRemoteRepo @Inject constructor(
    @Named("BooksRetrofit")retrofit: Retrofit
){
    private val service = retrofit.create(ApiRequest::class.java)

    suspend fun getProductsList(): Result<List<Book>> {
        return try {
            val response = service.getProductsList()
            Result.success(response)
        } catch (e: Exception) {
            Timber.e(e.toString())
            Result.failure(e)
        }
    }

    interface ApiRequest {
        @GET(CONSTANTS.BOOK_LIST)
        suspend fun getProductsList(): List<Book>
    }

}