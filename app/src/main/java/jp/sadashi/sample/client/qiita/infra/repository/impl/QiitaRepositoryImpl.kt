package jp.sadashi.sample.client.qiita.infra.repository.impl

import android.content.Context
import io.reactivex.Single
import jp.sadashi.sample.client.qiita.infra.api.QiitaApiClientFactory
import jp.sadashi.sample.client.qiita.infra.json.Tag
import jp.sadashi.sample.client.qiita.infra.preference.QiitaCachePreference
import jp.sadashi.sample.client.qiita.infra.repository.QiitaRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class QiitaRepositoryImpl(
    private val context: Context
) : QiitaRepository {
    override fun getTags(page: Int, perPage: Int, sort: String): Single<List<Tag>> {
        return QiitaApiClientFactory.create()
            .getTags(page, perPage, sort)
            .doAfterSuccess {
                it.forEach { tag ->
                    QiitaCachePreference(context)
                        .put(tag.id.toInt(), Json.encodeToString(tag))
                }
            }
    }

    override fun getTag(id: Int): Single<Tag> {
        val tagStr = QiitaCachePreference(context).get(id) ?: run {
            return QiitaApiClientFactory.create().getTag(id)
        }

        return Single.create { Json.decodeFromString(tagStr) }
    }
}