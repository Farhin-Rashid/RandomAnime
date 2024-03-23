package com.example.randomanime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private var url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val input = findViewById<TextView>(R.id.num)

        button.setOnClickListener(){
            var id = input.text.toString()
            url = "https://kitsu.io/api/edge/anime/$id"
            getText()
        }
    }

    private fun getText(){
        val client = AsyncHttpClient()
        val title = findViewById<TextView>(R.id.title)
        val ep = findViewById<TextView>(R.id.synopsis)
        val stat = findViewById<TextView>(R.id.stat)

        client[url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                title.text = "Title: " + json.jsonObject.getJSONObject("data").getJSONObject("attributes").getJSONObject("titles").getString("en")
                ep.text = "Episodes: " + json.jsonObject.getJSONObject("data").getJSONObject("attributes").getString("episodeCount")
                stat.text = "Status: " + json.jsonObject.getJSONObject("data").getJSONObject("attributes").getString("status")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("DID NOT WORK", errorResponse)
            }
        }]
    }
}