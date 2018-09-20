package com.getyourguide.efren.getyourguide.reviews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.Menu
import android.view.MenuItem
import android.view.animation.OvershootInterpolator
import com.getyourguide.efren.getyourguide.R
import com.getyourguide.efren.getyourguide.reviews.listReviews.ReviewsFragment
import com.getyourguide.efren.getyourguide.reviews.newReview.NewReviewFragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_reviews.*
import javax.inject.Inject

class ReviewsActivity : AppCompatActivity(), HasSupportFragmentInjector, NewReviewFragment.NavigateTo {

    interface ISortReviews {
        fun sortReviewsHighestRating()
        fun sortReviewsLowestRating()
        fun sortReviewsNewestDate()
        fun sortReviewsOldestDate()
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private var reviewsFragment: ReviewsFragment = ReviewsFragment()
    private var newReviewsFragment: NewReviewFragment = NewReviewFragment()
    private var sortReviews: ISortReviews = reviewsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)

        AndroidInjection.inject(this)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fr_main, reviewsFragment, null)
                .commit()

        initUI()
    }

    private fun initUI() {
        fab_add_review.setOnClickListener {
            if(reviewsFragment.isVisible){
                showFragment(newReviewsFragment, 135f)
            } else {
                showFragment(reviewsFragment, 000f)
            }
        }
    }

    private fun showFragment(fragment: Fragment, rotationValue: Float) {
        ViewCompat.animate(fab_add_review)
                .rotation(rotationValue)
                .withLayer()
                .setDuration(300)
                .setInterpolator(OvershootInterpolator(10.0F))
                .start()

        supportFragmentManager.beginTransaction()
                .replace(R.id.fr_main, fragment, null)
                .commit()
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.itemId
        when (id) {
            R.id.action_sort_highest_rating -> sortReviews.sortReviewsHighestRating()
            R.id.action_sort_lowest_rating -> sortReviews.sortReviewsLowestRating()
            R.id.action_sort_newest_date -> sortReviews.sortReviewsNewestDate()
            R.id.action_sort_oldest_date -> sortReviews.sortReviewsOldestDate()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun navigateToReviewsFragment() {
        showFragment(reviewsFragment, 000f)
    }
}
