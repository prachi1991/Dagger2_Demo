package com.ballchalu.ui.razorpay.base

sealed class Failure {
    class NetworkConnection : Failure()
    class ServerError : Failure()
    class SessionError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}
