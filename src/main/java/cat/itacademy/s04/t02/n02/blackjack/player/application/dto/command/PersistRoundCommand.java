package cat.itacademy.s04.t02.n02.blackjack.player.application.dto.command;

import java.math.BigDecimal;

public record PersistRoundCommand(
        String gameId,
        String playerId,
        BigDecimal reservedBetAmount,
        BigDecimal profitAmount,
        String roundOutcome
) {
}
