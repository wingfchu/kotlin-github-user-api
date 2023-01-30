package com.wendychu.github_user_api.ui.detail

sealed class UserDetailEvent {
    data class ShareProfile(var url: String) : UserDetailEvent()
}