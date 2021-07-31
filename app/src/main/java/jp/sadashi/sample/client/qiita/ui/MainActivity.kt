package jp.sadashi.sample.client.qiita.ui

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import jp.sadashi.sample.client.qiita.R
import jp.sadashi.sample.client.qiita.infra.json.Tag
import jp.sadashi.sample.client.qiita.infra.repository.QiitaRepository
import jp.sadashi.sample.client.qiita.infra.repository.impl.QiitaRepositoryImpl
import kotlinx.serialization.ExperimentalSerializationApi

class MainActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()

    private lateinit var repository: QiitaRepository
    private var tags: List<Tag> = emptyList()

    @ExperimentalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = QiitaRepositoryImpl(this)

        findViewById<Button>(R.id.get_tags).setOnClickListener {
            repository.getTags(1, 10, "sort")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tags = it
                    Log.d("sample", it.toString())
                }, {
                    Log.d("sample", it.printStackTrace().toString())
                }).addTo(disposable)
        }

        findViewById<Button>(R.id.get_tag).setOnClickListener {
            if (tags.isEmpty()) return@setOnClickListener

            repository.getTag(tags[0].id.toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("sample", it.toString())
                }, {
                    Log.d("sample", it.printStackTrace().toString())
                }).addTo(disposable)
        }
    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
    }
}