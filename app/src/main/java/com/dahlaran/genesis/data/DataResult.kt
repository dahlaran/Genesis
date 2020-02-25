package com.dahlaran.genesis.data

// Result of a data; with success or error who can use any type of data
sealed class DataResult<T>(var source: DataResultSource) {
    class Success<T>(val data: T, source: DataResultSource) : DataResult<T>(source)
    class Error<T>(val throwable: DataRequestException, source: DataResultSource) : DataResult<T>(source)
}