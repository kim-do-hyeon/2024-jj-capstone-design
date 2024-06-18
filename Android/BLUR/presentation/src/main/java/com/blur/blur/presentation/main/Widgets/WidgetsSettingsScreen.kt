package com.blur.blur.presentation.Main.Widgets

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.blur.blur.presentation.R
import com.blur.blur.presentation.theme.primaryLight
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun WidgetsSettingsScreen(
    viewModel: WidgetsSettingViewModel = hiltViewModel(),
    onDialog: (String) -> Unit,
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is WidgetsSettingSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    WidgetsSettingsScreen(
        message = state.messages ?: emptyMap(),
        widgetList = state.WidgetList,
        onApplyClicked = { message ->
            if (message != null) {
                viewModel.sendMessage(message)
            }
        },
        onSetWidget = viewModel::onSetWidget
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetsSettingsScreen(
    onSetWidget: () -> Unit,
    message: Map<String, List<Int>>,
    widgetList: List<String>,
    onApplyClicked: (Map<String, List<Int>>?) -> Unit  // sendMessage를 전달하기 위한 함수
) {
    val context = LocalContext.current
    var openDialog by remember { mutableStateOf(false) }
    // 라디오버튼의 옵션들을 위젯 리스트로 설정합니다.
    // 선택된 옵션을 추적하는 MutableState를 생성합니다.
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(if (widgetList.isNotEmpty()) widgetList[0] else null)
    }
    var selectedLocation: List<Int> by remember { mutableStateOf(emptyList()) }

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
                if (openDialog) {
                    Dialog(
                        onDismissRequest = {
                            openDialog = false
                        },
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(50.dp)
                                .wrapContentSize(Alignment.Center),
                            shape = RoundedCornerShape(16.dp),
                        ) {
                            Text(
                                text = "선택된 위치: $selectedLocation", // 선택된 위치 출력
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .align(alignment = Alignment.CenterHorizontally),
                                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            )
                            LazyColumn(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(20.dp)
                            ) {

                                items(widgetList) { option ->
                                    // 각 라디오버튼 옵션을 순회하며 표시합니다.
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .selectable(
                                                selected = (option == selectedOption),
                                                onClick = {
                                                    onOptionSelected(option)
                                                },
                                                role = Role.RadioButton
                                            )
                                    ) {
                                        Text(
                                            text = option,
                                            modifier = Modifier.padding(8.dp),
                                            textAlign = TextAlign.Center
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        // 선택된 옵션에 따라 라디오버튼 상태를 표시합니다.
                                        RadioButton(
                                            selected = (option == selectedOption),
                                            onClick = null
                                        )
                                    }
                                }
                            }
                            Button(
                                onClick = {
                                    openDialog = false
                                    val data =
                                        if (selectedOption != null && selectedLocation.isNotEmpty()) {
                                            mapOf(selectedOption to selectedLocation)
                                        } else {
                                            null
                                        }
                                    onApplyClicked(data)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(text = "적용")
                            }
                        }
                    }
                }

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
                                        it.value == listOf(row, col)
                                    }?.key
                                    val isDash =
                                        (row == 2 && (col == 2 || col == 3)) || (row == 3 && (col == 2 || col == 3))
                                    Box(
                                        modifier = Modifier.run {
                                            val padding = size(80.dp) // 클릭 핸들러를 전달합니다.
                                                .padding(8.dp)
                                            padding
                                        }, // 외부 패딩을 일정하게 설정
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            if (widgetName != null) {
                                                Text(
                                                    text = widgetName,
                                                    color = MaterialTheme.colorScheme.onBackground,
                                                    style = TextStyle(
                                                        fontSize = 12.sp,
                                                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                                                        fontWeight = FontWeight(700),
                                                        color = MaterialTheme.colorScheme.onBackground,
                                                        textAlign = TextAlign.End
                                                    )
                                                )
                                            } else if (isDash) {
                                                Text(
                                                    text = "",
                                                    color = Color.Black,
                                                    style = TextStyle(
                                                        fontSize = 50.sp,
                                                        fontFamily = FontFamily.Default,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                )
                                            } else {
                                                Icon(
                                                    Icons.Rounded.Add,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .size(50.dp)
                                                        .clickable(
                                                            onClick = {
                                                                selectedLocation = listOf(
                                                                    row,
                                                                    col
                                                                ) // 박스 클릭 시 위치 정보 저장
                                                                openDialog = true
                                                            }
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = onSetWidget,
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
    WidgetsSettingsScreen(
        message = mapOf(),
        widgetList = listOf(),
        onApplyClicked = {},
        onSetWidget = {},
    )
}