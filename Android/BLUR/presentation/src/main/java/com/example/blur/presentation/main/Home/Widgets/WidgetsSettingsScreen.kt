package com.example.blur.presentation.Main.Home.Widgets

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blur.presentation.Component.WidgetBox
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun WidgetsSettingsScreen(
    viewModel: WidgetsSettingViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is WidgetsStteingSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                Log.e("SendMessageScreen", sideEffect.message) // Log 태그 수정
            }
        }
    }

    WidgetsSettingsScreen(
        message = state.messages ?: emptyMap()
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetsSettingsScreen(
    message: Map<String, List<Int>>,
) {
    val context = LocalContext.current

    Surface {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = { Text(text = "위젯 설정") },
                    navigationIcon = {
                        // 뒤로 가기 버튼에 뒤로 가기 기능을 추가합니다.
                        IconButton(onClick = {
                            (context as? Activity)?.onBackPressed()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "뒤로 가기")
                        }
                    },
                )
            },
            content = { contentPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(contentPadding)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.weight(1f))

                    Column(
                        modifier = Modifier
                            .border(1.dp, Color.Black)

                    ) {
                        val rows = 3 // 전체 행 수
                        val cols = 4 // 전체 열 수
                        for (row in 1..rows) {
                            // 행 간격 추가
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                for (col in 1..cols) {
                                    val widgetName = message.entries.firstOrNull {
                                        it.value == listOf(
                                            row,
                                            col
                                        )
                                    }?.key
                                    val locationString = "$row, $col"
                                    WidgetBox(widgetName =widgetName )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("적용")
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        )
    }
}



@Preview
@Composable
fun WWidgetsSettingsScreenPreview() {
    WidgetsSettingsScreen(message = emptyMap())
}