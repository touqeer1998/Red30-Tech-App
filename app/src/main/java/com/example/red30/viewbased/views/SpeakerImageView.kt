package com.example.red30.viewbased.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import coil3.load
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.example.red30.R
import com.example.red30.databinding.ViewSpeakerImageBinding
import com.example.red30.viewbased.invisible
import com.example.red30.viewbased.visible

private const val EXAMPLE_NAME = "Alycia Jones"

class SpeakerImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewSpeakerImageBinding =
        ViewSpeakerImageBinding.inflate(LayoutInflater.from(context), this)

    var name: String = ""
        set(value) {
            field = value
            initializeView()
        }

    var imageUrl: String? = null
        set(value) {
            field = value
            initializeView()
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.SpeakerImageView) {
            name = getString(R.styleable.CircleLetterView_example_name).orEmpty().ifEmpty {
                if (isInEditMode) EXAMPLE_NAME else ""
            }
        }
        invalidate()
        requestLayout()
    }

    private fun initializeView() {
        binding.speakerInitial.name = name
        if (imageUrl.isNullOrEmpty()) {
            binding.speakerInitial.visible()
            binding.speakerImage.invisible()
        } else {
            binding.speakerImage.load(imageUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            binding.speakerInitial.invisible()
            binding.speakerImage.visible()
        }
    }
}
