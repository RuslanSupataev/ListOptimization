package kg.ruslan.testproject.domain.uc

import kg.ruslan.testproject.domain.ProjectsRepository

class GetProjectsUseCase(
	private val projectsRepository: ProjectsRepository
) {
	fun execute() = projectsRepository.getProjects()
}