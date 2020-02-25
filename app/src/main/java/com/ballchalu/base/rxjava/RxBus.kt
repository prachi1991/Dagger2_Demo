package com.ballchalu.base.rxjava

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

object RxBus {
    private val publisher: PublishSubject<Any> = PublishSubject.create<Any>()
    fun publish(event: Any) {
        publisher.onNext(event)
    }

    fun toObservable(): Observable<Any> {
        return publisher
    }

    fun <T> listen(eventType: Class<T>): Observable<T> =
        publisher.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).ofType(
            eventType
        )

    fun hasObservers(): Boolean {
        return publisher.hasObservers()
    }
}