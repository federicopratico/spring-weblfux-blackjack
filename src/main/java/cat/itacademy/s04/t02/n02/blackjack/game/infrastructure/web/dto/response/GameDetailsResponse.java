package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.response;

import java.math.BigDecimal;

public record GameDetailsResponse(
        String gameId,
        String gameStatus,
        BigDecimal bet,
        String playerStatus,
        int playerHandValue,
        int dealerHandValue,
        String roundOutcome
) {
}
