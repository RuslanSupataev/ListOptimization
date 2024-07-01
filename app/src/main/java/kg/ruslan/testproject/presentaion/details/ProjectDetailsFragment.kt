package kg.ruslan.testproject.presentaion.details

import androidx.navigation.fragment.navArgs
import kg.ruslan.testproject.core.presentation.BaseFragmentViewBindingState
import kg.ruslan.testproject.databinding.FragmentProjectDetailsBinding
import kg.ruslan.testproject.domain.model.Project
import kg.ruslan.testproject.utils.loadFromUrl
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.ext.android.inject

class ProjectDetailsFragment : BaseFragmentViewBindingState<
		ProjectDetailsState,
		ProjectDetailsEvents,
		FragmentProjectDetailsBinding,
		ProjectDetailsViewModel
		>(
	inflate = FragmentProjectDetailsBinding::inflate
) {
	override val viewModel: ProjectDetailsViewModel by inject()
	private val args: ProjectDetailsFragmentArgs by navArgs()
	
	override fun initialize() {
		super.initialize()
		setObserves(viewModel.state)
		getData()
	}
	
	private fun getData() {
		viewModel.handleSideEffects(ProjectDetailsEvents.FetchProject(args.projectTitle))
	}
	
	private fun setObserves(state: StateFlow<ProjectDetailsState>) {
		state.partialListener(this::showProject) { it.project }
	}
	
	private fun showProject(project: Project?) {
		if (project == null) return
		binding.run {
			ivCover.loadFromUrl(project.image)
			tvTitle.text = project.title
			tvDesc.text = project.description
			tvAuthor.text = project.author
			tvCreationDate.text = project.dateCreated
			tvCategory.text = project.category
		}
	}
}