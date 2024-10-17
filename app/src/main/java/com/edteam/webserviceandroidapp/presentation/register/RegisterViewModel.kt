package com.edteam.webserviceandroidapp.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edteam.webserviceandroidapp.data.networking.model.Developer
import com.edteam.webserviceandroidapp.data.networking.model.DeveloperRequest
import com.edteam.webserviceandroidapp.data.repository.DirectoryRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {

    var state by mutableStateOf(RegisterState())
      private set

    val repository = DirectoryRepositoryImp()

    fun registerDeveloper(request: DeveloperRequest){

        try{
            state = state.copy(isLoading = true)

            viewModelScope.launch {
                val response = withContext(Dispatchers.IO){
                    repository.registerDeveloper(request)
                }
                state = state.copy(successfull = response, error = null)
            }

        }catch (ex:Exception){
            state = state.copy(error = ex.message, successfull = null)
        }finally {
            state = state.copy(isLoading = false)
        }

    }

    fun getDeveloperById(id:Int){

        try{
            state = state.copy(isLoading = false)

            viewModelScope.launch {
                val response = withContext(Dispatchers.IO){
                    repository.getDevoperById(id)
                }
                state = state.copy(developer = response, error = null, successfull = null, isLoading = false)
            }

        }catch (ex:Exception){
            state = state.copy(error = ex.message, successfull = null, developer = null, isLoading = false)
        }/*finally {
            state = state.copy(isLoading = false)
        }*/

    }

    fun updateDeveloper(request: DeveloperRequest){

        try{

            state = state.copy(isLoading = false)

            viewModelScope.launch {
                val response = withContext(Dispatchers.IO){
                    repository.updateDeveloper(request)
                }
                state = state.copy(successfull = response, error = null, isLoading = false, developer = null)
            }

        }catch (ex:Exception){
            state = state.copy(error = ex.message, successfull = null, isLoading = false, developer = null)
        }

    }

    fun clearState(){
        state = state.copy(successfull = null, error = null)
    }


}

data class RegisterState(
    val isLoading: Boolean = false,
    val error:String?=null,
    val successfull: Developer?=null,
    val developer: Developer?=null
)