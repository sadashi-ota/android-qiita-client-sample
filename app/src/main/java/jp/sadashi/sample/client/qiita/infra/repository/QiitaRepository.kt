package jp.sadashi.sample.client.qiita.infra.repository

import io.reactivex.Completable
import io.reactivex.Single
import jp.sadashi.sample.client.qiita.infra.json.Tag

interface QiitaRepository {
    fun getTags(page: Int, perPage: Int = 10, sort: String = "count"): Single<List<Tag>>
    fun getTag(id: String): Single<Tag>
    fun clearCache(): Completable
}