package com.gorigolilagmail.kyutechapp2018.model

data class ApiRequest<T>(
        val count: Int,
        val next: String?,
        val previous: String?,
        val results: List<T>
)