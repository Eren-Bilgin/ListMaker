package com.yourcompany.listmaker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskList(


    val name: String,
    val tasks: List<String> = listOf()


) : Parcelable