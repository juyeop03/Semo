package kr.hs.dgsw.stac.domain

class MyLaundryModel (
    val date: String,
    val title: String,
    val content: String,
    val imageUri: String,
    val laundry: List<String>
)