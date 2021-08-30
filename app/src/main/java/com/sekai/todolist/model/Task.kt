package com.sekai.todolist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Task(
    val id: Int,
    val title: String?,
    val description: String?,
    val date: String?,
    val hour: String?
):Parcelable
