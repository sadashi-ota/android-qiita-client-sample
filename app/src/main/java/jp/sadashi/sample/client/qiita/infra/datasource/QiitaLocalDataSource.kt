package jp.sadashi.sample.client.qiita.infra.datasource

import android.content.Context
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import jp.sadashi.sample.client.qiita.infra.json.Tag
import jp.sadashi.sample.client.qiita.infra.preference.QiitaCachePreference
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class QiitaLocalDataSource(private val context: Context) {

    fun getTag(id: String): Maybe<Tag> {
        return Maybe.create {
            val tagStr = QiitaCachePreference(context).get(id) ?: run {
                it.onComplete()
                return@create
            }
            it.onSuccess(Json.decodeFromString(tagStr))
        }
    }

    fun updateCache(tag: Tag): Completable {
        return Completable.create {
            QiitaCachePreference(context).put(tag.id, Json.encodeToString(tag))
            it.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    fun clearCache(): Completable {
        return Completable.create {
            QiitaCachePreference(context).clear()
            it.onComplete()
        }.subscribeOn(Schedulers.io())
    }
}