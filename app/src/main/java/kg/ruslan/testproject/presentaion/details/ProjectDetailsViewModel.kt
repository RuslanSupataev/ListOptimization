package kg.ruslan.testproject.presentaion.details

import kg.ruslan.testproject.core.presentation.BaseStateViewModel
import kg.ruslan.testproject.domain.uc.GetProjectByNameUseCase
import kotlinx.coroutines.flow.update

class ProjectDetailsViewModel(
	private val getProjectByNameUseCase: GetProjectByNameUseCase,
) : BaseStateViewModel<ProjectDetailsState, ProjectDetailsEvents>(
	ProjectDetailsState(
		project = null,
		showError = null
	)
) {
	override suspend fun handleIntent(intent: ProjectDetailsEvents) {
		when (intent) {
			is ProjectDetailsEvents.FetchProject -> fetchProject(title = intent.title)
		}
	}
	
	private suspend fun fetchProject(title: String) = loadingOperation {
		handleResourceInFlow(getProjectByNameUseCase.execute(title)) { data ->
			_state.update {
				it.copy(
					project = data,
					showError = "Project not found".takeIf { data == null }
				)
			}
		}
	}
}