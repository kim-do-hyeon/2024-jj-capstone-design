package com.example.blur.presentation.Main.Home.Camera

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blur.components.Text.MessageText2
import com.example.blur.presentation.Component.Text.MessageText
import com.example.blur.presentation.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessImage(onNavigateHome:() -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        // 3초 후에 HomeScreen으로 이동
        delay(3000)
        coroutineScope.launch {
            onNavigateHome()
        }
    }

    
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "얼굴 등록") },
                navigationIcon = {
                    // 뒤로 가기 버튼에 뒤로 가기 기능을 추가합니다.
                    IconButton(onClick = {
                        (context as? Activity)?.onBackPressed()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                    }
                }
            )
        },
        content = { contentPadding ->
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(contentPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.successmark),
                        contentDescription =""
                    )
                    Spacer(modifier = Modifier.height(35.dp))
                    MessageText(value = "얼굴 등록 완료", size =26 )
                    Spacer(modifier = Modifier.height(8.dp))
                    MessageText2(value = "이미지가 업로드 되었습니다.", size =15 )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        },
    )
}

@Preview
@Composable
fun SuccessImagePreview() {
    SuccessImage(onNavigateHome = {})
}
