package com.dahlaran.genesis.data

class DataRequestException : Throwable {

    var type: DataRequestExceptionType

    constructor(type: DataRequestExceptionType) : super() {
        this.type = type
    }

    constructor(cause: Throwable?, type: DataRequestExceptionType) : super(cause) {
        this.type = type
    }

    constructor(message: String?, type: DataRequestExceptionType) : super(message) {
        this.type = type
    }
}
