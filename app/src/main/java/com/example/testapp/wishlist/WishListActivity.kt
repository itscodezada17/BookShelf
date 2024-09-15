package com.example.testapp.wishlist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.books.BooksListActivity
import com.example.testapp.books.model.Book
import com.example.testapp.databinding.ActivityWishListBinding
import com.example.testapp.utils.shortToast
import com.example.testapp.wishlist.callBacks.WishListCallBacks
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class WishListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWishListBinding

    //ktx
    private val viewModel by viewModels<WishListViewModel>()
    private val callback: WishListCallBacks by lazy {
        WishListCallBacks(viewModel)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWishListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startLoading()
        setObservers()
        setOnBackListeners()
    }

    fun startLoading(){
        binding.animation.playAnimation()
        binding.animation.visibility = View.VISIBLE
    }

    fun stopLoading(){
        binding.animation.pauseAnimation()
        binding.animation.visibility = View.GONE
    }

    private fun setOnBackListeners() {
        onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            /* override back pressing */
            override fun handleOnBackPressed() {
                val intent = Intent(this@WishListActivity, BooksListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        })
    }


    private fun setObservers() {
        viewModel.wishListsListLiveData.observe(this) {
            it?.let {
                Timber.d("Inside SettingRV")
                setUiInRecyclerView(it)
            }
        }

        viewModel.removeFromWishListLiveData.observe(this){
            it?.let {
                if (it){
                    this.shortToast("Removed To WishList")
                    startLoading()
                    viewModel.refreshWishList()
                }else{
                    this.shortToast("Failed To Remove from WishList")
                }
            }
        }

    }

    private fun setUiInRecyclerView(books: List<Book>?) {
        Timber.d("Inside SetRV")
        Handler(Looper.getMainLooper()).postDelayed({
            stopLoading()
            binding.rv.visibility = View.VISIBLE
        },1000)
        books?.let { bookList ->
            if (bookList.isEmpty()){
                binding.rv.visibility = View.GONE
                binding.noItem.visibility = View.VISIBLE
            }
            binding.rv.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = WishListAdapter(bookList, callback)
                this.setHasFixedSize(true)
            }
        }
    }
}