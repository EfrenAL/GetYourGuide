package com.getyourguide.efren.getyourguide.injection.module

import android.arch.lifecycle.ViewModelProvider
import com.getyourguide.efren.getyourguide.repositories.ReviewsRepository
import com.getyourguide.efren.getyourguide.reviews.listReviews.ReviewsViewModelFactory
import com.getyourguide.efren.getyourguide.reviews.newReview.NewReviewViewModelFactory
import com.getyourguide.efren.getyourguide.services.BASE_URL
import com.getyourguide.efren.getyourguide.services.GygApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    /**
     * Provides the MyApo service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideGygApi(retrofit: Retrofit): GygApi {
        return retrofit.create(GygApi::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    /**
     * Provides user repo.
     * @return user repository
     */
    @Provides
    @JvmStatic
    @Singleton
    internal fun provideReviewsRepository(gygApi: GygApi): ReviewsRepository {
        return ReviewsRepository(gygApi)
    }

    @Provides
    fun provideReviewsViewModelFactory(factory: ReviewsViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    fun provideNewReviewViewModelFactory(factory: NewReviewViewModelFactory): ViewModelProvider.Factory = factory

}