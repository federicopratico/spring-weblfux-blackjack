package cat.itacademy.s04.t02.n02.blackjack.game.application.dto.result;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.entity.Game;

public record GameResult(
        String gameId,
        String gameStatus
) {
    public static GameResult from(Game game) {
        return new GameResult(
                game.getGameId().toString(),
                game.getGameStatus().name()
        );
    }
}
