package kg.ruslan.testproject.presentaion.main

import androidx.navigation.fragment.findNavController
import kg.ruslan.testproject.core.presentation.BaseFragmentViewBindingState
import kg.ruslan.testproject.databinding.FragmentMainBinding
import kg.ruslan.testproject.presentaion.main.rv.ProjectsAdapter
import kg.ruslan.testproject.utils.rv.HolderPrefetcher
import kg.ruslan.testproject.utils.rv.PrefetchRecycledViewPool
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
	private lateinit var viewPool: PrefetchRecycledViewPool
	private val coroutineScope: CoroutineScope
		get() = CoroutineScope(Dispatchers.IO + SupervisorJob())
	
	override fun initialize() {
		super.initialize()
		optimizeRecyclerView()
		getData()
		initUi()
		callBacks()
		setObserves(viewModel.state)
	}
	
	private fun optimizeRecyclerView() {
		viewPool = PrefetchRecycledViewPool(
			requireActivity(),
			coroutineScope
		).apply {
			prepare()
		}
		binding.rvProjects.setRecycledViewPool(viewPool)
		prefetchItems(viewPool)
	}
	
	private fun getData() {
		viewModel.handleSideEffects(MainFragmentEvents.FetchProjects)
	}
	
	private fun initUi() {
		binding.rvProjects.adapter = adapter
	}
	
	private fun callBacks() {
		adapter.onItemClick {
			findNavController().navigate(
				MainFragmentDirections.actionMainFragmentToProjectDetailsFragment(it.title)
			)
		}
	}
	
	private fun prefetchItems(holderPrefetcher: HolderPrefetcher) {
		holderPrefetcher.setViewsCount(ProjectsAdapter.PROJECT_TYPE, 100) { fakeParent, viewType ->
			adapter.onCreateViewHolder(fakeParent, viewType)
		}
	}
	
	private fun setObserves(state: StateFlow<MainFragmentState>) {
		state.partialListener(adapter::submitList) { it.projects }
	}
}