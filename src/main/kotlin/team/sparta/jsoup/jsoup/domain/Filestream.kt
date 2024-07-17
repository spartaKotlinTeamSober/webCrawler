package team.sparta.jsoup.jsoup.domain

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import team.sparta.jsoup.jsoup.domain.data.WineData
import team.sparta.jsoup.jsoup.infra.GsonCustom
import java.io.File
import java.time.Duration
import java.time.LocalDateTime

@Configuration
class Filestream @Autowired constructor(private val gson :Gson) {
    var fileCnt=60
    val logger: Logger = LoggerFactory.getLogger(Manager::class.java)

    private fun toJson(wineDataList: List<WineData>) = gson.toJson(wineDataList)

    fun writeData(wineDataList: List<WineData>) {
        val stt= LocalDateTime.now()
        logger.info("Start writeData Process")
        File("/Users/t2023-m0097/Desktop/winds_data/wine_data_${fileCnt}.json")
            .also { it.writeText(toJson(wineDataList)) }

        val end= LocalDateTime.now()
        fileCnt++

        logger.info("End writeData Process : {}",Duration.between(stt,end))
    }
}