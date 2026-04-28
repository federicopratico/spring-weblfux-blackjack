package cat.itacademy.s04.t02.n02.blackjack.player.application.command;

import java.math.BigDecimal;

public record ReserveBetCommand(
        String playerId,
        String gameId,
        BigDecimal betAmount
) {
}
