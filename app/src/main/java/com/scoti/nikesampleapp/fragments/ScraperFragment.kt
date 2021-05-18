package com.scoti.nikesampleapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.scoti.nikesampleapp.models.Image
import com.scoti.nikesampleapp.R
import com.scoti.nikesampleapp.data.ScraperViewModel
import com.scoti.nikesampleapp.models.AppErrors
import com.scoti.nikesampleapp.utils.hideKeyboard
import com.scoti.nikesampleapp.utils.visible
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

        viewModel.errorMessage.observe(this, Observer {
            showErrorCode(it)
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
                scrapePage(url)
            }
        }
    }

    private fun scrapePage(url: String) {
        viewModel.scrapePage("https://${url}")
        requireView().hideKeyboard()
        showSpinner(true)
    }

    private fun loadImages(images: List<Image>) {
        showSpinner(false)
        requireView().findViewById<EndlessRecyclerView>(R.id.endlessRecycler).load(images)
        requireView().findViewById<EndlessRecyclerView>(R.id.endlessRecycler).visible = true
        requireView().findViewById<TextView>(R.id.error).visible = false
    }

    private fun showErrorCode(error: String) {
        showSpinner(false)
        requireView().findViewById<TextView>(R.id.error).visible = true
        requireView().findViewById<TextView>(R.id.error).text = error
        requireView().findViewById<EndlessRecyclerView>(R.id.endlessRecycler).visible = false
    }

    private fun showSpinner(enable: Boolean) {
        requireView().findViewById<ProgressBar>(R.id.spinner).visible = enable
    }
}