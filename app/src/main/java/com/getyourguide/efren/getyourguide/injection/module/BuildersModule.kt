package com.getyourguide.efren.getyourguide.injection.module

import com.getyourguide.efren.getyourguide.reviews.ReviewsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeReviewsActivity(): ReviewsActivity
}