package com.ccpp.shared.core.exception

class NotFoundException(var statusCode: Int = 0, message: String?) : Exception(message)
