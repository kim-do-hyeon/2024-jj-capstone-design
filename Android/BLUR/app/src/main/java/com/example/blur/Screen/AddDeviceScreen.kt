package com.example.blur.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blur.R
import com.example.blur.components.Button.FillBtn
import com.example.blur.components.TextField.AddDeviceTextField




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeviceScreen(
    navController: NavHostController,
) {

    var DeviceId by remember { mutableStateOf("") }
    var isButtonEnabled by remember { mutableStateOf(false) }
    // 기기 등록 상태를 나타내는 변수
    var deviceRegistered by remember { mutableStateOf(false) }

    // 기기 등록 함수
    fun registerDevice() {
        // 기기 등록 로직을 여기에 추가
        // 예를 들어 기기를 서버에 등록하고 등록 결과를 받아올 수 있습니다.
        // 등록 성공 시 deviceRegistered 값을 true로 설정합니다.
        deviceRegistered = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "기기 추가") },
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Localized description")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { registerDevice() } // 기기 등록 함수 호출
            ) {
                Icon(
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
            Spacer(modifier = Modifier.height(180.dp))
            Text(
                text = "연결하실 기기의 \n제품 코드를 입력해 주세요",
                style = TextStyle(
                    fontSize = 30.sp,
                    lineHeight = 39.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold)),
                    fontWeight = FontWeight(700),
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "제품코드는 기기 후면부에 있습니다.",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 23.4.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF828282),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(70.dp))
            AddDeviceTextField(
                onValueChange ={
                    DeviceId = it
                    isButtonEnabled = it.length == 9
                }
            )
            Spacer(modifier = Modifier.height(40.dp))
            FillBtn(
                value = "제품 등록",
                enabled = isButtonEnabled,
                onClick = { registerDevice() } // 기기 등록 함수 호출
            )
        }
    }
}

@Preview
@Composable
fun AddDeviceScreenPreview(){
    AddDeviceScreen(rememberNavController())
}