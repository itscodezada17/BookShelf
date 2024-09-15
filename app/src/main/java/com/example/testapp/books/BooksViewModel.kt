package com.example.testapp.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapp.di.DispatcherModule
import com.example.testapp.books.model.Book
import com.example.testapp.books.model.WishListBook
import com.example.testapp.books.source.DataSource
import com.example.testapp.utils.pref.UserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    @DispatcherModule.IoDispatcher val coroutineDispatcher: CoroutineDispatcher,
    private var repo: DataSource,
    private val userPref: UserPref
): ViewModel() {

    private val _booksListLiveData: MutableLiveData<List<Book>?> = MutableLiveData()
    val booksListLiveData: LiveData<List<Book>?> = _booksListLiveData

    private val _addToWishListLiveData: MutableLiveData<Boolean?> = MutableLiveData()
    val addToWishListLiveData: LiveData<Boolean?> = _addToWishListLiveData

    private val _wishListsListLiveData: MutableLiveData<List<Book>?> = MutableLiveData()
    val wishListsListLiveData: LiveData<List<Book>?> = _wishListsListLiveData

    private val _removeFromWishListLiveData: MutableLiveData<Boolean?> = MutableLiveData()
    val removeFromWishListLiveData: LiveData<Boolean?> = _removeFromWishListLiveData

    private val _isLoggedInLivedata: MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedInLivedata: LiveData<Boolean> = _isLoggedInLivedata

    private val _userEmailLivedata: MutableLiveData<String> = MutableLiveData()
    val userEmailLivedata: LiveData<String> = _userEmailLivedata

    init {
       getWishList()
       getBooksList()
       userDetails()
    }


    private fun getBooksList() {
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.getBooksList()
            response.onSuccess {
                addToCache(it)
                _booksListLiveData.postValue(it)
                Timber.d("BooksViewModel $it")
            }
            response.onFailure {
                Timber.d("Failed in BooksViewModel $it")
            }
        }
    }

    private fun getWishList() {
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.getWishList()
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

    private fun addToCache(book: List<Book>) {
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.addToCache(book)
            response.onSuccess {
                //_addToWishListLiveData.postValue(it)
                Timber.d("Added to cache $it")
            }
            response.onFailure {
                Timber.d("Failed in BooksViewModel $it")
            }
        }
    }

    fun addToWishList(book: Book) {
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.addToWishList(WishListBook(book.bookId, book.id,book.image, book.score, book.title, book.publishedChapterDate))
            response.onSuccess {
                _addToWishListLiveData.postValue(it)
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



    private fun userDetails(){
        viewModelScope.launch(coroutineDispatcher){
            val isLogIn = viewModelScope.async{
                userPref.isLogIn()
            }

            val userEmail = viewModelScope.async{
                userPref.userEmail()
            }
            _userEmailLivedata.postValue(userEmail.await())
            _isLoggedInLivedata.postValue(isLogIn.await())
        }
    }
}