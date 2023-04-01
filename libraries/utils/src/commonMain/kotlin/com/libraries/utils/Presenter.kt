package com.libraries.utils

abstract class Presenter<View : Any> : ViewDependencyMixin<View>() {
    open fun onResume() {}
    open fun onAppear() {}
    open fun onDisappear() {}
}
