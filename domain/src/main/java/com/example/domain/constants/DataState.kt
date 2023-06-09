package com.example.domain.constants

sealed class DataState<T>(
    val data: T? = null,
    val message: String? = null
)
{
    class Success<T>(data: T) : DataState<T>(data)
    class Error<T>(message: String, data: T? = null) : DataState<T>(data, message)
    class Loading<T>(data: T? = null) : DataState<T>(data)
    class Auth<T>(message: String?=null, data: T? = null) : DataState<T>(data,message)
}