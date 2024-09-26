package com.app.ktorcrud.ui

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.app.ktorcrud.response.Data
import com.app.ktorcrud.utils.AllEvents
import com.app.ktorcrud.viewmodel.UserViewModel

/**
 * Created by Priyanka.
 */

@Composable
fun LoadUsers(userViewModel: UserViewModel) {
    val state by userViewModel.users.collectAsState(initial = AllEvents.Nothing)
    val textState = remember { mutableStateOf(TextFieldValue("")) }

    LaunchedEffect(key1 = Unit) {
        userViewModel.getUsers()
    }

    when (state) {
        is AllEvents.Loading -> {
            ProgressDialog(showDialog = (state as AllEvents.Loading).loading)
        }

        is AllEvents.StringResource -> {
            Toast.makeText(
                LocalContext.current,
                (state as AllEvents.StringResource).asString().toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        is AllEvents.Success<*> -> {
            UserActivity((state as AllEvents.Success<*>).data as ArrayList<Data>, textState)
            ProgressDialog(showDialog = false)
        }

        else -> {
            ProgressDialog(showDialog = false)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserActivity(userData: ArrayList<Data>, searchText: MutableState<TextFieldValue>) {
    val userList = getFilteredUserList(searchText, userData)

    Box() {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            CompositionLocalProvider(
                LocalOverscrollConfiguration provides null
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    itemsIndexed(userList) { index, item ->
                        Row(modifier = Modifier.fillMaxHeight()) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    item.avatar
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(80.dp, 80.dp)
                                    .clip(CircleShape)
                            )
                            Text(
                                text = "${item.first_name} ${item.last_name}",
                                color = Color.Red,
                                modifier = Modifier
                                    .align(
                                        Alignment.CenterVertically
                                    )
                                    .padding(start = 20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getFilteredUserList(
    searchText: MutableState<TextFieldValue>,
    users: ArrayList<Data>
): ArrayList<Data> {
    var filteredUserList: ArrayList<Data>
    val searchedText = searchText.value.text
    filteredUserList = when {
        searchedText.isNotEmpty() -> {
            users.filter {
                val name = "${it.first_name} ${it.last_name}"
                name.contains(searchedText, ignoreCase = true)
            } as ArrayList<Data>
        }

        else -> {
            users
        }
    }
    return filteredUserList
}
