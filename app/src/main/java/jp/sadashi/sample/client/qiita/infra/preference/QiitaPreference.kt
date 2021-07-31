package jp.sadashi.sample.client.qiita.infra.preference

import android.content.Context

class QiitaCachePreference(context: Context) {
    companion object {
        private const val PREF_NAME = "QiitaCache"
    }

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun put(id: Int, tag: String) {
        sharedPreferences.edit().putString(id.toString(), tag).apply()
    }

    fun get(id: Int): String? = sharedPreferences.getString(id.toString(), "")

    fun delete(id: Int) = sharedPreferences.edit().remove(id.toString()).apply()
}