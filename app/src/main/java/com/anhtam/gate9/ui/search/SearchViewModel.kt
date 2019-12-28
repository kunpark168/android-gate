package com.anhtam.gate9.ui.search

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.repository.GameRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(gameRepository: GameRepository): ViewModel() {
    val games = gameRepository.getAllGame(1)
}