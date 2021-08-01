package jp.sadashi.sample.client.qiita.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import jp.sadashi.sample.client.qiita.R
import jp.sadashi.sample.client.qiita.infra.datasource.QiitaLocalDataSource
import jp.sadashi.sample.client.qiita.infra.datasource.QiitaRemoteDataSource
import jp.sadashi.sample.client.qiita.infra.json.Tag
import jp.sadashi.sample.client.qiita.infra.repository.QiitaRepository
import jp.sadashi.sample.client.qiita.infra.repository.impl.QiitaRepositoryImpl
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MainActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()

    private val repository: QiitaRepository = QiitaRepositoryImpl(
        QiitaRemoteDataSource(),
        QiitaLocalDataSource(this)
    )

    private var tags: List<Tag> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.load_tag_list).setOnClickListener {
            repository.getTags(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tags = it
                    Log.d("sample", it.toString())
                }, {
                    Log.d("sample", it.printStackTrace().toString())
                }).addTo(disposable)
        }

        findViewById<Button>(R.id.get_tag).setOnClickListener {
            val id = if (tags.isEmpty()) {
                "Android"
            } else {
                tags[0].id
            }

            repository.getTag(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("sample", it.toString())
                }, {
                    Log.d("sample", it.printStackTrace().toString())
                }).addTo(disposable)
        }

        findViewById<Button>(R.id.clear).setOnClickListener {
            repository.clearCache()
                .subscribe {
                    Log.d("sample", "Cache clear!")
                }.addTo(disposable)
        }
    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
    }
}