package com.slowpoke.spanishdist

import com.slowpoke.spanishdist.dictWordEntry.WordEntry
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DictApi {

    @GET("api/v3/references/spanish/json/{searchedWord}")
    suspend fun getDictEntry(@Path ("searchedWord") word: String, @Query ("key") key: String): Response<List<WordEntry>>
}

