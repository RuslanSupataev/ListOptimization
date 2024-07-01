package kg.ruslan.testproject.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadFromUrl(url: String) {
	Glide
		.with(this)
		.load(url)
		.diskCacheStrategy(DiskCacheStrategy.ALL)
		.into(this)
}

