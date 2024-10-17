package com.edteam.webserviceandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.edteam.webserviceandroidapp.navigation.SetupNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SetupNavigation()
            /*LaunchedEffect(key1 = true) {
                GlobalScope.launch(Dispatchers.Main) {
                    val response = withContext(Dispatchers.IO){
                        Api.api.getPokemons()
                    }

                    if(response.isSuccessful){
                        val body = response.body()
                        body?.let {
                            val pokemons = it.results
                            pokemons.forEach {
                                println(it.name)
                            }
                        }
                    }


                }
            }*/
        }
    }
}

