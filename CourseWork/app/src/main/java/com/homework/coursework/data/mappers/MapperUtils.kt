package com.homework.coursework.data.mappers

import android.os.Build
import android.text.Html


fun String.parseHtmlContent() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(this).toString()
    }

