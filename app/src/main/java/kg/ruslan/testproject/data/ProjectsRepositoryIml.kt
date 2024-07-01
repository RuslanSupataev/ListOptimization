package kg.ruslan.testproject.data

import kg.ruslan.testproject.core.data.BaseRepo
import kg.ruslan.testproject.core.domain.Resource
import kg.ruslan.testproject.data.model.ProjectDto
import kg.ruslan.testproject.domain.ProjectsRepository
import kg.ruslan.testproject.domain.model.Project
import kg.ruslan.testproject.utils.AssetsReader
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class ProjectsRepositoryIml(
	private val assetsReader: AssetsReader,
) : ProjectsRepository, BaseRepo() {
	
	private var cachedProjects : List<ProjectDto> = listOf()
	
	override fun findByTitle(title: String): Flow<Resource<Project?>> = request {
		if (cachedProjects.isEmpty()) loadProjects()
		
		val result = cachedProjects.first {
			it.title == title
		}.toDomain()
		
		emit(Resource.Success(result))
	}
	
	override fun getProjects(): Flow<Resource<List<Project>>> = request {
		if (cachedProjects.isEmpty()) loadProjects()
		
		emit(Resource.Success(cachedProjects.map { it.toDomain() }))
	}
	
	private fun loadProjects() {
		val data = assetsReader.readJSONFromAssets("data.json")
		cachedProjects = Json.decodeFromString(data)
	}
}