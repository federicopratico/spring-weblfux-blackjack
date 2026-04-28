package cat.itacademy.s04.t02.n02.blackjack.game.application.dto.result;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.entity.Game;

import java.math.BigDecimal;

public record GameResult(
        String gameId,
        String gameStatus,
        BigDecimal bet,
        String playerStatus,
        int playerHandValue,
        int dealerHandValue,
        String roundOutcome
) {

    public static GameResult from(Game game) {
        return new GameResult(
                game.getGameId().toString(),
                game.getGameStatus().name(),
                game.getSeat().getBet(),
                game.getSeat().getPlayerStatus().name(),
                game.getSeat().getHand().getHandValue(),
                game.getDealer().getHand().getHandValue(),
                game.getRoundOutcome().name()
        );
    }
}
