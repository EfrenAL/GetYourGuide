package com.getyourguide.efren.getyourguide.reviews.listReviews

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.getyourguide.efren.getyourguide.R
import com.getyourguide.efren.getyourguide.model.Review
import com.getyourguide.efren.getyourguide.reviews.ReviewsActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_reviews.*
import javax.inject.Inject


class ReviewsFragment : Fragment(), ReviewsActivity.ISortReviews {

    @Inject
    lateinit var reviewsViewModelFactory: ReviewsViewModelFactory
    private lateinit var viewModel: ReviewsViewModel

    private lateinit var reviewsAdapterPaged: ReviewsAdapterPaged
    private var errorSnackbar: Snackbar? = null

    enum class Direction(val value: String) {
        DESC("desc"), ASC("asc")
    }

    enum class SortBy(val value: String) {
        RATING("rating"), REVIEW_DATE("date_of_review")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //configureDagger
        AndroidSupportInjection.inject(this)
        //Init adapter
        initAdapter()
        //configureViewModel
        setupViewModel()
        //Initialize UI
        initUi()
    }

    private fun initUi() {
        srl_reviews_refresh.setColorSchemeResources(R.color.colorAccent)
        srl_reviews_refresh.setOnRefreshListener { viewModel.loadNewData(0, SortBy.REVIEW_DATE.value, Direction.DESC.value) }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, reviewsViewModelFactory).get(ReviewsViewModel::class.java)
        viewModel.loadingVisibility.observe(this, Observer { srl_reviews_refresh.isRefreshing = it!! != View.GONE })
        viewModel.data.observe(this, Observer<PagedList<Review>> { reviewsAdapterPaged.submitList(it) })
        viewModel.errorMessage.observe(this, Observer { displayError(it) })
    }

    private fun displayError(error: Int?) {
        if (error != null) {
            errorSnackbar = Snackbar.make(ll_reviews, error, Snackbar.LENGTH_SHORT)
            errorSnackbar?.show()
        } else {
            errorSnackbar?.dismiss()
        }
    }

    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        reviewsAdapterPaged = ReviewsAdapterPaged(this.context!!)

        rv_review_list.layoutManager = linearLayoutManager
        rv_review_list.adapter = reviewsAdapterPaged
    }

    override fun sortReviewsHighestRating() {
        viewModel.loadNewData(0, SortBy.RATING.value, Direction.DESC.value)
    }

    override fun sortReviewsLowestRating() {
        viewModel.loadNewData(0, SortBy.RATING.value, Direction.ASC.value)
    }

    override fun sortReviewsNewestDate() {
        viewModel.loadNewData(0, SortBy.REVIEW_DATE.value, Direction.DESC.value)
    }

    override fun sortReviewsOldestDate() {
        viewModel.loadNewData(0, SortBy.REVIEW_DATE.value, Direction.ASC.value)
    }

}