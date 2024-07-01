package kg.ruslan.testproject.data

import kg.ruslan.testproject.domain.ProjectsRepository
import kg.ruslan.testproject.utils.AssetsReader
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val koinModuleData = module {
	factory<ProjectsRepository> { ProjectsRepositoryIml(get()) }
	factory { AssetsReader(androidApplication()) }
}