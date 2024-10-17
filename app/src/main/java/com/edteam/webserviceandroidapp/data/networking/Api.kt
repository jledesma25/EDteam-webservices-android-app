package com.edteam.webserviceandroidapp.data.networking

import com.edteam.webserviceandroidapp.BuildConfig
import com.edteam.webserviceandroidapp.data.networking.model.DeleteDeveloperResponse
import com.edteam.webserviceandroidapp.data.networking.model.DeveloperRequest
import com.edteam.webserviceandroidapp.data.networking.model.DeveloperResponse
import com.edteam.webserviceandroidapp.data.networking.model.DirectoryResponse
import com.edteam.webserviceandroidapp.data.networking.model.PokemonResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

object Api {

    //URL COMPLETA = "https://pokeapi.co/api/v2/pokemon?limit=151&offset=0
    //URL_BASE = "https://pokeapi.co/"
    //METODO = "api/v2/pokemon?limit=151&offset=0"
    //URL_BASE + METODO = URL COMPLETO

    val authInterceptor = AuthInterceptor()

    val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    val clientBuilder = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor(authInterceptor)
        .build()

    //1. Configurar retrofit
    val retrofit = Retrofit.Builder()
        //.baseUrl("http://10.0.2.2:3000/")
        .baseUrl(BuildConfig.URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .client(clientBuilder)
        .build()


    //2. Definir los metodos
    interface MethodApi{

        @GET("api/v2/pokemon?limit=151&offset=0")
        suspend fun getPokemons() : Response<PokemonResponse>

        @GET("developer")
        suspend fun getDirectory(
            //@Header("Authorization") authorization:String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNzI5MTg4MjI1LCJleHAiOjE3MjkxOTE4MjV9.CGbiSMpJ22woiZXy9Sxj1n0BIy2gS7CqgGBxKMAF7BI"
        ) : Response<DirectoryResponse>

        @DELETE("developer/{developerId}")
        suspend fun deleteDeveloper(
            @Path("developerId") developerId:Int,
            //@Header("Authorization") authorization:String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFkbWluIiwiaWF0IjoxNzI5MTg4MjI1LCJleHAiOjE3MjkxOTE4MjV9.CGbiSMpJ22woiZXy9Sxj1n0BIy2gS7CqgGBxKMAF7BI"
        ) : Response<DeleteDeveloperResponse>

        @POST("developer")
        suspend fun registrerDeveloper(
            @Body request: DeveloperRequest
        ) : Response<DeveloperResponse>

        @GET("developer/{developerId}")
        suspend fun getDeveloperById(
            @Path("developerId") developerId:Int
        ) : Response<DeveloperResponse>

        @PUT("developer")
        suspend fun updateDeveloper(
            @Body request: DeveloperRequest
        ) : Response<DeveloperResponse>


    }


    //3. Exponer esta clase para que se consuma
    val api = retrofit.create(MethodApi::class.java)

}