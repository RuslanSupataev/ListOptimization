package kg.ruslan.testproject.core.presentation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kg.ruslan.testproject.core.domain.Resource

/**
 * [BaseStateViewModel] is base class of your ViewModel which working with states
 *
 * @param UIState is generic which extend [BaseUIState]
 * It's your UIState which you'll use in business logic
 * @param UIIntents is generic of your UIIntents
 * It's using to handle some events from View in ViewModel
 * @param defaultState is default state of your [UIState] which will use to create it
 * @constructor is creating instance of [BaseStateViewModel] and you can use some
 * properties and member functions of it
 * @author Ruslan
 * @since version 0.0.1
 */
open class BaseStateViewModel<UIState : BaseUIState, UIIntents>(
	private val defaultState: UIState,
) : ViewModel() {
	
	/**
	 * [_state] is [MutableStateFlow] with default value as [defaultState]
	 * You able to use in to change state of your UI/View
	 * This property created to manage [state]
	 */
	protected var _state: MutableStateFlow<UIState> =
		MutableStateFlow(defaultState)
	
	/**
	 * [state] is [StateFlow] which needs to be observed in a view
	 * This state contains state of all your View
	 */
	var state = _state.asStateFlow()
	
	/**
	 * [handleSideEffects] is a function to be called from View and handled in ViewModel
	 * It contains some UI actions
	 * You can handle these action in function [handleIntent]
	 */
	val handleSideEffects: (UIIntents) -> Unit
	
	/**
	 * Progress. some loading
	 */
	private val _progressLV: MutableLiveData<Boolean> = MutableLiveData()
	val progressLV: LiveData<Boolean> = _progressLV
	
	init {
		handleSideEffects = { intent ->
			viewModelScope.launch(Dispatchers.IO) {
				handleIntent(intent = intent)
				initialize()
			}
		}
	}
	
	/**
	 * Member function to initialize some property or do some logic in your viewModel
	 */
	protected open fun initialize() {}
	
	/**
	 * [handleIntent] is member function to offer some [UIIntents] to the ViewModel
	 * You need to override and implement this function
	 *
	 * @param intent is a [UIIntents] which is some actions from View
	 */
	protected open suspend fun handleIntent(intent: UIIntents) {}
	
	/**
	 * End loading in the screen.
	 */
	private fun startLoading() {
		_progressLV.postValue(true)
	}
	
	/**
	 * Start loading in the screen.
	 */
	private fun endLoading() {
		_progressLV.postValue(false)
	}
	
	/**
	 * Make some operation covered by loading
	 */
	suspend fun loadingOperation(operation: suspend () -> Unit) {
		startLoading()
		operation()
		endLoading()
	}
	
	/**
	 * [handleResourceInFlow] is a member function to observe [Flow] and process incoming data.
	 *
	 * @param resourceFlow is a [Flow] with some [Resource] type of [T]
	 * This param will be observed to process incoming data: [Resource.Success], [Resource.Error], [Resource.Loading]
	 * @param success is a higher-order function which will invoked if result got successfully
	 * If something went wrong and result have not got successfully this function
	 * will change [state] to report error by changing [BaseUIState.showError]
	 * Also this function will manage loading state of [state]
	 * @param emptySuccess is like [success] but without body
	 */
	protected suspend fun <T> handleResourceInFlow(
		resourceFlow: Flow<Resource<T>>,
		emptySuccess: ((Resource<Any>) -> Unit)? = null,
		success: ((T) -> Unit)? = null,
	) {
		resourceFlow.collect { resource ->
			when (resource) {
		
				is Resource.Success -> {
					emptySuccess?.invoke(Resource.Success(Any()))
					success?.invoke(resource.data)
					_progressLV.postValue(false)
				}
				
				is Resource.Error -> {
					_state.update {
						it.also { it.showError = resource.message }
					}
				}
			}
		}
	}
}
