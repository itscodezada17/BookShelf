package com.example.testapp.books.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.books.callBacks.CallBacks
import com.example.testapp.books.model.Book
import com.example.testapp.books.model.Year
import com.example.testapp.databinding.CategoryItemBinding

class YearListAdapter(
    private val callBacks: CallBacks,
    private val wishList: List<Book>,
    private val yearList: List<Year>
): RecyclerView.Adapter<CategoryViewHolder>() {
    private lateinit var binding: CategoryItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(callBacks, wishList, yearList[position])
    }

    override fun getItemCount(): Int {
        return yearList.size
    }
}

// Holds the views for adding it to image and text
class CategoryViewHolder(private val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(callBacks: CallBacks, wishList: List<Book>, year: Year){
        binding.rv.adapter = BookListAdapter(booksList = year.listOfItems, callBacks, wishlists = wishList)
    }

}