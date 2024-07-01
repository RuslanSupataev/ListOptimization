package kg.ruslan.testproject.core.data

interface DataMapper<T> {

    /**
     * Function for map data layer model to domain layer model
     */
    fun toDomain(): T
}