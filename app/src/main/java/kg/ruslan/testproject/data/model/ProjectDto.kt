package kg.ruslan.testproject.data.model

import kg.ruslan.testproject.core.data.DataMapper
import kg.ruslan.testproject.domain.model.Project
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectDto(
    @SerialName("author")
    val author: String,
    @SerialName("category")
    val category: String,
    @SerialName("date_created")
    val dateCreated: String,
    @SerialName("description")
    val description: String,
    @SerialName("image")
    val image: String,
    @SerialName("title")
    val title: String
) : DataMapper<Project> {
	
	override fun toDomain() = Project(
		author = author,
		category = category,
		dateCreated = dateCreated,
		description = description,
		image = image,
		title = title
	)
}