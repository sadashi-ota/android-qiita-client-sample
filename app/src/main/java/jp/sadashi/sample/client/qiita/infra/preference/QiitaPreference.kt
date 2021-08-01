package jp.sadashi.sample.client.qiita.infra.preference

import android.content.Context

class QiitaCachePreference(context: Context) {
    companion object {
        private const val PREF_NAME = "QiitaCache"
    }

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun put(id: String, tag: String) {
        sharedPreferences.edit().putString(id, tag).apply()
    }

    fun get(id: String): String? = sharedPreferences.getString(id, null)

    fun delete(id: String) = sharedPreferences.edit().remove(id).apply()

    fun clear() = sharedPreferences.edit().clear().apply()
}