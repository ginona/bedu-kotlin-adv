package com.gino.projectbedu.services

import com.gino.projectbedu.domain.Product
import com.gino.projectbedu.domain.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET
    suspend fun getProducts(@Url url:String): Response<List<Product>>
}