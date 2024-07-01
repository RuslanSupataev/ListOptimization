package kg.ruslan.testproject.presentaion.main

import kg.ruslan.testproject.core.presentation.BaseUIState
import kg.ruslan.testproject.domain.model.Project

data class MainFragmentState(
	val projects: List<Project>
) : BaseUIState()

sealed interface MainFragmentEvents {
	data object FetchProjects : MainFragmentEvents
}