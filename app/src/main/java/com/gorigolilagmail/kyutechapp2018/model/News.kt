package com.gorigolilagmail.kyutechapp2018.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import java.util.HashMap

data class News(
        val id: Int,
        val newsHeadingCode: Int,
        val sourceUrl: String,
        val infos: List<NewsInfo>,
        val attachmentInfos: List<AttachmentInfo>
): Parcelable {
    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int)  = dest.run {
        writeInt(id)
        writeInt(newsHeadingCode)
        writeString(sourceUrl)
        writeTypedList(infos)
        writeTypedList(attachmentInfos)
    }


    // 一覧で表示する情報を渡す (インターンについて   2018/12/11) など
    fun getTitleInfos(): HashMap<String, String> {
        var mainTitle = ""
        var subTitle = ""
        when(newsHeadingCode) {
            357 -> { // おしらせ(学生向け)
                mainTitle = infos.filter { it.title == "タイトル" }.map {it.content }.first()
                subTitle = infos.filter { it.title == "日付" }.map { it.content }.first()
            }
            391 -> { // 時間割・講義室変更
                mainTitle = infos.filter { it.title == "科目名" || it.title == "時限" }.map { it.content }.joinToString(separator = " ")
                subTitle = infos.filter { it.title == "日付" }.map { it.content }.first()
            }
            361 -> { // 休講通知
                mainTitle = infos.filter { it.title == "休講科目" || it.title == "時限" }.map { it.content }.joinToString(separator = " ")
                subTitle = infos.filter { it.title == "日付" }.map { it.content }.first()

            }
            363 -> { // 補講通知
                mainTitle = infos.filter { it.title == "補講科目" || it.title == "時限" }.map { it.content }.joinToString(separator = " ")
                subTitle = infos.filter { it.title == "日付" }.map { it.content }.first()

            }
            393 -> { // 学生呼出
                mainTitle = infos.filter { it.title == "対象学科等" }.map {it.content }.first()

            }
            364 -> { // 授業調整・期末試験
                mainTitle = infos.filter { it.title == "タイトル" }.map {it.content }.first()
                subTitle = infos.filter { it.title == "日付" }.map { it.content }.first()
            }
            373 -> { // 各種手続き
                mainTitle = infos.filter { it.title == "件名" }.map {it.content }.first()

            }
            367 -> { // 奨学金
                mainTitle = infos.filter { it.title == "件名" }.map {it.content }.first()
                subTitle = infos.filter { it.title == "期日" }.map { it.content }.first()

            }
            379 -> { // 集中講義
                mainTitle = infos.filter { it.title == "タイトル" }.map {it.content }.first()
                subTitle = infos.filter { it.title == "日付" }.map { it.content }.first()

            }
            372 -> { // 留学・国際関連
                mainTitle = infos.filter { it.title == "タイトル" }.map {it.content }.first()
                subTitle = infos.filter { it.title == "日付" }.map { it.content }.first()

            }
            368 -> { // 学部生情報
                mainTitle = infos.filter { it.title == "件名" }.map {it.content }.first()

            }
            370 -> { // 大学院生情報
                mainTitle = infos.filter { it.title == "件名" }.map {it.content }.first()
            }
        }
        return hashMapOf("mainTitle" to mainTitle, "subTitle" to subTitle)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<News> = object: Parcelable.Creator<News> {
            override fun createFromParcel(source: Parcel): News = source.run {
                News(readInt(), readInt(), readString(),
                        mutableListOf<NewsInfo>().apply { readTypedList(this, NewsInfo.CREATOR)},
                        mutableListOf<AttachmentInfo>().apply {readTypedList(this, AttachmentInfo.CREATOR)}
                )
            }

            override fun newArray(size: Int): Array<News?> = arrayOfNulls(size)
        }

        fun createDummy(): News =
                News(id = 1223, newsHeadingCode = 333, sourceUrl = "", infos = listOf(NewsInfo(title="あいうえお", content="かきくけこ")), attachmentInfos =  listOf())
    }
}

data class NewsInfo(
        val title: String,
        val content: String
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
        val title: String,
        val linkName: String,
        val url: String
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