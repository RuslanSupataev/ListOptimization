package kg.ruslan.testproject.core.domain

/**
 * [Resource] is using for transfer data between data and presentation layer
 *
 * @param data is some result from local or remote data source.
 * @param message is message from some error by trying get data from local/remote data source
 */
sealed class Resource<out T>(
    open val data: T? = null,
    open val message: String? = null,
) {

    /**
     * [Success] success response from data source
     */
    class Success<T>(override val data: T) : Resource<T>(data = data)

    /**
     * [Error] result got wrong. Error is state with message te notify receiver that something went wrong
     */
    class Error<T>(override val message: String, data: T? = null, val error: Throwable? = null) : Resource<T>(data = data, message = message)
}