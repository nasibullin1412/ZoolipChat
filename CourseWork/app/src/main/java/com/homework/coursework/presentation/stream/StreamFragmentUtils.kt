package com.homework.coursework.presentation.stream

import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.homework.coursework.R

internal fun Fragment.getDividerItemDecoration(): DividerItemDecoration {
    return DividerItemDecoration(
        context,
        DividerItemDecoration.VERTICAL
    ).apply {
        ResourcesCompat.getDrawable(
            resources,
            R.drawable.sh_item_border,
            requireContext().theme
        )?.let { setDrawable(it) }
    }
}
