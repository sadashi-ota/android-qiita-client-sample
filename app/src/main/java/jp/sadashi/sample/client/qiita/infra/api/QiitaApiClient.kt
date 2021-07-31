package jp.sadashi.sample.client.qiita.infra.api

import io.reactivex.Single
import jp.sadashi.sample.client.qiita.infra.json.Tag
import retrofit2.http.GET
import retrofit2.http.Query

interface QiitaApiClient {
    @GET("/api/v2/tags")
    fun getTags(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("sort") sort: String,
    ): Single<List<Tag>>
}