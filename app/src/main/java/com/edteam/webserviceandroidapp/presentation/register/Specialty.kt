package com.edteam.webserviceandroidapp.presentation.register

enum class Specialty(value:String) {

    MOBILE("MOBILE"),
    FRONTED("FRONTED"),
    BACKEND("BACKEND");

    companion object {
        fun getAllSpecialties() : List<String>{
            return values().map {
                it.name
            }
        }
    }

}