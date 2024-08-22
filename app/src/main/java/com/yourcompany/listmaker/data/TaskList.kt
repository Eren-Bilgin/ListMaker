package com.yourcompany.listmaker.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TaskList(
    val name: String,
    var tasks: List<String> = listOf(),
) : Parcelable