package com.l24o.stels.common.mvp

import rx.Subscription
import rx.lang.kotlin.plusAssign
import rx.subscriptions.CompositeSubscription

abstract class RxPresenter<V : IView> (view: V) : BasePresenter<V>(view) {

    protected val subscriptions: CompositeSubscription

    init {
        subscriptions = CompositeSubscription()
    }

    override fun onViewDetached() {
        subscriptions.clear()
        super.onViewDetached()
    }

    protected fun registerSubscription(subscription: Subscription) {
        subscriptions += subscription
    }

    protected fun removeSubscription(subscription: Subscription) {
        subscriptions.remove(subscription)
    }
}