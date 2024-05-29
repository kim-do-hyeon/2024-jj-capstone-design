package com.blur.blur.presentation.Main.Home.SendMessage

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MessageUserListScreen(
    viewmodel: SendMessageViewModel = hiltViewModel(),
    navigateToSendMessageScreen: (String) -> Unit // 사용자 이름을 전달하는 함수 추가
) {
    val state by viewmodel.collectAsState()
    val context = LocalContext.current

    viewmodel.collectSideEffect { sideEffect ->
        when (sideEffect) {

            is SendMessageSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    MessageUserListScreen(
        userList = state.UserList,
        onUserClicked = { username ->
            navigateToSendMessageScreen(username)
            viewmodel.getmessage()
        },
        getmessage = viewmodel::getmessage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageUserListScreen(
    userList: List<String>,
    onUserClicked: (String) -> Unit,
    getmessage: () -> Unit
) {
    val context = LocalContext.current

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
                    for (user in userList) {
                        // 클릭 가능한 사용자 이름을 표시합니다.
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .padding(horizontal = 10.dp)
                                .clickable {
                                    // 사용자를 클릭했을 때 실행될 작업을 호출합니다.
                                    onUserClicked(user)
                                    getmessage()
                                }
                        ) {
                            Spacer(modifier = Modifier.height(10.dp))
                            ListItem(
                                colors = ListItemDefaults.colors(
                                    containerColor = Color(0xFFFEF7FF)
                                ),
                                headlineContent = {
                                    Text(user)
                                },
                                leadingContent = {
                                    Icon(
                                        Icons.Rounded.Face,
                                        contentDescription = "Localized description",
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun SendMessageUserListScreenPreview() {
    val dummyUserList = listOf("User1", "User2", "User3","rfsdfsd","etsdgvwefsd","dfsfwecvds") // 더미 사용자 리스트
    MessageUserListScreen(
        userList = dummyUserList,
        onUserClicked = {},
        getmessage = {}
    )
}