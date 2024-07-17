package team.sparta.jsoup.jsoup.domain

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import team.sparta.jsoup.jsoup.domain.data.WineData
import kotlin.random.Random

@Component
class Manager(private val crawler: Crawler, private val filestream: Filestream) {
    val logger: Logger = LoggerFactory.getLogger(Manager::class.java)
    private val scope = CoroutineScope(Dispatchers.Default+Job())

    companion object{
        private val max=177327
        private val now=161865
        private var cnt=5900
    }
    @PostConstruct
    fun init() {
        val wineData=mutableListOf<WineData>()
        for(idx in now downTo 1 ) {
            Thread.sleep(Random.nextLong(1000,3000))
            try {
                wineData.add(crawler.setCrawlUrl("https://www.wine21.com/13_search/wine_view.html?Idx=${idx}&lq=LIST")
                    .let { crawler.getCrawlData(idx) })
            }catch (e:Exception){continue}
            logger.info("[{}] Wine data: {}",++cnt,wineData.last())

            if(wineData.size >= 100){
                fileStreamProcess(wineData.toList())
                wineData.clear()
            }
            if(cnt>=14880){
                fileStreamProcess(wineData.toList())
                break
            }
        }
    }

    @PreDestroy
    fun destroy()=runBlocking {
        scope.cancel()
        scope.coroutineContext[Job]?.join()
    }

    private fun fileStreamProcess(wineData: List<WineData>) {
        scope.launch(Dispatchers.IO) {
            filestream.writeData(wineData)
        }
    }
}