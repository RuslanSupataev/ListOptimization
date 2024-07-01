package kg.ruslan.testproject.presentaion.main

import kg.ruslan.testproject.core.presentation.BaseFragmentViewBindingState
import kg.ruslan.testproject.databinding.FragmentMainBinding
import kg.ruslan.testproject.presentaion.main.rv.ProjectsAdapter
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.ext.android.inject

class MainFragment : BaseFragmentViewBindingState<
		MainFragmentState,
		MainFragmentEvents,
		FragmentMainBinding,
		MainViewModel>(
	inflate = FragmentMainBinding::inflate
) {
	override val viewModel: MainViewModel by inject()
	private val adapter: ProjectsAdapter = ProjectsAdapter()
	
	override fun initialize() {
		super.initialize()
		getData()
		initUi()
		callBacks()
		setObserves(viewModel.state)
	}
	
	private fun getData() {
		viewModel.handleSideEffects(MainFragmentEvents.FetchProjects)
	}
	
	private fun initUi() {
		binding.rvProjects.adapter = adapter
	}
	
	private fun callBacks() {
		adapter.onItemClick {
			// navigate to details screen
		}
	}
	
	private fun setObserves(state: StateFlow<MainFragmentState>) {
		state.partialListener(adapter::submitList) { it.projects }
	}
}