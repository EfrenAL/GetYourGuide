package com.getyourguide.efren.getyourguide.model

import com.squareup.moshi.Json

data class Review(@field:Json(name = "review_id") val reviewId: Int?,
                  val rating: Double?,
                  val title: String?,
                  val message: String?,
                  val author: String?,
                  val foreignLanguage: Boolean?,
                  val date: String?,
                  @Transient val date_unformatted: String?,
                  val languageCode: String?,
                  @field:Json(name = "traveler_type") val travelerType: String?,
                  val reviewerName: String?,
                  val reviewerCountry: String?
)