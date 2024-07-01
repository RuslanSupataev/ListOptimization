package kg.ruslan.testproject.presentaion.main

import kg.ruslan.testproject.core.presentation.BaseStateViewModel
import kg.ruslan.testproject.domain.uc.GetProjectsUseCase
import kotlinx.coroutines.flow.update

class MainViewModel(
	private val getProjectsUseCase: GetProjectsUseCase,
) : BaseStateViewModel<MainFragmentState, MainFragmentEvents>(
	defaultState = MainFragmentState(
		projects = listOf()
	)
) {
	override suspend fun handleIntent(intent: MainFragmentEvents) {
		when (intent) {
			MainFragmentEvents.FetchProjects -> fetchProjects()
		}
	}
	
	private suspend fun fetchProjects() {
		handleResourceInFlow(getProjectsUseCase.execute()) { data ->
			_state.update {
				it.copy(projects = data)
			}
		}
	}
}