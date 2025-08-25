package com.abcd.pemantauankesehatananak.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class YoutubeResultModel(

    @SerializedName("kind")
    var kind: String? = null,

    @SerializedName("etag")
    var etag: String? = null,

    @SerializedName("items")
    var items: ArrayList<Items>? = null,

)

data class Items(
    @SerializedName("kind")
    var kind: String? = null,

    @SerializedName("etag")
    var etag: String? = null,

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("snippet")
    var snippet: Snippet? = null,
)

data class Snippet(
    @SerializedName("publishedAt")
    var publishedAt: String? = null,

    @SerializedName("channelId")
    var channelId: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("channelTitle")
    var channelTitle: String? = null,

)