package com.edteam.webserviceandroidapp.data.networking

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
            .newBuilder()
            .header("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNzI5MTg4MjI1LCJleHAiOjE3MjkxOTE4MjV9.CGbiSMpJ22woiZXy9Sxj1n0BIy2gS7CqgGBxKMAF7BI")
            .build()

        return chain.proceed(request)

    }
}