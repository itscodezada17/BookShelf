package com.example.testapp.books.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.books.callBacks.CallBacks
import com.example.testapp.books.model.Book
import com.example.testapp.databinding.BookItemBinding
import timber.log.Timber

class BookListAdapter(
    private val booksList: List<Book>,
    private val callBacks: CallBacks,
    private val wishlists: List<Book>
): RecyclerView.Adapter<UserViewHolder>()  {
    private lateinit var binding: BookItemBinding




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = BookItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }


    // binds the list items to a view
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val itemsView = booksList[position]
        holder.bind(itemsView, callBacks, wishlists)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return booksList.size
    }

}
// Holds the views for adding it to image and text
class UserViewHolder(private val binding: BookItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(book: Book, callBacks: CallBacks, wishlists: List<Book>){
        binding.book = book
        binding.callBack = callBacks
        Timber.d("The wishlist size is ${wishlists.size}")

        if (isExists(book, wishlists)){
            binding.wishList.setImageDrawable(ContextCompat.getDrawable(binding.wishList.context, R.drawable.ic_filled_heart))
        }else{
            binding.wishList.setImageDrawable(ContextCompat.getDrawable(binding.wishList.context, R.drawable.ic_empty_heart))
        }

        binding.wishList.setOnClickListener {
            if (isExists(book, wishlists)){
                callBacks.removeFromWishList(book)
                binding.wishList.setImageDrawable(ContextCompat.getDrawable(binding.wishList.context, R.drawable.ic_empty_heart))
            }else{
                callBacks.addToWishList(book)
                binding.wishList.setImageDrawable(ContextCompat.getDrawable(binding.wishList.context, R.drawable.ic_filled_heart))
            }
        }
    }

    private fun isExists(book: Book, wishlists: List<Book>): Boolean{
        for (books in wishlists){
            if(books.id == book.id && books.title == book.title){
                return true
            }
        }
        return false
    }
}