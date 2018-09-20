package com.getyourguide.efren.getyourguide.reviews.listReviews

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class ReviewsViewModelFactory @Inject constructor(private val reviewsViewModel: ReviewsViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewsViewModel::class.java)) {
            return reviewsViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}