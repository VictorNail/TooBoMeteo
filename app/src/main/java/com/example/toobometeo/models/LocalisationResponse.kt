package com.example.toobometeo.models

import com.google.gson.annotations.SerializedName

data class localisationResponse(
    val features: List<Feature>
)

data class Feature(
    @SerializedName("geometry") val geometry: Geometry,
    @SerializedName("properties") val properties: Properties
)

data class Geometry(
    @SerializedName("coordinates") val coordinates: List<Double>
)

data class Properties(
    @SerializedName("label") val label: String
)