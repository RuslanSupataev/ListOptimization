package kg.ruslan.testproject.utils.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

typealias HolderCreator = (fakeParent: ViewGroup, viewType: Int) -> RecyclerView.ViewHolder

interface HolderPrefetcher {
    fun setViewsCount(
        viewType: Int,
        count: Int,
        holderCreator: HolderCreator
    )
}