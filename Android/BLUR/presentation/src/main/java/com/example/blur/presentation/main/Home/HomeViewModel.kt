package com.example.blur.presentation.Main.Home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.blur.data.retrofit.UserService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // Context 주입
) : ViewModel() {


}
