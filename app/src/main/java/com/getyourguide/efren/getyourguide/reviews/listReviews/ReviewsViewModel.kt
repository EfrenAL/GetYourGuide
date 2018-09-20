package com.getyourguide.efren.getyourguide.reviews.listReviews

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.getyourguide.efren.getyourguide.model.Review
import com.getyourguide.efren.getyourguide.repositories.ReviewsRepository
import javax.inject.Inject


class ReviewsViewModel @Inject constructor(val reviewsRepo: ReviewsRepository) : ViewModel() {

    val loadingVisibility: LiveData<Int>
    val errorMessage: LiveData<Int>
    var data: LiveData<PagedList<Review>>

    init {
        loadingVisibility = reviewsRepo.loadingVisibility
        errorMessage = reviewsRepo.errorMessage
        data = reviewsRepo.data
    }

    fun loadNewData(rating: Int,  sortBy: String, direction: String){
        reviewsRepo.requestNewData(rating, sortBy, direction)
    }
}