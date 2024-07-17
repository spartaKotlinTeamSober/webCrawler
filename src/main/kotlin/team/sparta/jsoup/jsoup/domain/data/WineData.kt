package team.sparta.jsoup.jsoup.domain.data

data class WineData(
    var originIdx:Int = 0,
    var name:String="",
    var sweetness: Int = 0,
    var acidity: Int = 0,
    var body: Int = 0,
    var tannin: Int = 0,
    var type: String = "",
    var aroma: Map<String,List<String>> = mutableMapOf(),
    var price: Int? = 0,
    var kind: String? = "",
    var style: String? = "",
    var country: String? = "",
    var region: String? = "",
    var embedding: String? = null,
)
