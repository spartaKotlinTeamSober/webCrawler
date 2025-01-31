package team.sparta.jsoup.jsoup.infra

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GsonCustom {
    @Bean
    fun gson(): Gson = GsonBuilder().serializeNulls().create()
}