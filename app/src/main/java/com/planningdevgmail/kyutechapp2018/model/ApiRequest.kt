package com.planningdevgmail.kyutechapp2018.model

data class ApiRequest<T>(
        val count: Int,
        val next: String?,
        val previous: String?,
        val results: MutableList<T>
)