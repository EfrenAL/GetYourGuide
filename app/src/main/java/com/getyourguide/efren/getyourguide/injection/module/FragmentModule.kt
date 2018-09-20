package com.getyourguide.efren.getyourguide.injection.module

import com.getyourguide.efren.getyourguide.reviews.listReviews.ReviewsFragment
import com.getyourguide.efren.getyourguide.reviews.newReview.NewReviewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeReviewsFragment(): ReviewsFragment

    @ContributesAndroidInjector
    abstract fun contributeNewReviewFragment(): NewReviewFragment

}