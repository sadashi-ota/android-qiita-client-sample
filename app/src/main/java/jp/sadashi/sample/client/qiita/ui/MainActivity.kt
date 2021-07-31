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
import jp.sadashi.sample.client.qiita.infra.api.QiitaApiClientFactory
import kotlinx.serialization.ExperimentalSerializationApi

class MainActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()

    @ExperimentalSerializationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.do_api)
        button.setOnClickListener {
            QiitaApiClientFactory.create()
                .getTags(page = 1, per_page = 10, sort = "count")
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