package com.ballchalu.shared.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ballchalu.shared.core.exception.Failure
import com.ballchalu.shared.core.result.Either
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.usecase.internal.DefaultScheduler
import kotlinx.coroutines.CoroutineStart.DEFAULT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means than any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCase<in Params, out Type> where Type : Any {

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
        val job = GlobalScope.async(Dispatchers.Default, DEFAULT) { run(params) }
        GlobalScope.launch(Dispatchers.Main, DEFAULT) { onResult(job.await()) }
    }

//    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {
//        val job = async(CommonPool) { run(params) }
//        launch(UI) { onResult(job.await()) }
//    }

    class None
}

abstract class UseCaseBase<in P, R> {

    private val taskScheduler = DefaultScheduler

    /** Executes the use case asynchronously and places the [Results] in a MutableLiveData
     *
     * @param parameters the input parameters to run the use case with
     * @param result the MutableLiveData where the result is posted to
     *
     */
    operator fun invoke(parameters: P, result: MutableLiveData<Results<R>>) {
        // result.value = Result.Loading TODO: add data to Loading to avoid glitches
        try {
            taskScheduler.execute {
                try {
                    execute(parameters).let { useCaseResult ->
                        result.postValue(Results.Success(useCaseResult))
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                    result.postValue(Results.Error(e))
                }
            }
        } catch (e: Exception) {
            Timber.d(e)
            result.postValue(Results.Error(e))
        }
    }

    /** Executes the use case asynchronously and returns a [Results] in a new LiveData object.
     *
     * @return an observable [LiveData] with a [Results].
     *
     * @param parameters the input parameters to run the use case with
     */
    operator fun invoke(parameters: P): LiveData<Results<R>> {
        val liveCallback: MutableLiveData<Results<R>> = MutableLiveData()
        this(parameters, liveCallback)
        return liveCallback
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}

operator fun <R> UseCaseBase<Unit, R>.invoke(): LiveData<Results<R>> = this(Unit)
operator fun <R> UseCaseBase<Unit, R>.invoke(result: MutableLiveData<Results<R>>) =
    this(Unit, result)