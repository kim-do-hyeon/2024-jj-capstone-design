package com.blur.blur.presentation.Main.Calendar.home


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.blur.blur.data.model.main.home.todo.TodoMessage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import com.blur.blur.data.retrofit.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.annotation.concurrent.Immutable
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userService: UserService
) : ViewModel(), ContainerHost<TodoState, TodoSideEffect> {

    private val _selectedDate = MutableStateFlow<LocalDate?>(null)

    private val _TodoList = MutableStateFlow<List<TodoMessage>?>(null)
    val TodoList = _TodoList.asStateFlow()

    override val container: Container<TodoState, TodoSideEffect> = container(
        initialState = TodoState(),
        buildSettings = {
            this.exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                intent {
                    Log.e("TodoViewModel", "An error occurred: $throwable")
                }
            }
        }
    )

    init {
        loadTodoItems(selectedDate = LocalDate.now())
    }

    // 완료 상태 업데이트 함수
    fun onStatus(itemId: Int) {
        intent {
            try {
                val response = userService.TodoCheck(itemId)
                if (response.isSuccessful && response.body()?.result == "success") {
                    val updatedList = _TodoList.value?.map {
                        if (it.id == itemId) it.copy(status = it.status) else it
                    }
                    _TodoList.value = updatedList
                    loadTodoItems(selectedDate = _selectedDate.value)
                } else {
                    Log.e("TodoViewModel", "Failed to check todo status")
                }
            } catch (e: Exception) {
                Log.e("TodoViewModel", "Error checking todo status", e)
            }
        }
    }


    fun addTodoItem(selectedDate: LocalDate?, itemText: String) {
        intent {
            try {
                val formattedDate = selectedDate?.format(DateTimeFormatter.ISO_LOCAL_DATE)
                val response = formattedDate?.let { userService.addtodo(it, itemText) }

                if (response != null && response.isSuccessful && response.body()?.result == "success") {
                    loadTodoItems(selectedDate)
                } else {
                    Log.e("TodoViewModel", "Failed to add todo item")
                }
            } catch (e: Exception) {
                Log.e("TodoViewModel", "Error adding todo item", e)
            }
        }
    }


    fun itemOnChange() {
    }

    fun itemOnDelete() {
    }

    //선택한 날짜 아이템 불러오기
    fun loadTodoItems(selectedDate: LocalDate?) {
        intent {
            selectedDate?.let {
                _selectedDate.value = it
                Log.d("로그3", "날짜에 대한 할 일 항목 로드 중: $selectedDate")
                val response = userService.viewtodo(it.format(DateTimeFormatter.ISO_LOCAL_DATE))
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        try {
                            val gson = Gson()
                            val json = responseBody.message.replace("'", "\"")
                            Log.d("TodoViewModel", "수신된 JSON: $json")

                            val todoMessages = parseTodoMessagesFromJson(json)
                            _TodoList.value = todoMessages
                            Log.d("할일목록", TodoList.value.toString())
                        } catch (e: Exception) {
                            Log.e("TodoViewModel", "JSON 파싱 오류: $e")
                        }
                    } else {
                        Log.e("TodoViewModel", "서버 응답에서 유효한 데이터를 찾을 수 없습니다.")
                    }
                } else {
                    Log.e("TodoViewModel", "서버 응답 실패.")
                }
            }
        }
    }

    private fun parseTodoMessagesFromJson(json: String): List<TodoMessage> {
        val listType: Type = object : TypeToken<List<List<String>>>() {}.type
        val jsonArray: List<List<String>> = Gson().fromJson(json, listType)
        return jsonArray.map {
            TodoMessage(
                id = it[0].toInt(),
                date = it[1],
                text = it[2],
                status = it[3].toInt()
            )
        }
    }

    fun onItemTextChange(itemText: String) = blockingIntent {
        reduce { state.copy(itemText = itemText) }
    }
}

@Immutable
data class TodoState(
    val selectedDate: LocalDate? = null,
    val id: Int? = null,
    val itemText: String = "",
    val Status: Boolean = false,
    val itemDone: Boolean = false,

    )

sealed interface TodoSideEffect {
    class Toast(val message: String) : TodoSideEffect
}
