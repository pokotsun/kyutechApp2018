package com.gorigolilagmail.kyutechapp2018.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log

data class News(
        val id: Int,
        val infos: List<NewsInfo>,
        val attachmentInfos: List<AttachmentInfo>
): Parcelable {
    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.run {
            writeInt(id)
            writeTypedList(infos)
            writeTypedList(attachmentInfos)
        }
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<News> = object: Parcelable.Creator<News> {
            override fun createFromParcel(source: Parcel): News = source.run {
                val pk: Int = readInt()
                val newsInfos = mutableListOf<NewsInfo>().apply { readTypedList(this, NewsInfo.CREATOR) }
                val attachmentInfos = mutableListOf<AttachmentInfo>().apply { readTypedList(this, AttachmentInfo.CREATOR) }
                News(pk, newsInfos, attachmentInfos)
            }

            override fun newArray(size: Int): Array<News?> = arrayOfNulls(size)
        }
    }
}

data class NewsInfo(
        val title: String?,
        val content: String?
): Parcelable {
    override fun describeContents(): Int = 0
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.run {
            writeString(title)
            writeString(content)
        }
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<NewsInfo> = object: Parcelable.Creator<NewsInfo> {
            override fun createFromParcel(source: Parcel): NewsInfo = source.run {
                NewsInfo(readString(), readString())
            }

            override fun newArray(size: Int): Array<NewsInfo?> = arrayOfNulls(size)
        }
    }
}

data class AttachmentInfo(
        val title: String?,
        val linkName: String?,
        val url: String?
): Parcelable {
    override fun describeContents(): Int = 0
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.run {
            writeString(title)
            writeString(linkName)
            writeString(url)
        }
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AttachmentInfo> = object: Parcelable.Creator<AttachmentInfo> {
            override fun createFromParcel(source: Parcel): AttachmentInfo = source.run {
                AttachmentInfo(readString(), readString(), readString())
            }

            override fun newArray(size: Int): Array<AttachmentInfo?> = arrayOfNulls(size)
        }
    }
}