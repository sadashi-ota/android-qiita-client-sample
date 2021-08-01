package jp.sadashi.sample.client.qiita.infra.repository.impl

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Single
import jp.sadashi.sample.client.qiita.infra.datasource.QiitaLocalDataSource
import jp.sadashi.sample.client.qiita.infra.datasource.QiitaRemoteDataSource
import jp.sadashi.sample.client.qiita.infra.json.Tag
import jp.sadashi.sample.client.qiita.infra.repository.QiitaRepository
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class QiitaRepositoryImpl(
    private val remoteDataSource: QiitaRemoteDataSource,
    private val localDataSource: QiitaLocalDataSource
) : QiitaRepository {

    @SuppressLint("CheckResult")
    override fun getTags(page: Int, perPage: Int, sort: String): Single<List<Tag>> {
        return remoteDataSource.getTags(page, perPage, sort)
            .doAfterSuccess {
                it.forEach { tag ->
                    localDataSource.updateCache(tag).blockingGet()
                }
            }
    }

    @SuppressLint("CheckResult")
    override fun getTag(id: String): Single<Tag> {
        return localDataSource.getTag(id)
            .switchIfEmpty(
                remoteDataSource.getTag(id)
                    .doAfterSuccess { tag ->
                        localDataSource.updateCache(tag).blockingGet()
                    }
            )
    }

    override fun clearCache(): Completable {
        return localDataSource.clearCache()
    }

}