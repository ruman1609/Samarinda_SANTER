package com.rudyrachman16.samarindasanter.core.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(

    @field:SerializedName("preview")
    val preview: String? = null,

    @field:SerializedName("id_operator")
    val idOperator: Int? = null,

    @field:SerializedName("id_kategori")
    val idKategori: Int? = null,

    @field:SerializedName("sumber")
    val sumber: String? = null,

    @field:SerializedName("komentar")
    val komentar: Int? = null,

    @field:SerializedName("show")
    val show: Int? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("uuid")
    val uuid: String? = null,

    @field:SerializedName("deleted_at")
    val deletedAt: String? = null,

    @field:SerializedName("view")
    val view: Int? = null,

    @field:SerializedName("sumber_foto")
    val sumberFoto: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("judul")
    val judul: String? = null,

    @field:SerializedName("headline")
    val headline: Int? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("isi")
    val isi: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
) : Parcelable
