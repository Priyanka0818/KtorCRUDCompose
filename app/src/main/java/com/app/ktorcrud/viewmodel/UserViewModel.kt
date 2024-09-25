package com.app.ktorcrud.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.ktorcrud.apicall.ApiServiceImpl
import com.app.ktorcrud.utils.AllEvents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Priyanka.
 */
class UserViewModel(
    private val apiServiceImpl: ApiServiceImpl
) :
    ViewModel() {

    private val _startRoute = MutableStateFlow("1")
    val startRoute: StateFlow<String> = _startRoute.asStateFlow()

    val _users = MutableStateFlow<AllEvents>(AllEvents.Nothing)
    val users: StateFlow<AllEvents> = _users.asStateFlow()

    fun updateStartRoute(route: String) {
        _startRoute.value = route
    }
    fun getUsers() {
        viewModelScope.launch {
            _users.value = AllEvents.Loading(true)
            apiServiceImpl.getUserList(1).either({
                _users.value = AllEvents.DynamicError(it)
                it
            }) {
                _users.value = AllEvents.Success(it.data)
                it
            }
        }
    }
}