package com.libraries.utils

abstract class ViewController<View> {
    open fun setView(view: View) {}
    open fun dropView() {}
}
