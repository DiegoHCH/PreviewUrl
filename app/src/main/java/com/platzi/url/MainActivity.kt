package com.platzi.url


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.platzi.url.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URL


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        activityMainBinding.buttonUrl.setOnClickListener {
            val path: String = activityMainBinding.urlEditText.text.toString()
            previewUrl(path)
        }
    }

    data class MetaData(var titulo: String = String(), var imagenUrl: String = String())
    private fun previewUrl(path : String) {
        val url = URL(path)
        activityMainBinding.hostUrlTextView.text = url.host
        Thread{
            val stringBuilder = StringBuilder()
            try {
                val document : Document = Jsoup.connect(path).get( )
                val metatags = document.getElementsByTag("meta")
                val metaData = MetaData()
                /*metatags.find { it.attr("property").equals("og:title") }.let { element ->
                    metaData.titulo = element?.attr("content") ?: document.title().toString()
*//*                    runOnUiThread {
                        if (element != null){
                            activityMainBinding.titleUrlTextView.text = element.attr("content")

                        } else activityMainBinding.titleUrlTextView.text = document.title().toString()
                    }*//*
                }
                metatags.find { it.attr("property").equals("og:image") }.let { element ->
                    metaData.imagenUrl = element?.attr("content")
                    *//*if (element != null){
                        runOnUiThread {
                            Picasso
                                .get()
                                .load(element.attr("content"))
                                .error(R.drawable.ic_launcher_background)
                                .into(activityMainBinding.logoUrlImageView)
                        }
                    } else activityMainBinding.logoUrlImageView.setImageResource(R.drawable.ic_launcher_background)*//*
                }*/
                metaData.titulo = metatags.find { it.attr("property").equals("og:title") }?.attr("content") ?: document.title().toString()
                metaData.imagenUrl = metatags.find { it.attr("property").equals("og:image") }?.attr("content") ?: String()
                runOnUiThread {
                    if(metaData.imagenUrl.isNotEmpty()){
                        Picasso
                            .get()
                            .load(metaData.imagenUrl)
                            .error(R.drawable.ic_launcher_background)
                            .into(activityMainBinding.logoUrlImageView)
                    } else activityMainBinding.logoUrlImageView.setImageResource(R.drawable.ic_launcher_background)

                    activityMainBinding.titleUrlTextView.text = metaData.titulo

                }
            }catch (e : Exception){
                stringBuilder.append("Error: ").append(e.message)
            }
        }.start()
    }
}