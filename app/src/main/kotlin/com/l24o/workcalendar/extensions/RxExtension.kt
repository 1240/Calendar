package com.redmadrobot.core.extension

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

//region Single
fun <T> Single<T>.schedulersIoToMain(): Single<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}
//endregion

//region Observable
fun <T> Observable<T>.schedulersIoToMain(): Observable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

//endregion

//region Flowable
fun <T> Flowable<T>.schedulersIoToMain(): Flowable<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}
//endregion

//region Completable
fun Completable.schedulersIoToMain(): Completable {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}
//endregion

//region Maybe
fun <T> Maybe<T>.schedulersIoToMain(): Maybe<T> {
    return subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

//endregion