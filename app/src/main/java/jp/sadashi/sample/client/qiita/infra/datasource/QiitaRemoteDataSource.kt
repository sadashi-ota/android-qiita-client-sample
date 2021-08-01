package jp.sadashi.sample.client.qiita.infra.datasource

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jp.sadashi.sample.client.qiita.infra.api.QiitaApiClientFactory
import jp.sadashi.sample.client.qiita.infra.json.Tag
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class QiitaRemoteDataSource {
    private val service = QiitaApiClientFactory.create()

    fun getTags(page: Int, perPage: Int, sort: String): Single<List<Tag>> {
        return service.getTags(page, perPage, sort)
            .subscribeOn(Schedulers.io())
    }

    fun getTag(id: String): Single<Tag> {
        return service.getTag(id)
            .subscribeOn(Schedulers.io())
    }
}
