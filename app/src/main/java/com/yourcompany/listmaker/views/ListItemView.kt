package com.yourcompany.listmaker.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListItemView(
    value: String,
    onListItemClick: (String) -> Unit,
    checkedBoolean: Boolean,
    onClickCheckBox: (String) -> Unit
) {
    var checked by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(8.dp)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onListItemClick(value) },
        shape = shape,
        color = Color.LightGray.copy(alpha = 0.3f),
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = value, modifier = Modifier.padding(4.dp)
            )
            if (checkedBoolean) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                        onClickCheckBox(value)
                    },
                )
            }
            Spacer(Modifier.size(8.dp))
        }
    }
}
