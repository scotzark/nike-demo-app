package com.scoti.nikesampleapp.models

enum class AppErrors(val prettyName: String) {
    NO_IMAGES("Could not scrape any images from this the page %s"),
    COULD_NOT_PARSE_PAGE("Could not Scrape Website")
}