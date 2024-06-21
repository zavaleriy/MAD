package com.example.mad.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.R
import com.example.mad.models.Quote
import com.squareup.picasso.Picasso

class QuoteAdapter :
        RecyclerView.Adapter<QuoteAdapter.ViewHolder>() {

    private var quotes: List<Quote> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(quotes: List<Quote>) {
        this.quotes = quotes
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewQuote: TextView
        val textViewQuoteDesc: TextView
        val imageViewQuote: ImageView

        init {
            textViewQuote = view.findViewById(R.id.textViewQuote)
            textViewQuoteDesc = view.findViewById(R.id.textViewQuoteDesc)
            imageViewQuote = view.findViewById(R.id.imageViewQuote)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quote, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quote = quotes[position]
        holder.textViewQuote.text = quote.title
        holder.textViewQuoteDesc.text = quote.description
        Picasso.get()
            .load(quote.image)
            .into(holder.imageViewQuote)

    }

    override fun getItemCount(): Int = quotes.size

}