package com.blur.blur.presentation.Main.Calendar.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    // 각 날짜를 표시하는 상자를 그립니다.
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(10.dp) // 간격 조정
            .clip(RoundedCornerShape(10.dp)) // 모서리 둥글게
            .background(
                color =
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer
            )
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        // 날짜를 텍스트로 표시합니다.
        Text(
            // 날짜의 일(dayOfMonth)을 문자열로 변환하여 텍스트로 설정합니다.
            text = day.date.dayOfMonth.toString(),
            // 날짜의 위치에 따라 텍스트의 색상을 다르게 설정합니다.
            color = if (day.position == DayPosition.MonthDate) Color.Black else Color.White
        )
    }
}


@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    // 요일을 표시하는 행을 그립니다.
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // 주어진 요일 목록을 반복하면서 각 요일을 텍스트로 표시합니다.
        for (dayOfWeek in daysOfWeek) {
            Text(
                // 각 요일의 텍스트 스타일을 가로 중앙 정렬로 설정합니다.
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                // 해당 요일의 짧은 이름을 얻어와 텍스트로 표시합니다.
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

