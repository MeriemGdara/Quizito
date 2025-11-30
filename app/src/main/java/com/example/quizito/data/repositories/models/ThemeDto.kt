package com.example.quizito.data.repositories.models

import com.example.quizito.domain.models.Theme
import android.content.Context
data class ThemeDto(
    val id: String = "",
    val title: String = "",
    val imageName: String = ""   // ex: "animals"
) {
    fun toDomain(context: Context): Theme {
        val resId = context.resources.getIdentifier(
            imageName, "drawable", context.packageName
        )
        return Theme(id, title, if (resId != 0) resId else null)
    }
}

