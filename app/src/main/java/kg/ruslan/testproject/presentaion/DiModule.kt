package kg.ruslan.testproject.presentaion

import kg.ruslan.testproject.presentaion.details.ProjectDetailsViewModel
import kg.ruslan.testproject.presentaion.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModulePresentation = module {
	viewModel { MainViewModel(get()) }
	viewModel { ProjectDetailsViewModel(get()) }
}