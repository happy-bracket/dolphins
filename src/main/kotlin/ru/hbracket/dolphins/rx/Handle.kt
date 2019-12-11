package ru.hbracket.dolphins.rx

import io.reactivex.rxjava3.disposables.Disposable
import ru.hbracket.dolphins.f.ts.Handle

data class RxHandle(private val disposable: Disposable) : Handle {

    override fun release() {
        disposable.dispose()
    }

}