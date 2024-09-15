package com.example.testapp.books


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.books.adapters.YearListAdapter
import com.example.testapp.books.callBacks.CallBacks
import com.example.testapp.databinding.ActivityBooksListBinding
import com.example.testapp.books.model.Book
import com.example.testapp.books.model.Year
import com.example.testapp.profile.ProfileActivity
import com.example.testapp.utils.shortToast
import com.example.testapp.utils.tabSync.TabbedListMediator
import com.example.testapp.wishlist.WishListActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class BooksListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBooksListBinding

    //ktx
    private val viewModel by viewModels<BooksViewModel>()
    private val callback: CallBacks by lazy {
        CallBacks(viewModel)
    }
    private var wishlists: List<Book>? = null
    private var booksList: List<Book>? = null
    private var year: MutableList<Year> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBooksListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startLoading()
        setObservers()

        binding.profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        binding.wishListBtn.setOnClickListener {
            startActivity(Intent(this, WishListActivity::class.java))
        }
    }

    private fun initTabLayout() {
        binding.tabLayout.visibility = View.VISIBLE
        for (category in year) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(category.name))
        }
    }

    private fun initRecycler() {
        binding.rv.adapter = YearListAdapter(callback,wishlists?: listOf(), year)
    }

    private fun initMediator() {
        TabbedListMediator(
            binding.rv,
            binding.tabLayout,
            year.indices.toList()
        ).attach()
    }

    private fun userLogInfo(email: String, isLogIn: Boolean) {
        Timber.d("UserEmail: $email \n and User is LoggedIn: $isLogIn")
        shortToast("UserEmail: $email \n and User is LoggedIn: $isLogIn")
    }

    fun startLoading(){
        binding.animation.playAnimation()
        binding.animation.visibility = View.VISIBLE
    }

    fun stopLoading(){
        binding.animation.pauseAnimation()
        binding.animation.visibility = View.GONE
    }


    private fun setObservers() {
        viewModel.booksListLiveData.observe(this) {
            it?.let {
                Timber.d("Inside SettingRV")
                booksList = it
                setCategoryList(it)
                setUiInRecyclerView(it, wishlists)
            }
        }
        viewModel.wishListsListLiveData.observe(this) {
            it?.let {
                Timber.d("Inside wishListRv")
                wishlists = it
                setUiInRecyclerView(booksList, it)
            }
        }

        viewModel.addToWishListLiveData.observe(this){
            it?.let {
                if (it){
                    //startLoading()
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        viewModel.refreshWishlist()
//                    },3000)
                    AlertDialog.Builder(this).setPositiveButton("GoToWishLists") { _, _ ->
                        startActivity(Intent(this, WishListActivity::class.java))
                    }.show()
                }else{
                    this.shortToast("Failed To Add in WishList")
                }
            }
        }

        viewModel.removeFromWishListLiveData.observe(this){
            it?.let {
                if (it){
                    this.shortToast("Removed To WishList")
                    //startLoading()
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        viewModel.refreshWishlist()
//                    },3000)
                }else{
                    this.shortToast("Failed To Remove from WishList")
                }
            }
        }

        viewModel.isLoggedInLivedata.observe(this){ isLogIn ->
            //userLogInfo(viewModel.userEmailLivedata.value?:"", isLogIn = isLogIn)
        }
        viewModel.userEmailLivedata.observe(this){

        }

    }

    private fun setCategoryList(books: List<Book>) {
        val yearList = mutableListOf<String>()
        for (i in 0..<books.size){
            val yearr = DateTimeFormatter.ISO_INSTANT
                .format(java.time.Instant.ofEpochSecond(books[i].publishedChapterDate?:1))
            if(!yearList.contains(yearr.substring(0,4))){
                yearList.add(yearr.substring(0,4))
            }
        }
        for(i in 0..<yearList.size){
            val list = mutableListOf<Book>()
            for(j in 0..<books.size){
                val bookYear = DateTimeFormatter.ISO_INSTANT
                    .format(java.time.Instant.ofEpochSecond(books[j].publishedChapterDate?:1))
                if(yearList[i]==bookYear.substring(0,4)){
                    list.add(books[j])
                }
            }
            year.add(Year(yearList[i],list))
        }
        year.sortBy {
            it.name.toInt()
        }

    }

    private fun setUiInRecyclerView(books: List<Book>?, wishlists: List<Book>?) {
        Timber.d("Inside SetRV")
        if(!books.isNullOrEmpty() && wishlists !=null ){
            stopLoading()
            initTabLayout()
            initRecycler()
            initMediator()
        }

    }
}