package com.getyourguide.efren.getyourguide.repositories

import com.getyourguide.efren.getyourguide.model.Review
import com.getyourguide.efren.getyourguide.reviews.listReviews.ReviewsFragment.Direction
import com.getyourguide.efren.getyourguide.reviews.listReviews.ReviewsFragment.SortBy
import com.getyourguide.efren.getyourguide.services.GygApi
import com.getyourguide.efren.getyourguide.services.ReviewsResponse
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

//Example of how to unit test the ReviewsRepository
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class ReviewsRepositoryTest {

    @Mock
    lateinit var gygApi: GygApi

    @InjectMocks
    lateinit var reviewsRepository: ReviewsRepository

    @Test
    fun requestNewDataTestSuccess() {
        Mockito.`when`(gygApi.getReviews(20, 1, 0, SortBy.REVIEW_DATE.value, Direction.DESC.value)).thenReturn(mockSuccessData())
        reviewsRepository.requestNewData(0, SortBy.REVIEW_DATE.value, Direction.DESC.value) //ToDo check injection of DatasourceFactory
        Mockito.verify(reviewsRepository.data.value.orEmpty() != null)
        Mockito.verify(reviewsRepository.data.value.orEmpty().size == 20)
    }

    @Test
    fun requestNewDataTestError() {
        Mockito.`when`(gygApi.getReviews(20, 1, 0, SortBy.REVIEW_DATE.value, Direction.DESC.value)).thenReturn(mockErrorData())
        reviewsRepository.requestNewData(0, SortBy.REVIEW_DATE.value, Direction.DESC.value) //ToDo check injection of DatasourceFactory
        Mockito.verify(reviewsRepository.errorMessage.value != null )
    }

    private fun mockSuccessData(): Observable<Response<ReviewsResponse>> {
        var listReview: List<Review> = listOf(Review(1, 2.0, "title", "Message", "Author", true, "", "", "es", "solo", "Pepito", "Germany"))
        var reviewsResponse = ReviewsResponse(true, 10, listReview)
        var response: Response<ReviewsResponse> = Response.success(reviewsResponse)
        return Observable.fromArray(response)
    }

    private fun mockErrorData(): Observable<Response<ReviewsResponse>> {
        var response: Response<ReviewsResponse> = Response.error(400, null)
        return Observable.fromArray(response)
    }

}