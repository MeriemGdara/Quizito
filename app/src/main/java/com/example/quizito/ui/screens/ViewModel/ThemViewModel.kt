package com.example.quizito.ui.screens.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.quizito.domain.use_cases.GetThemesUseCase
import androidx.lifecycle.ViewModel
import com.example.quizito.domain.models.Theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val getThemesUseCase: GetThemesUseCase
) : ViewModel() {

    var themes by mutableStateOf(emptyList<Theme>())
        private set

    var searchQuery by mutableStateOf("")

    init {
        loadThemes()
    }

    private fun loadThemes() = viewModelScope.launch {
        themes = getThemesUseCase()
    }


    fun filteredThemes(): List<Theme> {
        return if (searchQuery.isEmpty()) themes
        else themes.filter { it.title.contains(searchQuery, ignoreCase = true) }
    }
}
