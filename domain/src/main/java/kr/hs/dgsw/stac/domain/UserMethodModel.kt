package kr.hs.dgsw.stac.domain

class UserMethodModel (
    val date: String,
    val title: String,
    val content: String,
    val imageUri: String,
    val laundry: List<String>
)