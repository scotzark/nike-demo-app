package com.scoti.nikesampleapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.scoti.nikesampleapp.models.Image
import com.scoti.nikesampleapp.R
import com.scoti.nikesampleapp.data.ScraperViewModel
import com.scoti.nikesampleapp.utils.hideKeyboard
import com.scoti.nikesampleapp.views.EndlessRecyclerView


class ScraperFragment : Fragment() {

    private val viewModel by lazy {
        ScraperViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.scrapedImages.observe(this, Observer{
            loadImages(it)
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scraper, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.scrapeIt).setOnClickListener {
            var url = requireView().findViewById<EditText>(R.id.url).text.toString()

            if (url.isNotEmpty()) {

                if (!(url.startsWith("https") || url.startsWith("http")))
                    url = "https://${url}"

                viewModel.scrapePage(url)
                requireView().hideKeyboard()
            }
        }


    }

    private fun loadImages(images: List<Image>) {
        requireView().findViewById<EndlessRecyclerView>(R.id.endlessRecycler).load(images)
    }
}