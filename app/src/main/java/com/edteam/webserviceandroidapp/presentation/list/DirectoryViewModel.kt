package com.edteam.webserviceandroidapp.presentation.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edteam.webserviceandroidapp.core.Result
import com.edteam.webserviceandroidapp.data.networking.model.Developer
import com.edteam.webserviceandroidapp.data.repository.DirectoryRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DirectoryViewModel : ViewModel() {

    var state by mutableStateOf(DirectoryState())
       private set

    val repository = DirectoryRepositoryImp()

    fun getDirectory(){

        viewModelScope.launch {

            state = state.copy(isLoading = true)

            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getDirectory()
                }
                when(response){
                    is Result.Error -> {
                        state = state.copy(error = response.message)
                    }
                    is Result.Success -> {
                        state = state.copy(directory = response.data)
                    }
                }


            }catch (ex:Exception){
                state = state.copy(error = ex.message)
            }finally {
                state = state.copy(isLoading = false)
            }

        }


    }

    fun deleteDeveloper(id:Int){

        viewModelScope.launch {

            state = state.copy(isLoading = true)
            try{

                val response = withContext(Dispatchers.IO){
                    repository.deleteDeveloper(id)
                }
                when(response){
                    is Result.Error -> {
                        state = state.copy(error = response.message)
                    }
                    is Result.Success -> {
                        state = state.copy(successDeleted = response.data)
                    }
                }


            }catch (ex:Exception){

            }finally {
                state = state.copy(isLoading = false)
            }

        }

    }
    fun clearState(){
        state = state.copy(successDeleted = null)
    }

}



data class DirectoryState(
    val isLoading:Boolean=false,
    val error:String?=null,
    val directory:List<Developer>?=null,
    val successDeleted:String?=null
)

