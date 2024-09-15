package com.example.testapp.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.books.callBacks.CallBacks
import com.example.testapp.books.model.Book
import com.example.testapp.databinding.BookItemBinding
import com.example.testapp.databinding.WishlistItemBinding
import com.example.testapp.wishlist.callBacks.WishListCallBacks


class WishListAdapter(
    private val booksList: List<Book>,
    private val callBacks: WishListCallBacks
): RecyclerView.Adapter<UserViewHolder>()  {
    private lateinit var binding: WishlistItemBinding




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = WishlistItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }


    // binds the list items to a view
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val itemsView = booksList[position]
        holder.bind(itemsView, callBacks)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return booksList.size
    }

}
// Holds the views for adding it to image and text
class UserViewHolder(private val binding: WishlistItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(book: Book, callBacks: WishListCallBacks){
        binding.book = book
        binding.callBack = callBacks
    }
}