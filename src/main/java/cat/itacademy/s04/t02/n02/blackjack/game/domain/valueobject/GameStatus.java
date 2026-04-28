package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject;

public enum GameStatus {
    PENDING_PLAYER,
    WAITING_FOR_BETS,
    DEALING,
    PLAYERS_TURN,
    DEALER_TURN,
    OUTCOME,
    ROUND_OVER,
}
