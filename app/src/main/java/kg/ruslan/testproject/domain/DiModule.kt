package kg.ruslan.testproject.domain

import kg.ruslan.testproject.domain.uc.GetProjectByNameUseCase
import kg.ruslan.testproject.domain.uc.GetProjectsUseCase
import org.koin.dsl.module

val koinModuleDomain = module {
	factory { GetProjectByNameUseCase(get()) }
	factory { GetProjectsUseCase(get()) }
}