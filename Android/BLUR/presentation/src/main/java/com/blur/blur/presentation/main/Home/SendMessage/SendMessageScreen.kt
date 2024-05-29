@file:OptIn(ExperimentalMaterial3Api::class)

package com.blur.blur.presentation.Main.Home.SendMessage

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.blur.blur.data.model.main.home.sendmessage.GetMessage
import com.blur.blur.presentation.Component.ChatBubble
import com.blur.blur.presentation.R
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun SendMessageScreen(
    viewModel: SendMessageViewModel = hiltViewModel(),
    username : String
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current
    val messageInfo by viewModel.messageInfo.collectAsState()
    viewModel.onReceiver_username(username) // ViewModel에 username 전달

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SendMessageSideEffect.HideLoading -> {}
            SendMessageSideEffect.ShowLoading -> {}
            is SendMessageSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    SendMessageScreen(
        messageInfo = messageInfo,
        SendMessage = state.SendMessage,
        onSendMessageChange = viewModel::onSendMessageChange,
        onSendMessageClick = viewModel::onSendMessageClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SendMessageScreen(
    messageInfo: List<GetMessage>?,
    SendMessage: String?,
    onSendMessageChange: (String) -> Unit,
    onSendMessageClick: () -> Unit,
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "메시지 보내기") },
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
                        .padding(contentPadding)

                ) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize(),
                        state = rememberLazyListState()
                    ) {
                        // 메시지를 날짜로 그룹화
                        val groupedMessages = messageInfo?.groupBy { it.timestamp.split(" ")[0] }

                        // 각 그룹에 대해 아이템을 생성
                        groupedMessages?.forEach { (date, messages) ->
                            // 날짜 표시
                            item {
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(
                                    text = date,
                                    style = TextStyle(
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                                        fontWeight = FontWeight.Bold,
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                )
                            }
                            // 해당 날짜에 속하는 모든 채팅 버블 표시
                            items(messages) { message ->
                                val (year, time) = message.timestamp.split(" ")
                                // 시간에서 초를 제거하고 분까지만 표시
                                val formattedTime = time.substringBeforeLast(":")
                                ChatBubble(
                                    content = message.content,
                                    time = formattedTime
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        if (SendMessage != null) {
                            OutlinedTextField(
                                value = SendMessage,
                                onValueChange = onSendMessageChange,
                                modifier = Modifier
                                    .weight(1f),
                                singleLine = true,
                                maxLines = 1,
                                keyboardOptions= KeyboardOptions(
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                        onSendMessageClick()

                                    }
                                ),
                            )
                        }
                        IconButton(
                            onClick = onSendMessageClick,
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.Send,
                                contentDescription = "",
                            )
                        }
                    }
                }

            }
        },
    )
}


@Preview
@Composable
fun SendMessageScreenPreview() {
    val dummyMessageInfo = listOf(
        GetMessage("rew", "메시지 내용 111111111111111111111", "2024-04-14 16:54:02"),
        GetMessage("ser", "메시지 내용 222222222222222222222222222", "2024-04-13 17:00:00"),
        GetMessage(
            "rfsd",
            "메시지 내용 333333333333333333333333333333333333333333333",
            "2024-04-13 17:30:45"
        ),
    )

    SendMessageScreen(
        messageInfo = dummyMessageInfo,
        SendMessage = "",
        onSendMessageChange = { },
        onSendMessageClick = {},
    )
}