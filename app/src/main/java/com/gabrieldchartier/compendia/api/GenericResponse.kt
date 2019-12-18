package com.gabrieldchartier.compendia.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GenericResponse(
        @SerializedName("response")
        @Expose
        var response: String
)