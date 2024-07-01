package kg.ruslan.testproject.presentaion.details

import kg.ruslan.testproject.core.presentation.BaseUIState
import kg.ruslan.testproject.domain.model.Project

data class ProjectDetailsState(
	val project: Project?,
	override var showError: String?,
) : BaseUIState()

sealed interface ProjectDetailsEvents {
	data class FetchProject(val title: String) : ProjectDetailsEvents
}