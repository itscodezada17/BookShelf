package com.example.testapp.auth.signUp

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Country(
    @SerializedName("country" ) var country : String? = null,
    @SerializedName("region"  ) var region  : String? = null
)
