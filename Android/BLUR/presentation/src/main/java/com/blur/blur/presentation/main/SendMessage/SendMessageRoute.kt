package com.blur.blur.presentation.Main.SendMessage



sealed class SendMessageRoute(
    val name:String
) {
    object SendMessageScreen: SendMessageRoute("SendMessageScreen")
    object MessageUserListScreen: SendMessageRoute("MessageUserListScreen")
}