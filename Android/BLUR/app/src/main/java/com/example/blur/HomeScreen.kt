package com.example.blur

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blur.components.Button.TwoText
import com.example.blur.components.Text.HomeTopText
import com.example.blur.components.Text.MessageText
import com.example.blur.components.Text.MessageText2

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text(text = "Top Bar") },
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Localized description")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = null)
                    }
                }

            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "add"
                )
            }
        }

    ) { contentPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            HomeTopText()
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}
