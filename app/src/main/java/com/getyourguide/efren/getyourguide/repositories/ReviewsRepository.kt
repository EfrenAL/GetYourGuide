package com.getyourguide.efren.getyourguide.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.os.Handler
import com.getyourguide.efren.getyourguide.model.Review
import com.getyourguide.efren.getyourguide.reviews.listReviews.ReviewsFragment.SortBy
import com.getyourguide.efren.getyourguide.reviews.listReviews.ReviewsFragment.Direction
import com.getyourguide.efren.getyourguide.services.GygApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewsRepository @Inject constructor(private val gygApi: GygApi) : ReviewsDataSource.NetworkStatus {

    var data: LiveData<PagedList<Review>> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val success: MutableLiveData<Boolean> = MutableLiveData()

    private var dataSource: ReviewsDataSourceFactory

    //Default request values
    private var optionalRequestParameter = OptionalRequestParameter(0, SortBy.REVIEW_DATE.value, Direction.DESC.value)

    private val pagedListConfig: PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()

    init {
        dataSource = ReviewsDataSourceFactory(gygApi, this as (ReviewsDataSource.NetworkStatus))
        dataSource.setRequestParameter(optionalRequestParameter)
        data = LivePagedListBuilder(dataSource, pagedListConfig).build()
    }

    @Suppress("UNUSED_PARAMETER")
    fun addReview(review: Review) {
        //ToDo Call to api to post data

        //For now we just simulate a delay
        val handler = Handler()
        handler.postDelayed({
            success.postValue(true)
        }, 1000)
    }

    fun requestNewData(rating: Int, sortBy: String, direction: String) {
        dataSource.getUsersDataSourceLiveData()!!.value!!.invalidate()
        dataSource.setRequestParameter(OptionalRequestParameter(rating, sortBy, direction))
        dataSource.create()
    }

    override fun initialLoadStatus(status: Int) {
        loadingVisibility.postValue(status)
    }
    override fun errorStatus(error: Int) {
        errorMessage.postValue(error)
    }
}