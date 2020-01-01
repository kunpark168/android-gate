package com.anhtam.gate9.v2.search

import androidx.lifecycle.ViewModel
import com.anhtam.gate9.repository.GameRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(gameRepository: GameRepository): ViewModel() {
    val games = gameRepository.getAllGame(1)
}