package com.edteam.webserviceandroidapp.data.networking.model

data class PokemonResponse(
    val count : Int,
    val next : String,
    val previous : String,
    val results : List<Pokemon>
)

data class Pokemon(
    val name:String,
    val url:String
)

data class DirectoryResponse(
    val success:Boolean,
    val message:String,
    val data:List<Developer>
)

data class Developer(
    val description:String,
    val email:String,
    val githubUrl:String,
    val id:Int,
    val lastName:String,
    val names:String,
    val specialty:String
)

data class DeleteDeveloperResponse(
    val success:Boolean,
    val message:String,
)

data class DeveloperResponse(
    val success:Boolean,
    val message:String,
    val data: Developer
)