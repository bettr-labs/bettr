package com.bettr.domain.bet_types

enum class BetType(val label: String, val emoji: String) {
    SPORTS("Apostas esportivas", "soccer"),
    SLOTS("Cassino Online/Slots", "slots"),
    LITTLE_TIGER_GAME("Jogo do Tigrinho", "tiger"),
    POQUER("Pôquer", "jester"),
    LOTTERY("Raspadinha/Loteria", "ticket"),
    OTHER("Outros jogos de azar", "dice")
}
