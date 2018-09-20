package com.getyourguide.efren.getyourguide.reviews.newReview

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class NewReviewViewModelFactory @Inject constructor(private val newReviewViewModel: NewReviewViewModel) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewReviewViewModel::class.java)) {
            return newReviewViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}