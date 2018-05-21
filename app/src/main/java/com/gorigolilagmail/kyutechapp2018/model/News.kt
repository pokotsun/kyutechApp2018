package com.gorigolilagmail.kyutechapp2018.model

data class News(
        val id: Int,
        val infos: List<NewsInfo>,
        val attachmentInfos: List<attachmentInfo>
)

data class NewsInfo(
        val title: String,
        val content: String
)

data class attachmentInfo(
        val title: String,
        val linkName: String,
        val url: String
)