package com.edteam.webserviceandroidapp.data.repository

import com.edteam.webserviceandroidapp.core.Result
import com.edteam.webserviceandroidapp.data.networking.model.Developer
import com.edteam.webserviceandroidapp.data.networking.model.DeveloperRequest

interface DirectoryRepository {

    suspend fun getDirectory() : Result<List<Developer>>
    suspend fun deleteDeveloper(id:Int): Result<String?>
    suspend fun registerDeveloper(developer: DeveloperRequest) : Developer?
    suspend fun getDevoperById(id:Int) : Developer?
    suspend fun updateDeveloper(developer: DeveloperRequest) : Developer?
}