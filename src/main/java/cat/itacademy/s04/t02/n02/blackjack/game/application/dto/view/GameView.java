package cat.itacademy.s04.t02.n02.blackjack.game.application.dto.view;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.result.GameResult;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.entity.Game;

import java.math.BigDecimal;

public record GameView(
        String gameId,
        String gameStatus,
        BigDecimal bet,
        String playerStatus,
        int playerHandValue,
        int dealerHandValue,
        String roundOutcome
) {
    public static GameView from(Game game) {
        return new GameView(
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
