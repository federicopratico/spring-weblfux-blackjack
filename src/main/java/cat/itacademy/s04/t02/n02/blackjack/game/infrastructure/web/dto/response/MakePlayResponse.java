package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.response;

import java.math.BigDecimal;

public record MakePlayResponse(
        String gameId,
        String gameStatus,
        String roundOutcome,
        BigDecimal bet,
        String playerStatus,
        int playerHandValue,
        int dealerHandValue
) {
}
