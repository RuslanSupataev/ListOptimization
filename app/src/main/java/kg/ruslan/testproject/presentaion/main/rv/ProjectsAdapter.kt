package kg.ruslan.testproject.presentaion.main.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kg.ruslan.testproject.databinding.ItemProjectBinding
import kg.ruslan.testproject.domain.model.Project
import kg.ruslan.testproject.utils.loadFromUrl

typealias OnItemClickCallBack = (Project) -> Unit

class ProjectsAdapter : ListAdapter<Project, ProjectsAdapter.ProjectViewHolder>(
	DiffUtilCallBack
) {
	
	private var clickCallBack: OnItemClickCallBack? = null
	
	fun onItemClick(onItemClickCallBack: OnItemClickCallBack) {
		clickCallBack = onItemClickCallBack
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
		return ProjectViewHolder(ItemProjectBinding.inflate(LayoutInflater.from(parent.context), parent, false))
	}
	
	override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
		// making ui slower
		Thread.sleep(50)
		
		holder.onBind(getItem(position))
	}
	
	companion object {
		const val PROJECT_TYPE = 2333
		
		private val DiffUtilCallBack = object : DiffUtil.ItemCallback<Project>() {
			override fun areItemsTheSame(oldItem: Project, newItem: Project) = oldItem.hashCode() == newItem.hashCode()
			override fun areContentsTheSame(oldItem: Project, newItem: Project) = oldItem == newItem
		}
	}
	
	inner class ProjectViewHolder(
		private val binding: ItemProjectBinding
	) : ViewHolder(binding.root) {
		
		fun onBind(project: Project) {
			binding.run {
				root.setOnClickListener {
					clickCallBack?.invoke(project)
				}
				
				tvTitle.text = project.title
				ivImage.loadFromUrl(project.image)
			}
		}
	}
}