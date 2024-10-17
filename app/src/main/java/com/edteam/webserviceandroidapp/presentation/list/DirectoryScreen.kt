package com.edteam.webserviceandroidapp.presentation.list

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.*
import com.edteam.webserviceandroidapp.data.networking.model.Developer
import com.edteam.webserviceandroidapp.presentation.common.AlertDialog
import com.edteam.webserviceandroidapp.presentation.common.CircleWithLetter


@Composable
fun DirectoryScreen(
    modifier: Modifier = Modifier,
    onNavigation: (Int?) -> Unit,
    viewModel: DirectoryViewModel = viewModel()
) {

    val state = viewModel.state
    val context = LocalContext.current

    var showDialog by remember {
        mutableStateOf(false)
    }
    var developerId by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(key1 = true) {
        viewModel.getDirectory()
    }

    if (showDialog) {
        AlertDialog(
            showDialog = showDialog,
            onCancel = {
                showDialog = false
            },
            onDelete = {
                viewModel.deleteDeveloper(developerId)
                showDialog = false
            }

        )
    }

    if (state.successDeleted != null) {
        Toast.makeText(context, state.successDeleted, Toast.LENGTH_SHORT).show()
        viewModel.clearState()
        viewModel.getDirectory()
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarComponent(
                title = "Directorio",
                style = TextStyle(
                    fontSize = 20.sp
                ),
                icon = Icons.Filled.Home
            )
        }
    ) { paddingValues ->

        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            state.directory?.let { developers ->
                items(developers) { developer ->
                    ItemDeveloper(
                        developer = developer,
                        onDeleteDeveloper = { id ->
                            showDialog = true
                            developerId = id
                        },
                        onEditDeveloper = { id ->
                            onNavigation(id)
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, bottom = 16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(onClick = {
                onNavigation(null)
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }
        }

    }
}

@Composable
fun ItemDeveloper(
    modifier: Modifier = Modifier,
    developer: Developer,
    onDeleteDeveloper: (Int) -> Unit,
    onEditDeveloper: (Int) -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                CircleWithLetter(letter = "${developer.names[0]}${developer.lastName[0]}")
            }
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = "${developer.names} ${developer.lastName}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${developer.specialty}",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Light
                )
                Row {
                    Icon(imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                onDeleteDeveloper(developer.id)
                            }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                onEditDeveloper(developer.id)
                            }
                    )
                }
            }

        }
    }

}

