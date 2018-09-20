package com.getyourguide.efren.getyourguide.repositories

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.getyourguide.efren.getyourguide.model.Review
import com.getyourguide.efren.getyourguide.services.GygApi


class ReviewsDataSourceFactory(private var gygApi: GygApi, private var networkStatus: ReviewsDataSource.NetworkStatus) : DataSource.Factory<Int, Review>() {

    private val mutableLiveData: MutableLiveData<ReviewsDataSource>? = MutableLiveData()
    private lateinit var reviewsDataSource: ReviewsDataSource
    private lateinit var requestParameter: OptionalRequestParameter

    fun setRequestParameter(requestParameter: OptionalRequestParameter) {
        this.requestParameter = requestParameter
    }

    override fun create(): DataSource<Int, Review> {
        reviewsDataSource = ReviewsDataSource(gygApi, networkStatus)
        reviewsDataSource.setRequestParameters(requestParameter)
        mutableLiveData!!.postValue(reviewsDataSource)
        return reviewsDataSource
    }

    fun getUsersDataSourceLiveData(): MutableLiveData<ReviewsDataSource>? {
        return mutableLiveData
    }

}