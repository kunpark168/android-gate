package com.anhtam.gate9.v2.search


import com.anhtam.domain.GameEntity
import com.anhtam.domain.dto.Game
import com.anhtam.gate9.R
import com.anhtam.gate9.adapter.chat.Message

class DataSource {
    companion object{

        fun getSampleGames(): List<Game> {
            val games = ArrayList<Game>()
            games.add(GameEntity("0", "Garena LQMB", R.drawable.sample_avatar_game_01))
            games.add(GameEntity("1", "Garena LOL", R.drawable.sample_avatar_game_02))
            games.add(GameEntity("2", "Garena FreeFire", R.drawable.sample_avatar_game_03))
            games.add(GameEntity("3", "Garena PUBG", R.drawable.sample_avatar_game_04))
            games.add(GameEntity("4", "Giftcode", R.drawable.sample_avatar_game_02))
            games.add(GameEntity("5", "Hiệp khách giang hồ ", R.drawable.sample_avatar_game_03))
            games.add(GameEntity("6", "Huyết Đao Mobi", R.drawable.sample_avatar_game_04))
            games.add(GameEntity("7", "Chiến tướng 3", R.drawable.sample_avatar_game_02))
            games.add(GameEntity("8", "Mộng bá vương ", R.drawable.sample_avatar_game_03))
            games.add(GameEntity("9", "Tiên kiếm truyền kỳ", R.drawable.sample_avatar_game_04))
            return games
        }

        fun getSampleChat(): List<Message> {
            val messages = ArrayList<Message>()
            messages.add(Message(0, "I got it", "19:01"))
            messages.add(Message(1, "Simple text chat", "19:02"))
            return messages
        }
    }
}