package kg.ruslan.testproject.core.data

import kg.ruslan.testproject.core.domain.Resource
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

open class BaseRepo {
    protected fun <T> doRequest(block: suspend FlowCollector<Resource<T>>.() -> Unit) = flow {
        try {
            block()
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    error = e,
                    message = e.localizedMessage ?: "Details has not been provided"
                )
            )
        }
    }
}