package com.rahulpundhir.myliv.circularimageloader

import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object NetworkRequest {

    private fun makeNetworkRequest(baseUrl: String): String? {

        var json: String? = null
        try {
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
            json = null
            if (urlConnection.responseCode == 200) {
                var stream = urlConnection.inputStream
                json = stream.bufferedReader().use(BufferedReader::readText)
                // Close InputStream
                stream.close()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }

    fun getImageUrl(baseUrl: String): String? {
        var json = makeNetworkRequest(baseUrl);
        if (json != null) {
            val jsonArray = JSONArray(json)
            var jsonObject: JSONObject = jsonArray.get(0) as JSONObject;
            return jsonObject.get("url").toString()
        }
        return null
    }
}