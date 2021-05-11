package com.scoti.nikesampleapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scoti.nikesampleapp.models.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.Exception

class ScraperViewModel : ViewModel() {
    val scrapedImages = MutableLiveData<List<Image>>()

    fun scrapePage(url: String) {

        CoroutineScope(Dispatchers.Default).launch {
            try {
                val images: MutableList<Image> = mutableListOf()
                val document: Document = Jsoup.connect(url).get()
                val imgs = document.select("img")

                imgs.forEach {
                    val element = it
                    val absoluteUrl: String = element.absUrl("src") //absolute URL on src
                    if (!absoluteUrl.contains(".svg"))
                        images.add(Image(absoluteUrl))
                }

                scrapedImages.postValue(images)
            }
            catch (e: Exception) {}
        }

    }
}