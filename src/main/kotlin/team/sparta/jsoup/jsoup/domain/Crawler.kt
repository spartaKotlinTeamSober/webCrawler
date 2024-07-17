package team.sparta.jsoup.jsoup.domain

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.context.annotation.Configuration
import team.sparta.jsoup.jsoup.domain.data.WineData

@Configuration
class Crawler() {
    private lateinit var crawl:Document

    fun setCrawlUrl(url:String){
        val document = Jsoup.connect(url).get()
        crawl=document
    }

    fun getCrawlData(idx: Int): WineData {
        val data = WineData()

        extractPrice(data)
        extractTypeCountryRegion(data)
        extractWineName(data)
        extractWineComponents(data)
        extractAroma(data)
        extractKind(data)
        extractStyle(data)

        data.originIdx = idx
        return data
    }

    private fun extractTypeCountryRegion(data: WineData) {
        crawl.select("body > section > div.inner > div.clear > div.wine-top-right > div.bagde-box > p")
            .text()
            .split(" ")
            .also {
                data.type = it[0]
                data.country = it[1]
                data.region = it[2]
            }
    }

    private fun extractWineName(data: WineData) {
        crawl.select("body > section > div.inner > div.clear > div.wine-top-right > dl > dt")
            .text()
            .also { data.name = it }
    }

    private fun extractPrice(data: WineData) {
        crawl.select("body > section > div.inner > div.clear > div.wine-top-right > p.wine-price")
            .text()
            .split("(", ")", " ")
            .also {
                if (it.contains("가격정보없음")) throw IllegalArgumentException("")
                else data.price = it[0].filter { digit -> digit.isDigit() }.toInt()
            }
    }

    private fun extractWineComponents(data: WineData) {
        crawl.select("body > section > div.inner > div.clear > div.wine-top-right > div.wine-components ul li")
            .map { it.select("div.filter-grade a.on").size }
            .also {
                data.sweetness = it[0]
                data.acidity = it[1]
                data.body = it[2]
                data.tannin = it[3]
            }
    }

    private fun extractAroma(data: WineData) {
        val map = mutableMapOf<String, MutableList<String>>()
        crawl.select("body > section > div.inner > div.clear > div.wine-top-right > div.wine-top-right-inner > div:nth-child(1)")
            .select("div.wine-matching-list")
            .let { select ->
                val values = select.text().replace(",", "")
                    .split(" ")
                    .toMutableList()
                    .filter { it.isNotBlank() }
                val keys = select.select("img").map { img ->
                    img.attr("src")
                        .substringAfter("ico-")
                        .substringBefore(".png")
                }

                var valueIndex=0
                keys.forEach { key ->
                    map[key] = mutableListOf()
                    var cnt=1

                    if(values[valueIndex].any { it.isDigit() })
                        cnt=values[valueIndex++].filter { it.isDigit() }.toInt()

                    map[key]?.addAll(values.subList(valueIndex, valueIndex + cnt))
                    valueIndex+=cnt
                }
            }
            .also {
                data.aroma = map.toMap()
            }
    }

    private fun extractKind(data: WineData) {
        crawl.select("#detail > div > div > dl:nth-child(3) > dd")
            .text()
            .also { data.kind = it.replace(" ", "") }
    }

    private fun extractStyle(data: WineData) {
        crawl.select("#detail > div > div > dl:nth-child(4)")
            .text()
            .also {
                if (it.contains("스타일")) data.style = it.replace("스타일 ", "")
                else data.style = null
            }
    }

}