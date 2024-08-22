package com.yourcompany.listmaker.views

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.listmaker.R
import com.yourcompany.listmaker.data.TaskList
import com.yourcompany.listmaker.viewmodel.ListDataManager

@Composable
fun TaskDetailScreen(
    taskName: String?, onBackPressed: () -> Unit
) {
    var checkedBoolean by remember { mutableStateOf(false) }
    val checkedList = remember { mutableStateListOf<String>() }
    val viewModel: ListDataManager = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = { checkedBoolean = !checkedBoolean }) {
                Icon(Icons.Filled.Check, contentDescription = "Localized description")
            }
            if (checkedBoolean) {
                IconButton(onClick = {
                    for (i in checkedList) {
                        viewModel.deleteListItem(taskName, i)
                        Log.e("Detail", "TaskListDetail: " + TaskList(i).toString())
                    }
                }) {
                    Icon(Icons.Filled.Clear, contentDescription = "")
                }
            }
        })
    }, topBar = {
        ListMakerTopAppBar(
            title = taskName ?: stringResource(id = R.string.label_task_list),
            showBackButton = true,
            onBackPressed = onBackPressed
        )
    }, content = {
        TaskDetailScreenContent(modifier = Modifier.padding(it),
            taskTodos = uiState.firstOrNull { it.name == taskName }?.tasks ?: emptyList(),
            checkedBoolean = checkedBoolean,
            onClickCheckBox = {
                if (!checkedList.contains(it)) {
                    checkedList.add(it)
                } else {
                    checkedList.remove(it)
                }
                Log.i("checkedListDetail", checkedList.joinToString(", "))
            })
    }, floatingActionButton = {
        ListMakerFloatingActionButton(title = stringResource(id = R.string.task_to_add),
            inputHint = stringResource(id = R.string.task_hint),
            onFabClick = {
                val mutableList =
                    uiState.firstOrNull { it.name == taskName }?.tasks?.toMutableStateList()
                mutableList?.add(it)
                uiState.firstOrNull { it.name == taskName }?.tasks = mutableList?.toList()!!
                viewModel.saveList(uiState.firstOrNull { it.name == taskName }!!)
            })
    })
}

@Composable
fun TaskDetailScreenContent(
    modifier: Modifier,
    taskTodos: List<String>,
    checkedBoolean: Boolean,
    onClickCheckBox: (String) -> Unit
) {
    if (taskTodos.isEmpty()) {
        EmptyView(message = stringResource(id = R.string.text_no_todos))
    } else {
        LazyColumn(modifier = modifier, content = {
            items(taskTodos) {
                ListItemView(value = it,
                    onListItemClick = {},
                    checkedBoolean = checkedBoolean,
                    onClickCheckBox = { value -> onClickCheckBox(value) })
            }
        })
    }
}
