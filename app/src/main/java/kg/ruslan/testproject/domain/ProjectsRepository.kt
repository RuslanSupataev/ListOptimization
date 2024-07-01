package kg.ruslan.testproject.domain

import kg.ruslan.testproject.core.domain.Resource
import kg.ruslan.testproject.domain.model.Project
import kotlinx.coroutines.flow.Flow

interface ProjectsRepository {
	fun getProjects() : Flow<Resource<List<Project>>>
	fun findByTitle(title: String) : Flow<Resource<Project?>>
}