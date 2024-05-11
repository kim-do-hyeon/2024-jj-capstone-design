package com.example.blur.presentation.Main.Home.SendMessage



sealed class SendMessageRoute(
    val name:String
) {
    object SendMessageScreen: SendMessageRoute("SendMessageScreen")
    object MessageUserListScreen: SendMessageRoute("MessageUserListScreen")
}