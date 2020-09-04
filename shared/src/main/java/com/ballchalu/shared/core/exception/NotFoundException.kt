package com.ballchalu.shared.core.exception

class NotFoundException(var statusCode: Int = 0, message: String?) : Exception(message)
