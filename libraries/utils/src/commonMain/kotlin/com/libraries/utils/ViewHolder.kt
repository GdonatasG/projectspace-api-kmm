package com.libraries.utils

class ViewHolder<View : Any> : ViewController<View>() {
    private var view: View? = null

    fun get(): View? = view

    override fun setView(view: View) {
        this.view = view
    }

    override fun dropView() {
        view = null
    }
}
