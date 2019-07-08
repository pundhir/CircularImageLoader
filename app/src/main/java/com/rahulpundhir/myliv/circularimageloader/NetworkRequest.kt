package com.rahulpundhir.myliv.circularimageloader

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

object NetworkRequest {

    private fun makeNetworkRequest(baseUrl: String): String {

        var url = URL(baseUrl)
        var urlConnection = url.openConnection() as HttpURLConnection

        // setting the  Request Method Type
        urlConnection.requestMethod = "GET"

        // adding the headers for request
        urlConnection.setRequestProperty("X-Api-Key", "DEMO-API-KEY")
        urlConnection.setRequestProperty("Content-Type", "application/json")

        // request timeout
        urlConnection.readTimeout = 60 * 1000
        urlConnection.connectTimeout = 60 * 1000
        urlConnection.setChunkedStreamingMode(0)
        urlConnection.doInput = true

        // network call
        urlConnection.connect()

        var stream = urlConnection.inputStream
        var json = stream.bufferedReader().use(BufferedReader::readText)

        // Close InputStream
        stream.close()
        return json
    }

    fun getImageUrl(baseUrl: String): String {
        var json = makeNetworkRequest(baseUrl);
        val jsonArray = JSONArray(json)
        var jsonObject: JSONObject = jsonArray.get(0) as JSONObject;
        return jsonObject.get("url").toString()
    }
}