package com.example.testapp.utils

class CONSTANTS {
    companion object{
        const val API_BASE_URL = "https://www.jsonkeeper.com"

        const val BOOK_LIST = "$API_BASE_URL/b/CNGI"

        const val COUNTRY_LIST = "$API_BASE_URL/b/IU1K"

        const val IP_COUNTRY_BASE_URL = "https://ipinfo.io/json/"

        const val COUNTRY_CODE_BASE_URL = "https://country.io/"

        const val COUNTRY_CODE_MAP = "${COUNTRY_CODE_BASE_URL}names.json"


        //const val BOOK_DETAIL = "${BOOK_LIST}/{book_id}"

    }
}