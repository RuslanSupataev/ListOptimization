package kg.ruslan.testproject.core.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

/**
 * [BaseFragmentViewBindingState] is a base class to extend from your View (MVVM)
 * It contains some state implementation.
 *
 * @param UIState is a state of your view
 * @param UIIntents is some actions from your view to handle in ViewModel
 * @param Binding is binding of your xml file. Initialized all of your views
 * @param ViewM is your ViewModel (MVVM)
 * @param inflate is member function from your [Binding] to create instance of your xml file
 *
 * @author Ruslan
 * @see BaseStateViewModel
 */
abstract class BaseFragmentViewBindingState<
		UIState : BaseUIState,
		UIIntents,
		Binding : ViewBinding,
		ViewM : BaseStateViewModel<UIState, UIIntents>,
		>(
	private val inflate: Inflate<Binding>,
) : Fragment() {
	
	private var _binding: Binding? = null
	protected val binding get() = _binding!!
	
	abstract val viewModel: ViewM
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {
		_binding = inflate.invoke(inflater, container, false)
		return binding.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initialize()
	}
	
	/**
	 * [initialize] is member function to initialize some fragment's stuff if needed.
	 * Called immediately after [onCreateView].
	 *
	 * !Do not delete super call after overriding!
	 */
	protected open fun initialize() {
		viewModel.state.partialListener(::showError) { it.showError }
		viewModel.progressLV.observe(viewLifecycleOwner, ::showProgress)
	}
	
	/**
	 * Member function [showProgress] is calling to show progress in the ui layer
	 *
	 * @param isProgress State of Progress.
	 *
	 * True if you need to show progress.
	 * False if you need to dismiss progress.
	 */
	open fun showProgress(isProgress: Boolean) {}
	
	/**
	 * This method calling [showErrorAlert] if param message is not null
	 *
	 * @param message is some message to be displayed
	 * @see showErrorAlert
	 */
	private fun showError(message: String? = null) {
		if (message != null)
			showErrorAlert(message)
	}
	
	/**
	 * [showErrorAlert] is member function to show some error message in [AlertDialog]
	 * on the screen
	 *
	 * @param message is some message to be displayed
	 */
	protected open fun showErrorAlert(message: String) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
	}
	
	/**
	 * [partialListener] is a extension function on [StateFlow]
	 * Using to set some listener on changing some property in the state.
	 * high-order function calling in [Dispatchers.Main] thread
	 *
	 * @param T is property you need to listen
	 * @param block is high-order function which is called on change property-you-need
	 * @param transform is high-order function which called to figure out which property you need.
	 * Within param value which is [UIState] and returning [T] which needs to be property of [UIState]
	 */
	protected inline fun <T> StateFlow<UIState>.partialListener(
		crossinline block: (T) -> Unit,
		crossinline transform: suspend (value: UIState) -> T,
	) {
		viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
			map(transform)
				.distinctUntilChanged()
				.collect {
					block(it)
				}
		}
	}
	
	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}