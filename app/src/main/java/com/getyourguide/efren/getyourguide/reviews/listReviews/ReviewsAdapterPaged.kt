package com.getyourguide.efren.getyourguide.reviews.listReviews

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.getyourguide.efren.getyourguide.R
import com.getyourguide.efren.getyourguide.model.Review
import kotlinx.android.synthetic.main.item_review.view.*

class ReviewsAdapterPaged(val context: Context) : PagedListAdapter<Review, ReviewViewHolder>(ReviewDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ReviewViewHolder {
        return ReviewViewHolder(LayoutInflater.from(context).inflate(R.layout.item_review, parent, false))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(this.getItem(position)!!)
    }

    companion object {
        val ReviewDiffCallback = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.reviewId == newItem.reviewId
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class ReviewViewHolder(var view: View) : RecyclerView.ViewHolder(view) {

    private val title = view.tv_title
    private val rating = view.rb_rating
    private val message = view.tv_message
    private val nameAddress = view.tv_name_address

    fun bind(review: Review) {
        title.text = review.title
        rating.rating = review.rating!!.toFloat()
        message.text = review.message
        nameAddress.text = review.reviewerName + " - " + review.reviewerCountry + " - " + review.date
    }
}