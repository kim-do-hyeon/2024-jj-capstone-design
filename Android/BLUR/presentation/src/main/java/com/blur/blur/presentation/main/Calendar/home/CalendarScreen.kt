package com.blur.blur.presentation.Main.Calendar.home

import androidx.compose.material3.*
import TodoItem
import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.blur.blur.data.model.main.home.todo.TodoMessage
import com.blur.blur.presentation.Main.Calendar.item.ItemEntryBody
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CalendarScreen(
    viewModel: TodoViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current
    val todoListState by viewModel.TodoList.collectAsState()
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TodoSideEffect.Toast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
            // Handle additional side effects as needed
        }
    }

    CalendarContent(
        itemText = state.itemText,
        onItemTextChange = viewModel::onItemTextChange,
        addTodoItem = viewModel::addTodoItem,
        itemOnDelete = viewModel::itemOnDelete,
        status = state.Status,
        onStatus = viewModel::onStatus,
        viewTodoItems = viewModel::loadTodoItems,
        todoList = todoListState ?: emptyList()  // Pass the todoListState.value
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalendarContent(
    itemText: String,
    onItemTextChange:(String)->Unit,
    addTodoItem: (LocalDate,String) -> Unit,
    itemOnDelete: () -> Unit,
    status: Boolean,
    onStatus: (Int) -> Unit,
    viewTodoItems: (LocalDate?) -> Unit,
    todoList: List<TodoMessage>  // New parameter to receive the todoListState
) {
    val context = LocalContext.current
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    // 모달 창 열기/닫기 상태를 관리하는 변수
    var isModalOpen by remember { mutableStateOf(false) }

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    var targetMonth by remember { mutableStateOf<YearMonth?>(null) }

    LaunchedEffect(targetMonth) {
        targetMonth?.let {
            calendarState.scrollToMonth(it)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "일정 관리") },
                navigationIcon = {
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
                        .padding(contentPadding)
                ) {
                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    HorizontalCalendar(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .weight(1.3f),
                        monthBody = { _, content ->
                            Box {
                                content()
                            }
                        },
                        monthContainer = { _, container ->
                            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                            Box(
                                modifier = Modifier
                                    .width(screenWidth * 0.95f)
                                    .padding(8.dp)
                                    .clip(shape = RoundedCornerShape(10.dp))
                                    .border(
                                        color = Color.Black,
                                        width = 1.dp,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            ) {
                                container()
                            }
                        },
                        calendarScrollPaged = true,
                        userScrollEnabled = true,
                        reverseLayout = false,
                        state = calendarState,
                        dayContent = { day ->
                            Day(day, isSelected = selectedDate == day.date) { clickedDay ->
                                selectedDate = clickedDay.date
                                viewTodoItems(clickedDay.date)
                                Log.d("로그1", selectedDate.toString())
                            }
                        },
                        monthHeader = { month ->
                            val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
                            Column(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.primary)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "${month.yearMonth.year}년 ${
                                            month.yearMonth.month.getDisplayName(
                                                TextStyle.FULL,
                                                Locale.getDefault()
                                            )
                                        }",
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(
                                            horizontal = 20.dp,
                                            vertical = 10.dp
                                        )
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    IconButton(onClick = {
                                        targetMonth =
                                            calendarState.firstVisibleMonth.yearMonth.minusMonths(1)
                                    }) {
                                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "")
                                    }
                                    IconButton(onClick = {
                                        targetMonth =
                                            calendarState.firstVisibleMonth.yearMonth.plusMonths(1)
                                    }) {
                                        Icon(Icons.Filled.ArrowForwardIos, contentDescription = "")
                                    }
                                }
                                Divider()
                                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
                                Divider()
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    selectedDate?.let { date ->
                        val formattedDate =
                            selectedDate.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"))
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "$formattedDate 일정",
                                    color = MaterialTheme.colorScheme.background,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                                IconButton(
                                    onClick = {
                                        isModalOpen = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Add,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.background
                                    )
                                }
                            }
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize()
                            ) {
                                items(todoList) { todoMessage ->
                                    TodoItem(
                                        onDelete = itemOnDelete,
                                        itemtext = todoMessage.text,
                                        status = todoMessage.status,
                                        onStatus = { onStatus(todoMessage.id) },
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )

    // 다이얼로그 창을 모달로 표시
    if (isModalOpen) {
        Dialog(
            onDismissRequest = {
                isModalOpen = false
                onItemTextChange("")
            }
        ) {
            ItemEntryBody(
                SelectedDate = selectedDate.toString(),
                ItemText = itemText,
                onItemTextChange = onItemTextChange,
                onSaveClick = {
                    addTodoItem(selectedDate, itemText)
                    onItemTextChange("")
                    isModalOpen = false
                },
                modifier = Modifier,
                onDismissRequest = { isModalOpen = false }
            )
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    CalendarContent(
        itemText = "finibus",
        onItemTextChange = {},
        addTodoItem = { localDate: LocalDate, s: String -> },
        itemOnDelete = {},
        status = false,
        onStatus = {},
        viewTodoItems = {},
        todoList = listOf()

    )
}

