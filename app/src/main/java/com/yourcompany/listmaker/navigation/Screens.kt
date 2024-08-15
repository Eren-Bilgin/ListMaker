package com.yourcompany.listmaker.navigation

sealed class Screens(val route: String) {
    object TaskListScreen : Screens("taskList")
    object TaskDetailScreen : Screens("taskDetail")


}