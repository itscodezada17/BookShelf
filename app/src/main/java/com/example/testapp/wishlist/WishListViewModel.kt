package com.example.testapp.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.books.model.Book
import com.example.testapp.books.model.WishListBook
import com.example.testapp.di.DispatcherModule
import com.example.testapp.wishlist.source.WishListDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    @DispatcherModule.IoDispatcher val coroutineDispatcher: CoroutineDispatcher,
    private var repo: WishListDataSource
): ViewModel() {

    private val _wishListsListLiveData: MutableLiveData<List<Book>?> = MutableLiveData()
    val wishListsListLiveData: LiveData<List<Book>?> = _wishListsListLiveData

    private val _removeFromWishListLiveData: MutableLiveData<Boolean?> = MutableLiveData()
    val removeFromWishListLiveData: LiveData<Boolean?> = _removeFromWishListLiveData

    init {
        getBooksList()
    }

    fun refreshWishList(){
        getBooksList()
    }


    private fun getBooksList() {
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.getBooksList()
            response.onSuccess {
                val list: MutableList<Book> = mutableListOf()
                for (book in it){
                    list.add(Book(book.bookId, book.id,book.image, book.score, book.title, book.publishedChapterDate))
                }
                val newList: List<Book> = list
                _wishListsListLiveData.postValue(newList)
                Timber.d("BooksViewModel $it")
            }
            response.onFailure {
                Timber.d("Failed in BooksViewModel $it")
            }
        }
    }

    fun removeFromWishList(book: Book) {
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.removeFromWishList(WishListBook(book.bookId, book.id,book.image, book.score, book.title, book.publishedChapterDate))
            response.onSuccess {
                _removeFromWishListLiveData.postValue(it)
                Timber.d("BooksViewModel $it")
            }
            response.onFailure {
                Timber.d("Failed in BooksViewModel $it")
            }
        }
    }

}