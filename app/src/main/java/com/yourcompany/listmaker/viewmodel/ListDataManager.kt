package com.yourcompany.listmaker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.preference.PreferenceManager
import com.yourcompany.listmaker.data.TaskList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListDataManager(private val application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow<List<TaskList>>(listOf())
    val uiState = _uiState.asStateFlow()

    init {
        readLists()
    }

    fun saveList(list: TaskList) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(application).edit()
        sharedPrefs.putStringSet(list.name, list.tasks.toHashSet())
        sharedPrefs.apply()
        readLists()
    }

    private fun readLists() {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(application)
        val contents = sharedPrefs.all
        val taskLists = mutableListOf<TaskList>()
        for (taskList in contents) {
            val taskItems = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, taskItems)
            taskLists.add(list)
        }
        _uiState.value = taskLists
    }

    fun deleteList(list: TaskList) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(application).edit()
        sharedPrefs.remove(list.name)
        sharedPrefs.remove(list.tasks.toString())
        sharedPrefs.apply()
        readLists()
    }

    fun deleteListItem(name: String?, taskName: String) {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(application)
        val task = sharedPrefs.getStringSet(name, mutableSetOf())
        val newList = task?.filter { it != taskName }?.toSet()
        sharedPrefs.edit().putStringSet(name, newList).apply()
    }
}