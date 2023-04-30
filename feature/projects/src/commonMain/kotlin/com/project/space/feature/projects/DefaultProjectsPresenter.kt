package com.project.space.feature.projects

import com.libraries.utils.PlatformScopeManager
import com.libraries.utils.ViewHolder

class DefaultProjectsPresenter(
    private val scope: PlatformScopeManager = PlatformScopeManager()
) : ProjectsPresenter() {
    override var viewHolder: ViewHolder<ProjectsView> = ViewHolder()

    private val view
        get() = viewHolder.get()
}
