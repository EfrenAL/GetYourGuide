package com.getyourguide.efren.getyourguide.services

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import com.squareup.moshi.Json
import com.getyourguide.efren.getyourguide.model.Review
import retrofit2.http.Headers
import retrofit2.http.Query

interface GygApi {

    @Headers("User-Agent: GetYourGuide")

    @GET("/berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json?")
    fun getReviews(@Query("count") count: Int,
                   @Query("page") page: Int,
                   @Query("rating") rating: Int?,
                   @Query("sortBy") sortBy: String?,
                   @Query("direction") direction: String?): Observable<Response<ReviewsResponse>>
}

const val BASE_URL: String = "https://www.getyourguide.com"

data class ReviewsResponse(
        var status: Boolean,
        @field:Json(name = "total_reviews_comments") var totalReviewsComments: Int,
        var data: List<Review>)


