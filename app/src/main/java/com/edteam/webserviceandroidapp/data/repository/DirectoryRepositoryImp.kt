package com.edteam.webserviceandroidapp.data.repository

import com.edteam.webserviceandroidapp.core.Result
import com.edteam.webserviceandroidapp.data.networking.Api
import com.edteam.webserviceandroidapp.data.networking.model.Developer
import com.edteam.webserviceandroidapp.data.networking.model.DeveloperRequest
import retrofit2.HttpException
import java.io.IOException

class DirectoryRepositoryImp : DirectoryRepository {

    override suspend fun getDirectory(): Result<List<Developer>> {

        return try{
            val response = Api.api.getDirectory()
            if(response.isSuccessful){
                Result.Success(data =  response.body()?.data ?: emptyList())
            }else{
                Result.Error(message = response.message())
            }
        }
        catch (ex: IOException){
            Result.Error(message = "Por favor, compruebe su conexion")
        }
        catch (ex: HttpException){
            Result.Error(message = "Error Http")
        }
        catch (ex:Exception){
            Result.Error(message = ex.message.toString())
        }


    }

    override suspend fun deleteDeveloper(id: Int): Result<String?> {
        return try{
            val response = Api.api.deleteDeveloper(id)
            if(response.isSuccessful){
                Result.Success(data = response.body()?.message )
            }else{
                Result.Error(message = response.message())
            }
        }catch (ex: IOException){
            Result.Error(message = "Por favor, compruebe su conexion")
        }
        catch (ex: HttpException){
            Result.Error(message = "Error Http")
        }
        catch (ex:Exception){
            Result.Error(message = ex.message.toString())
        }

    }

    override suspend fun registerDeveloper(request: DeveloperRequest): Developer? {
        val response = Api.api.registrerDeveloper(request)
        return response.body()?.data
    }

    override suspend fun getDevoperById(id: Int): Developer? {
        val response = Api.api.getDeveloperById(id)
        return response.body()?.data
    }

    override suspend fun updateDeveloper(request: DeveloperRequest): Developer? {
        val response = Api.api.updateDeveloper(request)
        return response.body()?.data
    }

}