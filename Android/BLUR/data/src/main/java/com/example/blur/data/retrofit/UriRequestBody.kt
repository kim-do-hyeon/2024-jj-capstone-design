package com.example.blur.data.retrofit

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink

class UriRequestBody constructor(
    val contextType: MediaType? = null,
):RequestBody() {
    override fun contentType(): MediaType? {
        return contextType
    }

    override fun writeTo(sink: BufferedSink) {
        TODO("Not yet implemented")
    }
}