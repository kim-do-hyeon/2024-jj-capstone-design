import ApiService
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WidgetScreen(navController: NavHostController) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "위젯 설정") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate("Home")
                        }
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Localized description")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(contentPadding)
        ) {
            val apiService = RetrofitClient.getApiService()

            // 위젯 목록을 저장할 변수 선언
            val widgets = remember { mutableStateOf<List<ApiService.WidgetData>>(emptyList()) }

            // 위젯 목록을 서버에서 가져오는 함수 호출
            getWidgets(apiService, widgets)

            // LazyColumn을 사용하여 위젯 목록을 표시
            LazyColumn {
                items(widgets.value) { widget ->
                    var checkedState by remember { mutableStateOf(false) }
                    ListItem(
                        headlineContent = {
                            Text(
                                text = "No: ${widget.widgetId}, Name: ${widget.widgetName}",
                                style = TextStyle(
                                    color= MaterialTheme.colorScheme.onBackground,
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        leadingContent = {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Localized description",
                            )
                        },
                        trailingContent = {
                            Checkbox(
                                checked = checkedState,
                                onCheckedChange = { checkedState = it }
                            )
                        }
                    )
                }
                item {
                    Divider(color = Color.Red, thickness = 1.dp)
                }
            }
        }
    }
}

// 위젯 목록을 서버에서 가져오는 함수
private fun getWidgets(apiService: ApiService, widgets: MutableState<List<ApiService.WidgetData>>) {
    apiService.getWidgets().enqueue(object : Callback<ApiService.WidgetResponse> {
        override fun onResponse(
            call: Call<ApiService.WidgetResponse>,
            response: Response<ApiService.WidgetResponse>
        ) {
            if (response.isSuccessful) {
                val widgetResponse = response.body()
                widgetResponse?.let {
                    // 서버 응답에서 위젯 목록을 가져와서 변수에 저장
                    val widgetList = it.message.map { entry ->
                        ApiService.WidgetData(widgetId = entry.key, widgetName = entry.value)
                    }
                    widgets.value = widgetList
                }
            } else {
                // 서버 응답 실패 처리
            }
        }

        override fun onFailure(call: Call<ApiService.WidgetResponse>, t: Throwable) {
            // 통신 실패 처리
        }
    })
}
