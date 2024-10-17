package com.edteam.webserviceandroidapp.presentation.register

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edteam.webserviceandroidapp.presentation.common.TopAppBarComponent
import com.edteam.webserviceandroidapp.ui.theme.Background
import androidx.lifecycle.viewmodel.compose.*
import com.edteam.webserviceandroidapp.data.networking.model.DeveloperRequest

@Composable
fun RegisterDeveloperScreen(
    modifier: Modifier = Modifier,
    id: Int,  //-1 Registro, 2,3 Actualizacion
    onBack: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    var names by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var specialty by remember {
        mutableStateOf(Specialty.getAllSpecialties()[0])
    }

    val title = if(id == -1) "Registrar" else "Editar"

    LaunchedEffect(key1 = true) {
        if (id != -1) {
         viewModel.getDeveloperById(id)
        }
    }

    LaunchedEffect(key1 = state.developer) {
        state.developer?.let {
            names = it.names
            lastName = it.lastName
            specialty = it.specialty
        }
    }

    if (state.error != null) {
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        viewModel.clearState()
    }

    if (state.successfull != null) {
        Toast.makeText(
            context,
            "Hemos registrado al desarrollador ${state.successfull.names}",
            Toast.LENGTH_SHORT
        ).show()
        onBack()
        viewModel.clearState()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBarComponent(
                title = title,
                style = TextStyle(
                    fontSize = 20.sp
                ),
                icon = Icons.AutoMirrored.Filled.ArrowBack
            )
        }
    ) { paddingValues ->



        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = names,
                onValueChange = {
                    names = it
                },
                shape = RoundedCornerShape(16.dp),
                label = {
                    Text(text = "Nombres")
                },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                ),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Background,
                    cursorColor = Background,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Background
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                shape = RoundedCornerShape(16.dp),
                label = {
                    Text(text = "Apellidos")
                },
                textStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                ),
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = Background,
                    cursorColor = Background,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Background
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Specialty.getAllSpecialties().forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        RadioButton(
                            selected = it == specialty,
                            onClick = {
                                specialty = it
                            }
                        )
                        Text(text = it)

                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {

                    if(id != -1){
                        //Update
                        viewModel.updateDeveloper(
                            DeveloperRequest(
                                names = names,
                                lastName = lastName,
                                specialty = specialty,
                                id = id
                            )
                        )
                    }else{
                        //Register
                        viewModel.registerDeveloper(
                            DeveloperRequest(
                                names = names,
                                lastName = lastName,
                                specialty = specialty
                            )
                        )
                    }



                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Background,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp
                )
            }
        }

    }


}