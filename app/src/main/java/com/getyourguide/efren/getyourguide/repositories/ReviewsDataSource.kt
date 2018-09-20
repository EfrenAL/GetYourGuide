package com.getyourguide.efren.getyourguide.repositories

import android.arch.paging.PageKeyedDataSource
import android.view.View
import com.getyourguide.efren.getyourguide.R
import com.getyourguide.efren.getyourguide.model.Review
import com.getyourguide.efren.getyourguide.services.GygApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ReviewsDataSource constructor(private val gygApi: GygApi, var networkStatus: NetworkStatus) : PageKeyedDataSource<Int, Review>() {

    private lateinit var requestParameter: OptionalRequestParameter
    private lateinit var subscription: Disposable

    interface NetworkStatus {
        fun initialLoadStatus(status: Int)
        fun errorStatus(error: Int)
    }

    fun setRequestParameters(requestParameter: OptionalRequestParameter) {
        this.requestParameter = requestParameter
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Review>) {
        subscription = gygApi.getReviews(params.requestedLoadSize, 0, requestParameter.rating, requestParameter.sortBy, requestParameter.direction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { networkStatus.initialLoadStatus(View.VISIBLE) }
                .doOnTerminate { networkStatus.initialLoadStatus(View.GONE) }
                .subscribe(
                        { callback.onResult(it.body()!!.data, null, 1) },
                        { onRetrieveError() }
                )
    }

    private fun onRetrieveError() {
        networkStatus.errorStatus(R.string.error_loading_data)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Review>) {

        subscription = gygApi.getReviews(params.requestedLoadSize, params.key, requestParameter.rating, requestParameter.sortBy, requestParameter.direction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            val nextKey: Int = (if (params.key == (it.body()!!.totalReviewsComments / 20)) -1 else params.key + 1).toInt()
                            if (nextKey != -1)
                                callback.onResult(it.body()!!.data, nextKey)
                        },
                        { onRetrieveError() }
                )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Review>) {}
}

data class OptionalRequestParameter(var rating: Int, var sortBy: String, var direction: String)