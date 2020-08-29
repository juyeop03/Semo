package kr.hs.dgsw.stac.domain

class UserMethodModel (
    val date: String,
    val name: String,
    val content: String,
    val image: String,
    val laundry: List<String>
)