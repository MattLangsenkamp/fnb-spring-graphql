package com.fnb.locations.customExceptions

class NotLoggedInExceptionException(message: String) : Exception(message)

class InvalidCredentialsException(message: String) : Exception(message)

class InsufficientPermissionsException(message: String) : Exception(message)

class IllegalArgumentException(message: String) : Exception(message)

class FailedToFetchResourceException(message: String) : Exception(message)
