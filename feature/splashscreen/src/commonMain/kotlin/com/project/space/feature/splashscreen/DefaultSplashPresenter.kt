package com.project.space.feature.splashscreen

import com.libraries.utils.ViewHolder

class DefaultSplashPresenter(
    private val onStartAppFlow: () -> Unit
) : SplashPresenter() {
    override var viewHolder: ViewHolder<SplashView> = ViewHolder()

    override fun startAppFlow() {
        onStartAppFlow()
    }
}
