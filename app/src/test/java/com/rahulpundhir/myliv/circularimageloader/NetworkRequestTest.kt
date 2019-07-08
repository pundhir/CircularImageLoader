package com.rahulpundhir.myliv.circularimageloader

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NetworkRequestTest {

    val BASE_URL = "https://api.thecatapi.com/v1/images/search"

    @Test
    fun getImageUrlSuccess() {
        assertNotNull(NetworkRequest.getImageUrl(BASE_URL))
    }

    @Test
    fun getImageUrlFailed() {
        assertNotNull(NetworkRequest.getImageUrl(""))
    }
}
