package com.example.movve.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request  = chain.request().newBuilder()
            .addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5NTliOGI5MDhlMDExZDYyZThiNzQ3YTdiZWIyMTRlZSIsInN1YiI6IjY1MDNlNTMxMWJmMjY2MDBjNWQ3ODJhYiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.WWco19gzAommSv9cPhGU7F90bOTb3uITpD6qjLSu2r4")
            .build()

        return chain.proceed(request)

    }

}