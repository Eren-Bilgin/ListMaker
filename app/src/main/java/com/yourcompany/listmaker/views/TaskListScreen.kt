package com.yourcompany.listmaker.views

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yourcompany.listmaker.R
import com.yourcompany.listmaker.data.TaskList
import com.yourcompany.listmaker.viewmodel.ListDataManager

@Composable
fun TaskListScreen(
    navigate: ((String) -> Unit)
) {
    var checkedBoolean by remember { mutableStateOf(false) }
    val taskListViewModel: ListDataManager = viewModel()
    val checkedList = remember { mutableStateListOf<String>() }
    val uiState by taskListViewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = { checkedBoolean = !checkedBoolean }) {
                Icon(Icons.Filled.Check, contentDescription = "Localized description")
            }
            if (checkedBoolean) {
                IconButton(onClick = {
                    for (i in checkedList) {
                        taskListViewModel.deleteList(TaskList(i))
                        Log.e("TaskListScreen", "TaskListScreen: " + TaskList(i).toString())
                    }
                }) {
                    Icon(Icons.Filled.Clear, contentDescription = "")
                }
            }
        })
    }, topBar = {
        ListMakerTopAppBar(title = stringResource(id = R.string.label_list_maker),
            showBackButton = false,
            onBackPressed = {})
    }, content = {
        TaskListContent(modifier = Modifier
            .padding(it)
            .fillMaxSize(),
            uiState = uiState,
            onClick = { taskName ->
                navigate(taskName)
            },
            checkedBoolean = checkedBoolean,
            onClickCheckBox = {
                if (!checkedList.contains(it)) {
                    checkedList.add(it)
                } else {
                    checkedList.remove(it)
                }
                Log.i("checkedList", checkedList.joinToString(", "))
            })
    }, floatingActionButton = {
        ListMakerFloatingActionButton(title = stringResource(id = R.string.name_of_list),
            inputHint = stringResource(id = R.string.task_hint),
            onFabClick = {
                taskListViewModel.saveList(TaskList(it))
            })
    })
}

@Composable
fun TaskListContent(
    modifier: Modifier,
    uiState: List<TaskList>,
    onClick: (String) -> Unit,
    checkedBoolean: Boolean,
    onClickCheckBox: (String) -> Unit
) {
    if (uiState.isEmpty()) {
        EmptyView(message = stringResource(id = R.string.text_no_tasks))
    } else {
        LazyColumn(modifier = modifier, content = {
            items(uiState) {
                ListItemView(value = it.name,
                    onListItemClick = { myValue ->
                        onClick(myValue)
                    },
                    checkedBoolean = checkedBoolean,
                    onClickCheckBox = { value -> onClickCheckBox(value) })
            }
        })
    }
}