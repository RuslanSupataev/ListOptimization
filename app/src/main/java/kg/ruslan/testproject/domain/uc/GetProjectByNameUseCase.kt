package kg.ruslan.testproject.domain.uc

import kg.ruslan.testproject.core.domain.Resource
import kg.ruslan.testproject.domain.ProjectsRepository
import kg.ruslan.testproject.domain.model.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetProjectByNameUseCase(
	private val projectsRepository: ProjectsRepository,
) {
	
	fun execute(title: String): Flow<Result> = projectsRepository.findByTitle(title).map {
		val data = it.data
		when (it) {
			is Resource.Error -> Result.Failure(it.message)
			is Resource.Success -> if (data == null) Result.NotFound
			else Result.Success(data)
		}
	}
	
	sealed interface Result {
		data object NotFound : Result
		data class Failure(val message: String) : Result
		data class Success(val project: Project) : Result
	}
}