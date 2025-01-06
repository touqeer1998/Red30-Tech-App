package com.example.red30.viewbased.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.red30.databinding.ViewTimeItemBinding

class TimeItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ViewTimeItemBinding =
        ViewTimeItemBinding.inflate(LayoutInflater.from(context), this)

    fun setValues(
        iconResourceId: Int,
        labelResourceId: Int,
        text: String
    ) {
        binding.icon.setImageResource(iconResourceId)
        binding.iconLabel.text = context.getString(labelResourceId)
        binding.text.text = text
    }
}
