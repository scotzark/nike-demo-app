package com.scoti.nikesampleapp.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scoti.nikesampleapp.models.AppErrors
import com.scoti.nikesampleapp.models.Image
import com.scoti.nikesampleapp.utils.isValidImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.Exception

class ScraperViewModel : ViewModel() {
    val scrapedImages = MutableLiveData<List<Image>>()
    val errorMessage = MutableLiveData<String>()

    fun scrapePage(url: String) {

        CoroutineScope(Dispatchers.Default).launch {
            try {
                val images: MutableList<Image> = mutableListOf()
                val document: Document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .followRedirects(true)
                        .get()

                val imgs = document.select("img")

                imgs.forEach {
                    val element = it
                    val absoluteUrl = element.absUrl("src")

                    if (absoluteUrl.isValidImage())
                        images.add(Image(absoluteUrl))

                }

                if (images.isEmpty()) {
                    errorMessage.postValue(String.format(AppErrors.NO_IMAGES.prettyName, url))
                }
                else {
                    scrapedImages.postValue(images)
                }
            }
            catch (e: Exception) {
                errorMessage.postValue("${AppErrors.COULD_NOT_PARSE_PAGE.prettyName}\n${e.localizedMessage}")

            }
        }

    }
}