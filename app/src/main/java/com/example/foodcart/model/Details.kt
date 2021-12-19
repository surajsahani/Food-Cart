package com.example.foodblogs.model

data class Details(
    val about: List<String>,
    val dishes: List<String>,
    val images: List<String>,
    val where: List<Where>
)
