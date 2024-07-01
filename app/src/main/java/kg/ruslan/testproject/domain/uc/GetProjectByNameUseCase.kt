package kg.ruslan.testproject.domain.uc

import kg.ruslan.testproject.core.domain.Resource
import kg.ruslan.testproject.domain.ProjectsRepository
import kg.ruslan.testproject.domain.model.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetProjectByNameUseCase(
	private val projectsRepository: ProjectsRepository,
) {
	fun execute(title: String): Flow<Resource<Project?>> = projectsRepository.findByTitle(title)
}