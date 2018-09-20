package com.getyourguide.efren.getyourguide.reviews.newReview

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.getyourguide.efren.getyourguide.model.Review
import com.getyourguide.efren.getyourguide.repositories.ReviewsRepository
import javax.inject.Inject

class NewReviewViewModel @Inject constructor(val reviewsRepo: ReviewsRepository): ViewModel() {

    lateinit var data: LiveData<Review>
    val success: LiveData<Boolean> = reviewsRepo.success

    fun insertNewReview(review: Review) {
        reviewsRepo.addReview(review)
    }
}