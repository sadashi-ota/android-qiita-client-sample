package jp.sadashi.sample.client.qiita.infra.json

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val followers_count: Int,
    val icon_url: String,
    val id: String,
    val items_count: Int
)
