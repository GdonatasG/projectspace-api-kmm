package com.libraries.utils

abstract class ViewDependencyMixin<View : Any> : ViewController<View>() {
    abstract var viewHolder: ViewHolder<View>

    override fun setView(view: View) {
        this.viewHolder.setView(view)
    }

    override fun dropView() {
        this.viewHolder.dropView()
    }
}
