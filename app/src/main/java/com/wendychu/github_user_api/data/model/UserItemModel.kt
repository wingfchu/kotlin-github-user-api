package com.wendychu.github_user_api.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserItemModel(
    var id: Int = 0,
    var username: String,
    var avatarUrl: String
): Parcelable